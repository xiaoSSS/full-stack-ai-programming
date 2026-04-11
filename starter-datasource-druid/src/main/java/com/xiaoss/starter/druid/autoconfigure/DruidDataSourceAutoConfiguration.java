package com.xiaoss.starter.druid.autoconfigure;

import com.alibaba.druid.pool.DruidDataSource;
import com.xiaoss.starter.druid.aop.ReadOnlyRouteAspect;
import com.xiaoss.starter.druid.aop.WriteRouteAspect;
import com.xiaoss.starter.druid.properties.DruidStarterProperties;
import com.xiaoss.starter.druid.properties.DruidStarterProperties.DatasourceNodeProperties;
import com.xiaoss.starter.druid.routing.RoutingDruidDataSource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;

@AutoConfiguration
@ConditionalOnClass(DruidDataSource.class)
@EnableConfigurationProperties(DruidStarterProperties.class)
@EnableTransactionManagement
public class DruidDataSourceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(DataSource.class)
    public DataSource dataSource(DruidStarterProperties properties) {
        DruidDataSource writeDataSource = createPrimaryDataSource(properties);

        if (!properties.isRoutingEnabled() || properties.getReplicas() == null || properties.getReplicas().isEmpty()) {
            return writeDataSource;
        }

        Map<Object, Object> targetDataSources = new LinkedHashMap<>();
        targetDataSources.put(RoutingDruidDataSource.writeKey(), writeDataSource);

        List<String> readKeys = properties.getReplicas().stream()
                .filter(replica -> StringUtils.hasText(replica.getUrl()))
                .map(replica -> {
                    int index = targetDataSources.size();
                    String key = "read-" + index;
                    targetDataSources.put(key, createReplicaDataSource(properties, replica));
                    return key;
                })
                .collect(Collectors.toList());

        if (readKeys.isEmpty()) {
            if (properties.getRouting().isStrict()) {
                throw new IllegalStateException("datasource.druid.routing.enabled=true but no valid datasource.druid.replicas[*].url configured");
            }
            return writeDataSource;
        }

        RoutingDruidDataSource routingDataSource = new RoutingDruidDataSource(readKeys);
        routingDataSource.setDefaultTargetDataSource(writeDataSource);
        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.afterPropertiesSet();
        return routingDataSource;
    }

    @Bean
    @ConditionalOnMissingBean(PlatformTransactionManager.class)
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @ConditionalOnMissingBean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager) {
        return new TransactionTemplate(platformTransactionManager);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "datasource.druid.routing", name = "annotation-enabled", havingValue = "true", matchIfMissing = true)
    public ReadOnlyRouteAspect readOnlyRouteAspect() {
        return new ReadOnlyRouteAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "datasource.druid.routing", name = "annotation-enabled", havingValue = "true", matchIfMissing = true)
    public WriteRouteAspect writeRouteAspect() {
        return new WriteRouteAspect();
    }

    private DruidDataSource createPrimaryDataSource(DruidStarterProperties properties) {
        DatasourceNodeProperties primary = properties.getPrimary();
        DruidDataSource dataSource = newBaseDataSource(properties);
        dataSource.setUrl(firstNonBlank(primary.getUrl(), properties.getUrl()));
        dataSource.setUsername(firstNonBlank(primary.getUsername(), properties.getUsername()));
        dataSource.setPassword(firstNonBlank(primary.getPassword(), properties.getPassword()));
        dataSource.setDriverClassName(firstNonBlank(primary.getDriverClassName(), properties.getDriverClassName()));
        if (properties.getRouting().isStrict() && !StringUtils.hasText(dataSource.getUrl())) {
            throw new IllegalStateException("datasource.druid.primary.url (or datasource.druid.url) must be configured");
        }
        return dataSource;
    }

    private DruidDataSource createReplicaDataSource(DruidStarterProperties properties, DatasourceNodeProperties replica) {
        DruidDataSource dataSource = newBaseDataSource(properties);
        dataSource.setUrl(replica.getUrl());
        dataSource.setUsername(firstNonBlank(replica.getUsername(), properties.getUsername()));
        dataSource.setPassword(firstNonBlank(replica.getPassword(), properties.getPassword()));
        dataSource.setDriverClassName(firstNonBlank(replica.getDriverClassName(), properties.getDriverClassName()));
        return dataSource;
    }

    private DruidDataSource newBaseDataSource(DruidStarterProperties properties) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setInitialSize(properties.getInitialSize());
        dataSource.setMinIdle(properties.getMinIdle());
        dataSource.setMaxActive(properties.getMaxActive());
        dataSource.setMaxWait(properties.getMaxWait().toMillis());
        return dataSource;
    }

    private String firstNonBlank(String first, String second) {
        return StringUtils.hasText(first) ? first : second;
    }
}

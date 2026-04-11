package com.xiaoss.starter.druid.routing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class RoutingDruidDataSource extends AbstractRoutingDataSource {

    private static final String WRITE_KEY = "write";
    private final AtomicInteger readIndex = new AtomicInteger(0);
    private final List<String> readKeys;

    public RoutingDruidDataSource(List<String> readKeys) {
        this.readKeys = readKeys == null ? Collections.emptyList() : new ArrayList<>(readKeys);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        DataSourceType manual = DataSourceContextHolder.current();
        if (manual == DataSourceType.READ && !readKeys.isEmpty()) {
            return nextReadKey();
        }
        if (manual == DataSourceType.WRITE) {
            return WRITE_KEY;
        }

        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly() && !readKeys.isEmpty()) {
            return nextReadKey();
        }
        return WRITE_KEY;
    }

    private String nextReadKey() {
        int index = Math.floorMod(readIndex.getAndIncrement(), readKeys.size());
        return readKeys.get(index);
    }

    public static String writeKey() {
        return WRITE_KEY;
    }
}

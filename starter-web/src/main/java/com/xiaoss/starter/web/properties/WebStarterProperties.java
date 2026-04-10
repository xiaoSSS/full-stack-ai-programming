package com.xiaoss.starter.web.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "xiaoss.web")
public class WebStarterProperties {

    private String zoneId = "Asia/Shanghai";
    private String dateTimePattern = "yyyy-MM-dd HH:mm:ss";
    private boolean writeLongAsString = true;
    private boolean contextEnabled = true;
    private boolean enumConverterEnabled = true;

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getDateTimePattern() {
        return dateTimePattern;
    }

    public void setDateTimePattern(String dateTimePattern) {
        this.dateTimePattern = dateTimePattern;
    }

    public boolean isWriteLongAsString() {
        return writeLongAsString;
    }

    public void setWriteLongAsString(boolean writeLongAsString) {
        this.writeLongAsString = writeLongAsString;
    }

    public boolean isContextEnabled() {
        return contextEnabled;
    }

    public void setContextEnabled(boolean contextEnabled) {
        this.contextEnabled = contextEnabled;
    }

    public boolean isEnumConverterEnabled() {
        return enumConverterEnabled;
    }

    public void setEnumConverterEnabled(boolean enumConverterEnabled) {
        this.enumConverterEnabled = enumConverterEnabled;
    }
}

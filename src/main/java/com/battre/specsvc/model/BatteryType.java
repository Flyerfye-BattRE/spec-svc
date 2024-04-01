package com.battre.specsvc.model;

import org.springframework.data.annotation.Id;


public class BatteryType {
    @Id
    private int batteryTypeId;
    private int testSchemeId;
    private String mfc;
    private int mfcId;
    private int terminalLayoutId;
    private int batteryTierId;
    private String composition;
    private String safetyInfo;
    private double minVoltage;
    private double maxVoltage;
    private double minCurrent;
    private double maxCurrent;

    public int getBatteryTypeId() {
        return batteryTypeId;
    }

    public void setBatteryTypeId(int batteryTypeId) {
        this.batteryTypeId = batteryTypeId;
    }

    public int getTestSchemeId() {
        return testSchemeId;
    }

    public void setTestSchemeId(int testSchemeId) {
        this.testSchemeId = testSchemeId;
    }

    public String getMfc() {
        return mfc;
    }

    public void setMfc(String mfc) {
        this.mfc = mfc;
    }

    public int getMfcId() {
        return mfcId;
    }

    public void setMfcId(int mfcId) {
        this.mfcId = mfcId;
    }

    public int getTerminalLayoutId() {
        return terminalLayoutId;
    }

    public void setTerminalLayoutId(int terminalLayoutId) {
        this.terminalLayoutId = terminalLayoutId;
    }

    public int getBatteryTierId() {
        return batteryTierId;
    }

    public void setBatteryTierId(int batteryTierId) {
        this.batteryTierId = batteryTierId;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getSafetyInfo() {
        return safetyInfo;
    }

    public void setSafetyInfo(String safetyInfo) {
        this.safetyInfo = safetyInfo;
    }

    public double getMinVoltage() {
        return minVoltage;
    }

    public void setMinVoltage(double minVoltage) {
        this.minVoltage = minVoltage;
    }

    public double getMaxVoltage() {
        return maxVoltage;
    }

    public void setMaxVoltage(double maxVoltage) {
        this.maxVoltage = maxVoltage;
    }

    public double getMinCurrent() {
        return minCurrent;
    }

    public void setMinCurrent(double minCurrent) {
        this.minCurrent = minCurrent;
    }

    public double getMaxCurrent() {
        return maxCurrent;
    }

    public void setMaxCurrent(double maxCurrent) {
        this.maxCurrent = maxCurrent;
    }
}
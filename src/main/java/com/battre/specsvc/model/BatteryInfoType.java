package com.battre.specsvc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "BatteryInfo", schema = "SpecSvcSchema")
public class BatteryInfoType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "battery_type_id")
    private int batteryTypeId;

    @Column(name = "test_scheme_id")
    private int testSchemeId;

    @Column(name = "mfc")
    private String mfc;

    @Column(name = "mfc_id")
    private int mfcId;

    @Column(name = "terminal_layout_id")
    private int terminalLayoutId;

    @Column(name = "tier_id")
    private int tierId;

    @Column(name = "composition")
    private String composition;

    @Column(name = "safety_info")
    private String safetyInfo;

    @Column(name = "min_voltage")
    private double minVoltage;

    @Column(name = "max_voltage")
    private double maxVoltage;

    @Column(name = "min_current")
    private double minCurrent;

    @Column(name = "max_current")
    private double maxCurrent;

    public BatteryInfoType() {
    }

    public BatteryInfoType(int batteryTypeId, int terminalLayoutId, int tierId) {
        this.batteryTypeId = batteryTypeId;
        this.terminalLayoutId = terminalLayoutId;
        this.tierId = tierId;
    }

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

    public int getTierId() {
        return tierId;
    }

    public void setTierId(int tierId) {
        this.tierId = tierId;
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
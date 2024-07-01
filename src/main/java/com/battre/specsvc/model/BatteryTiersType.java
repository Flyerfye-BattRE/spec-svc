package com.battre.specsvc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "BatteryTiers", schema = "SpecSvcSchema")
public class BatteryTiersType {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "tier_id")
  private Integer tierId;

  @Column(name = "tier_label", nullable = false, length = 2)
  private String tierLabel;

  public BatteryTiersType() {}

  public BatteryTiersType(String tierLabel) {
    this.tierLabel = tierLabel;
  }

  public Integer getBatteryTierId() {
    return tierId;
  }

  public void setBatteryTierId(Integer tierId) {
    this.tierId = tierId;
  }

  public String getBatteryTierLabel() {
    return tierLabel;
  }

  public void setBatteryTierLabel(String tierLabel) {
    this.tierLabel = tierLabel;
  }
}

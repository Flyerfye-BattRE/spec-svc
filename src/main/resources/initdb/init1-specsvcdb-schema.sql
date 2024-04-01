-- -----------------------------------------------------
-- Schema SpecSvcDb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS SpecSvcDb;

CREATE TABLE IF NOT EXISTS SpecSvcDb.TerminalLayouts (
  terminal_layout_id SERIAL PRIMARY KEY,
  terminal_layout_name VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS SpecSvcDb.BatteryTiers (
  battery_tier_id SERIAL PRIMARY KEY,
  battery_tier VARCHAR(2) NOT NULL
);

CREATE TABLE IF NOT EXISTS SpecSvcDb.BatteryTypes (
  battery_type_id SERIAL PRIMARY KEY,
  test_scheme_id INT NOT NULL,
  mfc VARCHAR(45) NOT NULL,
  mfc_id INT NOT NULL,
  terminal_layout_id INT NOT NULL,
  battery_tier_id INT NOT NULL,
  composition VARCHAR(45) NOT NULL,
  safety_info VARCHAR(45),
  min_voltage DECIMAL(10,3),
  max_voltage DECIMAL(10,3),
  min_current DECIMAL(10,3),
  max_current DECIMAL(10,3),
  CONSTRAINT terminal_layout_id FOREIGN KEY (terminal_layout_id) REFERENCES SpecSvcDb.TerminalLayouts(terminal_layout_id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT battery_tier_id FOREIGN KEY (battery_tier_id) REFERENCES SpecSvcDb.BatteryTiers(battery_tier_id) ON DELETE NO ACTION ON UPDATE NO ACTION
);
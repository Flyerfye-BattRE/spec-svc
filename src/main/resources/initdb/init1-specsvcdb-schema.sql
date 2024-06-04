-- -----------------------------------------------------
-- Schema SpecSvcSchema
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS SpecSvcSchema;

CREATE TABLE IF NOT EXISTS SpecSvcSchema.TerminalLayouts (
  terminal_layout_id SERIAL PRIMARY KEY,
  terminal_layout_name VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS SpecSvcSchema.BatteryTiers (
  tier_id SERIAL PRIMARY KEY,
  tier_label VARCHAR(2) NOT NULL
);

CREATE TABLE IF NOT EXISTS SpecSvcSchema.BatteryInfo (
  battery_type_id SERIAL PRIMARY KEY,
  test_scheme_id INT NOT NULL,
  mfc VARCHAR(45) NOT NULL,
  mfc_id INT NOT NULL,
  terminal_layout_id INT NOT NULL,
  tier_id INT NOT NULL,
  composition VARCHAR(45) NOT NULL,
  safety_info VARCHAR(45),
  min_voltage DECIMAL(10,3),
  max_voltage DECIMAL(10,3),
  min_current DECIMAL(10,3),
  max_current DECIMAL(10,3),
  CONSTRAINT terminal_layout_id_fk FOREIGN KEY (terminal_layout_id) REFERENCES TerminalLayouts(terminal_layout_id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT tier_id_fk FOREIGN KEY (tier_id) REFERENCES BatteryTiers(tier_id) ON DELETE NO ACTION ON UPDATE NO ACTION
);
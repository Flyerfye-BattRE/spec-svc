-- Insert statements for the "BatteryTiers" table
INSERT INTO SpecSvcSchema.BatteryTiers (battery_tier_id, battery_tier) VALUES
(1, 'AX'),
(2, 'CX'),
(3, 'DX'),
(4, 'K'),
(5, 'L'),
(6, 'T'),
(7, 'XK'),
(8, 'XL');

-- Insert statements for the "TerminalLayouts" table
INSERT INTO SpecSvcSchema.TerminalLayouts (terminal_layout_id, terminal_layout_name) VALUES
(1, 'Grid'),
(2, 'Radial'),
(3, 'Cascade'),
(4, 'Parallel'),
(5, 'Cluster');

-- Insert statements for the "BatteryTypes" table
INSERT INTO SpecSvcSchema.BatteryTypes (battery_type_id, test_scheme_id, mfc, mfc_id, terminal_layout_id, battery_tier_id, composition, safety_info, min_voltage, max_voltage, min_current, max_current) VALUES
(1, 2, 'Test Manufacturer', 3, 4, 5, 'Test Composition', 'Test Safety', '0', '10', '0', '10'),
(2, 1, 'Test Manufacturer', 2, 1, 7, 'Test Composition', 'Test Safety', '0', '20', '0', '40'),
(3, 4, 'Test Manufacturer', 3, 4, 2, 'Test Composition', 'Test Safety', '0', '5', '0', '20');
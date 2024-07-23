-- Insert statements for the "BatteryTiers" table
INSERT INTO SpecSvcSchema.BatteryTiers (tier_id, tier_label)
SELECT * FROM (VALUES
    (1, 'AX'),
    (2, 'CX'),
    (3, 'DX'),
    (4, 'K'),
    (5, 'L'),
    (6, 'T'),
    (7, 'XK'),
    (8, 'XL')
) AS v (tier_id, tier_label)
WHERE NOT EXISTS (
    SELECT 1 FROM SpecSvcSchema.BatteryTiers
);

-- Insert statements for the "TerminalLayouts" table
INSERT INTO SpecSvcSchema.TerminalLayouts (terminal_layout_id, terminal_layout_name)
SELECT * FROM (VALUES
    (1, 'Grid'),
    (2, 'Radial'),
    (3, 'Cascade'),
    (4, 'Parallel'),
    (5, 'Cluster')
) AS v (terminal_layout_id, terminal_layout_name)
WHERE NOT EXISTS (
    SELECT 1 FROM SpecSvcSchema.TerminalLayouts
);

-- Insert statements for the "BatteryTypes" table
INSERT INTO SpecSvcSchema.BatteryInfo (battery_type_id, battery_name, test_scheme_id, mfc, mfc_id, terminal_layout_id, tier_id, composition, safety_info, min_voltage, max_voltage, min_current, max_current)
SELECT * FROM (VALUES
    (1, 'Test Battery 1', 2, 'Test Manufacturer', 3, 4, 5, 'Test Composition', 'Test Safety', '0', '10', '0', '10'),
    (2, 'Test Battery 2', 1, 'Test Manufacturer', 2, 1, 7, 'Test Composition', 'Test Safety', '0', '20', '0', '40'),
    (3, 'Test Battery 3', 4, 'Test Manufacturer', 3, 4, 2, 'Test Composition', 'Test Safety', '0', '5', '0', '20')
) AS v (battery_type_id, battery_name, test_scheme_id, mfc, mfc_id, terminal_layout_id, tier_id, composition, safety_info, min_voltage, max_voltage, min_current, max_current)
WHERE NOT EXISTS (
    SELECT 1 FROM SpecSvcSchema.BatteryInfo
);
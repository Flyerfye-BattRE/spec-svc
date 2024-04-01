-- Insert statements for the "BatteryTiers" table
INSERT INTO SpecSvcDb.BatteryTiers (battery_tier_id, battery_tier) VALUES
(1, 'AX'),
(2, 'CX'),
(3, 'DX'),
(4, 'K'),
(5, 'L'),
(6, 'T'),
(7, 'XK'),
(8, 'XL');

-- Insert statements for the "TerminalLayouts" table
INSERT INTO SpecSvcDb.TerminalLayouts (terminal_layout_id, terminal_layout_name) VALUES
(1, 'Grid'),
(2, 'Radial'),
(3, 'Cascade'),
(4, 'Parallel'),
(5, 'Cluster');

-- Insert statements for the "BatteryTypes" table
INSERT INTO SpecSvcDb.BatteryTypes (battery_type_id, test_scheme_id, mfc, mfc_id, terminal_layout_id, battery_tier_id, composition, safety_info, min_voltage, max_voltage, min_current, max_current) VALUES
(1, 3, 'Big Batteries Inc', 1, 5, 1, 'Lead-Acid', 'Prevent Liquid Seepage', '220.000', '740.000', '324.000', '772.000'),
(2, 2, 'Super Charge Ltd', 2, 4, 2, 'Nickel-Cadmium', '', '210.000', '780.000', '335.000', '859.000'),
(3, 8, 'Fast Electron LLC', 3, 3, 3, 'Zinc-Carbon', 'Avoid Overcharging', '290.000', '640.000', '368.000', '912.000'),
(4, 7, 'Power Pack Co', 4, 2, 4, 'Lithium-Ion', 'Crash Protection', '220.000', '550.000', '295.000', '1111.000'),
(5, 5, 'Zip Zap Industries', 5, 1, 5, 'Lead-Acid', '', '240.000', '810.000', '287.000', '1010.000'),
(6, 1, 'Lightning Bolt Biz', 6, 2, 6, 'Lithium Polymer', '', '220.000', '770.000', '277.000', '804.000'),
(7, 4, 'Big Batteries Inc', 1, 3, 7, 'Lead-Acid', '', '260.000', '685.000', '312.000', '750.000'),
(8, 6, 'Fast Electron LLC', 3, 4, 8, 'Lithium-Ion', 'Temperature Management', '190.000', '692.000', '308.000', '793.000'),
(9, 9, 'Big Batteries Inc', 1, 5, 7, 'Zinc-Carbon', 'Temperature Management', '280.000', '710.000', '292.000', '812.000'),
(10, 8, 'Zip Zap Industries', 5, 4, 6, 'Lead-Acid', '', '180.000', '700.000', '279.000', '864.000'),
(11, 4, 'Super Charge Ltd', 2, 3, 5, 'Nickel-Cadmium', 'Prevent Liquid Seepage', '320.000', '805.000', '284.000', '891.000');
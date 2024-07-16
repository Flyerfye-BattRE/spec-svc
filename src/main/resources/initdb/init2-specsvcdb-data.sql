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
    (1, 'VoltGuard 5000', 3, 'Big Batteries Inc', 1, 5, 1, 'Lead-Acid', 'Prevent Liquid Seepage', 220.000, 740.000, 324.000, 772.000),
    (2, 'PowerPulse X2', 2, 'Super Charge Ltd', 2, 4, 2, 'Nickel-Cadmium', '', 210.000, 780.000, 335.000, 859.000),
    (3, 'Energex', 8, 'Fast Electron LLC', 3, 3, 3, 'Zinc-Carbon', 'Avoid Overcharging', 290.000, 640.000, 368.000, 912.000),
    (4, 'ChargeMax 750', 7, 'Power Pack Co', 4, 2, 4, 'Lithium-Ion', 'Crash Protection', 220.000, 550.000, 295.000, 1111.000),
    (5, 'ElectroBoost 9', 5, 'Zip Zap Industries', 5, 1, 5, 'Lead-Acid', '', 240.000, 810.000, 287.000, 1010.000),
    (6, 'DynamoCell', 1, 'Lightning Bolt Biz', 6, 2, 6, 'Lithium Polymer', '', 220.000, 770.000, 277.000, 804.000),
    (7, 'HyperCharge', 4, 'Big Batteries Inc', 1, 3, 7, 'Lead-Acid', '', 260.000, 685.000, 312.000, 750.000),
    (8, 'TitanPower 300', 6, 'Fast Electron LLC', 3, 4, 8, 'Lithium-Ion', 'Temperature Management', 190.000, 692.000, 308.000, 793.000),
    (9, 'VoltMaster', 9, 'Big Batteries Inc', 1, 5, 7, 'Zinc-Carbon', 'Temperature Management', 280.000, 710.000, 292.000, 812.000),
    (10, 'MegaCell', 8, 'Zip Zap Industries', 5, 4, 6, 'Lead-Acid', '', 180.000, 700.000, 279.000, 864.000),
    (11, 'PowerCore 7', 4, 'Super Charge Ltd', 2, 3, 5, 'Nickel-Cadmium', 'Prevent Liquid Seepage', 320.000, 805.000, 284.000, 891.000),
    (12, 'SuperVolt', 5, 'ElectroCell Innovations', 11, 3, 8, 'Zinc-Nickel', 'Maintain proper containment', 154.000, 724.000, 268.000, 748.000),
    (13, 'ChargePro 400', 6, 'EnergiCore Solutions', 8, 5, 3, 'Nickel-Iron', '', 123.000, 807.000, 270.000, 919.000),
    (14, 'EnergiPlus', 3, 'Volt Vault Enterprises', 7, 1, 4, 'Nickel-Manganese', '', 174.000, 805.000, 236.000, 926.000),
    (15, 'ElectroForce 1500', 2, 'ElectroCell Innovations', 11, 4, 7, 'Lithium-Cobalt', 'Monitor under adequate illumination', 217.000, 805.000, 367.000, 832.000),
    (16, 'DynamoX', 7, 'ElectroCell Innovations', 11, 3, 6, 'Aluminum-Lithium', 'Engage certified recyclers for disposal', 154.000, 709.000, 378.000, 788.000),
    (17, 'UltraCharge 88', 1, 'Charge Master Holdings', 10, 2, 5, 'Copper-Oxide', '', 248.000, 856.000, 304.000, 723.000),
    (18, 'TitanCell', 8, 'EnergiCore Solutions', 8, 5, 4, 'Titanium-Silicon', '', 136.000, 801.000, 369.000, 848.000),
    (19, 'VoltPlus', 9, 'Charge Master Holdings', 10, 1, 3, 'Copper-Oxide', '', 195.000, 791.000, 228.000, 741.000),
    (20, 'MegaPower', 5, 'EnergiCore Solutions', 8, 3, 2, 'Iron-Sulfide', '', 219.000, 826.000, 240.000, 775.000),
    (21, 'PowerX 25', 4, 'Battery Boost Technologies', 12, 2, 8, 'Lithium-Manganese', 'Monitor under adequate illumination', 120.000, 847.000, 234.000, 658.000),
    (22, 'HyperCell', 6, 'Dynamo Dynamics Corp', 9, 4, 7, 'Nickel-Iron', 'Utilize non-invasive diagnostics', 174.000, 835.000, 337.000, 893.000),
    (23, 'SuperCharge 450', 7, 'EnergiCore Solutions', 8, 5, 6, 'Zinc-Nickel', 'Maintain proper containment', 252.000, 716.000, 372.000, 683.000),
    (24, 'EnergiCore', 3, 'Dynamo Dynamics Corp', 9, 1, 5, 'Zinc-Nickel', '', 219.000, 766.000, 274.000, 710.000),
    (25, 'ElectroPulse', 2, 'EnergiCore Solutions', 8, 2, 4, 'Lithium-Cobalt', 'Maintain proper containment', 233.000, 786.000, 360.000, 843.000),
    (26, 'DynamoPro 210', 9, 'Volt Vault Enterprises', 7, 4, 3, 'Titanium-Silicon', '', 125.000, 870.000, 368.000, 685.000),
    (27, 'UltraVolt', 1, 'Charge Master Holdings', 10, 3, 2, 'Iron-Sulfide', '', 129.000, 741.000, 348.000, 902.000),
    (28, 'TitanBoost', 8, 'Volt Vault Enterprises', 7, 5, 8, 'Nickel-Iron', 'Engage certified recyclers for disposal', 152.000, 760.000, 294.000, 617.000),
    (29, 'VoltForce 95', 5, 'Dynamo Dynamics Corp', 9, 1, 7, 'Titanium-Silicon', '', 139.000, 797.000, 424.000, 840.000),
    (30, 'MegaCharge', 4, 'Volt Vault Enterprises', 7, 2, 6, 'Lithium-Manganese', '', 216.000, 879.000, 411.000, 884.000),
    (31, 'PowerBoost', 7, 'Dynamo Dynamics Corp', 9, 3, 5, 'Titanium-Silicon', 'Maintain proper containment', 174.000, 739.000, 355.000, 782.000),
    (32, 'HyperVolt', 1, 'ElectroCell Innovations', 11, 4, 4, 'Aluminum-Lithium', 'Engage certified recyclers for disposal', 186.000, 741.000, 241.000, 629.000),
    (33, 'SuperCell 1600', 8, 'Battery Boost Technologies', 12, 5, 3, 'Nickel-Manganese', '', 122.000, 827.000, 445.000, 620.000),
    (34, 'ChargeCore', 9, 'Charge Master Holdings', 10, 1, 2, 'Copper-Oxide', '', 123.000, 887.000, 451.000, 634.000),
    (35, 'EnergiForce 72', 5, 'Dynamo Dynamics Corp', 9, 2, 8, 'Lithium-Manganese', '', 233.000, 734.000, 349.000, 674.000),
    (36, 'ElectroX', 4, 'Charge Master Holdings', 10, 3, 7, 'Zinc-Nickel', '', 118.000, 774.000, 274.000, 940.000),
    (37, 'DynamoPlus 320', 6, 'ElectroCell Innovations', 11, 4, 6, 'Nickel-Iron', '', 250.000, 896.000, 423.000, 822.000),
    (38, 'UltraPower', 7, 'Volt Vault Enterprises', 7, 5, 5, 'Nickel-Iron', 'Use shock-absorbent packaging', 202.000, 769.000, 452.000, 734.000),
    (39, 'TitanX', 3, 'Charge Master Holdings', 10, 1, 4, 'Lithium-Cobalt', 'Engage certified recyclers for disposal', 200.000, 888.000, 391.000, 917.000),
    (40, 'VoltPro 880', 2, 'Battery Boost Technologies', 12, 2, 3, 'Lithium-Cobalt', 'Monitor under adequate illumination', 182.000, 767.000, 366.000, 940.000),
    (41, 'MegaVolt', 9, 'ElectroCell Innovations', 11, 3, 2, 'Magnesium-Titanium', '', 121.000, 862.000, 251.000, 780.000)
) AS v (battery_type_id, test_scheme_id, mfc, mfc_id, terminal_layout_id, tier_id, composition, safety_info, min_voltage, max_voltage, min_current, max_current)
WHERE NOT EXISTS (
    SELECT 1 FROM SpecSvcSchema.BatteryInfo
);
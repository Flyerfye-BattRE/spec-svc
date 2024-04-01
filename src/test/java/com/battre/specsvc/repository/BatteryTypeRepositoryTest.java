package com.battre.specsvc.repository;

import com.battre.specsvc.model.BatteryType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

// Ensuring the gRPC port doesn't conflict when running this test in parallel with the main application tests
@SpringBootTest(properties = {"grpc.server.port=50006"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BatteryTypeRepositoryTest {
    private static final Logger logger = Logger.getLogger(BatteryTypeRepositoryTest.class.getName());

    @Autowired
    private BatteryTypeRepository batteryTypeRepository;


    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() throws IOException, SQLException {
        // Execute SQL script to create tables and insert test data
        executeSqlScript("initdb/init1-specsvctestdb-schema.sql");
        executeSqlScript("initdb/init2-specsvctestdb-data.sql");

        logger.log(Level.INFO, "test setUp finished successfully");
    }


    private void executeSqlScript(String scriptPath) throws IOException, SQLException {
        // Load SQL script from resources
        ClassPathResource resource = new ClassPathResource(scriptPath);
        // Execute script using ScriptUtils
        ScriptUtils.executeSqlScript(dataSource.getConnection(), resource);
    }

    @Test
    void testGetRandomBatteryType() {
        // Call the method under test
        BatteryType randomBatteryType = batteryTypeRepository.getRandomBatteryType();

        // Verify the result
        assertNotNull(randomBatteryType);
        // All entries in the test database are expected to have the same test manufacturer
        assertEquals("Test Manufacturer", randomBatteryType.getMfc());
    }
}

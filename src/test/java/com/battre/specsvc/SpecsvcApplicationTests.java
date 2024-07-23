package com.battre.specsvc;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "grpc.server.port=9010")
class SpecsvcApplicationTests {
  @Test
  void contextLoads() {}
}

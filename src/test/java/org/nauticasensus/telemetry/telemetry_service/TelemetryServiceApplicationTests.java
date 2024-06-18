package org.nauticasensus.telemetry.telemetry_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@Import(TestContainersConfiguration.class)
@SpringBootTest
class TelemetryServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}

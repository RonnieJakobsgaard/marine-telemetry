package org.nauticasensus.telemetry.telemetry;

import org.springframework.boot.SpringApplication;

public class TestTelemetryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(TelemetryServiceApplication::main).with(TestContainersConfiguration.class).run(args);
	}

}

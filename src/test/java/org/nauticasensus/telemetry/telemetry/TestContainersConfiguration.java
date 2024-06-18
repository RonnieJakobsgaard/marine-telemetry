package org.nauticasensus.telemetry.telemetry;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@TestConfiguration(proxyBeanMethods = false)
@Testcontainers
class TestContainersConfiguration {

	@Bean
	@ServiceConnection
	public TimeScaleDbContainer<?> timeSeriesContainer() {
		return new TimeScaleDbContainer<>();
	}

	public static class TimeScaleDbContainer<SELF extends TimeScaleDbContainer<SELF>> extends JdbcDatabaseContainer<SELF> {

		public static final Integer POSTGRESQL_PORT = 5432;

		static final String DEFAULT_USER = "test";

		static final String DEFAULT_PASSWORD = "test";

		private String databaseName = "test";

		private String username = "test";

		private String password = "test";

		private static final String FSYNC_OFF_OPTION = "fsync=off";

		public TimeScaleDbContainer() {
			this(DockerImageName.parse("timescale/timescaledb:latest-pg16"));
		}

		public TimeScaleDbContainer(final DockerImageName dockerImageName) {
			super(dockerImageName);

			this.waitStrategy =
					new LogMessageWaitStrategy()
							.withRegEx(".*database system is ready to accept connections.*\\s")
							.withTimes(2)
							.withStartupTimeout(Duration.of(60, ChronoUnit.SECONDS));
			this.setCommand("postgres", "-c", FSYNC_OFF_OPTION);

			addExposedPort(POSTGRESQL_PORT);
		}

		@Override
		protected void configure() {
			// Disable Postgres driver use of java.util.logging to reduce noise at startup time
			withUrlParam("loggerLevel", "OFF");
			addEnv("POSTGRES_DB", databaseName);
			addEnv("POSTGRES_USER", username);
			addEnv("POSTGRES_PASSWORD", password);
		}

		@Override
		public String getDriverClassName() {
			return "org.postgresql.Driver";
		}

		@Override
		public String getJdbcUrl() {
			String additionalUrlParams = constructUrlParameters("?", "&");
			return (
					"jdbc:postgresql://" +
							getHost() +
							":" +
							getMappedPort(POSTGRESQL_PORT) +
							"/" +
							databaseName +
							additionalUrlParams
			);
		}

		@Override
		public String getUsername() {
			return username;
		}

		@Override
		public String getPassword() {
			return password;
		}

		@Override
		protected String getTestQueryString() {
			return "SELECT 1";
		}
	}
}

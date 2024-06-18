CREATE TABLE sensor_data (
    time TIMESTAMPTZ NOT NULL,
    sensor_id TEXT NOT NULL,
    unit_of_measurement TEXT NOT NULL,
    value DOUBLE PRECISION NULL
);

SELECT create_hypertable('sensor_data', 'time');
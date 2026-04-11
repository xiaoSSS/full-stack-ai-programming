package com.xiaoss.starter.utils.idgen;

public final class IdGenUtils {

    private static final Snowflake SNOWFLAKE = new Snowflake(1, 1);

    private IdGenUtils() {
    }

    public static long nextId() {
        return SNOWFLAKE.nextId();
    }

    private static final class Snowflake {
        private static final long EPOCH = 1704067200000L;
        private static final long MACHINE_BITS = 5L;
        private static final long DATACENTER_BITS = 5L;
        private static final long SEQUENCE_BITS = 12L;

        private static final long MAX_MACHINE = ~(-1L << MACHINE_BITS);
        private static final long MAX_DATACENTER = ~(-1L << DATACENTER_BITS);

        private static final long MACHINE_SHIFT = SEQUENCE_BITS;
        private static final long DATACENTER_SHIFT = SEQUENCE_BITS + MACHINE_BITS;
        private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS + MACHINE_BITS + DATACENTER_BITS;

        private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

        private final long machineId;
        private final long datacenterId;
        private long sequence = 0L;
        private long lastTimestamp = -1L;

        private Snowflake(long datacenterId, long machineId) {
            if (machineId > MAX_MACHINE || machineId < 0 || datacenterId > MAX_DATACENTER || datacenterId < 0) {
                throw new IllegalArgumentException("Invalid snowflake node config");
            }
            this.machineId = machineId;
            this.datacenterId = datacenterId;
        }

        private synchronized long nextId() {
            long current = System.currentTimeMillis();
            if (current < lastTimestamp) {
                throw new IllegalStateException("Clock moved backwards");
            }
            if (current == lastTimestamp) {
                sequence = (sequence + 1) & SEQUENCE_MASK;
                if (sequence == 0) {
                    current = waitNextMillis(current);
                }
            } else {
                sequence = 0L;
            }
            lastTimestamp = current;
            return ((current - EPOCH) << TIMESTAMP_SHIFT)
                | (datacenterId << DATACENTER_SHIFT)
                | (machineId << MACHINE_SHIFT)
                | sequence;
        }

        private long waitNextMillis(long current) {
            long now = System.currentTimeMillis();
            while (now <= current) {
                now = System.currentTimeMillis();
            }
            return now;
        }
    }
}

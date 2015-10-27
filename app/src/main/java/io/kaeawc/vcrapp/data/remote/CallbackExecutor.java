package io.kaeawc.vcrapp.data.remote;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CallbackExecutor {

    // initialize Executor
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static int FIXED_CORE_SIZE;

    static {
        FIXED_CORE_SIZE = CPU_COUNT;
        // not need more that 2 threads in FIXED_POOL
        if (CPU_COUNT > 2) {
            FIXED_CORE_SIZE = 2;
        }
    }

    public static final Executor EXECUTOR = Executors.newFixedThreadPool
            (FIXED_CORE_SIZE);

}

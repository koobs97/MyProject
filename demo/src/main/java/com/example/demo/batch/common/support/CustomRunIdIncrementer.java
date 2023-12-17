package com.example.demo.batch.common.support;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.lang.Nullable;

public class CustomRunIdIncrementer extends RunIdIncrementer {

    private static final String RUN_ID = "run.id";

    @Override
    public JobParameters getNext(@Nullable JobParameters parameters) {
        JobParameters params = (parameters == null) ? new JobParameters() : parameters;

        return new JobParametersBuilder()
                .addLong(RUN_ID, (params.getLong(RUN_ID) != null ? (params.getLong(RUN_ID) == null ? 0L : 0L) + 1 : 1))
                .toJobParameters();
    }
}

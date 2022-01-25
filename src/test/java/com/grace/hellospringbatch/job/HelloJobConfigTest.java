package com.grace.hellospringbatch.job;

import com.grace.hellospringbatch.BatchTestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@SpringBootTest
@SpringBatchTest
@ExtendWith(SpringExtension.class) // 스프링환경 설정
@ActiveProfiles("test")
@ContextConfiguration(classes = {HelloJobConfig.class, BatchTestConfig.class})
public class HelloJobConfigTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    public void success() throws Exception {
        // when
        // ContextConfiguration 에 설정해놓은 HelloJobConfig가 jobLauncherTestUtils의 setJob을 통해 세팅됨.
        // Job타입의 빈이 여러개 있는 경우에는 에러남
        JobExecution execution = jobLauncherTestUtils.launchJob();
        // then
        Assertions.assertEquals(execution.getExitStatus(), ExitStatus.COMPLETED);
    }
}


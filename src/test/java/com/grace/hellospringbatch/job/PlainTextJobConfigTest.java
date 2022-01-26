package com.grace.hellospringbatch.job;

import com.grace.hellospringbatch.BatchTestConfig;
import com.grace.hellospringbatch.core.domain.PlainText;
import com.grace.hellospringbatch.core.repository.PlainTextRepository;
import com.grace.hellospringbatch.core.repository.ResultTextRepository;
import org.junit.jupiter.api.AfterEach;
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

import java.util.stream.IntStream;


@SpringBootTest
@SpringBatchTest
@ExtendWith(SpringExtension.class) // 스프링환경 설정
@ActiveProfiles("test")
@ContextConfiguration(classes = {PlainTextJobConfig.class, BatchTestConfig.class})
public class PlainTextJobConfigTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private PlainTextRepository plainTextRepository;
    @Autowired
    private ResultTextRepository resultTextRepository;

    @AfterEach
    public void tearDown(){
        plainTextRepository.deleteAll();
        resultTextRepository.deleteAll();
    }

    @Test
    public void success_givenNoPlainText() throws Exception {
        // given
        // no plainText

        // when
        JobExecution execution = jobLauncherTestUtils.launchJob();
        // then
        Assertions.assertEquals(execution.getExitStatus(), ExitStatus.COMPLETED);
        Assertions.assertEquals(resultTextRepository.count(), 0);
    }

    @Test
    public void success_givenPlainText() throws Exception {
        // given
        givenPlainText(12);

        // when
        JobExecution execution = jobLauncherTestUtils.launchJob();

        // then
        Assertions.assertEquals(execution.getExitStatus(), ExitStatus.COMPLETED);
        Assertions.assertEquals(resultTextRepository.count(), 12);
    }

    // 텍스트 저장
    private void givenPlainText(Integer count){
        IntStream.range(0, count)
                .forEach(
                        num -> plainTextRepository.save(new PlainText(null, "text" + num))
                );
    }
}


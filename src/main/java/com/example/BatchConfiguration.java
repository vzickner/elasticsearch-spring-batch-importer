package com.example;

import com.example.domain.Employee;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Valentin Zickner
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Bean
    public Job importJob(JobBuilderFactory jobBuilderFactory,
                         Step importEmployeeStep) {
        return jobBuilderFactory.get("importJob")
                .start(importEmployeeStep)
                .build();
    }

    @Bean
    public Step importEmployeeStep(StepBuilderFactory stepBuilderFactory,
                                   ItemReader<Employee> employeeItemReader) {
        return stepBuilderFactory.get("importEmployeeStep")
                .chunk(20)
                .reader(employeeItemReader)
                .writer(e -> e.forEach(System.out::println))
                .build();
    }

}

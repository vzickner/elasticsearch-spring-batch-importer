package com.example;

import com.example.domain.Employee;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @author Valentin Zickner
 */
@Configuration
@EnableBatchProcessing
@EnableElasticsearchRepositories
public class BatchConfiguration {

    @Bean
    public Job importJob(JobBuilderFactory jobBuilderFactory,
                         Step importEmployeeStep) {
        return jobBuilderFactory.get("importJob")
                .incrementer(new RunIdIncrementer())
                .start(importEmployeeStep)
                .build();
    }

    @Bean
    public Step importEmployeeStep(StepBuilderFactory stepBuilderFactory,
                                   ItemReader<Employee> employeeItemReader,
                                   ItemWriter<Employee> employeeItemWriter) {
        return stepBuilderFactory.get("importEmployeeStep")
                .<Employee, Employee>chunk(1000)
                .reader(employeeItemReader)
                .writer(employeeItemWriter)
                .build();
    }

}

package com.example;

import com.example.domain.Employee;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Valentin Zickner
 */
@Component
@JobScope
public class EmployeesItemWriter implements ItemWriter<Employee> {

    private ElasticsearchEmployeeRepository elasticsearchEmployeeRepository;

    @Autowired
    public EmployeesItemWriter(ElasticsearchEmployeeRepository elasticsearchEmployeeRepository) {
        this.elasticsearchEmployeeRepository = elasticsearchEmployeeRepository;
    }

    @Override
    public void write(List<? extends Employee> items) throws Exception {
        this.elasticsearchEmployeeRepository.save(items);
    }
}

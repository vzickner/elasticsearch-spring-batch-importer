package com.example;

import com.example.domain.Employee;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

/**
 * @author Valentin Zickner
 */
public interface ElasticsearchEmployeeRepository extends ElasticsearchCrudRepository<Employee, Integer> {
}

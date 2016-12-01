package com.example;

import com.example.domain.Employee;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Valentin Zickner
 */
@Component
@JobScope
public class EmployeesItemWriter implements ItemWriter<Employee> {

    private ElasticsearchTemplate elasticsearchTemplate;
    private Date date;
    private Long runId;

    @Autowired
    public EmployeesItemWriter(ElasticsearchTemplate elasticsearchTemplate,
                               @Value("#{jobParameters['run.id']}") Long runId) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.runId = runId;
    }


    @Override
    public void write(List<? extends Employee> items) throws Exception {
        List<IndexQuery> indexQueries = items.stream()
                .map(item -> new IndexQueryBuilder().withObject(item).withId(String.valueOf(item.getEmpNo())))
                .map(builder -> builder.withType("employee"))
                .map(builder -> builder.withIndexName("employees-" + runId))
                .map(IndexQueryBuilder::build)
                .collect(Collectors.toList());

        this.elasticsearchTemplate.bulkIndex(indexQueries);
    }
}

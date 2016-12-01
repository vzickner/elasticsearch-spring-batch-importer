package com.example;

import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.AliasQuery;
import org.springframework.stereotype.Component;

import javax.batch.api.AbstractBatchlet;

import static org.springframework.batch.core.ExitStatus.COMPLETED;

/**
 * @author Valentin Zickner
 */
@Component
@JobScope
public class CreateIndexBatchletStep extends AbstractBatchlet {

    private ElasticsearchTemplate elasticsearchTemplate;
    private Long runId;

    @Autowired
    public CreateIndexBatchletStep(ElasticsearchTemplate elasticsearchTemplate,
                                   @Value("#{jobParameters['run.id']}") Long runId) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.runId = runId;
    }

    @Override
    public String process() throws Exception {
        AliasQuery aliasQuery = new AliasQuery();
        aliasQuery.setAliasName("employees");
        aliasQuery.setIndexName("employees-" + runId);
        elasticsearchTemplate.addAlias(aliasQuery);
        return COMPLETED.toString();
    }
}

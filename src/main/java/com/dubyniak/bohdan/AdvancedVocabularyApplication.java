package com.dubyniak.bohdan;

import com.dubyniak.bohdan.exception.mapper.EntityNotFoundExceptionMapper;
import com.dubyniak.bohdan.exception.mapper.NoDatabaseColumnsExceptionMapper;
import com.dubyniak.bohdan.resources.WordCardResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AdvancedVocabularyApplication extends Application<AdvancedVocabularyConfiguration> {
    private static final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-beans.xml");

    private WordCardResource wordCardResource;

    public static void main(final String[] args) throws Exception {
        ((AdvancedVocabularyApplication) applicationContext.getBean("advancedVocabularyApplication")).run(args);
    }

    @Override
    public String getName() {
        return "AdvancedVocabulary";
    }

    @Override
    public void initialize(final Bootstrap<AdvancedVocabularyConfiguration> bootstrap) {
    }

    @Override
    public void run(final AdvancedVocabularyConfiguration configuration, final Environment environment) {
        environment.jersey().register(wordCardResource);
        environment.jersey().register(new EntityNotFoundExceptionMapper());
        environment.jersey().register(new NoDatabaseColumnsExceptionMapper());
    }

    @Autowired
    public void setWordCardResource(WordCardResource wordCardResource) {
        this.wordCardResource = wordCardResource;
    }
}

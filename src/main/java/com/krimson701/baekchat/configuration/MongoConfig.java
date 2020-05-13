package com.krimson701.baekchat.configuration;


import com.krimson701.baekchat.configuration.properties.MongoProp;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class MongoConfig {

    @Autowired
    private MongoProp mongoProp;

    @Bean
    public MongoTemplate mongoTemplate(MongoMappingContext context) {
        MongoDbFactory mongoDbFactory = mongoDbFactory();
        MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), context);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, converter);
        return mongoTemplate;
    }

    public MongoDbFactory mongoDbFactory() {

        String dataBaseName = mongoProp.getDatabase();

        MongoClientURI uri = new MongoClientURI(mongoProp.getUri());
        MongoClient mongoClient = new MongoClient(uri);

        // Mongo DB Factory
        return new SimpleMongoDbFactory(mongoClient, dataBaseName);
    }
}
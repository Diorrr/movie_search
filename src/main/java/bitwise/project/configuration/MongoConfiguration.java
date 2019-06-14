package bitwise.project.configuration;

import com.mongodb.MongoClient;
import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;

@Configuration
@EnableMongoAuditing
public class MongoConfiguration {

    private final MongoConfigurationProperties properties;

    public MongoConfiguration(MongoConfigurationProperties configurationProperties) {
        this.properties = configurationProperties;
    }

    @Bean
    public MongoTemplate mongoTemplate() throws IOException {
        EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
        mongo.setBindIp(properties.getHost());
        mongo.setPort(properties.getPort());
        MongoClient mongoClient = mongo.getObject();
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, properties.getDatabase());

        return mongoTemplate;
    }
}

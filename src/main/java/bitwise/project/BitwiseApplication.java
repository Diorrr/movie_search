package bitwise.project;

import bitwise.project.configuration.MongoConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableConfigurationProperties(value = MongoConfigurationProperties.class)
@Slf4j
@EnableMongoRepositories
public class BitwiseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BitwiseApplication.class, args);
    }

}

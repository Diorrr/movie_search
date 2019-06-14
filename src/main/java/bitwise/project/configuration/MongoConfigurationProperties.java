package bitwise.project.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "mongodb.embedded")
public class MongoConfigurationProperties {

    private String host;

    private int port;

    private String database;

}

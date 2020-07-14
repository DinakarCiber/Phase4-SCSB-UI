package org.recap.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "monitoring")
public class MonitoringConfiguration {
    private Map<String, String> url;
    private final Logging logging = new Logging();

    @Data
    public static class Logging {
        private Map<String, String> url;
    }
}

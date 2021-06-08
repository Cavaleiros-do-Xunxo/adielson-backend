package calaveirosdoxunxo.adielson.spring.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("security")
public class SecurityProperties {

    private String token;
    private String header;
    private String prefix;
    private long expiration;

}

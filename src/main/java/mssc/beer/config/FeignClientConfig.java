package mssc.beer.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig
{
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor(@Value("${mssc.brewery.inventory.username}") String username,
                                                                   @Value("${mssc.brewery.inventory.password}") String password)
    {
        return new BasicAuthRequestInterceptor(username, password);
    }
}

package nudemeth.poc.identity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class Configurer implements WebMvcConfigurer {

    @Autowired
    private Environment environment;

    private Logger logger = LoggerFactory.getLogger(Configurer.class);

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] origins = GetAllowedOrigins();
        registry.addMapping("/**").allowedOrigins(origins);
    }

    private String[] GetAllowedOrigins() {
        String url = environment.getProperty("api.url");
        String urlTls = environment.getProperty("api.url.tls");
        String apiUrl = System.getenv("API_BASE_URL");
        String apiUrlTls = System.getenv("API_BASE_URL_TLS");

        if (apiUrl != null) {
            url = apiUrl;
        }

        if (apiUrlTls != null) {
            urlTls = apiUrlTls;
        }

        logger.info(String.format("API url: %s, %s", url, urlTls));
        
        return new String[] { url, urlTls };
    }
}
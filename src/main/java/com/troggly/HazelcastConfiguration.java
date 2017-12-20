package com.troggly;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

/**
 * Created by Vlad on 27.07.2017.
 */
@Configuration
public class HazelcastConfiguration {
    private static int TEN_MINUES = 10 * 60;

    @Bean
    public Config config() {
        return new Config()
                .addMapConfig(
                        new MapConfig()
                                .setName("token-cache")
                                .setEvictionPolicy(EvictionPolicy.LRU)
                                .setTimeToLiveSeconds(TEN_MINUES))
                .setProperty("hazelcast.logging.type", "slf4j");
    }
    @Bean
    public ViewResolver viewResolver(){
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        System.out.println("create:viewResolver");
        return viewResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine(){
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        System.out.println("create:templateEngine");
        return templateEngine;
    }

    @Bean
    public TemplateResolver templateResolver(){
        TemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        System.out.println("create:templateResolver");
        return templateResolver;
    }

}

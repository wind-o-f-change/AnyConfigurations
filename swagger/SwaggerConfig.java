package ru.ocrv.uad.backend.config.swagger;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashMap;
import java.util.Map;

import static ru.ocrv.uad.backend.constants.TagNames.*;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * <b>Конфиг для документации REST API</b>
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {

    @Bean
    public Docket api(SwaggerConfigProperties swaggerConfigProperties) {

        return addTags(new Docket(DocumentationType.SWAGGER_2))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(
                        Predicates.or(
                                regex( swaggerConfigProperties.getApiURL() + "/.*" )
                        ))

                .build();
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    private Docket addTags(Docket docket) {
        Map<String, String> tagMap = new HashMap<>();
        tagMap.put(TYPE_DEF_CONTROLLER, "Этот контроллер для получения возможных у сущностей типов из БД");
        tagMap.put(USER_CONTROLLER, "CRUD контроллер для класса 'User'");
        tagMap.put(ROLE_CONTROLLER, "CRUD контроллер для класса 'Role'");
        tagMap.put(ZONE_CONTROLLER, "CRUD контроллер для класса 'Zone'");
        tagMap.put(ENTITY_DETAIL_CONTROLLER, "CRUD контроллер для класса 'EntityDetail'");
        tagMap.put(RELATIONSHIP_CONTROLLER, "CRUD контроллер связей терминов (класс 'Relationship')");
        tagMap.put(PARENT_ENTITY_CONTROLLER, "Этот контроллер для получения всех сущностей-родителей из БД (которые не от кого не наследуются)");
        tagMap.put(TYPEDEF_LOCALISATION_CONTROLLER, "Этот контроллер для типов терминов с локализованными полями из БД");

        for (Map.Entry<String, String> entry : tagMap.entrySet()) {
            docket.tags(new Tag(entry.getKey(), entry.getValue()));
        }

        return docket;
    }
}
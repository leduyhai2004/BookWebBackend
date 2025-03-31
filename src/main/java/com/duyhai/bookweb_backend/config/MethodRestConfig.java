package com.duyhai.bookweb_backend.config;

import com.duyhai.bookweb_backend.entity.Type;
import com.duyhai.bookweb_backend.entity.User;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

// se config la ben front-end chi dc truy cap den nhung api nao
@Configuration
public class MethodRestConfig implements RepositoryRestConfigurer {
    private String url = "http://localhost:3000";

    @Autowired
    private EntityManager entityManager;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {


        //export id : cho phep show id trong json response
        config.exposeIdsFor(entityManager.getMetamodel().getEntities().stream()
                .map(entityType -> entityType.getJavaType()) // Use getJavaType() instead
                .toArray(Class[]::new));
//      config.exposeIdsFor(Type.class); : show id cho mot lop cu the


//        cors.addMapping("/**")
//                .allowedOrigins(url)
//                .allowedMethods("GET", "POST", "PUT", "DELETE"); // cho phep ben fe truy cap

//        HttpMethod[] chanCacPhuongThuc = {
////                HttpMethod.POST,
////                HttpMethod.PUT,
////                HttpMethod.PATCH,
//                HttpMethod.DELETE,
//        };
//        disableHttpMethod(User.class,config,chanCacPhuongThuc);
    }

    private void disableHttpMethod(Class c,
                                   RepositoryRestConfiguration configuration,
                                   HttpMethod[] methods) {
        configuration.getExposureConfiguration().forDomainType(c)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(methods))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(methods));
    }
}

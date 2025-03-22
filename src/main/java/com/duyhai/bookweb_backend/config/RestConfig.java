package com.duyhai.bookweb_backend.config;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;


@Component
public class RestConfig implements RepositoryRestConfigurer {
    @Autowired
    private EntityManager entityManager;

//    @Override
//    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
//        config.exposeIdsFor(entityManager.getMetamodel().getEntities().stream()
//                .map(entityType -> entityType.getJavaType()) // Use getJavaType() instead
//                .toArray(Class[]::new));
//    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(entityManager.getMetamodel().getEntities().stream()
                .map(entityType -> entityType.getJavaType()) // Use getJavaType() instead
                .toArray(Class[]::new));

    } // muc dich la de show cac id khi response
}

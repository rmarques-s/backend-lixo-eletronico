package com.lixo_eletronico.auth.infrastructure;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
public class ApiPrefixConfiguration implements WebMvcRegistrations {

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new PrefixHandlerMapping("/api/v1");
    }

    private static class PrefixHandlerMapping extends RequestMappingHandlerMapping {
        private final String prefix;

        public PrefixHandlerMapping(String prefix) {
            this.prefix = prefix;
        }

        @Override
        protected void registerHandlerMethod(Object handler, java.lang.reflect.Method method, RequestMappingInfo mapping) {
            RequestMappingInfo newMapping = RequestMappingInfo
                .paths(prefix)
                .build()
                .combine(mapping);
            super.registerHandlerMethod(handler, method, newMapping);
        }
    }
}

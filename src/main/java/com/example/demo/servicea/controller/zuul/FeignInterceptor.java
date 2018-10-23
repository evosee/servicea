package com.example.demo.servicea.controller.zuul;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        template.header(ZuulFilter.RELATION_ID, RelationIdContextHolder.get());

    }
}

package com.example.demo.servicea.controller.zuul;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
//将id注入到restTemplate
public class RelationIdInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().add(ZuulFilter.RELATION_ID,RelationIdContextHolder.get());

        return execution.execute(request,body);
    }
}

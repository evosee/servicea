package com.example.demo.servicea;

import com.example.demo.servicea.controller.zuul.FeignInterceptor;
import com.example.demo.servicea.controller.zuul.RelationIdInterceptor;
import feign.Feign;
import feign.RequestInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient //发现注册服务的实例
@EnableFeignClients
@EnableCircuitBreaker//启动hystrix熔断和降级
@EnableResourceServer//开启认证
@EnableBinding(Source.class)
public class ServiceaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceaApplication.class, args);
    }

    //配合EnableFeignClients可以用于负载均衡的调用
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if(interceptors==null){
            restTemplate.setInterceptors(Collections.singletonList(new RelationIdInterceptor()));
        }else{
            interceptors.add(new RelationIdInterceptor());
        }
        return restTemplate;
    }

    @Bean
    public RequestInterceptor requestInterceptor(){
        return new FeignInterceptor();
    }

    @Bean
    public OAuth2RestTemplate oAuth2RestTemplate(UserInfoRestTemplateFactory factory){
        return factory.getUserInfoRestTemplate();
    }


}

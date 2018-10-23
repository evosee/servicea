package com.example.demo.servicea.controller;

import com.example.demo.servicea.remote.FeignProviderRemote;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RequestMapping("/a")
@RestController
@RefreshScope
@EnableCircuitBreaker
public class FirstController {
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private FeignProviderRemote feignProviderRemote;

    @Value("${test.value}")
    private String test;

    @RequestMapping("/first")
    public String first() {
        System.out.println("a first");


        return "a";
    }

    /**
     * 用discover查找信息
     */
    @RequestMapping("/discover")
    public String discoverClient() {
        List<ServiceInstance> a = discoveryClient.getInstances("servicea");
        System.out.println(a.size());
        List<ServiceInstance> b = discoveryClient.getInstances("serviceb");
        System.out.println(b.size());
        ServiceInstance bInstance = b.get(0);
        String url = bInstance.getUri().toString();
        url += "/b/first";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        System.out.println(responseEntity.getBody());
        return "discover";
    }

    /**
     * 用FeignClients配合RestTemplate调用
     */
    @RequestMapping("/feigh")
    public String feigh() {

        String url = "http://serviceb/b/first";
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        System.out.println(responseEntity.getBody());
        return "feigh";
    }

    @RequestMapping("/feignProvider")
    @HystrixCommand(commandProperties = {@HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "2000")}
            , fallbackMethod = "fallback",
            threadPoolKey = "hThreadPoll",
            threadPoolProperties = {@HystrixProperty(name = "coreSize", value = "5"), @HystrixProperty(name = "maxQueueSize", value = "10")})
    public String feignProvider() {
        System.out.println("test____________________:" + test);

        String r = feignProviderRemote.feignProvider();

        return r;
    }

    public String fallback() {
        System.out.println("__________fail back");
        return "fail";
    }
}

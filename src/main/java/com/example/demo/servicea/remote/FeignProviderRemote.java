package com.example.demo.servicea.remote;

import com.example.demo.servicebprovider.provider.FeignProvider;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("serviceb")
public interface FeignProviderRemote extends FeignProvider {
}

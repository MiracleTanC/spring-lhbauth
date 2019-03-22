package com.lhb.lhbauth.jwt.demo.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zqh
 */
@Service
@FeignClient(url = "http://127.0.0.1/oauth", name = "engine")
public interface ResponService {
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public Object getAccessToken(@RequestParam("grant_type") String grantType,
                                 @RequestParam("client_id") String clientId,
                                 @RequestParam("client_secret") String clientSecret,
                                 @RequestParam("redirect_uri") String redirectUri,
                                 @RequestParam("code") String code,
                                 @RequestParam("scope") String scope);

        //?grant_type=authorization_code&client_id=lvhaibao&client_secret=123456&redirect_uri=http://baidu.com&code=3wq3B7&scope=app


//    public JSONObject getEngineMesasge(@RequestParam("uid") String uid, @RequestParam("productCode") String productCode) {
//    }
}

package com.lhb.lhbauth.jwt.demo.web.controller;

import com.lhb.lhbauth.jwt.demo.service.ResponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lvhaibao
 * @description
 * @date 2018/12/25 0025 14:35
 */
@RestController
@RequestMapping("/getCode")
public class CodeRequestController {

    @Autowired
    ResponService responService;

    @GetMapping("/get")
    public Object hello(@RequestParam("code") String code) {
        return responService.getAccessToken("authorization_code", "lvhaibao", "123456", "http://localhost/getCode/get", code, "app");
    }

    //?grant_type=authorization_code&client_id=lvhaibao&client_secret=123456&redirect_uri=http://baidu.com&code=3wq3B7&scope=app


}

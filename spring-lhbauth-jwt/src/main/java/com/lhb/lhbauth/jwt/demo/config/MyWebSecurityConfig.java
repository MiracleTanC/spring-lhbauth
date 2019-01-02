package com.lhb.lhbauth.jwt.demo.config;

import com.lhb.lhbauth.jwt.demo.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.lhb.lhbauth.jwt.demo.authentication.mobile.SmsCodeFilter;
import com.lhb.lhbauth.jwt.demo.cahe.redis.VcodeManager;
import com.lhb.lhbauth.jwt.demo.constants.FromLoginConstant;
import com.lhb.lhbauth.jwt.demo.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author lvhaibao
 * @description 浏览器配置
 * @date 2018/12/25 0025 10:53
 */
@Configuration
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private VcodeManager vcodeManager;
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        SmsCodeFilter smsCodeFilter = new SmsCodeFilter(vcodeManager);
        smsCodeFilter.setSecurityProperties(securityProperties);
        smsCodeFilter.afterPropertiesSet();

        http.addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
                //表单登录,loginPage为登录请求的url,loginProcessingUrl为表单登录处理的URL
                .formLogin().loginPage(FromLoginConstant.LOGIN_PAGE).loginProcessingUrl(FromLoginConstant.LOGIN_PROCESSING_URL)
                //允许访问
                .and().authorizeRequests().antMatchers(
                FromLoginConstant.LOGIN_PROCESSING_URL,
                FromLoginConstant.LOGIN_PAGE,
                securityProperties.getOauthLogin().getOauthLogin(),
                securityProperties.getOauthLogin().getOauthGrant(),
                "/myLogout",
                "/code/sms")
//                "/oauth/**")
                .permitAll().anyRequest().authenticated()
                //禁用跨站伪造
                .and().csrf().disable()
                //短信验证码配置
                .apply(smsCodeAuthenticationSecurityConfig);

    }


}

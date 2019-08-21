package com.example.springboot;

import com.example.service.HelloServiceApi;
import com.example.serviceImpl.HelloServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author by cld
 * @date 2019/8/12  11:12
 * @description:
 */
//@RestController    //该注解是 @Controller 和 @ResponseBody 注解的合体版
@Controller
public class HelloController {

//    @Autowired
//    private SystemProperties systemProperties;

    @Resource
    private HelloServiceApi helloServiceApi;
    //HelloServiceImpl


    //Resource,Autowired 做bean的注入时使用

    @RequestMapping("hello")  //和@RequestMapping("/hello") 都可以访问
    @ResponseBody   //返回的是内容
    public String fun(){
        helloServiceApi.fun();
        return "hello Spring Boot!";
    }

    @RequestMapping("vm")
    public String vm(ModelAndView mav, HttpServletRequest request){
        return "test";
    }

}

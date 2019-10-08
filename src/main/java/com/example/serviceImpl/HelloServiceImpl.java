package com.example.serviceImpl;

import com.example.bean.UserInfoListVo;
import com.example.bean.exampleBean.UserInfo;
import com.example.mapperInterface.MyUserMapper;
import com.example.service.HelloServiceApi;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by cld
 * @date 2019/8/15  18:41
 * @description:
 */
//A component required a bean of type 'HelloServiceApi' that could not be found.
@Service
public class HelloServiceImpl implements HelloServiceApi {

    @Resource
    private MyUserMapper myUserMapper;

    @Override
    public void fun() {
        UserInfoListVo  userInfoListVo = new UserInfoListVo();
        List<UserInfo> userInfoList = myUserMapper.queryUser(userInfoListVo);
    }
}

package com.example.mapperInterface;

import com.example.bean.UserInfoListVo;
import com.example.bean.exampleBean.UserInfo;

import java.util.List;

//A component required a bean of type 'com.example.mapperInterface.MyUserMapper' that could not be found.
//@Mapper
public interface MyUserMapper {
    List<UserInfo> queryUser(UserInfoListVo userInfoListVo);
}

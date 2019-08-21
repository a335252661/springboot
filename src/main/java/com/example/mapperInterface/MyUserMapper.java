package com.example.mapperInterface;

import com.example.bean.UserInfo;
import com.example.bean.UserInfoListVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

//A component required a bean of type 'com.example.mapperInterface.MyUserMapper' that could not be found.
//@Mapper
public interface MyUserMapper {
    List<UserInfo> queryUser(UserInfoListVo userInfoListVo);
}

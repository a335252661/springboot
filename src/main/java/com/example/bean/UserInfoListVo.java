package com.example.bean;


import com.example.bean.exampleBean.UserInfo;

import java.util.List;

public class UserInfoListVo extends UserInfo {


    private String registerStart;
    private String registerEnd;

    private List<UserInfo> userInfoListVoList;

    public List<UserInfo> getUserInfoListVoList() {
        return userInfoListVoList;
    }

    public void setUserInfoListVoList(List<UserInfo> userInfoListVoList) {
        this.userInfoListVoList = userInfoListVoList;
    }

    private int page;
    private int rows;

    /**
     * since 2018-12-11 layui 分页使用
     * 和rows功能相同
     */
    private int limit;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getRegisterStart() {
        return registerStart;
    }

    public void setRegisterStart(String registerStart) {
        this.registerStart = registerStart;
    }

    public String getRegisterEnd() {
        return registerEnd;
    }

    public void setRegisterEnd(String registerEnd) {
        this.registerEnd = registerEnd;
    }
}

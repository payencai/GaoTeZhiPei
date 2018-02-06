package com.yichan.gaotezhipei.login.entity;

import java.util.List;

/**
 * Created by ckerv on 2018/2/4.
 */

public class UserEntity {

    /**
     * account : string
     * belongCity : string
     * createTime : 2018-02-04T08:07:08.836Z
     * heading : string
     * hxAccount : string
     * id : string
     * isCancel : 0
     * isMember : 0
     * isUse : 0
     * name : string
     * password : string
     * personalId : string
     * roleId : ["string"]
     * tag : string
     * telephone : string
     * token : string
     * type : 0
     */

    private String account;
    private String belongCity;
    private String createTime;
    private String heading;
    private String hxAccount;
    private String id;
    private int isCancel;
    private int isMember;
    private int isUse;
    private String name;
    private String password;
    private String personalId;
    private String tag;
    private String telephone;
    private String token;
    private int type;
    private List<String> roleId;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBelongCity() {
        return belongCity;
    }

    public void setBelongCity(String belongCity) {
        this.belongCity = belongCity;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getHxAccount() {
        return hxAccount;
    }

    public void setHxAccount(String hxAccount) {
        this.hxAccount = hxAccount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(int isCancel) {
        this.isCancel = isCancel;
    }

    public int getIsMember() {
        return isMember;
    }

    public void setIsMember(int isMember) {
        this.isMember = isMember;
    }

    public int getIsUse() {
        return isUse;
    }

    public void setIsUse(int isUse) {
        this.isUse = isUse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<String> getRoleId() {
        return roleId;
    }

    public void setRoleId(List<String> roleId) {
        this.roleId = roleId;
    }
}

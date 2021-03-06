package com.yichan.gaotezhipei.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ckerv on 2018/2/4.
 */

public class UserManager {
    private static final String TAG = UserManager.class.getSimpleName();
    // 兼容之前版本，不改动
    private static final String PREFERENCES_NAME = "gaoteapplication";

    private static UserManager userManager;
    private SharedPreferences userPreferences;
    private Context context;

    private boolean isLogin = false;

    private String token;

    private String account;
    private String password;
    private String roleType;//"1"代表服务方，"2"代表需求方，登录用

    private int type;//1为后台管理，2为需求方，3为拼货司机，4为物流司机，5为服务网点，6为需求方与拼货司机

    private String id;

    private String city;

    private String locatedCity;

    private String avatar;

    private String nickName;




    private UserManager(Context context) {
        this.context = context.getApplicationContext();
        userPreferences = this.context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public synchronized static UserManager getInstance(Context context) {
        if (userManager == null) {
            synchronized (UserManager.class) {
                if (userManager == null)
                    userManager = new UserManager(context.getApplicationContext());
            }
        }
        return userManager;
    }

    private SharedPreferences.Editor getEditor() {
        return userPreferences.edit();
    }

    public boolean isLogin() {
        this.isLogin = userPreferences.getBoolean("isLogin", false);
        return isLogin;
    }

    public void setLogin(boolean login) {
        this.isLogin = login;
        getEditor().putBoolean("isLogin", login).apply();
    }

    public String getId() {
        this.id = userPreferences.getString("userId", null);
        return id;
    }

    public void setId(String id) {
        this.id = id;
        getEditor().putString("userId", id);
    }

    public String getToken() {
        this.token = userPreferences.getString("token", null);
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        getEditor().putString("token", token).apply();
    }

    public String getAccount() {
        this.account = userPreferences.getString("account", null);
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
        getEditor().putString("account", account).apply();
    }

    public String getPassword() {
        this.password = userPreferences.getString("password", null);
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        getEditor().putString("password", password).apply();
    }

    public String getRoleType() {
        this.roleType = userPreferences.getString("roleType", null);
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
        getEditor().putString("roleType", roleType).apply();
    }

    public int getType() {
        this.type = userPreferences.getInt("type", 0);
        return type;
    }

    public void setType(int type) {
        this.type = type;
        getEditor().putInt("type", type).apply();
    }

    public String getCity() {
        this.city = userPreferences.getString("city", "昆明");
        return city;
    }

    public void setCity(String city) {
        this.city = city;
        getEditor().putString("city", city).apply();

    }

    public String getLocatedCity() {
        this.locatedCity = userPreferences.getString("locatedCity", "昆明");
        return locatedCity;
    }

    public void setLocatedCity(String city) {
        this.locatedCity = city;
        getEditor().putString("locatedCity", city).apply();

    }

    public boolean isDemand() {
        return getRoleType().equals("2");
    }

    public String getAvatar() {
        this.avatar = userPreferences.getString("avatar", null);
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
        getEditor().putString("avatar", avatar).apply();
    }

    public String getNickName() {
        this.nickName = userPreferences.getString("nickName", null);
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
        getEditor().putString("nickName", nickName).apply();
    }
}

package com.njfu.entity;

import java.util.List;

/**员工操作登录信息类
 * Created by lvxz on 2018/5/13.
 */


public class Staff {

    private String loginid;
    private String account;
    private String pwd;

    private String cwoc;    //组编号
    private String dsca;    //组名称
    private String prcd;    //组报完工权限
    private String right;   //组按钮权限

    public Staff() {
    }

    public Staff(String loginid, String account, String pwd, String cwoc, String dsca, String prcd, String right) {
        this.loginid = loginid;
        this.account = account;
        this.pwd = pwd;
        this.cwoc = cwoc;
        this.dsca = dsca;
        this.prcd = prcd;
        this.right = right;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getCwoc() {
        return cwoc;
    }

    public void setCwoc(String cwoc) {
        this.cwoc = cwoc;
    }

    public String getDsca() {
        return dsca;
    }

    public void setDsca(String dsca) {
        this.dsca = dsca;
    }

    public String getPrcd() {
        return prcd;
    }

    public void setPrcd(String prcd) {
        this.prcd = prcd;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return
                "| 用户名:" + loginid +
              "\n| 用户账号:" + account +
              "\n| 组号:" + cwoc +
              "\n| 组名:" + dsca +
              "\n| 按钮:" + prcd +
              "\n| 操作:" + right +
              "\n------------------------------------------------------------------------------\n"
                ;
    }
}

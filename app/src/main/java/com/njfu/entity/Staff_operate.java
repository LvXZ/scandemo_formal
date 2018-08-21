package com.njfu.entity;

import java.util.List;

/** 小组操作权限类
 * Created by lvxz on 2018/6/5.
 */


public class Staff_operate{
    private String name;                //当前小组拥有操作的名称
    private List<String> prcd;          //保存当前操作员报完工所有数据权限,将Staff的String-prcd,转化为List<String>-prcd

    public Staff_operate() {
    }

    public Staff_operate(List<String> prcd) {
        this.prcd = prcd;
    }

    public Staff_operate(String name, List<String> prcd) {
        this.name = name;
        this.prcd = prcd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPrcd() {
        return prcd;
    }

    public void setPrcd(List<String> prcd) {
        this.prcd = prcd;
    }

    @Override
    public String toString() {
        return "Staff_operate{" +
                "操作name='" + name + '\'' +
                ", 操作prcd=" + prcd +
                '}';
    }
}

package com.njfu.entity;

/**工序机器记录对象
 * Created by lvxz on 2018/6/2.
 */

public class Machine001 {

    private String mach_id;
    private String pdnos;
    private String account;
    private String add_date;

    public Machine001() {
    }

    public Machine001(String mach_id, String pdnos, String account) {
        this.mach_id = mach_id;
        this.pdnos = pdnos;
        this.account = account;
    }

    public Machine001(String mach_id, String pdnos, String account, String add_date) {
        this.mach_id = mach_id;
        this.pdnos = pdnos;
        this.account = account;
        this.add_date = add_date;
    }

    public String getMach_id() {
        return mach_id;
    }

    public void setMach_id(String mach_id) {
        this.mach_id = mach_id;
    }

    public String getPdnos() {
        return pdnos;
    }

    public void setPdnos(String pdnos) {
        this.pdnos = pdnos;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAdd_date() {
        return add_date;
    }

    public void setAdd_date(String add_date) {
        this.add_date = add_date;
    }

    @Override
    public String toString() {
        return "Machine001{" +
                "mach_id='" + mach_id + '\'' +
                ", pdnos='" + pdnos + '\'' +
                ", account='" + account + '\'' +
                ", add_date='" + add_date + '\'' +
                '}';
    }

}

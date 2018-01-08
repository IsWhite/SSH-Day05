package com.lanou.domain;

import java.io.Serializable;

/**
 * C-2
 * 班级
 */
public class Clazz  implements Serializable{
    private  int cid;//班级id
    private String cname;//班级名
    private String cinfor;//班级信息

    public Clazz() {
    }

    public Clazz(String cname, String cinfor) {
        this.cname = cname;
        this.cinfor = cinfor;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCinfor() {
        return cinfor;
    }

    public void setCinfor(String cinfor) {
        this.cinfor = cinfor;
    }

    @Override
    public String toString() {
        return "Clazz{" +
                "cid=" + cid +
                ", cname='" + cname + '\'' +
                ", cinfor='" + cinfor + '\'' +
                '}';
    }
}

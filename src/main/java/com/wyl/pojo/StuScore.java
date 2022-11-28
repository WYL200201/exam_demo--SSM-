package com.wyl.pojo;

import javax.xml.crypto.Data;
import java.util.Date;

public class StuScore {
    private Integer seid;
    private Integer userid;
    private Integer classid;
    private Integer eid;
    private Integer zscore;
    private Integer score;
    private String titime;
    private String pname;
    private Users users;

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Integer getSeid() {
        return seid;
    }

    public void setSeid(Integer seid) {
        this.seid = seid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getClassid() {
        return classid;
    }

    public void setClassid(Integer classid) {
        this.classid = classid;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Integer getZscore() {
        return zscore;
    }

    public void setZscore(Integer zscore) {
        this.zscore = zscore;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getTitime() {
        return titime;
    }

    public void setTitime(String titime) {
        this.titime = titime;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}

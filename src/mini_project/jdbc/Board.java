package mini_project.jdbc;

import java.sql.Date;

public class Board {
    private int bno;
    private String btitle;
    private String bconect;
    private String bwriter;
    private Date bdate;

    public int getBno() {
        return bno;
    }

    public void setBno(int bno) {
        this.bno = bno;
    }

    public String getBtitle() {
        return btitle;
    }

    public void setBtitle(String btitle) {
        this.btitle = btitle;
    }

    public String getBconect() {
        return bconect;
    }

    public void setBconect(String bconect) {
        this.bconect = bconect;
    }

    public String getBwriter() {
        return bwriter;
    }

    public void setBwriter(String bwriter) {
        this.bwriter = bwriter;
    }

    public Date getBdate() {
        return bdate;
    }

    public void setBdate(Date bdate) {
        this.bdate = bdate;
    }
}

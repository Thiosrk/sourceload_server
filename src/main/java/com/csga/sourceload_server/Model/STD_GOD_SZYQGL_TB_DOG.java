package com.csga.sourceload_server.Model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "STD_GOD_SZYQGL_TB_DOG")
public class STD_GOD_SZYQGL_TB_DOG {

    @Id
    @GeneratedValue
    private Integer u_id;//临时主键
    private String ETL_JOB; //ETL任务名
    private String DEL_FLAG; //删除标志
    private Date END_DT; //结束日期
    private Date START_DT; //开始日期
    private Date TBSJ; //填报时间
    private Date NWXWWTBSJC; //内网向外网同步时间戳
    private String QZXXLYDM; //犬只信息来源代码
    private String QZCDSCDM; //犬只彻底删除代码
    private String QZCZLXDM; //犬只操作类型代码
    private String SHBS; //审核标识
    private String MJJH; //民警警号
    private Date TJSJ; //提交时间
    private String QZZZZDM; //犬只证制证代码
    private Date SCLZRQ; //首次领证日期
    private String BZDDBH; //办证地点编号
    private String QZZXYYDM; //犬只注销原因代码
    private Date ZXRQ; //注销日期
    private Date ZHYCYXRQ; //最后一次延续日期
    private Date ZZRQ; //制证日期
    private Date DJRQ; //登记日期
    private String QZYCBZ; //犬只异常标志
    private String QZH; //犬证号
    private String DZXPH; //电子芯片号
    private String QYYZID; //犬拥有者ID
    private String QZLYFS; //犬只领养方式
    private String AQCSDM; //安全措施代码
    private String QYTDM; //犬用途代码
    private String QLYDM; //犬来源代码
    private String QDXDM; //犬大小代码
    private String QZMS; //犬只毛色
    private String QZCSNY; //犬只出生年月
    private String QZDM; //犬种代码
    private String QXBDM; //犬性别代码
    private String QZMZ; //犬只名字
    private String SQBH; //社区编号
    private String PCSBH; //派出所编号
    private String YXTBH; //原系统编号
    private String QBH; //犬编号

    public Integer getU_id(){
        return u_id;
    }
    public void setU_id(Integer u_id){
        this.u_id = u_id;
    }
    public String getETL_JOB(){
        return ETL_JOB;
    }
    public void setETL_JOB(String ETL_JOB){
        this.ETL_JOB=ETL_JOB;
    }
    public String getDEL_FLAG(){
        return DEL_FLAG;
    }
    public void setDEL_FLAG(String DEL_FLAG){
        this.DEL_FLAG=DEL_FLAG;
    }
    public Date getEND_DT(){
        return END_DT;
    }
    public void setEND_DT(Date END_DT){
        this.END_DT=END_DT;
    }
    public Date getSTART_DT(){
        return START_DT;
    }
    public void setSTART_DT(Date START_DT){
        this.START_DT=START_DT;
    }
    public Date getTBSJ(){
        return TBSJ;
    }
    public void setTBSJ(Date TBSJ){
        this.TBSJ=TBSJ;
    }
    public Date getNWXWWTBSJC(){
        return NWXWWTBSJC;
    }
    public void setNWXWWTBSJC(Date NWXWWTBSJC){
        this.NWXWWTBSJC=NWXWWTBSJC;
    }
    public String getQZXXLYDM(){
        return QZXXLYDM;
    }
    public void setQZXXLYDM(String QZXXLYDM){
        this.QZXXLYDM=QZXXLYDM;
    }
    public String getQZCDSCDM(){
        return QZCDSCDM;
    }
    public void setQZCDSCDM(String QZCDSCDM){
        this.QZCDSCDM=QZCDSCDM;
    }
    public String getQZCZLXDM(){
        return QZCZLXDM;
    }
    public void setQZCZLXDM(String QZCZLXDM){
        this.QZCZLXDM=QZCZLXDM;
    }
    public String getSHBS(){
        return SHBS;
    }
    public void setSHBS(String SHBS){
        this.SHBS=SHBS;
    }
    public String getMJJH(){
        return MJJH;
    }
    public void setMJJH(String MJJH){
        this.MJJH=MJJH;
    }
    public Date getTJSJ(){
        return TJSJ;
    }
    public void setTJSJ(Date TJSJ){
        this.TJSJ=TJSJ;
    }
    public String getQZZZZDM(){
        return QZZZZDM;
    }
    public void setQZZZZDM(String QZZZZDM){
        this.QZZZZDM=QZZZZDM;
    }
    public Date getSCLZRQ(){
        return SCLZRQ;
    }
    public void setSCLZRQ(Date SCLZRQ){
        this.SCLZRQ=SCLZRQ;
    }
    public String getBZDDBH(){
        return BZDDBH;
    }
    public void setBZDDBH(String BZDDBH){
        this.BZDDBH=BZDDBH;
    }
    public String getQZZXYYDM(){
        return QZZXYYDM;
    }
    public void setQZZXYYDM(String QZZXYYDM){
        this.QZZXYYDM=QZZXYYDM;
    }
    public Date getZXRQ(){
        return ZXRQ;
    }
    public void setZXRQ(Date ZXRQ){
        this.ZXRQ=ZXRQ;
    }
    public Date getZHYCYXRQ(){
        return ZHYCYXRQ;
    }
    public void setZHYCYXRQ(Date ZHYCYXRQ){
        this.ZHYCYXRQ=ZHYCYXRQ;
    }
    public Date getZZRQ(){
        return ZZRQ;
    }
    public void setZZRQ(Date ZZRQ){
        this.ZZRQ=ZZRQ;
    }
    public Date getDJRQ(){
        return DJRQ;
    }
    public void setDJRQ(Date DJRQ){
        this.DJRQ=DJRQ;
    }
    public String getQZYCBZ(){
        return QZYCBZ;
    }
    public void setQZYCBZ(String QZYCBZ){
        this.QZYCBZ=QZYCBZ;
    }
    public String getQZH(){
        return QZH;
    }
    public void setQZH(String QZH){
        this.QZH=QZH;
    }
    public String getDZXPH(){
        return DZXPH;
    }
    public void setDZXPH(String DZXPH){
        this.DZXPH=DZXPH;
    }
    public String getQYYZID(){
        return QYYZID;
    }
    public void setQYYZID(String QYYZID){
        this.QYYZID=QYYZID;
    }
    public String getQZLYFS(){
        return QZLYFS;
    }
    public void setQZLYFS(String QZLYFS){
        this.QZLYFS=QZLYFS;
    }
    public String getAQCSDM(){
        return AQCSDM;
    }
    public void setAQCSDM(String AQCSDM){
        this.AQCSDM=AQCSDM;
    }
    public String getQYTDM(){
        return QYTDM;
    }
    public void setQYTDM(String QYTDM){
        this.QYTDM=QYTDM;
    }
    public String getQLYDM(){
        return QLYDM;
    }
    public void setQLYDM(String QLYDM){
        this.QLYDM=QLYDM;
    }
    public String getQDXDM(){
        return QDXDM;
    }
    public void setQDXDM(String QDXDM){
        this.QDXDM=QDXDM;
    }
    public String getQZMS(){
        return QZMS;
    }
    public void setQZMS(String QZMS){
        this.QZMS=QZMS;
    }
    public String getQZCSNY(){
        return QZCSNY;
    }
    public void setQZCSNY(String QZCSNY){
        this.QZCSNY=QZCSNY;
    }
    public String getQZDM(){
        return QZDM;
    }
    public void setQZDM(String QZDM){
        this.QZDM=QZDM;
    }
    public String getQXBDM(){
        return QXBDM;
    }
    public void setQXBDM(String QXBDM){
        this.QXBDM=QXBDM;
    }
    public String getQZMZ(){
        return QZMZ;
    }
    public void setQZMZ(String QZMZ){
        this.QZMZ=QZMZ;
    }
    public String getSQBH(){
        return SQBH;
    }
    public void setSQBH(String SQBH){
        this.SQBH=SQBH;
    }
    public String getPCSBH(){
        return PCSBH;
    }
    public void setPCSBH(String PCSBH){
        this.PCSBH=PCSBH;
    }
    public String getYXTBH(){
        return YXTBH;
    }
    public void setYXTBH(String YXTBH){
        this.YXTBH=YXTBH;
    }
    public String getQBH(){
        return QBH;
    }
    public void setQBH(String QBH){
        this.QBH=QBH;
    }
}


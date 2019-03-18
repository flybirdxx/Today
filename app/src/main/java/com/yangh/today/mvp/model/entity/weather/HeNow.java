package com.yangh.today.mvp.model.entity.weather;

/**
 * Created by yangH on 2019/3/17.
 */
public class HeNow {

    /**
     * cond : {"code":"104","txt":"阴"}
     * fl : 17
     * hum : 49
     * pcpn : 0.0
     * pres : 1022
     * tmp : 17
     * vis : 16
     * wind : {"deg":"175","dir":"南风","sc":"1","spd":"2"}
     */

    private CondBean cond;//实况天气状况代码
    private String fl;//	体感温度，默认单位：摄氏度
    private String hum;//相对湿度
    private String pcpn;//	降水量
    private String pres;//	大气压强
    private String tmp;//	温度，默认单位：摄氏度
    private String vis;//	能见度，默认单位：公里
    private WindBean wind;//风力

    public CondBean getCond() {
        return cond;
    }

    public void setCond(CondBean cond) {
        this.cond = cond;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getPcpn() {
        return pcpn;
    }

    public void setPcpn(String pcpn) {
        this.pcpn = pcpn;
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getVis() {
        return vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }

    public WindBean getWind() {
        return wind;
    }

    public void setWind(WindBean wind) {
        this.wind = wind;
    }

    public static class CondBean {
        /**
         * code : 104
         * txt : 阴
         */

        private String code;
        private String txt;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }
    }

    public static class WindBean {
        /**
         * deg : 175
         * dir : 南风
         * sc : 1
         * spd : 2
         */

        private String deg;//风向360角度
        private String dir;//风向
        private String sc;//风力
        private String spd;//风速，公里/小时

        public String getDeg() {
            return deg;
        }

        public void setDeg(String deg) {
            this.deg = deg;
        }

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
        }

        public String getSc() {
            return sc;
        }

        public void setSc(String sc) {
            this.sc = sc;
        }

        public String getSpd() {
            return spd;
        }

        public void setSpd(String spd) {
            this.spd = spd;
        }
    }
}

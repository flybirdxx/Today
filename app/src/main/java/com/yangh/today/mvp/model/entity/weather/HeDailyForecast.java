package com.yangh.today.mvp.model.entity.weather;

/**
 * Created by yangH on 2019/3/17.
 */
public class HeDailyForecast {

    /**
     * astro : {"mr":"14:17","ms":"03:33","sr":"06:29","ss":"18:32"}
     * cond : {"code_d":"104","code_n":"101","txt_d":"阴","txt_n":"多云"}
     * date : 2019-03-17
     * hum : 76
     * pcpn : 1.0
     * pop : 55
     * pres : 1015
     * tmp : {"max":"17","min":"9"}
     * uv : 0
     * vis : 25
     * wind : {"deg":"151","dir":"东南风","sc":"1-2","spd":"8"}
     */

    private AstroBean astro;//天文数值
    private CondBean cond;//天气状况
    private String date;//当地日期
    private String hum;//湿度
    private String pcpn;//降雨量
    private String pop;//降水概率
    private String pres;//气压
    private TmpBean tmp;//温度
    private String uv;//紫外线强度
    private String vis;//能见度
    private WindBean wind;//风力状况

    public AstroBean getAstro() {
        return astro;
    }

    public void setAstro(AstroBean astro) {
        this.astro = astro;
    }

    public CondBean getCond() {
        return cond;
    }

    public void setCond(CondBean cond) {
        this.cond = cond;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public TmpBean getTmp() {
        return tmp;
    }

    public void setTmp(TmpBean tmp) {
        this.tmp = tmp;
    }

    public String getUv() {
        return uv;
    }

    public void setUv(String uv) {
        this.uv = uv;
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

    public static class AstroBean {
        /**
         * mr : 14:17
         * ms : 03:33
         * sr : 06:29
         * ss : 18:32
         */

        private String mr;//日升时间
        private String ms;//日落时间
        private String sr;//日出时间
        private String ss;//日落时间

        public String getMr() {
            return mr;
        }

        public void setMr(String mr) {
            this.mr = mr;
        }

        public String getMs() {
            return ms;
        }

        public void setMs(String ms) {
            this.ms = ms;
        }

        public String getSr() {
            return sr;
        }

        public void setSr(String sr) {
            this.sr = sr;
        }

        public String getSs() {
            return ss;
        }

        public void setSs(String ss) {
            this.ss = ss;
        }
    }

    public static class CondBean {
        /**
         * code_d : 104
         * code_n : 101
         * txt_d : 阴
         * txt_n : 多云
         */

        private String code_d;//白天天气状况代码
        private String code_n;//晚间天气状况代码
        private String txt_d;//白天天气状况描述
        private String txt_n;//晚间天气状况描述

        public String getCode_d() {
            return code_d;
        }

        public void setCode_d(String code_d) {
            this.code_d = code_d;
        }

        public String getCode_n() {
            return code_n;
        }

        public void setCode_n(String code_n) {
            this.code_n = code_n;
        }

        public String getTxt_d() {
            return txt_d;
        }

        public void setTxt_d(String txt_d) {
            this.txt_d = txt_d;
        }

        public String getTxt_n() {
            return txt_n;
        }

        public void setTxt_n(String txt_n) {
            this.txt_n = txt_n;
        }
    }

    public static class TmpBean {
        /**
         * max : 17
         * min : 9
         */

        private String max;//最高温度
        private String min;//	最低温度

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }
    }

    public static class WindBean {
        /**
         * deg : 151
         * dir : 东南风
         * sc : 1-2
         * spd : 8
         */

        private String deg;//风向360角度
        private String dir;//风向
        private String sc;//风力
        private String spd;//	风速，公里/小时

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

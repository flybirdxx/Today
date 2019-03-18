package com.yangh.today.mvp.model.entity.weather;

/**
 * 空气质量
 *
 * @author yangH
 * @date 2019/3/17
 */
public class HeAqi {

    /**
     * city : {"aqi":"73","qlty":"良","pm25":"51","pm10":"95","no2":"66","so2":"11","co":"1.0","o3":"23"}
     */
//城市数据
    private CityBean city;

    public CityBean getCity() {
        return city;
    }

    public void setCity(CityBean city) {
        this.city = city;
    }

    public static class CityBean {
        /**
         * aqi : 73
         * qlty : 良
         * pm25 : 51
         * pm10 : 95
         * no2 : 66
         * so2 : 11
         * co : 1.0
         * o3 : 23
         */

        private String aqi;//空气质量指数
        private String qlty;//空气质量类别
        private String pm25;
        private String pm10;
        private String no2;//二氧化氮平均值
        private String so2;//二氧化硫的平均值
        private String co;//一氧化碳平均值
        private String o3;//臭氧平均值

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public String getQlty() {
            return qlty;
        }

        public void setQlty(String qlty) {
            this.qlty = qlty;
        }

        public String getPm25() {
            return pm25;
        }

        public void setPm25(String pm25) {
            this.pm25 = pm25;
        }

        public String getPm10() {
            return pm10;
        }

        public void setPm10(String pm10) {
            this.pm10 = pm10;
        }

        public String getNo2() {
            return no2;
        }

        public void setNo2(String no2) {
            this.no2 = no2;
        }

        public String getSo2() {
            return so2;
        }

        public void setSo2(String so2) {
            this.so2 = so2;
        }

        public String getCo() {
            return co;
        }

        public void setCo(String co) {
            this.co = co;
        }

        public String getO3() {
            return o3;
        }

        public void setO3(String o3) {
            this.o3 = o3;
        }
    }
}

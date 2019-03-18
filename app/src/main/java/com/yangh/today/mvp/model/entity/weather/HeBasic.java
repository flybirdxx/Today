package com.yangh.today.mvp.model.entity.weather;

/**
 * Created by yangH on 2019/3/17.
 */
public class HeBasic {

    /**
     * city : 武汉
     * cnty : 中国
     * id : CN101200101
     * lat : 30.5843544
     * lon : 114.29856873
     * update : {"loc":"2019-03-17 10:55","utc":"2019-03-17 02:55"}
     */

    private String city;//城市名称
    private String cnty;//国家名称
    private String id;//城市ID
    private String lat;//纬度
    private String lon;//经度
    private UpdateBean update;//数据更新时间

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCnty() {
        return cnty;
    }

    public void setCnty(String cnty) {
        this.cnty = cnty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public UpdateBean getUpdate() {
        return update;
    }

    public void setUpdate(UpdateBean update) {
        this.update = update;
    }

    public static class UpdateBean {
        /**
         * loc : 2019-03-17 10:55
         * utc : 2019-03-17 02:55
         */

        private String loc;//数据更新的当地时间
        private String utc;//数据更新的UTC时间

        public String getLoc() {
            return loc;
        }

        public void setLoc(String loc) {
            this.loc = loc;
        }

        public String getUtc() {
            return utc;
        }

        public void setUtc(String utc) {
            this.utc = utc;
        }
    }
}

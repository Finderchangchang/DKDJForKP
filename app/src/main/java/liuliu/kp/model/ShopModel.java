package liuliu.kp.model;

import java.util.List;

/**
 * Created by Finder丶畅畅 on 2018/1/19 23:07
 * QQ群481606175
 */

public class ShopModel {

    /**
     * state : 1
     * msg :
     * data : [{"id":"1","classname":"苹果","classid":"4","name":"测试第一个商家","phone":"110","xiangmu":"经营项目","address":"这里是地址","lat":"0","lng":"0","zhizhao":"执照图片的网址","card1":"身份证正面地址","card2":"身份证反面地址","ewmMoney":"收款二维码地址","imga":"展示图地址","imgb":"展示图地址","imgc":"展示图地址","imgd":"展示图地址","imge":"展示图地址","cityId":"18"}]
     */

    private String state;
    private String msg;
    private List<DataBean> data;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * classname : 苹果
         * classid : 4
         * name : 测试第一个商家
         * phone : 110
         * xiangmu : 经营项目
         * address : 这里是地址
         * lat : 0
         * lng : 0
         * zhizhao : 执照图片的网址
         * card1 : 身份证正面地址
         * card2 : 身份证反面地址
         * ewmMoney : 收款二维码地址
         * imga : 展示图地址
         * imgb : 展示图地址
         * imgc : 展示图地址
         * imgd : 展示图地址
         * imge : 展示图地址
         * cityId : 18
         */

        private String id;
        private String classname;
        private String classid;
        private String name;
        private String phone;
        private String xiangmu;
        private String address;
        private String lat;
        private String lng;
        private String zhizhao;
        private String card1;
        private String card2;
        private String ewmMoney;
        private String imga;
        private String imgb;
        private String imgc;
        private String imgd;
        private String imge;
        private String cityId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getClassname() {
            return classname;
        }

        public void setClassname(String classname) {
            this.classname = classname;
        }

        public String getClassid() {
            return classid;
        }

        public void setClassid(String classid) {
            this.classid = classid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getXiangmu() {
            return xiangmu;
        }

        public void setXiangmu(String xiangmu) {
            this.xiangmu = xiangmu;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getZhizhao() {
            return zhizhao;
        }

        public void setZhizhao(String zhizhao) {
            this.zhizhao = zhizhao;
        }

        public String getCard1() {
            return card1;
        }

        public void setCard1(String card1) {
            this.card1 = card1;
        }

        public String getCard2() {
            return card2;
        }

        public void setCard2(String card2) {
            this.card2 = card2;
        }

        public String getEwmMoney() {
            return ewmMoney;
        }

        public void setEwmMoney(String ewmMoney) {
            this.ewmMoney = ewmMoney;
        }

        public String getImga() {
            return imga;
        }

        public void setImga(String imga) {
            this.imga = imga;
        }

        public String getImgb() {
            return imgb;
        }

        public void setImgb(String imgb) {
            this.imgb = imgb;
        }

        public String getImgc() {
            return imgc;
        }

        public void setImgc(String imgc) {
            this.imgc = imgc;
        }

        public String getImgd() {
            return imgd;
        }

        public void setImgd(String imgd) {
            this.imgd = imgd;
        }

        public String getImge() {
            return imge;
        }

        public void setImge(String imge) {
            this.imge = imge;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }
    }
}

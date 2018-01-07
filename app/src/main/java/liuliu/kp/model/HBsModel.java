package liuliu.kp.model;

import java.util.List;

/**
 * Created by Finder丶畅畅 on 2018/1/8 01:18
 * QQ群481606175
 */

public class HBsModel {

    /**
     * state : 1
     * msg : success
     * data : [{"编号":"1","用户ID":"11966","领取时间":"2018/1/7 1:35:05","过期时间":"2018/1/10 1:35:05","领取金额":"1.68","是否使用":"否","消费单号":"","是否可用":"可用"},{"编号":"2","用户ID":"11966","领取时间":"2018/1/7 1:35:19","过期时间":"2018/1/10 1:35:19","领取金额":"1.79","是否使用":"否","消费单号":"","是否可用":"可用"}]
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
         * 编号 : 1
         * 用户ID : 11966
         * 领取时间 : 2018/1/7 1:35:05
         * 过期时间 : 2018/1/10 1:35:05
         * 领取金额 : 1.68
         * 是否使用 : 否
         * 消费单号 :
         * 是否可用 : 可用
         */

        private String 编号;
        private String 用户ID;
        private String 领取时间;
        private String 过期时间;
        private String 领取金额;
        private String 是否使用;
        private String 消费单号;
        private String 是否可用;

        public String get编号() {
            return 编号;
        }

        public void set编号(String 编号) {
            this.编号 = 编号;
        }

        public String get用户ID() {
            return 用户ID;
        }

        public void set用户ID(String 用户ID) {
            this.用户ID = 用户ID;
        }

        public String get领取时间() {
            return 领取时间;
        }

        public void set领取时间(String 领取时间) {
            this.领取时间 = 领取时间;
        }

        public String get过期时间() {
            return 过期时间;
        }

        public void set过期时间(String 过期时间) {
            this.过期时间 = 过期时间;
        }

        public String get领取金额() {
            return 领取金额;
        }

        public void set领取金额(String 领取金额) {
            this.领取金额 = 领取金额;
        }

        public String get是否使用() {
            return 是否使用;
        }

        public void set是否使用(String 是否使用) {
            this.是否使用 = 是否使用;
        }

        public String get消费单号() {
            return 消费单号;
        }

        public void set消费单号(String 消费单号) {
            this.消费单号 = 消费单号;
        }

        public String get是否可用() {
            return 是否可用;
        }

        public void set是否可用(String 是否可用) {
            this.是否可用 = 是否可用;
        }
    }
}

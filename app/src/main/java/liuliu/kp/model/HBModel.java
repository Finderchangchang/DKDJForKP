package liuliu.kp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Finder丶畅畅 on 2018/1/7 18:24
 * QQ群481606175
 */

public class HBModel implements Serializable {

    /**
     * state : 1
     * msg : success
     * data : [{"有效天数":"3天","过期时间":"2018/1/10 1:35:19","红包金额":"1.79"}]
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

    public static class DataBean implements Serializable {
        /**
         * 有效天数 : 3天
         * 过期时间 : 2018/1/10 1:35:19
         * 红包金额 : 1.79
         */

        private String 有效天数;
        private String 过期时间;
        private String 红包金额;

        public String get有效天数() {
            return 有效天数;
        }

        public void set有效天数(String 有效天数) {
            this.有效天数 = 有效天数;
        }

        public String get过期时间() {
            return 过期时间;
        }

        public void set过期时间(String 过期时间) {
            this.过期时间 = 过期时间;
        }

        public String get红包金额() {
            return 红包金额;
        }

        public void set红包金额(String 红包金额) {
            this.红包金额 = 红包金额;
        }
    }
}

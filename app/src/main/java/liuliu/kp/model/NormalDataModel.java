package liuliu.kp.model;

import java.util.List;

/**
 * Created by Finder丶畅畅 on 2018/6/14 23:03
 * QQ群481606175
 */

public class NormalDataModel {
    private String state;
    private String msg;
    private List<Date1> zl;
    private List<Date1> tj;
    private List<Date3> che;

    public class Date1 {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class Date3 {
        private String name;
        private String id;
        private String money;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }

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

    public List<Date1> getZl() {
        return zl;
    }

    public void setZl(List<Date1> zl) {
        this.zl = zl;
    }

    public List<Date1> getTj() {
        return tj;
    }

    public void setTj(List<Date1> tj) {
        this.tj = tj;
    }

    public List<Date3> getChe() {
        return che;
    }

    public void setChe(List<Date3> che) {
        this.che = che;
    }
}

package liuliu.kp.model;

import java.util.List;

/**
 * Created by Finder丶畅畅 on 2018/1/19 23:03
 * QQ群481606175
 */

public class GroupModel{


    /**
     * state : 1
     * msg :
     * data : [{"id":"1","classname":"生鲜果蔬","dataList":[{"id":"3","classname":"大虾"},{"id":"4","classname":"苹果"},{"id":"5","classname":"蔬菜"},{"id":"6","classname":"龙虾"}]}]
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
         * classname : 生鲜果蔬
         * dataList : [{"id":"3","classname":"大虾"},{"id":"4","classname":"苹果"},{"id":"5","classname":"蔬菜"},{"id":"6","classname":"龙虾"}]
         */

        private String id;
        private String classname;
        private List<DataListBean> dataList;

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

        public List<DataListBean> getDataList() {
            return dataList;
        }

        public void setDataList(List<DataListBean> dataList) {
            this.dataList = dataList;
        }

        public static class DataListBean {
            /**
             * id : 3
             * classname : 大虾
             */

            private String id;
            private String classname;

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
        }
    }
}

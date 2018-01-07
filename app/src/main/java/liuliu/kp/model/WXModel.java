package liuliu.kp.model;

import java.util.List;

/**
 * Created by Finder丶畅畅 on 2018/1/7 20:41
 * QQ群481606175
 */

public class WXModel {

    /**
     * state : 1
     * msg : success
     * data : [{"id":"2","wxusername":"cyyijiaqin@163.com","wxpwd":"YJQ@2633019","AppId":"wx91664201904be69e","AppSecret":"","partnerid":"1488122312","apikey":"zw8zml3jqu0MkDyyAhDLMdJTGfBJdCaM","domain":"http://kuaipao.myejq.com","type":"用户版"}]
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
         * id : 2
         * wxusername : cyyijiaqin@163.com
         * wxpwd : YJQ@2633019
         * AppId : wx91664201904be69e
         * AppSecret :
         * partnerid : 1488122312
         * apikey : zw8zml3jqu0MkDyyAhDLMdJTGfBJdCaM
         * domain : http://kuaipao.myejq.com
         * type : 用户版
         */

        private String id;
        private String wxusername;
        private String wxpwd;
        private String AppId;
        private String AppSecret;
        private String partnerid;
        private String apikey;
        private String domain;
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getWxusername() {
            return wxusername;
        }

        public void setWxusername(String wxusername) {
            this.wxusername = wxusername;
        }

        public String getWxpwd() {
            return wxpwd;
        }

        public void setWxpwd(String wxpwd) {
            this.wxpwd = wxpwd;
        }

        public String getAppId() {
            return AppId;
        }

        public void setAppId(String AppId) {
            this.AppId = AppId;
        }

        public String getAppSecret() {
            return AppSecret;
        }

        public void setAppSecret(String AppSecret) {
            this.AppSecret = AppSecret;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getApikey() {
            return apikey;
        }

        public void setApikey(String apikey) {
            this.apikey = apikey;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}

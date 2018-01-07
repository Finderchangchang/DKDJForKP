package liuliu.kp.model;

import java.util.List;

/**
 * Created by Finder丶畅畅 on 2018/1/7 20:39
 * QQ群481606175
 */

public class ALiModel {

    /**
     * state : 1
     * msg : success
     * data : [{"id":"2","帐号":"15642635333@126.com","APPID":"2017070707668680","合作者身份":"2088521245432893","商户私钥":"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMbfz010qY0i2UMTLmAKY/hihdtCHns619GJi7JPHLk4RNaV7W3rn5pyIZbY1KgkEKrZe+QTBGtCTm4yvEx6FhbU2R0RTQwmtxq6FfYzA6Z445HxZK7aTsFKNrdMXaisch1bYcYXa7wqaLZAdkoSEsB1PktBtvnxasiPcKlz6rNNAgMBAAECgYEAsq62ME6IZEGSWTyrpyjOx4Wl5OkUP/4PmLlvZWbFVPlkiDSu3dsdTIQM/96Tuvqkawz6zmHzz3cExugY3NG4c3d814ndGfkxet4pHj8MkI7cAdWxx3wAH839JcgPd2zptF0cn4xSf1/sbd2I1WLX4en/kjYHijyrMjdHQvgmHHUCQQDmqaDoTIu/yD//sHxRRA6M7STZNgpOVZaw9cXAwQT/Xm4j/5xT8cRL5wvOkBt+J17hMI0u4BUTkAtvlZuxmED3AkEA3LhGCKTxFP8ASwCLcOp1S6n5kAD836hfEcPggrK9j2C6JaL3NboYwuVOHFOZNEeZaSmWhVSQkM8Eo/W8/wXg2wJBAIduCzK5VsgovCLVLXjNe7mUtzDtgMmTiSGnfA97hQKnIAX024WSCw4BvzXt+KZuWU/goNRn7xgIWB6Q+dQhL+sCQDa8C8IBBTfrtKrky6+ItOW6lQS8+fiMzGafaIGAyblQh1jl/N/58kICk0g7AvcP+l81zPnrap4+eCwguGmVj5kCQGMMQuF5/EIhwlQk42UK/UJXPdcPVUj2Eczn4yc5ic9ZHZ0JeEi5PDvi0TCSXMKcwYeVV2h+nY07uXdEwhmVLL0=","支付宝公钥":"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDG389NdKmNItlDEy5gCmP4YoXbQh57OtfRiYuyTxy5OETWle1t65+aciGW2NSoJBCq2XvkEwRrQk5uMrxMehYW1NkdEU0MJrcauhX2MwOmeOOR8WSu2k7BSja3TF2orHIdW2HGF2u8Kmi2QHZKEhLAdT5LQbb58WrIj3Cpc+qzTQIDAQAB","授权域名":"http://kuaipao.myejq.com","type":"用户版"}]
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
         * 帐号 : 15642635333@126.com
         * APPID : 2017070707668680
         * 合作者身份 : 2088521245432893
         * 商户私钥 : MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMbfz010qY0i2UMTLmAKY/hihdtCHns619GJi7JPHLk4RNaV7W3rn5pyIZbY1KgkEKrZe+QTBGtCTm4yvEx6FhbU2R0RTQwmtxq6FfYzA6Z445HxZK7aTsFKNrdMXaisch1bYcYXa7wqaLZAdkoSEsB1PktBtvnxasiPcKlz6rNNAgMBAAECgYEAsq62ME6IZEGSWTyrpyjOx4Wl5OkUP/4PmLlvZWbFVPlkiDSu3dsdTIQM/96Tuvqkawz6zmHzz3cExugY3NG4c3d814ndGfkxet4pHj8MkI7cAdWxx3wAH839JcgPd2zptF0cn4xSf1/sbd2I1WLX4en/kjYHijyrMjdHQvgmHHUCQQDmqaDoTIu/yD//sHxRRA6M7STZNgpOVZaw9cXAwQT/Xm4j/5xT8cRL5wvOkBt+J17hMI0u4BUTkAtvlZuxmED3AkEA3LhGCKTxFP8ASwCLcOp1S6n5kAD836hfEcPggrK9j2C6JaL3NboYwuVOHFOZNEeZaSmWhVSQkM8Eo/W8/wXg2wJBAIduCzK5VsgovCLVLXjNe7mUtzDtgMmTiSGnfA97hQKnIAX024WSCw4BvzXt+KZuWU/goNRn7xgIWB6Q+dQhL+sCQDa8C8IBBTfrtKrky6+ItOW6lQS8+fiMzGafaIGAyblQh1jl/N/58kICk0g7AvcP+l81zPnrap4+eCwguGmVj5kCQGMMQuF5/EIhwlQk42UK/UJXPdcPVUj2Eczn4yc5ic9ZHZ0JeEi5PDvi0TCSXMKcwYeVV2h+nY07uXdEwhmVLL0=
         * 支付宝公钥 : MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDG389NdKmNItlDEy5gCmP4YoXbQh57OtfRiYuyTxy5OETWle1t65+aciGW2NSoJBCq2XvkEwRrQk5uMrxMehYW1NkdEU0MJrcauhX2MwOmeOOR8WSu2k7BSja3TF2orHIdW2HGF2u8Kmi2QHZKEhLAdT5LQbb58WrIj3Cpc+qzTQIDAQAB
         * 授权域名 : http://kuaipao.myejq.com
         * type : 用户版
         */

        private String id;
        private String 帐号;
        private String APPID;
        private String 合作者身份;
        private String 商户私钥;
        private String 支付宝公钥;
        private String 授权域名;
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String get帐号() {
            return 帐号;
        }

        public void set帐号(String 帐号) {
            this.帐号 = 帐号;
        }

        public String getAPPID() {
            return APPID;
        }

        public void setAPPID(String APPID) {
            this.APPID = APPID;
        }

        public String get合作者身份() {
            return 合作者身份;
        }

        public void set合作者身份(String 合作者身份) {
            this.合作者身份 = 合作者身份;
        }

        public String get商户私钥() {
            return 商户私钥;
        }

        public void set商户私钥(String 商户私钥) {
            this.商户私钥 = 商户私钥;
        }

        public String get支付宝公钥() {
            return 支付宝公钥;
        }

        public void set支付宝公钥(String 支付宝公钥) {
            this.支付宝公钥 = 支付宝公钥;
        }

        public String get授权域名() {
            return 授权域名;
        }

        public void set授权域名(String 授权域名) {
            this.授权域名 = 授权域名;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}

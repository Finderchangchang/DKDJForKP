package liuliu.kp.method;

import java.io.StringReader;

import android.content.Context;
import android.util.Log;

import org.apache.http.NameValuePair;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.os.AsyncTask;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import liuliu.kp.R;
import liuliu.kp.base.BaseActivity;
import liuliu.kp.base.BaseApplication;
import liuliu.kp.config.Util;

import android.util.Xml;

import java.util.HashMap;
import java.util.Map;

import liuliu.kp.wxapi.MD5;

import org.apache.http.message.BasicNameValuePair;

import liuliu.kp.config.Key;

import org.xmlpull.v1.XmlPullParser;

import android.app.ProgressDialog;

import com.tencent.mm.sdk.modelpay.PayReq;

import android.widget.Toast;

/**
 * 作者：lwj on 2016/7/21 21:40
 * 邮箱：1031066280@qq.com
 */
public class WxUtil {

    private Context mcontext;
    private String mbody;
    private String morderID;
    private int mprice;
    private StringBuffer sbb;
    private IWXAPI msgApi;
    private Map<String, String> resultunifiedorder;
    // 微信支付的参数
    private PayReq request;

    /**
     * @param context
     * @param body
     * @param desc
     * @param orderID
     * @param price
     * @return
     */

    public void load(Context context, String body, String desc, String orderID, int price) {
        mcontext = context;
        mbody = body;
        morderID = orderID;
        mprice = price;
        sbb = new StringBuffer();
        msgApi = WXAPIFactory.createWXAPI(mcontext, Utils.getCache("APP_ID"));
        request = new PayReq();
   /*     if (msgApi != null) {
            msgApi.registerApp(Key.APP_ID);//注册App到微信

        }*/
        String urlString = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        PrePayIdAsyncTask prePayIdAsyncTask = new PrePayIdAsyncTask();
        prePayIdAsyncTask.execute(urlString);      //生成prepayId
    }


    private class PrePayIdAsyncTask extends AsyncTask<String, Void, Map<String, String>> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            // dialog = ProgressDialog.show(mcontext, "提示", "正在提交订单");

        }

        @Override
        protected Map<String, String> doInBackground(String... params) {
            String url = String.format(params[0]);
            String entity = getProductArgs();
            Log.e("Simon", ">>>>entity:" + entity);
            byte[] buf = Util.httpPost(url, entity);
            //byte[] buf=net.sourceforge.simcpux.Util
            String content = new String(buf);//返回的东西
            Log.e("orion", "----" + content);
            Map<String, String> xml = decodeXml(content);

            return xml;
        }

        //微信支付调起结果
        @Override
        protected void onPostExecute(Map<String, String> result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (dialog != null) {
                dialog.dismiss();
            }
            sbb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");
            Log.e("prepay_id", result.get("prepay_id"));
            resultunifiedorder = result;
            Log.e("resulet:", result + "");
            genPayReq();//生成签名参数
            sendPayReq();//调起支付

        }
    }

    String nonceStr = getNonceStr();//随机数

    private String getProductArgs() {
        // TODO Auto-generated method stub
        try {

            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("appid", Utils.getCache("APP_ID")));

            packageParams.add(new BasicNameValuePair("body", BaseApplication.getContext().getResources().getString(R.string.app_name)+"-充值"));
            packageParams.add(new BasicNameValuePair("mch_id", Utils.getCache("MCH_ID")));
            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
            packageParams.add(new BasicNameValuePair("notify_url", Utils.getCache("domain") + "/wx/userNotify.aspx"));//写你们的回调地址
            packageParams.add(new BasicNameValuePair("out_trade_no", morderID));//订单码
            packageParams.add(new BasicNameValuePair("spbill_create_ip", "127.0.0.1"));//终端ip
            packageParams.add(new BasicNameValuePair("total_fee", mprice + ""));
            packageParams.add(new BasicNameValuePair("trade_type", "APP"));

            String sign = getPackageSign(packageParams);
            packageParams.add(new BasicNameValuePair("sign", sign));
            String xmlString = toXml(packageParams);
            return new String(xmlString.toString().getBytes(), "ISO-8859-1");
        } catch (Exception e) {
            Exception s = e;
            Log.i("eeeeeeeee", e.toString());
            return null;
        }
    }

    public Map<String, String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if ("xml".equals(nodeName) == false) {
                            //实例化student对象
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
            Log.e("Simon", "----" + e.toString());
        }
        return null;

    }

    // 生成随机号，防重发
    private String getNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
                .getBytes());
    }
    /*
     * 封装结束
	 *
	 */

	/*
     *
	 生成签名
	 */

    private String getPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Utils.getCache("API_KEY"));

        Log.e("Simon", ">>>>sb:" + sb);
        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("Simon", ">>>>packageSign:" + packageSign);
        return packageSign;
    }

    /*
 * 转换成xml
 *
 */
    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");


            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");

        Log.e("Simon", ">>>>sb:" + sb.toString());
        return sb.toString();
    }

    /*
     * 生成签名参数 封装
	 */
    private void genPayReq() {

        request.appId = Utils.getCache("APP_ID");
        request.partnerId = Utils.getCache("MCH_ID");
        if (resultunifiedorder != null) {
            request.prepayId = resultunifiedorder.get("prepay_id");
            Log.e(">>>>>partnerId:", request.partnerId);
            request.packageValue = "Sign=WXPay";
        } else {
            Toast.makeText(mcontext, "prepayid为空",
                    Toast.LENGTH_SHORT).show();
        }
        request.timeStamp = String.valueOf(genTimeStamp());
        request.nonceStr = getNonceStr();

        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", request.appId));
        signParams.add(new BasicNameValuePair("noncestr", request.nonceStr));
        signParams.add(new BasicNameValuePair("package", request.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", request.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", request.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", request.timeStamp));

        request.sign = genAppSign(signParams);

        sbb.append("sign\n" + request.sign + "\n\n");
        Log.e("Simon", "----" + signParams.toString());

    }

    /*
 * 调起微信支付
 */
    private void sendPayReq() {

        msgApi.registerApp(Utils.getCache("APP_ID"));
        Log.e("request:", request + "");
        msgApi.sendReq(request);


    }


    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sbbb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sbbb.append(params.get(i).getName());
            sbbb.append('=');
            sbbb.append(params.get(i).getValue());
            sbbb.append('&');
        }
        sbbb.append("key=");
        sbbb.append(Utils.getCache("API_KEY"));

        this.sbb.append("sign str\n" + sbbb.toString() + "\n\n");
        String appSign = MD5.getMessageDigest(sbbb.toString().getBytes());
        Log.e("Simon", "----" + appSign);
        return appSign;
    }

}

package liuliu.kp.method;

import java.util.Map;

import liuliu.kp.model.ALiModel;
import liuliu.kp.model.GroupModel;
import liuliu.kp.model.HBModel;
import liuliu.kp.model.HBsModel;
import liuliu.kp.model.MessageModel;
import liuliu.kp.model.NormalDataModel;
import liuliu.kp.model.ShopModel;
import liuliu.kp.model.VersionModel;
import liuliu.kp.model.WXModel;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Administrator on 2016/9/20.
 */


//短信接口 0 注册 1找回密码 2短信登陆
public interface GitHubAPI {
    //登录
    @GET("App/Cpaotui/login.aspx")
    Observable<MessageModel> login(@QueryMap Map<String, String> map);

    //获得验证码
    @GET("App/Cpaotui/getCode.aspx")
    Observable<MessageModel> getCode(@QueryMap Map<String, String> map);

    //注册FindPassword
    @GET("App/Cpaotui/register.aspx")
    Observable<MessageModel> regUser(@QueryMap Map<String, String> map);

    //忘记密码
    @GET("App/Cpaotui/FindPassword.aspx")
    Observable<MessageModel> findPwd(@QueryMap Map<String, String> map);

    //2.2.1.	获取骑士坐标
    @GET("App/Cpaotui/Getdeliverlatlng.aspx")
    Observable<MessageModel> getQsLatLngs(@Query("cityid") String cityid);

    //算路
    @GET("App/Cpaotui/GetSendfee.aspx")
    Observable<MessageModel> suanLu(@QueryMap Map<String, String> map);

    //添加订单
    @GET("App/Cpaotui/SubmitOrder.aspx")
    Observable<MessageModel> saveOrder(@QueryMap Map<String, String> map);

    //获得地址列表
    @GET("App/Cpaotui/GetAddresslist.aspx")
    Observable<MessageModel> getAddressList(@QueryMap Map<String, String> userid);

    //增删改地址
    @GET("App/Cpaotui/AddMyaddress.aspx")
    Observable<MessageModel> addAddress(@QueryMap Map<String, String> map);

    //获得订单列表
    @GET("App/Cpaotui/GetOrderlistByUserid.aspx")
    Observable<MessageModel> getOrderList(@QueryMap Map<String, String> map);

    //获得订单详情
    @GET("App/Cpaotui/GetOrderInfoByOrderid.aspx")
    Observable<MessageModel> getOrderDetail(@Query("dataid") String orderid);

    //取消订单
    @GET("App/Cpaotui/CancelOrder.aspx")
    Observable<MessageModel> cancleOrder(@QueryMap Map<String, String> map);

    //获得用户信息
    @GET("App/Cpaotui/GetUserinfo.aspx")
    Observable<MessageModel> getUserById(@Query("userid") String orderid);

    //获得城市列表
    @GET("App/Cpaotui/GetCityList.aspx")
    Observable<MessageModel> getCityList();


    //获得城市列表
    @GET("App/Cpaotui/getShopFenlei.aspx")
    Observable<GroupModel> getShopFenlei(@Query("cityid") String cityid);

    //获得城市列表
    @GET("App/Cpaotui/getShopFenleiShop.aspx")
    Observable<ShopModel> getShopDetail(@QueryMap Map<String, String> map);

    @GET("download/version.aspx?c=4")
    Observable<VersionModel> checkUpdate();

    @GET("App/Android/getWxAlipay.aspx?type=1&name=user")
    Observable<ALiModel> getAli();

    @GET("App/Android/getWxAlipay.aspx?type=2&name=user")
    Observable<WXModel> getWX();

    //检测当前账号，是否能领取红包
    @GET("App/Android/gethongbao.aspx")
    Observable<HBModel> getHB(@Query("uid") String orderid);

    //红包查询接口（用于消费选红包）
    @GET("App/Android/gethongbaoList.aspx")
    Observable<HBsModel> getHBList(@Query("uid") String orderid);

    //根据城市ID获得体积，重量，车类型
    @GET("/App/Cpaotui/getTJzlLx.aspx")
    Observable<NormalDataModel> getTJzlLx(@Query("cityid") String cityid);
}

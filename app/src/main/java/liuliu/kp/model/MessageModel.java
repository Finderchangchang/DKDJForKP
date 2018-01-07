package liuliu.kp.model;

import java.util.List;

/**
 * Created by Administrator on 2016/11/28.
 */

public class MessageModel {
    private String success;
    private String errorMsg;
    private CodeModel data;
    private List<LatLngModel> deliverdata;//骑士坐标集合
    private FeiModel feedata;//算费
    private List<PoiModel> datalist;
    private OrderListModel orderdata;//
    private UserModel userdata;
    private OrderDetailModel orderinfodata;
    private List<CityModel> citydata;


    public List<CityModel> getCitydata() {
        return citydata;
    }

    public void setCitydata(List<CityModel> citydata) {
        this.citydata = citydata;
    }

    public OrderDetailModel getOrderinfodata() {
        return orderinfodata;
    }

    public void setOrderinfodata(OrderDetailModel orderinfodata) {
        this.orderinfodata = orderinfodata;
    }

    public UserModel getUserdata() {
        return userdata;
    }

    public void setUserdata(UserModel userdata) {
        this.userdata = userdata;
    }

    public List<PoiModel> getDatalist() {
        return datalist;
    }

    public OrderListModel getOrderdata() {
        return orderdata;
    }

    public void setOrderdata(OrderListModel orderdata) {
        this.orderdata = orderdata;
    }

    public void setDatalist(List<PoiModel> datalist) {
        this.datalist = datalist;
    }

    public List<LatLngModel> getDeliverdata() {
        return deliverdata;
    }

    public void setDeliverdata(List<LatLngModel> deliverdata) {
        this.deliverdata = deliverdata;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public CodeModel getData() {
        return data;
    }

    public void setData(CodeModel data) {
        this.data = data;
    }

    public FeiModel getFeedata() {
        return feedata;
    }

    public void setFeedata(FeiModel feedata) {
        this.feedata = feedata;
    }
}

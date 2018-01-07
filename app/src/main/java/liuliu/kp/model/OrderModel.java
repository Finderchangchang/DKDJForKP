package liuliu.kp.model;

import java.io.Serializable;


/**
 * Created by Administrator on 2016/12/2.
 */


public class OrderModel implements Serializable{

    /**
     * orderid : pt20161201105127522
     * dataid : 17561
     * orderTime : 2016-12-01 10:51:01
     * TotalPrice : 5.0
     * orderStatus : 3
     * sendState : 3
     * IsShopSet : 1
     * orderType : 我要买
     * payState : 1
     * qAddress : 天鹅中路西苑小区南区53号楼
     * sAddress : 莲池区恒祥北大街479号
     * qTell :
     * sTell : 17093215800
     * deliverName : 孙金峰
     * deliverPhone : 15805373031
     */

    private String orderid;
    private String dataid;
    private String orderTime;
    private String TotalPrice;
    private String orderStatus;
    private String sendState;
    private String IsShopSet;
    private String orderType;
    private String payState;
    private String qAddress;
    private String sAddress;
    private String qTell;
    private String sTell;
    private String deliverName;
    private String deliverPhone;
    private String dingdanzhuangtai;

    public String getqAddress() {
        return qAddress;
    }

    public void setqAddress(String qAddress) {
        this.qAddress = qAddress;
    }

    public String getsAddress() {
        return sAddress;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress;
    }

    public String getqTell() {
        return qTell;
    }

    public void setqTell(String qTell) {
        this.qTell = qTell;
    }

    public String getsTell() {
        return sTell;
    }

    public void setsTell(String sTell) {
        this.sTell = sTell;
    }

    public String getDingdanzhuangtai() {
        return dingdanzhuangtai;
    }

    public void setDingdanzhuangtai(String dingdanzhuangtai) {
        this.dingdanzhuangtai = dingdanzhuangtai;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getDataid() {
        return dataid;
    }

    public void setDataid(String dataid) {
        this.dataid = dataid;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String TotalPrice) {
        this.TotalPrice = TotalPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getSendState() {
        return sendState;
    }

    public void setSendState(String sendState) {
        this.sendState = sendState;
    }

    public String getIsShopSet() {
        return IsShopSet;
    }

    public void setIsShopSet(String IsShopSet) {
        this.IsShopSet = IsShopSet;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }

    public String getQAddress() {
        return qAddress;
    }

    public void setQAddress(String qAddress) {
        this.qAddress = qAddress;
    }

    public String getSAddress() {
        return sAddress;
    }

    public void setSAddress(String sAddress) {
        this.sAddress = sAddress;
    }

    public String getQTell() {
        return qTell;
    }

    public void setQTell(String qTell) {
        this.qTell = qTell;
    }

    public String getSTell() {
        return sTell;
    }

    public void setSTell(String sTell) {
        this.sTell = sTell;
    }

    public String getDeliverName() {
        return deliverName;
    }

    public void setDeliverName(String deliverName) {
        this.deliverName = deliverName;
    }

    public String getDeliverPhone() {
        return deliverPhone;
    }

    public void setDeliverPhone(String deliverPhone) {
        this.deliverPhone = deliverPhone;
    }
}

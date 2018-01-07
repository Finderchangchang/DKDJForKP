package liuliu.kp.listener;

import java.util.HashMap;
import java.util.Map;

import liuliu.kp.config.Key;
import liuliu.kp.method.HttpUtil;
import liuliu.kp.method.Utils;
import liuliu.kp.view.IOrder;
import liuliu.kp.view.IOrderDetail;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/12/2.
 */
interface IOrderMView {

    void loadOrder(int pageindex, String sendstate);

    void cancleOrder(String orderType, String orderId);

    void getOrderDetail(String did);
}

public class OrderListener implements IOrderMView {
    IOrder mView;
    IOrderDetail mDetail;

    public OrderListener(IOrder mView) {
        this.mView = mView;
    }

    public OrderListener(IOrderDetail mDetail) {
        this.mDetail = mDetail;
    }

    @Override
    public void loadOrder(int pageindex, String sendstate) {
        Map<String, String> map = new HashMap<>();
        map.put("userid", Utils.getCache(Key.KEY_UserId));
        map.put("orderstatus", sendstate);
        map.put("pageindex", pageindex + "");
        map.put("pagesize", "5");
        HttpUtil.load()
                .getOrderList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    if (("1").equals(model.getSuccess())) {
                        if (model.getOrderdata() != null) {
                            mView.refreshOrder(model.getOrderdata());
                        } else {
                            mView.refreshOrder(null);
                        }
                    } else {
                        mView.refreshOrder(null);
                    }
                }, error -> {
                    mView.refreshOrder(null);
                });
    }

    @Override
    public void cancleOrder(String orderType, String orderId) {

    }

    @Override
    public void getOrderDetail(String did) {
        HttpUtil.load()
                .getOrderDetail(did)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    if (("1").equals(model.getSuccess())) {
                        if (model != null) {
                            mDetail.loadResult(model.getOrderinfodata());
                        } else {
                            mDetail.loadResult(null);
                        }
                    } else {
                        mDetail.loadResult(null);
                    }
                }, error -> {
                    mDetail.loadResult(null);
                });
    }
}

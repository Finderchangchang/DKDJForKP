package liuliu.kp.listener;

import liuliu.kp.method.HttpUtil;
import liuliu.kp.method.Utils;
import liuliu.kp.model.ALiModel;
import liuliu.kp.model.WXModel;
import liuliu.kp.view.IMain;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/30.
 */

public class MainListener implements IMainMView {
    IMain mView;

    public MainListener(IMain mView) {
        this.mView = mView;
    }

    @Override
    public void loadQSLatLngs(String cid) {
        HttpUtil.load()
                .getQsLatLngs(cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    if (("1").equals(model.getSuccess())) {
                        if (model.getDeliverdata() != null) {
                            mView.getQsLatLng(model.getDeliverdata());
                        } else {
                            mView.getQsLatLng(null);
                        }
                    } else {
                        mView.getQsLatLng(null);
                    }
                }, error -> {
                    mView.getQsLatLng(null);
                });
    }

    @Override
    public void checkUpdate() {
        HttpUtil.load()
                .getAli()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    if (model.getData() != null && model.getData().size() > 0) {
                        ALiModel.DataBean key = model.getData().get(0);
                        Utils.putCache("PARTNER", key.get合作者身份());
                        Utils.putCache("SELLER", key.get帐号());
                        Utils.putCache("HD", key.get授权域名());
                        Utils.putCache("RSA_PRIVATE", key.get商户私钥());
                    }
                }, error -> {
                    String s = "";
                });
        HttpUtil.load()
                .getWX()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    if (model.getData() != null && model.getData().size() > 0) {
                        WXModel.DataBean key = model.getData().get(0);
                        Utils.putCache("APP_ID", key.getAppId());
                        Utils.putCache("MCH_ID", key.getPartnerid());
                        Utils.putCache("domain", key.getDomain());
                        Utils.putCache("API_KEY", key.getApikey());
                    }
                }, error -> {
                    String s = "";
                });
    }
}

interface IMainMView {
    void loadQSLatLngs(String cid);

    void checkUpdate();
}

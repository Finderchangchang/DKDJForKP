package liuliu.kp.listener;

import liuliu.kp.method.HttpUtil;
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
                .checkUpdate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    mView.checkUpdate(model);
                }, error -> {
                    mView.checkUpdate(null);
                });
    }
}

interface IMainMView {
    void loadQSLatLngs(String cid);

    void checkUpdate();
}

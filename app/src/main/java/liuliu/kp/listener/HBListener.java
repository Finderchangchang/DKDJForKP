package liuliu.kp.listener;

import liuliu.kp.config.Key;
import liuliu.kp.method.HttpUtil;
import liuliu.kp.method.Utils;
import liuliu.kp.view.IHB;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Finder丶畅畅 on 2018/1/7 18:15
 * QQ群481606175
 */
interface IHBMView {
    void getHB();

    void getHBList();
}

public class HBListener implements IHBMView {
    IHB mView;

    public HBListener(IHB mView) {
        this.mView = mView;
    }

    @Override
    public void getHB() {
        HttpUtil.load()
                .getHB(Utils.getCache(Key.KEY_UserId))
                //.getHB("12")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    if (("1").equals(model.getState())) {
                        if (model.getData() != null && model.getData().size() > 0) {
                            mView.getHB(model.getData().get(0));
                        }
                    } else {
                        mView.getHB(null);
                    }
                }, error -> {
                    mView.getHB(null);
                });
    }

    @Override
    public void getHBList() {
        HttpUtil.load()
                .getHBList(Utils.getCache(Key.KEY_UserId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    if (("1").equals(model.getState())) {
                        if (model.getData().size() > 0) {
                            mView.getHBList(model.getData().get(0));
                        } else {
                            mView.getHBList(null);
                        }
                    } else {
                        mView.getHBList(null);
                    }
                }, error -> {
                    mView.getHBList(null);
                });
    }
}

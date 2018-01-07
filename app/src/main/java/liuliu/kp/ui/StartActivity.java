package liuliu.kp.ui;

import net.tsz.afinal.FinalDb;

import liuliu.kp.R;
import liuliu.kp.base.BaseActivity;
import liuliu.kp.method.HttpUtil;
import liuliu.kp.method.Utils;
import liuliu.kp.model.CityModel;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 启动页面
 * Created by Administrator on 2016/12/5.
 */

public class StartActivity extends BaseActivity {
    @Override
    public void initViews() {
        setContentView(R.layout.activity_start);
        db = FinalDb.create(this);
    }

    FinalDb db;

    @Override
    public void initEvents() {
        String userid = Utils.getCache("UserId");
        if (userid == null) {
            Utils.putCache("UserId", "");
        }
        HttpUtil.load()
                .getCityList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    if (("1").equals(model.getSuccess())) {
                        db.deleteAll(CityModel.class);//删除所有的城市。
                        for (CityModel cityModel : model.getCitydata()) {
                            db.save(cityModel);
                        }
                        Utils.IntentPost(MainActivity.class);
                        finish();
                    } else {
                        ToastShort(model.getErrorMsg());
                        Utils.IntentPost(MainActivity.class);
                        finish();
                    }
                }, error -> {
                    ToastShort("请检查网络连接");
                    Utils.IntentPost(MainActivity.class);
                    finish();
                });
    }
}

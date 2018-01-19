package liuliu.kp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.youth.banner.Banner;

import liuliu.kp.R;
import liuliu.kp.base.BaseActivity;
import liuliu.kp.method.GlideImageLoader;
import liuliu.kp.method.HttpUtil;
import liuliu.kp.model.ShopModel;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShopDetailActivity extends BaseActivity {

    String shop_id;
    Banner main_title_ban;

    @Override
    public void initViews() {
        shop_id = getIntent().getStringExtra("shop_id");
        setContentView(R.layout.activity_shop_detail);
        main_title_ban = (Banner) findViewById(R.id.main_title_ban);
        main_title_ban.setImageLoader(new GlideImageLoader());
    }

    @Override
    public void initEvents() {
        HttpUtil.load()
                .getShopDetail(shop_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    if (("1").equals(model.getState())) {
                        ShopModel.DataBean key;
                        if (model.getData().size() > 0) {
                            key = model.getData().get(0);

                        }

                    }
                });
    }
}

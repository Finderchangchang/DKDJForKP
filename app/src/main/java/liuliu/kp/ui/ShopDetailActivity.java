package liuliu.kp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import liuliu.kp.R;
import liuliu.kp.base.BaseActivity;
import liuliu.kp.method.CommonAdapter;
import liuliu.kp.method.CommonViewHolder;
import liuliu.kp.method.GlideImageLoader;
import liuliu.kp.method.HttpUtil;
import liuliu.kp.model.ImgModel;
import liuliu.kp.model.ShopModel;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShopDetailActivity extends BaseActivity {

    String shop_id;
    GridView img_gv;
    CommonAdapter<ImgModel> adapter;
    List<ImgModel> list = new ArrayList();
    Button select_shop_btn;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_shop_detail);
        img_gv = (GridView) findViewById(R.id.img_gv);
        select_shop_btn = (Button) findViewById(R.id.select_shop_btn);
        adapter = new CommonAdapter<ImgModel>(this, list, R.layout.item_img) {
            @Override
            public void convert(CommonViewHolder holder, ImgModel imgModel, int position) {
                holder.setImageURL(R.id.img_iv, imgModel.getUrl());
                holder.setBGText(R.id.title_tv, imgModel.getTitle());
            }
        };
        img_gv.setAdapter(adapter);
        shop_id = getIntent().getStringExtra("shop_id");
    }

    ShopModel.DataBean key;

    @Override
    public void initEvents() {
        select_shop_btn.setOnClickListener(v -> {
            if (key != null) {
                setResult(11, new Intent().putExtra("model", key));
                finish();
            }
        });
        HttpUtil.load()
                .getShopDetail(shop_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    if (("1").equals(model.getState())) {
                        if (model.getData().size() > 0) {
                            key = model.getData().get(0);
                            if (key != null) {
                                list.add(new ImgModel(key.getZhizhao(), "营业执照"));
                                list.add(new ImgModel(key.getCard1(), "法人身份证"));
                                list.add(new ImgModel(key.getCard2(), "法人身份证反面"));
                                list.add(new ImgModel(key.getEwmMoney(), "收款二维码"));
                                list.add(new ImgModel(key.getImga(), "展示图"));
                                list.add(new ImgModel(key.getImgb(), "展示图"));
                                list.add(new ImgModel(key.getImgc(), "展示图"));
                                list.add(new ImgModel(key.getImgd(), "展示图"));
                                list.add(new ImgModel(key.getImge(), "展示图"));
                            }
                            adapter.refresh(list);
                        } else {
                            ToastShort("当前无数据");
                            finish();
                        }

                    } else {
                        ToastShort("当前无数据");
                        finish();
                    }
                }, error -> {
                    ToastShort("当前无数据");
                    finish();
                });
    }
}

package liuliu.kp.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;

import net.tsz.afinal.view.TitleBar;

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

    ShopModel.DataBean shop;
    List<String> list = new ArrayList();
    Button select_shop_btn;
    TextView shop_xiangmu_tv;
    TextView shop_address_tv;
    TitleBar title_bar;
    ImageView sk_iv;
    Banner banner;
    LinearLayout tel_ll;
    String url;
    String shop_id;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_shop_detail);
        banner = (Banner) findViewById(R.id.banner);
        tel_ll = (LinearLayout) findViewById(R.id.tel_ll);
        title_bar = (TitleBar) findViewById(R.id.title_bar);
        title_bar.setLeftClick(() -> finish());
        sk_iv = (ImageView) findViewById(R.id.sk_iv);
        shop_xiangmu_tv = (TextView) findViewById(R.id.shop_xiangmu_tv);
        shop_address_tv = (TextView) findViewById(R.id.shop_address_tv);
        select_shop_btn = (Button) findViewById(R.id.select_shop_btn);
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        tel_ll.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + shop.getPhone()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        shop = (ShopModel.DataBean) getIntent().getSerializableExtra("model");
        if (shop != null) {
            shop_id = shop.getId();
            title_bar.setCenter_str(shop.getName());
            shop_xiangmu_tv.setText("商家地址：" + shop.getAddress());
            shop_address_tv.setText("经营项目：" + shop.getXiangmu());
            list.add(shop.getImga());
            if (!TextUtils.isEmpty(shop.getImgb())) {
                list.add(shop.getImgb());
            }
            if (!TextUtils.isEmpty(shop.getImgc())) {
                list.add(shop.getImgc());
            }
            if (!TextUtils.isEmpty(shop.getImgd())) {
                list.add(shop.getImgd());
            }
            if (!TextUtils.isEmpty(shop.getImge())) {
                list.add(shop.getImge());
            }
            banner.setImages(list);
            //banner设置方法全部调用完毕时最后调用
            banner.start();
            Glide.with(this)
                    .load(shop.getEwmMoney())
                    .placeholder(R.mipmap.iconb)
                    .into(sk_iv);
            url = shop.getEwmMoney();
        }
        sk_iv.setOnClickListener(v -> {
            Intent intent = new Intent(this, BigImgActivity.class);
            intent.putExtra("url", url);
            intent.putExtra("shop_id", shop_id);
            startActivity(intent);
        });
    }


    @Override
    public void initEvents() {
        select_shop_btn.setOnClickListener(v -> {
            if (shop != null) {
                setResult(11, new Intent().putExtra("model", shop));
                finish();
            }
        });
    }
}

package liuliu.kp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import net.tsz.afinal.view.TitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import liuliu.kp.R;
import liuliu.kp.base.BaseActivity;
import liuliu.kp.base.BaseApplication;
import liuliu.kp.method.CommonAdapter;
import liuliu.kp.method.CommonViewHolder;
import liuliu.kp.method.HttpUtil;
import liuliu.kp.method.Utils;
import liuliu.kp.model.ImgModel;
import liuliu.kp.model.OrderModel;
import liuliu.kp.model.PoiModel;
import liuliu.kp.model.ShopModel;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 * */
public class ShopsActivity extends BaseActivity {
    CommonAdapter<ShopModel.DataBean> mAdapter;
    List<ShopModel.DataBean> list = new ArrayList<>();
    TitleBar title_bar;
    ListView lv;
    String shop_id;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_shops);
        title_bar = (TitleBar) findViewById(R.id.title_bar);
        shop_id = getIntent().getStringExtra("shop_id");
        title_bar.setLeftClick(() -> finish());
        lv = (ListView) findViewById(R.id.lv);
        mAdapter = new CommonAdapter<ShopModel.DataBean>(this, list, R.layout.item_shop) {
            @Override
            public void convert(CommonViewHolder holder, ShopModel.DataBean model, int position) {
                holder.setText(R.id.title_tv, model.getName());
                holder.setText(R.id.tel_tv, "商家电话：" + model.getPhone());
                holder.setText(R.id.desc_tv, model.getXiangmu());
                holder.setImageURL(R.id.shop_img_iv, model.getImga());
                holder.setText(R.id.juli_tv, model.getJuli() + "");

            }
        };
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, ShopDetailActivity.class);
            intent.putExtra("model", list.get(position));
            startActivityForResult(intent, 12);
        });
        Map<String, String> map = new HashMap<>();
        map.put("fenleiid", shop_id);
        map.put("lat", Utils.getCache("now_lat"));
        map.put("lng", Utils.getCache("now_lng"));

        HttpUtil.load()
                .getShopDetail(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    if (("1").equals(model.getState())) {
                        if (model.getData().size() > 0) {
                            list = model.getData();
                            mAdapter.refresh(list);
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

    PoiModel poiModel = new PoiModel();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 11) {
            ShopModel.DataBean key = (ShopModel.DataBean) data.getSerializableExtra("model");
            if (key != null) {
                Intent intent = new Intent();
                poiModel.setLat(Double.parseDouble(key.getLat()));
                poiModel.setLng(Double.parseDouble(key.getLng()));
                poiModel.setDetailAddress(key.getAddress());
                poiModel.setPoiName(key.getName());
                intent.putExtra("val", poiModel);
                setResult(8, intent);
                finish();//关闭当前页面
            }
        }
    }

    @Override
    public void initEvents() {

    }
}

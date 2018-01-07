package liuliu.kp.ui;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.tsz.afinal.view.TitleBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import liuliu.kp.R;
import liuliu.kp.base.BaseActivity;
import liuliu.kp.method.HttpUtil;
import liuliu.kp.model.OrderDetailModel;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/12/3.
 */

public class OrderDetailActivity extends BaseActivity {
    @Bind(R.id.title_bar)
    TitleBar titleBar;
    @Bind(R.id.od_title)
    LinearLayout odTitle;
    @Bind(R.id.order_state_tv)
    TextView order_state_tv;
    @Bind(R.id.od_id)
    TextView odId;
    @Bind(R.id.od_createtime)
    TextView odCreatetime;
    @Bind(R.id.od_fa_address)
    TextView odFaAddress;
    @Bind(R.id.od_shou_address)
    TextView odShouAddress;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.od_fa_phone)
    TextView odFaPhone;
    @Bind(R.id.od_shou_phone)
    TextView odShouPhone;
    @Bind(R.id.od_totalmoney)
    TextView odTotalmoney;
    @Bind(R.id.od_juli)
    TextView odJuli;
    @Bind(R.id.od_type)
    TextView odType;
    @Bind(R.id.od_content)
    LinearLayout odContent;
    String dataid;
    @Bind(R.id.od_content_tv)
    TextView od_content_tv;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        dataid = getIntent().getStringExtra("dataid");
        String orderstate = getIntent().getStringExtra("orderstate");
        if (orderstate != null) {
            order_state_tv.setText(orderstate);
            odTitle.setVisibility(View.VISIBLE);
        } else {
            odTitle.setVisibility(View.GONE);
        }
    }

    @Override
    public void initEvents() {
        titleBar.setLeftClick(() -> finish());
        HttpUtil.load()
                .getOrderDetail(dataid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    if (("1").equals(model.getSuccess())) {
                        OrderDetailModel order = model.getOrderinfodata();
                        if (order != null) {
                            odId.setText(order.getOrderid());
                            odCreatetime.setText(order.getOrderTime());
                            odFaAddress.setText(order.getQAddress());
                            odShouAddress.setText(order.getSAddress());
                            odFaPhone.setText(order.getQTell());
                            odShouPhone.setText(order.getSTell());
                            odTotalmoney.setText(order.getTotalPrice() + "元");
                            odJuli.setText("约" + order.getJuLi() + "公里");
                            odType.setText(order.getOrderType());
                            od_content_tv.setText(order.getRemark());
                        }
                    } else {

                    }
                }, error -> {

                });

    }
}

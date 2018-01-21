package liuliu.kp.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import liuliu.kp.R;
import liuliu.kp.base.BaseApplication;
import liuliu.kp.config.Key;
import liuliu.kp.listener.HBListener;
import liuliu.kp.listener.OrderListener;
import liuliu.kp.method.CommonAdapter;
import liuliu.kp.method.CommonViewHolder;
import liuliu.kp.method.RefreshLayout;
import liuliu.kp.method.Utils;
import liuliu.kp.method.WxUtil;
import liuliu.kp.model.HBModel;
import liuliu.kp.model.HBsModel;
import liuliu.kp.model.OrderListModel;
import liuliu.kp.model.OrderModel;
import liuliu.kp.model.UserModel;
import liuliu.kp.view.IHB;
import liuliu.kp.view.IOrder;
import liuliu.kp.wxapi.PayResult;
import liuliu.kp.wxapi.SignUtils;

/**
 * Created by Administrator on 2016/12/2.
 */

public class OrderFragment extends Fragment implements IOrder, IHB {
    CommonAdapter mAdapter;
    ListView lv;
    List<OrderModel> order;
    int tab_index;
    int page_num = 0;//当前页数
    RefreshLayout refresh_rfl;
    Dialog bottom_dialog;
    WxUtil wxUtil = new WxUtil();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        order = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.address_list, null, false);
        lv = (ListView) view.findViewById(R.id.address_list_lv);
        refresh_rfl = (RefreshLayout) view.findViewById(R.id.refresh_rfl);
        order = new ArrayList<>();
        inflate = LayoutInflater.from(OrderListActivity.mIntails).inflate(R.layout.dialog_pay, null);
        bottom_dialog = new Dialog(OrderListActivity.mIntails, R.style.ActionSheetDialogStyle);
        mAdapter = new CommonAdapter<OrderModel>(BaseApplication.getContext(), order, R.layout.item_order) {
            @Override
            public void convert(CommonViewHolder holder, OrderModel model, int position) {
                int tab_bg = 0;
                String tel = "";
                switch (model.getOrderType()) {
                    case "帮我送":
                        tab_bg = R.mipmap.order_song;
                        tel = model.getSTell();
                        holder.setText(R.id.io_phone, "送货电话：" + tel);
                        break;
                    case "帮我取":
                        tab_bg = R.mipmap.order_qu;
                        tel = model.getQTell();
                        holder.setText(R.id.io_phone, "取货电话：" + tel);
                        break;
                }
                if (tab_bg != 0) {
                    holder.setBG(R.id.io_title_left, tab_bg);
                } else {//帮我买
                    holder.setBG(R.id.io_title_left, R.mipmap.order_mai);
                    tel = model.getSTell();
                    holder.setText(R.id.io_phone, "送货电话：" + tel);
                }
                if (("2").equals(model.getOrderStatus()) && ("0").equals(model.getPayState())) {
                    holder.setText(R.id.pay_tv, "去支付");
                    holder.setVisible(R.id.pay_tv, true);
                    holder.setImageResource(R.id.io_iv_img, R.mipmap.dai_zhifu);
                } else if (("7").equals(model.getOrderStatus()) && ("0").equals(model.getSendState())) {
                    holder.setText(R.id.pay_tv, "取消订单");
                    holder.setVisible(R.id.pay_tv, true);
                    holder.setImageResource(R.id.io_iv_img, R.mipmap.shalou);
                } else {
                    holder.setVisible(R.id.pay_tv, false);
                }
                holder.setText(R.id.io_tv_type, model.getDingdanzhuangtai());
                if (model.getQAddress() == null || ("").equals(model.getQAddress())) {
                    holder.setText(R.id.io_cong_address, "任意地址购买");
                } else {
                    holder.setText(R.id.io_cong_address, model.getQAddress());
                }
                holder.setText(R.id.io_dao_address, model.getSAddress());
                holder.setText(R.id.io_title_left, model.getOrderType());
                holder.setText(R.id.io_title_right, model.getOrderTime());
                holder.setOnClickListener(R.id.pay_tv, v -> {
                    if (("未支付").equals(model.getDingdanzhuangtai())) {//支付
                        Order_Id = model.getOrderid();
                        Order_Price = Double.parseDouble(model.getTotalPrice());
                        showDialog(model.getOrderid(), model.getTotalPrice());
                    } else {
                        Intent intent = new Intent(OrderListActivity.mIntails, CancelOrderActivity.class);
                        intent.putExtra("orderid", model.getOrderid());
                        startActivityForResult(intent, 8);
                    }
                });
            }
        };
        lv.setAdapter(mAdapter);
        refresh_rfl.setLoading(true);
        refresh_rfl.setOnLoadListener(() -> {
            if (listener != null) {
                if (!bottom) {
                    listener.loadOrder(page_num++, tab_index + "");
                }
            } else {
                refresh_rfl.setLoading(false);
            }
        });
        refresh_rfl.setOnRefreshListener(() -> {
            page_num = 1;
            listener.loadOrder(page_num, tab_index + "");
        });
        lv.setOnItemClickListener((parent, view1, position, id) -> {
            Utils.IntentPost(OrderDetailsActivity.class, intent -> {
                intent.putExtra("orderid", order.get(position).getDataid());
            });
        });
        return view;
    }

    @Override
    public void getHB(HBModel.DataBean model) {

    }

    HBsModel.DataBean lq_model;

    @Override
    public void getHBList(HBsModel.DataBean model) {
        if (model != null && model.get是否可用() == "可用" && model.get是否使用() == "否") {
            lq_model = model;
            load_pop(model.get领取金额());
        } else {
            lq_model = null;
            load_pop("0");
        }
    }

    String Order_Id = "";
    Double Order_Price;

    class MyThread extends Thread {


        public MyThread(String orderId, String price) {
            Order_Id = orderId;
            Order_Price = Double.parseDouble(price);
        }

        public void run() {
            wxUtil.load(OrderListActivity.mIntails, BaseApplication.getContext().getResources().getString(R.string.app_name), BaseApplication.getContext().getResources().getString(R.string.app_name)+"支付", Order_Id, (int) (Order_Price * 100));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 99://取消成功通知刷新
                refreshList(tab_index);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    boolean pay_is_wx = true;
    private View inflate;

    private void showDialog(String orderId, String price) {
//        HBListener listener = new HBListener(this);
//        listener.getHBList();//检测当前是否有红包
        load_pop("0");
    }

    private void load_pop(String num) {
        //将布局设置给Dialog
        bottom_dialog.setContentView(inflate);
        Button pay_btn = (Button) inflate.findViewById(R.id.pay_btn);
        TextView pay_tv = (TextView) inflate.findViewById(R.id.pay_tv);
        RelativeLayout wx_pay_rl = (RelativeLayout) inflate.findViewById(R.id.wx_pay_rl);
        RelativeLayout zfb_pay_rl = (RelativeLayout) inflate.findViewById(R.id.zfb_pay_rl);
        CheckBox wx_cb = (CheckBox) inflate.findViewById(R.id.wx_cb);
        CheckBox zfb_cb = (CheckBox) inflate.findViewById(R.id.zfb_cb);
        TextView hb_pay_tv = (TextView) inflate.findViewById(R.id.hb_pay_tv);
        RelativeLayout hb_pay_rl = (RelativeLayout) inflate.findViewById(R.id.hb_pay_rl);
        if (num != "0") {
            hb_pay_tv.setText("红包金额：" + num + "元");
            hb_pay_rl.setVisibility(View.VISIBLE);
        } else {
            hb_pay_rl.setVisibility(View.GONE);
        }
        wx_cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            zfb_cb.setChecked(!isChecked);
            pay_is_wx = isChecked;
        });
        zfb_cb.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            wx_cb.setChecked(!isChecked);
            pay_is_wx = !isChecked;
        }));
        wx_pay_rl.setOnClickListener(v -> {
            wx_cb.setChecked(true);
            zfb_cb.setChecked(false);
        });
        zfb_pay_rl.setOnClickListener(v -> {
            wx_cb.setChecked(false);
            zfb_cb.setChecked(true);
        });
        pay_tv.setText(Order_Price + "元");
        pay_btn.setOnClickListener(v -> {

            if (Order_Id != null) {
                if (!pay_is_wx) {
                    String orderInfo = getOrderInfo(BaseApplication.getContext().getResources().getString(R.string.app_name), BaseApplication.getContext().getResources().getString(R.string.app_name)+"支付", Order_Price + "", Order_Id);
                    String sign = sign(orderInfo);
                    try {
                        sign = URLEncoder.encode(sign, "UTF-8");
                    } catch (UnsupportedEncodingException e) {

                    }
                    /**
                     * 完整的符合支付宝参数规范的订单信息
                     */
                    final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
                    Runnable payRunnable = () -> {
                        // 构造PayTask 对象
                        PayTask alipay = new PayTask(OrderListActivity.mIntails);
                        // 调用支付接口，获取支付结果
                        String result = alipay.pay(payInfo, true);
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    };
                    Thread payThread = new Thread(payRunnable);
                    payThread.start();
                } else {
                    bottom_dialog.dismiss();
                    new MyThread(Order_Id, Order_Price + "").start();
                }
            }
        });
        ImageView dialog_close_iv = (ImageView) inflate.findViewById(R.id.dialog_close_iv);
        dialog_close_iv.setOnClickListener(v -> bottom_dialog.dismiss());//关闭当前dialog
        //获取当前Activity所在的窗体
        Window dialogWindow = bottom_dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        int intw = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int inth = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        inflate.measure(intw, inth);
        int intheight = inflate.getMeasuredHeight();

        WindowManager.LayoutParams lp = bottom_dialog.getWindow().getAttributes();
        Display display = OrderListActivity.mIntails.getWindowManager().getDefaultDisplay();
        lp.width = display.getWidth(); //设置宽度
        lp.y = -intheight;//设置Dialog距离底部的距离
        dialogWindow.setAttributes(lp);
        bottom_dialog.show();//显示对话框
    }

    OrderListener listener;

    public void refreshList(int position) {
        tab_index = position;
        if (listener == null) {
            listener = new OrderListener(this);
        }
        page_num = 1;
        listener.loadOrder(page_num, position + "");
    }

    boolean bottom = false;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (bottom_dialog != null) {
                bottom_dialog.dismiss();
            }
            switch (msg.what) {
                case 1: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(OrderListActivity.mIntails, "支付成功", Toast.LENGTH_SHORT).show();
                        /*后期需要实现跳转到订单相信页面，是支付失败还是再来一单*/
                        refreshList(tab_index);
                    } else {
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(OrderListActivity.mIntails, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(OrderListActivity.mIntails, "支付失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    /**
     * create the order info. 创建订单信息
     */
    private String getOrderInfo(String subject, String body, String price, String orderid) {
        String a = Utils.getCache("PARTNER") + "---" + Utils.getCache("SELLER");
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + Utils.getCache("PARTNER") + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + Utils.getCache("SELLER") + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + Order_Id + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + Order_Price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + Utils.getCache("HD") + "/Alipay/userKuaipaoNotify.aspx" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    private String sign(String content) {
        String s = Utils.getCache("RSA_PRIVATE");
        return SignUtils.sign(content, Utils.getCache("RSA_PRIVATE"));
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

    @Override
    public void refreshOrder(OrderListModel model) {
        if (model != null) {
            if (page_num > 1) {
                for (OrderModel orderModel : model.getOrderlist()) {
                    order.add(orderModel);
                }
                mAdapter.refresh(order);
            } else {
                order = new ArrayList<>();
                order = model.getOrderlist();
                mAdapter.refresh(order);
                page_num = 1;
            }
        }
        if (refresh_rfl != null) {
            refresh_rfl.setRefreshing(false);
        }
        loadMore(model);
    }

    boolean isBottom;//没到底部

    /**
     * 判断是否有加载更多。。。
     *
     * @param model
     */
    private void loadMore(OrderListModel model) {
        if (model != null) {
            if (model.getTotal() != null && model.getPage() != null) {
                if (("0").equals(model.getTotal()) || model.getTotal().equals(model.getPage())) {
                    isBottom = true;
                    bottom = true;
                    refresh_rfl.closeBottom();
                } else {
                    isBottom = false;
                    bottom = false;
                    refresh_rfl.setLoading(isBottom);
                }
            } else {
                isBottom = false;
                bottom = false;
                refresh_rfl.setLoading(isBottom);
            }
        }
    }

    @Override
    public void loadMoreOrder(OrderListModel model) {

    }

    @Override
    public void changeStateResult(boolean result) {

    }
}

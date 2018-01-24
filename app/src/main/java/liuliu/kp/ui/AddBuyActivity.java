package liuliu.kp.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.view.TitleBar;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import liuliu.kp.R;
import liuliu.kp.base.BaseActivity;
import liuliu.kp.base.BaseApplication;
import liuliu.kp.config.CustomGridView;
import liuliu.kp.config.Key;
import liuliu.kp.config.ShopListActivity;
import liuliu.kp.config.Util;
import liuliu.kp.listener.AddressManageListener;
import liuliu.kp.listener.HBListener;
import liuliu.kp.listener.SuanLuListener;
import liuliu.kp.method.CommonAdapter;
import liuliu.kp.method.CommonViewHolder;
import liuliu.kp.method.HttpUtil;
import liuliu.kp.method.Utils;
import liuliu.kp.method.WxUtil;
import liuliu.kp.model.FeiModel;
import liuliu.kp.model.HBModel;
import liuliu.kp.model.HBsModel;
import liuliu.kp.model.PoiModel;
import liuliu.kp.model.SaveOrderModel;
import liuliu.kp.model.TagModel;
import liuliu.kp.view.IAddBuy;
import liuliu.kp.view.IAddressManage;
import liuliu.kp.view.IHB;
import liuliu.kp.wxapi.PayResult;
import liuliu.kp.wxapi.SignUtils;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static liuliu.kp.R.id.price_detail_ll;
import static liuliu.kp.method.Utils.URLEncodeImage;
import static liuliu.kp.method.Utils.getCache;

/**
 * 购买
 * Created by Administrator on 2016/11/30.
 */

public class AddBuyActivity extends BaseActivity implements IAddBuy, IAddressManage, IHB {
    public static AddBuyActivity mInstail;
    @Bind(R.id.title_bar)
    TitleBar titleBar;
    @Bind(R.id.buy_type_gv)
    CustomGridView buyTypeGv;
    @Bind(R.id.but_what_et)
    EditText butWhatEt;
    @Bind(R.id.jiujin_buy_rb)
    CheckBox jiujinBuyRb;
    @Bind(R.id.change_address_iv)
    ImageView changeAddressIv;
    @Bind(R.id.buy_address_tv)
    EditText buyAddressTv;
    @Bind(R.id.sc_buy_address_iv)
    ImageView scBuyAddressIv;
    @Bind(R.id.send_address_tv)
    EditText sendAddressTv;
    @Bind(R.id.sc_send_address_iv)
    ImageView scSendAddressIv;
    @Bind(R.id.tel_et)
    EditText telEt;
    @Bind(R.id.je_et)
    EditText jeEt;
    @Bind(R.id.no_know_price_rb)
    CheckBox noKnowPriceRb;
    @Bind(R.id.price_tv)
    TextView priceTv;
    @Bind(R.id.pay_tv)
    TextView payTv;
    PoiModel buy_poi;//购买地址
    PoiModel send_poi;//送货地址
    boolean firstIsbuy = true;//第一个model为购买
    @Bind(R.id.buy_address_ll)
    LinearLayout buyAddressLl;
    @Bind(R.id.send_address_ll)
    LinearLayout sendAddressLl;
    List<TagModel> list = new ArrayList<>();
    CommonAdapter<TagModel> modelCommonAdapter;
    @Bind(R.id.tag_key_tv)
    TextView tag_key_tv;
    @Bind(R.id.total_price_tv)
    TextView totalPriceTv;
    @Bind(R.id.good_price_tv)
    TextView goodPriceTv;
    @Bind(price_detail_ll)
    LinearLayout priceDetailLl;
    private RouteSearch routeSearch;
    SuanLuListener mListener;
    Dialog dialog;
    private View inflate;
    @Bind(R.id.select_tel_tv)
    TextView select_tel_tv;
    @Bind(R.id.select_shop_ll)
    LinearLayout select_shop_ll;
    AddressManageListener addressManageListener;
    WxUtil wxUtil = new WxUtil();

    void load() {
        HttpUtil.load()
                .getShopFenlei("18")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    if (("1").equals(model.getState())) {
                        list = new ArrayList<>();
                        for (int i = 0; i < model.getData().size(); i++) {
                            if (i == 0) {
                                list.add(new TagModel(model.getData().get(i).getClassname(), model.getData().get(i).getId(), "", true));
                            } else {
                                list.add(new TagModel(model.getData().get(i).getClassname(), model.getData().get(i).getId(), "", false));
                            }
                        }
                        modelCommonAdapter.refresh(list);
                    }
                });
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_add_buy);
        ButterKnife.bind(this);
        mInstail = this;
        telEt.setText(getCache("tel"));
        titleBar.setLeftClick(() -> finish());
        select_shop_ll.setOnClickListener(v -> {
            Intent intent = new Intent(this, ShopListActivity.class);
            startActivityForResult(intent, 0);
        });
        save = new SaveOrderModel();
        addressManageListener = new AddressManageListener(this);
        inflate = LayoutInflater.from(this).inflate(R.layout.dialog_pay, null);
        send_poi = (PoiModel) getIntent().getSerializableExtra("model");
        load();
        bottom_dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        dialog = Utils.ProgressDialog(this, "算路中，请稍后...", true);
        mListener = new SuanLuListener(this);
        titleBar.setRightClick(() -> Utils.IntentPost(WebActivity.class, intent -> intent.putExtra("web", "计价标准")));
        modelCommonAdapter = new CommonAdapter<TagModel>(this, list, R.layout.item_btn) {
            @Override
            public void convert(CommonViewHolder holder, TagModel tagModel, int position) {
                holder.setBGText(R.id.poi_field_id, tagModel.getTag());
//                if (tagModel.isClick()) {
//                    holder.setBG(R.id.poi_field_id, R.mipmap.tag_click);
//                    holder.setTextColor(R.id.poi_field_id, R.color.colorchongzhizi);
//                } else {
                    holder.setBG(R.id.poi_field_id, R.drawable.tab_btn_bg);
                    holder.setTextColor(R.id.poi_field_id, R.color.colorsettingzi);
//                }
                holder.setOnClickListener(R.id.poi_field_id, v -> {
//                    butWhatEt.setHint(tagModel.getVal());
//                    tag_key_tv.setText(tagModel.getTag());
//                    TagModel tag = list.get(clickItem);
//                    tag.setClick(false);
//                    list.add(clickItem, tag);
//                    list.remove(clickItem);
//                    clickItem = position;
//                    tagModel.setClick(true);
//                    list.add(position, tagModel);
//                    list.remove(position);
//                    modelCommonAdapter.notifyDataSetChanged();
                    Intent intent = new Intent(AddBuyActivity.this, ShopsActivity.class);
                    intent.putExtra("shop_id", tagModel.getId());
                    startActivityForResult(intent, 0);
                });
            }
        };
        scBuyAddressIv.setOnClickListener(v -> {//收藏购买地址
            if (firstIsbuy) {
                if (buy_poi != null) {
                    buy_poi.setState(1);
                    addressManageListener.addAddress(buy_poi);
                }
            } else {
                if (send_poi != null) {
                    send_poi.setState(1);
                    addressManageListener.addAddress(send_poi);
                }
            }
        });
        scSendAddressIv.setOnClickListener(v -> {//收藏收货地址
            if (!firstIsbuy) {
                if (buy_poi != null) {
                    buy_poi.setState(1);
                    addressManageListener.addAddress(buy_poi);
                }
            } else {
                if (send_poi != null) {
                    send_poi.setState(1);
                    addressManageListener.addAddress(send_poi);
                }
            }
        });
        select_tel_tv.setOnClickListener(v -> {
            Intent intent = new Intent(this, ContactsActivity.class);
            startActivityForResult(intent, 0);
        });
        titleBar.setLeftClick(() -> finish());
        buyTypeGv.setAdapter(modelCommonAdapter);
        modelCommonAdapter.notifyDataSetChanged();
        payTv.setOnClickListener(v -> {
            if (("").equals(butWhatEt.getText().toString().trim())) {
                ToastShort("请填写需要购买的物品信息");
            } else if (jiujinBuyRb.isChecked() && firstIsbuy && send_poi == null) {//取送一个为空，且没选中
                ToastShort("请选择收货地址");
            } else if (jiujinBuyRb.isChecked() && !firstIsbuy && buy_poi == null) {
                ToastShort("请选择收货地址");
            } else if (!jiujinBuyRb.isChecked() && (buy_poi == null || send_poi == null)) {
                if (buy_poi == null && send_poi == null) {
                    ToastShort("请选择购买地址");
                } else if (buy_poi == null) {
                    ToastShort(firstIsbuy ? "请选择购买地址" : "请选择收货地址");
                } else if (send_poi == null) {
                    ToastShort(!firstIsbuy ? "请选择购买地址" : "请选择收货地址");
                }
            }
//            else if (("").equals(telEt.getText().toString().trim())) {
//                ToastShort("请填写联系电话");
//            }
            else if (("").equals(jeEt.getText().toString().trim()) && !noKnowPriceRb.isChecked()) {
                ToastShort("请输入商品金额");
            } else {
                save.setOrdertype("1");
                save.setUserid(getCache(Key.KEY_UserId));
                save.setRemark(URLEncodeImage(list.get(clickItem).getTag() + ":" + butWhatEt.getText().toString()));
                if (jiujinBuyRb.isChecked()) {//选择了
                    save.setNearbuy("1");
                } else {
                    save.setNearbuy("0");
                    if (firstIsbuy) {
                        save.setLat1(buy_poi.getLat() + "");
                        save.setLng1(buy_poi.getLng() + "");
                    } else {
                        save.setLat1(send_poi.getLat() + "");
                        save.setLng1(send_poi.getLng() + "");
                    }
                }
                if (firstIsbuy) {
                    save.setLat2(send_poi.getLat() + "");
                    save.setLng2(send_poi.getLng() + "");
                    if (buy_poi != null) {
                        save.setAddress1(URLEncodeImage(buy_poi.getPoiAddress()));
                    } else {
                        save.setAddress1("");
                    }
                    if (send_poi != null) {
                        save.setAddress2(URLEncodeImage(send_poi.getPoiAddress()));
                    } else {
                        save.setAddress2("");
                    }
                } else {
                    save.setLat2(buy_poi.getLat() + "");
                    save.setLng2(buy_poi.getLng() + "");
                    if (buy_poi != null) {
                        save.setAddress2(URLEncodeImage(buy_poi.getPoiAddress()));
                    } else {
                        save.setAddress2("");
                    }
                    if (send_poi != null) {
                        save.setAddress1(URLEncodeImage(send_poi.getPoiAddress()));
                    } else {
                        save.setAddress1("");
                    }
                }
                save.setTel2(telEt.getText().toString().trim());//必填
                if (noKnowPriceRb.isChecked()) {
                    save.setIsknow("0");//不知
                    save.setFoodfee("0");
                } else {
                    save.setIsknow("1");//知道金额
                    save.setFoodfee(URLEncodeImage(jeEt.getText().toString().trim()));//帮我买的知道金额
                }
                if (feiyong != null) {
                    if (!("0.00").equals(feiyong.getTotalfee())) {
                        save.setSendfee(feiyong.getQibufee());
                        save.setLichengfee(feiyong.getLichengfee());
                        save.setTotalfee(feiyong.getTotalfee());
                        save.setJuli(feiyong.getAlljuli());
                        save.setSource("2");//android设备
                        save.setIsdaishoufee("0");
                        FinalDb db = FinalDb.create(this);
                        if (send_poi != null) {
                            db.save(send_poi);
                        }
                        if (buy_poi != null) {
                            db.save(buy_poi);
                        }
                        HBListener listener = new HBListener(this);
                        listener.getHBList();//检测当前是否有红包
                    } else {
                        ToastShort("距离太远，请重新下单");
                    }
                } else {
                    ToastShort("请重新选择地址");
                }

            }
        });
        noKnowPriceRb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                jeEt.setText("");
            }
        });
        changeAddressIv.setOnClickListener(v -> {//买送位置交换
            firstIsbuy = !firstIsbuy;
            loadUI();
            jiujinBuyRb.setChecked(false);
            if (buy_poi != null && send_poi != null) {
                sl(new LatLonPoint(buy_poi.getLat(), buy_poi.getLng()), new LatLonPoint(send_poi.getLat(), send_poi.getLng()));
            } else {
                priceDetailLl.setVisibility(View.INVISIBLE);
            }
        });
        priceDetailLl.setOnClickListener(v -> {
            if (feiyong != null) {
                Utils.IntentPost(PriceDetailActivity.class, intent -> {
                    intent.putExtra("model", feiyong);
                });
            }
        });
    }

    boolean pay_is_wx = true;

    @Override
    public void getHB(HBModel.DataBean model) {

    }

    /**
     * 弹出dialog
     */
    @Override
    public void getHBList(HBsModel.DataBean model) {
        if (model != null) {
            lq_model = model;
            showDialog(model.get领取金额());
        } else {
            lq_model = null;
            showDialog("0");
        }
    }

    class MyThread extends Thread {
        String Order_Id = "";
        int Order_Price;

        public MyThread(String orderId, String price) {
            Order_Id = orderId;
            Order_Price = (int) (Double.parseDouble(price) * 100);
        }

        public void run() {
            wxUtil.load(AddBuyActivity.this, BaseApplication.getContext().getResources().getString(R.string.app_name), BaseApplication.getContext().getResources().getString(R.string.app_name) + "支付", Order_Id, Order_Price);
        }
    }

    HBsModel.DataBean lq_model;

    private void showDialog(String num) {
        //将布局设置给Dialog
        bottom_dialog.setContentView(inflate);
        Button pay_btn = (Button) inflate.findViewById(R.id.pay_btn);
        TextView pay_tv = (TextView) inflate.findViewById(R.id.pay_tv);
        RelativeLayout wx_pay_rl = (RelativeLayout) inflate.findViewById(R.id.wx_pay_rl);
        RelativeLayout zfb_pay_rl = (RelativeLayout) inflate.findViewById(R.id.zfb_pay_rl);
        CheckBox wx_cb = (CheckBox) inflate.findViewById(R.id.wx_cb);
        CheckBox zfb_cb = (CheckBox) inflate.findViewById(R.id.zfb_cb);
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
        TextView hb_pay_tv = (TextView) inflate.findViewById(R.id.hb_pay_tv);
        RelativeLayout hb_pay_rl = (RelativeLayout) inflate.findViewById(R.id.hb_pay_rl);
        Double price1 = 0.0;
        try {
            price1 = Double.parseDouble(feiyong.getTotalfee()) - Double.parseDouble(lq_model.get领取金额());
            if (price1 <= 0) {
                price1 = 0.01;
            }
        } catch (Exception e) {
            price1 = Double.parseDouble(feiyong.getTotalfee());
        }
        BigDecimal b = new BigDecimal(price1);
        price1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        if (num != "0") {
            hb_pay_tv.setText("红包金额：" + num + "元");
            hb_pay_rl.setVisibility(View.VISIBLE);
            pay_tv.setText(price1 + "元");
        } else {
            pay_tv.setText(price1 + "元");
            hb_pay_rl.setVisibility(View.GONE);
        }
        zfb_pay_rl.setOnClickListener(v -> {
            wx_cb.setChecked(false);
            zfb_cb.setChecked(true);
        });
        pay_btn.setOnClickListener(v -> {
            if (lq_model != null) {
                save.setHbid(lq_model.get编号());
            }
            mListener.saveOrder(save);//生成预订单，调起支付
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

        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        Display display = getWindowManager().getDefaultDisplay();
        lp.width = display.getWidth(); //设置宽度
        lp.y = intheight / 2;//设置Dialog距离底部的距离
        dialogWindow.setAttributes(lp);
        bottom_dialog.show();//显示对话框
    }

    Dialog bottom_dialog;
    SaveOrderModel save;

    int clickItem = 0;

//    private void load() {
//        list = new ArrayList<>();
//        list.add(new TagModel("随意购", "想买什么，就买什么！请填写商品名称和数量", true));
//        list.add(new TagModel("咖啡", "请填写咖啡品类、口味、杯型等具体要求", false));
//        list.add(new TagModel("香烟", "请填写香烟名称型号，包装要求等", false));
//        list.add(new TagModel("酒", "请填写酒类名称、度数等要求", false));
//        list.add(new TagModel("早餐", "请填写餐品名称、份数、是否忌口等要求", false));
//        list.add(new TagModel("宵夜", "请填写餐品名称、份数、是否忌口等要求", false));
//        list.add(new TagModel("药品", "请填写药品名称、厂家等要求", false));
//        list.add(new TagModel("生鲜", "请填写生鲜种类、重量、体积等要求", false));
//    }

    @Override
    public void initEvents() {
        buyAddressLl.setOnClickListener(v -> {
            if (!jiujinBuyRb.isChecked()) {
                Intent intent = new Intent(AddBuyActivity.this, SelectAddressActivity.class);
                if (firstIsbuy) {
                    intent.putExtra("model", buy_poi);
                } else {
                    intent.putExtra("model", send_poi);
                }
                startActivityForResult(intent, 0);
            }
        });
        sendAddressLl.setOnClickListener(v -> {
            Intent intent = new Intent(AddBuyActivity.this, SelectAddressActivity.class);
            if (firstIsbuy) {
                intent.putExtra("model", send_poi);
            } else {
                intent.putExtra("model", buy_poi);
            }
            startActivityForResult(intent, 1);
        });
        loadUI();
        routeSearch = new RouteSearch(this);
        routeSearch.setRouteSearchListener(new RouteSearch.OnRouteSearchListener() {
            @Override
            public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

            }

            @Override
            public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

            }

            @Override
            public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {
                if (i == 1000 && walkRouteResult != null) {
                    if (walkRouteResult.getPaths().size() > 0) {
                        WalkPath path = walkRouteResult.getPaths().get(0);
                        address_length = Double.valueOf(path.getDistance()) / 1000;
                        if (address_length <= 0) {
                            ToastShort("送货距离有问题，请联系业务员~~");
                        } else {
                            totalPriceTv.setText("距离：" + address_length + "公里");
                            if (jiujinBuyRb.isChecked()) {
                                mListener.js(address_length + "", "1");
                            } else {
                                mListener.js(address_length + "", "0");
                            }
                        }
                    }
                } else {
                    dialog.dismiss();
                    priceDetailLl.setVisibility(View.VISIBLE);
                    ToastShort("当前仅支持本市配送");
                    priceTv.setText("当前仅支持本市配送");
                }
            }

            @Override
            public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

            }
        });
        jiujinBuyRb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                totalPriceTv.setText("");
                if (firstIsbuy && send_poi != null) {
                    mListener.js(address_length + "", "1");
                    dialog.show();
                } else if (!firstIsbuy && buy_poi != null) {
                    mListener.js(address_length + "", "1");
                    dialog.show();
                } else {
                    priceDetailLl.setVisibility(View.INVISIBLE);
                }
                buyAddressTv.setText("");//清空输入框
                if (firstIsbuy) {
                    buy_poi = null;
                } else {
                    send_poi = null;
                }
            } else if (address_length == 0) {
                priceDetailLl.setVisibility(View.INVISIBLE);
            } else {
                dialog.show();
                mListener.js(address_length + "", "0");
            }
        });
    }

    /**
     * 购买地址，收货地址切换
     */
    private void loadUI() {
        if (firstIsbuy) {
            if (send_poi != null) {
                if (send_poi.getDetailAddress() != null) {
                    sendAddressTv.setText(send_poi.getPoiName() + "  " + send_poi.getDetailAddress());
                } else {
                    sendAddressTv.setText(send_poi.getPoiName());
                }
            } else {
                sendAddressTv.setText("");
            }
            if (buy_poi != null) {
                if (buy_poi.getDetailAddress() != null) {
                    buyAddressTv.setText(buy_poi.getPoiName() + "  " + buy_poi.getDetailAddress());
                } else {
                    buyAddressTv.setText(buy_poi.getPoiName());
                }
            } else {
                buyAddressTv.setText("");
            }
        } else {
            if (send_poi != null) {
                if (send_poi.getDetailAddress() != null) {
                    buyAddressTv.setText(send_poi.getPoiName() + "  " + send_poi.getDetailAddress());
                } else {
                    buyAddressTv.setText(send_poi.getPoiName());
                }
            } else {
                buyAddressTv.setText("");
            }
            if (buy_poi != null) {
                if (buy_poi.getDetailAddress() != null) {
                    sendAddressTv.setText(buy_poi.getPoiName() + "  " + buy_poi.getDetailAddress());
                } else {
                    sendAddressTv.setText(buy_poi.getPoiName());
                }
            } else {
                sendAddressTv.setText("");
            }
        }
    }

    double address_length = 0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 8) {
            switch (requestCode) {
                case 0://购买地址跳转回来
                    if (firstIsbuy) {
                        buy_poi = (PoiModel) data.getSerializableExtra("val");
                    } else {
                        send_poi = (PoiModel) data.getSerializableExtra("val");
                    }
                    break;
                case 1://送达地址跳转回来
                    if (firstIsbuy) {
                        send_poi = (PoiModel) data.getSerializableExtra("val");
                    } else {
                        buy_poi = (PoiModel) data.getSerializableExtra("val");
                    }
                    break;
            }
            if (jiujinBuyRb.isChecked()) {
                mListener.js(address_length + "", "1");
                dialog.show();
            } else {
                if (buy_poi != null && send_poi != null) {
                    sl(new LatLonPoint(buy_poi.getLat(), buy_poi.getLng()), new LatLonPoint(send_poi.getLat(), send_poi.getLng()));
                }
            }
            loadUI();
        }
        if (requestCode == 0) {
            if (resultCode == 77) {
                telEt.setText(data.getStringExtra("tel"));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void sl(LatLonPoint start, LatLonPoint end) {
        dialog.show();
        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(start, end);
        RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo, RouteSearch.WalkDefault);
        routeSearch.calculateWalkRouteAsyn(query);// 异步路径规划步行模式查询
    }

    FeiModel feiyong;

    @Override
    public void slResult(FeiModel model, String error) {
        feiyong = model;
        priceDetailLl.setVisibility(View.VISIBLE);
        if (error != null) {
            priceTv.setText(error);
        } else {
            if (model != null) {
                priceTv.setText("￥" + model.getTotalfee());
            }
        }
        dialog.dismiss();
    }

    @Override
    public void saveResult(String orderId, String price) {
        bottom_dialog.dismiss();
        Double price1 = 0.0;
        try {
            price1 = Double.parseDouble(price) - Double.parseDouble(lq_model.get领取金额());
            if (price1 <= 0) {
                price1 = 0.01;
            }
        } catch (Exception e) {
            price1 = Double.parseDouble(price);
        }
        BigDecimal b = new BigDecimal(price1);
        price1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        if (orderId != null) {
            if (!pay_is_wx) {
                String orderInfo = getOrderInfo(BaseApplication.getContext().getResources().getString(R.string.app_name),
                        BaseApplication.getContext().getResources().getString(R.string.app_name) + "支付", price1 + "", orderId);
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
                    PayTask alipay = new PayTask(AddBuyActivity.this);
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
                new MyThread(orderId, price1 + "").start();
            }
        } else {

        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(AddBuyActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        /*后期需要实现跳转到订单相信页面，是支付失败还是再来一单*/
                        finish();
                    } else {
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(AddBuyActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddBuyActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
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

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + Utils.getCache("PARTNER") + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + Utils.getCache("SELLER") + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + orderid + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径 http://kuaipao.myejq.com/Alipay/userKuaipaoNotify.aspx
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

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, Utils.getCache("RSA_PRIVATE"));
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

    @Override
    public void manageResult(boolean result) {
        if (result) {
            ToastShort("收藏成功");
        } else {
            ToastShort("收藏失败");
        }
    }
}

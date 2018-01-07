package liuliu.kp.ui;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.tsz.afinal.view.TitleBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import liuliu.kp.R;
import liuliu.kp.base.BaseActivity;
import liuliu.kp.listener.UserListener;
import liuliu.kp.method.Utils;
import liuliu.kp.model.UserModel;
import liuliu.kp.view.IUser;

/**
 * 个人中心
 * Created by Administrator on 2016/12/2.
 */

public class UserActivity extends BaseActivity implements IUser {
    @Bind(R.id.title_bar)
    TitleBar titleBar;
    @Bind(R.id.address_ll)
    LinearLayout addressLl;
    @Bind(R.id.all_order_ll)
    LinearLayout allOrderLl;
    @Bind(R.id.wait_pay_ll)
    LinearLayout waitPayLl;
    @Bind(R.id.wait_jie_ll)
    LinearLayout waitJieLl;
    @Bind(R.id.sending_ll)
    LinearLayout sendingLl;
    @Bind(R.id.user_iv)
    ImageView userIv;
    @Bind(R.id.user_tel_tv)
    TextView userTelTv;
    @Bind(R.id.tel_ll)
    LinearLayout telLl;
    @Bind(R.id.update_pwd_ll)
    LinearLayout updatePwdLl;
    @Bind(R.id.cuowu_ll)
    LinearLayout cuowuLl;
    @Bind(R.id.xz_ll)
    LinearLayout xzLl;
    @Bind(R.id.tk_ll)
    LinearLayout tkLl;
    @Bind(R.id.about_us_ll)
    LinearLayout aboutUsLl;
    @Bind(R.id.exit_btn)
    Button exitBtn;
    @Bind(R.id.user_info_ll)
    LinearLayout userInfoLl;
    UserListener mListener;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        mListener = new UserListener(this);
        userInfoLl.setOnClickListener(v -> {
            if (("").equals(Utils.getCache("UserId"))) {
                Utils.IntentPost(LoginActivity.class);
            }
        });
    }

    @Override
    public void initEvents() {
        titleBar.setLeftClick(() -> finish());
        if (("").equals(Utils.getCache("UserId"))) {
            exitBtn.setVisibility(View.GONE);
        } else {
            exitBtn.setVisibility(View.VISIBLE);
            mListener.getUser();
        }
        addressLl.setOnClickListener(v -> {
            if (("").equals(Utils.getCache("UserId"))) {
                Utils.IntentPost(LoginActivity.class);
            } else {
                Utils.IntentPost(AddressActivity.class);
            }
        });
        allOrderLl.setOnClickListener(v -> {
            if (("").equals(Utils.getCache("UserId"))) {
                Utils.IntentPost(LoginActivity.class);
            } else {
                Utils.IntentPost(OrderListActivity.class, intent -> {
                    intent.putExtra("state", 0);
                });
            }
        });
        waitPayLl.setOnClickListener(v -> {
            if (("").equals(Utils.getCache("UserId"))) {
                Utils.IntentPost(LoginActivity.class);
            } else {
                Utils.IntentPost(OrderListActivity.class, intent -> {
                    intent.putExtra("state", 1);
                });
            }
        });
        waitJieLl.setOnClickListener(v -> {
            if (("").equals(Utils.getCache("UserId"))) {
                Utils.IntentPost(LoginActivity.class);
            } else {
                Utils.IntentPost(OrderListActivity.class, intent -> {
                    intent.putExtra("state", 2);
                });
            }
        });
        sendingLl.setOnClickListener(v -> {
            if (("").equals(Utils.getCache("UserId"))) {
                Utils.IntentPost(LoginActivity.class);
            } else {
                Utils.IntentPost(OrderListActivity.class, intent -> {
                    intent.putExtra("state", 3);
                });
            }
        });
        telLl.setOnClickListener(v -> {//拨打电话
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "04212633019"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        updatePwdLl.setOnClickListener(v -> {//修改密码
            if (!("").equals(Utils.getCache("UserId"))) {
                Utils.IntentPost(FindPwdActivity.class);
            } else {
                Utils.IntentPost(LoginActivity.class);
            }
        });
        aboutUsLl.setOnClickListener(v -> Utils.IntentPost(WebActivity.class, intent -> intent.putExtra("web", "关于我们")));
        xzLl.setOnClickListener(v -> Utils.IntentPost(WebActivity.class, intent -> intent.putExtra("web", "使用须知")));
        tkLl.setOnClickListener(v -> Utils.IntentPost(WebActivity.class, intent -> intent.putExtra("web", "服务条款")));
        cuowuLl.setOnClickListener(v -> Utils.IntentPost(WebActivity.class, intent -> intent.putExtra("web", "常见问题")));
        exitBtn.setOnClickListener(v -> {
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("确定要退出账号吗?");
            builder.setNegativeButton("确定", (dialog1, which) -> {
                finish();
                MainActivity.mInstails.finish();
                Utils.putCache("UserId", "");
            });
            builder.setPositiveButton("取消", null);
            builder.show();
        });
    }

    @Override
    public void setUserInfo(UserModel model) {
        if (model != null) {
            userTelTv.setText(model.getNickname());
            exitBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mListener.getUser();
    }
}

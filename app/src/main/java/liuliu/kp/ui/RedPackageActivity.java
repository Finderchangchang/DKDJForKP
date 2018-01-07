package liuliu.kp.ui;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import liuliu.kp.R;
import liuliu.kp.base.BaseActivity;
import liuliu.kp.listener.HBListener;
import liuliu.kp.method.Utils;
import liuliu.kp.model.HBModel;
import liuliu.kp.model.UserModel;
import liuliu.kp.view.IHB;

public class RedPackageActivity extends Activity {
    TextView money_tv;
    TextView yx_tv;
    ImageView close_iv;
    Button finish_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_package);
        finish_btn = (Button) findViewById(R.id.finish_btn);
        close_iv = (ImageView) findViewById(R.id.close_iv);
        yx_tv = (TextView) findViewById(R.id.yx_tv);
        money_tv = (TextView) findViewById(R.id.money_tv);
        close_iv.setOnClickListener(v -> finish());
        HBModel.DataBean model = (HBModel.DataBean) getIntent().getSerializableExtra("model");
        money_tv.setText(model.get红包金额());
        yx_tv.setText(model.get有效天数());
        finish_btn.setOnClickListener(v -> {
            Utils.IntentPost(AddBuyActivity.class);
            finish();
        });
    }
}

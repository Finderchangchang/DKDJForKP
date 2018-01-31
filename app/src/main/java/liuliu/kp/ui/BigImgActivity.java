package liuliu.kp.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import liuliu.kp.R;
import liuliu.kp.base.BaseApplication;

public class BigImgActivity extends AppCompatActivity {
    Button down_btn;
    String url = "";
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_img);
        url = getIntent().getStringExtra("url");
        down_btn = (Button) findViewById(R.id.down_btn);
        iv = (ImageView) findViewById(R.id.iv);
        down_btn.setOnClickListener(v -> {
            /** 首先默认个文件保存路径 */
            Glide.with(this).load(url).asBitmap().placeholder(R.mipmap.iconb)
                    .listener(new RequestListener() {
                        @Override
                        public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                            saveImage((Bitmap) resource);
                            return false;
                        }

                    })
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
        });
        Glide.with(this)
                .load(url)
                .placeholder(R.mipmap.iconb)
                .into(iv);
    }

    public void saveImage(Bitmap bitmap) {
        // 首先保存图片
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();
        File appDir = new File(file, "Beauty");
        boolean createed = false;
        if (!appDir.exists()) {
            createed = appDir.mkdirs();
        }
        String fileName = getIntent().getStringExtra("shop_id") + ".jpg";
        File currentFile = new File(appDir, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(currentFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 最后通知图库更新
        this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(currentFile.getPath()))));
        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
    }
}

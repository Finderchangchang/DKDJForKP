package liuliu.kp.method;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by Finder丶畅畅 on 2018/1/20 07:51
 * QQ群481606175
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        if (context != null) {
            Glide.with(context).load(path).into(imageView);
        }
    }
}


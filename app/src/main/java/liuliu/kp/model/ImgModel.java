package liuliu.kp.model;

/**
 * Created by Administrator on 2018/1/20.
 */

public class ImgModel {
    public ImgModel(String url, String title) {
        this.url = url;
        this.title = title;
    }

    String url;
    String title;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

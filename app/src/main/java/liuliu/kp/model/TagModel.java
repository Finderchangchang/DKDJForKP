package liuliu.kp.model;

/**
 * Created by Administrator on 2016/11/29.
 */

public class TagModel {
    String tag;
    String val;
    String id;
    boolean click;

    public TagModel(String tag, String id, String val, boolean click) {
        this.tag = tag;
        this.id = id;
        this.val = val;
        this.click = click;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public boolean isClick() {
        return click;
    }

    public void setClick(boolean click) {
        this.click = click;
    }
}

package liuliu.kp.view;

import java.util.List;

import liuliu.kp.model.LatLngModel;
import liuliu.kp.model.VersionModel;

/**
 * Created by Administrator on 2016/11/26.
 */

public interface IMain {
    void getQsLatLng(List<LatLngModel> list);

    void checkUpdate(VersionModel model);
}

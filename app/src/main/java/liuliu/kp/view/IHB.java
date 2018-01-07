package liuliu.kp.view;

import liuliu.kp.model.HBModel;
import liuliu.kp.model.HBsModel;
import liuliu.kp.model.UserModel;

/**
 * Created by Administrator on 2016/12/3.
 */

public interface IHB {
    //检测当前账号是否有红包
    void getHB(HBModel.DataBean model);

    //获得当前可用红包
    void getHBList(HBsModel.DataBean model);
}

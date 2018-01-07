package liuliu.kp.view;

import liuliu.kp.model.OrderListModel;

/**
 * Created by Administrator on 2016/12/2.
 */

public interface IOrder {
    //刷新后返回数据
    void refreshOrder(OrderListModel model);

    //加载更多显示数据
    void loadMoreOrder(OrderListModel model);

    //改变订单状态处理结果
    void changeStateResult(boolean result);
}

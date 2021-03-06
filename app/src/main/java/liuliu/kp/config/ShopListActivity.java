package liuliu.kp.config;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import net.tsz.afinal.view.TitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import liuliu.kp.R;
import liuliu.kp.method.HttpUtil;
import liuliu.kp.method.Utils;
import liuliu.kp.model.GroupModel;
import liuliu.kp.model.PoiModel;
import liuliu.kp.model.ShopModel;
import liuliu.kp.ui.ShopDetailActivity;
import liuliu.kp.ui.ShopsActivity;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShopListActivity extends AppCompatActivity {
    @Bind(R.id.left_listview)
    ListView leftListview;
    @Bind(R.id.pinnedListView)
    PinnedHeaderListView pinnedListView;
    private boolean isScroll = true;
    private LeftListAdapter adapter;
    TitleBar title_bar;
    private String[] leftStr = new String[]{"面食类", "盖饭", "寿司", "烧烤", "酒水", "凉菜", "小吃", "粥", "休闲"};
    private List<GroupModel.DataBean> left_list = new ArrayList();
    private List<List<GroupModel.DataBean.DataListBean>> right_list = new ArrayList();
    private boolean[] flagArray = {true, false, false, false, false, false, false, false, false};
    private String[][] rightStr = new String[][]{{"热干面", "臊子面", "烩面"},
            {"番茄鸡蛋", "红烧排骨", "农家小炒肉"},
            {"芝士", "丑小丫", "金枪鱼"}, {"羊肉串", "烤鸡翅", "烤羊排"}, {"长城干红", "燕京鲜啤", "青岛鲜啤"},
            {"拌粉丝", "大拌菜", "菠菜花生"}, {"小食组", "紫薯"},
            {"小米粥", "大米粥", "南瓜粥", "玉米粥", "紫米粥"}, {"儿童小汽车", "悠悠球", "熊大", " 熊二", "光头强"}
    };
    MainSectionedAdapter sectionedAdapter;
    static ShopListActivity context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        ButterKnife.bind(this);
        pinnedListView = (PinnedHeaderListView) findViewById(R.id.pinnedListView);
        context = this;
        title_bar = (TitleBar) findViewById(R.id.title_bar);
        title_bar.setLeftClick(() -> finish());
        Map<String, String> map = new HashMap<>();
        String cid = Utils.getCache("cid");//18
        HttpUtil.load()
                .getShopFenlei("18")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    if (("1").equals(model.getState())) {
                        left_list = model.getData();
//                        for (int i = 0; i < left_list.size(); i++) {
//                            right_list.add(left_list.get(i).getDataList());
//                        }
                        adapter = new LeftListAdapter(this, left_list, flagArray);
                        leftListview.setAdapter(adapter);
//                        sectionedAdapter = new MainSectionedAdapter(this, left_list, right_list);
//                        sectionedAdapter.setClick((section, position) -> {
//                            now_section = section;
//                            now_position = position;
//                            Intent intent = new Intent(this, ShopsActivity.class);
//                            intent.putExtra("shop_id", right_list.get(section).get(position).getId());
//                            startActivityForResult(intent, 12);
////                            Intent intent = new Intent(this, ShopDetailActivity.class);
////                            intent.putExtra("shop_id", right_list.get(section).get(position).getId());
////                            startActivityForResult(intent, 12);
//                        });
//                        pinnedListView.setAdapter(sectionedAdapter);

//                        pinnedListView.setOnScrollListener(new AbsListView.OnScrollListener() {
//                            @Override
//                            public void onScrollStateChanged(AbsListView arg0, int scrollState) {
//                                switch (scrollState) {
//                                    // 当不滚动时
//                                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
//                                        // 判断滚动到底部
//                                        if (pinnedListView.getLastVisiblePosition() == (pinnedListView.getCount() - 1)) {
//                                            leftListview.setSelection(ListView.FOCUS_DOWN);
//                                        }
//
//                                        // 判断滚动到顶部
//                                        if (pinnedListView.getFirstVisiblePosition() == 0) {
//                                            leftListview.setSelection(0);
//                                        }
//
//                                        break;
//                                }
//                            }
//
//                            int y = 0;
//                            int x = 0;
//                            int z = 0;
//
//                            @Override
//                            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                                if (isScroll) {
//                                    for (int i = 0; i < rightStr.length; i++) {
//                                        if (i == sectionedAdapter.getSectionForPosition(pinnedListView.getFirstVisiblePosition())) {
//                                            flagArray[i] = true;
//                                            x = i;
//                                        } else {
//                                            flagArray[i] = false;
//                                        }
//                                    }
//                                    if (x != y) {
//                                        adapter.notifyDataSetChanged();
//                                        y = x;
//                                        if (y == leftListview.getLastVisiblePosition()) {
////                            z = z + 3;
//                                            leftListview.setSelection(z);
//                                        }
//                                        if (x == leftListview.getFirstVisiblePosition()) {
////                            z = z - 1;
//                                            leftListview.setSelection(z);
//                                        }
//                                        if (firstVisibleItem + visibleItemCount == totalItemCount - 1) {
//                                            leftListview.setSelection(ListView.FOCUS_DOWN);
//                                        }
//                                    }
//                                } else {
//                                    isScroll = true;
//                                }
//                            }
//                        });
                    } else {
                        finish();
                        Toast.makeText(this, "当前无数据", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
                    finish();
                    Toast.makeText(this, "当前无数据", Toast.LENGTH_SHORT).show();
                });
        leftListview.setOnItemClickListener((arg0, view, position, arg3) -> {
            isScroll = false;

            for (int i = 0; i < leftStr.length; i++) {
                if (i == position) {
                    flagArray[i] = true;
                } else {
                    flagArray[i] = false;
                }
            }
            adapter.notifyDataSetChanged();
            int rightSection = 0;
            for (int i = 0; i < position; i++) {
                rightSection += sectionedAdapter.getCountForSection(i) + 1;
            }
            pinnedListView.setSelection(rightSection);

        });


    }

    int now_section = 0;
    int now_position = 0;
    PoiModel poiModel = new PoiModel();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 11) {
//            ShopModel.DataBean key = (ShopModel.DataBean) data.getSerializableExtra("model");
//            if (key != null) {
//                Intent intent = new Intent();
//                poiModel.setLat(Double.parseDouble(key.getLat()));
//                poiModel.setLng(Double.parseDouble(key.getLng()));
//                poiModel.setDetailAddress(key.getAddress());
//                poiModel.setPoiName(key.getName());
//                intent.putExtra("val", poiModel);
//                setResult(8, intent);
//                finish();//关闭当前页面
//            }
            Intent intent = new Intent();
            poiModel = (PoiModel) data.getSerializableExtra("val");
            intent.putExtra("val", poiModel);
            setResult(8, intent);
            finish();//关闭当前页面
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}

package cn.white.ymc.wanandroidmaster.ui.system;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.white.ymc.wanandroidmaster.R;
import cn.white.ymc.wanandroidmaster.base.BaseFragment;
import cn.white.ymc.wanandroidmaster.data.bean.SystemBean;
import cn.white.ymc.wanandroidmaster.ui.system.adapter.SystemAdapter;
import cn.white.ymc.wanandroidmaster.ui.system.systemdetail.SystemDetailActivity;
import cn.white.ymc.wanandroidmaster.util.ConstantUtil;

/**
 * 体系 fragment
 *
 * @packageName: cn.white.ymc.wanandroidmaster.ui.fragment.system
 * @fileName: SystemFragment
 * @date: 2018/7/27  9:10
 * @author: ymc
 * @QQ:745612618
 */

public class SystemFragment extends BaseFragment implements SystemContract.View,
        SystemAdapter.OnItemClickListener {
    @BindView(R.id.rv_system)
    RecyclerView rvSystem;
    @BindView(R.id.normal_view)
    SmartRefreshLayout normalView;

    private List<SystemBean> systemBeanList;
    private SystemPresenter presenter;
    private SystemAdapter madapter;

    public static SystemFragment getInstance() {
        return new SystemFragment();
    }

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_system;
    }

    @Override
    protected void initData() {
        presenter = new SystemPresenter(this);
        systemBeanList = new ArrayList<>();
        madapter = new SystemAdapter(R.layout.item_system,systemBeanList);
        presenter.getSystemList();
        madapter.setOnItemClickListener(this);
        rvSystem.setAdapter(madapter);
    }

    @Override
    protected void initUI() {
        super.initUI();
        showLoading();
        setRefresh();
        rvSystem.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public void getSystemListOk(List<SystemBean> dataBean) {
        systemBeanList = dataBean;
        madapter.replaceData(dataBean);
        showNormal();
    }

    @Override
    public void getSystemListErr(String info) {
        showError(info);
    }

    /**
     * SmartRefreshLayout刷新加载
     */
    private void setRefresh() {
        normalView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.autoRefresh();
                refreshLayout.finishRefresh(1000);
            }
        });
    }

    @Override
    public void reload() {
        showLoading();
        presenter.getSystemList();
    }

    /**
     * 回到顶部
     */
    public void scrollToTop(){
        rvSystem.smoothScrollToPosition(0);
    }

    /**
     * item 点击事件
     * @param adapter
     * @param view
     * @param position
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, view, getString(R.string.web_view));
        Intent intent = new Intent(activity, SystemDetailActivity.class);
        intent.putExtra(ConstantUtil.SYSTEM,madapter.getData().get(position));
        startActivity(intent,options.toBundle());
    }
}

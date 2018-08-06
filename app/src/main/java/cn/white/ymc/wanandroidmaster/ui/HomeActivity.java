package cn.white.ymc.wanandroidmaster.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.white.ymc.wanandroidmaster.R;
import cn.white.ymc.wanandroidmaster.base.BaseActivity;
import cn.white.ymc.wanandroidmaster.ui.fragment.demo.DemoFragment;
import cn.white.ymc.wanandroidmaster.ui.fragment.home.HomeFragment;
import cn.white.ymc.wanandroidmaster.ui.fragment.mine.MineFragment;
import cn.white.ymc.wanandroidmaster.ui.fragment.system.SystemFragment;
import cn.white.ymc.wanandroidmaster.util.BottomNavigationViewHelper;

/**
 * 主界面 activity
 */


public class HomeActivity extends BaseActivity {
    @BindView(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;
    @BindView(R.id.float_button)
    FloatingActionButton floatButton;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    private List<Fragment> fragmentList;
    private int lastIndex;
    private long mExitTime;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    floatButton.setVisibility(View.VISIBLE);
                    selectFragment(0);
                    return true;
                case R.id.navigation_system:
                    floatButton.setVisibility(View.VISIBLE);
                    selectFragment(1);
                    return true;
                case R.id.navigation_demo:
                    floatButton.setVisibility(View.VISIBLE);
                    selectFragment(2);
                    return true;
                case R.id.navigation_mine:
                    floatButton.setVisibility(View.GONE);
                    selectFragment(3);
                    return true;
                default:
                    break;
            }
            return false;
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        initFragment();
        selectFragment(0);
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(toolbarCommon);
    }

    /**
     * 设置默认选中fragment
     * @param index 碎片fragment
     */
    private void selectFragment(int index) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = fragmentList.get(index);
        Fragment lastFragment = fragmentList.get(lastIndex);
        lastIndex = index;
        ft.hide(lastFragment);
        if (!currentFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
            ft.add(R.id.frame_layout, currentFragment);
        }
        ft.show(currentFragment);
        ft.commitAllowingStateLoss();
    }

    @Override
    protected void initView() {
        // 将item 设置为不移动
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /**
     * 初始化碎片
     */
    private void initFragment() {
        fragmentList = new ArrayList<>();
        fragmentList.add(HomeFragment.getInstance());
        fragmentList.add(SystemFragment.getInstance());
        fragmentList.add(DemoFragment.getInstance());
        fragmentList.add(MineFragment.getInstance());
    }


    /**
     * 创建 menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    /**
     * menu 选择器
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.main_menu_hot:

                break;
            case R.id.main_menu_search:

                break;
                default:
                    break;
        }
        return super.onOptionsItemSelected(item);
    }
}
package com.curiosity.mycalendar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.curiosity.mycalendar.config.FieldDefine;
import com.curiosity.mycalendar.fragment.CalendarFragment;
import com.curiosity.mycalendar.fragment.EmptyFragment;
import com.curiosity.mycalendar.main.presenter.IMainPresenter;
import com.curiosity.mycalendar.main.presenter.MainPresenter;
import com.curiosity.mycalendar.main.view.IMainView;
import com.curiosity.mycalendar.page.curriculum.CurriculumFragment;
import com.curiosity.mycalendar.info.LoginActivity;
import com.curiosity.mycalendar.utils.BitmapUtils;
import com.curiosity.mycalendar.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 2016-12-29
 * E-mail : curiooosity.h@gmail.com
 */

public class MainActivity extends AppCompatActivity implements IMainView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navigation)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private MenuItem current_menuitem;
    private ActionBarDrawerToggle mDrawerToggle;
    private View headerView;
    private CircleImageView civ;

    private IMainPresenter mIMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("mytest", "onCreate");

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        mIMainPresenter = new MainPresenter(this, getApplicationContext());
        initDrawer();
        switch2Curriculum();

        checkLogin();
    }

    private void initDrawer() {
        headerView = navigationView.getHeaderView(0);
        initNavigation();
        initHeaderView();
    }

    private void initNavigation() {
        mDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        mDrawerToggle.syncState();
        drawerLayout.addDrawerListener(mDrawerToggle);

        current_menuitem = navigationView.getMenu().findItem(R.id.curriculum_info);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item != current_menuitem) {
                    current_menuitem = item;
                    mIMainPresenter.switchNavigation(item.getItemId());
                    item.setChecked(true);
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private TextView tagName;
    private TextView tagInstitute;
    private TextView tagMajor;
    private TextView tagClass;
    private TextView tag;

    private void initHeaderView() {
        tagName = ButterKnife.findById(headerView, R.id.tag_name);
        tagInstitute = ButterKnife.findById(headerView, R.id.tag_institute);
        tagMajor = ButterKnife.findById(headerView, R.id.tag_major);
        tagClass = ButterKnife.findById(headerView, R.id.tag_class);
        tag = ButterKnife.findById(headerView, R.id.tag);
        setVisibility(View.GONE, tagName, tagInstitute, tagMajor, tagClass);

        civ = (CircleImageView) headerView.findViewById(R.id.tag_image);

        civ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isLogin = mIMainPresenter.getLoginStatus();
                Log.d("mytest", "MainActivity civ.setOnClickListener: " + isLogin);
                if (isLogin) {
                    showLogoutAlert();
                } else {
                    mIMainPresenter.login();
                }
            }
        });
    }

    /**
     * 检查登录状态
     * 如果已经登录，则显示学生信息和课表信息
     */
    private void checkLogin() {
        boolean isLogin = mIMainPresenter.getLoginStatus();
        if (isLogin) {
            mIMainPresenter.getStudentInfo();
        }
    }

    @Override
    public void login() {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, LoginActivity.class);
        startActivityForResult(intent, LOGIN_REQUEST_CODE);
    }

    @Override
    public void logout() {
        tagName.setText("");
        tagInstitute.setText("");
        tagMajor.setText("");
        tagClass.setText("");

        tag.setText(R.string.login);
        civ.setImageResource(R.drawable.background_material);

        setVisibility(View.GONE, tagName, tagInstitute, tagMajor, tagClass);

        ToastUtils.ToastShort(MainActivity.this, R.string.toast_logout);
    }

    /**
     * 弹出退出登录提示
     */
    private void showLogoutAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.logout_alert_title)
                .setMessage(R.string.logout_alert_msg)
                .setPositiveButton(R.string.logout_alert_pos, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mIMainPresenter.logout();
                    }
                })
                .setNegativeButton(R.string.logout_alert_neg, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.show();
    }

    /**
     * 切换 Fragment 面板
     *
     * @param fragment 需要的 Fragment
     */
    private void switchFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void switch2Test() {
        Log.d("mytest", "switch2Test");
        Fragment fragment = new EmptyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(FieldDefine.EMPTY_MSG, "测试");
        fragment.setArguments(bundle);
        switchFragment(fragment);
    }

    @Override
    public void switch2Curriculum() {
        Log.d("mytest", "switch2Curriculum");
        Fragment fragment = new CurriculumFragment();
        Bundle bundle = new Bundle();
        bundle.putString(FieldDefine.EMPTY_MSG, getString(R.string.emtpy_frag_no_curr));
        fragment.setArguments(bundle);
        switchFragment(fragment);
    }

    @Override
    public void switch2Calender() {
        Log.d("mytest", "switch2Calender");
        Fragment fragment = new CalendarFragment();
        Bundle bundle = new Bundle();
        bundle.putString(FieldDefine.EMPTY_MSG, getString(R.string.emtpy_frag_no_cal));
        fragment.setArguments(bundle);
        switchFragment(fragment);
    }

    /**
     * 设置 header 标签
     *
     * @param stuName      姓名
     * @param stuInstitute 学院
     * @param stuMajor     专业
     * @param stuClass     班级
     */
    @Override
    public void setStudentInfo(String stuName, String stuInstitute, String stuMajor, String stuClass) {
        tagName.setText(stuName);
        tagInstitute.setText(stuInstitute);
        tagMajor.setText(stuMajor);
        tagClass.setText(stuClass);

        civ.setImageBitmap(BitmapUtils.getBitmapWithInSample(getResources(), R.drawable.login_success, 8));
        tag.setText(R.string.logout);

        setVisibility(View.VISIBLE, tagName, tagInstitute, tagMajor, tagClass);
    }

    /**
     * 设置 View 的可见性
     *
     * @param visibility View.VISIBLE View.INVISIBLE View.GONE
     * @param views      需要设置的 View
     */
    private void setVisibility(int visibility, View... views) {
        for (View view : views) {
            view.setVisibility(visibility);
        }
    }

    public static final int LOGIN_REQUEST_CODE = 0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case LOGIN_REQUEST_CODE:
                if (resultCode == LoginActivity.LOGIN_SUCCESS_CODE) {
                    mIMainPresenter.getStudentInfo();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.curriculum_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("mytest", "MainActivity: onDestroy");
    }
}

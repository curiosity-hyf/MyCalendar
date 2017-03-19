package com.curiosity.mycalendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.curiosity.mycalendar.config.FieldDefine;
import com.curiosity.mycalendar.fragment.CalendarFragment;
import com.curiosity.mycalendar.fragment.CurriculumFragment;
import com.curiosity.mycalendar.fragment.EmptyFragment;
import com.curiosity.mycalendar.main.presenter.IMainPresenter;
import com.curiosity.mycalendar.main.presenter.MainPresenter;
import com.curiosity.mycalendar.main.view.IMainView;
import com.curiosity.mycalendar.sysinfo.FetchInfoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Description :
 * Author : Curiosity
 * Date : 2016-12-29
 * E-mail : 1184581135qq@gmail.com
 */

public class MainActivity extends AppCompatActivity implements IMainView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navigation)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    TextView username;
    TextView institute;
    TextView major;
    TextView clas;
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

        initDrawer();
        switch2Curriculum();

        mIMainPresenter = new MainPresenter(this);
    }

    private void initDrawer() {
        headerView = navigationView.getHeaderView(0);
        initCirCleImage();
        initNavigation();
    }

    private void initCirCleImage() {
        civ = (CircleImageView) headerView.findViewById(R.id.profile_image);

        civ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIMainPresenter.login();
            }
        });
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

    private void initHeaderView() {
        username = (TextView) headerView.findViewById(R.id.username);
        institute = (TextView) headerView.findViewById(R.id.institute);
        major = (TextView) headerView.findViewById(R.id.major);
        clas = (TextView) headerView.findViewById(R.id.clas);

        username.setVisibility(View.VISIBLE);
        institute.setVisibility(View.VISIBLE);
        major.setVisibility(View.VISIBLE);
        clas.setVisibility(View.VISIBLE);
        headerView.findViewById(R.id.tag).setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.curriculum_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void login() {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, FetchInfoActivity.class);
        startActivityForResult(intent, FieldDefine.LOGIN_REQUEST_CODE);
    }

    /**
     * 切换Fragment面板
     *
     * @param fragment 需要的Fragment
     */
    private void switchFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void switch2Test() {
        Log.d("mytest", "test fragment");
        Fragment fragment = new EmptyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(FieldDefine.EMPTY_MSG, "测试");
        fragment.setArguments(bundle);
        switchFragment(fragment);
    }

    @Override
    public void switch2Curriculum() {
        Log.d("mytest", "R.id.curriculum_info");
        Fragment fragment = new CurriculumFragment();
        Bundle bundle = new Bundle();
        bundle.putString(FieldDefine.EMPTY_MSG, getString(R.string.emtpy_frag_no_curr));
        fragment.setArguments(bundle);
        switchFragment(fragment);
    }

    @Override
    public void switch2Calender() {
        Log.d("mytest", "R.id.calendar_info");
        Fragment fragment = new CalendarFragment();
        Bundle bundle = new Bundle();
        bundle.putString(FieldDefine.EMPTY_MSG, getString(R.string.emtpy_frag_no_cal));
        fragment.setArguments(bundle);
        switchFragment(fragment);
    }
}

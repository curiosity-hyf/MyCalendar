package com.curiosity.mycalendar.sysinfo;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.curiosity.mycalendar.MainActivity;
import com.curiosity.mycalendar.R;
import com.curiosity.mycalendar.sysinfo.fragment.LoginFragment;
import com.curiosity.mycalendar.sysinfo.fragment.YearSelectFragment;
import com.curiosity.mycalendar.sysinfo.presenter.FetchPresenter;
import com.curiosity.mycalendar.sysinfo.presenter.IFetchPresenter;
import com.curiosity.mycalendar.sysinfo.view.IFetchView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 2017-3-13
 * E-mail : curiooosity.h@gmail.com
 */

public class LoginActivity extends AppCompatActivity implements IFetchView, LoginFragment.OnLoadListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private IFetchPresenter mFetchPresenter;
    private LoginFragment mLoginFragment = null;
    private YearSelectFragment mYearSelectFragment = null;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fetch_layout);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        //设置是否有返回箭头
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.action_bar_semester);
        }

        mFetchPresenter = new FetchPresenter(this, this);

        switchInitFragment();

    }

    private void switchInitFragment() {
        mYearSelectFragment = new YearSelectFragment();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, mYearSelectFragment)
                .commit();
    }
    /**
     * 切换Fragment面板
     *
     * @param fragment 需要的Fragment
     */
    private void switchLastFragment(Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.frag_last_in, R.animator.frag_last_out)
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void switchNextFragment(Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.frag_next_in, R.animator.frag_next_out)
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void showNextStep(boolean show) {
        menu.getItem(0).setVisible(show);
    }

    @Override
    public void switchYearFragment(Bundle bundle) {
        if (mYearSelectFragment == null) {
            mYearSelectFragment = new YearSelectFragment();
        }
        if (bundle != null) {
            mYearSelectFragment.setArguments(bundle);
        }
        switchLastFragment(mYearSelectFragment);
    }

    @Override
    public void switchLoginFragment(Bundle bundle) {
        if (mLoginFragment == null) {
            mLoginFragment = new LoginFragment();
        }
        if (bundle != null) {
            mLoginFragment.setArguments(bundle);
        }
        switchNextFragment(mLoginFragment);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.year_select_menu, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.next_step:
                Bundle bundle = mYearSelectFragment.getSelect();
                mFetchPresenter.switchNavigation(1, bundle);
                if(getSupportActionBar()!=null) {
                    getSupportActionBar().setTitle(R.string.action_bar_login);
                }
                break;
            case android.R.id.home:
                boolean res = mFetchPresenter.navigationBack();
                if(getSupportActionBar()!=null) {
                    getSupportActionBar().setTitle(R.string.action_bar_semester);
                }
                if (!res) return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        mFetchPresenter.navigationBack();
//        super.onBackPressed();
    }

    public static final int LOGIN_SUCCESS_CODE = 1;
    public static final int LOGIN_FIALURE_CODE = 2;

    @Override
    public void onSuccess() {
        Intent loginIntent = new Intent();
        loginIntent.setClass(LoginActivity.this, MainActivity.class);
        setResult(LOGIN_SUCCESS_CODE, loginIntent);
        finish();
    }
}

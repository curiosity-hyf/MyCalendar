package com.curiosity.mycalendar.info;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.curiosity.mycalendar.MainActivity;
import com.curiosity.mycalendar.R;
import com.curiosity.mycalendar.info.fragment.LoginFragment;
import com.curiosity.mycalendar.info.fragment.YearSelectFragment;
import com.curiosity.mycalendar.info.presenter.FetchPresenter;
import com.curiosity.mycalendar.info.presenter.IFetchPresenter;
import com.curiosity.mycalendar.info.view.IFetchView;

import java.util.HashMap;
import java.util.Map;

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

    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fetch_layout);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        //设置是否有返回箭头
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.action_bar_login);
        }

        mFetchPresenter = new FetchPresenter(this, this);

        switchInitFragment();
    }

    private void switchInitFragment() {
        mYearSelectFragment = new YearSelectFragment();
        mLoginFragment = new LoginFragment();
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, mLoginFragment)
                .add(R.id.fragment_container, mYearSelectFragment)
                .hide(mYearSelectFragment)
                .hide(mLoginFragment)
                .commit();
//        currentFragment = mYearSelectFragment;
//        currentFragment = mLoginFragment;
        mFetchPresenter.switchNavigation(0, null);
    }

    /**
     * 切换Fragment面板
     *
     * @param fragment 需要的Fragment
     */
    private void switchLastFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.frag_last_in, R.animator.frag_last_out);
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }
        fragmentTransaction
                .show(fragment)
                .commit();
        currentFragment = fragment;
    }

    private void switchNextFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.frag_last_in, R.animator.frag_last_out);
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }
        fragmentTransaction
                .show(fragment)
                .commit();
        currentFragment = fragment;
    }

    @Override
    public void showCompleted(boolean show) {
        menu.getItem(0).setVisible(show);
    }

    @Override
    public void switchYearFragment(Bundle bundle) {
        if (mYearSelectFragment == null) {
            mYearSelectFragment = new YearSelectFragment();
        }
        if (bundle != null) {
            mYearSelectFragment.setWeekMax((HashMap<String, Integer>) bundle.getSerializable("week"));
        }
        switchLastFragment(mYearSelectFragment);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.action_bar_semester);
        }
    }

    @Override
    public void switchLoginFragment(Bundle bundle) {
        if (mLoginFragment == null) {
            mLoginFragment = new LoginFragment();
        }
        if (bundle != null) {
            mLoginFragment.setGradeSemester(bundle);
        }
        switchNextFragment(mLoginFragment);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.action_bar_login);
        }
//        switchLastFragment(mLoginFragment);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("myA", "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.year_select_menu, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.select_completed:

                break;
            case android.R.id.home:
                boolean res = mFetchPresenter.navigationBack();
                if (!res) return true;
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(R.string.action_bar_login);
                }
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

    private void removeFragment() {
        getFragmentManager()
                .beginTransaction()
                .remove(mLoginFragment)
                .remove(mYearSelectFragment)
                .commit();
    }

    @Override
    public void onLoadSuccess(HashMap<String, Integer> curriculumMaxWeek) {
        Bundle b  = new Bundle();
        b.putSerializable("week", curriculumMaxWeek);
        mFetchPresenter.switchNavigation(1, b);
        mYearSelectFragment.saveSelect();
        Intent loginIntent = new Intent();
        loginIntent.setClass(LoginActivity.this, MainActivity.class);
        setResult(LOGIN_SUCCESS_CODE, loginIntent);
        removeFragment();
        finish();
    }
}

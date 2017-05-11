package com.curiosity.mycalendar.page.curriculum;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.curiosity.mycalendar.R;
import com.curiosity.mycalendar.bean.Curriculum;
import com.curiosity.mycalendar.bean.WeekCourses;
import com.curiosity.mycalendar.config.FieldDefine;
import com.curiosity.mycalendar.customview.MenuFAB;
import com.curiosity.mycalendar.customview.WeekIndicator;
import com.curiosity.mycalendar.info.LoginActivity;
import com.curiosity.mycalendar.page.curriculum.presenter.IWeekPresenter;
import com.curiosity.mycalendar.page.curriculum.presenter.WeekPresenter;
import com.curiosity.mycalendar.page.curriculum.view.IWeekView;
import com.curiosity.mycalendar.utils.SQLiteHelper;
import com.curiosity.mycalendar.utils.SharedPreferenceUtil;
import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description :
 * Author : curiosity-hyf
 * Date : 17-5-1
 * E-mail : curiooosity.h@gmail.com
 */

public class CurriculumFragment extends Fragment implements IWeekView {

    public int REQUEST_ACTIVITY_CODE = 1;
    public int REQUEST_SETTING_CODE = 2;
    private IWeekPresenter mPresenter;

    static MenuFAB fab;
    MaterialSheetFab<MenuFAB> materialSheetFab;
    @BindView(R.id.empty_msg)
    TextView empty_msg;
    @BindView(R.id.btn_getCurriculum)
    TextView btn_getCurriculum;

    @BindView(R.id.week_indicator)
    WeekIndicator indicator;
    @BindView(R.id.curriculum_pager)
    ViewPager pager;
    @BindView(R.id.curriculum_ll)
    LinearLayout ll;

    View view;
    View sheetView;
    View overlay;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("mytest", "Curri -- > onCreateView");
        view = inflater.inflate(R.layout.curriculum_layout, container, false);
        ButterKnife.bind(this, view);
        mPresenter = new WeekPresenter(this.getActivity().getApplicationContext(), this);
        return view;
    }

    @OnClick(R.id.btn_getCurriculum)
    public void onGetCurriculum() {
        startActivityForResult(new Intent().setClass(getContext(), LoginActivity.class), REQUEST_ACTIVITY_CODE);
        if (materialSheetFab.isSheetVisible())
            materialSheetFab.hideSheet();
    }

    public static final int REQUEST_RESET = 0;
    public static final int REQUEST_CHANGE = 1;

    @OnClick(R.id.btn_change)
    public void onChangeCurriculum() {
        if (!view.findViewById(R.id.btn_change).isEnabled()) {
            Toast.makeText(this.getActivity().getApplicationContext(), R.string.curriculum_login_msg, Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent();
            intent.putExtra("curriculum", "change");
            intent.setClass(this.getContext(), LoginActivity.class);
            startActivityForResult(intent, REQUEST_CHANGE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_RESET) {

        } else if(requestCode == REQUEST_CHANGE) {
            dataChange();
        }
    }

    private void initFAB() {
        fab = (MenuFAB) view.findViewById(R.id.fab);
        sheetView = view.findViewById(R.id.fab_sheet);
        overlay = view.findViewById(R.id.dim_overlay);
        int sheetColor = getResources().getColor(R.color.white);
        int fabColor = getResources().getColor(R.color.colorAccent);

        // Initialize material sheet FAB
        materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay,
                sheetColor, fabColor);

        materialSheetFab.setEventListener(new MaterialSheetFabEventListener() {
            @Override
            public void onShowSheet() {
                super.onShowSheet();
                if (!SharedPreferenceUtil.getLogin(CurriculumFragment.this.getActivity().getApplicationContext())) {
                    view.findViewById(R.id.btn_change).setEnabled(false);
                } else {
                    view.findViewById(R.id.btn_change).setEnabled(true);
                }
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("mytest", "Curriculum Frag-->onPause");
        if (materialSheetFab.isSheetVisible())
            materialSheetFab.hideSheet();
    }

    private Curriculum curriculum;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("mytest", "Curriculum Frag-->onActivityCreated");

//        Log.d("mytest", year + " " + semester);
//        if (mData != null) {
//            int size = mData.size();
//            Log.d("mytest", "size1 = ------------" + size);
//            for (int i = size - 1; i >= 0; --i) {
//                adapter.deleteData(0);
//            }
//        }
//            initData(account, year, semester);
//            initView();


        Bundle bundle = getArguments();
        String emptyMsg = bundle.getString(FieldDefine.EMPTY_MSG);
        initFAB();
        curriculum = mPresenter.getCurriculum();
        initView();
    }

    // TODO 不应该直接重新初始化
    public void dataChange() {
        empty_msg.setVisibility(View.VISIBLE);
        ll.setVisibility(View.VISIBLE);
        curriculum = mPresenter.getCurriculum();
        initView();
    }

    private List<WeekFragment> list = new ArrayList<>();
    private FragmentPagerAdapter pagerAdapter;

    private void initView() {
        if (curriculum != null && curriculum.getCount() != 0) {
            empty_msg.setVisibility(View.GONE);
            List<String> titles = new ArrayList<>();
            int lastWeek = 0;
            int cur = 1;
            for (int i = 0; i < curriculum.getCount(); ++i) {
                WeekCourses weekCourses = curriculum.getWeekCoursesAt(i);
                if (lastWeek + 1 < weekCourses.getWeekNum()) {
                    for (int j = lastWeek + 1; j < weekCourses.getWeekNum() - 1; ++j) {
                        WeekFragment fragment = new WeekFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("msg", "empty");
                        fragment.setArguments(bundle);
                        list.add(fragment);
                        titles.add(String.format(Locale.CHINESE, "第%d周", cur++));
                    }
                }
                WeekFragment fragment = new WeekFragment();
                Bundle bundle = new Bundle();
                bundle.putString("msg", "data");
                fragment.setArguments(bundle);
                fragment.setData(weekCourses);
                list.add(fragment);
                titles.add(String.format(Locale.CHINESE, "第%d周", cur++));
                lastWeek = weekCourses.getWeekNum();
            }


            indicator.setTabItem(titles);
            pagerAdapter = new FragmentPagerAdapter(getFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
                    return list.get(position);
                }

                @Override
                public int getCount() {
                    return list.size();
                }
            };
            pager.setAdapter(pagerAdapter);

            pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    indicator.scroll(position, positionOffset);
                }

                @Override
                public void onPageSelected(int position) {
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
            int curWeek = SharedPreferenceUtil.getWeekOrder(this.getActivity().getApplicationContext());
            pager.setCurrentItem(curWeek - 1);
            indicator.setCurTab(curWeek - 1);

        } else {
            ll.setVisibility(View.GONE);
        }
    }
}

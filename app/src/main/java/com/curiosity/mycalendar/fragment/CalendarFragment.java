package com.curiosity.mycalendar.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.curiosity.mycalendar.R;
import com.curiosity.mycalendar.config.FieldDefine;
import com.curiosity.mycalendar.customview.MenuFAB;
import com.gordonwong.materialsheetfab.MaterialSheetFab;

import butterknife.ButterKnife;

/**
 * Description :
 * Author : Curiosity
 * Date : 2016-12-25
 * E-mail : curiooosity.h@gmail.com
 */

public class CalendarFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.calendar_list_layout, container, false);
        ButterKnife.bind(this, view);

        Log.d("mytest", "Calendar Frag-->onCreateView");
        return view;
    }

    private void initFAB() {

        MenuFAB fab = (MenuFAB) view.findViewById(R.id.fab);
        View sheetView = view.findViewById(R.id.fab_sheet);
        View overlay = view.findViewById(R.id.dim_overlay);
        int sheetColor = getResources().getColor(R.color.cardview_light_background);
        int fabColor = getResources().getColor(R.color.colorAccent);

        // Initialize material sheet FAB
        MaterialSheetFab<MenuFAB> materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay,
                sheetColor, fabColor);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("mytest", "Calendar Frag-->onStart");
        Bundle bundle = getArguments();
        String emptyMsg = bundle.getString(FieldDefine.EMPTY_MSG);
        Log.d("mytest", emptyMsg);
        initFAB();
    }
}

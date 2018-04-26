package com.alex.witAg.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.alex.witAg.R;

import java.util.ArrayList;

/**
 * Created by dth
 * Des:
 * Date: 2018-03-08.
 */

public class LeftTabView extends FrameLayout{

    private int selectPosition = -1;
    private OnSelectedChangeListener mListener;

    public LeftTabView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public LeftTabView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LeftTabView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    ArrayList<View> views = new ArrayList<>();

    public void initView(Context context) {
        View view = View.inflate(context, R.layout.left_tab_view, this);
        LinearLayout ll_container = (LinearLayout) view.findViewById(R.id.ll_container);

        LinearLayout ll_home = (LinearLayout) view.findViewById(R.id.ll_home);
        LinearLayout ll_data = (LinearLayout) view.findViewById(R.id.ll_data);
        LinearLayout ll_setting = (LinearLayout) view.findViewById(R.id.ll_setting);
        LinearLayout ll_controler = (LinearLayout) view.findViewById(R.id.ll_controler);
        LinearLayout ll_about = (LinearLayout) view.findViewById(R.id.ll_about);

        views.add(ll_home);
        views.add(ll_data);
        views.add(ll_setting);
        views.add(ll_controler);
        views.add(ll_about);

//        setSelectPosition(0);

        for (int i = 0; i < views.size(); i++) {
            View view1 = views.get(i);
            int finalI = i;
            view1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelectPosition(finalI);
                }
            });
        }

    }

    public void setSelectPosition(int position) {
        if(position == selectPosition) return;
        selectPosition = position;

        for (View view : views) {
            view.setBackgroundColor(Color.TRANSPARENT);
        }

        views.get(position).setBackgroundColor(Color.parseColor("#4faa4b"));

        if (mListener != null) {
            mListener.onSelectedPosition(views.get(position),selectPosition);
        }
    }

    public interface OnSelectedChangeListener{
        void onSelectedPosition(View view, int position);
    }

    public void setOnSelectedChangeListener(OnSelectedChangeListener listener) {
        mListener = listener;
    }

    public int getSelectPosition() {
        return selectPosition;
    }
}

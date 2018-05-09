package com.alex.witAg.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex.witAg.AppContants;
import com.alex.witAg.R;
import com.alex.witAg.base.BaseFragment;
import com.alex.witAg.bean.PhotoDetailRecodeBean;
import com.alex.witAg.bean.PicListBean;
import com.alex.witAg.presenter.HomeImgPresenter;
import com.alex.witAg.presenter.viewImpl.IHomeImgView;
import com.alex.witAg.utils.MyAnimUtil;
import com.alex.witAg.utils.TimeUtils;
import com.alex.witAg.utils.ToastUtils;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dth
 * Des:
 * Date: 2018-03-14.
 */

public class HomeImgFragment extends BaseFragment<HomeImgPresenter, IHomeImgView> implements IHomeImgView {
    private String TAG = HomeImgFragment.class.getName();
    @BindView(R.id.sdv_big)
    SimpleDraweeView mSdvBig;
    @BindView(R.id.tv_date)
    TextView         mTvDate;
    @BindView(R.id.tv_home_img_choose_date)
    TextView tvChooseDate;
    @BindView(R.id.ic_left)
    ImageView        mIcLeft;
    @BindView(R.id.img_recyclerView)
    RecyclerView     mImgRecyclerView;
    @BindView(R.id.ic_right)
    ImageView        mIcRight;
    @BindView(R.id.info_recyclerView)
    RecyclerView     mInfoRecyclerView;

    private String picUrl = "";

    private ImageAdapter imageAdapter;
    private InfoAdapter infoAdapter;
    private  TimePickerView pvTime;

    @Override
    protected void fetchData() {

    }

    @Override
    protected void init(View view, @Nullable Bundle savedInstanceState) {
        initRecyclerView();
        initPickerView();
        mTvDate.setText(TimeUtils.millis2String(System.currentTimeMillis(),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
        tvChooseDate.setText(TimeUtils.millis2String(System.currentTimeMillis(),new SimpleDateFormat("yyyy-MM-dd")));
        getPresenter().getHomeImgList(TimeUtils.millis2String(System.currentTimeMillis(),
                    new SimpleDateFormat("yyyy-MM-dd"))+" 00:00:00");
    }

    private void initPickerView() {
        //时间选择器
        pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tvChooseDate.setText(TimeUtils.date2String(date,new SimpleDateFormat("yyyy-MM-dd")));
                String time = TimeUtils.date2String(date,new SimpleDateFormat("yyyy-MM-dd"))+" 00:00:00";
                Log.i(TAG,time);
                getPresenter().getHomeImgList(time);
                pvTime.dismiss();
            }
        })   .setType(new boolean[]{true, true, true, false, false, false})// 显示年月日
                .build();
    }

    private void initRecyclerView() {
        imageAdapter= new ImageAdapter();
        mImgRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        mImgRecyclerView.setAdapter(imageAdapter);

        infoAdapter = new InfoAdapter();
        mInfoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mInfoRecyclerView.setAdapter(infoAdapter);
    }

    @Override
    protected int tellMeLayout() {
        return R.layout.fragment_home_img;
    }

    @Override
    protected HomeImgPresenter initPresenter() {
        return new HomeImgPresenter();
    }

    @Override
    protected void onRetryListener() {

    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }

    @OnClick({R.id.ic_left, R.id.ic_right,R.id.tv_home_img_choose_date,R.id.sdv_big})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_left:
                break;
            case R.id.ic_right:
                break;
            case R.id.tv_home_img_choose_date:
                showTimePicker();
                break;
            case R.id.sdv_big:
                if (!TextUtils.isEmpty(picUrl)){
                    Bundle bundle = new Bundle();
                    bundle.putString(AppContants.SHOW_PIC_URL_KEY,picUrl);
                    ARouter.getInstance().build(AppContants.ARouterUrl.SHOW_PIC)
                            .with(bundle)
                            .navigation();
                }
                break;
        }
    }

    private void showTimePicker() {
        pvTime.show();
    }

    @Override
    public void updatePicture(PicListBean picListBean) {
        imageAdapter.setNewData(picListBean.getList());
        if (picListBean.getList().size()>0) {
            String url = picListBean.getList().get(0).getUrl();
            if (null!=url) {
                mSdvBig.setImageURI(Uri.parse(url));
                picUrl = url;
            }
            getPresenter().getRecode(picListBean.getList().get(0).getId() + "");
        }else {
            ToastUtils.showToast("当前无图片");
        }
        imageAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateRecode(PhotoDetailRecodeBean photoDetailRecodeBean) {
        infoAdapter.setNewData(photoDetailRecodeBean.getRecord());
        infoAdapter.notifyDataSetChanged();
    }

    class ImageAdapter extends BaseQuickAdapter<PicListBean.ListBean, BaseViewHolder> {

        public ImageAdapter() {
            super(R.layout.item_img);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, PicListBean.ListBean listBean) {
            baseViewHolder.setText(R.id.tv_main_pic_item_small,listBean.getName());
            SimpleDraweeView simpleDraweeView = baseViewHolder.getView(R.id.sdv_big);
            if (null!=listBean.getUrl()) {
                simpleDraweeView.setImageURI(Uri.parse(listBean.getUrl()));
            }
            simpleDraweeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    simpleDraweeView.startAnimation(MyAnimUtil.alphHalf2All());
                    if (null!=listBean.getUrl()) {
                        Log.i(TAG,listBean.getUrl());
                        mTvDate.setText(listBean.getName());
                        mSdvBig.setImageURI(Uri.parse(listBean.getUrl()));
                        picUrl = listBean.getUrl();
                        mSdvBig.startAnimation(MyAnimUtil.alph02All());
                        getPresenter().getRecode(listBean.getId()+"");
                    }
                }
            });
        }

    }

    class InfoAdapter extends BaseQuickAdapter<PhotoDetailRecodeBean.RecordBean, BaseViewHolder> {

        public InfoAdapter() {
            super(R.layout.item_info);
        }

        @Override
        protected void convert(BaseViewHolder helper, PhotoDetailRecodeBean.RecordBean recordBean) {
            String detailStr = recordBean.getPestType();
            String type = "", stage = "",sex = "";

            if (!TextUtils.isEmpty(detailStr)){
                String[] details = detailStr.split("-");
                if (details.length>=3){
                    type = details[0];
                    stage = details[1];
                    if (TextUtils.equals(details[2],"1")){
                        sex="雌性";
                    }else if (TextUtils.equals(details[2],"2")){
                        sex = "雄性";
                    }
                }
            }
            helper.setText(R.id.tv_main_info_kind,type); //害虫品种
            helper.setText(R.id.tv_main_info_level,stage);  //生长阶段
            helper.setText(R.id.tv_main_info_sex,sex);   //性别
            helper.setText(R.id.tv_main_info_count,recordBean.getCount()+""); //害虫数量
        }
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter){
            return AnimationUtils.loadAnimation(getActivity(),R.anim.right2left);
        }else {
            return super.onCreateAnimation(transit,enter,nextAnim);
        }
    }

}

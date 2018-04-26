package com.alex.witAg.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alex.witAg.R;
import com.alex.witAg.base.BaseFragment;
import com.alex.witAg.presenter.HomeLinePresenter;
import com.alex.witAg.presenter.viewImpl.IHomeLineView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.angmarch.views.NiceSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

/**
 * Created by dth
 * Des:
 * Date: 2018-03-14.
 */

public class HomeLineFragment extends BaseFragment<HomeLinePresenter, IHomeLineView> implements IHomeLineView {
    @BindView(R.id.lc_chart)
    LineChart mLc_chart;
    @BindView(R.id.sp_char_leader)
    NiceSpinner sp_char_leader;
    @BindView(R.id.sp_char_crops)
    NiceSpinner sp_char_crops;
    @BindView(R.id.sp_char_bug)
    NiceSpinner sp_char_bug;
    @BindView(R.id.sp_char_class)
    NiceSpinner sp_char_class;
    @BindView(R.id.sp_char_filter)
    NiceSpinner sp_char_filter;

    @Override
    protected void fetchData() {

    }

    @Override
    protected void init(View view, @Nullable Bundle savedInstanceState) {
        setChart();
        setSpinner();
    }

    /**
     * 测试数据
     */
    private void setSpinner() {
        List<String> dataset = new LinkedList<>(Arrays.asList("负责人名", "王三三", "李四四"));
        sp_char_leader.attachDataSource(dataset);
        List<String> dataset2 = new LinkedList<>(Arrays.asList("作物名称", "小麦", "水稻", "玉米", "果树"));
        sp_char_crops.attachDataSource(dataset2);
        List<String> dataset3 = new LinkedList<>(Arrays.asList("白背飞虱", "稻飞虱", "玉米螟", "棉铃虫", "麦红蜘蛛", "蝗虫"));
        sp_char_bug.attachDataSource(dataset3);
        List<String> dataset4 = new LinkedList<>(Arrays.asList("雌雄总数量", "雌数量", "雄数量"));
        sp_char_class.attachDataSource(dataset4);
        List<String> dataset5 = new LinkedList<>(Arrays.asList("时间段", "时间点"));
        sp_char_filter.attachDataSource(dataset5);


    }

    private void setChart() {
        mLc_chart.getDescription().setEnabled(false);
        mLc_chart.setTouchEnabled(false);
        mLc_chart.setDragDecelerationFrictionCoef(0.9f);
        mLc_chart.setDragEnabled(true);
        mLc_chart.setScaleEnabled(true);
        mLc_chart.setDrawGridBackground(false);
        mLc_chart.setHighlightPerDragEnabled(true);
        mLc_chart.setDrawBorders(false);
        setData(100, 100);
        mLc_chart.animateX(1000);

        /**设置数据点坐标样式*/
        Legend l = mLc_chart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        l.setPosition(Legend.LegendPosition.LEFT_OF_CHART_INSIDE);
        l.setTextSize(11f);
        l.setTextColor(Color.BLACK);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        /**设置X轴样式*/
        XAxis xAxis = mLc_chart.getXAxis();
        xAxis.setTextSize(11f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            private SimpleDateFormat mFormat = new SimpleDateFormat("MMM-dd");
            @Override
            public String getFormattedValue(float value, AxisBase axisBase) {
                long millis = TimeUnit.HOURS.toMillis((long) value);
                return mFormat.format(new Date(millis));            }
        });

        /**设置Y轴样式*/
        YAxis leftAxis = mLc_chart.getAxisLeft();
        xAxis.setTextSize(11f);
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setAxisMaximum(300f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);
        mLc_chart.getAxisRight().setEnabled(false);

    }


    private void setData(int count, int range) {
        long now = TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis());
        float from = now;
        // count = hours
        float to = now + count;



        ArrayList<Entry> values = new ArrayList<Entry>();

        for (float x = from; x < to; x++) {
            float y = (float) (Math.random() * range) + 50;
            values.add(new Entry(x, y)); // add one entry per hour
        }

        LineDataSet set1;
        if (mLc_chart.getData() != null && mLc_chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mLc_chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
        } else {
            set1 = new LineDataSet(values, "2018");
            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(ColorTemplate.getHoloBlue());
            set1.setCircleColor(ColorTemplate.getHoloBlue());
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);
            LineData data = new LineData(set1);
            data.setValueTextColor(Color.BLACK);
            data.setValueTextSize(9f);

            // set data
            mLc_chart.setData(data);
        }
    }

    @Override
    protected int tellMeLayout() {
        return R.layout.fragment_home_line;
    }

    @Override
    protected HomeLinePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onRetryListener() {

    }

    @Override
    protected View getStatusTargetView() {
        return null;
    }
}

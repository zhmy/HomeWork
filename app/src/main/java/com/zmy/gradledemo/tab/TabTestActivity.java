package com.zmy.gradledemo.tab;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.zmy.gradledemo.R;
import com.zmy.gradledemo.apidemo.DepthPageTransformer;
import com.zmy.gradledemo.apidemo.ZoomOutPageTransformer;

/**
 * Created by zmy on 2016/11/11.
 */

public class TabTestActivity extends AppCompatActivity {

    private String tab1Titles[] = new String[]{"tab1", "tab2", "tab3"};
    private String tab2Titles[] = new String[]{"关注", "推荐", "生活情感", "游戏动漫", "其他"};
    private String tab3Titles[] = new String[]{"tab1", "tab2", "tab3", "tab4", "tab5"};


    private TabLayout tab1, tab2;
//    private  NextTabLayout tab3;
    private  PagerSlidingTabStrip tab3;
    private ViewPager viewPager1, viewPager2, viewPager3;

    private ViewPagerAdapter adapter1, adapter2, adapter3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_test);

        tab1 = (TabLayout) findViewById(R.id.tab1);
        tab2 = (TabLayout) findViewById(R.id.tab2);
        tab3 = (PagerSlidingTabStrip) findViewById(R.id.tab3);

        viewPager1 = (ViewPager) findViewById(R.id.viewpager1);
        viewPager2 = (ViewPager) findViewById(R.id.viewpager2);
        viewPager3 = (ViewPager) findViewById(R.id.viewpager3);

        adapter1 = new ViewPagerAdapter(getSupportFragmentManager(), this, tab1Titles);
        adapter2 = new ViewPagerAdapter(getSupportFragmentManager(), this, tab2Titles);
        adapter3 = new ViewPagerAdapter(getSupportFragmentManager(), this, tab2Titles);

        viewPager1.setAdapter(adapter1);
        viewPager2.setAdapter(adapter2);
        viewPager3.setAdapter(adapter3);

        tab1.setupWithViewPager(viewPager1);
        tab2.setupWithViewPager(viewPager2);

        tab3.setViewPager(viewPager3);

        setTabsValue();
//        tab3.setupWithViewPager(viewPager3);
        viewPager1.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPager2.setPageTransformer(true, new DepthPageTransformer());

    }

    private void setTabsValue() {
        // 设置Tab是自动填充满屏幕的
//        tab3.setShouldExpand(true);

        // 设置Tab的分割线的颜色
//        tab3.setDividerColor(Color.TRANSPARENT);
        // 设置分割线的上下的间距,传入的是dp
//        tab3.setDividerPaddingTopBottom(8);

        // 设置Tab底部线的高度,传入的是dp
//        tab3.setUnderlineHeight(0);
        //设置Tab底部线的颜色
//        tab3.setUnderlineColor(getResources().getColor(R.color.colorAccent));

        // 设置Tab 指示器Indicator的高度,传入的是dp
//        tab3.setIndicatorHeight(4);
        // 设置Tab Indicator的颜色
//        tab3.setIndicatorColor(getResources().getColor(R.color.color_ffd500));

        // 设置Tab标题文字的大小,传入的是dp
//        tab3.setTextSize(14);
        // 设置选中Tab文字的颜色
//        tab3.setSelectedTextColor(getResources().getColor(R.color.color_333333));
        //设置正常Tab文字的颜色
//        tab3.setTextColor(getResources().getColor(R.color.color_666666));

        //  设置点击Tab时的背景色
//        tab3.setTabBackground(R.drawable.background_tab);

        //是否支持动画渐变(颜色渐变和文字大小渐变)
//        tab3.setFadeEnabled(true);
        // 设置最大缩放,是正常状态的0.3倍
//        tab3.setZoomMax(0.2F);
        //设置Tab文字的左右间距,传入的是dp
//        tab3.setTabPaddingLeftRight(10);
    }
}

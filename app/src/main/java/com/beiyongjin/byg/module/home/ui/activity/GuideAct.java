package com.beiyongjin.byg.module.home.ui.activity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.beiyongjin.byg.common.BaseParams;
import com.beiyongjin.byg.common.Constant;
import com.erongdu.wireless.tools.utils.ActivityManage;
import com.erongdu.wireless.tools.utils.ContextHolder;
import com.erongdu.wireless.tools.utils.SPUtil;
import com.github.mzule.activityrouter.annotation.Router;
import com.beiyongjin.byg.R;
import com.beiyongjin.byg.common.AppConfig;
import com.beiyongjin.byg.common.RouterUrl;
import com.beiyongjin.byg.common.ui.BaseActivity;
import com.beiyongjin.byg.module.home.viewModel.ViewPagerAdapter;
import com.github.mzule.activityrouter.router.Routers;

import java.util.ArrayList;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/30 15:06
 * <p>
 * Description: 引导页
 */
@Router(RouterUrl.AppCommon_Guide)
public class GuideAct extends BaseActivity implements OnClickListener, OnPageChangeListener {
    // image容器
    private ViewPager        mViewPager;
    // point容器
  //  private LinearLayout     mSpace;
    // ViewPager适配器
    private ViewPagerAdapter mAdapter;
    // image存放View
    private ArrayList<View>  views;
    // 引导图片资源
    private TypedArray       pics;
    // 底部小点的图片
   // private ArrayList<ImageView>  points;
    // 记录当前选中位置
    private int              currentIndex;
    // 引导页数量
    private int  COUNT = AppConfig.GUIDE_COUNT;
    private Button rightNow;
    private Button jumpout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_layout);
        pics = getResources().obtainTypedArray(R.array.guideImages);
        mViewPager = (ViewPager) findViewById(R.id.guide_viewpager);
        rightNow = (Button) findViewById(R.id.rightnow_register);
        jumpout = (Button) findViewById(R.id.jumpout);


        jumpout.setOnClickListener(onGuideLister);
        rightNow.setOnClickListener(onGuideLister);
        //  mSpace = (LinearLayout) findViewById(R.id.guide_bottom_point);
        initView();
        initData();
    }

    OnClickListener onGuideLister=new OnClickListener() {
        @Override
        public void onClick(View view) {
            SPUtil.saveValue(SPUtil.getSp(ContextHolder.getContext(), BaseParams.SP_NAME), Constant.IS_FIRST_IN, false);
            Routers.open(GuideAct.this, RouterUrl.getRouterUrl(String.format(RouterUrl.AppCommon_Main, Constant.NUMBER_6)));
            finish();
        }
    };

    /**
     * 初始化组件
     */
    private void initView() {
        // 实例化ArrayList对象
        views = new ArrayList<>();
       // points = new ArrayList<>();
        // 实例化ViewPager适配器
        mAdapter = new ViewPagerAdapter(views);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        // 定义一个布局并设置参数
        LayoutParams mParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        // 初始化引导图片列表
        for (int i = 0; i < COUNT; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            // 防止图片不能填满屏幕
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            // 加载图片资源
            iv.setImageDrawable(pics.getDrawable(i));
            views.add(iv);
        }
        // 设置数据
        mViewPager.setAdapter(mAdapter);
        // 设置监听
      //  mViewPager.addOnPageChangeListener(this);
        // 初始化底部小点
       // initPoint();
    }

    /**
     * 初始化底部小点
     */
  /*  private void initPoint() {
        ImageView view;
        for (int i = 0; i < COUNT; i++) {
            view = new ImageView(this);
            LayoutParams layoutParams = new LayoutParams(36, 36);
            view.setPadding(5, 5, 5, 5);
            view.setLayoutParams(layoutParams);
            if (i == 0) {
                view.setImageResource(R.drawable.guide_point_cur);
            } else {
                view.setImageResource(R.drawable.guide_point);
            }
          //  mSpace.addView(view);
           // points.add(view);
        }
    }
*/
    /**
     * 滑动状态改变时调用
     */
    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    /**
     * 当前页面滑动时调用
     */
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    /**
     * 新的页面被选中时调用
     */
    @Override
    public void onPageSelected(int position) {
        // 设置底部小点选中状态
      //  setCurDot(position);
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.guide_viewpager:
               break;
           case R.id.rightnow_register:

               break;
           case R.id.jumpout:

               break;

       }
        int position = (Integer) v.getTag();
        setCurView(position);
      //  setCurDot(position);
    }

    /**
     * 设置当前页面的位置
     */
    private void setCurView(int position) {
        if (position < 0 || position >= COUNT) {
            return;
        }
        mViewPager.setCurrentItem(position);
    }

    /**
     * 设置当前的小点的位置
   /*  *//*
    private void setCurDot(int position) {
        if (position < 0 || position > COUNT - 1 || currentIndex == position) {
            return;
        }
        points.get(currentIndex).setImageResource(R.drawable.guide_point);
        points.get(position).setImageResource(R.drawable.guide_point_cur);
        currentIndex = position;
    }*/

    @Override
    public void onBackPressed() {
        ActivityManage.onExit();
    }
}

package com.beiyongjin.byg.common.binding;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.BaseKeyListener;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;
import com.erongdu.wireless.tools.utils.ConverterUtil;
import com.erongdu.wireless.tools.utils.DensityUtil;
import com.erongdu.wireless.tools.utils.EditTextFormat;
import com.erongdu.wireless.tools.utils.RegularUtil;
import com.erongdu.wireless.views.BadgeView;
import com.erongdu.wireless.views.EditTextWithDrawable;
import com.erongdu.wireless.views.LeftRightLayout;
import com.erongdu.wireless.views.PlaceholderLayout;
import com.erongdu.wireless.views.appbar.ToolBar;
import com.erongdu.wireless.views.recyclerView.DividerLine;
import com.beiyongjin.byg.R;
import com.beiyongjin.byg.common.Constant;
import com.beiyongjin.byg.common.SwipeListener;
import com.beiyongjin.byg.utils.PlaceholderHelper;
import com.beiyongjin.byg.utils.Util;
import com.beiyongjin.byg.views.TasksCompletedView;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/8/17 11:53
 * <p/>
 * Description: 自定义Setters
 */
@SuppressWarnings("unused")
public class BindingAdapters {
    /**
     * 设置view是否显示
     */
    @BindingAdapter({"visibility"})
    public static void viewVisibility(View view, boolean visible) {
        if (visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * 设置view是否显示
     */
    @BindingAdapter({"invisibility"})
    public static void viewInVisibility(View view, boolean visible) {
        if (visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 设置EditText是否可编辑
     */
    @BindingAdapter({"editable"})
    public static void setEditable(EditText view, boolean editable) {
        // 是否可编辑
        if (editable) {
            if (view.getKeyListener() == null) {
                view.setKeyListener(new BaseKeyListener() {
                    @Override
                    public int getInputType() {
                        return 0;
                    }
                });
            }
        } else {
            view.setKeyListener(null);
        }
    }

    /**
     * 为TextView设置错误提示
     */
    @BindingAdapter({"textError"})
    public static void errorToast(TextView view, String error) {
        view.setError(error);
    }

    /**
     * 为RecyclerView添加分割线
     *
     * @param type
     *         水平 - HORIZONTAL = 0;
     *         垂直 - VERTICAL = 1;
     *         十字 - CROSS = 9;
     */
    @BindingAdapter({"addItemDecoration"})
    public static void addItemDecoration(RecyclerView view, int type) {
        DividerLine dividerLine;
        switch (type) {
            case DividerLine.HORIZONTAL:
                dividerLine = new DividerLine(DividerLine.HORIZONTAL);
                dividerLine.setMarginStart(20);
                view.addItemDecoration(dividerLine);
                break;

            case DividerLine.VERTICAL:
                dividerLine = new DividerLine(DividerLine.VERTICAL);
                view.addItemDecoration(dividerLine);
                break;

            case DividerLine.CROSS:
                dividerLine = new DividerLine(DividerLine.CROSS);
                view.addItemDecoration(dividerLine);
                break;

            case Constant.NUMBER_2:
                dividerLine = new DividerLine(DividerLine.HORIZONTAL);
                view.addItemDecoration(dividerLine);
                break;

            case DividerLine.DEFAULT:
            default:
                break;
        }
    }

    /**
     * 设置view的宽高比
     *
     * @param view
     *         view对象
     * @param widthRatio
     *         宽度占比
     * @param aspectRatio
     *         宽高比
     *         app:widthRatio="@{0.60f}"
     *         app:aspectRatio="@{0.67f}"
     */
    @BindingAdapter(value = {"widthRatio", "aspectRatio"}, requireAll = false)
    public static void aspectRatio(View view, float widthRatio, float aspectRatio) {
        // 宽度
        float width;
        // 高度
        float height;
        // view 的 LayoutParams
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        if (widthRatio != 0) {
            if (widthRatio > 1) {
                widthRatio = 1;
            }
            width = DensityUtil.getWidth(Util.getActivity(view)) * widthRatio;
        } else {
            width = DensityUtil.getWidth(Util.getActivity(view));
        }

        if (aspectRatio != 0) {
            height = width * aspectRatio;
        } else {
            height = layoutParams.height;
        }

        layoutParams.width = (int) width;
        layoutParams.height = (int) height;

        view.setLayoutParams(layoutParams);
        view.requestLayout();
    }

    /**
     * 设置 TextView CompoundDrawables 的点击事件
     */
    @BindingAdapter(value = {"leftListener", "topListener", "rightListener", "bottomListener"}, requireAll = false)
    public static void editTextWithDrawable(EditTextWithDrawable view,
                                            EditTextWithDrawable.DrawableLeftListener leftListener,
                                            EditTextWithDrawable.DrawableTopListener topListener,
                                            EditTextWithDrawable.DrawableRightListener rightListener,
                                            EditTextWithDrawable.DrawableBottomListener bottomListener) {
        if (null != leftListener) {
            view.setLeftListener(leftListener);
        }

        if (null != topListener) {
            view.setTopListener(topListener);
        }

        if (null != rightListener) {
            view.setRightListener(rightListener);
        }

        if (null != bottomListener) {
            view.setBottomListener(bottomListener);
        }
    }

    /**
     * View上显示小红点
     * <p/>
     * setTargetView(View)       --> 设置哪个控件显示数字提醒，参数就是一个 view 对象
     * setBadgeCount(int)        --> 设置提醒的数字
     * setBadgeGravity(Gravity)  --> 设置 BadgeView 的显示位置,Gravity.TOP
     * setBackgroundColor()      --> 设置 BadgeView 的背景色，当然还可以设置背景图片
     * setBackgroundResource()   --> 设置背景图片
     * setTypeface()             --> 设置显示的字体
     * setShadowLayer()          --> 设置字体的阴影
     */
    @BindingAdapter(value = {"badgeText", "badgeTextSize"}, requireAll = false)
    public static void badgeView(View view, String text, int textSize) {
        BadgeView badgeView = (BadgeView) view.getTag(R.id.badge_view_tag);
        if (null == badgeView) {
            badgeView = new BadgeView(view.getContext());
            view.setTag(R.id.badge_view_tag, badgeView);
        }
        badgeView.setTargetView(view);
        badgeView.setText(text);
        if (textSize != 0) {
            badgeView.setTextSize(textSize);
        }
    }

    /**
     * 限制EditText只能输入两位小数
     */
    @BindingAdapter(value = {"filterType", "watcher"}, requireAll = false)
    public static void editTextFilter(EditText view, int type, EditTextFormat.EditTextFormatWatcher watcher) {
        switch (type) {
            // 限制EditText只能输入两位小数
            case Constant.NUMBER_0:
                EditTextFormat.addFilter(view, EditTextFormat.getLengthFilter());
                break;

            // 限制EditText不能输入空格
            case Constant.NUMBER_1:
                EditTextFormat.addFilter(view, EditTextFormat.getBlankFilter());
                break;

            // 银行卡卡号输入格式化
            case Constant.NUMBER_2:
                EditTextFormat.bankCardNumAddSpace(view, watcher);
                break;

            // 手机号输入格式化
            case Constant.NUMBER_3:
                EditTextFormat.phoneNumAddSpace(view, watcher);
                break;

            // 身份证号输入格式化
            case Constant.NUMBER_4:
                EditTextFormat.IDCardNumAddSpace(view, watcher);
                break;

            default:
                break;
        }
    }

    /**
     * 配置下拉刷新事件
     */
    @BindingAdapter(value = {"listener"})
    public static void listener(final SwipeToLoadLayout layout, SwipeListener listener) {
        if (listener != null) {
            LayoutInflater mInflater = (LayoutInflater) layout.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // 设置头部、底部加载刷新控件
            layout.setRefreshHeaderView(mInflater.inflate(R.layout.swipe_twitter_header, layout, false));
            layout.setLoadMoreFooterView(mInflater.inflate(R.layout.swipe_twitter_footer, layout, false));
            layout.setSwipeStyle(SwipeToLoadLayout.STYLE.CLASSIC);
            layout.setLoadMoreEnabled(true);
            layout.post(new Runnable() {
                @Override
                public void run() {
                    layout.setRefreshing(true);
                }
            });

            if (layout.getChildCount() >= 2) {
                for (int i = 0; i < layout.getChildCount(); i++) {
                    View tClass = layout.getChildAt(i);
                    if (tClass instanceof RecyclerView) {// 判断滑动布局是否为recyclerView，添加自动加载事件
                        ((RecyclerView) tClass).setOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                                    if (!ViewCompat.canScrollVertically(recyclerView, 2)) {
                                        layout.setLoadingMore(true);
                                    }
                                }
                            }
                        });
                        break;
                    } else if (tClass instanceof ListView) {// 判断滑动布局是否为ListView，添加自动加载事件
                        ((ListView) tClass).setOnScrollListener(new AbsListView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(AbsListView view, int scrollState) {
                                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                                    if (view.getLastVisiblePosition() == view.getCount() - 1 && !ViewCompat.canScrollVertically(view, 1)) {
                                        layout.setLoadingMore(true);
                                    }
                                }
                            }

                            @Override
                            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                            }
                        });
                        break;
                    } else if (tClass instanceof ScrollView) {// 判断滑动布局是否为ScrollView，添加自动加载事件
                        final ScrollView scrollView = ((ScrollView) tClass);
                        layout.setLoadMoreEnabled(false);
                        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                            @Override
                            public void onScrollChanged() {
                                if (scrollView.getChildAt(0).getHeight() < scrollView.getScrollY() + scrollView.getHeight() && !ViewCompat
                                        .canScrollVertically(scrollView, 1)) {
                                    layout.setLoadingMore(true);
                                }
                            }
                        });
                        break;
                    }
                }
            }

            // 添加响应事件方法
            layout.setOnLoadMoreListener(listener);
            layout.setOnRefreshListener(listener);
            // 初始化刷新方法类
            listener.swipeInit(layout);
        }
    }

    /**
     * 为RecyclerView设置adapter
     */
    @BindingAdapter({"recyclerAdapter"})
    public static void recyclerViewAdapter(RecyclerView view, RecyclerView.Adapter adapter) {
        if (null == view.getAdapter()) {
            view.setAdapter(adapter);
        } else {
            view.swapAdapter(adapter, true);
        }
    }

    @BindingAdapter({"loadHtmlData"})
    public static void loadHtmlData(WebView webView, String data) {
        if (TextUtils.isEmpty(data)) {
            return;
        }
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        // webView.loadData(data, "text/html; charset=UTF-8", null);
        webView.loadDataWithBaseURL(null, data, "text/html", "UTF-8", null);
    }

    @BindingAdapter({"titleText"})
    public static void titleText(ToolBar toolBar, String title) {
        if (null == title) {
            title = "";
        }
        toolBar.setTitle(title);
    }

    @BindingAdapter({"leftTxt"})
    public static void leftText(LeftRightLayout view, String leftTxt) {
        if (null == leftTxt) {
            leftTxt = "";
        }
        view.setLeftText(leftTxt);
    }

    @BindingAdapter({"rightTxt"})
    public static void rightTxt(LeftRightLayout view, String rightTxt) {
        if (null == rightTxt) {
            rightTxt = "";
        }
        view.setRightText(rightTxt);
    }

    @BindingAdapter({"android:digits"})
    public static void digits(EditText editText, String digits) {
        editText.setKeyListener(DigitsKeyListener.getInstance(digits));
    }

    /**
     * 为ImageView设置图片
     *
     * @param imageView
     *         imageView
     * @param path
     *         路径
     * @param defaultImage
     *         默认图片
     * @param errorImage
     *         加载失败图片
     */
    @BindingAdapter(value = {"src", "defaultImage", "errorImage"}, requireAll = false)
    public static void setImage(final ImageView imageView, String path, Drawable defaultImage, Drawable errorImage) {
        Context context = imageView.getRootView().getContext();
        try {
            System.gc();
            if (null == errorImage) {
                errorImage = ContextCompat.getDrawable(context, R.drawable.default_picture);
            }
            if (TextUtils.isEmpty(path)) {
                if (defaultImage != null) {
                    imageView.setImageDrawable(defaultImage);
                } else {
                    imageView.setImageDrawable(errorImage);
                }
            } else {
                if (RegularUtil.isNumeric(path)) {
                    if (null == defaultImage) {
                        Glide.with(context).load(ConverterUtil.getInteger(path)).thumbnail(0.1f).error(errorImage).into(imageView);
                    } else {
                        Glide.with(context).load(ConverterUtil.getInteger(path)).thumbnail(0.1f).placeholder(defaultImage).error(errorImage).into(imageView);
                    }
                } else {
                    if (null == defaultImage) {
                        Glide.with(context).load(path).thumbnail(0.1f).error(errorImage).into(imageView);
                    } else {
                        Glide.with(context).load(path).thumbnail(0.1f).placeholder(defaultImage).error(errorImage).into(imageView);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 空态图
     */
    @BindingAdapter(value = {"placeholderState", "placeholderListener"}, requireAll = false)
    public static void placeholderConfig(PlaceholderLayout layout, int state, PlaceholderLayout.OnReloadListener listener) {
        PlaceholderHelper.getInstance().setStatus(layout, state);
        if (null != listener) {
            layout.setOnReloadListener(listener);
        }
    }

    @BindingAdapter(value = "enable")
    public static void setEnable(TextView textView, boolean enable) {
        if (enable) {
            textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.app_color_principal));
            textView.setBackgroundResource(R.drawable.stroke_fillet_transparent_principal);
        } else {
            textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.text_dark));
            textView.setBackgroundResource(R.drawable.stroke_fillet_transparent_grey);
        }
    }

    @BindingAdapter(value = {"taskViewText", "taskViewProgress"}, requireAll = false)
    public static void setTaskViewValue(TasksCompletedView taskView, String text, double progress) {
        if (null != text) {
            taskView.setText(text);
        }
        taskView.setProgress(progress);
    }

}

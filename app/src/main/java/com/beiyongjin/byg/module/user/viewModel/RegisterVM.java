package com.beiyongjin.byg.module.user.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.erongdu.wireless.friday.Friday;
import com.erongdu.wireless.tools.utils.ContextHolder;
import com.erongdu.wireless.tools.utils.RegularUtil;
import com.github.mzule.activityrouter.router.Routers;
import com.beiyongjin.byg.BR;
import com.beiyongjin.byg.R;
import com.beiyongjin.byg.common.CommonType;
import com.beiyongjin.byg.common.RouterUrl;
import com.beiyongjin.byg.common.ViewClick;
import com.beiyongjin.byg.databinding.ItemProtocolBinding;
import com.beiyongjin.byg.module.mine.dataModel.recive.CommonRec;
import com.beiyongjin.byg.module.user.viewControl.RegisterCtrl;
import com.beiyongjin.byg.utils.FridayConstant;
import com.beiyongjin.byg.utils.InputCheck;

import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/11/17 14:03
 * <p/>
 * Description:注册页面模型({@link RegisterCtrl})
 */
public class RegisterVM extends BaseObservable {
    /** 手机号 */
    private String   phone;
    /** 验证码 */
    private String   code;
    /** 登录密码 */
    private String   pwd;
    /** 邀请人 */
    private String   invite;
    /** 获取验证码按钮是否可用 */
    private boolean  codeEnable;
    /** 按钮是否可用 */
    private boolean  enable;
    /** 图形验证码 */
    private Drawable drawable;
    /** 是否选中协议 */
    private boolean check = true;
    /** 协议空间 */
    private LinearLayout    protocolLayout;
    private Context         context;
    /** 协议列表 */
    private List<CommonRec> protocolList;
    /** 代码框是否选中 */
    private boolean         codeSel;
    private Drawable        codeSelDraw;
    /** 密码框是否选中 */
    private boolean         pwdSel;
    private Drawable        pwdSelDraw;
    /** 邀请人框是否选中 */
    private boolean         inviteSel;
    private Drawable        inviteSelDraw;

    public void setProtocolLayout(LinearLayout protocolLayout) {
        this.protocolLayout = protocolLayout;
        context = protocolLayout.getRootView().getContext();
    }

    public void setProtocolList(List<CommonRec> protocolList) {
        this.protocolList = protocolList;
        System.out.println("protocolList.size()" + protocolList.size());
        addProtocol(protocolList);
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        checkInput();
        codeEnableCheck();
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
        checkInput();
        notifyPropertyChanged(BR.code);
    }

    @Bindable
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
        checkInput();
        notifyPropertyChanged(BR.pwd);
    }

    @Bindable
    public String getInvite() {
        return invite;
    }

    public void setInvite(String invite) {
        this.invite = invite;
        notifyPropertyChanged(BR.invite);
    }

    @Bindable
    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
        notifyPropertyChanged(BR.drawable);
    }

    @Bindable
    public boolean isCodeEnable() {
        return codeEnable;
    }

    public void setCodeEnable(boolean codeEnable) {
        this.codeEnable = codeEnable;
        notifyPropertyChanged(BR.codeEnable);
    }

    @Bindable
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
        notifyPropertyChanged(BR.enable);
    }

    /**
     * TimeButton是否可用
     */
    private void codeEnableCheck() {
//        if (RegularUtil.isPhone(phone)) {
//            setCodeEnable(true);
//        } else {
//            setCodeEnable(false);
//        }
        if (RegularUtil.isPhoneLength(phone)) {
            setCodeEnable(true);
        } else {
            setCodeEnable(false);
        }
    }

    @Bindable
    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
        checkInput();
        notifyPropertyChanged(BR.check);
    }

    /**
     * 将还款类型TextView 加入布局 适配屏幕宽度
     */
    private void addProtocol(List<CommonRec> list) {
        LinearLayout linearLayout = createLinearLayout();
        int          width        = getLayoutWidth();
        for (int i = 0; i < list.size(); i++) {//根据控件大小,添加TextView
            System.out.println("name" + list.get(i).getName());
            String name = list.get(i).getName();
            ItemProtocolBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_protocol, null,
                    false);

            itemBinding.setItem("《" + name + "》");//设置文字

            //绑定各个还款方式点击事件
            ViewClick viewClick = new ViewClick() {
                @Override
                public void onClick(View view) {
                    Friday.onEvent(view.getContext(), FridayConstant.REGISTER_PROTOCOL);
                    CommonRec rec = (CommonRec) getObject();
                    //Routers.open(view.getRootView().getContext(), )
                    Routers.open(view.getRootView().getContext(), RouterUrl.getRouterUrl(String.format(RouterUrl.AppCommon_WebView, rec.getName(), CommonType
                            .getUrl(rec.getValue()), "")));
                }
            };
            viewClick.setObject(list.get(i));
            itemBinding.getRoot().setOnClickListener(viewClick);
            itemBinding.executePendingBindings();// 加载布局,为计算组件宽高

            int itemWidth = getViewWidth(itemBinding.getRoot());
            if (width - itemWidth < 0) {// 若超出大小,则新建
                width = getLayoutWidth();
                protocolLayout.addView(linearLayout);
                linearLayout = createLinearLayout();
            }
            width = width - itemWidth;
            linearLayout.addView(itemBinding.getRoot());
            //repayViews.add(itemBinding);
            System.out.println("width" + width);
        }

        protocolLayout.addView(linearLayout);
    }

    /**
     * 创建 LinearLayout
     */
    private LinearLayout createLinearLayout() {
        LinearLayout              linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //        layoutParams.bottomMargin = (int) protocolLayout.getRootView().getContext().getResources().getDimension(R.dimen.x20);
        linearLayout.setLayoutParams(layoutParams);
        return linearLayout;
    }

    /**
     * 计算View 宽度
     */
    private int getViewWidth(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredWidth();
    }

    /**
     * 计算Layout宽度
     */
    private int getLayoutWidth() {
        WindowManager wm    = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int           width = wm.getDefaultDisplay().getWidth();
        return width - 2 * (int) protocolLayout.getRootView().getContext().getResources().getDimension(R.dimen.x20);
    }

    /**
     * 输入校验
     */
    private void checkInput() {
//        if (RegularUtil.isPhone(phone) && checkPwd(pwd) && InputCheck.checkCode(code) && isCheck()) {
//            setEnable(true);
//        } else {
//            setEnable(false);
//        }
        if (RegularUtil.isPhoneLength(phone) && checkPwd(pwd) && InputCheck.checkCode(code) && isCheck()) {
            setEnable(true);
        } else {
            setEnable(false);
        }
    }

    private boolean checkPwd(String pwd) {
        if (TextUtils.isEmpty(pwd))
            return false;
        if (pwd.length() >= 6 && pwd.length() <= 16) {
            return true;
        } else {
            return false;
        }
    }

    public void setCodeSel(boolean codeSel) {
        this.codeSel = codeSel;
        notifyPropertyChanged(BR.codeSelDraw);
    }

    public void setPwdSel(boolean pwdSel) {
        this.pwdSel = pwdSel;
        notifyPropertyChanged(BR.pwdSelDraw);
    }

    public void setInviteSel(boolean inviteSel) {
        this.inviteSel = inviteSel;
        notifyPropertyChanged(BR.inviteSelDraw);
    }

    @Bindable
    public Drawable getCodeSelDraw() {
        if (codeSel) {
            return ContextHolder.getContext().getResources().getDrawable(R.drawable.icon_security_sel);
        } else {
            return ContextHolder.getContext().getResources().getDrawable(R.drawable.icon_security);
        }
    }

    @Bindable
    public Drawable getPwdSelDraw() {
        if (pwdSel) {
            return ContextHolder.getContext().getResources().getDrawable(R.drawable.jojo_mine_register_locak);
        } else {
            return ContextHolder.getContext().getResources().getDrawable(R.drawable.jojo_password_lock);
        }
    }

    @Bindable
    public Drawable getInviteSelDraw() {
        if (inviteSel) {
            return ContextHolder.getContext().getResources().getDrawable(R.drawable.icon_invite_sel);
        } else {
            return ContextHolder.getContext().getResources().getDrawable(R.drawable.icon_invent);
        }
    }
}

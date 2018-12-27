package com.beiyongjin.byg.module.home.viewControl;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.amap.api.location.AMapLocation;
import com.erongdu.wireless.friday.Friday;
import com.erongdu.wireless.info.SharedInfo;
import com.erongdu.wireless.network.entity.HttpResult;
import com.erongdu.wireless.network.entity.ListData;
import com.erongdu.wireless.tools.utils.ActivityManage;
import com.erongdu.wireless.tools.utils.ContextHolder;
import com.erongdu.wireless.tools.utils.ConverterUtil;
import com.erongdu.wireless.tools.utils.PermissionCheck;
import com.erongdu.wireless.tools.utils.StringFormat;
import com.erongdu.wireless.tools.utils.TextUtil;
import com.erongdu.wireless.tools.utils.ToastUtil;
import com.github.mzule.activityrouter.router.Routers;
import com.beiyongjin.byg.MyApplication;
import com.beiyongjin.byg.R;
import com.beiyongjin.byg.common.AppConfig;
import com.beiyongjin.byg.common.CommonType;
import com.beiyongjin.byg.common.Constant;
import com.beiyongjin.byg.common.DialogUtils;
import com.beiyongjin.byg.common.RequestResultCode;
import com.beiyongjin.byg.common.RouterUrl;
import com.beiyongjin.byg.common.ViewClick;
import com.beiyongjin.byg.databinding.HomeLoanActBinding;
import com.beiyongjin.byg.databinding.ItemProtocolBinding;
import com.beiyongjin.byg.module.home.dataModel.LoanSub;
import com.beiyongjin.byg.module.home.ui.activity.LoanAct;
import com.beiyongjin.byg.module.mine.dataModel.recive.CommonRec;
import com.beiyongjin.byg.network.NetworkUtil;
import com.beiyongjin.byg.network.RDClient;
import com.beiyongjin.byg.network.RequestCallBack;
import com.beiyongjin.byg.network.api.CommonService;
import com.beiyongjin.byg.network.api.LoanService;
import com.beiyongjin.byg.utils.FridayConstant;
import com.beiyongjin.byg.utils.Util;
import com.beiyongjin.byg.utils.statistics.DeviceInfoUtils;
import com.beiyongjin.byg.views.InputPwdPopView;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.OnSweetClickListener;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: JinFu
 * E-mail: jf@erongdu.com
 * Date: 2017/2/14 15:58
 * <p/>
 * Description: {@link LoanAct}
 */
public class LoanCtrl {
    private final String extToken;
    public  ObservableField<String> loanMoney     = new ObservableField<>();
    public  ObservableField<String> loanLimit     = new ObservableField<>();
    public  ObservableField<String> repayTime     = new ObservableField<>();
    public  ObservableField<String> repayTimeTips = new ObservableField<>();
    public  ObservableField<String> realMoney     = new ObservableField<>();
    public  ObservableField<String> repayMoney    = new ObservableField<>();
    public  ObservableField<String> fee           = new ObservableField<>();
    public  ObservableField<String> cardName      = new ObservableField<>();
    public  ObservableField<String> cardNo        = new ObservableField<>();
    private String                  cardId        = "";
    public InputPwdPopView popView;
    public  ObservableField<Boolean>   checked    = new ObservableField<>(true);
    public  ObservableField<String>    protocol   = new ObservableField<>();
    public  ObservableField<String>    address    = new ObservableField<>();
    public  ObservableField<String>    coordinate = new ObservableField<>();
    public  ObservableField<CommonRec> rec        = new ObservableField<>();
    public  HomeLoanActBinding binding;
    private Handler                    handler    = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                DialogUtils.showDialog(ActivityManage.peek(), ContextHolder.getContext().getString(R.string.credit_reset), ContextHolder.getContext()
                        .getString
                                (R.string.cancel), ContextHolder.getContext().getString(R.string.credit_center_reset_tip), new OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        pwd.setText("");
                        sweetAlertDialog.dismiss();
                        if (AppConfig.CREDIT_CENTER_INDEX_NUM == Constant.NUMBER_3) {
                            Routers.open(apply.getContext(), RouterUrl.getRouterUrl(String.format(RouterUrl.Mine_CreditThreeCenter,Constant.STATUS_1)));
                        } else if (AppConfig.CREDIT_CENTER_INDEX_NUM == Constant.NUMBER_2) {
                            Routers.open(apply.getContext(), RouterUrl.getRouterUrl(RouterUrl.Mine_CreditTwoCenter));
                        } else {
                            Routers.open(apply.getContext(), RouterUrl.getRouterUrl(RouterUrl.Mine_CreditCenter));
                        }
                    }
                }, new OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        pwd.setText("");
                        sweetAlertDialog.dismiss();
                    }
                });
            } else if (msg.what == 0) {
                DialogUtils.showDialog(ActivityManage.peek(), ContextHolder.getContext().getString(R.string.dialog_confirm), ContextHolder.getContext()
                        .getString
                                (R.string.loan_find_pwd), ContextHolder.getContext().getString(R.string.loan_pwd_error), new OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        pwd.setText("");
                        sweetAlertDialog.dismiss();
                        Routers.open(ContextHolder.getContext(), RouterUrl.getRouterUrl(String.format(RouterUrl.UserInfoManage_ForgotPayPwd, Constant
                                .STATUS_1)));
                    }
                }, new OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        pwd.setText("");
                        confirmApplyClick(apply);
                        sweetAlertDialog.dismiss();
                    }
                });
            }
        }
    };
    private EditText pwd;
    private View     apply;

    public LoanCtrl(HomeLoanActBinding binding,View apply, String loanMoney, String loanLimit, String realMoney, String fee, String cardName, String cardNo, String cardId,String extToken) {
       this.binding=binding;
        this.apply = apply;
        this.loanMoney.set(loanMoney);
        double repay = (ConverterUtil.getDouble(realMoney) + ConverterUtil.getDouble(fee)) / 1.0;
        System.out.println("repay" + repay);
        System.out.println("repay" + (ConverterUtil.getDouble(realMoney) + ConverterUtil.getDouble(fee)));
        /*if (repay == (ConverterUtil.getDouble(realMoney) + ConverterUtil.getDouble(fee))) {
            this.repayMoney.set(String.valueOf(ConverterUtil.getInteger(String.valueOf(repay))));
        } else {
            this.repayMoney.set(StringFormat.doubleFormat(repay));
        }*/
        this.repayMoney.set(StringFormat.twoDecimalFormat(repay));
        int limit = ConverterUtil.getInteger(loanLimit);
        if (limit <= 1) {
            this.repayTime.set(ContextHolder.getContext().getString(R.string.today));
            repayTimeTips.set(ContextHolder.getContext().getString(R.string.loan_tip_2));
        } else {
            this.repayTime.set(String.valueOf(limit - 1));
            repayTimeTips.set(ContextHolder.getContext().getString(R.string.loan_tip_3));
        }
        this.loanLimit.set(loanLimit);
        this.realMoney.set(realMoney);
        this.fee.set(fee);
        this.cardName.set(cardName);
        this.cardNo.set(StringFormat.bankcardFormat(cardNo));
        this.cardId = cardId;
        this.extToken=extToken;
        Log.i("hjc","token:::"+ this.extToken);
        initListener();
        req_data();
    }

    private void initListener() {
        popView = new InputPwdPopView(ContextHolder.getContext(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwd.setText("");
                Routers.open(ContextHolder.getContext(), RouterUrl.getRouterUrl(String.format(RouterUrl.UserInfoManage_ForgotPayPwd, Constant.STATUS_1)));
                popView.dismiss();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popView.dismiss();
            }
        }, new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtil.isEmpty(s.toString()) && s.toString().length() == 6) {
                    requestLoan(s.toString());
                }
            }
        });
        popView.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                pwd.setText("");
            }
        });
        pwd = (EditText) popView.getContentView().findViewById(R.id.pwd);
    }

    public void requestLoan(String pwdStr) {
        LoanSub loanSub = new LoanSub(loanMoney.get(), cardId, fee.get(), realMoney.get(),
                loanLimit.get(), pwdStr.toString(), address.get(), coordinate.get(),  DeviceInfoUtils.getIP(ActivityManage.peek()),extToken);
        Call<HttpResult> call = RDClient.getService(LoanService.class).getLoanApply(loanSub);
        Log.i("hjc","token:::"+loanSub.toString());
        NetworkUtil.showCutscenes(call);

        popView.dismiss();
        pwd.setText("");
        call.enqueue(new RequestCallBack<HttpResult>() {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                if (response.body().getCode() == 401) {
                    //popView.dismiss();
                    pwd.setText("");
                    handler.sendEmptyMessage(0);
                }else if (response.body().getCode() == 402) {
                    //popView.dismiss();
                    pwd.setText("");
                    handler.sendEmptyMessage(1);
                } else {
                    Log.i("hjc","LoanCtrl");
                    ToastUtil.toast(response.body().getMsg());
                    //popView.dismiss();
                    ActivityManage.peek().setResult(Activity.RESULT_OK);
                    ActivityManage.pop();
                }
                if (popView.isShowing()) {
                    popView.dismiss();
                }
            }
        });
    }

    private void req_data() {
        Call<HttpResult<ListData<CommonRec>>> call = RDClient.getService(CommonService.class).protocolList();
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<HttpResult<ListData<CommonRec>>>() {
            @Override
            public void onSuccess(Call<HttpResult<ListData<CommonRec>>> call, Response<HttpResult<ListData<CommonRec>>> response) {
                convertData(response.body().getData().getList());
            }
        });
    }

    private void convertData(List<CommonRec> list) {
        List<CommonRec> temp = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getCode().indexOf("protocol_borrow") != -1) {
                    temp.add(list.get(i));
                }
            }
        }
        addProtocol(temp);
    }

    /** 借款协议 */
    public void protocolClick(View view) {
        if (rec.get() != null) {
            Routers.open(view.getContext(), RouterUrl.getRouterUrl(String.format(RouterUrl.AppCommon_WebView, rec.get().getName(), CommonType.getUrl(rec.get()
                    .getValue()), "")));
        }
    }

    /** 确认申请 */
    public void confirmApplyClick(final View view) {
        Friday.onEvent(view.getContext(), FridayConstant.LOAN_APPLY_SUBMIT);
        if (TextUtil.isEmpty(address.get()) || TextUtil.isEmpty(coordinate.get())) {
            NetworkUtil.showCutscenes("", "");
            MyApplication.openGps(new MyApplication.OnPosChanged() {
                @Override
                public void changed(AMapLocation location) {
                    address.set(location.getAddress());
                    coordinate.set(location.getLongitude() + "," + location.getLatitude());
                    NetworkUtil.dismissCutscenes();
                }
            }, true);
            return;
        }
        if (PermissionCheck.getInstance().hasAlwaysDeniedPermission(Util.getActivity(view), new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION})) {
            PermissionCheck.getInstance().askForPermissions(Util.getActivity(view), new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, PermissionCheck.REQUEST_CODE_ALL);
            return;
        }
        // 判断是否已经设置交易密码，是否进入设置界面
        int payseting = (int) SharedInfo.getInstance().getValue(Constant.IS_PAY_SETTING, 0);
        if (payseting == 1) {
            Routers.openForResult(Util.getActivity(view), RouterUrl.getRouterUrl(String.format(RouterUrl.Mine_Settings_Pay_Pwd, Constant.NUMBER_0)),
                    RequestResultCode.REQ_PAY_SETTING_PWD);
        } else {
            popView.showAtLocation(view, Gravity.CENTER, 0, -100);
            Util.showKeyboard(view.getContext());
        }
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
            ItemProtocolBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(Util.getActivity(binding.getRoot())), R.layout.item_protocol, null,
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
                binding.protocol.addView(linearLayout);
                linearLayout = createLinearLayout();
            }
            width = width - itemWidth;
            linearLayout.addView(itemBinding.getRoot());
            //repayViews.add(itemBinding);
            System.out.println("width" + width);
        }

        binding.protocol.addView(linearLayout);
    }
    /**
     * 创建 LinearLayout
     */
    private LinearLayout createLinearLayout() {
        LinearLayout              linearLayout = new LinearLayout(Util.getActivity(binding.getRoot()));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //        layoutParams.bottomMargin = (int) protocolLayout.getRootView().getContext().getResources().getDimension(R.dimen.x20);
        linearLayout.setLayoutParams(layoutParams);
        return linearLayout;
    }
    /**
     * 计算Layout宽度
     */
    private int getLayoutWidth() {
        WindowManager wm    = (WindowManager) Util.getActivity(binding.getRoot()).getSystemService(Context.WINDOW_SERVICE);
        int           width = wm.getDefaultDisplay().getWidth();
        return width - 2 * (int) binding.protocol.getRootView().getContext().getResources().getDimension(R.dimen.x20);
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
}

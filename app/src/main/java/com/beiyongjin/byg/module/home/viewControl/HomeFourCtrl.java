package com.beiyongjin.byg.module.home.viewControl;

import android.content.ContentResolver;
import android.content.Intent;
import android.databinding.ObservableField;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.beiyongjin.byg.module.home.dataModel.LoanRec;
import com.beiyongjin.byg.views.ServiceDialog;
import com.erongdu.wireless.info.SharedInfo;
import com.erongdu.wireless.network.entity.HttpResult;
import com.erongdu.wireless.network.entity.ListData;
import com.erongdu.wireless.tools.utils.ContextHolder;
import com.erongdu.wireless.tools.utils.ConverterUtil;
import com.erongdu.wireless.tools.utils.StringFormat;
import com.erongdu.wireless.tools.utils.TextUtil;
import com.erongdu.wireless.views.recyclerView.DividerLine;
import com.erongdu.wireless.views.spinnerwheel.adapters.ListWheelAdapter;
import com.github.mzule.activityrouter.router.Routers;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;
import com.beiyongjin.byg.MainAct;
import com.beiyongjin.byg.R;
import com.beiyongjin.byg.common.AppConfig;
import com.beiyongjin.byg.common.CommonType;
import com.beiyongjin.byg.common.Constant;
import com.beiyongjin.byg.common.DialogUtils;
import com.beiyongjin.byg.common.FeatureConfig;
import com.beiyongjin.byg.common.RouterUrl;
import com.beiyongjin.byg.common.SwipeListener;
import com.beiyongjin.byg.common.ui.BaseRecyclerViewCtrl;
import com.beiyongjin.byg.databinding.HomeFourFragBinding;
import com.beiyongjin.byg.module.home.dataModel.HomeChoiceRec;
import com.beiyongjin.byg.module.home.dataModel.HomeRec;
import com.beiyongjin.byg.module.home.dataModel.LoanProgressRec;
import com.beiyongjin.byg.module.home.dataModel.NeedDivertRec;
import com.beiyongjin.byg.module.home.dataModel.NoticeRec;
import com.beiyongjin.byg.module.home.viewModel.HomeBannerRec;
import com.beiyongjin.byg.module.home.viewModel.HomeFourVM;
import com.beiyongjin.byg.module.home.viewModel.HomeProgressVM;
import com.beiyongjin.byg.module.mine.dataModel.recive.CommonRec;
import com.beiyongjin.byg.module.repay.viewModel.LoanProgressVM;
import com.beiyongjin.byg.module.user.dataModel.receive.OauthTokenMo;
import com.beiyongjin.byg.network.NetworkUtil;
import com.beiyongjin.byg.network.RDClient;
import com.beiyongjin.byg.network.RequestCallBack;
import com.beiyongjin.byg.network.api.CommonService;
import com.beiyongjin.byg.network.api.LoanService;
import com.beiyongjin.byg.utils.Util;
import com.beiyongjin.byg.views.CostDialog;
import com.beiyongjin.byg.views.OnRulerChangeListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.pedant.SweetAlert.OnSweetClickListener;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: 沈志华
 * E-mail: shenzh@erongdu.com
 * Date: 2017/8/9$ 11:26$
 * <p/>
 * {@link com.beiyongjin.byg.module.home.ui.fragment.HomeFourFrag}
 */
public class HomeFourCtrl extends BaseRecyclerViewCtrl implements  SeekBar.OnSeekBarChangeListener {
    private HomeFourFragBinding binding;
    //借款浮动天数
    public  ObservableField<String> loanDay        = new ObservableField<>();
    //借款浮动额度
    public  ObservableField<String> loanMoney      = new ObservableField<>("0.00");
    //实际借款金额
    public  ObservableField<String> realMoney      = new ObservableField<>("0.00");
    //服务费用
    public  ObservableField<String> serviceMoney   = new ObservableField<>("0.00");
    public  ObservableField<String> repayButtonStr = new ObservableField<>("");
    private List<String>            moneyList      = new ArrayList<>();
    private List<String>            dayList        = new ArrayList<>();
    //    //首页
    public      HomeFourVM homeVM;
    public       ObservableField<Integer> homeShow       = new ObservableField<>(View.GONE);
    public       ObservableField<Integer> loanShow       = new ObservableField<>(View.GONE);
    /** 今日限额 */
    public       ObservableField<Integer> dayLimitShow   = new ObservableField<>(FeatureConfig.enableFeature(FeatureConfig.enabledailyLimitFeature) ?
            View.VISIBLE : View.GONE);
    public       ObservableField<Boolean> showUpMoney    = new ObservableField<>(FeatureConfig.enableFeature(FeatureConfig.enablelimitAmountTipFeature));
    /** 计算金额 */
    private      String                   calculateMoney = "";
    /** 计算金额 */
    private      String                   calculateDay   = "";
    /** 计算费率 */
    private      String                   calculateRate  = "";
    public       ObservableField<Boolean> isClick        = new ObservableField<>(false);
    public       ObservableField<Boolean> isMinDay       = new ObservableField<>(true);
    private      int                      width          = 0;
    /** 借款标识 */
    public final int                      LOAN           = 0x0111;
    private HomeChoiceRec       rec;
    private List<HomeChoiceRec> choiceRecList;
    private HomeRec             homeRec;
    public  ObservableField<String> oneItemValue = new ObservableField<>(Constant.STATUS_20);
    public  ObservableField<String> currLocation = new ObservableField<>("500");
    //是否可选择的天数列表为1个 1个显示一个按钮
    public  ObservableField<Boolean> isOneSelectDay = new ObservableField<>(false);
    /** 是否配置详情 */
    private boolean                 isFeeDetail  = FeatureConfig.enableFeature(FeatureConfig.enablefeeDetailFeature);
    /*seekBar的最大值*/
    private int Max=Constant.SEEK_MAX;

    public HomeFourCtrl(HomeFourFragBinding binding, int width) {
        this.binding = binding;
        this.width = width;
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) binding.banner.getLayoutParams();
       // linearParams.height = (int) (width * 0.2);
        binding.banner.setVisibility(View.VISIBLE);
        binding.banner.setLayoutParams(linearParams);
        binding.homeseekbar.setProgress(100);
        binding.homeseekbar.setOnSeekBarChangeListener(this);

        //设置textView的字体渐变色

        homeVM = new HomeFourVM();
        viewModel.set(new HomeProgressVM());
        viewModel.get().clipToPadding = false;
        viewModel.get().type = DividerLine.DEFAULT;

        initListener();

        listener.set(new SwipeListener() {
            @Override
            public void swipeInit(SwipeToLoadLayout swipeLayout) {
                setSwipeLayout(swipeLayout);
                swipeLayout.setLoadMoreEnabled(false);
            }

            @Override
            public void refresh() {
                reqHomeData();
                reqHomeChoiceData();
                reqNotice();
                reqBanner();
            }

            @Override
            public void loadMore() {
            }
        });
      // binding.homeseekbar.setHomeSeekBarListener(this);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                binding.wheelView.setCurrentItem(binding.wheelView.getCurrentItem() + 1, true);
            } else if (msg.what == 2) {
                HomeRec homeRec = (HomeRec) msg.obj;

                if (dayList.size() == 0 || moneyList.size() == 0 || !Arrays.equals(dayList.toArray(), homeRec.getDayList().toArray())
                        || !Arrays.equals(moneyList.toArray(), homeRec.getCreditList().toArray())) {
                    loanDay.set(homeRec.getMaxDays() + "天");
                    loanMoney.set(homeRec.getMaxCredit());
                    calculateMoney = homeRec.getMinCredit();
                    dayList = homeRec.getDayList();
                    moneyList = homeRec.getCreditList();

                    calculateRate = homeRec.getInterests().get(homeRec.getInterests().size() - 1);
                    //binding.hsbSelectedDay.setProgress(100);

                }
            }
        }
    };

    /** 获取公告 */
    public void reqNotice() {
        Call<HttpResult<ListData<NoticeRec>>> noticeCall = RDClient.getService(LoanService.class).getNoticeList();
        noticeCall.enqueue(new RequestCallBack<HttpResult<ListData<NoticeRec>>>(getSwipeLayout()) {
            @Override
            public void onSuccess(Call<HttpResult<ListData<NoticeRec>>> call, Response<HttpResult<ListData<NoticeRec>>> response) {
                List<NoticeRec> noticeRecList = response.body().getData().getList();
                if (noticeRecList != null && noticeRecList.size() > 0) {
                    List<String> wheelList = new ArrayList<>();
                    for (int i = 0; i < noticeRecList.size(); i++) {
                        wheelList.add(noticeRecList.get(i).getValue());
                    }
                    ListWheelAdapter adapter = new ListWheelAdapter<String>(ContextHolder.getContext(), R.layout.list_item_home_text4, wheelList);
                    adapter.setTextSize(13);
                    binding.wheelView.setViewAdapter(adapter);
                }
            }
        });
        Call<HttpResult<ListData<CommonRec>>> call = RDClient.getService(CommonService.class).h5List();
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<HttpResult<ListData<CommonRec>>>() {
            @Override
            public void onSuccess(Call<HttpResult<ListData<CommonRec>>> call, Response<HttpResult<ListData<CommonRec>>> response) {
                convert(response.body().getData().getList());
            }
        });
    }

    private void convert(List<CommonRec> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            if (CommonType.ABOUNT_US.equals(list.get(i).getCode())) {
                homeVM.setAboutUrl(list.get(i));
            }
        }
    }

    /**
     * 关于我们
     */
    public void aboutClick(View view) {
        if (homeVM.getAboutUrl() != null) {
            Routers.open(view.getContext(), RouterUrl.getRouterUrl(String.format(RouterUrl.AppCommon_WebView, homeVM.getAboutUrl().getName(), CommonType
                    .getUrl(homeVM.getAboutUrl()
                            .getValue()), "")));
        }
    }

    /**
     * 获取借款进度
     *
     * @param list
     */
    private void getProgressData(List<LoanProgressRec> list) {
        if (list != null && list.size() > 0) {
            List<LoanProgressVM> vms = new ArrayList<>();
            if (list.size() == 1) {
                LoanProgressVM  vm  = new LoanProgressVM();
                LoanProgressRec rec = list.get(0);
                vm.setLoanTime(rec.getCreateTime());
                vm.setRemark(rec.getRemark());
                vm.setRepayTime(rec.getRepayTime());
                vm.setType(rec.getType());
                if (10 == vm.getType()) {
                    vm.setFirst(true);
                }
                vm.setState(rec.getState());
                vm.setEnd(true);
                vms.add(vm);
            } else {
                for (int i = 0; i < list.size(); i++) {
                    LoanProgressVM vm = new LoanProgressVM();
                    vm.setLoanTime(list.get(i).getCreateTime());
                    vm.setRemark(list.get(i).getRemark());
                    vm.setRepayTime(list.get(i).getRepayTime());
                    vm.setType(list.get(i).getType());
                    vm.setState(list.get(i).getState());
                    if (i == 0) {
                        //if (10 == vm.getType()) {
                        vm.setFirst(true);
                        if (Constant.NUMBER_10 == vm.getType() || Constant.NUMBER_20 == vm.getType()) {
                            vm.setGrey_1(false);
                        }
                        //}
                    }
                    if ((i == list.size() - 1) && i != 0) {
                        vm.setEnd(true);
                    }
                    vms.add(vm);
                }
            }
            viewModel.get().items.clear();
            viewModel.get().items.addAll(vms);
        }
    }

    private void convertHomeData(HomeRec data) {
        this.homeRec = data;
        homeVM.setPwd(data.isPwd());
        homeVM.setMinDays(data.getMinDays());
        homeVM.setMaxDays(data.getMaxDays());
        homeVM.setBorrowId(data.getBorrowId());
        homeVM.setBorrow(data.isBorrow());
        homeVM.setCreditList(data.getCreditList());
        homeVM.setDayList(data.getDayList());
        homeVM.setFee(data.getFee());
        homeVM.setMaxCredit(data.getMaxCredit());
        homeVM.setMinCredit(data.getMinCredit());
        homeVM.setCardNo(data.getCardNo());
        homeVM.setCardId(data.getCardId());
        homeVM.setCardName(data.getCardName());
        homeVM.setInterests(data.getInterests());
        homeVM.setTitle(data.getTitle());
        homeVM.setTotal(data.getTotal());
        homeVM.setLoanCeiling(data.getLoanCeiling());
        homeVM.setRepay(data.isRepay());

        if (data.isRefuse()) {
            repayButtonStr.set(ContextHolder.getContext().getString(R.string.home_know_now));
            homeVM.setRepayBtnShow(true);
        } else if (data.isRepay()) {
            repayButtonStr.set(ContextHolder.getContext().getString(R.string.home_to_repay_now));
            homeVM.setRepayBtnShow(true);
        } else {
            homeVM.setRepayBtnShow(false);
        }
        if (TextUtil.isEmpty(data.getCount())) {
            homeVM.setCount(Constant.STATUS_0);
            //homeVM.setTotal(data.getMaxCredit());
        } else {
            homeVM.setCount(data.getCount());
        }
        if (null != data.getAuth()) {
            if (!TextUtil.isEmpty(data.getAuth().getQualified()) || !TextUtil.isEmpty(data.getAuth().getResult()) || !TextUtil.isEmpty(data.getAuth()
                    .getTotal())) {
                homeVM.setAuthCount(data.getAuth().getResult());
                homeVM.setAuthTotal(data.getAuth().getTotal());
                if (Constant.STATUS_0.equals(data.getAuth().getQualified())) {
                    isClick.set(true);
                } else {
                    isClick.set(false);
                }
            }
        } else {
            homeVM.setAuthCount("0");
            homeVM.setAuthTotal("0");
        }
        if (homeVM.isBorrow()) {
            MainAct.repayState = 2;
        } else {
            MainAct.repayState = 1;
        }
    }

    public void reqHomeData() {

        Call<HttpResult<HomeRec>> call = RDClient.getService(LoanService.class).getHomeIndex();
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<HttpResult<HomeRec>>(getSwipeLayout()) {
            @Override
            public void onSuccess(Call<HttpResult<HomeRec>> call, Response<HttpResult<HomeRec>> response) {
                final HomeRec homeRec = response.body().getData();
                convertHomeData(homeRec);
                if (homeRec.isBorrow()) {

                    getProgressData(response.body().getData().getList());

                    loanShow.set(View.VISIBLE);
                    homeShow.set(View.GONE);
                } else {
                    /*new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Message message = new Message();
                            message.obj = homeRec;
                            message.what = 2;
                            handler.sendMessage(message);
                        }
                    }).start();*/
                    if (dayList.size() == 0 || moneyList.size() == 0 || !Arrays.equals(dayList.toArray(), homeRec.getDayList().toArray())
                            || !Arrays.equals(moneyList.toArray(), homeRec.getCreditList().toArray())) {
                        loanDay.set(homeRec.getMaxDays() + "天");
                        loanMoney.set(ConverterUtil.getInteger(homeRec.getMinCredit()) + "");
                        currLocation.set(homeRec.getMinCredit());
                        calculateMoney = homeRec.getMaxCredit();
                        calculateDay = homeRec.getMinDays();
                        dayList = homeRec.getDayList();
                        moneyList = homeRec.getCreditList();
                        binding.mainMax.setText(moneyList.get(moneyList.size()-1)+"元");
                        binding.mainMin.setText(moneyList.get(0)+"元");
                        binding.homeseekbar.setList(moneyList);
                        loanMoney.set(moneyList.get(moneyList.size()-1));
                      //  Log.i("hjc","贷款的金额："+moneyList.get(moneyList.size()-1)+"...."+"选择的时间"+calculateDay);

                         Max=Integer.parseInt(moneyList.get(moneyList.size()-1));
                        /*Log.i("hjc","最大值：:"+Integer.parseInt(moneyList.get(moneyList.size()-1)));
                        for (int i=0;i<moneyList.size();i++){
                            Log.i("hjc","区间：:"+moneyList.get(i));
                        }*/

                        if(dayList.size()>1){
                          isOneSelectDay.set(false);
                        }else{
                            isOneSelectDay.set(true);
                        }

                        calculateRate = homeRec.getInterests().get(homeRec.getInterests().size() - 1);
                        //binding.hsbSelectedDay.setProgress(100);
                    }
                    isMinDay.set(true);
                    binding.homeseekbar.setProgress(100);
                    loanMoney.set(moneyList.get(moneyList.size()-1));
                    convertHomeChoiceData(moneyList.get(moneyList.size()-1),calculateDay);
                  //  calculateRate();
                    homeShow.set(View.VISIBLE);
                    loanShow.set(View.GONE);
                }
            }


        });
    }

    private void initListener() {
        binding.wheelView.setCyclic(true);
        binding.wheelView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(5000);
                        Message message = Message.obtain();
                        message.what = 1;
                        handler.sendMessage(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /** 选择最小天数 */
    public void selectMinDay(View view) {
        isMinDay.set(true);
        loanDay.set(homeRec.getMinDays());
        calculateDay = homeRec.getMinDays();
        calculateRate();
    }

    /** 选择最大天数 */
    public void selectMaxDay(View view) {
        isMinDay.set(false);
        loanDay.set(homeRec.getMaxDays());
        calculateDay = homeRec.getMaxDays();
        calculateRate();
    }

    /** 计算费率 */
    private void calculateRate() {
        double real = ConverterUtil.getDouble(calculateMoney);
        if (real != 0) {
            //判断费用计算从服务端获取
            /*if (FeatureConfig.enablecaculateFeeNativeFeature == 1) {*/
            Log.i("hjc","222222");
            convertHomeChoiceData(calculateMoney, calculateDay);
            // do noting
            /*} else {
                serviceMoney.set(StringFormat.twoDecimalFormat(Double.parseDouble(calculateRate) * real));
                String money = String.valueOf(real - Double.parseDouble(serviceMoney.get()));
                realMoney.set(StringFormat.twoDecimalFormat(money));
            }*/
        }
    }

    /** dataModel to viewModel */
    private void convertHomeChoiceData(String amount, String timeLimit) {
        if (choiceRecList == null)
            return;
        for (int i = 0; i < choiceRecList.size(); i++) {
            HomeChoiceRec rec = choiceRecList.get(i);
            if (ConverterUtil.getDouble(amount) == ConverterUtil.getDouble(rec.getAmount()) && ConverterUtil.getDouble(timeLimit) == ConverterUtil.getDouble
                    (rec.getTimeLimit())) {
                serviceMoney.set(StringFormat.twoDecimalFormat(Double.parseDouble(rec.getFee())));
                String money = rec.getRealAmount();
                realMoney.set(StringFormat.twoDecimalFormat(money));
                this.rec = rec;
            }
        }
    }

    /**
     * 获取服务费率和手续费
     */
    public void reqHomeChoiceData() {
        if (isFeeDetail) {
            Call<HttpResult<ListData<HomeChoiceRec>>> call = RDClient.getService(LoanService.class).getHomeChoicesList();
            call.enqueue(new RequestCallBack<HttpResult<ListData<HomeChoiceRec>>>(getSwipeLayout()) {
                @Override
                public void onSuccess(Call<HttpResult<ListData<HomeChoiceRec>>> call, Response<HttpResult<ListData<HomeChoiceRec>>> response) {
                    choiceRecList = response.body().getData().getList();
                    convertHomeChoiceData(calculateMoney, calculateDay);
                }
            });
        } else {
            Call<HttpResult<ListData<HomeChoiceRec>>> call = RDClient.getService(LoanService.class).getHomeChoice();
            call.enqueue(new RequestCallBack<HttpResult<ListData<HomeChoiceRec>>>(getSwipeLayout()) {
                @Override
                public void onSuccess(Call<HttpResult<ListData<HomeChoiceRec>>> call, Response<HttpResult<ListData<HomeChoiceRec>>> response) {
                    choiceRecList = response.body().getData().getList();
                    convertHomeChoiceData(calculateMoney, calculateDay);
                }
            });
        }
    }

    /**
     * 立即申请
     */
    public void applyClick(View view) {
        if ((boolean) SharedInfo.getInstance().getValue(Constant.IS_LAND, false)) {
            if (!isClick.get()) {
                // 移除关于支付设置交易密码变量(去除历史记录中存储的变量)
                SharedInfo.getInstance().remove(Constant.IS_PAY_SETTING);
                if (!homeVM.isPwd()) {
                    SharedInfo.getInstance().saveValue(Constant.IS_PAY_SETTING, 1);
                }
                Routers.openForResult(Util.getActivity(view), RouterUrl.getRouterUrl(String.format(RouterUrl.Loan_Details,
                        calculateMoney, calculateDay, realMoney.get(), serviceMoney.get(), homeVM.getCardName(), homeVM.getCardNo(), homeVM.getCardId()
                )), LOAN);
            } else {
                DialogUtils.showDialog(view.getContext(), R.string.cancel, R.string.home_go_credit, R.string.home_credit_toast, new OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        if (AppConfig.CREDIT_CENTER_INDEX_NUM == Constant.NUMBER_3) {
                            Routers.open(sweetAlertDialog.getContext(), RouterUrl.getRouterUrl(String.format(RouterUrl.Mine_CreditThreeCenter, Constant
                                    .STATUS_1)));
                        } else if (AppConfig.CREDIT_CENTER_INDEX_NUM == Constant.NUMBER_2) {
                            Routers.open(sweetAlertDialog.getContext(), RouterUrl.getRouterUrl(RouterUrl.Mine_CreditTwoCenter));
                        } else {
                            Routers.open(sweetAlertDialog.getContext(), RouterUrl.getRouterUrl(RouterUrl.Mine_CreditCenter));
                        }
                        sweetAlertDialog.dismissWithAnimation();
                    }
                });
            }
        } else {
            if(FeatureConfig.enableFeature(FeatureConfig.enablewebHomeFeature)){
                Routers.open(view.getContext(), RouterUrl.getRouterUrl(String.format(RouterUrl.UserInfoManage_Login,Constant.STATUS_4)));
            }else{
                Routers.open(view.getContext(), RouterUrl.getRouterUrl(RouterUrl.UserInfoManage_Login));
            }
        }
    }

    /**
     * 跳转认证中心
     */
    public void auth(View view) {
        if ((boolean) SharedInfo.getInstance().getValue(Constant.IS_LAND, false)) {
            if (AppConfig.CREDIT_CENTER_INDEX_NUM == Constant.NUMBER_3) {
                Routers.open(view.getContext(), RouterUrl.getRouterUrl(String.format(RouterUrl.Mine_CreditThreeCenter, Constant.STATUS_1)));
            } else if (AppConfig.CREDIT_CENTER_INDEX_NUM == Constant.NUMBER_2) {
                Routers.open(view.getContext(), RouterUrl.getRouterUrl(RouterUrl.Mine_CreditTwoCenter));
            } else {
                Routers.open(view.getContext(), RouterUrl.getRouterUrl(RouterUrl.Mine_CreditCenter));
            }
        } else {
            Routers.open(view.getContext(), RouterUrl.getRouterUrl(RouterUrl.UserInfoManage_Login));
        }
    }

    /**
     * 立即申请
     */
    public void knowOrRepay(View view) {
        if (homeRec.isRefuse()) {
            if (SharedInfo.getInstance().getEntity(OauthTokenMo.class) == null)
                return;
            Call<HttpResult<NeedDivertRec>> call = RDClient.getService(LoanService.class).needDivert();
            call.enqueue(new RequestCallBack<HttpResult<NeedDivertRec>>() {
                @Override
                public void onSuccess(Call<HttpResult<NeedDivertRec>> call, Response<HttpResult<NeedDivertRec>> response) {
                    if (response.body().getData().getState().equals(Constant.STATUS_10)) {
                        String url = response.body().getData().getUrl() + "?type=1";
                        Routers.open(ContextHolder.getContext(), RouterUrl.getRouterUrl(String.format(RouterUrl.AppCommon_WebView,
                                ContextHolder.getContext().getString(R.string.drainage), url, "")));
                    } else {
                        reqHomeData();
                    }
                }
            });
        } else if (homeRec.isRepay()) {
            Routers.open(view.getContext(), RouterUrl.getRouterUrl(String.format(RouterUrl.AppCommon_Main, Constant.STATUS_2)));
        }
    }

    /*调用客服*/
    public void service(View view){

        new ServiceDialog(view.getContext()).show();




    }

    public void costDialog(View view) {
        if (rec != null) {
            new CostDialog(view.getContext(), rec).show();
        }
    }

    public HomeFourVM getHomeVM() {
        return homeVM;
    }

    /** 完善资料 */
    public void perfectClick(View view) {
        if ((boolean) SharedInfo.getInstance().getValue(Constant.IS_LAND, false)) {
            if (AppConfig.CREDIT_CENTER_INDEX_NUM == Constant.NUMBER_3) {
                Routers.open(view.getContext(), RouterUrl.getRouterUrl(String.format(RouterUrl.Mine_CreditThreeCenter, Constant.STATUS_1)));
            } else if (AppConfig.CREDIT_CENTER_INDEX_NUM == Constant.NUMBER_2) {
                Routers.open(view.getContext(), RouterUrl.getRouterUrl(RouterUrl.Mine_CreditTwoCenter));
            } else {
                Routers.open(view.getContext(), RouterUrl.getRouterUrl(RouterUrl.Mine_CreditCenter));
            }
        } else {
            Routers.open(view.getContext(), RouterUrl.getRouterUrl(RouterUrl.UserInfoManage_Login));
        }
    }


    /**
     * 请求banner数据
     */
    private void reqBanner() {
        Call<HttpResult<ListData<HomeBannerRec>>> call = RDClient.getService(LoanService.class).getBanner();
        call.enqueue(new RequestCallBack<HttpResult<ListData<HomeBannerRec>>>() {
            @Override
            public void onSuccess(Call<HttpResult<ListData<HomeBannerRec>>> call, Response<HttpResult<ListData<HomeBannerRec>>> response) {
                if (null != response.body() && null != response.body().getData() && null != response.body().getData().getList() &&
                        response.body().getData().getList().size() != Constant.NUMBER_0) {
                    List<HomeBannerRec> bannerListRec = response.body().getData().getList();
                    Collections.sort(bannerListRec, new Comparator<HomeBannerRec>() {
                        @Override
                        public int compare(HomeBannerRec lhs, HomeBannerRec rhs) {
                            //按照升序排列
                          if(Integer.valueOf(lhs.getSort())>Integer.valueOf(rhs.getSort())){
                              return 1;
                          }else if(Integer.valueOf(lhs.getSort()) == Integer.valueOf(rhs.getSort())){
                              return 0;
                          }else{
                              return -1;
                          }
                        }
                    });
                    List                images        = new ArrayList();
                    for (int i = 0; i < bannerListRec.size(); i++) {
                        HomeBannerRec rec = bannerListRec.get(i);
                        images.add(i,rec.getPath());
                    }
                    //设置图片集合
                    binding.banner.setImages(images);
                    bannerInit(bannerListRec);
                    //banner设置方法全部调用完毕时最后调用
                    binding.banner.start();
                }else{
                    List images = new ArrayList();
                    Uri  uri    = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                    + Util.getActivity(binding.getRoot()).getResources().getResourcePackageName(R.drawable.banner_default) + "/"
                    + Util.getActivity(binding.getRoot()).getResources().getResourceTypeName(R.drawable.banner_default) + "/"
                    + Util.getActivity(binding.getRoot()).getResources().getResourceEntryName(R.drawable.banner_default));
                    images.add(0,uri);
                    //设置图片集合
                    binding.banner.setImages(images);
                    binding.banner.setImageLoader(new GlideImageLoader());
                    binding.banner.isAutoPlay(true);
                    //设置轮播时间
                    binding.banner.setDelayTime(5000);
                    //设置指示器位置（当banner模式中有指示器时）
                    binding.banner.setIndicatorGravity(BannerConfig.CENTER);
                    //banner设置方法全部调用完毕时最后调用
                    binding.banner.start();
                }
            }
        });
    }

    /***
     * banner初始化
     */
    public void bannerInit(final List<HomeBannerRec> bannerListRec) {
        binding.banner.setImageLoader(new GlideImageLoader());
        binding.banner.isAutoPlay(true);
        //设置轮播时间
        binding.banner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        binding.banner.setIndicatorGravity(BannerConfig.CENTER);

      /*  binding.banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                for (int i = 0; i < bannerListRec.size(); i++) {
                    if (null != bannerListRec.get(i).getLink() && ConverterUtil.getInteger(bannerListRec.get(i).getSort()) == position) {
                        Routers.open(binding.getRoot().getContext(), RouterUrl.getRouterUrl(String.format(RouterUrl.AppCommon_WebView, "", bannerListRec.get(i).getLink(), "")));
                    }
                }
            }
        });*/
    }

  /*  @Override
    public void setValue(int value) {
        loanMoney.set(value+"");
        calculateMoney=value+"";
        convertHomeChoiceData(calculateMoney, calculateDay);
    }
*/


    //*拖动的过程*//*
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        int Size = moneyList.size();
        /*int maxSize = moneyList.size() - 1;
        int min = Integer.parseInt(moneyList.get(0));
        int max = Integer.parseInt(moneyList.get(maxSize));
        int target=min+i*((max-min)/100);
        int closeIntenge = Util.getCloseIntenge(target, moneyList);*/
        int m=100/Size;
        String sTarget ="";
        if(i/m==Size){
            sTarget=moneyList.get(Size-1);
        }else {
            sTarget = moneyList.get((i / m));
        }

    //   Log.i("hjc","滑动的距离：："+i+"..."+"数组size::"+Size+"..."+"i/m：："+i/m);
        loanMoney.set(sTarget);
        calculateMoney=sTarget;
      //  Log.i("hjc","3333333");
        convertHomeChoiceData(sTarget, calculateDay);
    }
    //*拖动的开始*//*
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {



    }
    //拖动的结果
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}

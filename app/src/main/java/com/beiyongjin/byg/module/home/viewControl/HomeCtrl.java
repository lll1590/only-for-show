package com.beiyongjin.byg.module.home.viewControl;

import android.databinding.ObservableField;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
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
import com.beiyongjin.byg.databinding.HomeFragBinding;
import com.beiyongjin.byg.module.home.dataModel.HomeChoiceRec;
import com.beiyongjin.byg.module.home.dataModel.HomeRec;
import com.beiyongjin.byg.module.home.dataModel.LoanProgressRec;
import com.beiyongjin.byg.module.home.dataModel.NeedDivertRec;
import com.beiyongjin.byg.module.home.dataModel.NoticeRec;
import com.beiyongjin.byg.module.home.ui.fragment.HomeFrag;
import com.beiyongjin.byg.module.home.viewModel.HomeProgressVM;
import com.beiyongjin.byg.module.home.viewModel.HomeVM;
import com.beiyongjin.byg.module.mine.dataModel.recive.CommonRec;
import com.beiyongjin.byg.module.repay.viewModel.LoanProgressVM;
import com.beiyongjin.byg.module.user.dataModel.receive.OauthTokenMo;
import com.beiyongjin.byg.network.NetworkUtil;
import com.beiyongjin.byg.network.RDClient;
import com.beiyongjin.byg.network.RequestCallBack;
import com.beiyongjin.byg.network.api.CommonService;
import com.beiyongjin.byg.network.api.LoanService;
import com.beiyongjin.byg.utils.ConvertUtil;
import com.beiyongjin.byg.utils.Util;
import com.beiyongjin.byg.views.CostDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.pedant.SweetAlert.OnSweetClickListener;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: JinFu
 * E-mail: jf@erongdu.com
 * Date: 2017/2/14 10:42
 * <p/>
 * Description: {@link HomeFrag}
 */
public class HomeCtrl extends BaseRecyclerViewCtrl implements AMapLocationListener{
    private HomeFragBinding binding;
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
    //    public  ObservableField<HomeVM>  vm             = new ObservableField<>();
    public HomeVM homeVM;
    /**当前城市*/
    public ObservableField<String>  city           = new ObservableField<>("");
    private AMapLocationClient                       mlocationClient;
    private AMapLocationClientOption                 mLocationOption;
    //借款详情
    //    public ObservableField<LoanVM> loanVM = new ObservableField<>();
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
    private      int                      width          = 0;
    /** 借款标识 */
    public final int                      LOAN           = 0x0111;
    private HomeChoiceRec       rec;
    private List<HomeChoiceRec> choiceRecList;
    private HomeRec             homeRec;
    /** 是否配置详情 */
    private boolean isFeeDetail = FeatureConfig.enableFeature(FeatureConfig.enablefeeDetailFeature);

    public HomeCtrl(HomeFragBinding binding, int widthPixels) {
        this.binding = binding;

        this.width = widthPixels;
        homeVM = new HomeVM();
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
                getGpsCity();
                reqHomeData();
                reqHomeChoiceData();
                reqNotice();

            }

            @Override
            public void loadMore() {

            }
        });
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
                    loanMoney.set(homeRec.getMaxCredit() + "元");
                    calculateMoney = homeRec.getMinCredit();
                    dayList = homeRec.getDayList();
                    moneyList = homeRec.getCreditList();
                    calculateRate = homeRec.getInterests().get(homeRec.getInterests().size() - 1);
                    binding.hsbSelectedDay.setProgress(100);
                    binding.hsbSelectedMoney.setProgress(100);
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
                    ListWheelAdapter adapter = new ListWheelAdapter<String>(ContextHolder.getContext(), R.layout.list_item_home_text, wheelList);
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
        }else if (data.isRepay()) {
            repayButtonStr.set(ContextHolder.getContext().getString(R.string.home_to_repay_now));
            homeVM.setRepayBtnShow(true);
        } else {
            homeVM.setRepayBtnShow(false);
        }
        if (TextUtil.isEmpty(data.getCount())) {
            homeVM.setCount(Constant.STATUS_0);
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
                        loanMoney.set(ConverterUtil.getInteger(homeRec.getMaxCredit()) + "元");
                        calculateMoney = homeRec.getMinCredit();
                        dayList = homeRec.getDayList();
                        moneyList = homeRec.getCreditList();
                        calculateRate = homeRec.getInterests().get(homeRec.getInterests().size() - 1);
                        binding.hsbSelectedDay.setProgress(100);
                        binding.hsbSelectedMoney.setProgress(100);
                    }
                    calculateRate();
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
        binding.hsbSelectedMoney.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                                                @Override
                                                                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                                                    if (homeVM == null || homeVM.getCreditList() == null)
                                                                        return;
                                                                    double temp  = 100.0 / homeVM.getCreditList().size();
                                                                    int    index = (int) Math.ceil(progress / temp);
                                                                    if (index == 0) {
                                                                    } else {
                                                                        index = index - 1;
                                                                    }
                                                                    //下面到账金额的改变
                                                                    String money = homeVM.getCreditList().get(index);
                                                                    calculateMoney = money;
                                                                    calculateRate();
                                                                    loanMoney.set(money + "元");
                                                                    //字体的改变
                                                                    TextPaint paint = binding.tvMoney.getPaint();
                                                                    float     len   = paint.measureText(loanMoney.get());
                                                                    RelativeLayout.LayoutParams paramsStrength = (RelativeLayout.LayoutParams) binding
                                                                            .tvMoney.getLayoutParams();
                                                                    int leftmargin = binding.hsbSelectedMoney.getSeekBarThumb()
                                                                            .getBounds().centerX() - (int) len / 2;
                                                                    if (leftmargin < 0)
                                                                        leftmargin = 0;
                                                                    if (leftmargin + len + ConvertUtil.dip2px(ContextHolder.getContext(), 12) > binding
                                                                            .rlMoney.getWidth()) {
                                                                        leftmargin = binding.rlMoney.getWidth() - (int) len - ConvertUtil.dip2px
                                                                                (ContextHolder.getContext(), 12);
                                                                    }
                                                                    if (progress == 100 && leftmargin < 0) {
                                                                        leftmargin = (int) (width * 0.75);
                                                                    }
                                                                    paramsStrength.leftMargin = leftmargin;
                                                                    paramsStrength.width = (int) len + 20;
                                                                    binding.tvMoney.setLayoutParams(paramsStrength);
                                                                    if (progress == 100) {
                                                                        binding.tvMoney.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
                                                                    } else if (progress == 0) {
                                                                        binding.tvMoney.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
                                                                    } else {
                                                                        binding.tvMoney.setGravity(Gravity.BOTTOM | Gravity.LEFT);
                                                                    }/**/
                                                                }

                                                                @Override
                                                                public void onStartTrackingTouch(SeekBar seekBar) {
                                                                }

                                                                @Override
                                                                public void onStopTrackingTouch(SeekBar seekBar) {
                                                                }
                                                            }

        );
        binding.hsbSelectedDay.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()

                                                          {
                                                              @Override
                                                              public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                                                  if (homeVM == null || homeVM.getDayList() == null)
                                                                      return;
                                                                  String day;
                                                                  double temp  = 100.0 / homeVM.getDayList().size();
                                                                  int    index = (int) Math.ceil(progress / temp);
                                                                  if (index == 0) {
                                                                  } else {
                                                                      index = index - 1;
                                                                  }
                                                                  calculateRate = homeVM.getInterests().get(index);
                                                                  day = homeVM.getDayList().get(index);
                                                                  calculateDay = day;
                                                                  calculateRate();
                                                                  loanDay.set(day + "天");
                                                                  TextPaint paint = binding.tvDay.getPaint();
                                                                  float     len   = paint.measureText(loanDay.get());
                                                                  RelativeLayout.LayoutParams paramsStrength = (RelativeLayout.LayoutParams) binding.tvDay
                                                                          .getLayoutParams();
                                                                  int leftmargin = binding.hsbSelectedDay.getSeekBarThumb()
                                                                          .getBounds().centerX() - (int) len / 2;
                                                                  if (leftmargin < 0)
                                                                      leftmargin = 0;
                                                                  if (leftmargin + len + ConvertUtil.dip2px(ContextHolder.getContext(), 12) > binding.rlDay
                                                                          .getWidth()) {
                                                                      leftmargin = binding.rlDay.getWidth() - (int) len - ConvertUtil.dip2px(ContextHolder
                                                                              .getContext(), 12);
                                                                  }
                                                                  if (progress == 100 && leftmargin < 0) {
                                                                      leftmargin = (int) (width * 0.8);
                                                                  }
                                                                  paramsStrength.leftMargin = leftmargin;
                                                                  paramsStrength.width = (int) len;
                                                                  binding.tvDay.setLayoutParams(paramsStrength);
                                                                  if (progress == 100) {
                                                                      binding.tvDay.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
                                                                  } else if (progress == 0) {
                                                                      binding.tvDay.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
                                                                  } else {
                                                                      binding.tvDay.setGravity(Gravity.BOTTOM | Gravity.LEFT);
                                                                  }
                                                              }

                                                              @Override
                                                              public void onStartTrackingTouch(SeekBar seekBar) {
                                                              }

                                                              @Override
                                                              public void onStopTrackingTouch(SeekBar seekBar) {
                                                              }
                                                          }

        );
    }

    /** 计算费率 */
    private void calculateRate() {
        double real = ConverterUtil.getDouble(calculateMoney);
        if (real != 0) {
            //判断费用计算从服务端获取
            /*if (FeatureConfig.enablecaculateFeeNativeFeature == 1) {*/
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
                            Routers.open(sweetAlertDialog.getContext(), RouterUrl.getRouterUrl(String.format(RouterUrl.Mine_CreditThreeCenter, Constant.STATUS_1)));
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
            Routers.open(view.getContext(), RouterUrl.getRouterUrl(RouterUrl.UserInfoManage_Login));
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

    public void costDialog(View view) {
        if (rec != null) {
            new CostDialog(view.getContext(), rec).show();
        }
    }

    public HomeVM getHomeVM() {
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


    /**获取当前位置*/
    public void getGpsCity(){
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(Util.getActivity(binding.getRoot()));
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位一次
            mLocationOption.setOnceLocation(true);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                city.set(aMapLocation.getCity());
            }else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }
}


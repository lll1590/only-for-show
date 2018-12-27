package com.beiyongjin.byg;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.net.Uri;
import android.text.TextUtils;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.beiyongjin.byg.common.AppConfig;
import com.beiyongjin.byg.common.BaseParams;
import com.beiyongjin.byg.common.Constant;
import com.beiyongjin.byg.common.FeatureConfig;
import com.beiyongjin.byg.common.LifecycleApplication;
import com.beiyongjin.byg.module.home.ui.activity.GuideAct;
import com.beiyongjin.byg.module.home.ui.activity.SplashAct;
import com.beiyongjin.byg.module.user.dataModel.receive.OauthTokenMo;
import com.beiyongjin.byg.service.CustomIntentService;
import com.beiyongjin.byg.service.CustomPushService;
import com.beiyongjin.byg.utils.LogcatManager;
import com.beiyongjin.byg.utils.aLiveUtil.utils.LFHttpRequestUtils;
import com.bugtags.library.Bugtags;
import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.target.ViewTarget;
import com.erongdu.wireless.friday.Friday;
import com.erongdu.wireless.greendao.RDDatabaseLoader;
import com.erongdu.wireless.info.SharedInfo;
import com.erongdu.wireless.network.entity.ApiEventBean;
import com.erongdu.wireless.network.utils.EventBusUtils;
import com.erongdu.wireless.network.utils.SerializedUtil;
import com.erongdu.wireless.tools.Config;
import com.erongdu.wireless.tools.encryption.RSA;
import com.erongdu.wireless.tools.log.Logger;
import com.erongdu.wireless.tools.utils.ActivityManage;
import com.erongdu.wireless.tools.utils.ContextHolder;
import com.erongdu.wireless.tools.utils.ToastUtil;
import com.github.mzule.activityrouter.router.RouterCallback;
import com.github.mzule.activityrouter.router.RouterCallbackProvider;
import com.github.mzule.activityrouter.router.SimpleRouterCallback;
import com.igexin.sdk.PushManager;
import com.minivision.livebodylibrary.util.FaceDetector;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.utils.Log;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import cn.tongdun.android.shell.FMAgent;
import cn.tongdun.android.shell.exception.FMException;
import okhttp3.OkHttpClient;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/8/16 10:51
 * <p>
 * Description:
 */
public class MyApplication extends LifecycleApplication implements RouterCallbackProvider, AMapLocationListener {
    private Class userPushService = CustomIntentService.class;
    public String BaiQiToken="";
    public String getBaiQiToken() {
        return BaiQiToken;
    }

    public void setBaiQiToken(String baiQiToken) {
        BaiQiToken = baiQiToken;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.i(TAG, ">>>>>>>>>>>>>>>>>>> Enter <<<<<<<<<<<<<<<<<<<");
        basicInit();
        dataInit();
        initPush();

        //打印错误奔溃信息
        startLogcatManager();
        registerGaoDeLocation();

        EventBusUtils.register(this);
    }

    /**
     * 初始化推送sdk
     **/
    private void initPush() {
        PushManager.getInstance().initialize(this.getApplicationContext(), CustomPushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), CustomIntentService.class);
    }

    private static MyApplication instance;

    public static synchronized MyApplication getInstance() {
        return instance;
    }

    //字体图标
    private Typeface iconTypeFace;

    public Typeface getIconTypeFace() {
        return iconTypeFace;
    }

    /**
     * 初始化 Application 运行所需的配置信息
     */
    private void basicInit() {
        Friday.setEnable(FeatureConfig.enableFeature(FeatureConfig.enablebehaviorGatherFeature));
        Friday.start(this, AppConfig.BEHAVIOR_GATHER_KEY);
        ContextHolder.init(this);
        Config.DEBUG.set(AppConfig.IS_DEBUG);
        Config.ROOT_PATH.set(BaseParams.ROOT_PATH);
        if (AppConfig.IS_DEBUG) {
            // Bugtags初始化
            Bugtags.start("acffe161e4fd4aff25e84f7762b19132", this, Bugtags.BTGInvocationEventBubble);
        }
        // 内存共享对象初始化
        SharedInfo.init(BaseParams.SP_NAME);
        // 分享初始化
        shareInit();
        // 数据库配置初始化
        RDDatabaseLoader.init(BaseParams.DATABASE_NAME, null);
        // 手势密码配置初始化
        initGesture();
        // iconfont 初始化
        iconFontInit();
        // 使用OkHttp加载网络图片
        Glide.get(this).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(new OkHttpClient()));
        // 为glide中使用的setTag设置特殊的ID用以标识，以防冲突。
        ViewTarget.setTagId(R.id.glide_tag);
        try {
            // RSA加密 key 初始化
            SerializedUtil.init(RSA.getKey(RSA.MODE.MODULUS_EXPONENT, "密钥类型: 0 - 私钥, 1 - 公钥", "模数", "指数"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 退出动作初始化
        ActivityManage.setOperations(new ActivityManage.ExtraOperations() {
            @Override
            public void onExit() {
                Friday.onAppShutDown(getApplicationContext());
                // TODO APP退出时需要额外处理的事情，例如广播的反注册，服务的解绑
            }

            @Override
            public void onActivityFinish(Activity activity) {
                // TODO activity 销毁时需要额外处理的事情，例如finish动画等
            }
        });
        LFHttpRequestUtils.initClient("http://cloudapi.linkface.cn");
        if (FeatureConfig.enableFeature(FeatureConfig.enabletongdunModuleFeature)) {
            //同盾初始化
            try {
                if (AppConfig.IS_DEBUG) {
                    FMAgent.init(this, FMAgent.ENV_SANDBOX);
                } else {
                    FMAgent.init(this, FMAgent.ENV_PRODUCTION);
                }
            } catch (FMException e) {
                e.printStackTrace();
            }
        }
        if (AppConfig.IDENTIFICATION_TYPE == Constant.NUMBER_3) {
            FaceDetector.init(this);
        }
    }

    /** iconfont 初始化 */
    private void iconFontInit() {
        instance = this;
        iconTypeFace = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
    }

    /**
     * 分享初始化
     */
    private void shareInit() {
        //微信
        PlatformConfig.setWeixin(AppConfig.WX_APP_ID, AppConfig.WX_APP_SECRET);
        //新浪微博
        //        PlatformConfig.setSinaWeibo(BaseParams.SINA_APP_ID, BaseParams.SINA_APP_SECRET);
        //QQ_ID
        PlatformConfig.setQQZone(AppConfig.QQ_ID, AppConfig.QQ_APP_KEY);
    }

    /**
     * 手势密码配置信息初始化
     */
    private void initGesture() {
        // APP在后台运行后返回APP，无需解锁的最大时间
        long millsWaitLock = 5 * 1000;
        // 手势密码最大输入错误次数
        int maxErrorCount = 5;
        // 忽略的Activity
        Set<Class<? extends Activity>> ignoreActivities = new HashSet<>();
        ignoreActivities.add(GuideAct.class);
        ignoreActivities.add(SplashAct.class);
        // 初始化
        // GestureLogic.init(millsWaitLock, maxErrorCount, LockAct.class, UnlockAct.class, ignoreActivities);
    }

    /**
     * 输出初始化
     */
    private void dataInit() {
        // 根据保存的 OauthToken ，判断用户是否已登录
        if (null != SharedInfo.getInstance().getEntity(OauthTokenMo.class)) {
            SharedInfo.getInstance().saveValue(Constant.IS_LAND, true);
        } else {
            SharedInfo.getInstance().saveValue(Constant.IS_LAND, false);
        }
    }

    @Override
    public RouterCallback provideRouterCallback() {
        return new SimpleRouterCallback() {
            @Override
            public void beforeOpen(Context context, Uri uri) {
            }

            @Override
            public void afterOpen(Context context, Uri uri) {
            }

            @Override
            public void notFound(Context context, Uri uri) {
            }

            @Override
            public void error(Context context, Uri uri, Throwable e) {
            }
        };
    }

    public void registerGaoDeLocation() {
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(this);
            //设置定位监听
            mLocationClient.setLocationListener(this);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            setLocOption(10000);
            mLocationClient.startLocation();
        }
    }

    public static void setLocOption(int time) {
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);// 设置定位模式
        option.setInterval(time);// 设置发起定位请求的间隔时间为5000ms
        option.setNeedAddress(true);// 返回的定位结果包含地址信息
        //		option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
        //设置为高精度定位模式
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationClient.setLocationOption(option);
    }

    /*********************** 高德地图定位 *****************************/
    public static AMapLocationClient mLocationClient;
    private static String  locCity  = "";
    private static String  district = "";
    public static  String  address  = "";
    public static  double  lat      = 0;
    public static  double  lon      = 0;
    private static boolean openGps  = false;
    private static int     locCount = 0;

    /***********
     * 打开GPS
     *
     * @param lisenter
     * @param status
     *         表示是否进行GPS跟踪
     */
    public static void openGps(OnPosChanged lisenter, boolean status) {
        locCount = 0;
        onPosChanged = lisenter;
        openGps = status;
        setLocOption(5000);
        mLocationClient.startLocation();
    }

    /**
     * 判断GPS是否开启
     */
    public static boolean isOpen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        //        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps) {//|| network) {
            return true;
        }

        return false;
    }

    /**********
     * 位置回调
     */
    static OnPosChanged onPosChanged;

    public static void setOnPosChanged(OnPosChanged callback) {
        locCount = 0;
        onPosChanged = callback;
    }

    public interface OnPosChanged {
        public void changed(AMapLocation location);
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                district = amapLocation.getDistrict();
                //                address = amapLocation.getProvince() + "," + amapLocation.getCity() + "," + district + "," + amapLocation.getStreet();
                address = amapLocation.getAddress();
                locAddress = amapLocation.getCity() + district + amapLocation.getStreet() + amapLocation.getStreetNum();
                locCity = amapLocation.getCity();
                lon = amapLocation.getLongitude();
                lat = amapLocation.getLatitude();

                if (locCity != null && !"".equals(locCity)) {
                    if (!openGps)
                        mLocationClient.stopLocation();
                    if (onPosChanged != null)
                        onPosChanged.changed(amapLocation);
                } else {
                    locCount++;
                    if (locCount >= 4) {
                        if (!openGps)
                            mLocationClient.stopLocation();
                        if (onPosChanged != null)
                            onPosChanged.changed(amapLocation);
                    }
                }
            } else {
                System.out.println("amapLocation.getErrorCode()" + amapLocation.getErrorCode());
                System.out.println("amapLocation.getErrorInfo()" + amapLocation.getErrorInfo());
                if (onPosChanged != null)
                    onPosChanged.changed(amapLocation);
            }
        }
    }

    private static String locAddress = "";

    public static String getLocAddress() {
        return locAddress;
    }

    public static void closeGps() {
        mLocationClient.stopLocation();
    }
    private void startLogcatManager() {
        if (Constant.DEBUG) {
            LogcatManager.getInstance().startLogcatManager(this, Constant.ADB_LOGCAT);
        }
    }

    private void stopLogcatManager() {
        if (Constant.DEBUG) {
            LogcatManager.getInstance().stopLogcatManager();
        }
    }
    //application 终结时
    @Override
    public void onTerminate() {
        super.onTerminate();
        stopLogcatManager();
        EventBusUtils.unregister(this);
    }

    public void onEventMainThread(ApiEventBean event) {
        Log.i("alsan", "event start...");
        if(event != null){
            Log.i("alsan", event.getMsg());
            ToastUtil.toast(event.getMsg());
        }
    }
}

package com.beiyongjin.byg.views;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.beiyongjin.byg.common.Constant;
import com.beiyongjin.byg.common.RouterUrl;
import com.beiyongjin.byg.utils.Util;
import com.chad.library.adapter.base.BaseViewHolder;
import com.erongdu.wireless.greendao.dao.DaoMaster;
import com.erongdu.wireless.tools.utils.ConverterUtil;
import com.erongdu.wireless.tools.utils.StringFormat;
import com.erongdu.wireless.tools.utils.ToastUtil;
import com.erongdu.wireless.views.LeftRightLayout;
import com.erongdu.wireless.views.NoDoubleClickTextView;
import com.beiyongjin.byg.R;
import com.beiyongjin.byg.common.FeatureConfig;
import com.beiyongjin.byg.common.QuickAdapter;
import com.beiyongjin.byg.databinding.FeeDetailItemBinding;
import com.beiyongjin.byg.module.home.dataModel.HomeChoiceRec;
import com.beiyongjin.byg.module.home.dataModel.HomeFeeDetailItemRec;
import com.github.mzule.activityrouter.router.Routers;

import java.util.List;

/**
 * Author: TaoHao
 * E-mail: taoh@erongdu.com
 * Date: 2017/05/05$ 14:34$
 * <p/>
 * Description:
 */
public class ServiceDialog extends Dialog implements View.OnClickListener {
    private ServiceDialog            self;
    private NoDoubleClickTextView button;
    private ImageView close;
    private ImageView callPhone;
    private RelativeLayout callWechat;
    private RelativeLayout call_qq;

    /** 是否配置详情 */
    //private boolean isFeeDetail = FeatureConfig.enableFeature(FeatureConfig.enableServiceDetailFeature);


    public ServiceDialog(Context context) {
        super(context, R.style.CostDialog);
        this.self = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.service_dialog);
        callPhone = (ImageView) findViewById(R.id.call_telphone);
        callWechat = (RelativeLayout) findViewById(R.id.call_wechat);
        call_qq = (RelativeLayout) findViewById(R.id.call_qq);
        close = (ImageView) findViewById(R.id.service_close);
        call_qq.setOnClickListener(this);
        callPhone.setOnClickListener(this);
        callWechat.setOnClickListener(this);
       close.setOnClickListener(this);


    }


    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
                //拨打客服电话
            case R.id.call_telphone:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ Constant.ServiceTelPhone));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);

                break;
            //跳转到微信的二维码界面
            case R.id.call_wechat:
                if (Util.checkApkExist(v.getContext(),"com.tencent.mm")){
                    Routers.open(v.getContext(),RouterUrl.getRouterUrl(RouterUrl.Mine_ServiceWeChat));

                }else {
                    ToastUtil.toast("本机未安装微信应用");
                }

                break;
            //跳转到qq客服聊天界面
            case R.id.call_qq:
                if (Util.checkApkExist(v.getContext(), "com.tencent.mobileqq")){
                    Util.getActivity(v).startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=2323781245&version=1")));
                }else{
                    ToastUtil.toast("本机未安装QQ应用");
                }

                break;
            //关闭弹出框
            case R.id.service_close:
                this.dismiss();
                break;


        }
        this.dismiss();
        //self.dismiss();
    }



}

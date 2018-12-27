package com.beiyongjin.byg.module.user.viewControl;

import android.view.View;

import com.erongdu.wireless.network.entity.HttpResult;
import com.erongdu.wireless.tools.utils.ActivityManage;
import com.erongdu.wireless.tools.utils.ContextHolder;
import com.erongdu.wireless.tools.utils.ToastUtil;
import com.beiyongjin.byg.R;
import com.beiyongjin.byg.module.mine.dataModel.recive.InfoRec;
import com.beiyongjin.byg.module.mine.dataModel.submit.UpdatePwdSub;
import com.beiyongjin.byg.module.user.viewModel.SettingsUpdatePwdVM;
import com.beiyongjin.byg.module.user.ui.activity.SettingsUpdatePwdAct;
import com.beiyongjin.byg.network.RDClient;
import com.beiyongjin.byg.network.RequestCallBack;
import com.beiyongjin.byg.network.api.MineService;
import com.beiyongjin.byg.utils.DisplayFormat;
import com.beiyongjin.byg.utils.InputCheck;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: JinFu
 * E-mail: jf@erongdu.com
 * Date: 2017/2/21 14:37
 * <p/>
 * Description:修改密码 {@link SettingsUpdatePwdAct}
 */
public class SettingsUpdatePwdCtrl {
    private SettingsUpdatePwdVM pwdVM;

    public SettingsUpdatePwdCtrl() {
        pwdVM = new SettingsUpdatePwdVM();
        reqinit();
    }

    /** 请求个人信息 */
    private void reqinit() {
        Call<HttpResult<InfoRec>> call = RDClient.getService(MineService.class).getInfo();
        call.enqueue(new RequestCallBack<HttpResult<InfoRec>>() {
            @Override
            public void onSuccess(Call<HttpResult<InfoRec>> call, Response<HttpResult<InfoRec>> response) {
                if (null != response.body()) {
                    pwdVM.setPhone(DisplayFormat.accountHideFormat(response.body().getData().getPhone()));
                }
            }
        });
    }

    /** 修改密码 */
    public void submit(View view) {
        if (!pwdVM.getConfirmPwd().equals(pwdVM.getNewPwd())) {
            ToastUtil.toast(R.string.seetings_pwd_tips);
            return;
        }
        if (!InputCheck.checkPwd(pwdVM.getPwd()) || !InputCheck.checkPwd(pwdVM.getNewPwd()) || !InputCheck.checkPwd(pwdVM.getConfirmPwd())) {
            ToastUtil.toast(ContextHolder.getContext().getString(R.string.settings_pwd_desc));
            return;
        }
        Call<HttpResult> call = RDClient.getService(MineService.class).updatePwd(new UpdatePwdSub(pwdVM.getNewPwd(), pwdVM.getPwd()));
        call.enqueue(new RequestCallBack<HttpResult>() {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                ToastUtil.toast(response.body().getMsg());
                ActivityManage.pop();
            }
        });
    }

    public SettingsUpdatePwdVM getPwdVM() {
        return pwdVM;
    }
}

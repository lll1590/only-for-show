package com.beiyongjin.byg.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.beiyongjin.byg.R;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/3/30 下午3:28
 * <p/>
 * Description: 分享弹出窗口()
 */
public class ShareDialog extends Dialog {
    public  View     shareView;
    private TextView title;

    public ShareDialog(Context context) {
        this(context, null);
    }

    public ShareDialog(Context context, View.OnClickListener shareClick) {
        super(context, R.style.CustomDialog);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        shareView = inflater.inflate(R.layout.share_popup_window, null);
        title = (TextView) shareView.findViewById(R.id.share_title);
        shareView.findViewById(R.id.share_wechat).setOnClickListener(shareClick);
        shareView.findViewById(R.id.share_wxcircle).setOnClickListener(shareClick);
        shareView.findViewById(R.id.share_qq).setOnClickListener(shareClick);
        shareView.findViewById(R.id.share_qq_zone).setOnClickListener(shareClick);
        shareView.findViewById(R.id.share_qr_code).setOnClickListener(shareClick);
        shareView.findViewById(R.id.share_cancel).setOnClickListener(shareClick);
        Window window = getWindow();
        window.setContentView(shareView);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setWindowAnimations(R.style.PopupAnimation);
        // shareView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        shareView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = title.getTop();
                int y      = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    /***
     * 设置二维码
     * @param bitmap
     */
    public void setQrCode(Bitmap bitmap) {
        ((ImageView) shareView.findViewById(R.id.iv_qr_code)).setImageBitmap(bitmap);
    }

    /***
     * 展示二维码
     */
    public void showQrCode() {
        shareView.findViewById(R.id.ll_share).setVisibility(View.GONE);
        shareView.findViewById(R.id.ll_qr_code).setVisibility(View.VISIBLE);
    }

    /***
     * 重置弹出框
     */
    public void restDialog() {
        shareView.findViewById(R.id.ll_share).setVisibility(View.VISIBLE);
        shareView.findViewById(R.id.ll_qr_code).setVisibility(View.GONE);
    }
}

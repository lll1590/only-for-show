package com.beiyongjin.byg.views;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseViewHolder;
import com.erongdu.wireless.tools.utils.ConverterUtil;
import com.erongdu.wireless.tools.utils.StringFormat;
import com.erongdu.wireless.views.LeftRightLayout;
import com.erongdu.wireless.views.NoDoubleClickTextView;
import com.beiyongjin.byg.R;
import com.beiyongjin.byg.common.FeatureConfig;
import com.beiyongjin.byg.common.QuickAdapter;
import com.beiyongjin.byg.databinding.FeeDetailItemBinding;
import com.beiyongjin.byg.module.home.dataModel.HomeChoiceRec;
import com.beiyongjin.byg.module.home.dataModel.HomeFeeDetailItemRec;

import java.util.List;

/**
 * Author: TaoHao
 * E-mail: taoh@erongdu.com
 * Date: 2017/05/05$ 14:34$
 * <p/>
 * Description:
 */
public class CostDialog extends Dialog implements View.OnClickListener {
    private CostDialog            self;
    private NoDoubleClickTextView button;
    /** 是否配置详情 */
    private boolean isFeeDetail = FeatureConfig.enableFeature(FeatureConfig.enablefeeDetailFeature);
    private HomeChoiceRec rec;

    public CostDialog(Context context, HomeChoiceRec rec) {
        super(context, R.style.CostDialog);
        this.rec = rec;
        this.self = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.cost_dialog);
        button = (NoDoubleClickTextView) findViewById(R.id.dialog_button);
        button.setOnClickListener(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.fee_detail);
        LinearLayout detail       = (LinearLayout) findViewById(R.id.detail);
        if (isFeeDetail) {
            detail.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            FeeDetailAdapter adapter = new FeeDetailAdapter(rec.getFeeDetailList());
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            LeftRightLayout total = (LeftRightLayout) findViewById(R.id.total);
//            total.setRightText(StringFormat.subZeroAndDot(ConverterUtil.getDouble(rec.getFee())) + "元");
            total.setRightText(StringFormat.twoDecimalFormat(ConverterUtil.getDouble(rec.getFee())) + "元");
        } else {
            detail.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            LeftRightLayout loan_interest      = (LeftRightLayout) findViewById(R.id.loan_interest);
            LeftRightLayout credit_inquiry_fee = (LeftRightLayout) findViewById(R.id.credit_inquiry_fee);
            LeftRightLayout service_charge     = (LeftRightLayout) findViewById(R.id.service_charge);
            LeftRightLayout total              = (LeftRightLayout) findViewById(R.id.total);
            loan_interest.setRightText(StringFormat.subZeroAndDot(rec.getFeeDetail().getInterest()) + "元");
            credit_inquiry_fee.setRightText(StringFormat.subZeroAndDot(rec.getFeeDetail().getInfoAuthFee()) + "元");
            service_charge.setRightText(StringFormat.subZeroAndDot(rec.getFeeDetail().getServiceFee()) + "元");
            total.setRightText(StringFormat.subZeroAndDot(ConverterUtil.getDouble(rec.getFeeDetail().getInterest()) +
                    ConverterUtil.getDouble(rec.getFeeDetail().getInfoAuthFee()) +
                    ConverterUtil.getDouble(rec.getFeeDetail().getServiceFee())) + "元");
        }
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        self.dismiss();
    }

    class FeeDetailAdapter extends QuickAdapter<HomeFeeDetailItemRec, FeeDetailAdapter.DetailViewHolder> {
        public FeeDetailAdapter(List<HomeFeeDetailItemRec> data) {
            super(data);
        }

        @Override
        protected void convert(DetailViewHolder detailViewHolder, HomeFeeDetailItemRec rec) {
            FeeDetailItemBinding binding = detailViewHolder.getBinding();
            binding.feeDetailItem.setLeftText(rec.getTitle());
            binding.feeDetailItem.setRightText(StringFormat.twoDecimalFormat(Double.parseDouble(rec.getValue())) + "元");
        }

        /** 创建item viewHolder */
        @Override
        protected DetailViewHolder createBaseViewHolder(View view) {
            return new DetailViewHolder(view);
        }

        @Override
        protected View getItemView(int layoutResId, ViewGroup parent) {
            FeeDetailItemBinding binding = DataBindingUtil.inflate(mLayoutInflater, R.layout.fee_detail_item, parent, false);
            if (binding == null) {
                return super.getItemView(layoutResId, parent);
            }
            View view = binding.getRoot();
            view.setTag(R.id.linker_item, binding);
            return view;
        }

        class DetailViewHolder extends BaseViewHolder {
            public DetailViewHolder(View itemView) {
                super(itemView);
            }

            public FeeDetailItemBinding getBinding() {
                return (FeeDetailItemBinding) getConvertView().getTag(R.id.linker_item);
            }
        }
    }
}

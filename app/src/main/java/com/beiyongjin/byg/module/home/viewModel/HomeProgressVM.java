package com.beiyongjin.byg.module.home.viewModel;

import com.beiyongjin.byg.BR;
import com.beiyongjin.byg.R;
import com.beiyongjin.byg.common.ui.BaseRecyclerViewVM;
import com.beiyongjin.byg.module.repay.viewModel.LoanProgressVM;

import me.tatarka.bindingcollectionadapter.ItemView;

/**
 * Author: JinFu
 * E-mail: jf@erongdu.com
 * Date: 2017/2/18 16:00
 * <p>
 * Description:
 */
public class HomeProgressVM extends BaseRecyclerViewVM<LoanProgressVM> {
    @Override
    protected void selectView(ItemView itemView, int position, LoanProgressVM item) {
        itemView.set(BR.item, R.layout.list_item_home_progress);
    }
}

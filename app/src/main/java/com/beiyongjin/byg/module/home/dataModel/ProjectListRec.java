package com.beiyongjin.byg.module.home.dataModel;

import java.util.List;

/**
 * Author: TaoHao
 * E-mail: taoh@erongdu.com
 * Date: 2016/10/27$ 16:10$
 * <p/>
 * Description: 列表外层({@link ProjectItemRec})
 */
public class ProjectListRec {
    List<ProjectItemRec> choicelist;
    List<ProjectItemRec> novicelist;

    public List<ProjectItemRec> getChoicelist() {
        return choicelist;
    }

    public List<ProjectItemRec> getNovicelist() {
        return novicelist;
    }
}

package com.yerina.earthquake.domain.request;

import lombok.Data;

/**
 * Created by philip on 2016-10-07.
 */
@Data
public class ShelterReq {

    SelectList selectList = new SelectList();

    public void setPageIndex(int pageIndex) {
        selectList.setPageIndex(pageIndex);
    }
    public void setQ_area_cd_1(String area1) {
        selectList.setQ_area_cd_1(area1);
    }
    public void setQ_area_cd_2(String area2) {
        selectList.setQ_area_cd_2(area2);
    }
    public void setQ_area_cd_3(String area3) {
        selectList.setQ_area_cd_3(area3);
    }
}

@Data
class SelectList{

	int pageIndex =1;
    String searchGb ="pageSearch";
    String q_area_cd_1;
    String q_area_cd_2;
    String q_area_cd_3;
    String q_equp_type="S";
    String gubunCode="CO7NN00006";

}
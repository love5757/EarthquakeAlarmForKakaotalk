package com.yerina.earthquake.domain.request;

import lombok.Data;

/**
 * Created by philip on 2016-10-06.
 */
@Data
public class RegionReq {

    ReqInfo reqInfo = new ReqInfo();

    public void setOrgCd(String orgCd) {
        reqInfo.setUpperOrgCd(orgCd);
    }
}

@Data
class ReqInfo{

    String upperOrgCd ="0";

}
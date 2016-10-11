package com.yerina.earthquake.domain.earthquake;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Region {

    String orgCd;
    String arcd;
    String fllOrgNm;
    String orgNm;
    String upperOrgCd;
    String orgAcctoStepSeCd;
    String useAt;
    int totCnt;
    int num;

}

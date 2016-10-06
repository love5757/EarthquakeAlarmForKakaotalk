package com.yerina.earthquake.domain;

import lombok.Data;
import lombok.ToString;

import java.util.List;


/**
 * Created by philip on 2016-10-06.
 */

@ToString
@Data
public class RegionResult {
    rtnResult rtnResult;
    List<Region> List;
}

@Data
@ToString
class rtnResult{
    String resultMsg;
    int totCnt;
    int pageSize;
    int resultCode;
}


package com.yerina.earthquake.domain.result;

import com.yerina.earthquake.domain.earthquake.Shelter;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by philip on 2016-10-07.
 */

@ToString
@Data
public class ShelterResult {
    rtnShelterResult rtnResult;
    List<Shelter> List;

    public int getPageSize(){
        return rtnResult.getPageSize();
    }
}

@Data
@ToString
class rtnShelterResult{
    String resultMsg;
    int totCnt;
    int pageSize;
    int resultCode;
}
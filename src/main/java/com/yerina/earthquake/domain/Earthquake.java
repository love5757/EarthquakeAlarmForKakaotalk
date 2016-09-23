package com.yerina.earthquake.domain;

import lombok.Data;

import java.util.Date;

/**
 * Created by philip on 2016-09-22.
 */
@Data
public class Earthquake {

    private int num;              // 번호(index)
    private Date earthQuakeTime;        // 지진 발생시간
    private double earthQuakeSacle;        // 지진 규모
    private double latitude;          // 위도
    private double longitude;          // 경도
    private String earthQuakeArea;        // 지진 발생지역

    // 마지막 지진 index 값
    private int lastCount;          // 가장 최근번호


}

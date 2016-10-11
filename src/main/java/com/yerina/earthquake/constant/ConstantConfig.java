package com.yerina.earthquake.constant;

/**
 * Created by philip on 2016-09-22.
 */
public enum ConstantConfig {

    URL ("URL","http://necis.kma.go.kr/necis-dbf/user/earthquake/annual_earthquake.do"),
    LASTEARTHQUAKE_ELEMENTS ("LASTEARTHQUAKE_ELEMENTS","div.yearly_earthquake tbody tr"),

    REGSION_POST_URL("url","http://www.safekorea.go.kr/idsiSFK/sfk/ca/cac/are2/area2List.do"),
    SHELTER_POST_URL("url","http://www.safekorea.go.kr/idsiSFK/sfk/cs/cvi/egf/selectMsfrtnTrlSport.do"),


    LASTCOUNT_ELEMENTS ("LASTCOUNT_ELEMENTS","div.totalCount b");

    private String code;
    private String desc;

    ConstantConfig(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    public String getCode() {
        return this.code;
    }
    public String getDesc() {
        return this.desc;
    }


}

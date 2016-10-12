package com.yerina.earthquake.constant;

import java.util.Arrays;
import java.util.List;

/**
 * Created by philip on 2016-10-10.
 */
public class Constant {
    public final static List DEFAULT_BUTTONS = Arrays.asList("1. 최근 지진 정보", "2. 대피요령", "3. 대피소 찾기");
    public final static String KMA_NECIS_URL = "http://necis.kma.go.kr/necis-dbf/usermain/new/common/userMainNewForm.do";
    public final static String LASTEARTHQUAKE_ELEMENTS ="div.yearly_earthquake tbody tr";

    public final static String REGSION_POST_URL = "http://www.safekorea.go.kr/idsiSFK/sfk/ca/cac/are2/area2List.do";
    public final static String SHELTER_POST_URL = "http://www.safekorea.go.kr/idsiSFK/sfk/cs/cvi/egf/selectMsfrtnTrlSport.do";


    public final static String LEVEL1 = "극소수의 사람을 제외하고는 전혀 느낄 수 없는 수준";
    public final static String LEVEL2 = "소수의 사람들, 특히 건물의 윗 층에 있는 소수의 사람들만 느낄 수 있는 수준으로 섬세하게 매달린 물체 진동";
    public final static String LEVEL3 = "거의 모든 사람들이 느끼는 수준\n밤에 잠을 깨며 그릇, 창문, 문 등이 소란하며 벽이 갈라지는 소리를 냄. 대형 트럭이 벽을 받는 느낌을 주고 정지하고 있는 자동차의 움직임이 뚜렷함";
    public final static String LEVEL4 = "모든 사람들이 밖으로 뛰어 나오는 수준 \n잘 설계된 건물에 피해가 없을 수 있으나 보통 건축물에는 약간의 피해가 있으며, 부실한 건축물에는 큰 피해가 발생하고 운전자가 느낄 수 있음.";
    public final static String LEVEL5 = "창벽이 무너지고 굴뚝, 기둥, 벽들이 무너짐. 무거운 가구가 뒤집어 지며 모래와 진흙이 솟아남. 우물 수면이 변하고 운전자가 방해를 받음.";
    public final static String LEVEL6 = "교량이 부서지고 땅에 넓은 균열이 발생되며, 지하 파이프가 완전히 파괴됨. 연약한 땅이 푹 꺼지고 지층이 어긋나며, 기차선로가 심하게 휘어짐.\n지표면에 파동이 보이고 시야와 수평면이 뒤틀리고 물체가 하늘로 던져짐.";

}

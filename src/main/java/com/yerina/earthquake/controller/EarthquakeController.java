package com.yerina.earthquake.controller;

import com.yerina.earthquake.constant.Constant;
import com.yerina.earthquake.domain.earthquake.Earthquake;
import com.yerina.earthquake.domain.earthquake.Region;
import com.yerina.earthquake.domain.earthquake.Shelter;
import com.yerina.earthquake.domain.message.Keyboard;
import com.yerina.earthquake.domain.message.Message;
import com.yerina.earthquake.domain.message.MessageButton;
import com.yerina.earthquake.domain.message.Photo;
import com.yerina.earthquake.domain.request.RequestMessage;
import com.yerina.earthquake.domain.response.ResponseMessage;
import com.yerina.earthquake.service.inf.EarthquakeService;
import com.yerina.earthquake.service.inf.SearchShelterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by philip on 2016-09-22.
 */

@RestController
@RequestMapping("/earthquake")
public class EarthquakeController {

    private static final Logger logger = LoggerFactory.getLogger(EarthquakeController.class);

    @Autowired
    private EarthquakeService earthquakeService;
    @Autowired
    private SearchShelterService searchShelterService;

    public static ConcurrentHashMap<String, List<Region>> regionState = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, List<Shelter>> shelterState = new ConcurrentHashMap<>();

    @RequestMapping(value = "/keyboard", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public Keyboard homeKeyboardAPI() {

        Keyboard keyboardResponse = new Keyboard();
        keyboardResponse.setType("buttons");
        keyboardResponse.setButtons(Constant.DEFAULT_BUTTONS);
        logger.debug("[keyboardResponse][{}]",keyboardResponse);

        return keyboardResponse;
    }

    @RequestMapping(value = "/message", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public Object receiveAndAutoReplyAPI(@RequestBody RequestMessage requestMessage) {
        logger.info("[request message][{}]", requestMessage);

        ResponseMessage responseMessage = new ResponseMessage();
        Message message = new Message();
        Photo photo = new Photo();

        Keyboard keyboard = new Keyboard();
        keyboard.setType("buttons");
        keyboard.setButtons(Constant.DEFAULT_BUTTONS);

        if(requestMessage.getContent().contains("Cancel")){
            message.setText("모든 작업이 취소 되었습니다.");
            responseMessage.setKeyboard(keyboard);
            responseMessage.setMessage(message);

            //저장된 사용자 데이터 제거
            regionState.remove(requestMessage.getUser_key());
            //저장된 사용자 대피소 제거
            shelterState.remove(requestMessage.getUser_key());

        }else if(requestMessage.getContent().startsWith("1")){
            final List<Earthquake> infoEarthquake1 = earthquakeService.getInfoEarthquake();

            StringBuffer earthquakeInfo = new StringBuffer();
            earthquakeInfo.append("최근 발생 지진 현황 정보\n\n");

            for (Earthquake earthquake : infoEarthquake1) {
                earthquakeInfo.append("-----------------\n");
                earthquakeInfo.append("강도 : "+earthquake.getEarthQuakeSacle()+"\n");
                earthquakeInfo.append("시간 : "+earthquake.getEarthQuakeTime()+"\n");
                earthquakeInfo.append("위치 : "+earthquake.getEarthQuakeArea()+"\n");
            }
            
            message.setText(String.valueOf(earthquakeInfo));

            MessageButton messageButton = new MessageButton("국가지진종합정보센터", Constant.KMA_NECIS_URL);
            message.setMessage_button(messageButton);
            responseMessage.setKeyboard(keyboard);
            responseMessage.setMessage(message);

        }else if(requestMessage.getContent().startsWith("2")){
            StringBuffer takeShelterString = new StringBuffer();
            takeShelterString.append("지진 대피 요령\n\n");
            takeShelterString.append("1. 집안에 있을 경우\n");
            takeShelterString.append("  - 테이블 밑으로 들어가 몸을 보호\n");
            takeShelterString.append("  - 가스밸브 및 전스 OFF\n");
            takeShelterString.append("  - 진동이 멈춘후 밖으로 대피 한다.\n\n");
            takeShelterString.append("2. 집 밖에 있을 경우\n");
            takeShelterString.append("  - 낙하물 주의 및 머리 보호\n\n");
            takeShelterString.append("3. 상가에 있을 경우\n");
            takeShelterString.append("  - 침착하게 행동\n\n");
            takeShelterString.append("4. 엘리베이터를 타고 있을 경우\n");
            takeShelterString.append("  - 가장 가까운 층에 내려 대피\n\n");
            takeShelterString.append("5. 전철을 타고 있는 경우\n");
            takeShelterString.append("  - 고정물을 꽉 잡자\n\n");
            takeShelterString.append("6. 운전을 하고 있는 경우\n");
            takeShelterString.append("  - 도로 우측에 정차한 후 대피\n\n");
            takeShelterString.append("7. 산이나 바다에 있을 경우\n");
            takeShelterString.append("  - 산사태 등 위험 지역 신속 대피\n\n");
            takeShelterString.append("8. 부상자가 있는 경우\n");
            takeShelterString.append("  - 서로 협력해서 응급 구호\n\n");
            takeShelterString.append("9. 피난은 마지막 수단\n");
            takeShelterString.append("  - 짐은 최소한으로 하고 대피는 걸어서\n\n");
            takeShelterString.append("10. 올바른 정보에 따라 행동\n");
            takeShelterString.append("  - 유언비어를 믿지 말자\n");

            message.setText(String.valueOf(takeShelterString));
            MessageButton messageButton = new MessageButton("이미지 보기", "http://www.kfpp.or.kr/Upload/BoardImages/%EC%A7%80%EC%A7%84%20%EB%8C%80%ED%94%BC%EC%9A%94%EB%A0%B9.jpg");
            message.setMessage_button(messageButton);
            responseMessage.setKeyboard(keyboard);
            responseMessage.setMessage(message);

        }else if(requestMessage.getContent().startsWith("3")){
            responseMessage = searchShelterService.showRegionList(requestMessage);
        }else if(requestMessage.getContent().startsWith(" ")){
            responseMessage = searchShelterService.findNearShelter(requestMessage);
        }else if(requestMessage.getContent().startsWith("시설")){
            responseMessage = searchShelterService.selectShelterInfomation(requestMessage);
        }

        return responseMessage;
    }

    @RequestMapping(value = "/friend", method = { RequestMethod.POST}, produces = "application/json; charset=utf-8")
    public void friendADD(@RequestBody String  user_key) {
        logger.debug("[친구 추가][{}]",user_key);
    }

    @RequestMapping(value = "/friend/{user_key}", method = {RequestMethod.DELETE}, produces = "application/json; charset=utf-8")
    public void friendBlock(@PathVariable("user_key") String  userKey) {
        logger.info("[친구 삭제/차단][{}]",userKey);
        regionState.remove(userKey);
        shelterState.remove(userKey);
    }

    @RequestMapping(value = "/chat_room/{user_key}", method = RequestMethod.DELETE, produces = "application/json; charset=utf-8")
    public void chatRoomLeave(@PathVariable("user_key") String  userKey) {
        logger.info("[채팅방 나가기][{}]",userKey);
        regionState.remove(userKey);
        shelterState.remove(userKey);
    }

}

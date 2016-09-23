package com.yerina.earthquake.controller;

import com.yerina.earthquake.domain.*;
import com.yerina.earthquake.service.inf.EarthquakeService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * Created by philip on 2016-09-22.
 */

@RestController
@RequestMapping("/earthquake")
public class EarthquakeController {

    private static final Logger logger = LoggerFactory.getLogger(EarthquakeController.class);

    @Autowired
    private EarthquakeService earthquakeService;

    @RequestMapping(value = "/keyboard", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public Keyboard homeKeyboardAPI(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        Keyboard keyboardResponse = new Keyboard();
        keyboardResponse.setType("buttons");
        keyboardResponse.setButtons(Arrays.asList("1. 최근 지진 정보", "2. 대피요령"));
        logger.debug("[keyboardResponse][{}]",keyboardResponse);

        return keyboardResponse;
    }

    @RequestMapping(value = "/message", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public Object receiveAndAutoReplyAPI(@RequestBody RequestMessage requestMessage) {
        logger.debug("[request message][{}]", requestMessage);

        ResponseMessage responseMessage = new ResponseMessage();
        Message message = new Message();
        Photo photo = new Photo();

        Keyboard keyboard = new Keyboard();
        keyboard.setType("buttons");
        keyboard.setButtons(Arrays.asList("1. 최근 지진 정보", "2. 대피요령"));

        if(requestMessage.getContent().startsWith("1")){
            final Earthquake infoEarthquake = earthquakeService.getInfoEarthquake();
            message.setText("강도 : "+infoEarthquake.getEarthQuakeSacle()+ "\n" +
                            "발생 시간 : " +infoEarthquake.getEarthQuakeTime() +"\n" +
                            "위치 :" + infoEarthquake.getEarthQuakeArea() +"\n"
            );
            MessageButton messageButton = new MessageButton("국가지진종합정보센터", "http://necis.kma.go.kr/necis-dbf/usermain/new/common/userMainNewForm.do");
            message.setMessage_button(messageButton);
            responseMessage.setKeyboard(keyboard);
            responseMessage.setMessage(message);

        }else if(requestMessage.getContent().startsWith("2")){
            message.setText(
                    "지진 대피 요령\n\n" +
                    "1. 집안에 있을 경우\n"+
                    "  - 테이블 밑으로 들어가 몸을 보호\n" +
                    "  - 가스밸브 및 전스 OFF\n" +
                    "  - 진동이 멈춘후 밖으로 대피 한다.\n\n" +
                    "2. 집 밖에 있을 경우\n"+
                    "  - 낙하물 주의 및 머리 보호\n\n" +
                    "3. 상가에 있을 경우\n"+
                    "  - 침착하게 행동\n\n" +
                    "4. 엘리베이터를 타고 있을 경우\n"+
                    "  - 가장 가까운 층에 내려 대피\n\n" +
                    "5. 전철을 타고 있는 경우\n"+
                    "  - 고정물을 꽉 잡자\n\n" +
                    "6. 운전을 하고 있는 경우\n"+
                    "  - 도로 우측에 정차한 후 대피\n\n" +
                    "7. 산이나 바다에 있을 경우\n"+
                    "  - 산사태 등 위험 지역 신속 대피\n\n" +
                    "8. 부상자가 있는 경우\n"+
                    "  - 서로 협력해서 응급 구호\n\n" +
                    "9. 피난은 마지막 수단\n"+
                    "  - 짐은 최소한으로 하고 대피는 걸어서\n\n" +
                    "10. 올바른 정보에 따라 행동\n"+
                    "  - 유언비어를 믿지 말자\n"
            );
            MessageButton messageButton = new MessageButton("이미지 보기", "http://www.kfpp.or.kr/Upload/BoardImages/%EC%A7%80%EC%A7%84%20%EB%8C%80%ED%94%BC%EC%9A%94%EB%A0%B9.jpg");
            message.setMessage_button(messageButton);
            responseMessage.setKeyboard(keyboard);
            responseMessage.setMessage(message);
        }

        return responseMessage;
    }

    @RequestMapping(value = "/friend", method = { RequestMethod.POST}, produces = "application/json; charset=utf-8")
    public void friendADD(@RequestBody String  user_key) {
        logger.debug("[친구 추가][{}]",user_key);
    }

    @RequestMapping(value = "/friend/{user_key}", method = {RequestMethod.DELETE}, produces = "application/json; charset=utf-8")
    public void friendBlock(@PathVariable("user_key") String  userKey) {
        logger.debug("[친구 삭제/차단][{}]",userKey);
    }

    @RequestMapping(value = "/chat_room/{user_key}", method = RequestMethod.DELETE, produces = "application/json; charset=utf-8")
    public void chatRoomLeave(@PathVariable("user_key") String  userKey) {
        logger.debug("[채팅방 나가기][{}]",userKey);
    }

}

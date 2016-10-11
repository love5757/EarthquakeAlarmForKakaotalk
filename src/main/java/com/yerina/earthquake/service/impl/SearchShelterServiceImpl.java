package com.yerina.earthquake.service.impl;

import com.google.gson.Gson;
import com.yerina.earthquake.constant.ConstantConfig;
import com.yerina.earthquake.domain.earthquake.Region;
import com.yerina.earthquake.domain.earthquake.Shelter;
import com.yerina.earthquake.domain.message.Keyboard;
import com.yerina.earthquake.domain.message.Message;
import com.yerina.earthquake.domain.message.MessageButton;
import com.yerina.earthquake.domain.message.Photo;
import com.yerina.earthquake.domain.request.RegionReq;
import com.yerina.earthquake.domain.request.RequestMessage;
import com.yerina.earthquake.domain.request.ShelterReq;
import com.yerina.earthquake.domain.response.ResponseMessage;
import com.yerina.earthquake.domain.result.RegionResult;
import com.yerina.earthquake.domain.result.ShelterResult;
import com.yerina.earthquake.service.inf.SearchShelterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.yerina.earthquake.controller.EarthquakeController.regionState;
import static com.yerina.earthquake.controller.EarthquakeController.shelterState;

/**
 * Created by philip on 2016-10-06.
 */
@Service
public class SearchShelterServiceImpl implements SearchShelterService{

    private static final Logger logger = LoggerFactory.getLogger(SearchShelterServiceImpl.class);

    @Resource
    RestTemplate restTemplate;

    ResponseMessage responseMessage = new ResponseMessage();
    Message message = new Message();
    Photo photo = new Photo();
    Keyboard keyboard = new Keyboard();

    List<Region> fristRegdList = new ArrayList<>();

    public  <T, R> R  collectInformaionOfSafeKorea(String url, T requestDomain , R result ){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        final ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, requestDomain, String.class);
        return (R) new Gson().fromJson(stringResponseEntity.getBody(), result.getClass());
    }


    @Override
    public ResponseMessage showRegionList(RequestMessage requestMessage) {
        //대피소 검색 시작시 시도 구분값을 항시 업데이트
        fristRegdList = collectInformaionOfSafeKorea(ConstantConfig.REGSION_POST_URL.getDesc(), new RegionReq(), new RegionResult()).getList();

        StringBuffer regionHelpText = new StringBuffer();
        regionHelpText.append("시도, 시군구, 읍면동을\n 차례로 선택하세요.\n");
        message.setText(String.valueOf(regionHelpText));

        List<String> regionList = new ArrayList<>();
        regionList.add("Cancel(취소)");
        for (Region region : fristRegdList) {
            //앞의 공백을 넣어서 계속 해서 지역 호출
            regionList.add(" "+region.getFllOrgNm());
        }
        Keyboard regionKeyboard = new Keyboard();
        regionKeyboard.setType("buttons");
        regionKeyboard.setButtons(regionList);
        responseMessage.setKeyboard(regionKeyboard);

        responseMessage.setMessage(message);

        if(regionState.containsKey(requestMessage.getUser_key())){
            regionState.remove(requestMessage.getUser_key());
        }
        regionState.put(requestMessage.getUser_key(),new ArrayList<>());

        return responseMessage;
    }

    @Override
    public ResponseMessage findNearShelter(RequestMessage requestMessage) {

        Region foundSelectRegion = new Region();

        StringBuffer regionHelpText = new StringBuffer();
        List<String> nextRegionList = new ArrayList<>();
        nextRegionList.add("Cancel(취소)");
        RegionReq regionReq = new RegionReq();
        List<Region> userRegionList = regionState.get(requestMessage.getUser_key());

        if(userRegionList.isEmpty()){ //시도 선택으로  시군구 키보드 노출

            //선택된 시도의 orgCd를 찾아서 시군구 리스트 조회
            foundSelectRegion = fristRegdList
                    .stream()
                    .filter(region -> region.getFllOrgNm().replace(" ", "").equals(requestMessage.getContent().replace(" ", "")))
                    .reduce((first, second) -> second)
                    .orElseThrow(() -> new IllegalStateException("no element"));

            //선택한 시도 add
            userRegionList.add(foundSelectRegion);

            //선택된 시도의 orgCd로 시군구 리스트 조회
            regionReq.setOrgCd(foundSelectRegion.getOrgCd());
            final List<Region> searchSelectRegionList = collectInformaionOfSafeKorea(ConstantConfig.REGSION_POST_URL.getDesc(), regionReq, new RegionResult()).getList();

            //조회된 시군구 리스트 nextRegionList add
            searchSelectRegionList.forEach(region -> nextRegionList.add(" "+region.getFllOrgNm()));

            //안내 메세지
            regionHelpText.append("선택 주소 : ");
            regionHelpText.append(foundSelectRegion.getFllOrgNm());
            regionHelpText.append("\n시군구를 선택하세요..");
            message.setText(String.valueOf(regionHelpText));

        }else if(userRegionList.size() == 1){ //시군구 선택으로 읍면동 키보드 노출

            //선택된 시도의 orgCd로 시군구 조회
            final String secondRegionOrgCd = userRegionList.stream().reduce((frist, second) -> second).get().getOrgCd();
            regionReq.setOrgCd(secondRegionOrgCd);
            final List<Region> secondSearchSelectRegionList = collectInformaionOfSafeKorea(ConstantConfig.REGSION_POST_URL.getDesc(), regionReq, new RegionResult()).getList();

            //선택된 시군구의 Region
            foundSelectRegion = secondSearchSelectRegionList
                    .stream()
                    .filter(region -> region.getFllOrgNm().replace(" ", "").equals(requestMessage.getContent().replace(" ", "")))
                    .reduce((first, second) -> second)
                    .orElseThrow(() -> new IllegalStateException("no element"));

            userRegionList.add(foundSelectRegion);

            //선택된 시군구의 읍면동 조회
            regionReq.setOrgCd(foundSelectRegion.getOrgCd());
            final List<Region> thirdSearchSelectRegionList = collectInformaionOfSafeKorea(ConstantConfig.REGSION_POST_URL.getDesc(), regionReq, new RegionResult()).getList();

            //조회된 읍면동 리스트 nextRegionList add
            thirdSearchSelectRegionList.forEach(region -> nextRegionList.add(" "+region.getFllOrgNm()));

            //안내 메세지
            regionHelpText.append("선택 주소 : ");
            regionHelpText.append(foundSelectRegion.getFllOrgNm());
            regionHelpText.append("\n읍면동를 선택하세요..");
            message.setText(String.valueOf(regionHelpText));

        }else if(userRegionList.size() == 2){ // 읍면동 선택으로 대피소 키보드 노출
            //선택된 시군구의 orgCd로 읍면동 리스트조회
            final String thirdRegionOrgCd = userRegionList.stream().reduce((frist, second) -> second).get().getOrgCd();
            regionReq.setOrgCd(thirdRegionOrgCd);
            final List<Region> thirdSearchSelectRegionList = collectInformaionOfSafeKorea(ConstantConfig.REGSION_POST_URL.getDesc(), regionReq, new RegionResult()).getList();

            //선택된 읍면동 Region
            foundSelectRegion = thirdSearchSelectRegionList
                    .stream()
                    .filter(region -> region.getFllOrgNm().replace(" ", "").equals(requestMessage.getContent().replace(" ", "")))
                    .reduce((first, second) -> second)
                    .orElseThrow(() -> new IllegalStateException("no element"));
            userRegionList.add(foundSelectRegion);

            logger.info("[{Select Done User Region List}][{}]",userRegionList);

            ShelterReq shelterReq = new ShelterReq();
            shelterReq.setPageIndex(1);
            shelterReq.setQ_area_cd_1(userRegionList.get(0).getOrgCd());
            shelterReq.setQ_area_cd_2(userRegionList.get(1).getOrgCd());
            shelterReq.setQ_area_cd_3(userRegionList.get(2).getOrgCd());
            final int pageSize = collectInformaionOfSafeKorea(ConstantConfig.SHELTER_POST_URL.getDesc(),shelterReq,new ShelterResult()).getPageSize();

            List<Shelter> totalShelterResult = new ArrayList<>();

            for (int i= 1; i <= pageSize;i++){
                shelterReq.setPageIndex(i);
                collectInformaionOfSafeKorea(ConstantConfig.SHELTER_POST_URL.getDesc(),shelterReq,new ShelterResult()).getList().forEach(shelter -> totalShelterResult.add(shelter));
            }

            totalShelterResult.forEach(shelter -> nextRegionList.add("시설:"+shelter.getFACIL_NM()+" / 규모:"+shelter.getFACIL_POW()+shelter.getFACIL_UNIT()));

            //안내 메세지
            regionHelpText.append("["+foundSelectRegion.getFllOrgNm()+"]");
            regionHelpText.append("\n주변의 대피소 입니다.\n대피소를 눌러 위치를 확인하세요.");
            message.setText(String.valueOf(regionHelpText));

            //사용자별 대피소 리스트
            shelterState.put(requestMessage.getUser_key(), totalShelterResult);
        }

        Keyboard regionKeyboard = new Keyboard();
        regionKeyboard.setType("buttons");
        regionKeyboard.setButtons(nextRegionList);
        responseMessage.setKeyboard(regionKeyboard);
        responseMessage.setMessage(message);

        logger.debug("[responseMessage][{}]",responseMessage);

        return responseMessage;


    }

    @Override
    public ResponseMessage selectShelterInfomation(RequestMessage requestMessage) {
        logger.info("[shelterState][{}]",shelterState);
        List<String> shelterList = new ArrayList<>();
        shelterList.add("Cancel(취소)");
        shelterState.get(requestMessage.getUser_key()).
                forEach(shelter -> shelterList.add("시설:"+shelter.getFACIL_NM()+" / 규모:"+shelter.getFACIL_POW()+shelter.getFACIL_UNIT()));

        final Shelter shelterInfomation = shelterState.get(requestMessage.getUser_key())
                .stream()
                .filter(shelter -> shelter.getFACIL_NM().replace(" ", "").equals(requestMessage.getContent().split(":")[1].split("/")[0].replace(" ", "")))
                .reduce((fristShelter, secondShelter) -> secondShelter)
                .orElseThrow(() -> new IllegalStateException("no element"));

        //대피소 정보
        StringBuffer earthquakeInfo = new StringBuffer();
        earthquakeInfo.append("시설명 : "+ shelterInfomation.getFACIL_NM() +"\n\n");
        earthquakeInfo.append("신주소 : "+ shelterInfomation.getNEW_FACIL_RD_ADDR() +"\n\n");
        earthquakeInfo.append("구주소 : "+ shelterInfomation.getFACIL_RD_ADDR() +"\n\n");
        earthquakeInfo.append("규모 : "+ shelterInfomation.getFACIL_POW() +shelterInfomation.getFACIL_UNIT()+"\n\n");

        message.setText(String.valueOf(earthquakeInfo));
        MessageButton messageButton = new MessageButton("지도에서 확인", "http://maps.google.com/maps?q="+shelterInfomation.getFACIL_RD_ADDR());
        message.setMessage_button(messageButton);
        responseMessage.setKeyboard(keyboard);
        responseMessage.setMessage(message);

        Keyboard regionKeyboard = new Keyboard();
        regionKeyboard.setType("buttons");
        regionKeyboard.setButtons(shelterList);
        responseMessage.setKeyboard(regionKeyboard);
        responseMessage.setMessage(message);

        return responseMessage;
    }

}

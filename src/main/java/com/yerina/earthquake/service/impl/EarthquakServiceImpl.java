package com.yerina.earthquake.service.impl;

import com.yerina.earthquake.constant.Config;
import com.yerina.earthquake.domain.Earthquake;
import com.yerina.earthquake.service.inf.EarthquakeService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by philip on 2016-09-22.
 */
@Service
public class EarthquakServiceImpl implements EarthquakeService{

    private static final Logger logger = LoggerFactory.getLogger(EarthquakServiceImpl.class);

    public  Earthquake getInfoEarthquake()  {
        Earthquake earthquake = new Earthquake();

        Document doc = null;
        try {
            doc = Jsoup.connect(Config.URL.getDesc()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements lastEarthQuakeInfo = doc.select(Config.LASTEARTHQUAKE_ELEMENTS.getDesc());
        Elements lastCount = doc.select(Config.LASTCOUNT_ELEMENTS.getDesc());
        for(Element e: lastEarthQuakeInfo){
         logger.debug("[{}]",e.text());
        }
        // 가장 최근 번호 저장
        earthquake.setLastCount( Integer.parseInt(lastCount.text()));

        String str = lastEarthQuakeInfo.first().text();
        System.out.println(str);
        String[] values = str.split(" ");

        Date to = null;

        String from = values[1]+" "+values[2];
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            to = transFormat.parse(from);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 최근에 일어난 지진정보 earthquake 객체에 넣기
        earthquake.setNum(Integer.parseInt(values[0]));
        earthquake.setEarthQuakeTime(to);
        earthquake.setEarthQuakeSacle(Double.parseDouble(values[3]));
        earthquake.setLatitude(Double.parseDouble(values[4]));
        earthquake.setLongitude(Double.parseDouble(values[5]));
        earthquake.setEarthQuakeArea(values[6]+" "+values[7]+" "+values[8]+" "+values[9]+" "+ values[10]);

        logger.debug("[earthquake][{}]", earthquake);

        return earthquake;
    }

}

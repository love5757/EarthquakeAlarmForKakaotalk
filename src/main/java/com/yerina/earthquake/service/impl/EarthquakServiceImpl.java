package com.yerina.earthquake.service.impl;

import com.yerina.earthquake.constant.Constant;
import com.yerina.earthquake.domain.earthquake.Earthquake;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by philip on 2016-09-22.
 */
@Service
public class EarthquakServiceImpl implements EarthquakeService{

    private static final Logger logger = LoggerFactory.getLogger(EarthquakServiceImpl.class);

    public List<Earthquake> getInfoEarthquake()  {
        List<Earthquake> earthquakeListResult = new ArrayList<>();

        Document doc = null;
        try {
            doc = Jsoup.connect(Constant.KMA_NECIS_URL).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements earthQuakeInfo = doc.select(Constant.LASTEARTHQUAKE_ELEMENTS);

        for(Element element: earthQuakeInfo){
            Earthquake earthquake = new Earthquake();
            String[] values = element.text().split(" ");
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

            earthquakeListResult.add(earthquake);

            //최근 지진 5개 정보
            if(earthquakeListResult.size() == 5) break;

        }


        logger.debug("[earthquake][{}]", earthquakeListResult);

        return earthquakeListResult;
    }

}

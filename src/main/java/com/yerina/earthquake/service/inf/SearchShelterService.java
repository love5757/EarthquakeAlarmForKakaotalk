package com.yerina.earthquake.service.inf;

import com.yerina.earthquake.domain.request.RequestMessage;
import com.yerina.earthquake.domain.response.ResponseMessage;

/**
 * Created by philip on 2016-10-06.
 */
public interface SearchShelterService {

    ResponseMessage showRegionList(RequestMessage requestMessage);

    ResponseMessage findNearShelter(RequestMessage requestMessage);

    ResponseMessage selectShelterInfomation(RequestMessage requestMessage);


}

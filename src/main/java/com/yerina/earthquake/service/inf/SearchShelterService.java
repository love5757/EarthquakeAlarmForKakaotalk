package com.yerina.earthquake.service.inf;

import com.yerina.earthquake.domain.RegionResult;
import com.yerina.earthquake.domain.request.RegionReq;

/**
 * Created by philip on 2016-10-06.
 */
public interface SearchShelterService {

    RegionResult searchRegion(RegionReq orgCd);

}

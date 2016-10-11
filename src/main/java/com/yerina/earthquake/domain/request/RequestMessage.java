package com.yerina.earthquake.domain.request;

import lombok.Data;

/**
 * Created by philip on 2016-09-22.
 */
@Data
public class RequestMessage {

    private String user_key;
    private String type;
    private String content;

}

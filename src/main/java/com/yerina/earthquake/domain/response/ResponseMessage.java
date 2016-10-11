package com.yerina.earthquake.domain.response;

import com.yerina.earthquake.domain.message.Keyboard;
import com.yerina.earthquake.domain.message.Message;
import lombok.Data;

/**
 * Created by philip on 2016-09-22.
 */
@Data
public class ResponseMessage {

    private Message message;
    private Keyboard keyboard;

}

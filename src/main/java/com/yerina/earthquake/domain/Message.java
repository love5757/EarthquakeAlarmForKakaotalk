package com.yerina.earthquake.domain;

import lombok.Data;

/**
 * Created by philip on 2016-09-22.
 */
@Data
public class Message {

    private String text;
    private Photo photo;
    private MessageButton message_button;


}

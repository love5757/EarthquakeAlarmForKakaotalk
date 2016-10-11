package com.yerina.earthquake.domain.message;

import lombok.Data;

import java.util.List;

/**
 * Created by philip on 2016-09-22.
 */
@Data
public class Keyboard {
    private String type;
    private List<String> buttons;
}

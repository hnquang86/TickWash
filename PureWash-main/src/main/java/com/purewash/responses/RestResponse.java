package com.purewash.responses;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestResponse<T> {

    private int statusCode;

    private String error;

    // message can be String or ArrayList
    private Object message;

    private T data;


}


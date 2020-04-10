package com.chenkh.vchat.base.msg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MsgBody<T> {

    private boolean success;

    private String reason;

    private T body;

}

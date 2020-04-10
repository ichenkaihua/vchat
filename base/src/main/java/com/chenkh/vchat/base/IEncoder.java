package com.chenkh.vchat.base;

public interface IEncoder {

    boolean support(Object content);

     byte[] encode(Object content);

}

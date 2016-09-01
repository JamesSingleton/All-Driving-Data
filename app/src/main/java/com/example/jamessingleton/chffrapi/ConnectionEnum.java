package com.example.jamessingleton.chffrapi;

/**
 * Created by frank on 8/30/16.
 */
public enum ConnectionEnum {
    WIFI ("wifi"),MOBILE ("mobile");

    public final String value;

    ConnectionEnum(String value){
        this.value = value;
    }

}

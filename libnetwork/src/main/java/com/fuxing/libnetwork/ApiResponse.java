package com.fuxing.libnetwork;


/**
 * @author ：Create by lichunfu
 * @Date : 2020-04-10
 * Description:
 **/
public class ApiResponse<T> {
    public  boolean success;
    public  int status;
    public  String message;
    public  T body;
}

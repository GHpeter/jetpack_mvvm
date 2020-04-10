package com.fuxing.libnetwork.inter;

import java.lang.reflect.Type;

/**
 * @author ：Create by lichunfu
 * @Date : 2020-04-10
 * Description:
 **/
public interface Convert<T> {
    T convert(String response, Type type);

}

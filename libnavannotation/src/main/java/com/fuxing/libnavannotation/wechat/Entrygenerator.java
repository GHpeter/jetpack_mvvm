package com.fuxing.libnavannotation.wechat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ：Create by lichunfu
 * @Date : 2020-04-07
 * Description:传入包名和微信模板代码
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Entrygenerator {
    String packageName();
    Class<?> entryTemplete();
}

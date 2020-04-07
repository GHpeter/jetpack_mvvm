package com.fuxing.libnavannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @author ：Create by lichunfu
 * @Date : 2020-04-07
 * Description:
 **/
@Target(ElementType.TYPE)
public @interface ActivityDestination {
    String pageUrl();
    boolean needLogin() default  false;
    boolean  asStarter() default  false;
}

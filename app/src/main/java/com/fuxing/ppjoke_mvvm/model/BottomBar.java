package com.fuxing.ppjoke_mvvm.model;

import java.util.List;

/**
 * @author ：Create by lichunfu
 * @Date : 2020-04-08
 * Description:解析main_tabs_config对象
 **/
public class BottomBar {

    /**
     * activeColor : #333333
     * inActiveColor : #666666
     * selectTab : 0
     * tabs : [{"size":24,"enable":true,"index":0,"pageUrl":"main/tabs/home","title":"首页"}]
     */

    //选中的颜色
    public String activeColor;
    //默认的颜色
    public String inActiveColor;
    public int selectTab;

    public List<Tab> tabs;


    public static class Tab {
        /**
         * size : 24
         * enable : true
         * index : 0
         * pageUrl : main/tabs/home
         * title : 首页
         */

        public int size;
        //是否显示
        public boolean enable;
        //下标的位置
        public int index;
        public String pageUrl;
        //文本显示
        public String title;
        public String tintColor;


    }
}

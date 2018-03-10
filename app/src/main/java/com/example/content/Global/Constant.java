package com.example.content.Global;

/**
 * Created by 佳南 on 2017/9/22.
 */

public class Constant {
    public static final boolean debugable = true;//是否启动测试模式
    public static final String mainList = "http://ejoytest.21d.me:8080/RSP/app/main/mainList";
    public static final String subList = "http://ejoytest.21d.me:8080/RSP/app/sub/sublist";
    public static final String subListConcat = subList+"?appId=10000001&&mainId=";
    public static final String mainListLable = "http://ejoytest.21d.me:8080/RSP/app/main/mainListByLabel?appId=10000001&&labelName=";
    public static final String detailList = "http://ejoytest.21d.me:8080/RSP/app/sub/findOne?subId=";
    public static final int NETWORK_ON = 0;
    public static final int NETWORK_OFF = 1;
    public static final int NETWORK_wifi = 2;
    public static final int NETWORK_mobile = 3;
//    http://ejoytest.21d.me:8080/RSP/app/sub/sublist?appId=10000001&&mainId=EOjzuW1Yyv0qwnvRAhm
//    http://ejoytest.21d.me:8080/RSP/app/main/mainListByLabel?labelName=0-3
}

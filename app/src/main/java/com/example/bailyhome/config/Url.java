package com.example.bailyhome.config;

/**
 * Created by Administrator on 2016/5/17 0017.
 *
 */
public class Url {
    public final static String baseUrl = "http://101.200.120.92:9088/edus_auto/";
//    public final static String baseUrl = "http://192.168.6.131:8080/mj_oto_edus_auto_new/";
    //课程表接口
    public final static String courseTable = "model_ajaxList.do?&tableId=9547&mainTableId=&mainPageId=&mainId=&pageId=6054&source=1";
    //课程包接口
    public final static String coursePackage = "model_ajaxList.do?&tableId=462&mainTableId=&mainPageId=&mainId=&pageId=6336&source=1";
    //登陆接口
    public final static String loginUrl = "login_interfaceLogin.do?proId=56df76b60cf2beac602aaed3&source=1";
    //成绩接口
    public final static String scoreUrl = "model_ajaxList.do?tableId=462&pageId=6102&source=1";
    //作业成绩接口
    public final static String workScorUrl = "model_ajaxList.do?&tableId=17677&pageId=6640&AFM_16_2_andOr=0&AFM_16_strCond_pld=0&AFM_17_2_andOr=0&AFM_17_strCond_pld=0&source=1";
    //课首测试成绩
    public final static String firstTestUrl = "model_ajaxList.do?&tableId=9547&pageId=6103&AFM_15_2_andOr=0&AFM_15_strCond_pld=0&source=1";
    //阶段测评
    public final static String stateTestUrl = "model_ajaxList.do?tableId=17655&pageId=6051&AFM_22_2_andOr=0&AFM_22_strCond_pld=0&source=1";
    //总结报告---阶段测评
    public final static String sumStateTestUrl = "&AFM_24_2_andOr=0&AFM_24_strCond_pld=3";

    //模考成绩
    public final static String mokeTestUrl = "model_ajaxList.do?&tableId=9547&pageId=6107&AFM_20_2_andOr=0&AFM_20_strCond_pld=0&source=1";
    //考试成绩
    public final static String examscoreUrl = "model_ajaxList.do?&tableId=17634&pageId=6307&AFM_8_2_andOr=0&AFM_8_strCond_pld=0&AFM_9_2_andOr=0&AFM_9_strCond_pld=0&source=1";

    public final static String coursePackageDetailButtom = "model_ajaxList.do?" +
            "&tableId=9547&mainTableId=&mainPageId=&mainId=&pageId=6326&AFM_91_4_dicCond_pld=0&AFM_91_4_andOr=0&AFM_91_searchEle=&AFM_92_2_andOr=0&AFM_92_strCond_pld=0&AFM_93_4_dicCond_pld=0" +
            "&AFM_93_4_andOr=0&source=1";
    //请假记录
    public final static String leaveRecUrl = "model_ajaxList.do?&tableId=17381&mainTableId=&mainPageId=&mainId=&pageId=6068&source=1";
    //回访记录
    //public final static String reVisitRecUrl = "model_ajaxList.do?&tableId=9550&mainTableId=&mainPageId=&mainId=&pageId=6067&source=1";

    public final static String reVisitRecUrl = "model_ajaxList.do?&tableId=9550&mainTableId=&mainPageId=&mainId=&pageId=6629&AFM_16_2_andOr=0&AFM_16_strCond_pld=0&AFM_1_d_dicCond_pld=0&AFM_1_d_andOr=0&AFM_6_d_dicCond_pld=0&AFM_6_d_andOr=0&source=1";

    //阶段总结报告
    public final static String stateSumRepUrl = "/model_ajaxList.do?&tableId=17703&pageId=6098&AFM_41_2_andOr=0&AFM_41_strCond_pld=0&AFM_42_2_andOr=0&AFM_42_strCond_pld=0&source=1";

    //学员信息
    public final static String stuInfo = "model_ajaxList.do?&tableId=462&mainTableId=&mainPageId=&mainId=&pageId=6064&source=1";

    //阶段计划上半部分
    public final static String stagePlanTop = "model_ajaxList.do?" +
            "&tableId=17703&mainTableId=&mainPageId=&mainId=&pageId=6097&AFM_17_4_dicCond_pld=0&AFM_17_4_andOr=0&source=1";

    //阶段计划下半部分
    public final static String stagePlanButtom = "model_ajaxList.do?&tableId=17706&mainTableId=&mainPageId=&mainId=&pageId=6185&AFM_11_4_dicCond_pld=0&AFM_11_4_andOr=0&source=1";
    //修改密码
    public final static String changePsw = "phone_resetStuPassword.do?";
    //阶段总结----教师评价平均分
    public final static String sumTEvalScoreAvgUrl = "model_ajaxList.do?&tableId=9547&pageId=6518&AFM_8_2_andOr=0&AFM_8_strCond_pld=0&AFM_9_2_andOr=0&AFM_9_strCond_pld=0&AFM_10_2_andOr=0&AFM_10_strCond_pld=0&source=1";
}

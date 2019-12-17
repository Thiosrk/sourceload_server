package com.csga.sourceload_server.Utils.Data;

import com.alibaba.fastjson.JSONArray;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DataTestUtils {

    public static String testOne(String serviceId,
                                 String systemId,
                                 String requestUrl){

        String s = DataDownloadUtils.loaddata(serviceId,systemId,"","count(1) over()","","1","1",requestUrl);
        String result = JsonUtil.JsontoString(s,"result");
        String message = JsonUtil.JsontoString(s,"message");
        if (result.equals("true")){
            JSONArray data = (JSONArray) JsonUtil.toJson(s,"data");
            Integer count = data.getJSONObject(0).getInteger("count");
            return message+","+"{ "+serviceId+" }数据总量："+count;
        }else if (result.equals("false")){
            return "调用失败,异常信息："+message;
        }
        return "其他异常";

    }

    public static Map<String,String> testList(Map<String, String> sourceList,String requestUrl){
        Map<String,String> results = new HashMap<>();
        Set<String> keys =  sourceList.keySet();
        for (String systemId : keys){
            String serviceId = sourceList.get(systemId);
            String result = testOne(serviceId,systemId,requestUrl);
            results.put(systemId,result);
        }
        return results;

    }

    public static void main(String[] args) {

        String str = "STD_PER_TYDLPT_T_SYS_USER_CORE\t1186454892701736960\n" +
                "STD_EVT_DPT_AJ_CLCS_JGZXB\t1171322819582468096\n" +
                "STD_EVT_DPT_AJ_JBXX\t1171322819582468096\n" +
                "STD_EVT_DPT_AJ_SADW\t1171322819582468096\n" +
                "STD_EVT_DPT_AJ_SPXXB\t1171322819582468096\n" +
                "STD_PER_DPT_AJ_SHRXX\t1171322819582468096\n" +
                "STD_EVT_DPT_AJ_TH\t1171322819582468096\n" +
                "STD_PER_DPT_AJ_RY_ZHRY\t1171322819582468096\n" +
                "STD_ORG_DPT_JZWEB_TJBB_DICT_AJZT\t1171322819582468096\n" +
                "STD_PER_QB_T_BU_ZDRY\t1171322819582468096\n" +
                "STD_EVT_DPT_JCJ_CJXX\t1171322819582468096\n" +
                "STD_GOD_CG_VEHICLE\t1171322819582468096\n" +
                "STD_GOD_CG_VIO_SURVEIL\t1171322819582468096\n" +
                "STD_GOD_CG_DRIVINGLICENSE\t1171322819582468096\n" +
                "STD_GOD_RGZZRK_ZZRK_SJ\t1171322819582468096\n" +
                "STD_PER_WGHZL_T_LDRKXXB\t1171322819582468096\n" +
                "STD_ORG_ZALG_GASTHAUS\t1171322819582468096\n" +
                "STD_EVT_ZALG_GUEST\t1171322819582468096\n" +
                "STD_PER_ZCPT_T_YW_RK_QGPSRY\t1171322819582468096\n" +
                "STD_EVT_ZCPT_QGBDQJDC_CXB\t1171322819582468096\n" +
                "STD_EVT_ZCPT_QGBDQJDC_DJB\t1171322819582468096\n" +
                "STD_PER_STSJGL_QGZTRY_CXRY\t1171322819582468096\n" +
                "STD_PER_STSJGL_QGZTRY_DJRY\t1171322819582468096\n" +
                "STD_PER_RGCZRK_PERSON_INFO\t1171322819582468096\n" +
                "STD_PER_DPT_RY_JBXX\t1171322819582468096\n" +
                "STD_PER_DIM_Z_ZWRYXX\t1171322819582468096\n" +
                "STD_GOD_DPT_RS_WPXX\t1171322819582468096\n" +
                "STD_PER_DPT_RS_RYXXB\t1171322819582468096\n" +
                "STD_GOD_DPT_WP_SAWP\t1171322819582468096\n" +
                "STD_PER_DPT_JCJ_SJRY\t1171322819582468096\n" +
                "STD_ORG_ZCPT_T_YW_JG_WB_XML\t1171322819582468096\n" +
                "STD_EVT_ZALG_POLICE\t1171322819582468096\n" +
                "STD_EVT_DPT_BLXX\t1171326230734413824\n" +
                "STD_EVT_DPT_AJ_CLCS\t1171326230734413824\n" +
                "STD_EVT_DPT_AJ_RY_DCRY\t1171326230734413824\n" +
                "STD_EVT_DPT_AJ_FLWS\t1171326230734413824\n" +
                "STD_ORG_DPT_JZWEB_TJBB_DICT_AB\t1171326230734413824\n" +
                "STD_EVT_DPT_AJ_THAJ\t1171326230734413824\n" +
                "STD_EVT_DPT_AJ_THCY\t1171326230734413824\n" +
                "STD_EVT_DPT_AJ_XYRXX\t1171326230734413824\n" +
                "STD_EVT_DPT_AJ_ZRXX\t1171326230734413824\n" +
                "STD_EVT_DPT_JZWEB_TJBB_DICT_BZZM\t1171326230734413824\n" +
                "STD_ORG_ZALG_GASTHAUS\t1171326230734413824\n" +
                "STD_PER_RGCZRK_CZRK_CZRK\t1171326230734413824\n" +
                "STD_EVT_DPT_DIC_ITEMS_ST\t1171326230734413824\n" +
                "STD_ADR_DZK_DZ_XZQH\t1171326230734413824\n" +
                "STD_PER_CG_VIO_VIOLATION\t1171326230734413824\n" +
                "STD_PER_DPT_RY_JZXX\t1171326230734413824\n" +
                "STD_EVT_DPT_JCJ_JJXX\t1171326230734413824\n" +
                "STD_EVT_DPT_RS_DX\t1171326230734413824\n" +
                "STD_EVT_DPT_RS_THQD\t1171326230734413824\n" +
                "STD_EVT_DPT_RS_RYTXL\t1171326230734413824\n" +
                "STD_EVT_DPT_JCJ_SJDW\t1171326230734413824\n" +
                "STD_PER_CG_ACD_FILEHUMAN\t1171326230734413824\n" +
                "STD_ORG_TYDLPT_T_SYS_DEPT\t1171326230734413824\n" +
                "STD_ORG_TYDLPT_T_SYS_USER_DEPT_UUMS\t1171326230734413824\n" +
                "STD_ORG_RT_WJ_RY_ONLINE_INFO\t1171326230734413824\n" +
                "STD_ORG_RT_WJ_RY_OFFLINE_INFO\t1171326230734413824\n" +
                "STD_EVT_ZCPT_T_WFFZ_AJXX\t1171326230734413824\n" +
                "STD_PER_ZCPT_T_WFFZ_RYXX\t1171326230734413824\n" +
                "STD_EVT_DPT_WZ_DNAXX\t1171326230734413824\n" +
                "STD_EVT_STSJGL_T_XZ_ZTRY\t1171326230734413824\n" +
                "STD_EVT_PCS_T_RY_ZDRY_CG\t1171326230734413824\n" +
                "STD_PER_PCS_T_RY_ZDRY_GK\t1171326230734413824\n" +
                "STD_PER_DPT_RY_ZDRYGLXX\t1171326230734413824\n" +
                "STD_PER_QB_T_ZDRY_HZ\t1171326230734413824\n" +
                "STD_PER_PCS_T_RY_ZDRY\t1171326230734413824\n" +
                "STD_GOD_DPT_DIC_ITEMS\t1171326230734413824\n";

        Map map = new HashMap<String,String>();
        String[] a =str.split("\n");
        for (String b :a){
            String[] tmp = b.split("\t");
            map.put(tmp[0],tmp[1]);
            System.out.println(tmp[0]+"   ,id："+tmp[1]);
        }

//        System.out.println(testOne("1","1","1"));
    }




}

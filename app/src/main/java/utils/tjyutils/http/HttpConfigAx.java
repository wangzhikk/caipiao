package utils.tjyutils.http;


import com.cp.App;

import utils.wzutils.common.StringTool;

/**
 * Created by ishare on 2016/6/30.
 */
public class HttpConfigAx {

    public static void setBaseUrl(String baseUrl) {
        HttpConfigAx.baseUrl = baseUrl;
    }

    private static String baseUrl = "";
    public static String imIp="msg.ile18.club";
    public static int imPort=7901;

    public static String getBaseUrl() {
        if(App.isDebug){
            if(true)return baseUrl="https://api.ile18.club/";//唐总;
            String app="/caipiao_api/";
            if(StringTool.isEmpty(baseUrl)){
                baseUrl="http://192.168.88.54:8080";//唐总
                baseUrl="http://api.yxwh168.com";//唐总
                //baseUrl="http://192.168.88.83:8080";//廖正伟
                //baseUrl="http://192.168.88.82:8080";//尹仁明
                //baseUrl = "http://192.168.88.91:8080";//徐海斌
                //baseUrl="http://1831uo0347.51mypc.cn/";//徐海斌
                // return  "http://5743xhb03479.xicp.io/";//徐海斌
                //baseUrl = "http://192.168.88.34:8080";//测试服务器

                //baseUrl="http://192.168.88.108:8080/shop_app/";//服务器\
                //baseUrl="http://47.89.180.83:8080/shop_app/";
                //baseUrl="http://192.168.88.100:8080";//我
            }
            return baseUrl+app;
        }else {
            return "https://api.ile18.club/";
        }

    }

    /***
     * 一些静态文件的地址
     * @param path
     * @return
     */
    public static String getHtmlUrl(String path) {
        return getBaseUrl()+path;
    }
    public static String httpsKey="-----BEGIN CERTIFICATE-----\n" +
            "MIIGPjCCBSagAwIBAgIJAPMgyRxo6LsNMA0GCSqGSIb3DQEBCwUAMIG0MQswCQYD\n" +
            "VQQGEwJVUzEQMA4GA1UECBMHQXJpem9uYTETMBEGA1UEBxMKU2NvdHRzZGFsZTEa\n" +
            "MBgGA1UEChMRR29EYWRkeS5jb20sIEluYy4xLTArBgNVBAsTJGh0dHA6Ly9jZXJ0\n" +
            "cy5nb2RhZGR5LmNvbS9yZXBvc2l0b3J5LzEzMDEGA1UEAxMqR28gRGFkZHkgU2Vj\n" +
            "dXJlIENlcnRpZmljYXRlIEF1dGhvcml0eSAtIEcyMB4XDTE4MDYwNjA4MzIxMFoX\n" +
            "DTE5MDYwNTAzNDAwN1owPDEhMB8GA1UECxMYRG9tYWluIENvbnRyb2wgVmFsaWRh\n" +
            "dGVkMRcwFQYDVQQDEw5hcGkuaWxlMTguY2x1YjCCASIwDQYJKoZIhvcNAQEBBQAD\n" +
            "ggEPADCCAQoCggEBAIy1GCmhd2EBJJT02cGI4RRfIaHJahCVBMGxedZXBGypLSpE\n" +
            "b/R25JdxBqFAzM4fzsminJ5X5BU2piEVXHmSA1WIwtWBzvZ5z1f6qkZ0FGsSBk75\n" +
            "tQzzArlPdj9IPdSTLHQ0uIVUZctCizplKWHEelzbjV7b65t2PzMoSK8sonEZIx/y\n" +
            "u0qxPApDbo2TovyLm1yQaXOIus+5NJhG2tFv3CF4k2vMf5yjcBrWdpR87jS8HoVc\n" +
            "MVCjkd1binGBDIgSRO1M055LnVpfImt3SeUrJJTy4VQtRbj0Tj5LezxizeqrzU5u\n" +
            "F0cHi0qDXVv06iHcSjeThyfTmWiY6TsR1g5/1dcCAwEAAaOCAsgwggLEMAwGA1Ud\n" +
            "EwEB/wQCMAAwHQYDVR0lBBYwFAYIKwYBBQUHAwEGCCsGAQUFBwMCMA4GA1UdDwEB\n" +
            "/wQEAwIFoDA3BgNVHR8EMDAuMCygKqAohiZodHRwOi8vY3JsLmdvZGFkZHkuY29t\n" +
            "L2dkaWcyczEtODM1LmNybDBdBgNVHSAEVjBUMEgGC2CGSAGG/W0BBxcBMDkwNwYI\n" +
            "KwYBBQUHAgEWK2h0dHA6Ly9jZXJ0aWZpY2F0ZXMuZ29kYWRkeS5jb20vcmVwb3Np\n" +
            "dG9yeS8wCAYGZ4EMAQIBMHYGCCsGAQUFBwEBBGowaDAkBggrBgEFBQcwAYYYaHR0\n" +
            "cDovL29jc3AuZ29kYWRkeS5jb20vMEAGCCsGAQUFBzAChjRodHRwOi8vY2VydGlm\n" +
            "aWNhdGVzLmdvZGFkZHkuY29tL3JlcG9zaXRvcnkvZ2RpZzIuY3J0MB8GA1UdIwQY\n" +
            "MBaAFEDCvSeOzDSDMKIz1/tss/C0LIDOMC0GA1UdEQQmMCSCDmFwaS5pbGUxOC5j\n" +
            "bHVighJ3d3cuYXBpLmlsZTE4LmNsdWIwHQYDVR0OBBYEFFVHTVY/zZywSzSIExWk\n" +
            "ZAoJUBCcMIIBBAYKKwYBBAHWeQIEAgSB9QSB8gDwAHUApLkJkLQYWBSHuxOizGdw\n" +
            "Cjw1mAT5G9+443fNDsgN3BAAAAFj1Dpe/wAABAMARjBEAiAfQWKUogrkYC1p81LL\n" +
            "EAI8AnyLHmqZKxvVELR+LbXmxAIgAnq6HINbn39l1sYm76rC+HE+I1Y3NmpHekZH\n" +
            "5Vv/K0AAdwB0ftqDMa0zEJEhnM4lT0Jwwr/9XkIgCMY3NXnmEHvMVgAAAWPUOmAH\n" +
            "AAAEAwBIMEYCIQC62QI+ZPxaKhXBKBqrMNXgQvAvxcLqQcH0eXK6NFbMggIhAJw2\n" +
            "CLFxMHHVZviDIouV6W0i5RAl6HdyXnpFix7ZSUmFMA0GCSqGSIb3DQEBCwUAA4IB\n" +
            "AQCKNzH6yX1yV6hjXz4Ap/vv59Wr12Tm7cY+VbkaJkzPwBxBGJi8ziBJxNkusD7h\n" +
            "AZog8do+my0ZoSaQfK4dwZDCMG17MwUvRcPqOqn1TSxIeuzB6jxfwXq8QHD1mmhz\n" +
            "/inuoMJ9JhvWuZ0kW8Xss8IPoHPyllxawkfb6RrxBJbKufcMXRCKnDDecYP82R7G\n" +
            "DlnamnAxMNfmxW0T0VzHax225c1C1CqTX//MD44EkO5VBHCr5ps/l0GginZJ0SCZ\n" +
            "Prwv8rjm5raQV/joZ8rPpl5yTJfRWvdIpUPT0FxHfWoujWMPG9wCNCmHGjROTcb7\n" +
            "IHwlEtloEjtlQ+9tpAWu+O0l\n" +
            "-----END CERTIFICATE-----\n";


}

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
    public static String imIp="msg.yxwh168.com";
    public static String getBaseUrl() {
        if(App.isDebug){
            if(true)return baseUrl="http://api.yxwh168.com/";//唐总;
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
            return "https://api.99tjg.com/";
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
            "MIIFdTCCBF2gAwIBAgIQOyYg8VpdlZ513jJNqR1s4DANBgkqhkiG9w0BAQsFADCB\n" +
            "lDELMAkGA1UEBhMCVVMxHTAbBgNVBAoTFFN5bWFudGVjIENvcnBvcmF0aW9uMR8w\n" +
            "HQYDVQQLExZTeW1hbnRlYyBUcnVzdCBOZXR3b3JrMR0wGwYDVQQLExREb21haW4g\n" +
            "VmFsaWRhdGVkIFNTTDEmMCQGA1UEAxMdU3ltYW50ZWMgQmFzaWMgRFYgU1NMIENB\n" +
            "IC0gRzEwHhcNMTcwNzEzMDAwMDAwWhcNMTgwNzEzMjM1OTU5WjAYMRYwFAYDVQQD\n" +
            "DA1hcGkuOTl0amcuY29tMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA\n" +
            "nHR65cC/Hj7sZFQ+tHjKxeEtaLv9T5alAm3hGzKYra/l/H3B87Y5PsBQ6aYVc5tM\n" +
            "+ne3FV2IA0UmBw2v5sV+QMLfeNAixAhLmxsDgjcoYtbfKLW0pqxWnvyEXkHZfHpM\n" +
            "3Y8+YAGw9sv3EZT44Ygu2LPpuo1pSxjYjpnwS1LkTB3Hx6IrG+k9sUHK6lyJO19F\n" +
            "8b4nYrH5hcKniGVdk1LIxH94vNMuNDdb2ZMJB4OR3MeRM+6nF1hgG6ibMZQdmoae\n" +
            "kqPAVGuWjlaKULEmurcHt8jvIGkLzZb4mkY74poOp2sBpCJ6Q+rvcm3NItoiRPMz\n" +
            "cb+4tT+ejxRfv6C5h/TCMwIDAQABo4ICPDCCAjgwGAYDVR0RBBEwD4INYXBpLjk5\n" +
            "dGpnLmNvbTAJBgNVHRMEAjAAMGEGA1UdIARaMFgwVgYGZ4EMAQIBMEwwIwYIKwYB\n" +
            "BQUHAgEWF2h0dHBzOi8vZC5zeW1jYi5jb20vY3BzMCUGCCsGAQUFBwICMBkMF2h0\n" +
            "dHBzOi8vZC5zeW1jYi5jb20vcnBhMB8GA1UdIwQYMBaAFFxhnrB2QalqqkML4cdu\n" +
            "MClusc02MA4GA1UdDwEB/wQEAwIFoDAdBgNVHSUEFjAUBggrBgEFBQcDAQYIKwYB\n" +
            "BQUHAwIwVwYIKwYBBQUHAQEESzBJMB8GCCsGAQUFBzABhhNodHRwOi8vaGMuc3lt\n" +
            "Y2QuY29tMCYGCCsGAQUFBzAChhpodHRwOi8vaGMuc3ltY2IuY29tL2hjLmNydDCC\n" +
            "AQMGCisGAQQB1nkCBAIEgfQEgfEA7wB2AN3rHSt6DU+mIIuBrYFocH4ujp0B1VyI\n" +
            "jT0RxM227L7MAAABXTsky0QAAAQDAEcwRQIgYXhTRKzzNI1XRFHpxlPDm183U4J3\n" +
            "SLPd7qW0L5eHQ5QCIQDWpMdWZisbKE3YZs7G9rFtVctJ+XzMWTtQaRounObIBQB1\n" +
            "AKS5CZC0GFgUh7sTosxncAo8NZgE+RvfuON3zQ7IDdwQAAABXTsky1sAAAQDAEYw\n" +
            "RAIgXZjETjkDDIdhJLXnq4hRe9zIbzjG4QZ5vRRQwoajqdUCIDqF333AMIZ+Y/zf\n" +
            "PMW5DfooI2d/O22KDuJCKYnqxTH4MA0GCSqGSIb3DQEBCwUAA4IBAQCVBeA9kRu2\n" +
            "udHG76IKX/PxERkuAGrW2OzoP3Idjan3XfRhhxoaMqverAlERwiu2uzIHPlOPXU7\n" +
            "VZWsbvnNXQFNvnJYZj0XiKnWjIr6xE5pLVXhSvlHkymWnky3tXCwnKI1DTm06Y6D\n" +
            "IIw9HGcYXshE+2KHQ9fPVWpfcp6K3gALxqjX6jBsgd4drqupSFH+iDfw+FUSr368\n" +
            "IWwSHSoAOJJcLhgmINAMLXcrhne+y9Lek3q6uwnXLgAtlFiO3NBy7X8MJl1V6DVY\n" +
            "BEnMKGsyUrk0dUpfSEG/xhFZAfqQn4sBqC2/u71UgqVqSsmyX2j0lUiildl+RJjq\n" +
            "OLza6BN+36HH\n" +
            "-----END CERTIFICATE-----\n";


}

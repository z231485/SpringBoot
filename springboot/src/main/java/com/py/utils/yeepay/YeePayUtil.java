package com.py.utils.yeepay;

import com.alibaba.fastjson.JSONObject;
import com.yeepay.g3.sdk.yop.client.YopClient3;
import com.yeepay.g3.sdk.yop.client.YopRequest;
import com.yeepay.g3.sdk.yop.client.YopResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class YeePayUtil {

    protected static final Logger log = LoggerFactory.getLogger(YeePayUtil.class);

    /**********************************授权扣款*****************************************/

    //商编前面加SQKK
    private static String appKey = "SQKK10000469946";
    //商户私钥，测试用这个，到时候正式更换成自己的生成的私钥
    private static String private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCrS0KstpuJs75jrO2Tc7x5WfeQ8ER1tPr34OPiJfETinvTQeNzZc13V1Wm+yhk5Xsv4OWuSHCok5MwhEgc4cb03loTcS4pqAHL4u13hEycf+fTkoIQ88IpmFMcnu0l3dYqzj34fEgH7EN4byCDDd9cNtZWuzA9iitwJuoCjx4sJqxvftxPQV6gbPZEwIWPw3i1ALG8LgKDRiB/i8hw4PfRCGrYtwjOjwKsiEetxNUpROs8nUSpRBQnr8G1Z85sN6wH83eH3y2uJ4O4G/Eao3FG8rHVWPIiQaXtyFqFx8B3pFAwqYuxWaciInlNPTLMd1Ee+BDqQ9FN/tzy/q5CSTevAgMBAAECggEBAJiAYbT4wpMfNrLxI3ayhKsZgQJGFv0gioujad9OXkpCcamMsJ5tlTbZx0TpuHXTpQ/kTzgSAFLlSBbavoUQMZySVWmXyzyE+kx2FWrhm399lHzVo/zJuCRmHCCQEZwz21ey1JNkupBrNUqEzVJASIqFu9/tua4gVDn+OzraBkfREWV9E6CmQ0xl7SNz9awWpg/8zTqLMsXSR3Q6+ezd5vHZTZvkG5sLh2cKgylZoZawJgrGpW5mt+nXlPhvXmW6XcX730DWNvTgIOtduDZ0Ig5JEya8V9XC+1OcHAkpXXMG2ohABZtIKKS4yLWSwgCFErgVyien5T2kdEIGPbnE55ECgYEA88r2DHhssH9fSIzHo6yL3VMit30Qs6fjxmQXcqxewYtk11yPpn2ROaGBYitzsv/kgVLECRsfIREGsUjPqz7Al8NpgqoQEf27zWaf6x59cs3g2vB6rtilvng8C3dnSFjKZQ/tbXv/QHDz/A549rwm4fO689ptjegS3zcAaptF7okCgYEAs976k3NCpln7FzGKtMrMk5my/ed8Dj70cntxdwNBcsOhbbh1HbCsn2F/PLdqd6bJc+Qdi7hIGecUZ/6/CuEDY/8jZqm9b6B4vnxZFBTUyqHcL8cXmMUDi9p7SoSsCZ92RCD0x4SbOjhym7yuvw14gk+hlBSvWuC+YhEMRlinJncCgYBJ+12Pizvwk7amnZI36TTIhWITrLBU1K4almVHN2fJ9DM157DwJUrc4lYRJH6H43/EfwleegyITFJrmlzq6rAnXfW24UTfMNC9FFeTUj1fiXqi9jdEuBoUIwiVsjZ1jfxdjufOQcLEG4LvCrVKqu5hw0UIm1CDr9mKQ3as41HlgQKBgCjBD+NSzTolzxdtOTFHddzHiV+wEFKl/vrlb0r46N5Y5v2WOqr0edhO3eZi5HOhzak9eVhL88IyslPxy1VqsDr69wlu0iY1pMX8JK7BHYmf7OTCZl1N3kTUxvSWZOh1QfWjxfJi4Ezrt0QEF0/gfHqCEmkb2rNrkpdjp3VU5uJ3AoGBANhQ1Jl3X3UOVXQm0ZXQ7OkPkWE5XZIOb+WUcB7JTXKmpAArqI2FOe3Ki+8rMWDNgDUu+/nvSSWzrAsG8+jR9Hp+fSQ6poagwpWcitPjFYQip6RUBx6Cv6mBCMXDGnxOQIi8GJCITT86SsF1YbirDmi/Yd58xPEBbQdjysRWhr8e";
    //异步通知
    public static String callbackurl = "http://47.98.243.156:9630/dk/app/yeepay/notify";

    //向YOP发请求
    public static JSONObject yeepayYOP(Map<String, String> map, String Uri) throws IOException {
        //请求封装
        YopRequest yoprequest = new YopRequest(appKey, private_key);
        Set<Entry<String, String>> entry = map.entrySet();
        for (Entry<String, String> s : entry) {
            yoprequest.addParam(s.getKey(), s.getValue());
        }
        //请求
        YopResponse yopresponse = YopClient3.postRsa(Uri, yoprequest);
        //通信错误，一般是没对接好，暂时不处理
        if (StringUtils.equals("FAILURE", yopresponse.getState())) {
            if (null != yopresponse.getError()) {
                log.error("易宝支付错误码" + yopresponse.getError().getCode());
                log.error("易宝支付错误信息" + yopresponse.getError().getMessage());
                log.error("易宝支付错误明细：" + yopresponse.getError().getSubMessage());
                return null;
            }
        }
        JSONObject jsonObject = null;
        if (StringUtils.isNotBlank(yopresponse.getStringResult())) {
            try {
                jsonObject = JSONObject.parseObject(yopresponse.getStringResult());
            } catch (Exception e) {
            }
        }
        return jsonObject;
    }


}

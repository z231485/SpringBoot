package com.py.utils.wxpay;

import com.py.utils.EncryptionUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

public class WxPayUtil {


    /**
     * 创建随机字符串
     *
     * @param length
     * @return
     */
    public static String CreateRandomStr(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer sb = new StringBuffer();
        Random rd = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(str.charAt(rd.nextInt(str.length() - 1)));
        }
        return sb.toString();
    }


    /**
     * 拼接签名
     *
     * @param parameters
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String SplicingSign(SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + WxPayConfig.API_KEY);
        return sb.toString();
    }


    /**
     * 创建签名
     *
     * @param parameters
     * @return
     */
    public static String createSign(SortedMap<Object, Object> parameters) {
        //拼接签名
        String str = SplicingSign(parameters);
        return EncryptionUtils.md5(str).toUpperCase();
    }


    /**
     * 效验签名
     *
     * @return boolean
     */
    public static boolean ValidationSign(SortedMap<Object, Object> parameters) {
        //拼接签名
        String str = SplicingSign(parameters);
        String sign = EncryptionUtils.md5(str).toUpperCase();
        String returnSign = ((String) parameters.get("sign")).toLowerCase();
        return returnSign.equals(sign);
    }


    /**
     * 微信支付将请求参数转换为xml格式的String
     *
     * @param parameters
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String getRequestXml(SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if ("attach".equalsIgnoreCase(key) || "body".equalsIgnoreCase(key) || "sign".equalsIgnoreCase(key)) {
                sb.append("<" + key + ">" + "<![CDATA[" + value + "]]></" + key + ">");
            } else {
                sb.append("<" + key + ">" + value + "</" + key + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * 微信支付将请求参数转换为xml格式的String
     *
     * @param paramMap
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String getRequestXmlQuery(SortedMap<String, String> paramMap) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set set = paramMap.entrySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            sb.append("<" + key + ">" + "<![CDATA[" + value + "]]></" + key + ">");
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     *
     * @param strxml
     * @return
     * @throws JDOMException
     * @throws IOException
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Map doXMLParse(String strxml) {
        Map<String, String> map = new HashMap();
        try {
            //防止XXE漏洞攻击
            strxml = filterXXE(strxml);
            //替换头部
            strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
            if (null == strxml || "".equals(strxml)) {
                return null;
            }
            InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(in);
            Element root = doc.getRootElement();
            List list = root.getChildren();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
                String k = e.getName();
                String v = "";
                List children = e.getChildren();
                if (children.isEmpty()) {
                    v = e.getTextNormalize();
                } else {
                    v = getChildrenText(children);
                }
                map.put(k, v);
            }
            //关闭流
            in.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获取子结点的xml
     *
     * @param children
     * @return String
     */
    @SuppressWarnings("rawtypes")
    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if (!children.isEmpty()) {
            Iterator it = children.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if (!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }
        return sb.toString();
    }


    /**
     * 防止XXE漏洞 注入实体攻击 ,过滤用户提交的XML数据   关键词:DOCTYPE ENTITY SYSTEM PUBLIC
     *
     * @param xmlStr
     * @return
     */
    public static String filterXXE(String xmlStr) {
        xmlStr = xmlStr.replace("DOCTYPE", "").replace("ENTITY", "").replace("SYSTEM", "").replace("PUBLIC", "");
        return xmlStr;
    }


    /**
     * @param return_code 返回编码
     * @param return_msg  返回信息
     * @return
     * @Description：返回给微信的XML
     */
    public static void writeBackXML(String return_code, String return_msg, HttpServletResponse response) {
        try {
            String resXml = "<xml>\r\n" +
                    "<return_code><![CDATA[" + return_code + "]]></return_code>\r\n" +
                    "<return_msg><![CDATA[" + return_msg + "]]></return_msg>\r\n" +
                    "</xml>";
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

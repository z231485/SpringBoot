package com.py.utils.yeepay;

import java.util.ResourceBundle;

public class YeePayConfig {

    public static Object object = new Object();

    public static YeePayConfig config = null;

    public static ResourceBundle rb = null;

    public static final String File_Name = "merchantInfo";

    public YeePayConfig() {
        rb = ResourceBundle.getBundle(File_Name);
    }

    public static YeePayConfig getInstance() {
        synchronized (object) {
            if (config == null) {
                config = new YeePayConfig();
            }
            return config;
        }
    }

    public String getValue(String key) {
        return (rb.getString(key));
    }

}

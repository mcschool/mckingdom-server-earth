package com.mckd.earth;

public class Config {

    public String apiUrl;

    public Config() {
        String env = "local";
        if (env == "local") {
            this.apiUrl = "http://localhost:5000";
        } else {
            this.apiUrl = "http://mc-kingdom.com";
        }
    }

    public static String getApiUrl() {
        String env = "local";
        String apiUrl;
        if (env == "local") {
            apiUrl = "http://localhost:5000";
        } else {
            apiUrl = "http://mc-kingdom.com";
        }
        return apiUrl;
    }
}

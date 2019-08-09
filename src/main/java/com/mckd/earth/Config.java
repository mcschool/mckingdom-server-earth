package com.mckd.earth;

public class Config {

    public String apiUrl;

    public Config() {
        String env = System.getenv("MCK_ENV");
        System.out.println("=== config =============");
        System.out.println(env);
        System.out.println("========================");
        if (env == "production") {
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

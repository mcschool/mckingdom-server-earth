package com.mckd.earth;

public class Config {

    public String apiUrl;

    public Config() {
        String env = System.getenv("MCK_ENV");
        System.out.println("=== config =============");
        System.out.println(env);
        System.out.println("========================");
        if (env.equals("production")) {
            this.apiUrl = "https://mc-kingdom.com";
        } else {
            this.apiUrl = "http://localhost:5000";
        }
    }

    public static String getApiUrl() {
        String env = "local";
        String apiUrl;
        if (env == "local") {
            apiUrl = "http://localhost:5000";
        } else {
            apiUrl = "https://mc-kingdom.com";
        }
        return apiUrl;
    }
}

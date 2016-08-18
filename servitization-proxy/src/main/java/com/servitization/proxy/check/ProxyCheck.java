package com.servitization.proxy.check;

public class ProxyCheck {

    public static String sourceServicePathCheck(String path) {
        if (!path.startsWith("/"))
            return "/" + path;

        return path;
    }

    public static String targetServiceNameCheck(String name) {
    	if (name.length() == 0) {
    		return name;
    	}
        if (!name.startsWith("/"))
            return "/" + name;

        return name;
    }

    public static String servicePoolUrlCheck(String url) {
        if (!url.startsWith("http://"))
            url = "http://" + url;

        return url;
    }
}

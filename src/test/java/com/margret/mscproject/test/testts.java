package com.margret.mscproject.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Before;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;
import org.zaproxy.zap.model.Target;

public class testts {
    private WebDriver driver;
    private Target target;
    private ClientApi zap;
    static final String ZAP_API_KEY = "t2a7j1anpb5ef01idrto4vh46f";
    static final int ZAP_PROXY_PORT = 8081;
    static final String ZAP_PROXY_HOST = "localhost";
    @Before
    public void setUp() throws ClientApiException {
        // Start OWASP ZAP proxy
        String zapServerURL = ZAP_PROXY_HOST + ":" + ZAP_PROXY_PORT;
        Proxy proxy = new Proxy();
        proxy.setHttpProxy(zapServerURL);
        proxy.setSslProxy(zapServerURL);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setProxy(proxy);
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(chromeOptions);
        zap = new ClientApi(ZAP_PROXY_HOST, ZAP_PROXY_PORT, ZAP_API_KEY);

        // Configure ZAP to scan the Juice Shop application
        String targetUrl = "http://localhost:3000";
        zap.accessUrl(targetUrl);

        // Configure ZAP to use a context that includes the Juice Shop application
        String contextName = "Juice Shop Context";
        ApiResponse contextId = zap.context.newContext(contextName);
        zap.context.includeInContext(String.valueOf(contextId), targetUrl + "/.*");

        // Configure ZAP to use an active scan policy
//        int policyId = zap.ascan.getScannerPolicyIds("").get(0); // use the default policy
//        zap.ascan.setScannerPolicy(policyId);
//        zap.ascan.

        // Configure ZAP to use a spidering policy
        zap.spider.setOptionMaxDepth(5);
        zap.spider.setOptionMaxDuration(60);
    }
}

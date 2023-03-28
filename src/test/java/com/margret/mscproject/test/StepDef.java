package com.margret.mscproject.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.zaproxy.zap.model.Target;

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;

import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;


public class StepDef {

    private WebDriver driver;
    private Target target;
    private ClientApi zap;
    static final String ZAP_API_KEY = "t2a7j1anpb5ef01idrto4vh46f";
    static final int ZAP_PROXY_PORT = 8081;
    static final String ZAP_PROXY_HOST = "localhost";

    String zapSessionName = "juice-shop-session";
    String zapSessionLocation = "/path/to/zap/session/files";

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

    }

    @After
    public void tearDown() {
        // Close the Firefox driver
        driver.quit();

        // Save the ZAP report
        try {
            String report = Arrays.toString(zap.core.htmlreport());
            FileUtils.writeStringToFile(FileUtils.getFile("./zap-report.html"), report, "UTF-8");
        } catch (ClientApiException | IOException e) {
            e.printStackTrace();
        }
    }

    public void juiceShopDast(){
    String targetUrl = "http://localhost:3000";
    driver.get(targetUrl);
}

}

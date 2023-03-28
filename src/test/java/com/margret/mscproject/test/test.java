package com.margret.mscproject.test;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.zaproxy.clientapi.core.*;
import org.zaproxy.zap.model.Target;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;



public class test {

        private WebDriver driver;
        private Target target;
        private static ClientApi zap;
        static final String ZAP_API_KEY = "t2a7j1anpb5ef01idrto4vh46f";
        static final int ZAP_PROXY_PORT = 8081;
        static final String ZAP_PROXY_HOST = "localhost";

        String zapSessionName = "juice-shop-session";
        String zapSessionLocation = "/path/to/zap/session/files";

        @Before
        public void setUp() throws ClientApiException {
            // Start OWASP ZAP proxy
            zap = new ClientApi(ZAP_PROXY_HOST, ZAP_PROXY_PORT, ZAP_API_KEY);
            zap.core.newSession(zapSessionName, "", "");
            zap.core.saveSession(zapSessionName, zapSessionLocation);

            // Set up Chrome driver with ZAP proxy
            String zapServerURL = ZAP_PROXY_HOST + ":" + ZAP_PROXY_PORT;
            Proxy proxy = new Proxy();
            proxy.setHttpProxy(zapServerURL);
            proxy.setSslProxy(zapServerURL);
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setProxy(proxy);
            WebDriverManager.chromedriver().setup();
            chromeOptions.addArguments("--ignore-certificate-errors");
            chromeOptions.setCapability(CapabilityType.PROXY, proxy);
            driver = new ChromeDriver(chromeOptions);

//
//                // Set the target URL for the ZAP scan
//                target = new Target();
//                target.setAddress("localhost");
//                target.setPort(3000);
//                zap.context.setContextInScope("Default Context", target);
//                zap.spider.scan(target.getAddress(), target.getPort());


        }

        @After
        public void tearDown() {
           if (driver != null) {
               driver.quit();
           }

            try {
                String report = new String(zap.core.htmlreport());
                FileUtils.writeStringToFile(FileUtils.getFile("./zap-report.html"), report, "UTF-8");
            } catch (ClientApiException | IOException e) {
                e.printStackTrace();
            } finally {
                // Shutdown the ZAP API client connection
                try {
                    zap.core.shutdown();
                } catch (ClientApiException e) {
                    e.printStackTrace();
                }
            }
        }

    public static void spiderTarget(String targetURL) throws InterruptedException, ClientApiException {
        String application_base_url = "https://<application_url>/"; // Example code to start and synchronize the spider scan.private static void startSpiderScan()


        // Start spider scan
        ApiResponse apiResponse = zap.spider.scan(targetURL, null, null, null, null);
        int progress;

        // Scan response returns scan id to support concurrent scanning.
        String scanId = ((ApiResponseElement) apiResponse).getValue();
        // Poll the status till over.
        do {
            Thread.sleep(5000);
            progress = Integer.parseInt(((ApiResponseElement) zap.spider.status(scanId)).getValue());

        } while (progress < 100);

        // Process the spider results if needed
        List<ApiResponse> spiderResults = ((ApiResponseList) zap.spider.results(scanId)).getItems();
    }

    public void juiceShopDast() throws ClientApiException {
            String targetUrl = "http://localhost:3000";

//            UrlNode urlNode = new UrlNode(targetUrl);
//            target = new Target(urlNode);
//
//
//
//            target = new Target(targetUrl);
//            zap.core.accessUrl(targetUrl);
//            zap.ascan.scan(target, "", "", null, null, null);
//            zap.spider.scan(target, 3, null, null, null);
        }


}
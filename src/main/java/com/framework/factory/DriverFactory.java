package com.framework.factory;

import com.aventstack.extentreports.Status;
import com.framework.services.PropertiesService;
import com.framework.services.ScreenshotService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

public class DriverFactory {
    private static volatile DriverFactory instance = null;
    private Properties propertiesWeb = PropertiesService.readProperties("configuration/webDriverConfig.properties");
    ;

    private DriverFactory() {

    }

    public static DriverFactory init() {
        if (instance == null) {
            synchronized (DriverFactory.class) {
                if (instance == null)
                    instance = new DriverFactory();
            }
        }
        return instance;
    }

    ThreadLocal<WebDriver> trdMainDriver = new ThreadLocal<>();
    ThreadLocal<WebDriver> trdWebDriver = new ThreadLocal<>();

    public WebDriver get() {
        return trdMainDriver.get();
    }

    public void set(WebDriver webDriver) {
        trdMainDriver.set(webDriver);
    }

    public void remove() {
        trdMainDriver.remove();
    }

    /**
     * Put url on WebDriver
     */
    public void goto_url(String strURL,String strDescription) {
        DriverFactory.init().get().get(strURL);
        ScreenshotService.init().screenShotChromes(Status.INFO,strDescription);

    }

    /**
     * @return Return WebDriver by config webDriverProperties
     */
    public void initWebDriver() {
        String strBrowser = propertiesWeb.getProperty("Browser");
        String strSource = propertiesWeb.getProperty("Source");
        WebDriver webDriver = null;
        useLocalorDefault(strSource,strBrowser);

        switch (strBrowser) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("enable-automation"); // https://stackoverflow.com/a/43840128/1689770
                chromeOptions.addArguments("--no-sandbox"); //https://stackoverflow.com/a/50725918/1689770

                if (propertiesWeb.getProperty("Maximize").equalsIgnoreCase("true"))
                    chromeOptions.addArguments("--start-maximized");

                if (propertiesWeb.getProperty("Headless").equalsIgnoreCase("true")) {
                    chromeOptions.addArguments("--headless");
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--disable-dev-shm-usage"); //https://stackoverflow.com/a/50725918/1689770
                    chromeOptions.addArguments("--disable-browser-side-navigation"); //https://stackoverflow.com/a/49123152/1689770
                }

                if (propertiesWeb.getProperty("Incognito").equalsIgnoreCase("true"))
                    chromeOptions.addArguments("--incognito");

                if (propertiesWeb.getProperty("Proxy").equalsIgnoreCase("true")){
                    String strProxy = propertiesWeb.getProperty("ProxyHost");
                    chromeOptions.addArguments("--proxy-server= "+ strProxy);
                }

                int intCounter = 0;
                while (webDriver==null && intCounter<5) {
                    try {
                        webDriver = new ChromeDriver(chromeOptions);
                    } catch (Exception e) {
                    }
                    intCounter++;
                }

                break;
            case "firefox":
                webDriver = new FirefoxDriver();
                break;

            case "opera":
                //noinspection deprecation
                webDriver = new OperaDriver();
                break;
            case "ie":
                webDriver = new InternetExplorerDriver();
                break;
        }

        Long ObjectWaitTime = Long.parseLong(propertiesWeb.getProperty("WaitTime"));
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ObjectWaitTime));
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ObjectWaitTime * 2));
        webDriver.manage().timeouts().scriptTimeout(Duration.ofSeconds(ObjectWaitTime * 2));

        trdWebDriver.set(webDriver);
        set(webDriver);
    }



    /**
     * Close Browser
     */
    public void tearDown() {
        trdMainDriver.get().quit();
        remove();
    }

    /**
     * @param strSource Source use local driver or webdrivermanager, because bca use proxy, sometimes we can't connect chromedriver
     * @param strBrowser What browser for the setup
     */
    private void useLocalorDefault(String strSource,String strBrowser){
        if(strSource.equalsIgnoreCase("driverLocal")){
            switch (strBrowser) {
                case "chrome":
                    checkVersionChrome();
                    String strdriverLocal = propertiesWeb.getProperty("DriverLocal");
                    System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\driverLocal\\"+strdriverLocal);
                    break;
                case "firefox":
                    break;
                case "opera":
                    break;
                case "ie":
                    break;
            }
        }else{
            switch (strBrowser) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    break;
                case "opera":
                    WebDriverManager.operadriver().setup();
                    break;
                case "ie":
                    WebDriverManager.iedriver().setup();
                    break;
            }
        }
    }

    private void checkVersionChrome() {
        String chromeRegistry = null;
        String strChromeDriver = "";

        //Find Chrome version in Registry Editor
        Runtime rt = Runtime.getRuntime();
        Process proc = null;
        try {
            proc = rt.exec("reg query " + "HKEY_CURRENT_USER\\Software\\Google\\Chrome\\BLBeacon " +  "/v version");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BufferedReader regData = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));
        try {
            while ((chromeRegistry = regData.readLine())!=null) {
                if (chromeRegistry.contains("version")) {
                    //Split values from Chrome registry to get Chrome Vers. (E.g:"   version    REG_SZ    103.0.5060.114")
                    String[] strChromeVersion = chromeRegistry.split("\\s+");
                    //Split Chrome version by the dot
                    String[] splitVersion = strChromeVersion[3].split("\\.");
                    //check if the second value equal to 0 (xxx.0.xxxx.xxx)
                    if (splitVersion[1].equalsIgnoreCase("0")){
                        strChromeDriver = splitVersion[0];
                    }
//                    else {
//                        strChromeDriver = splitVersion[0]+"."+splitVersion[1];
//                    }
                }
            }
            propertiesWeb.setProperty("DriverLocal","chromedriver"+strChromeDriver+".exe");
        } catch (Exception e) {
            try {
                throw new Exception("Chrome version not found in Registry Editor");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}

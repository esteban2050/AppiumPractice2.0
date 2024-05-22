package org.appium;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.appium.pageObject.android.FormPage;
import org.appium.utils.AppiumUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

public class BaseTest extends AppiumUtils {

    public AndroidDriver driver;
    public AppiumDriverLocalService service;
    public FormPage formPage;

    @BeforeClass
    public void configureAppium() throws IOException {

        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "//src//main//resources//data.properties");
        prop.load(fis);
        String IpAddress = prop.getProperty("ipAddress");
        String port = prop.getProperty("port");

        service = startAppiumServer(IpAddress, Integer.parseInt(port));
        //AndroidDriver, IOSDriver
        //Appium code is interpreted by appium server and send the commands to the mobile Appium code -> Appium Server -> Mobile
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName(prop.getProperty("AndroidDeviceName"));
        URL resource = getClass().getClassLoader().getResource("driver/chromedriver.exe");
        if (resource == null) {
            throw new IllegalArgumentException("El archivo no se encontró: test/java/resources/driver/chromerdriver.exe" );
        } else {
            File file = new File(resource.getFile());
            System.out.println("Ruta absoluta del archivo: " + file.getPath());
            // Luego puedes realizar las operaciones que necesites con el archivo
            options.setChromedriverExecutable(file.getPath());
        }
        URL app = getClass().getClassLoader().getResource("apps/General-Store.apk");
        File fileApp = new File(app.getFile());
        //options.setApp("C://Users//Esteban//IdeaProjects//Appium//src//test//java//resources//ApiDemos-debug.apk");
        options.setApp(fileApp.getPath());
        driver = new AndroidDriver(service.getUrl(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        formPage= new FormPage(driver);
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
        service.stop();
    }
}

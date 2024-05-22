package org.appium;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.appium.pageObject.android.CartPage;
import org.appium.pageObject.android.ProductCataloguePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class eCommerceTest_4_Hybrid extends BaseTest {

    @Test(dataProvider = "getData")
    public void FillForm(HashMap<String,String> input) throws InterruptedException {

        formPage.setNameField(input.get("name"));
        formPage.setGender(input.get("gender"));
        formPage.setCountry(input.get("country"));
        ProductCataloguePage productCataloguePage = formPage.submitForm();
        productCataloguePage.addItemToCartByIndex(0);
        productCataloguePage.addItemToCartByIndex(0);
        CartPage cartPage = productCataloguePage.goToCartPage();

        // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        //wait.until(ExpectedConditions.attributeContains(driver.findElement(By.id("com.androidsample.generalstore:id/toolbar_title")),"text","Cart"));

        double totalSum = cartPage.getProductsSum();
        double displayFormattedSum = cartPage.getTotalAmountDisplayed();
        Assert.assertEquals(totalSum, displayFormattedSum);
        cartPage.acceptTermsConditions();
        cartPage.submitOrder();


//        Set<String> contexts = driver.getContextHandles();
//        for (String context : contexts) {
//            System.out.println(context);
//        }
//
//        driver.context("WEBVIEW_com.androidsample.generalstore"); //This context could change depending on the app, this will depend on the developer with the name he had given it
//        driver.findElement(By.name("q")).sendKeys("rahul shetty academy");
//        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
//        driver.pressKey(new KeyEvent(AndroidKey.BACK));
//        driver.context("NATIVE_APP");
    }

    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String,String>>  data = getJsonData(System.getProperty("user.dir")+"//src//test//java//testData//eCommerce.json");
        return new Object[][]{{data.get(0)}, {data.get(1)}};
    }

    @BeforeMethod
    public void preSetup() {
        formPage.setActivity();
    }
}

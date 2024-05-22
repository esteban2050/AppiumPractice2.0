package org.appium.pageObject.android;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.appium.utils.AndroidActions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends AndroidActions  {

    AndroidDriver driver;

    public CartPage(AndroidDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver),this);
    }

    @AndroidFindBy(id = "com.androidsample.generalstore:id/productPrice")
    private List<WebElement> ProductList;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/totalAmountLbl")
    private WebElement totalAmount;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/termsButton")
    private WebElement terms;

    @AndroidFindBy(id = "android:id/button1")
    private WebElement acceptButton;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/btnProceed")
    private WebElement proceed;

    @AndroidFindBy(className = "android.widget.CheckBox")
    private WebElement checkBox;

    public List<WebElement> getProductList(){
        return ProductList;
    }

    public double getProductsSum(){
        int count = ProductList.size();
        double totalSum = 0;
        for(int i =0; i < count; i++){
            String amountString = ProductList.get(i).getText();
            double cost = Double.parseDouble(amountString.substring(1));
            totalSum = totalSum + cost;
        }
        return totalSum;
    }

    public Double getTotalAmountDisplayed(){
        return getFormattedAmount(totalAmount.getText());
    }

    public void acceptTermsConditions(){
        longPressAction(terms);
        acceptButton.click();
    }

    public Double getFormattedAmount(String amount){
        return Double.parseDouble(amount.substring(1));
    }

    public void submitOrder(){
        checkBox.click();
        proceed.click();
    }
}

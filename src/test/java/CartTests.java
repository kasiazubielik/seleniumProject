import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CartTests {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    public void driverSetup() {

        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
//        WebDriverManager.firefoxdriver().setup();
        driver = new ChromeDriver();

        driver.manage().window().setSize(new Dimension(1280, 720));
        driver.manage().window().setPosition(new Point(8, 30));
        driver.navigate().to("https://fakestore.testelka.pl/");
        driver.findElement(By.cssSelector("a[class*='dismiss-link']")).click();

        wait = new WebDriverWait(driver, 10);
    }

    @AfterEach
    public void driverQuite() {
        driver.quit();
    }

    @Test
    public void addToCartFromProductPageTest() {
        driver.findElement(By.cssSelector("a[href*='category/yoga']")).click();
        driver.findElement(By.cssSelector("li.post-64")).click();
        driver.findElement(By.cssSelector("button[name='add-to-cart']")).click();
        driver.findElement(By.cssSelector("a.cart-contents")).click();

        String expectedProduct = "Wczasy relaksacyjne z yogą w Toskanii";
        Assertions.assertEquals(expectedProduct, getProductName(),
                "Product is not added to cart.");
    }

    @Test
    public void addToCartFromProductCategoryTest() {
        driver.findElement(By.cssSelector("a[href*='category/yoga']")).click();
        driver.findElement(By.cssSelector("a[data-product_id='60']")).click();
        waitForProcessingEnd();
        driver.findElement(By.cssSelector("a.cart-contents")).click();

        String expectedProduct = "Yoga i pilates w Hiszpanii";
        Assertions.assertEquals(expectedProduct, getProductName(),
                "Product is not added to cart.");
    }

    @Test
    public void addDifferentProductsToCartFromProductCategoryTest() {
        driver.findElement(By.cssSelector("a[href*='category/yoga']")).click();
        driver.findElement(By.cssSelector("a[data-product_id='60']")).click();
        waitForProcessingEnd();
        driver.findElement(By.cssSelector("a[data-product_id='61']")).click();
        waitForProcessingEnd();
        driver.findElement(By.cssSelector("a[data-product_id='64']")).click();
        waitForProcessingEnd();
        driver.findElement(By.cssSelector("a[data-product_id='62']")).click();
        waitForProcessingEnd();
        driver.findElement(By.cssSelector("a[data-product_id='53']")).click();
        waitForProcessingEnd();
        driver.findElement(By.cssSelector("a[data-product_id='60']")).click();
        waitForProcessingEnd();
        driver.findElement(By.cssSelector("a[data-product_id='53']")).click();
        waitForProcessingEnd();
        driver.findElement(By.cssSelector("a[data-product_id='62']")).click();
        waitForProcessingEnd();
        driver.findElement(By.cssSelector("a[data-product_id='63']")).click();
        waitForProcessingEnd();
        driver.findElement(By.cssSelector("a[data-product_id='53']")).click();
        waitForProcessingEnd();
        driver.findElement(By.cssSelector("a[data-product_id='60']")).click();
        waitForProcessingEnd();
        driver.findElement(By.cssSelector("a.cart-contents")).click();

        Assertions.assertEquals(11, getNumberOfProductsInCart(),
                "Number of products in cart is not what expected.");
    }

    @Test
    public void chooseNumberFromProductPageTest() {
        driver.findElement(By.cssSelector("a[href*='category/yoga']")).click();
        driver.findElement(By.cssSelector("li.post-64")).click();
        WebElement amountInput = driver.findElement(By.cssSelector("input[name='quantity']"));
        amountInput.clear();
        amountInput.sendKeys("5");
        driver.findElement(By.cssSelector("button[name='add-to-cart']")).click();
        driver.findElement(By.cssSelector("a.cart-contents")).click();

        Assertions.assertEquals("5", getNumberOfProduct(),
                "Number of one products in cart is not what expected.");
    }

    @Test
    public void tenDifferentProductsAddToCartTest() {
        driver.findElement(By.cssSelector("a[href*='category/yoga']")).click();
        driver.findElement(By.cssSelector("a[data-product_id='61']")).click();
        waitForProcessingEnd();
        driver.findElement(By.cssSelector("a[data-product_id='64']")).click();
        waitForProcessingEnd();
        driver.findElement(By.cssSelector("a[data-product_id='60']")).click();
        waitForProcessingEnd();
        driver.findElement(By.cssSelector("a[data-product_id='53']")).click();
        waitForProcessingEnd();
        driver.findElement(By.cssSelector("a[data-product_id='62']")).click();
        waitForProcessingEnd();
        driver.findElement(By.cssSelector(".cat-item-18>a")).click();
        driver.findElement(By.cssSelector("a[data-product_id='386']")).click();
        waitForProcessingEnd();
        driver.findElement(By.cssSelector("a[data-product_id='393']")).click();
        waitForProcessingEnd();
        driver.findElement(By.cssSelector("a[data-product_id='391']")).click();
        waitForProcessingEnd();
        driver.findElement(By.cssSelector("a[data-product_id='50']")).click();
        waitForProcessingEnd();
        driver.findElement(By.cssSelector("a[data-product_id='389']")).click();
        waitForProcessingEnd();
        driver.findElement(By.cssSelector("a.cart-contents")).click();

        Assertions.assertEquals(10, getNumberOfProductsInCart(),
                "Number of products in cart is not what expected.");
    }

    @Test
    public void chooseAmountFromCartPageTest() {
        driver.findElement(By.cssSelector("a[href*='category/yoga']")).click();
        driver.findElement(By.cssSelector("li.post-64")).click();
        driver.findElement(By.cssSelector("button[name='add-to-cart']")).click();
        driver.findElement(By.cssSelector("a.cart-contents")).click();
        WebElement amountInput = driver.findElement(By.cssSelector("input[class='input-text qty text']"));
        amountInput.clear();
        amountInput.sendKeys("5");
        driver.findElement(By.cssSelector("button[name='update_cart']")).click();

        wait.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(By.cssSelector("button[name='update_cart']"))));
        Assertions.assertEquals("5", getNumberOfProduct(),
                "Number of one products in cart is not what expected.");
    }

    @Test
    public void removeProductFromCartPageTest() {
        driver.findElement(By.cssSelector("a[href*='category/yoga']")).click();
        driver.findElement(By.cssSelector("li.post-64")).click();
        driver.findElement(By.cssSelector("button[name='add-to-cart']")).click();
        driver.findElement(By.cssSelector("a.cart-contents")).click();
        driver.findElement(By.cssSelector(".remove")).click();

        WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[role='alert']")));

        Assertions.assertEquals("Usunięto: “Wczasy relaksacyjne z yogą w Toskanii”. Cofnij?", alert.getText(),
                "Alert message is not what expected.");
    }

    public String getProductName() {
        String productName = driver.findElement(By.cssSelector("tbody>:nth-last-of-type(2)>td.product-name")).getText();
        return productName;
    }

    public String getNumberOfProduct() {
        String numberOfProduct = driver.findElement(By.cssSelector("tbody>:nth-last-of-type(2)>td.product-quantity input")).getAttribute("value");
        return  numberOfProduct;
    }

    public int getNumberOfProductsInCart() {
        List<WebElement> numberOfProducts = driver.findElements(By.cssSelector("tr.woocommerce-cart-form__cart-item"));
        return numberOfProducts.size();
    }

    private void waitForProcessingEnd() {
        By processingOverlay = By.cssSelector(".loading");
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(processingOverlay, 0));
        wait.until(ExpectedConditions.numberOfElementsToBe(processingOverlay, 0));
    }
}

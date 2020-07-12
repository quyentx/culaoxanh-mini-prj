package main;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class Main {

    public static Properties p;

    public static void main(String[] args) throws IOException, InterruptedException {
        // Read config file
        p = CommonOperations.readConfig();
        googleSearch(p.getProperty("keyWord"), Integer.parseInt(p.getProperty("repeatedTime")));
    }

    public static void googleSearch(String keyword, int repeatNumber) throws IOException, InterruptedException {

        for (int i = 0; i <= repeatNumber - 1; i++) {
            WebDriver driver;
            String browser = p.getProperty("browser");
            // Run testscript with firefox browser
            if (browser.equalsIgnoreCase("firefox")) {
                System.out.println("You're running Firefox");
                System.setProperty("webdriver.gecko.driver", "lib/geckodriver.exe");
                driver = new FirefoxDriver();
                // Run testscript with chrome browser
            } else if (browser.equalsIgnoreCase("chrome")) {
                System.out.println("You're running Chrome");
                System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");
                driver = new ChromeDriver();
            } else {
                System.out.println("You're running Chrome");
                System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");
                driver = new ChromeDriver();
            }
            driver.get(p.getProperty("baseUrl"));
            WebElement element = driver.findElement(By.name("q"));
            element.sendKeys(keyword);
            element.submit();

            ////div[@class="bkWMgd"]/div[@class="srg"]/div[@class="g"]/div[1]/div[@class="rc"]/div[@class="r"]/a
            List<WebElement> findElements = driver.findElements(By.xpath("//div[@class=\"r\"]/a"));
            findElements.get(0).click();
            Thread.sleep(1000);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.history.go(-1)");
            /*
            Search for culaoxanh.com.vn from result list
            Click on it
             */
            for (int ind = 0; ind < 9; ind++) {
                List<WebElement> elements = driver.findElements(By.xpath("//div[@class=\"r\"]/a"));
                if (elements.get(ind).getAttribute("href").equalsIgnoreCase(p.getProperty("destLink"))) {
                    elements.get(ind).click();
                    System.out.println("Clicked 1");
                }
            }
            /*
            If culaoxanh.com.vn is not found on first page, find it on the next 9 pages
             */
            aa:
            for (int pInd = 3; pInd < 11; pInd++) {
                WebElement pageNum = driver.findElement(By.xpath("//*[@id=\"nav\"]/tbody/tr/td[" + pInd + "]"));
                pageNum.click();
                for (int inde = 0; inde < 9; inde++) {
                    List<WebElement> elementss = driver.findElements(By.xpath("//div[@class=\"r\"]/a"));
                    if (elementss.get(inde).getAttribute("href").contains(p.getProperty("destLink"))) {
                        elementss.get(inde).click();
                        Thread.sleep(Long.parseLong(p.getProperty("waitingTime")) * 1000);
                        break aa;
                    }
                }
            }
            //close browser
            driver.close();
            System.out.println("Browser is closed! " + (i + 1) + " loop completed");
        }
    }
}
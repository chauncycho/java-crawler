package crawlers.selenium_crawler;

import crawlers.Crawler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;

public class HtmlCrawler implements Crawler {
    public String getHtmlSource() {
        return null;
    }

    public void write(String path) {

    }

    public static void main(String[] args) {
        System.getProperties().setProperty("webdriver.chrome.driver", "./driver/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.baidu.com");
        driver.quit();
    }
}

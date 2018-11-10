package crawlers.selenium_crawler;

import crawlers.Crawler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;

public class HtmlCrawler implements Crawler {
    private String url;
    private WebDriver driver = null;

    public HtmlCrawler(String url){
        this.url = url;
    }

    public String getHtmlSource() {
        WebDriver driver = openDriver();
        driver.get(url);
        String res = driver.getPageSource();
        close();
        return res;
    }

    public void write(String path) {

    }

    /**
     * 打开驱动
     * @return
     */
    private WebDriver openDriver(){
        if(driver == null) {
            System.getProperties().setProperty("webdriver.chrome.driver", "./driver/chromedriver");
            WebDriver driver = new ChromeDriver();
            this.driver = driver;
        }
        return this.driver;
    }

    /**
     * 关闭驱动
     */
    public void close(){
        if (driver != null){
            driver.quit();
            driver = null;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        HtmlCrawler htmlCrawler = new HtmlCrawler("https://www.zhihu.com/question/22913650");
        System.out.println(htmlCrawler.getHtmlSource());
        Thread.sleep(10*1000);
        htmlCrawler.close();
    }
}

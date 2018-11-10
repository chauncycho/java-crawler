package crawlers.selenium_crawler;

import crawlers.Crawler;
import crawlers.Utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class HtmlCrawler implements Crawler,Runnable {
    private String url;
    private WebDriver driver = null;
    private String source;


    public void run() {
        write("./output");
    }

    public HtmlCrawler(String url){
        this.url = url;
    }

    public String getHtmlSource() {
        WebDriver driver = openDriver();
        driver.get(url);
        this.source = driver.getPageSource();
        close();
        return this.source;
    }

    public void write(String path) {
        if (this.source == null){//判断网页是否读取过
            getHtmlSource();
        }
        try {
            //写文件
            Utils.write(path,this.source,new URL(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
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
        HtmlCrawler htmlCrawler = new HtmlCrawler("http://image.baidu.com/search/index?tn=baiduimage&ct=201326592&lm=-1&cl=2&ie=gb18030&word=%CD%BC%C6%AC&fr=ala&ala=1&alatpl=others&pos=0");
        htmlCrawler.write("./output");
    }

}

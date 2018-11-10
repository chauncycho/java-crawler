package crawlers.jsoup_crawler;

import crawlers.Crawler;
import crawlers.Utils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HtmlCrawler implements Runnable, Crawler {
    private String url = null;//网页路径
    private Document document = null;//网页文件
    private int connectCount = 0;//连接次数

    public HtmlCrawler(String url){
        this.url = url;
    }


    public void run() {
        write("./output");
    }

    /**
     * 得到HTML源文件
     * @return (String)HTML源文件
     */
    public String getHtmlSource(){
        return getDocument().toString();
    }

    /**
     * 得到HTML源文件
     * @return (org.jsoup.nodes.Document)HTML源文件
     */
    public Document getDocument(){
        try {
            //获取网页
            Document document = Jsoup.connect(url).timeout(1000).get();

            if (document == null || document.toString().trim().equals("")){
                Utils.setProxy();//换代理ip
                System.out.println(connectCount++);

                if(connectCount > 10){//如果死活连接不上，就放弃
                    return null;
                }

                return getDocument();//继续爬
            }
            this.document = document;
            return document;
        } catch (IOException e) {
            System.out.println("出现链接超时等其他情况");
            Utils.setProxy();// 换代理ip
            System.out.println(connectCount++);

            if(connectCount > 20){//如果死活连接不上，就放弃
                return null;
            }

            return getDocument();// 继续爬
        }
    }

    /**
     * 写入文件
     * @param path 文件路径
     */
    public void write(String path){
        //写文件
        try {
            File file = Utils.getFile(path,new URL(url));
            if (document == null){//还没爬取过
                getDocument();
            }
            Document document = this.document;

            Utils.write(file,document.toString());
        } catch (NullPointerException e){
            System.out.println("连接失败");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public static void main(String[] args) {
        HtmlCrawler htmlCrawler = new HtmlCrawler("https://movie.douban.com/");
        htmlCrawler.write("./output/");
    }

}

package crawlers.normal_crawler;

import crawlers.Crawler;
import crawlers.Utils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 简单使用方法：
 * 1.通过实例一个HTMLCrawler，传入网页URL参数
 * 2.使用getHtmlSource方法可得到String类型的网页内容
 * 3.使用write方法，传入输出路径，即可输出一个网页源文件
 *
 * 本爬虫只可爬取静态HTML页面
 */

public class HtmlCrawler implements Runnable, Crawler {
    private String url;
    private String htmlSource = null;

    public void run() {
        this.write("./output");
    }

    public HtmlCrawler(String url){
        this.url = url;
    }

    /**
     * 得到HTML源网页
     * @return
     */
    public String getHtmlSource(){
        return send(url);
    }

    public void write(String path){
        try {
            File file = Utils.getFile(path,new URL(url));

            if (htmlSource != null) {//文件已爬取过
                write(file, htmlSource);
            }else{//文件未爬取过
                write(file, getHtmlSource());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送请求获取静态网页
     * @param url 请求地址
     * @return 静态网页
     */
    private String send(String url){
        StringBuilder sb = new StringBuilder();
        try {
            //URL地址
            URL realUrl = new URL(url);
            System.out.println(realUrl.getHost());
            //获得连接
            URLConnection urlConnection = realUrl.openConnection();

            //连接
            urlConnection.connect();

            //得到响应的body
            BufferedInputStream bis = new BufferedInputStream(urlConnection.getInputStream());
            int length = 0;
            byte[] bytes = new byte[1024];
            while((length = bis.read(bytes)) != -1 ){
                sb.append(new String(bytes,0,length,"utf-8"));
            }
//            System.out.println(sb.toString());

            htmlSource = sb.toString();//存入变量

            bis.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 得到响应的网页后，写入文件
     * @param targetFile 文件写入位置
     */
    private void write(File targetFile, String source){
        Utils.write(targetFile,source);
    }

    /**
     * 示例
     */
    public static void main(String[] args){
        HtmlCrawler hc = new HtmlCrawler("https://www.zhihu.com/topics");
        hc.write("./output");
    }
}

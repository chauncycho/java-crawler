package dynamic.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HtmlCrawler implements Runnable {
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

            if(connectCount > 10){//如果死活连接不上，就放弃
                return null;
            }

            return getDocument();// 继续爬
        }
    }

    public void write(String path){
        File file = getFile(path);
        if (document == null){//还没爬取过
            getDocument();
        }
        Document document = this.document;

        //写文件
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(document.toString().getBytes("utf-8"));
            bos.flush();
            bos.close();
        } catch (NullPointerException e){
            System.out.println("连接失败");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得文件
     * 文件不存在则创建，目录不存在则创建
     * 如果是目录，则创建一个以域名为名的文件
     * @param path 文件路径
     * @return
     */
    private File getFile(String path){
        File file = new File(path);
        file = new File(file.getPath());//转换成绝对路径
        try {

            boolean isFile = file.getName().contains(".");//判断是文件还是文件夹

            if(!file.exists() && isFile){//不存在，同时是文件
                File parentFile = file.getParentFile();
                if (!parentFile.exists()){//父文件不存在
                    parentFile.mkdirs();//创建
                }
                file.createNewFile();
            }

            if(!isFile){//是文件夹
                URL url = new URL(this.url);
                if (!file.exists()){//不存在
                    file.mkdirs();//创建
                }

                //时间戳
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                String timeStamp = format.format(new Date());

                file = new File(file.getPath()+"/"+url.getHost().replace(".","_")+timeStamp+".html");
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static void main(String[] args) {
        HtmlCrawler htmlCrawler = new HtmlCrawler("https://www.zhihu.com/topics");
        htmlCrawler.write("./output/lalal.html");
    }

}

package test;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 连接测试
 */
public class TestConnect {
    /**
     * 发送请求获取静态网页
     * @param url 请求地址
     * @return 静态网页
     */
    public static String send(String url){
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
            System.out.println(sb.toString());
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
     * @param targetPath 文件写入位置
     */
    public static void write(String targetPath, String source){
        try {
            //缓冲输出流
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(targetPath));

            //写文件
            bos.write(source.getBytes("utf-8"));

            //关闭流
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        send("https://www.baidu.com");
        write("./target.html",send("https://www.baidu.com"));
    }
}

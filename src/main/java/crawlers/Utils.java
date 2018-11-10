package crawlers;

import org.jsoup.nodes.Document;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class Utils {
    public static void write(File file, String resource){
        //写文件
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(resource.getBytes("utf-8"));
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入文件
     * @param path 文件路径
     */
    public static void write(String path, String resource, URL url){
        File file = Utils.getFile(path,url);
        write(file,resource);
    }

    /**
     * 获得文件
     * 文件不存在则创建，目录不存在则创建
     * 如果是目录，则创建一个以域名为名的文件
     * @param path 文件路径
     * @return
     */
    public static File getFile(String path, URL url){
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

    /**
     * 更改系统ip
     */
    public static void setProxy(){
        try {
            String ip = getRandomIp();
            String[] ipSplited = ip.split(":");
            String host = ipSplited[0];
            String port = ipSplited[1];

            System.setProperty("http.maxRedirects", "50");
            System.getProperties().setProperty("proxySet", "true");
            System.getProperties().setProperty("http.proxyHost", host);
            System.getProperties().setProperty("http.proxyPort", port);

            System.out.println("ip代理："+ip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取ip文件
     * @return
     */
    private static List<String> readIps(){
        List<String> ips = new Vector<String>();
        BufferedReader br = null;
        try {
            InputStreamReader isr = new InputStreamReader(crawlers.Utils.class.getResourceAsStream("/ips.txt"));
            br = new BufferedReader(isr);
            while(true) {
                String line = br.readLine();
                if (line != null){
                    ips.add(line);
                }else{
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ips;
    }

    /**
     * 得到随机ip
     * @return
     */
    private static String getRandomIp(){
        List<String> ips = readIps();
        Random random = new Random();
        int randomNumber = random.nextInt(ips.size());
        return ips.get(randomNumber);
    }
}

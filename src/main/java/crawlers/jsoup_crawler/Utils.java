package crawlers.jsoup_crawler;

import java.io.*;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class Utils {
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
            InputStreamReader isr = new InputStreamReader(Utils.class.getResourceAsStream("/ips.txt"));
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

    public static void main(String[] args) {
        for (int i = 0 ; i < 100 ; i ++) {
            setProxy();
        }
    }
}

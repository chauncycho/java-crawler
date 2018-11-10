package test;

/**
 * 并发测试
 */
public class ConcurrentTest {
    public static void main(String[] args) {
        int count = 0;//
        int index = 0;//urls下标
        long startTime = System.nanoTime();
        long endTime = 0;
        //网站列表
        String[] urls = new String[]{"https://www.hao123.com/",
                "http://www.youku.com/",
                "https://weibo.com/",
                "https://www.suning.com/?utm_source=hao123&utm_medium=cnxhic2",
                "http://www.qianlong.com/",
                "http://interview.qianlong.com/",
                "http://v.hao123.baidu.com/dongman/",
                "https://tieba.baidu.com/index.html",
                "http://www.ce.cn/",
                "http://www.cnr.cn/",
                "http://www.beijing.gov.cn/",
                "https://www.zhihu.com/topics",
                "https://www.zhihu.com/question/22913650",
                "http://www.yingjiesheng.com/job-003-834-169.html",
                "https://weibo.com/zhihu",
                "https://www.zhihu.com/question/22298352",
                "https://ai.taobao.com/",
                "https://www.hao123.com/licai",
                "http://www.iqiyi.com/"};

        while(index < urls.length){
            if(Thread.activeCount() < 10) {//限制10个线程
                System.out.println("正在爬取第"+(index+1)+"个网站");
                Thread thread = new Thread(new jsoup.crawler.HtmlCrawler(urls[index++]));
                thread.start();
            }
        }
        while(Thread.activeCount() != 1){
            System.out.println("爬取完毕");
            endTime = System.nanoTime();
            break;
        }
        System.out.println("用时"+(endTime - startTime)/ Math.pow(10,9) +"秒");
    }
}

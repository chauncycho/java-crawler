package test;

import crawler.HtmlCrawler;

public class ImageTest {
    public static void main(String[] args) {
        HtmlCrawler htmlCrawler = new HtmlCrawler("https://www.zhihu.com/people/yao-cheng-46/");
        htmlCrawler.write("./output");
    }
}

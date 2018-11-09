package test;

import crawler.HtmlCrawler;

public class ImageTest {
    public static void main(String[] args) {
        HtmlCrawler htmlCrawler = new HtmlCrawler("http://img17.3lian.com/d/file/201701/23/957c2d2610210dfa0fe087790530a8dd.jpg");
        htmlCrawler.write("./image1.jpg");
    }
}

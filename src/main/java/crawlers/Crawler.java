package crawlers;

public interface Crawler {
    /**
     * 得到HTML源文件
     * @return HTML源文件
     */
    public String getHtmlSource();

    /**
     * 写入文件
     * @param path 文件路径
     */
    public void write(String path);
}

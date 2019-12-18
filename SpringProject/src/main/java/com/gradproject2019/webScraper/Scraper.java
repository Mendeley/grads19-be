package com.gradproject2019.webScraper;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.util.regex.Pattern;

public class Scraper extends WebCrawler {

    private String url;

    Scraper() {
        this.url = "https://www.baeldung.com/crawler4j";
    }

    private ScraperOutput scraperOutput;
    private final static Pattern Exclusions = Pattern.compile(".*(\\.(css|js|xml|gif|jpg|png|mp3|mp4|zip|gz|pdf))$");


    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String urlString = url.getURL().toLowerCase();
        return !Exclusions.matcher(urlString).matches();
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        logger.info("URL: {}", url);

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();

            logger.info("Text: {}", text);
            logger.info("Html: {}", html);

            //TODO: Ensure that the scraper output is not created if values are null or there's an exception
            if (text != null && html != null) scraperOutput = new ScraperOutput(text, html);
        }
    }

    public ScraperOutput getScraperOutput() {
        return scraperOutput;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

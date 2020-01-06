package com.gradproject2019.webScraper;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scraper extends WebCrawler {

    private final static Pattern Exclusions = Pattern.compile(".*(\\.(css|js|xml|gif|jpg|png|mp3|mp4|zip|gz|pdf))$");
    private ScraperOutput scraperOutput;

    Scraper() {

    }

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

            //logger.info("Text: {}", text);

            Pattern titlePattern = Pattern.compile("<h1>(\\S+)</h>");

            Matcher titleMatcher = titlePattern.matcher(html);

            if (text != null && html != null && titleMatcher.find()) {
                String codeGroup = titleMatcher.group(1);
                logger.info("Title: {}", codeGroup);

                String scrapedConferenceTitle = "example title";
                Instant scrapedDateTime = Instant.now();
                String scrapedCity = "example city";
                String scrapedDescription = "example description";
                String scrapedTopic = "example topic";

                scraperOutput = new ScraperOutput(scrapedConferenceTitle, scrapedDateTime, scrapedCity, scrapedDescription, scrapedTopic);
            }
        }

        //TODO: Ensure that the scraper output is not created if values are null or there's an exception

    }

    public ScraperOutput getScraperOutput() {
        return scraperOutput;
    }
}

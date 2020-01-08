package com.gradproject2019.webScraper;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventbriteScraper extends WebCrawler {

    private final static Pattern Exclusions = Pattern.compile(".*(\\.(css|js|xml|gif|jpg|png|mp3|mp4|zip|gz|pdf))$");
    private ScraperOutput scraperOutput;

    EventbriteScraper() {

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

            //***Pattern Matcher Version***

        if (page.getParseData() instanceof HtmlParseData) {

            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();

            String html = htmlParseData.getHtml();

            Pattern titlePattern = Pattern.compile("<h1([^>]*)title(.*)>(.*)</h1>");
            Matcher titleMatcher = titlePattern.matcher(html);

            if (titleMatcher.find()) {
                logger.info("Match");
                String tester = titleMatcher.group(3);
                logger.info("Title: {}", tester);

            } else {
                logger.info("No title match");
            }

            Pattern timePattern = Pattern.compile("<time([^>]*)>(.*)</time>");
            Matcher timeMatcher = timePattern.matcher(html);

            if (timeMatcher.find()) {
                logger.info("Match");
                String tester = timeMatcher.group(2);
                logger.info("Time: {}", tester);

            } else {
                logger.info("No time match");
            }

            Pattern cityPattern = Pattern.compile("<div([^>]*)event-details__data(.*)>(.*)</div>");
            Matcher cityMatcher = cityPattern.matcher(html);

            if (cityMatcher.find()) {
                logger.info("Match");
                String tester = cityMatcher.group(3);
                logger.info("City: {}", tester);

            } else {
                logger.info("No city match");
            }

            Pattern descriptionPattern = Pattern.compile("<div([^>]*)content(.*)>(.*)</div>");
            Matcher descriptionMatcher = descriptionPattern.matcher(html);

            if (descriptionMatcher.find()) {
                logger.info("Match");
                String tester = descriptionMatcher.group(3);
                logger.info("Description: {}", tester);

            } else {
                logger.info("No description match");
            }



//
//            if (text != null && html != null && titleMatcher.find()) {
//                String codeGroup = titleMatcher.group(1);
//                logger.info("Title: {}", codeGroup);
//
//                String scrapedConferenceTitle = "example title";
//                Instant scrapedDateTime = Instant.now();
//                String scrapedCity = "example city";
//                String scrapedDescription = "example description";
//                String scrapedTopic = "example topic";
//
//                scraperOutput = new ScraperOutput(scrapedConferenceTitle, scrapedDateTime, scrapedCity, scrapedDescription, scrapedTopic);
//            }
        }

        //TODO: Ensure that the scraper output is not created if values are null or there's an exception
    }

    public ScraperOutput getScraperOutput() {
        return scraperOutput;
    }
}

package gradproject2019.webScraper;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventbriteScraper extends WebCrawler {

    private final static Pattern Exclusions = Pattern.compile(".*(\\.(css|js|xml|gif|jpg|png|mp3|mp4|zip|gz|pdf))$");
    private ScraperOutput scraperOutput = new ScraperOutput();

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

        if (page.getParseData() instanceof HtmlParseData) {

            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();

            String html = htmlParseData.getHtml();
            String text = htmlParseData.getText();

            if (html != null ) {

                Pattern titlePattern = Pattern.compile("<h1([^>]*)title(.*)>(.*)</h1>");
                Matcher titleMatcher = titlePattern.matcher(html);

                if (titleMatcher.find()) {
                    String scrapedConferenceTitle = titleMatcher.group(3);
                    logger.info("Title: {}", scrapedConferenceTitle);
                    scraperOutput.setScrapedConferenceTitle(scrapedConferenceTitle);
                }

                Pattern datePattern = Pattern.compile("<time([^>]*)>([^>]*)<p>(.*)</p>");
                Matcher dateMatcher = datePattern.matcher(html);

                if (dateMatcher.find()) {
                    String scrapedDate = dateMatcher.group(3);
                    logger.info("Date: {}", scrapedDate);
                    //scraperOutput.setScrapedDate(scrapedDate);

                }

                Pattern timePattern = Pattern.compile("(([01]?[0-9]|2[0-3]):[0-5][0-9]).*");
                Matcher timeMatcher = timePattern.matcher(text);

                if (timeMatcher.find()) {
                    String scrapedTime = timeMatcher.group(1);
                    logger.info("Start Time: {}", scrapedTime);
                    //scraperOutput.setScrapedTime(scrapedTime);

                }

                Pattern cityPattern = Pattern.compile("<div([^>]*)class=\"event-details__data\">([^>]*)<p>(.*)</p>([^>]*)<p>(.*)</p>([^>]*)<p>(.*)</p>([^>]*)<p>(.*)</p>");
                Matcher cityMatcher = cityPattern.matcher(html);

                if (cityMatcher.find()) {
                    String scrapedCity = cityMatcher.group(7);
                    logger.info("City: {}", scrapedCity);
                    scraperOutput.setScrapedCity(scrapedCity);

                }

                Pattern descriptionPattern = Pattern.compile("<strong([^>]*)>(.*)</strong>");
                Matcher descriptionMatcher = descriptionPattern.matcher(html);

                if (descriptionMatcher.find()) {
                    String scrapedDescription = descriptionMatcher.group(2);
                    logger.info("Description: {}", scrapedDescription);
                    scraperOutput.setScrapedDescription(scrapedDescription);

                }

                String scrapedTopic ="";
                logger.info("Topic: {}", scrapedTopic);
                scraperOutput.setScrapedTopic(scrapedTopic);
            }
        }
        //TODO: Ensure that the scraper output is not created if values are null or there's an exception
    }

    public ScraperOutput getScraperOutput() {
        return scraperOutput;
    }

}
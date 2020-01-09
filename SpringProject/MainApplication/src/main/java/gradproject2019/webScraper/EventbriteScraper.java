package gradproject2019.webScraper;

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

        if (page.getParseData() instanceof HtmlParseData) {

            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();

            String html = htmlParseData.getHtml();
            String text = htmlParseData.getText();

            String htmlNoPTag = html.replace("<p>","").replace("</p>", "");

            if (html != null ) {

                Pattern titlePattern = Pattern.compile("<h1([^>]*)title(.*)>(.*)</h1>");
                Matcher titleMatcher = titlePattern.matcher(html);

                if (titleMatcher.find()) {
                    logger.info("Match");
                    String scrapedConferenceTitle = titleMatcher.group(3);
                    logger.info("Title: {}", scrapedConferenceTitle);

                } else {
                    logger.info("No title match");
                }

                Pattern dateTimePattern = Pattern.compile("<time class=\"clrfix\" data-automation=\"event-details-time\"> <p>(.*)</p> </time>");
                Matcher dateTimeMatcher = dateTimePattern.matcher(htmlNoPTag);

                if (dateTimeMatcher.find()) {
                    logger.info("Match");
                    String scrapedDateTime = dateTimeMatcher.group(2);
                    logger.info("dateTime: {}", scrapedDateTime);

                } else {
                    logger.info("No dateTime match");
                }

                Pattern timePattern = Pattern.compile(".*(([01]?[0-9]|2[0-3]):[0-5][0-9])");
                Matcher timeMatcher = timePattern.matcher(text);

                if (timeMatcher.find()) {
                    logger.info("Match");
                    String scrapedTime = timeMatcher.group(1);
                    logger.info("Start Time: {}", scrapedTime);

                } else {
                    logger.info("No time match");
                }

                Pattern cityPattern = Pattern.compile("<div([^>]*)event-details(.*)>(.*)</div>");
                Matcher cityMatcher = cityPattern.matcher(htmlNoPTag);

                if (cityMatcher.find()) {
                    logger.info("Match");
                    String scrapedCity = cityMatcher.group(3);
                    logger.info("City: {}", scrapedCity);

                } else {
                    logger.info("No city match");
                }

                Pattern descriptionPattern = Pattern.compile("<div([^>]*)content(.*)>(.*)</div>");
                Matcher descriptionMatcher = descriptionPattern.matcher(html);

                if (descriptionMatcher.find()) {
                    logger.info("Match");
                    String scrapedDescription = descriptionMatcher.group(3);
                    logger.info("Description: {}", scrapedDescription);

                } else {
                    logger.info("No description match");
                }
            }
        }
        //TODO: Ensure that the scraper output is not created if values are null or there's an exception
    }

    public ScraperOutput getScraperOutput() {
        return scraperOutput;
    }
}
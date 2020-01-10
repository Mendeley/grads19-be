package gradproject2019.webScraper;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReedExhibitionsScraper extends WebCrawler {
    private final static Pattern Exclusions = Pattern.compile(".*(\\.(css|js|xml|gif|jpg|png|mp3|mp4|zip|gz|pdf))$");
    private ScraperOutput scraperOutput = new ScraperOutput();

    ReedExhibitionsScraper() {

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

            //String htmlNoPTag = html.replace("<p>","").replace("</p>", "");

            if (html != null ) {

                Pattern titlePattern = Pattern.compile("<h1([^>]*)title(.*)>(.*)</h1>");
                Matcher titleMatcher = titlePattern.matcher(html);

                if (titleMatcher.find()) {
                    logger.info("Match");
                    String scrapedConferenceTitle = titleMatcher.group(3);
                    logger.info("Title: {}", scrapedConferenceTitle);
                    scraperOutput.setScrapedConferenceTitle(scrapedConferenceTitle);

                } else {
                    logger.info("No title match");
                }

                Pattern datePattern = Pattern.compile("<div([^>]*)listing-hero-header(.*)>(.*)</div>");
                Matcher dateMatcher = datePattern.matcher(html);

                if (dateMatcher.find()) {
                    logger.info("Match");
                    String scrapedDate = dateMatcher.group(2);
                    logger.info("Date: {}", scrapedDate);
                    scraperOutput.setScrapedDate(scrapedDate);

                } else {
                    logger.info("No date match");
                }

                Pattern timePattern = Pattern.compile(".*(([01]?[0-9]|2[0-3]):[0-5][0-9])");
                Matcher timeMatcher = timePattern.matcher(text);

                if (timeMatcher.find()) {
                    logger.info("Match");
                    String scrapedTime = timeMatcher.group(1);
                    logger.info("Start Time: {}", scrapedTime);
                    scraperOutput.setScrapedTime(scrapedTime);

                } else {
                    logger.info("No time match");
                }

                Pattern cityPattern = Pattern.compile("<div([^>]*)event-details(.*)>(.*)</div>");
                Matcher cityMatcher = cityPattern.matcher(html);

                if (cityMatcher.find()) {
                    logger.info("Match");
                    String scrapedCity = cityMatcher.group(3);
                    logger.info("City: {}", scrapedCity);
                    scraperOutput.setScrapedCity(scrapedCity);

                } else {
                    logger.info("No city match");
                }

                Pattern descriptionPattern = Pattern.compile("<div([^>]*)content(.*)>(.*)</div>");
                Matcher descriptionMatcher = descriptionPattern.matcher(html);

                if (descriptionMatcher.find()) {
                    logger.info("Match");
                    String scrapedDescription = descriptionMatcher.group(3);
                    logger.info("Description: {}", scrapedDescription);
                    scraperOutput.setScrapedDescription(scrapedDescription);

                } else {
                    logger.info("No description match");
                }
            }
        }
        //TODO: Ensure that the scraper output is not created if values are null or there's an exception
    }

}

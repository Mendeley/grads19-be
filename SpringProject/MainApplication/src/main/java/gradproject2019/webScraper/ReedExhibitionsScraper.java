package gradproject2019.webScraper;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;

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


            if (html != null ) {

                Pattern titlePattern = Pattern.compile("<div([^>]*)data-quickedit-field-id(.*)>(.*)</div>");
                Matcher titleMatcher = titlePattern.matcher(html);

                if (titleMatcher.find()) {
                    logger.info("Match");
                    String scrapedConferenceTitle = titleMatcher.group(3);
                    logger.info("Title: {}", scrapedConferenceTitle);
                    scraperOutput.setScrapedConferenceTitle(scrapedConferenceTitle);

                } else {
                    logger.info("No title match");
                }

                Pattern datePattern = Pattern.compile("<div([^>]*)field--name-event-formatted-date(.*)>(.*)</div>");
                Matcher dateMatcher = datePattern.matcher(html);

                if (dateMatcher.find()) {
                    logger.info("Match");
                    String scrapedDate = dateMatcher.group(3);
                    String replaceDateHtmlEntities = HtmlUtils.htmlUnescape(scrapedDate);
                    logger.info("Date: {}", replaceDateHtmlEntities);
                    scraperOutput.setScrapedDate(replaceDateHtmlEntities);

                } else {
                    logger.info("No date match");
                }

                String scrapedTime ="";
                logger.info("Start Time: {}", scrapedTime);
                scraperOutput.setScrapedTime(scrapedTime);


                Pattern cityPattern = Pattern.compile("<span([^>]*)taxonomy-term([^>]*)count-1([^>]*)>(.*)</span>");
                Matcher cityMatcher = cityPattern.matcher(html);

                if (cityMatcher.find()) {
                    logger.info("Match");
                    String scrapedCity = cityMatcher.group(4);
                    logger.info("City: {}", scrapedCity);
                    scraperOutput.setScrapedCity(scrapedCity);

                } else {
                    logger.info("No city match");
                }

                Pattern descriptionPattern = Pattern.compile("<div([^>]*)field--name-field-short-descriptio([^>]*)>([^>]*)<p><span><span><strong><span><span>(.*)</span></span></strong></span></span></p>");
                Matcher descriptionMatcher = descriptionPattern.matcher(html);

                if (descriptionMatcher.find()) {
                    logger.info("Match");
                    String scrapedDescription = descriptionMatcher.group(4);
                    String replaceDescriptionHtmlEntities = HtmlUtils.htmlUnescape(scrapedDescription);
                    logger.info("Description: {}", replaceDescriptionHtmlEntities);
                    scraperOutput.setScrapedDescription(replaceDescriptionHtmlEntities);

                } else {
                    logger.info("No description match");
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

package gradproject2019.webScraper;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.jpa.internal.util.PersistenceUtilHelper;
import org.springframework.web.util.HtmlUtils;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
                    String scrapedConferenceTitle = titleMatcher.group(3);
                    logger.info("Title: {}", scrapedConferenceTitle);
                    scraperOutput.setScrapedConferenceTitle(scrapedConferenceTitle);

                }

                Pattern datePattern = Pattern.compile("<div([^>]*)field--name-event-formatted-date(.*)>(.*)</div>");
                Matcher dateMatcher = datePattern.matcher(html);

                if (dateMatcher.find()) {
                    String scrapedDate = dateMatcher.group(3);
                    String replaceDateHtmlEntities = HtmlUtils.htmlUnescape(scrapedDate);
                    logger.info("Full date: {}", replaceDateHtmlEntities);


                    String indexSingleBeg = replaceDateHtmlEntities.substring(1, 3);
                    String indexDoubleBeg = replaceDateHtmlEntities.substring(2, 4);
                    String indexSingleSingle= replaceDateHtmlEntities.substring(7, 8);
                    String indexSingleDouble = replaceDateHtmlEntities.substring(8, 9);
                    String indexDoubleSingle = replaceDateHtmlEntities.substring(8, 9);
                    String indexDoubleDouble = replaceDateHtmlEntities.substring(9, 10);

                    if (!replaceDateHtmlEntities.contains("-")) {
                        scraperOutput.setScrapedDate(replaceDateHtmlEntities);
                    }

                    else if ((indexSingleBeg.equals("th") || indexSingleBeg.equals("st") || indexSingleBeg.equals("rd") || indexSingleBeg.equals("nd") )&& indexSingleSingle.equals(" ")) {
                        String finalString1 = replaceDateHtmlEntities.substring(0,3);
                        String finalString2 = replaceDateHtmlEntities.substring(8);
                        String finalString = (finalString1 + " " + finalString2);
                        logger.info("Date: {}", finalString);
                        scraperOutput.setScrapedDate(finalString);

                    }

                    else if ( (indexSingleBeg.equals("th") || indexSingleBeg.equals("st") || indexSingleBeg.equals("rd") || indexSingleBeg.equals("nd") )&& indexSingleDouble.equals(" ")) {
                        String finalString1 = replaceDateHtmlEntities.substring(0,3);
                        String finalString2 = replaceDateHtmlEntities.substring(9);
                        String finalString = (finalString1 + " " + finalString2);
                        logger.info("Date: {}", finalString);
                        scraperOutput.setScrapedDate(finalString);
                    }

                    else if (( indexDoubleBeg.equals("th") || indexDoubleBeg.equals("st") || indexDoubleBeg.equals("rd") || indexDoubleBeg.equals("nd") ) && indexDoubleSingle.equals(" ")) {
                        String finalString1 = replaceDateHtmlEntities.substring(0,4);
                        String finalString2 = replaceDateHtmlEntities.substring(9);
                        String finalString = (finalString1 + " " + finalString2);
                        logger.info("Date: {}", finalString);
                        scraperOutput.setScrapedDate(finalString);
                    }

                    else if ( ( indexDoubleBeg.equals("th") || indexDoubleBeg.equals("st") || indexDoubleBeg.equals("rd") || indexDoubleBeg.equals("nd") )  && indexDoubleDouble.equals(" ")) {
                        String finalString1 = replaceDateHtmlEntities.substring(0,4);
                        String finalString2 = replaceDateHtmlEntities.substring(10);
                        String finalString = (finalString1 + " " + finalString2);
                        logger.info("Date: {}", finalString);
                        scraperOutput.setScrapedDate(finalString);
                    }

                    else {
                        scraperOutput.setScrapedDate(replaceDateHtmlEntities);
                    } }

                String scrapedTime = "00:00:00";
                logger.info("Time: {}", scrapedTime);
                scraperOutput.setScrapedTime(scrapedTime);

                Pattern cityPattern = Pattern.compile("<span([^>]*)taxonomy-term([^>]*)count-1([^>]*)>(.*)</span>");
                Matcher cityMatcher = cityPattern.matcher(html);

                if (cityMatcher.find()) {
                    String scrapedCity = cityMatcher.group(4);
                    logger.info("City: {}", scrapedCity);
                    scraperOutput.setScrapedCity(scrapedCity);

                }

                Pattern descriptionPattern = Pattern.compile("<div([^>]*)field--name-field-short-descriptio([^>]*)>([^>]*)<p><span><span><strong><span><span>(.*)</span></span></strong></span></span></p>");
                Matcher descriptionMatcher = descriptionPattern.matcher(html);

                if (descriptionMatcher.find()) {
                    String scrapedDescription = descriptionMatcher.group(4);
                    String replaceDescriptionHtmlEntities = HtmlUtils.htmlUnescape(scrapedDescription);
                    logger.info("Description: {}", replaceDescriptionHtmlEntities);
                    scraperOutput.setScrapedDescription(replaceDescriptionHtmlEntities);

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

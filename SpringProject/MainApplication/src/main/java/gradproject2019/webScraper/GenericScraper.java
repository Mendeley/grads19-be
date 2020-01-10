package gradproject2019.webScraper;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenericScraper extends WebCrawler {
    private final static Pattern Exclusions = Pattern.compile(".*(\\.(css|js|xml|gif|jpg|png|mp3|mp4|zip|gz|pdf))$");
    private ScraperOutput scraperOutput = new ScraperOutput();

    GenericScraper() {

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

            if (html != null && text != null) {

                Pattern titlePattern = Pattern.compile("<h1([^>]*)title(.*)>(.*)</h1>");
                Matcher titleMatcher = titlePattern.matcher(html);

                if (titleMatcher.find()) {
                    String scrapedConferenceTitle = titleMatcher.group(3);
                    scraperOutput.setScrapedConferenceTitle(scrapedConferenceTitle);

                }

                Pattern datePattern = Pattern.compile("<div([^>]*)date(.*)>(.*)</div>");
                Matcher dateMatcher = datePattern.matcher(html);

                if (dateMatcher.find()) {
                    String scrapedDate = dateMatcher.group(3);
                    scraperOutput.setScrapedDate(scrapedDate);

                }

                Pattern timePattern = Pattern.compile(".*(([01]?[0-9]|2[0-3]):[0-5][0-9])");
                Matcher timeMatcher = timePattern.matcher(text);

                if (timeMatcher.find()) {
                    String scrapedTime = timeMatcher.group(1);
                    scraperOutput.setScrapedTime(scrapedTime);

                }

                Pattern cityPattern = Pattern.compile("<div([^>]*)location(.*)>(.*)</div>");
                Matcher cityMatcher = cityPattern.matcher(html);

                if (cityMatcher.find()) {
                    String scrapedCity = cityMatcher.group(3);
                    scraperOutput.setScrapedCity(scrapedCity);

                }

                Pattern descriptionPattern = Pattern.compile("<div([^>]*)content(.*)>(.*)</div>");
                Matcher descriptionMatcher = descriptionPattern.matcher(html);

                if (descriptionMatcher.find()) {
                    String scrapedDescription = descriptionMatcher.group(3);
                    scraperOutput.setScrapedDescription(scrapedDescription);

                }
            }
        }
    }
    public ScraperOutput getScraperOutput() {
        return scraperOutput;
    }
}

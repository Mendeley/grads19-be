package com.gradproject2019;

import com.gradproject2019.webScraper.EventbriteScraper;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class ScraperTest {

    @InjectMocks
    private EventbriteScraper scraper;

    private Page constructTestPage(String conferenceURL) {
        WebURL url = new WebURL();
        url.setURL(conferenceURL);

        Page page = new Page(url);
        page.setParseData(createHtmlParseData());

        return page;
    }

    private HtmlParseData createHtmlParseData() {
        HtmlParseData testData = new HtmlParseData();
        testData.setHtml("<h1>set some html here TOMORROW</h1>");
        testData.setText("test text");

        return testData;
    }

//    @Test
//    public void shouldScrapeValidURL() throws Exception {
//        String validConferenceURL = "https://www.eventbrite.co.uk/e/bemoredigital-conference-2020-tickets-75866405461?aff=ebdssbdestsearch";
//
//        Page testPage = constructTestPage(validConferenceURL);
//
//        scraper.visit(testPage);
//        ScraperOutput actualOutput = scraper.getScraperOutput();
//
//        assertThat(actualOutput.getScrapedConferenceTitle()).isEqualTo("example title");
//        assertThat(actualOutput.getScrapedDateTime()).isEqualTo(Instant.now());
//        assertThat(actualOutput.getScrapedCity()).isEqualTo("example city");
//        assertThat(actualOutput.getScrapedDescription()).isEqualTo("example description");
//        assertThat(actualOutput.getScrapedTopic()).isEqualTo("example topic");
//    }

    @Test
    public void shouldVisitReturnsFalse() throws Exception {

        WebURL url = new WebURL();
        url.setURL("https://www.example.css");

        boolean b = scraper.shouldVisit(null, url);

        assertThat(b).isFalse();
    }

    @Test
    public void shouldVisitReturnsTrue() throws Exception {

        WebURL url = new WebURL();
        url.setURL("https://www.eventbrite.co.uk/e/bemoredigital-conference-2020-tickets-75866405461?aff=ebdssbdestsearch");

        boolean b = scraper.shouldVisit(null, url);

        assertThat(b).isTrue();
    }
}
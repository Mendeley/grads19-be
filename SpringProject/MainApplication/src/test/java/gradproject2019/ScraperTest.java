package gradproject2019;

import gradproject2019.webScraper.EventbriteScraper;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import gradproject2019.webScraper.ScraperOutput;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class ScraperTest {

    @InjectMocks
    private EventbriteScraper scraper;
    private ScraperOutput scraperOutput;

    private Page constructTestPage(String conferenceURL) {
        WebURL url = new WebURL();
        url.setURL(conferenceURL);

        Page page = new Page(url);
        page.setParseData(createHtmlParseData());

        return page;
    }

    private HtmlParseData createHtmlParseData() {
        HtmlParseData testData = new HtmlParseData();
        testData.setHtml("<h1>test html/h1>");
        testData.setText("#BeMoreDigital Conference 2020 Thu, 5 March 2020, 09:00 – 18:30 GMT");

        return testData;
    }

//    @Test
//    public void shouldScrapeValidURL() throws Exception {
//        String validConferenceURL = "https://www.eventbrite.co.uk/e/bemoredigital-conference-2020-tickets-75866405461?aff=ebdssbdestsearch";
//
//        Page testPage = constructTestPage(validConferenceURL);
//
//        scraper.visit(testPage);
//        ScraperOutput actualOutput = scraperOutput;
//
//        assertThat(actualOutput.getScrapedConferenceTitle()).isEqualTo("example title");
//        assertThat(actualOutput.getScrapedDate()).isEqualTo("example date");
//        assertThat(actualOutput.getScrapedTime()).isEqualTo("8:30");
//        assertThat(actualOutput.getScrapedCity()).isEqualTo("example city");
//        assertThat(actualOutput.getScrapedDescription()).isEqualTo("example description");
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
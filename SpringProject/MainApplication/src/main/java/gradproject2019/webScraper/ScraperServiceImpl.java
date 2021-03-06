package gradproject2019.webScraper;


import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.springframework.stereotype.Service;

@Service
public class ScraperServiceImpl implements ScraperService {

    @Override
    public ScraperResponseDto startScraper(String url) throws Exception {
        CrawlConfig config = new CrawlConfig();

        config.setCrawlStorageFolder("/tmp/crawler4j");

        config.setPolitenessDelay(100);

        config.setMaxDepthOfCrawling(0);

        config.setCleanupDelaySeconds(1);
        config.setThreadMonitoringDelaySeconds(1);
        config.setThreadShutdownDelaySeconds(1);
        config.setShutdownOnEmptyQueue(true);

        config.setMaxPagesToFetch(1);

        config.setIncludeBinaryContentInCrawling(false);

        config.setResumableCrawling(false);

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        controller.addSeed(url);

        int numberOfCrawlers = 1;

        ScraperOutput scraperOutput;

        if (url.contains("eventbrite")) {
            CrawlController.WebCrawlerFactory<EventbriteScraper> factory = EventbriteScraper::new;
            controller.start(factory, numberOfCrawlers);

            scraperOutput = EventbriteScraper.getScraperOutput();
        } else if (url.contains("reedexhibitions")) {
            CrawlController.WebCrawlerFactory<ReedExhibitionsScraper> factory = ReedExhibitionsScraper::new;
            controller.start(factory, numberOfCrawlers);

            scraperOutput = ReedExhibitionsScraper.getScraperOutput();
        } else {
            CrawlController.WebCrawlerFactory<GenericScraper> factory = GenericScraper::new;
            controller.start(factory, numberOfCrawlers);

            scraperOutput = GenericScraper.getScraperOutput();
        }

        return scraperOutputToResponseDto(scraperOutput);
    }

    private ScraperResponseDto scraperOutputToResponseDto(ScraperOutput scraperOutput) {
        return new ScraperResponseDto().from(scraperOutput);
    }
}
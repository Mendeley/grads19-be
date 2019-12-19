package com.gradproject2019.webScraper;


import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ScraperServiceImpl implements ScraperService {

    @Override
    public void startScraper(String url) throws Exception {
        CrawlConfig config = new CrawlConfig();

        config.setCrawlStorageFolder("/tmp/crawler4j");

        config.setPolitenessDelay(1000);

        config.setMaxDepthOfCrawling(0);

        config.setMaxPagesToFetch(2);

        config.setIncludeBinaryContentInCrawling(false);

        config.setResumableCrawling(false);

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig,pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        controller.addSeed("https://www.eventbrite.co.uk/e/women-driven-development-conference-tickets-84582421299?aff=ebdssbdestsearch");

        int numberOfCrawlers = 1;

        CrawlController.WebCrawlerFactory<Scraper> factory = Scraper::new;

        controller.start(factory, numberOfCrawlers);
    }

}
≠
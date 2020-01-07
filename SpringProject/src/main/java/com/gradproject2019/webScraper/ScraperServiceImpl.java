package com.gradproject2019.webScraper;


import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.springframework.stereotype.Service;

@Service
public class ScraperServiceImpl implements ScraperService {

    @Override
    public void startScraper(String url) throws Exception {
        CrawlConfig config = new CrawlConfig();

        config.setCrawlStorageFolder("/tmp/crawler4j");

        config.setPolitenessDelay(100);

        config.setMaxDepthOfCrawling(0);

        config.setMaxPagesToFetch(1);

        config.setIncludeBinaryContentInCrawling(false);

        config.setResumableCrawling(false);

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig,pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        controller.addSeed(url);

        int numberOfCrawlers = 1;

        CrawlController.WebCrawlerFactory<Scraper> factory = Scraper::new;

        controller.start(factory, numberOfCrawlers);
    }

}
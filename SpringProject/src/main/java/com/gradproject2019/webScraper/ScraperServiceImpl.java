package com.gradproject2019.webScraper;


import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class ScraperServiceImpl implements ScraperService {

    @Override
    public void startScraper(String url) throws Exception {
        CrawlConfig config = new CrawlConfig();

        config.setCrawlStorageFolder("/tmp/crawler4j");

        config.setPolitenessDelay(1000);

        config.setMaxDepthOfCrawling(1);

        config.setMaxPagesToFetch(1000);

        config.setIncludeBinaryContentInCrawling(false);

        config.setResumableCrawling(false);

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig,pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
    }

}

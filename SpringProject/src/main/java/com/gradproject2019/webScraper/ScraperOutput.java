package com.gradproject2019.webScraper;

public class ScraperOutput {
    private String text;
    private String html;

    public ScraperOutput(String text, String html) {
        this.text = text;
        this.html = html;
    }

    public String getText() {
        return text;
    }

    public String getHtml() {
        return html;
    }
}

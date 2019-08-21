package com.pirateway.scraper.service;

import com.pirateway.scraper.exception.DataValidateException;
import com.pirateway.scraper.api.service.IForkService;
import com.pirateway.scraper.model.entity.Fork;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BetScraperService {

    @Autowired
    IForkService forkService;

    public void refresh() throws DataValidateException, IOException {
        Document doc = Jsoup
                .connect("https://positivebet.com/ru/bets/index/")
                .userAgent("Chrome/76.0.3809.100")
                .referrer("http://www.yandex.ru")
                .get();

        Elements rows = doc.select("div#gridBets.grid-view").select("tbody");

        int i = 0;
        for (Element row : rows.select("tr")) {
            i++;
            String forkType = row.select("td").get(0).select("a").get(2).select("img").attr("alt");
            String forkAge = row.select("td").get(0).select("div").select("a").text();
            String profit = row.select("td").get(1).select("nobr").text();
            String evenOneBk = row.select("td").get(2).select("div").get(0).text();
            String evenTwoBk = row.select("td").get(2).select("div").get(1).text();
            String eventOneDescription = "Событие скрыто!";
            String eventTwoDescription = "Событие скрыто!";
            String eventOneLink = "#";
            String eventTwoLink = "#";
            String eventOneTextLink = "Событие скрыто!";
            String eventTwoTextLink = "Событие скрыто!";
            String eventOneMoving = "";
            String eventTwoMoving = "";
            String eventOneCoefficient = "";
            String eventTwoCoefficient = "";
            String eventOneForksCount = "";
            String eventTwoForksCount = "";

            try {
                eventOneDescription = row.select("td").get(3).select("div").get(0).text() +
                        row.select("td").get(3).select("small").get(0).text();
                eventTwoDescription = row.select("td").get(3).select("div").get(1).text() +
                        row.select("td").get(3).select("small").get(1).text();
                eventOneLink = row.select("td").get(4).select("nobr").get(0).select("a").attr("href");
                eventOneTextLink = row.select("td").get(4).select("nobr").get(0).select("a").text();
                eventTwoLink = row.select("td").get(4).select("nobr").get(1).select("a").attr("href");
                eventTwoTextLink = row.select("td").get(4).select("nobr").get(1).select("a").text();

                eventOneMoving = row.select("td").get(5).select("nobr").get(0).select("a").select("img").attr("alt");
                eventTwoMoving = row.select("td").get(5).select("nobr").get(1).select("a").select("img").attr("alt");
                eventOneCoefficient = row.select("td").get(5).select("nobr").get(0).text();
                eventTwoCoefficient = row.select("td").get(5).select("nobr").get(1).text();

            } catch (Exception e){}

            Fork fork = new Fork(
                    String.valueOf(i),
                    "",
                    forkType,
                    forkAge,
                    profit);

            fork.setEventOneBk(evenOneBk);
            fork.setEventOneDescription(eventOneDescription);
            fork.setEventOneCoefficient(eventOneCoefficient);
            fork.setEventOneLink(crawl("https://positivebet.com"+eventOneLink));
            fork.setEventOneTextLink(eventOneTextLink);

            fork.setEventTwoBk(evenTwoBk);
            fork.setEventTwoDescription(eventTwoDescription);
            fork.setEventTwoCoefficient(eventTwoCoefficient);
            fork.setEventTwoLink(crawl("https://positivebet.com"+eventTwoLink));
            fork.setEventTwoTextLink(eventTwoTextLink);

            forkService.create(fork);
        }
    }

    public void clear() throws DataValidateException {
        forkService.clear();
    }

    private String crawl(String url) throws IOException {
        Connection.Response response = Jsoup.connect(url).followRedirects(false).execute();
        String link = response.parse().getElementsByTag("meta").get(0).attr("content");
        return link.substring(link.indexOf('=')+1);
    }
}

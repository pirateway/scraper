package com.pirateway.scraper.service;

import com.pirateway.scraper.exception.DataValidateException;
import com.pirateway.scraper.api.service.IForkService;
import com.pirateway.scraper.model.entity.Fork;
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
                .referrer("http://www.google.com")
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
                String response1 = Jsoup.connect("https://positivebet.com"+eventOneLink).followRedirects(true).execute().url().toExternalForm();
                System.out.println(response1);

                eventTwoLink = row.select("td").get(4).select("nobr").get(1).select("a").attr("href");
                String response2 = Jsoup.connect("https://positivebet.com"+eventTwoLink).followRedirects(true).execute().url().toExternalForm();
                System.out.println(response2);

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

            fork.setEventOneBk("1) "+evenOneBk);
            fork.setEventOneDescription("1) "+eventOneDescription);
            fork.setEventOneCoefficient("1) "+eventOneCoefficient);
            fork.setEventOneLink("https://positivebet.com"+eventOneLink);

            fork.setEventTwoBk("2) "+evenTwoBk);
            fork.setEventTwoDescription("2) "+eventTwoDescription);
            fork.setEventTwoCoefficient("2) "+eventTwoCoefficient);
            fork.setEventTwoLink("https://positivebet.com"+eventTwoLink);


            forkService.create(fork);
        }
    }

    public void clear() throws DataValidateException {
        forkService.clear();
    }
}

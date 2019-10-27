package com.pirateway.scraper.service;

import com.pirateway.scraper.api.service.IBetScraperService;
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
import java.util.HashMap;
import java.util.Map;

@Component
public class BetScraperService implements IBetScraperService {

    @Autowired
    IForkService forkService;

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BetScraperService.class);
    private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1636.0 Safari/537.36";

    @Override
    public HashMap<String, String> login(String loginFormUrl, String username, String password) throws IOException {

        HashMap<String, String> cookies = new HashMap<>();
        HashMap<String, String> formData = new HashMap<>();

        Connection.Response loginForm = Jsoup
                .connect(loginFormUrl)
                .method(Connection.Method.GET)
                .userAgent(USER_AGENT)
                .execute();

        Document loginDoc = loginForm.parse();
        cookies.putAll(loginForm.cookies());
        String authToken = loginDoc.select("#login-form > input[type=hidden]").val();

        formData.put("YII_CSRF_TOKEN", authToken);
        formData.put("UserLogin[username]", username);
        formData.put("UserLogin[password]", password);
        formData.put("UserLogin[rememberMe]", "0");
        formData.put("UserLogin[rememberMe]", "1");
        formData.put("yt0", "");

        Connection.Response homePage = Jsoup.connect(loginFormUrl)
                .cookies(cookies)
                .data(formData)
                .method(Connection.Method.POST)
                .userAgent(USER_AGENT)
                .execute();

        cookies.put("PHPSESSID", homePage.cookie("PHPSESSID"));
        return cookies;
    }

    public void refresh() throws DataValidateException, IOException {

        HashMap<String, String> cookies = login(
                "https://positivebet.com/ru/user/login",
                "alexpet20@mail.ru",
                "ihaihaiha");

        Connection.Response forkPage = Jsoup
                .connect("https://positivebet.com/ru/bets/index?markNewEvent=false&perPage=20&crid=&ajax=gridBets")
                .header("X-Requested-With", "XMLHttpRequest")
                .header("Accept", "*/*")
                .header("Accept-Encoding", "gzip, deflate, sdch")
                .header("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4")
                .cookies(cookies)
                .userAgent(USER_AGENT)
                .execute();

        Document doc = forkPage.parse();
//        System.out.println(doc);
        Elements rows = doc.select("div#gridBets.grid-view").select("tbody");
        int i = 0;
        for (Element row : rows.select("tr")) {
            i++;
            String forkType = "";
            String forkAge = "";
            String profit = "";
            String evenOneBk = "";
            String evenTwoBk = "";
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
                System.out.print("Fork type - ");
                forkType = row.select("td").get(0).select("a").get(2).select("img").attr("alt");
                System.out.println(forkType);
            }catch(Exception e){
                System.out.println(" error: "+e.getMessage());
            }
            try {
                System.out.print("Fork age - ");
                forkAge = row.select("td").get(0).select("div").select("a").text();
                System.out.println(forkAge);
            } catch (Exception e) {
                System.out.println(" error: "+e.getMessage());
            }
            try {
                System.out.print("Profit fork - ");
                profit = row.select("td").get(1).select("nobr").text();
                System.out.println(profit);
            } catch (Exception e) {
                System.out.println(" error: "+e.getMessage());
            }
            try {
                System.out.print("Even One Bookmaker - ");
                evenOneBk = row.select("td").get(2).children().first().text();
                System.out.println(evenOneBk);
            } catch (Exception e) {
                System.out.println(" error: "+e.getMessage());
            }
            try {
                System.out.print("Even Two Bookmaker - ");
                evenTwoBk = row.select("td").get(2).children().last().text();
                System.out.println(evenTwoBk);
            } catch (Exception e) {
                System.out.println(" error: "+e.getMessage());
            }
            try {
                System.out.print("Even One Description - ");
                eventOneDescription = row.select("td").get(3).child(1).text() + " " +
                            row.select("td").get(3).select("small").get(0).text();
                System.out.println(eventOneDescription);
            } catch (Exception e) {
                System.out.println(" error: "+e.getMessage());
            }
            try {
                System.out.print("Even Two Description - ");
                eventTwoDescription = row.select("td").get(3).child(5).text() + " " +
                            row.select("td").get(3).select("small").get(1).text();
                System.out.println(eventTwoDescription);
            } catch (Exception e) {
                System.out.println(" error: "+e.getMessage());
            }
            try {
                System.out.print("Even One Link - ");
                eventOneLink = row.select("td").get(4).select("nobr").get(0).select("a").attr("href");
                System.out.println(eventOneLink);
            } catch (Exception e) {
                System.out.println(" error: "+e.getMessage());
            }
            try {
                System.out.print("Even One Text Link - ");
                eventOneTextLink = row.select("td").get(4).select("nobr").get(0).select("a").text();
                System.out.println(eventOneTextLink);
            } catch (Exception e) {
                System.out.println(" error: "+e.getMessage());
            }
            try {
                System.out.print("Even Two Link - ");
                eventTwoLink = row.select("td").get(4).select("nobr").get(1).select("a").attr("href");
                System.out.println(eventTwoLink);
            } catch (Exception e) {
                System.out.println(" error: "+e.getMessage());
            }
            try {
                System.out.print("Even Two Text Link - ");
                eventTwoTextLink = row.select("td").get(4).select("nobr").get(1).select("a").text();
                System.out.println(eventTwoTextLink);
            } catch (Exception e) {
                System.out.println(" error: "+e.getMessage());
            }

            try {
                System.out.print("Even One Moving - ");
                eventOneMoving = row.select("td").get(5).select("nobr").get(0).select("a").select("img").attr("alt");
                System.out.println(eventOneMoving);
            } catch (Exception e) {
                System.out.println(" error: "+e.getMessage());
            }
            try {
                System.out.print("Even Two Moving - ");
                eventTwoMoving = row.select("td").get(5).select("nobr").get(1).select("a").select("img").attr("alt");
                System.out.println(eventTwoMoving);
            } catch (Exception e) {
                System.out.println(" error: "+e.getMessage());
            }
            try {
                System.out.print("Even One Coefficient - ");
                eventOneCoefficient = row.select("td").get(5).select("nobr").get(0).text();
                System.out.println(eventOneCoefficient);
            } catch (Exception e) {
                System.out.println(" error: "+e.getMessage());
            }
            try {
                System.out.print("Even Two Coefficient - ");
                eventTwoCoefficient = row.select("td").get(5).select("nobr").get(1).text();
                System.out.println(eventOneCoefficient);
            } catch (Exception e) {
                System.out.println(" error: "+e.getMessage());
            }

            Fork fork = new Fork(
                    String.valueOf(i),
                    "",
                    forkType,
                    forkAge,
                    profit);

            fork.setEventOneBk(evenOneBk);
            fork.setEventOneDescription(eventOneDescription);
            fork.setEventOneCoefficient(eventOneCoefficient);
            fork.setEventOneLink(crawl("https://positivebet.com" + eventOneLink, cookies));
            fork.setEventOneTextLink(eventOneTextLink);

            fork.setEventTwoBk(evenTwoBk);
            fork.setEventTwoDescription(eventTwoDescription);
            fork.setEventTwoCoefficient(eventTwoCoefficient);
            fork.setEventTwoLink(crawl("https://positivebet.com" + eventTwoLink, cookies));
            fork.setEventTwoTextLink(eventTwoTextLink);

            forkService.create(fork);
        }
    }

    public void clear() throws DataValidateException {
        forkService.clear();
    }

    private String crawl(String url, Map<String, String> cookies) throws IOException {
        Connection.Response response =
                Jsoup.connect(url).cookies(cookies).followRedirects(false).execute();
        String link = response.parse().getElementsByTag("meta").get(0).attr("content");
        return link.substring(link.indexOf('=') + 1);
    }
}

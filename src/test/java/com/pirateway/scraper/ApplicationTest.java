package com.pirateway.scraper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ApplicationTest {

    public static void main(String[] args) throws IOException {
        HashMap<String, String> cookies = new HashMap<>();
        HashMap<String, String> formData = new HashMap<>();
        String loginFormUrl = "https://positivebet.com/ru/user/login";
        String loginActionUrl = "https://positivebet.com/ru/user/login";
        String username = "alexpet20@mail.ru";
        String password = "ihaihaiha";
        String userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:70.0) Gecko/20100101 Firefox/70.0";

        Connection.Response loginForm = Jsoup
                .connect(loginFormUrl)
                .method(Connection.Method.GET)
                .userAgent(userAgent)
                .execute();

        Document loginDoc = loginForm.parse();
        cookies.putAll(loginForm.cookies());

       // printInformation(cookies,"COOKIES");
        printInformation(loginForm.headers(),"HEADERS");

        String authToken = loginDoc.select("#login-form > input[type=hidden]").val();
        formData.put("YII_CSRF_TOKEN", authToken);
        formData.put("UserLogin[username]", username);
        formData.put("UserLogin[password]", password);


        Connection.Response homePage = Jsoup.connect(loginActionUrl)
                .cookies(cookies)
                .data(formData)
                .method(Connection.Method.POST)
                .userAgent(userAgent)
                .execute();

        cookies.put("PHPSESSID",homePage.cookie("PHPSESSID"));
       // printInformation(cookies,"COOKIES");
        printInformation(homePage.headers(),"HEADERS");

        Connection.Response forkPage = Jsoup
                .connect("https://positivebet.com/ru/bets/index")
                .cookies(cookies)
                .method(Connection.Method.GET)
                .userAgent(userAgent)
                .execute();

        printInformation(forkPage.headers(),"HEADERS");
        System.out.println(forkPage.parse());

    }

    private static void printInformation(Map<String, String> information, String informationName){
        System.out.println("********************PRINT "+informationName+"******************");
        for (String item:information.keySet()){
            /*System.out.print(item+":");
            System.out.println(information.get(item));*/
        }
    }
}
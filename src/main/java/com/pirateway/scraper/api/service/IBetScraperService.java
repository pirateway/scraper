package com.pirateway.scraper.api.service;

import com.pirateway.scraper.exception.DataValidateException;

import java.io.IOException;
import java.util.HashMap;

public interface IBetScraperService {
    HashMap<String, String> login(String loginFormUrl, String username, String password) throws IOException;
    void refresh() throws DataValidateException, IOException, InterruptedException;
    void clear() throws DataValidateException;
}

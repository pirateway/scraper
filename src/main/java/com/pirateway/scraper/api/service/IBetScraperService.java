package com.pirateway.scraper.api.service;

import com.pirateway.scraper.exception.DataValidateException;

import java.io.IOException;

public interface IBetScraperService {
    void refresh() throws DataValidateException, IOException, InterruptedException;
    void clear() throws DataValidateException;
}

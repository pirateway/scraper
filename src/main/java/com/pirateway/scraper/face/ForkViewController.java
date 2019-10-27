package com.pirateway.scraper.face;

import com.pirateway.scraper.api.service.IBetScraperService;
import com.pirateway.scraper.api.service.IForkService;
import com.pirateway.scraper.exception.DataValidateException;
import com.pirateway.scraper.model.entity.Fork;
import com.pirateway.scraper.service.BetScraperService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@SessionScope
public class ForkViewController {

    @Nullable
    private List<Fork> forks;

    @Nullable
    private Fork selectedFork;

    @NotNull
    private Fork editFork;

    @NotNull
    @Autowired
    private IForkService forkService;

    @NotNull
    @Autowired
    private IBetScraperService betScraperService;

    public Collection<Fork> getForks(
    ) throws DataValidateException {
        forks = new ArrayList<>(forkService.findAll());
        return forks;
    }

    public void setForks(List<Fork> forks) {
        this.forks = forks;
    }

    public void refreshForks(
    ) throws DataValidateException, IOException, InterruptedException {
        betScraperService.clear();
        betScraperService.refresh();
    }

    @NotNull
    public Fork getEditFork(
    ){
        return editFork;
    }

    public void setEditFork(
            @NotNull final Fork editFork
    ) {
        this.editFork = editFork;
    }

    @Nullable
    public Fork getSelectedFork() {
        return selectedFork;
    }

    public void setSelectedFork(
            @Nullable Fork selectedFork
    ) {
        this.selectedFork = selectedFork;
    }

    public void onRowSelect(SelectEvent event) {
        selectedFork = ((Fork) event.getObject());
    }

    public void onRowUnselect(UnselectEvent event) {
        selectedFork = null;
    }

}

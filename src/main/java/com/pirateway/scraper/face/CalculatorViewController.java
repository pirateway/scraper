package com.pirateway.scraper.face;

import com.pirateway.scraper.model.entity.Fork;
import com.pirateway.scraper.util.OptionsUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.primefaces.PrimeFaces;
import org.springframework.stereotype.Controller;

@Controller
public class CalculatorViewController {

    @Nullable private Fork selectedFork;

    public void show(
            @NotNull final Fork fork
    ){
        selectedFork = fork;
        PrimeFaces.current().dialog().openDynamic("calculatorOutcome", OptionsUtil.getWindowOptions(), null);
    }

    @Nullable
    public Fork getSelectedFork() {
        return selectedFork;
    }

    public void setSelectedFork(@Nullable Fork selectedFork) {
        this.selectedFork = selectedFork;
    }
}

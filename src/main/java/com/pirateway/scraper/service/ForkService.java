package com.pirateway.scraper.service;

import com.pirateway.scraper.api.repository.IForkRepository;
import com.pirateway.scraper.api.service.IForkService;
import com.pirateway.scraper.exception.DataValidateException;
import com.pirateway.scraper.util.DataValidator;
import com.pirateway.scraper.model.entity.Fork;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class ForkService implements IForkService {


    @NotNull
    final private IForkRepository forkRepository;

    @Autowired
    public ForkService(
            @NotNull final IForkRepository forkRepository
    ) {
        this.forkRepository = forkRepository;
    }

    @Override
    @Transactional
    public void create(@Nullable Fork fork
    ) throws DataValidateException {
        DataValidator.validateFork(fork);
        forkRepository.save(fork);
    }

    @Override
    @Transactional
    public void createAll(@Nullable List<Fork> forks
    ) throws DataValidateException {
        for (Fork fork:forks){
            create(fork);
        }
    }

    @Override
    @Transactional
    public void clear() throws DataValidateException {
        forkRepository.deleteAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Fork> findAll() throws DataValidateException {
        return forkRepository.findAll();
    }
}

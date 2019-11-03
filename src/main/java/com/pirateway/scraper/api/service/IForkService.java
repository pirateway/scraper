package com.pirateway.scraper.api.service;

import com.pirateway.scraper.exception.DataValidateException;
import com.pirateway.scraper.model.entity.Fork;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public interface IForkService {

    @Transactional
    void create(
            @Nullable final Fork fork
    ) throws DataValidateException;

    @Transactional
    void createAll(
            @Nullable final List<Fork> forks
    ) throws DataValidateException;

    /*@Transactional
    void edit(
            @Nullable final ProjectDTO projectDTO
    ) throws DataValidateException;

    @Transactional(readOnly = true)
    ProjectDTO findOne(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException;

    @Transactional
    void remove(
            @Nullable final String userId
    ) throws DataValidateException;*/

    @Transactional
    void clear(
    ) throws DataValidateException;

    /*@Transactional(readOnly = true)
    ProjectDTO findOne(
            @Nullable final String id
    ) throws DataValidateException;

    @Transactional
    void remove(
            @Nullable final String id
    ) throws DataValidateException;
*/
    @Transactional(readOnly = true)
    Collection<Fork> findAll(
    ) throws DataValidateException;

  /*  @Transactional(readOnly = true)
    Collection<ProjectDTO> findAllByUserId(
            @Nullable final String id
    ) throws DataValidateException;

    @Transactional
    void removeAllByUserId(
            @Nullable final String id
    ) throws DataValidateException;

    @Transactional(readOnly = true)
    Collection<ProjectDTO> sortAllByUserId(
            @Nullable final String id,
            @Nullable final String parameter
    ) throws DataValidateException;

    @Transactional(readOnly = true)
    Collection<ProjectDTO> findAllByPartOfNameOrDescription(
            @Nullable final String name,
            @Nullable final String description,
            @Nullable final String userId
    ) throws DataValidateException;*/
}

package com.fredjo.DevRegistery.infra.repository;

import com.fredjo.DevRegistery.domain.entity.ProgrammingLanguage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgrammingLanguageRepository extends CrudRepository<ProgrammingLanguage, Long>, PagingAndSortingRepository<ProgrammingLanguage, Long> {

    Page<ProgrammingLanguage> findAll(Pageable pageable);

}
package com.fredjo.DevRegistery.infra.repository;

import com.fredjo.DevRegistery.domain.entity.Developer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeveloperRepository extends CrudRepository<Developer, Long>, PagingAndSortingRepository<Developer, Long> {

    List<Developer> findByLastName(String name);

    Page<Developer> findAll(Pageable pageable);

}
package com.fredjo.DevRegistery.infra.repository;

import com.fredjo.DevRegistery.domain.entity.Developer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "developer", path = "developer")
public interface DeveloperRepository extends CrudRepository<Developer, Long> {

    List<Developer> findByLastName(@Param("name") String name);

}
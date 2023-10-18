package com.fredjo.DevRegistery.infra.repository;

import com.fredjo.DevRegistery.domain.entity.ProgrammingLanguage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "programming_language", path = "programming_language")
public interface ProgrammingLanguageRepository extends CrudRepository<ProgrammingLanguage, Long> {
}
package com.fredjo.DevRegistery.application.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Base service class providing common CRUD operations.
 * Reduces code duplication across service implementations.
 *
 * @param <T> Entity type
 * @param <D> DTO type
 * @param <ID> ID type
 * @param <R> Repository type
 */
@RequiredArgsConstructor
public abstract class BaseService<T, D, ID, R extends CrudRepository<T, ID>> {

    protected final R repository;
    protected final ModelMapper modelMapper;
    protected abstract Logger getLogger();
    protected abstract Class<D> getDtoClass();

    /**
     * Find entity by ID and map to DTO.
     *
     * @param id the entity ID
     * @return Optional containing the DTO if found
     */
    public Optional<D> getById(final ID id) {
        getLogger().info("Fetching entity with id: {}", id);
        return repository.findById(id)
                .map(entity -> modelMapper.map(entity, getDtoClass()));
    }

    /**
     * Get all entities and map to DTOs.
     *
     * @return List of DTOs
     */
    public List<D> getAll() {
        getLogger().info("Fetching all entities");
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(entity -> modelMapper.map(entity, getDtoClass()))
                .collect(Collectors.toList());
    }

    /**
     * Delete entity by ID.
     *
     * @param id the entity ID
     */
    public void deleteById(final ID id) {
        getLogger().info("Deleting entity with id: {}", id);
        repository.deleteById(id);
    }

    /**
     * Save entity from DTO.
     *
     * @param dto the DTO
     * @return the saved DTO
     */
    public D save(D dto) {
        getLogger().info("Saving entity from DTO");
        T entity = modelMapper.map(dto, getEntityClass());
        T savedEntity = repository.save(entity);
        return modelMapper.map(savedEntity, getDtoClass());
    }

    protected abstract Class<T> getEntityClass();
}

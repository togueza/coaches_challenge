package com.code.challenge.repository;

import com.code.challenge.domain.Code;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * <p>Repository layer for {@link Code} entity. Extends {@link CrudRepository} to reuse JPA save actions</p>
 */
@Repository
public interface CodeRepository extends CrudRepository<Code, Long> {

    /**
     * <p>Find entity by its id</p>
     * @param id {@link Code} unique identifier
     * @return
     */
    @Query("select d from Code d where d.id = :id")
    Optional<Code> findById(@Param("id") Long id);

    @Override
    <S extends Code> S save(S s);
}

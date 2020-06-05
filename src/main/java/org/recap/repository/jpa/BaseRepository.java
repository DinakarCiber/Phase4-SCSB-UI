package org.recap.repository.jpa;

import org.recap.model.jpa.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<E extends AbstractEntity> extends JpaRepository<E, Integer> {

}
package org.jpa.chap02.repository;

import org.jpa.chap02.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreJpaRepository extends JpaRepository<StoreEntity, StoreEntity.StoreEntityId> {
}

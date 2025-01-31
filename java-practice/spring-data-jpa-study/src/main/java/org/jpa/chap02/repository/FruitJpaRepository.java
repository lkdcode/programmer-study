package org.jpa.chap02.repository;

import org.jpa.chap02.entity.FruitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FruitJpaRepository extends JpaRepository<FruitEntity, FruitEntity.FruitEntityId> {
}

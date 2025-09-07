package demo.example.query.modules.fruit.adapter.out.infrastructure.jpa.repository

import demo.example.query.modules.fruit.adapter.out.infrastructure.jpa.entity.FruitJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface FruitJpaRepository : JpaRepository<FruitJpaEntity, Long> {
}
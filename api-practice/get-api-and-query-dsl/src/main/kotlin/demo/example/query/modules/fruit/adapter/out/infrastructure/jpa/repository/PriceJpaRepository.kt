package demo.example.query.modules.fruit.adapter.out.infrastructure.jpa.repository

import demo.example.query.modules.fruit.adapter.out.infrastructure.jpa.entity.PriceJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PriceJpaRepository : JpaRepository<PriceJpaEntity, Long> {
}
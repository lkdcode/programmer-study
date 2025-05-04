package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.query;

import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity.ProductOptionJpaEntity;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface QueryProductOptionJpaRepository extends Repository<ProductOptionJpaEntity, Long> {
    Optional<ProductOptionJpaEntity> findById(Long id);
}

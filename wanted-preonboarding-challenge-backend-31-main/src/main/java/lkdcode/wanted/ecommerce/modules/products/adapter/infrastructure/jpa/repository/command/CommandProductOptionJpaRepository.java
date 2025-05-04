package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.command;

import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity.ProductOptionJpaEntity;
import org.springframework.data.repository.Repository;

public interface CommandProductOptionJpaRepository extends Repository<ProductOptionJpaEntity, Long> {
    void save(ProductOptionJpaEntity entity);
}
package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.command;

import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity.ProductOptionGroupJpaEntity;
import org.springframework.data.repository.Repository;

public interface CommandProductOptionGroupJpaRepository extends Repository<ProductOptionGroupJpaEntity, Long> {
    ProductOptionGroupJpaEntity save(ProductOptionGroupJpaEntity entity);
}

package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "product_option_groups")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOptionGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String name;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Builder
    public ProductOptionGroup(Long id, Product product, String name, Integer displayOrder) {
        this.id = id;
        this.product = product;
        this.name = name;
        this.displayOrder = displayOrder;
    }
}
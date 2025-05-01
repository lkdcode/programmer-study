package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lkdcode.wanted.ecommerce.framework.common.jpa.entity.EntityValidator;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "product_details")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDetail extends EntityValidator<ProductDetail> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Digits(integer = 8, fraction = 2)
    @Column(precision = 10, scale = 2)
    private BigDecimal weight;

    @Lob
    private String dimensions;

    @Lob
    private String materials;

    @Size(max = 100)
    @Column(name = "country_of_origin", length = 100)
    private String countryOfOrigin;

    @Lob
    @Column(name = "warranty_info")
    private String warrantyInfo;

    @Lob
    @Column(name = "care_instructions")
    private String careInstructions;

    @Lob
    @Column(name = "additional_info")
    private String additionalInfo;

    @Builder
    public ProductDetail(Long id, Product product, BigDecimal weight, String dimensions, String materials, String countryOfOrigin, String warrantyInfo, String careInstructions, String additionalInfo) {
        this.id = id;
        this.product = product;
        this.weight = weight;
        this.dimensions = dimensions;
        this.materials = materials;
        this.countryOfOrigin = countryOfOrigin;
        this.warrantyInfo = warrantyInfo;
        this.careInstructions = careInstructions;
        this.additionalInfo = additionalInfo;
        super.validSelf();
    }
}
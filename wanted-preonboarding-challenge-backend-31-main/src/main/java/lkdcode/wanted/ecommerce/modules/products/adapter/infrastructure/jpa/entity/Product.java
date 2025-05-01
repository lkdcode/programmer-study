package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lkdcode.wanted.ecommerce.framework.common.jpa.entity.EntityValidator;
import lkdcode.wanted.ecommerce.modules.brand.adapter.infrastructure.jpa.entity.Brand;
import lkdcode.wanted.ecommerce.modules.sellers.adapter.infrastructure.jpa.entity.Seller;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "products")
@NoArgsConstructor
public class Product extends EntityValidator<Product> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(unique = true, nullable = false)
    private String slug;

    @Size(max = 500)
    @Column(name = "short_description", length = 500)
    private String shortDescription;

    @Lob
    @Column(name = "full_description")
    private String fullDescription;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @NotNull
    @Size(max = 20)
    @Column(length = 20, nullable = false)
    private String status;

    @Builder
    public Product(Long id, String name, String slug, String shortDescription, String fullDescription, LocalDateTime createdAt, LocalDateTime updatedAt, Seller seller, Brand brand, String status) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.seller = seller;
        this.brand = brand;
        this.status = status;
        super.validSelf();
    }
}
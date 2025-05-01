package lkdcode.wanted.ecommerce.modules.tag.adapter.infrastructure.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lkdcode.wanted.ecommerce.framework.common.jpa.entity.EntityValidator;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "tags")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag extends EntityValidator<Tag> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100, unique = true, nullable = false)
    private String slug;

    @Builder
    public Tag(Long id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        super.validSelf();
    }
}
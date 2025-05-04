package lkdcode.wanted.ecommerce.modules.products.adapter.in.rest.command;

import jakarta.validation.Valid;
import lkdcode.wanted.ecommerce.framework.common.api.response.ApiResponse;
import lkdcode.wanted.ecommerce.modules.products.adapter.in.rest.command.dto.request.create.CreateProductDTO;
import lkdcode.wanted.ecommerce.modules.products.adapter.in.rest.command.mapper.CommandProductApiMapper;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.command.create.CreateProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommandProductApi {
    private final CreateProductService service;
    private final CommandProductApiMapper mapper;

    @PostMapping("/api/products")
    public ResponseEntity<?> getCreateProduct(
        @Valid @RequestBody CreateProductDTO request
    ) {
        final var response = service.create(mapper.convert(request));

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ApiResponse.success(
                    mapper.convert(response),
                    "상품이 성공적으로 등록되었습니다."
                )
            );
    }
}
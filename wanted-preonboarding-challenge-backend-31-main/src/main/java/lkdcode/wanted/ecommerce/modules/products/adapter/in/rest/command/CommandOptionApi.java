package lkdcode.wanted.ecommerce.modules.products.adapter.in.rest.command;

import jakarta.validation.Valid;
import lkdcode.wanted.ecommerce.framework.common.api.response.ApiResponse;
import lkdcode.wanted.ecommerce.modules.products.adapter.in.rest.command.dto.request.option.AddOptionDTO;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.option.command.AddOptionService;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommandOptionApi {
    private final AddOptionService addOptionService;

    @PostMapping("/api/products/{id}/options")
    public ResponseEntity<?> getAddOption(
        @PathVariable(name = "id") Long id,
        @Valid @RequestBody final AddOptionDTO request
    ) {
        final var response = addOptionService.addOption(new ProductId(id), request.getAddOptionModel());

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ApiResponse.success(
                    response,
                    "상품 옵션이 성공적으로 추가되었습니다."
                )
            );
    }

    @PutMapping("/api/products/{id}/options/{optionId}")
    public ResponseEntity<?> getUpdateOption() {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                ApiResponse.success(
                    null,
                    "상품 옵션이 성공적으로 수정되었습니다."
                )
            );
    }

    @DeleteMapping("/api/products/{id}/options/{optionId}")
    public ResponseEntity<?> getDeleteOption() {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                ApiResponse.success(
                    null,
                    "상품 옵션이 성공적으로 삭제되었습니다."
                )
            );
    }

    @PostMapping("/api/products/{id}/images")
    public ResponseEntity<?> getAddImage() {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                ApiResponse.success(
                    null,
                    "상품 이미지가 성공적으로 추가되었습니다."
                )
            );
    }
}
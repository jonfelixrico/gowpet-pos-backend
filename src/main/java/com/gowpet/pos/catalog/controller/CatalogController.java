package com.gowpet.pos.catalog.controller;

import com.gowpet.pos.auth.service.SessionService;
import com.gowpet.pos.catalog.CatalogItem;
import com.gowpet.pos.catalog.CatalogItemService;
import com.gowpet.pos.catalog.ItemType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/catalog")
@SecurityRequirement(name = "bearerAuth")
class CatalogController {
    private final CatalogItemService catalogSvc;
    private final SessionService sessionSvc;


    CatalogController(CatalogItemService catalogSvc, SessionService sessionSvc) {
        this.catalogSvc = catalogSvc;
        this.sessionSvc = sessionSvc;
    }

    @GetMapping
    ResponseEntity<List<CatalogItem>> listItems(@RequestParam(required = false) String searchTerm,
                                                @RequestParam(defaultValue = "0") Integer pageNo,
                                                @RequestParam(defaultValue = "30") Integer itemCount,
                                                @RequestParam(required = false) List<ItemType> types) {
        var page = catalogSvc.list(pageNo, itemCount, types, searchTerm);
        return ResponseEntity.ok()
                .header("X-Total-Count", Integer.toString(page.getTotalPages()))
                .body(page.getContent());
    }

    @PostMapping("/product")
    Map<String, String> createGoods(@RequestBody CatalogItemService.CatalogItemFields item) {
        var sessionUser = sessionSvc.getSessionUser().orElseThrow();
        var created = catalogSvc.create(item, sessionUser);

        return Map.of("id", created.getId());
    }

    @GetMapping("/product/{id}")
    ResponseEntity<CatalogItem> getProduct(@PathVariable String id) {
        return ResponseEntity.of(catalogSvc.findById(id));
    }

    @DeleteMapping("/product/{id}")
    void deleteProduct(@PathVariable String id) {
        var sessionUser = sessionSvc.getSessionUser().orElseThrow();
        catalogSvc.delete(id, sessionUser);
    }

    @PutMapping("/product/{id}")
    CatalogItem updateProduct(@PathVariable String id, @RequestBody CatalogItemService.CatalogItemFields item) {
        var sessionUser = sessionSvc.getSessionUser().orElseThrow();
        return catalogSvc.update(id, item, sessionUser);
    }

    @GetMapping("/code/{code}")
    ResponseEntity<CatalogItem> getProductByCode(@PathVariable @NotBlank String code) {
        return ResponseEntity.of(catalogSvc.findByCode(code));
    }

    @ExceptionHandler(NoSuchElementException.class)
    ResponseEntity<ErrorResponse> handleNoSuchElement() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}

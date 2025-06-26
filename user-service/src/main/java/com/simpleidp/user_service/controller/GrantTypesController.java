package com.simpleidp.user_service.controller;

import com.simpleidp.user_service.dto.GrantTypeDropDTO;
import com.simpleidp.user_service.entity.GrantTypes;
import com.simpleidp.user_service.repository.GrantTypesRepository;
import com.simpleidp.user_service.service.GrantTypesService;
import com.simpleidp.user_service.utils.GrantType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/grant-types")
@RequiredArgsConstructor
public class GrantTypesController {

    private final GrantTypesRepository grantTypesRepository;
    private final GrantTypesService grantTypesService;

    @PostMapping
    public ResponseEntity<GrantTypes> createGrantType(@RequestBody Map<String, String> body) {
        String grantTypeStr = body.get("grantType");
        try {
            GrantType grantType = GrantType.valueOf(grantTypeStr.toUpperCase());
            GrantTypes oauthGrantType = new GrantTypes();
            oauthGrantType.setGrantType(grantType);
            GrantTypes saved = grantTypesRepository.save(oauthGrantType);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/dropdown")
    public List<GrantTypeDropDTO> getGrantTypesDropdown() {
        return grantTypesService.getGrantTypesForDropdown();
    }
}

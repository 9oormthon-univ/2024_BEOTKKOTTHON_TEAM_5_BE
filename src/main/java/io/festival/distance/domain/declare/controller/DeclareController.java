package io.festival.distance.domain.declare.controller;

import io.festival.distance.domain.declare.dto.DeclareDto;
import io.festival.distance.domain.declare.service.DeclareService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/declare")
@CrossOrigin
public class DeclareController {
    private final DeclareService declareService;

    @PostMapping
    public ResponseEntity<Void> sendDeclare(@RequestBody DeclareDto declareDto, Principal principal){
        declareService.writeDeclare(declareDto,principal);
        return ResponseEntity.ok().build();
    }
}

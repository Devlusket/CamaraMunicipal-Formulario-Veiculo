package com.camara.veiculo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.camara.veiculo.dto.request.FormularioRequestDTO;
import com.camara.veiculo.dto.response.FormularioResponseDTO;
import com.camara.veiculo.service.FormularioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Formulários", description = "Gerencimento de formulários de veículos da câmara")
public class FormularioController {

  private final FormularioService formularioService;

  @GetMapping("/admin/formularios")
  @Operation(summary = "Listagem completa de formulários")
  public ResponseEntity<List<FormularioResponseDTO>> listarTodos() {
    return ResponseEntity.ok(formularioService.listarTodos());
  }

  @PostMapping("/formularios")
  @Operation(summary = "Cria um novo formulário")
  public ResponseEntity<FormularioResponseDTO> criar(@RequestBody @Valid FormularioRequestDTO dto) {

    return ResponseEntity.status(HttpStatus.CREATED).body(formularioService.criar(dto));
  }
}

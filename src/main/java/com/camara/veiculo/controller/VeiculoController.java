package com.camara.veiculo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.camara.veiculo.dto.request.VeiculoRequestDTO;
import com.camara.veiculo.dto.response.VeiculoResponseDTO;
import com.camara.veiculo.service.VeiculoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Veículos", description = "Gerencimento de veículos da câmara")
public class VeiculoController {


  private final VeiculoService  veiculoService;

  @GetMapping("/veiculos")
  @Operation(summary = "Lista de veículos ativos / público")
  public ResponseEntity<List<VeiculoResponseDTO>> listarAtivos() {
    return ResponseEntity.ok(veiculoService.listarAtivos());
  }

  @GetMapping("/admin/veiculos")
  @Operation(summary = "Lista todos os veículos incluindo inativos / admin")
  public ResponseEntity<List<VeiculoResponseDTO>> listarTodos() {
    return ResponseEntity.ok(veiculoService.listarTodos());
  }

  @PostMapping("/admin/veiculos")
  @Operation(summary = "Cria novo veículo / admin")
  public ResponseEntity<VeiculoResponseDTO> criar(@RequestBody @Valid VeiculoRequestDTO dto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(veiculoService.criar(dto));
  }

  @PutMapping("/admin/veiculos/{id}")
  @Operation(summary = "Atualiza dados de um veículo / admin")
  public ResponseEntity<VeiculoResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid VeiculoRequestDTO dto) {
    return ResponseEntity.ok(veiculoService.atualizar(id, dto));
  }

  @DeleteMapping("/admin/veiculos/{id}")
  @Operation(summary = "Desativa um veículo / admin")
  public ResponseEntity<Void> desativar(@PathVariable Long id) {
    veiculoService.desativar(id);
    return ResponseEntity.noContent().build();
  }
}

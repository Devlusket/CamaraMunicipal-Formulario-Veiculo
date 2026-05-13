package com.camara.veiculo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.camara.veiculo.dto.request.AgendamentoRequestDTO;
import com.camara.veiculo.dto.response.AgendamentoResponseDTO;
import com.camara.veiculo.dto.response.DisponibilidadeResponseDTO;
import com.camara.veiculo.service.AgendamentoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Agendamentos", description = "Gerencimento de agendamentos para veículos da câmara")
public class AgendamentoController {


  private final AgendamentoService agendamentoService;

  @PostMapping("/agendamentos")
  @Operation(summary = "Cria um novo agendamento / público")
  public ResponseEntity<AgendamentoResponseDTO> criar(@RequestBody @Valid AgendamentoRequestDTO dto) {

    return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoService.criar(dto));
  }



  @DeleteMapping("/agendamentos/{id}")
  @Operation(summary = "Cancela um agendamento / publico")
  public ResponseEntity<Void> cancelar(@PathVariable Long id) {
    agendamentoService.cancelar(id);
    return ResponseEntity.noContent().build();
  }


  @GetMapping("/agendamentos/disponibilidade")
  @Operation(summary = "Verifica disponibilidade do veículo no periodo / publico")
      public ResponseEntity<DisponibilidadeResponseDTO> verificarDisponibilidade(
            @RequestParam Long veiculoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {
        return ResponseEntity.ok(agendamentoService.verificarDisponibilidade(veiculoId, dataInicio, dataFim));
    }


  @GetMapping("/agendamentos/futuros")
  @Operation(summary = "Lista agendamentos futuros por veiculo ativo / publico")
  public ResponseEntity<List<AgendamentoResponseDTO>> listarFuturosAtivos(@RequestParam Long veiculoId) {
    return ResponseEntity.ok(agendamentoService.listarFuturosAtivos(veiculoId));
  }

  
  @GetMapping("/admin/agendamentos")
    @Operation(summary = "Listagem completa de agendamentos — admin")
    public ResponseEntity<List<AgendamentoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(agendamentoService.listarTodos());
    }
}

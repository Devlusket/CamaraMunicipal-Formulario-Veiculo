package com.camara.veiculo.dto.response;

import java.time.LocalDateTime;

import com.camara.veiculo.entity.Agendamento;
import com.camara.veiculo.enums.StatusAgendamento;

public record AgendamentoResponseDTO(

  Long id,
  String requisitante,
  String cargo,
  String veiculoNome,
  String veiculoPlaca,
  LocalDateTime dataInicio,
  LocalDateTime dataFim,
  StatusAgendamento status,
  LocalDateTime createdAt
) {

  public static AgendamentoResponseDTO fromEntity(Agendamento agendamento) {
    return new AgendamentoResponseDTO(
      agendamento.getId(),
      agendamento.getRequisitante(),
      agendamento.getCargo(),
      agendamento.getVeiculo().getNome(),
      agendamento.getVeiculo().getPlaca(),
      agendamento.getDataInicio(),
      agendamento.getDataFim(),
      agendamento.getStatus(),
      agendamento.getCreatedAt());
  }
}

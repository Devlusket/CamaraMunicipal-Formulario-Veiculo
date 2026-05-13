package com.camara.veiculo.dto.response;

import java.time.LocalDateTime;

import com.camara.veiculo.entity.Formulario;

public record FormularioResponseDTO(

  Long id,
  String requisitante,
  String cargo,
  String veiculoNome,
  String veiculoPlaca,
  LocalDateTime dataSaida,
  LocalDateTime dataRetornoPrevista,
  String itinerario,
  String justificativa,
  Integer odometroSaida,
  String observacao,
  LocalDateTime createdAt
) {

  public static FormularioResponseDTO fromEntity(Formulario formulario) {
    return new FormularioResponseDTO(
      formulario.getId(),
      formulario.getRequisitante(),
      formulario.getCargo(),
      formulario.getVeiculo().getNome(),
      formulario.getVeiculo().getPlaca(),
      formulario.getDataSaida(),
      formulario.getDataRetornoPrevista(),
      formulario.getItinerario(),
      formulario.getJustificativa(),
      formulario.getOdometroSaida(),
      formulario.getObservacao(),
      formulario.getCreatedAt());
  }
}

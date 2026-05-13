package com.camara.veiculo.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record FormularioRequestDTO(

  @NotBlank String requisitante,
  @NotBlank String cargo,
  @NotNull Long veiculoId,
  @NotNull LocalDateTime dataSaida,
  @NotNull LocalDateTime dataRetornoPrevista,
  @NotBlank String itinerario,
  @NotBlank String justificativa,
  @NotNull @Positive Integer odometroSaida,
  String observacao
) {

}

package com.camara.veiculo.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AgendamentoRequestDTO(

    @NotBlank String requisitante,
    @NotBlank String cargo,
    @NotNull Long veiculoId,
    @NotNull @Future LocalDateTime dataInicio,
    @NotNull LocalDateTime dataFim
) {

}

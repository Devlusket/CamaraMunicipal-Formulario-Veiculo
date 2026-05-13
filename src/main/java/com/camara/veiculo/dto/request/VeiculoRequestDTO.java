package com.camara.veiculo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VeiculoRequestDTO(

  @NotBlank String nome,
  @NotBlank @Size(min = 7, max = 10) String placa
) {

}

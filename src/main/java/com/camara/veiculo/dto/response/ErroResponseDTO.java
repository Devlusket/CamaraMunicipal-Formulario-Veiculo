package com.camara.veiculo.dto.response;

import java.time.LocalDateTime;

public record ErroResponseDTO(

  int status,
  String message,
  LocalDateTime timestamp
) {

}

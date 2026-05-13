package com.camara.veiculo.dto.response;

public record DisponibilidadeResponseDTO(

  Boolean disponivel,
  String veiculoNome,
  String veiculoPlaca
) {

}

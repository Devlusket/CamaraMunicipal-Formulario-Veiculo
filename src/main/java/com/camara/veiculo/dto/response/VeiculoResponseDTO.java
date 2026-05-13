package com.camara.veiculo.dto.response;

import com.camara.veiculo.entity.Veiculo;

public record VeiculoResponseDTO(

    Long id,
    String nome,
    String placa,
    Boolean ativo
) {

  public static VeiculoResponseDTO fromEntity(Veiculo veiculo) {
    return new VeiculoResponseDTO(
      veiculo.getId(),
      veiculo.getNome(),
      veiculo.getPlaca(),
      veiculo.getAtivo());
  }
}

package com.camara.veiculo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.camara.veiculo.dto.request.VeiculoRequestDTO;
import com.camara.veiculo.dto.response.VeiculoResponseDTO;
import com.camara.veiculo.entity.Veiculo;
import com.camara.veiculo.exception.ResourceNotFoundException;
import com.camara.veiculo.repository.VeiculoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VeiculoService {


  private final VeiculoRepository veiculoRepository;

  public List<VeiculoResponseDTO> listarAtivos() {
    return veiculoRepository.findAllByAtivoTrue()
      .stream()
      .map(VeiculoResponseDTO::fromEntity)
      .toList();
  }

  public List<VeiculoResponseDTO> listarTodos() {
    return veiculoRepository.findAll()
      .stream()
      .map(VeiculoResponseDTO::fromEntity)
      .toList();
  }

  public VeiculoResponseDTO criar(VeiculoRequestDTO dto) {

    Veiculo veiculo = Veiculo.builder()
      .nome(dto.nome())
      .placa(dto.placa())
      .ativo(true)
      .build();

    return VeiculoResponseDTO.fromEntity(veiculoRepository.save(veiculo));
  }

  public VeiculoResponseDTO atualizar(Long id, VeiculoRequestDTO dto) {
    Veiculo veiculo = veiculoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Veiculo não encontrado"));
    veiculo.setNome(dto.nome());
    veiculo.setPlaca(dto.placa());
    return VeiculoResponseDTO.fromEntity(veiculoRepository.save(veiculo));
  }

  public void desativar(Long id) {
    Veiculo veiculo = veiculoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Veiculo não encontrado"));
    veiculo.setAtivo(false);
    veiculoRepository.save(veiculo);
  }
  

}

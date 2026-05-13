package com.camara.veiculo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.camara.veiculo.dto.request.FormularioRequestDTO;
import com.camara.veiculo.dto.response.FormularioResponseDTO;
import com.camara.veiculo.entity.Formulario;
import com.camara.veiculo.entity.Veiculo;
import com.camara.veiculo.exception.ConflictException;
import com.camara.veiculo.exception.ResourceNotFoundException;
import com.camara.veiculo.repository.AgendamentoRepository;
import com.camara.veiculo.repository.FormularioRepository;
import com.camara.veiculo.repository.VeiculoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FormularioService {


  private final FormularioRepository formularioRepository;
  private final VeiculoRepository veiculoRepository;
  private final AgendamentoRepository agendamentoRepository;


  public FormularioResponseDTO criar(FormularioRequestDTO dto) {

    Veiculo veiculo = veiculoRepository.findById(dto.veiculoId()).
      orElseThrow(() -> new ResourceNotFoundException("Veiculo não encontrado"));

    if (!veiculo.getAtivo()) {
      throw new ConflictException("Veiculo inativo e não pode ser usado.");
    }

    if (!dto.dataRetornoPrevista().isAfter(dto.dataSaida())) {
      throw new ConflictException("Data de retorno prevista deve ser posterior a data de saida.");
    }

    List<Formulario> conflitosFormulario = formularioRepository.findConflitos(dto.veiculoId(), dto.dataSaida(), dto.dataRetornoPrevista());
    if (!conflitosFormulario.isEmpty()) {
      throw new ConflictException("Veiculo ocupado no periodo informado.");
    }

    boolean conflitosAgendamento = !agendamentoRepository.findConflitosAtivos(dto.veiculoId(), dto.dataSaida(), dto.dataRetornoPrevista()).isEmpty();
    if (conflitosAgendamento) {
      throw new ConflictException("Veiculo ocupado no periodo informado.");
    }

    Formulario formulario = Formulario.builder()
      .requisitante(dto.requisitante())
      .cargo(dto.cargo())
      .veiculo(veiculo)
      .dataSaida(dto.dataSaida())
      .dataRetornoPrevista(dto.dataRetornoPrevista())
      .itinerario(dto.itinerario())
      .justificativa(dto.justificativa())
      .odometroSaida(dto.odometroSaida())
      .observacao(dto.observacao())
      .build();

    return FormularioResponseDTO.fromEntity(formularioRepository.save(formulario));
  }



  public List<FormularioResponseDTO> listarTodos() {
    return formularioRepository.findAll()
      .stream()
      .map(FormularioResponseDTO::fromEntity)
      .toList();
  }


  public List<FormularioResponseDTO> buscarPorPeriodo(Long veiculoId, int ano, Integer mes) {


    LocalDateTime inicio;
    LocalDateTime fim;

    if (mes != null) {
      inicio = LocalDateTime.of(ano, mes, 1, 0, 0);
      fim = inicio.plusMonths(1);
    } else {
      inicio = LocalDateTime.of(ano, 1, 1, 0, 0);
      fim = LocalDateTime.of(ano, 12, 31, 23, 59, 59);
    }

    return formularioRepository.findByVeiculoIdAndDataSaidaBetweenOrderByDataSaidaAsc(veiculoId, inicio, fim)
      .stream()
      .map(FormularioResponseDTO::fromEntity)
      .toList();
  }
}

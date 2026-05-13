package com.camara.veiculo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.camara.veiculo.dto.request.AgendamentoRequestDTO;
import com.camara.veiculo.dto.response.AgendamentoResponseDTO;
import com.camara.veiculo.dto.response.DisponibilidadeResponseDTO;
import com.camara.veiculo.entity.Agendamento;
import com.camara.veiculo.entity.Veiculo;
import com.camara.veiculo.enums.StatusAgendamento;
import com.camara.veiculo.exception.ConflictException;
import com.camara.veiculo.exception.ResourceNotFoundException;
import com.camara.veiculo.repository.AgendamentoRepository;
import com.camara.veiculo.repository.FormularioRepository;
import com.camara.veiculo.repository.VeiculoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final VeiculoRepository veiculoRepository;
    private final FormularioRepository formularioRepository;

    public AgendamentoResponseDTO criar(AgendamentoRequestDTO dto) {
        // 1. Busca o veículo — lança 404 se não existir
        Veiculo veiculo = veiculoRepository.findById(dto.veiculoId())
            .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado com id: " + dto.veiculoId()));

        // 2. Verifica se o veículo está ativo
        if (!veiculo.getAtivo()) {
            throw new ConflictException("Veículo inativo e não pode ser agendado");
        }

        // 3. Verifica se data fim é depois da data início
        if (!dto.dataFim().isAfter(dto.dataInicio())) {
            throw new IllegalArgumentException("A data fim deve ser posterior à data início");
        }

        // 4. Verifica conflito com agendamentos ativos
        if (!agendamentoRepository.findConflitosAtivos(
                dto.veiculoId(), dto.dataInicio(), dto.dataFim()).isEmpty()) {
            throw new ConflictException("O veículo já possui um agendamento nesse período");
        }

        // 5. Verifica conflito com formulários existentes
        if (!formularioRepository.findConflitos(
                dto.veiculoId(), dto.dataInicio(), dto.dataFim()).isEmpty()) {
            throw new ConflictException("O veículo já possui um registro de uso nesse período");
        }

        // 6. Salva e retorna
        Agendamento agendamento = Agendamento.builder()
            .requisitante(dto.requisitante())
            .cargo(dto.cargo())
            .veiculo(veiculo)
            .dataInicio(dto.dataInicio())
            .dataFim(dto.dataFim())
            .build();

        return AgendamentoResponseDTO.fromEntity(agendamentoRepository.save(agendamento));
    }

    public void cancelar(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado com id: " + id));
        agendamento.setStatus(StatusAgendamento.CANCELADO);
        agendamentoRepository.save(agendamento);
    }

    public DisponibilidadeResponseDTO verificarDisponibilidade(
            Long veiculoId, LocalDateTime dataInicio, LocalDateTime dataFim) {

        Veiculo veiculo = veiculoRepository.findById(veiculoId)
            .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado com id: " + veiculoId));

        boolean conflitosAgendamento = !agendamentoRepository
            .findConflitosAtivos(veiculoId, dataInicio, dataFim).isEmpty();

        boolean conflitosFormulario = !formularioRepository
            .findConflitos(veiculoId, dataInicio, dataFim).isEmpty();

        boolean disponivel = !conflitosAgendamento && !conflitosFormulario;

        return new DisponibilidadeResponseDTO(disponivel, veiculo.getNome(), veiculo.getPlaca());
    }

    public List<AgendamentoResponseDTO> listarFuturosAtivos(Long veiculoId) {
        return agendamentoRepository
            .findByVeiculoIdAndStatusAndDataInicioAfter(
                veiculoId, StatusAgendamento.ATIVO, LocalDateTime.now())
            .stream()
            .map(AgendamentoResponseDTO::fromEntity)
            .toList();
    }

    public List<AgendamentoResponseDTO> listarTodos() {
        return agendamentoRepository.findAll()
            .stream()
            .map(AgendamentoResponseDTO::fromEntity)
            .toList();
    }
}
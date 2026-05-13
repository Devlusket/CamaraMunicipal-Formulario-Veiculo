package com.camara.veiculo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.camara.veiculo.entity.Agendamento;
import com.camara.veiculo.enums.StatusAgendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    List<Agendamento> findByVeiculoIdAndStatusAndDataInicioAfter(
        Long veiculoId, StatusAgendamento status, LocalDateTime dataInicio);

    @Query("SELECT a FROM Agendamento a WHERE a.veiculo.id = :veiculoId AND a.status = com.camara.veiculo.enums.StatusAgendamento.ATIVO AND a.dataInicio < :dataFim AND a.dataFim > :dataInicio")
    List<Agendamento> findConflitosAtivos(
        @Param("veiculoId") Long veiculoId,
        @Param("dataInicio") LocalDateTime dataInicio,
        @Param("dataFim") LocalDateTime dataFim);
}
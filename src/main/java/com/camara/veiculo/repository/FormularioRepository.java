package com.camara.veiculo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.camara.veiculo.entity.Formulario;

public interface FormularioRepository extends JpaRepository<Formulario, Long>{


  List<Formulario> findByVeiculoIdAndDataSaidaBetweenOrderByDataSaidaAsc(Long veiculoId, LocalDateTime inicio, LocalDateTime fim);

  @Query("SELECT f FROM Formulario f WHERE f.veiculo.id = :veiculoId AND f.dataSaida < :dataFim AND f.dataRetornoPrevista > :dataInicio")
  List<Formulario> findConflitos(Long veiculoId, LocalDateTime dataInicio, LocalDateTime dataFim);
}

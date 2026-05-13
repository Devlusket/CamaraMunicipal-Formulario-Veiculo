package com.camara.veiculo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.camara.veiculo.entity.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {


  List<Veiculo> findAllByAtivoTrue(boolean ativo);
}

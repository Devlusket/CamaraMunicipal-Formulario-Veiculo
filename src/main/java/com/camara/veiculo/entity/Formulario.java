package com.camara.veiculo.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "formularios")
public class Formulario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String requisitante;

  @Column(nullable = false)
  private String cargo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "veiculo_id", nullable = false)
  private Veiculo veiculo;

  @Column(name = "data_saida", nullable = false)
  private LocalDateTime dataSaida;

  @Column(name = "data_retorno_prevista", nullable = false)
  private LocalDateTime dataRetornoPrevista;

  @Column(nullable = false)
  private String itinerario;

  @Column(nullable = false)
  private String justificativa;

  @Column(name = "odometro_saida", nullable = false)
  private Integer odometroSaida;

  @Column(nullable = true)
  private String observacao;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;
}

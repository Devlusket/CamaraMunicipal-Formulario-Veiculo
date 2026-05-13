
package com.camara.veiculo.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.camara.veiculo.enums.StatusAgendamento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "agendamentos")
public class Agendamento {

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

  @Column(name = "data_inicio", nullable = false)
  private LocalDateTime dataInicio;

  @Column(name = "data_fim", nullable = false)
  private LocalDateTime dataFim;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private StatusAgendamento status = StatusAgendamento.ATIVO;

  @Column(name = "created_at", updatable = false)
  @CreationTimestamp
  private LocalDateTime createdAt;


}

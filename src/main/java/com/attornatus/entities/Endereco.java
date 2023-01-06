package com.attornatus.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "endereco")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "Logradouro")
    private String Logradouro;

    @Column(name = "CEP")
    private String CEP;

    @Column(name = "numero")
    private Integer numeroDaCasa;

    @Column(name = "cidade")
    private String cidade;

    @ManyToOne
    @JoinColumn(name = "idPessoa")
    private Pessoa pessoa;

    @Column(name = "endereco_principal")
    private Boolean enderecoPrincipal;
}

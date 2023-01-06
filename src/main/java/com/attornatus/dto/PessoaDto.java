package com.attornatus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaDto {

    private Long id;
    private String nome;
    private String dataNascimento;

}

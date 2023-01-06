package com.attornatus.dto;

import com.attornatus.entities.Endereco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaDto {


    private Long id;
    private String nome;
    private String dataNascimento;

}

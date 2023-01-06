package com.attornatus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO {

    private String Logradouro;
    private String CEP;
    private Integer numeroDaCasa;
    private String cidade;
    private Long pessoa;
    private Boolean enderecoPrincipal;

}

package com.attornatus.repositories;

import com.attornatus.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    @Query(nativeQuery = true, value =
        "SELECT * FROM " +
                " Endereco  " +
                " WHERE id_pessoa = :id ")
    List<Endereco> obterEnderecosPorIdDePessoa(
            @Param("id") Long id);

}

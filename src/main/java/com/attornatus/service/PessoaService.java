package com.attornatus.service;

import com.attornatus.dto.PessoaDto;
import com.attornatus.entities.Pessoa;
import com.attornatus.exception.RegraNegocioException;
import com.attornatus.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;


    public Pessoa salvar(Pessoa pessoa) {
        validar(pessoa);
        return repository.save(pessoa);
    }

    public List buscar() {
        List<Pessoa> result = repository.findAll();
        return result;
    }

    public Pessoa buscarPorId(Long id) {
        Pessoa result = repository.findById(id).get();
        return result;
    }

    public Pessoa atualizar(Pessoa pessoa) {
        validar(pessoa);
        return repository.save(pessoa);
    }

    public void deletar(Pessoa pessoa) {
        repository.delete(pessoa);
    }


    public void validar(Pessoa pessoa) {

        if(pessoa.getNome() == null || pessoa.getNome().trim().equals("") ) {
            throw new RegraNegocioException("Informe um nome");
        }

        if(pessoa.getDataNascimento() == null || pessoa.getDataNascimento().trim().equals("") ) {
            throw new RegraNegocioException("Informe uma data");
        }
    }


}

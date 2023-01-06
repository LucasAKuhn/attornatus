package com.attornatus.service;

import com.attornatus.entities.Endereco;
import com.attornatus.exception.RegraNegocioException;
import com.attornatus.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository repository;


    public Endereco salvar(Endereco endereco) {
        validar(endereco);
        return repository.save(endereco);
    }

    public List buscar() {
        List<Endereco> result = repository.findAll();
        return result;
    }

    public Endereco buscarPorId(Long id) {
        Endereco result = repository.findById(id).get();
        return result;
    }

    public Endereco atualizar(Endereco endereco) {
        validar(endereco);
        return repository.save(endereco);
    }

    public void deletar(Endereco endereco) {
        repository.delete(endereco);
    }

    public List<Endereco> obterEnderecosPorIdDePessoa(Long idPessoa){
        return repository.obterEnderecosPorIdDePessoa(idPessoa);
    }

    public void validar(Endereco endereco) {

        if(endereco.getLogradouro() == null || endereco.getLogradouro().trim().equals("") ) {
            throw new RegraNegocioException("Informe um logradouro");
        }

        if(endereco.getCEP() == null || endereco.getCEP().trim().equals("") ) {
            throw new RegraNegocioException("Informe um CEP");
        }

        if(endereco.getNumeroDaCasa() == null) {
            throw new RegraNegocioException("Informe um numero");
        }

        if(endereco.getCidade() == null || endereco.getCidade().trim().equals("") ) {
            throw new RegraNegocioException("Informe uma Cidade");
        }

        if(endereco.getEnderecoPrincipal() == null) {
            throw new RegraNegocioException("Informe se o endereço é o principal");
        }
    }
}

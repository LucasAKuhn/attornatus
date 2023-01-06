package com.attornatus.controllers;

import com.attornatus.dto.EnderecoDTO;
import com.attornatus.entities.Endereco;
import com.attornatus.entities.Pessoa;
import com.attornatus.exception.RegraNegocioException;
import com.attornatus.repositories.EnderecoRepository;
import com.attornatus.repositories.PessoaRepository;
import com.attornatus.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private EnderecoService service;

    //Criar endereço para pessoa
    @PostMapping
    public ResponseEntity salvar(@RequestBody EnderecoDTO dto) {
        try{
            Endereco endereco = converter(dto);
            endereco = service.salvar(endereco);
            return ResponseEntity.ok(endereco);
        } catch(RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/achar-todos-enderecos")
    public ResponseEntity acharTodos() {

        return ResponseEntity.ok(service.buscar());
    }

    //Listar endereços da pessoa
    @GetMapping(value = "/achar-endereco-por-id/{id}")
    public ResponseEntity acharPorId(@PathVariable Long id) {
        Optional<Endereco> enderecoOptional = enderecoRepository.findById(id);
        if (!enderecoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereço não encontrado");
        }

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping(value = "/atualizar/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable(value = "id") Long id,
                                            @RequestBody Endereco endereco) {
        Optional<Endereco> enderecoOptional = enderecoRepository.findById(id);
        if(!enderecoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereço não encontrado");
        }

        try{
            var endereco1 = enderecoOptional.get();

            endereco1.setLogradouro(endereco.getLogradouro());
            endereco1.setCEP(endereco.getCEP());
            endereco1.setNumeroDaCasa(endereco.getNumeroDaCasa());
            endereco1.setCidade(endereco.getCidade());

            return ResponseEntity.status(HttpStatus.OK).body(service.atualizar(endereco1));
        }catch(RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/deletar/{id}")
    public ResponseEntity<Object> deletar(@PathVariable(value = "id") Long id) {
        Optional<Endereco> enderecoOptional = enderecoRepository.findById(id);
        if(!enderecoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereço não encontrado");
        }
        service.deletar(enderecoOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Endereço deletado com sucesso");
    }

    //Listar endereços da pessoa
    @GetMapping(value = "listar-enderecos-por-pessoa")
    public ResponseEntity acharEnderecosPorPessoa(@Param("id") Long idPessoa) {

        return ResponseEntity.ok().body(service.obterEnderecosPorIdDePessoa(idPessoa));
    }



    private Endereco converter(EnderecoDTO dto) {
        Endereco endereco = new Endereco();

        endereco.setLogradouro(dto.getLogradouro());
        endereco.setCEP(dto.getCEP());
        endereco.setNumeroDaCasa(dto.getNumeroDaCasa());
        endereco.setCidade(dto.getCidade());
        endereco.setEnderecoPrincipal(dto.getEnderecoPrincipal());

        Pessoa pessoa = pessoaRepository
                .findById(dto.getPessoa())
                .orElseThrow( () -> new RegraNegocioException("Pessoa não encontrada"));

        endereco.setPessoa(pessoa);

        return endereco;
    }

}

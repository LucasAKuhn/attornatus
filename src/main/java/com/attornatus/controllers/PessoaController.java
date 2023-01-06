package com.attornatus.controllers;

import com.attornatus.dto.PessoaDto;
import com.attornatus.entities.Endereco;
import com.attornatus.entities.Pessoa;
import com.attornatus.exception.RegraNegocioException;
import com.attornatus.repositories.EnderecoRepository;
import com.attornatus.repositories.PessoaRepository;
import com.attornatus.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/pessoa")
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PessoaService pessoaService;

    //Criar uma pessoa
    @PostMapping
    public ResponseEntity save(@RequestBody PessoaDto dto) {
       try{
           Pessoa pessoa = converter(dto);
           pessoa = pessoaService.salvar(pessoa);
           return ResponseEntity.ok(pessoa);
       } catch(RegraNegocioException e) {
           return ResponseEntity.badRequest().body(e.getMessage());
       }
    }

    //Listar pessoas
    @GetMapping(value = "/achar-todos")
    public ResponseEntity findAll() {

        return ResponseEntity.ok(pessoaService.buscar());
    }

    //Consultar uma pessoa
    @GetMapping(value = "/achar-por-id/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);
        if (!pessoaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada");
        }

            return ResponseEntity.ok(pessoaService.buscarPorId(id));
    }

    //Editar uma pessoa
    @PutMapping(value = "/atualizar/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") Long id,
                                         @RequestBody Pessoa pessoa) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);
        if(!pessoaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada");
        }

        try{
            var pessoa1 = pessoaOptional.get();

            pessoa1.setNome(pessoa.getNome());
            pessoa1.setDataNascimento(pessoa.getDataNascimento());

            return ResponseEntity.status(HttpStatus.OK).body(pessoaService.atualizar(pessoa1));
        } catch(RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/deletar/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);
        if(!pessoaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada");
        }
        pessoaService.deletar(pessoaOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Pessoa deletada com sucesso");
    }

    private Pessoa converter(PessoaDto dto) {
        Pessoa pessoa = new Pessoa();

        pessoa.setNome(dto.getNome());
        pessoa.setDataNascimento(dto.getDataNascimento());

        return pessoa;
    }
}

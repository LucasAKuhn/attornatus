package com.attornatus.repository;

import com.attornatus.entities.Pessoa;
import com.attornatus.repositories.PessoaRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class PessoaRepositoryTest {

    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void deveSalvarUmaPessoa() {
        Pessoa pessoa = criarPessoa();

        pessoa = pessoaRepository.save(pessoa);

        Assertions.assertThat(pessoa.getId()).isNotNull();
    }

    @Test
    public void deveDeletarUmaPessoa() {
        Pessoa pessoa = criarEPersistirUmaPessoa();

        pessoa = entityManager.find(Pessoa.class, pessoa.getId());

        pessoaRepository.delete(pessoa);

        Pessoa pessoaInexistente = entityManager.find(Pessoa.class, pessoa.getId());
        Assertions.assertThat(pessoaInexistente).isNull();
    }

    @Test
    public void deveAtualizarUmaPessoa() {
        Pessoa pessoa = criarEPersistirUmaPessoa();

        pessoa.setNome("Pedro");
        pessoa.setDataNascimento("06/01/2023");

        pessoaRepository.save(pessoa);

        Pessoa pessoaAtualizada = entityManager.find(Pessoa.class, pessoa.getId());

        Assertions.assertThat(pessoaAtualizada.getNome()).isEqualTo("Pedro");
        Assertions.assertThat(pessoaAtualizada.getDataNascimento()).isEqualTo("06/01/2023");
    }

    @Test
    public void deveBuscarUmaPessoaPorId() {
        Pessoa pessoa = criarEPersistirUmaPessoa();

        Optional<Pessoa> pessoaEncontrada = pessoaRepository.findById(pessoa.getId());

        Assertions.assertThat(pessoaEncontrada.isPresent()).isTrue();
    }

    private Pessoa criarEPersistirUmaPessoa() {
        Pessoa pessoa = criarPessoa();
        entityManager.persist(pessoa);
        return pessoa;
    }

    public static Pessoa criarPessoa() {
        return Pessoa.builder()
                .nome("Lucas Kuhn")
                .dataNascimento("03/02/1996")
                .build();
    }

}

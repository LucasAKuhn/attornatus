package com.attornatus.repository;

import com.attornatus.entities.Endereco;
import com.attornatus.entities.Pessoa;
import com.attornatus.repositories.EnderecoRepository;
import com.attornatus.repositories.PessoaRepository;
import org.junit.Test;
import org.assertj.core.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class PessoaRepositoryTest {

    @Autowired
    PessoaRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void deveSalvarUmaPessoa() {
        Pessoa pessoa = criarPessoa();

        pessoa = repository.save(pessoa);

        Assertions.assertThat(pessoa.getId()).isNotNull();
    }

    @Test
    public void deveDeletarUmaPessoa() {
        Pessoa pessoa = criarEPersistirUmaPessoa();

        pessoa = entityManager.find(Pessoa.class, pessoa.getId());

        repository.delete(pessoa);

        Pessoa pessoaInexistete = entityManager.find(Pessoa.class, pessoa.getId());
        Assertions.assertThat(pessoaInexistete).isNull();
    }

    @Test
    public void deveAtualizarUmaPessoa() {
        Pessoa pessoa = criarEPersistirUmaPessoa();

        pessoa.setNome("Pedro");
        pessoa.setDataNascimento("06/01/2023");

        repository.save(pessoa);

        Pessoa pessoaAtualizada = entityManager.find(Pessoa.class, pessoa.getId());

        Assertions.assertThat(pessoaAtualizada.getNome()).isEqualTo("Pedro");
        Assertions.assertThat(pessoaAtualizada.getDataNascimento()).isEqualTo("06/01/2023");
    }

    @Test
    public void deveBuscarUmEnderecoPorId() {
        Pessoa pessoa = criarEPersistirUmaPessoa();

        Optional<Pessoa> pessoaEncontrada = repository.findById(pessoa.getId());

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

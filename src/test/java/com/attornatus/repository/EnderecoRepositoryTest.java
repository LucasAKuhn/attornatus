package com.attornatus.repository;


import com.attornatus.entities.Endereco;
import com.attornatus.repositories.EnderecoRepository;
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

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class EnderecoRepositoryTest {

    @Autowired
    EnderecoRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void deveSalvarUmEndereco() {
        Endereco endereco = criarEndereco();

        endereco = repository.save(endereco);

        Assertions.assertThat(endereco.getId()).isNotNull();
    }

    @Test
    public void deveDeletarUmEndereco() {
        Endereco endereco = criarEPersistirUmEndereco();

        endereco = entityManager.find(Endereco.class, endereco.getId());

        repository.delete(endereco);

        Endereco lancamentoInexistete = entityManager.find(Endereco.class, endereco.getId());
        Assertions.assertThat(lancamentoInexistete).isNull();
    }

    @Test
    public void deveAtualizarUmEndereco() {
        Endereco endereco = criarEPersistirUmEndereco();

        endereco.setLogradouro("Av: Carlos Suhre");
        endereco.setCEP("Teste");
        endereco.setNumeroDaCasa(000);
        endereco.setCidade("Arroio do Meio");

        repository.save(endereco);

        Endereco enderecoAtualizado = entityManager.find(Endereco.class, endereco.getId());

        Assertions.assertThat(enderecoAtualizado.getLogradouro()).isEqualTo("Av: Carlos Suhre");
        Assertions.assertThat(enderecoAtualizado.getCEP()).isEqualTo("Teste");
        Assertions.assertThat(enderecoAtualizado.getNumeroDaCasa()).isEqualTo(000);
        Assertions.assertThat(enderecoAtualizado.getCidade()).isEqualTo("Arroio do Meio");
    }

    @Test
    public void deveBuscarUmEnderecoPorId() {
        Endereco endereco = criarEPersistirUmEndereco();

        Optional<Endereco> lancamentoEncontrado = repository.findById(endereco.getId());

        Assertions.assertThat(lancamentoEncontrado.isPresent()).isTrue();
    }

    private Endereco criarEPersistirUmEndereco() {
        Endereco endereco = criarEndereco();
        entityManager.persist(endereco);
        return endereco;
    }
    public static Endereco criarEndereco() {
        return Endereco.builder()
                .Logradouro("R. Miguel Inácio Faraco")
                .CEP("88705-050")
                .numeroDaCasa(355)
                .cidade("Tubarão")
                .pessoa(null)
                .enderecoPrincipal(true)
                .build();
    }
}

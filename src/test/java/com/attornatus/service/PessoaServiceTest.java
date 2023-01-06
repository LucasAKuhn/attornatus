package com.attornatus.service;

import com.attornatus.entities.Pessoa;
import com.attornatus.exception.RegraNegocioException;
import com.attornatus.repositories.PessoaRepository;
import com.attornatus.repository.PessoaRepositoryTest;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class PessoaServiceTest {

    @SpyBean
    PessoaService pessoaService;

    @MockBean
    PessoaRepository pessoaRepository;

    @Test
    public void  deveSalvarUmaPessoa() {

        Pessoa pessoaASalvar = PessoaRepositoryTest.criarPessoa();
        Mockito.doNothing().when(pessoaService).validar(pessoaASalvar);

        Pessoa pessoaSalva = PessoaRepositoryTest.criarPessoa();
        pessoaSalva.setId(1l);
        Mockito.when(pessoaRepository.save(pessoaASalvar)).thenReturn(pessoaSalva);

        Pessoa pessoa = pessoaService.salvar(pessoaASalvar);

        Assertions.assertThat(pessoa.getId()).isEqualTo(pessoaSalva.getId());
    }

    @Test
    public void naoDeveSalvarUmaPessoaQuandoHouverErroDeValidacao() {

        Pessoa pessoaASalvar = PessoaRepositoryTest.criarPessoa();
        Mockito.doThrow( RegraNegocioException.class ).when(pessoaService).validar(pessoaASalvar);

        Assertions.catchThrowableOfType( () -> pessoaService.salvar(pessoaASalvar), RegraNegocioException.class);
        Mockito.verify(pessoaRepository, Mockito.never()).save(pessoaASalvar);
    }

    @Test
    public void deveDeletarUmaPessoa() {

        Pessoa pessoa = PessoaRepositoryTest.criarPessoa();
        pessoa.setId(1l);

        pessoaService.deletar(pessoa);

        Mockito.verify(pessoaRepository).delete(pessoa);
    }

    @Test
    public void deveObterUmaPessoaPorID() {
        Long id = 1l;

        Pessoa pessoa = PessoaRepositoryTest.criarPessoa();
        pessoa.setId(id);

        Mockito.when( pessoaRepository.findById(id)).thenReturn( Optional.of(pessoa));

        Optional<Pessoa> resultado = Optional.ofNullable(pessoaService.buscarPorId(id));

        Assertions.assertThat(resultado.isPresent()).isTrue();
    }

}

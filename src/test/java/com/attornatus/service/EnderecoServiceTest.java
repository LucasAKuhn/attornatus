package com.attornatus.service;

import com.attornatus.entities.Endereco;
import com.attornatus.entities.Pessoa;
import com.attornatus.exception.RegraNegocioException;
import com.attornatus.repositories.EnderecoRepository;
import com.attornatus.repositories.PessoaRepository;
import com.attornatus.repository.EnderecoRepositoryTest;
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
public class EnderecoServiceTest {

    @SpyBean
    EnderecoService service;

    @MockBean
    EnderecoRepository repository;

    @Test
    public void  deveSalvarUmaPessoa() {

        Endereco enderecoASalvar = EnderecoRepositoryTest.criarEndereco();
        Mockito.doNothing().when(service).validar(enderecoASalvar);

        Endereco enderecoSalvar = EnderecoRepositoryTest.criarEndereco();
        enderecoSalvar.setId(1l);
        Mockito.when(repository.save(enderecoSalvar)).thenReturn(enderecoSalvar);

        Endereco endereco = service.salvar(enderecoSalvar);

        Assertions.assertThat(endereco.getId()).isEqualTo(enderecoSalvar.getId());
    }

    @Test
    public void naoDeveSalvarUmEnderecoQuandoHouverErroDeValidacao() {

        Endereco enderecoASalvar = EnderecoRepositoryTest.criarEndereco();
        Mockito.doThrow( RegraNegocioException.class ).when(service).validar(enderecoASalvar);

        Assertions.catchThrowableOfType( () -> service.salvar(enderecoASalvar), RegraNegocioException.class);
        Mockito.verify(repository, Mockito.never()).save(enderecoASalvar);
    }

    @Test
    public void deveDeletarUmEndereco() {

        Endereco endereco = EnderecoRepositoryTest.criarEndereco();
        endereco.setId(1l);

        service.deletar(endereco);

        Mockito.verify(repository).delete(endereco);
    }

    @Test
    public void deveObterUmEnderecoPorID() {
        Long id = 1l;

        Endereco endereco = EnderecoRepositoryTest.criarEndereco();
        endereco.setId(id);

        Mockito.when( repository.findById(id)).thenReturn( Optional.of(endereco));

        Optional<Endereco> resultado = Optional.ofNullable(service.buscarPorId(id));

        Assertions.assertThat(resultado.isPresent()).isTrue();
    }

}

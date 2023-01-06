package com.attornatus.service;

import com.attornatus.entities.Endereco;
import com.attornatus.exception.RegraNegocioException;
import com.attornatus.repositories.EnderecoRepository;
import com.attornatus.repository.EnderecoRepositoryTest;
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
    EnderecoService enderecoService;

    @MockBean
    EnderecoRepository enderecoRepository;

    @Test
    public void deveSalvarUmEndereco() {

        Endereco enderecoASalvar = EnderecoRepositoryTest.criarEndereco();
        Mockito.doNothing().when(enderecoService).validar(enderecoASalvar);

        Endereco enderecoSalvar = EnderecoRepositoryTest.criarEndereco();
        enderecoSalvar.setId(1l);
        Mockito.when(enderecoRepository.save(enderecoSalvar)).thenReturn(enderecoSalvar);

        Endereco endereco = enderecoService.salvar(enderecoSalvar);

        Assertions.assertThat(endereco.getId()).isEqualTo(enderecoSalvar.getId());
    }

    @Test
    public void naoDeveSalvarUmEnderecoQuandoHouverErroDeValidacao() {

        Endereco enderecoASalvar = EnderecoRepositoryTest.criarEndereco();
        Mockito.doThrow( RegraNegocioException.class ).when(enderecoService).validar(enderecoASalvar);

        Assertions.catchThrowableOfType( () -> enderecoService.salvar(enderecoASalvar), RegraNegocioException.class);
        Mockito.verify(enderecoRepository, Mockito.never()).save(enderecoASalvar);
    }

    @Test
    public void deveDeletarUmEndereco() {

        Endereco endereco = EnderecoRepositoryTest.criarEndereco();
        endereco.setId(1l);

        enderecoService.deletar(endereco);

        Mockito.verify(enderecoRepository).delete(endereco);
    }

    @Test
    public void deveObterUmEnderecoPorID() {
        Long id = 1l;

        Endereco endereco = EnderecoRepositoryTest.criarEndereco();
        endereco.setId(id);

        Mockito.when( enderecoRepository.findById(id)).thenReturn( Optional.of(endereco));

        Optional<Endereco> resultado = Optional.ofNullable(enderecoService.buscarPorId(id));

        Assertions.assertThat(resultado.isPresent()).isTrue();
    }

}

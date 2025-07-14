package com.tech.sulwork.service;

import com.tech.sulwork.dto.ColaboradorDto;
import com.tech.sulwork.dto.ItemCafeDto;
import com.tech.sulwork.entity.Colaborador;
import com.tech.sulwork.entity.ItemCafe;
import com.tech.sulwork.repository.ColaboradorRepository;
import com.tech.sulwork.repository.ItemCafeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CafeServiceTest {

    @Mock
    private ColaboradorRepository colaboradorRepo;

    @Mock
    private ItemCafeRepository itemCafeRepo;

    @InjectMocks
    private CafeService cafeService;

    private Colaborador colaborador;
    private ItemCafe itemCafe;
    private ColaboradorDto colaboradorDto;
    private ItemCafeDto itemCafeDto;

    @BeforeEach
    void setUp() {
        colaborador = new Colaborador();
        colaborador.setIdColaborador(1L);
        colaborador.setCpf("12345678901");
        colaborador.setNome("João Silva");

        itemCafe = new ItemCafe();
        itemCafe.setIdItem(1L);
        itemCafe.setDescricao("Café Premium");
        itemCafe.setDataCafe(LocalDate.now().plusDays(1));
        itemCafe.setEntregue(false);
        itemCafe.setColaborador(colaborador);

        colaboradorDto = new ColaboradorDto();
        colaboradorDto.setCpf("12345678901");
        colaboradorDto.setNome("João Silva");

        itemCafeDto = new ItemCafeDto(
                null,
                "Café Premium",
                LocalDate.now().plusDays(1),
                false,
                "12345678901"
        );
    }

    @Test
    void listarItensPorData_DeveRetornarListaVazia_QuandoNaoHouverItens() {
        String dataStr = "01/01/2024";
        LocalDate data = LocalDate.of(2024, 1, 1);
        when(itemCafeRepo.findAllByDataCafe(data)).thenReturn(Arrays.asList());

        List<ItemCafeDto> resultado = cafeService.listarItensPorData(dataStr);

        assertTrue(resultado.isEmpty());
        verify(itemCafeRepo).findAllByDataCafe(data);
    }

    @Test
    void listarItensPorData_DeveRetornarItens_QuandoHouverItens() {
        String dataStr = "01/01/2024";
        LocalDate data = LocalDate.of(2024, 1, 1);
        when(itemCafeRepo.findAllByDataCafe(data)).thenReturn(Arrays.asList(itemCafe));

        List<ItemCafeDto> resultado = cafeService.listarItensPorData(dataStr);

        assertEquals(1, resultado.size());
        ItemCafeDto dto = resultado.get(0);
        assertEquals(itemCafe.getIdItem(), dto.getIdItem());
        assertEquals(itemCafe.getDescricao(), dto.getDescricao());
        assertEquals(itemCafe.getDataCafe(), dto.getDataCafe());
        assertEquals(itemCafe.getEntregue(), dto.getEntregue());
        assertEquals(colaborador.getCpf(), dto.getCpfColaborador());
    }

    @Test
    void listarItensPorData_DeveRetornarCpfNull_QuandoColaboradorForNull() {
        String dataStr = "01/01/2024";
        LocalDate data = LocalDate.of(2024, 1, 1);
        itemCafe.setColaborador(null);
        when(itemCafeRepo.findAllByDataCafe(data)).thenReturn(Arrays.asList(itemCafe));

        List<ItemCafeDto> resultado = cafeService.listarItensPorData(dataStr);

        assertEquals(1, resultado.size());
        assertNull(resultado.get(0).getCpfColaborador());
    }

    @Test
    void cadastrarColaborador_DeveRegistrarComSucesso_QuandoDadosValidos() {
        when(colaboradorRepo.findByCpf(colaboradorDto.getCpf())).thenReturn(null);
        when(colaboradorRepo.findByNome(colaboradorDto.getNome())).thenReturn(null);

        assertDoesNotThrow(() -> cafeService.cadastrarColaborador(colaboradorDto));

        verify(colaboradorRepo).save(any(Colaborador.class));
    }

    @Test
    void cadastrarColaborador_DeveLancarExcecao_QuandoCpfForNull() {
        colaboradorDto.setCpf(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> cafeService.cadastrarColaborador(colaboradorDto)
        );

        assertEquals("CPF deve ter 11 dígitos.", exception.getMessage());
        verify(colaboradorRepo, never()).save(any());
    }

    @Test
    void cadastrarColaborador_DeveLancarExcecao_QuandoCpfTiverTamanhoInvalido() {
        colaboradorDto.setCpf("123456789");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> cafeService.cadastrarColaborador(colaboradorDto)
        );

        assertEquals("CPF deve ter 11 dígitos.", exception.getMessage());
        verify(colaboradorRepo, never()).save(any());
    }

    @Test
    void cadastrarColaborador_DeveLancarExcecao_QuandoCpfJaExistir() {
        when(colaboradorRepo.findByCpf(colaboradorDto.getCpf())).thenReturn(colaborador);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> cafeService.cadastrarColaborador(colaboradorDto)
        );

        assertEquals("CPF já cadastrado.", exception.getMessage());
        verify(colaboradorRepo, never()).save(any());
    }

    @Test
    void cadastrarColaborador_DeveLancarExcecao_QuandoNomeJaExistir() {
        when(colaboradorRepo.findByCpf(colaboradorDto.getCpf())).thenReturn(null);
        when(colaboradorRepo.findByNome(colaboradorDto.getNome())).thenReturn(colaborador);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> cafeService.cadastrarColaborador(colaboradorDto)
        );

        assertEquals("Nome já cadastrado.", exception.getMessage());
        verify(colaboradorRepo, never()).save(any());
    }


    @Test
    void adicionarItem_DeveRegistrarComSucesso_QuandoDadosValidos() {
        when(colaboradorRepo.findByCpf(itemCafeDto.getCpfColaborador())).thenReturn(colaborador);
        when(itemCafeRepo.existsItemNaData(itemCafeDto.getDescricao(), itemCafeDto.getDataCafe())).thenReturn(0);

        assertDoesNotThrow(() -> cafeService.adicionarItem(itemCafeDto));

        verify(itemCafeRepo).save(any(ItemCafe.class));
    }

    @Test
    void adicionarItem_DeveLancarExcecao_QuandoColaboradorNaoExistir() {
        // Given
        when(colaboradorRepo.findByCpf(itemCafeDto.getCpfColaborador())).thenReturn(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> cafeService.adicionarItem(itemCafeDto)
        );

        assertEquals("Colaborador não encontrado.", exception.getMessage());
        verify(itemCafeRepo, never()).save(any());
    }

    @Test
    void adicionarItem_DeveLancarExcecao_QuandoDataForPassada() {
        itemCafeDto.setDataCafe(LocalDate.now().minusDays(1));
        when(colaboradorRepo.findByCpf(itemCafeDto.getCpfColaborador())).thenReturn(colaborador);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> cafeService.adicionarItem(itemCafeDto)
        );

        assertEquals("Data deve ser futura.", exception.getMessage());
        verify(itemCafeRepo, never()).save(any());
    }

    @Test
    void adicionarItem_DeveLancarExcecao_QuandoItemJaExistirNaData() {
        when(colaboradorRepo.findByCpf(itemCafeDto.getCpfColaborador())).thenReturn(colaborador);
        when(itemCafeRepo.existsItemNaData(itemCafeDto.getDescricao(), itemCafeDto.getDataCafe())).thenReturn(1);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> cafeService.adicionarItem(itemCafeDto)
        );

        assertEquals("Esse item já foi escolhido para essa data.", exception.getMessage());
        verify(itemCafeRepo, never()).save(any());
    }

    @Test
    void adicionarItem_DeveUsarFalsePorPadrao_QuandoEntregueForNull() {
        itemCafeDto.setEntregue(null);
        when(colaboradorRepo.findByCpf(itemCafeDto.getCpfColaborador())).thenReturn(colaborador);
        when(itemCafeRepo.existsItemNaData(itemCafeDto.getDescricao(), itemCafeDto.getDataCafe())).thenReturn(0);

        cafeService.adicionarItem(itemCafeDto);

        verify(itemCafeRepo).save(argThat(item ->
                item.getEntregue() == false
        ));
    }

    @Test
    void atualizarEntrega_DeveAtualizarComSucesso_QuandoItemExistir() {
        Long idItem = 1L;
        Boolean novoStatus = true;
        when(itemCafeRepo.findById(idItem)).thenReturn(Optional.of(itemCafe));

        assertDoesNotThrow(() -> cafeService.atualizarEntrega(idItem, novoStatus));

        assertEquals(novoStatus, itemCafe.getEntregue());
        verify(itemCafeRepo).save(itemCafe);
    }

    @Test
    void atualizarEntrega_DeveLancarExcecao_QuandoItemNaoExistir() {
        Long idItem = 999L;
        Boolean novoStatus = true;
        when(itemCafeRepo.findById(idItem)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> cafeService.atualizarEntrega(idItem, novoStatus)
        );

        assertEquals("Item não encontrado.", exception.getMessage());
        verify(itemCafeRepo, never()).save(any());
    }

    @Test
    void atualizarEntrega_DevePermitirValorNull_ParaParametroEntregue() {
        Long idItem = 1L;
        Boolean novoStatus = null;
        when(itemCafeRepo.findById(idItem)).thenReturn(Optional.of(itemCafe));

        assertDoesNotThrow(() -> cafeService.atualizarEntrega(idItem, novoStatus));

        assertNull(itemCafe.getEntregue());
        verify(itemCafeRepo).save(itemCafe);
    }

    @Test
    void atualizarEntrega_DeveAtualizarDeTrueParaFalse() {
        Long idItem = 1L;
        Boolean novoStatus = false;
        itemCafe.setEntregue(true);
        when(itemCafeRepo.findById(idItem)).thenReturn(Optional.of(itemCafe));

        cafeService.atualizarEntrega(idItem, novoStatus);

        assertFalse(itemCafe.getEntregue());
        verify(itemCafeRepo).save(itemCafe);
    }
}
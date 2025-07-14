package com.tech.sulwork.service;

import com.tech.sulwork.dto.ColaboradorDto;
import com.tech.sulwork.dto.ItemCafeDto;
import com.tech.sulwork.entity.Colaborador;
import com.tech.sulwork.entity.ItemCafe;
import com.tech.sulwork.repository.ColaboradorRepository;
import com.tech.sulwork.repository.ItemCafeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CafeService {

    private final ColaboradorRepository colaboradorRepo;
    private final ItemCafeRepository itemCafeRepo;

    public CafeService(ColaboradorRepository colaboradorRepo, ItemCafeRepository itemCafeRepo) {
        this.colaboradorRepo = colaboradorRepo;
        this.itemCafeRepo = itemCafeRepo;
    }

    public List<ItemCafeDto> listarItensPorData(String dataStr) {
        LocalDate data = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        List<ItemCafe> itens = itemCafeRepo.findAllByDataCafe(data);

        return itens.stream()
                .map(item -> new ItemCafeDto(
                        item.getIdItem(),
                        item.getDescricao(),
                        item.getDataCafe(),
                        item.getEntregue(),
                        item.getColaborador() != null ? item.getColaborador().getCpf() : null
                ))
                .collect(Collectors.toList());
    }


    @Transactional
    public void cadastrarColaborador(ColaboradorDto dto) {
        if (dto.getCpf() == null || dto.getCpf().length() != 11) {
            throw new IllegalArgumentException("CPF deve ter 11 dígitos.");
        }

        if (colaboradorRepo.findByCpf(dto.getCpf()) != null) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }

        if (colaboradorRepo.findByNome(dto.getNome()) != null) {
            throw new IllegalArgumentException("Nome já cadastrado.");
        }

        Colaborador c = new Colaborador();
        c.setCpf(dto.getCpf());
        c.setNome(dto.getNome());
        colaboradorRepo.save(c);
    }

    @Transactional
    public void adicionarItem(ItemCafeDto dto) {
        Colaborador colaborador = colaboradorRepo.findByCpf(dto.cpfColaborador);
        if (colaborador == null) {
            throw new IllegalArgumentException("Colaborador não encontrado.");
        }

        if (dto.dataCafe.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data deve ser futura.");
        }

        int jaExiste = itemCafeRepo.existsItemNaData(dto.descricao, dto.dataCafe);

        if (jaExiste > 0) {
            throw new IllegalArgumentException("Esse item já foi escolhido para essa data.");
        }

        ItemCafe item = new ItemCafe();
        item.setDescricao(dto.descricao);
        item.setDataCafe(dto.dataCafe);
        item.setEntregue(dto.entregue != null ? dto.entregue : false);
        item.setColaborador(colaborador);

        itemCafeRepo.save(item);
    }

    public void atualizarEntrega(Long idItem, Boolean entregue) {
        Optional<ItemCafe> opt = itemCafeRepo.findById(idItem);
        if (opt.isEmpty()) {
            throw new IllegalArgumentException("Item não encontrado.");
        }

        ItemCafe item = opt.get();
        item.setEntregue(entregue);
        itemCafeRepo.save(item);
    }
}

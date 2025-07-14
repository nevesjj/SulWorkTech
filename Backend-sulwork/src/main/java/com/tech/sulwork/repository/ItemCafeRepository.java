package com.tech.sulwork.repository;

import com.tech.sulwork.entity.ItemCafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ItemCafeRepository extends JpaRepository<ItemCafe, Long> {

    @Query(value = "SELECT COUNT(*) FROM itens_cafe WHERE descricao = :descricao AND data_cafe = :data", nativeQuery = true)
    int existsItemNaData(@Param("descricao") String descricao, @Param("data") LocalDate data);

    @Query(value = "SELECT * FROM itens_cafe WHERE data_cafe = :data", nativeQuery = true)
    List<ItemCafe> findAllByDataCafe(@Param("data") LocalDate data);
}

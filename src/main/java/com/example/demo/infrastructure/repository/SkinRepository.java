package com.example.demo.infrastructure.repository;

import com.example.demo.domain.entities.SkinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SkinRepository extends JpaRepository<SkinEntity, Long> {
    List<SkinEntity> findByPriceGreaterThanEqual(double minPrice);
    SkinEntity findByName(String name);
    @Query("SELECT s FROM SkinEntity s WHERE s.price >= :currentSkinPrice * :multiplier")
    List<SkinEntity> finAvailableSkins(@Param("currentSkinPrice") double currentSkinPrice,
                                       @Param("multiplier") double multiplier);

    @Query("SELECT s FROM SkinEntity s WHERE s.price >= :currentSkinPrice * 1.75")
    List<SkinEntity> finAvailableSkins(@Param("currentSkinPrice") double currentSkinPrice);

}

package com.example.demo.infrastructure.repository;

import com.example.demo.domain.models.Skin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SkinRepository extends JpaRepository<Skin, Long> {
    List<Skin> findByPriceGreaterThanEqual(double minPrice);
    @Query("SELECT s FROM Skin s WHERE s.price >= :currentSkinPrice * :multiplier")
    List<Skin> finAvailableSkins(@Param("currentSkinPrice") double currentSkinPrice,
                                 @Param("multiplier") double multiplier);

    @Query("SELECT s FROM Skin s WHERE s.price >= :currentSkinPrice * 1.75")
    List<Skin> finAvailableSkins(@Param("currentSkinPrice") double currentSkinPrice);

}

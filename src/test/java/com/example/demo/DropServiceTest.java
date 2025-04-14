package com.example.demo;

import com.example.demo.application.dto.CaseBaseInfo;
import com.example.demo.application.dto.SkinBaseDto;
import com.example.demo.application.service.CaseDropService;
import com.example.demo.application.service.UpgradeChanceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DropServiceTest {

    @Autowired
    private CaseDropService caseDropService;

    @Autowired
    private UpgradeChanceService upgradeChanceService;

    @Test
    public void testDropRandomSkin() {
        var skinA = SkinBaseDto.builder()
                .id(1L)
                .price(100.0)
                .build();

        var skinB = SkinBaseDto.builder()
                .id(2L)
                .price(25.0)
                .build();

        var skinC = SkinBaseDto.builder()
                .id(3L)
                .price(120.0)
                .build();

        var caseBaseInfo = CaseBaseInfo.builder()
                .id(1L)
                .price(100.0)
                .skins(List.of(skinA, skinB, skinC))
                .build();

        var trials = 1000;
        var skinACount = 0;
        var skinBCount = 0;
        var skinCCount = 0;

        for (var i = 0; i < trials; i++) {
            var droppedSkin = caseDropService.getDroppedSkin(caseBaseInfo, 0.75);
            if (droppedSkin.getId() == 1L) {
                skinACount++;
            } else if (droppedSkin.getId() == 2L) {
                skinBCount++;
            }
            else {
                skinCCount++;
            }
        }

        System.out.println("Skin A выпал " + skinACount + " раз из " + trials);
        System.out.println("Skin B выпал " + skinBCount + " раз из " + trials);
        System.out.println("Skin C выпал " + skinCCount + " раз из " + trials);
    }

    @Test
    public void testUpgradeRandomSkin() {
        var skinA = SkinBaseDto.builder()
                .id(1L)
                .price(120)
                .build();

        var skinB = SkinBaseDto.builder()
                .id(2L)
                .price(38)
                .build();

        var chance = upgradeChanceService.performUpgrade(skinB, skinA, 0.5);
        System.out.println("Skin B " + chance.isSuccess() + " в Skin A с шансом " + chance.getRolledChance() +
                "\n БЫЛ ШАНС " + chance.getUpgradeChance());
    }
}

package com.nau.shop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Category {
    PROTEIN("Протеїн"),
    CREATINE("Креатин"),
    GAINER("Гейнер"),
    FATBURNER("Жироспалювач"),
    AMINOCYCLOTES("Амінокислоти"),
    PRETRAIN("Предтренувальний комплекс"),
    VITAMINS("Вітаміни"),
    ENERGETIC("Енергетик"),
    TESTOBUSTER("Тестостерон бустер");

    private final String value;
}

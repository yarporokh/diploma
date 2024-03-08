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
    PRETRAIN("Предтренувальний комплекс");

    private final String value;
}

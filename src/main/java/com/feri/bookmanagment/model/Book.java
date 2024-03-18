package com.feri.bookmanagment.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// Lombok anotacija za avtomatsko generiranje getterjev, setterjev, konstruktorjev in metode toString
@Data
// Lombok anotacija za avtomatsko generiranje konstruktorja brez argumentov
@NoArgsConstructor
// Lombok anotacija za avtomatsko generiranje konstruktorja z vsemi argumenti
@AllArgsConstructor
// Spring Data anotacija, ki določa, da je ta razred MongoDB dokument
@Document
public class Book {
    // Polje označeno kot ID dokumenta v MongoDB
    @Id
    private String id;

    // Polja, ki predstavljajo lastnosti knjige
    private String title;
    private String author;
    private String isbn;
    private String genre;

    // Lombok avtomatsko generira getterje, setterje in metode toString, equals in hashCode
}

package ru.education.lab5;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private String name;
    private String author;
    private int publishingYear;
    private String isbn;
    private String publisher;

}

package ru.education.lab5;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
public class Main {
    public static void main(String[] args) {
        String filePath = "books.json";
        Gson gson = new Gson();
        List<Visitor> visitors;

        // Читаем файл
        try (FileReader reader = new FileReader(filePath)) {
            Type listType = new TypeToken<List<Visitor>>() {}.getType();
            visitors = gson.fromJson(reader, listType);
        } catch (IOException e) {
            System.out.println("Не удалось прочитать файл: " + e.getMessage());
            return;
        }

        if (visitors == null || visitors.isEmpty()) {
            System.out.println("Файл пуст");
            return;
        }

        System.out.println("Данные успешно загружены.\n");

        // Задание 1
        System.out.println("1. Список посетителей:");
        visitors.forEach(v -> System.out.println("   " + v.getName() + " " + v.getSurname() + ", телефон: " + v.getPhone()));
        System.out.println("   Всего посетителей: " + visitors.size() + "\n");

        // Задание 2
        System.out.println("2. Все книги без повторений:");
        List<Book> uniqueBooks = visitors.stream()
                .flatMap(v -> v.getFavoriteBooks().stream())
                .distinct()
                .collect(Collectors.toList());
        uniqueBooks.forEach(b -> System.out.println("   " + b.getName() + " — " + b.getAuthor()));
        System.out.println("   Всего уникальных книг: " + uniqueBooks.size() + "\n");

        // Задание 3
        System.out.println("3. Книги по году издания:");
        visitors.stream()
                .flatMap(v -> v.getFavoriteBooks().stream())
                .distinct()
                .sorted(Comparator.comparingInt(Book::getPublishingYear))
                .forEach(b -> System.out.println("   " + b.getPublishingYear() + " — " + b.getName()));
        System.out.println();

        // Задание 4
        System.out.println("4. Проверка на наличие книг Jane Austen:");
        boolean hasAusten = visitors.stream()
                .flatMap(v -> v.getFavoriteBooks().stream())
                .anyMatch(b -> "Jane Austen".equalsIgnoreCase(b.getAuthor()));
        if (hasAusten) {
            System.out.println("   Да, книги этого автора есть в списках\n");
        } else {
            System.out.println("   Нет, книг этого автора не найдено\n");
        }

        // Задание 5
        System.out.println("5. Максимальное число книг у одного посетителя:");
        int maxBooks = visitors.stream()
                .mapToInt(v -> v.getFavoriteBooks().size())
                .max()
                .orElse(0);
        System.out.println("   Максимум книг: " + maxBooks + "\n");

        // Задание 6
        System.out.println("6. Рассылка SMS подписчикам:");

        double averageBooks = visitors.stream()
                .mapToInt(v -> v.getFavoriteBooks().size())
                .average()
                .orElse(0.0);

        System.out.printf("   Среднее количество книг у всех: %.2f%n", averageBooks);

        List<SmsMassage> messages = visitors.stream()
                .filter(Visitor::isSubscribed)
                .map(v -> {
                    int bookCount = v.getFavoriteBooks().size();
                    String text;
                    if (bookCount > averageBooks) {
                        text = "you are a bookworm";
                    } else if (bookCount < averageBooks) {
                        text = "read more";
                    } else {
                        text = "fine";
                    }
                    return new SmsMassage(v.getPhone(), text);
                })
                .collect(Collectors.toList());

        messages.forEach(m -> System.out.println("   Кому: " + m.getPhoneNumber() + " | Текст: " + m.getMessage()));
    }
}

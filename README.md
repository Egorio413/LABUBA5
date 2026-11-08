# Лабораторная работа №5 — Stream API, Gson, Lombok

**Выполнил:** Абрамов Егор
## Описание
Программа читает данные из файла `books.json`, парсит их с помощью библиотеки **Gson**, и выполняет 6 заданий с использованием **Stream API**. Для генерации геттеров, сеттеров, `equals` и `hashCode` используется **Lombok**.

## Как запустить
1. Убедитесь, что у вас установлен JDK 17+ и Maven.
2. В терминале : `mvn clean compile exec:java -Dexec.mainClass="ru.university.lab5.Main"` (или просто запустить класс `Main` в IntelliJ IDEA).
....
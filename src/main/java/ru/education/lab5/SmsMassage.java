package ru.education.lab5;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsMassage {
    private String phoneNumber;
    private String message;
}

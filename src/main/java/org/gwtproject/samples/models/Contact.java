package org.gwtproject.samples.models;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Contact {

    String firstName;

    String lastName;

    Date birthDate;

    String address;

    public static Contact createRandomContact() {
        Faker faker = new Faker();
        return new Contact(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.date().birthday(1, 5),
                faker.address().streetAddress()
        );
    }

    public int getAge(Date birthDate) {
        LocalDate birthDay = LocalDate.parse(birthDate.toString(),
                DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss zzz yyyy", Locale.US));
        return Period.between(birthDay, LocalDate.now()).getYears();
    }
}

package com.raymundo.bankapp.utils;

import com.raymundo.bankapp.dto.ClientDto;
import com.raymundo.bankapp.dto.CreditDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {

    private static final String[] names = new String[]{
            "Ivan", "Anton", "Vasily", "Igor",
            "Andrey", "Egor", "Stanislav", "Sergey",
            "Vladimir", "Vadim"
    };
    private static final String[] surnames = new String[]{
            "Ivanov", "Petrov", "Sidorov", "Belkin",
            "Averin", "Bogrov", "Kozlov", "Mirnov",
            "Pelevin", "Sazonov"
    };
    private static final String[] patronymics = new String[]{
            "Alexandrovich", "Petrovich", "Mihailovich", "Vladimirovich",
            "Ivanovich", "Borisovich", "Nikolaevich", "Artemovich",
            "Andreevich", "Denisovich"
    };
    private static final String[] phoneNumbers = new String[]{
            "+79805623451", "+79954619452", "+79201152834", "+79046621543",
            "+79809054483", "+79047245518", "+79957345123", "+79202400967",
            "+79206503986", "+79951305298"
    };
    private static final String[] emails = new String[]{
            "flexx84@gmail.com", "idawletgareewa@mail.ru", "cobwikim@yandex.ru",
            "excellent47@gmail.com", "gshaw7@mail.ru", "sergiojimenezt@yandex.ru",
            "cerescats@gmail.com", "darling@mail.ru",
            "punkprincess201@gmail.com", "buffyandstone@yandex.ru"
    };
    private static final String[] passportNumbers = new String[]{
            "221687", "163427", "628132", "943780",
            "409257", "761409", "256612", "843021",
            "683771", "804307"
    };

    private static final double[] creditLimits = new double[]{
            100000, 50000, 250000.16, 75000.5,
            310000.7, 95000, 150000.4, 185000.2,
            200000, 270000.8
    };

    private static final double[] interestRates = new double[]{
            9, 10.5, 8.8, 14,
            12.4, 19.5, 11, 13.5,
            15.6, 17, 16
    };


    public static ClientDto getClient() {
        Random random = new Random();
        String name = names[random.ints(0, 10).findFirst().getAsInt()];
        String surname = surnames[random.ints(0, 10).findFirst().getAsInt()];
        String patronymic = patronymics[random.ints(0, 10).findFirst().getAsInt()];
        String phoneNumber = phoneNumbers[random.ints(0, 10).findFirst().getAsInt()];
        String email = emails[random.ints(0, 10).findFirst().getAsInt()];
        String passportNumber = passportNumbers[random.ints(0, 10).findFirst().getAsInt()];
        return new ClientDto(name, surname, patronymic, phoneNumber, email, passportNumber);
    }

    public static CreditDto getCredit() {
        Random random = new Random();
        double creditLimit = creditLimits[random.ints(0, 10).findFirst().getAsInt()];
        double interestRate = interestRates[random.ints(0, 10).findFirst().getAsInt()];
        return new CreditDto(creditLimit, interestRate);
    }

    public static List<ClientDto> getClients(int amount) {
        List<ClientDto> result = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            String name = names[random.ints(0, 10).findFirst().getAsInt()];
            String surname = surnames[random.ints(0, 10).findFirst().getAsInt()];
            String patronymic = patronymics[random.ints(0, 10).findFirst().getAsInt()];
            String phoneNumber = phoneNumbers[random.ints(0, 10).findFirst().getAsInt()];
            String email = emails[random.ints(0, 10).findFirst().getAsInt()];
            String passportNumber = passportNumbers[random.ints(0, 10).findFirst().getAsInt()];
            result.add(new ClientDto(name, surname, patronymic, phoneNumber, email, passportNumber));
        }
        return result;
    }

    public static List<CreditDto> getCredits(int amount) {
        List<CreditDto> result = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            double creditLimit = creditLimits[random.ints(0, 10).findFirst().getAsInt()];
            double interestRate = interestRates[random.ints(0, 10).findFirst().getAsInt()];
            result.add(new CreditDto(creditLimit, interestRate));
        }
        return result;
    }

}

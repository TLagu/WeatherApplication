package com.sda.weather;

import java.util.Scanner;

public class UserInterface {

    public void run() {
        System.out.println("Aplikacja jest uruchomiona\n");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Witaj w aplikacji pogodowej, co chcesz zrobić?");
            System.out.println("0. Zamknij aplikację");

            int option = scanner.nextInt();

            switch (option) {
                case 0:
                    return;
            }
        }
    }
}

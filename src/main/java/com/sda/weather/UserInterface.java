package com.sda.weather;

import com.sda.weather.location.LocationController;

import java.util.Scanner;

public class UserInterface {

    private final LocationController locationController;

    public UserInterface(LocationController localityController) {
        this.locationController = localityController;
    }

    public void run() {
        System.out.println("Aplikacja jest uruchomiona\n");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Witaj w aplikacji pogodowej, co chcesz zrobić?");
            System.out.println("1. Dodać nową lokalizację do bazy");
            System.out.println("2. Wyświetl wszystkie lokalizacje z bazy");
            System.out.println("3. Pobierz dane pogodowe");
            System.out.println("0. Zamknij aplikację");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    createLocation();
                    break;
                case 2:
                    readLocations();
                case 0:
                    return;
            }
        }
    }

    private void createLocation() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj miejscowość:");
        String locality = scanner.nextLine();
        System.out.println("Podaj region (może być pusty):");
        String region = scanner.nextLine();
        System.out.println("Podaj kraj:");
        String country = scanner.nextLine();
        System.out.println("Podaj długość geograficzną (-180 -> W, 180 -> E):");
        String longitude = scanner.nextLine();
        System.out.println("Podaj szerokość geograficzną (-90 -> S, 90 -> N):");
        String latitude = scanner.nextLine();
        // POST: /location
        String request = String.format("{\"locality\":\"%s\",\"region\":\"%s\",\"country\":\"%s\",\"longitude\":\"%s\",\"latitude\":\"%s\"}",
                locality, region, country, longitude, latitude);
        System.out.println("Wysylany json: " + request);
        String response = locationController.createLocation(request);
        System.out.println("Odpowiedz z serwera: " + response);
    }

    private void readLocations() {
        String response = locationController.getLocations();
        System.out.println("Odpowiedz z serwera: " + response);
    }

}

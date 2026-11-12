package com.tub.Api;

import java.util.Scanner;

public class ApiMain {

  public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);

        CityClient cityClient=new CityClient();
        WikipediaClient wikipediaClient= new WikipediaClient();

        while(true) {

            System.out.print("\nStadt eingeben oder 'exit': ");
            String cityName = scanner.nextLine();

            if (cityName.equalsIgnoreCase("exit")) {
                break;
            }
           City city=cityClient.getCity(cityName);

            if (city == null) {
                System.out.println("Stadt nicht gefunden.");
                continue;
            }

            System.out.println("\n=== Informationen ===");
            System.out.println("Stadt: " + city.getName());
            System.out.println("Land: " + city.getCountry());
            System.out.println("Breitengrad: " + city.getLatitude());
            System.out.println("Längengrad: " + city.getLongitude());

            System.out.println("\n=== Wikipedia ===");

            String summary =
                    wikipediaClient.getSummary(city.getName());

            System.out.println(summary);
        }
        scanner.close();
    }
}




import java.util.List;
import java.util.Scanner;

//Denne klassen går ut på selve bruker menyen, her tar vi imot en variabel serviceSQL for å
public class UserInterface {
    private final ServiceSQL serviceSQL;
    private boolean importStatus;

    //Her lager vi konstruktøren som vi tar i bruk for å hente metoder fra ServiceSQL klassen
    //På denne måten opprettholder vi sikkerhet
    public UserInterface() {
        serviceSQL = new ServiceSQL();
    }

    //En veldig basic metode som kaller på informasjonen om menyen og gir brukeren valg til de forskjellige metodene
    public void runMenu() {
        Scanner scanner = new Scanner(System.in);
        int userOptionInput = 0;
        while (userOptionInput != 6) {
            optionsPrint();
            userOptionInput = Integer.valueOf(scanner.nextLine());
            switch (userOptionInput) {
                case 1 -> dataImportFromTextFile();
                case 2 -> getAllVehicles();
                case 3 -> getFuelAmount();
                case 4 -> getDrivableVehicles();
                case 5 -> getAvgMotorcycleEngineCapactiy();
                case 6 -> exitMenu();
            }
        }
    }

    //En sout som gir informasjon til bruker, denne kjøres i UserInterface
    private void optionsPrint() {
        System.out.println("Welcome to Vehicle & Scrap, " +
                "your number one provider of vehicle information\n" +
                "You have 5 choices \n" +
                "1: Import data from file\n" +
                "2: All information about vehicles\n" +
                "3: Total fuel amount in fossil cars\n" +
                "4: All information about driveable vehicles\n" +
                "5: Get average motorcycle engine capacity\n" +
                "6: Exit");
    }

    //Denne metoden spør brukeren om man er sikker på om de vil ut av menyen, for eks. "are you sure you want to exit?"
    //OBS! EKSTRAFUNKSJON
    //Utfordring som jeg ikke fikk til, for en eller annen grunn så fungerer det å exit med tast 2 og noen ganger ikke
    private void exitMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("----------------------");
        System.out.println("Are you sure?");
        String options = "Press 1 to stay\nPress 2 to exit";
        System.out.println(options);
        while (true) {
            int input = Integer.valueOf(scanner.nextLine());
            if (input == 1) {
                runMenu();
            } else if (input == 2) {
                break;
            } else {
                System.out.println("Not valid input\n" + options);
            }
        }
    }

    //Denne metoden passer på importering av informasjon til databasen skjer uten problemer
    //Den vil fortelle deg om import = success eller om det er allerede gjort
    //OBS! EKSTRAFUNKSJON
    private void dataImportFromTextFile() {
        if (importStatus) {
            System.out.println("Data import already done. Returning.");
            return;
        }
        try {
            serviceSQL.parseImportScrapyard();
            serviceSQL.parseImportVehicle();
            importStatus = true;
            System.out.println("IMPORT SUCCESSFUL");
            System.out.println("----------------------");
        } catch (Exception e) {
            System.err.println("Data import already done. Cannot run again.");

        }
    }

    //Denne metoden itirerer gjennom getAllVehicle metoden som returner en liste av alle vehicles
    //OBS! EKSTRAFUNKSJON (den gir mengden vehicles)
    private void getAllVehicles() {
        List<Vehicle> vehicle = serviceSQL.getAllVehicle();
        System.out.println("Here is all information about all vehicles:\n----------------------");
        for (Vehicle vehicle1 : vehicle) {
            System.out.println(vehicle1);
        }
        System.out.println("----------------------\nHere is the amount of vehicles: " + vehicle.size() + "\n----------------------");
    }

    //Denne metoden itirerer gjennom getAllVehicle metoden som returner en liste av alle vehicles som er kjørbare
    private void getDrivableVehicles() {
        List<Vehicle> vehicle = serviceSQL.getAllVehicleDrivable();
        System.out.println("Here is all information about all driveable vehicles:\n----------------------");
        for (Vehicle vehicle1 : vehicle) {
            System.out.println(vehicle1);
        }
        System.out.println("----------------------\nHere is the amount of vehicles: " + vehicle.size() + "\n----------------------");
    }

    //Denne metoden henter return verdien av ServiceSQL klassen som henter totalsummen av fossilfuel i fossilcar
    private void getFuelAmount() {
        System.out.println("----------------------\nHere is the total amount of fuel in all fossil cars: " + serviceSQL.getFossilFuelAmount() + "\n----------------------");
    }

    //OBS! EKSTRAFUNKSJON (her hentes gjennomsnittlig engine capacity for motorsykler)
    private void getAvgMotorcycleEngineCapactiy() {
        System.out.println("----------------------\nHere is the average motorcycle engine capacity: " + serviceSQL.getAvgMotorcycleEngineCapacity() + "\n----------------------");
    }


}

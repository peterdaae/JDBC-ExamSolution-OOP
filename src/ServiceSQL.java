import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

    //Klassen som utfører alle sql-hendelser som import,select osv.
public class ServiceSQL {
    private final MysqlDataSource source;

    //Konstruktør som henter "properties" som er loadet av prop_loader og setter properties til valgte "set"
    public ServiceSQL() {
        source = new MysqlDataSource();
        source.setServerName(PropertyLoader.properties.getProperty("host"));
        source.setDatabaseName(PropertyLoader.properties.getProperty("dbName"));
        source.setPort(Integer.parseInt(PropertyLoader.properties.getProperty("port")));
        source.setUser(PropertyLoader.properties.getProperty("user"));
        source.setPassword(PropertyLoader.properties.getProperty("pass"));
    }

    //SQL Spørring som importerer inn i scrapyard tabell
    private static final String IMPORT_SCRAPYARD_DB = "INSERT INTO SCRAPYARD (ScrapyardID, Name, Address, PhoneNumber) VALUES (?, ?, ?, ?)";

    //SQL Spørring som importerer inn i disse tabeller :
    private static final String IMPORT_ELECTRICCAR_DB = "INSERT INTO ELECTRICCAR (VehicleID, Brand, Model, YearModel, RegistrationNumber, ChassisNumber,Driveable, NumberOfSellableWheels,ScrapyardID,BatteryCapacity,ChargeLevel) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
    private static final String IMPORT_FOSSILCAR_DB = "INSERT INTO FOSSILCAR (VehicleID, Brand, Model, YearModel, RegistrationNumber, ChassisNumber,Driveable, NumberOfSellableWheels,ScrapyardID,FuelType,FuelAmount) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
    private static final String IMPORT_MOTORCYCLE_DB = "INSERT INTO MOTORCYCLE (VehicleID, Brand, Model, YearModel, RegistrationNumber, ChassisNumber,Driveable, NumberOfSellableWheels,ScrapyardID,HasSideCar,EngineCapacity,IsModified,NumberOfWheels) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";

    //SQL Spørring som gir alt fra disse tabeller :
    private static final String GET_ALL_ELECTRICCAR_DB = "SELECT * FROM ELECTRICCAR;";
    private static final String GET_ALL_FOSSILCAR_DB = "SELECT * FROM FOSSILCAR;";
    private static final String GET_ALL_MOTORCYCLE_DB = "SELECT * FROM MOTORCYCLE;";

    //SQL Spørring som gir sum av FuelAmount fra fossilcar
    private static final String GET_FUELAMOUNT_FOSSILCAR_DB = "SELECT SUM(FuelAmount) FROM FOSSILCAR;";

    //SQL Spørring som gir alle kjørbare "vehicles"
    private static final String GET_DRIVABLE_ELECTRICCAR_DB = "SELECT * FROM ELECTRICCAR WHERE DRIVEABLE = true";
    private static final String GET_DRIVABLE_FOSSILCAR_DB = "SELECT * FROM FOSSILCAR WHERE DRIVEABLE = true";
    private static final String GET_DRIVABLE_MOTORCYCLE_DB = "SELECT * FROM MOTORCYCLE WHERE DRIVEABLE = true";

    //OBS! EKSTRAFUNKSJON
    //SQL Spørring som gir gjennomsnittlig engine capacity fra motorsykkel tabellene
    private static final String GET_AVG_MOTORCYCLE_ENGINE_CAPACITY_DB = "SELECT AVG(EngineCapacity) FROM MOTORCYCLE;";

    //Metode som returnere en double verdi av average motorsykkel engine capacity
    public double getAvgMotorcycleEngineCapacity() {
        double avgAmount = 0;
        try (Connection connection = source.getConnection();
             PreparedStatement stmtAvg = connection.prepareStatement(GET_AVG_MOTORCYCLE_ENGINE_CAPACITY_DB);
             ResultSet resultAvg = stmtAvg.executeQuery();
        ) {
            while (resultAvg.next()) {
                avgAmount = resultAvg.getDouble(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return avgAmount;
    }

    //Metode som importerer alt informasjon fra tekstfil om scrapyard inn i databasen
    public void parseImportScrapyard() {
        File file = new File("vehicles.txt");
        try (Scanner fileScanner = new Scanner(file);
             Connection connection = source.getConnection();
             PreparedStatement stmtScrapyard = connection.prepareStatement(IMPORT_SCRAPYARD_DB);
        ) {
            fileScanner.nextLine();
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.equals("26")) {
                    break;
                }
                int id = Integer.valueOf(line);
                String name = fileScanner.nextLine();
                String address = fileScanner.nextLine();
                String number = fileScanner.nextLine();
                fileScanner.nextLine();

                stmtScrapyard.setInt(1, id);
                stmtScrapyard.setString(2, name);
                stmtScrapyard.setString(3, address);
                stmtScrapyard.setString(4, number);
                stmtScrapyard.executeUpdate();
            }


        } catch (FileNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Metode som importerer alt informasjon fra tekstfil om scrapyard inn i databasen
    public void parseImportVehicle() {
        File file = new File("vehicles.txt");
        try (Scanner fileScanner = new Scanner(file);
             Connection connection = source.getConnection();
             PreparedStatement stmtElectric = connection.prepareStatement(IMPORT_ELECTRICCAR_DB);
             PreparedStatement stmtFossil = connection.prepareStatement(IMPORT_FOSSILCAR_DB);
             PreparedStatement stmtCycle = connection.prepareStatement(IMPORT_MOTORCYCLE_DB);
        ) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.equals("26")) {
                    break;
                }
            }

            while (fileScanner.hasNextLine()) {
                int vehichleID = Integer.valueOf(fileScanner.nextLine());
                int scrapyardID = Integer.valueOf(fileScanner.nextLine());
                String vehicleType = fileScanner.nextLine();
                String brand = fileScanner.nextLine();
                String model = fileScanner.nextLine();
                int yearModel = Integer.valueOf(fileScanner.nextLine());
                String regNumber = fileScanner.nextLine();
                String chaNumber = fileScanner.nextLine();
                boolean driveable = Boolean.valueOf(fileScanner.nextLine());
                int sellableWheels = Integer.valueOf(fileScanner.nextLine());

                switch (vehicleType) {
                    case "FossilCar" -> {
                        String fuelType = fileScanner.nextLine();
                        int fuelAmount = Integer.valueOf(fileScanner.nextLine());
                        fileScanner.nextLine();

                        stmtFossil.setInt(1, vehichleID);
                        stmtFossil.setString(2, brand);
                        stmtFossil.setString(3, model);
                        stmtFossil.setInt(4, yearModel);
                        stmtFossil.setString(5, regNumber);
                        stmtFossil.setString(6, chaNumber);
                        stmtFossil.setBoolean(7, driveable);
                        stmtFossil.setInt(8, sellableWheels);
                        stmtFossil.setInt(9, scrapyardID);
                        stmtFossil.setString(10, fuelType);
                        stmtFossil.setInt(11, fuelAmount);
                        stmtFossil.executeUpdate();
                    }

                    case "ElectricCar" -> {
                        int batteryCapacity = Integer.valueOf(fileScanner.nextLine());
                        int chargeLevel = Integer.valueOf(fileScanner.nextLine());
                        fileScanner.nextLine();

                        stmtElectric.setInt(1, vehichleID);
                        stmtElectric.setString(2, brand);
                        stmtElectric.setString(3, model);
                        stmtElectric.setInt(4, yearModel);
                        stmtElectric.setString(5, regNumber);
                        stmtElectric.setString(6, chaNumber);
                        stmtElectric.setBoolean(7, driveable);
                        stmtElectric.setInt(8, sellableWheels);
                        stmtElectric.setInt(9, scrapyardID);
                        stmtElectric.setInt(10, batteryCapacity);
                        stmtElectric.setInt(11, chargeLevel);
                        stmtElectric.executeUpdate();
                    }

                    case "Motorcycle" -> {
                        boolean hasSideCar = Boolean.valueOf(fileScanner.nextLine());
                        int engineCapactiy = Integer.valueOf(fileScanner.nextLine());
                        boolean isModified = Boolean.valueOf(fileScanner.nextLine());
                        int numberOfWheels = Integer.valueOf(fileScanner.nextLine());
                        fileScanner.nextLine();

                        stmtCycle.setInt(1, vehichleID);
                        stmtCycle.setString(2, brand);
                        stmtCycle.setString(3, model);
                        stmtCycle.setInt(4, yearModel);
                        stmtCycle.setString(5, regNumber);
                        stmtCycle.setString(6, chaNumber);
                        stmtCycle.setBoolean(7, driveable);
                        stmtCycle.setInt(8, sellableWheels);
                        stmtCycle.setInt(9, scrapyardID);
                        stmtCycle.setBoolean(10, hasSideCar);
                        stmtCycle.setInt(11, engineCapactiy);
                        stmtCycle.setBoolean(12, isModified);
                        stmtCycle.setInt(13, numberOfWheels);
                        stmtCycle.executeUpdate();
                    }
                }
            }
        } catch (FileNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Metode som henter all informasjon fra databasen om alle vehicles
    public List<Vehicle> getAllVehicle() {
        List<Vehicle> vehicle = new ArrayList<>();
        try (Connection connection = source.getConnection();
             PreparedStatement stmtElectric = connection.prepareStatement(GET_ALL_ELECTRICCAR_DB);
             PreparedStatement stmtFossil = connection.prepareStatement(GET_ALL_FOSSILCAR_DB);
             PreparedStatement stmtMotorcycle = connection.prepareStatement(GET_ALL_MOTORCYCLE_DB);
             ResultSet resultElectric = stmtElectric.executeQuery();
             ResultSet resultFossil = stmtFossil.executeQuery();
             ResultSet resultCycle = stmtMotorcycle.executeQuery();
        ) {
            while (resultElectric.next()) {
                String type = "ElectricCar";
                int id = resultElectric.getInt("VehicleID");
                String brand = resultElectric.getString("Brand");
                String model = resultElectric.getString("Model");
                int yearModel = resultElectric.getInt("YearModel");
                String regNumber = resultElectric.getString("RegistrationNumber");
                String chaNumber = resultElectric.getString("ChassisNumber");
                boolean drivable = resultElectric.getBoolean("Driveable");
                int wheelSell = resultElectric.getInt("NumberOfSellableWheels");
                int scrapID = resultElectric.getInt("ScrapYardID");
                int battery = resultElectric.getInt("BatteryCapacity");
                int charge = resultElectric.getInt("ChargeLevel");
                vehicle.add(new ElectricCar(type, id, brand, model, yearModel, regNumber, chaNumber, drivable, wheelSell, scrapID, battery, charge));
            }

            while (resultFossil.next()) {
                String type = "FossilCar";
                int id = resultFossil.getInt("VehicleID");
                String brand = resultFossil.getString("Brand");
                String model = resultFossil.getString("Model");
                int yearModel = resultFossil.getInt("YearModel");
                String regNumber = resultFossil.getString("RegistrationNumber");
                String chaNumber = resultFossil.getString("ChassisNumber");
                boolean drivable = resultFossil.getBoolean("Driveable");
                int wheelSell = resultFossil.getInt("NumberOfSellableWheels");
                int scrapID = resultFossil.getInt("ScrapYardID");
                String fuelType = resultFossil.getString("FuelType");
                int fuelAmount = resultFossil.getInt("FuelAmount");
                vehicle.add(new FossilCar(type, id, brand, model, yearModel, regNumber, chaNumber, drivable, wheelSell, scrapID, fuelType, fuelAmount));
            }

            while (resultCycle.next()) {
                String type = "Motorcycle";
                int id = resultCycle.getInt("VehicleID");
                String brand = resultCycle.getString("Brand");
                String model = resultCycle.getString("Model");
                int yearModel = resultCycle.getInt("YearModel");
                String regNumber = resultCycle.getString("RegistrationNumber");
                String chaNumber = resultCycle.getString("ChassisNumber");
                boolean drivable = resultCycle.getBoolean("Driveable");
                int wheelSell = resultCycle.getInt("NumberOfSellableWheels");
                int scrapID = resultCycle.getInt("ScrapYardID");
                boolean side = resultCycle.getBoolean("HasSideCar");
                int engine = resultCycle.getInt("EngineCapacity");
                boolean mod = resultCycle.getBoolean("IsModified");
                int amountWheels = resultCycle.getInt("NumberOfWheels");
                vehicle.add(new MotorCycle(type, id, brand, model, yearModel, regNumber, chaNumber, drivable, wheelSell, scrapID, side, engine, mod, amountWheels));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vehicle;
    }

    //Metode som henter totalsummen av fossilfuel i alle fossilcars
    public int getFossilFuelAmount() {
        int amountFuel = 0;
        try (Connection connection = source.getConnection();
             PreparedStatement stmtFuel = connection.prepareStatement(GET_FUELAMOUNT_FOSSILCAR_DB);
             ResultSet resultFuel = stmtFuel.executeQuery();
        ) {
            while (resultFuel.next()) {
                amountFuel = resultFuel.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return amountFuel;
    }

    //Metode som henter alle vehicles som er kjørbare, denne er veldig lik getAllVehicle, forskjellen er bare SQL spørring i Prepared Statement
    public List<Vehicle> getAllVehicleDrivable() {
        List<Vehicle> vehicle = new ArrayList<>();
        try (Connection connection = source.getConnection();
             PreparedStatement stmtElectric = connection.prepareStatement(GET_DRIVABLE_ELECTRICCAR_DB);
             PreparedStatement stmtFossil = connection.prepareStatement(GET_DRIVABLE_FOSSILCAR_DB);
             PreparedStatement stmtMotorcycle = connection.prepareStatement(GET_DRIVABLE_MOTORCYCLE_DB);
             ResultSet resultElectric = stmtElectric.executeQuery();
             ResultSet resultFossil = stmtFossil.executeQuery();
             ResultSet resultCycle = stmtMotorcycle.executeQuery();
        ) {
            while (resultElectric.next()) {
                String type = "ElectricCar";
                int id = resultElectric.getInt("VehicleID");
                String brand = resultElectric.getString("Brand");
                String model = resultElectric.getString("Model");
                int yearModel = resultElectric.getInt("YearModel");
                String regNumber = resultElectric.getString("RegistrationNumber");
                String chaNumber = resultElectric.getString("ChassisNumber");
                boolean drivable = resultElectric.getBoolean("Driveable");
                int wheelSell = resultElectric.getInt("NumberOfSellableWheels");
                int scrapID = resultElectric.getInt("ScrapYardID");
                int battery = resultElectric.getInt("BatteryCapacity");
                int charge = resultElectric.getInt("ChargeLevel");
                vehicle.add(new ElectricCar(type, id, brand, model, yearModel, regNumber, chaNumber, drivable, wheelSell, scrapID, battery, charge));
            }

            while (resultFossil.next()) {
                String type = "FossilCar";
                int id = resultFossil.getInt("VehicleID");
                String brand = resultFossil.getString("Brand");
                String model = resultFossil.getString("Model");
                int yearModel = resultFossil.getInt("YearModel");
                String regNumber = resultFossil.getString("RegistrationNumber");
                String chaNumber = resultFossil.getString("ChassisNumber");
                boolean drivable = resultFossil.getBoolean("Driveable");
                int wheelSell = resultFossil.getInt("NumberOfSellableWheels");
                int scrapID = resultFossil.getInt("ScrapYardID");
                String fuelType = resultFossil.getString("FuelType");
                int fuelAmount = resultFossil.getInt("FuelAmount");
                vehicle.add(new FossilCar(type, id, brand, model, yearModel, regNumber, chaNumber, drivable, wheelSell, scrapID, fuelType, fuelAmount));
            }

            while (resultCycle.next()) {
                String type = "Motorcycle";
                int id = resultCycle.getInt("VehicleID");
                String brand = resultCycle.getString("Brand");
                String model = resultCycle.getString("Model");
                int yearModel = resultCycle.getInt("YearModel");
                String regNumber = resultCycle.getString("RegistrationNumber");
                String chaNumber = resultCycle.getString("ChassisNumber");
                boolean drivable = resultCycle.getBoolean("Driveable");
                int wheelSell = resultCycle.getInt("NumberOfSellableWheels");
                int scrapID = resultCycle.getInt("ScrapYardID");
                boolean side = resultCycle.getBoolean("HasSideCar");
                int engine = resultCycle.getInt("EngineCapacity");
                boolean mod = resultCycle.getBoolean("IsModified");
                int amountWheels = resultCycle.getInt("NumberOfWheels");
                vehicle.add(new MotorCycle(type, id, brand, model, yearModel, regNumber, chaNumber, drivable, wheelSell, scrapID, side, engine, mod, amountWheels));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vehicle;
    }

}



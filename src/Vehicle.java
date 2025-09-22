public abstract class Vehicle {

    //Bruker her en abstrakt-klasse som en mal for underklassene som kommer til å være ElectricCar, FossilCar og Motorcycle
    //Siden ElectricCar, FossilCar og Motorcycle har felles egenskaper brukes denne klassen som superklassen for disse
    //Her er felles data-verdier og metoder som underklassene skal arve.

    private String vehicleType;
    private int vehicleId;
    private String brand;
    private String model;
    private int yearModel;
    private String registrationNumber;
    private String chassisNumber;
    private boolean driveable;
    private int numberOfSellableWheels;
    private int scrapyardID;

    public Vehicle(String vehicleType, int vehicleId, String brand, String model, int yearModel, String registrationNumber, String chassisNumber, boolean driveable, int numberOfSellableWheels, int scrapyardID) {
        this.vehicleType = vehicleType;
        this.vehicleId = vehicleId;
        this.brand = brand;
        this.model = model;
        this.yearModel = yearModel;
        this.registrationNumber = registrationNumber;
        this.chassisNumber = chassisNumber;
        this.driveable = driveable;
        this.numberOfSellableWheels = numberOfSellableWheels;
        this.scrapyardID = scrapyardID;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleType='" + vehicleType + '\'' +
                ", vehicleId=" + vehicleId +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", yearModel=" + yearModel +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", chassisNumber='" + chassisNumber + '\'' +
                ", driveable=" + driveable +
                ", numberOfSellableWheels=" + numberOfSellableWheels +
                ", scrapyardID=" + scrapyardID +
                '}';
    }

    public boolean isDriveable() {
        return driveable;
    }
}

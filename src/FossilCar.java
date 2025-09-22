public class FossilCar extends Vehicle {

    //Egne ikke-felles verdier for FossilCar
    //Arvet konstruktor fra super-klasse med egne verdier
    //Arvet toString-metode med egne verdier inkludert
    private String fuelType;
    private int fuelAmount;

    public FossilCar(String vehicleType, int vehicleId, String brand, String model, int yearModel, String registrationNumber, String chassisNumber, boolean driveable, int numberOfSellableWheels, int scrapyardID, String fuelType, int fuelAmount) {
        super(vehicleType, vehicleId, brand, model, yearModel, registrationNumber, chassisNumber, driveable, numberOfSellableWheels, scrapyardID);
        this.fuelType = fuelType;
        this.fuelAmount = fuelAmount;
    }

    @Override
    public String toString() {
        return "FossilCar{" +
                "fuelType='" + fuelType + '\'' +
                ", fuelAmount=" + fuelAmount +
                "} " + super.toString();
    }

}

public class MotorCycle extends Vehicle {

    //Egne ikke-felles verdier for Motorcycle
    //Arvet konstruktor fra super-klasse med egne verdier
    //Arvet toString-metode med egne verdier inkludert
    private boolean hasSideCar;
    private int engineCapactiy;
    private boolean isModified;
    private int numberOfWheels;

    public MotorCycle(String vehicleType, int vehicleId, String brand, String model, int yearModel, String registrationNumber, String chassisNumber, boolean driveable, int numberOfSellableWheels, int scrapyardID, boolean hasSideCar, int engineCapactiy, boolean isModified, int numberOfWheels) {
        super(vehicleType, vehicleId, brand, model, yearModel, registrationNumber, chassisNumber, driveable, numberOfSellableWheels, scrapyardID);
        this.hasSideCar = hasSideCar;
        this.engineCapactiy = engineCapactiy;
        this.isModified = isModified;
        this.numberOfWheels = numberOfWheels;
    }

    @Override
    public String toString() {
        return "MotorCycle{" +
                "hasSideCar=" + hasSideCar +
                ", engineCapactiy=" + engineCapactiy +
                ", isModified=" + isModified +
                ", numberOfWheels=" + numberOfWheels +
                "} " + super.toString();
    }
}

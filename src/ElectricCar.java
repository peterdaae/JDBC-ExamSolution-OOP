public class ElectricCar extends Vehicle {

    //Egne ikke-felles verdier for ElectricCar
    //Arvet konstruktor fra super-klasse med egne verdier
    //Arvet toString-metode med egne verdier inkludert
    private int batteryCapactiy;
    private int chargeLevel;

    public ElectricCar(String vehicleType, int vehicleId, String brand, String model, int yearModel, String registrationNumber, String chassisNumber, boolean driveable, int numberOfSellableWheels, int scrapyardID, int batteryCapactiy, int chargeLevel) {
        super(vehicleType, vehicleId, brand, model, yearModel, registrationNumber, chassisNumber, driveable, numberOfSellableWheels, scrapyardID);
        this.batteryCapactiy = batteryCapactiy;
        this.chargeLevel = chargeLevel;
    }

    @Override
    public String toString() {
        return "ElectricCar{" +
                "batteryCapactiy=" + batteryCapactiy +
                ", chargeLevel=" + chargeLevel +
                "} " + super.toString();
    }
}

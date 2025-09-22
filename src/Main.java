public class Main {
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        ui.runMenu();

        //OBS! VIKTIG INFORMASJON i readME.md
        //main klasse som bare kjører hele programmet

        //HIERARIKET
        //SUPER-KLASSE -> Vehicle
        //SUB-KLASSER -> ElectricCar, FossilCar, MotorCycle
        //prop.properties -> PropertyLoader -> ServiceSQL + SUPER/SUB-KLASSER -> UserInterface -> Main
    }
}
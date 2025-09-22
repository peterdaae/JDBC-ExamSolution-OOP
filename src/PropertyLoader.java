import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

//Denne klassen laster inn "sensitiv informasjon" fra prop.properties filen
//Den gjør informasjonen tilgjengelig ved props objektet
//Unntak om informasjon ikke blir lest eller lastet inn skikkelig
public class PropertyLoader {
    public static final Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("prop.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Unable to load properties file" + e.getMessage());
        }

    }
}


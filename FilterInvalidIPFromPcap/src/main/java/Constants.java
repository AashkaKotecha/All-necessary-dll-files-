import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Properties;

public class Constants {
    public static Properties properties = getPropertyInstance();

    private static Properties getPropertyInstance(){

        if(null == properties)
            properties = new Properties();
        try{

            properties.load(new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getClass().getResourceAsStream("/application.properties"))));

        }catch(IOException e){
            e.printStackTrace();
        }
        return properties;
    }
    public static Long getDelayValue(String delay){
        return (null != properties.getProperty(delay) ? Long.valueOf(properties.getProperty(delay)) : 0L);
    }
    public static Long getPeriodValue(String period){
        return (null != properties.getProperty(period) ? Long.valueOf(properties.getProperty(period)) : 1000L);
    }
}

import java.lang.reflect.Field;

public class initLoadJnet {
    public static void initJnetLib(){
        //If we don't set the path programmatically as below then we have to pass the path manually while running from cmd prompt
        //java -Djava.library.path="C:\Windows\System32\jnetpcap-1.4.r1425" -jar FileCounter.jar
        setLibraryPath();
    }
    private static void setLibraryPath() {

        try {

            System.setProperty("java.library.path", "C:\\Windows\\System32\\jnetpcap-1.4.r1425");

            Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
            fieldSysPath.setAccessible(true);
            fieldSysPath.set(null, null);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}

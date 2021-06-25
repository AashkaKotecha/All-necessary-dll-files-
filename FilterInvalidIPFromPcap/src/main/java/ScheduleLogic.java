import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

public class ScheduleLogic extends TimerTask {


    public ScheduleLogic() {
        int countOfFile = Integer.valueOf(readIntCounter());
        System.out.println("Running with globalCounter value: "+countOfFile+" at time: " + new java.util.Date());
        try{

            String fileToBeRead =Constants.properties.getProperty("FILENAMEPATTERN")+countOfFile+Constants.properties.getProperty("PCAPEND");
            File file = new File(Constants.properties.getProperty("FOLDERPATH")+fileToBeRead);
            if(file.exists()){
                ReadPcap.readPcap(fileToBeRead);
                countOfFile++;
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error while reading file: "+e.getMessage());
        }
        writeCounterValue(countOfFile);
        System.out.println("Updating globalCounter at the end: "+countOfFile);
    }

    @Override
    public void run() {
        new ScheduleLogic();
    }
    public static void main(String[] args)
    {
        initLoadJnet.initJnetLib(); //Loading the jnet dll library programmatically in Classpath
        System.out.println(System.getProperty("java.library.path"));
        Timer timer = new Timer();
        TimerTask task = new ScheduleLogic();
        // Long delay = (System.currentTimeMillis() - task.scheduledExecutionTime());
        // Long delay =  task.scheduledExecutionTime(); use this if we want delay to be measured from previous task's execution time
        timer.scheduleAtFixedRate(task,Constants.getDelayValue("DELAY"),Constants.getPeriodValue("PERIOD"));


    }
    public static void writeCounterValue(int count){
        try {
            FileWriter fileWriter = new FileWriter(Constants.properties.getProperty("COUNTERFILE"));
            fileWriter.write(String.valueOf(count));
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readIntCounter(){
        try{
            int i;
            FileReader fileReader = new FileReader(Constants.properties.getProperty("COUNTERFILE"));
            while (( i=fileReader.read()) != -1){
                System.out.println(String.valueOf((char) i));
                return  String.valueOf((char) i);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return "1";
    }


}

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import com.opencsv.CSVWriter;

public class WriteToCSVFiles {
    static String csvFolder = Constants.properties.getProperty("CSVFOLDER");
    static String end = Constants.properties.getProperty("CSVEND");
    static String csvFileName;
    public static void createSourceCSVFromPcap(List<String> finalList, String filename){
        final String masterIP = Constants.properties.getProperty("MASTERIP");
        String separator = ".";
        int sepPos = filename.lastIndexOf(separator);
        csvFileName = csvFolder+filename.substring(0,sepPos)+end;
        char c = ';';
        String newline = "\n";
        String [] arr = finalList.toArray(new String[0]);
        try{
            File file = new File(csvFileName);
            boolean flag = file.createNewFile();
            System.out.println("File creation: "+flag);
            FileWriter writer = new FileWriter(csvFileName);
            for(int i=0;i<arr.length;i++){
                if(i==0) {
                    arr[i] = arr[i] + newline;
                }
                arr[i] = arr[i]+String.valueOf(c)+newline;
                writer.write(arr[i]);
            }

            writer.flush();
            writer.close();
            FilterLogic.invalidIP(masterIP,csvFileName);
            finalList.clear();
        }catch(IOException e){
            e.printStackTrace();
        }

    }
    public static void outputInvalidCSVFile(List<String> list){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            List<String> distinctList = list.stream().distinct().collect(Collectors.toList());
            if(distinctList.contains("IP Address")){
                distinctList.remove("IP Address");
            }
            distinctList.removeAll(Arrays.asList("", null));
            CSVWriter writer = new CSVWriter(new FileWriter(Constants.properties.getProperty("OutputCSVInvalidFilePath")));
            String [] arr = distinctList.toArray(new String[0]);
            for(int i=0;i<arr.length;i++) {
                arr[i] = arr[i]+" "+formatter.format(date);
            }
            writer.writeNext(arr);
            writer.close();

        }catch(IOException e){
            e.printStackTrace();
        }

    }

}

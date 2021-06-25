import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilterLogic {
    public static void invalidIP(String mIPFile, String sIPFile) {
        try{
            FileReader filereader1 = new FileReader(mIPFile);
            FileReader filereader2 = new FileReader(sIPFile);

            CSVFormat format = CSVFormat.RFC4180.withHeader().withDelimiter(';');
            CSVParser parser1 = new CSVParser(filereader1, format);
            CSVParser parser2 = new CSVParser(filereader2, format);
            List<String> masterIP = new ArrayList<String>();
            List<String> sourceIP = new ArrayList<String>();

            for(CSVRecord record1 : parser1)
                masterIP.add(record1.get("IP Address").toString());
            parser1.close();
            System.out.println("MasterIP: "+masterIP);


            for(CSVRecord record2 : parser2)
                sourceIP.add(record2.get("IP Address").toString());
            parser2.close();
            System.out.println("SourceIP: "+sourceIP);


            List<String> differences = (List<String>) sourceIP.stream()
                    .filter(element -> !masterIP.contains(element))
                    .collect(Collectors.toList());
            CSVPrinter printer = new CSVPrinter(System.out, format.withDelimiter(' '));

            System.out.println("****Difference****");
            for(String m : differences){
                printer.printRecord(m);
            }
            printer.close();


            WriteToCSVFiles.outputInvalidCSVFile(differences); //output to a csv file


        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

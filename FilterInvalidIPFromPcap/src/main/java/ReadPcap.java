import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.jnetpcap.Pcap;
import org.jnetpcap.packet.JPacket;
import org.jnetpcap.packet.JPacketHandler;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Tcp;

public class ReadPcap {
    static double count = 0;
    static String folderPath = Constants.properties.getProperty("FOLDERPATH");

    public static void readPcap(String pcapFileName) {
        String pcapFilePath = folderPath + pcapFileName;
        List<String> finalList = new ArrayList<String>();
        StringBuilder errbuf = new StringBuilder();
        try {
            final Pcap pcap = Pcap.openOffline(pcapFilePath, errbuf);

            if (pcap == null) {
                System.out.println("Error occurred : " + errbuf.toString());
                return;
            }

            pcap.loop(10, new JPacketHandler<String>() {
                @Override
                public void nextPacket(JPacket jPacket, String s) {
                    String sourceIP, destinationIP;
                    Ip4 ip = new Ip4();
                    final Tcp tcp = new Tcp();
                    if (jPacket.hasHeader(tcp)) {
                        if (jPacket.hasHeader(ip)) {

                            if (finalList.isEmpty())
                                finalList.add("IP Address");

                            sourceIP = FormatUtils.ip(ip.source());
                            finalList.add(sourceIP);
                            destinationIP = FormatUtils.ip(ip.destination());
                            finalList.add(destinationIP);

                            System.out.println("srcIP=" + sourceIP + " dstIP=" + destinationIP);
                        }
                    }

                    count++;

                }
            }, "jNetPcap");
            count = 0;
            WriteToCSVFiles.createSourceCSVFromPcap(finalList, pcapFileName);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while reading file: " + e.getMessage());
        }

    }
}

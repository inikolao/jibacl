/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.maventest;

import java.io.*;
import java.net.UnknownHostException;
import java.util.*;
import org.msgpack.rpc.*;
import org.msgpack.rpc.error.TransportError;
import us.jubat.classifier.ClassifierClient;
import us.jubat.classifier.EstimateResult;
import us.jubat.classifier.LabeledDatum;
import us.jubat.common.Datum;

/**
 *
 * @author root
 */
public class simplet3 {
    private static Datum makeDatum(String key,double value) {
        return new Datum().addNumber(key, value);
    }
    private static LabeledDatum makeTrainDatum(String label, String key, double value) {
        return new LabeledDatum(label, makeDatum(key,value));
    }
    public static void createTrainFile() throws Exception
    {
        //text manipulation
        PrintWriter writer = new PrintWriter("trainfs.dat", "UTF-8");
        File folder = new File("/root/Documents/Diplwmatikh/jubatusWSp/jubatestFs/dataTrain");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
            //System.out.println(file.getAbsolutePath());
            FileReader fr =new FileReader(file.getAbsolutePath());
            BufferedReader txtr =new BufferedReader(fr);
            writer.println(txtr.readLine());
            txtr.close();
            }
        }
        writer.close();
        PrintWriter trd = new PrintWriter("train.dat", "UTF-8");
        FileReader frd =new FileReader("trainfs.dat");
        BufferedReader dr =new BufferedReader(frd);
        //String lne =dr.readLine();
        String lne= null;
        while ((lne = dr.readLine()) != null) {
        lne=lne.replaceAll("\"readings\"\\:\\[|\\]", "");
        lne = lne.replaceFirst("\\{","");
        lne = lne.replaceFirst(",\"capabili.*","");
        //System.out.println(lne);
        //System.out.println(split[0]);
        String type="";
        String[] split1 = lne.split(",\\{");
        if(split1[0].contains("cur"))
        {
            type="cur";
        }
        if(split1[0].contains("tmp"))
        {
            type="tmp";
        }
        if(split1[0].contains("light"))
        {
            type="light";
        }
        String[] split2 = lne.split("\",");
        String out=split2[1];
        out=out.replaceFirst("\\{", "");
        String[] split3 =out.split("},\\{");
        split3[(split3.length-1)]=split3[(split3.length-1)].replaceFirst("}", "");
        String fout;
        
        //for loop mexri split3.length
        for(int i=0;i<(split3.length);i++){
        fout=split1[0]+",\"type\":\""+type+"\","+split3[i];
        trd.println(fout);
        
        }
        
        }
        trd.close();
        dr.close();
        
        
    }
    public static void createClassfFile() throws Exception
    {
        //text manipulation
        PrintWriter writer = new PrintWriter("classfs.dat", "UTF-8");
        File folder = new File("/root/Documents/Diplwmatikh/jubatusWSp/jubatestFs/dataCl");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
            //System.out.println(file.getAbsolutePath());
            FileReader fr =new FileReader(file.getAbsolutePath());
            BufferedReader txtr =new BufferedReader(fr);
            writer.println(txtr.readLine());
            txtr.close();
            }
        }
        writer.close();
        PrintWriter trd = new PrintWriter("class.dat", "UTF-8");
        FileReader frd =new FileReader("classfs.dat");
        BufferedReader dr =new BufferedReader(frd);
        //String lne =dr.readLine();
        String lne= null;
        while ((lne = dr.readLine()) != null) {
        lne=lne.replaceAll("\"readings\"\\:\\[|\\]", "");
        lne = lne.replaceFirst("\\{","");
        lne = lne.replaceFirst(",\"capabili.*","");
        //System.out.println(lne);
        //System.out.println(split[0]);
        String type="";
        String[] split1 = lne.split(",\\{");
        if(split1[0].contains("cur"))
        {
            type="cur";
        }
        if(split1[0].contains("tmp"))
        {
            type="tmp";
        }
        if(split1[0].contains("light"))
        {
            type="light";
        }
        String[] split2 = lne.split("\",");
        String out=split2[1];
        out=out.replaceFirst("\\{", "");
        String[] split3 =out.split("},\\{");
        split3[(split3.length-1)]=split3[(split3.length-1)].replaceFirst("}", "");
        String fout;
        
        //for loop mexri split3.length
        for(int i=0;i<(split3.length);i++){
        fout=split1[0]+",\"type\":\""+type+"\","+split3[i];
        trd.println(fout);
        
        }
        
        }
        trd.close();
        dr.close();
        
        
    }
    public simplet3(){}
    public static void main(String[] args) throws Exception {
        createTrainFile();
        createClassfFile();
        String host = "192.168.2.8";
        int port = 9199;
        String name = "test";
        int k=1;
        ClassifierClient client = new ClassifierClient(host, port, name, 10);

        List<LabeledDatum> trainData;
        trainData = new ArrayList();
        FileReader frdc =new FileReader("train.dat");
        BufferedReader drc =new BufferedReader(frdc);
        String lne;
        String[] elem;
        String[] elemval1 = null;
        String[] elemval2 = null;
        while ((lne = drc.readLine()) != null) {
        //lne = drc.readLine();
        elem=lne.split(",");
        //System.out.println(elem[0]);
        elemval1=elem[1].split(":");
        elemval2=elem[3].split(":");
        
        //elemval[1] [0].replaceAll("\"", ""), elemval[1] [1].replaceAll("\"", "")
        //System.out.println(k);
        trainData.add(makeTrainDatum(elemval1 [1],elemval2 [0].replaceAll("\"", ""), Double.parseDouble(elemval2 [1])));
        //k++;
        }
        drc.close();
        frdc.close();
        client.train(trainData);

        //Datum[] testData = { //
        //makeDatum("reading", 25),
        //        makeDatum("reading", 0), };
         List<Datum> testData= new ArrayList();
         String lne2= null;
        String[] elem2;
        String[] elemval21 = null;
        String[] elemval22 = null;
        int sz2;
        //datum creation
        System.out.println("clsf");
        Datum[] tesst = null;
        Datum ts;
        //DATUM format-> NodID ? ,Type ? ,Timestamp ? ,reading ? ,StringReading ?
        System.out.println("LALALA");
        int k2=0;
        FileReader frdc2 =new FileReader("class.dat");
        BufferedReader drc2 =new BufferedReader(frdc2);
        while ((lne2 = drc2.readLine()) != null) {
        elem2=lne2.split(",");
        //elemval21=elem2[1].split(":");
        elemval22=elem2[3].split(":");
        //elemval[1] [0].replaceAll("\"", ""), elemval[1] [1].replaceAll("\"", "")
        //System.out.println(elemval21 [0].replaceAll("\"", ""));
        //System.out.println(Integer.parseInt(elemval22 [1]));
        testData.add(makeDatum(elemval22 [0].replaceAll("\"", ""), Double.parseDouble(elemval22 [1])));
        }
        drc2.close();
        frdc2.close();
        List<List<EstimateResult>> results = client.classify(testData);

        for (List<EstimateResult> result : results) {
            for (EstimateResult r : result) {
                System.out.printf("%s %f\n", r.label, r.score);
            }
            System.out.println();
        }

        System.exit(0);
    }
}

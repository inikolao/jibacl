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
public class simplet {
//String hst= new String();
//int prt=0;    
public simplet(){


}
    public void createTrainFile() throws Exception
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
        System.out.println(lne);
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
    public void createClassfFile() throws Exception
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
        System.out.println(lne);
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
    public void client(String hst,int prt) throws UnknownHostException, FileNotFoundException, IOException
    {
         String host = "192.168.2.8";
        int port = 9199;
        String name = "test";
        ClassifierClient cl=new ClassifierClient(host,port,name,10);    
        try{
        
        //String lne =dr.readLine();
        String lne= null;
        String[] elem;
        String[] elemval1 = null;
        String[] elemval2 = null;
        int sz;
        //datum creation
        Datum dt = null;
        //DATUM format-> NodID ? ,Type ? ,Timestamp ? ,reading ? ,StringReading ?
        LabeledDatum d = null;
        LabeledDatum[] fd = null;
        System.out.println("datum create");
        int k=0;
        FileReader frdc =new FileReader("train.dat");
        BufferedReader drc =new BufferedReader(frdc);
        while ((lne = drc.readLine()) != null) {
        //lne = drc.readLine();
        System.out.println(lne);
        elem=lne.split(",");
        System.out.println(elem[0]);
        sz=elem.length;//need Tuples einai kalyetra.....        
        elemval1=elem[1].split(":");
        elemval2=elem[3].split(":");
        System.out.println(elemval1 [0].replaceAll("\"", ""));
        System.out.println(Integer.parseInt(elemval2 [1]));
        //elemval[1] [0].replaceAll("\"", ""), elemval[1] [1].replaceAll("\"", "")
        //System.out.println(k);
        fd[k] =new LabeledDatum(elemval1 [1],new Datum().addNumber(elemval2 [0].replaceAll("\"", ""), Integer.parseInt(elemval2 [1])));
        k++;
        System.out.println("k1");
        System.out.println(k);
        
        }
        
        drc.close();
        frdc.close();
        cl.train(Arrays.asList(fd));
        }
        catch(Exception exv){System.out.println("Skatoules");}

        System.out.println(cl.getStatus());
        //String lne =dr.readLine();
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
//exei problhma h grammh?        
        //tesst [k2]=
        //Datum hg=new Datum().addNumber(elemval22 [0].replaceAll("\"", ""), Integer.parseInt(elemval22 [1]));
        //tesst [k2]=hg;
        k2++;
        System.out.println(k2);
        }
        drc2.close();
        frdc2.close();
        //System.out.println("clyfi FUCK YOU");
        Datum hg=new Datum().addNumber("cur", 25);
        Datum[] testData = {hg,hg,hg};
       //cl.classify(Arrays.asList(testData));
        //try{
        double fds = 0;
        
        List<List<EstimateResult>> results = cl.classify(Arrays.asList(testData));
        for (List<EstimateResult> result : results) {
            //EstimateResult hsg;
            for (EstimateResult r : result) {
                System.out.println(r.label);
                System.out.println("OK");
                fds=r.score;
                //r.label
            }
            //System.out.println(cl.getConfig());
            //String[] ls=(String[]) results.toArray();
            System.out.println(results);
        }
       //}
       //catch(Exception eklkj){System.out.println("Skatoulaasd");}
    }
    
    
    public static void main(String[] args)
    {
        simplet sm= new simplet();
        
        try{
            sm.createTrainFile();
            sm.createClassfFile();
            sm.client("192.168.2.8", 9199);
            System.exit(0);
        }
        catch(Exception ex)
        {
        //System.out.println("yparxei thema");
        }

    }
}
/*
PrintWriter wr = new PrintWriter("test.dat", "UTF-8");
        wr.println(split3[0]);
        wr.close();
*/

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
public class simplet2 {

    private static Datum makeDatum(String key,double value) {
        return new Datum().addNumber(key, value);
    }
    private static LabeledDatum makeTrainDatum(String label, String key, double value) {
        return new LabeledDatum(label, makeDatum(key,value));
    }
    public simplet2(){}
    
    
     public static void main(String[] args) throws Exception {
        String host = "192.168.2.8";
        int port = 9199;
        String name = "test";

        ClassifierClient client = new ClassifierClient(host, port, name, 1);

        LabeledDatum[] trainData = { //
        makeTrainDatum("cur","reading",0),
        makeTrainDatum("light","reading",1),
        makeTrainDatum("tmp","reading",25),
        makeTrainDatum("tmp","reading",21),
        makeTrainDatum("light","reading",1.2),
        makeTrainDatum("cur","reading",0),
        makeTrainDatum("tmp","reading",23),
        makeTrainDatum("tmp","reading",24),
        makeTrainDatum("tmp","reading",21)
        };

        client.train(Arrays.asList(trainData));

        Datum[] testData = { //
        makeDatum("reading", 25),
                makeDatum("reading", 0), };

        List<List<EstimateResult>> results = client.classify(
                Arrays.asList(testData));

        for (List<EstimateResult> result : results) {
            for (EstimateResult r : result) {
                System.out.printf("%s %f\n", r.label, r.score);
            }
            System.out.println();
        }

        System.exit(0);
    }
}

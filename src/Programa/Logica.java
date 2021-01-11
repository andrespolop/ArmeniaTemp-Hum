/* Integrantes: 
- Dario Castaño. Cod: 0221820013 
- Fernando Padilla. Cod: 0221820007 
- Andrés Polo. Cod: 0221820015 
*/




package Programa;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import weka.classifiers.functions.LinearRegression;
import weka.clusterers.SimpleKMeans;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;


public class Logica {
    
    public ArrayList KMeans(int n) throws FileNotFoundException, IOException, Exception{
        
        String ruta = "armenia22.csv";
        
        Instances dataset = new Instances(new BufferedReader(new FileReader(ruta)));
        
        SimpleKMeans SKM = new SimpleKMeans();
        
        SKM.setNumClusters(n);
        
        SKM.buildClusterer(dataset);
        
        Instances c = SKM.getClusterCentroids();
   
        ArrayList<Object> i = new ArrayList<>();
        
        for(int x=0;x<n;x++){
            i.add(x+1);
            i.add(c.instance(x).value(0));
            i.add(c.instance(x).value(1));
            i.add(c.instance(x).value(2));
        }
        
        return i;
    }
    
    public Instances obtenerDataset(String ruta, int indice) throws FileNotFoundException, IOException{
       
        Instances dataset = new Instances(new BufferedReader(new FileReader(ruta)));
        
        FastVector fv = new FastVector(2);
        fv.addElement(new Attribute("Temperatura"));
        if(indice==0){
            fv.addElement(new Attribute("Humedad"));
        }else if(indice==1){
            fv.addElement(new Attribute("Velocidad viento"));
        }
        //Declarar dataset
        Instances datasetResultado = new Instances("Dataset",fv,dataset.size());
        
        for(int x=0;x<dataset.size();x++){
       
            Instance i = new DenseInstance(2);
       
            i.setValue((Attribute) fv.elementAt(0), dataset.get(x).value(0));
       
            i.setValue((Attribute) fv.elementAt(1), dataset.get(x).value(indice+1));
        
            datasetResultado.add(i);
        }
       
        datasetResultado.setClassIndex(1);
      
        return datasetResultado;
    }
    
    public ArrayList regresionLineal(int indice) throws IOException, Exception{
      
        String ruta = "armenia22.csv";
     
        String s="+";
   
        Instances dataset;
     
        dataset = obtenerDataset(ruta,indice);

        LinearRegression lr = new LinearRegression();

        lr.buildClassifier(dataset);

        double[] c = lr.coefficients();
       
        if(c[2]<0){
            s="";
        }
    
        String e = "y="+c[0]+"x"+s+c[2];
      
        ArrayList<Object>rl = new ArrayList<>();
        rl.add(e);
        rl.add(c[0]);
        rl.add(c[2]);
        return rl;
    }
}


package tests.grafos;

import java.util.Spliterator;

import grafos.dinamicas.Grafo;
import lineales.dinamicas.Lista;

public class TestGrafo {
  public static void main(String[] args) {
        Grafo g1=new Grafo();
        g1.insertarVertice("PI3006");
        g1.insertarVertice("MO3008");
        g1.insertarVertice("VA3007");
        g1.insertarVertice("MA3003");
        g1.insertarVertice("TE3005");
        g1.insertarVertice("CU3002");
        g1.insertarVertice("SO3004");
        g1.insertarVertice("NE3001");
        g1.insertarVertice("RI3009");
        g1.insertarVertice("FU3010");
        g1.insertarArco("PI3006", "MA3003", 310);
        g1.insertarArco("MA3003", "VA3007", 290);
        g1.insertarArco("MA3003", "CU3002", 210);
        g1.insertarArco("CU3002", "NE3001", 230);
        g1.insertarArco("NE3001", "PI3006", 300);
        g1.insertarArco("NE3001", "RI3009", 355);
        g1.insertarArco("RI3009", "FU3010", 305);
        g1.insertarArco("FU3010", "SO3004", 280);
        g1.insertarArco("SO3004", "TE3005", 300);
        g1.insertarArco("MO3008", "TE3005", 280);
        g1.insertarArco("TE3005", "MA3003", 370);
        System.out.println(g1.toString());
        System.out.println("existe vertice: "+g1.existeVertice("NE3001"));
        System.out.println("existe vertice: "+g1.existeVertice(19));
        System.out.println("existe arco: "+g1.existeArco("FU3010", "SO3004"));
        System.out.println("existe arco: "+g1.existeArco("CU3002", "MA3003"));
        //g1.vaciar();
        System.out.println(g1.vacio());
        Grafo g2=g1.clone();
        System.out.println(g2.toString());
        Lista camino = g1.encontrarCaminoEtiquetaMinima("MA3003", "TE3005");
        System.out.println("Camino etiqueta minima de MA3003 a TE3005: " + camino.toString());
        camino = g1.encontrarCaminoEtiquetaMinima("NE3001", "MA3003");
        System.out.println("Camino etiqueta minima de NE3001 a MA3003: " + camino.toString());
        camino = g1.caminoMasCorto("NE3001", "VA3007");
        System.out.println("Camino con menos vertices de NE3001 a VA3007: " + camino.toString());
        camino = g1.caminoMasCorto("PI3006", "TE3005");
        System.out.println("Camino con menos vertices de PI3006 a TE3005: " + camino.toString());
        g1.eliminarArco("FU3010", "SO3004");
        g1.eliminarArco("VA3007", "MO3008");
        g1.eliminarArco("VA3008", "MO3009");
        System.out.println(g1.toString());
        g1.eliminarVertice("NE3001");
        g1.eliminarVertice("MA2018");
        System.out.println(g1.toString());
    }
}

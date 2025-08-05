package grafos;

import lineales.dinamicas.Lista;

public class TestGrafo {
  public static void main(String[] args) {
        Grafo g1=new Grafo();
        g1.insertarVertice('A');
        g1.insertarVertice('B');
        g1.insertarVertice('C');
        g1.insertarVertice('E');
        g1.insertarVertice('D');
        g1.eliminarArco('E', 'B');
        g1.insertarArco('A', 'B', 3);
        g1.insertarArco('A', 'C', 2);
        g1.insertarArco('A', 'E', 4);
        g1.insertarArco('E', 'D', 6);
        g1.insertarArco('C', 'E', 9);
        g1.insertarArco('B', 'E', 8);
        System.out.println(g1.toString());
        System.out.println("existe vertice: "+g1.existeVertice('D'));
        System.out.println("existe vertice: "+g1.existeVertice(19));
        System.out.println("existe arco: "+g1.existeArco('E', 'B'));
        System.out.println("existe arco: "+g1.existeArco('A', 'R'));
        //g1.vaciar();
        System.out.println(g1.vacio());
        Grafo g2=g1.clone();
        System.out.println(g2.toString());
        Lista camino = g1.caminoMasCorto('A', 'E');
        System.out.println("Camino más corto de A a E: " + camino.toString());
    }
}

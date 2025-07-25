package propiasDelDominio;

import java.util.Scanner;
import java.util.HashMap;
import conjuntistas.dinamicas.ArbolAVL;
import grafos.dinamicas.Grafo;

public class TransporteDeAgua {
  private ArbolAVL arbolCiudades;
  private Grafo grafoTuberias;
  private HashMap mapeoTuberias;

  public TransporteDeAgua(){
    this.arbolCiudades=new ArbolAVL();
    this.grafoTuberias=new Grafo();
    this.mapeoTuberias=new HashMap();
  }

  public ArbolAVL getArbolCiudades(){
    return this.arbolCiudades;
  }

  public Grafo getGrafoTuberias(){
    return this.grafoTuberias;
  }

  public void setArbolCiudades(ArbolAVL otro){
    this.arbolCiudades=otro;
  }

  public void setGrafoTuberias(Grafo otro){
    this.grafoTuberias=otro;
  }
  
}
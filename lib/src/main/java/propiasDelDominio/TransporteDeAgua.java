package propiasDelDominio;

import java.util.Scanner;
import java.util.HashMap;
import propositoEspecifico.ArbolAVLDicc;
import grafos.dinamicas.Grafo;

public class TransporteDeAgua {
  private ArbolAVLDicc arbolCiudades;
  private Grafo grafoTuberias;
  private HashMap mapeoTuberias;

  public TransporteDeAgua(){
    this.arbolCiudades=new ArbolAVLDicc();
    this.grafoTuberias=new Grafo();
    this.mapeoTuberias=new HashMap();
  }

  public ArbolAVLDicc getArbolCiudades(){
    return this.arbolCiudades;
  }

  public Grafo getGrafoTuberias(){
    return this.grafoTuberias;
  }

  public void setArbolCiudades(ArbolAVLDicc otro){
    this.arbolCiudades=otro;
  }

  public void setGrafoTuberias(Grafo otro){
    this.grafoTuberias=otro;
  }
  
}
package propiasDelDominio;

import java.util.HashMap;
import conjuntistas.ArbolAVL;
import grafos.Grafo;

public class TransporteDeAgua {
  private ArbolAVL arbolCiudades;
  private Grafo grafoTuberias;
  private HashMap mapeoTuberias;

  public TransporteDeAgua(){
    this.arbolCiudades=null;
    this.grafoTuberias=null;
    this.mapeoTuberias=null;
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

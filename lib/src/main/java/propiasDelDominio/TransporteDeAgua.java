package propiasDelDominio;

import java.util.Scanner;
import java.util.HashMap;
import propositoEspecifico.ArbolAVLDicc;
import grafos.dinamicas.Grafo;
import propiasDelDominio.Ciudad;
import java.util.Iterator;

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
  
  public void bajaCiudad() {
    Scanner sc = new Scanner(System.in);
    String nombreIngresado;
    Ciudad ciudadADarDeBaja;
    boolean existe;
    boolean continuar;

    do {
      System.out.print("Ingrese el nombre de la ciudad a dar de baja: ");
      nombreIngresado = sc.nextLine();
      existe = this.arbolCiudades.existeClave(nombreIngresado);
      if (!existe) {
        System.out.print("La Ciudad ingresada no existe. ¿Desea volver a intentar? (S/n): ");
        switch(sc.nextLine().toUpperCase()) {
          case "": case "S":
            continuar = true;
            break;
          default:
            continuar = false;
            break;
        }
      } else {
        continuar = false;
        bajaCiudadAux(nombreIngresado);
      }
    } while (continuar);
  }
  private void bajaCiudadAux(String nombreCiudad) {
    Ciudad laCiudad = (Ciudad) arbolCiudades.obtenerInformacion(nombreCiudad);
    String nomenclatura = laCiudad.getNomenclatura();
    Iterator<DominioTuberia> iteradorClaves;
    DominioTuberia claveActual;
    // Primero eliminamos la ciudad del AVL
    arbolCiudades.eliminar(nombreCiudad);

    // Luego debemos eliminar todas las tuberias que tengan a esa ciudad como origen o destino
    // Primero nos encargamos de eliminarlas en el Grafo
    grafoTuberias.eliminarVertice(nomenclatura);

    // Despues, eliminamos todos los mapeos en el HashMap que tengan a esa Ciudad
    // Para eso, iteramos todos los DominioTuberia, verificamos si la ciudad está presente
    // Y de ahi, eliminamos todos los en los que esto sea cierto
    iteradorClaves = this.mapeoTuberias.keySet().iterator();
    while (iteradorClaves.hasNext()) {
      claveActual = iteradorClaves.next();
      if (claveActual.tieneCiudad(nomenclatura)) {
        this.mapeoTuberias.remove(claveActual);
      }
    }
  }
}
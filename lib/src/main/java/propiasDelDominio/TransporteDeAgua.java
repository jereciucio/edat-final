package propiasDelDominio;

import java.util.Scanner;
import java.util.HashMap;
import propositoEspecifico.ArbolAVLDicc;
import grafos.dinamicas.Grafo;
import propiasDelDominio.Ciudad;
import java.util.Iterator;
import lineales.dinamicas.Pila;

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
  public boolean altaCiudad() {
    Scanner sc = new Scanner(System.in);
    boolean continuar = true;
    boolean exito = false;
    do {
      System.out.print("Ingrese el nombre de la ciudad: ");
      String nombreCiudad = sc.nextLine().trim();
      if (nombreCiudad.length() < 2) {
        System.out.println("El nombre debe tener al menos 2 caracteres.");
      } else {
        System.out.print("Ingrese la nomenclatura: ");
        String nomenclaturaCiudad = sc.nextLine().trim().toUpperCase();
        if (!Ciudad.nomenclaturaValida(nombreCiudad, nomenclaturaCiudad)) {
          System.out.println(
              "Nomenclatura inválida. Debe comenzar con las dos primeras letras del nombre y un número entre 3000 y 4000.");
        } else {
          System.out.print("Ingrese la superficie: ");
          double superficieCiudad = sc.nextDouble();
          if (superficieCiudad < 0) {
            System.out.println("Valor no válido. Debe ser un número positivo.");
          } else {
            System.out.print("Ingrese el consumo per cápita: ");
            double consumoCiudad = sc.nextDouble();
            if (consumoCiudad < 0) {
              System.out.println("Valor no válido. Debe ser un número positivo.");
            } else {
              double consumo = sc.nextDouble();
              sc.nextLine();
              ArbolAVLDicc calendario = new ArbolAVLDicc();
              exito = registrarCiudad(nombreCiudad, nomenclaturaCiudad, superficieCiudad, consumo, calendario);
              if (exito) {
                System.out.println("Ciudad cargada correctamente.");
              } else {
                System.out.println("La ciudad no se ha cargado (puede que ya exista el nombre o la nomenclatura).");
              }
              continuar = false;
              break;
            }
          }
        }
      }
      System.out.print("¿Desea volver a intentar? (S/n): ");
      String opcion = sc.nextLine().toUpperCase();
      switch (opcion) {
        case "S":
          continuar = true;
          break;
        default:
          continuar = false;
          break;
      }
    } while (continuar);
    return exito;
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
    Pila clavesAEliminar = new Pila();
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
        clavesAEliminar.apilar(claveActual);
      }
    }
    while (!clavesAEliminar.esVacia()) {
      claveActual = (DominioTuberia) clavesAEliminar.obtenerTope();
      clavesAEliminar.desapilar();
      this.mapeoTuberias.remove(claveActual);
    }
  }
}
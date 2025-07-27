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

  public TransporteDeAgua() {
    this.arbolCiudades = new ArbolAVLDicc();
    this.grafoTuberias = new Grafo();
    this.mapeoTuberias = new HashMap();
  }

  public ArbolAVLDicc getArbolCiudades() {
    return this.arbolCiudades;
  }

  public Grafo getGrafoTuberias() {
    return this.grafoTuberias;
  }

  public void setArbolCiudades(ArbolAVLDicc otro) {
    this.arbolCiudades = otro;
  }

  public void setGrafoTuberias(Grafo otro) {
    this.grafoTuberias = otro;
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

  private boolean registrarCiudad(String nombre, String nomenclatura, double superficie, double consumo,
      ArbolAVLDicc calendario) {
    boolean registrado = true;
    Ciudad nuevaCiudad = new Ciudad(nombre, nomenclatura, calendario, superficie, consumo);
    if (arbolCiudades.existeClave(nombre)) {
      System.out.println("Ya existe una ciudad con ese nombre.");
      registrado = false;
    } else if (grafoTuberias.existeVertice(nomenclatura)) {
      System.out.println("Ya existe una ciudad con esa nomenclatura.");
      registrado = false;
    } else {
      arbolCiudades.insertar(nombre, nuevaCiudad);
      grafoTuberias.insertarVertice(nomenclatura);
      mapeoTuberias.put(nomenclatura, nuevaCiudad);
    }
    return registrado;
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
        switch (sc.nextLine().toUpperCase()) {
          case "":
          case "S":
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

    // Luego debemos eliminar todas las tuberias que tengan a esa ciudad como origen
    // o destino
    // Primero nos encargamos de eliminarlas en el Grafo
    grafoTuberias.eliminarVertice(nomenclatura);

    // Despues, eliminamos todos los mapeos en el HashMap que tengan a esa Ciudad
    // Para eso, iteramos todos los DominioTuberia, verificamos si la ciudad está
    // presente
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

  public void bajaTuberia() {
    Scanner sc = new Scanner(System.in);
    String ciudadOrigen, ciudadDestino;
    boolean existe, continuar;
    do {
      System.out.print("Ingrese la nomenclatura de la ciudad origen: ");
      ciudadOrigen = sc.nextLine().trim().toUpperCase();
      System.out.print("Ingrese la nomenclatura de la ciudad destino: ");
      ciudadDestino = sc.nextLine().trim().toUpperCase();
      existe = grafoTuberias.existeVertice(ciudadOrigen) && grafoTuberias.existeVertice(ciudadDestino)
          && grafoTuberias.existeArco(ciudadOrigen, ciudadDestino);
      if (!existe) {
        System.out.print("La tubería especificada no existe. ¿Desea volver a intentar? (S/n): ");
        switch (sc.nextLine().trim().toUpperCase()) {
          case "":
          case "S":
            continuar = true;
            break;
          default:
            continuar = false;
            break;
        }
      } else {
        continuar = false;
        bajaTuberiaAux(ciudadOrigen, ciudadDestino);
        System.out.println("Tubería eliminada exitosamente.");
      }
    } while (continuar);
  }

  private void bajaTuberiaAux(String ciudadOrigen, String ciudadDestino) {
    // Primeramente eliminamos el arco pasanado las ciudades
    grafoTuberias.eliminarArco(ciudadOrigen, ciudadDestino);
    // Removemos la tuberia del hashMap
    DominioTuberia clave = new DominioTuberia(ciudadOrigen, ciudadDestino);
    mapeoTuberias.remove(clave);
  }

  public void modificarTuberia() {
    Scanner sc = new Scanner(System.in);
    String nombreCiudadOrigen, nombreCiudadDestino;
    boolean existe, continuar;
    do {
      System.out.println("Ingrese el nombre de la ciudad de origen:");
      nombreCiudadOrigen = sc.nextLine();
      System.out.println("Ingrese el nombre de la ciudad de destino:");
      nombreCiudadDestino = sc.nextLine();
      existe = this.arbolCiudades.existeClave(nombreCiudadOrigen) &&
          this.arbolCiudades.existeClave(nombreCiudadDestino);
      if (!existe) {
        System.out.println("Alguna ciudad ingresada no existe. ¿Desea volver a intentar? (S/n)");
        switch (sc.nextLine().toUpperCase()) {
          case "":
          case "S":
            continuar = true;
            break;
          default:
            continuar = false;
            break;
        }
      } else {
        continuar = false;
        cambiarTuberia(nombreCiudadOrigen, nombreCiudadDestino);
        System.out.println("Tubería actualizada correctamente con ciudad de origen: " + nombreCiudadOrigen
            + " y ciudad de destino: " + nombreCiudadDestino);
      }
    } while (continuar);
  }

  private void cambiarTuberia(String nombreCiudadOrigen, String nombreCiudadDestino) {
    double caudalMax, caudalMin, diametro;
    String estado;
    Scanner sc = new Scanner(System.in);
    System.out.println("Ingrese el diametro de la tubería:");
    diametro = sc.nextInt();
    System.out.println("Ingrese el estado de la tubería (ACTIVO, EN REPARACIÓN, EN DISEÑO, INACTIVO):");
    estado = sc.nextLine();
    System.out.println("Ingrese el caudal minimo de la tubería:");
    caudalMin = sc.nextDouble();
    System.out.println("Ingrese el caudal máximo de la tubería:");
    caudalMax = sc.nextDouble();
    sc.close();
    DatosTuberia datosTuberia = new DatosTuberia(nombreCiudadOrigen, nombreCiudadDestino, caudalMin, caudalMax,
        diametro, estado);
    Ciudad ciudadOrigen = (Ciudad) this.arbolCiudades.obtenerInformacion(nombreCiudadOrigen);
    Ciudad ciudadDestino = (Ciudad) this.arbolCiudades.obtenerInformacion(nombreCiudadDestino);
    String claveTuberia = ciudadOrigen.getNomenclatura() + "-" + ciudadDestino.getNomenclatura();
    this.mapeoTuberias.put(claveTuberia, datosTuberia);
    boolean existeTuberia = this.grafoTuberias.existeArco(ciudadOrigen.getNomenclatura(),
        ciudadDestino.getNomenclatura());
    if (!existeTuberia) {
      this.grafoTuberias.insertarArco(ciudadOrigen.getNomenclatura(), ciudadDestino.getNomenclatura(), caudalMax);
    } else {
      this.grafoTuberias.obtenerArco(ciudadOrigen.getNomenclatura(), ciudadDestino.getNomenclatura())
          .setEtiqueta(caudalMax);
    }
  }

}
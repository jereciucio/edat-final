package propiasDelDominio;

import conjuntistas.ArbolHeap;
import grafos.dinamicas.Grafo;
import grafos.dinamicas.NodoAdy;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import lineales.dinamicas.Lista;
import lineales.dinamicas.Pila;
import propositoEspecifico.ArbolAVLDicc;

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

  public void menuTransporteDeAgua(){
    Scanner sc = new Scanner(System.in);
    boolean continuar = true;
    while (continuar) {
      System.out.println("=== Menú de Transporte de Agua ===");
      System.out.println("Seleccione una opción:");
      System.out.println("(1) Alta de ciudad");
      System.out.println("(2) Baja de ciudad");
      System.out.println("(3) Modificar ciudad");
      System.out.println("(4) Alta de tubería");
      System.out.println("(5) Baja de tubería");
      System.out.println("(6) Modificar tubería");
      System.out.println("(7) Alta de habitantes");
      System.out.println("(8) Cantidad de habitantes y volumen de agua consumido");
      System.out.println("(9) Listar ciudades por rango de nombre de ciudades y volumen de agua consumido");
      System.out.println("(10) Obtener el camino que llegue de A a B tal que el caudal pleno del camino completo sea el mínimo entre los caminos posibles");
      System.out.println("(11) Obtener el camino que llegue de A a B pasando por la mínima cantidad de ciudades");
      System.out.println("(12) Listar ciudades por consumo anual");
      System.out.println("(13) Mostrar sistema de Transporte de agua");
      System.out.println("(0) Salir del menu");

      int opcion = sc.nextInt();
      sc.nextLine(); // Consumir el salto de línea pendiente

      switch (opcion) {
        case 1:
          altaCiudad();
          break;
        case 2:
          bajaCiudad();
          break;
        case 3:
          modificarCiudad();
          break;
        case 4:
          altaTuberia();
          break;
        case 5:
          bajaTuberia();
          break;
        case 6:
          modificarTuberias();
          break;
        case 7:
          altaHabitantes();
          break;
        case 8:
          cantHabitantesYVolAgua(0, 0);
          break;
        case 9:
          listarCiudadesPorConsumoAnual();
          break;
        case 10:
          /// Obtener el camino que llegue de A a B tal que el caudal pleno del camino completo sea el mínimo entre los caminos posibles
          break;
        case 11:
          /// Obtener el camino que llegue de A a B pasando por la mínima cantidad de ciudades
          break;
        case 12:
          listarCiudadesPorConsumoAnual();
          break;
        case 13:
          mostrarSistema();
          break;
        case 0:
          continuar = false;
          break;
        default:
          System.out.println("Opción no válida. Inténtelo nuevamente.");
      }
    }
    sc.close();
  }

  public boolean altaCiudad() {
    Scanner sc = new Scanner(System.in);
    boolean continuar = true;
    boolean exito = false;
    do {
      System.out.print("Ingrese el nombre de la ciudad: ");
      String nombreCiudad = sc.nextLine().trim().toUpperCase();
      if (nombreCiudad.length() < 2) {
        System.out.println("El nombre debe tener al menos 2 caracteres.");
      } else {
        System.out.print("Ingrese la nomenclatura: ");
        String nomenclaturaCiudad = sc.nextLine().trim().toUpperCase();
        if (!Ciudad.nomenclaturaValida(nombreCiudad, nomenclaturaCiudad)) {
          System.out.println(
              "Nomenclatura inválida. Debe comenzar con las dos primeras letras del nombre y un"
                  + " número entre 3000 y 4000.");
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
              sc.nextLine();
              ArbolAVLDicc calendario = new ArbolAVLDicc();
              exito =
                  registrarCiudad(
                      nombreCiudad,
                      nomenclaturaCiudad,
                      superficieCiudad,
                      consumoCiudad,
                      calendario);
            }
          }
        }
      }
      if (!exito) {
        System.out.println(
            "La ciudad no se ha cargado (puede que ya exista el nombre o la nomenclatura).");
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
      } else {
        System.out.println("Ciudad cargada correctamente.");
        continuar = false;
      }
    } while (continuar);
    return exito;
  }

  private boolean registrarCiudad(
      String nombre,
      String nomenclatura,
      double superficie,
      double consumo,
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
    boolean existe;
    boolean continuar;

    do {
      System.out.print("Ingrese el nombre de la ciudad a dar de baja: ");
      nombreIngresado = sc.nextLine().trim().toUpperCase();
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

  public void modificarCiudad() {
    Scanner sc = new Scanner(System.in);
    String nombreIngresado;
    boolean existe;
    boolean continuar;
    int datoAModificar;
    double superficieNueva;
    double consumoNuevo;

    // Primero solicitamos un nombre de ciudad válido.
    do {
      System.out.print("Ingrese el nombre de la ciudad a modificar: ");
      nombreIngresado = sc.nextLine().trim().toUpperCase();
      existe = this.arbolCiudades.existeClave(nombreIngresado);
      if (!existe) {
        System.out.print("La Ciudad ingresada no existe. ¿Desea volver a intentar? (s/N): ");
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
      }
    } while (continuar);

    // Solicitar el dato a modificar.
    if (existe) {
      System.out.println("Seleccione el dato a modificar:");
      System.out.println("(1) Superficie");
      System.out.println("(2) Consumo de agua promedio en m³ por habitante");
      System.out.print("Su respuesta: ");
      datoAModificar = sc.nextInt();
      sc.nextLine(); // Consumir el \n
      switch (datoAModificar) {
        case 1:
          System.out.print("Ingrese la nueva superficie: ");
          superficieNueva = sc.nextDouble();
          if (superficieNueva < 0) {
            System.out.println("La superficie ingresada es inválida, inténtelo nuevamente.");
          } else {
            modificarSuperficieCiudad(superficieNueva, nombreIngresado);
          }
          break;
        case 2:
          System.out.print("Ingrese el nuevo consumo de agua promedio por habitante: ");
          consumoNuevo = sc.nextDouble();
          if (consumoNuevo < 0) {
            System.out.println("El consumo ingresado es inválido, inténtelo nuevamente.");
          } else {
            modificarConsumoCiudad(consumoNuevo, nombreIngresado);
          }
          break;
      }
    }
    sc.close();
  }

  private void modificarSuperficieCiudad(double superficie, String nombreCiudad) {
    Ciudad laCiudad = (Ciudad) arbolCiudades.obtenerInformacion(nombreCiudad);
    laCiudad.setSuperficie(superficie);
  }

  private void modificarConsumoCiudad(double consumo, String nombreCiudad) {
    Ciudad laCiudad = (Ciudad) arbolCiudades.obtenerInformacion(nombreCiudad);
    laCiudad.setConsumo(consumo);
  }

  public void altaTuberia() {
    Scanner sc = new Scanner(System.in);
    String nombreCiudadOrigen;
    String nombreCiudadDestino;
    Ciudad ciudadOrigen;
    Ciudad ciudadDestino;
    double caudalMinimo;
    double caudalMaximo;
    double diametro;
    int estado;

    System.out.print("Ingrese el nombre de la ciudad de origen de la nueva tubería: ");
    nombreCiudadOrigen = sc.nextLine().trim().toUpperCase();
    ciudadOrigen = (Ciudad) arbolCiudades.obtenerInformacion(nombreCiudadOrigen);
    if (ciudadOrigen != null) {
      System.out.print("Ingrese el nombre de la ciudad de destino: ");
      nombreCiudadDestino = sc.nextLine().trim().toUpperCase();
      ciudadDestino = (Ciudad) arbolCiudades.obtenerInformacion(nombreCiudadDestino);
      if (ciudadDestino != null) {
        if (!grafoTuberias.existeArco(
            ciudadOrigen.getNomenclatura(), ciudadDestino.getNomenclatura())) {
          System.out.print("Ingrese el caudal mínimo en m² por hora: ");
          caudalMinimo = sc.nextDouble();
          sc.nextLine(); // Consumir \n
          if (caudalMinimo >= 0) {
            System.out.print("Ingrese el caudal máximo en m² por hora: ");
            caudalMaximo = sc.nextDouble();
            sc.nextLine(); // Consumir \n
            if (caudalMaximo >= 0) {
              System.out.print("Ingrese el diámetro en milímetros: ");
              diametro = sc.nextDouble();
              if (diametro >= 0) {
                System.out.println("[1] Activo [2] En reparacion [3] En diseño [4] Inactivo");
                System.out.print(
                    "Seleccione de la lista anterior el estado actual de la tubería: ");
                estado = sc.nextInt();
                sc.nextLine(); // Consumir \n
                if (estado >= 1 && estado <= 4) {
                  altaTuberiaAux(
                      ciudadOrigen.getNomenclatura(),
                      ciudadDestino.getNomenclatura(),
                      caudalMinimo,
                      caudalMaximo,
                      diametro,
                      estado);
                } else {
                  System.out.println("El estado seleccionado es inválido. Inténtelo nuevamente.");
                }
              } else {
                System.out.println("El diámetro no puede ser negativo. Inténtelo nuevamente.");
              }
            } else {
              System.out.println("El caudal no puede ser negativo. Inténtelo nuevamente.");
            }
          } else {
            System.out.println("El caudal no puede ser negativo. Inténtelo nuevamente.");
          }
        } else {
          System.out.println(
              "Ya existe una tubería entre las dos ciudades ingresadas. Inténtelo nuevamente.");
        }
      } else {
        System.out.println("La ciudad ingresada no existe. Inténtelo nuevamente.");
      }
    } else {
      System.out.println("La ciudad ingresada no existe. Inténtelo nuevamente.");
    }
  }

  public void altaTuberiaAux(
      String nomenclaturaCiudadOrigen,
      String nomenclaturaCiudadDestino,
      double caudalMin,
      double caudalMax,
      double diametro,
      int estado) {
    String textoEstado = "";
    switch (estado) {
      case 1:
        textoEstado = "ACTIVO";
        break;
      case 2:
        textoEstado = "EN REPARACION";
        break;
      case 3:
        textoEstado = "EN DISEÑO";
        break;
      case 4:
        textoEstado = "INACTIVO";
        break;
    }
    DominioTuberia dominio =
        new DominioTuberia(nomenclaturaCiudadOrigen, nomenclaturaCiudadDestino);
    DatosTuberia datos =
        new DatosTuberia(
            nomenclaturaCiudadOrigen,
            nomenclaturaCiudadDestino,
            caudalMin,
            caudalMax,
            diametro,
            textoEstado);

    mapeoTuberias.put(dominio, datos);
    grafoTuberias.insertarArco(nomenclaturaCiudadOrigen, nomenclaturaCiudadDestino, caudalMax);
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
      existe =
          grafoTuberias.existeVertice(ciudadOrigen)
              && grafoTuberias.existeVertice(ciudadDestino)
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

  public void modificarTuberias(){
    Scanner sc = new Scanner(System.in);
    String ciudadOrigen, ciudadDestino;
    boolean existe, continuar;
    do {
      System.out.println("Ingrese el nombre de la ciudad de origen:");
      ciudadOrigen = sc.nextLine().trim().toUpperCase();
      System.out.println("Ingrese el nombre de la ciudad de destino:");
      ciudadDestino = sc.nextLine().trim().toUpperCase();
      existe =this.arbolCiudades.existeClave(ciudadOrigen)
              && this.arbolCiudades.existeClave(ciudadDestino);
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
      }
    } while (continuar);
    continuar = true;
    do{
      System.out.println("Seleccione el dato a modificar:");
      System.out.println("(1) Caudal mínimo");
      System.out.println("(2) Caudal máximo");
      System.out.println("(3) Diámetro");
      System.out.println("(4) Estado");
      System.out.print("Su respuesta: ");
      int opcion = sc.nextInt();
      sc.nextLine(); 
      switch (opcion) {
        case 1:
          modificarCaudalMinimo(ciudadOrigen, ciudadDestino);
          continuar = false;
          break;
        case 2:
          modificarCaudalMaximo(ciudadOrigen, ciudadDestino);
          continuar = false;
          break;
        case 3:
          modificarDiametro(ciudadOrigen, ciudadDestino);
          continuar = false;
          break;
        case 4:
          modificarEstado(ciudadOrigen, ciudadDestino);
          continuar = false;
          break;
        default:
          System.out.println("Opción inválida. Inténtelo nuevamente.");
      }
    } while (continuar);
    sc.close();
  }

  private void modificarCaudalMinimo(String ciudadOrigen, String ciudadDestino) {
    Scanner sc = new Scanner(System.in);
    double nuevoCaudalMinimo;
    boolean valido, continuar;
    do {
      System.out.println("Ingrese el nuevo caudal minimo:");
      nuevoCaudalMinimo = sc.nextDouble();
      valido=nuevoCaudalMinimo>0;
      if (!valido) {
        System.out.println("El caudal mínimo debe ser mayor que 0. ¿Desea volver a intentar? (S/n)");
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
        sc.close();
        continuar = false;
        Ciudad ciudadO = (Ciudad) this.arbolCiudades.obtenerInformacion(ciudadOrigen);
        Ciudad ciudadD = (Ciudad) this.arbolCiudades.obtenerInformacion(ciudadDestino);
        DominioTuberia clave = new DominioTuberia(ciudadO.getNomenclatura(), ciudadD.getNomenclatura());
        DatosTuberia datosTuberia = (DatosTuberia) this.mapeoTuberias.get(clave);
        if (datosTuberia != null) {
          datosTuberia.setCaudalMinimo(nuevoCaudalMinimo);
          System.out.println("Caudal mínimo actualizado correctamente.");
        } else {
          System.out.println("No se encontró la tubería especificada.");
        }
      }
    } while (continuar);
  }

  private void modificarCaudalMaximo(String ciudadOrigen, String ciudadDestino){
    Scanner sc = new Scanner(System.in);
    double nuevoCaudalMaximo;
    boolean valido, continuar;
    do {
      System.out.println("Ingrese el nuevo caudal máximo:");
      nuevoCaudalMaximo = sc.nextDouble();
      valido=nuevoCaudalMaximo>0;
      if (!valido) {
        System.out.println("El caudal máximo debe ser mayor que 0. ¿Desea volver a intentar? (S/n)");
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
        sc.close();
        continuar = false;
        Ciudad ciudadO = (Ciudad) this.arbolCiudades.obtenerInformacion(ciudadOrigen);
        Ciudad ciudadD = (Ciudad) this.arbolCiudades.obtenerInformacion(ciudadDestino);
        NodoAdy caudalEnGrafo=this.grafoTuberias.obtenerArco(ciudadO.getNomenclatura(), ciudadD.getNomenclatura());
        caudalEnGrafo.setEtiqueta(nuevoCaudalMaximo);
        DominioTuberia clave = new DominioTuberia(ciudadO.getNomenclatura(), ciudadD.getNomenclatura());
        DatosTuberia datosTuberia = (DatosTuberia) this.mapeoTuberias.get(clave);
        if (datosTuberia != null) {
          datosTuberia.setCaudalMaximo(nuevoCaudalMaximo);
          System.out.println("Caudal máximo actualizado correctamente.");
        } else {
          System.out.println("No se encontró la tubería especificada.");
        }
      }
    } while (continuar);
  }

  private void modificarDiametro(String ciudadOrigen, String ciudadDestino){
    Scanner sc = new Scanner(System.in);
    double nuevoDiametro;
    boolean valido, continuar;
    do {
      System.out.println("Ingrese el nuevo diámetro:");
      nuevoDiametro = sc.nextDouble();
      valido=nuevoDiametro>0;
      if (!valido) {
        System.out.println("El diámetro debe ser mayor que 0. ¿Desea volver a intentar? (S/n)");
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
        sc.close();
        continuar = false;
        Ciudad ciudadO = (Ciudad) this.arbolCiudades.obtenerInformacion(ciudadOrigen);
        Ciudad ciudadD = (Ciudad) this.arbolCiudades.obtenerInformacion(ciudadDestino);
        DominioTuberia clave = new DominioTuberia(ciudadO.getNomenclatura(), ciudadD.getNomenclatura());
        DatosTuberia datosTuberia = (DatosTuberia) this.mapeoTuberias.get(clave);
        if (datosTuberia != null) {
          datosTuberia.setDiametro(nuevoDiametro);
          System.out.println("Diámetro actualizado correctamente.");
        } else {
          System.out.println("No se encontró la tubería especificada.");
        }
      }
    } while (continuar);
  }

  private void modificarEstado(String ciudadOrigen, String ciudadDestino){
    Scanner sc = new Scanner(System.in);
    String nuevoEstado;
    boolean valido, continuar;
    do {
      System.out.println("Ingrese el nuevo estado:");
      nuevoEstado = sc.nextLine().toUpperCase();
      valido=nuevoEstado.equals("ACTIVO")
          || nuevoEstado.equals("EN REPARACION")
          || nuevoEstado.equals("EN DISEÑO")
          || nuevoEstado.equals("INACTIVO");
      if (!valido) {
        System.out.println("El estado ingresado no es válido. ¿Desea volver a intentar? (S/n)");
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
        sc.close();
        continuar = false;
        Ciudad ciudadO = (Ciudad) this.arbolCiudades.obtenerInformacion(ciudadOrigen);
        Ciudad ciudadD = (Ciudad) this.arbolCiudades.obtenerInformacion(ciudadDestino);
        DominioTuberia clave = new DominioTuberia(ciudadO.getNomenclatura(), ciudadD.getNomenclatura());
        DatosTuberia datosTuberia = (DatosTuberia) this.mapeoTuberias.get(clave);
        if (datosTuberia != null) {
          datosTuberia.setEstado(nuevoEstado);
          System.out.println("Estado actualizado correctamente.");
        } else {
          System.out.println("No se encontró la tubería especificada.");
        }
      }
    } while (continuar);
  }

  public void altaHabitantes() {
    Scanner sc = new Scanner(System.in);
    String nombreCiudad;
    boolean existe, continuar;
    do {
      System.out.println("Ingrese el nombre de la ciudad:");
      nombreCiudad = sc.nextLine().trim().toUpperCase();
      existe = this.arbolCiudades.existeClave(nombreCiudad);
      if (!existe) {
        System.out.println("La ciudad ingresada no existe. ¿Desea volver a intentar? (S/n)");
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
        sc.close();
        continuar = false;
        modificarHabitantes(nombreCiudad);
        System.out.println("Habitantes actualizados correctamente en la ciudad: " + nombreCiudad);
      }
    } while (continuar);
  }

  private void modificarHabitantes(String nombreCiudad) {
    Scanner sc = new Scanner(System.in); 
    int habitantes, anio, mes;
    System.out.println("Ingrese la cantidad de habitantes:");
    habitantes = sc.nextInt();
    System.out.println("Ingrese el numero del anio:");
    anio = sc.nextInt();
    sc.nextLine(); // Consumir el salto de línea pendiente
    System.out.println("Ingrese el numero del mes:");
    mes = sc.nextInt();
    sc.close();
    Ciudad unaCiudad = (Ciudad) this.arbolCiudades.obtenerInformacion(nombreCiudad);
    Anio unAnio = (Anio) unaCiudad.getCalendarioHabitantes().obtenerInformacion(anio);
    if (unAnio == null) {
      unAnio = new Anio(anio);
      unAnio.setValor(mes, habitantes);
      unaCiudad.getCalendarioHabitantes().insertar(anio, unAnio);
    } else {
      unaCiudad.setHabitantes(habitantes, mes, anio);
    }
  }

  public void cantHabitantesYVolAgua(int mes, int anio) {
    Scanner sc = new Scanner(System.in);
    boolean continuar = true;
    do {
      boolean exito = true;
      System.out.println("Ingrese el nombre de la ciudad");
      String nombreCiudad = sc.nextLine().toUpperCase();
      if (!this.arbolCiudades.existeClave(nombreCiudad)) {
        System.out.println("La ciudad ingresada no existe");
        exito = false;
      } else if (mes < 1 || mes > 12) {
        System.out.println("Mes invalido");
        exito = false;
      } else {
        Ciudad ciudadBuscada = (Ciudad) arbolCiudades.obtenerInformacion(nombreCiudad);
        double volumenAgua = ciudadBuscada.getConsumoMensual(mes, anio);
        if (volumenAgua == -1) {
          exito = false;
          System.out.println("El año no esta registrado");
        } else {
          int habitantes = ciudadBuscada.getHabitantes(mes, anio);
          System.out.println("Ciudad: " + nombreCiudad);
          System.out.println("Año: " + anio + " y Mes: " + mes);
          System.out.println("Cantidad de Habitantes: " + habitantes);
          System.out.println("Volumen de agua: " + volumenAgua);
          continuar = false;
        }
      }
      if (!exito) {
        switch (sc.nextLine().toUpperCase()) {
          case "":
          case "S":
            continuar = true;
            break;
          default:
            continuar = false;
            break;
        }
      }
    } while (continuar);
  }

  public void obtenerCiudadRango() {
    Scanner sc = new Scanner(System.in);
    String minNombre, maxNombre;
    boolean existe, continuar;
    do {
      System.out.println("Ingrese el nombre de la ciudad minima:");
      minNombre = sc.nextLine().trim().toUpperCase();
      System.out.println("Ingrese el nombre de la ciudad maxima:");
      maxNombre = sc.nextLine().trim().toUpperCase();
      existe =
          this.arbolCiudades.existeClave(minNombre) && this.arbolCiudades.existeClave(maxNombre);
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
        sc.close();
        continuar = false;
        listarCiudadesRango(minNombre, maxNombre);
      }
    } while (continuar);
  }

  private void listarCiudadesRango(String minNombre, String maxNombre) {
    Scanner sc = new Scanner(System.in);
    double minVolumen, maxVolumen;
    int anio, mes;
    Lista ciudadesRango = new Lista();
    boolean valido, continuar;
    do {
      System.out.println("Ingrese el volumen de agua minimo:");
      minVolumen = sc.nextDouble();
      System.out.println("Ingrese el volumen de agua maximo:");
      maxVolumen = sc.nextDouble();
      sc.nextLine();
      valido = minVolumen >= 0 && maxVolumen >= minVolumen;
      if (!valido) {
        System.out.println("Algún volumen ingresado no es valido. ¿Desea volver a intentar? (S/n)");
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
        do {
          System.out.println("Ingrese el número del año:");
          anio = sc.nextInt();
          System.out.println("Ingrese el número del mes:");
          mes = sc.nextInt();
          valido = mes >= 1 && mes <= 12 && anio > 0;
          if (!valido) {
            System.out.println("Algún valor ingresado en año o mes no es valido. ¿Desea volver a intentar? (S/n)");
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
            sc.close();
            continuar = false;
            ciudadesRango = this.arbolCiudades.listarPorRango(minNombre, maxNombre);
            if (ciudadesRango.esVacia()) {
              System.out.println("No hay ciudades en el rango especificado.");
            } else {
              while (!ciudadesRango.esVacia()) {
                Ciudad unaCiudad = (Ciudad) ciudadesRango.recuperar(1);
                double volumenCiudad = unaCiudad.getConsumoMensual(mes, anio);
                if (volumenCiudad >= minVolumen && volumenCiudad <= maxVolumen) {
                  System.out.println("\n" + unaCiudad.toString());
                }
                ciudadesRango.eliminar(1);
              }
            }
          }
        } while (continuar);
      }
    } while (continuar);
  }  

  public void listarCiudadesPorConsumoAnual() {
    Scanner sc = new Scanner(System.in);
    System.out.println("Ingrese el año: ");
    int anio = sc.nextInt();
    Lista listaCiudades = arbolCiudades.listarDatos();
    ArbolHeap heap = new ArbolHeap();
    boolean incompleto = false;
    while (!listaCiudades.esVacia() && !incompleto) {
      Ciudad ciudad = (Ciudad) listaCiudades.recuperar(1);
      double consumoAnual = ciudad.getConsumoTotal(anio);
      if (consumoAnual == -1) {
        incompleto = true;
      } else {
        NodoConsumo nodo = new NodoConsumo(ciudad, consumoAnual);
        heap.insertar(nodo);
        listaCiudades.eliminar(1);
      }
    }
    if (incompleto) {
      System.out.println("No todas las ciudades tienen datos cargados para el año ingresado.");
    } else {
      System.out.println("Listado de ciudades por consumo anual:");
      while (!heap.esVacio()) {
        NodoConsumo nodo = (NodoConsumo) heap.recuperarCima();
        System.out.println(nodo.toString());
        heap.eliminarCima();
      }
    }
  }

  public void mostrarSistema() {
    System.out.println("=== Estado del Sistema de Transporte de Agua ===\n");
    System.out.println("Árbol AVL de Ciudades:");
    System.out.println(arbolCiudades.toString());
    System.out.println();
    System.out.println("Grafo de Tuberías:");
    System.out.println(grafoTuberias.toString());
    System.out.println();

    System.out.println("Mapeo de Tuberías (HashMap):");
    if (mapeoTuberias.isEmpty()) {
        System.out.println("El mapeo está vacío.");
    } else {
        for (Object clave : mapeoTuberias.keySet()) {
            System.out.println("Clave: " + clave.toString());
            System.out.println("Valor: " + mapeoTuberias.get(clave).toString());
            System.out.println("---");
        }
    }
    System.out.println("===============================================");
  }
  public void precargarCiudades(String pathArchivo) {
    try (BufferedReader br = new BufferedReader(new FileReader(pathArchivo))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            linea = linea.trim();
            if (!linea.isEmpty()) {
                StringTokenizer separadorTokens = new StringTokenizer(linea, ";");
                if (separadorTokens.countTokens() >= 4) {
                    String nombre = separadorTokens.nextToken().trim().toUpperCase();
                    String nomenclatura = separadorTokens.nextToken().trim().toUpperCase();
                    double superficie = Double.parseDouble(separadorTokens.nextToken().trim());
                    double consumo = Double.parseDouble(separadorTokens.nextToken().trim());
                    ArbolAVLDicc calendario = new ArbolAVLDicc(); // Árbol vacío
                    registrarCiudad(nombre, nomenclatura, superficie, consumo, calendario);
                }
            }
        }
        System.out.println("Ciudades precargadas correctamente.");
    } catch (IOException e) {
        System.out.println("Error al leer el archivo de ciudades: " + e.getMessage());
    }
}
public void precargarTuberias(String pathArchivo) {
    try (BufferedReader lector = new BufferedReader(new FileReader(pathArchivo))) {
        String linea;
        while ((linea = lector.readLine()) != null) {
            linea = linea.trim();
            if (!linea.isEmpty()) {
                StringTokenizer separador = new StringTokenizer(linea, ";");
                if (separador.countTokens() >= 5) {
                    String campoCiudades = separador.nextToken().trim().toUpperCase();
                    StringTokenizer separadorCiudades = new StringTokenizer(campoCiudades, "-");
                    if (separadorCiudades.countTokens() == 2) {
                        String origen = separadorCiudades.nextToken();
                        String destino = separadorCiudades.nextToken();
                        double caudalMin = Double.parseDouble(separador.nextToken().trim());
                        double caudalMax = Double.parseDouble(separador.nextToken().trim());
                        double diametro = Double.parseDouble(separador.nextToken().trim());
                        String estado = separador.nextToken().trim().toUpperCase();

                        DominioTuberia dominio = new DominioTuberia(origen, destino);
                        DatosTuberia datos = new DatosTuberia(origen, destino, caudalMin, caudalMax, diametro, estado);
                        this.mapeoTuberias.put(dominio, datos);
                        this.grafoTuberias.insertarArco(origen, destino, caudalMax);
                    }
                }
            }
        }
        System.out.println("Tuberías precargadas correctamente.");
    } catch (IOException e) {
        System.out.println("Error al leer el archivo de tuberías: " + e.getMessage());
    }
}
}

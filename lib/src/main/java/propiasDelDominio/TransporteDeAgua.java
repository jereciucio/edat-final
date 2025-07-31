package propiasDelDominio;

import conjuntistas.ArbolHeap;
import grafos.dinamicas.Grafo;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
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

  private void modificarCiudad() {
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

  public void modificarTuberia() {
    Scanner sc = new Scanner(System.in);
    String nombreCiudadOrigen, nombreCiudadDestino;
    boolean existe, continuar;
    do {
      System.out.println("Ingrese el nombre de la ciudad de origen:");
      nombreCiudadOrigen = sc.nextLine().trim().toUpperCase();
      System.out.println("Ingrese el nombre de la ciudad de destino:");
      nombreCiudadDestino = sc.nextLine().trim().toUpperCase();
      existe =
          this.arbolCiudades.existeClave(nombreCiudadOrigen)
              && this.arbolCiudades.existeClave(nombreCiudadDestino);
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
        System.out.println(
            "Tubería actualizada correctamente con ciudad de origen: "
                + nombreCiudadOrigen
                + " y ciudad de destino: "
                + nombreCiudadDestino);
      }
    } while (continuar);
  }

  private void cambiarTuberia(String nombreCiudadOrigen, String nombreCiudadDestino) {
    double caudalMax, caudalMin, diametro;
    String estado;
    Scanner sc = new Scanner(System.in);
    System.out.println("Ingrese el diametro de la tubería:");
    diametro = sc.nextInt();
    System.out.println(
        "Ingrese el estado de la tubería (ACTIVO, EN REPARACIÓN, EN DISEÑO, INACTIVO):");
    estado = sc.nextLine().trim().toUpperCase();
    System.out.println("Ingrese el caudal minimo de la tubería:");
    caudalMin = sc.nextDouble();
    System.out.println("Ingrese el caudal máximo de la tubería:");
    caudalMax = sc.nextDouble();
    sc.close();
    DatosTuberia datosTuberia =
        new DatosTuberia(
            nombreCiudadOrigen, nombreCiudadDestino, caudalMin, caudalMax, diametro, estado);
    Ciudad ciudadOrigen = (Ciudad) this.arbolCiudades.obtenerInformacion(nombreCiudadOrigen);
    Ciudad ciudadDestino = (Ciudad) this.arbolCiudades.obtenerInformacion(nombreCiudadDestino);
    String claveTuberia = ciudadOrigen.getNomenclatura() + "-" + ciudadDestino.getNomenclatura();
    this.mapeoTuberias.put(claveTuberia, datosTuberia);
    boolean existeTuberia =
        this.grafoTuberias.existeArco(
            ciudadOrigen.getNomenclatura(), ciudadDestino.getNomenclatura());
    if (!existeTuberia) {
      this.grafoTuberias.insertarArco(
          ciudadOrigen.getNomenclatura(), ciudadDestino.getNomenclatura(), caudalMax);
    } else {
      this.grafoTuberias
          .obtenerArco(ciudadOrigen.getNomenclatura(), ciudadDestino.getNomenclatura())
          .setEtiqueta(caudalMax);
    }
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
        double volumenAgua = ciudadBuscada.getConsumo(mes, anio);
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
    System.out.println("Ingrese el volumen de agua minimo:");
    minVolumen = sc.nextDouble();
    System.out.println("Ingrese el volumen de agua maximo:");
    maxVolumen = sc.nextDouble();
    sc.nextLine(); // Consumir el salto de línea pendiente
    System.out.println("Ingrese el numero del anio:");
    anio = sc.nextInt();
    System.out.println("Ingrese el numero del mes:");
    mes = sc.nextInt();
    sc.close();
    ciudadesRango = this.arbolCiudades.listarPorRango(minNombre, maxNombre);
    if (ciudadesRango.esVacia()) {
      System.out.println("No hay ciudades en el rango especificado.");
    } else {
      while (!ciudadesRango.esVacia()) {
        Ciudad unaCiudad = (Ciudad) ciudadesRango.recuperar(1);
        double volumenCiudad = unaCiudad.getHabitantes(mes, anio) * unaCiudad.getConsumo(mes, anio);
        if (volumenCiudad >= minVolumen && volumenCiudad <= maxVolumen) {
          System.out.println("\n" + unaCiudad.toString());
        }
        ciudadesRango.eliminar(1);
      }
    }
  }

  public void listarCiudadesPorConsumoAnual() {
    Scanner sc = new Scanner(System.in);
    System.out.println("Ingrese el año: ");
    int anio = sc.nextInt();
    Lista listaCiudades = arbolCiudades.listarDatos();
    ArbolHeap heap = new ArbolHeap();
    boolean incompleto = false;
    int i = 1;
    while (i <= listaCiudades.longitud() && !incompleto) {
      Ciudad ciudad = (Ciudad) listaCiudades.recuperar(i);
      double consumoAnual = ciudad.getConsumoTotal(anio);
      if (consumoAnual == -1) {
        incompleto = true;
      } else {
        NodoConsumo nodo = new NodoConsumo(ciudad, consumoAnual);
        heap.insertar(nodo);
        i++;
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
}

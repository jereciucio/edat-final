package propositoEspecifico;

import lineales.dinamicas.Lista;

public class ArbolAVLDicc {
  private NodoAVLDicc raiz;

  public ArbolAVLDicc() {
    this.raiz = null;
  }

  public boolean existeClave(Comparable laClave) {
    return existeClaveAux(this.raiz, laClave);
  }

  private boolean existeClaveAux(NodoAVLDicc nodoActual, Comparable laClave) {
    int comparacion = 0;
    boolean exito = false;
    if (nodoActual != null) {
      comparacion = laClave.compareTo(nodoActual.getClave());

      if (comparacion < 0) {
        exito = existeClaveAux(nodoActual.getIzquierdo(), laClave);
      } else if (comparacion > 0) {
        exito = existeClaveAux(nodoActual.getDerecho(), laClave);
      } else {
        exito = true;
      }
    }
    return exito;
  }

  private NodoAVLDicc rotacionSimpleIzquierda(NodoAVLDicc raiz) {
    NodoAVLDicc hijo = raiz.getDerecho();
    NodoAVLDicc temp = hijo.getIzquierdo();
    hijo.setIzquierdo(raiz);
    raiz.setDerecho(temp);

    raiz.recalcularAltura();
    hijo.recalcularAltura();

    return hijo;
  }

  private NodoAVLDicc rotacionSimpleDerecha(NodoAVLDicc raiz) {
    NodoAVLDicc hijo = raiz.getIzquierdo();
    NodoAVLDicc temp = hijo.getDerecho();
    hijo.setDerecho(raiz);
    raiz.setIzquierdo(temp);

    raiz.recalcularAltura();
    hijo.recalcularAltura();

    return hijo;
  }

  private NodoAVLDicc rotacionDobleIzquierdaDerecha(NodoAVLDicc raiz) {
    raiz.setIzquierdo(rotacionSimpleIzquierda(raiz.getIzquierdo()));
    return rotacionSimpleDerecha(raiz);
  }

  private NodoAVLDicc rotacionDobleDerechaIzquierda(NodoAVLDicc raiz) {
    raiz.setDerecho(rotacionSimpleDerecha(raiz.getDerecho()));
    return rotacionSimpleIzquierda(raiz);
  }

  public boolean insertar(Comparable laClave, Object elDato) {
    boolean[] exito = new boolean[1];
    exito[0] = false;
    this.raiz = insertarAux(this.raiz, laClave, elDato, exito);
    return exito[0];
  }

  private NodoAVLDicc insertarAux(
      NodoAVLDicc nodoActual, Comparable laClave, Object elDato, boolean[] exito) {
    int comparacion = 0;

    // Buscar el lugar donde insertar el elemento hasta llegar a un nulo o encontrar el elemento.
    if (nodoActual != null) {
      comparacion = laClave.compareTo(nodoActual.getClave());
      if (comparacion < 0) {
        nodoActual.setIzquierdo(insertarAux(nodoActual.getIzquierdo(), laClave, elDato, exito));
      } else if (comparacion > 0) {
        nodoActual.setDerecho(insertarAux(nodoActual.getDerecho(), laClave, elDato, exito));
      }
    } else {
      nodoActual = new NodoAVLDicc(laClave, elDato, null, null);
      exito[0] = true;
    }

    // Recalcular altura y rebalancear.
    if (nodoActual != null) {
      nodoActual.recalcularAltura();
      nodoActual = rebalancear(nodoActual);
    }
    return nodoActual;
  }

  private NodoAVLDicc rebalancear(NodoAVLDicc nodo) {
    int balance = balance(nodo);
    int balanceHijo = 0;

    if (balance >= 2) { // Desbalance hacia la izquierda
      balanceHijo = balance(nodo.getIzquierdo());

      if (balanceHijo >= 0) {
        // El hijo tiene el mismo o nulo desbalance
        nodo = rotacionSimpleDerecha(nodo);
      } else {
        // El hijo tiene el desbalance contrario
        nodo = rotacionDobleIzquierdaDerecha(nodo);
      }
    } else if (balance <= -2) { // Desbalance hacia la derecha
      balanceHijo = balance(nodo.getDerecho());

      if (balanceHijo <= 0) {
        // El hijo tiene el mismo o nulo desbalance
        nodo = rotacionSimpleIzquierda(nodo);
      } else {
        // El hijo tiene el desbalance contrario
        nodo = rotacionDobleDerechaIzquierda(nodo);
      }
    }

    return nodo;
  }

  private int balance(NodoAVLDicc nodo) {
    NodoAVLDicc izquierdo = null; 
    NodoAVLDicc derecho = null;
    if (nodo != null) {
      izquierdo = nodo.getIzquierdo();
      derecho = nodo.getDerecho();
    }
    int alturaIzquierdo = -1;
    int alturaDerecho = -1;
    if (izquierdo != null) {
      alturaIzquierdo = izquierdo.getAltura();
    }
    if (derecho != null) {
      alturaDerecho = derecho.getAltura();
    }
    return alturaIzquierdo - alturaDerecho;
  }

  public boolean eliminar(Comparable laClave) {
    boolean[] exito = {false};
    this.raiz = eliminarAux(this.raiz, laClave, exito);
    return exito[0];
  }

  private NodoAVLDicc eliminarAux(NodoAVLDicc nodo, Comparable laClave, boolean[] exito) {
    NodoAVLDicc resultado = nodo;
    if (nodo != null) {
      int comparacion = laClave.compareTo(nodo.getClave());

      if (comparacion < 0) {
        nodo.setIzquierdo(eliminarAux(nodo.getIzquierdo(), laClave, exito));
      } else if (comparacion > 0) {
        nodo.setDerecho(eliminarAux(nodo.getDerecho(), laClave, exito));
      } else {
        // Nodo encontrado
        exito[0] = true;

        // Caso 1: sin hijos
        if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
          resultado = null;
        }
        // Caso 2.1: Un solo hijo (Solo izquierdo)
        else if (nodo.getDerecho() == null) {
          resultado = nodo.getIzquierdo();
        }
        // Caso 2.2: Un solo hijo (Solo derecho)
        else if (nodo.getIzquierdo() == null) {
          resultado = nodo.getDerecho();
        }
        // Caso 3: Dos hijos
        else {
          NodoAVLDicc sucesor = encontrarMinimo(nodo.getDerecho());
          nodo.setClave(sucesor.getClave());
          nodo.setDato(sucesor.getDato());
          nodo.setDerecho(eliminarAux(nodo.getDerecho(), sucesor.getClave(), exito));
          resultado = nodo;
        }
      }
    }
    if (resultado != null) {
      resultado.recalcularAltura();
      resultado = rebalancear(resultado);
    }
    return resultado;
  }

  private NodoAVLDicc encontrarMinimo(NodoAVLDicc nodo) {
    NodoAVLDicc resultado = null;
    if (nodo.getIzquierdo() == null) {
      resultado = nodo; // Caso base: Ya es el menor de su subarbol
    } else {
      resultado = encontrarMinimo(nodo.getIzquierdo()); // Seguir buscando por la izquierda
    }
    return resultado;
  }

  public Object obtenerInformacion(Comparable laClave) {
    return obtenerInformacionAux(this.raiz, laClave);
  }

  private Object obtenerInformacionAux(NodoAVLDicc nodoActual, Comparable laClave) {
    Object resultado = null;
    int comparacion;
    // Caso base, si no se encuentra el nodo de la clave buscada (es decir, que el nodoActual sería
    // nulo), se devolverá como resultado un `null`.
    if (nodoActual != null) {
      comparacion = laClave.compareTo(nodoActual.getClave());
      if (comparacion < 0) {
        resultado = obtenerInformacionAux(nodoActual.getIzquierdo(), laClave);
      } else if (comparacion > 0) {
        resultado = obtenerInformacionAux(nodoActual.getDerecho(), laClave);
      } else {
        resultado = nodoActual.getDato();
      }
    }
    return resultado;
  }

  public Lista listarClaves() {
    Lista listaClaves = new Lista();
    int[] pos = {1};
    listarClavesAux(this.raiz, listaClaves, pos);
    return listaClaves;
  }

  private void listarClavesAux(NodoAVLDicc nodoActual, Lista listaClaves, int[] pos) {
    // Recorrido in-orden
    if (nodoActual != null) {
      // Visitar sub-árbol izquierdo
      listarClavesAux(nodoActual.getIzquierdo(), listaClaves, pos);
      // Añadir el elemento actual
      listaClaves.insertar(nodoActual.getClave(), pos[0]);
      pos[0]++;
      // Visitar el sub-árbol derecho
      listarClavesAux(nodoActual.getDerecho(), listaClaves, pos);
    }
  }

  public Lista listarDatos() {
    Lista listaDatos = new Lista();
    int[] pos = {1};
    listarDatosAux(this.raiz, listaDatos, pos);
    return listaDatos;
  }

  private void listarDatosAux(NodoAVLDicc nodoActual, Lista listaDatos, int[] pos) {
    // Recorrido in-orden
    if (nodoActual != null) {
      // Visitar sub-árbol izquierdo
      listarDatosAux(nodoActual.getIzquierdo(), listaDatos, pos);
      // Añadir el elemento actual
      listaDatos.insertar(nodoActual.getDato(), pos[0]);
      pos[0]++;
      // Visitar el sub-árbol derecho
      listarDatosAux(nodoActual.getDerecho(), listaDatos, pos);
    }
  }

  public Lista listarPorRango(Comparable claveInferior, Comparable claveSuperior){
    Lista rango = new Lista();
    listarPorRangoAux(this.raiz, claveInferior, claveSuperior, rango);
    return rango;
  }

  private void listarPorRangoAux(NodoAVLDicc n, Comparable claveInferior, Comparable claveSuperior, Lista rango){
    if(n!=null){
      if(n.getClave().compareTo(claveInferior)>0){
        listarPorRangoAux(n.getIzquierdo(), claveInferior, claveSuperior, rango);
      }
      if(n.getClave().compareTo(claveInferior) >= 0 && n.getClave().compareTo(claveSuperior) <= 0){
        rango.insertar(n.getDato(), rango.longitud() + 1);
      }
      if(n.getClave().compareTo(claveSuperior)<0){
        listarPorRangoAux(n.getDerecho(), claveInferior, claveSuperior, rango);
      }
    }
  }

  public String toString() {
    String representación = "";
    if (!this.esVacio()) {
      representación = "Raiz: " + this.raiz.getDato()+ "\n" + toStringAux(this.raiz);
    }
    return representación;
  }

  private String toStringAux(NodoAVLDicc nodo) {
    String representacion = "";
    final String VACIO = "-";
    representacion += nodo.getDato() +" (alt: " + nodo.getAltura() + ") ";
    if (nodo.getIzquierdo() != null) {
      representacion += "\n    HI: " + nodo.getIzquierdo().getDato().toString() + " ";
    } else {
      representacion += "\n    HI " + VACIO + " ";
    }
    if (nodo.getDerecho() != null) {
      representacion += "\n    HD: " + nodo.getDerecho().getDato().toString();
    } else {
      representacion += "\n    HD " + VACIO;
    }
    // Recorrido en preorden
    if (nodo.getIzquierdo() != null) {
      representacion += "\n" + toStringAux(nodo.getIzquierdo());
    }
    if (nodo.getDerecho() != null) {
      representacion += "\n" + toStringAux(nodo.getDerecho());
    }
    return representacion;
  }

  public boolean esVacio() {
    return this.raiz == null;
  }
}
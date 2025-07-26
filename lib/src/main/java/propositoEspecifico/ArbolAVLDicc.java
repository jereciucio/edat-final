package propositoEspecifico;

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
    } else { // Desbalance hacia la derecha
      balanceHijo = balance(nodo.getDerecho());

      if (balanceHijo <= 0) {
        // El hijo tiene el mismo o nulo desbalance
        nodo = rotacionSimpleDerecha(nodo);
      } else {
        // El hijo tiene el desbalance contrario
        nodo = rotacionDobleIzquierdaDerecha(nodo);
      }
    }

    return nodo;
  }

  private int balance(NodoAVLDicc nodo) {
    NodoAVLDicc izquierdo = nodo.getIzquierdo();
    NodoAVLDicc derecho = nodo.getDerecho();
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

  public boolean eliminar (Comparable laClave) {
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
}

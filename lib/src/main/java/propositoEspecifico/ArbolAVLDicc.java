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
}

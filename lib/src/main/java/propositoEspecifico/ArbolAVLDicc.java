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
}
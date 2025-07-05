package grafos.dinamicas;

public class NodoVert {
  private Object elem;
  private NodoVert sigVertice;
  private NodoAdy primerAdy;

  public NodoVert(Object unElemento, NodoVert unSigVertice, NodoAdy unPrimerAdy) {
    this.elem = unElemento;
    this.sigVertice = unSigVertice;
    this.primerAdy = unPrimerAdy;
  }

  public Object getElem() {
    return this.elem;
  }

  public void setElem(Object unElem) {
    this.elem = unElem;
  }

  public NodoVert getSigVertice() {
    return this.sigVertice;
  }

  public void setSigVertice(NodoVert unSigVertice) {
    this.sigVertice = unSigVertice;
  }
}
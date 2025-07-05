package grafos.dinamicas;

public class NodoVert {
  private Object elem;
  private NodoVert sigVertice;
  private NodoAdy primerAdy;

  public NodoVert(Object unElemento, NodoVert unSigVertice, NodoAdy unPrimerAdy) {
    this.elem = unElemento;
    this.sigVertice = null;
    this.primerAdy = null;
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

  public NodoAdy getPrimerAdy() {
    return this.primerAdy;
  }

  public void setPrimerAdy(NodoAdy unPrimerAdy) { 
    this.primerAdy = unPrimerAdy;
  }
}
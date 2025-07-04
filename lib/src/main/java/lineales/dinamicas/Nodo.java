package lineales.dinamicas;
public class Nodo {

  private Object elemento;
  private Nodo enlace;

  public Nodo(Object unElemento, Nodo unEnlace) {
    this.elemento = unElemento;
    this.enlace = unEnlace;
  }

  public Object getElem() {
    return this.elemento;
  }
  public Nodo getEnlace() {
    return this.enlace;
  }

  public void setElemento(Object unObjeto) {
    this.elemento = unObjeto;
  }
  public void setEnlace(Nodo unNodo) {
    this.enlace = unNodo;
  }
}
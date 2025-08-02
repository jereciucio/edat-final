package grafos.dinamicas;
public class NodoAdy {
  NodoVert vertice;
  NodoAdy sigAdyacente;
  Comparable etiqueta;
  
  public NodoAdy(NodoVert elVertice, Comparable laEtiqueta) {
    // Recibe como primer parámetro el vértice hacia el que se dirige el arco.
    this.vertice = elVertice;
    this.sigAdyacente = null;
    this.etiqueta = laEtiqueta;
  }

  public NodoVert getVertice() {
    return this.vertice;
  }

  public NodoAdy getSigAdy() {
    return this.sigAdyacente;
  } 
  public Comparable getEtiqueta() {
    return this.etiqueta;
  }

  public void setVertice(NodoVert elVertice) {
    this.vertice = elVertice;
  }

  public void setSigAdyacente(NodoAdy elSiguiente) {
    this.sigAdyacente = elSiguiente;
  }

  public void setEtiqueta (Comparable laEtiqueta) {
    this.etiqueta = laEtiqueta;
  }
}
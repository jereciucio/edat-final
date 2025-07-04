package grafos.dinamicas
public class NodoAdy {
  NodoVert vertice;
  NodoAdy sigAdyacente;
  Object etiqueta;
  
  public NodoAdy(NodoVert elVertice, Object laEtiqueta) {
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
  public Object getEtiqueta() {
    return this.etiqueta;
  }
}
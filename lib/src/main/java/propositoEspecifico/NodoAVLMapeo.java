package propositoEspecifico;
public class NodoAVLMapeo {
  private Comparable clave;
  private Object dato;
  private int altura;
  private NodoAVLMapeo izquierdo;
  private NodoAVLMapeo derecho;

  public NodoAVLMapeo(Comparable laClave, Object elDato, NodoAVLMapeo elIzquierdo, NodoAVLMapeo elDerecho) {
    this.clave = laClave;
    this.dato = elDato;
    this.izquierdo = elIzquierdo;
    this.derecho = elDerecho;
    this.altura = 0;
  }

  public NodoAVLMapeo(Comparable laClave, Object elDato) {
    this.clave = laClave;
    this.dato = elDato;
    this.izquierdo = null;
    this.derecho = null;
    this.altura = 0;
  }

  public Comparable getClave() {
    return this.clave;
  }

  public Object getDato() {
    return this.dato;
  }

  public NodoAVLMapeo getDerecho() {
    return this.derecho;
  }

  public NodoAVLMapeo getIzquierdo() {
    return this.izquierdo;
  }

  public int getAltura() {
    return this.altura;
  }

  public void setClave(Comparable laClave) {
    this.clave = laClave;
  }

  public void setDato(Object elDato) {
    this.dato = elDato;
  }

  public void setDerecho(NodoAVLMapeo elDerecho) {
    this.derecho = elDerecho;
  }

  public void setIzquierdo(NodoAVLMapeo elIzquierdo) {
    this.izquierdo = elIzquierdo;
  }

  public void recalcularAltura() {
    int alturaMax = -1;
    if (this.izquierdo != null) {
      alturaMax = this.izquierdo.altura;
    }
    if (this.derecho != null) {
      int alturaDerecho = this.derecho.altura;
      // Si el derecho es mayor, lo pone como altura maxima
      alturaMax = ( alturaDerecho > alturaMax ) ? alturaDerecho : alturaMax;
    }
    this.altura = alturaMax + 1;
  }
}
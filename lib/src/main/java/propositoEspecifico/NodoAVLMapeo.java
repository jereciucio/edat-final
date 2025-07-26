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

}
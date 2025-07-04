package conjuntistas.dinamicas;
public class NodoAVL {
  private Comparable elem;
  private int altura;
  private NodoAVL izquierdo;
  private NodoAVL derecho;

  public NodoAVL(Comparable elElem, NodoAVL elIzquierdo, NodoAVL elDerecho) {
    this.elem = elElem;
    this.izquierdo = elIzquierdo;
    this.derecho = elDerecho;
    this.altura = 0;
  }

  public Comparable getElem() {
    return this.elem;
  }
  public void setElem(Comparable elElem) {
    this.elem = elElem;
  }
  public NodoAVL getIzquierdo() {
    return this.izquierdo;
  }
  public void setIzquierdo(NodoAVL elIzquierdo) {
    this.izquierdo = elIzquierdo;
  }
  public NodoAVL getDerecho() {
    return this.derecho;
  }
  public void setDerecho(NodoAVL elDerecho) {
    this.derecho = elDerecho;
  }
  public int getAltura() {
    return this.altura;
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
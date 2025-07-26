/*****************AUTORES****************
 * - Jeremías Martinez, Legajo FAI-4695
 * - Juan Ignacio Muñoz, Legajo FAI-4484
 * - Julián Forquera, Legajo FAI-5187
 * - Xavier Mora, Legajo FAI-5338
 */
package lineales.dinamicas;

public class Pila {
  private Nodo tope;

  public Pila() {
    this.tope = null;
  }

  public boolean apilar(Object nuevoElem) {
    Nodo nuevoNodo = new Nodo(nuevoElem, this.tope);
    // Actualiza el tope siempre al nuevo nodo
    this.tope = nuevoNodo;
    // Como la pila es dinamica el puntero siempre apuntara al nuevo tope

    // Retornamos siempre true, ya que 
    // al ser dinamica, la pila nunca 
    // está llena.
    return true;
  }

  public boolean desapilar() {
    boolean exito = false;
    if (this.tope != null) {
      exito = true;
      this.tope = this.tope.getEnlace();
      // Si la pila no esta vacia actualiza el tope
    }
    return exito;
  }

  public boolean esVacia() {
    return (this.tope == null);
    // Evalua si la pila esta vacia
  }

  public void vaciar() {
    this.tope = null;
    // Actualiza el tope y el garbage collector elimina los nodos
  }

  public String toString() {
    String txt = "";
    if (this.tope == null) {
      txt = "[]";
    } else {
      Nodo nodoActual = this.tope;
      txt = "[";
      while (nodoActual != null) {
        txt += nodoActual.getElem().toString();
        nodoActual = nodoActual.getEnlace();
        if (nodoActual != null) {
          txt += ",";
        }
      }
      txt += "]";
    }
    return txt;
  }

  // Version Recursiva
  public Pila clone() {
    Pila nuevaPila = new Pila();
    nuevaPila.tope = cloneRecursivo(this.tope);
    // Llama al metodo privado para clonar nodos
    return nuevaPila;
  }

  private Nodo cloneRecursivo(Nodo nodoActual) {
    Nodo clonNodoActual = null;
    // Se inicializa en null para tomar en cuenta Caso Base donde nodoActual es null
    // Si el nodo no es null, se clona junto al enlace
    if (nodoActual != null) {
      // Llamada recursiva
      Nodo clonNodoSiguiente = cloneRecursivo(nodoActual.getEnlace());
      // Se crea un nuevo nodo con el mismo elemento y el enlace al clon del siguiente nodo
      clonNodoActual = new Nodo(nodoActual.getElem(), clonNodoSiguiente);
    }
    // Se retorna el nodo clonado o null si es caso base
    return clonNodoActual;
  }

  // Clone con implementacion sin recursividad
  //public Pila cloneDos() {
  //  Pila nuevaPila = new Pila();
  //  if (this.tope != null) {
  //    Nodo nodoOriginal = this.tope.getEnlace();
  //    Nodo nodoNuevo = new Nodo(this.tope.getElem(), null);
  //    nuevaPila.tope = nodoNuevo;
  //    while (nodoOriginal != null) {
  //      nodoNuevo.setEnlace(new Nodo(nodoOriginal.getElem(), null));
  //      nodoNuevo = nodoNuevo.getEnlace();
  //      nodoOriginal = nodoOriginal.getEnlace();
  //    }
  //  }
  //  return nuevaPila;
  //}

  public Object obtenerTope() {
    Object elemTope;
    if (this.tope == null) {
      elemTope = null;
      // Si la pila es vacia se devuelve null
    } else {
      elemTope = tope.getElem();
      // La pila posee elementos, devuelve el elemento en tope
    }
    return elemTope;
  }
}
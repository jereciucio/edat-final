package lineales.dinamicas;
import lineales.dinamicas.Nodo;
public class Lista {
  Nodo cabecera;
  int largo;

  public Lista() {
    this.cabecera = null;
    this.largo = 0;
  }

  public boolean insertar(Object elem, int pos) {
    boolean exito = false;
    Nodo anterior;
    Nodo siguiente;
    if (pos > 1 && pos <= largo + 1) {
      anterior = encontrarNodo(pos - 1);
      siguiente = anterior.getEnlace();
      anterior.setEnlace(new Nodo(elem, siguiente));
      this.largo++;
      exito = true;
    } else if (pos == 1) {
      this.cabecera = new Nodo(elem, cabecera);
      this.largo++;
      exito = true;
    }
    return exito;
  }

  public boolean eliminar(int pos) {
    Nodo anterior;
    Nodo actual;
    boolean exito = false;
    if (cabecera != null) {
      if (pos > 1 && pos <=largo) {
        anterior = encontrarNodo(pos-1);
        actual = anterior.getEnlace();
        anterior.setEnlace(actual.getEnlace());
        this.largo--;
        exito = true;
      } else if (pos == 1) {
        this.cabecera = this.cabecera.getEnlace();
        this.largo--;
        exito = true;
      }
    }
    return exito;
  }

  public Object recuperar(int pos) {
    Nodo nodoEncontrado = this.encontrarNodo(pos);
    Object elemento = null;
    if (nodoEncontrado != null) {
      elemento = nodoEncontrado.getElem();
    }
    return elemento;

  }
  public int localizar(Object elem) {
    int i = 1;
    int pos = -1;
    Nodo nodoActual = this.cabecera;
    boolean encontrado = false;
    while (i <= this.largo && !encontrado) {
      if (elem != null) {
        encontrado = elem.equals(nodoActual.getElem());
      } else {
        encontrado = elem == nodoActual.getElem();
      }
      if (encontrado) {
        pos = i;
        encontrado = true;
      }
      nodoActual = nodoActual.getEnlace();
      i++;
    }
    return pos;
  }

  public void vaciar() {
    this.cabecera = null;
    this.largo = 0;
  }

  public boolean esVacia() {
    return this.largo <= 0;
  }

  public Lista clone() {
    Lista nuevaLista = new Lista();
    Nodo nodoActual = this.cabecera;
    Object elemActual;
    int pos = 1;
    if (this.largo >= 1) {
      while (pos <= this.largo) {
        elemActual = nodoActual.getElem();
        nuevaLista.insertar(elemActual, pos);

        nodoActual = nodoActual.getEnlace();
        pos++;
      }
    }
    return nuevaLista;
  }

  public int longitud() {
    return this.largo;
  }

  public Nodo encontrarNodo(int pos) {
    Nodo nodoActual = this.cabecera;
    Nodo nodoEncontrado = null;
    int i;
    if (pos >= 1 && pos <= this.largo) {
      for (i = 1; i <= pos; i++) {
        if (i != pos) {
          nodoActual = nodoActual.getEnlace();
        } else {
          nodoEncontrado = nodoActual;
        }
      } 
    }
    return nodoEncontrado;
  }

  public String toString() {
    String texto = "";
    Nodo nodoActual = this.cabecera;
    Nodo nodoSiguiente;
    if (!this.esVacia()) {
      texto = "[";
      while (nodoActual != null) {
        nodoSiguiente = nodoActual.getEnlace();
        if (nodoSiguiente != null) {
          texto += nodoActual.getElem().toString() + ",";
        } else {
          texto += nodoActual.getElem().toString() + "]";
        }
        nodoActual = nodoSiguiente;
      }
    } else {
      texto = "[]";
    }
    return texto;
  }

  public void invertir() {
    Nodo anterior = null;
    Nodo actual = this.cabecera;
    Nodo siguiente;
    
    while (actual != null) {
      siguiente = actual.getEnlace();
      actual.setEnlace(anterior);
      anterior = actual;
      actual = siguiente;
    }
    this.cabecera = anterior;
  }
}
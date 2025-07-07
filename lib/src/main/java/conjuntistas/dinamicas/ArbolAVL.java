package conjuntistas.dinamicas;

import lineales.dinamicas.Lista;

public class ArbolAVL {
  private NodoAVL raiz;

  public ArbolAVL() {
    this.raiz = null;
  }

  public boolean pertenece(Comparable elem) {
    return perteneceAux(this.raiz, elem);
  }

  private boolean perteneceAux(NodoAVL nodoActual, Comparable elem) {
    int comparacion = 0;
    boolean exito = false;
    if (nodoActual != null) {
      comparacion = elem.compareTo(nodoActual.getElem());

      if (comparacion < 0) {
        exito = perteneceAux(nodoActual.getIzquierdo(), elem);
      } else if (comparacion > 0) {
        exito = perteneceAux(nodoActual.getDerecho(), elem);
      } else {
        exito = true;
      }
    }
    return exito;
  }

  private NodoAVL rotacionSimpleIzquierda(NodoAVL raiz) {
    NodoAVL hijo = raiz.getDerecho();
    NodoAVL temp = hijo.getIzquierdo();
    hijo.setIzquierdo(raiz);
    raiz.setDerecho(temp);

    raiz.recalcularAltura();
    hijo.recalcularAltura();

    return hijo;
  }

  private NodoAVL rotacionSimpleDerecha(NodoAVL raiz) {
    NodoAVL hijo = raiz.getIzquierdo();
    NodoAVL temp = hijo.getDerecho();
    hijo.setDerecho(raiz);
    raiz.setIzquierdo(temp);

    raiz.recalcularAltura();
    hijo.recalcularAltura();

    return hijo;
  }

  private NodoAVL rotacionDobleIzquierdaDerecha(NodoAVL raiz) {
    raiz.setIzquierdo(rotacionSimpleIzquierda(raiz.getIzquierdo()));
    return rotacionSimpleDerecha(raiz);
  }

  private NodoAVL rotacionDobleDerechaIzquierda(NodoAVL raiz) {
    raiz.setDerecho(rotacionSimpleDerecha(raiz.getDerecho()));
    return rotacionSimpleIzquierda(raiz);
  }

  public boolean insertar(Comparable elem) {
    boolean[] exito = new boolean[1];
    exito[0] = false;
    this.raiz = insertarAux(this.raiz, elem, exito);
    return exito[0];
  }

  private NodoAVL insertarAux(NodoAVL nodoActual, Comparable elem, boolean[] exito) {
    int comparacion = 0;
    boolean yaPresente = false;

    // Buscar el lugar donde insertar el elemento hasta llegar a un nulo o encontrar el elemento.
    if (nodoActual != null) {
      comparacion = elem.compareTo(nodoActual.getElem());
      if (comparacion < 0) {
        nodoActual.setIzquierdo(insertarAux(nodoActual.getIzquierdo(), elem, exito));
      } else if (comparacion > 0) {
        nodoActual.setDerecho(insertarAux(nodoActual.getDerecho(), elem, exito));
      } else {
        yaPresente = true;
      }
    } else {
      if (!yaPresente) {
        nodoActual = new NodoAVL(elem, null, null);
      }
      exito[0] = true;
    }

    // Recalcular altura y rebalancear.
    if (nodoActual != null) {
      nodoActual.recalcularAltura();
      nodoActual = rebalancear(nodoActual);
    }
    return nodoActual;
  }

  private NodoAVL rebalancear(NodoAVL nodo) {
    int balance = balance(nodo);
    int balanceHijo = 0;

    if (balance >= 2) {
      balanceHijo = balance(nodo.getIzquierdo());

      // Condicion: que los signos sean iguales
      if (balance * balanceHijo >= 0) {
        nodo = rotacionSimpleDerecha(nodo);
      } else {
        nodo = rotacionDobleIzquierdaDerecha(nodo);
      }
    } else if (balance <= -2) {
      balanceHijo = balance(nodo.getDerecho());

      // Condicion: que los signos sean iguales
      if (balance * balanceHijo <= 0) {
        nodo = rotacionSimpleIzquierda(nodo);
      } else {
        nodo = rotacionDobleDerechaIzquierda(nodo);
      }
    }

    return nodo;
  }

  private int balance(NodoAVL nodo) {
    // Precondicion: nodo != null
    NodoAVL izquierdo = nodo.getIzquierdo();
    NodoAVL derecho = nodo.getDerecho();
    int alturaIzquierdo = -1;
    int alturaDerecho = -1;
    if (izquierdo != null) {
      alturaIzquierdo = izquierdo.getAltura();
    }
    if (derecho != null) {
      alturaDerecho = derecho.getAltura();
    }
    return alturaIzquierdo - alturaDerecho;
  }

  public Lista listar() {
    Lista lista = new Lista();
    int[] pos = {1};
    listarAux(this.raiz, lista, pos);
    return lista;
  }

  public void listarAux(NodoAVL nodoActual, Lista lista, int[] pos) {
    // Debe añadir los elementos mediante un recorrido in-orden;
    if (nodoActual != null) {
      // Visitar sub-arbol izquierdo
      listarAux(nodoActual.getIzquierdo(), lista, pos);
      // Añadir el elemento actual
      lista.insertar(nodoActual.getElem(), pos[0]);
      pos[0]++;
      // Visitar sub-arbol derecho
      listarAux(nodoActual.getDerecho(), lista, pos);
    }
  }

  public Object minimoELem(){
        Object elem=null;
        elem=minimoElemAux(this.raiz);
        return elem;
    }

    private Object minimoElemAux(NodoAVL n){
        Object elem=null;
        if(n!=null){
            if(n.getIzquierdo()==null){
                elem=n.getElem();
            }else{
                elem=minimoElemAux(n.getIzquierdo());
            }
        }
        return elem;
    }

    public Object maximoElem(){
        Object elem=null;
        elem=maximoElemAux(this.raiz);
        return elem;
    }

    private Object maximoElemAux(NodoAVL n){
        Object elem=null;
        if(n!=null){
            if(n.getDerecho()==null){
                elem=n.getElem();
            }else{
                elem=maximoElemAux(n.getDerecho());
            }
        }
        return elem;
    }

    public boolean esVacio(){
        return this.raiz==null;
    }

    public void vaciar(){
        this.raiz=null;
    }

    public ArbolAVL clone(){
        ArbolAVL clon = new ArbolAVL();
        clon.raiz=cloneAux(this.raiz);
        return clon;
    }

    private NodoAVL cloneAux(NodoAVL n){
        NodoAVL nuevo=null;
        if(n!=null){
            nuevo=new NodoAVL(n.getElem(),null,null);
            nuevo.setIzquierdo(cloneAux(n.getIzquierdo()));
            nuevo.setDerecho(cloneAux(n.getDerecho()));
        }
        return nuevo;
    }

  // TODO: Agregar altura al toString()
  public String toString() {
    return toStringRecursivo(raiz);
  }

  public String toStringRecursivo(NodoAVL nodoActual) {
    String texto = "";
    NodoAVL izquierdo;
    NodoAVL derecho;
    if (nodoActual != null) {
      izquierdo = nodoActual.getIzquierdo();
      derecho = nodoActual.getDerecho();

      // Visita del nodoActual
      texto += nodoActual.getElem().toString() + " -> ";

      // Agregar los hijos al string del nodoActual
      if (izquierdo != null) {
        texto += "HI: " + izquierdo.getElem().toString();
      } else {
        texto += "HI: null";
      }
      if (derecho != null) {
        texto += " HD: " + derecho.getElem().toString();
      } else {
        texto += " HD: null";
      }

      // Agregar la altura
      texto += " Altura: " + nodoActual.getAltura();

      // Llamada recursiva a los hijos;
      if (izquierdo != null) {
        texto += "\n" + toStringRecursivo(izquierdo);
      }
      if (derecho != null) {
        texto += "\n" + toStringRecursivo(derecho);
      }
    }
    return texto;
  }
}

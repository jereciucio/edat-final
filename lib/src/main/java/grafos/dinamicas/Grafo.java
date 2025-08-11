package grafos.dinamicas;

import lineales.dinamicas.Lista;
public class Grafo {
  private NodoVert inicio;

  public Grafo(){
    this.inicio = null;
  }

  public boolean insertarVertice(Object vertice) {
    boolean exito = true;
    if (this.inicio == null) {
        this.inicio = new NodoVert(vertice, null, null);
    } else {
        NodoVert aux = this.inicio;
        while (aux.getSigVertice() != null && !aux.getElem().equals(vertice)) {
            aux = aux.getSigVertice();
        }
        if (aux.getElem().equals(vertice)) {
            exito = false;
        } else {
            aux.setSigVertice(new NodoVert(vertice, null, null));
        }
    }
    return exito;
}

  public boolean eliminarVertice(Object vertice){
    boolean exito=false;
    if(this.inicio!=null && this.inicio.getElem().equals(vertice)){
      this.inicio=this.inicio.getSigVertice();
      exito=true;
    }else{
      NodoVert actual=this.inicio;
      NodoVert anterior=null;
      while(actual!=null && !exito){
        if(actual.getElem().equals(vertice)){
          if(anterior!=null){
            anterior.setSigVertice(actual.getSigVertice());
          }
          exito=true;
        }else{
          anterior=actual;
          actual=actual.getSigVertice();
        }
      }
    }
    if(exito){
      NodoVert aux=this.inicio;
      while (aux!=null) {
        NodoAdy actualAdy=aux.getPrimerAdy();
        NodoAdy anteriorAdy=null;
        while(actualAdy!=null){
          if(actualAdy.getVertice().getElem().equals(vertice)){
            if(anteriorAdy==null){
              aux.setPrimerAdy(actualAdy.getSigAdy());
              actualAdy=aux.getPrimerAdy();
            }else{
              anteriorAdy.setSigAdyacente(actualAdy.getSigAdy());
              actualAdy=anteriorAdy.getSigAdy();
            }
          }else{
            anteriorAdy=actualAdy;
            actualAdy=actualAdy.getSigAdy();
          }
        }
        aux=aux.getSigVertice();
      }
    }
    return exito;
  }

  public boolean existeVertice(Object vertice){
    boolean encontrado=false;
    if(this.inicio!=null){
      encontrado=buscarVertice(vertice)!=null;
    }
    return encontrado;
  }

  private NodoVert buscarVertice(Object vertice) {
    NodoVert aux = this.inicio;
    while (aux != null && !aux.getElem().equals(vertice)) {
        aux = aux.getSigVertice();
    }
    return aux;
}

  public boolean insertarArco(Object verSalida, Object verEntrada, Comparable etiqueta){
    boolean exito=false;
    NodoVert nodoSalida=buscarVertice(verSalida);
    NodoVert nodoEntrada=buscarVertice(verEntrada);
    if(nodoSalida!=null && nodoEntrada!=null){
      NodoAdy ady=nodoSalida.getPrimerAdy();
      boolean existe =false;
      while(ady!=null && !existe){
        if(ady.getVertice().equals(nodoEntrada)){
          existe=true;
        }else{
        ady = ady.getSigAdy();
        }
      }
      if(!existe){
        NodoAdy nuevo=new NodoAdy(nodoEntrada, etiqueta);
        nuevo.setSigAdyacente(nodoSalida.getPrimerAdy());
        nodoSalida.setPrimerAdy(nuevo);
        exito=true;
      }
    }
    return exito;
  }

  public boolean eliminarArco(Object verSalida, Object verEntrada){
    boolean exito=false;
    NodoVert nodoSalida=buscarVertice(verSalida);
    if(nodoSalida!=null){
      NodoAdy actualAdy=nodoSalida.getPrimerAdy();
      NodoAdy anteriorAdy=null;
      while(actualAdy!=null && !exito){
        if(actualAdy.getVertice().getElem().equals(verEntrada)){
          exito=true;
          if(anteriorAdy==null){
            nodoSalida.setPrimerAdy(actualAdy.getSigAdy());
          }else{
            anteriorAdy.setSigAdyacente(actualAdy.getSigAdy());
          }
        }else{
          anteriorAdy=actualAdy;
          actualAdy=actualAdy.getSigAdy();
        }
      }
    }
    return exito;
  }

  public boolean existeArco(Object verSalida, Object verEntrada){
    boolean existe=false;
    NodoVert nodoSalida=buscarVertice(verSalida);
    NodoVert nodoEntrada=buscarVertice(verEntrada);
    if(nodoSalida!=null && nodoEntrada!=null){
      NodoAdy ady=nodoSalida.getPrimerAdy();
      while(ady!=null && !existe){
        if(ady.getVertice().equals(nodoEntrada)){
          existe=true;
        }
        ady=ady.getSigAdy();
      }
    }
    return existe;
  }

  public NodoAdy obtenerArco(Object verSalida, Object verEntrada){
    NodoAdy resultado = null;
    boolean existe = false;
    NodoVert nodoSalida=buscarVertice(verSalida);
    NodoVert nodoEntrada=buscarVertice(verEntrada);
    if(nodoSalida!=null && nodoEntrada!=null){
      NodoAdy ady=nodoSalida.getPrimerAdy();
      while(ady!=null && !existe){
        if(ady.getVertice().equals(nodoEntrada)){
          existe=true;
          resultado = ady;
        }
        ady=ady.getSigAdy();
      }
    }
    return resultado;
  }

  public boolean vacio(){
    boolean esVacio=this.inicio==null;
    return esVacio;
  }

  public void vaciar(){
    this.inicio=null;
  }

  public Grafo clone(){
    Grafo copia=new Grafo();
    if(this.inicio!=null){
      NodoVert aux=this.inicio;
      NodoVert nodAnterior=null;
      NodoVert actual;
      actual=new NodoVert(aux.getElem(), null, null);
      copia.inicio=actual;
      while(aux.getSigVertice()!=null){
        aux=aux.getSigVertice();
        nodAnterior=actual;
        actual=new NodoVert(aux.getElem(), null, null);
        nodAnterior.setSigVertice(actual);
      }
      aux=copia.inicio;
      NodoVert aux2=this.inicio;
      while(aux!=null && aux2!=null){
        NodoAdy adyOriginal=aux2.getPrimerAdy();
        NodoAdy ultimo=null;
        while(adyOriginal!=null){
          NodoVert destino=copia.buscarVertice(adyOriginal.getVertice().getElem());
          NodoAdy nuevo=new NodoAdy(destino,adyOriginal.getEtiqueta());
          if(ultimo==null){
            aux.setPrimerAdy(nuevo);
          }else{
            ultimo.setSigAdyacente(nuevo);
          }
          ultimo=nuevo;
          adyOriginal=adyOriginal.getSigAdy();
        }
        aux=aux.getSigVertice();
        aux2=aux2.getSigVertice();
      }
    }
    return copia;
  }
  
  public String toString() {
    String cad = "";
    if (this.inicio == null) {
        cad = "Grafo vacío";
    } else {
        NodoVert actual = this.inicio;
        while (actual != null) {
            cad += actual.getElem() + " ->";
            NodoAdy ady = actual.getPrimerAdy();
            if (ady == null) {
                cad += " [sin adyacencias]";
            } else {
                while (ady != null) {
                    cad += " " + ady.getVertice().getElem();
                    if(ady.getSigAdy() != null) {
                      cad += " (" + ady.getEtiqueta() + "),";
                    }else{
                      cad += " (" + ady.getEtiqueta() + ")";
                    }
                    ady = ady.getSigAdy();
                }
            }
            cad += "\n"; // Salto de línea entre vértices
            actual = actual.getSigVertice();
        }
    }
    return cad;
  }

  public Lista encontrarCaminoEtiquetaMinima(Object elemVerticeSalida, Object elemVerticeEntrada) {
    NodoVert nodoSalida = buscarVertice(elemVerticeSalida);
    Lista camino = null;
    if (nodoSalida != null) { 
      camino = encontrarCaminoEtiquetaMinimaAux(nodoSalida, new Lista(), 1, null, elemVerticeEntrada);
    }
    return camino;
  }

  private Lista encontrarCaminoEtiquetaMinimaAux(NodoVert verticeActual, Lista visitados, int posVisitados, Comparable maxActual, Object elemVerticeEntrada) {
    Lista mejorCamino = null;
    Comparable mejorMax = null;
    if (verticeActual.getElem().equals(elemVerticeEntrada)) {
      mejorCamino = new Lista();
      mejorCamino.insertar(elemVerticeEntrada, 1);
      mejorMax = maxActual;
    } else {
      visitados.insertar(verticeActual.getElem(), posVisitados);
      NodoAdy arcoActual = verticeActual.getPrimerAdy();
      while (arcoActual != null) {
        NodoVert siguiente = arcoActual.getVertice();
        if (visitados.localizar(siguiente.getElem()) == -1) {
          Comparable nuevoMax = maxActual;
          if (nuevoMax == null || arcoActual.getEtiqueta().compareTo(nuevoMax) > 0) {
            nuevoMax = arcoActual.getEtiqueta();
          }
          Lista caminoRama = encontrarCaminoEtiquetaMinimaAux(siguiente, visitados, posVisitados + 1, nuevoMax, elemVerticeEntrada);
          if (caminoRama != null) {
            caminoRama.insertar(verticeActual.getElem(), 1);
            if (mejorCamino == null || nuevoMax.compareTo(mejorMax) < 0) {
              mejorCamino = caminoRama;
              mejorMax = nuevoMax;
            }
          }
        }
        arcoActual = arcoActual.getSigAdy();
      }
      visitados.eliminar(posVisitados);
    }
    return mejorCamino;
  }

  public Lista caminoMasCorto(Object elemVerticeSalida, Object elemVerticeEntrada) {
    NodoVert nodoSalida = buscarVertice(elemVerticeSalida);
    Lista camino = null;
    if (nodoSalida != null) {
        int[] minCantidadNodos = {Integer.MAX_VALUE};
        camino = caminoMasCortoAux(nodoSalida, new Lista(), 1, 0, minCantidadNodos, elemVerticeEntrada);
    }
    return camino;
  }

  private Lista caminoMasCortoAux(NodoVert verticeActual, Lista visitados, int posVisitados, int cantidadActual, int[] minCantidadNodos, Object elemVerticeEntrada) {
    Lista caminoMinimo = null;
    Lista caminoActual;
    // Caso base: llegamos al destino
    if (verticeActual.getElem().equals(elemVerticeEntrada)) {
        caminoMinimo = new Lista();
        caminoMinimo.insertar(elemVerticeEntrada, 1);
        minCantidadNodos[0] = cantidadActual;
    } else {
        // marcamos este vértice como visitado para evitar ciclos y luego recorremos los adyacntes del vertice actual
        visitados.insertar(verticeActual.getElem(), posVisitados);
        NodoAdy arcoActual = verticeActual.getPrimerAdy();
        while (arcoActual != null) {
            NodoVert verticeSiguiente = arcoActual.getVertice();
            // verificamos si ya fue visitado
            if (visitados.localizar(verticeSiguiente.getElem()) == -1) {
                // verifica que el camino explorado tiene menos nodos y si es asi realiza la recursion 
                if (cantidadActual + 1 < minCantidadNodos[0]) {
                    caminoActual = caminoMasCortoAux(verticeSiguiente, visitados, posVisitados + 1, cantidadActual + 1, minCantidadNodos, elemVerticeEntrada);
                    if (caminoActual != null) {
                        caminoActual.insertar(verticeActual.getElem(), 1);
                        caminoMinimo = caminoActual;
                    }
                }
            }
            //pasamos al siguiente arco
            arcoActual = arcoActual.getSigAdy();
        }
        // desmarcamos este vértice antes de volver
        visitados.eliminar(posVisitados);
    }
    return caminoMinimo;
  }
}
package grafos.dinamicas;

public class Grafo {
  private NodoVert inicio;

  public Grafo(){
    this.inicio = null;
  }

  public boolean insertarVertice(Object vertice){
    boolean exito=true;
    if(this.inicio==null){
        this.inicio=new NodoVert(vertice, null, null);
        exito=true;
    }else{
      NodoVert aux=this.inicio;
      while(aux.getSigVertice()!=null && exito){
        if(aux.getElem().equals(vertice)){
          exito=false;
        }else{
          aux=aux.getSigVertice();
        }
      }
      aux.setSigVertice(new NodoVert(vertice, null, null));
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

  private NodoVert buscarVertice(Object vertice){
    NodoVert existe=null;
    NodoVert aux=this.inicio;
    while(aux!=null && existe==null ){
        if(!aux.getElem().equals(vertice)){
            aux=aux.getSigVertice();
        }else{
            existe=aux;
        }
    }
    return existe;
  }

  public boolean insertarArco(Object verSalida, Object verEntrada, Object etiqueta){
    boolean exito=false;
    NodoVert nodoSalida=buscarVertice(verSalida);
    NodoVert nodoEntrada=buscarVertice(verEntrada);
    if(nodoSalida!=null && nodoEntrada!=null){
      NodoAdy ady=nodoSalida.getPrimerAdy();
      boolean existe =false;
      while(ady!=null && !existe){
        if(ady.getVertice().equals(nodoEntrada)){
          existe=true;
        }
        ady=ady.getSigAdy();
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

  public boolean vacio(){
    boolean esVacio=this.inicio==null;
    return esVacio;
  }

  public void vaciar(){
    this.inicio=null;
  }

  public String toString(){
    String cad="";
    if(this.inicio==null){
        cad="Grafo vacio";
    }else{
        NodoVert actual = this.inicio;
        while (actual != null) {
            cad+="\n"+"Vértice Salida: " + actual.getElem().toString() + " -> ";
            NodoAdy ady = actual.getPrimerAdy();
            while (ady != null) {
                cad+="\n" +"Arco: "+ady.getEtiqueta().toString() + " -> ";
                cad+="Vertice entrada: " + ady.getVertice().getElem().toString()+" ";
                ady = ady.getSigAdy();
            }
            actual = actual.getSigVertice();
        }
    }    
    return cad;
  }
}

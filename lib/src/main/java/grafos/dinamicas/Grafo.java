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

  public boolean existeVertice(Object vertice){
    boolean encontrado = buscarVertice(vertice);
    return encontrado;
  }

  private boolean buscarVertice(Object vertice){
    boolean existe=false;
    NodoVert aux=this.inicio;
    while(aux!=null && !existe){
        if(!aux.getElem().equals(vertice)){
            aux=aux.getSigVertice();
        }else{
            existe=true;
        }
    }
    return existe;
  }

  public boolean insertarArco(Object verSalida, Object verEntrada, Object etiqueta){
    boolean exito=false;
    NodoVert aux=this.inicio;
    NodoVert aux0=null;
    NodoVert aux1=null;
    while(aux!=null && (aux0==null || aux1==null)){
      if(aux.getElem().equals(verSalida)) aux0=aux;
      if(aux.getElem().equals(verEntrada)) aux1=aux;
      aux=aux.getSigVertice();
    }
    if(aux0!=null && aux1!=null){
        exito=true;
        NodoAdy nuevo=aux0.getPrimerAdy();
        if(nuevo!=null){
          while(nuevo.getSigAdy()!=null){
            nuevo=nuevo.getSigAdy();
          }
          nuevo.setSigAdyacente(new NodoAdy(aux1, etiqueta));
        }else{
          aux0.setPrimerAdy(new NodoAdy(aux1, etiqueta));
        } 
      }
    return exito;
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

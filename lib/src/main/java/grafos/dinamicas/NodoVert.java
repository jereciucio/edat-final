package grafos.dinamicas;
public class NodoVert{
  private Object elem;
  private NodoVert sigVertice;
  private NodoAdy primerAdy;

   public NodoVert(Object unElemento, NodoVert unSigVertice, NodoAdy unPrimerAdy) {
        this.elem = unElemento;
        this.sigVertice = unSigVertice;
        this.primerAdy = unPrimerAdy;
    }
}
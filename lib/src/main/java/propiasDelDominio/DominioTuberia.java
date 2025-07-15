package propiasDelDominio;
public class DominioTuberia {
  String ciudadOrigen;
  String ciudadDestino;

  public DominioTuberia(String nomCiudadOrigen, String nomCiudadDestino) {
    this.ciudadOrigen = nomCiudadOrigen;
    this.ciudadDestino = nomCiudadDestino;
  }

  public boolean equals(DominioTuberia otroPar) {
    boolean valorIgualdad;
    valorIgualdad = this.ciudadOrigen == otroPar.ciudadOrigen && this.ciudadDestino == otroPar.ciudadDestino;
    return valorIgualdad;
  }
}
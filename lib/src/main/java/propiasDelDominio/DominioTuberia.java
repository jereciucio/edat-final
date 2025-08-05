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

  public int hashCode() {
    int valorHash = this.ciudadOrigen.hashCode() * this.ciudadDestino.hashCode();
    return valorHash;
  }

  public boolean tieneCiudad(String nomenclatura) {
    // Verifica si la tuberia va o viene de la ciudad ingresada como paramentro
    return (nomenclatura == this.ciudadOrigen || nomenclatura == this.ciudadDestino);
  }

  public String toString() {
    return "["+this.ciudadOrigen+","+this.ciudadDestino+"]";
  }
}
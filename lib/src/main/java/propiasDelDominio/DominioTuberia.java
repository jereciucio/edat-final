package propiasDelDominio;
public class DominioTuberia {
  String ciudadOrigen;
  String ciudadDestino;

  public DominioTuberia(String nomCiudadOrigen, String nomCiudadDestino) {
    this.ciudadOrigen = nomCiudadOrigen;
    this.ciudadDestino = nomCiudadDestino;
  }

  public boolean equals(Object unDominio) {
  boolean esIgual = false;
  if (unDominio instanceof DominioTuberia) {
    DominioTuberia otro = (DominioTuberia) unDominio;
    esIgual = this.ciudadOrigen.equals(otro.ciudadOrigen)
        && this.ciudadDestino.equals(otro.ciudadDestino);
  }
  return esIgual;
}

  public int hashCode() {
    int valorHash = this.ciudadOrigen.hashCode() * this.ciudadDestino.hashCode();
    return valorHash;
  }

  public boolean tieneCiudad(String nomenclatura) {
    // Verifica si la tuberia va o viene de la ciudad ingresada como paramentro
    return (nomenclatura.equals(this.ciudadOrigen) || nomenclatura.equals(this.ciudadDestino));
  }

  public String toString() {
    return "["+this.ciudadOrigen+","+this.ciudadDestino+"]";
  }
}
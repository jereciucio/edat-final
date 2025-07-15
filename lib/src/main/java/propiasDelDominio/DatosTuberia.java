package propiasDelDominio;
public class DatosTuberia {
  private double caudalMinimoM3;
  private double caudalMaximoM3;
  private double diametroMM;
  private String nomenclatura;
  private String estado;

  public DatosTuberia(String nomCiudadOrigen, String nomCiudadDestino) {
    this.nomenclatura = "(" + nomCiudadOrigen + ", " + nomCiudadDestino + ")";
    this.caudalMinimoM3 = 0.0;
    this.caudalMaximoM3 = 0.0;
    this.diametroMM = 0.0;
    this.estado = "EN DISEÑO";
  }

  public DatosTuberia(String nomCiudadOrigen, String nomCiudadDestino,
                      double elCaudalMinimo, double elCaudalMaximo, double elDiametro, String elEstado) {
    this.nomenclatura = "(" + nomCiudadOrigen + ", " + nomCiudadDestino + ")";
    this.caudalMinimoM3 = elCaudalMinimo;
    this.caudalMaximoM3 = elCaudalMaximo;
    this.diametroMM = elDiametro;
    this.estado = elEstado;
  }

  public String getNomenclatura() {
    return this.nomenclatura;
  }

  public double getCaudalMinimo() {
    return this.caudalMinimoM3;
  }
  
  public void setCaudalMinimo(double elCaudalMinimo) {
    this.caudalMinimoM3 = elCaudalMinimo;
  }

  public double getCaudalMaximo() {
    return this.caudalMaximoM3;
  }

  public void setCaudalMaximo(double elCaudalMaximo) {
    this.caudalMaximoM3 = elCaudalMaximo;
  }

  public double getDiametro() {
    return this.diametroMM;
  }

  public void setDiametro(double elDiametro) {
    this.diametroMM = elDiametro;
  }

  public String getEstado() {
    return this.estado;
  }

  public void setEstado(String elEstado) {
    // No verifica si el estado es válido. Debería hacerse en cualquier utilizacion desde
    // TransporteDeAgua
    this.estado = elEstado;
  }
}
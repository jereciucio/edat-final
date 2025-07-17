package propiasDelDominio;

public class Anio {
  private int numeroAnio;
  private int[] meses;

  public Anio(int unNumeroAnio) {
    this.numeroAnio = unNumeroAnio;
    this.meses = new int[12];
  }
  public Object getValor(int unMes) {
    Object valor;
    if (unMes >= 1 && unMes <= 12) {
      valor = this.meses[unMes - 1];
    } else {
      valor = null;
    }
    return valor;
  }
}

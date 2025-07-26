package propiasDelDominio;

public class Anio {
  private int numeroAnio;
  private int[] meses;

  public Anio(int unNumeroAnio) {
    this.numeroAnio = unNumeroAnio;
    this.meses = new int[12];
  }
  public int getValor(int unMes) {
    int valor;
    if (unMes >= 1 && unMes <= 12) {
      valor = this.meses[unMes - 1];
    } else {
      valor = 0;
    }
    return valor;
  }
  public void setValor(int unMes, int unValor) {
    if(unMes >= 1 && unMes <= 12){
      this.meses[unMes - 1] = unValor;
    }
  }
  public String toString(){
    int i;
    String cad = "(" + this.numeroAnio + " " + this.meses.toString() + ")";
    return cad;
  }
}
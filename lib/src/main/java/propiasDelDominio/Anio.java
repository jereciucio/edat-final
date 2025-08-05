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
      valor = -1;
    }
    return valor;
  }
  public void setValor(int unMes, int unValor) {
    if(unMes >= 1 && unMes <= 12){
      this.meses[unMes - 1] = unValor;
    }
  }
  public String toString(){
    String cad = "(" + this.numeroAnio + " [";
    for (int i = 0; i < this.meses.length; i++) {
      cad += this.meses[i];
      if (i < this.meses.length - 1) {
        cad += ", ";
      }
    }
    cad += "])";
    return cad;
  }
}
package propiasDelDominio;

public class Anio implements Comparable <Anio> {
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
  public void setValor(int unMes, int unValor) {
    if(unMes >= 1 && unMes <= 12){
      this.meses[unMes - 1] = unValor;
    }
  }
  public int compareTo(Anio unAnio){
    int retorno = 0;
    if(this.numeroAnio > unAnio.numeroAnio){
      retorno = 1;
    }else if(this.numeroAnio < unAnio.numeroAnio){
      retorno = -1;
    }
    return retorno;
  }
}

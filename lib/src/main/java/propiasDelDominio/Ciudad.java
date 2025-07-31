package propiasDelDominio;

import propositoEspecifico.ArbolAVLDicc;

public class Ciudad {
  private String nombre;
  private String nomenclatura;
  private ArbolAVLDicc calendarioHabitantes;
  private double superficie;
  private double consumoPerCapita;

  public Ciudad(
      String nombre,
      String nomenclatura,
      ArbolAVLDicc calendario,
      double superficie,
      double consumo) {
    this.nombre = nombre;
    this.nomenclatura = nomenclatura;
    this.calendarioHabitantes = calendario;
    this.superficie = superficie;
    this.consumoPerCapita = consumo;
  }

  public String getNombre() {
    return this.nombre;
  }

  public String getNomenclatura() {
    return this.nomenclatura;
  }

  public ArbolAVLDicc getCalendarioHabitantes() {
    return this.calendarioHabitantes;
  }

  public double getSuperficie() {
    return this.superficie;
  }

  public int getHabitantes(int mes, int anio) {
    // int cantidad=0;
    // if(mes>0 && mes<13 && anio>1900){
    //   Anio unAnio = this.calendarioHabitantes.buscar(anio);
    //   if(unAnio!=null){
    //     cantidad=unAnio.getValor(mes);
    //   }
    // }
    // return cantidad;
    // FIXME: Implementar recuperar en ArbolAVL y compareTo(int) en Anio
    return 0;
  }

  public double getConsumoMensual(int mes, int anio) {
    double consumo = -1;
    if (mes > 0 && mes < 13 && anio > 1900) {
      Anio unAnio = (Anio) this.calendarioHabitantes.obtenerInformacion(anio);
      if (unAnio != null) {
        int diasMes = obtenerDiasMes(mes);
        consumo = this.consumoPerCapita * unAnio.getValor(mes) * diasMes;
      }
    }
    return consumo;
  }

  public double getConsumoTotal(int anio) {
    double consumo = -1;
    Anio elAnio;
    int mes, diasMes;
    elAnio = (Anio) this.calendarioHabitantes.obtenerInformacion(anio);
    if (elAnio != null) {
      for (mes = 1; mes <= 12; mes++) {
        diasMes = obtenerDiasMes(mes);
        consumo += this.consumoPerCapita * elAnio.getValor(mes) * diasMes;
      }
    }
    return consumo;
  }

  private int obtenerDiasMes(int mes) {
    int dias;
    switch (mes) {
      case 4: case 6: case 9: case 11:
        dias = 30;
        break;
      case 2:
        dias = 28;
        break;
      default:
        dias = 31;
    }
    return dias;
  }

  public boolean setHabitantes(int cantidad, int mes, int anio) {
    boolean exito = false;
    if (cantidad > 0 && mes > 0 && mes < 13) {
      Anio unAnio = (Anio) this.calendarioHabitantes.obtenerInformacion(anio);
      if (unAnio != null) {
        unAnio.setValor(mes, cantidad);
        exito = true;
      }
    }
    return exito;
  }

  public void setConsumo(double consumoPromedio) {
    if (consumoPromedio >= 0) {
      this.consumoPerCapita = consumoPromedio;
    }
  }

  public void setSuperficie(double laSuperficie) {
    if (laSuperficie >= 0) {
      this.superficie = laSuperficie;
    }
  }

  public static boolean nomenclaturaValida(String unNombre, String unaNomenclatura) {
    boolean esValido = false;
    if (unaNomenclatura != null && unaNomenclatura.length() == 6) {
      String letrasEsperadas = unNombre.substring(0, 2).toUpperCase();
      String letrasNomenclatura = unaNomenclatura.substring(0, 2);
      if (letrasEsperadas.equals(letrasNomenclatura)) {
        int numeroNomenclatura = Integer.parseInt(unaNomenclatura.substring(2));
        if (numeroNomenclatura >= 3000 && numeroNomenclatura <= 4000) {
          esValido = true;
        }
      }
    }
    return esValido;
  }

  public String toString() {
    String cad = "";
    cad += "nombre: " + this.nombre;
    cad += "\n" + "nomenclatura: " + this.nomenclatura;
    cad += "\n" + "superficie: " + this.superficie;
    cad += "\n" + "consumo perCapita: " + this.consumoPerCapita;
    cad += "\n" + "Calendario de habitantes: " + this.calendarioHabitantes.toString();
    return cad;
  }
}
package propiasDelDominio;

import propositoEspecifico.ArbolAVLDicc;

public class Ciudad {
  private String nombre;
  private String nomenclatura;
  private ArbolAVLDicc calendarioHabitantes;
  private double superficie;
  private double consumoPerCapita;

  public Ciudad(String nombre, String nomenclatura, ArbolAVLDicc calendario, double superficie, double consumo){
    this.nombre=nombre;
    this.nomenclatura=nomenclatura;
    this.calendarioHabitantes=calendario;
    this.superficie=superficie;
    this.consumoPerCapita=consumo;
  }

  public String getNombre(){
    return this.nombre;
  }

  public String getNomenclatura(){
    return this.nomenclatura;
  }

  public double getSuperficie(){
    return this.superficie;
  }

  public int getHabitantes(int mes, int anio){
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

  public double getConsumo(int mes, int anio){
    // double consumo=0;
    // if(mes>0 && mes<13 && anio>1900){
    //   Anio unAnio=this.calendarioHabitantes.buscar(anio);
    //   if(unAnio!=null){
    //     consumo=this.consumoPerCapita*unAnio.getValor(mes);
    //   }   
    // }
    // return consumo;
    // FIXME: Implementar recuperar en ArbolAVL y compareTo(int) en Anio
    return 0;
  }

  public boolean setHabitantes(int cantidad, int mes, int anio){
    // boolean exito=false;
    // if(cantidad>0 && mes>0 && mes<13 && anio>1900){
    //   Anio unAnio=this.calendarioHabitantes.buscar(anio);
    //   if(unAnio != null){
    //     unAnio.setValor(mes, cantidad);
    //     exito = true;
    //   }
    // }
    // return exito;
    // FIXME: Implementar recuperar en ArbolAVL y compareTo(int) en Anio
    return false;
  }

  public void setConsumo(double consumoPromedio){
    if(consumoPromedio >= 0){
    this.consumoPerCapita=consumoPromedio;
    }
  }

  public String toString(){
    String cad="";
    cad+="nombre: "+this.nombre;
    cad+="\n"+"nomenclatura: "+this.nomenclatura;
    cad+="\n"+"superficie: "+this.superficie;
    cad+="\n"+"consumo perCapita: "+this.consumoPerCapita;
    cad+="\n"+"Calendario de habitantes: "+this.calendarioHabitantes.toString();
    return cad;
  }
  
}
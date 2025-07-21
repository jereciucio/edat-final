package propiasDelDominio;

import conjuntistas.ArbolAVL;

public class Ciudad {
  private String nombre;
  private String nomenclatura;
  private ArbolAVL calendarioHabitantes;
  private double superficie;
  private double consumoPerCapita;

  public Ciudad(String nombre, String nomenclatura, ArbolAVL calendario, double superficie, double consumo){
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
    int cantidad=0;
    if(mes>0 && mes<13 && anio>1900){
      Anio unAnio=this.calendarioHabitantes.buscar(anio);
      if(unAnio!=null){
        cantidad=(int)unAnio.getValor(mes);
      }
    }
    return cantidad;
  }
  
}

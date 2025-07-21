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
  
}

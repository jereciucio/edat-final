package propiasDominio;

import propiasDelDominio.DatosTuberia;

public class TestDatosTuberia {
  public static void main(String[] args) {
    DatosTuberia datos=new DatosTuberia("NE3001", "CU3002", 200, 300, 20, "ACTIVA");
    System.out.println(datos.getNomenclatura());
    System.out.println(datos.getCaudalMinimo());
    System.out.println(datos.getCaudalMaximo());
    System.out.println(datos.getDiametro());
    System.out.println(datos.getEstado());
    System.out.println(datos.toString());
  }
}
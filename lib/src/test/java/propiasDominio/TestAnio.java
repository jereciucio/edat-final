package propiasDominio;

import propiasDelDominio.Anio;

public class TestAnio {
  public static void main(String[] args) {
        Anio unAnio=new Anio(2017);
        unAnio.setValor(1, 23550);
        unAnio.setValor(2, 46670);
        unAnio.setValor(3, 7660);
        unAnio.setValor(4, 27330);
        unAnio.setValor(5, 11120);
        unAnio.setValor(6, 4860);
        unAnio.setValor(7, 4900);
        unAnio.setValor(8, 50000);
        unAnio.setValor(9, 23480);
        unAnio.setValor(10, 9770);
        unAnio.setValor(11, 4320);
        unAnio.setValor(12, 20000);
        System.out.println(unAnio.toString());
        System.out.println(unAnio.getValor(5));
        System.out.println(unAnio.getValor(10));
        System.out.println(unAnio.getValor(15));
        unAnio.setValor(15, 1234);
        unAnio.setValor(7, 200000);
        unAnio.setValor(12, 3000000);
        System.out.println(unAnio.toString());
    }
}
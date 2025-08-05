package propiasDominio;

public class TestAnio {
  public static void main(String[] args) {
        int[] meses={23320,46670,7660,27330,11120,4860,4900,50000,23480,9770,4320,20000};
        Anio unAnio=new Anio();
        unAnio.setAnio(2017);
        unAnio.setMeses(meses);
        unAnio.setAnio(2017);
        unAnio.setMeses(meses);
        System.out.println(unAnio.toString());
        System.out.println(unAnio.getValor(7));
        unAnio.setValor(7, 777);
        System.out.println(unAnio.toString());
    }
}

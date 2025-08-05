public class TestCiudad {
  public static void main(String[] args) {
        int[] meses={23550,46670,7660,27330,11120,4860,4900,50000,23480,9770,4320,20000};
        Anio unAnio=new Anio();
        unAnio.setAnio(2017);
        unAnio.setMeses(meses);
        int[] meses2={230,470,660,27330,120,460,400,5000,2350,770,420,2000};
        Anio unAnio2=new Anio();
        unAnio2.setAnio(2018);
        unAnio2.setMeses(meses2);
        int[] meses3={2336,46,766,273,111,48,480,50,2348,977,432,200};
        Anio unAnio3=new Anio();
        unAnio3.setAnio(2016);
        unAnio3.setMeses(meses3);
        ArbolAVL a1=new ArbolAVL();
        a1.insertar(unAnio);
        a1.insertar(unAnio2);
        a1.insertar(unAnio3);
        Ciudad unaCiudad=new Ciudad("Neufuen", "NE3001", a1, 1200000, 0.25);
        System.out.println(unaCiudad.getHabitantes(7, 2017));
        unaCiudad.setHabitantes(777, 7, 2017);
        System.out.println(unaCiudad.getHabitantes(7, 2017));
        System.out.println(unaCiudad.toString());
    }
}

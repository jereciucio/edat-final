package propiasDelDominio;

public class NodoConsumo implements Comparable<NodoConsumo> {
    private Ciudad ciudad;
    private double consumo;

    public NodoConsumo(Ciudad unaCiudad, double unConsumo) {
        this.ciudad = unaCiudad;
        this.consumo = unConsumo;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public double getConsumo() {
        return consumo;
    }

    public int compareTo(NodoConsumo otroNodo) {
      int numero = 0;
        if (this.consumo > otroNodo.consumo) {
            numero = 1;
        } else if (this.consumo < otroNodo.consumo) {
            numero = 0;
        }
        return numero;
    }

    public String toString() {
        return ciudad.getNombre() + " - Consumo Anual: " + consumo;
    }
}

package conjuntistas;

public class ArbolHeap {
  private int TAMANIO = 20;
    private int ultimo;
    private Comparable[] heap;

    public ArbolHeap() {
        this.heap = new Comparable[TAMANIO];
        this.ultimo = 0;
    }

    public boolean insertar(Comparable unElemento) {
        boolean exito = false;
        if (this.ultimo < heap.length - 1) {
            this.ultimo++;
            int i = this.ultimo;
            heap[i] = unElemento;
            // Flotar el nuevo elemento hacia arriba
            while (i > 1 && heap[i / 2].compareTo(heap[i]) < 0) {
                Comparable temp = heap[i];
                heap[i] = heap[i / 2];
                heap[i / 2] = temp;
                i = i / 2;
            }
            exito = true;
        }
        return exito;
    }

    public boolean eliminarCima() {
        boolean exito = false;
        if (this.ultimo > 0) {
            this.heap[1] = this.heap[ultimo];
            this.ultimo--;
            hacerBajar(1);
            exito = true;
        }
        return exito;
    }

    private void hacerBajar(int posPadre) {
        int posH;
        Comparable temp = this.heap[posPadre];
        boolean salir = false;
        while (!salir) {
            posH = posPadre * 2;
            if (posH <= this.ultimo) {
                // Si tiene dos hijos, se elige el mayor
                if (posH < this.ultimo && this.heap[posH + 1].compareTo(this.heap[posH]) > 0) {
                    posH++;
                }
                if (this.heap[posH].compareTo(temp) > 0) {
                    this.heap[posPadre] = this.heap[posH];
                    this.heap[posH] = temp;
                    posPadre = posH;
                } else {
                    salir = true;
                }
            } else {
                salir = true;
            }
        }
    }

    public Comparable recuperarCima() {
        if (ultimo != 0) {
            return heap[1];
        }
        return null;
    }

    public boolean esVacio() {
        return ultimo == 0;
    }
}

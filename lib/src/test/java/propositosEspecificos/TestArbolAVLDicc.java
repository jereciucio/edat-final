package propositosEspecificos;

import propiasDelDominio.Anio;
import propositoEspecifico.ArbolAVLDicc;
import static org.junit.Assert.*;
import org.junit.Test;

public class TestArbolAVLDicc {
  @Test
  public void testInsertarRaiz() {
    ArbolAVLDicc tree = new ArbolAVLDicc();
    boolean insertar = tree.insertar(1, 1);
    String expectedTreeString = "Raiz: 1";
    expectedTreeString += "\n1 (alt: 0)";
    expectedTreeString += "\n    HI: -";
    expectedTreeString += "\n    HD: -";
    String treeString = tree.toString();
    System.err.println(treeString);

    assertEquals(expectedTreeString, treeString);
    assertEquals(insertar, true);
  }

  @Test
  public void testInsertarYaPresente() {
    ArbolAVLDicc tree = new ArbolAVLDicc();
    String expectedTreeString = "Raiz: 9";
    expectedTreeString += "\n9 (alt: 2)";
    expectedTreeString += "\n    HI: 7";
    expectedTreeString += "\n    HD: 10";
    expectedTreeString += "\n7 (alt: 1)";
    expectedTreeString += "\n    HI: -";
    expectedTreeString += "\n    HD: 8";
    expectedTreeString += "\n8 (alt: 0)";
    expectedTreeString += "\n    HI: -";
    expectedTreeString += "\n    HD: -";
    expectedTreeString += "\n10 (alt: 0)";
    expectedTreeString += "\n    HI: -";
    expectedTreeString += "\n    HD: -";

    tree.insertar(10, 10);
    tree.insertar(7, 7);
    tree.insertar(9, 9);
    tree.insertar(8, 8);

    boolean insertar1 = tree.insertar(9, 9);
    boolean insertar2 = tree.insertar(7, 7);
    boolean insertar3 = tree.insertar(8, 8);
    boolean insertar4 = tree.insertar(10, 10);

    String treeString = tree.toString();
    System.err.println(treeString);

    assertEquals(insertar1, false);
    assertEquals(insertar2, false);
    assertEquals(insertar3, false);
    assertEquals(insertar4, false);
    assertEquals(expectedTreeString, treeString);
  }

  @Test
  public void testInsertarRotacionDerecha() {
    ArbolAVLDicc tree = new ArbolAVLDicc();
    tree.insertar(30, 30);
    tree.insertar(20, 20);
    tree.insertar(10, 10); // Provoca rotación simple derecha

    String expectedTreeString = "Raiz: 20";
    expectedTreeString += "\n20 (alt: 1)";
    expectedTreeString += "\n    HI: 10";
    expectedTreeString += "\n    HD: 30";
    expectedTreeString += "\n10 (alt: 0)";
    expectedTreeString += "\n    HI: -";
    expectedTreeString += "\n    HD: -";
    expectedTreeString += "\n30 (alt: 0)";
    expectedTreeString += "\n    HI: -";
    expectedTreeString += "\n    HD: -";

    assertEquals(expectedTreeString, tree.toString());
  }

  @Test
  public void testInsercionRotacionIzquierda() {
    ArbolAVLDicc tree = new ArbolAVLDicc();
    tree.insertar(10, 10);
    tree.insertar(20, 20);
    tree.insertar(30, 30); // Provoca rotación izquierda

    String expectedTreeString = "Raiz: 20";
    expectedTreeString += "\n20 (alt: 1)";
    expectedTreeString += "\n    HI: 10";
    expectedTreeString += "\n    HD: 30";
    expectedTreeString += "\n10 (alt: 0)";
    expectedTreeString += "\n    HI: -";
    expectedTreeString += "\n    HD: -";
    expectedTreeString += "\n30 (alt: 0)";
    expectedTreeString += "\n    HI: -";
    expectedTreeString += "\n    HD: -";

    assertEquals(expectedTreeString, tree.toString());
  }

  @Test
  public void testObtenerInformacionExistente() {
    ArbolAVLDicc tree = new ArbolAVLDicc();
    tree.insertar(5, "A");
    tree.insertar(3, "B");
    tree.insertar(7, "C");

    assertEquals("A", tree.obtenerInformacion(5));
    assertEquals("B", tree.obtenerInformacion(3));
    assertEquals("C", tree.obtenerInformacion(7));
  }

  @Test
  public void testObtenerInformacionClaveInexistente() {
    ArbolAVLDicc tree = new ArbolAVLDicc();
    tree.insertar(1, "X");
    assertNull(tree.obtenerInformacion(999));
  }

  @Test
  public void testEliminarHoja() {
    ArbolAVLDicc tree = new ArbolAVLDicc();
    tree.insertar(10, 10);
    tree.insertar(5, 5);
    tree.insertar(15, 15);

    String expectedTreeString = "Raiz: 10";
    expectedTreeString += "\n10 (alt: 1)";
    expectedTreeString += "\n    HI: -";
    expectedTreeString += "\n    HD: 15";
    expectedTreeString += "\n15 (alt: 0)";
    expectedTreeString += "\n    HI: -";
    expectedTreeString += "\n    HD: -";

    assertTrue(tree.eliminar(5));
    assertNull(tree.obtenerInformacion(5));
    assertEquals(expectedTreeString, tree.toString());
  }

  @Test
  public void testEliminarNodoConUnHijo() {
    ArbolAVLDicc tree = new ArbolAVLDicc();
    tree.insertar(10, 10);
    tree.insertar(5, 5);
    tree.insertar(2, 2);

    assertTrue(tree.eliminar(5));
    assertNull(tree.obtenerInformacion(5));

    String expectedTreeString = "Raiz: 10";
    expectedTreeString += "\n10 (alt: 1)";
    expectedTreeString += "\n    HI: 2";
    expectedTreeString += "\n    HD: -";
    expectedTreeString += "\n2 (alt: 0)";
    expectedTreeString += "\n    HI: -";
    expectedTreeString += "\n    HD: -";

    assertEquals(expectedTreeString, tree.toString());
  }
}
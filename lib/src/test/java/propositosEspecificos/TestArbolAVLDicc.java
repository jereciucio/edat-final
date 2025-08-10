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
    System.err.print(treeString);

    assertEquals(expectedTreeString, treeString);
    assertEquals(insertar, true);
  }
}
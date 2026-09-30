package WM.I26002_GestionBines.scripts;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;
import modelo.BaseExecution;
import WM.om.GestionBines;

/**
 * DESCRIPCIÓN:
 * Validar que los Bines de QA (preexistentes) sigan mostrándose aun cuando se hayan insertado nuevos bines. Confirma que la inserción masiva no eliminó, modificó ni perdió ninguno de los registros previos en la tabla EO_RMS.TDC_BIN.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC_FT_002_Validar que los bines de QA continúen registrados después de la ejecución del Script de inserción
 *
 * PREREQUISITOS:
 * - Acceso a la base de datos FCDASPFR (QA)
 * - Haber ejecutado el script de inserción de nuevos bines
 *
 * INSUMOS:
 * - Excel con columna: FECHA_CORTE
 *
 * @author  Gerardo Lopez
 * @date    27/05/2026
 */

public class ATC_FT_02_ValidarBinesQAContinuenRegistradosDAS extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_02_ValidarBinesQAContinuenRegistradosDAS_test(HashMap<String, String> data) throws Exception {

        testCase.setProject_Name("I26002_Gestion de Bines");

        GestionBines gb = new GestionBines(testCase);

        String fechaCorte = data.get("FECHA_CORTE");

        addStep("Validar que se hayan insertado BINs nuevos desde la fecha de corte " + fechaCorte);

        Map<String, String> conteo = gb.countRegisterInsert(fechaCorte);

        assertTrue(conteo.get("RESULTADO_CONTEO").contains("OK — Conteo correcto"),
                "FALLA en conteo — " + conteo.get("RESULTADO_CONTEO")
                + " (Total antes: " + conteo.get("TOTAL_ANTES")
                + ", Filas nuevas: " + conteo.get("FILAS_NUEVAS") + ")");

        addStep("Validar que los BINs de QA (preexistentes a " + fechaCorte + ") continúen registrados en TDC_BIN");

        int cantidadBinsQA = gb.validarBinesQAIntactos(fechaCorte);

        assertFalse(cantidadBinsQA == 0,
                "No se encontró ningún BIN preexistente a " + fechaCorte + ". "
                + "Todos los registros previos podrían haber sido eliminados o alterados.");

    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
        return "Acceso a BD FCDASPFR QA. Script de inserción de nuevos bines ejecutado previamente.";
    }

    @Override
    public String setTestDescription() {
        return "Validar que los Bines de QA (preexistentes) sigan mostrándose aun cuando " +
               "se hayan insertado más bines mediante el script de inserción masiva.";
    }

    @Override
    public String setTestDesigner() {
        return "Gerardo Lopez";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_02_ValidarBinesQAContinuenRegistradosDAS";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}
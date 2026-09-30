package WM.I26002_GestionBines.scripts;

import static org.testng.Assert.assertTrue;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;
import modelo.BaseExecution;
import WM.om.GestionBines;

/**
 * DESCRIPCIÓN: Validar que despues de ejecutar el job se muestren los bines insertados EN TDC_BIN de BD FCDASPFR
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *     - MTC_FT_009_Validar cantidad nueva insertada de bines post ejecucion TPE_SYNC
 *
 * PREREQUISITOS:
 * - Acceso a la base de datos: FCTDCQA_QRO
 * - Haber insertado los bines nuevos mediante ejecución de Update.
 * - Haber ejecutado previamente el job TPE_SYNC o FEMSA_TPE_SERVICIO
 *
 * INSUMOS:
 * Excel con columna: FECHA_CORTE
 *
 * @author Gerardo Lopez
 * @date 26/05/2026
 */

public class ATC_FT_09_ValidarCantidadNuevaInsertadaBinesPostTPE_SYNC extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_09_ValidarCantidadNuevaInsertadaBinesPostTPE_SYNC_test(HashMap<String, String> data) throws Exception {

        testCase.setProject_Name("I26002_Gestion de Bines");

        GestionBines gb = new GestionBines(testCase);

        String fechaCorte = data.get("FECHA_CORTE");

        addStep("Validar que se hayan insertado BINs nuevos desde la fecha de corte " + fechaCorte
                + " en BD FCTDCQA_QRO tras la ejecucion del job");

        Map<String, String> resultado = gb.binInsertTDC(fechaCorte);

        assertTrue(resultado.get("RESULTADO_CONTEO").contains("OK — Conteo correcto"),
                "FALLO en conteo — " + resultado.get("RESULTADO_CONTEO")
                + " (Total antes: " + resultado.get("TOTAL_ANTES")
                + ", Filas nuevas: " + resultado.get("FILAS_NUEVAS") + ")");

    }

    @Override
    public void beforeTest() {}

    @Override
    public String setPrerequisites() {
        return "- Acceso a la base de datos: FCTDCQA_QRO\n"
             + "- Haber insertado los bines nuevos mediante ejecución de Update.\n"
             + "- Haber ejecutado previamente el job TPE_SYNC o FEMSA_TPE_SERVICIO";
    }

    @Override
    public String setTestDescription() {
        return "Validar que despues de ejecutar el job se muestren los bines insertados en TDC_BIN de BD FCTDCQA_QRO";
    }

    @Override
    public String setTestDesigner() {
        return "Gerardo Lopez";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_09_ValidarCantidadNuevaInsertadaBinesPostTPE_SYNC";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}
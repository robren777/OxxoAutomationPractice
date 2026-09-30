package WM.I26002_GestionBines.scripts;

import static org.testng.Assert.assertTrue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;
import modelo.BaseExecution;
import WM.om.GestionBines;

/**
 * DESCRIPCIÓN: Validar que los datos ingresados desde una fecha de corte se 
 * encuentren en la tabla TDC_BIN de BD FCDASPFR y esten completos.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *     - MTC_FT_001_Validar cantidad nueva insertada de bines mediante script
 *
 * PREREQUISITOS:
 * - Acceso a la base de datos: FCDASPRF QA
 * - Haber insertado los bines nuevos mediante ejecución de Update.
 *
 * INSUMOS:
 * Excel con columna FECHA_CORTE en formato DD/MM/YYYY
 *
 * @author Gerardo Lopez
 * @date 26/05/2026
 */

public class ATC_FT_01_ValidarCantidadNuevaInsertadaDeBines extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_01_ValidarCantidadNuevaInsertadaDeBines_test(HashMap<String, String> data) throws Exception {

        GestionBines gb = new GestionBines(testCase);

        testCase.setProject_Name("I26002_Gestion de Bines");

        String fechaCorte = data.get("FECHA_CORTE");

        addStep("Validar que se hayan insertado BINs nuevos desde la fecha de corte " + fechaCorte);

        Map<String, String> resultado = gb.countRegisterInsert(fechaCorte);

        assertTrue(resultado.get("RESULTADO_CONTEO").contains("OK — Conteo correcto"),
                "FALLO en conteo — " + resultado.get("RESULTADO_CONTEO")
                + " (Total antes: " + resultado.get("TOTAL_ANTES")
                + ", Filas nuevas: " + resultado.get("FILAS_NUEVAS")
                + ", Total actual: " + resultado.get("TOTAL_DESPUES") + ")");

        addStep("Listar los BINs insertados desde la fecha de corte como evidencia");

        List<String> binsNuevos = gb.getBinsInsertadosDesde(fechaCorte);

        addStep("Validar que los BINs insertados desde " + fechaCorte
                + " tengan sus referencias completas (bandera, banco, tipo de tarjeta, bank_code)");

        List<String> binsIncompletos = gb.checkIncompleteDataBinsDesde(fechaCorte);

        assertTrue(binsIncompletos.isEmpty(),
                "FALLO — Existen " + binsIncompletos.size()
                + " BINs insertados desde " + fechaCorte + " con referencias incompletas.");

    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
        return "- Acceso a la base de datos: FCDASPRF QA\n" +
               "- Haber insertado los bines nuevos mediante ejecución de Update.";
    }

    @Override
    public String setTestDescription() {
        return "Validar que los datos ingresados desde la fecha de corte se encuentren en la tabla "
                + "TDC_BIN de BD FCDASPFR y esten completos.";
    }

    @Override
    public String setTestDesigner() {
        return "Gerardo Lopez";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_01_ValidarCantidadNuevaInsertadaDeBines";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}
package WM.I26002_GestionBines.scripts;

import static org.testng.Assert.assertTrue;
import java.util.HashMap;

import org.testng.annotations.Test;
import modelo.BaseExecution;
import WM.om.GestionBines;

/**
 * DESCRIPCIÓN: Validar que los datos ingresados se encuentren en la tabla TDC_BIN de BD FCDASPFR
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *     - MTC_FT_003_Validar las columnas de los bines insertados en BD FCDASPFR sean correctos
 *
 * PREREQUISITOS:
 * - Acceso a la base de datos: FCDASPRF QA
 * - Haber insertado los bines nuevos mediante ejecución de Update.
 *
 * @author Gerardo Lopez
 * @date 28/05/2026
 */

public class ATC_FT_03_ValidarColumnasBinesInsertadosEnFCDASPFR extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_03_ValidarColumnasBinesInsertadosEnFCDASPFR_test(HashMap<String, String> data) throws Exception {

        testCase.setProject_Name("I26002_Gestion de Bines");

        GestionBines gb = new GestionBines(testCase);

        addStep("Validar integridad de datos de todos los BINs en TDC_BIN "
                + "(bandera, banco, tipo de tarjeta, bank_code)");

        int cantidadIncompletos = gb.checkBinsIntegrity();

        assertTrue(cantidadIncompletos == 0,
                "FALLA - Se encontraron " + cantidadIncompletos
                + " BINs con datos incompletos o sin registro en tablas relacionadas.");

    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
        return " * - Acceso a la base de datos: FCDASPRF QA\r\n"
        		+ " * - Haber insertado los bines nuevos mediante ejecución de Update.";
    }

    @Override
    public String setTestDescription() {
        return "Validar que los datos ingresados se encuentren en la tabla TDC_BIN de BD FCDASPFR";
    }

    @Override
    public String setTestDesigner() {
        return "Gerardo Lopez";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_03_ValidarColumnasBinesInsertadosEnFCDASPFR_test";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}
package WEB.cometa.scripts.mvp2;

import static org.testng.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**Desc: Validar que los usuarios CEDIS  tomen en cuenta el identificador Ãºnico al mostrar la informaciÃ³n 
 * @autor Yair Lizarraga
 * @date Octubre 2024
 */
public class ATC_FT_07_HU2_ValidarUsuarioComercialDIRECTOCOMERCIALTomeCuentaSoloIdentificadorUnico extends BaseExecution {
    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_07_HU2_ValidarUsuarioComercialDIRECTOCOMERCIALTomeCuentaSoloIdentificadorUnico_test(HashMap<String, String> data) {
        testCase.setProject_Name("Portal Web: Cometa");

        /*
         * Variables
         ******************************************************************************************/
        String user = data.get("user");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String url = data.get("Url");

        u = new SeleniumUtil(new ChromeTest(), true);
        PortalCometa pc = new PortalCometa(u, testCase);

        String dbUrl = GlobalVariables.DB_URL_Cometa;
        String dbUser = GlobalVariables.DB_USER_Cometa;
        String dbPassword = GlobalVariables.DB_PASSWORD_Cometa;
        String query = "SELECT id, cr_plaza, cr_tienda, estatus, transferencia, origen, id_retek FROM public.xxcm_historico_correos LIMIT 20;";
        /*
         *                                             PASO 1
         ******************************************************************************************/
        addStep("Ingresar a la liga");
        u.get(url);
        u.refresh();
        u.hardWait(3);
        testCase.addScreenShotCurrentStep(u, "Pantalla cargada");
        pc.PrivatePortal();

        /*
         *                                             PASO 2
         ******************************************************************************************/
        addStep("Ingresar las credenciales correspondientes al perfil de Cometa para el inicio de sesion");
        boolean resultStep2 = pc.IniciarSesionNoToken(user, password);
        if(resultStep2){
            testCase.addTextEvidenceCurrentStep("Se logro iniciar sesion correctamente");
        } else {
            testCase.addTextEvidenceCurrentStep("No se pudo iniciar sesion");
        }
        assertTrue(resultStep2, "No se pudo iniciar sesion");


        /*
         *                                             PASO 3
         ******************************************************************************************/
        addStep("Ingresar a Portal Cometa");
        boolean resultStep3 = pc.seleccionarPortalCometaQA();
        if(resultStep3){
            testCase.addTextEvidenceCurrentStep("Se pudo seleccionar el portal Cometa");
        } else{
            testCase.addTextEvidenceCurrentStep("No es visible el elemento de Cometa");
        }
        assertTrue(resultStep3, "No es visible el elemento de Cometa");

        /*
         *                                             PASO 4
         ******************************************************************************************/
        addStep("Ingresar a Comprobantes de compra y Resumen");
        boolean resultStep4 = pc.clicBotonCometaDirectos();
        if(resultStep4){
            testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y entrar en Directos");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el elemento Directos");
        }
        assertTrue(resultStep4, "No se encontro el elemento Directos");

        boolean resultStep4_1 = pc.ClicBotonComprobantesDeCompra();
        if(resultStep4_1){
            testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y entrar en Comprobantes de Compra");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el elemento Comprobantes de Compra");
        }
        assertTrue(resultStep4_1,"No se encontro el elemento Comprobantes de Compra");
     
        // Paso 4 : Conectar a la base de datos PostgreSQL y realizar la consulta
        addStep("Conectar a la base de datos PostgreSQL y realizar la consulta");
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            // Obtener metadatos de la consulta
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Crear un StringBuilder para almacenar los resultados
            StringBuilder resultBuilder = new StringBuilder();

            // Crear la fila de encabezado con formato justificado
            for (int i = 1; i <= columnCount; i++) {
                resultBuilder.append(String.format("%-20s", metaData.getColumnName(i)));
            }
            resultBuilder.append("\n");

            // Rellenar las filas con los datos con formato justificado
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    resultBuilder.append(String.format("%-20s", rs.getString(i)));
                }
                resultBuilder.append("\n");
            }

               // Imprimir los resultados en la evidencia
               testCase.addTextEvidenceCurrentStep(resultBuilder.toString());

           } catch (Exception e) {
               e.printStackTrace();
           }
    }

    @Override
    public String setTestFullName() {
        return "";
    }

    @Override
    public String setTestDescription() {
        return "Validar que los usuarios CEDIS tomen en cuenta el identificador Ãºnico al mostrar la informaciÃ³n";
    }

    @Override
    public String setTestDesigner() {
        return "Yair Lizarraga";
    }

    @Override
    public String setTestInstanceID() {
        return "";
    }

    @Override
    public String setPrerequisites() {
        return "Contar con credenciales vÃ¡lidas para el inicio de sesiÃ³n de perfil CEDIS (ADMIN) ";
    }

    @Override
    public void beforeTest() {

    }

    @AfterMethod
    public void afterMethod(){
        u.getDriver().quit();
    }
}
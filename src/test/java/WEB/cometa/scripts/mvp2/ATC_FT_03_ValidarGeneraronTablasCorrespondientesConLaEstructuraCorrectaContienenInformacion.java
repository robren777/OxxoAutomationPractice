package WEB.cometa.scripts.mvp2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.SeleniumUtil;

/**Desc: Validar que se pueda realizar una consulta en PostgreSQL y obtener los resultados.
 * @autor Yair Lizarraga
 * @date Octubre 2024
 */
public class ATC_FT_03_ValidarGeneraronTablasCorrespondientesConLaEstructuraCorrectaContienenInformacion extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_03_ValidarGeneraronTablasCorrespondientesConLaEstructuraCorrectaContienenInformacion_test(HashMap<String, String> data) throws Exception {
        testCase.setProject_Name("Portal Cometa");

        // Variables
        String user = data.get("user");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String url = data.get("Url");
        String dbUrl = GlobalVariables.DB_URL_Cometa;
        String dbUser = GlobalVariables.DB_USER_Cometa;
        String dbPassword = GlobalVariables.DB_PASSWORD_Cometa;
        String query = "SELECT supplier, supplier_name, rfc FROM public.xxcm_proveedores_job_sync LIMIT 20;";
        String query2 = "SELECT fuente_de_abasto, id_retek, supplier FROM public.xxcm_proveedor_has_tienda LIMIT 20;";
        String query3 = "SELECT id_tienda, cr_plaza, desc_plaza, desc_region, id_region, zona_operativa FROM public.xxcm_region_plaza_tienda_job_sync LIMIT 20;";
        String query4 = "SELECT id_supplier, id_retek FROM public.xxcm_cedis_has_tienda; LIMIT 20;";
        
        // Paso 1: Validar que se generÃ³ la tabla de relaciÃ³n: proovedor - FA. 
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
                resultBuilder.append(String.format(" %-18s ", metaData.getColumnName(i)));
            }
            resultBuilder.append("\n"); // AsegÃºrate de usar \n para nueva lÃ­nea

            // Rellenar las filas con los datos con formato justificado y centrado
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String value = rs.getString(i);
                    int padding = (18 - value.length()) / 2;
                    String centeredValue = String.format("%" + padding + "s%s%" + (18 - value.length() - padding) + "s", "", value, "");
                    resultBuilder.append(String.format(" %-18s ", centeredValue));
                }
                resultBuilder.append("\n"); // AsegÃºrate de usar \n para nueva lÃ­nea
            }

            // Imprimir los resultados en la evidencia
            testCase. addTextEvidenceCurrentStep(resultBuilder.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

       


        
        // Validar que se generÃ³ la tabla de relaciÃ³n: tienda - FA.  
        addStep("Conectar a la base de datos PostgreSQL y realizar la consulta");
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query2)) {

            // Obtener metadatos de la consulta
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Crear un StringBuilder para almacenar los resultados
            StringBuilder resultBuilder = new StringBuilder();

            // Crear la fila de encabezado con formato justificado
            for (int i = 1; i <= columnCount; i++) {
                resultBuilder.append(String.format(" %-18s ", metaData.getColumnName(i)));
            }
            resultBuilder.append("\n"); // AsegÃºrate de usar \n para nueva lÃ­nea

            // Rellenar las filas con los datos con formato justificado y centrado
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String value = rs.getString(i);
                    int padding = (18 - value.length()) / 2;
                    String centeredValue = String.format("%" + padding + "s%s%" + (18 - value.length() - padding) + "s", "", value, "");
                    resultBuilder.append(String.format(" %-18s ", centeredValue));
                }
                resultBuilder.append("\n"); // AsegÃºrate de usar \n para nueva lÃ­nea
            }

            // Imprimir los resultados en la evidencia
            testCase. addTextEvidenceCurrentStep(resultBuilder.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Validar que se generÃ³ la tabla de relaciÃ³n: Region - proovedor.   
        addStep("Conectar a la base de datos PostgreSQL y realizar la consulta");
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query3)) {

            // Obtener metadatos de la consulta
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Crear un StringBuilder para almacenar los resultados
            StringBuilder resultBuilder = new StringBuilder();

            // Crear la fila de encabezado con formato justificado
            for (int i = 1; i <= columnCount; i++) {
                resultBuilder.append(String.format(" %-18s ", metaData.getColumnName(i)));
            }
            resultBuilder.append("\n"); // AsegÃºrate de usar \n para nueva lÃ­nea

            // Rellenar las filas con los datos con formato justificado y centrado
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String value = rs.getString(i);
                    int padding = (18 - value.length()) / 2;
                    String centeredValue = String.format("%" + padding + "s%s%" + (18 - value.length() - padding) + "s", "", value, "");
                    resultBuilder.append(String.format(" %-18s ", centeredValue));
                }
                resultBuilder.append("\n"); // AsegÃºrate de usar \n para nueva lÃ­nea
            }

            // Imprimir los resultados en la evidencia
            testCase. addTextEvidenceCurrentStep(resultBuilder.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        // Validar que se generÃ³ la tabla de relaciÃ³n: tienda - proovedor.    
        addStep("Conectar a la base de datos PostgreSQL y realizar la consulta");
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query3)) {

            // Obtener metadatos de la consulta
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Crear un StringBuilder para almacenar los resultados
            StringBuilder resultBuilder = new StringBuilder();

            // Crear la fila de encabezado con formato justificado
            for (int i = 1; i <= columnCount; i++) {
                resultBuilder.append(String.format(" %-18s ", metaData.getColumnName(i)));
            }
            resultBuilder.append("\n"); // AsegÃºrate de usar \n para nueva lÃ­nea

            // Rellenar las filas con los datos con formato justificado y centrado
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String value = rs.getString(i);
                    int padding = (18 - value.length()) / 2;
                    String centeredValue = String.format("%" + padding + "s%s%" + (18 - value.length() - padding) + "s", "", value, "");
                    resultBuilder.append(String.format(" %-18s ", centeredValue));
                }
                resultBuilder.append("\n"); // AsegÃºrate de usar \n para nueva lÃ­nea
            }

            // Imprimir los resultados en la evidencia
            testCase. addTextEvidenceCurrentStep(resultBuilder.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beforeTest() {

    }

    @Override
    public String setPrerequisites() {
        return "1. Contar con acceso a la base de datos PostgreSQL (usuario y contraseÃ±a).";
    }

    @Override
    public String setTestDescription() {
        return "VDescripciÃ³n: Validar que se generarÃ³n las siguientes tablas: \r\n"
        		+ "\r\n"
        		+ "-Generar la tabla de relaciÃ³n: proovedor - FA.  \r\n"
        		+ "\r\n"
        		+ "-Generar la tabla de relaciÃ³n: tienda - FA. \r\n"
        		+ "\r\n"
        		+ "-Generar la tabla de relaciÃ³n: Region - proovedor.  \r\n"
        		+ "\r\n"
        		+ "-Generar la tabla de relaciÃ³n: plaza - proovedor.  \r\n"
        		+ "\r\n"
        		+ "-Generar la tabla de relaciÃ³n: tienda - proovedor.  \r\n"
        		+ "\r\n"
        		+ "-Generar la tabla de relaciÃ³n: Zona - proovedor. ";
    }

    @Override
    public String setTestDesigner() {
        return "Yair Lizarraga, HEXAWARE";
    }

    @Override
    public String setTestFullName() {
        return "";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}
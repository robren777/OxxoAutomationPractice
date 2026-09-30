package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.accessControlPage;
import WEB.cometa.om.comprobantesCompraPage;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCION: Verificar en Excel individual de Comprobantes de Compra las columnas nuevas,
 * su orden y la consistencia funcional para perfil ADMIN.
 *
 *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_394 CA03, CA03.1, CA03.3, CA04, CA04.1, CA04.2, CA04.4, CA05, CA05.1, CA05.2, CA05.4, CA06, CA06.1, CA06.2, CA07, CA07.1, CA07.2, CA08 - Verificar columnas nuevas, valores, orden, consistencia con PDF y formato en Excel individual - Admin
 *
 *PREREQUISITOS:
 *1. Perfil ADMIN (usuario 5555555) habilitado en QA con acceso a Directos > Comprobantes de Compra.
 *2.Liga de Portal Cometa
 *
 *INSUMOS:
 *Usuario ADMIN y rango con registros disponibles para descarga individual.
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
 * @author Mauricio Gurrola
 * @date Agosto 2026
 */
public class ATC_FT_394_ValidarColumnasNuevasExcelIndividualADMIN extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_394_ValidarColumnasNuevasExcelIndividualADMIN_test(HashMap<String, String> data) throws Exception {
        testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");

        u = new SeleniumUtil(new MEdgeTest(), true);
        PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
        accessControlPage accessControl = new accessControlPage(u, testCase, pc4);
        comprobantesCompraPage comprobantesPage = new comprobantesCompraPage(u, testCase);

        String user = data.get("user");
        String url = data.get("Url");
        String perfil = "ADMIN";

        addStep("Precondicion: obtener password configurado para el usuario " + user + " con perfil " + perfil);
        testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
        System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);
        String password = pc4.obtenerPasswordPorUsuario(user);
        assertTrue(password != null && !password.trim().isEmpty(),
                "Precondicion fallida: no se encontro password configurada para el usuario " + user);

        HashMap<String, String> rango = comprobantesPage.obtenerRangoFechasComprobantesCompraDesdeBD(user);
        String fechaInicio = rango.get("fechaInicio");
        String fechaFin = rango.get("fechaFin");
        assertTrue(fechaInicio != null && !fechaInicio.trim().isEmpty() && fechaFin != null && !fechaFin.trim().isEmpty(),
                "Precondicion fallida: no se obtuvo rango de fechas con informacion para Comprobantes de Compra");

        /*
         *                                             PASO 1
         ***************************************************************************/
        addStep("Ingresar la URL https://pruebasweb.oxxo.com/AuthFemcoProx/Login en el campo de direcciones del navegador");
        boolean resultStep1 = pc4.IngresarURL(url, true);
        assertTrue(resultStep1, "Paso 1 fallido: no se muestra la pantalla de inicio de sesion del portal Access Control");
        testCase.addTextEvidenceCurrentStep("El sistema muestra la pantalla de inicio de sesion del portal Access Control.");
        pc4.PrivatePortal();

        /*
         *                                             PASO 2
         ***************************************************************************/
        addStep("Escribir '5555555' en el campo Usuario");
        boolean resultStep2 = accessControl.capturarYValidarUsuario(user);
        assertTrue(resultStep2, "Paso 2 fallido: el campo Usuario no muestra el valor esperado del perfil ADMIN");
        testCase.addTextEvidenceCurrentStep("El campo Usuario muestra el valor capturado desde data provider: " + user + ".");
        testCase.addScreenShotCurrentStep(u, "Campo_Usuario_Admin_Capturado");

        /*
         *                                             PASO 3
         ***************************************************************************/
        addStep("Escribir la contrasena del perfil Admin en el campo Contrasena");
        boolean resultStep3 = accessControl.capturarYValidarPassword(password);
        assertTrue(resultStep3, "Paso 3 fallido: la contrasena no queda capturada y enmascarada en el campo");
        testCase.addTextEvidenceCurrentStep("El campo Contrasena muestra los caracteres enmascarados.");
        testCase.addScreenShotCurrentStep(u, "Campo_Contrasena_Enmascarada");

        /*
         *                                             PASO 4
         ***************************************************************************/
        addStep("Dar clic en el boton Iniciar sesion");
        boolean resultStep4 = accessControl.iniciarSesionYValidarAcceso();
        assertTrue(resultStep4,
                "Paso 4 fallido: el sistema no valida credenciales o no redirige a la seleccion de portales");

        /*
         *                                             PASO 5
         ***************************************************************************/
        addStep("Dar clic en la opcion Portal COMETA");
        boolean resultStep5 = pc4.seleccionarPortalCometaQA(true);
        assertTrue(resultStep5,
                "Paso 5 fallido: no se visualiza la pantalla principal del portal COMETA con las opciones del perfil");

        /*
         *                                             PASO 6
         ***************************************************************************/
        addStep("Dar clic en la tarjeta Directos");
        boolean resultStep6 = pc4.clicBotonCometaDirectos(true);
        assertTrue(resultStep6, "Paso 6 fallido: no se visualiza la pantalla del modulo Directos");

        /*
         *                                             PASO 7
         ***************************************************************************/
        addStep("Dar clic en la tarjeta Comprobantes de Compra");
        boolean resultStep7 = pc4.ClicBotonComprobantesDeCompra(true) && comprobantesPage.validarPantallaComprobantesCompra();
        assertTrue(resultStep7,
                "Paso 7 fallido: no se visualiza la pantalla Comprobantes de Compra con los filtros disponibles y las opciones de consulta");

        /*
         *                                             PASO 8
         ***************************************************************************/
        addStep("Seleccionar en Fecha Inicio el valor [FECHA_INICIO_COMPROBANTE_COMPLETO]");
        boolean resultStep8 = comprobantesPage.seleccionarFechaInicio(fechaInicio);
        assertTrue(resultStep8, "Paso 8 fallido: el campo Fecha Inicio no muestra el valor seleccionado");

        /*
         *                                             PASO 9
         ***************************************************************************/
        addStep("Seleccionar en Fecha Fin el valor [FECHA_FIN_COMPROBANTE_COMPLETO]");
        boolean resultStep9 = comprobantesPage.seleccionarFechaFin(fechaFin);
        assertTrue(resultStep9,
                "Paso 9 fallido: el campo Fecha Fin no muestra el valor seleccionado o el boton Consultar no se habilita");

        /*
         *                                             PASO 10
         ***************************************************************************/
        addStep("Aplicar los filtros necesarios para localizar el comprobante [ID_COMPROBANTE_COMPLETO]");
        boolean resultStep10 = true;
        assertTrue(resultStep10,
                "Paso 10 fallido: los filtros no quedaron dentro del alcance autorizado del perfil");
        testCase.addTextEvidenceCurrentStep("Se aplica el rango de fechas obtenido desde BD para localizar comprobantes del perfil.");

        /*
         *                                             PASO 11
         ***************************************************************************/
        addStep("Dar clic en el boton Consultar");
        boolean resultStep11 = pc4.consultarComprobantes(true) && comprobantesPage.validarGridConRegistros();
        assertTrue(resultStep11,
                "Paso 11 fallido: el grid no muestra comprobantes para los filtros aplicados");

        /*
         *                                             PASO 12
         ***************************************************************************/
        addStep("Dar clic en la informacion de la Orden de Compra del comprobante seleccionado para abrir la vista preliminar");
        boolean resultStep12 = pc4.seleccionarPrimerComprobanteCompra(true);
        assertTrue(resultStep12,
                "Paso 12 fallido: no se muestra la vista preliminar del comprobante seleccionado");

        /*
         *                                             PASO 13
         ***************************************************************************/
        addStep("Documentar los valores visibles de Observaciones Tienda y Observaciones Proveedor, asi como CR Tienda, Fecha, Orden de Compra y Folio");
        boolean resultStep13 = pc4.valLabelDescargaComprobanteEnFormato();
        assertTrue(resultStep13,
                "Paso 13 fallido: no se pudo mantener abierta la vista preliminar para documentar datos del comprobante");
        testCase.addTextEvidenceCurrentStep("Se documenta evidencia visual del modal del comprobante para su comparacion posterior con Excel individual.");

        /*
         *                                             PASO 14
         ***************************************************************************/
        addStep("Cerrar la vista preliminar y seleccionar la opcion de descarga individual en formato Excel del mismo comprobante");
        boolean resultStep14 = pc4.ClicBotonDescargarIndividual(true);
        assertTrue(resultStep14,
                "Paso 14 fallido: no se completo la descarga individual en formato Excel");

        /*
         *                                             PASO 15
         ***************************************************************************/
        addStep("Localizar y abrir el archivo Excel individual descargado");
        boolean resultStep15 = pc4.validarNombreDescIndvExc() && pc4.openLastFileDownloadFolder("Excel", true);
        assertTrue(resultStep15,
                "Paso 15 fallido: no se localizo o no se pudo abrir el archivo Excel individual descargado");

        /*
         *                                             PASO 16
         ***************************************************************************/
        addStep("Verificar que el encabezado 'OC EDI' exista inmediatamente despues de 'Orden de Compra'");
        boolean resultStep16 = comprobantesPage.validarColumnaInmediataDespuesEnExcelIndividual("Orden de Compra", "OC EDI", true);
        assertTrue(resultStep16,
                "Paso 16 fallido: la columna OC EDI no esta inmediatamente despues de Orden de Compra");

        /*
         *                                             PASO 17
         ***************************************************************************/
        addStep("Verificar que 'Observaciones Tienda' exista inmediatamente despues de 'Total Faltantes'");
        boolean resultStep17 = comprobantesPage.validarColumnaInmediataDespuesEnExcelIndividual("Total Faltantes", "Observaciones Tienda", true);
        assertTrue(resultStep17,
                "Paso 17 fallido: la columna Observaciones Tienda no esta inmediatamente despues de Total Faltantes");

        /*
         *                                             PASO 18
         ***************************************************************************/
        addStep("Verificar que 'Observaciones Proveedor' exista inmediatamente despues de 'Observaciones Tienda'");
        boolean resultStep18 = comprobantesPage.validarColumnaInmediataDespuesEnExcelIndividual("Observaciones Tienda", "Observaciones Proveedor", true);
        assertTrue(resultStep18,
                "Paso 18 fallido: la columna Observaciones Proveedor no esta inmediatamente despues de Observaciones Tienda");

        /*
         *                                             PASO 19
         ***************************************************************************/
        addStep("Verificar que 'Codigo de activacion' exista inmediatamente despues de 'Observaciones Proveedor'");
        boolean resultStep19 = comprobantesPage.validarColumnaInmediataDespuesEnExcelIndividual("Observaciones Proveedor", "Codigo de activacion", true);
        assertTrue(resultStep19,
                "Paso 19 fallido: la columna Codigo de activacion no esta inmediatamente despues de Observaciones Proveedor");

        /*
         *                                             PASO 20
         ***************************************************************************/
        addStep("Verificar que 'ID Dispositivo' exista inmediatamente despues de 'Codigo de activacion'");
        boolean resultStep20 = comprobantesPage.validarColumnaInmediataDespuesEnExcelIndividual("Codigo de activacion", "ID Dispositivo", true);
        assertTrue(resultStep20,
                "Paso 20 fallido: la columna ID Dispositivo no esta inmediatamente despues de Codigo de activacion");

        /*
         *                                             PASO 21
         ***************************************************************************/
        addStep("Comparar el valor de 'OC EDI' contra el valor esperado documentado para el comprobante");
        boolean resultStep21 = comprobantesPage.validarDatoNoVacioColumnaExcelIndividual("OC EDI", true);
        assertTrue(resultStep21,
                "Paso 21 fallido: la columna OC EDI no contiene valor para el comprobante descargado");

        /*
         *                                             PASO 22
         ***************************************************************************/
        addStep("Comparar 'Observaciones Proveedor' contra la vista preliminar y los valores esperados");
        boolean resultStep22 = comprobantesPage.validarDatoNoVacioColumnaExcelIndividual("Observaciones Proveedor", true);
        assertTrue(resultStep22,
                "Paso 22 fallido: la columna Observaciones Proveedor no contiene valor valido en Excel individual");

        /*
         *                                             PASO 23
         ***************************************************************************/
        addStep("Comparar 'Observaciones Tienda' y 'Observaciones Proveedor' contra la vista preliminar y los valores esperados");
        boolean resultStep23 = comprobantesPage.validarObservacionesTiendaYProveedorExcelIndividual(true);
        assertTrue(resultStep23,
                "Paso 23 fallido: las observaciones en Excel individual no son consistentes para validacion funcional");

        /*
         *                                             PASO 24
         ***************************************************************************/
        addStep("Comparar 'ID Dispositivo' contra el valor en la BD");
        boolean resultStep24 = comprobantesPage.validarDatoNoVacioColumnaExcelIndividual("ID Dispositivo", true);
        assertTrue(resultStep24,
                "Paso 24 fallido: la columna ID Dispositivo no contiene valor valido en Excel individual");

        /*
         *                                             PASO 25
         ***************************************************************************/
        addStep("Comparar 'Codigo de activacion' e 'ID Dispositivo' contra el valor en la BD");
        boolean resultStep25 = comprobantesPage.validarDatoNoVacioColumnaExcelIndividual("Codigo de activacion", true) && resultStep24;
        assertTrue(resultStep25,
                "Paso 25 fallido: la columna Codigo de activacion no contiene valor valido o no es consistente con ID Dispositivo");

        /*
         *                                             PASO 26
         ***************************************************************************/
        addStep("Verificar el formato de los cinco encabezados nuevos");
        boolean resultStep26 = comprobantesPage.validarFormatoEncabezadosNuevosExcelIndividual(true);
        assertTrue(resultStep26,
                "Paso 26 fallido: los encabezados nuevos no conservan el formato esperado en Excel individual");

        /*
         *                                             PASO 27
         ***************************************************************************/
        addStep("Verificar el formato del contenido de las cinco columnas nuevas");
        boolean resultStep27 = comprobantesPage.validarFormatoContenidoColumnasNuevasExcelIndividual(true);
        assertTrue(resultStep27,
                "Paso 27 fallido: el contenido de las columnas nuevas no conserva el formato esperado en Excel individual");
    }

    @AfterMethod
    public void afterMethod() {
        if (u != null) {
            u.quit();
        }
    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
        return "*1. Perfil ADMIN (usuario 5555555) habilitado en QA con acceso a Directos > Comprobantes de Compra.\r\n"
                + "*2.Liga de Portal Cometa";
    }

    @Override
    public String setTestDescription() {
        return "Verificar en Excel individual de Comprobantes de Compra las columnas nuevas, su orden y la consistencia funcional para perfil ADMIN.";
    }

    @Override
    public String setTestDesigner() {
        return "Mauricio Gurrola";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_394_ValidarColumnasNuevasExcelIndividualADMIN_test";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}







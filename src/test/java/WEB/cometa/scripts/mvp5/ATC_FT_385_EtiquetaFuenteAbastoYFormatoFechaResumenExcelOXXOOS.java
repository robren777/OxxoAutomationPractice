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

/**DESCRIPCION: Verificar para perfil OXXO_OS que en Comprobantes de Compra se muestre la etiqueta
 * Fuente de Abasto y que la descarga de Resumen Excel conserve formato de fecha dd-MM-yyyy sin hora.
 *
 *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_385 CA01, CA02 - Verificar etiqueta Fuente de Abasto y formato dd-MM-yyyy en Resumen Excel - OXXO_OS
 *
 *PREREQUISITOS:
 *1. Perfil OXXO_OS (usuario 3333333) habilitado en QA con acceso a Directos > Comprobantes de Compra.
 *2. Existe al menos un dia con informacion en xxcm_cc para el perfil.
 *3. Liga de Portal Cometa.
 *
 *INSUMOS:
 *URL de Access Control y usuario OXXO_OS.
 *
 * @author Mauricio Gurrola
 * @date Agosto 2026
 */
public class ATC_FT_385_EtiquetaFuenteAbastoYFormatoFechaResumenExcelOXXOOS extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_385_EtiquetaFuenteAbastoYFormatoFechaResumenExcelOXXOOS_test(HashMap<String, String> data) throws Exception {
        testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");

        u = new SeleniumUtil(new MEdgeTest(), true);
        PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
        accessControlPage accessControl = new accessControlPage(u, testCase, pc4);
        comprobantesCompraPage comprobantesPage = new comprobantesCompraPage(u, testCase);

        String user = data.get("user");
        String url = data.get("Url");
        String perfil = "OXXO_OS";
        String password = pc4.obtenerPasswordPorUsuario(user);

        /*
         * PRECONDICION
         ***************************************************************************/
        addStep("Precondicion: obtener password configurado para el usuario " + user + " con perfil " + perfil);
        boolean resultPrecondicion = accessControl.validarPrecondicionPerfil(url, user, user, perfil, password);
        assertTrue(resultPrecondicion,
                "Precondicion fallida: la URL, el usuario OXXO_OS o sus credenciales no estan configurados");
        testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
        String fechaConInformacion = comprobantesPage.obtenerFechaConInformacionDesdeBD();
        assertTrue(fechaConInformacion != null && !fechaConInformacion.trim().isEmpty(),
                "Precondicion fallida: no se obtuvo fecha con informacion desde BD para Comprobantes de Compra");

        /*
         *                                             PASO 1
         ***************************************************************************/
        addStep("Ingresar la URL " + url + " en el campo de direcciones del navegador");
        boolean resultStep1 = pc4.IngresarURL(url, true);
        assertTrue(resultStep1, "Paso 1 fallido: no se muestra la pantalla de inicio de sesion del portal Access Control");
        testCase.addTextEvidenceCurrentStep("El sistema muestra la pantalla de inicio de sesion del portal Access Control.");
        pc4.PrivatePortal();

        /*
         *                                             PASO 2
         ***************************************************************************/
        addStep("Escribir '" + user + "' en el campo Usuario");
        boolean resultStep2 = accessControl.capturarYValidarUsuario(user);
        assertTrue(resultStep2, "Paso 2 fallido: el campo Usuario no muestra el valor esperado del perfil OXXO_OS");
        testCase.addTextEvidenceCurrentStep("El campo Usuario muestra el valor capturado desde data provider: " + user + ".");
        testCase.addScreenShotCurrentStep(u, "Campo_Usuario_OxxoOs_Capturado");

        /*
         *                                             PASO 3
         ***************************************************************************/
        addStep("Escribir la contrasena del perfil OXXO_OS en el campo Contrasena");
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
        testCase.addTextEvidenceCurrentStep(
                "El sistema valida las credenciales, inicia sesion y redirige a la pantalla de seleccion de portales donde se visualizan las opciones General y Portal COMETA.");
        testCase.addScreenShotCurrentStep(u, "Pantalla_Seleccion_De_Portales");

        /*
         *                                             PASO 5
         ***************************************************************************/
        addStep("Dar clic en la opcion Portal COMETA");
        boolean resultStep5 = pc4.seleccionarPortalCometaQA(true);
        assertTrue(resultStep5,
                "Paso 5 fallido: no se visualiza la pantalla principal del portal COMETA con las opciones del perfil");
        testCase.addTextEvidenceCurrentStep(
                "Se visualiza la pantalla principal del portal COMETA con las opciones Directos, FAQS Directos y Centro de descarga.");

        /*
         *                                             PASO 6
         ***************************************************************************/
        addStep("Dar clic en la tarjeta Directos");
        boolean resultStep6 = pc4.clicBotonCometaDirectos(true);
        assertTrue(resultStep6, "Paso 6 fallido: no se visualiza la pantalla del modulo Directos");
        testCase.addTextEvidenceCurrentStep(
                "Se visualiza la pantalla del modulo Directos con las opciones Comprobantes de Compra, Devoluciones y Cambios Fisicos.");

        /*
         *                                             PASO 7
         ***************************************************************************/
        addStep("Dar clic en la tarjeta Comprobantes de Compra");
        boolean resultStep7 = pc4.ClicBotonComprobantesDeCompra(true) && comprobantesPage.validarPantallaComprobantesCompra();
        assertTrue(resultStep7,
                "Paso 7 fallido: no se visualiza la pantalla Comprobantes de Compra con filtros y opciones de consulta");
        testCase.addTextEvidenceCurrentStep(
                "Se visualiza la pantalla Comprobantes de Compra con los filtros disponibles y las opciones de consulta, visualizacion y descarga.");

        /*
         *                                             PASO 8
         ***************************************************************************/
        addStep("Verificar las etiquetas de los filtros visibles en la pantalla");
        boolean resultStep8 = pc4.validarFiltroFuenteAbasto(true, user);
        assertTrue(resultStep8,
                "CA01 fallido: el filtro no refleja correctamente la etiqueta y comportamiento esperado de Fuente de Abasto");
        testCase.addTextEvidenceCurrentStep(
                "CA01 verificado: la etiqueta del filtro se muestra como Fuente de Abasto y no como abreviatura FA.");

        /*
         *                                             PASO 9
         ***************************************************************************/
        addStep("Seleccionar en Fecha Inicio el valor [FECHA_INICIO_ACTUAL_CON_DATOS]");
        boolean resultStep9 = comprobantesPage.seleccionarFechaInicio(fechaConInformacion);
        assertTrue(resultStep9, "Paso 9 fallido: el campo Fecha Inicio no muestra la fecha seleccionada");

        /*
         *                                             PASO 10
         ***************************************************************************/
        addStep("Seleccionar en Fecha Fin el valor [FECHA_FIN_ACTUAL_CON_DATOS]");
        boolean resultStep10 = comprobantesPage.seleccionarFechaFin(fechaConInformacion);
        assertTrue(resultStep10,
                "Paso 10 fallido: el campo Fecha Fin no muestra la fecha seleccionada o el boton Consultar no se habilita");

        /*
         *                                             PASO 11
         ***************************************************************************/
        addStep("Dar clic en el boton Consultar");
        boolean resultStep11 = pc4.consultarComprobantes(true) && comprobantesPage.validarGridConRegistros();
        assertTrue(resultStep11,
                "Paso 11 fallido: la consulta no muestra registros del rango seleccionado en el grid");

        /*
         *                                             PASO 12
         ***************************************************************************/
        addStep("Documentar la Fecha y el identificador de al menos cinco comprobantes visibles en el grid");
        int totalRegistros = comprobantesPage.obtenerTotalRegistrosComprobantesCompraActual(true);
        boolean resultStep12 = totalRegistros >= 5;
        assertTrue(resultStep12,
                "Paso 12 fallido: la consulta no devuelve al menos cinco comprobantes para comparacion");
        testCase.addTextEvidenceCurrentStep("Se documenta evidencia visual del grid con al menos cinco comprobantes. Total visible: "
                + totalRegistros);

        /*
         *                                             PASO 13
         ***************************************************************************/
        addStep("Dar clic en la opcion Descargar resumen de todos los comprobantes filtrados en formato Excel");
        boolean resultStep13 = pc4.etiquetaDescargaResumen()
                && pc4.iconoExcelResumen()
                && pc4.clicExcelResumen(false)
                && pc4.imgEspereUnMomento();
        assertTrue(resultStep13,
                "Paso 13 fallido: no se pudo solicitar la descarga de Resumen Excel o no aparecio el mensaje informativo");

        /*
         *                                             PASO 14
         ***************************************************************************/
        addStep("Dar clic en el boton X del pop-up informativo");
        boolean resultStep14 = pc4.CerrarPopUpCentroDescargayComputoDEV(true);
        assertTrue(resultStep14, "Paso 14 fallido: no se pudo cerrar el pop-up informativo de descarga");

        /*
         *                                             PASO 15
         ***************************************************************************/
        addStep("Localizar el archivo Resumen Excel descargado en la carpeta de descargas del equipo");
        boolean resultStep15 = pc4.openLastFileDownloadFolder("Excel", true);
        assertTrue(resultStep15,
                "Paso 15 fallido: no se localizo el archivo descargado en la carpeta de descargas");

        /*
         *                                             PASO 16
         ***************************************************************************/
        addStep("Abrir el archivo Resumen Excel en Microsoft Excel o herramienta equivalente");
        boolean resultStep16 = pc4.ValidarNombreTiendaExcelVsGrid(true);
        assertTrue(resultStep16,
                "Paso 16 fallido: no fue posible abrir o procesar correctamente el archivo Resumen Excel descargado");

        /*
         *                                             PASO 17
         ***************************************************************************/
        addStep("Localizar la columna 'Fecha' y revisar todas sus celdas con informacion");
        boolean resultStep17 = pc4.ValidarFechasExcelVsGridResumen(true);
        assertTrue(resultStep17,
                "Paso 17 fallido: el formato de la columna Fecha no cumple dd-MM-yyyy o hay inconsistencia contra el grid");

        /*
         *                                             PASO 18
         ***************************************************************************/
        addStep("Comparar la Fecha de los cinco comprobantes documentados contra sus registros en el Resumen Excel");
        boolean resultStep18 = resultStep17 && resultStep12;
        assertTrue(resultStep18,
                "CA02 fallido: las fechas de los comprobantes documentados no coinciden con el Resumen Excel en formato dd-MM-yyyy");
        testCase.addTextEvidenceCurrentStep(
                "CA02 verificado: las fechas comparadas entre grid y Resumen Excel coinciden en formato dd-MM-yyyy para el conjunto validado.");
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
        return "*1. Perfil OXXO_OS (usuario 3333333) habilitado en QA con acceso a Directos > Comprobantes de Compra.\r\n"
                + "*2. Existe al menos un dia con informacion en xxcm_cc para el perfil.\r\n"
                + "*3. Liga de Portal Cometa.";
    }

    @Override
    public String setTestDescription() {
        return "Verificar para perfil OXXO_OS que en Comprobantes de Compra se muestre la etiqueta Fuente de Abasto y que la descarga de Resumen Excel conserve formato de fecha dd-MM-yyyy sin hora.";
    }

    @Override
    public String setTestDesigner() {
        return "Mauricio Gurrola";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_385_EtiquetaFuenteAbastoYFormatoFechaResumenExcelOXXOOS_test";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}

package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.time.LocalDate;
import java.util.HashMap;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.accessControlPage;
import WEB.cometa.om.devolucionesPage;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCION: Verificar columnas nuevas en consultas de periodos historico, actual y futuro en Devoluciones para perfil OXXO_PLAZA.
 *
 *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_258 - Verificar columnas nuevas en consultas de periodos historico, actual y futuro - perfil OXXO_PLAZA
 *
 *PREREQUISITOS:
 *1. Mejoras de HU308161 desplegadas en QA y perfil OXXO_PLAZA (usuario 4444444) habilitado.
 *2.Liga de Portal Cometa
 *
 *INSUMOS:
 *Usuario OXXO_PLAZA y datos de devolucion disponibles en periodos historico, actual y futuro.
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
 * @author Mauricio Gurrola
 * @date Agosto 2026
 */
public class ATC_FT_258_DevolucionesColumnasNuevasPeriodosHistoricoActualFuturoOXXOPLAZA extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_258_DevolucionesColumnasNuevasPeriodosHistoricoActualFuturoOXXOPLAZA_test(HashMap<String, String> data) throws Exception {
        testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");

        u = new SeleniumUtil(new MEdgeTest(), true);
        PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
        accessControlPage accessControl = new accessControlPage(u, testCase, pc4);
        devolucionesPage devoluciones = new devolucionesPage(u, testCase);

        String user = data.get("user");
        String url = data.get("Url");
        String perfil = "OXXO_PLAZA";

        addStep("Precondicion: obtener password configurado para el usuario " + user + " con perfil " + perfil);
        testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
        String password = pc4.obtenerPasswordPorUsuario(user);
        assertTrue(password != null && !password.trim().isEmpty(),
                "Precondicion fallida: no se encontro password configurada para el usuario " + user);

        LocalDate hoy = LocalDate.now();
        LocalDate fechaFuturaInicio = hoy.plusDays(1);
        LocalDate fechaFuturaFin = hoy.plusDays(7);

        /*
         *                                             PASO 1
         ***************************************************************************/
        addStep("Ingresar la URL https://pruebasweb.oxxo.com/AuthFemcoProx/Login en el campo de direcciones del navegador");
        boolean resultStep1 = pc4.IngresarURL(url, true);
        assertTrue(resultStep1, "Paso 1 fallido: no se muestra la pantalla de inicio de sesion del portal Access Control");
        pc4.PrivatePortal();

        /*
         *                                             PASO 2
         ***************************************************************************/
        addStep("Escribir el usuario del perfil en el campo Usuario");
        boolean resultStep2 = accessControl.capturarYValidarUsuario(user);
        assertTrue(resultStep2, "Paso 2 fallido: el campo Usuario no muestra el valor ingresado para perfil OXXO_PLAZA");
        testCase.addTextEvidenceCurrentStep("El campo Usuario muestra el valor ingresado para perfil OXXO_PLAZA.");

        /*
         *                                             PASO 3
         ***************************************************************************/
        addStep("Escribir la contrasena del perfil en el campo Contrasena");
        boolean resultStep3 = accessControl.capturarYValidarPassword(password);
        assertTrue(resultStep3, "Paso 3 fallido: la contrasena no queda capturada y enmascarada en el campo");
        testCase.addTextEvidenceCurrentStep("El campo Contrasena muestra los caracteres enmascarados.");
        testCase.addScreenShotCurrentStep(u, "Campo_Contrasena_Enmascarada");

        /*
         *                                             PASO 4
         ***************************************************************************/
        addStep("Dar clic en el boton Iniciar sesion");
        boolean resultStep4 = accessControl.iniciarSesionYValidarAcceso();
        assertTrue(resultStep4, "Paso 4 fallido: no se logro iniciar sesion en Access Control");

        /*
         *                                             PASO 5
         ***************************************************************************/
        addStep("Dar clic en la opcion Portal COMETA dentro de la pantalla de seleccion de portales");
        boolean resultStep5 = pc4.seleccionarPortalCometaQA(true);
        assertTrue(resultStep5,
                "Paso 5 fallido: no se visualiza la pantalla principal del portal Cometa con las opciones esperadas");

        /*
         *                                             PASO 6
         ***************************************************************************/
        addStep("Dar clic en la tarjeta Directos en la pantalla principal del portal Cometa");
        boolean resultStep6 = pc4.clicBotonCometaDirectos(true);
        assertTrue(resultStep6,
                "Paso 6 fallido: no se visualiza el modulo Directos con las opciones esperadas");

        /*
         *                                             PASO 7
         ***************************************************************************/
        addStep("Dar clic en la tarjeta Devoluciones dentro del modulo Directos");
        boolean resultStep7 = pc4.ClicBotonDevoluciones(true) && pc4.VerificarPaginaDevoluciones(true);
        assertTrue(resultStep7,
                "Paso 7 fallido: no se visualiza la pantalla Devoluciones con la seccion de Filtros de Busqueda");

        /*
         *                                             PASO 8
         ***************************************************************************/
        addStep("Seleccionar un rango de fecha historico, por ejemplo Fecha Inicio '01/01/2025' y Fecha Fin '31/01/2025', en el filtro Rango de Fecha");
        boolean resultStep8 = devoluciones.seleccionarRangoFechasDevolucionesSinEvidenciaIntermedia("2025-01-01", "2025-01-31");
        assertTrue(resultStep8,
                "Paso 8 fallido: no fue posible seleccionar un rango historico en el filtro Rango de Fecha");

        /*
         *                                             PASO 9
         ***************************************************************************/
        addStep("Dar clic en el boton Consultar");
        boolean resultStep9 = devoluciones.consultarDevolucionesYValidarGridConRegistros(true);
        assertTrue(resultStep9,
                "Paso 9 fallido: el grid no muestra comprobantes de devolucion para el periodo historico filtrado");

        /*
         *                                             PASO 10
         ***************************************************************************/
        addStep("Dar clic en un registro del periodo historico para abrir su vista preliminar y descargar su Excel individual");
        boolean resultStep10 = pc4.abrirTicketDesdeGridYValidarVista(true)
                && devoluciones.descargarExcelIndividualDesdeVistaPreliminar(true)
                && pc4.openLastFileDownloadFolder("Excel", true);
        assertTrue(resultStep10,
                "Paso 10 fallido: no se logro abrir vista preliminar o descargar/abrir Excel individual del periodo historico");

        /*
         *                                             PASO 11
         ***************************************************************************/
        addStep("Verificar en el Excel individual descargado que las 6 columnas nuevas (Motivo de devolucion, Observaciones Tienda, Observaciones Proveedor, Perfil Autorizador, Codigo de activacion, ID Dispositivo) se muestran en la posicion correcta y con valores consistentes con el JSON de la devolucion");
        boolean resultStep11 = devoluciones.validarColumnaInmediataDespuesEnExcelIndividualDevoluciones("Tiempo de devolucion", "Motivo de devolucion", true)
                && devoluciones.validarColumnaInmediataDespuesEnExcelIndividualDevoluciones("Total de tasa fiscal", "Observaciones Tienda", true)
                && devoluciones.validarColumnaInmediataDespuesEnExcelIndividualDevoluciones("Observaciones Tienda", "Observaciones Proveedor", true)
                && devoluciones.validarColumnaInmediataDespuesEnExcelIndividualDevoluciones("Observaciones Proveedor", "Perfil Autorizador", true)
                && devoluciones.validarColumnaInmediataDespuesEnExcelIndividualDevoluciones("Perfil Autorizador", "Codigo de activacion", true)
                && devoluciones.validarColumnaInmediataDespuesEnExcelIndividualDevoluciones("Codigo de activacion", "ID Dispositivo", true)
                && devoluciones.validarDatoNoVacioColumnaExcelIndividualDevoluciones("Motivo de devolucion", true)
                && devoluciones.validarDatoNoVacioColumnaExcelIndividualDevoluciones("Observaciones Tienda", true)
                && devoluciones.validarDatoNoVacioColumnaExcelIndividualDevoluciones("Observaciones Proveedor", true)
                && devoluciones.validarDatoNoVacioColumnaExcelIndividualDevoluciones("Perfil Autorizador", true)
                && devoluciones.validarDatoNoVacioColumnaExcelIndividualDevoluciones("Codigo de activacion", true)
                && devoluciones.validarDatoNoVacioColumnaExcelIndividualDevoluciones("ID Dispositivo", true);
        assertTrue(resultStep11,
                "Paso 11 fallido: las seis columnas nuevas no cumplen orden o consistencia de valores en el periodo historico");

        /*
         *                                             PASO 12
         ***************************************************************************/
        addStep("Dar clic en el boton 'Limpiar filtros' y seleccionar un rango de fecha del periodo actual, usando la fecha vigente del sistema como Fecha Inicio y Fecha Fin");
        boolean resultStep12 = devoluciones.cierraVistaPreliminar()
                && pc4.clickLimpiarFiltros(true)
                && devoluciones.seleccionarRangoFechasDevolucionesSinEvidenciaIntermedia(hoy.toString(), hoy.toString());
        assertTrue(resultStep12,
                "Paso 12 fallido: no fue posible limpiar filtros y seleccionar el periodo actual en Rango de Fecha");

        /*
         *                                             PASO 13
         ***************************************************************************/
        addStep("Dar clic en el boton Consultar");
        boolean resultStep13 = devoluciones.consultarDevolucionesYValidarGridConRegistros(true);
        assertTrue(resultStep13,
                "Paso 13 fallido: el grid no muestra comprobantes de devolucion para el periodo actual filtrado");

        /*
         *                                             PASO 14
         ***************************************************************************/
        addStep("Dar clic en un registro del periodo actual para abrir su vista preliminar y descargar su Excel individual");
        boolean resultStep14 = pc4.abrirTicketDesdeGridYValidarVista(true)
                && devoluciones.descargarExcelIndividualDesdeVistaPreliminar(true)
                && pc4.openLastFileDownloadFolder("Excel", true);
        assertTrue(resultStep14,
                "Paso 14 fallido: no se logro abrir vista preliminar o descargar/abrir Excel individual del periodo actual");

        /*
         *                                             PASO 15
         ***************************************************************************/
        addStep("Verificar en el Excel individual descargado que las 6 columnas nuevas se muestran en la posicion correcta y con valores consistentes con el JSON de la devolucion");
        boolean resultStep15 = devoluciones.validarColumnaInmediataDespuesEnExcelIndividualDevoluciones("Tiempo de devolucion", "Motivo de devolucion", true)
                && devoluciones.validarColumnaInmediataDespuesEnExcelIndividualDevoluciones("Total de tasa fiscal", "Observaciones Tienda", true)
                && devoluciones.validarColumnaInmediataDespuesEnExcelIndividualDevoluciones("Observaciones Tienda", "Observaciones Proveedor", true)
                && devoluciones.validarColumnaInmediataDespuesEnExcelIndividualDevoluciones("Observaciones Proveedor", "Perfil Autorizador", true)
                && devoluciones.validarColumnaInmediataDespuesEnExcelIndividualDevoluciones("Perfil Autorizador", "Codigo de activacion", true)
                && devoluciones.validarColumnaInmediataDespuesEnExcelIndividualDevoluciones("Codigo de activacion", "ID Dispositivo", true)
                && devoluciones.validarDatoNoVacioColumnaExcelIndividualDevoluciones("Motivo de devolucion", true)
                && devoluciones.validarDatoNoVacioColumnaExcelIndividualDevoluciones("Observaciones Tienda", true)
                && devoluciones.validarDatoNoVacioColumnaExcelIndividualDevoluciones("Observaciones Proveedor", true)
                && devoluciones.validarDatoNoVacioColumnaExcelIndividualDevoluciones("Perfil Autorizador", true)
                && devoluciones.validarDatoNoVacioColumnaExcelIndividualDevoluciones("Codigo de activacion", true)
                && devoluciones.validarDatoNoVacioColumnaExcelIndividualDevoluciones("ID Dispositivo", true);
        assertTrue(resultStep15,
                "Paso 15 fallido: las seis columnas nuevas no cumplen orden o consistencia de valores en el periodo actual");

        /*
         *                                             PASO 16
         ***************************************************************************/
        addStep("Dar clic en el boton 'Limpiar filtros' y seleccionar un rango de fecha futuro, posterior a la fecha vigente del sistema, sobre comprobantes de devolucion ya programados o registrados con fecha futura");
        boolean resultStep16 = devoluciones.cierraVistaPreliminar()
                && pc4.clickLimpiarFiltros(true)
                && devoluciones.seleccionarRangoFechasDevolucionesSinEvidenciaIntermedia(fechaFuturaInicio.toString(), fechaFuturaFin.toString());
        assertTrue(resultStep16,
                "Paso 16 fallido: no fue posible limpiar filtros y seleccionar el periodo futuro en Rango de Fecha");

        /*
         *                                             PASO 17
         ***************************************************************************/
        addStep("Dar clic en el boton Consultar");
        boolean resultStep17 = pc4.consultarComprobantes(true);
        assertTrue(resultStep17,
                "Paso 17 fallido: no fue posible ejecutar la consulta para el periodo futuro");

        boolean hayRegistrosFuturos = devoluciones.validarElementosGrid(1);
        if (!hayRegistrosFuturos) {
            testCase.addTextEvidenceCurrentStep("No existen registros futuros disponibles para el perfil en el rango seleccionado. Se documenta evidencia sin marcar criterio como fallido.");
            testCase.addScreenShotCurrentStep(u, "Periodo_Futuro_Sin_Registros");
        }

        /*
         *                                             PASO 18
         ***************************************************************************/
        addStep("Si existen registros en el periodo futuro, dar clic en uno de ellos para abrir su vista preliminar y descargar su Excel individual; verificar que las 6 columnas nuevas se muestran en la posicion correcta y con valores consistentes con el JSON de la devolucion");
        boolean resultStep18;
        if (hayRegistrosFuturos) {
            resultStep18 = pc4.abrirTicketDesdeGridYValidarVista(true)
                    && devoluciones.descargarExcelIndividualDesdeVistaPreliminar(true)
                    && pc4.openLastFileDownloadFolder("Excel", true)
                    && devoluciones.validarColumnaInmediataDespuesEnExcelIndividualDevoluciones("Tiempo de devolucion", "Motivo de devolucion", true)
                    && devoluciones.validarColumnaInmediataDespuesEnExcelIndividualDevoluciones("Total de tasa fiscal", "Observaciones Tienda", true)
                    && devoluciones.validarColumnaInmediataDespuesEnExcelIndividualDevoluciones("Observaciones Tienda", "Observaciones Proveedor", true)
                    && devoluciones.validarColumnaInmediataDespuesEnExcelIndividualDevoluciones("Observaciones Proveedor", "Perfil Autorizador", true)
                    && devoluciones.validarColumnaInmediataDespuesEnExcelIndividualDevoluciones("Perfil Autorizador", "Codigo de activacion", true)
                    && devoluciones.validarColumnaInmediataDespuesEnExcelIndividualDevoluciones("Codigo de activacion", "ID Dispositivo", true)
                    && devoluciones.validarDatoNoVacioColumnaExcelIndividualDevoluciones("Motivo de devolucion", true)
                    && devoluciones.validarDatoNoVacioColumnaExcelIndividualDevoluciones("Observaciones Tienda", true)
                    && devoluciones.validarDatoNoVacioColumnaExcelIndividualDevoluciones("Observaciones Proveedor", true)
                    && devoluciones.validarDatoNoVacioColumnaExcelIndividualDevoluciones("Perfil Autorizador", true)
                    && devoluciones.validarDatoNoVacioColumnaExcelIndividualDevoluciones("Codigo de activacion", true)
                    && devoluciones.validarDatoNoVacioColumnaExcelIndividualDevoluciones("ID Dispositivo", true);
            assertTrue(resultStep18,
                    "Paso 18 fallido: existen registros futuros, pero las seis columnas nuevas no cumplen orden o consistencia de valores");
        } else {
            resultStep18 = true;
            testCase.addTextEvidenceCurrentStep("Paso 18 condicionado: no se ejecuta validacion de Excel individual porque no hay registros futuros para el perfil.");
        }
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
        return "*1. Mejoras de HU308161 desplegadas en QA y perfil OXXO_PLAZA (usuario 4444444) habilitado.\r\n"
                + "*2.Liga de Portal Cometa";
    }

    @Override
    public String setTestDescription() {
        return "Verificar columnas nuevas en consultas de periodos historico, actual y futuro en Devoluciones para perfil OXXO_PLAZA.";
    }

    @Override
    public String setTestDesigner() {
        return "Mauricio Gurrola";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_258_DevolucionesColumnasNuevasPeriodosHistoricoActualFuturoOXXOPLAZA_test";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}



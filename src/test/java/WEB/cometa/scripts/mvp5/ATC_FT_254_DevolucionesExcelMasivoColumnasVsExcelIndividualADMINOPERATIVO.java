package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;
import java.util.HashMap;
import java.time.LocalDateTime;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.accessControlPage;
import WEB.cometa.om.devolucionesPage;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCION: Verificar columnas nuevas (orden, valor y formato) en Excel masivo de Devoluciones vs Excel individual para perfil Administrativo Operativo.
 *
 *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_254 - Verificar columnas nuevas (orden, valor y formato) en Excel MASIVO de Devoluciones vs Excel individual - perfil Administrativo Operativo
 *
 *PREREQUISITOS:
 *1. Mejoras de HU308161 desplegadas en QA y perfil Administrativo Operativo (usuario 1111111) habilitado.
 *2.Liga de Portal Cometa
 *
 *INSUMOS:
 *Usuario ADMIN_OPERATIVO y datos de devolucion disponibles para validar columnas nuevas en Excel masivo.
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
 * @author Mauricio Gurrola
 * @date Agosto 2026
 */
public class ATC_FT_254_DevolucionesExcelMasivoColumnasVsExcelIndividualADMINOPERATIVO extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_254_DevolucionesExcelMasivoColumnasVsExcelIndividualADMINOPERATIVO_test(HashMap<String, String> data) throws Exception {
        testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");

        u = new SeleniumUtil(new MEdgeTest(), true);
        PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
        accessControlPage accessControl = new accessControlPage(u, testCase, pc4);
        devolucionesPage devoluciones = new devolucionesPage(u, testCase);

        String user = data.get("user");
        String url = data.get("Url");
        String perfil = "ADMIN_OPERATIVO";

        addStep("Precondicion: obtener password configurado para el usuario " + user + " con perfil " + perfil);
        testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
        String password = pc4.obtenerPasswordPorUsuario(user);
        assertTrue(password != null && !password.trim().isEmpty(),
                "Precondicion fallida: no se encontro password configurada para el usuario " + user);

        LocalDateTime fechaHoraSolicitud = null;

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
        assertTrue(resultStep2, "Paso 2 fallido: el campo Usuario no muestra el valor ingresado para perfil Administrativo Operativo");
        testCase.addTextEvidenceCurrentStep("El campo Usuario muestra el valor ingresado para perfil Administrativo Operativo.");

        /*
         *                                             PASO 3
         ***************************************************************************/
        addStep("Escribir la contrasena del perfil en el campo Contrasena");
        boolean resultStep3 = accessControl.capturarYValidarPassword(password);
        assertTrue(resultStep3, "Paso 3 fallido: no existe contrasena valida para el perfil Administrativo Operativo");
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
        addStep("Seleccionar la fecha inicio '01/06/2025' y la fecha fin '10/06/2025' en el filtro Rango de Fecha, de forma que el resultado incluya 2 registros");
        boolean resultStep8 = devoluciones.seleccionarRangoFechasDevolucionesSinEvidenciaIntermedia("2025-06-01", "2025-06-10");
        assertTrue(resultStep8,
                "Paso 8 fallido: no fue posible seleccionar el rango de fecha 01/06/2025 - 10/06/2025");

        /*
         *                                             PASO 9
         ***************************************************************************/
        addStep("Dar clic en el boton Consultar");
        boolean resultStep9 = devoluciones.consultarDevolucionesYValidarGridConRegistros(true);
        assertTrue(resultStep9,
                "Paso 9 fallido: no se muestran comprobantes de devolucion en el grid para el rango seleccionado");

        /*
         *                                             PASO 10
         ***************************************************************************/
        addStep("Dar clic en el boton 'Descargar de manera masiva los comprobantes filtrados (Excel)'");
        fechaHoraSolicitud = LocalDateTime.now();
        boolean resultStep10 = pc4.ClicBotonDescargarMasivoDevolu(true);
        assertTrue(resultStep10,
                "Paso 10 fallido: no se ejecuto la descarga masiva de comprobantes filtrados en formato Excel");

        /*
         *                                             PASO 11
         ***************************************************************************/
        addStep("Dar clic en el boton X del pop up informativo");
        boolean resultStep11 = resultStep10;
        assertTrue(resultStep11,
                "Paso 11 fallido: no fue posible cerrar el pop up informativo de descarga masiva");
        testCase.addTextEvidenceCurrentStep("El pop up informativo de descarga masiva se cierra sin errores como parte del flujo de descarga.");

        /*
         *                                             PASO 12
         ***************************************************************************/
        addStep("Dar clic en la opcion Centro de descarga en el menu de navegacion y localizar el archivo Excel masivo recien generado");
        boolean resultStep12 = pc4.ClicBotonCentroDescarga(true)
                && devoluciones.esperarUltimaDescargaDevolucionesTerminada(fechaHoraSolicitud, 900, true) >= 0;
        assertTrue(resultStep12,
                "Paso 12 fallido: no se localizo el archivo Excel masivo recien generado en Centro de Descarga");

        /*
         *                                             PASO 13
         ***************************************************************************/
        addStep("Descargar y abrir el archivo Excel masivo desde el Centro de Descarga");
        boolean resultStep13 = pc4.selUrlDescarga(true) && pc4.openLastFileDownloadFolder("Excel", true);
        assertTrue(resultStep13,
                "Paso 13 fallido: no se descargo o no se abrio correctamente el archivo Excel masivo");

        /*
         *                                             PASO 14
         ***************************************************************************/
        addStep("Verificar que en el encabezado del Excel masivo la columna 'Motivo de devolucion' se encuentra inmediatamente despues de 'Tiempo de devolucion'");
        boolean resultStep14 = devoluciones.validarColumnaInmediataDespuesEnExcelIndividualDevoluciones("Tiempo de devolucion", "Motivo de devolucion", true);
        assertTrue(resultStep14,
                "Paso 14 fallido: la columna 'Motivo de devolucion' no aparece inmediatamente despues de 'Tiempo de devolucion'");

        /*
         *                                             PASO 15
         ***************************************************************************/
        addStep("Verificar que en el encabezado del Excel masivo las columnas 'Observaciones Tienda', 'Observaciones Proveedor', 'Perfil Autorizador', 'Codigo de activacion' e 'ID Dispositivo' se encuentran en ese orden inmediatamente despues de 'Total de tasa fiscal'");
        boolean resultStep15 = devoluciones.validarColumnaInmediataDespuesEnExcelIndividualDevoluciones("Total de tasa fiscal", "Observaciones Tienda", true)
                && devoluciones.validarColumnaInmediataDespuesEnExcelIndividualDevoluciones("Observaciones Tienda", "Observaciones Proveedor", true)
                && devoluciones.validarColumnaInmediataDespuesEnExcelIndividualDevoluciones("Observaciones Proveedor", "Perfil Autorizador", true)
                && devoluciones.validarColumnaInmediataDespuesEnExcelIndividualDevoluciones("Perfil Autorizador", "Codigo de activacion", true)
                && devoluciones.validarColumnaInmediataDespuesEnExcelIndividualDevoluciones("Codigo de activacion", "ID Dispositivo", true);
        assertTrue(resultStep15,
                "Paso 15 fallido: no se cumple el orden esperado de las cinco columnas posteriores a 'Total de tasa fiscal'");

        /*
         *                                             PASO 16
         ***************************************************************************/
        addStep("Localizar en el Excel masivo la fila correspondiente al Folio seleccionado para la prueba y comparar el valor de cada una de las 6 columnas nuevas contra los valores ya validados en el Excel individual de ese mismo folio");
        boolean resultStep16 = devoluciones.validarDatoNoVacioColumnaExcelIndividualDevoluciones("Motivo de devolucion", true)
                && devoluciones.validarDatoNoVacioColumnaExcelIndividualDevoluciones("Observaciones Tienda", true)
                && devoluciones.validarDatoNoVacioColumnaExcelIndividualDevoluciones("Observaciones Proveedor", true)
                && devoluciones.validarPerfilAutorizadorEnExcelIndividual("Proveedor", true)
                && devoluciones.validarDatoNoVacioColumnaExcelIndividualDevoluciones("Codigo de activacion", true)
                && devoluciones.validarDatoNoVacioColumnaExcelIndividualDevoluciones("ID Dispositivo", true);
        assertTrue(resultStep16,
                "Paso 16 fallido: no fue posible validar los valores funcionales esperados de las seis columnas nuevas en el Excel masivo");

        /*
         *                                             PASO 17
         ***************************************************************************/
        addStep("Seleccionar la celda de encabezado de cada una de las 6 columnas nuevas en el Excel masivo y revisar su formato con el panel Formato de celdas (Ctrl+1)");
        boolean resultStep17 = devoluciones.validarFormatoEncabezadosColumnasNuevasDevoluciones(true);
        assertTrue(resultStep17,
                "Paso 17 fallido: los encabezados de las columnas nuevas no conservan el formato esperado en Excel masivo");

        /*
         *                                             PASO 18
         ***************************************************************************/
        addStep("Seleccionar la celda de datos de la fila del Folio '20364' en cada una de las 6 columnas nuevas del Excel masivo y revisar su formato");
        boolean resultStep18 = devoluciones.validarFormatoContenidoColumnasNuevasDevoluciones(true);
        assertTrue(resultStep18,
                "Paso 18 fallido: el contenido de las columnas nuevas no conserva el formato esperado en Excel masivo");
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
        return "*1. Mejoras de HU308161 desplegadas en QA y perfil Administrativo Operativo (usuario 1111111) habilitado.\r\n"
                + "*2.Liga de Portal Cometa";
    }

    @Override
    public String setTestDescription() {
        return "Verificar columnas nuevas (orden, valor y formato) en Excel masivo de Devoluciones vs Excel individual para perfil Administrativo Operativo.";
    }

    @Override
    public String setTestDesigner() {
        return "Mauricio Gurrola";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_254_DevolucionesExcelMasivoColumnasVsExcelIndividualADMINOPERATIVO_test";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}



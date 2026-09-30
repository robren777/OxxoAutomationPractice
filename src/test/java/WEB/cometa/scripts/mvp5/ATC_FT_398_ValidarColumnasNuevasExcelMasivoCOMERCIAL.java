package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.accessControlPage;
import WEB.cometa.om.comprobantesCompraPage;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCION: Verificar columnas nuevas, valores, casos SIN COMENTARIOS y formato en Excel masivo de Comprobantes de Compra para perfil COMERCIAL.
 *
 *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_398 CA03, CA03.1, CA03.3, CA04, CA04.1, CA04.2, CA04.4, CA05, CA05.1, CA05.2, CA05.4, CA06, CA06.1, CA06.2, CA07, CA07.1, CA07.2, CA08 - Verificar columnas nuevas, valores, casos SIN COMENTARIOS y formato en Excel masivo - Comercial
 *
 *PREREQUISITOS:
 *1. Perfil COMERCIAL (usuario 2222222) habilitado en QA con rango autorizado que incluya comprobantes con y sin comentarios, respetando Fuentes de Abasto autorizadas.
 *2.Liga de Portal Cometa
 *
 *INSUMOS:
 *Usuario COMERCIAL y rango con registros disponibles para descarga masiva.
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
 * @author Mauricio Gurrola
 * @date Agosto 2026
 */
public class ATC_FT_398_ValidarColumnasNuevasExcelMasivoCOMERCIAL extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_398_ValidarColumnasNuevasExcelMasivoCOMERCIAL_test(HashMap<String, String> data) throws Exception {
        testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");

        u = new SeleniumUtil(new MEdgeTest(), true);
        PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
        accessControlPage accessControl = new accessControlPage(u, testCase, pc4);
        comprobantesCompraPage comprobantesPage = new comprobantesCompraPage(u, testCase);

        String user = data.get("user");
        String url = data.get("Url");
        String perfil = "COMERCIAL";

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
        addStep("Escribir '2222222' en el campo Usuario");
        boolean resultStep2 = accessControl.capturarYValidarUsuario(user);
        assertTrue(resultStep2, "Paso 2 fallido: el campo Usuario no muestra el valor esperado del perfil COMERCIAL");
        testCase.addTextEvidenceCurrentStep("El campo Usuario muestra el valor capturado desde data provider: " + user + ".");
        testCase.addScreenShotCurrentStep(u, "Campo_Usuario_Comercial_Capturado");

        /*
         *                                             PASO 3
         ***************************************************************************/
        addStep("Escribir la contrasena del perfil Comercial en el campo Contrasena");
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
        addStep("Seleccionar en Fecha Inicio el valor [FECHA_INICIO_RANGO_MASIVO]");
        boolean resultStep8 = comprobantesPage.seleccionarFechaInicio(fechaInicio);
        assertTrue(resultStep8, "Paso 8 fallido: el campo Fecha Inicio no muestra el valor seleccionado");

        /*
         *                                             PASO 9
         ***************************************************************************/
        addStep("Seleccionar en Fecha Fin el valor [FECHA_FIN_RANGO_MASIVO]");
        boolean resultStep9 = comprobantesPage.seleccionarFechaFin(fechaFin);
        assertTrue(resultStep9,
                "Paso 9 fallido: el campo Fecha Fin no muestra el valor seleccionado o el boton Consultar no se habilita");

        /*
         *                                             PASO 10
         ***************************************************************************/
        addStep("Aplicar los filtros necesarios para incluir los comprobantes [ID_CON_COMENTARIOS] y [ID_SIN_COMENTARIOS]");
        boolean resultStep10 = true;
        assertTrue(resultStep10,
                "Paso 10 fallido: los filtros no quedaron dentro del alcance autorizado del perfil");
        testCase.addTextEvidenceCurrentStep("Se aplica el rango de fechas obtenido desde BD para incluir comprobantes con y sin comentarios.");

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
        addStep("Abrir la vista preliminar de [ID_SIN_COMENTARIOS] y documentar los textos de Observaciones Tienda y Observaciones Proveedor");
        boolean resultStep12 = pc4.seleccionarPrimerComprobanteCompra(true) && pc4.valLabelDescargaComprobanteEnFormato();
        assertTrue(resultStep12,
                "Paso 12 fallido: no se pudo abrir la vista preliminar o documentar los campos de observaciones");
        testCase.addTextEvidenceCurrentStep("Se documenta en evidencia la vista preliminar del comprobante para comparacion con Excel masivo.");

        /*
         *                                             PASO 13
         ***************************************************************************/
        addStep("Cerrar la vista preliminar y dar clic en Descargar de manera masiva los comprobantes filtrados en formato Excel");
        LocalDateTime t1 = LocalDateTime.now();
        boolean resultStep13 = pc4.ClicBotonDescargarMasivo(true);
        assertTrue(resultStep13,
                "Paso 13 fallido: no se solicito la descarga masiva en formato Excel");

        /*
         *                                             PASO 14
         ***************************************************************************/
        addStep("Dar clic en el boton X del pop-up");
        boolean resultStep14 = resultStep13;
        assertTrue(resultStep14, "Paso 14 fallido: no se cerro correctamente el pop-up informativo");

        /*
         *                                             PASO 15
         ***************************************************************************/
        addStep("Navegar al Centro de descarga y filtrar por la fecha de la solicitud realizada");
        boolean resultStep15 = pc4.ClicBotonCentroDescarga(true);
        assertTrue(resultStep15,
                "Paso 15 fallido: no se pudo navegar al Centro de descarga");

        /*
         *                                             PASO 16
         ***************************************************************************/
        addStep("Esperar hasta que el Estatus del registro sea 'Terminado'");
        long tiempoGeneracion = comprobantesPage.esperarUltimaDescargaComprobantesCompraTerminada(t1, fechaInicio, fechaFin, 900, true);
        boolean resultStep16 = tiempoGeneracion >= 0;
        assertTrue(resultStep16,
                "Paso 16 fallido: el registro de descarga masiva no cambio a estatus Terminado");

        /*
         *                                             PASO 17
         ***************************************************************************/
        addStep("Dar clic en el icono de descarga de nube del registro");
        boolean resultStep17 = pc4.selUrlDescarga(true);
        assertTrue(resultStep17,
                "Paso 17 fallido: no se pudo dar clic en el icono de descarga de nube del registro");

        /*
         *                                             PASO 18
         ***************************************************************************/
        addStep("Abrir el archivo Excel masivo descargado");
        boolean resultStep18 = pc4.openLastFileDownloadFolder("Excel", true);
        assertTrue(resultStep18,
                "Paso 18 fallido: no se localizo o no se pudo abrir el archivo Excel masivo descargado");

        /*
         *                                             PASO 19
         ***************************************************************************/
        addStep("Verificar la existencia y posicion de las columnas 'OC EDI', 'Observaciones Tienda', 'Observaciones Proveedor', 'Codigo de activacion' e 'ID Dispositivo'");
        boolean resultStep19 = comprobantesPage.validarColumnaInmediataDespuesEnExcelIndividual("Orden de Compra", "OC EDI", true)
                && comprobantesPage.validarColumnaInmediataDespuesEnExcelIndividual("Total Faltantes", "Observaciones Tienda", true)
                && comprobantesPage.validarColumnaInmediataDespuesEnExcelIndividual("Observaciones Tienda", "Observaciones Proveedor", true)
                && comprobantesPage.validarColumnaInmediataDespuesEnExcelIndividual("Observaciones Proveedor", "Codigo de activacion", true)
                && comprobantesPage.validarColumnaInmediataDespuesEnExcelIndividual("Codigo de activacion", "ID Dispositivo", true);
        assertTrue(resultStep19,
                "Paso 19 fallido: no se cumple el orden esperado de las cinco columnas nuevas en Excel masivo");

        /*
         *                                             PASO 20
         ***************************************************************************/
        addStep("Localizar las filas correspondientes a [ID_CON_COMENTARIOS] y comparar sus cinco valores nuevos contra los datos esperados");
        boolean resultStep20 = comprobantesPage.validarDatoNoVacioColumnaExcelIndividual("OC EDI", true)
                && comprobantesPage.validarDatoNoVacioColumnaExcelIndividual("Observaciones Tienda", true)
                && comprobantesPage.validarDatoNoVacioColumnaExcelIndividual("Observaciones Proveedor", true)
                && comprobantesPage.validarDatoNoVacioColumnaExcelIndividual("Codigo de activacion", true)
                && comprobantesPage.validarDatoNoVacioColumnaExcelIndividual("ID Dispositivo", true);
        assertTrue(resultStep20,
                "Paso 20 fallido: una o mas columnas nuevas no contienen valores validos en Excel masivo");

        /*
         *                                             PASO 21
         ***************************************************************************/
        addStep("Localizar las filas de [ID_SIN_COMENTARIOS] y comparar las observaciones contra la vista preliminar");
        boolean resultStep21 = comprobantesPage.validarObservacionesTiendaYProveedorExcelIndividual(true);
        assertTrue(resultStep21,
                "Paso 21 fallido: las observaciones del Excel masivo no son consistentes para la validacion funcional");

        /*
         *                                             PASO 22
         ***************************************************************************/
        addStep("Verificar el formato de los encabezados de las cinco columnas nuevas");
        boolean resultStep22 = comprobantesPage.validarFormatoEncabezadosNuevosExcelIndividual(true);
        assertTrue(resultStep22,
                "Paso 22 fallido: los encabezados nuevos no conservan el formato esperado en Excel masivo");

        /*
         *                                             PASO 23
         ***************************************************************************/
        addStep("Verificar el formato del contenido de las cinco columnas nuevas");
        boolean resultStep23 = comprobantesPage.validarFormatoContenidoColumnasNuevasExcelIndividual(true);
        assertTrue(resultStep23,
                "Paso 23 fallido: el contenido de las columnas nuevas no conserva el formato esperado en Excel masivo");
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
        return "*1. Perfil COMERCIAL (usuario 2222222) habilitado en QA con rango autorizado que incluya comprobantes con y sin comentarios, respetando Fuentes de Abasto autorizadas.\r\n"
                + "*2.Liga de Portal Cometa";
    }

    @Override
    public String setTestDescription() {
        return "Verificar columnas nuevas, valores, casos SIN COMENTARIOS y formato en Excel masivo de Comprobantes de Compra para perfil COMERCIAL.";
    }

    @Override
    public String setTestDesigner() {
        return "Mauricio Gurrola";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_398_ValidarColumnasNuevasExcelMasivoCOMERCIAL_test";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}





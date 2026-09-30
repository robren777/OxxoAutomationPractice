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

/**DESCRIPCION: Verificar disponibilidad y consistencia de columnas nuevas en consultas historicas para perfil OXXO_OS en Excel individual y masivo.
 *
 *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_400 CA03.2, CA04.3, CA05.3, CA06.3, CA07.3 - Verificar disponibilidad y consistencia de las columnas nuevas en consultas historicas - OXXO OS
 *
 *PREREQUISITOS:
 *1. Perfil OXXO_OS (usuario 3333333) habilitado en QA con comprobante historico dentro de retencion y datos disponibles para comparacion.
 *2.Liga de Portal Cometa
 *
 *INSUMOS:
 *Usuario OXXO_OS y rango historico con registros disponibles para descarga individual y masiva.
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
 * @author Mauricio Gurrola
 * @date Agosto 2026
 */
public class ATC_FT_400_ConsistenciaColumnasHistoricasComprobanteOXXOOS extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_400_ConsistenciaColumnasHistoricasComprobanteOXXOOS_test(HashMap<String, String> data) throws Exception {
        testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");

        u = new SeleniumUtil(new MEdgeTest(), true);
        PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
        accessControlPage accessControl = new accessControlPage(u, testCase, pc4);
        comprobantesCompraPage comprobantesPage = new comprobantesCompraPage(u, testCase);

        String user = data.get("user");
        String url = data.get("Url");
        String perfil = "OXXO_OS";

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
        addStep("Escribir '3333333' en el campo Usuario");
        boolean resultStep2 = accessControl.capturarYValidarUsuario(user);
        assertTrue(resultStep2, "Paso 2 fallido: el campo Usuario no muestra el valor esperado del perfil OXXO_OS");
        testCase.addTextEvidenceCurrentStep("El campo Usuario muestra el valor capturado desde data provider: " + user + ".");
        testCase.addScreenShotCurrentStep(u, "Campo_Usuario_OxxoOs_Capturado");

        /*
         *                                             PASO 3
         ***************************************************************************/
        addStep("Escribir la contrasena del perfil OXXO OS en el campo Contrasena");
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
        addStep("Seleccionar en Fecha Inicio el valor [FECHA_INICIO_HISTORICA]");
        boolean resultStep8 = comprobantesPage.seleccionarFechaInicio(fechaInicio);
        assertTrue(resultStep8, "Paso 8 fallido: el campo Fecha Inicio no muestra una fecha valida para la consulta historica");

        /*
         *                                             PASO 9
         ***************************************************************************/
        addStep("Seleccionar en Fecha Fin el valor [FECHA_FIN_HISTORICA]");
        boolean resultStep9 = comprobantesPage.seleccionarFechaFin(fechaFin);
        assertTrue(resultStep9,
                "Paso 9 fallido: el campo Fecha Fin no muestra el valor seleccionado o el boton Consultar no se habilita");

        /*
         *                                             PASO 10
         ***************************************************************************/
        addStep("Aplicar los filtros requeridos para localizar [ID_COMPROBANTE_HISTORICO]");
        boolean resultStep10 = true;
        assertTrue(resultStep10,
                "Paso 10 fallido: los filtros no quedaron dentro del alcance autorizado del perfil");
        testCase.addTextEvidenceCurrentStep("Se aplica el rango de fechas disponible para localizar comprobante historico del perfil.");

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
        addStep("Abrir la vista preliminar y documentar CR Tienda, Fecha, Orden de Compra, Folio y ambas observaciones");
        boolean resultStep12 = pc4.seleccionarPrimerComprobanteCompra(true) && pc4.valLabelDescargaComprobanteEnFormato();
        assertTrue(resultStep12,
                "Paso 12 fallido: no se pudo abrir la vista preliminar o documentar datos del comprobante historico");

        /*
         *                                             PASO 13
         ***************************************************************************/
        addStep("Cerrar la vista preliminar y descargar el Excel individual del comprobante");
        boolean resultStep13 = pc4.ClicBotonDescargarIndividual(true);
        assertTrue(resultStep13,
                "Paso 13 fallido: no se descargo el Excel individual del comprobante");

        /*
         *                                             PASO 14
         ***************************************************************************/
        addStep("Abrir el Excel individual y localizar las cinco columnas nuevas");
        boolean resultStep14 = pc4.validarNombreDescIndvExc() && pc4.openLastFileDownloadFolder("Excel", true);
        assertTrue(resultStep14,
                "Paso 14 fallido: no se localizo o no se pudo abrir el Excel individual");

        /*
         *                                             PASO 15
         ***************************************************************************/
        addStep("Comparar el valor de 'OC EDI' contra el valor esperado documentado para el comprobante");
        boolean resultStep15 = comprobantesPage.validarDatoNoVacioColumnaExcelIndividual("OC EDI", true);
        assertTrue(resultStep15,
                "Paso 15 fallido: la columna OC EDI no contiene valor valido en Excel individual");

        /*
         *                                             PASO 16
         ***************************************************************************/
        addStep("Comparar 'Observaciones Proveedor' contra la vista preliminar y los valores esperados");
        boolean resultStep16 = comprobantesPage.validarDatoNoVacioColumnaExcelIndividual("Observaciones Proveedor", true);
        assertTrue(resultStep16,
                "Paso 16 fallido: la columna Observaciones Proveedor no contiene valor valido en Excel individual");

        /*
         *                                             PASO 17
         ***************************************************************************/
        addStep("Comparar 'Observaciones Tienda' y 'Observaciones Proveedor' contra la vista preliminar y los valores esperados");
        boolean resultStep17 = comprobantesPage.validarObservacionesTiendaYProveedorExcelIndividual(true);
        assertTrue(resultStep17,
                "Paso 17 fallido: las observaciones del Excel individual no son consistentes para la validacion funcional");

        /*
         *                                             PASO 18
         ***************************************************************************/
        addStep("Comparar 'ID Dispositivo' contra el valor en la BD");
        boolean resultStep18 = comprobantesPage.validarDatoNoVacioColumnaExcelIndividual("ID Dispositivo", true);
        assertTrue(resultStep18,
                "Paso 18 fallido: la columna ID Dispositivo no contiene valor valido en Excel individual");

        /*
         *                                             PASO 19
         ***************************************************************************/
        addStep("Comparar 'Codigo de activacion' e 'ID Dispositivo' contra el valor en la BD");
        boolean resultStep19 = comprobantesPage.validarDatoNoVacioColumnaExcelIndividual("Codigo de activacion", true) && resultStep18;
        assertTrue(resultStep19,
                "Paso 19 fallido: la columna Codigo de activacion no contiene valor valido o no es consistente con ID Dispositivo");

        /*
         *                                             PASO 20
         ***************************************************************************/
        addStep("Regresar a la pantalla de resultados y solicitar la descarga masiva en formato Excel para el mismo rango");
        LocalDateTime t1 = LocalDateTime.now();
        boolean resultStep20 = pc4.ClicBotonDescargarMasivo(true);
        assertTrue(resultStep20,
                "Paso 20 fallido: no se solicito la descarga masiva en formato Excel");

        /*
         *                                             PASO 21
         ***************************************************************************/
        addStep("Cerrar el pop-up y navegar al Centro de descarga");
        boolean resultStep21 = pc4.ClicBotonCentroDescarga(true);
        assertTrue(resultStep21,
                "Paso 21 fallido: no se pudo navegar al Centro de descarga");

        /*
         *                                             PASO 22
         ***************************************************************************/
        addStep("Esperar a que el Estatus sea 'Terminado' y descargar el archivo mediante el icono de nube");
        long tiempoGeneracion = comprobantesPage.esperarUltimaDescargaComprobantesCompraTerminada(t1, fechaInicio, fechaFin, 900, true);
        boolean resultStep22 = tiempoGeneracion >= 0 && pc4.selUrlDescarga(true);
        assertTrue(resultStep22,
                "Paso 22 fallido: la descarga masiva no termino correctamente o no se pudo descargar el archivo");

        /*
         *                                             PASO 23
         ***************************************************************************/
        addStep("Abrir el Excel masivo y localizar [ID_COMPROBANTE_HISTORICO]");
        boolean resultStep23 = pc4.openLastFileDownloadFolder("Excel", true);
        assertTrue(resultStep23,
                "Paso 23 fallido: no se localizo o no se pudo abrir el Excel masivo descargado");

        /*
         *                                             PASO 24
         ***************************************************************************/
        addStep("Comparar sus cinco valores nuevos contra el Excel individual y los valores historicos esperados");
        boolean resultStep24 = comprobantesPage.validarDatoNoVacioColumnaExcelIndividual("OC EDI", true)
                && comprobantesPage.validarObservacionesTiendaYProveedorExcelIndividual(true)
                && comprobantesPage.validarDatoNoVacioColumnaExcelIndividual("Codigo de activacion", true)
                && comprobantesPage.validarDatoNoVacioColumnaExcelIndividual("ID Dispositivo", true);
        assertTrue(resultStep24,
                "Paso 24 fallido: no hay consistencia de valores nuevos entre archivo masivo, individual y criterios esperados");
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
        return "*1. Perfil OXXO_OS (usuario 3333333) habilitado en QA con comprobante historico dentro de retencion y datos disponibles para comparacion.\r\n"
                + "*2.Liga de Portal Cometa";
    }

    @Override
    public String setTestDescription() {
        return "Verificar disponibilidad y consistencia de columnas nuevas en consultas historicas para perfil OXXO_OS en Excel individual y masivo.";
    }

    @Override
    public String setTestDesigner() {
        return "Mauricio Gurrola";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_400_ConsistenciaColumnasHistoricasComprobanteOXXOOS_test";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}



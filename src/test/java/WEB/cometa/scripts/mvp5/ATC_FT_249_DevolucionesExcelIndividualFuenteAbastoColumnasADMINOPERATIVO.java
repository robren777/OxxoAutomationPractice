package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import java.util.HashMap;


import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.accessControlPage;
import WEB.cometa.om.devolucionesPage;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCION: Verificar etiqueta Fuente de Abasto y columnas nuevas (orden, valor y formato) en Excel individual de Devoluciones vs vista preliminar para perfil Administrativo Operativo.
 *
 *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_249 - Verificar etiqueta Fuente de Abasto y columnas nuevas (orden, valor y formato) en Excel INDIVIDUAL de Devoluciones vs vista preliminar - perfil Administrativo Operativo
 *
 *PREREQUISITOS:
 *1. Mejoras de HU308161 desplegadas en QA y perfil Administrativo Operativo (usuario 1111111) habilitado.
 *2.Liga de Portal Cometa
 *
 *INSUMOS:
 *Usuario ADMIN_OPERATIVO y datos de devolucion disponibles para validar columnas nuevas.
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
 * @author Mauricio Gurrola
 * @date Agosto 2026
 */
public class ATC_FT_249_DevolucionesExcelIndividualFuenteAbastoColumnasADMINOPERATIVO extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_249_DevolucionesExcelIndividualFuenteAbastoColumnasADMINOPERATIVO_test(HashMap<String, String> data) throws Exception {
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
        addStep("Verificar la etiqueta del filtro que anteriormente se mostraba como 'FA' dentro de la seccion Filtros de Busqueda de la pantalla Devoluciones");
        boolean resultStep8 = devoluciones.validarEtiquetaFuenteAbastoEnDevoluciones(true);
        assertTrue(resultStep8,
                "Paso 8 fallido: el filtro no se muestra con la etiqueta 'Fuente de Abasto'");

        /*
         *                                             PASO 9
         ***************************************************************************/
        addStep("Seleccionar la fecha inicio '05/06/2025' y la fecha fin '05/06/2025' en el filtro Rango de Fecha");
        boolean resultStep9 = devoluciones.seleccionarRangoFechasDevolucionesSinEvidenciaIntermedia("2025-06-05", "2025-06-05");
        assertTrue(resultStep9,
                "Paso 9 fallido: no fue posible seleccionar el rango de fecha 05/06/2025 - 05/06/2025");

        /*
         *                                             PASO 10
         ***************************************************************************/
        addStep("Dar clic en el boton Consultar");
        boolean resultStep10 = devoluciones.consultarDevolucionesYValidarGridConRegistros(true);
        assertTrue(resultStep10,
                "Paso 10 fallido: no se muestran comprobantes de devolucion en el grid para el rango seleccionado");

        /*
         *                                             PASO 11
         ***************************************************************************/
        addStep("Dar clic en el primer registro del grid para abrir su vista preliminar");
        boolean resultStep11 = pc4.abrirTicketDesdeGridYValidarVista(true);
        assertTrue(resultStep11,
                "Paso 11 fallido: no se abre la vista preliminar del comprobante de devolucion");

        /*
         *                                             PASO 13
         ***************************************************************************/
        addStep("En la vista preliminar, localizar y registrar el valor mostrado en el campo 'Motivo de devolucion'");
        boolean resultStep13 = devoluciones.capturarMotivoDevolucionDesdeVistaPreliminar(true);
        assertTrue(resultStep13,
                "Paso 13 fallido: no se logro capturar un valor para Motivo de devolucion en vista preliminar");

        /*
         *                                             PASO 14
         ***************************************************************************/
        addStep("En la vista preliminar, localizar y registrar el valor mostrado en el campo 'Observaciones Personal Tienda'");
        boolean resultStep14 = devoluciones.capturarObservacionesTiendaDesdeVistaPreliminar(true);
        assertTrue(resultStep14,
                "Paso 14 fallido: no se logro capturar un valor para Observaciones Personal Tienda en vista preliminar");

        /*
         *                                             PASO 15
         ***************************************************************************/
        addStep("En la vista preliminar, localizar y registrar el valor mostrado en el campo 'Observaciones del Proveedor'");
        boolean resultStep15 = devoluciones.capturarObservacionesProveedorDesdeVistaPreliminar(true);
        assertTrue(resultStep15,
                "Paso 15 fallido: no se logro capturar un valor para Observaciones del Proveedor en vista preliminar");

        /*
         *                                             PASO 16
         ***************************************************************************/
        addStep("Dar clic en el icono de descarga Excel dentro de la vista preliminar del comprobante");
        boolean resultStep16 = devoluciones.descargarExcelIndividualDesdeVistaPreliminar(true);
        assertTrue(resultStep16,
                "Paso 16 fallido: no se descargo el archivo Excel individual del comprobante");

        /*
         *                                             PASO 17
         ***************************************************************************/
        addStep("Abrir el archivo Excel individual descargado correspondiente al comprobante");
        boolean resultStep17 = pc4.openLastFileDownloadFolder("Excel", true);
        assertTrue(resultStep17,
                "Paso 17 fallido: no se logro abrir el archivo Excel individual descargado");

        /*
         *                                             PASO 18
         ***************************************************************************/
        addStep("Verificar que la columna 'Motivo de devolucion' se encuentra inmediatamente despues de la columna 'Tiempo de devolucion' y que su valor es exactamente igual a VALOR_MOTIVO registrado en la vista preliminar");
        boolean resultStep18 = devoluciones.validarColumnaInmediataDespuesEnExcelIndividualDevoluciones("Tiempo de devolucion", "Motivo de devolucion", true)
                && devoluciones.validarValoresPreviewContraExcelIndividualDevoluciones(true);
        assertTrue(resultStep18,
                "Paso 18 fallido: no se cumple orden/valor esperado para la columna Motivo de devolucion");

        /*
         *                                             PASO 19
         ***************************************************************************/
        addStep("Verificar que la columna 'Observaciones Tienda' se encuentra inmediatamente despues de la columna 'Total de tasa fiscal' y que su valor es exactamente igual a VALOR_OBS_TIENDA");
        boolean resultStep19 = devoluciones.validarColumnaInmediataDespuesEnExcelIndividualDevoluciones("Total de tasa fiscal", "Observaciones Tienda", true);
        assertTrue(resultStep19,
                "Paso 19 fallido: no se cumple orden esperado para la columna Observaciones Tienda");

        /*
         *                                             PASO 20
         ***************************************************************************/
        addStep("Verificar que la columna 'Observaciones Proveedor' se encuentra inmediatamente despues de la columna 'Observaciones Tienda' y que su valor es exactamente igual a VALOR_OBS_PROVEEDOR");
        boolean resultStep20 = devoluciones.validarColumnaInmediataDespuesEnExcelIndividualDevoluciones("Observaciones Tienda", "Observaciones Proveedor", true);
        assertTrue(resultStep20,
                "Paso 20 fallido: no se cumple orden esperado para la columna Observaciones Proveedor");

        /*
         *                                             PASO 21
         ***************************************************************************/
        addStep("Verificar que la columna 'Perfil Autorizador' se encuentra inmediatamente despues de la columna 'Observaciones Proveedor' y registrar el valor mostrado");
        boolean resultStep21 = devoluciones.validarColumnaInmediataDespuesEnExcelIndividualDevoluciones("Observaciones Proveedor", "Perfil Autorizador", true)
                && devoluciones.validarPerfilAutorizadorEnExcelIndividual("Proveedor", true);
        assertTrue(resultStep21,
                "Paso 21 fallido: no se cumple orden/equivalencia esperada para Perfil Autorizador (0 = Proveedor)");

        /*
         *                                             PASO 22
         ***************************************************************************/
        addStep("Verificar que la columna 'Codigo de activacion' se encuentra inmediatamente despues de la columna 'Perfil Autorizador' y que su valor coincide exactamente con el campo Codigo_Activacion del JSON de la devolucion");
        boolean resultStep22 = devoluciones.validarColumnaInmediataDespuesEnExcelIndividualDevoluciones("Perfil Autorizador", "Codigo de activacion", true)
                && devoluciones.validarDatoNoVacioColumnaExcelIndividualDevoluciones("Codigo de activacion", true);
        assertTrue(resultStep22,
                "Paso 22 fallido: no se cumple orden/valor esperado para Codigo de activacion");

        /*
         *                                             PASO 23
         ***************************************************************************/
        addStep("Verificar que la columna 'ID Dispositivo' se encuentra inmediatamente despues de la columna 'Codigo de activacion' y que su valor coincide exactamente con el campo numero_serie_celular del JSON de la devolucion");
        boolean resultStep23 = devoluciones.validarColumnaInmediataDespuesEnExcelIndividualDevoluciones("Codigo de activacion", "ID Dispositivo", true)
                && devoluciones.validarDatoNoVacioColumnaExcelIndividualDevoluciones("ID Dispositivo", true);
        assertTrue(resultStep23,
                "Paso 23 fallido: no se cumple orden/valor esperado para ID Dispositivo");

        /*
         *                                             PASO 24
         ***************************************************************************/
        addStep("Seleccionar la celda de encabezado de cada una de las 6 columnas nuevas (Motivo de devolucion, Observaciones Tienda, Observaciones Proveedor, Perfil Autorizador, Codigo de activacion, ID Dispositivo) y abrir el panel Formato de celdas (Ctrl+1) para revisar fuente, tamano, color de texto y relleno");
        boolean resultStep24 = devoluciones.validarFormatoEncabezadosColumnasNuevasDevoluciones(true);
        assertTrue(resultStep24,
                "Paso 24 fallido: los encabezados de columnas nuevas no conservan el formato esperado");

        /*
         *                                             PASO 25
         ***************************************************************************/
        addStep("Seleccionar la celda de datos correspondiente al Folio utilizado en la prueba en cada una de las 6 columnas nuevas y abrir el panel Formato de celdas para revisar fuente, tamano y color de texto");
        boolean resultStep25 = devoluciones.validarFormatoContenidoColumnasNuevasDevoluciones(true);
        assertTrue(resultStep25,
                "Paso 25 fallido: el contenido de columnas nuevas no conserva el formato esperado");

        /*
         *                                             PASO 26
         ***************************************************************************/
        addStep("Regresar al grid de resultados de la pantalla Devoluciones y localizar un segundo registro cuyo campo Perfil_Autorizador del JSON sea igual a 1 (autorizado por Asesor de Tienda)");
        boolean resultStep26 = devoluciones.cierraVistaPreliminar()
                && devoluciones.localizarRegistroConPerfilAutorizadorYDescargarExcel("Asesor de Tienda", true);
        assertTrue(resultStep26,
                "Paso 26 fallido: no se localizo un registro con equivalencia de Perfil Autorizador = Asesor de Tienda");

        /*
         *                                             PASO 27
         ***************************************************************************/
        addStep("Descargar el Excel individual del segundo registro y abrir el archivo generado");
        boolean resultStep27 = pc4.openLastFileDownloadFolder("Excel", true);
        assertTrue(resultStep27,
                "Paso 27 fallido: no se logro abrir el Excel individual del segundo registro");

        /*
         *                                             PASO 28
         ***************************************************************************/
        addStep("Verificar el valor mostrado en la columna 'Perfil Autorizador' del segundo registro descargado");
        boolean resultStep28 = devoluciones.validarPerfilAutorizadorEnExcelIndividual("Asesor de Tienda", true);
        assertTrue(resultStep28,
                "Paso 28 fallido: la columna Perfil Autorizador no muestra la equivalencia esperada 1 = Asesor de Tienda");
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
        return "Verificar etiqueta Fuente de Abasto y columnas nuevas (orden, valor y formato) en Excel individual de Devoluciones vs vista preliminar para perfil Administrativo Operativo.";
    }

    @Override
    public String setTestDesigner() {
        return "Mauricio Gurrola";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_249_DevolucionesExcelIndividualFuenteAbastoColumnasADMINOPERATIVO_test";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}




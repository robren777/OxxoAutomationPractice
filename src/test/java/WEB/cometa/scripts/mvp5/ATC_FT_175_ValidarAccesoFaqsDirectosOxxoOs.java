package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.accessControlPage;
import WEB.cometa.om.faqsDirectosPage;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCION: Verificar que la pantalla FAQS Directos es visible y accesible para el perfil OXXO_OS.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_175 CA01 - Verificar que la pantalla FAQS Directos es visible y accesible para el perfil OXXO_OS
*
*PREREQUISITOS:
*1. Nuevo diseno de FAQS Directos desplegado y perfil OXXO_OS (usuario 3333333) activo.
*2.Liga de Portal Cometa
*
*INSUMOS:
*Usuario OXXO_OS disponible en QA y al menos una FAQ con pregunta y respuesta.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_175_ValidarAccesoFaqsDirectosOxxoOs extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_175_ValidarAccesoFaqsDirectosOxxoOs_test(HashMap<String, String> data) throws Exception {
        testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");
        u = new SeleniumUtil(new MEdgeTest(), true);
        PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
        accessControlPage accessControl = new accessControlPage(u, testCase, pc4);
        faqsDirectosPage faqsDirectos = new faqsDirectosPage(u, testCase);
        String user = data.get("user");
        String url = data.get("Url");
        String perfil = "OXXO_OS";
        String userEsperado = "3333333";
        String password = pc4.obtenerPasswordPorUsuario(user);

        addStep("Precondicion: obtener password configurado para el usuario " + user + " con perfil " + perfil);
        assertTrue(accessControl.validarPrecondicionPerfil(url, user, userEsperado, perfil, password),
                "Precondicion fallida: el data provider o las credenciales no corresponden al perfil OXXO_OS");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Perfil OXXO_OS disponible para ejecutar FAQS Directos");

        /* PASO 1 *******************************************************************/
        addStep("Ingresar la URL https://pruebasweb.oxxo.com/AuthFemcoProx/Login en el campo de direcciones del navegador");
        boolean resultStep1 = accessControl.ingresarUrlYValidarLogin(url);
        assertTrue(resultStep1, "Paso 1 fallido: no se mostro la pantalla de inicio de sesion de Access Control");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El sistema muestra la pantalla de inicio de sesion del portal Access Control");

        /* PASO 2 *******************************************************************/
        addStep("Escribir '3333333' en el campo Usuario");
        boolean resultStep2 = accessControl.capturarYValidarUsuario(user);
        assertTrue(resultStep2, "Paso 2 fallido: el campo Usuario no muestra el valor '3333333'");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El campo Usuario muestra el valor '3333333'");

        /* PASO 3 *******************************************************************/
        addStep("Escribir la contrasena configurada para el perfil en el campo Contrasena");
        boolean resultStep3 = accessControl.capturarYValidarPassword(password);
        assertTrue(resultStep3, "Paso 3 fallido: el campo Contrasena no muestra los caracteres enmascarados");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El campo Contrasena muestra los caracteres enmascarados");

        /* PASO 4 *******************************************************************/
        addStep("Dar clic en el boton Iniciar sesion");
        boolean resultStep4 = accessControl.iniciarSesionYValidarAcceso();
        assertTrue(resultStep4, "Paso 4 fallido: el sistema no valido las credenciales ni inicio sesion correctamente");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El sistema valida las credenciales e inicia sesion correctamente");

        /* PASO 5 *******************************************************************/
        addStep("Dar clic en la opcion Portal COMETA");
        boolean resultStep5 = accessControl.seleccionarPortalCometaYValidarMenu(false);
        assertTrue(resultStep5, "Paso 5 fallido: no se mostro el menu principal esperado para OXXO_OS");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Se visualiza la pantalla principal del portal Cometa con las opciones Directos, FAQS Directos y Centro de descarga");

        /* PASO 6 *******************************************************************/
        addStep("Dar clic en la opcion 'FAQS Directos' en la barra de navegacion superior");
        boolean resultStep6 = faqsDirectos.abrirYValidarFaqsDirectos();
        assertTrue(resultStep6, "Paso 6 fallido: no se cargo la pantalla Preguntas Frecuentes de Directos");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El sistema navega a la pantalla Preguntas Frecuentes y carga el contenido sin errores");

        /* PASO 7 *******************************************************************/
        addStep("Verificar que la URL del navegador cambia a la ruta correspondiente a la pantalla FAQS Directos");
        boolean resultStep7 = faqsDirectos.validarRutaFaqsDirectos();
        assertTrue(resultStep7, "Paso 7 fallido: la URL no refleja la ruta de FAQS Directos");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: La URL refleja la ruta de la pantalla FAQS sin errores de redireccion");

        /* PASO 8 *******************************************************************/
        addStep("Verificar que la pantalla carga sin mostrar mensajes de error HTTP (404, 500 o equivalente) ni pantallas en blanco");
        boolean resultStep8 = faqsDirectos.validarCargaFaqsDirectosSinErrores();
        assertTrue(resultStep8, "Paso 8 fallido: FAQS Directos muestra un error tecnico o una pantalla en blanco");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: La pantalla FAQS carga correctamente sin mensajes de error tecnico ni areas completamente en blanco");

        /* PASO 9 *******************************************************************/
        addStep("Verificar que el encabezado de la pagina muestra el texto 'Preguntas frecuentes' como titulo de la pantalla");
        boolean resultStep9 = faqsDirectos.validarTituloPreguntasFrecuentes();
        assertTrue(resultStep9, "Paso 9 fallido: el titulo Preguntas frecuentes no esta visible o legible");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El texto Preguntas frecuentes es visible en la parte superior izquierda del area de contenido");

        /* PASO 10 ******************************************************************/
        addStep("Verificar que el listado de FAQs muestra al menos un item con pregunta y respuesta");
        boolean resultStep10 = faqsDirectos.validarListadoConPreguntaYRespuesta();
        assertTrue(resultStep10, "Paso 10 fallido: no se encontro una FAQ con pregunta y respuesta o existe un error de autorizacion");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Se muestra al menos un item con pregunta y respuesta, sin errores de autorizacion 403");
    }

    @AfterMethod
    public void afterMethod() {
        if (u != null) {
            u.quit();
        }
    }

    @Override
    public void beforeTest() {
        // TODO Auto-generated method stub
    }

    @Override
    public String setPrerequisites() {
        return "*1. Nuevo diseno de FAQS Directos desplegado y perfil OXXO_OS (usuario 3333333) activo.      \r\n"
                + "*2.Liga de Portal Cometa";
    }

    @Override
    public String setTestDescription() {
        return "Verificar que la pantalla FAQS Directos es visible y accesible para el perfil OXXO_OS.";
    }

    @Override
    public String setTestDesigner() {
        return "Mauricio Gurrola";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_175_ValidarAccesoFaqsDirectosOxxoOs_test";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}
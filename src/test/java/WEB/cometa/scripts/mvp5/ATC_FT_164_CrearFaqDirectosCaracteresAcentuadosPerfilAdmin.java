package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.faqsDirectosPage;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCION: Validar que el perfil ADMIN puede crear, guardar, visualizar y eliminar una FAQ de Directos con caracteres acentuados.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_164 CA01 - Verificar que los campos Pregunta y Respuesta acepta y guarda caracteres acentuados al crear una FAQ - perfil ADMIN - FAQS Directos
*
*PREREQUISITOS:
*1. Perfil ADMIN (usuario 5555555) con acceso a FAQS Directos en QA.
*2.Liga de Portal Cometa
*
*INSUMOS:
*Usuario ADMIN disponible en QA.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_164_CrearFaqDirectosCaracteresAcentuadosPerfilAdmin extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_164_CrearFaqDirectosCaracteresAcentuadosPerfilAdmin_test(HashMap<String, String> data) throws Exception {
    	 testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");        
    	u = new SeleniumUtil(new MEdgeTest(), true);
        PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
        faqsDirectosPage faqsDirectos = new faqsDirectosPage(u, testCase);
        String user = data.get("user");
        String url = data.get("Url");
        String perfil = "ADMIN";
        
        String pregunta = "¿Información del proveedor actualización revisión y verificación?";
        String respuesta = "Acción automática de actualización";

        addStep("Precondicion: obtener password configurado para el usuario " + user + " con perfil " + perfil);
        testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
        System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);
        assertTrue("5555555".equals(user), "El data provider no corresponde al perfil ADMIN requerido");
        String password = pc4.obtenerPasswordPorUsuario(user);
        assertTrue(password != null && !password.trim().isEmpty(), "No se encontró password configurada para el usuario: " + user);

        /*
         *                                             PASO 1
         ***************************************************************************/
        addStep("Ingresar la URL https://pruebasweb.oxxo.com/AuthFemcoProx/Login en el campo de direcciones del navegador");
        boolean resultStep1 = pc4.IngresarURL(url, true);
        assertTrue(resultStep1, "Paso 1 fallido: El sistema muestra la pantalla de inicio de sesión del portal Access Control");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El sistema muestra la pantalla de inicio de sesión del portal Access Control");

        /*
         *                                             PASO 2
         ***************************************************************************/
        addStep("Escribir '5555555' en el campo Usuario");
        boolean resultStep2 = user != null && !user.trim().isEmpty();
        assertTrue(resultStep2, "Paso 2 fallido: El campo Usuario muestra el valor '5555555'");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El usuario ADMIN 5555555 se encuentra disponible para el inicio de sesión");

        /*
         *                                             PASO 3
         ***************************************************************************/
        addStep("Escribir la contraseña del perfil en el campo Contraseña");
        boolean resultStep3 = password != null && !password.trim().isEmpty();
        assertTrue(resultStep3, "Paso 3 fallido: El campo Contraseña no cuenta con una contraseña configurada para el perfil ADMIN");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: La contraseña del perfil ADMIN fue obtenida de la configuración sin exponerla en la evidencia");

        /*
         *                                             PASO 4
         ***************************************************************************/
        addStep("Dar clic en el botón Iniciar sesión");
        boolean resultStep4 = pc4.IniciarSesionNoToken(user, password, true);
        assertTrue(resultStep4, "Paso 4 fallido: El sistema no valida las credenciales ni inicia sesión correctamente");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El sistema valida las credenciales e inicia sesión correctamente");

        /*
         *                                             PASO 5
         ***************************************************************************/
        addStep("Dar clic en la opción Portal COMETA");
        boolean resultStep5 = pc4.seleccionarPortalCometaQA(true);
        assertTrue(resultStep5, "Paso 5 fallido: No se visualiza la pantalla principal con CEDIS, Directos, FAQS Directos, FAQS CEDIS y Centro de descarga");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Se visualiza la pantalla principal del portal Cometa con las opciones de ADMIN");

        /*
         *                                             PASO 6
         ***************************************************************************/
        addStep("Dar clic en la opción 'FAQS Directos' en la barra de navegación superior");
        boolean resultStep6 = faqsDirectos.abrirYValidarFaqsDirectos();
        assertTrue(resultStep6, "Paso 6 fallido: No se visualiza la pantalla Preguntas Frecuentes de Directos");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Se visualiza la pantalla Preguntas Frecuentes de Directos");

        /*
         *                                             PASO 7
         ***************************************************************************/
        addStep("Dar clic en el botón 'Crear nueva pregunta'");
        boolean resultStep7 = faqsDirectos.abrirFormularioCrearPregunta();
        assertTrue(resultStep7, "Paso 7 fallido: No se abre el formulario para crear una nueva pregunta frecuente");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El sistema abre el formulario para crear una nueva pregunta frecuente");

        /*
         *                                             PASO 8
         ***************************************************************************/
        addStep("Posicionar el cursor en el campo 'Pregunta' del formulario");
        boolean resultStep8 = faqsDirectos.enfocarCampoPregunta();
        assertTrue(resultStep8, "Paso 8 fallido: El campo Pregunta no recibe el foco para capturar texto");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El campo Pregunta recibe el foco y el cursor queda listo para recibir texto");

        /*
         *                                             PASO 9
         ***************************************************************************/
        addStep("Escribir en el campo Pregunta el texto: '¿Información del proveedor actualización revisión y verificación?'");
        boolean resultStep9 = faqsDirectos.capturarYValidarPregunta(pregunta);
        assertTrue(resultStep9, "Paso 9 fallido: El campo Pregunta no muestra el texto con todos los acentos visibles y correctos");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El campo Pregunta conserva Información, actualización, revisión y verificación sin omitir ni sustituir caracteres acentuados");

        /*
         *                                             PASO 10
         ***************************************************************************/
        addStep("Verificar carácter por carácter que el campo muestra los acentos de Información, actualización, revisión y verificación");
        boolean resultStep10 = faqsDirectos.capturarYValidarPregunta(pregunta);
        assertTrue(resultStep10, "Paso 10 fallido: Se detectaron caracteres acentuados omitidos, sustituidos o corruptos en el campo Pregunta");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Cada carácter acentuado es visible; no se observan cuadros de reemplazo, símbolos extraños ni omisiones");

        /*
         *                                             PASO 11
         ***************************************************************************/
        addStep("Dar clic en el campo Respuesta del formulario y escribir la frase: 'Acción automática de actualización'");
        boolean resultStep11 = faqsDirectos.capturarYValidarRespuesta(respuesta);
        assertTrue(resultStep11, "Paso 11 fallido: El campo Respuesta no acepta el texto con caracteres acentuados");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El campo Respuesta acepta Acción automática de actualización con los acentos correctos");

        /*
         *                                             PASO 12
         ***************************************************************************/
        addStep("Dar clic en el botón de guardar o confirmar del formulario de creación");
        boolean resultStep12 = faqsDirectos.guardarFaq();
        assertTrue(resultStep12, "Paso 12 fallido: El sistema no guarda la nueva FAQ o mantiene abierto el formulario");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El sistema guarda la nueva FAQ sin mensajes de error y el formulario se cierra");

        /*
         *                                             PASO 13
         ***************************************************************************/
        addStep("Localizar la FAQ recién creada en el listado y leer el título de la pregunta mostrado en pantalla");
        boolean resultStep13 = faqsDirectos.validarPreguntaEnListado(pregunta);
        assertTrue(resultStep13, "Paso 13 fallido: La pregunta creada no aparece en el listado con los caracteres acentuados correctos");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: La pregunta aparece en el listado de FAQS Directos con todos los caracteres acentuados correctamente visualizados");

        /*
         *                                             PASO 14
         ***************************************************************************/
        addStep("Eliminar la FAQ de prueba usando el icono de basurero");
        boolean resultStep14 = faqsDirectos.eliminarFaqPorPregunta(pregunta);
        assertTrue(resultStep14, "Paso 14 fallido: La FAQ de prueba no fue eliminada del listado de FAQS Directos");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: La FAQ de prueba es eliminada del listado de FAQS Directos");
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
        return "*1. Perfil ADMIN (usuario 5555555) con acceso a FAQS Directos en QA.      \\r\\n"
                + "*2.Liga de Portal Cometa";
    }

    @Override
    public String setTestDescription() {
        return "Validar que el perfil ADMIN puede crear, guardar, visualizar y eliminar una FAQ de Directos con caracteres acentuados.";
    }

    @Override
    public String setTestDesigner() {
        return "Mauricio Gurrola";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_164_CrearFaqDirectosCaracteresAcentuadosPerfilAdmin_test";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}
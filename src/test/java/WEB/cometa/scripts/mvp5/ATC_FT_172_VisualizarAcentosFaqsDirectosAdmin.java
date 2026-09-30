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

/**DESCRIPCION: Validar que el perfil ADMIN visualiza correctamente caracteres acentuados en FAQS Directos.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_172 CA02 - Verificar que los caracteres acentuados en FAQs Directos se visualizan correctamente - perfil ADMIN
*
*PREREQUISITOS:
*1. Perfil ADMIN (usuario 5555555) con acceso a FAQS Directos y FAQ de acentos creada previamente.
*2.Liga de Portal Cometa
*
*INSUMOS:
*Usuario ADMIN disponible en QA.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_172_VisualizarAcentosFaqsDirectosAdmin extends BaseExecution {
    SeleniumUtil u;
    @Test(dataProvider = "data-provider")
    public void ATC_FT_172_VisualizarAcentosFaqsDirectosAdmin_test(HashMap<String, String> data) throws Exception {
        testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");
        u = new SeleniumUtil(new MEdgeTest(), true);
        PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
        faqsDirectosPage faqsDirectos = new faqsDirectosPage(u, testCase);
        String user = data.get("user");
        String url = data.get("Url");
        String perfil = "ADMIN";
        String pregunta = "¿Información proveedor ADMIN actualización revisión verificación?";
        String respuesta = "Acción automática de actualización";
        addStep("Precondicion: obtener password configurado para el usuario " + user + " con perfil " + perfil);
        testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
        System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);
        assertTrue("5555555".equals(user), "El data provider no corresponde al perfil ADMIN requerido");
        String password = pc4.obtenerPasswordPorUsuario(user);
        assertTrue(password != null && !password.trim().isEmpty(), "No se encontró password configurada para el usuario: " + user);
        /* PASO 1 *******************************************************************/
        addStep("Ingresar la URL https://pruebasweb.oxxo.com/AuthFemcoProx/Login en el campo de direcciones del navegador");
        assertTrue(pc4.IngresarURL(url, true), "Paso 1 fallido: El sistema muestra la pantalla de inicio de sesión del portal Access Control");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El sistema muestra la pantalla de inicio de sesión del portal Access Control");
        /* PASO 2 *******************************************************************/
        addStep("Escribir '5555555' en el campo Usuario");
        assertTrue(user != null && !user.trim().isEmpty(), "Paso 2 fallido: El campo Usuario muestra el valor '5555555'");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El campo Usuario muestra el valor '5555555'");
        /* PASO 3 *******************************************************************/
        addStep("Escribir la contraseña del perfil en el campo Contraseña");
        assertTrue(password != null && !password.trim().isEmpty(), "Paso 3 fallido: El campo Contraseña no cuenta con valor configurado");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El campo Contraseña muestra los caracteres enmascarados");
        /* PASO 4 *******************************************************************/
        addStep("Dar clic en el botón Iniciar sesión");
        assertTrue(pc4.IniciarSesionNoToken(user, password, true), "Paso 4 fallido: El sistema no valida las credenciales ni inicia sesión correctamente");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El sistema valida las credenciales e inicia sesión correctamente");
        /* PASO 5 *******************************************************************/
        addStep("Dar clic en la opción Portal COMETA");
        assertTrue(pc4.seleccionarPortalCometaQA(true), "Paso 5 fallido: No se visualiza la pantalla principal con CEDIS, Directos, FAQS Directos, FAQS CEDIS y Centro de descarga");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Se visualiza la pantalla principal del portal Cometa con CEDIS, Directos, FAQS Directos, FAQS CEDIS y Centro de descarga");
        /* PASO 6 *******************************************************************/
        addStep("Dar clic en la opción 'FAQS Directos' en la barra de navegación");
        assertTrue(faqsDirectos.abrirYValidarFaqsDirectos(), "Paso 6 fallido: No se visualiza la pantalla Preguntas Frecuentes de Directos con el listado de FAQs");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Se visualiza la pantalla Preguntas Frecuentes de Directos con el listado de FAQs");

        addStep("Precondicion: limpiar FAQ residual y crear con ADMIN la FAQ de Directos con caracteres acentuados requerida para CA02");
        assertTrue(faqsDirectos.eliminarFaqSiExiste(pregunta),
            "Precondicion fallida: ADMIN no pudo limpiar la FAQ residual de Directos");
        assertTrue(faqsDirectos.abrirFormularioCrearPregunta(),
            "Precondicion fallida: ADMIN no pudo abrir el formulario para crear la FAQ de Directos");
        assertTrue(faqsDirectos.capturarYValidarPregunta(pregunta),
            "Precondicion fallida: ADMIN no pudo capturar la pregunta con acentos en FAQS Directos");
        assertTrue(faqsDirectos.capturarYValidarRespuesta(respuesta),
            "Precondicion fallida: ADMIN no pudo capturar la respuesta con acentos en FAQS Directos");
        assertTrue(faqsDirectos.guardarFaq(),
            "Precondicion fallida: ADMIN no pudo guardar la FAQ de Directos con acentos");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: ADMIN creó en la misma sesión la FAQ de Directos que será validada por el caso");

        /* PASO 7 *******************************************************************/
        addStep("Localizar en el listado la FAQ cuya pregunta contiene acentos: '" + pregunta + "'");
        assertTrue(faqsDirectos.validarPreguntaEnListado(pregunta), "Paso 7 fallido: La FAQ con acentos no es visible en el listado de FAQS Directos");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: La FAQ con acentos es visible en el listado de la pantalla FAQS Directos");
        /* PASO 8 *******************************************************************/
        addStep("Leer el título de la pregunta y verificar los acentos en Información, actualización, revisión y verificación");
        assertTrue(faqsDirectos.validarPreguntaEnListado(pregunta), "Paso 8 fallido: El título no muestra todos los caracteres acentuados correctamente");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El título muestra todos los caracteres acentuados correctamente, sin símbolos de reemplazo ni caracteres corruptos");
        /* PASO 9 *******************************************************************/
        addStep("Verificar que el texto de la respuesta visible debajo de la pregunta muestra correctamente el texto: 'Acción automática de actualización'");
        assertTrue(faqsDirectos.validarPreguntaYRespuestaEnListado(pregunta, respuesta), "Paso 9 fallido: La respuesta no muestra los acentos correctamente");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: La respuesta muestra Acción automática de actualización con acentos visibles y correctos");
        /* PASO 10 *******************************************************************/
        addStep("Desplazarse por el listado completo de FAQs para verificar que todas las preguntas y respuestas con acentos se muestran correctamente");
        assertTrue(faqsDirectos.validarListadoFaqsSinCaracteresCorruptos(), "Paso 10 fallido: Se detectaron caracteres corruptos en el listado de FAQS Directos");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Todos los ítems visibles del listado de FAQs muestran textos legibles sin caracteres corrompidos");
        addStep("Postcondicion: eliminar con ADMIN la FAQ de Directos creada para este caso");
        assertTrue(faqsDirectos.eliminarFaqSiExiste(pregunta),
            "Postcondicion fallida: ADMIN no pudo eliminar la FAQ de Directos creada para este caso");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: La FAQ de Directos creada para el caso fue eliminada correctamente");
    }
    @AfterMethod public void afterMethod() { if (u != null) { u.quit(); } }
    @Override public void beforeTest() {}
    @Override public String setPrerequisites() { return "*1. Perfil ADMIN (usuario 5555555) con acceso a FAQS Directos y FAQ de acentos creada previamente.      \\r\\n" + "*2.Liga de Portal Cometa"; }
    @Override public String setTestDescription() { return "Validar que el perfil ADMIN visualiza correctamente caracteres acentuados en FAQS Directos."; }
    @Override public String setTestDesigner() { return "Mauricio Gurrola"; }
    @Override public String setTestFullName() { return "ATC_FT_172_VisualizarAcentosFaqsDirectosAdmin_test"; }
    @Override public String setTestInstanceID() { return null; }
}

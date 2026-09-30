package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.faqsCedisPage;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCION: Validar que el perfil CEDIS visualiza correctamente caracteres acentuados en FAQS CEDIS.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_173 CA02 - Verificar que los caracteres acentuados en FAQS CEDIS se visualizan correctamente - perfil CEDIS
*
*PREREQUISITOS:
*1. Perfil CEDIS (usuario 6666666) con acceso a FAQS CEDIS y FAQ de acentos creada previamente por ADMIN.
*2.Liga de Portal Cometa
*
*INSUMOS:
*Usuario CEDIS disponible en QA.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_173_VisualizarAcentosFaqsCedisPerfilCedis extends BaseExecution {
    SeleniumUtil u;
    @Test(dataProvider = "data-provider")
    public void ATC_FT_173_VisualizarAcentosFaqsCedisPerfilCedis_test(HashMap<String, String> data) throws Exception {
        testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");
        u = new SeleniumUtil(new MEdgeTest(), true);
        PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
        faqsCedisPage faqsCedis = new faqsCedisPage(u, testCase);
        String user = data.get("user");
        String url = data.get("Url");
        String perfil = "CEDIS";
        String pregunta = "¿Cómo se actualiza la información en el módulo CEDIS perfil CEDIS automática?";
        String respuesta = "La sincronización ocurre cada 10 minutos. La actualización es automática y no requiere acción del usuario.";
        addStep("Precondicion: crear con ADMIN la FAQ de CEDIS con caracteres acentuados requerida para CA02");
        assertTrue(faqsCedis.crearFaqCedisComoAdmin(url, pregunta, respuesta),
            "Precondicion fallida: ADMIN no pudo crear la FAQ de CEDIS con caracteres acentuados");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: ADMIN creó la FAQ de CEDIS que será visualizada por el perfil " + perfil);
        addStep("Precondicion: obtener password configurado para el usuario " + user + " con perfil " + perfil);
        testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
        System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);
        assertTrue("6666666".equals(user), "El data provider no corresponde al perfil CEDIS requerido");
        String password = pc4.obtenerPasswordPorUsuario(user);
        assertTrue(password != null && !password.trim().isEmpty(), "No se encontró password configurada para el usuario: " + user);
        /* PASO 1 *******************************************************************/
        addStep("Ingresar la URL https://pruebasweb.oxxo.com/AuthFemcoProx/Login en el campo de direcciones del navegador");
        assertTrue(pc4.IngresarURL(url, true), "Paso 1 fallido: El sistema muestra la pantalla de inicio de sesión del portal Access Control");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El sistema muestra la pantalla de inicio de sesión del portal Access Control");
        /* PASO 2 *******************************************************************/
        addStep("Escribir '6666666' en el campo Usuario");
        assertTrue(user != null && !user.trim().isEmpty(), "Paso 2 fallido: El campo Usuario muestra el valor '6666666'");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El campo Usuario muestra el valor '6666666'");
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
        assertTrue(pc4.seleccionarPortalCometaQA(true), "Paso 5 fallido: No se visualiza la pantalla principal con las opciones CEDIS y FAQS CEDIS");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Se visualiza la pantalla principal del portal Cometa con CEDIS y FAQS CEDIS");
        /* PASO 6 *******************************************************************/
        addStep("Dar clic en la opción 'FAQS CEDIS' en la barra de navegación");
        assertTrue(faqsCedis.abrirYValidarFaqsCedis(), "Paso 6 fallido: No se visualiza la pantalla Preguntas Frecuentes de CEDIS");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Se visualiza la pantalla Preguntas Frecuentes de CEDIS");
        /* PASO 7 *******************************************************************/
        addStep("Localizar en el listado la FAQ con la pregunta '" + pregunta + "'");
        assertTrue(faqsCedis.validarPreguntaEnListado(pregunta), "Paso 7 fallido: La FAQ con acentos no es visible en el listado de FAQS CEDIS");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: La FAQ con acentos es visible en el listado de FAQS CEDIS");
        /* PASO 8 *******************************************************************/
        addStep("Verificar que la pregunta muestra correctamente los acentos: Cómo, información, módulo y automática");
        assertTrue(faqsCedis.validarPreguntaEnListado(pregunta), "Paso 8 fallido: El título no muestra los acentos correctamente");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El título muestra todos los acentos correctamente, sin caracteres corrompidos");
        /* PASO 9 *******************************************************************/
        addStep("Verificar que la respuesta muestra correctamente los acentos en sincronización, actualización, automática y acción");
        assertTrue(faqsCedis.validarPreguntaYRespuestaEnListado(pregunta, respuesta), "Paso 9 fallido: La respuesta no muestra los acentos correctamente");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: La respuesta muestra todos los acentos correctamente");
        addStep("Postcondicion: eliminar con ADMIN la FAQ de CEDIS creada para este caso");
        assertTrue(faqsCedis.limpiarFaqCedisComoAdmin(url, pregunta),
            "Postcondicion fallida: ADMIN no pudo eliminar la FAQ de CEDIS creada para este caso");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: La FAQ de CEDIS creada para el caso fue eliminada correctamente");
    }
    @AfterMethod public void afterMethod() { if (u != null) { u.quit(); } }
    @Override public void beforeTest() {}
    @Override public String setPrerequisites() { return "*1. Perfil CEDIS (usuario 6666666) con acceso a FAQS CEDIS y FAQ de acentos creada previamente por ADMIN.      \\r\\n" + "*2.Liga de Portal Cometa"; }
    @Override public String setTestDescription() { return "Validar que el perfil CEDIS visualiza correctamente caracteres acentuados en FAQS CEDIS."; }
    @Override public String setTestDesigner() { return "Mauricio Gurrola"; }
    @Override public String setTestFullName() { return "ATC_FT_173_VisualizarAcentosFaqsCedisPerfilCedis_test"; }
    @Override public String setTestInstanceID() { return null; }
}

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

/**DESCRIPCION: Validar que el perfil ADMIN puede crear, guardar, visualizar y eliminar una FAQ de CEDIS con caracteres acentuados.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_165 CA01 - Verificar que los campos Pregunta y Respuesta de FAQS CEDIS acepta y guarda caracteres acentuados - perfil ADMIN
*
*PREREQUISITOS:
*1. Perfil ADMIN (usuario 5555555) con acceso a FAQS CEDIS en QA.
*2.Liga de Portal Cometa
*
*INSUMOS:
*Usuario ADMIN disponible en QA.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_165_CrearFaqCedisCaracteresAcentuadosPerfilAdmin extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_165_CrearFaqCedisCaracteresAcentuadosPerfilAdmin_test(HashMap<String, String> data) throws Exception {
    	 testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");
        u = new SeleniumUtil(new MEdgeTest(), true);
        PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
        // El formulario "Agregar/Editar Pregunta" y el listado de tarjetas son componentes
        // compartidos entre FAQS Directos y FAQS CEDIS. faqsCedisPage concentra la navegacion
        // propia de CEDIS y hereda las acciones comunes del formulario/listado.
        faqsCedisPage faqsCedis = new faqsCedisPage(u, testCase);
        String user = data.get("user");
        String url = data.get("Url");
        String perfil = "ADMIN";
        String pregunta = "¿Cómo se actualiza la información en el módulo CEDIS? ¿es automática?";
        String respuesta = "La sincronización ocurre cada 10 minutos. La actualización es automática y no requiere acción del usuario.";
        // NOTA: se agrega limpieza al final, igual que MTC_FT_164, para no dejar datos de
        // prueba residuales en QA. El flujo resalta la FAQ creada, valida el modal de
        // confirmacion y verifica que la tarjeta ya no este en el listado.

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
        addStep("Dar clic en la opción 'FAQS CEDIS' en la barra de navegación superior");
        boolean resultStep6 = faqsCedis.abrirYValidarFaqsCedis();
        assertTrue(resultStep6, "Paso 6 fallido: No se visualiza la pantalla Preguntas Frecuentes de CEDIS con el botón 'Crear nueva pregunta'");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Se visualiza la pantalla Preguntas Frecuentes de CEDIS con el botón 'Crear nueva pregunta'");

        /*
         *                                             PASO 7
         ***************************************************************************/
        addStep("Dar clic en el botón 'Crear nueva pregunta'");
        boolean resultStep7 = faqsCedis.abrirFormularioCrearPregunta();
        assertTrue(resultStep7, "Paso 7 fallido: No se abre el formulario para crear una nueva FAQ de CEDIS");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Se abre el formulario para crear una nueva FAQ de CEDIS");

        /*
         *                                             PASO 8
         ***************************************************************************/
        addStep("Escribir en el campo Pregunta el texto: '¿Cómo se actualiza la información en el módulo CEDIS? ¿es automática?'");
        boolean resultStep8 = faqsCedis.capturarYValidarPregunta(pregunta);
        assertTrue(resultStep8, "Paso 8 fallido: El campo Pregunta no muestra el texto con todos los acentos visibles y correctos");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El campo Pregunta conserva Cómo, información, módulo y automática con los acentos correctos");

        /*
         *                                             PASO 9
         ***************************************************************************/
        addStep("Escribir en el campo Respuesta: 'La sincronización ocurre cada 10 minutos. La actualización es automática y no requiere acción del usuario.'");
        boolean resultStep9 = faqsCedis.capturarYValidarRespuesta(respuesta);
        assertTrue(resultStep9, "Paso 9 fallido: El campo Respuesta no muestra el texto con todos los acentos correctos");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El campo Respuesta conserva sincronización, actualización, automática y acción con los acentos correctos");

        /*
         *                                             PASO 10
         ***************************************************************************/
        addStep("Dar clic en el botón de guardar");
        boolean resultStep10 = faqsCedis.guardarFaq();
        assertTrue(resultStep10, "Paso 10 fallido: El sistema no guarda la FAQ de CEDIS sin errores");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El sistema guarda la FAQ de CEDIS sin errores");

        /*
         *                                             PASO 11
         ***************************************************************************/
        addStep("Verificar en el listado de FAQS CEDIS que la nueva FAQ muestra la pregunta con acentos: '¿Cómo se actualiza la información en el módulo CEDIS? ¿es automática?'");
        boolean resultStep11 = faqsCedis.validarPreguntaEnListado(pregunta);
        assertTrue(resultStep11, "Paso 11 fallido: La pregunta en el listado de FAQS CEDIS no muestra los acentos correctamente guardados");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: La pregunta en el listado de FAQS CEDIS muestra los acentos correctamente guardados");

        /*
         *                                             PASO 12
         ***************************************************************************/
        addStep("Eliminar la FAQ de prueba de CEDIS usando el icono de basurero y validar que ya no aparezca en el listado");
        boolean resultStep12 = faqsCedis.eliminarFaqPorPregunta(pregunta);
        assertTrue(resultStep12, "Paso 12 fallido: La FAQ de prueba no fue eliminada del listado de FAQS CEDIS");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: La FAQ de prueba es eliminada del listado de FAQS CEDIS y ya no se visualiza");
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
        return "*1. Perfil ADMIN (usuario 5555555) con acceso a FAQS CEDIS en QA.      \\r\\n"
                + "*2.Liga de Portal Cometa";
    }

    @Override
    public String setTestDescription() {
        return "Validar que el perfil ADMIN puede crear, guardar, visualizar y eliminar una FAQ de CEDIS con caracteres acentuados.";
    }

    @Override
    public String setTestDesigner() {
        return "Mauricio Gurrola";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_165_CrearFaqCedisCaracteresAcentuadosPerfilAdmin_test";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}

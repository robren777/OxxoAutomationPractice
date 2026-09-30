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

/**DESCRIPCION: Validar que el perfil ADMIN puede editar una FAQ existente de CEDIS agregando caracteres acentuados en Pregunta y Respuesta, y restaurar el contenido original.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_167 CA01 - Verificar que al editar una FAQ existente se pueden agregar caracteres acentuados en Pregunta y Respuesta - perfil ADMIN - FAQS CEDIS
*
*PREREQUISITOS:
*1. Perfil ADMIN (usuario 5555555) con acceso a FAQS CEDIS y al menos una FAQ existente.
*2.Liga de Portal Cometa
*
*INSUMOS:
*Usuario ADMIN disponible en QA.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_167_EditarFaqCedisCaracteresAcentuadosPerfilAdmin extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_167_EditarFaqCedisCaracteresAcentuadosPerfilAdmin_test(HashMap<String, String> data) throws Exception {
        testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");
        u = new SeleniumUtil(new MEdgeTest(), true);
        PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
        faqsCedisPage faqsCedis = new faqsCedisPage(u, testCase);
        String user = data.get("user");
        String url = data.get("Url");
        String perfil = "ADMIN";
        String caracteresAcentuados = " aéíóú AÉÍÓÚ";
        // CA01 valida el acento en "Acción"; ':' se excluye porque la validación de seguridad del formulario lo filtra.
        String prefijoRespuesta = "Acción ";

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
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El campo Usuario muestra el valor '5555555'");

        /*
         *                                             PASO 3
         ***************************************************************************/
        addStep("Escribir la contraseña del perfil en el campo Contraseña");
        boolean resultStep3 = password != null && !password.trim().isEmpty();
        assertTrue(resultStep3, "Paso 3 fallido: El campo Contraseña no cuenta con una contraseña configurada para el perfil ADMIN");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: La contraseña del perfil ADMIN fue obtenida sin exponerla en evidencia");

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
        addStep("Dar clic en 'FAQS CEDIS' en la barra de navegación");
        boolean resultStep6 = faqsCedis.abrirYValidarFaqsCedis();
        assertTrue(resultStep6, "Paso 6 fallido: No se visualiza la pantalla Preguntas Frecuentes con el listado de FAQs");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Se visualiza la pantalla Preguntas Frecuentes con el listado de FAQs");

        /*
         *                                             PASO 7
         ***************************************************************************/
        addStep("Identificar la primera FAQ del listado y dar clic en su icono de lápiz amarillo");
        boolean resultStep7 = faqsCedis.abrirFormularioEditarPrimeraFaq();
        assertTrue(resultStep7, "Paso 7 fallido: El formulario de edición no se abre mostrando el contenido actual de la FAQ");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El formulario de edición se abre mostrando el contenido actual de la FAQ");

        /*
         *                                             PASO 8
         ***************************************************************************/
        addStep("Anotar el contenido original de los campos Pregunta y Respuesta para restaurarlos al final de la prueba");
        String preguntaOriginal = faqsCedis.obtenerPreguntaActualFormulario();
        String respuestaOriginal = faqsCedis.obtenerRespuestaActualFormulario();
        boolean resultStep8 = preguntaOriginal != null && !preguntaOriginal.trim().isEmpty()
                && respuestaOriginal != null && !respuestaOriginal.trim().isEmpty();
        assertTrue(resultStep8, "Paso 8 fallido: No fue posible documentar el contenido original de Pregunta y Respuesta");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El contenido original queda documentado para restauración posterior");

        /*
         *                                             PASO 9
         ***************************************************************************/
        addStep("En el campo Pregunta, posicionar el cursor al final del texto existente y agregar los caracteres: ' aéíóú AÉÍÓÚ'");
        boolean resultStep9 = faqsCedis.agregarTextoAlFinalPregunta(caracteresAcentuados);
        assertTrue(resultStep9, "Paso 9 fallido: El campo Pregunta no muestra los 10 caracteres acentuados agregados");
        String preguntaEditada = preguntaOriginal + caracteresAcentuados;
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: Los 10 caracteres acentuados requeridos por CA01 son visibles en Pregunta");

        /*
         *                                             PASO 10
         ***************************************************************************/
        addStep("En el campo Respuesta, posicionar el cursor al inicio y agregar el texto: 'Acción ' antes del contenido existente");
        boolean resultStep10 = faqsCedis.agregarTextoAlInicioRespuesta(prefijoRespuesta);
        assertTrue(resultStep10, "Paso 10 fallido: El campo Respuesta no muestra 'Acción ' seguido del contenido previo");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El campo Respuesta muestra 'Acción ' seguido del contenido previo con el carácter ó visible y correcto");

        /*
         *                                             PASO 11
         ***************************************************************************/
        addStep("Dar clic en el botón de guardar del formulario de edición");
        boolean resultStep11 = faqsCedis.guardarFaq();
        assertTrue(resultStep11, "Paso 11 fallido: El sistema no guarda los cambios sin mostrar mensajes de error");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El sistema guarda los cambios sin mostrar mensajes de error");

        /*
         *                                             PASO 12
         ***************************************************************************/
        addStep("Localizar la FAQ editada en el listado y verificar que el título de la pregunta muestra los caracteres acentuados que fueron agregados: aéíóú AÉÍÓÚ");
        boolean resultStep12 = faqsCedis.validarPreguntaEnListado(preguntaEditada);
        assertTrue(resultStep12, "Paso 12 fallido: La pregunta en el listado no muestra los 10 caracteres acentuados correctamente");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: La pregunta en el listado muestra los 10 caracteres acentuados correctamente y persisten tras guardar");

        /*
         *                                             PASO 13
         ***************************************************************************/
        addStep("Dar clic en el icono de lápiz de la FAQ para volver al formulario de edición y restaurar el contenido original documentado en el paso 8");
        boolean resultStep13Open = faqsCedis.abrirFormularioEditarFaqPorPregunta(preguntaEditada);
        assertTrue(resultStep13Open, "Paso 13 fallido: No fue posible abrir la FAQ editada para restaurar el contenido original");
        boolean resultStep13 = faqsCedis.restaurarPreguntaYRespuestaOriginal(preguntaOriginal, respuestaOriginal);
        assertTrue(resultStep13, "Paso 13 fallido: El contenido original no fue restaurado correctamente");
        testCase.addTextEvidenceCurrentStep("Resultado esperado validado: El contenido original es restaurado. La FAQ queda en su estado previo a la prueba");
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
        return "*1. Perfil ADMIN (usuario 5555555) con acceso a FAQS CEDIS y al menos una FAQ existente en QA.      \\r\\n"
                + "*2.Liga de Portal Cometa";
    }

    @Override
    public String setTestDescription() {
        return "Validar que el perfil ADMIN puede editar una FAQ existente de CEDIS agregando caracteres acentuados en Pregunta y Respuesta, y restaurar el contenido original.";
    }

    @Override
    public String setTestDesigner() {
        return "Mauricio Gurrola";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_167_EditarFaqCedisCaracteresAcentuadosPerfilAdmin_test";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}

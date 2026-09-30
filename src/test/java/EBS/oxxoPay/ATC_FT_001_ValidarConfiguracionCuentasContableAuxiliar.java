package EBS.oxxoPay;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.Sikuli.MetodosSikuli;
import EBS.Sikuli.PatternUtil;
import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_ValidarConfiguracionCuentasContableAuxiliar extends BaseExecution {

    @Test(dataProvider = "data-provider")

    public void ATC_FT_001_ValidarConfiguracionCuentasContableAuxiliar_test(HashMap<String, String> data)
            throws Exception {
        /*
         * VARIABLES
         */
        String ebsUrl = data.get("server");
        String userName = data.get("userName");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String ebsPattern = data.get("ebsPattern");
        String responsabilidad = data.get("responsabilidad");
        String configuracion = data.get("carpeta1");
        String financiera = data.get("carpeta2");
        String flexfields = data.get("carpeta3");
        String clave = data.get("carpeta4");
        String nombre = data.get("nombre");
        String valor = data.get("valor");
        String descripcion = data.get("descripcion");
        String cierreAnual = data.get("cierreAnual");
        String valor2 = data.get("valor2");
        String descripcion2 = data.get("descripcion2");
        String cierreAnual2 = data.get("cierreAnual2");
        String valor3 = data.get("valor3");
        String descripcion3 = data.get("descripcion3");

        /*
         * UTILERIAS
         */
        SeleniumUtil u = new SeleniumUtil(new IETest(), true);
        LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
        EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
        SikuliUtil sk = new SikuliUtil();
        String pathImg = sk.PatternPath("EBS\\"+ebsPattern);
        Pattern pathimg = new Pattern();
        ArrayList<String> ListImage = new ArrayList<String>();
        HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
        ArrayList<Pattern> patterns = new ArrayList<Pattern>();
        File Fol = new File(pathImg);
        File Folder = new File(pathImg);

        MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

        PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);

        /*
         * PASOS DEL CASO DE PRUEBA, MATRIZ: RST Devoluciones OXXO PAY
         */

        // PASO 1
        addStep("Ingresar al ambiente de EBS.");
        logger.info("EBS URL: " + ebsUrl);
        System.out.println("EBS URL: " + ebsUrl);
        logger.info("User Name: " + userName);
        System.out.println("User Name: " + userName);
        // PASO 2
        addStep("Seleccionar idioma: Español Latinoamericano.");
        // PASO 3
        addStep("Ingresar usuario y contraseña.");
        // PASO 4
        addStep("Dar clic en el botón de 'Conectese'.");
        inicio.login();
        // PASO 5
        addStep("Ingresar a la responsabilidad 'MXOXXO-GLXXXXX-SUPER USUARIO'");
        paginaInicial.clickOnResponsabilidad(responsabilidad);
        // PASO 6
        addStep("Seleccionar: Configuración : Financiera -> Flexfields -> Clave -> Valores");
        paginaInicial.clickOnCarpeta(configuracion);
        paginaInicial.clickOnCarpeta(financiera);
        paginaInicial.clickOnCarpeta(flexfields);
        paginaInicial.clickOnCarpeta(clave);
        paginaInicial.clickValoresGLSuperUsuario();
        u.hardWait(10);
        patternUtil.runApplet();
        u.hardWait(8);
        // PASO 7
        addStep("Seleccionar value set, Name = 'FEMCO_GL_CUENTA', Clic en botón 'Find'.");
        patternUtil.keyAlt("j");
        patternUtil.enterText(nombre);
        patternUtil.keyAlt("n");
        // PASO 8
        addStep("Posicionarse en la celda de Value, Clic en F11, capturar value = " + valor + ", Ctrl F11.");
        patternUtil.clickImage("valorazul");
        patternUtil.f11();
        patternUtil.enterText(valor);
        patternUtil.ctrlF11();
        patternUtil.nTAB(1);
        u.hardWait(1);
        String descripcionApplet = p.controlCopiar();
        System.out.println("Descripcion del data provider: " + descripcion);
        System.out.println("Descripcion del Applet: " + descripcionApplet);
        assertEquals(descripcion, descripcionApplet);
        patternUtil.nTAB(6);
        u.hardWait(3);
        patternUtil.validatePattern("calificadores");
        assertTrue(patternUtil.validatePattern("calificadores"));
        patternUtil.clickAceptarParametros();
        u.hardWait(3);
        patternUtil.nTAB(3);
        u.hardWait(3);
        String cierreAnualApplet = p.controlCopiar();
        System.out.println("Cierre Anual del data provider: " + cierreAnual);
        System.out.println("Cierre Anual del Applet: " + cierreAnualApplet);
        assertEquals(cierreAnual, cierreAnualApplet);
        u.hardWait(3);
        patternUtil.clickAceptarParametros();
        u.hardWait(3);
        // PASO 9
        addStep("Posicionarse en la celda de Value, Clic en F11, capturar value = " + valor2 + ", Ctrl F11. ");
        patternUtil.f11();
        patternUtil.enterText(valor2);
        patternUtil.ctrlF11();
        patternUtil.nTAB(1);
        u.hardWait(3);
        String descripcionApplet2 = p.controlCopiar();
        System.out.println("Descripcion del data provider: " + descripcion2);
        System.out.println("Descripcion del Applet: " + descripcionApplet2);
        assertEquals(descripcion2, descripcionApplet2);
        patternUtil.nTAB(6);
        u.hardWait(3);
        patternUtil.validatePattern("calificadores2");
        assertTrue(patternUtil.validatePattern("calificadores2"));
        patternUtil.clickAceptarParametros();
        u.hardWait(3);
        patternUtil.nTAB(3);
        u.hardWait(2);
        String cierreAnual2Applet = p.controlCopiar();
        System.out.println("Cierre Anual 2 del data provider: " + cierreAnual2);
        System.out.println("Cierre Anual 2 del Applet: " + cierreAnual2Applet);
        assertEquals(cierreAnual2, cierreAnual2Applet);
        patternUtil.closeText();
        patternUtil.closeText();
        patternUtil.clickImage("valornum");
        u.hardWait(3);
        // PASO 10
        addStep("Posicionarse en la celda de Value, Clic en F11, capturar value = " + valor3 + ", Ctrl F11. ");
        patternUtil.f11();
        patternUtil.enterText(valor3);
        patternUtil.ctrlF11();
        patternUtil.nTAB(1);
        u.hardWait(2);
        String descripcion3Applet = p.controlCopiar();
        System.out.println("Descripcion 3 del data provider: " + descripcion3);
        System.out.println("Descripcion 3 del Applet: " + descripcion3Applet);
        assertEquals(descripcion3, descripcion3Applet);
        patternUtil.nTAB(6);
        u.hardWait(3);
        patternUtil.validatePattern("calificadores");
        assertTrue(patternUtil.validatePattern("calificadores"));
        patternUtil.clickAceptarParametros();
        u.hardWait(3);
        patternUtil.nTAB(3);
        u.hardWait(2);
        testCase.addScreenShotCurrentStep("cierreAnualFinal");
        String cierreAnualFinal = p.controlCopiar();
        System.out.println("Cierre Anual del data provider: " + cierreAnual);
        System.out.println("Cierre Anual del Applet: " + cierreAnualFinal);
        assertEquals(cierreAnual, cierreAnualFinal);
        testCase.addTextEvidenceCurrentStep("Cierre Anual del data provider: " + cierreAnual);
        testCase.addTextEvidenceCurrentStep("Cierre Anual del Applet: " + cierreAnualFinal);
        patternUtil.clickAceptarParametros();
        patternUtil.closeWindow();
        patternUtil.ventanaConfirmarSalir("a");
        u.close();

    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_001_ValidarConfiguracionCuentasContableAuxiliar_test";
    }

    @Override
    public String setTestDescription() {
        return "Validar que las cuentas contables y la cuenta auxiliar esten configuradas. Tener responsabilidad 'MXOXXO-GLXXXXX-Super Usuario'.";
    }

    @Override
    public String setTestDesigner() {
        return "JessicaR@hexaware.com";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }

    @Override
    public String setPrerequisites() {
        return "Tener responsabilidad 'MXOXXO-GLXXXXX-Super Usuario'";
    }

    @Override
    public void beforeTest() {
        // TODO Auto-generated method stub

    }

}

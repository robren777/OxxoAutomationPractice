package WEB.updateNomina.om;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import WEB.om.MetodosExtra;
import WEB.util.AuxMethods;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class MofUpdNom {
    //Elementos
    //Access Control
    By aEnlaceMof = By.xpath("//*[text()[normalize-space()='Monitor de Obligaciones Fiscales']]");
    //Menu Mof
    By aMenuMantenimiento = By.xpath("//*[@id='ddtopmenubar']/ul/li[9]/a");
    By aSubmenuPeriodo = By.xpath("//*[@id='ddsubmenu8927-80']/li[2]/a");
    //Seccion Mantenimento > Periodos
    By divSelectAnio = By.xpath("//*[@id='form1:anioId']/div[2]");
    By liOption2024 = By.xpath("//*[@id='form1:anioId_panel']/ul/li[2]");
    By liOption2023 = By.xpath("//*[@id='form1:anioId_panel']/ul/li[3]");
    By selectMostrarNumeroDeRegistros = By.xpath("//*[@id='form1:tablaPeriodos_paginator_bottom']/select");
    By option15Registros = By.xpath("//*[@id='form1:tablaPeriodos_paginator_bottom']/select/option[3]");
    By spanListaDeRegistros2 = By.xpath("//*[@id='form1:tablaPeriodos_paginator_bottom']/span[4]/span[2]");
    By divOptionJulio = By.xpath("//div[text()='Julio']");
    By inputBotonAceptarActivar = By.xpath("//*[@id='form1:btnActivarPeriodo']");
    By inputBotonSiAlerta = By.xpath("//*[@id='form1:j_idt223']");

    //Seccion Contabilidad > Principal
    By xmlCPDeduccionesb = By.xpath("/html/body/div[1]/div/div[1]/div/div[2]/div/div/form[2]/div/ul/li[3]");
    By xmlCPDNomina = By.xpath("//*[@id='formPrincipal2:j_idt239']");
    By xmlCPDOperativas = By.xpath("/html/body/div[1]/div/div[1]/div/div[2]/div/div/form[2]/div/ul/li[3]/ul/table/tbody/tr/td/ul/li[5]/a/span");
    By xmlCPDPersonales = By.xpath("/html/body/div[1]/div/div[1]/div/div[2]/div/div/form[2]/div/ul/li[3]/ul/table/tbody/tr/td/ul/li[6]/a/span");
    By xmlCPDEstadoBtnAgregar = By.xpath("//*[@id='formEstado1:btnAgregarDE']");
    By selectCPDNSemana = By.xpath("//*[@id='formNomina1:cbSemanaDN']");
    By checkboxCPDNS = By.xpath("//*[@id='formNomina1:tablaNomina:SelectAllCheckBox']/div[2]");

    private SeleniumUtil u;
    private WebDriverWait wait;
    private TestCase testCase;
    private AuxMethods aux;
    private MetodosExtra me;
    private UpdateNomina updNom;

    /**
     * Constructor.
     *
     * @param u
     * @param testCase
     * @author Mario Alberto De Leon Rdz.
     */
    public MofUpdNom(SeleniumUtil u, TestCase testCase) {
        super(); // Llama al constructor de la clase padre (en este caso, Object)
        this.u = u; // Asigna el objeto SeleniumUtil pasado como par�metro a la variable de instancia
        this.wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(60)); // Inicializa WebDriverWait con el driver de SeleniumUtil y un timeout de 60 segundos
        this.testCase = testCase; // Asigna el objeto TestCase pasado como par�metro a la variable de instancia
        this.updNom = new UpdateNomina(u, testCase);
    }

    public void IngresarAPortalMof(){
        wait.until(ExpectedConditions.elementToBeClickable(aEnlaceMof));
        u.click(aEnlaceMof);
    }

    public void SelectMOFMantenimientoPeriodos(){
        wait.until(ExpectedConditions.presenceOfElementLocated(aMenuMantenimiento));
        u.highLight(aMenuMantenimiento);
        u.mouseOver(aMenuMantenimiento);
        testCase.addScreenShotCurrentStep("Menu mantenimiento");
        u.hardWait(3);
        u.highLight(aSubmenuPeriodo);

        u.click(aSubmenuPeriodo);

    }

    public void SeleccionarPeriodo(String anio){
        wait.until(ExpectedConditions.elementToBeClickable(divSelectAnio));
        u.highLight(divSelectAnio);
        u.click(divSelectAnio);
        WebElement optionAnio = u.getDriver().findElement(By.xpath("//*[@id='form1:anioId_panel']/ul/li[text()='"+anio+"']"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='form1:anioId_panel']/ul/li[text()='"+anio+"']")));
        optionAnio.click();

    }
    public void SeleccionarTamanoListaDeRegistros(){
    	u.hardWait(3);
    	wait.until(ExpectedConditions.presenceOfElementLocated(selectMostrarNumeroDeRegistros));
        u.click(selectMostrarNumeroDeRegistros);
        u.click(option15Registros);
    }
    public void ActivarPeriodo(String mes){
        //TODO: parametrizar este metodo para activar cualquier periodo, ingresar parametro en xpath.
        wait.until(ExpectedConditions.presenceOfElementLocated(divOptionJulio));
        //u.click(divOptionJulio);
    	WebElement optionMes = u.getDriver().findElement(By.xpath("//div[text()='"+mes+"']"));
    	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='"+mes+"']")));
        optionMes.click();
        wait.until(ExpectedConditions.elementToBeClickable(inputBotonAceptarActivar));
        u.click(inputBotonAceptarActivar);
        wait.until(ExpectedConditions.elementToBeClickable(inputBotonSiAlerta));
        u.click(inputBotonSiAlerta);
    }

    public void clicCPDeduccionesNomina(){
        wait.until(ExpectedConditions.presenceOfElementLocated(xmlCPDeduccionesb));
        u.scrollToElement(xmlCPDeduccionesb);
        u.highLight(xmlCPDeduccionesb);
        u.mouseOver(xmlCPDeduccionesb);
        u.highLight(xmlCPDNomina);
        u.mouseOver(xmlCPDNomina);
        testCase.addScreenShotCurrentStep(u, "xmlCPDNomina");
        u.click(xmlCPDNomina);
    }
    public void SeleccionarCPDNSemana(String semana){
        wait.until(ExpectedConditions.elementToBeClickable(selectCPDNSemana));
        u.click(selectCPDNSemana);

        WebElement optionSemana = u.getDriver().findElement(By.xpath("//*[@id='formNomina1:cbSemanaDN_panel']/ul/li[text()='"+semana+"']"));
    	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='formNomina1:cbSemanaDN_panel']/ul/li[text()='"+semana+"']")));
        optionSemana.click();
        //Esto es para tomar captura de los sueldos de los empleados
        wait.until(ExpectedConditions.elementToBeClickable(checkboxCPDNS));
        testCase.addScreenShotCurrentStep("Sueldo Empleados");
    }
}

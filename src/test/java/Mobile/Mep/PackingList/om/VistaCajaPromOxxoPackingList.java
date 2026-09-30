package Mobile.Mep.PackingList.om;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class VistaCajaPromOxxoPackingList {
    /*
     * Variables Globales
     ******************************************************************************************/
    private TestCase testCase;
    private AppiumUtil au;
    private BaseExecution baseExecution;
    private int smallHardWait = 2000;
    private WebDriverWait wait = null;
    /*
     * Localizadores CajaPromOxxoPackingList
     ******************************************************************************************/
    private By modalPeriodoReporteFinalizado = By
	    .xpath("//android.widget.TextView[@text=\"Periodo de reporte finalizado\"]");

    // Main menu
    private By btnBackArrow = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[1]");
    private By subtitle = By.xpath("//android.widget.TextView[@text=\"Reporta por:\"]");
    private By btnVerReportes = By.xpath("//android.widget.TextView[@text=\"Ver todos los reportes\"]");
    private By btnQuestionMark = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[2]");
    private By popUpGuia = By
	    .xpath("//android.widget.TextView[@text=\"Guía para Realizar verificación de materiales promocionales\"]");
    private By btnRealizarPackingList = By.xpath("//android.widget.TextView[@text=\"Realizar Packing List\"]");
    private By btnCajaCompleta = By.xpath("//android.widget.TextView[@text=\"Caja Completa\"]");
    private By title = By.xpath("//android.widget.TextView[@text=\"Caja PromOXXO - Packing list\"]");

    // Todos los reportes
    private By titleReportes = By.xpath("//android.widget.TextView[@text=\"Todos los reportes\"]");
    private By labelNoReportes = By.xpath("//android.widget.TextView[@text=\"No hay materiales reportados\"]");
    private By expandFirstReport = By
	    .xpath("(//android.view.ViewGroup[starts-with(@content-desc, 'Faltantes Periodo Promocional')])[1]");
    private By descriptionReportLineOne = By.xpath(
	    "(//android.view.ViewGroup[starts-with(@content-desc,'Faltantes Periodo Promocional')]/android.widget.TextView)[3]");
    private By descriptionReportLineTwo = By.xpath(
	    "(//android.view.ViewGroup[starts-with(@content-desc,'Faltantes Periodo Promocional')]/android.widget.TextView)[4]");

    // CajaCompleta
    private By titleCajaCompleta = By.xpath("//android.widget.TextView[@text=\"Reporte de Caja Completa\"]");
    private By dropdownMotivo = By.xpath("//android.widget.TextView[@text=\"Selecciona un motivo\"]");
    private By opcionNoRecibi = By.xpath("//android.widget.TextView[@text=\"No recibí la caja\"]");
    private By btnFinalizar = By.xpath("//android.widget.TextView[@text=\"Finalizar\"]");

    // PackingList
    private By titlePackingList = By.xpath("//android.widget.TextView[@text=\"Caja PromOXXO - Packing list\"]");
    private By btnCamaraAntesDeUnaFoto = By.xpath("(//android.view.ViewGroup)[last()]");
    private By btnCamara = By.xpath("(//android.view.ViewGroup)[last()-1]");
    private By modalGuia = By.xpath("//android.widget.TextView[@text=\"Guía para toma de fotografías\"]");
    private By btnCerrar = By.xpath("//android.widget.TextView[@text=\"Cerrar\"]");
    private By btnGaleria = By.xpath("//android.widget.ImageView");
    private By btnFlechaDerecha = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[3]");
    private By btnTomarFoto = By.xpath("//android.widget.TextView[@text=\"Tomar Foto\"]");
    private By btnRetake = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[3]");
    private By btnDelete = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[4]");
    private By btnContinuar = By.xpath("//android.widget.TextView[@text=\"Continuar\"]");
    private By popupFotoEliminada = By.xpath("//android.widget.TextView[@text=\"La foto ha sido eliminada.\"]");

    // Reporte de Materiales Faltantes
    private By titleReporteMateriales = By
	    .xpath("//android.widget.TextView[@text=\"Reporte de Materiales Faltantes\"]");
    private By subTitleReporteMateriales = By.xpath("//android.widget.TextView[@text=\"Selecciona el material:\"]");
    private By checkBoxAll = By.xpath("(//android.view.ViewGroup[@resource-id='checkbox-container'])[1]/*[1]");
    private By checkBoxOne = By.xpath("(//android.view.ViewGroup[@resource-id=\"checkbox-container\"])[2]/*[1]");
    private By popUpEnvioExitoso = By.xpath("//android.widget.TextView[@text=\"Reporte enviado exitosamente\"]");
    private By labelFirstElement = By.xpath("//android.widget.TextView[@text=\"Banner Check Out\"]");
    /**
     * Constructor
     ******************************************************************************************/
    /**
     * Constructor de la clase PackingListUtils
     * 
     * @author GTIM
     * @param au       (AppiumUtil): objeto de la clase
     * @param testCase (TestCase): objeto de la clase
     */
    public VistaCajaPromOxxoPackingList(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
	this.testCase = testCase;
	this.au = au;
	this.baseExecution = baseExecution;
	au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Esperar 10 Segundos por cada elemento
									      // que falte por aparecer
	this.wait = new WebDriverWait(au.driver, Duration.ofSeconds(10));
    }
    // =========================
    // Helpers
    // =========================
    private Boolean isDisplayed(By element) {
	try {
	    return au.driver.findElements(element).size() > 0;
	} catch (Exception ex) {
	    throw new RuntimeException("Error al validar el elemento", ex);
	}
    }
    private Boolean isDisplayed(By element,String tituloFoto) {
 	try {
 	    boolean flag = au.driver.findElements(element).size() > 0;
 	    takeEvidence(tituloFoto);
 	    return flag;
 	} catch (Exception ex) {
 	    throw new RuntimeException("Error al validar el elemento", ex);
 	}
     }
    private Boolean isSelected(By element) {
 	try {
 	   return au.driver.findElement(element).getAttribute("resource-id").equals("checkbox-checked-icon");
 	} catch (Exception ex) {
 	    throw new RuntimeException("Error al validar el elemento", ex);
 	}
     }
    private Boolean isSelected(By element,String tituloFoto) {
 	try {
 	    Thread.sleep(smallHardWait);
 	    boolean flag = au.driver.findElement(element).getAttribute("resource-id").equals("checkbox-checked-icon");
 	    takeEvidence(tituloFoto);
 	    return flag;
 	} catch (Exception ex) {
 	    throw new RuntimeException("Error al validar el elemento", ex);
 	}
     }
    private Boolean isEnabled(By element) {
 	try {
 	   return au.driver.findElement(element).isEnabled();
 	} catch (Exception ex) {
 	    throw new RuntimeException("Error al validar el elemento", ex);
 	}
     }
    private Boolean isEnabled(By element,String tituloFoto) {
 	try {
 	    wait.until(driver -> au.driver.findElement(element).isEnabled());
 	    boolean flag = au.driver.findElement(element).isEnabled();
 	    takeEvidence(tituloFoto);
 	    return flag;
 	} catch (Exception ex) {
 	    throw new RuntimeException("Error al validar el elemento", ex);
 	}
     }
    private void takeEvidence(String tituloFoto) {
	testCase.addScreenShotCurrentStep(au, "");
	testCase.addTextEvidenceCurrentStep(tituloFoto);
    }

    private void click(By element, String elementName) throws Exception {
	try {
	    WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
	    wait.until(ExpectedConditions.elementToBeClickable(element)).click();
	} catch (Exception e) {
	    throw new Exception("Error al intentar hacer click en " + elementName, e);
	}
    }
    private String getText(By element) throws Exception {
	try {
	    WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
	    return wait.until(ExpectedConditions.visibilityOfElementLocated(element)).getText();
	} catch (Exception e) {
	    throw new Exception("Error al obtener el texto del elemento.", e);
	}
    }
    
    // =========================
    // Main menu
    // =========================
    public Boolean validatePopupPeriodoReporteFinalizadoIsDisplayed() {
	return isDisplayed(modalPeriodoReporteFinalizado,"Se muestra el popUp");
    }

    public Boolean validateIfPopupFotoEliminadaIsDisplayed() {
	return isDisplayed(popupFotoEliminada,"Se despliega el popup de foto eliminada y se elimina la foto");
    }

    public Boolean validateIfBtnBackArrowIsDisplayed() {
	return isDisplayed(btnBackArrow);
    }

    public Boolean validateIfSubtitleIsDisplayed() {
	return isDisplayed(subtitle);
    }

    public Boolean validateIfBtnVerReportesIsDisplayed() {
	return isDisplayed(btnVerReportes);
    }

    public Boolean validateIfBtnQuestionMarkIsDisplayed() {
	return isDisplayed(btnQuestionMark);
    }

    public Boolean validateIfBtnRealizarPackingListIsDisplayed() {
	return isDisplayed(btnRealizarPackingList);
    }

    public Boolean validateIfBtnCajaCompletaIsDisplayed() {
	return isDisplayed(btnCajaCompleta,"Se muestra la pantalla Caja PromOXXO - Packing List con todos sus elementos");
    }

    public Boolean validateIfTitleIsDisplayed() {
	return isDisplayed(title);
    }

    /**
     * Realiza clic en btnBackArrow
     * 
     * @author gtim
     * @return void
     * @throws Exception
     * @date 09/07/2026
     */
    public void clickBtnBackArrow() throws Exception {
	click(btnBackArrow, "btnBackArrow");
    }

    /**
     * Realiza clic en btnVerReportes
     * 
     * @author gtim
     * @return void
     * @throws Exception
     * @date 09/07/2026
     */
    public void clickBtnVerReportes() throws Exception {
	click(btnVerReportes, "btnVerReportes");
    }

    /**
     * Realiza clic en btnQuestionMark
     * 
     * @author gtim
     * @return void
     * @throws Exception
     * @date 09/07/2026
     */
    public void clickBtnQuestionMark() throws Exception {
	click(btnQuestionMark, "btnQuestionMark");
    }

    /**
     * Realiza clic en btnRealizarPackingList
     * 
     * @author gtim
     * @return void
     * @throws Exception
     * @date 09/07/2026
     */
    public void clickBtnRealizarPackingList() throws Exception {
	click(btnRealizarPackingList, "btnRealizarPackingList");
    }

    /**
     * Realiza clic en btnCajaCompleta
     * 
     * @author gtim
     * @return void
     * @throws Exception
     * @date 09/07/2026
     */
    public void clickBtnCajaCompleta() throws Exception {
	click(btnCajaCompleta, "btnCajaCompleta");
    }

    public Boolean validateIfPopUpGuiaIsDisplayed() {
	return isDisplayed(popUpGuia,"Se muestra el pop up Guía para Realizar verificación de materiales promocionales");
    }

    // =========================
    // Todos los reportes
    // =========================

    public Boolean validateIfTitleReportesIsDisplayed() {
	return isDisplayed(titleReportes);
    }

    public Boolean validateIfExpandFirstReportIsDisplayed() {
	return isDisplayed(expandFirstReport);
    }

    public void clickExpandFirstReport() throws Exception {
	click(expandFirstReport, "expandFirstReport");
    }

    public Boolean validateIfDescriptionReportLineOneIsDisplayed() {
	return isDisplayed(descriptionReportLineOne,"Se muestra la pantalla 'Todos los reportes' con reportes desplegados");
    }

    public Boolean validateIfDescriptionReportLineTwoIsDisplayed() {
	return isDisplayed(descriptionReportLineTwo);
    }

    public Boolean validateIfTodosLosReportesIsDisplayed() {
	Boolean flag = isDisplayed(expandFirstReport) || isDisplayed(labelNoReportes);
	takeEvidence("Se muestra la pantalla 'Todos los reportes'");
	return flag;
    }

    // =========================
    // Caja Completa
    // =========================

    public Boolean validateIfTitleCajaCompletaIsDisplayed() {
	return isDisplayed(titleCajaCompleta);
    }

    public Boolean validateIfDropdownMotivoIsDisplayed() {
	return isDisplayed(dropdownMotivo);
    }

    public void clickDropdownMotivo() throws Exception {
	click(dropdownMotivo, "dropdownMotivo");
    }

    public Boolean validateIfOpcionNoRecibiIsDisplayed() {
	return isDisplayed(opcionNoRecibi,"Se muestra la lista de opciones.");
    }

    public void clickOpcionNoRecibi() throws Exception {
	click(opcionNoRecibi, "opcionNoRecibi");
    }

    public Boolean validateIfBtnFinalizarIsDisplayed() {
	return isDisplayed(btnFinalizar);
    }
    public Boolean validateIfBtnFinalizarIsEnabled() {
	return isEnabled(btnFinalizar,"La opcion fue seleccionada y el boton esta disponible");
    }

    public void clickBtnFinalizar() throws Exception {
	click(btnFinalizar, "btnFinalizar");
    }
    public Boolean validateIfPopUpEnvioExitosoCajaIsDisplayed() {
	return isDisplayed(popUpEnvioExitoso,"Se genero el reporte de caja comleta.");
    }
    // =========================
    // Packing List
    // =========================

    public Boolean validateIfTitlePackingListIsDisplayed() {
	return isDisplayed(titlePackingList);
    }

    public Boolean validateIfBtnCamaraAntesDeUnaFotoIsDisplayed() throws InterruptedException {
	// Se agrega hardwait por falta de elemento diferenciador
	Thread.sleep(smallHardWait);
	return isDisplayed(btnCamaraAntesDeUnaFoto,"Se muestra la camara");
    }

    public void clickBtnCamaraAntesDeUnaFoto() throws Exception {
	click(btnCamaraAntesDeUnaFoto, "btnCamaraAntesDeUnaFoto");
    }

    public Boolean validateIfBtnCamaraIsDisplayed() {
	return isDisplayed(btnCamara);
    }

    public void clickBtnCamara() throws Exception {
	click(btnCamara, "btnCamara");
    }

    public Boolean validateIfModalGuiaIsDisplayed() {
	return isDisplayed(modalGuia,"Se muestra pop up");
    }

    public Boolean validateIfBtnCerrarIsDisplayed() {
	return isDisplayed(btnCerrar);
    }

    public void clickBtnCerrar() throws Exception {
	click(btnCerrar, "btnCerrar");
    }

    public Boolean validateIfBtnGaleriaIsDisplayed() {
	return isDisplayed(btnGaleria);
    }

    public void clickBtnGaleria() throws Exception {
	click(btnGaleria, "btnGaleria");
    }

    public Boolean validateIfBtnFlechaDerechaIsDisplayed() {
	return isDisplayed(btnFlechaDerecha,"Se despliegan el icono de galeria y el icono de flecha a la derecha");
    }

    public void clickBtnFlechaDerecha() throws Exception {
	click(btnFlechaDerecha, "btnFlechaDerecha");
    }

    public Boolean validateIfBtnTomarFotoIsDisplayed() {
	return isDisplayed(btnTomarFoto,"Se desplego la pantalla Photo Gallery\"");
    }


    public void clickBtnTomarFoto() throws Exception {
	click(btnTomarFoto, "btnTomarFoto");
    }

    public Boolean validateIfBtnRetakeIsDisplayed() {
	return isDisplayed(btnRetake);
    }

    public void clickBtnRetake() throws Exception {
	click(btnRetake, "btnRetake");
    }

    public Boolean validateIfBtnDeleteIsDisplayed() {
	return isDisplayed(btnDelete);
    }

    public void clickBtnDelete() throws Exception {
	click(btnDelete, "btnDelete");
    }

    public Boolean validateIfBtnContinuarIsDisplayed() {
	return isDisplayed(btnContinuar);
    }

    public void clickBtnContinuar() throws Exception {
	click(btnContinuar, "btnContinuar");
    }

    // =========================
    // Reporte de Materiales Faltantes
    // =========================
    public Boolean validateIfBtnFlechaDerechaReportesIsDisplayed() {
 	return isDisplayed(btnFlechaDerecha);
     }
    public Boolean validateIfBtnTomarFotoReporteMaterialesIsDisplayed() {
 	return isDisplayed(btnTomarFoto);
     }
    public Boolean validateIfTitleReporteMaterialesIsDisplayed() {
	return isDisplayed(titleReporteMateriales);
    }

    public Boolean validateIfSubTitleReporteMaterialesIsDisplayed() {
	return isDisplayed(subTitleReporteMateriales,"Se despliega la pantalla Reporte de Materiales Faltantes.");
    }
    
    public Boolean validateIfCheckBoxAllIsDisplayed() {
	return isDisplayed(checkBoxAll);
    }
    public Boolean validateIfListIsDisplayed() {
	return isDisplayed(labelFirstElement,"Se muestran elementos en la pantalla");
    }

    public void clickCheckBoxAll() throws Exception {
	click(checkBoxAll, "checkBoxAll");
    }

    public Boolean validateIfCheckBoxOneIsDisplayed() {
	return isDisplayed(checkBoxOne);
    }

    public void clickCheckBoxOne() throws Exception {
	click(checkBoxOne, "checkBoxOne");
    }

    public Boolean validateIfPopUpEnvioExitosoIsDisplayed() {
	return isDisplayed(popUpEnvioExitoso);
    }
    public Boolean validateIfPhotoWasTaken() {
	Boolean flag = this.validateIfBtnGaleriaIsDisplayed()&& this.validateIfBtnFlechaDerechaIsDisplayed();
	takeEvidence("Se tomo la foto");
	return flag;
    }
    public Boolean validateIfScreenTakePhotoIsDisplayed() {
	Boolean flag = this.validateIfBtnGaleriaIsDisplayed() && this.validateIfBtnFlechaDerechaReportesIsDisplayed();
	takeEvidence("Se despliega la pantalla de toma de foto");
	return flag;
    }
    
    public Boolean validateIfCheckAllIsSelected() {
	return isSelected(checkBoxAll);
    }
    public Boolean validateIfFirstCheckBoxIsSelected() throws InterruptedException {
 	return isSelected(checkBoxOne,"Se muestra checkbox seleccionado");
     }
    public Boolean validateIfBtnContinuarIsEnabled() throws InterruptedException {
 	return isEnabled(btnContinuar,"Se muestra disponible el boton continuar");
     }
    

    /**
     * Obtiene el texto de la primera línea de descripción del reporte.
     *
     * @author gtim
     * @return String
     * @throws Exception
     * @date 09/07/2026
     */
    public String getDescriptionReportLineOne() throws Exception {
	return getText(descriptionReportLineOne);
    }

    /**
     * Obtiene el texto de la segunda línea de descripción del reporte.
     *
     * @author gtim
     * @return String
     * @throws Exception
     * @date 09/07/2026
     */
    public String getDescriptionReportLineTwo() throws Exception {
	return getText(descriptionReportLineTwo);
    }
}

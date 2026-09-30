package WorkFlows.util.PromoBara;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class editPromocion {
	
	SeleniumUtil u;
	TestCase testCase;

	
	
	By btnPromociones = By.xpath("//a[text()='Promociones']");
	By btnBuscar = By.xpath("//a[text( )=\"Buscar\"]");
	By btnAceptar = By.cssSelector(".ui-dialog-buttonset > button:nth-child(1)");
	By btnGuardar = By.cssSelector("#btnGuardar");
	By btnSalirInf = By.cssSelector("#btnCancelar"); //Inferior pantalla
	By btnEnviar = By.cssSelector("#btnEnviar");
	By dropDPeriodoPromocional = By.cssSelector("#periodoPromocional-button");
	By btnExcel = By.cssSelector("#tabs-1 > table:nth-child(5) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > a:nth-child(1)");
	By btnReportLigas = By.id("btnReporteLigas");
	
	
	//PANTALLA BUSQUEDA DE PROMOCIONES
	By inputfolio = By.cssSelector("#folio");
	By selectA = By.cssSelector("#anio");
	By selectPeriodo_buscar = By.cssSelector("#periodoId");
	By btnBuscaFolio = By.cssSelector("#btnBuscar");
	By btnCrearNuevaPromo = By.cssSelector("#btnCrearNueva > span:nth-child(2)");
	By ckbTodasPromo = By.id("cb_gridBuscar");
	//PANTALLA COBERTURAS
	By pestanaCobertura = By.cssSelector("li.ui-corner-top:nth-child(2)");
	By ckb01PruebaBar = By.xpath("//label[text()=' AEG Prueba BAR ']//preceding-sibling::input");
	public editPromocion (SeleniumUtil u, TestCase testCase) {
		this.u = u;
		this.testCase = testCase;


	}
	
	/**
	 * Selecciona promoción para edición
	 * @param folio
	 */
	public void selectPromo(String folio) {
		
		By btnEdith = By.id("jEditButton_"+folio);
		u.hardWait(1);
		u.scrollToElement(btnPromociones);
		testCase.addBoldTextEvidenceCurrentStep("Ingresar a la opción del menú 'Promociones Buscar'");
		u.highLight(btnPromociones);
		u.mouseOver(btnPromociones);
		u.hardWait(2);
		u.unhighLight(btnPromociones);
		
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.mouseOverAndClick(btnBuscar);
		
		
		
		u.waitWebElement(btnCrearNuevaPromo, 120, 3);
		
		testCase.addBoldTextEvidenceCurrentStep("Ingresa folio");
		u.highLight(inputfolio);
		u.sendKeys(inputfolio, folio);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.unhighLight(inputfolio);
		
		testCase.addBoldTextEvidenceCurrentStep("Selecciona año");
		u.highLight(selectA);
		drop("2022", selectA);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.unhighLight(selectA);
		
		u.hardWait(2);
		
		testCase.addBoldTextEvidenceCurrentStep("Selecciona Periodo");
		u.highLight(selectPeriodo_buscar);
		drop("PruebaAutomatizacion01", selectPeriodo_buscar);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.unhighLight(selectPeriodo_buscar);
		
		testCase.addBoldTextEvidenceCurrentStep("Click en buscar");
		u.highLight(btnBuscaFolio);
		u.click(btnBuscaFolio);
		testCase.addScreenShotCurrentStep("");
		
		testCase.addBoldTextEvidenceCurrentStep("Hacer clic en el icono lápiz 'Editar'.");
		u.highLight(btnEdith);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.click(btnEdith);
		u.getDriver().manage().window().maximize();


	}
	
	/**
	 * Edita la información de la pestaña de coberturas
	 */
	
	public void editCobertura() {
		u.highLight(pestanaCobertura);
		testCase.addScreenShotCurrentStep("");
		u.mouseOverAndClick(pestanaCobertura);
		u.hardWait(1);
		u.unhighLight(pestanaCobertura);
		
		u.click(ckb01PruebaBar);
	}
	
	
	/**
	 * Valida que no exista el botón guardar para promociones
	 * enviadas, aplica solo para usuario categoria
	 */
	public void existeBntAcepGuardar() {
		try {
			u.waitWebElement(dropDPeriodoPromocional, 120, 2);
			u.scrollToElement(btnSalirInf);
			u.highLight(btnSalirInf);
		
			
			if(u.getDriver().findElements(btnGuardar).size() != 0 ||u.getDriver().findElements(btnEnviar).size() != 0) {
				testCase.addScreenShotCurrentStep("");
				throw new AssertionError("Se encontraron los botones Guardar o Salir");
			}
			
			
		}catch(Exception e) {
			
			System.out.println("Error---> " + e);

			throw new AssertionError("Se encontraron los botones Guardar o Salir");
			
		}
		
	}
	
	/**
	 * Selecciona una promoción para clonarla
	 * @param folio
	 */
	public void clonacion(String folio) {
		
		By btnClona = By.id("jClontButton_"+folio);
		u.hardWait(1);
		u.scrollToElement(btnPromociones);
		testCase.addBoldTextEvidenceCurrentStep("Ingresar a la opción del menú 'Promociones Buscar'");
		u.highLight(btnPromociones);
		u.mouseOver(btnPromociones);
		u.hardWait(2);
		u.unhighLight(btnPromociones);
		
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.mouseOverAndClick(btnBuscar);
		
		
		
		u.waitWebElement(btnCrearNuevaPromo, 120, 3);
		
		testCase.addBoldTextEvidenceCurrentStep("Ingresa folio");
		u.highLight(inputfolio);
		u.sendKeys(inputfolio, folio);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.unhighLight(inputfolio);
		
		testCase.addBoldTextEvidenceCurrentStep("Selecciona año");
		u.highLight(selectA);
		drop("2022", selectA);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.unhighLight(selectA);
		
		u.hardWait(2);
		
		testCase.addBoldTextEvidenceCurrentStep("Selecciona Periodo");
		u.highLight(selectPeriodo_buscar);
		drop("PruebaAutomatizacion01", selectPeriodo_buscar);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.unhighLight(selectPeriodo_buscar);
		
		testCase.addBoldTextEvidenceCurrentStep("Click en buscar");
		u.highLight(btnBuscaFolio);
		u.click(btnBuscaFolio);
		testCase.addScreenShotCurrentStep("");

		
		testCase.addBoldTextEvidenceCurrentStep("Hacer clic en el icono lápiz 'Editar'.");
		u.highLight(btnClona);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.click(btnClona);
		u.waitWebElement(pestanaCobertura, 120, 5);
		u.getDriver().manage().window().maximize();



	}
	
	/**
	 * Selecciona una opción de un elemento tipo select
	 * @param op
	 * @param selectElement
	 */
	public void drop(String op, By selectElement) {
		
		Select dropdown=  new Select (u.getDriver().findElement((selectElement)));
		u.hardWait(2);
		System.out.println("Elemento selec <<<<"+selectElement);
		//u.click(selectElement);
		dropdown.selectByVisibleText(op);
	}
	
	/**
	 * Descarga el reporte correspondiente de las promociones creadas
	 * @param reporte
	 */
	public void reports(String reporte) {
		By chkFolio = By.xpath("/html/body/div[2]/table[2]/tbody/tr/td[1]/table/tbody/tr/td/div/table[1]/tbody/tr/td/table/tbody/tr/td/div/div[3]/div[3]/div/table/tbody/tr[2]/td[2]");
		testCase.addBoldTextEvidenceCurrentStep("Ingresar a la opción del menú 'Promociones Buscar'");
		u.highLight(btnPromociones);
		u.mouseOver(btnPromociones);
		u.hardWait(2);
		u.unhighLight(btnPromociones);
		
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.mouseOverAndClick(btnBuscar);
		
		u.waitWebElement(selectA, 120, 3);
		u.hardWait(4);
		
		testCase.addBoldTextEvidenceCurrentStep("Selecciona año");
		u.highLight(selectA);
		drop("2022", selectA);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.unhighLight(selectA);
		
		u.hardWait(2);
		
		testCase.addBoldTextEvidenceCurrentStep("Selecciona Periodo");
		u.highLight(selectPeriodo_buscar);
		drop("PruebaAutomatizacion01", selectPeriodo_buscar);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.unhighLight(selectPeriodo_buscar);
		
		testCase.addBoldTextEvidenceCurrentStep("Click en buscar");
		u.highLight(btnBuscaFolio);
		u.click(btnBuscaFolio);
		testCase.addScreenShotCurrentStep("");
	

		 switch (reporte) {
		 
	        case "Excel":
	        	
	    		testCase.addBoldTextEvidenceCurrentStep("Selecciona todas las promociones");
	    		u.highLight(ckbTodasPromo);
	    		testCase.addScreenShotCurrentStep("");
	    		u.click(ckbTodasPromo);
	    		testCase.addScreenShotCurrentStep("");
	    		
	        	testCase.addBoldTextEvidenceCurrentStep("Hacer clic en el link 'Excel'.");
	    		u.scrollToElement(btnExcel);
	    		u.hardWait(2);
	    		u.click(btnExcel);
	    		testCase.addScreenShotCurrentStep("");
	        	
		
			break;

	        case "ligas":
	        	testCase.addBoldTextEvidenceCurrentStep("Selecciona la promoción");
	    		u.highLight(chkFolio);
	    		u.click(chkFolio);
	    		testCase.addScreenShotCurrentStep("");
	    		
	        	testCase.addBoldTextEvidenceCurrentStep("Hacer clic en el botón Reporte de ligas");
	    		u.scrollToElement(btnReportLigas);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(2);
	    		u.click(btnReportLigas);
	    		
	    		u.hardWait(4);
	    		testCase.addScreenShotCurrentStep("");


	        	
	            break;
	             	
		 	}
		
				
	}

}

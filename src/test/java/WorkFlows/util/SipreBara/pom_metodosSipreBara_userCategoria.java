package WorkFlows.util.SipreBara;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class pom_metodosSipreBara_userCategoria {
	

	SeleniumUtil u;
	TestCase testCase;
	private WebDriverWait wait;
	Random random = new Random();




	
	public pom_metodosSipreBara_userCategoria (SeleniumUtil u, TestCase testCase ) {
		this.u = u;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		this.testCase = testCase;

	}
	
	
	By reglaPrecio = By.cssSelector("#ddtopmenubar > ul:nth-child(1) > li:nth-child(3) > a:nth-child(1)");
	By selectAsignaReglaPrecio = By.cssSelector("#mod6_ARP_Cat");
	By btnAsignarReglas = By.id("btnAsignarReglas");
	By btnBuscar = By.cssSelector("div.grid3:nth-child(3) > p:nth-child(2) > a");
	By tabla = By.xpath("//table[contains(@id,'gridArtsSinRegla')]//tr//td[1]");
	By selectReglaBara = By.xpath("//*[@id=\"selectBARA\"]");
	
	//REPORTES
	By reportes = By.id("reportes");
	By cumplimiento = By.id("mod29_RepCatCumplimiento");
	By btnReglasNegocio = By.cssSelector("#mod30_RepCatReglaNeg");
	By excepcionesProxVencer = By.cssSelector("#mod28_RepCatExcepAVencer");


	By selectPlaza = By.id("divPlazaId_cmb");
	By selectZona = By.id("divZona_cmb");
	By selectDiv = By.id("divisiones_cmb"); //SuperGrupo
	By selectCategoria = By.id("categorias_cmb");
	By btnGeneraReport = By.cssSelector("#reportesGral > a:nth-child(3)");
	By btnLimpiar = By.cssSelector("#reportesGral > a:nth-child(2)");
	
	//RESPUESTAS EXCEPCIONES
	
	By respuestas = By.cssSelector("#ddtopmenubar > ul:nth-child(1) > li:nth-child(2) > a:nth-child(1)");
	By opRespuestas = By.cssSelector("#mod4_RE");
	By inputItem = By.cssSelector("#searchString");
	By selectGrupo= By.cssSelector("#divisiones_cmb");
	By selectCate = By.cssSelector("#categorias_cmb");
	By btnBuscaItem = By.cssSelector("a.boton:nth-child(4)");
	By tableItem = By.xpath("//table[contains(@id,'gridRespuesExcep_frozen')]//tr[2]//td[3]");
	
	By selectAceptaRechaza = By.cssSelector("#selecRespuesta");
	By tbxRacionalCat = By.cssSelector("#txtRacionalCat");
	By btnAplicar = By.cssSelector("a.boton:nth-child(2)");
	
	


/**
 * Asigna una regla de Precio
 * @author alicia.ceja
 */
	public void asignaReglaPrecio () {
		
		try {
		
			u.highLight(reglaPrecio);
			u.mouseOver(reglaPrecio);
			testCase.addScreenShotCurrentStep(u, "");

			u.hardWait(1);
			
			u.highLight(selectAsignaReglaPrecio);
			testCase.addScreenShotCurrentStep(u, "");
			u.mouseOverAndClick(selectAsignaReglaPrecio);
			
		}catch(Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fall� al seleccionar art�culo");
		}
		
		
	}
	
	
	/**
	 * Click en bot�n Asignar Regla de Precio y Click en Bot�n Buscar
	 * @author alicia.ceja
	 */
	
	public void btnAsignaReglaBuscar() {
		
		try {
			
		
			u.scrollToElement(btnAsignarReglas);

			u.highLight(btnAsignarReglas);
			u.click(btnAsignarReglas);
			testCase.addScreenShotCurrentStep(u, "");

	
			u.scrollToElement(btnBuscar);
			u.highLight(btnBuscar);
			testCase.addScreenShotCurrentStep(u, "");
			u.click(btnBuscar);
		}catch(Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Error al dar click en los botones buscar o Asignar regla");
		
		}	
		
	}
	
	
	/**
	 * Regresa la cantidad de registros que se muestran en la tabla de la interfaz
	 * @return
	 * @author alicia.ceja
	 */
	public int numeroElementosTabla() {

		
		List<WebElement> elementsTable = u.getDriver().findElements(tabla);
		int registros = elementsTable.size()-1;
	
		return registros;
	}
	
	/**
	 * Selecciona un articulo de una tabla de manera aleatoria
	 * @author alicia.ceja
	 */
	public void seleccionaArticulo() {
		u.hardWait(3);
		String articulo;
		try {
			
			List<WebElement> elementsTable = u.getDriver().findElements(tabla);
			int registros = elementsTable.size()-1;
			System.out.println("Elementos de la tabla interfaz --->"+registros);
			
			int elemento = random.nextInt(elementsTable.size()-1);
			 articulo = elementsTable.get(4).getText();
			
			System.out.println("elemento --->"+elemento);
			System.out.println("arti --->"+articulo);
			
			for(WebElement ele: elementsTable) {
				
				String value = ele.getText();			
				if (value.contains(articulo)) {

				By arti= By.xpath("//td[text( )=\""+articulo+"\"]");
				System.out.println("Path arti --->"+articulo);

				u.scrollToElement(arti);
			
				u.highLight(arti);
				u.click(arti);
				testCase.addScreenShotCurrentStep(u, "");
				
				u.hardWait(2);
				u.scrollToElement(btnAsignarReglas);
				u.highLight(btnAsignarReglas);
				testCase.addScreenShotCurrentStep(u, "");
				u.click(btnAsignarReglas);
				
				u.hardWait(3);
				u.highLight(selectReglaBara);
				u.click(selectReglaBara);
				
				Select dropdownSelecTipoArchivo =  new Select (u.getDriver().findElement(selectReglaBara));
				dropdownSelecTipoArchivo.selectByVisibleText("Complementos Reunion>Desechables>COMPETENCIA(0.75-0.95)_PS Conveniencia");;
				testCase.addScreenShotCurrentStep(u, "");
				
				u.hardWait(2);
				
				u.highLight(btnAsignarReglas);
				u.click(btnAsignarReglas);
				testCase.addScreenShotCurrentStep(u, "");
				
				
				break;
					
				}
				

			}		

			
		}catch(Exception e) {
			
		System.out.println(" -> " + e);
		throw new AssertionError("Fall� al seleccionar art�culo");
		}
				
	}
	
	/**
	 * Genera la descarga del reporte seleccionado
	 * @param op
	 * @author alicia.ceja
	 */
	
	public void generaReporte(String op) {
		String opcionSelect;
		try {
			
			u.waitWebElement(reportes, 120, 2);
			u.highLight(reportes);
    		testCase.addBoldTextEvidenceCurrentStep("Apartado de reportes");
			u.mouseOver(reportes);
			testCase.addScreenShotCurrentStep("");
			u.unhighLight(reportes);

			 
	        switch (op) {
	 
	        case "cumplimiento":
	        	u.highLight(cumplimiento);
	    		testCase.addBoldTextEvidenceCurrentStep("Selecciona del men� 'Reportes' la opci�n 'Cumplimiento'");
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.mouseOverAndClick(cumplimiento);
	        	u.unhighLight(cumplimiento);
	        	
	    		testCase.addBoldTextEvidenceCurrentStep("Seleccionar una plaza del filtro Plaza");
	    		opcionSelect="Mexico Bara";
	    		u.highLight(selectPlaza);
	    		drop(opcionSelect, selectPlaza);
	    		testCase.addScreenShotCurrentStep("");
	        	u.unhighLight(selectPlaza);

	    		u.hardWait(1);
	    
	    		
	    		testCase.addBoldTextEvidenceCurrentStep("Seleccionar un valor del filtro 'Super Grupo'");
	    		opcionSelect ="Botanas";
	    		u.highLight(selectDiv);
	    		drop(opcionSelect,selectDiv);
	    		testCase.addScreenShotCurrentStep("");
	    		u.unhighLight(selectDiv);
	    		

	    		testCase.addBoldTextEvidenceCurrentStep("Seleccionar un valor del filtro 'Categor�a'");
	    		u.highLight(selectCategoria);
	    		opcionSelect ="Frituras";
	    		drop(opcionSelect,selectCategoria);
	    		testCase.addScreenShotCurrentStep("");
	    		u.unhighLight(selectCategoria);
	    		
	    		u.highLight(btnGeneraReport);
	    		testCase.addBoldTextEvidenceCurrentStep("Click en bot�n Generar Reporte");
	    		u.highLight(btnGeneraReport);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(2);
	    		u.mouseOverAndClick(btnGeneraReport);
	    		testCase.addBoldTextEvidenceCurrentStep("El reporte se descarga correctamente");
	    		u.hardWait(3);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(3);
	    		cambiarVentana(1);
	    		u.hardWait(3);
	    		
	    	/*	testCase.addBoldTextEvidenceCurrentStep("Click en bot�n Limpiar");
	    		u.highLight(btnLimpiar);
	    		u.mouseOverAndClick(btnLimpiar);
	    		u.hardWait(1);
	    		testCase.addScreenShotCurrentStep("");
	    		u.unhighLight(btnLimpiar);
	        	*/
	            break;
	        case "reglaNegocio":
	        	testCase.addBoldTextEvidenceCurrentStep("Seleccionar del men� ''reportes'' la opci�n Reglas de Negocio");
	    		u.hardWait(2);
	    		u.highLight(btnReglasNegocio);
	    		testCase.addScreenShotCurrentStep(u, "");
	    		u.mouseOverAndClick(btnReglasNegocio);
	    		
	    		u.waitWebElement(btnGeneraReport, 120, 3);
	    		
	    		testCase.addBoldTextEvidenceCurrentStep("Seleccionar una plaza del filtro Plaza");
	    		opcionSelect="Mexico Bara";
	    		u.highLight(selectPlaza);
	    		drop(opcionSelect, selectPlaza);
	    		testCase.addScreenShotCurrentStep("");
	        	u.unhighLight(selectPlaza);

	    		u.hardWait(1);
	    
	    		
	    		testCase.addBoldTextEvidenceCurrentStep("Seleccionar un valor del filtro 'Super Grupo'");
	    		opcionSelect ="Botanas";
	    		u.highLight(selectDiv);
	    		drop(opcionSelect,selectDiv);
	    		testCase.addScreenShotCurrentStep("");
	    		u.unhighLight(selectDiv);
	    		

	    		testCase.addBoldTextEvidenceCurrentStep("Seleccionar un valor del filtro 'Categor�a'");
	    		u.highLight(selectCategoria);
	    		opcionSelect ="Frituras";
	    		drop(opcionSelect,selectCategoria);
	    		testCase.addScreenShotCurrentStep("");
	    		u.unhighLight(selectCategoria);
	    	
	    		u.hardWait(2);
	    		
	    		testCase.addBoldTextEvidenceCurrentStep("Click en bot�n Generar Reporte");
	    		u.highLight(btnGeneraReport);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.mouseOverAndClick(btnGeneraReport);
	    		testCase.addBoldTextEvidenceCurrentStep("El reporte se descarga correctamente");
	    		u.hardWait(3);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(3);
	    		cambiarVentana(1);
	    		u.hardWait(3);
	 
	            break;
	 
	        case "excepciones":
	        	testCase.addBoldTextEvidenceCurrentStep("Seleccionar del men� ''reportes'' la opci�n Excepciones Proximas a Vencer");
	    		u.hardWait(2);
	    		u.highLight(excepcionesProxVencer);
	    		testCase.addScreenShotCurrentStep(u, "");
	    		u.mouseOverAndClick(excepcionesProxVencer);
	    		
	    		u.waitWebElement(btnGeneraReport, 120, 3);
	    		
	    		testCase.addBoldTextEvidenceCurrentStep("Seleccionar una plaza del filtro Plaza");
	    		opcionSelect="Mexico Bara";
	    		u.highLight(selectPlaza);
	    		drop(opcionSelect, selectPlaza);
	    		testCase.addScreenShotCurrentStep("");
	        	u.unhighLight(selectPlaza);

	    		u.hardWait(1);
	    
	    		
	    		testCase.addBoldTextEvidenceCurrentStep("Seleccionar un valor del filtro 'Super Grupo'");
	    		opcionSelect ="Botanas";
	    		u.highLight(selectDiv);
	    		drop(opcionSelect,selectDiv);
	    		testCase.addScreenShotCurrentStep("");
	    		u.unhighLight(selectDiv);
	    		

	    		testCase.addBoldTextEvidenceCurrentStep("Seleccionar un valor del filtro 'Categor�a'");
	    		u.highLight(selectCategoria);
	    		opcionSelect ="Frituras";
	    		drop(opcionSelect,selectCategoria);
	    		testCase.addScreenShotCurrentStep("");
	    		u.unhighLight(selectCategoria);
	    	
	    		u.hardWait(2);
	    		
	    		testCase.addBoldTextEvidenceCurrentStep("Click en bot�n Generar Reporte");
	    		u.highLight(btnGeneraReport);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(1);
	    		u.mouseOverAndClick(btnGeneraReport);
	    		testCase.addBoldTextEvidenceCurrentStep("El reporte se descarga correctamente");
	    		u.hardWait(3);
	    		testCase.addScreenShotCurrentStep("");
	    		u.hardWait(3);
	    		cambiarVentana(1);
	    		u.hardWait(3);
	 
	            break;

	        default:
	        	
	        }
			
		}catch(Exception e) {
			System.out.println("Error---> " + e);

			throw new AssertionError("Error al generar reporte");

		}
	}
	
	
	/**
	 * Selecciona una opcion de un comboBox
	 * @param op
	 * @param selectElement
	 * @author alicia.ceja
	 */
	public void drop(String op, By selectElement) {
		
		try {
			Select dropdown=  new Select (u.getDriver().findElement((selectElement)));
			dropdown.selectByVisibleText(op);
		}catch (Exception e) {
			System.out.println("Error---> " + e);

			throw new AssertionError("Error al seleccionar una opci�n del cmb");

        }
		

	}
	
	/**
	 * Realiza un Switch a una ventana
	 * @param pantalla
	 * @author alicia.ceja
	 */
	public  void cambiarVentana(int pantalla) {
        try {
            ArrayList<String> tabs = new ArrayList<String> (u.getDriver().getWindowHandles());
            u.getDriver().switchTo().window(tabs.get(pantalla));
        } catch (Exception e) {
    		System.out.println("Error---> " + e);

			throw new AssertionError("Error al cambiar de ventana");

        }
    }
	
	
	public void rechazaExcepcion (String item_) {
		u.waitWebElement(respuestas, 120, 3);
		u.mouseOver(respuestas);
		u.hardWait(2);
		u.highLight(opRespuestas);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(3);
		u.mouseOverAndClick(opRespuestas);
		
	/*	testCase.addBoldTextEvidenceCurrentStep("Seleccionar los filtros que se necesitan");
		u.waitWebElement(selectGrupo, 120, 6);
		drop("Yoghurt",selectGrupo);
		u.hardWait(2);
		drop("Yogurt Bebible",selectCate);
		u.hardWait(2);
		u.highLight(inputItem);
		u.sendKeys(inputItem,item_);
		testCase.addScreenShotCurrentStep("");*/


		u.waitWebElement(btnBuscaItem, 120, 3);
		u.scrollToElement(btnBuscaItem);
		testCase.addBoldTextEvidenceCurrentStep(" Dar clic en buscar");
		u.highLight(btnBuscaItem);
		u.click(btnBuscaItem);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.unhighLight(btnBuscaItem);
		
		u.hardWait(2);
		
		u.waitWebElement(tableItem, 120, 2);
		
		testCase.addBoldTextEvidenceCurrentStep("Seleccionar el item");
		u.highLight(tableItem);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.click(tableItem);
		u.unhighLight(tableItem);
		
		testCase.addBoldTextEvidenceCurrentStep("Seleccionar en el campo Respues, la opción rechazar");
		u.scrollToElement(selectAceptaRechaza);
		u.highLight(selectAceptaRechaza);
		drop("Rechazar",selectAceptaRechaza);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(3);
		u.unhighLight(selectAceptaRechaza);


		testCase.addBoldTextEvidenceCurrentStep("Capturar información de racional de category");
		u.highLight(tbxRacionalCat);
		u.sendKeys(tbxRacionalCat, "Rechazar Item");
		testCase.addScreenShotCurrentStep("");
		u.hardWait(3);
		u.unhighLight(tbxRacionalCat);
		testCase.addBoldTextEvidenceCurrentStep("Dar click en Aplicar");
		u.scrollToElement(btnAplicar);
		u.highLight(btnAplicar);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(3);
		u.click(btnAplicar);
		testCase.addScreenShotCurrentStep("");		
	}
	
	
	public void aceptaExcepcion(String item) {
		u.waitWebElement(respuestas, 120, 3);
		u.mouseOver(respuestas);
		u.hardWait(2);
		u.highLight(opRespuestas);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(3);
		u.mouseOverAndClick(opRespuestas);
		
	/*	testCase.addBoldTextEvidenceCurrentStep("Seleccionar los filtros que se necesitan");
		u.waitWebElement(selectGrupo, 120, 6);
		drop("Yoghurt",selectGrupo);
		u.hardWait(2);
		drop("Yogurt Bebible",selectCate);
		u.hardWait(2);
		u.highLight(inputItem);
		u.sendKeys(inputItem,item_);
		testCase.addScreenShotCurrentStep("");*/


		u.waitWebElement(btnBuscaItem, 120, 3);
		u.scrollToElement(btnBuscaItem);
		testCase.addBoldTextEvidenceCurrentStep(" Dar clic en buscar");
		u.highLight(btnBuscaItem);
		u.click(btnBuscaItem);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.unhighLight(btnBuscaItem);
		
		u.hardWait(2);
		
		u.waitWebElement(tableItem, 120, 2);
		
		testCase.addBoldTextEvidenceCurrentStep("Seleccionar el item");
		u.highLight(tableItem);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.click(tableItem);
		u.unhighLight(tableItem);
		
		testCase.addBoldTextEvidenceCurrentStep("Seleccionar en el campo Respues, la opción Aceptar");
		u.scrollToElement(selectAceptaRechaza);
		u.highLight(selectAceptaRechaza);
		drop("Aceptar",selectAceptaRechaza);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(3);
		u.unhighLight(selectAceptaRechaza);


		testCase.addBoldTextEvidenceCurrentStep("Capturar información de racional de category");
		u.highLight(tbxRacionalCat);
		u.sendKeys(tbxRacionalCat, "Aceptar Item");
		testCase.addScreenShotCurrentStep("");
		u.hardWait(3);
		u.unhighLight(tbxRacionalCat);
		testCase.addBoldTextEvidenceCurrentStep("Dar click en Aplicar");
		u.scrollToElement(btnAplicar);
		u.highLight(btnAplicar);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(3);
		u.click(btnAplicar);
		testCase.addScreenShotCurrentStep("");	
	}
	
}

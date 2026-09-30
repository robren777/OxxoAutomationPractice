package IMA.util;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
import org.testng.asserts.SoftAssert;

import modelo.TestCase;
import utils.FileUtil;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class IMATools {
	private IMAUtils ima;
	private SeleniumUtil u;
	TestCase testCase;

	SoftAssert softAssert = new SoftAssert();

	// Nombre del reporte
	String report;

	// Elementos para seleccionar al proveedor
	By searchBoxProveedor;
	By searchBtnProveedor;
	By opcionProveedor;
	By btnAgregarProveedor;

	// Elementos para seleccionar los meses
	By searchBoxMes;
	By searchBtnMes;
	By opcionMes;
	By btnAgregarMes;

	// Elementos para seleccionar el area
	By searchBoxArea;
	By searchBtnArea;
	By opcionArea;
	By btnAgregarArea;

	// Elementos para seleccionar los a~os
	By searchBoxYear;
	By searchBtnYear;
	By opcionYear;
	By btnAgregarYear;

	// Elementos para la seleccion de tienda dentro de la jerarquia
	By tienda;
	By searchBoxTienda;
	By searchBtnTienda;
	By opcionTienda;
	By btnAgregarTienda;

	// Elementos para la seleccion de tienda dentro de la jerarquia
	By tipoTienda;
	By scrollTipoTienda;
	By btnTipoTienda;

	// Elementos para la seleccion de la semana
	By opcionSemana;
	By btnSemana;

	// Elementos para la seleccion de Plaza dentro de la jerarquia
	By plaza;
	By nivelJerarquico;
	By opcionPlaza;
	By jerarquiaTienda;
	By btnAgregarPlaza;

	// Elementos para la seleccion de categoria dentro de la jerarquia
	By categoria;
	By searchBoxCategoria;
	By searchBtnCategoria;
	By opcionCategoria;
	By btnAgregarCategoria;

	// Elementos para la seleccion de los promts
	By menuDesplegable;
	By searchBox;
	By btnSearchBox;
	By opcion;
	By btnAgregarOpcion;

	
	//Elemento mensaje de error cuentas 
	By btnContinuar = By.xpath("//*[@id=\"3054\"]");
	// Elementos para ejecutar el informe
	By btnEjecutarInforme;

	// Elementos para Exportar a Excel
	By menuInformes = By.xpath("//*[@id=\"ribbonToolbarTabsListContainer\"]/div[1]/table/tbody/tr/td[3]");
	By opcionExportar = By.xpath("//*[@id=\"RptHomeMenu_\"]/tbody/tr/td/div/div[18]");
	By exportarExcelConFormato = By.xpath(
			"//*[@id=\"RptHomeExportMenu_WEB-INFxmllayoutsblocksHomeExportMenuLayoutxml\"]/tbody/tr/td/div/div[8]");
	By exportarPDF = By.xpath(
			"//*[@id=\"RptHomeExportMenu_WEB-INFxmllayoutsblocksHomeExportMenuLayoutxml\"]/tbody/tr/td/div/div[1]");
	By cerrarVentana = By.linkText("Cerrar la ventana");

	// Elementos para asignar filtros
	By filtroMes = By.xpath("//*[@id=\"optimizedDrillKeyAndProps_2\"]/option[3]");
	By btnFiltroMes = By.id("deDrillImageName_2");

	By filtroYear = By.xpath("//*[@id=\"optimizedDrillKeyAndProps_3\"]/option[2]");
	By btnFiltroYear = By.id("deDrillImageName_3");

	By filtroFuenteDeAbasto = By.xpath(
			"//*[@id=\"drillEditor_pane\"]/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[3]/td[1]/div/div[2]/span/a");
	By btnFiltroFuenteDeAbasto = By.id("AdvancedDrillSubmit");

	By txtYearProveedor;

	By btnAtras;

	
	ChromeDriver driver;
	
	public IMATools(SeleniumUtil u, TestCase testCase) {
		ima = new IMAUtils(u, testCase);
		this.u = u;
		this.testCase = testCase;

		ima = new IMAUtils(u, testCase);
	}

	/**
	 * Este metodo asigna los locators a cada elemento visible necesario para los
	 * reportes solicitados. Asigna los locators y en base al reporte dado en los
	 * parametros realiza las acciones necesarias para ejecutar el reporte.
	 * 
	 * @param report
	 */
	public void EjecutarInformeReporteSeleccionado(String report) {
		boolean exist = true;
		switch (report) {

		case "Recepciones en tienda por fuente de abasto":

			// Elementos para seleccionar al proveedor
			searchBoxProveedor = By.id("id_mstr72_txt");
			searchBtnProveedor = By.xpath("//*[@id=\"id_mstr72\"]/table/tbody/tr/td[2]/div/input");
			opcionProveedor = By.xpath("//*[@id=\"id_mstr74ListContainer\"]/div[4]");
			btnAgregarProveedor = By.id("id_mstr76");

			// Elementos para seleccionar los meses
			searchBoxMes = By.id("id_mstr136_txt");
			searchBtnMes = By.xpath("//*[@id=\"id_mstr136\"]/table/tbody/tr/td[2]/div/input");
			opcionMes = By.xpath("//*[@id=\"id_mstr138ListContainer\"]/div[4]");
			btnAgregarMes = By.id("id_mstr140");

			// Elementos para la seleccion de tienda dentro de la jerarquia
			// Se necesita seleccionar primero la opcion "Tienda" para abilitar el campo de
			// busqueda
			tienda = By.linkText("Tienda");
			searchBoxTienda = By.id("id_mstr175_txt");
			searchBtnTienda = By.xpath("//*[@id=\"id_mstr175\"]/table/tbody/tr/td[2]/div/input");
			opcionTienda = By.xpath("//*[@id=\"id_mstr182ListContainer\"]/div");
			btnAgregarTienda = By.id("id_mstr186");

			// Elementos para ejecutar el informe
			btnEjecutarInforme = By.id("id_mstr194");

			ima.SelectOption(report);
			ima.capturarPrompts(searchBoxProveedor, searchBtnProveedor, opcionProveedor, btnAgregarProveedor,
					"GASEOSAS");
			ima.capturarPrompts(searchBoxMes, searchBtnMes, opcionMes, btnAgregarMes, "Abril");
			u.highLight(tienda);
			u.click(tienda);
			u.hardWait(5);
			ima.capturarPrompts(searchBoxTienda, searchBtnTienda, opcionTienda, btnAgregarTienda, "MONTEVIDEO");
			// Ejecutar informe
			u.highLight(btnEjecutarInforme);
			u.click(btnEjecutarInforme);
			u.waitForLoadPage();

			break;

		// =============================================================================================
		// =============================================================================================
		// =============================================================================================

		case "Recepciones en tienda por fuente de abasto (Selección Proveedor)":
			// Elementos para seleccionar al proveedor
			searchBoxProveedor = By.id("id_mstr97_txt");
			searchBtnProveedor = By.xpath("//*[@id=\"id_mstr97\"]/table/tbody/tr/td[2]/div/input");
			opcionProveedor = By.xpath("//*[@id=\"id_mstr99ListContainer\"]/div[4]");
			btnAgregarProveedor = By.id("id_mstr101");

			txtYearProveedor = By.id("id_mstr42_txt");

			// Elementos para ejecutar el informe
			btnEjecutarInforme = By.id("id_mstr110");

			ima.SelectOption(report);
			u.highLight(txtYearProveedor);
			u.clear(txtYearProveedor);
			u.sendKeys(txtYearProveedor, "2019");

			ima.capturarPrompts(searchBoxProveedor, searchBtnProveedor, opcionProveedor, btnAgregarProveedor,
					"GASEOSAS");
			// Ejecutar informe
			u.highLight(btnEjecutarInforme);
			u.click(btnEjecutarInforme);
			break;

		// =============================================================================================
		// =============================================================================================
		// =============================================================================================

		case "Devoluciones en CEDIS por Fuente de abasto Mensual":

			// Elementos para seleccionar los meses
			searchBoxMes = By.id("id_mstr58_txt");
			searchBtnMes = By.xpath("//*[@id=\"id_mstr58\"]/table/tbody/tr/td[2]/div/input");
			opcionMes = By.xpath("//*[@id=\"id_mstr60ListContainer\"]/div[3]");
			btnAgregarMes = By.id("id_mstr62");

			// Elementos para la seleccion de producto dentro de la jerarquia
			// Se necesita seleccionar primero la opcion "Categoria" para habilitar el campo
			// de busqueda
			categoria = By.linkText("Categoria");
			searchBoxCategoria = By.id("id_mstr97_txt");
			searchBtnCategoria = By.xpath("//*[@id=\"id_mstr97\"]/table/tbody/tr/td[2]/div");
			opcionCategoria = By.id("id_mstr104ListContainer");
			btnAgregarCategoria = By.id("id_mstr108");

			// Elementos para ejecutar el informe
			btnEjecutarInforme = By.id("id_mstr116");

			ima.SelectOption(report);
			ima.capturarPrompts(searchBoxMes, searchBtnMes, opcionMes, btnAgregarMes, "Abril");
			u.highLight(categoria);
			u.click(categoria);
			u.hardWait(5);
			ima.capturarPrompts(searchBoxCategoria, searchBtnCategoria, opcionCategoria, btnAgregarCategoria,
					"CIGARRILLOS");
			// Ejecutar informe
			u.highLight(btnEjecutarInforme);
			u.click(btnEjecutarInforme);
			break;

		// =============================================================================================
		// =============================================================================================
		// =============================================================================================

		case "Costo Mano de Obra y Material":
			String[] Columnas_a_validar3 = { "SELECCIONAR MES FIN", "SELECCIONAR AÑOS FIN A CONSULTAR",
					"SELECCIONAR PLAZA" };
			ima.SelectOption(report);

			testCase.addTextEvidenceCurrentStep("Validar los filtros para la generacion del reporte");
			validarPrompts(Columnas_a_validar3);

			// Elementos para seleccionar los meses
			testCase.addTextEvidenceCurrentStep("Llenar el filtro Mes Fin");
			u.click(By.xpath("//span[text()='SELECCIONAR MES FIN']"));
			searchBox = By.id("id_mstr66_txt");
			btnSearchBox = By.xpath("//*[@id=\"id_mstr66\"]/table/tbody/tr/td[2]/div/input");
			opcion = By.xpath("//*[@id=\"id_mstr68ListContainer\"]/div/div");
			btnAgregarOpcion = By.id("id_mstr70");
			ima.capturarPrompts(searchBox, btnSearchBox, opcion, btnAgregarOpcion, "Mayo");
			testCase.addScreenShotCurrentStep(u, "Prompt Seleccionado");

			// Elementos para seleccionar los anios fin a consultar
			testCase.addTextEvidenceCurrentStep("Llenar el filtro Anios fin a consultar");
			u.click(By.xpath("//span[text()='SELECCIONAR AÑOS FIN A CONSULTAR']"));
			searchBox = By.id("id_mstr100_txt");
			btnSearchBox = By.xpath("//*[@id=\"id_mstr100\"]/table/tbody/tr/td[2]/div/input");
			opcion = By.xpath("//div[text()='2022']");
			btnAgregarOpcion = By.id("id_mstr104");
			ima.capturarPrompts(searchBox, btnSearchBox, opcion, btnAgregarOpcion, "2022");
			testCase.addScreenShotCurrentStep(u, "Prompt Seleccionado");

			// Elementos para seleccionar la plaza, se seleccionan todas las plazas
			u.click(By.xpath("//span[text()='SELECCIONAR PLAZA']"));
			searchBox = By.id("id_mstr138_txt");
			btnSearchBox = By.xpath("//*[@id=\"id_mstr138\"]/table/tbody/tr/td[2]/div/input");
			menuDesplegable = By.xpath(
					"//*[@id=\"id_mstr143\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[3]/div[1]/img");
			opcion = By.xpath("//*[@id=\"id_mstr160\"]/div/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]");
			btnAgregarOpcion = By.id("id_mstr144");
			// *[@id="id_mstr160"]/div/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]

			testCase.addTextEvidenceCurrentStep("Validar la existencia de los atributos Region y Plaza");
			validarAtributoPrompts("Region");
			u.click(menuDesplegable);
			validarAtributoPrompts("Plaza");
			testCase.addScreenShotCurrentStep(u, "Atributo Validado");

			u.sendKeys(searchBox, "Cuernavaca");
			u.click(btnSearchBox);
			u.hardWait(3);

			u.highLight(opcion);
			u.click(opcion);

			u.highLight(btnAgregarOpcion);
			u.click(btnAgregarOpcion);

			testCase.addTextEvidenceCurrentStep("Llenar filtro plaza");
			testCase.addScreenShotCurrentStep(u, "Prompt Plaza Seleccionado");

			// Elementos para ejecutar el informe
			btnEjecutarInforme = By.id("id_mstr149");

			// Ejecutar informe
			u.highLight(btnEjecutarInforme);
			u.click(btnEjecutarInforme);
			u.waitForLoadPage();
			break;

		// =============================================================================================
		// =============================================================================================
		// =============================================================================================

		case "Detalle de Orden de Trabajo":
			String[] Columnas_a_validar4 = { "SELECCIONAR AÑOS FIN A CONSULTAR", "SELECCIONAR MES FIN",
					"SELECCIONAR PLAZA" };
			ima.SelectOption(report);

			testCase.addTextEvidenceCurrentStep("Validar los filtros para la generacion del reporte");
			validarPrompts(Columnas_a_validar4);

			// Elementos para seleccionar los anios fin a consultar
			testCase.addTextEvidenceCurrentStep("Llenar el filtro  a~os fin");
			btnAgregarOpcion = By.id("id_mstr71");
			u.highLight(btnAgregarOpcion);
			u.click(btnAgregarOpcion);
			testCase.addScreenShotCurrentStep(u, "Prompt Seleccionado");

			// Elementos para seleccionar los meses
			testCase.addTextEvidenceCurrentStep("Llenar el filtro Mes Fin");
			u.click(By.xpath("//span[text()='SELECCIONAR MES FIN']"));
			btnAgregarOpcion = By.id("id_mstr105");
			u.highLight(btnAgregarOpcion);
			u.click(btnAgregarOpcion);
			testCase.addScreenShotCurrentStep(u, "Prompt Seleccionado");

			// Elementos para seleccionar la plaza
			u.click(By.xpath("//span[text()='SELECCIONAR PLAZA']"));
			menuDesplegable = By.xpath(
					"//*[@id=\"id_mstr143\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[1]/img");
			opcion = By.xpath(
					"//*[@id=\"id_mstr143\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[1]");
			btnAgregarOpcion = By.id("id_mstr144");

			testCase.addTextEvidenceCurrentStep("Validar la existencia de los atributos Region y Plaza");
			validarAtributoPrompts("Region");
			u.click(menuDesplegable);
			validarAtributoPrompts("Plaza");
			testCase.addScreenShotCurrentStep(u, "Atributo Validado");

			u.highLight(opcion);
			u.click(opcion);

			u.highLight(btnAgregarOpcion);
			u.click(btnAgregarOpcion);

			testCase.addTextEvidenceCurrentStep("Llenar filtro plaza");
			testCase.addScreenShotCurrentStep(u, "Prompt Plaza Seleccionado");

			// Elementos para ejecutar el informe
			btnEjecutarInforme = By.id("id_mstr149");

			// Ejecutar informe
			u.highLight(btnEjecutarInforme);
			u.click(btnEjecutarInforme);
			u.waitForLoadPage();
			break;

		// =============================================================================================
		// =============================================================================================
		// =============================================================================================

		case "Gastos Mano de Obra por Prestador de Servicio":
			String[] Columnas_a_validar5 = { "SELECCIONAR AÑOS FIN A CONSULTAR", "SELECCIONAR MES FIN",
					"SELECCIONAR PLAZA" };
			ima.SelectOption(report);

			testCase.addTextEvidenceCurrentStep("Validar los filtros para la generacion del reporte");
			validarPrompts(Columnas_a_validar5);

			// Elementos para seleccionar los anios fin a consultar
			testCase.addTextEvidenceCurrentStep("Llenar el filtro  ainos fin");
			u.click(By.xpath("//span[text()='SELECCIONAR AÑOS FIN A CONSULTAR']"));
			btnAgregarOpcion = By.id("id_mstr71");
			u.highLight(btnAgregarOpcion);
			u.click(btnAgregarOpcion);
			testCase.addScreenShotCurrentStep(u, "Prompt Seleccionado");

			// Elementos para seleccionar los meses
			testCase.addTextEvidenceCurrentStep("Llenar el filtro Mes Fin");
			u.click(By.xpath("//span[text()='SELECCIONAR MES FIN']"));
			searchBox = By.id("id_mstr100_txt");
			btnSearchBox = By.xpath("//*[@id=\"id_mstr100\"]/table/tbody/tr/td[2]/div/input");
			opcion = By.xpath("//div[text()='Mayo']");
			btnAgregarOpcion = By.id("id_mstr104");
			ima.capturarPrompts(searchBox, btnSearchBox, opcion, btnAgregarOpcion, "Mayo");
			testCase.addScreenShotCurrentStep(u, "Prompt Seleccionado");

			// Elementos para seleccionar la plaza
			u.click(By.xpath("//span[text()='SELECCIONAR PLAZA']"));
			menuDesplegable = By.xpath(
					"//*[@id=\"id_mstr143\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[1]/img");
			opcion = By.xpath(
					"//*[@id=\"id_mstr143\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[1]");
			btnAgregarOpcion = By.id("id_mstr144");

			testCase.addTextEvidenceCurrentStep("Validar la existencia de los atributos Region y Plaza");
			validarAtributoPrompts("Region");
			u.click(menuDesplegable);
			validarAtributoPrompts("Plaza");
			testCase.addScreenShotCurrentStep(u, "Atributo Validado");

			u.highLight(opcion);
			u.click(opcion);

			u.highLight(btnAgregarOpcion);
			u.click(btnAgregarOpcion);

			testCase.addTextEvidenceCurrentStep("Llenar filtro plaza");
			testCase.addScreenShotCurrentStep(u, "Prompt Plaza Seleccionado");

			// Elementos para ejecutar el informe
			btnEjecutarInforme = By.id("id_mstr149");

			// Ejecutar informe
			u.highLight(btnEjecutarInforme);
			u.click(btnEjecutarInforme);
			u.waitForLoadPage();
			break;

		// =============================================================================================
		// =============================================================================================
		// =============================================================================================

		case "Reporte Costos x Categorías Mensual":
			String[] Columnas_a_validar2 = { "SELECCIONAR AÑOS FIN A CONSULTAR", "SELECCIONAR MES FIN",
					"SELECCIONAR PLAZA" };
			ima.SelectOption(report);

			testCase.addTextEvidenceCurrentStep("Validar los filtros para la generacion del reporte");
			validarPrompts(Columnas_a_validar2);

			// Elementos para seleccionar los anios fin a consultar
			testCase.addTextEvidenceCurrentStep("Llenar el filtro Anios fin a consultar");
			u.click(By.xpath("//span[text()='SELECCIONAR AÑOS FIN A CONSULTAR']"));
			searchBox = By.id("id_mstr66_txt");
			btnSearchBox = By.xpath("//*[@id=\"id_mstr66\"]/table/tbody/tr/td[2]/div/input");
			opcion = By.xpath("//div[text()='2022']");
			btnAgregarOpcion = By.id("id_mstr70");
			ima.capturarPrompts(searchBox, btnSearchBox, opcion, btnAgregarOpcion, "2022");
			testCase.addScreenShotCurrentStep(u, "Prompt Seleccionado");

			// Elementos para seleccionar los meses
			testCase.addTextEvidenceCurrentStep("Llenar el filtro Mes Fin");
			u.click(By.xpath("//span[text()='SELECCIONAR MES FIN']"));
			searchBox = By.id("id_mstr100_txt");
			btnSearchBox = By.xpath("//*[@id=\"id_mstr100\"]/table/tbody/tr/td[2]/div/input");
			opcion = By.xpath("//div[text()='Mayo']");
			btnAgregarOpcion = By.id("id_mstr104");
			ima.capturarPrompts(searchBox, btnSearchBox, opcion, btnAgregarOpcion, "Mayo");
			testCase.addScreenShotCurrentStep(u, "Prompt Seleccionado");

			// Elementos para seleccionar la plaza, se seleccionan todas las plazas
			u.click(By.xpath("//span[text()='SELECCIONAR PLAZA']"));
			searchBox = By.id("id_mstr138_txt");
			btnSearchBox = By.xpath("//*[@id=\"id_mstr138\"]/table/tbody/tr/td[2]/div/input");
			menuDesplegable = By.xpath(
					"//*[@id=\"id_mstr143\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[3]/div[1]/img");
			opcion = By.xpath("//*[@id=\"id_mstr160\"]/div/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]");
			btnAgregarOpcion = By.id("id_mstr144");
			// *[@id="id_mstr160"]/div/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]

			testCase.addTextEvidenceCurrentStep("Validar la existencia de los atributos Region y Plaza");
			validarAtributoPrompts("Region");
			u.click(menuDesplegable);
			validarAtributoPrompts("Plaza");
			testCase.addScreenShotCurrentStep(u, "Atributo Validado");

			u.sendKeys(searchBox, "Cuernavaca");
			u.click(btnSearchBox);
			u.hardWait(3);

			u.highLight(opcion);
			u.click(opcion);

			u.highLight(btnAgregarOpcion);
			u.click(btnAgregarOpcion);

			testCase.addTextEvidenceCurrentStep("Llenar filtro plaza");
			testCase.addScreenShotCurrentStep(u, "Prompt Plaza Seleccionado");

			// Elementos para ejecutar el informe
			btnEjecutarInforme = By.id("id_mstr149");

			// Ejecutar informe
			u.highLight(btnEjecutarInforme);
			u.click(btnEjecutarInforme);
			u.waitForLoadPage();
			break;

		// =============================================================================================
		// =============================================================================================
		// =============================================================================================

		case "Reporte Costos x Categorías Acumulado":
			String[] Columnas_a_validar1 = { "SELECCIONAR AÑOS FIN A CONSULTAR", "SELECCIONAR MES FIN",
					"SELECCIONAR PLAZA" };
			ima.SelectOption(report);

			testCase.addTextEvidenceCurrentStep("Validar los filtros para la generacion del reporte");
			validarPrompts(Columnas_a_validar1);

			// Elementos para seleccionar los anios fin a consultar
			testCase.addTextEvidenceCurrentStep("Llenar el filtro Anios fin a consultar");
			u.click(By.xpath("//span[text()='SELECCIONAR AÑOS FIN A CONSULTAR']"));
			searchBox = By.id("id_mstr66_txt");
			btnSearchBox = By.xpath("//*[@id=\"id_mstr66\"]/table/tbody/tr/td[2]/div/input");
			opcion = By.xpath("//div[text()='2022']");
			btnAgregarOpcion = By.id("id_mstr70");
			ima.capturarPrompts(searchBox, btnSearchBox, opcion, btnAgregarOpcion, "2022");
			testCase.addScreenShotCurrentStep(u, "Prompt Seleccionado");

			// Elementos para seleccionar los meses
			testCase.addTextEvidenceCurrentStep("Llenar el filtro Mes Fin");
			u.click(By.xpath("//span[text()='SELECCIONAR MES FIN']"));
			searchBox = By.id("id_mstr100_txt");
			btnSearchBox = By.xpath("//*[@id=\"id_mstr100\"]/table/tbody/tr/td[2]/div/input");
			opcion = By.xpath("//div[text()='Mayo']");
			btnAgregarOpcion = By.id("id_mstr104");
			ima.capturarPrompts(searchBox, btnSearchBox, opcion, btnAgregarOpcion, "Mayo");
			testCase.addScreenShotCurrentStep(u, "Prompt Seleccionado");

			// Elementos para seleccionar la plaza, se seleccionan todas las plazas
			u.click(By.xpath("//span[text()='SELECCIONAR PLAZA']"));
			searchBox = By.id("id_mstr138_txt");
			btnSearchBox = By.xpath("//*[@id=\"id_mstr138\"]/table/tbody/tr/td[2]/div/input");
			menuDesplegable = By.xpath(
					"//*[@id=\"id_mstr143\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[3]/div[1]/img");
			opcion = By.xpath("//*[@id=\"id_mstr160\"]/div/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]");
			btnAgregarOpcion = By.id("id_mstr144");
			// *[@id="id_mstr160"]/div/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]

			testCase.addTextEvidenceCurrentStep("Validar la existencia de los atributos Region y Plaza");
			validarAtributoPrompts("Region");
			u.click(menuDesplegable);
			validarAtributoPrompts("Plaza");
			testCase.addScreenShotCurrentStep(u, "Atributo Validado");

			u.sendKeys(searchBox, "Cuernavaca");
			u.click(btnSearchBox);
			u.hardWait(3);

			u.highLight(opcion);
			u.click(opcion);

			u.highLight(btnAgregarOpcion);
			u.click(btnAgregarOpcion);

			testCase.addTextEvidenceCurrentStep("Llenar filtro plaza");
			testCase.addScreenShotCurrentStep(u, "Prompt Plaza Seleccionado");

			// Elementos para ejecutar el informe
			btnEjecutarInforme = By.id("id_mstr149");

			// Ejecutar informe
			u.highLight(btnEjecutarInforme);
			u.click(btnEjecutarInforme);
			u.waitForLoadPage();
			break;

		// =============================================================================================
		// =============================================================================================
		// =============================================================================================

		case "Reporte Cifras de Control - Plaza":

			// Elementos para seleccionar los meses
			searchBoxMes = By.id("id_mstr58_txt");
			searchBtnMes = By.xpath("//*[@id=\"id_mstr58\"]/table/tbody/tr/td[2]/div/input");
			opcionMes = By.xpath("//*[@id=\"id_mstr60ListContainer\"]/div[3]");
			btnAgregarMes = By.id("id_mstr62");

			// Elementos para la seleccion de producto dentro de la jerarquia
			// Se necesita seleccionar primero la opcion "Categoria" para habilitar el campo
			// de busqueda
			categoria = By.linkText("Categoria");
			searchBoxCategoria = By.id("id_mstr97_txt");
			searchBtnCategoria = By.xpath("//*[@id=\"id_mstr97\"]/table/tbody/tr/td[2]/div");
			opcionCategoria = By.id("id_mstr104ListContainer");
			btnAgregarCategoria = By.id("id_mstr108");

			// Elementos para ejecutar el informe
			btnEjecutarInforme = By.id("id_mstr116");

			ima.SelectOption(report);
			break;

		// =============================================================================================
		// =============================================================================================
		// =============================================================================================

		case "Reporte Cifras de Control - Tienda":

			// Elementos para seleccionar los meses
			searchBoxMes = By.id("id_mstr58_txt");
			searchBtnMes = By.xpath("//*[@id=\"id_mstr58\"]/table/tbody/tr/td[2]/div/input");
			opcionMes = By.xpath("//*[@id=\"id_mstr60ListContainer\"]/div[3]");
			btnAgregarMes = By.id("id_mstr62");

			// Elementos para la seleccion de producto dentro de la jerarquia
			// Se necesita seleccionar primero la opcion "Categoria" para habilitar el campo
			// de busqueda
			categoria = By.linkText("Categoria");
			searchBoxCategoria = By.id("id_mstr97_txt");
			searchBtnCategoria = By.xpath("//*[@id=\"id_mstr97\"]/table/tbody/tr/td[2]/div");
			opcionCategoria = By.id("id_mstr104ListContainer");
			btnAgregarCategoria = By.id("id_mstr108");

			// Elementos para ejecutar el informe
			btnEjecutarInforme = By.id("id_mstr116");

			ima.SelectOption(report);
			/*
			 * ima.capturarPrompts(searchBoxMes, searchBtnMes, opcionMes, btnAgregarMes,
			 * "Abril"); u.highLight(categoria); u.click(categoria); u.hardWait(5);
			 * ima.capturarPrompts(searchBoxCategoria, searchBtnCategoria, opcionCategoria,
			 * btnAgregarCategoria, "CIGARRILLOS"); // Ejecutar informe
			 * u.highLight(btnEjecutarInforme); u.click(btnEjecutarInforme);
			 */
			break;

		// =============================================================================================
		// =============================================================================================
		// =============================================================================================

		case "Reporte Ticket Tardios - Resumen":

			// Elementos para seleccionar los meses
			searchBoxMes = By.id("id_mstr58_txt");
			searchBtnMes = By.xpath("//*[@id=\"id_mstr58\"]/table/tbody/tr/td[2]/div/input");
			opcionMes = By.xpath("//*[@id=\"id_mstr60ListContainer\"]/div[3]");
			btnAgregarMes = By.id("id_mstr62");

			// Elementos para la seleccion de producto dentro de la jerarquia
			// Se necesita seleccionar primero la opcion "Categoria" para habilitar el campo
			// de busqueda
			categoria = By.linkText("Categoria");
			searchBoxCategoria = By.id("id_mstr97_txt");
			searchBtnCategoria = By.xpath("//*[@id=\"id_mstr97\"]/table/tbody/tr/td[2]/div");
			opcionCategoria = By.id("id_mstr104ListContainer");
			btnAgregarCategoria = By.id("id_mstr108");

			// Elementos para ejecutar el informe
			btnEjecutarInforme = By.id("id_mstr116");

			ima.SelectOption(report);
			/*
			 * ima.capturarPrompts(searchBoxMes, searchBtnMes, opcionMes, btnAgregarMes,
			 * "Abril"); u.highLight(categoria); u.click(categoria); u.hardWait(5);
			 * ima.capturarPrompts(searchBoxCategoria, searchBtnCategoria, opcionCategoria,
			 * btnAgregarCategoria, "CIGARRILLOS"); // Ejecutar informe
			 * u.highLight(btnEjecutarInforme); u.click(btnEjecutarInforme);
			 */
			break;

		// =============================================================================================
		// =============================================================================================
		// =============================================================================================

		case "1 Saldazo Depósitos y retiros por ubicación":

			// Elementos para seleccionar los meses
			searchBoxMes = By.id("id_mstr96_txt");
			searchBtnMes = By.xpath("//*[@id=\"id_mstr96\"]/table/tbody/tr/td[2]/div");
			opcionMes = By.xpath("//*[contains(text(),'201908:Agosto:Ago:2019')]");
			btnAgregarMes = By.id("id_mstr100");

			nivelJerarquico = By.xpath(
					"//*[@id=\"id_mstr141\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[1]/img");
			plaza = By.linkText("Plaza.");
			opcionPlaza = By.xpath(
					"//*[@id=\"id_mstr141\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]/div[1]");
			btnAgregarPlaza = By.id("id_mstr142");
			// Elementos para ejecutar el informe
			btnEjecutarInforme = By.id("id_mstr149");
			System.out.println(report);
			ima.SelectOption(report);

			u.highLight(tipoTienda);
			u.click(tipoTienda);
			u.highLight(btnTipoTienda);
			u.click(btnTipoTienda);

			ima.capturarPrompts(searchBoxMes, searchBtnMes, opcionMes, btnAgregarMes, "201908");

			u.highLight(nivelJerarquico);
			u.click(nivelJerarquico);

			for (int i = 1; i < 4; i++) {
				opcionPlaza = By.xpath(
						"//*[@id=\"id_mstr141\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]/div["
								+ i + "]");
				u.highLight(opcionPlaza);
				u.click(opcionPlaza);

				u.highLight(btnAgregarPlaza);
				u.click(btnAgregarPlaza);
			}
			u.highLight(btnEjecutarInforme);
			u.click(btnEjecutarInforme);

			break;

		case "1B Saldazo Depósitos y retiros por ubicación, género y rango de edad":

			// Elementos para seleccionar los meses
			searchBoxMes = By.id("id_mstr96_txt");
			searchBtnMes = By.xpath("//*[@id=\"id_mstr96\"]/table/tbody/tr/td[2]/div");
			opcionMes = By.xpath("//*[contains(text(),'201908:Agosto:Ago:2019')]");
			btnAgregarMes = By.id("id_mstr100");

			nivelJerarquico = By.xpath(
					"//*[@id=\"id_mstr141\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[1]/img");
			plaza = By.linkText("Plaza.");
			opcionPlaza = By.xpath(
					"//*[@id=\"id_mstr141\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]/div[1]");
			btnAgregarPlaza = By.id("id_mstr142");
			// Elementos para ejecutar el informe
			btnEjecutarInforme = By.id("id_mstr149");
			System.out.println(report);
			ima.SelectOption(report);

			u.highLight(tipoTienda);
			u.click(tipoTienda);
			u.highLight(btnTipoTienda);
			u.click(btnTipoTienda);

			ima.capturarPrompts(searchBoxMes, searchBtnMes, opcionMes, btnAgregarMes, "201908");

			u.highLight(nivelJerarquico);
			u.click(nivelJerarquico);

			for (int i = 1; i < 4; i++) {
				opcionPlaza = By.xpath(
						"//*[@id=\"id_mstr141\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]/div["
								+ i + "]");
				u.highLight(opcionPlaza);
				u.click(opcionPlaza);

				u.highLight(btnAgregarPlaza);
				u.click(btnAgregarPlaza);
			}
			u.highLight(btnEjecutarInforme);
			u.click(btnEjecutarInforme);

			break;

		case "2 Saldazo Transacciones de compra por ubicación":

			nivelJerarquico = By.xpath(
					"//*[@id=\"id_mstr77\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[1]/img");
			btnAgregarPlaza = By.id("id_mstr78");

			// Elementos para seleccionar los meses
			searchBoxMes = By.id("id_mstr106_txt");
			searchBtnMes = By.xpath("//*[@id=\"id_mstr106\"]/table/tbody/tr/td[2]/div");
			opcionMes = By.xpath("//*[contains(text(),'201908:Agosto:Ago:2019')]");
			btnAgregarMes = By.id("id_mstr110");

			// Elementos para ejecutar el informe
			btnEjecutarInforme = By.id("id_mstr149");
			System.out.println(report);
			ima.SelectOption(report);

			u.highLight(nivelJerarquico);
			u.click(nivelJerarquico);

			for (int i = 1; i < 4; i++) {
				opcionPlaza = By.xpath(
						"//*[@id=\"id_mstr77\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]/div["
								+ i + "]");
				u.highLight(opcionPlaza);
				u.click(opcionPlaza);

				u.highLight(btnAgregarPlaza);
				u.click(btnAgregarPlaza);
			}

			ima.capturarPrompts(searchBoxMes, searchBtnMes, opcionMes, btnAgregarMes, "201908");

			u.highLight(btnEjecutarInforme);
			u.click(btnEjecutarInforme);

			break;

		case "3 Saldazo Compras por categoría":

			// Elementos para seleccionar los meses
			searchBoxMes = By.id("id_mstr96_txt");
			searchBtnMes = By.xpath("//*[@id=\"id_mstr96\"]/table/tbody/tr/td[2]/div");
			opcionMes = By.xpath("//*[contains(text(),'202007')]");
			btnAgregarMes = By.id("id_mstr100");

			nivelJerarquico = By.xpath(
					"//*[@id=\"id_mstr141\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[1]/img");
			plaza = By.linkText("Plaza.");
			opcionPlaza = By.xpath(
					"//*[@id=\"id_mstr141\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]/div[1]");
			btnAgregarPlaza = By.id("id_mstr142");

			// Elementos para ejecutar el informe
			btnEjecutarInforme = By.id("id_mstr149");
			System.out.println(report);
			ima.SelectOption(report);

			ima.capturarPrompts(searchBoxMes, searchBtnMes, opcionMes, btnAgregarMes, "202007");

			u.highLight(nivelJerarquico);
			u.click(nivelJerarquico);

			for (int i = 1; i < 4; i++) {
				opcionPlaza = By.xpath(
						"//*[@id=\"id_mstr141\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]/div["
								+ i + "]");
				u.highLight(opcionPlaza);
				u.click(opcionPlaza);

				u.highLight(btnAgregarPlaza);
				u.click(btnAgregarPlaza);
			}
			u.highLight(btnEjecutarInforme);
			u.click(btnEjecutarInforme);

			break;

		case "3 B Saldazo Compras por categoría, rango de edad y género":
			ima.SelectOption(report);

			// Elementos para seleccionar los meses
			searchBox = By.id("id_mstr103_txt");
			btnSearchBox = By.xpath("//*[@id=\"id_mstr103\"]/table/tbody/tr/td[2]/div");
			opcion = By.xpath("//*[contains(text(),'201911')]");
			btnAgregarOpcion = By.id("id_mstr107");

			ima.capturarPrompts(searchBox, btnSearchBox, opcion, btnAgregarOpcion, "201911");

			// Elementos para seleccionar las tiendas
			menuDesplegable = By.xpath(
					"//*[@id=\"id_mstr148\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[1]/img");
			btnAgregarOpcion = By.id("id_mstr149");

			u.highLight(menuDesplegable);
			u.click(menuDesplegable);

			for (int i = 36; i < 42; i++) {
				opcion = By.xpath(
						"//*[@id=\"id_mstr148\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]/div["
								+ i + "]");
				u.highLight(opcion);
				u.click(opcion);

				u.highLight(btnAgregarOpcion);
				u.click(btnAgregarOpcion);
			}

			// Elementos para seleccionar las tiendas
			btnAgregarOpcion = By.id("id_mstr181");

			for (int i = 1; i < 11; i++) {
				opcion = By.xpath("//*[@id=\"id_mstr179ListContainer\"]/div[" + i + "]");
				u.highLight(opcion);
				u.click(opcion);

				u.highLight(btnAgregarOpcion);
				u.click(btnAgregarOpcion);
			}

			// Elementos para ejecutar el informe
			btnEjecutarInforme = By.id("id_mstr190");

			u.highLight(btnEjecutarInforme);
			u.click(btnEjecutarInforme);

			break;

		case "5 Saldazo Reporte de tarjetas vendidas":

			// Elementos para seleccionar los meses
			searchBoxMes = By.id("id_mstr96_txt");
			searchBtnMes = By.xpath("//*[@id=\"id_mstr96\"]/table/tbody/tr/td[2]/div");
			opcionMes = By.xpath("//*[contains(text(),'201908:Agosto:Ago:2019')]");
			btnAgregarMes = By.id("id_mstr100");

			nivelJerarquico = By.xpath(
					"//*[@id=\"id_mstr141\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[1]/img");
			plaza = By.linkText("Plaza.");
			opcionPlaza = By.xpath(
					"//*[@id=\"id_mstr141\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]/div[1]");
			btnAgregarPlaza = By.id("id_mstr142");
			// Elementos para ejecutar el informe
			btnEjecutarInforme = By.id("id_mstr149");
			System.out.println(report);
			ima.SelectOption(report);

			u.highLight(tipoTienda);
			u.click(tipoTienda);
			u.highLight(btnTipoTienda);
			u.click(btnTipoTienda);

			ima.capturarPrompts(searchBoxMes, searchBtnMes, opcionMes, btnAgregarMes, "201908");

			u.highLight(nivelJerarquico);
			u.click(nivelJerarquico);

			for (int i = 1; i < 4; i++) {
				opcionPlaza = By.xpath(
						"//*[@id=\"id_mstr141\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]/div["
								+ i + "]");
				u.highLight(opcionPlaza);
				u.click(opcionPlaza);

				u.highLight(btnAgregarPlaza);
				u.click(btnAgregarPlaza);
			}
			u.highLight(btnEjecutarInforme);
			u.click(btnEjecutarInforme);

			break;

		case "5 B Saldazo Reporte de tarjetas vendidas por rango de edad y género":

			// Elementos para seleccionar los meses
			searchBoxMes = By.id("id_mstr96_txt");
			searchBtnMes = By.xpath("//*[@id=\"id_mstr96\"]/table/tbody/tr/td[2]/div");
			opcionMes = By.xpath("//*[contains(text(),'201908:Agosto:Ago:2019')]");
			btnAgregarMes = By.id("id_mstr100");

			nivelJerarquico = By.xpath(
					"//*[@id=\"id_mstr141\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[1]/img");
			plaza = By.linkText("Plaza.");
			opcionPlaza = By.xpath(
					"//*[@id=\"id_mstr141\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]/div[1]");
			btnAgregarPlaza = By.id("id_mstr142");
			// Elementos para ejecutar el informe
			btnEjecutarInforme = By.id("id_mstr149");
			System.out.println(report);
			ima.SelectOption(report);

			u.highLight(tipoTienda);
			u.click(tipoTienda);
			u.highLight(btnTipoTienda);
			u.click(btnTipoTienda);

			ima.capturarPrompts(searchBoxMes, searchBtnMes, opcionMes, btnAgregarMes, "201908");

			u.highLight(nivelJerarquico);
			u.click(nivelJerarquico);

			for (int i = 1; i < 4; i++) {
				opcionPlaza = By.xpath(
						"//*[@id=\"id_mstr141\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]/div["
								+ i + "]");
				u.highLight(opcionPlaza);
				u.click(opcionPlaza);

				u.highLight(btnAgregarPlaza);
				u.click(btnAgregarPlaza);
			}
			u.highLight(btnEjecutarInforme);
			u.click(btnEjecutarInforme);

			break;

		case "6 B Saldazo_Compras promocionales_Navegado a categoría":
			ima.SelectOption(report);

			// Elementos para llenar el prompt Plaza a traves del Negocio
			menuDesplegable = By.xpath(
					"//*[@id=\"id_mstr110\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[1]/img");
			opcion = By.xpath(
					"//*[@id=\"id_mstr110\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]/div[1]");
			btnAgregarOpcion = By.id("id_mstr111");

			u.highLight(menuDesplegable);
			u.click(menuDesplegable);

			u.highLight(opcion);
			u.click(opcion);

			u.highLight(btnAgregarOpcion);
			u.click(btnAgregarOpcion);

			// Elementos para llenar el prompt Periodo Promocional Oxxo
			menuDesplegable = By
					.xpath("//*[@id=\"id_mstr150\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[1]");
			u.highLight(menuDesplegable);
			u.click(menuDesplegable);

			searchBox = By.id("id_mstr143_txt");
			btnSearchBox = By.xpath("//*[@id=\"id_mstr143\"]/table/tbody/tr/td[2]/div/input");

			u.highLight(searchBox);
			u.sendKeys(searchBox, "2013");
			u.highLight(btnSearchBox);
			u.click(btnSearchBox);
			u.hardWait(3);

			menuDesplegable = By.xpath(
					"//*[@id=\"id_mstr168\"]/div/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]/div/div[1]/img");
			u.highLight(menuDesplegable);
			u.click(menuDesplegable);

			opcion = By.xpath(
					"//*[@id=\"id_mstr168\"]/div/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[1]");
			btnAgregarOpcion = By.id("id_mstr151");

			u.highLight(opcion);
			u.click(opcion);

			u.highLight(btnAgregarOpcion);
			u.click(btnAgregarOpcion);

			// Elementos para ejecutar el informe
			btnEjecutarInforme = By.id("id_mstr158");
			u.highLight(btnEjecutarInforme);
			u.click(btnEjecutarInforme);

			break;

		case "7 Saldazo_Reporte de uso":

			nivelJerarquico = By.xpath(
					"//*[@id=\"id_mstr77\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[1]/img");
			btnAgregarPlaza = By.id("id_mstr78");

			// Elementos para seleccionar los meses
			searchBoxMes = By.id("id_mstr106_txt");
			searchBtnMes = By.xpath("//*[@id=\"id_mstr106\"]/table/tbody/tr/td[2]/div");
			opcionMes = By.xpath("//*[contains(text(),'201908:Agosto:Ago:2019')]");
			btnAgregarMes = By.id("id_mstr110");

			// Elementos para ejecutar el informe
			btnEjecutarInforme = By.id("id_mstr149");
			System.out.println(report);
			ima.SelectOption(report);

			u.highLight(nivelJerarquico);
			u.click(nivelJerarquico);

			for (int i = 1; i < 4; i++) {
				opcionPlaza = By.xpath(
						"//*[@id=\"id_mstr77\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]/div["
								+ i + "]");
				u.highLight(opcionPlaza);
				u.click(opcionPlaza);

				u.highLight(btnAgregarPlaza);
				u.click(btnAgregarPlaza);
			}

			ima.capturarPrompts(searchBoxMes, searchBtnMes, opcionMes, btnAgregarMes, "201908");

			u.highLight(btnEjecutarInforme);
			u.click(btnEjecutarInforme);

			break;

		case "8 Saldazo_Compras por categoría, rango de edad y género (monedero)":
			ima.SelectOption(report);

			// Elementos para seleccionar los meses
			searchBox = By.id("id_mstr96_txt");
			btnSearchBox = By.xpath("//*[@id=\"id_mstr96\"]/table/tbody/tr/td[2]/div");
			opcion = By.xpath("//*[contains(text(),'201908')]");
			btnAgregarOpcion = By.id("id_mstr100");

			ima.capturarPrompts(searchBox, btnSearchBox, opcion, btnAgregarOpcion, "201908");

			// Elementos para seleccionar el promp plaza
			menuDesplegable = By.xpath(
					"//*[@id=\"id_mstr141\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[1]/img");
			btnAgregarOpcion = By.id("id_mstr142");

			u.highLight(menuDesplegable);
			u.click(menuDesplegable);

			for (int i = 1; i < 4; i++) {
				opcion = By.xpath(
						"//*[@id=\"id_mstr141\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]/div["
								+ i + "]");
				u.highLight(opcion);
				u.click(opcion);

				u.highLight(btnAgregarOpcion);
				u.click(btnAgregarOpcion);
			}

			// Elementos para ejecutar el informe
			btnEjecutarInforme = By.id("id_mstr149");

			u.highLight(btnEjecutarInforme);
			u.click(btnEjecutarInforme);

			break;

		case "8 Saldazo_Compras por categoría, rango de edad y género (monedero)/Semana":
			String[] reportS = report.split("/");

			int x = 0;
			for (String i : reportS) {
				if (x == 0) {
					System.out.println(i);
					report = i;
					ima.SelectOption(report);
					x++;
				}
				x++;
			}

			// Elementos para llenar el prompt Jerarquia Organizacional
			opcion = By.xpath("//*[contains(text(),'Negocio')]");
			u.highLight(opcion);
			u.click(opcion);

			opcion = By.xpath("//*[@id=\"id_mstr107ListContainer\"]/div[2]");
			btnAgregarOpcion = By.id("id_mstr111");
			u.highLight(opcion);
			u.click(opcion);

			u.highLight(btnAgregarOpcion);
			u.click(btnAgregarOpcion);

			// Elementos para seleccionar los meses
			searchBox = By.id("id_mstr140_txt");
			btnSearchBox = By.xpath("//*[@id=\"id_mstr140\"]/table/tbody/tr/td[2]/div/input");
			opcion = By.xpath("//*[contains(text(),'202014')]");
			btnAgregarOpcion = By.id("id_mstr144");

			ima.capturarPrompts(searchBox, btnSearchBox, opcion, btnAgregarOpcion, "202014");

			// Elementos para ejecutar el informe
			btnEjecutarInforme = By.id("id_mstr153");

			u.highLight(btnEjecutarInforme);
			u.click(btnEjecutarInforme);

			break;

		case "9 Saldazo Reporte de tarjetas vendidas (Operador)":
			ima.SelectOption(report);

			// Elementos para seleccionar los meses
			searchBox = By.id("id_mstr96_txt");
			btnSearchBox = By.xpath("//*[@id=\"id_mstr96\"]/table/tbody/tr/td[2]/div");
			opcion = By.xpath("//*[contains(text(),'201908')]");
			btnAgregarOpcion = By.id("id_mstr100");

			ima.capturarPrompts(searchBox, btnSearchBox, opcion, btnAgregarOpcion, "201908");

			// Elementos para seleccionar el promp plaza
			menuDesplegable = By.xpath(
					"//*[@id=\"id_mstr141\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[1]/img");
			btnAgregarOpcion = By.id("id_mstr142");

			u.highLight(menuDesplegable);
			u.click(menuDesplegable);

			for (int i = 1; i < 4; i++) {
				opcion = By.xpath(
						"//*[@id=\"id_mstr141\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]/div["
								+ i + "]");
				u.highLight(opcion);
				u.click(opcion);

				u.highLight(btnAgregarOpcion);
				u.click(btnAgregarOpcion);
			}

			// Elementos para ejecutar el informe
			btnEjecutarInforme = By.id("id_mstr149");

			u.highLight(btnEjecutarInforme);
			u.click(btnEjecutarInforme);

			break;

		case "10 Saldazo_Resumen (monedero)":

			tienda = By.linkText("//*[@title = 'Tienda']");
			opcionTienda = By.xpath("//*[@id=\"id_mstr107ListContainer\"]/div[1]");
			btnAgregarTienda = By.id("id_mstr111");

			// Elementos para seleccionar los meses
			searchBoxMes = By.id("id_mstr58_txt");
			searchBtnMes = By.xpath("//*[@id=\"id_mstr58\"]/table/tbody/tr/td[2]/div/input");
			opcionMes = By.xpath("//*[@id=\"id_mstr60ListContainer\"]/div[3]");
			btnAgregarMes = By.id("id_mstr62");

			opcionSemana = By.xpath("//*[@id=\"id_mstr142ListContainer\"]/div[1]/div");
			btnSemana = By.id("id_mstr144");

			// Elementos para ejecutar el informe
			btnEjecutarInforme = By.id("id_mstr153");
			System.out.println(report);
			ima.SelectOption(report);
			/*
			 * ima.capturarPrompts(searchBoxMes, searchBtnMes, opcionMes, btnAgregarMes,
			 * "Abril"); u.highLight(categoria); u.click(categoria); u.hardWait(5);
			 * ima.capturarPrompts(searchBoxCategoria, searchBtnCategoria, opcionCategoria,
			 * btnAgregarCategoria, "CIGARRILLOS"); // Ejecutar informe
			 * u.highLight(btnEjecutarInforme); u.click(btnEjecutarInforme);
			 */
			break;

		case "Nivel de Servicio y Venta Perdida (Semanal)":
			ima.SelectOption(report);

			// Elementos para seleccionar la plaza a consultar
			testCase.addTextEvidenceCurrentStep("Llenar el filtro  Plaza a Consultar");
			u.click(By.xpath("//span[1][text()='Seleccion de Plazas a Consultar.']"));
			searchBox = By.id("id_mstr63_txt");
			btnSearchBox = By.xpath("//*[@id=\"id_mstr63\"]/table/tbody/tr/td[2]/div/input");
			opcion = By.xpath("//div[text()='Mexicali:10MXL:Oxxo']");
			btnAgregarOpcion = By.id("id_mstr67");
			ima.capturarPrompts(searchBox, btnSearchBox, opcion, btnAgregarOpcion, "Mexicali");
			testCase.addScreenShotCurrentStep(u, "Prompt Seleccionado");

			// Elementos para seleccionar Proveedor
			testCase.addTextEvidenceCurrentStep("Llenar el filtro Seleccion de Marca Proveedor a Consultar");
			u.click(By.xpath("//span[1][text()='Seleccion de Marca Proveedor a Consultar (Limitado a 30).']"));
			searchBox = By.id("id_mstr97_txt");
			btnSearchBox = By.xpath("//*[@id=\"id_mstr97\"]/table/tbody/tr/td[2]/div/input");
			opcion = By.xpath("//div[text()='IMEXA']");
			btnAgregarOpcion = By.id("id_mstr101");
			ima.capturarPrompts(searchBox, btnSearchBox, opcion, btnAgregarOpcion, "IMEXA");
			testCase.addScreenShotCurrentStep(u, "Prompt Seleccionado");

			// Elementos para seleccionar Semanas
			testCase.addTextEvidenceCurrentStep("Llenar el filtro Seleccion de Semana Consultar");
			u.click(By.xpath("//span[1][text()='Seleccion de Semana a Consultar (Maximo 4).']"));
			searchBox = By.id("id_mstr131_txt");
			btnSearchBox = By.xpath("//*[@id=\"id_mstr131\"]/table/tbody/tr/td[2]/div/input");
			opcion = By.xpath("//*[@id=\"id_mstr133ListContainer\"]/div/div");
			btnAgregarOpcion = By.id("id_mstr135");
			ima.capturarPrompts(searchBox, btnSearchBox, opcion, btnAgregarOpcion, "202122");
			testCase.addScreenShotCurrentStep(u, "Prompt Seleccionado");

			// Elementos para ejecutar el informe
			btnEjecutarInforme = By.id("id_mstr144");

			// Ejecutar informe
			u.highLight(btnEjecutarInforme);
			u.click(btnEjecutarInforme);
			u.waitForLoadPage();
			break;

		// =============================================================================================
		// =============================================================================================
		// =============================================================================================
			
		case "Análisis Servicios Plaza (Mes) - Remesas internacionales": 
			ima.SelectOption(report);

			// Elementos para seleccionar la plaza a consultar
			testCase.addTextEvidenceCurrentStep("Llenar el filtro  Plaza a Consultar");
			u.click(By.xpath("//span[1][text()='Seleccion de Plazas a Consultar.']"));
			searchBox = By.id("id_mstr63_txt");
			btnSearchBox = By.xpath("//*[@id=\"id_mstr63\"]/table/tbody/tr/td[2]/div/input");
			opcion = By.xpath("//div[text()='Mexicali:10MXL:Oxxo']");
			btnAgregarOpcion = By.id("id_mstr67");
			ima.capturarPrompts(searchBox, btnSearchBox, opcion, btnAgregarOpcion, "Mexicali");
			testCase.addScreenShotCurrentStep(u, "Prompt Seleccionado");

			// Elementos para seleccionar Proveedor
			testCase.addTextEvidenceCurrentStep("Llenar el filtro Seleccion de Marca Proveedor a Consultar");
			u.click(By.xpath("//span[1][text()='Seleccion de Marca Proveedor a Consultar (Limitado a 30).']"));
			searchBox = By.id("id_mstr97_txt");
			btnSearchBox = By.xpath("//*[@id=\"id_mstr97\"]/table/tbody/tr/td[2]/div/input");
			opcion = By.xpath("//div[text()='IMEXA']");
			btnAgregarOpcion = By.id("id_mstr101");
			ima.capturarPrompts(searchBox, btnSearchBox, opcion, btnAgregarOpcion, "IMEXA");
			testCase.addScreenShotCurrentStep(u, "Prompt Seleccionado");

			// Elementos para ejecutar el informe
			btnEjecutarInforme = By.id("id_mstr144");

			// Ejecutar informe
			u.highLight(btnEjecutarInforme);
			u.click(btnEjecutarInforme);
			u.waitForLoadPage();
			break;

		// =============================================================================================
		// =============================================================================================
		// =============================================================================================

		case "Análisis Servicios Nacional (Semana) - Remesas Internacionales":
		ima.SelectOption(report);

		// Elementos para seleccionar un tipo de Tienda
		testCase.addTextEvidenceCurrentStep("Llenar el filtro Tipo de Tienda");
		u.click(By.xpath("//span[1][text()='Seleccione un Tipo de tienda.']"));
		opcion = By.xpath("//div[@title='Tiendas Maduras Comparativas Base 2021']");
		btnAgregarOpcion = By.id("id_mstr55");
		
		u.highLight(opcion);
		u.click(opcion);
		
		u.highLight(btnAgregarOpcion);
		u.click(btnAgregarOpcion);
		
		testCase.addScreenShotCurrentStep(u, "Prompt Seleccionado");

		// Elementos para seleccionar las Semanas
		testCase.addTextEvidenceCurrentStep("Llenar el filtro Seleccion de Semanas");
		u.click(By.xpath("//span[1][text()='Seleccion de Semanas']"));
		searchBox = By.id("id_mstr86_txt");
		btnSearchBox = By.xpath("//*[@id=\"id_mstr86\"]/table/tbody/tr/td[2]/div/input");
		opcion = By.xpath("//div[@title='202113:Semana 13:S13']");
		btnAgregarOpcion = By.id("id_mstr90");
		ima.capturarPrompts(searchBox, btnSearchBox, opcion, btnAgregarOpcion, "202113");
		testCase.addScreenShotCurrentStep(u, "Prompt Seleccionado");

		// Elementos para ejecutar el informe
		btnEjecutarInforme = By.id("id_mstr99");

		// Ejecutar informe
		u.highLight(btnEjecutarInforme);
		u.click(btnEjecutarInforme);
		u.waitForLoadPage();
		break;

	// =============================================================================================
	// =============================================================================================
	// =============================================================================================
		
		case "Análisis Servicios Nacional (Mes) - Remesas Internacionales":
		ima.SelectOption(report);

		// Elementos para seleccionar un tipo de Tienda
		testCase.addTextEvidenceCurrentStep("Llenar el filtro Tipo de Tienda");
		u.click(By.xpath("//span[1][text()='Seleccione un Tipo de tienda.']"));
		opcion = By.xpath("//div[@title='Tiendas Maduras Comparativas Base 2021']");
		btnAgregarOpcion = By.id("id_mstr58");
		
		u.highLight(opcion);
		u.click(opcion);
		
		u.highLight(btnAgregarOpcion);
		u.click(btnAgregarOpcion);
		
		testCase.addScreenShotCurrentStep(u, "Prompt Seleccionado");

		// Elementos para seleccionar los meses
		testCase.addTextEvidenceCurrentStep("Llenar el filtro Seleccion de Meses");
		u.click(By.xpath("//span[1][text()='Seleccione de 1 a 3 meses']"));
		menuDesplegable = By.xpath("//*[@id=\"id_mstr100\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[1]/img");
		u.highLight(menuDesplegable);
		u.click(menuDesplegable);
		u.hardWait(4);
		menuDesplegable= By.xpath("//*[@id=\"id_mstr100\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[2]/div[3]");
		u.highLight(menuDesplegable);
		u.click(menuDesplegable);
		
		searchBox = By.id("id_mstr93_txt");
		btnSearchBox = By.xpath("//*[@id=\"id_mstr93\"]/table/tbody/tr/td[2]/div/input");
		opcion = By.xpath("//*[@id=\"id_mstr116\"]/div/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]");
		btnAgregarOpcion = By.id("id_mstr101");
		ima.capturarPrompts(searchBox, btnSearchBox, opcion, btnAgregarOpcion, "Enero");
		testCase.addScreenShotCurrentStep(u, "Prompt Seleccionado");

		// Elementos para ejecutar el informe
		btnEjecutarInforme = By.id("id_mstr108");

		// Ejecutar informe
		u.highLight(btnEjecutarInforme);
		u.click(btnEjecutarInforme);
		u.waitForLoadPage();
		break;

	// =============================================================================================
	// =============================================================================================
	// =============================================================================================
		default:
			exist = false;
			break;
		}
		
		assertTrue(exist, "Reporte no encontrado");
	}

	/**
	 * Metodo para iniciar sesion en MicroSrategy
	 */
	public void Login() {
		u.get(GlobalVariables.URL_MICROSTRATEGY);
		ima.login();
	}

	/**
	 * Metodo para llegar a la carpeta COMPRAS Y DEVOLUCIONES en MicroStrategy
	 */
	public void Select_FolderComprasyDevoluciones() {

		ima.SelectOption("Información Comercial QA");
		ima.SelectOptionByXpath("//*[@id=\"FolderIcons\"]/tbody/tr[5]/td[1]/table/tbody/tr/td[2]/span/a"); // informacion
																											// comercial
		//*[@id="FolderIcons"]/tbody/tr[5]/td[1]/table/tbody/tr/td[2]/span/a
		ima.SelectOptionByXpath("//*[@id=\"FolderList\"]/tbody/tr[31]/td[2]/span[1]/a"); // reportes colombia
		u.hardWait(3);
		ima.SelectOptionByXpath("//*[@id=\"FolderList\"]/tbody/tr[1]/td[2]/span[1]/a"); // analisis de compras

		/*
		 * Select folder solo con SelectOption
		 * ima.SelectOption("InformaciÃ³n Comercial QA");
		 * ima.SelectOption("Informes compartidos");
		 * ima.SelectOption("InformaciÃ³n Comercial");
		 * ima.SelectOption("Reportes Colombia");
		 * ima.SelectOption("AnÃ¡lisis de Compras"); ima.
		 * SelectOption("Recepciones en tienda por fuente de abasto (SelecciÃ³n CategorÃ­a)"
		 * );
		 */

		ima.clickOnReporte("Recepciones en tienda por fuente de abasto (Selección Categoría)");
	}

	/**
	 * Metodo para llegar a la carpeta Tarjeta Saldazo Semana en MicroStrategy
	 */

	public void goToRoute(String proyecto, String route) {
		String[] tmp = route.split("/");
		System.out.println(proyecto);
		ima.SelectOption(proyecto);
		for (String i : tmp) {
			System.out.println(i);
			u.highLight(By.xpath("//*[@title = '" + i + "']"));
			testCase.addScreenShotCurrentStep(u, "Seleccionamos " + i);
			ima.clickjs(By.xpath("//*[@title = '" + i + "']"));
			u.waitForLoadPage();
		}
	}

	public void goToRouteSubFolder(String proyecto, String route) {
		
		String[] tmp = route.split("/");
		System.out.println(proyecto);
		ima.SelectOption(proyecto);
		ima.SelectOption("Más...");
		for (String i : tmp) {
			System.out.println(i);
			u.highLight(By.xpath("//*[@title = '" + i + "']"));
			testCase.addScreenShotCurrentStep(u, "Seleccionamos " + i);
			ima.clickjs(By.xpath("//*[@title = '" + i + "']"));
			u.waitForLoadPage();
			
		
		}
		
	}

	public void gotoRoute(String route) {
		u.waitForLoadPage();
		
		String[] tmp = route.split("/");
		for (String i : tmp) {
			By by = By.xpath("//*[contains(text(),'" + i + "')]");
			u.waitWebElement(by, 15, 1);
			u.highLight(by);
			testCase.addScreenShotCurrentStep(u, "Seleccionamos " + i);
			ima.clickjs(By.xpath("//*[contains(text(),'" + i + "')]"));
			u.waitForLoadPage();
		}
	}

	public void showAllSharedInf() {
		u.click(By.xpath("//span[.='MÃƒÂ¡s...']"));
	}

	public void capturaPrompt(String area, String anio, String mes) {
		List<WebElement> lista = u.listElements(By.xpath("//img[@title='Agregar']"));
		int j = 0;
		for (WebElement i : lista) {
			switch (j) {
			case 0:
				u.click(By.xpath("//div[contains(text(),'" + area + "')]"));
				break;
			case 1:
				u.click(By.xpath("//div[contains(text(),'" + anio + "')]"));
				break;
			case 2:
				u.click(By.xpath("//div[contains(text(),'" + mes + "')]"));
				break;
			}
			j++;
			i.click();
		}
		u.click(By.xpath("//input[@value='Ejecutar Informe']"));

		u.waitWebElement(By.xpath("//span[.='DETALLES DE INFORME']"), 360000, 1);
	}

	public void clickonAllList(By by) {
		List<WebElement> lista = u.listElements(by);
		for (WebElement i : lista) {
			u.click(by);
		}
	}

	public void capturaPrompt(String[] input) {
		List<WebElement> lista = u.listElements(By.xpath("//img[@title='Agregar']"));
		List<WebElement> inputtext = u.listElements(By.xpath("//input[@type='text']"));
		List<WebElement> lupas = u.listElements(By.className("mstrBGIcon_tbSearch"));
		List<String> ent = Arrays.asList(input);
		
		u.waitForLoadPage();
		u.hardWait(1);
		int j = 0;
		for (WebElement i : lista) {

			try {
				u.scrollToElement(By.xpath("//*[.='" + ent.get(j) + "']"));
				u.click(By.xpath("//*[.='" + ent.get(j) + "']"));

			} catch (Exception ex) {
				System.out.println("No se encontro el elemento: "+ent.get(j));
			}
			i.click();
			j++;
		}
		ScreenShotPrompt();
	}

	public void capturaPrompt(String[] input, int initfrom) {
		List<WebElement> lista = u.listElements(By.xpath("//img[@title='Agregar']"));
		List<String> ent = Arrays.asList(input);
		int j = 0;
		for (WebElement i : lista) {
			if (j >= initfrom) {
				ima.clickjs(By.xpath("//*[contains(text(),'" + ent.get(j) + "')]"));
				//u.click(By.xpath("//*[contains(text(),'" + ent.get(j) + "')]"));
				i.click();
			}
			j++;
		}
		ScreenShotPrompt();
	}

	public void SeleccionarParamsLimiteDispersion(){
		u.click(By.xpath("//*[@id=\"id_mstr53ListContainer\"]/div[10]/div"));
		u.click(By.xpath("//*[@id=\"id_mstr55\"]/img"));
		u.sendKeys(By.xpath("//*[@id=\"id_mstr86_txt\"]"), "202102");
		u.click(By.xpath("//*[@id=\"id_mstr86\"]/table/tbody/tr/td[2]/div/input"));
		u.hardWait(8); 
		u.click(By.xpath("//*[@id=\"id_mstr90\"]/img"));
	}
	
	public void ScreenShotPrompt() {
		// List<WebElement> lista =
		// u.listElements(By.className("mstrPromptTOCListItemRequired"));
		List<WebElement> lista = u.listElements(By.className("mstrPromptTOCListItemIndex"));
		// lista.get(lista.size()-1).click();
		for (WebElement i : lista) {
			i.click();
			try {
				Thread.sleep(250);
			} catch (Exception ex) {

			}
		}

		for (WebElement i : lista) {
			i.click();
			try {
				Thread.sleep(750);
			} catch (Exception ex) {

			}
			testCase.addScreenShotCurrentStep(u, "Captura");
		}
	}

	public void Seleccionar(String texto) {
		By elem = By.xpath("//*[.='" + texto + "']");
		
		u.highLight(elem);
		testCase.addScreenShotCurrentStep(u, "Seleccion");
		u.click(elem);
	}

	public void ejecutarInforme() {
		u.click(By.xpath("//input[@value='Ejecutar Informe']"));
		u.waitWebElement(By.xpath("//span[.='DETALLES DE INFORME']"), 360000, 1);
	}

	public void resalta(String elemento) {
		u.highLight(By.xpath("//*[contains(text(),'" + elemento + "')]"));
	}
	
	public void resaltaByType(String typeofelement,String elemento) {
		u.highLight(By.xpath("//"+ typeofelement +"[contains(text(),'" + elemento + "')]"));
	}
	
	public void eliminaResaltado(String elemento) {
		u.highLight(By.xpath("//*[contains(text(),'" + elemento + "')]"));
	}

	public void exportar(String type) {
		try {
			u.click(By.xpath("//div[.='Inicio de informes']"));
		} catch (Exception ex) {

		}

		if (type.contentEquals("PDF")) {
			u.click(By.id("tbPDF"));
			u.highLight(By.id("tbPDF"));
		} else if (type.contentEquals("Excel")) {
			u.click(By.id("tbExport"));
			u.highLight(By.id("tbExport"));
		}
		testCase.addScreenShotCurrentStep(u, "Reporte");
	}

	public void gotoDown(int times) {
		JavascriptExecutor js = ((JavascriptExecutor) u.getDriver());
		for (int i = 0; i < times; i++) {
			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		}
	}

	public void generarReporte(String type_busqueda) { // Se modifico este metodo para poder hacer la busqueda del
														// elemento desde la clase principal
		u.scroll();
		ima.capturarPrompts("id_mstr104_txt", "202001", "//*[@id=\"id_mstr104\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr108\"]/img", ""); // Se agregaron comillas para evitar errores
		ima.capturarPromptsConSeleccion("//*[@id=\"id_mstr150ListContainer\"]/div[1]/div/a", "id_mstr143_txt",
				type_busqueda, "//*[@id=\"id_mstr143\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr150ListContainer\"]/div/div", "//*[@id=\"id_mstr154\"]/img");
		ima.capturarPromptsConSeleccion("//*[@id=\"id_mstr195ListContainer\"]/div[3]/div/a", "id_mstr188_txt",
				"MONTEVIDEO", "//*[@id=\"id_mstr188\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr195ListContainer\"]/div/div", "//*[@id=\"id_mstr199\"]/img");
		ima.clickBtnEjecutarInforme("id_mstr207");
	}

	public void clickOnReporte(String reporte) {
		ima.clickOnReporte(reporte);
	}

	// ************************************* METODOS REPORTES 21 AL 26
	// ***************************************************+

	public void generarReporte() {
		u.scroll();
		ima.capturarPrompts("id_mstr104_txt", "202001", "//*[@id=\"id_mstr106ListContainer\"]/div/div",
				"//*[@id=\"id_mstr104\"]/table/tbody/tr/td[2]/div/input", "//*[@id=\"id_mstr108\"]/img");
		ima.capturarPromptsConSeleccion("//*[@id=\"id_mstr150ListContainer\"]/div[1]/div/a", "id_mstr143_txt", "PALETA",
				"//*[@id=\"id_mstr143\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr150ListContainer\"]/div/div", "//*[@id=\"id_mstr154\"]/img");

		ima.capturarPromptsConSeleccion("//*[@id=\"id_mstr195ListContainer\"]/div[3]/div/a", "id_mstr188_txt",
				"MONTEVIDEO", "//*[@id=\"id_mstr188\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr195ListContainer\"]/div/div", "//*[@id=\"id_mstr199\"]/img");
		ima.clickBtnEjecutarInforme("id_mstr207");
	}

	public void validacionDeCamposReporte21() {
		// ima.validarColumnas(By.id("pbk3"), "Tienda:"); //CR TIENDA
		// ima.validarColumnas(By.id("pbk3"), "Tienda:"); //NOMBRE TIENDA
		ima.validarColumnas(By.id("UniqueReportID_1_1_1"), "Producto"); // SKU
		ima.validarColumnas(By.id("UniqueReportID_2_2_1"), "Indicadores"); // DESCRIPCION PRODUCTO
		ima.validarColumnas(By.id("UniqueReportID_-1_2_1"), "Uds Recibidas Directos MA"); // Unidades recibidas Netas
																							// (Mes actual)
		ima.validarColumnas(By.id("UniqueReportID_-1_4_1"), "Uds Recibidas Directos MMAA"); // Unidades recibidas Netas
																							// // (Mismo mes, aÃ±o
																							// anterior)
		ima.validarColumnas(By.id("UniqueReportID_-1_1_1"), "$ Cmp Directos MA"); // $ Recepciones Netas (Mes actual)
		ima.validarColumnas(By.id("UniqueReportID_-1_3_1"), "$ Cmp Directos MMAA"); // $ Recepciones Netas (Mismo mes,
																					// aÃ±o anterior)
		ima.validarColumnas(By.id("UniqueReportID_-1_5_1"), "$ VariaciÃ³n"); // $ VariaciÃ³n
		ima.validarColumnas(By.id("UniqueReportID_-1_6_1"), "% VariaciÃ³n"); // % VariaciÃ³n
	}

	public void NavegacionFiltroTiempo() {
		ima.SelectOptionByXpath("//*[@id=\"ribbonToolbarTabsListContainer\"]/div[3]/table/tbody/tr/td[3]"); // datos
		ima.SelectOptionByXpath("//*[@id=\"RptDataMenu_\"]/tbody/tr/td/div/div[3]/a/div[2]/span[1]"); // navegacion
		ima.SelectOptionByXpath("//*[@id=\"optimizedDrillKeyAndProps_2\"]");
		ima.SelectOptionByXpath("//*[@id=\"optimizedDrillKeyAndProps_2\"]/option[2]");
		u.highLight(By.xpath("//*[@id=\"deDrillImageName_2\"]"));
		u.click(By.xpath("//*[@id=\"deDrillImageName_2\"]"));
		u.unhighLight(By.xpath("//*[@id=\"deDrillImageName_2\"]"));
	}

	public void NavegacionFiltroCategorias() {
		ima.SelectOptionByXpath("//*[@id=\"ribbonToolbarTabsListContainer\"]/div[3]/table/tbody/tr/td[3]"); // datos
		ima.SelectOptionByXpath("//*[@id=\"RptDataMenu_\"]/tbody/tr/td/div/div[3]/a/div[2]/span[1]"); // navegacion
		ima.SelectOptionByXpath("//*[@id=\"optimizedDrillKeyAndProps_4\"]"); // categoria
		ima.SelectOptionByXpath("//*[@id=\"optimizedDrillKeyAndProps_4\"]/option[2]"); // segmento
		ima.SelectOptionByXpath("//*[@id=\"deDrillImageName_4\"]"); // btn aplicar
	}

	public void NavegacionFiltroTienda() {
		ima.SelectOptionByXpath("//*[@id=\"ribbonToolbarTabsListContainer\"]/div[3]/table/tbody/tr/td[3]"); // datos
		ima.SelectOptionByXpath("//*[@id=\"RptDataMenu_\"]/tbody/tr/td/div/div[3]/a/div[2]/span[1]"); // navegacion
		ima.SelectOptionByXpath("//*[@id=\"optimizedDrillKeyAndProps_6\"]"); // tienda
		ima.SelectOptionByXpath("//*[@id=\"optimizedDrillKeyAndProps_6\"]/option[2]"); // distrito
		ima.SelectOptionByXpath("//*[@id=\"deDrillImageName_6\"]"); // btn aplicar
	}

	public void ExportarAExcel() {
		System.out.println("descargar excel");
		u.waitForLoadPage();
		ima.SelectOptionByXpath("//*[@id=\"ribbonToolbarTabsListContainer\"]/div[1]/table/tbody/tr/td[3]");
		ima.SelectOptionByXpath("//*[@id=\"RptHomeMenu_\"]/tbody/tr/td/div/div[18]/a/div[2]");
		ima.SelectOptionByXpath(
				"//*[@id=\"RptHomeExportMenu_WEB-INFxmllayoutsblocksHomeExportMenuLayoutxml\"]/tbody/tr/td/div/div[8]/a/div[2]/span[1]");
		u.waitForLoadPage();
		u.hardWait(3);
	}


	public void ExportarAExcel2() {
		System.out.println("descargar excel");
		u.waitForLoadPage();
		ima.SelectOptionByXpath("//*[@id=\"ribbonToolbarTabsListContainer\"]/div[1]/table/tbody/tr/td[3]");
		ima.SelectOptionByXpath("//*[@id=\"RptHomeMenu_\"]/tbody/tr/td/div/div[18]/a/div[2]");
		ima.SelectOptionByXpath(
				"//*[@id=\"RptHomeExportMenu_WEB-INFxmllayoutsblocksHomeExportMenuLayoutxml\"]/tbody/tr/td/div/div[13]/a/div[2]/span[1]");
		u.waitForLoadPage();
		ima.SelectOption("Información Comercial QA");
		ima.SelectOptionByXpath("//*[@id=\"FolderIcons\"]/tbody/tr[5]/td[1]/table/tbody/tr/td[2]/span/a"); // informacion																									// comercial
		ima.SelectOptionByXpath("//*[@id=\"FolderList\"]/tbody/tr[31]/td[2]/span[1]/a"); // reportes colombia
		u.hardWait(3);
	}

	/**
	 * Metodo para llegar a la carpeta CONTROL DE GASTOS/REPORTES en MicroStrategy
	 */
	public void Select_ControlDeGastos_Reportes() {
		ima.SelectOption("Mantenimiento QA");
		ima.SelectOptionByXpath("//*[@id=\"FolderIcons\"]/tbody/tr[1]/td[1]/table/tbody/tr/td[2]/span/a"); // Control de
																											// // Gastos
		ima.SelectOptionByXpath("//*[@id=\"FolderIcons\"]/tbody/tr[3]/td/div/table/tbody/tr/td[2]/a"); // Reportes
	}

	/**
	 * Metodo para llegar a la carpeta FACTURACION GLOBAL en MicroStrategy
	 */
	public void Select_FolderFacturacionGlobal() {
		ima.SelectOption("Tickets QA");
		ima.SelectOption("Facturacion Global"); // Carpeta Facturacion Global
	}

	/**
	 * Este metodo es usado para validar un prompt para generar un reporte, se
	 * valida que exista el promt PLAZA
	 * 
	 * @param report
	 */
	public void EjecutarInforme_ReporteValidacionPlaza(String report) {
		// Elementos para seleccionar al proveedor
		searchBoxProveedor = By.id("id_mstr72_txt");
		searchBtnProveedor = By.xpath("//*[@id=\"id_mstr72\"]/table/tbody/tr/td[2]/div/input");
		opcionProveedor = By.xpath("//*[@id=\"id_mstr74ListContainer\"]/div[4]");
		btnAgregarProveedor = By.id("id_mstr76");

		// Elementos para seleccionar los meses
		searchBoxMes = By.id("id_mstr136_txt");
		searchBtnMes = By.xpath("//*[@id=\"id_mstr136\"]/table/tbody/tr/td[2]/div/input");
		opcionMes = By.xpath("//*[@id=\"id_mstr138ListContainer\"]/div[4]");
		btnAgregarMes = By.id("id_mstr140");

		// Elementos para la seleccion de tienda dentro de la jerarquia
		// Se necesita seleccionar primero la opcion "Tienda" para abilitar el campo de
		// busqueda
		plaza = By.linkText("Plaza.");
		nivelJerarquico = By.id("ListBlockContents_id_mstr182");
		opcionPlaza = By.xpath("//*[@id=\"id_mstr182ListContainer\"]/div[1]/div/a");
		jerarquiaTienda = By.linkText("Tienda");
		opcionTienda = By.xpath("//*[@id=\"id_mstr182ListContainer\"]/div[1]");
		btnAgregarPlaza = By.id("id_mstr186");

		// Elementos para ejecutar el informe
		btnEjecutarInforme = By.id("id_mstr194");

		ima.SelectOption(report);
		ima.capturarPrompts(searchBoxProveedor, searchBtnProveedor, opcionProveedor, btnAgregarProveedor, "GASEOSAS");
		ima.capturarPrompts(searchBoxMes, searchBtnMes, opcionMes, btnAgregarMes, "Abril");
		ima.validarPromt(plaza, nivelJerarquico, opcionPlaza, jerarquiaTienda, opcionTienda, btnAgregarPlaza);
	}

	/**
	 * Metodo para validar las columnas superiores de una tabla en un reporte de
	 * MicroStrategy Estas columnas aparecen en el reporte como campos de seleccion
	 * con listas desplegables
	 */
	public String validarColumnasSuperiores(String[] input) {
		for (String i : input) {
			By columna = By.xpath("//span[@ds='" + i + "'][@ax=3]");
			Boolean existe = u.getDriver().findElements(columna).size() != 0;
			if (existe == true) {
				u.highLight(columna);
				testCase.addScreenShotCurrentStep(u, "Columna " + i);
			} else {
				testCase.addScreenShotCurrentStep(u, "No esta la columna " + i);
				System.out.println("No esta la columna " + i);
				return i;
			}

		}
		testCase.addScreenShotCurrentStep(u, "Columnas validadas");
		return "";
	}

	/*	*//**
			 * Metodo para validar las columnas de una tabla en un reporte de MicroStrategy
			 *//*
				 * public String validarColumnas(String[] input) { for (String i : input) { By
				 * columna = By.xpath("//td[.='" + i + "']"); Boolean existe =
				 * u.getDriver().findElements(columna).size() != 0; if (existe == true) {
				 * u.highLight(columna); testCase.addScreenShotCurrentStep(u, "Columna " + i); }
				 * else { testCase.addScreenShotCurrentStep(u, "No esta la columna " + i);
				 * System.out.println("No esta la columna " + i); return i; } }
				 * testCase.addScreenShotCurrentStep(u, "Columnas validadas"); return ""; }
				 */

	/**
	 * Metodo para aplicar el filtro MES Y A~O a distintos reportes de MicroStrategy
	 */
	public void aplicarFiltrosMesyYear() {
		ima.goToFiltros();
		ima.aplicarFiltros(filtroMes, btnFiltroMes);
		u.waitForLoadPage();
		ima.aplicarFiltros(filtroYear, btnFiltroYear);
	}

	/**
	 * Metodo para aplicar el filtro FUENTE DE ABASTO a distintos reportes de
	 * MicroStrategy
	 */
	public void aplicarFiltrosFuenteAbasto() {
		ima.goToFiltros();
		ima.SelectOptionByXpath(
				"//*[@id=\"drillEditor_pane\"]/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[3]/td[1]/div/div[2]/span");
		u.waitForLoadPage();
		ima.SelectOptionById("AdvancedDrillSubmit");
	}

	/**
	 * Metodo para aplicar el filtro CATEGORIA a distintos reportes de MicroStrategy
	 */
	public void aplicarFiltrosCategoria() {
		ima.goToFiltros();
		ima.SelectOptionByXpath("//*[@id=\"optimizedDrillKeyAndProps_4\"]/option[5]");
		u.waitForLoadPage();
		ima.SelectOptionById("deDrillImageName_4");
	}

	/**
	 * Metodo para aplicar el filtro TIENDA a distintos reportes de MicroStrategy
	 */
	public void aplicarFiltrosTienda() {
		ima.goToFiltros();
		ima.SelectOptionByXpath("//*[@id=\"optimizedDrillKeyAndProps_5\"]/option[2]");
		u.waitForLoadPage();
		ima.SelectOptionById("deDrillImageName_5");
	}

	/**
	 * Metodo para exportar a Excel un reporte generado en MicroStrategy
	 */
	public void exportarAExcel() {
		u.highLight(menuInformes);
		u.click(menuInformes);

		u.highLight(opcionExportar);
		u.click(opcionExportar);

		u.highLight(exportarExcelConFormato);
		u.click(exportarExcelConFormato);

	}

	/**
	 * Metodo para exportar a PDF un reporte generado en MicroStrategy
	 */
	public void exportarAPDF() {
		u.highLight(menuInformes);
		u.click(menuInformes);

		u.highLight(opcionExportar);
		u.click(opcionExportar);

		u.highLight(exportarPDF);
		u.click(exportarPDF);

	}

	/**
	 * Metodo para validar los promts para generar un reporte
	 * 
	 * @author
	 * @param input
	 * @return
	 */
	public String validarPrompts(String[] input) {
		for (String i : input) {
			By prompt = By.xpath("//span[text()='" + i + "']");
			Boolean existe = u.getDriver().findElements(prompt).size() != 0;
			if (existe == true) {
				u.highLight(prompt);
				System.out.println("Prompt " + i);
				System.out.println(existe);
			} else {
				testCase.addScreenShotCurrentStep(u, "No se encontro el prompt: " + i);
				System.out.println("No esta el prompt " + i);
				return i;
			}
		}
		testCase.addScreenShotCurrentStep(u, "Prompts Validados");
		return "";
	}

	/**
	 * Metodo para validar los atributos de los promts para generar un reporte
	 * 
	 * @author
	 * @param input
	 * @return
	 */
	public String validarAtributoPrompts(String input) {
		By atributo = By.xpath("//span[text()='" + input + "']");
		u.highLight(atributo);
		u.click(atributo);
		Boolean existe = u.getDriver().findElements(atributo).size() != 0;
		if (existe == true) {
			u.highLight(atributo);
			System.out.println("Prompt " + input);
			System.out.println(existe);
		} else {
			testCase.addScreenShotCurrentStep(u, "No se encontro el atributo: " + input);
			System.out.println("No esta el atributo " + input);
			return input;
		}
		return "";
	}

	/*
	 * validarConexion se usa para validar la conexion a una base de datos, dando
	 * como parametro un SQLResult
	 * 
	 * @param connectionResult
	 */
	public void validarConexion(SQLResult connectionResult) {
		boolean connection = true;

		if (connectionResult.isEmpty()) {
			connection = false;

		} else {
			testCase.addTextEvidenceCurrentStep("Conexion exitosa con la base de datos");
		}
		assertTrue(connection, "Fallo de conexion con la base de datos");
	}

	public void validarConexionFallada(SQLResult connectionResult) {
		boolean connection = true;

		if (connectionResult.isEmpty()) {
			connection = false;

		} else {
			testCase.addTextEvidenceCurrentStep("Conexion exitosa con la base de datos");
		}
		assertFalse(connection, "Fallo de conexion con la base de datos");
	}

	// ****************************************** METODOS REPORTE 41 AL
	// 45**********************************************************+++++
	public void generarReporteDevolucionesEnTienda() {
		u.scroll();
		ima.capturarPrompts("id_mstr102_txt", "GASEOSAS", "//*[@id=\"id_mstr104ListContainer\"]/div[4]/div",
				"//*[@id=\"id_mstr102\"]/table/tbody/tr/td[2]/div/input", "//*[@id=\"id_mstr106\"]/img");
		ima.capturarPrompts("id_mstr136_txt", "202001", "//*[@id=\"id_mstr138ListContainer\"]/div/div",
				"//*[@id=\"id_mstr136\"]/table/tbody/tr/td[2]/div/input", "//*[@id=\"id_mstr140\"]/img");
		ima.capturarPromptsConSeleccion("//*[@id=\"id_mstr182ListContainer\"]/div[3]/div/a", "id_mstr175_txt",
				"MONTEVIDEO", "//*[@id=\"id_mstr175\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr182ListContainer\"]/div/div", "//*[@id=\"id_mstr186\"]/img");

		ima.clickBtnEjecutarInforme("id_mstr194");
	}

	public void validacionDeCamposReporte41() {
		// ima.validarColumnas(By.id("pbk3"), "Tienda:"); //CR TIENDA
		// ima.validarColumnas(By.id("pbk3"), "Tienda:"); //NOMBRE TIENDA
		ima.validarColumnas(By.id("UniqueReportID_1_1_1"), "Producto"); // SKU
		ima.validarColumnas(By.id("UniqueReportID_2_2_1"), "Indicadores"); // DESCRIPCION PRODUCTO
		ima.validarColumnas(By.id("UniqueReportID_-1_2_1"), "Uds Dev Directos MA"); // Unidades recibidas Netas (Mes
																					// actual)
		ima.validarColumnas(By.id("UniqueReportID_-1_4_1"), "Uds Dev Directos MMAA"); // Unidades recibidas Netas (Mismo
																						// mes, aÃƒÂ±o anterior)
		ima.validarColumnas(By.id("UniqueReportID_-1_1_1"), "$ Dev Directos MA"); // $ Recepciones Netas (Mes actual)
		ima.validarColumnas(By.id("UniqueReportID_-1_3_1"), "$ Dev Directos MMAA"); // $ Recepciones Netas (Mismo mes,
																					// aÃƒÂ±o anterior)
		ima.validarColumnas(By.id("UniqueReportID_-1_5_1"), "$ VariaciÃƒÂ³n"); // $ VariaciÃƒÂ³n
		ima.validarColumnas(By.id("UniqueReportID_-1_6_1"), "% VariaciÃƒÂ³n"); // % VariaciÃƒÂ³n
	}

	public void navegacionFiltroFuenteAbasto() {
		ima.SelectOptionByXpath("//*[@id=\"ribbonToolbarTabsListContainer\"]/div[3]/table/tbody/tr/td[3]"); // datos
		ima.SelectOptionByXpath("//*[@id=\"RptDataMenu_\"]/tbody/tr/td/div/div[3]/a/div[2]/span[1]"); // navegacion
		ima.SelectOptionByXpath(
				"//*[@id=\"drillEditor_pane\"]/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[3]/td[1]/div/div[2]/span/a"); // fuente
																																	// de
																																	// abasto
		u.hardWait(2);
		ima.SelectOptionByXpath("//*[@id=\"optimizedDrillKeyAndProps\"]/option[1]"); // opcion aÃƒÂ±o
		ima.SelectOptionByXpath("//*[@id=\"AdvancedDrillSubmit\"]"); // btn aplicar
	}

	public void validarSeleccionPromptPlaza() {
		u.scroll(20);
		ima.SelectOptionByXpath("//*[@id=\"id_mstr182ListContainer\"]/div[5]/div/a");
	}

	public void validarFormatoColumnas() {
		System.out.println("validacion columnas");
		ima.validarFormatoColumnas(By.id("UniqueReportID_-1_1_1"), "$");
		ima.validarFormatoColumnas(By.id("UniqueReportID_-1_3_1"), "$");
		ima.validarFormatoColumnas(By.id("UniqueReportID_-1_5_1"), "$");
		ima.validarFormatoColumnas(By.id("UniqueReportID_-1_6_1"), "%");
	}

	// *********************************** METODOS PARA MATRIZ
	// NEXXO******************************************************
	public void Select_FolderCaratulas() {
		ima.SelectOption("Mantenimiento QA");
		ima.SelectOption("Informes compartidos");
		ima.SelectOption("Control de Gastos");
		ima.SelectOption("Caratulas");
	}

	public void Select_OrdenesDeTrabajoMensuales() {
		ima.SelectOption("Ordenes de Trabajo Mensuales");
	}

	public void Select_RankeoPorGastos() {
		ima.SelectOption("Rankeo por Gastos");
	}

	public void Select_FolderConcentradoCostosMantenimiento() {
		ima.SelectOption("Mantenimiento QA");
		ima.SelectOption("Informes compartidos");
		ima.SelectOption("Control de Gastos");
		ima.SelectOption("Formas OS");
		u.hardWait(2);
		ima.SelectOptionByXpath("//*[@id=\"FolderIcons\"]/tbody/tr/td[2]/div/table/tbody/tr/td[2]/a"); // reportes
		ima.SelectOption("Concentrado Costo de Mantenimiento");
	}

	public void Select_FolderCalificacionesMensualesImagen() {
		ima.SelectOption("Mantenimiento QA");
		ima.SelectOption("Informes compartidos");
		ima.SelectOption("Imagen Tiendas");
		ima.SelectOption("Calificaciones Mensuales Imagen");
	}

	public void capturarPromptsOrdenesdeTrabajoMensuales() { // este metodo sirve en varios reportes
		u.waitForLoadPage();
		u.hardWait(2);
		ima.capturarPrompts("id_mstr66_txt", "2022", "//*[@id=\"id_mstr68ListContainer\"]/div/div",
				"//*[@id=\"id_mstr66\"]/table/tbody/tr/td[2]/div/input", "//*[@id=\"id_mstr70\"]/img");
		u.click(By.xpath("//*[@id=\"id_mstr39\"]/table/tbody/tr[2]/td[2]"));
		ima.capturarPrompts("id_mstr100_txt", "Mayo", "//*[@id=\"id_mstr102ListContainer\"]/div/div",
				"//*[@id=\"id_mstr100\"]/table/tbody/tr/td[2]/div/input", "//*[@id=\"id_mstr104\"]/img");
		u.click(By.xpath("//*[@id=\"id_mstr39\"]/table/tbody/tr[3]/td[2]/span[1]"));
		ima.seleccionarPlazaReporteRankeoPorGastos(
				"//*[@id=\"id_mstr143\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[1]/img",
				"//*[@id=\"id_mstr143\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[1]/span",
				"id_mstr138_txt", "TIJ", "//*[@id=\"id_mstr138\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr164\"]/div/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]/div[1]/div[1]/span",
				"//*[@id=\"id_mstr144\"]/img"); // 10CUE
		ima.clickBtnEjecutarInforme("id_mstr149");
	}

	public void ExportarAPdf() {
		System.out.println("exportar a PDF");
		u.hardWait(5);
		u.waitForLoadPage();
		ima.SelectOptionByXpath("//*[@id=\"ribbonToolbarTabsListContainer\"]/div[1]/table/tbody/tr/td[3]");
		// ima.SelectOptionByXpath("//*[@id=\"ribbonToolbarTabsListContainer\"]/div[1]/table/tbody/tr/td[3]");
		ima.SelectOptionByXpath("//*[@id=\"RptHomeMenu_\"]/tbody/tr/td/div/div[18]/a/div[2]");
		ima.SelectOptionByXpath(
				"//*[@id=\"RptHomeExportMenu_WEB-INFxmllayoutsblocksHomeExportMenuLayoutxml\"]/tbody/tr/td/div/div[1]/a/div[2]/span[1]");
		u.hardWait(5);
		// ima.SelectOptionByXpath("//*[@id=\"3131\"]");
	}

	public void capturarPromptsRankeoPorGastos() {
		u.waitForLoadPage();
		u.hardWait(2);
		ima.capturarPrompts("id_mstr66_txt", "2018", "//*[@id=\"id_mstr68ListContainer\"]/div/div",
				"//*[@id=\"id_mstr66\"]/table/tbody/tr/td[2]/div/input", "//*[@id=\"id_mstr70\"]/img");
		u.click(By.xpath("//*[@id=\"id_mstr39\"]/table/tbody/tr[2]/td[2]"));
		ima.capturarPrompts("id_mstr100_txt", "Enero", "//*[@id=\"id_mstr102ListContainer\"]/div/div",
				"//*[@id=\"id_mstr100\"]/table/tbody/tr/td[2]/div/input", "//*[@id=\"id_mstr104\"]/img");
		u.click(By.xpath("//*[@id=\"id_mstr39\"]/table/tbody/tr[3]/td[2]/span[1]"));
		ima.seleccionarPlazaReporteRankeoPorGastos(
				"//*[@id=\"id_mstr143\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[1]/img",
				"//*[@id=\"id_mstr143\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[1]/span",
				"id_mstr138_txt", "MON", "//*[@id=\"id_mstr138\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr164\"]/div/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]/div[1]/div[1]/span",
				"//*[@id=\"id_mstr144\"]/img");
		ima.clickBtnEjecutarInforme("id_mstr149");
	}

	public void Select_FolderCalificacionesBimestralesImagen() {
		ima.SelectOption("Mantenimiento QA");
		ima.SelectOption("Informes compartidos");
		ima.SelectOption("Imagen Tiendas");
		ima.SelectOption("Calificaciones Bimestrales Imagen");
	}

	public void Select_FolderGraficaImagenPlaza() {
		ima.SelectOption("Mantenimiento QA");
		ima.SelectOption("Informes compartidos");
		ima.SelectOption("Imagen Tiendas");
		ima.SelectOption("Grafica Imagen por Plaza");
	}

	public List<String> ObtenerVariacionDevoluciones() {
		return ima.ObtenerVariacionDev();
	}

	public String validarColumnas(String[] input) {
		for (String i : input) {
			try {
				u.highLight(By.xpath("//td[.='" + i + "']"));
			} catch (Exception ex) {
				testCase.addScreenShotCurrentStep(u, "No se encontro la columna: " + i);
				return i;
			}
		}
		testCase.addScreenShotCurrentStep(u, "Columnas validadas");
		return "";
	}

	public int comparaTxt(By by) {
		String text = u.getText(by);
		if (text.contains("%"))
			return 1;
		if (text.contains("$"))
			return 2;
		return 0;
	}

	/**
	 * Con este metodo se valida que la consulta tenga registros
	 * 
	 * @param connectionResult
	 * @param query
	 */
	public void validarContenidoTablas(SQLResult connectionResult, String query) {
		boolean connection = true;

		if (connectionResult.isEmpty()) {
			connection = false;

		} else {
			connection = true;
			testCase.addTextEvidenceCurrentStep("Si existen registros en la tabla");
		}
		assertTrue(connection, "No hay registros en la tabla: " + query);
	}

	/**
	 * Metodo para validar las colmnas dentro de una tabla en base a una lista de
	 * los nombres de las columnas a validar
	 * 
	 * @param connectionResult
	 * @param query
	 * @param columnasAValidar
	 */
	public void validarColumnasSQL(SQLResult connectionResult, String query, String[] columnasAValidar) {
		List<String> columnas = connectionResult.getColumnNames();
		List<String> columnasNoEncontradas = new ArrayList<String>();
		boolean success;

		for (String i : columnasAValidar) {
			if (columnas.contains(i)) {
				System.out.println("Columnas encontradas"+i);
				// testCase.addTextEvidenceCurrentStep("Si se encuentra la columna: " + i);
			} else {
				System.out.println("Columnas no encontradas");
				testCase.addTextEvidenceCurrentStep("No se ecuentra la comlumna: " + i);
				columnasNoEncontradas.add(i);
			}
		}

		if (columnasNoEncontradas.size() == 0) {
			success = true;
		} else {
			success = false;
		}

		assertTrue(success, "No se han encontrado las columnas: " + columnasNoEncontradas);
	}

		/**
=======

	public void obtenerColumnas(SQLResult connectionResult) {
		List<String> columnas = connectionResult.getColumnNames();
		testCase.addTextEvidenceCurrentStep("Columnas de la tabla: ");
		for (String i : columnas) {
			System.out.println(i);
			testCase.addTextEvidenceCurrentStep(i);
		}

	}

	/**
	 * Metodo para comparar los registros de dos tablas o dos consultas
	 * 
	 * @param connectionResult
	 * @param connectionResultComparar
	 */
	public void compararContenidoTablas(SQLResult connectionResult, SQLResult connectionResultComparar) {
		int numRegistros = connectionResult.getRowCount();
		List<String> registro;
		List<String> registro2;
		boolean validacion = false;

		testCase.addTextEvidenceCurrentStep("Columnas de la tabla: ");
		for (int i = 0; i < numRegistros; i++) {
			registro = connectionResult.getRow(i);
			registro2 = connectionResultComparar.getRow(i);

			if (registro.containsAll(registro2)) {
				validacion = true;
			} else {
				validacion = false;
			}
		}

		assertTrue(validacion, "No se encuentran los mismos registros en las tablas");
	}
	

	/**
	 * Metodo para validar que los registros de una columna no estan vacios
	 * 
	 * @param connectionResult
	 * @param connectionResultComparar
	 */
	public void validarContenidoColumnas(SQLResult connectionResult, String columnName) {
		int numRegistros = connectionResult.getRowCount();
		String registro;
		boolean validacion = false;

		for (int i = 0; i < numRegistros; i++) {
			registro = connectionResult.getData(i, columnName);
			System.out.println(registro);

			if (registro.isEmpty()) {
				validacion = false;
			} else {
				validacion = true;
			}
		}

		assertTrue(validacion, "La columna no contiene registros");
	}

	public void Select_FolderAnalisisPromociones() {
		ima.SelectOption("Información Comercial QA");
		ima.SelectOptionByXpath("//*[@id=\"FolderIcons\"]/tbody/tr[5]/td[1]/table/tbody/tr/td[2]/span/a"); // informacion
																											// comercial
		ima.SelectOptionByXpath("//*[@id=\"FolderList\"]/tbody/tr[9]/td[2]/span[1]/a"); // analisis de promociones
		u.hardWait(3);
	}

	public void Select_FolderProtecPatrimonial() {
		ima.SelectOption("Información Comercial QA");
		ima.SelectOptionByXpath("//*[@id=\"FolderIcons\"]/tbody/tr[5]/td[1]/table/tbody/tr/td[2]/span/a"); // informacion
																											// comercial
		u.hardWait(3);
		ima.SelectOptionByXpath("/html/body/div[4]/table/tbody/tr/td[2]/div[2]/div[1]/table/tbody/tr[29]/td[2]"); // proteccion
																													// patrimonial
		u.hardWait(2);
		ima.SelectOptionByXpath("/html/body/div[4]/table/tbody/tr/td[2]/div[2]/div[1]/table/tbody/tr[1]/td[2]/span[1]"); // llavero
																															// tae
		u.hardWait(3);
	}

	public void Select_FolderTAEtienda() {
		ima.SelectOption("Información Comercial QA");
		ima.SelectOptionByXpath("//*[@id=\"FolderIcons\"]/tbody/tr[5]/td[1]/table/tbody/tr/td[2]/span/a"); // informacion
																											// comercial
		u.hardWait(3);
		ima.SelectOptionByXpath("/html/body/div[4]/table/tbody/tr/td[2]/div[2]/div[1]/table/tbody/tr[29]/td[2]");
		u.hardWait(2);
		// ima.SelectOptionByXpath("/html/body/div[4]/table/tbody/tr/td[2]/div[2]/div[1]/table/tbody/tr[1]/td[2]/span[1]");
		u.click(By.xpath("/html/body/div[4]/table/tbody/tr/td[2]/div[2]/div[1]/table/tbody/tr[1]/td[2]/span[1]/a"));
		u.hardWait(3);
		ima.SelectOptionByXpath(
				"/html/body/div[4]/table/tbody/tr/td[2]/div[2]/div[1]/table/tbody/tr[1]/td[2]/span[1]/a");
		u.hardWait(2);
	}

	public void generarReporteLlavero() {
		// ima.capturarPrompts("id_mstr63_txt", "Dic17",
		// "//*[@id=\"id_mstr65ListContainer\"]/div/div",
		// "//*[@id=\"id_mstr63\"]/table/tbody/tr/td[2]/div/input",
		// "//*[@id=\"id_mstr67\"]/img");
		ima.capturarPromptsConSeleccion("//*[@id=\"id_mstr65ListContainer\"]/div/div", "id_mstr63_txt", "Dic17",
				"//*[@id=\"id_mstr63\"]/table/tbody/tr/td[2]/div/input", "//*[@id=\"id_mstr65ListContainer\"]/div/div",
				"//*[@id=\"id_mstr67\"]/img");
		// u.hardWait(3);
		u.scroll();
		ima.capturarPromptsConSeleccion("//*[@id=\"id_mstr99ListContainer\"]/div[1]/div", "id_mstr97_txt", "2017",
				"//*[@id=\"id_mstr97\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr99ListContainer\"]/div[1]/div", "//*[@id=\"id_mstr101\"]/img");
		u.scroll();
		ima.clickBtnEjecutarInforme("id_mstr140");
	}

	public void generarRentabilidadTAE() {
		ima.capturarPromptsConSeleccion("//*[@id=\"id_mstr65ListContainer\"]/div/div", "id_mstr63_txt", "Dic17",
				"//*[@id=\"id_mstr63\"]/table/tbody/tr/td[2]/div/input", "//*[@id=\"id_mstr65ListContainer\"]/div/div",
				"//*[@id=\"id_mstr67\"]/img");
		// ima.capturarPrompts("id_mstr63_txt", "Dic17",
		// "//*[@id=\"id_mstr65ListContainer\"]/div/div",
		// "//*[@id=\"id_mstr63\"]/table/tbody/tr/td[2]/div/input",
		// "//*[@id=\"id_mstr67\"]/img");
		u.scroll(10);
		u.click(By.xpath("//*[@id=\"id_mstr85ListContainer\"]/div[3]/div"));
		u.scroll();
		ima.clickBtnEjecutarInforme("id_mstr120");
		u.hardWait(10);
	}

	public void generarRentabilidadTAEtienda() {
		ima.capturarPromptsConSeleccion("//*[@id=\"id_mstr75ListContainer\"]/div/div", "id_mstr73_txt", "Dic17",
				"//*[@id=\"id_mstr73\"]/table/tbody/tr/td[2]/div/input", "//*[@id=\"id_mstr75ListContainer\"]/div/div",
				"//*[@id=\"id_mstr77\"]/img");
		u.scroll();
		u.click(By.xpath("//*[@id=\"id_mstr95ListContainer\"]/div[3]/div"));
		u.scroll();
		u.click(By.xpath(
				"//*[@id=\"id_mstr162\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[1]/span"));
		u.click(By.xpath("//*[@id=\"id_mstr163\"]/img"));
		ima.clickBtnEjecutarInforme("id_mstr170");
		u.hardWait(10);
	}

	public void generarReporteProm() {
		u.click(By.xpath("//*[@id=\"id_mstr106ListContainer\"]/div/div"));
		u.click(By.xpath("//*[@id=\"id_mstr104\"]/img"));
		ima.capturarPromptsConSeleccion("//*[@id=\"id_mstr100ListContainer\"]/div[2]/div", "id_mstr98_txt", "2017",
				"//*[@id=\"id_mstr98\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr100ListContainer\"]/div[2]/div", "//*[@id=\"id_mstr102\"]/img");
		ima.clickBtnEjecutarInforme("id_mstr111");
		u.hardWait(140);
	}

	public void validarRepPromociones() {
		ima.validarColumnas(By.id("UniqueReportID_1_1_1"), "Promocion");
		ima.validarColumnas(By.id("UniqueReportID_1_2_1"), "Categoria");
		ima.validarColumnas(By.id("UniqueReportID_1_3_1"), "Producto");
		ima.validarColumnas(By.id("UniqueReportID_2_1_1"), "Indicadores");
	}

	public void generarReportePromN() {// no deberia arrojar reporte
		u.click(By.xpath("//*[@id=\"id_mstr106ListContainer\"]/div/div"));
		u.click(By.xpath("//*[@id=\"id_mstr104\"]/img"));
		u.scroll();
		ima.capturarPromptsConSeleccion("//*[@id=\"id_mstr100ListContainer\"]/div[7]/div", "id_mstr98_txt", "2013",
				"//*[@id=\"id_mstr98\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr100ListContainer\"]/div[7]/div", "//*[@id=\"id_mstr102\"]/img");
		ima.clickBtnEjecutarInforme("id_mstr111");
		u.hardWait(140);
	}

	public void validarRepPromocionesN() {// validar que no haya filas de reporte
		ima.validarColumnas(By.xpath("//*[@id=\"report_toolbar\"]/table/tbody/tr/td[2]/span[1]"), "   ");
	}

	public void Generate_ReportReceptionsByProductPilotStoresCEDISMonthly() {
		/*
		 * Created by: mhernandez85 Date: 29-06-2022 Description: Function to generate
		 * the report Receptions by product pilot stores CEDIS Monthly
		 */
		ima.capturarPrompts2("id_mstr58_txt", "202007", "//*[@id=\"id_mstr58\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr62\"]/img");
		u.scroll();
		ima.capturarPromptsConSeleccion("//*[@id=\"id_mstr104ListContainer\"]/div[2]/div/a", "id_mstr97_txt",
				"CIGARRILLOS", "//*[@id=\"id_mstr97\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr104ListContainer\"]/div[1]/div", "//*[@id=\"id_mstr108\"]/img");
		ima.clickBtnEjecutarInforme("id_mstr116");
	}

	public void validacionDeReportReceptionsByProductPilotStoresCEDISMonthly() {
		/*
		 * Created by: mhernandez85 Date: 30-06-2022 Description: Function to Validate
		 * that the report displays the correct columns
		 */
		ima.validarColumnas(By.id("UniqueReportID_1_1_1"), "Producto"); // SKU
		ima.validarColumnas(By.id("UniqueReportID_2_2_1"), "Indicadores"); // DESCRIPCION PRODUCTO
		ima.validarColumnas(By.id("UniqueReportID_-1_2_1"), "Uds Recibidas Directos MA"); // Unidades recibidas Netas
																							// (Mes actual)
		ima.validarColumnas(By.id("UniqueReportID_-1_4_1"), "Uds Recibidas Directos MMAA"); // Unidades recibidas Netas
																							// (Mismo mes, año anterior)
		ima.validarColumnas(By.id("UniqueReportID_-1_1_1"), "$ Cmp Directos MA"); // $ Recepciones Netas (Mes actual)
		ima.validarColumnas(By.id("UniqueReportID_-1_3_1"), "$ Cmp Directos MMAA"); // $ Recepciones Netas (Mismo mes,
																					// año anterior)
		ima.validarColumnas(By.id("UniqueReportID_-1_5_1"), "$ Variación"); // $ Variación
		ima.validarColumnas(By.id("UniqueReportID_-1_6_1"), "% Variación"); // % Variación

		testCase.addScreenShotCurrentStep(u, "Validar que el reporte despliegue las  columnas correctas");
	}

	public void NavegacioMenuDatosOpcionNavegacion() {
		/*
<<<<<<< HEAD
		 * Created by: mhernandez85 Date: 30-06-2022 Description: Funci�n para Navegar
		 * al men� "Datos", y seleccionar la opci�n "Navegaci�n".
=======
		 * Created by: mhernandez85 Date: 30-06-2022 Description: Función para Navegar
		 * al menú "Datos", y seleccionar la opción "Navegación".
>>>>>>> 739943af2ce5cf5cc4e1b482094e91b249dd2521
		 */
		u.click(By.xpath("//*[@id=\"ribbonToolbarTabsListContainer\"]/div[3]/table/tbody/tr/td[3]"));// Click Menu Datos
		u.highLight(By.xpath("//*[@id=\"RptDataMenu_\"]/tbody/tr/td/div/div[3]/a/div[2]/span[1]"));
		u.click(By.xpath("//*[@id=\"RptDataMenu_\"]/tbody/tr/td/div/div[3]/a/div[2]/span[1]"));// Click SubMenu
																					// NAVEGACI�N
		u.hardWait(6);
		testCase.addScreenShotCurrentStep(u, "Navegar al menu Datos y seleccionar la opcion Navegacion");

	}

	public void ValidarFiltroMuneuDatosNavegacion(String filterType) {
		/*
		 * Created by: mhernandez85 Date: 30-06-2022 Description: Function to validate
		 * the filters of the Report of Receptions by product pilot stores CEDIS Mensua
		 * Precondition: Pass through the data provider, the filter type field
		 * "FilterType"
		 */
		switch (filterType) {
		case "Filtro Tiempo":

			System.out.println("Entro a Filtro Tiempo ");
			u.click(By.xpath("//*[@id=\"optimizedDrillKeyAndProps_2\"]/option[3]"));// Seleccionar Filtro
			u.highLight(By.id("deDrillImageName_2"));
			u.click(By.id("deDrillImageName_2"));// btn aplicar
			u.scroll();

			testCase.addScreenShotCurrentStep(u, "Aplicar un filtro para el campo Ejercicio (Anio y Mes)");

			break;

		case "Filtro Categorias":

			System.out.println("Entro a Filtro Categorias");

			u.click(By.xpath("//*[@id=\"optimizedDrillKeyAndProps_5\"]/option[2]"));// Seleccionar Filtro
			u.highLight(By.id("deDrillImageName_5"));
			u.click(By.id("deDrillImageName_5"));// btn aplicar
			u.scroll();

			testCase.addScreenShotCurrentStep(u, "Aplicar un filtro para el campo Categoria");

			break;

		case "Filtro Tienda piloto CEDIS":

			System.out.println("Entro a Filtro Tienda piloto CEDI");
			break;

		default:
			System.out.println("El filtro : " + filterType + "No Existe");

		}
	}
	
	public void NavegacioMenuInicioDeInformes_Exportar_ExcelConFormato() {
		/*
		 * Created by: mhernandez85 Date: 01-07-2022 Description: Funcion para Navegar
		 * al menu Inicio de Informes>>Exportar>>Excel Con Formato".
		 */
		u.hardWait(15);
		u.waitForLoadPage();
		ima.SelectOptionByXpath("//*[@id=\"ribbonToolbarTabsListContainer\"]/div[1]/table/tbody/tr/td[3]");
		ima.SelectOptionByXpath("//*[@id=\"RptHomeMenu_\"]/tbody/tr/td/div/div[18]/a/div[2]");
		ima.SelectOptionByXpath(
				"//*[@id=\"RptHomeExportMenu_WEB-INFxmllayoutsblocksHomeExportMenuLayoutxml\"]/tbody/tr/td/div/div[8]/a");// Click

		u.waitForLoadPage();
		u.hardWait(6);
	}

	public void NavegacioMenuInicioDeInformes_Exportar_PDF() {
		/*
		 * Created by: mhernandez85 Date: 01-07-2022 Description: Funcion para Navegar
		 * al menu Inicio de Informes>>Exportar>>Excel Con Formato".
		 */
		u.waitForLoadPage();
		ima.SelectOptionByXpath("//*[@id=\"ribbonToolbarTabsListContainer\"]/div[1]/table/tbody/tr/td[3]");
		ima.SelectOptionByXpath("//*[@id=\"RptHomeMenu_\"]/tbody/tr/td/div/div[18]/a/div[2]");
		ima.SelectOptionByXpath(
				"//*[@id=\"RptHomeExportMenu_WEB-INFxmllayoutsblocksHomeExportMenuLayoutxml\"]/tbody/tr/td/div/div[1]/a/div[2]/span[1]");
				
		u.waitForLoadPage();
		u.hardWait(6);
	}

	public void ValidarFormatoColumnasReporte() {
		/*
		 * Created by: mhernandez85 Date: 01-07-2022 Description: Funcoion Validar el
		 * formato de las columnas del reporte Reporte de Recepciones por producto
		 * tiendas piloto CEDIS Mensual ".
		 */
		ima.CompararCadena(By.xpath("//*[@id=\"table_UniqueReportID\"]/tbody/tr[6]/td[3]"), "$"); // $ Cmp Directos MA
		ima.CompararCadena(By.xpath("//*[@id=\"table_UniqueReportID\"]/tbody/tr[6]/td[5]"), "$"); // $ Cmp Directos MMAA
		ima.CompararCadena(By.xpath("//*[@id=\"table_UniqueReportID\"]/tbody/tr[6]/td[7]"), "$"); // $ Variacion
		ima.CompararCadena(By.xpath("//*[@id=\"table_UniqueReportID\"]/tbody/tr[6]/td[8]"), "%"); // % Variacion

	}

	public void Select_FolderDetalleOrdenDeTrabajo() {
		/*
		 * Created by: mhernandez85 Date: 07-07-2022 Description: Funcoion para Ingresar
		 * en la ruta: Mantenimiento\Public Objects\Reports\Control de
		 * Gastos\Caratulas\Detalle de Orden de Trabajo ".
		 */
		ima.SelectOption("Mantenimiento QA");// Mantenimiento QA
		u.highLight(By.xpath("//*[@id=\"mstr23\"]/div"));
		u.click(By.xpath("//*[@id=\"mstr23\"]/div"));// Clic Lupa Buscar
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"));
		u.sendKeys(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"), "Public Objects");// Buscar
																													// carpeta
																													// Public
																													// Objects
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));
		u.click(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));// Clic Lupa Buscar
		u.hardWait(4);
		u.highLight(By.xpath("//*[@id=\"mstr170\"]/div/div"));
		u.click(By.xpath("//*[@id=\"mstr170\"]/div/div"));// Clic Public Objects
		ima.SelectOption("Reports");// Reports
		ima.SelectOption("Control de Gastos");// Control de Gastos
		ima.SelectOption("Caratulas");// Caratulas
		ima.SelectOption("Detalle de Orden de Trabajo");// Detalle de Orden de Trabajo

	}

	public void generarReporteDetalleDeOrdenDeTrabajo(String Tienda, String Ano, String Mes) {
		/*
		 * Created by: mhernandez85 Date: 07-07-2022 Description: Generar Reporte
		 * Detalle de Orden de Trabajo ".
		 */

		ima.capturarPrompts2("id_mstr63_txt", Tienda, "//*[@id=\"id_mstr63\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr67\"]/img");
		ima.capturarPrompts2("id_mstr97_txt", Ano, "//*[@id=\"id_mstr97\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr101\"]/img");
		u.scroll();
		ima.capturarPrompts2("id_mstr131_txt", Mes, "//*[@id=\"id_mstr131\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr135\"]/img");
		ima.clickBtnEjecutarInforme("id_mstr144");

	}

	public void generarReporteGastosManoDeObraPorPrestadorDeServicio(String Tienda, String Ano, String Mes) {
		/*
		 * Created by: mhernandez85 Date: 07-07-2022 Description: Generar Reporte Gastos
		 * Mano de Obra por Prestador de Servicio ".
		 */

		ima.capturarPrompts2("id_mstr63_txt", Tienda, "//*[@id=\"id_mstr63\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr68\"]/img");
		ima.capturarPrompts2("id_mstr97_txt", Ano, "//*[@id=\"id_mstr97\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr101\"]/img");
		u.scroll();
		ima.capturarPrompts2("id_mstr131_txt", Mes, "//*[@id=\"id_mstr131\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr135\"]/img");
		ima.clickBtnEjecutarInforme("id_mstr144");

	}

	public void generarReporteCostoManoDeObraMaterial(String Tienda, String Ano, String Mes) {
		/*
		 * Created by: mhernandez85 Date: 07-07-2022 Description: Generar Reporte Gastos
		 * Mano de Obra por Prestador de Servicio ".
		 */

		ima.capturarPrompts2("id_mstr63_txt", Tienda, "//*[@id=\"id_mstr63\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr68\"]/img");
		ima.capturarPrompts2("id_mstr97_txt", Mes, "//*[@id=\"id_mstr97\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr101\"]/img");
		u.scroll();
		ima.capturarPrompts2("id_mstr131_txt", Ano, "//*[@id=\"id_mstr131\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr135\"]/img");

		ima.clickBtnEjecutarInforme("id_mstr144");

	}

	public void Select_FolderGastosManoDeObraPorPrestadorDeServicio() {
		/*
		 * Created by: mhernandez85 Date: 07-07-2022 Description: Funcoion para Ingresar
		 * en la ruta: Mantenimiento\Public Objects\Reports\Control de
		 * Gastos\Caratulas\Gastos Mano de Obra por Prestador de Servicio".
		 */
		ima.SelectOption("Mantenimiento QA");// Mantenimiento QA
		u.highLight(By.xpath("//*[@id=\"mstr23\"]/div"));
		u.click(By.xpath("//*[@id=\"mstr23\"]/div"));// Clic Lupa Buscar
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"));
		u.sendKeys(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"), "Public Objects");// Buscar
																													// carpeta
																													// Public
																													// Objects
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));
		u.click(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));// Clic Lupa Buscar
		u.hardWait(4);
		u.highLight(By.xpath("//*[@id=\"mstr170\"]/div/div"));
		u.click(By.xpath("//*[@id=\"mstr170\"]/div/div"));// Clic Public Objects
		ima.SelectOption("Reports");// Reports
		ima.SelectOption("Control de Gastos");// Control de Gastos
		ima.SelectOption("Caratulas");// Caratulas
		ima.SelectOption("Gastos Mano de Obra por Prestador de Servicio");// Gastos Mano de Obra por Prestador de
																			// Servicio

	}

	public void Select_FolderRefaccionesPorProveedor() {
		/*
		 * Created by: mhernandez85 Date: 08-07-2022 Description: Funcoion para Ingresar
		 * en la ruta: Mantenimiento\Public Objects\Reports\Control de
		 * Gastos\Refacciones\Refacciones por Proveedor".
		 */
		ima.SelectOption("Mantenimiento QA");// Mantenimiento QA
		u.highLight(By.xpath("//*[@id=\"mstr23\"]/div"));
		u.click(By.xpath("//*[@id=\"mstr23\"]/div"));// Clic Lupa Buscar
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"));
		u.sendKeys(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"), "Public Objects");// Buscar
																													// carpeta
																													// Public
																													// Objects
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));
		u.click(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));// Clic Lupa Buscar
		u.hardWait(4);
		u.highLight(By.xpath("//*[@id=\"mstr170\"]/div/div"));
		u.click(By.xpath("//*[@id=\"mstr170\"]/div/div"));// Clic Public Objects
		ima.SelectOption("Reports");// Reports
		ima.SelectOption("Control de Gastos");// Control de Gastos
		ima.SelectOption("Refacciones");// Refacciones
		ima.SelectOption("Refacciones por Proveedor");// Refacciones por Proveedor

	}

	public void Select_GastosMensualPrestadorServiciosTienda() {
		/*
		 * Created by: mhernandez85 Date: 11-07-2022 Description: Funcoion para Ingresar
		 * en la ruta: Mantenimiento\Public Objects\Reports\Control de
		 * Gastos\Reportes\Gastos Mensual por Prestador de Servicios y Tienda".
		 */
		ima.SelectOption("Mantenimiento QA");// Mantenimiento QA
		u.highLight(By.xpath("//*[@id=\"mstr23\"]/div"));
		u.click(By.xpath("//*[@id=\"mstr23\"]/div"));// Clic Lupa Buscar
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"));
		u.sendKeys(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"), "Public Objects");// Buscar
																													// carpeta
																													// Public
																													// Objects
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));
		u.click(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));// Clic Lupa Buscar
		u.hardWait(4);
		u.highLight(By.xpath("//*[@id=\"mstr170\"]/div/div"));
		u.click(By.xpath("//*[@id=\"mstr170\"]/div/div"));// Clic Public Objects
		ima.SelectOption("Reports");// Reports
		ima.SelectOption("Control de Gastos");// Control de Gastos
		ima.SelectOption("Reportes");// Reportes
		ima.SelectOption("Gastos Mensual por Prestador de Servicios y Tienda");// Gastos Mensual por Prestador de
																				// Servicios y Tienda

	}

	public void Select_FolderCostoManoDeObraMaterial() {
		/*
		 * Created by: mhernandez85 Date: 07-07-2022 Description: Funcoion para Ingresar
		 * en la ruta: Mantenimiento\Public Objects\Reports\Control de
		 * Gastos\Caratulas\Costo Mano de Obra y Material".
		 */
		ima.SelectOption("Mantenimiento QA");// Mantenimiento QA
		u.highLight(By.xpath("//*[@id=\"mstr23\"]/div"));
		u.click(By.xpath("//*[@id=\"mstr23\"]/div"));// Clic Lupa Buscar
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"));
		u.sendKeys(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"), "Public Objects");// Buscar
																													// carpeta
																													// Public
																													// Objects
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));
		u.click(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));// Clic Lupa Buscar
		u.hardWait(4);
		u.highLight(By.xpath("//*[@id=\"mstr170\"]/div/div"));
		u.click(By.xpath("//*[@id=\"mstr170\"]/div/div"));// Clic Public Objects
		ima.SelectOption("Reports");// Reports
		ima.SelectOption("Control de Gastos");// Control de Gastos
		ima.SelectOption("Caratulas");// Caratulas
		ima.SelectOption("Costo Mano de Obra y Material");// Costo Mano de Obra y Material

	}

	public void generarReporteRefaccionesPorProveedor(String Tienda, String Ano, String Mes) {
		/*
		 * Created by: mhernandez85 Date: 07-07-2022 Description: Generar Reporte Gastos
		 * Mano de Obra por Prestador de Servicio ".
		 */

		ima.capturarPrompts2("id_mstr63_txt", Tienda, "//*[@id=\"id_mstr63\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr68\"]/img");
		ima.capturarPrompts2("id_mstr97_txt", Ano, "//*[@id=\"id_mstr97\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr101\"]/img");
		u.scroll();
		ima.capturarPrompts2("id_mstr131_txt", Mes, "//*[@id=\"id_mstr131\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr135\"]/img");
		ima.clickBtnEjecutarInforme("id_mstr144");

	}

	public void generarReporteGastosMensualPrestadorServiciosTienda(String Tienda, String Ano, String Mes) {
		/*
		 * Created by: mhernandez85 Date: 07-07-2022 Description: Generar ReporteGastos
		 * Mensual por Prestador de Servicios y Tienda ".
		 */

		ima.capturarPrompts2("id_mstr63_txt", Tienda, "//*[@id=\"id_mstr63\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr68\"]/img");
		ima.capturarPrompts2("id_mstr97_txt", Ano, "//*[@id=\"id_mstr97\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr101\"]/img");
		u.scroll();
		ima.capturarPrompts2("id_mstr131_txt", Mes, "//*[@id=\"id_mstr131\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr135\"]/img");
		ima.clickBtnEjecutarInforme("id_mstr144");

	}

	public void Select_GraficaCostosCategoriasAcumulado() {
		/*
		 * Created by: mhernandez85 Date: 08-07-2022 Description: Funcoion para Ingresar
		 * en la ruta: Mantenimiento\Public Objects\Reports\Control de
		 * Gastos\Reportes\Grafica Costos x Categorias Acumulado".
		 */
		ima.SelectOption("Mantenimiento QA");// Mantenimiento QA
		u.highLight(By.xpath("//*[@id=\"mstr23\"]/div"));
		u.click(By.xpath("//*[@id=\"mstr23\"]/div"));// Clic Lupa Buscar
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"));
		u.sendKeys(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"), "Public Objects");// Buscar
																													// carpeta
																													// Public
																													// Objects
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));
		u.click(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));// Clic Lupa Buscar
		u.hardWait(4);
		u.highLight(By.xpath("//*[@id=\"mstr170\"]/div/div"));
		u.click(By.xpath("//*[@id=\"mstr170\"]/div/div"));// Clic Public Objects
		ima.SelectOption("Reports");// Reports
		ima.SelectOption("Control de Gastos");// Control de Gastos
		ima.SelectOption("Reportes");// Reportes
		ima.SelectOption("Grafica Costos x Categorías Acumulado");// Grafica Costos x Categorías Acumulado


	}

	public void generarReporteGraficaCostosCategoriasAcumulado(String Tienda, String Ano, String Mes) {
		/*
		 * Created by: mhernandez85 Date: 07-07-2022 Description: Generar Reporte
		 * Grafica Costos x Categorias Acumulado".
		 */

		ima.capturarPrompts2("id_mstr63_txt", Tienda, "//*[@id=\"id_mstr63\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr68\"]/img");
		ima.capturarPrompts2("id_mstr97_txt", Ano, "//*[@id=\"id_mstr97\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr101\"]/img");
		u.scroll();
		ima.capturarPrompts2("id_mstr131_txt", Mes, "//*[@id=\"id_mstr131\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr135\"]/img");
		ima.clickBtnEjecutarInforme("id_mstr144");

	}

	public void Select_GraficaCostosCategoriasMensual() {
		/*
		 * Created by: mhernandez85 Date: 08-07-2022 Description: Funcoion para Ingresar
		 * en la ruta: Mantenimiento\Public Objects\Reports\Control de
		 * Gastos\Reportes\Grafica Costos x Categorias Mensual".
		 */
		ima.SelectOption("Mantenimiento QA");// Mantenimiento QA
		u.highLight(By.xpath("//*[@id=\"mstr23\"]/div"));
		u.click(By.xpath("//*[@id=\"mstr23\"]/div"));// Clic Lupa Buscar
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"));
		u.sendKeys(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"), "Public Objects");// Buscar
																													// carpeta
																													// Public
																													// Objects
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));
		u.click(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));// Clic Lupa Buscar
		u.hardWait(4);
		u.highLight(By.xpath("//*[@id=\"mstr170\"]/div/div"));
		u.click(By.xpath("//*[@id=\"mstr170\"]/div/div"));// Clic Public Objects
		ima.SelectOption("Reports");// Reports
		ima.SelectOption("Control de Gastos");// Control de Gastos
		ima.SelectOption("Reportes");// Reportes

		u.hardWait(2);
		ima.SelectOptionByXpath("//*[@id=\"FolderIcons\"]/tbody/tr[2]/td[1]");// Grafica Costos x Categorias Mensual

	}

	public void generarReporteGraficaCostosCategoriasMensual(String Tienda, String Ano, String Mes) {
		/*
		 * Created by: mhernandez85 Date: 07-07-2022 Description: Generar Reporte
		 * Grafica Costos x Categorias Mensual".
		 */
		ima.SelectOption("Grafica Costos x Categorías Mensual");// Grafica Costos x Categorias Mensual

	}

	public void generarReporteGraficaCostosCategoriasMensual2(String Tienda, String Ano, String Mes) {
		/*
		 * Created by: mhernandez85 Date: 07-07-2022 Description: Generar Reporte
		 * Grafica Costos x Categorias Mensual".
		 */

		ima.capturarPrompts2("id_mstr63_txt", Tienda, "//*[@id=\"id_mstr63\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr68\"]/img");
		ima.capturarPrompts2("id_mstr97_txt", Ano, "//*[@id=\"id_mstr97\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr101\"]/img");
		u.scroll();
		ima.capturarPrompts2("id_mstr131_txt", Mes, "//*[@id=\"id_mstr131\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr135\"]/img");
		ima.clickBtnEjecutarInforme("id_mstr144");

	}

	public void Select_PresupuestoGastosAcumulado() {
		/*
		 * Created by: mhernandez85 Date: 08-07-2022 Description: Funcoion para Ingresar
		 * en la ruta: Mantenimiento\Public Objects\Reports\Control de
		 * Gastos\Reportes\Presupuesto\Gastos Acumulado".
		 */
		ima.SelectOption("Mantenimiento QA");// Mantenimiento QA
		u.highLight(By.xpath("//*[@id=\"mstr23\"]/div"));
		u.click(By.xpath("//*[@id=\"mstr23\"]/div"));// Clic Lupa Buscar
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"));
		u.sendKeys(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"), "Public Objects");// Buscar
																													// carpeta
																													// Public
																													// Objects
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));
		u.click(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));// Clic Lupa Buscar
		u.hardWait(4);
		u.highLight(By.xpath("//*[@id=\"mstr170\"]/div/div"));
		u.click(By.xpath("//*[@id=\"mstr170\"]/div/div"));// Clic Public Objects
		ima.SelectOption("Reports");// Reports
		ima.SelectOption("Control de Gastos");// Control de Gastos
		ima.SelectOption("Reportes");// Reportes
		ima.SelectOption("Presupuesto / Gastos Acumulado");// Presupuesto / Gastos Acumulado

	}

	public void generarReportePresupuestoGastosAcumulado(String Tienda, String Ano, String Mes) {
		/*
		 * Created by: mhernandez85 Date: 07-07-2022 Description: Generar Reporte
		 * Grafica Costos x Categorias Mensual".
		 */

		ima.capturarPrompts2("id_mstr97_txt", Ano, "//*[@id=\"id_mstr97\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr101\"]/img");
		u.scroll();
		ima.capturarPrompts2("id_mstr131_txt", Mes, "//*[@id=\"id_mstr131\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr135\"]/img");
		/*
		 * ima.capturarPrompts2("id_mstr66_txt", Tienda,
		 * "//*[@id=\"id_mstr63\"]/table/tbody/tr/td[2]/div/input",
		 * "//*[@id=\"id_mstr68\"]/img");
		 */

		ima.clickBtnEjecutarInforme("id_mstr144");

	}

	public void Select_PresupuestoGastosMensual() {
		/*
		 * Created by: mhernandez85 Date: 08-07-2022 Description: Funcoion para Ingresar
		 * en la ruta: Mantenimiento\Public Objects\Reports\Control de
		 * Gastos\Reportes\Presupuesto / Gastos Mensual".
		 */
		ima.SelectOption("Mantenimiento QA");// Mantenimiento QA
		u.highLight(By.xpath("//*[@id=\"mstr23\"]/div"));
		u.click(By.xpath("//*[@id=\"mstr23\"]/div"));// Clic Lupa Buscar
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"));
		u.sendKeys(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"), "Public Objects");// Buscar
																													// carpeta
																													// Public
																													// Objects
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));
		u.click(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));// Clic Lupa Buscar
		u.hardWait(4);
		u.highLight(By.xpath("//*[@id=\"mstr170\"]/div/div"));
		u.click(By.xpath("//*[@id=\"mstr170\"]/div/div"));// Clic Public Objects
		ima.SelectOption("Reports");// Reports
		ima.SelectOption("Control de Gastos");// Control de Gastos
		ima.SelectOption("Reportes");// Reportes
		ima.SelectOption("Presupuesto / Gastos Mensual");// Presupuesto / Gastos Mensual

	}

	public void generarPresupuestoGastosMensual(String Tienda, String Ano, String Mes) {
		/*
		 * Created by: mhernandez85 Date: 07-07-2022 Description: Generar Reporte
		 * Grafica Costos x Categorias Mensual".
		 */

		ima.capturarPrompts2("id_mstr63_txt", Tienda, "//*[@id=\"id_mstr63\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr68\"]/img");
		ima.capturarPrompts2("id_mstr97_txt", Ano, "//*[@id=\"id_mstr97\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr101\"]/img");
		u.scroll();
		ima.capturarPrompts2("id_mstr131_txt", Mes, "//*[@id=\"id_mstr131\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr135\"]/img");
		ima.clickBtnEjecutarInforme("id_mstr144");

	}

	public void SelectFolder_ReporteConsolidadoRefaccionesPorProveedor() {
		/*
		 * Created by: mhernandez85 Date: 20-07-2022 Description: función para
		 * seleccionar la siguiente Ruta:  Mantenimiento QA > Informes Compartidos >
		 * Control de Gastos > Refacciones  > Reporte Consolidado de refacciones por
		 * Proveedor.
		 */
		ima.SelectOption("Mantenimiento QA");
		ima.SelectOption("Control de Gastos");
		ima.SelectOption("Refacciones");
		ima.SelectOption("Consolidado de Refacciones por Proveedor");

		testCase.addScreenShotCurrentStep(u, "Reporte Consolidado de refacciones por Proveedor");

	}

	public void Generate_ReporteConsolidadoRefaccionesPorProveedor(String Tienda, String Ano, String Mes) {
		/*
		 * Created by: mhernandez85 Date: 20-07-2022 Description: Generar Reporte
		 * Grafica Costos x Categorias Mensual".
		 */

		ima.capturarPrompts2("id_mstr63_txt", Tienda, "//*[@id=\"id_mstr63\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr68\"]/img");
		ima.capturarPrompts2("id_mstr97_txt", Ano, "//*[@id=\"id_mstr97\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr101\"]/img");
		u.scroll();
		ima.capturarPrompts2("id_mstr131_txt", Mes, "//*[@id=\"id_mstr131\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr135\"]/img");
		ima.clickBtnEjecutarInforme("id_mstr144");

	}

	public void ValidarSentenciaDeBusqueda(String Cadena1, String Cadena2, String Cadena3) {
		/*
		 * Created by: mhernandez85 Date: 20-07-2022 Description: Validar la sentencia
		 * que genera Microstrategy, con la finalidad de comprobar  que la informacion
		 * mostrada.
		 */

		u.waitWebElement(
				By.xpath("//*[@id=\"reportDetails_ReportDetailsPanelStyle\"]/div[1]/table/tbody/tr[1]/td/div/span[1]"),
				120, 600);
		u.highLight(
				By.xpath("//*[@id=\"reportDetails_ReportDetailsPanelStyle\"]/div[1]/table/tbody/tr[1]/td/div/span[1]"));
		u.highLight(By.xpath("//*[@id=\"promptDetails_PromptDetailsStyle\"]/div[1]/table/tbody/tr[1]/td/div/span[1]"));
		ima.CompararCadena(By.xpath(
				"//*[@id=\"reportDetails_ReportDetailsPanelStyle\"]/div[1]/table/tbody/tr[2]/td/table/tbody/tr[1]/td"),
				Cadena1);// Verifique que la seccion Detalles del informe contenga la palabra ingresada
							// para el campo
		ima.CompararCadena(By.xpath(
				"//*[@id=\"reportDetails_ReportDetailsPanelStyle\"]/div[1]/table/tbody/tr[2]/td/table/tbody/tr[1]/td"),
				Cadena2);// Verifique que la seccion Detalles del informe contenga la palabra ingresada
							// para el campo
		ima.CompararCadena(By.xpath(
				"//*[@id=\"reportDetails_ReportDetailsPanelStyle\"]/div[1]/table/tbody/tr[2]/td/table/tbody/tr[1]/td"),
				Cadena3);// Verifique que la seccion Detalles del informe contenga la palabra ingresada
							// para el campo

	}

	public void Select_ProyeccionVentaMensual_vs_MesAnioAnterior() {
		/*
		 * Created by: mhernandez85 Date: 21-07-2022 Description: Funcoion para Ingresar
		 * en la ruta: Informacion Comercial QA\Public Objects\Reports\Informacion
		 * Comercial\Analisis de Proyecciones\ Proyecci�n de Venta Mensual vs Mes a�o
		 * Anterior"
		 */
		ima.SelectOption("Información Comercial QA");// Información Comercial QA
		u.highLight(By.xpath("//*[@id=\"mstr23\"]/div"));
		u.click(By.xpath("//*[@id=\"mstr23\"]/div"));// Clic Lupa Buscar
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"));
		u.sendKeys(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"), "Public Objects");// Buscar
																													// carpeta
																													// Public
																													// Objects
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));
		u.click(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));// Clic Lupa Buscar
		u.hardWait(4);
		u.highLight(By.xpath("//*[@id=\"mstr186\"]/div/div"));
		u.click(By.xpath("//*[@id=\"mstr186\"]/div/div"));// Clic Public Objects
		ima.SelectOption("Reports");// Reports
		u.highLight(By.xpath("//*[@id=\"FolderList\"]/tbody/tr[9]/td[2]/span[1]/a"));
		u.click(By.xpath("//*[@id=\"FolderList\"]/tbody/tr[9]/td[2]/span[1]/a"));// Informacion Comercial
		ima.SelectOption("Análisis de Proyecciones");// An�lisis de Proyecciones
		u.hardWait(4);
		ima.SelectOption("Proyección de Venta Mensual vs Mes Año Anterior");// Proyeccion de Venta Mensual vs Mes A�o
																			// Anterior
	}

	public void ValidarProyeccionVentaMensual_vs_MesAnioAnterior(String Zona, String Plaza) {
		/*
		 * Created by: mhernandez85 Date: 21-07-2022 Description: Validar que el reporte
		 * Poza Rica en la Zona Sureste.
		 */
		ima.CompararCadena(By.xpath("//*[@id=\"table_UniqueReportID\"]/tbody/tr[2]/td[1]"), Zona);// Verifique que
																									// contenga la Zona
																									// Sureste
		ima.CompararCadena(By.xpath("//*[@id=\"table_UniqueReportID\"]/tbody/tr[2]/td[2]/a"), Plaza);// Verifique que la
																										// contenga la
																										// plaza Poza
																										// Rica
	}

	public void SelectFolder_ReporteArticulosEnPromocionPorPeriodo_Plaza() {
		/*
		 * Created by: mhernandez85 Date: 20-07-2022 Description: función para
		 * seleccionar la siguiente Ruta:  Información Comercial>Informes Compartidos>A
		 * Reportes por Liberar>Promociones periodo 28 días >reporte Articulos en
		 * promocion por periodo (Plaza)
		 */
		ima.SelectOption("Información Comercial QA");// Información Comercial QA
		ima.SelectOption("A Reportes por Liberar");// A Reportes por Liberar
		ima.SelectOption("Promociones periodo 28 días");// Promociones periodo 28 días
		ima.SelectOption("Articulos en promocion por periodo (Plaza)");// Articulos en promocion por periodo (Plaza)
		testCase.addScreenShotCurrentStep(u, "Reporte Articulos en promocion por periodo (Plaza)");

	}

	public void Generate_ReporteArticulosEnPromocionPorPeriodo(String Tienda, String MesPromocional,
			String categoriaProducto) {
		/*
		 * Created by: mhernandez85 Date: 22-07-2022 Description: Generar Reporte
		 * Articulos en promoción por periodo (Plaza)".
		 */
		ima.capturarPromptsConSeleccion("//*[@id=\"id_mstr87ListContainer\"]/div[2]/div/a", "id_mstr80_txt", Tienda,
				"//*[@id=\"id_mstr80\"]/table/tbody/tr/td[2]/div/input", "//*[@id=\"id_mstr87ListContainer\"]/div/div",
				"//*[@id=\"id_mstr91\"]/img");// Seleccione de la lista la(s) Plazas que seran incluidas en el an�lisis.
		u.click(By.xpath("//*[@id=\"id_mstr117ListContainer\"]/div[10]/div"));// Clic sobre Tiendas Todas
		u.click(By.xpath("//*[@id=\"id_mstr119\"]/img"));// clic en el bot�n agregar a la izquierda
		u.scroll();
		ima.capturarPrompts2("id_mstr150_txt", MesPromocional, "//*[@id=\"id_mstr150\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr155\"]/img");// Elegir Mes Promocional
		ima.capturarPromptsConSeleccion("//*[@id=\"id_mstr196ListContainer\"]/div[2]/div/a", "id_mstr189_txt",
				categoriaProducto, "//*[@id=\"id_mstr189\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr196ListContainer\"]/div[1]/div", "//*[@id=\"id_mstr200\"]/img");// Selecciona la
																						// producto
		ima.clickBtnEjecutarInforme("id_mstr208");
		testCase.addScreenShotCurrentStep(u, "Reporte Articulos en promocion por periodo (Plaza)");

	}

	public void SelectFolder_ReporteArticulosEnPromocionPorPeriodo_Segmento() {
		/*
		 * Created by: mhernandez85 Date: 20-07-2022 Description: función para
		 * seleccionar la siguiente Ruta:  Informacion Comercial>Informes Compartidos>A
		 * Reportes por Liberar>Promociones periodo 28 días >reporte Articulos en
		 * promocion por periodo (Plaza)
		 */
		ima.SelectOption("Información Comercial QA");// Información Comercial QA
		ima.SelectOption("A Reportes por Liberar");// A Reportes por Liberar
		ima.SelectOption("Promociones periodo 28 días");// Promociones periodo 28 días
		ima.SelectOption("Articulos en promocion por periodo (Segmento)");// Articulos en promocion por periodo
																			// (Segmento)
		testCase.addScreenShotCurrentStep(u, "Reporte Articulos en promocion por periodo (Segmento)");

	}

	public void SelectFolder_ReporteArticulosEnPromocionPorPeriodo_Tienda() {
		/*
		 * Created by: mhernandez85 Date: 20-07-2022 Description: funcion para
		 * seleccionar la siguiente Ruta:  Informacion Comercial>Informes Compartidos>A
		 * Reportes por Liberar>Promociones periodo 28 dias >reporte Articulos en
		 * promocion por periodo (Plaza)
		 */
		ima.SelectOption("Información Comercial QA");// Información Comercial QA
		ima.SelectOption("A Reportes por Liberar");// A Reportes por Liberar
		ima.SelectOption("Promociones periodo 28 días");// Promociones periodo 28 días
		ima.SelectOption("Articulos en promocion por periodo (Tienda)");// Articulos en promocion por periodo (Tienda)
		testCase.addScreenShotCurrentStep(u, "Reporte Articulos en promocion por periodo (Tienda)");

	}

	public void SelectFolder_ReporteEvaluacionPromocionesPeriodos28D() {
		/*
		 * Created by: mhernandez85 Date: 22-07-2022 Description: funcion para
		 * seleccionar la siguiente Ruta: Información Comercial>Informes
		 * Compartidos>Informacion Comercial>Análisis de Promociones>Se selecciona el
		 * reporte Evaluacion de Promociones Periodos 28D
		 */
		ima.SelectOption("Información Comercial QA");// Información Comercial QA
		ima.SelectOptionByXpath("//a[title=\"Información Comercial\"]");
		// ima.SelectOption("Información Comercial");//Información Comercial
		ima.SelectOption("Análisis de Promociones");// Análisis de Promociones
		ima.SelectOption("Evaluación de Promociones Periodos 28D");// Evaluación de Promociones Periodos 28D
		testCase.addScreenShotCurrentStep(u, "Reporte Evaluación de Promociones Periodos 28D");

	}

	public void Generate_ReporteEvaluacionPromocionesPeriodos28D(String Tienda, String MesPromocional,
			String categoriaProducto) {
		/*
		 * Created by: mhernandez85 Date: 22-07-2022 Description: Generar Reporte
		 * Evaluacion de Promociones Periodos 28D".
		 */

		ima.capturarPromptsConSeleccion("//*[@id=\"id_mstr87ListContainer\"]/div[2]/div/a", "id_mstr80_txt", Tienda,
				"//*[@id=\"id_mstr80\"]/table/tbody/tr/td[2]/div/input", "//*[@id=\"id_mstr87ListContainer\"]/div/div",
				"//*[@id=\"id_mstr91\"]/img");// Seleccione de la lista la(s) Plazas que seran incluidas en el análisis.
		u.click(By.xpath("//*[@id=\"id_mstr117ListContainer\"]/div[10]/div"));// Clic sobre Tiendas Todas
		u.click(By.xpath("//*[@id=\"id_mstr119\"]/img"));// clic en el botón agregar a la izquierda
		u.scroll();
		ima.capturarPrompts2("id_mstr150_txt", MesPromocional, "//*[@id=\"id_mstr150\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr155\"]/img");// Elegir Mes Promocional
		ima.capturarPromptsConSeleccion("//*[@id=\"id_mstr196ListContainer\"]/div[2]/div/a", "id_mstr189_txt",
				categoriaProducto, "//*[@id=\"id_mstr189\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr196ListContainer\"]/div[1]/div", "//*[@id=\"id_mstr200\"]/img");// Selecciona la
																									// producto
		ima.clickBtnEjecutarInforme("id_mstr208");
		testCase.addScreenShotCurrentStep(u, "Reporte Articulos en promocion por periodo (Plaza)");

	}

	public void SelectFolder_PurchaseReportsAndReturns() {
		/*
		 * Created by: mhernandez85 Date: 29-06-2022 Description: Function to select the
		 * Purchase Reports and Returns folder
		 */
		ima.SelectOption("InformaciÃ³n Comercial QA");
		ima.SelectOptionByXpath("//*[@id=\"FolderIcons\"]/tbody/tr[5]/td[1]/table/tbody/tr/td[2]/span/a"); // informacion
																											// comercial
		ima.SelectOptionByXpath("//*[@id=\"FolderList\"]/tbody/tr[31]/td[2]/span[1]/a"); // reportes colombia
		u.hardWait(3);
		ima.SelectOptionByXpath("//*[@id=\"FolderList\"]/tbody/tr[1]/td[2]/span[1]/a"); // analisis de compras
		testCase.addScreenShotCurrentStep(u, "Seleccionar la carpeta de compras y devoluciones");

	}

	public void Generate_ReportReceptionsByProductPilotStoresCEDISMonthly2() {
		/*
		 * Created by: Alfonso Date: 29-06-2022 Description: Function to generate the
		 * report Receptions by product pilot stores CEDIS Monthly
		 */
		ima.capturarPrompts2("id_mstr58_txt", "202007", "//*[@id=\"id_mstr58\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr62\"]/img");
		u.scroll();
		ima.capturarPromptsConSeleccion("//*[@id=\"id_mstr104ListContainer\"]/div[2]/div/a", "id_mstr97_txt",
				"CIGARRILLOS", "//*[@id=\"id_mstr97\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr104ListContainer\"]/div[1]/div", "//*[@id=\"id_mstr108\"]/img");
		ima.clickBtnEjecutarInforme("id_mstr116");
	}

	public void validacionDeReportReceptionsByProductPilotStoresCEDISMonthly2() {
		/*
		 * Created by: Alfonso Date: 30-06-2022 Description: Function to Validate that
		 * the report displays the correct columns
		 */
		ima.validarColumnas(By.id("UniqueReportID_1_1_1"), "Producto"); // SKU
		ima.validarColumnas(By.id("UniqueReportID_2_2_1"), "Indicadores"); // DESCRIPCION PRODUCTO
		ima.validarColumnas(By.id("UniqueReportID_-1_2_1"), "Uds Recibidas Directos MA"); // Unidades recibidas Netas
																							// (Mes actual)
		ima.validarColumnas(By.id("UniqueReportID_-1_4_1"), "Uds Recibidas Directos MMAA"); // Unidades recibidas Netas
																							// (Mismo mes, aÃ±o
																							// anterior)
		ima.validarColumnas(By.id("UniqueReportID_-1_1_1"), "$ Cmp Directos MA"); // $ Recepciones Netas (Mes actual)
		ima.validarColumnas(By.id("UniqueReportID_-1_3_1"), "$ Cmp Directos MMAA"); // $ Recepciones Netas (Mismo mes,
																					// aÃ±o anterior)
		ima.validarColumnas(By.id("UniqueReportID_-1_5_1"), "$ VariaciÃ³n"); // $ VariaciÃ³n
		ima.validarColumnas(By.id("UniqueReportID_-1_6_1"), "% VariaciÃ³n"); // % VariaciÃ³n
		testCase.addScreenShotCurrentStep(u, "Validar que el reporte despliegue las  columnas correctas");
	}

	public void NavegacioMenuDatosOpcionNavegacion2() {
		/*
		 * Created by: Alfonso Date: 30-06-2022 Description: Funcion para Navegar al
		 * menu "Datos", y seleccionar la opcion "Navegacion".
		 */
		u.click(By.xpath("//*[@id=\"ribbonToolbarTabsListContainer\"]/div[3]/table/tbody/tr/td[3]"));// Click Menu Datos
		u.highLight(By.xpath("//*[@id=\"RptDataMenu_\"]/tbody/tr/td/div/div[3]/a/div[2]/span[1]"));
		u.click(By.xpath("//*[@id=\"RptDataMenu_\"]/tbody/tr/td/div/div[3]/a/div[2]/span[1]"));// Click SubMenu
																								// NAVEGACIÃ“N
		u.hardWait(6);
		testCase.addScreenShotCurrentStep(u, "Navegar al menÃº Datos y seleccionar la opciÃ³n NavegaciÃ³ns");
	}

	public void ValidarFiltroMuneuDatosNavegacion2(String filterType) {
		/*
		 * Created by: Alfonso Date: 30-06-2022 Description: Function to validate the
		 * filters of the Report of Receptions by product pilot stores CEDIS Mensua
		 * Precondition: Pass through the data provider, the filter type field
		 * "FilterType"
		 */
		switch (filterType) {
		case "Filtro Tiempo":

			System.out.println("Entro a Filtro Tiempo ");
			u.click(By.xpath("//*[@id=\"optimizedDrillKeyAndProps_2\"]/option[3]"));// Seleccionar Filtro
			u.highLight(By.id("deDrillImageName_2"));
			u.click(By.id("deDrillImageName_2"));// btn aplicar
			u.scroll();
			testCase.addScreenShotCurrentStep(u, "Aplicar un filtro para el campo Ejercicio (AÃ±o y Mes)");

			break;

		case "Filtro CategorÃ­as":

			System.out.println("Entro a Filtro CategorÃ­as");
			u.click(By.xpath("//*[@id=\"optimizedDrillKeyAndProps_5\"]/option[2]"));// Seleccionar Filtro
			u.highLight(By.id("deDrillImageName_5"));
			u.click(By.id("deDrillImageName_5"));// btn aplicar
			u.scroll();
			testCase.addScreenShotCurrentStep(u, "Aplicar un filtro para el campo CategorÃ­a");

			break;

		case "Filtro Tienda piloto CEDIS":

			System.out.println("Entro a Filtro Tienda piloto CEDI");
			break;

		default:
			System.out.println("El filtro : " + filterType + "No Existe");

		}
	}

	public void NavegacioMenuInicioDeInformes_Exportar_ExcelConFormato2() {
		u.waitForLoadPage();
		u.highLight(By.xpath("//*[@id=\"ribbonToolbarTabsListContainer\"]/div[1]/table/tbody/tr/td[3]"));
		u.click(By.xpath("//*[@id=\"ribbonToolbarTabsListContainer\"]/div[1]/table/tbody/tr/td[3]"));// Click Menu
																										// Inicio de
																										// Informes
		u.highLight(By.xpath("//*[@id=\"RptHomeMenu_\"]/tbody/tr/td/div/div[18]/a/div[2]"));
		u.click(By.xpath("//*[@id=\"RptHomeMenu_\"]/tbody/tr/td/div/div[18]/a/div[2]"));// Click SubMenu Exportar
		u.highLight(By.xpath(
				"//*[@id=\"RptHomeExportMenu_WEB-INFxmllayoutsblocksHomeExportMenuLayoutxml\"]/tbody/tr/td/div/div[8]/a"));
		u.click(By.xpath(
				"//*[@id=\"RptHomeExportMenu_WEB-INFxmllayoutsblocksHomeExportMenuLayoutxml\"]/tbody/tr/td/div/div[8]/a"));// Click
																															// Excel
																															// con formato
		u.hardWait(6);
		testCase.addScreenShotCurrentStep(u, "Exportar Excel con Formato");
	}

	public void NavegacioMenuInicioDeInformes_Exportar_PDF2() {
		/*
		 * Created by: Alfonso Date: 01-07-2022 Description: FunciÃ³n para Navegar al
		 * menÃº Inicio de Informes>>Exportar>>Excel Con Formato".
		 */
		u.waitForLoadPage();
		u.highLight(By.xpath("//*[@id=\"ribbonToolbarTabsListContainer\"]/div[1]/table/tbody/tr/td[3]"));
		u.click(By.xpath("//*[@id=\"ribbonToolbarTabsListContainer\"]/div[1]/table/tbody/tr/td[3]"));// Click Menu
																										// Inicio de
																										// Informes
		u.highLight(By.xpath("//*[@id=\"RptHomeMenu_\"]/tbody/tr/td/div/div[18]/a/div[2]"));
		u.click(By.xpath("//*[@id=\"RptHomeMenu_\"]/tbody/tr/td/div/div[18]/a/div[2]"));// Click SubMenu Exportar
		u.highLight(By.xpath(
				"//*[@id=\"RptHomeExportMenu_WEB-INFxmllayoutsblocksHomeExportMenuLayoutxml\"]/tbody/tr/td/div/div[1]/a"));
		u.click(By.xpath(
				"//*[@id=\"RptHomeExportMenu_WEB-INFxmllayoutsblocksHomeExportMenuLayoutxml\"]/tbody/tr/td/div/div[1]/a"));// Click
																															// PDF
		u.hardWait(6);
		testCase.addScreenShotCurrentStep(u, "Exportar PDF");
	}

	public void ValidarFormatoColumnasReporte2() {
		/*
		 * Created by: Alfonso Date: 01-07-2022 Description: Funcoion Validar el formato
		 * de las columnas del reporte Reporte de Recepciones por producto tiendas
		 * piloto CEDIS Mensual ".
		 */
		ima.CompararCadena(By.xpath("//*[@id=\"table_UniqueReportID\"]/tbody/tr[6]/td[3]"), "$"); // $ Cmp Directos MA
		ima.CompararCadena(By.xpath("//*[@id=\"table_UniqueReportID\"]/tbody/tr[6]/td[5]"), "$"); // $ Cmp Directos MMAA
		ima.CompararCadena(By.xpath("//*[@id=\"table_UniqueReportID\"]/tbody/tr[6]/td[7]"), "$"); // $ VariaciÃ³n
		ima.CompararCadena(By.xpath("//*[@id=\"table_UniqueReportID\"]/tbody/tr[6]/td[8]"), "%"); // % VariaciÃ³n
	}

	public void Select_FolderDetalleOrdenDeTrabajo2() {
		/*
		 * Created by: Alfonso Date: 07-07-2022 Description: Funcoion para Ingresar en
		 * la ruta: Mantenimiento\Public Objects\Reports\Control de
		 * Gastos\Caratulas\Detalle de Orden de Trabajo ".
		 */
		ima.SelectOption("Mantenimiento QA");// Mantenimiento QA
		u.highLight(By.xpath("//*[@id=\"mstr23\"]/div"));
		u.click(By.xpath("//*[@id=\"mstr23\"]/div"));// Clic Lupa Buscar
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"));
		u.sendKeys(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"), "Public Objects");// Buscar
																													// carpeta
																													// Public
																													// Objects
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));
		u.click(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));// Clic Lupa Buscar
		u.hardWait(4);
		u.highLight(By.xpath("//*[@id=\"mstr170\"]/div/div"));
		u.click(By.xpath("//*[@id=\"mstr170\"]/div/div"));// Clic Public Objects
		ima.SelectOption("Reports");// Reports
		ima.SelectOption("Control de Gastos");// Control de Gastos
		ima.SelectOption("Caratulas");// Caratulas
		ima.SelectOption("Detalle de Orden de Trabajo");// Detalle de Orden de Trabajo

	}

	public void generarReporteDetalleDeOrdenDeTrabajo2(String Tienda, String Ano, String Mes) {
		/*
		 * Created by: Alfonso Date: 07-07-2022 Description: Generar Reporte Detalle de
		 * Orden de Trabajo ".
		 */

		ima.capturarPrompts2("id_mstr63_txt", Tienda, "//*[@id=\"id_mstr63\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr67\"]/img");
		ima.capturarPrompts2("id_mstr97_txt", Ano, "//*[@id=\"id_mstr97\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr101\"]/img");
		u.scroll();
		ima.capturarPrompts2("id_mstr131_txt", Mes, "//*[@id=\"id_mstr131\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr135\"]/img");
		ima.clickBtnEjecutarInforme("id_mstr144");

	}

	public void generarReporteGastosManoDeObraPorPrestadorDeServicio2(String Tienda, String Ano, String Mes) {
		/*
		 * Created by: Alfonso Date: 07-07-2022 Description: Generar Reporte Gastos Mano
		 * de Obra por Prestador de Servicio ".
		 */

		ima.capturarPrompts2("id_mstr63_txt", Tienda, "//*[@id=\"id_mstr63\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr68\"]/img");
		ima.capturarPrompts2("id_mstr97_txt", Ano, "//*[@id=\"id_mstr97\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr101\"]/img");
		u.scroll();
		ima.capturarPrompts2("id_mstr131_txt", Mes, "//*[@id=\"id_mstr131\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr135\"]/img");
		ima.clickBtnEjecutarInforme("id_mstr144");

	}

	public void generarReporteCostoManoDeObraMaterial2(String Tienda, String Ano, String Mes) {
		/*
		 * Created by:Alfonso Date: 07-07-2022 Description: Generar Reporte Gastos Mano
		 * de Obra por Prestador de Servicio ".
		 */

		ima.capturarPrompts2("id_mstr63_txt", Tienda, "//*[@id=\"id_mstr63\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr68\"]/img");
		ima.capturarPrompts2("id_mstr97_txt", Mes, "//*[@id=\"id_mstr97\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr101\"]/img");
		u.scroll();
		ima.capturarPrompts2("id_mstr131_txt", Ano, "//*[@id=\"id_mstr131\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr135\"]/img");

		ima.clickBtnEjecutarInforme("id_mstr144");

	}

	public void Select_FolderGastosManoDeObraPorPrestadorDeServicio2() {
		/*
		 * Created by: Alfonso Date: 07-07-2022 Description: Funcion para Ingresar en la
		 * ruta: Mantenimiento\Public Objects\Reports\Control de Gastos\Caratulas\Gastos
		 * Mano de Obra por Prestador de Servicio".
		 */
		ima.SelectOption("Mantenimiento QA");// Mantenimiento QA
		u.highLight(By.xpath("//*[@id=\"mstr23\"]/div"));
		u.click(By.xpath("//*[@id=\"mstr23\"]/div"));// Clic Lupa Buscar
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"));
		u.sendKeys(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"), "Public Objects");// Buscar
																													// carpeta
																													// Public
																													// Objects
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));
		u.click(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));// Clic Lupa Buscar
		u.hardWait(4);
		u.highLight(By.xpath("//*[@id=\"mstr170\"]/div/div"));
		u.click(By.xpath("//*[@id=\"mstr170\"]/div/div"));// Clic Public Objects
		ima.SelectOption("Reports");// Reports
		ima.SelectOption("Control de Gastos");// Control de Gastos
		ima.SelectOption("Caratulas");// Caratulas
		ima.SelectOption("Gastos Mano de Obra por Prestador de Servicio");// Gastos Mano de Obra por Prestador de
																			// Servicio

	}

	public void Select_FolderRefaccionesConsolidado() {
		/*
		 * Created by: Date: 08-07-2022 Description: Funcion para Ingresar en la ruta:
		 * Mantenimiento\Public Objects\Reports\Control de
		 * Gastos\Refacciones\Consolidado de refacciones".
		 */
		ima.SelectOption("Mantenimiento QA");// Mantenimiento QA
		u.highLight(By.xpath("//*[@id=\"mstr23\"]/div"));
		u.click(By.xpath("//*[@id=\"mstr23\"]/div"));// Clic Lupa Buscar
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"));
		u.sendKeys(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"), "Public Objects");// Buscar
																													// carpeta
																													// Public
																													// Objects
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));
		u.click(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));// Clic Lupa Buscar
		u.hardWait(4);
		u.highLight(By.xpath("//*[@id=\"mstr170\"]/div/div"));
		u.click(By.xpath("//*[@id=\"mstr170\"]/div/div"));// Clic Public Objects
		ima.SelectOption("Reports");// Reports
		ima.SelectOption("Control de Gastos");// Control de Gastos
		ima.SelectOption("Refacciones");// Refacciones
		//ima.SelectOption("Consolidado de Refacciones");// Consolidado de refacciones
		u.hardWait(3);
		u.click(By.xpath("//*[@id=\"mstr23\"]/div"));
		u.highLight(By.xpath("/html/body/div[10]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"));
		u.sendKeys(By.xpath("/html/body/div[10]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"), "consolidado de refacciones");
		u.highLight(By.xpath("//*[@id=\"mstr97\"]/div/div"));
		u.click(By.xpath("//*[@id=\"mstr97\"]/div/div"));
						  
						  
	}
	
	public void validacionReporteRefaConsol() {
		ima.validarColumnas(By.xpath("//*[@id=\"UniqueReportID_1_1_1\"]"), "Proveedor"); 
		ima.validarColumnas(By.xpath("/html/body/div[4]/table/tbody/tr/td[2]/div[2]/div[1]/table/tbody/tr/td[2]/form/div[11]/table[1]/tbody/tr/td/div/div/div[1]/table/tbody/tr[1]/td[2]"), "Refaccion"); 
		ima.validarColumnas(By.xpath("/html/body/div[4]/table/tbody/tr/td[2]/div[2]/div[1]/table/tbody/tr/td[2]/form/div[11]/table[1]/tbody/tr/td/div/div/div[1]/table/tbody/tr[1]/td[3]"), "Indicadores"); 
		ima.validarColumnas(By.xpath("/html/body/div[4]/table/tbody/tr/td[2]/div[2]/div[1]/table/tbody/tr/td[2]/form/div[11]/table[1]/tbody/tr/td/div/div/div[1]/table/tbody/tr[1]/td[4]"), "Cantidad"); 
		ima.validarColumnas(By.xpath("/html/body/div[4]/table/tbody/tr/td[2]/div[2]/div[1]/table/tbody/tr/td[2]/form/div[11]/table[1]/tbody/tr/td/div/div/div[1]/table/tbody/tr[1]/td[5]"), "Costo Total"); 
	}

	public void Select_FolderConsumoRefacciones() {
		/*
		 * Created by: Date: 08-07-2022 Description: Funcion para Ingresar en la ruta:
		 * Mantenimiento\Public Objects\Reports\Control de Gastos\Refacciones\Consumo de
		 * refacciones".
		 */
		ima.SelectOption("Mantenimiento QA");// Mantenimiento QA
		u.highLight(By.xpath("//*[@id=\"mstr23\"]/div"));
		u.click(By.xpath("//*[@id=\"mstr23\"]/div"));// Clic Lupa Buscar
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"));
		u.sendKeys(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"), "Public Objects");// Buscar
																													// carpeta
																													// Public
																													// Objects
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));
		u.click(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));// Clic Lupa Buscar
		u.hardWait(4);
		u.highLight(By.xpath("//*[@id=\"mstr170\"]/div/div"));
		u.click(By.xpath("//*[@id=\"mstr170\"]/div/div"));// Clic Public Objects
		ima.SelectOption("Reports");// Reports
		ima.SelectOption("Control de Gastos");// Control de Gastos
		ima.SelectOption("Refacciones");// Refacciones
		ima.SelectOption("Consumo de Refacciones");// Consumo de refacciones

	}

	public void Select_FolderRefaccionesUsadas() {
		/*
		 * Created by: Date: 08-07-2022 Description: Funcion para Ingresar en la ruta:
		 * Mantenimiento\Public Objects\Reports\Control de Gastos\Refacciones\Consumo de
		 * refacciones".
		 */
		ima.SelectOption("Mantenimiento QA");// Mantenimiento QA
		u.highLight(By.xpath("//*[@id=\"mstr23\"]/div"));
		u.click(By.xpath("//*[@id=\"mstr23\"]/div"));// Clic Lupa Buscar
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"));
		u.sendKeys(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"), "Public Objects");// Buscar
																													// carpeta
																													// Public
																													// Objects
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));
		u.click(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));// Clic Lupa Buscar
		u.hardWait(4);
		u.highLight(By.xpath("//*[@id=\"mstr170\"]/div/div"));
		u.click(By.xpath("//*[@id=\"mstr170\"]/div/div"));// Clic Public Objects
		ima.SelectOption("Reports");// Reports
		ima.SelectOption("Control de Gastos");// Control de Gastos
		ima.SelectOption("Refacciones");// Refacciones
		ima.SelectOption("Refacciones Mas Usadas");// Consumo de refacciones

	}

	public void Select_GastosMensualPrestadorServiciosTienda2() {
		/*
		 * Created by: Date: 11-07-2022 Description: Funcion para Ingresar en la ruta:
		 * Mantenimiento\Public Objects\Reports\Control de Gastos\Reportes\Gastos
		 * Mensual por Prestador de Servicios y Tienda".
		 */
		ima.SelectOption("Mantenimiento QA");// Mantenimiento QA
		u.highLight(By.xpath("//*[@id=\"mstr23\"]/div"));
		u.click(By.xpath("//*[@id=\"mstr23\"]/div"));// Clic Lupa Buscar
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"));
		u.sendKeys(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"), "Public Objects");// Buscar
																													// carpeta
																													// Public
																													// Objects
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));
		u.click(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));// Clic Lupa Buscar
		u.hardWait(4);
		u.highLight(By.xpath("//*[@id=\"mstr170\"]/div/div"));
		u.click(By.xpath("//*[@id=\"mstr170\"]/div/div"));// Clic Public Objects
		ima.SelectOption("Reports");// Reports
		ima.SelectOption("Control de Gastos");// Control de Gastos
		ima.SelectOption("Reportes");// Reportes
		ima.SelectOption("Gastos Mensual por Prestador de Servicios y Tienda");// Gastos Mensual por Prestador de
																				// Servicios y Tienda

	}

	public void Select_FolderCostoManoDeObraMaterial2() {
		/*
		 * Created by: Alfonso Date: 07-07-2022 Description: Funcion para Ingresar en la
		 * ruta: Mantenimiento\Public Objects\Reports\Control de Gastos\Caratulas\Costo
		 * Mano de Obra y Material".
		 */
		ima.SelectOption("Mantenimiento QA");// Mantenimiento QA
		u.highLight(By.xpath("//*[@id=\"mstr23\"]/div"));
		u.click(By.xpath("//*[@id=\"mstr23\"]/div"));// Clic Lupa Buscar
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"));
		u.sendKeys(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"), "Public Objects");// Buscar
																													// carpeta
																													// Public
																													// Objects
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));
		u.click(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));// Clic Lupa Buscar
		u.hardWait(4);
		u.highLight(By.xpath("//*[@id=\"mstr170\"]/div/div"));
		u.click(By.xpath("//*[@id=\"mstr170\"]/div/div"));// Clic Public Objects
		ima.SelectOption("Reports");// Reports
		ima.SelectOption("Control de Gastos");// Control de Gastos
		ima.SelectOption("Caratulas");// Caratulas
		ima.SelectOption("Costo Mano de Obra y Material");// Costo Mano de Obra y Material

	}

	public void generarReporteRefaccionesConsolidado(String Anio, String Mes) {
		/*
		 * Created by: Date: Description: Generar reporte de consolidado de
		 * refacciones".
		 */
		u.highLight(By.xpath("//*[@id=\"id_mstr143\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[1]/img"));
		u.click(By.xpath("//*[@id=\"id_mstr143\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[1]/img"));
		u.highLight(By.xpath("//*[@id=\"id_mstr143\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[1]/div[1]/span"));
		u.click(By.xpath("//*[@id=\"id_mstr143\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[1]/div[1]/span"));
		u.click(By.xpath("//*[@id=\"id_mstr144\"]/img"));
		//ima.capturarPrompts2("id_mstr138_txt", Tienda, "//*[@id=\"id_mstr138\"]/table/tbody/tr/td[2]/div/input",
			//	"//*[@id=\"id_mstr144\"]/img");
		ima.capturarPrompts2("id_mstr66_txt", Anio, "//*[@id=\"id_mstr66\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr70\"]/img");
		u.scroll();
		u.hardWait(3);
		ima.capturarPrompts2("id_mstr100_txt", Mes, "//*[@id=\"id_mstr100\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr104\"]/img");
		ima.clickBtnEjecutarInforme("id_mstr149");
	}

	public void generarReporteConsumoRefacciones(String Anio, String Mes) {
		ima.capturarPrompts2("id_mstr66_txt", Anio, "//*[@id=\"id_mstr66\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr70\"]/img");  

		ima.capturarPrompts2("id_mstr100_txt", Mes, "//*[@id=\"id_mstr100\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr104\"]/img");
		u.scrollToElement(By.xpath("//*[@id=\"id_mstr138_txt\"]"));
		u.highLight(By.xpath("//*[@id=\"id_mstr143\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[17]/div[1]/span"));
		u.click(By.xpath("//*[@id=\"id_mstr143\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[17]/div[1]/span"));
		u.click(By.xpath("//*[@id=\"id_mstr144\"]/img"));
		ima.clickBtnEjecutarInforme("id_mstr149");
		u.hardWait(5);
	}

	public void generarReporteRefaccionesUsadas(String Anio, String Mes) {
		u.highLight(By.xpath("//*[@id=\"id_mstr143\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[17]/div[1]/span"));
		u.click(By.xpath("//*[@id=\"id_mstr143\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[17]/div[1]/span"));
		u.click(By.xpath("//*[@id=\"id_mstr144\"]/img"));
		ima.capturarPrompts2("id_mstr66_txt", Anio, "//*[@id=\"id_mstr66\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr70\"]/img");
		u.scroll();
		ima.capturarPrompts2("id_mstr100_txt", Mes, "//*[@id=\"id_mstr100\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr104\"]/img");
		ima.clickBtnEjecutarInforme("id_mstr149");

	}

	public void generarReporteGastosMensualPrestadorServiciosTienda2(String Tienda, String Ano, String Mes) {
		/*
		 * Created by: Alfonso Date: 07-07-2022 Description: Generar ReporteGastos
		 * Mensual por Prestador de Servicios y Tienda ".
		 */

		ima.capturarPrompts2("id_mstr63_txt", Tienda, "//*[@id=\"id_mstr63\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr68\"]/img");
		ima.capturarPrompts2("id_mstr97_txt", Ano, "//*[@id=\"id_mstr97\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr101\"]/img");
		u.scroll();
		ima.capturarPrompts2("id_mstr131_txt", Mes, "//*[@id=\"id_mstr131\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr135\"]/img");
		ima.clickBtnEjecutarInforme("id_mstr144");

	}


	public String modificarOpcionesFiltro(By searchBox2, By btnSearchBox2, By opcion2, By btnAgregarOpcion2,
			String[] opciones) {
		for (String i : opciones) {
			try {
				u.clear(searchBox);
				ima.capturarPrompts(searchBox, btnSearchBox, opcion, btnAgregarOpcion, i);
			} catch (Exception ex) {
				testCase.addScreenShotCurrentStep(u, "No se encontro la opcion: " + i);
				return i;
			}
		}
		testCase.addScreenShotCurrentStep(u, "Filtro modificado");
		// Elementos para ejecutar el informe
		btnEjecutarInforme = By.id("id_mstr144");

		// Ejecutar informe
		u.highLight(btnEjecutarInforme);
		u.click(btnEjecutarInforme);
		u.waitForLoadPage();
		return "";

	}

	public boolean validarElemento(By icono) {
		boolean exist;
		try {
			u.highLight(icono);
			exist = u.isDisplayed(icono);
			testCase.addScreenShotCurrentStep("Icono encontrado");
		} catch (Exception e) {
			exist = false;
		}
		
		return exist;
	}

	public boolean seleccionarFiltro(String filtroName, String opcionName) {
		boolean exist = true;
		By filtro;
		By menu;
		try {
			filtro = By.xpath("(//span[contains(text(),'"+ filtroName +"')])[2]");
			u.highLight(filtro);
			
			menu = By.xpath("//*[@id=\"elemKey\"]");
			u.click(menu);
			
			u.highLight(By.xpath("//option[contains(text(),'"+ opcionName+"')]"));
			u.click(By.xpath("//option[contains(text(),'"+ opcionName+"')]"));
			
			testCase.addScreenShotCurrentStep("Opcion sleleccionada");
			
		} catch (Exception e) {
			exist = false;
		}
		return exist;
		
	}
	
	public void FiltroReporte(String option) {
		By optionDatos = By.xpath("//div[.='Datos']");
		By iconoFiltro = By.id("tbViewFilter");
		By Selection = By.xpath("//span/select");
		
		
		ima.clickjs(optionDatos);
		u.hardWait(2);
		ima.clickjs(iconoFiltro);
		
		u.hardWait(3);
		u.highLight(Selection);
		
		Select sel = new Select(u.getDriver().findElement(Selection));
		sel.selectByVisibleText(option);
		
	}
	
	public void SeleccionaTodosReporte() {
		By agregaTodos = By.xpath("//*[@id=\"addAllUnitsButton\"]/img");
		By buttonAplicar = By.xpath("//*[@id=\"acceptButton\"]");
		
		u.click(agregaTodos);
		u.highLight(buttonAplicar);
		testCase.addScreenShotCurrentStep("Reporte");
		u.click(buttonAplicar);
	}
	
	public void seleccionaElementosReporte(String elemento) {
		By busqueda = By.xpath("//*[@id=\"namePattern\"]");
		
		u.highLight(busqueda);
		u.sendKeys(busqueda, elemento);
		u.getDriver().findElement(busqueda).sendKeys(Keys.ENTER);
		u.hardWait(5);
		
		SeleccionaTodosReporte();
	}

	public String obtenerDato(By element) {
		
		u.highLight(element);
		String texto = u.getText(element);
		System.out.println(texto);
		
		return texto;
	}


	public void Select_GraficaCostosCategoríasAcumulado() {
		/*
		 * Created by: Date: 08-07-2022 Description: Funcion para Ingresar en la ruta:
		 * Mantenimiento\Public Objects\Reports\Control de Gastos\Reportes\Grafica
		 * Costos x Categorías Acumulado".
		 */
		ima.SelectOption("Mantenimiento QA");// Mantenimiento QA
		u.highLight(By.xpath("//*[@id=\"mstr23\"]/div"));
		u.click(By.xpath("//*[@id=\"mstr23\"]/div"));// Clic Lupa Buscar
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"));
		u.sendKeys(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"), "Public Objects");// Buscar
																													// carpeta
																													// Public
																													// Objects
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));
		u.click(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));// Clic Lupa Buscar
		u.hardWait(4);
		u.highLight(By.xpath("//*[@id=\"mstr170\"]/div/div"));
		u.click(By.xpath("//*[@id=\"mstr170\"]/div/div"));// Clic Public Objects
		ima.SelectOption("Reports");// Reports
		ima.SelectOption("Control de Gastos");// Control de Gastos
		ima.SelectOption("Reportes");// Reportes
		ima.SelectOption("Grafica Costos x Categorías Acumulado");// Grafica Costos x Categorías Acumulado

	}

	public void generarReporteGraficaCostosCategoríasAcumulado(String Tienda, String Ano, String Mes) {
		/*
		 * Created by: Date: 07-07-2022 Description: Generar Reporte Grafica Costos x
		 * Categorías Acumulado".
		 */

		ima.capturarPrompts2("id_mstr63_txt", Tienda, "//*[@id=\"id_mstr63\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr68\"]/img");
		ima.capturarPrompts2("id_mstr97_txt", Ano, "//*[@id=\"id_mstr97\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr101\"]/img");
		u.scroll();
		ima.capturarPrompts2("id_mstr131_txt", Mes, "//*[@id=\"id_mstr131\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr135\"]/img");
		ima.clickBtnEjecutarInforme("id_mstr144");

	}

	public void Select_GraficaCostosCategoríasMensual() {
		/*
		 * Created by: Date: 08-07-2022 Description: Funcion para Ingresar en la ruta:
		 * Mantenimiento\Public Objects\Reports\Control de Gastos\Reportes\Grafica
		 * Costos x Categorías Mensual".
		 */
		ima.SelectOption("Mantenimiento QA");// Mantenimiento QA
		u.highLight(By.xpath("//*[@id=\"mstr23\"]/div"));
		u.click(By.xpath("//*[@id=\"mstr23\"]/div"));// Clic Lupa Buscar
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"));
		u.sendKeys(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[1]/div[1]/input"), "Public Objects");// Buscar
																													// carpeta
																													// Public
																													// Objects
		u.highLight(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));
		u.click(By.xpath("//*[@id=\"mstr24\"]/div[1]/div[3]/div/div[2]/div[2]/div[3]"));// Clic Lupa Buscar
		u.hardWait(4);
		u.highLight(By.xpath("//*[@id=\"mstr170\"]/div/div"));
		u.click(By.xpath("//*[@id=\"mstr170\"]/div/div"));// Clic Public Objects
		ima.SelectOption("Reports");// Reports
		ima.SelectOption("Control de Gastos");// Control de Gastos
		ima.SelectOption("Reportes");// Reportes
		ima.SelectOption("Grafica Costos x Categorías Mensual");// Grafica Costos x Categorías Mensual

	}

	public void generarReporteGraficaCostosCategoríasMensual(String Tienda, String Ano, String Mes) {
		/*
		 * Created by: Date: 07-07-2022 Description: Generar Reporte Grafica Costos x
		 * Categorías Mensual".
		 */
		ima.capturarPrompts2("id_mstr63_txt", Tienda, "//*[@id=\"id_mstr63\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr68\"]/img");
		ima.capturarPrompts2("id_mstr97_txt", Ano, "//*[@id=\"id_mstr97\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr101\"]/img");
		u.scroll();
		ima.capturarPrompts2("id_mstr131_txt", Mes, "//*[@id=\"id_mstr131\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr135\"]/img");
		ima.clickBtnEjecutarInforme("id_mstr144");

	}

	public void validarElementosreporte_OrdenesDeTrabajoMensuales() {
		/*
		 * Created by: Andrea Cano Date: 20-08-2022 Description: Validar elementos
		 * prompts
		 */
		u.highLight(By.xpath("//*[@id=\"id_mstr39\"]/table/tbody/tr[1]/td[2]/span[1]"));
		u.highLight(By.xpath("//*[@id=\"id_mstr39\"]/table/tbody/tr[2]/td[2]"));
		u.highLight(By.xpath("//*[@id=\"id_mstr39\"]/table/tbody/tr[3]/td[2]/span[1]"));

		testCase.addScreenShotCurrentStep(u, "Validacion elementos");

		u.unhighLight(By.xpath("//*[@id=\"id_mstr39\"]/table/tbody/tr[1]/td[2]/span[1]"));
		u.unhighLight(By.xpath("//*[@id=\"id_mstr39\"]/table/tbody/tr[2]/td[2]"));
		u.unhighLight(By.xpath("//*[@id=\"id_mstr39\"]/table/tbody/tr[3]/td[2]/span[1]"));
	}

	public void capturarPromptsCalificacionesBimImagen() { // este metodo sirve en varios reportes
		/*
		 * Created by: Andrea Cano Date: 20-08-2022 Description: Capturar prompts
		 * reporte
		 */
		u.waitForLoadPage();
		u.hardWait(2);
		ima.capturarPrompts("id_mstr66_txt", "2022", "//*[@id=\"id_mstr68ListContainer\"]/div/div",
				"//*[@id=\"id_mstr66\"]/table/tbody/tr/td[2]/div/input", "//*[@id=\"id_mstr70\"]/img");
		u.click(By.xpath("//*[@id=\"id_mstr39\"]/table/tbody/tr[2]/td[2]"));
		ima.capturarPrompts("id_mstr100_txt", "Mayo", "//*[@id=\"id_mstr102ListContainer\"]/div/div",
				"//*[@id=\"id_mstr100\"]/table/tbody/tr/td[2]/div/input", "//*[@id=\"id_mstr104\"]/img");
		u.click(By.xpath("//*[@id=\"id_mstr39\"]/table/tbody/tr[3]/td[2]/span[1]"));
		ima.seleccionarPlazaReporteRankeoPorGastos(
				"//*[@id=\"id_mstr143\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[1]/img",
				"//*[@id=\"id_mstr143\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[1]/span",
				"id_mstr138_txt", "Tlaxcala", "//*[@id=\"id_mstr138\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr164\"]/div/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]/div/div[1]/span",
				"//*[@id=\"id_mstr144\"]/img"); // 10CUE
		ima.clickBtnEjecutarInforme("id_mstr149");
	}

	public void Select_FolderCalificacionesMensualesImagenTienda() {
		/*
		 * Created by: Andrea Cano Date: 20-08-2022 Description: Seleccionar reporte
		 */
		ima.SelectOption("Mantenimiento QA");
		ima.SelectOption("Informes compartidos");
		ima.SelectOption("Imagen Tiendas");
		ima.SelectOption("Calificaciones Mensuales Imagen Tienda");
	}

	public void exportarExcelGraficaImagenPorPlaza() {
		/*
		 * Created by: Andrea Cano Date: 20-08-2022 Description: opcion exportar
		 * diferente a los demas reportes ya que es diferente xpath
		 */
		System.out.println("descargar excel");
		u.waitForLoadPage();
		ima.SelectOptionByXpath("//*[@id=\"ribbonToolbarTabsListContainer\"]/div[1]/table/tbody/tr/td[3]");
		ima.SelectOptionByXpath("//*[@id=\"RptHomeMenu_\"]/tbody/tr/td/div/div[18]/a/div[2]");
		ima.SelectOptionByXpath(
				"//*[@id=\"RptHomeExportMenu_WEB-INFxmllayoutsblocksHomeExportMenuLayoutxml\"]/tbody/tr/td/div/div[13]/a/div[2]/span[1]");
		u.waitForLoadPage();
		u.hardWait(3);
	}

	public void abrirReporteExcel(String nameReport) throws AWTException {
		u.hardWait(6);

		String path = GlobalVariables.REPORTE_PATH + nameReport;

		String pathRename = GlobalVariables.REPORTE_PATH + Math.random() * 10 + nameReport.replace(" ", "_");

		if (FileUtil.fileExists(path)) {
			File oldfile = new File(path);
			System.out.println(oldfile);

			File newfile = new File(pathRename);
			System.out.println(newfile);

			if (oldfile.renameTo(newfile)) {
				System.out.println("archivo renombrado");
				
				testCase.setAditionalAttachment(GlobalVariables.REPORTE_PATH+pathRename);
				Screen s = new Screen();
				try {
					FileUtil.openFile(pathRename);

					Robot r = new Robot();
					r.delay(10000);

					s.click(GlobalVariables.IMGS_PATH + "OpcYes.PNG");
					r.delay(20000);
					//u.hardWait(10);
					try {
					s.click(GlobalVariables.IMGS_PATH + "insertExcel.PNG");
					System.out.println("Si se encontro la imagen insertExcel");
					} catch (FindFailed e) {
						System.out.println("No se encontro la imagen insertExcel, buscando 2 opcion");
						s.click(GlobalVariables.IMGS_PATH + "insertExcel2.PNG");
					}
					
					testCase.addScreenShotCurrentStep(nameReport);
				} catch (FindFailed e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("error al renombrar archivo, por lo tanto no se pudo abrir");
			}
		}
	}

	//Metodo para comparar un registo especifico de una consulta con el de otra
		public void compararCampoTablas(SQLResult connectionResult, String columna, int row, 
				SQLResult connectionResult2, String columna2, int row2) {
			String reg1= connectionResult.getData(row, columna);
			String reg2= connectionResult2.getData(row2, columna2);
			
			if(reg1.equals(reg2)) {
				System.out.println("Los registros son iguales "+ reg1 +"  "+ reg2);
				testCase.addTextEvidenceCurrentStep("Los registros son iguales "+ reg1 +"  "+ reg2);
			} else {
				System.out.println("Los registros son diferentes "+ reg1 +"  "+ reg2);
				testCase.addTextEvidenceCurrentStep("Los registros son diferentes "+ reg1 +"  "+ reg2);
			}
		}
		
	public void Select_FolderTelefoniaServicios() {
		ima.SelectOption("Informaci�n Comercial QA");
		ima.SelectOptionByXpath("//*[@id=\"FolderIcons\"]/tbody/tr[5]/td[1]/table/tbody/tr/td[2]/span/a"); // informacion
																											// comercial
		u.hardWait(3);
		u.scrollToElement(By.xpath("/html/body/div[4]/table/tbody/tr/td[2]/div[2]/div[1]/table/tbody/tr[33]/td[2]"));
		ima.SelectOptionByXpath("/html/body/div[4]/table/tbody/tr/td[2]/div[2]/div[1]/table/tbody/tr[33]/td[2]"); // telefonia y servicios
		u.click(By.xpath("//*[@id=\"FolderList\"]/tbody/tr[33]/td[2]/span[1]/a"));
		u.hardWait(2);
		ima.SelectOptionByXpath("//*[@id=\"FolderList\"]/tbody/tr[1]/td[2]/span[1]/a"); // resultados servicios
		u.hardWait(3);
		ima.SelectOptionByXpath("/html/body/div[4]/table/tbody/tr/td[2]/div[2]/div[1]/table/tbody/tr[1]/td[2]/span[1]"); // nacional
		u.hardWait(3);           
		ima.SelectOptionByXpath("/html/body/div[4]/table/tbody/tr/td[2]/div[2]/div[1]/table/tbody/tr[7]/td[2]/span[1]"); // remesas
		u.hardWait(3);		
	}
	public void Select_ReturnRemesas() {
		ima.SelectOptionByXpath("/html/body/table/tbody/tr/td/div[4]/table/tbody/tr/td[1]/div/div/div[2]/div[1]/div[1]/span[1]"); // remesas
		u.hardWait(2);		
	}
	public void generarReporteRemesasInterMes() {
		u.highLight(By.xpath("//*[@id=\"id_mstr56ListContainer\"]/div[10]/div"));
		u.click(By.xpath("//*[@id=\"id_mstr56ListContainer\"]/div[10]/div"));
		u.click(By.xpath("//*[@id=\"id_mstr58\"]/img"));
		u.click(By.xpath("//*[@id=\"id_mstr100\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[1]/img"));
		u.click(By.xpath("//*[@id=\"id_mstr100\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[1]/img"));
		u.highLight(By.xpath("//*[@id=\"id_mstr115\"]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[7]/div[1]/span"));
		u.click(By.xpath("//*[@id=\"id_mstr115\"]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[7]/div[1]/span"));
		u.click(By.xpath("//*[@id=\"id_mstr101\"]/img"));
		u.highLight(By.xpath("//*[@id=\"id_mstr115\"]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[8]/div[1]/span"));
		u.click(By.xpath("//*[@id=\"id_mstr115\"]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[8]/div[1]/span"));
		u.click(By.xpath("//*[@id=\"id_mstr101\"]/img"));
		u.highLight(By.xpath("//*[@id=\"id_mstr115\"]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[9]/div[1]/span"));
		u.click(By.xpath("//*[@id=\"id_mstr115\"]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[9]/div[1]/span"));
		u.click(By.xpath("//*[@id=\"id_mstr101\"]/img"));
		u.highLight(By.id("id_mstr108"));
		testCase.addScreenShotCurrentStep(u, "filtrosReporte");
		ima.clickBtnEjecutarInforme("id_mstr108");
		u.hardWait(60);
	}
	public void generarReporteRemesasInterSemana(String semana) {
		u.highLight(By.xpath("//*[@id=\"id_mstr53ListContainer\"]/div[9]/div"));//tienda
		u.click(By.xpath("//*[@id=\"id_mstr53ListContainer\"]/div[9]/div"));
		u.click(By.xpath("//*[@id=\"id_mstr55\"]/img"));//agregar
		ima.capturarPrompts2("id_mstr86_txt", semana, "//*[@id=\"id_mstr86\"]/table/tbody/tr/td[2]/div/input",
				"//*[@id=\"id_mstr90\"]/img");
		u.highLight(By.id("id_mstr99"));
		testCase.addScreenShotCurrentStep(u, "filtrosReporte");
		ima.clickBtnEjecutarInforme("id_mstr99");
		u.hardWait(60);
	}
	public void validarExistaFiltro(By xpath) {
		u.takeScreenShotHighLight("filtros", xpath);
		testCase.addScreenShotCurrentStep(u, "filtros");
		u.hardWait(5);
	}
	public void validarRepRemesas() {
		ima.validarColumnas(By.xpath("//*[@id=\"reportDetails_ReportDetailsPanelStyle\"]/div[1]/table/tbody/tr[1]/td/div/span[1]"), "DETALLES DE INFORME");
		ima.validarColumnas(By.xpath("//*[@id=\"promptDetails_PromptDetailsStyle\"]/div[1]/table/tbody/tr[1]/td/div/span[1]"), "DETALLES DE SELECCI�N DIN�MICA");
		u.highLight(By.xpath("//*[@id=\"reportDetails_ReportDetailsPanelStyle\"]/div[1]/table/tbody/tr[1]/td/div/span[1]"));
		u.highLight(By.xpath("//*[@id=\"promptDetails_PromptDetailsStyle\"]/div[1]/table/tbody/tr[1]/td/div/span[1]"));
		testCase.addScreenShotCurrentStep(u, "Reporte");
	}
	public void ProveedorRemesas(String opcProveedor) {
		By proveedor= By.cssSelector("select#elemKey");//By.xpath("select//*[@id=\"elemKey\"]");
		u.highLight(proveedor);
		Select proveedorRemesas= new Select(u.getDriver().findElement(proveedor));
		proveedorRemesas.selectByVisibleText(opcProveedor);
		testCase.addScreenShotCurrentStep(u, "Proveedor");
	}
	public void validarColumnaRep() {
		ima.validarColumnas(By.id("UniqueReportID_1_1_1"), "Zonas");
		ima.validarColumnas(By.id("UniqueReportID_1_2_1"), "Plaza.");
		//ima.validarColumnas(By.id("UniqueReportID_2_1_1"), "Mes");
		ima.validarColumnas(By.id("UniqueReportID_2_2_1"), "Indicadores");
		testCase.addTextEvidenceCurrentStep("Columas de reporte Validas");
	}
}

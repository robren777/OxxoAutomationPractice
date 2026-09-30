package WEB.simoti_Chile.om;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.sikuli.script.FindFailed;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class IndicadoresPorTotales_SimotiChile {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	MenuPrincipal_SimotiChile mp;
	
	/*
	 * Localizadores pantalla Indicadores por totales
	 ********************************************************************************************/
	
	By xmlTitulo = By.xpath("//label[contains(text(), 'Indicadores por Totales')]");
	By xmlPlazaTd = By.xpath("//*[@id=\"j_idt163:panelFiltros\"]/tbody/tr/td[2]");
	By xmlPlazaSeleccionada = By.xpath("//*[@id=\"j_idt163:cmbPlaza_label\"]");
	By xmlPlazas = By.xpath("//*[@id=\"j_idt163:cmbPlaza_items\"]/li");
	By xmlMesTd = By.xpath("//*[@id=\"j_idt163:panelFiltros\"]/tbody/tr/td[4]");
	By xmlMesSeleccionado = By.xpath("//*[@id=\"j_idt163:cmbMes_label\"]");
	By xmlMeses = By.xpath("//*[@id=\"j_idt163:cmbMes_items\"]/li");
	By xmlAnioTd = By.xpath("//*[@id=\"j_idt163:panelFiltros\"]/tbody/tr/td[6]");
	By xmlAnioSeleccionado = By.xpath("//*[@id=\"j_idt163:cmbannio_label\"]");
	By xmlAnios = By.xpath("//*[@id=\"j_idt163:cmbannio_items\"]/li");
	
	By xmlConsultarBtn = By.xpath("//a[contains(text(), 'Consultar')]");
	By xmlExportarBtn = By.xpath("//a[contains(text(), 'Exportar')]");
	
	By xmlSeccionResultados = By.xpath("//fieldset[2]");
	By xmlSinResultados = By.xpath("//fieldset[2]/div/span/div/div/div/table/tbody/tr/td");
	String xmlEncabezadosTabla = "//fieldset[2]/div/span/div/div/div/table/thead/tr/th[%s]/span";

	String xmlEncabezadosMayus = "//thead[@id='j_idt163:dtColors_head']//tr[@role='row']";

	By xmlSinResultados2 = By.xpath("//tbody[@id='j_idt163:dtColors_data']/tr/td[contains(text(),'Sin resultados')]");
	//String xmlEncabezadosTablaIPT = "//*[@id=\"j_idt163:dtColors\"]/div[1]/table";
	//"//*[@id=\"j_idt163:dtColors\"]/div[1]"
	By xmlSeccionResultadosIPT = By.xpath("//*[@id=\"j_idt163:panelTables\"]");
	
	
	By xmlInicio = By.xpath("//*[@id=\"j_idt163:dtColors_paginator_bottom\"]/span[2]");
	By xmlAdelante = By.xpath("//*[@id=\"j_idt163:dtColors_paginator_bottom\"]/span[3]");
	By xmlAtras = By.xpath("//*[@id=\"j_idt163:dtColors_paginator_bottom\"]/span[5]");
	By xmlFin = By.xpath("//*[@id=\"j_idt163:dtColors_paginator_bottom\"]/span[6]");
	
	By xmlMensaje = By.xpath("//div[@id='j_idt163:msgs_container']/div/div/div[2]/span");

	By xmlNumResultados = By.xpath("//span[@class='ui-paginator-current']");
	String xmlDatosTabla = "//fieldset[2]/div/span/div/div/div/table/tbody/tr[%s]";
	
	//String encabezadoTabla = "//fieldset[2]/div/span/div/div/div/table/thead/tr/th['%s']";
	String encabezadoTabla = "//fieldset[2]/div/span/div/div/div/table/tbody/tr/td[%s]";
	
	By numeroFilas = By.xpath("//fieldset[2]/div/span/div/div/div/table/tbody/tr/td");
	
	By numeroColumnas = By.xpath("//fieldset[2]/div/span/div/div/div/table/tbody/tr");
	
	String colores = "//fieldset[2]/div/span/div/div/div/table/tbody/tr[%s]/td[2]/img";
	//By colores = By.xpath("//fieldset[2]/div/span/div/div/div/table/tbody/tr[1]/td[2]/img");

	
	/*
	 * Constructor
	 ********************************************************************************************/
	public IndicadoresPorTotales_SimotiChile(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
		this.mp = new MenuPrincipal_SimotiChile(u, testCase);
	}
	
	
	/*
	 * Metodos pantalla Indicadores por totales
	 ********************************************************************************************/
	
	/**
	 * Metodo para validar que se visualicen los elementos de la pantalla
	 * @author Maria Dueñas
	 * @return Retorna true si se visualizan los elementos 
	 */
	public Boolean validarPantalla() {
		Boolean exist = false;
		
		try {
			if(me.existElement(xmlTitulo) && me.existElement(xmlAnioTd) && me.existElement(xmlConsultarBtn) &&
					me.existElement(xmlExportarBtn) && me.existElement(xmlMesTd) && me.existElement(xmlPlazaTd) &&
					me.existElement(xmlSeccionResultados) && u.getText(xmlSinResultados).trim().equals("Sin resultados")) {
			
				exist = true;
				
				u.highLight(xmlAnioTd);
				u.highLight(xmlConsultarBtn);
				u.highLight(xmlExportarBtn);
				u.highLight(xmlMesTd);
				u.highLight(xmlPlazaTd);
				u.highLight(xmlSeccionResultados);
				u.highLight(xmlSinResultados);
				u.highLight(xmlTitulo);
				
				testCase.addScreenShotCurrentStep(u, "validarPantalla");
				
				u.unhighLight(xmlAnioTd);
				u.unhighLight(xmlConsultarBtn);
				u.unhighLight(xmlExportarBtn);
				u.unhighLight(xmlMesTd);
				u.unhighLight(xmlPlazaTd);
				u.unhighLight(xmlSeccionResultados);
				u.unhighLight(xmlSinResultados);
				u.unhighLight(xmlTitulo);
			}
		}catch(Exception e) {
			testCase.addScreenShotCurrentStep(u, "Error");
		}
		
		return exist;
	}
	
	/**
	 * Metodo para validar los encabezados de la tabla
	 * @return Retorna true si son correctos
	 * @author Maria Dueñas
	 */
	public Boolean validarEncabezados() {
		Boolean correct = false;
		
		String no = u.getText(By.xpath(String.format(xmlEncabezadosTabla, "1"))).trim();
		String color = u.getText(By.xpath(String.format(xmlEncabezadosTabla, "2"))).trim();
		System.out.println(no);
		System.out.println(color);
		Boolean dias = false;
		Integer dia = 1;
		for (int i = 3; i <= 33; i++) {
			Integer d =Integer.valueOf(u.getText(By.xpath(String.format(xmlEncabezadosTabla, Integer.toString(i)))).trim());
			
			if(dia != d) {
				dias = false;
				break;
			}else {
				dias = true;
				dia ++;
			}
			
			
		}
		
		if(no.equals("No.") && color.equals("Color") && dias) {
			correct = true;
		}
		
		testCase.addScreenShotCurrentStep(u, "validarEncabezado");
		
		return correct;
	}
	
	/**
	 * Metodo para validar botones de paginado
	 * @return Retorna true si estan los botones
	 * @author Maria Dueñas
	 */
	public Boolean validarBotonespaginado() {
		Boolean exist = false;
		
		if(me.existElement(xmlInicio) && me.existElement(xmlAdelante) && me.existElement(xmlAtras) && me.existElement(xmlFin)) {
			exist = true;
			u.highLight(xmlInicio);
			u.highLight(xmlAdelante);
			u.highLight(xmlAtras);
			u.highLight(xmlFin);
			
			testCase.addScreenShotCurrentStep(u, "validarBotonespaginado");
			
			u.unhighLight(xmlInicio);
			u.unhighLight(xmlAdelante);
			u.unhighLight(xmlAtras);
			u.unhighLight(xmlFin);
			
		}
		
		return exist;
	}
	
	/**
	 * Metodo para validar que exista el filtro Plaza
	 * @author Andrea Contreras
	 * @param mes
	 * @return N/A
	 */
	public Boolean validarFiltroPlaza() {
        Boolean existe = false;        
        u.highLight(xmlPlazaSeleccionada);
        existe = me.existElement(xmlPlazaSeleccionada);
        testCase.addScreenShotCurrentStep(u, "Plaza");
        u.unhighLight(xmlPlazaSeleccionada);       
        return existe;
    }
	
	/**
	 * Metodo para validar que se muestre el mes actual y se enlisten todos los meses del año
	 * @return Retorna true si se muestran correctamente
	 * @author Maria Dueñas
	 */
	public Boolean validarFiltroMes() {
		Boolean correct = false;
		
		int annio;
		String mes;
		String MES[] = new String[] {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
		Calendar c1 = Calendar.getInstance();
		annio = c1.get(Calendar.YEAR);
		mes = MES[c1.get(Calendar.MONTH)];

		String mes_Sistema = u.getText(xmlMesSeleccionado).trim();
		u.highLight(xmlMesSeleccionado);
		testCase.addScreenShotCurrentStep(u, "Mes");
		u.unhighLight(xmlMesSeleccionado);
		u.click(xmlMesSeleccionado);
		List<WebElement> allOptions = u.getDriver().findElements(xmlMeses);
		
		int cont = 0;
		String meses[] = new String[12]; 
		for(WebElement option:allOptions){
			

			if(!option.getText().equals("Seleccione una opción")){
			  meses[cont] = option.getText();
			  cont ++;
			  //System.out.print(option.getText() + "\n");
			}
		}
		
		if (mes_Sistema.equals(mes) == true && Arrays.equals(meses, MES)) {
			correct = true;
		}
		
		u.click(xmlMesSeleccionado);
		
		return correct;
	}
	
	/**
	 * Metodo para validar que se muestre el año actual y se enlisten todos los años de 2010 al actual
	 * @return Retorna true si se muestran correctamente
	 * @author Maria Dueñas
	 */
	public Boolean validarFiltroAnio() {
		Boolean correct = false;
		
		int annio;
		Calendar c1 = Calendar.getInstance();
		annio = c1.get(Calendar.YEAR);
		
		int cont = 0;
		int ANIO[] = new int[annio-2009]; 
		for(int i = 2010; i <= annio; i++) {
			ANIO[cont] = i;
			cont ++;
		}
		
		int anio_Sistema = Integer.parseInt(u.getText(xmlAnioSeleccionado).trim());
		u.highLight(xmlAnioSeleccionado);
		testCase.addScreenShotCurrentStep(u, "Anio");
		u.unhighLight(xmlAnioSeleccionado);
		u.click(xmlAnioSeleccionado);
		
		List<WebElement> allOptions = u.getDriver().findElements(xmlAnios);
		
		cont = 0;
		int anios[] = new int[annio-2009]; 
		
		for(WebElement option:allOptions){

			if(!option.getText().equals("Seleccione una opción")) {
			  anios[cont] = Integer.parseInt(option.getText());
			  cont ++;
			}
		}
		
		if(anio_Sistema == annio && Arrays.equals(ANIO, anios)) {
			correct = true;
		}
		
		u.click(xmlAnioSeleccionado);
		
		return correct;
	}
	
	/**
	 * Metodo para seleccionar un mes mayor al actual
	 * @return Retorna true si se selecciono correctamente el mes o false en caso contrario
	 * @author Maria Dueñas
	 */
	public Boolean selectMesMayor() {
		Boolean correct = false;
		
		int annio;
		String mes;
		String MES[] = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
		Calendar c1 = Calendar.getInstance();
		annio = c1.get(Calendar.YEAR);
		
		if (c1.get(Calendar.MONTH) != 11) {
			mes = MES[c1.get(Calendar.MONTH) + 1];
		}else {
			mes = MES[0];
		}
		
		u.click(xmlMesSeleccionado);
		
		List<WebElement> allOptions = u.getDriver().findElements(xmlMeses); 
		
		for(WebElement option:allOptions){
			if(option.getText().trim().equals(mes)) {
			  option.click();
			  correct = true;
			  break;
			}
		}
		
		u.highLight(xmlMesSeleccionado);
		testCase.addScreenShotCurrentStep(u, "selectMes");
		u.unhighLight(xmlMesSeleccionado);
		
		return correct;
	}
	
	/**
	 * Metodo para seleccionar una plaza
	 * @param opcion
	 * @return Retorna true si se selecciono correctamente el mes o false en caso contrario
	 * @author Andrea Contreras
	 */
	public Boolean selectPlaza(String opcion) {
		Boolean correct = false;
		
		u.click(xmlPlazaSeleccionada);
		
		List<WebElement> allOptions = u.getDriver().findElements(xmlPlazas); 
		
		for(WebElement option:allOptions){
			if(option.getText().trim().equals(opcion)) {
			  option.click();
			  correct = true;
			  break;
			}
		}
		
		u.highLight(xmlPlazaSeleccionada);
		testCase.addScreenShotCurrentStep(u, "selectPlaza");
		u.unhighLight(xmlPlazaSeleccionada);
		
		return correct;
	}
	
	/**
	 * Metodo para seleccionar un año
	 * @param opcion
	 * @return Retorna true si se selecciono correctamente el año proporcionado
	 * @author Maria Dueñas
	 */
	public Boolean selectAnio(String opcion) {
		Boolean correct = false;
		
		u.click(xmlAnioSeleccionado);
		List<WebElement> allOptions = u.getDriver().findElements(xmlAnios);
		
		for(WebElement option:allOptions){
			if(option.getText().trim().equals(opcion)) {
				option.click();
				correct = true;
				break;
			}
		}
		
		u.highLight(xmlAnioSeleccionado);
		testCase.addScreenShotCurrentStep(u, "AnioSeleccionado");
		u.unhighLight(xmlAnioSeleccionado);
		
		return correct;
	}
	
	/**
	 * Metodo para seleccionar un mes
	 * @param opcion
	 * @return Retorna true si se selecciono correctamente el mes o false en caso contrario
	 * @author Maria Dueñas
	 */
	public Boolean selectMes(String opcion) {
		Boolean correct = false;
		
		u.click(xmlMesSeleccionado);
		
		me.explicitWaitToBeClickable(xmlMeses, Duration.ofSeconds(10));
		List<WebElement> allOptions = u.getDriver().findElements(xmlMeses); 
		
		for(WebElement option:allOptions){
			//System.out.print(option.getText().trim());
			if((option.getText().trim()).equals(opcion)) {
			  option.click();
			  correct = true;
			  break;
			}
		}
		
		u.highLight(xmlMesSeleccionado);
		testCase.addScreenShotCurrentStep(u, "selectMes");
		u.unhighLight(xmlMesSeleccionado);
		
		return correct;
	}
	
	/**
	 * Metodo para dar click en el boton Consultar
	 * @author Maria Dueñas
	 */
	public void clickConsultarBtn() {
		u.highLight(xmlConsultarBtn);
		testCase.addScreenShotCurrentStep(u, "Consultar");
		u.unhighLight(xmlConsultarBtn);
		u.click(xmlConsultarBtn);
		u.hardWait(5);
		testCase.addScreenShotCurrentStep(u, "clickConsultar");
	}
	
	/**
	 * Metodo para dar click en el boton Exportar
	 * @author Maria Dueñas
	 */
	public void clickExportarBtn() {
		u.highLight(xmlExportarBtn);
		testCase.addScreenShotCurrentStep(u, "Exportar");
		u.unhighLight(xmlExportarBtn);
		u.click(xmlExportarBtn);
		testCase.addScreenShotCurrentStep(u, "clickExportar");
	}
	
	/**
	 * Metodo para validar que se muestren resultados en la tabla
	 * @return Retorna true si se muestra el mensaje o false en caso contrario
	 * @author Andrea Contreras
	 */
	public Boolean existenResultados() {
		
		Boolean exist = false;
		
		me.scrollToElement(xmlSeccionResultados);
		u.highLight(xmlSeccionResultados);
		
		if(me.existElement(xmlSinResultados2)==false) {
						
			exist = true;
			
		}
		
		testCase.addScreenShotCurrentStep(u, "validarResultados");
		
		return exist;
	}
	
	/**
	 * Metodo para validar que no aparezca un mensaje
	 * @return Retorna true si se muestra el mensaje o false en caso contrario
	 * @author Andrea Contreras
	 */
	public Boolean existeMensaje() {
		Boolean exist = false;
		
		if(u.isDisplayed(xmlMensaje)) {
			    me.scrollToElement(xmlMensaje);
				exist = true;
		}

		testCase.addScreenShotCurrentStep(u, "validarMensaje");
		
		return exist;
	}
	
	/**
	 * Metodo para validar que aparezca el mensaje "No hay información para exportar."
	 * @return Retorna true si se muestra el mensaje o false en caso contrario
	 * @author Maria Dueñas
	 */
	public Boolean validarMensajeNoHayInformacion() {
		Boolean exist = false;
		String mensaje = "";
		
		//if(u.getDriver().findElement(xmlMensaje).isDisplayed()) {
		if(u.isDisplayed(xmlMensaje)) {
			me.scrollToElement(xmlMensaje);
			mensaje = u.getText(xmlMensaje).trim();
			u.highLight(xmlMensaje);
			testCase.addScreenShotCurrentStep(u, "validarMensaje");
			u.unhighLight(xmlMensaje);
			
			if (mensaje.equals("No hay informaci�n para exportar.")) {
				exist = true;
			}
			
		} else {
			testCase.addScreenShotCurrentStep(u, "validarMensaje");
		}
		
		return exist;
	}
	
	
	/**
	 * Metodo para validar que aparezca el mensaje "Ha ocurrido un error al consultar la información del reporte, favor de reportar al área de soporte."
	 * @return Retorna true si se muestra el mensaje o false en caso contrario
	 * @author Andrea Contreras
	 */
	public Boolean validarMensajeError() {
		Boolean exist = false;
		String mensaje = "";
		
		if(u.isDisplayed(xmlMensaje)) {
			me.scrollToElement(xmlMensaje);
			mensaje = u.getText(xmlMensaje).trim();
			u.highLight(xmlMensaje);
			testCase.addScreenShotCurrentStep(u, "validarMensaje");
			u.unhighLight(xmlMensaje);
			

			if (mensaje.equals("Ha ocurrido un error al consultar la información del reporte, favor de reportar al área de soporte")) {

				exist = true;
			}
			
		} else {
			testCase.addScreenShotCurrentStep(u, "validarMensaje");
		}
		
		return exist;

	}
	
	/**
	 * Método para seleccionar dato celda
	 * @return Retorna contenido de celda
	 * @author Bernardo Moya 
	 */
	
	public String datoCelda(Sheet sheet, int r, int c) {
		DataFormatter formatter = new DataFormatter();
        
        Cell celda = sheet.getRow(r).getCell(c);
        String contenidoCelda = formatter.formatCellValue(celda);
        //System.out.println("celda: -" + contenidoCelda + "-");
        
        return contenidoCelda;
	}
	
	/**
	 * Método para eliminar los datos vacíos de las celdas
	 * @return 
	 * @author Bernardo Moya 
	 */
	public void eliminarDatosVaciosExcel(List<String> listaExcel) {		
		System.out.println("Lista Excel antes de ser corregida: "+listaExcel);
		listaExcel.removeAll(Arrays.asList("",null));
		System.out.println("Lista Excel: "+listaExcel);
	}
	
	/**
	 * Método para validar color de la imagen (círculo) de la tabla según su ID
	 * @return 
	 * @author Bernardo Moya 
	 */	
	public void validarColor(List<String> listaWeb) {
		
		listaWeb.removeAll(Arrays.asList("",null));
		//System.out.println("Lista Web elemento 1: "+listaWeb.get(1));
		
		if (listaWeb.contains("j_idt163:dtColors:0:j_idt211")) {
			listaWeb.remove(1);
			listaWeb.add(1,"black");
		}
		else if(listaWeb.contains("j_idt151:dtColors:0:j_idt195")) {
			listaWeb.remove(1);
			listaWeb.add(1,"red");
		}
		
		else if(listaWeb.contains("j_idt151:dtColors:2:j_idt198")) {
			listaWeb.remove(1);
			listaWeb.add(1,"white");
		}
		
		else if(listaWeb.contains("j_idt151:dtColors:3:j_idt197")) {
			listaWeb.remove(1);
			listaWeb.add(1,"yellow");
		}
					
		System.out.println("Color corregido: "+listaWeb);
	}
	
	/**
	 * Método para validar datos de las celdas de Excel y compararlo con los datos de la tabla indicadores por totales
	 * @return retorna true o false según la validación si fue exitosa o no
	 * @author Bernardo Moya 
	 */
	 public Boolean validarDatoss(String filename, String number) throws IOException {
			Boolean correct = false;
			String formato = "dd-MM-yyyy";
			SimpleDateFormat formatFecha = new SimpleDateFormat(formato);
	        LocalDateTime datetime = LocalDateTime.now();

	        String nombreArchivo = filename;
	        String fecha = DateTimeFormatter.ofPattern(formato).format(datetime);
	        String fechaSinGuion = fecha.replace("-", "");
	        System.out.println(fechaSinGuion);	        
		    String pathYourSystem = System.getProperty("user.home") + "\\Downloads\\"+ nombreArchivo + fechaSinGuion + number +".xlsx";
		    File fichero = new File(pathYourSystem);		    
		    System.out.println("Path your sistem: "+pathYourSystem);

	        
	        FileInputStream inputStream = new FileInputStream(new File(pathYourSystem));
	        Workbook workbook = new XSSFWorkbook(inputStream);
	        Sheet firstSheet = workbook.getSheetAt(0);
	        
	        List<WebElement> filas = u.getDriver().findElements(numeroFilas);
	        List<WebElement> columnas = u.getDriver().findElements(numeroColumnas);
	        int numeroDeFilas = filas.size();	        
	        int numeroDeColumnas = columnas.size();
	        String elementosWeb [] = new String[numeroDeFilas];
	        String elementosExcel [] = new String[43];	       
	        String elementosDiferentes [] = new String[numeroDeFilas];
	        String coloresCirculos [] = new String[numeroDeColumnas];
	        System.out.println("Número de filas: "+numeroDeFilas);	       
	        
	        
	        for (int i =0; i<numeroDeFilas; i++) {
	        	elementosWeb[i] = u.getText(By.xpath(String.format(encabezadoTabla, Integer.toString(i+1)))).trim();	        		        	
	        	//u.highLight(By.xpath(String.format(encabezadoTabla, Integer.toString(i+1))));	   
	        	if (elementosWeb[i] != elementosExcel[i]) {
	        		elementosDiferentes[i] = elementosWeb[i];
	        	}
	        }
	        
	        for (int i=0; i<43; i++) {
	        	elementosExcel[i] = datoCelda(firstSheet,9,i+1);	        		        
	        }	  
	        
	        List<String> listaWeb = new ArrayList<String>(Arrays.asList(elementosWeb));
	        System.out.println("Lista Web: "+listaWeb);
	        List<String> listaExcel = new ArrayList<String>(Arrays.asList(elementosExcel));
	        eliminarDatosVaciosExcel(listaExcel);
	        
	        	        
	        for (int i=0;i<numeroDeColumnas; i++) {	        	
	        	List<WebElement> elementosColores = u.getDriver().findElements(By.xpath(String.format(colores, Integer.toString(i+1))));
	        	u.highLight(By.xpath(String.format(colores, Integer.toString(i+1))));
	        	coloresCirculos[i] = elementosColores.get(i).getAttribute("id");
	        	listaWeb.add(1,coloresCirculos[i]);
	        	System.out.println("IDs de colores: "+coloresCirculos[i]);
	        }
	        
	        validarColor(listaWeb);	     
	        
	        if(listaWeb.equals(listaExcel)) {
	        	correct = true;
	        }
	        else {
	        	correct = false;
	        }
	        	        
	        //String colorNegro = u.getDriver().findElement(color).getAttribute(pathYourSystem)	        
	        	       
	        System.out.println(" ");
	        System.out.println("Se retorna: "+correct);
	        return correct;
	    }  
	

	/**
	 * Metodo para validar los encabezados de la tabla
	 * @return Retorna true si son correctos
	 * @author Pablo Pozos
	 */
	public Boolean validarEncabazadosMes() throws IOException {
		Boolean correct = false;

		u.scrollToElement(xmlSeccionResultados);

		String no = u.getText(By.xpath(String.format(xmlEncabezadosTabla, "1"))).trim();		

		String color = u.getText(By.xpath(String.format(xmlEncabezadosTabla, "2"))).trim();
		String mes = u.getText(xmlMesSeleccionado).trim();
		Integer days = diasenMes(mes)+2;

		Boolean dias = false;
		Integer dia = 1;
		for (int i = 3; i <= days; i++) {
			Integer d =Integer.valueOf(u.getText(By.xpath(String.format(xmlEncabezadosTabla, Integer.toString(i)))).trim());					
			if(dia != d) {
				dias = false;
				break;
			}else {
				dias = true;
				dia ++;
			}


		}

		if(no.equals("No.") && color.equals("Color") && dias) {
			correct = true;
		}

		testCase.addScreenShotCurrentStep(u, "validarEncabezado");

		return correct;
	}

	/**
	 * Metodo auxiliar para el metodo de mostrar los datos de la tabla de indicadores por totales, el cual
	 * sirve para obtener los dias en cada mes
	 * @param string
	 * @return entero que corresponde al numero de dias en el mes
	 * @author Pablo Pozos
	 */
	public Integer diasenMes(String mes) {
		Integer dias = 0;
		Month month = null;
		switch(mes)
		{
			case "Enero":
			{
				month = month.JANUARY;
				dias = month.length(false);
			}
			case "Febrero":
			{
				month = month.FEBRUARY;
				dias = month.length(false);
			}
			case "Marzo":
			{
				month = month.MARCH;
				dias = month.length(false);
			}
			case "Abril":
			{
				month = month.APRIL;
				dias = month.length(false);
			}
			case "Mayo":
			{
				month = month.MAY;
				dias = month.length(false);
			}
			case "Junio":
			{
				month = month.JUNE;
				dias = month.length(false);
			}
			case "Julio":
			{
				month = month.JULY;
				dias = month.length(false);
			}
			case "Agosto":
			{
				month = month.AUGUST;
				dias = month.length(false);
			}
			case "Septiembre":
			{
				month = month.SEPTEMBER;
				dias = month.length(false);
			}
			case "Octubre":
			{
				month = month.OCTOBER;
				dias = month.length(false);
			}
			case "Noviembre":
			{
				month = month.NOVEMBER;
				dias = month.length(false);
			}
			case "Diciembre":
			{
				month = month.DECEMBER;
				dias = month.length(false);
			}
			default:
			{
				dias = 30;
			}

		}
		return dias;
	}

	/**
	 * Metodo auxiliar para el metodo de checar Mayusculas
	 * @param string
	 * @return
	 * @author Pablo Pozos
	 */
	public Boolean checarMayusculas(String text){
		char[] chars = text.toCharArray();
		if(chars[0] != Character.toUpperCase(chars[0]))
			return false;
		int i = 0;
		for(char characters : chars ){
			if(characters == ' ' && (chars[i+1] != Character.toUpperCase(chars[i+1])))
				return false;
			i++;}
		return true;
	}


 /**
	 * Metodo para validar los encabezados de la tabla
	 * @return Retorna true si son correctos
	 * @author Jorge Ruiz, Yair Lizárraga
	 */
	public Boolean validarEncabazadosIPT() throws IOException {
				Boolean correct = false;
				
				u.scrollToElement(xmlSeccionResultadosIPT);
				
				
				String no = u.getText(By.xpath(String.format(xmlEncabezadosTabla, "1"))).trim();
				String color = u.getText(By.xpath(String.format(xmlEncabezadosTabla, "2"))).trim();
				
				System.out.println(no);
				System.out.println(color);
				
				Boolean dias = false;
				Integer dia = 1;
				for (int i = 3; i <= 33; i++) {
					Integer d = Integer.valueOf(u.getText(By.xpath(String.format(xmlEncabezadosTabla, Integer.toString(i)))).trim());
					System.out.println(d);

					if(dia != d) {
						dias = false;

						break;
					}else {
						dias = true;
						dia ++;
					}
					
				}
				
				if(no.equals("No.") && color.equals("Color") && dias) {
					correct = true;
				}
				
				testCase.addScreenShotCurrentStep(u, "validarEncabezado");
				
				return correct;
			}
	
	
	


	public ArrayList<String> addNewTab(){

		((JavascriptExecutor) u.getDriver()).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(u.getDriver().getWindowHandles());

		return tabs;
	}

	/**
	 * Metodo para descargar y abrir un archivo desde el navegador
	 * @return correct: true si abrio el archivo
	 * @param String: partofname - Nombre o parte de nombre del archivo
	 * @param String: browser - Navegador (Edge or Chrome)
	 * @author Andrea Contreras & David Valdez
	 */
	public Boolean abrirArchivoExcel(String partofname,String browser) throws FindFailed {
		boolean correct = false;

		ArrayList<String> tabs = addNewTab();
		u.getDriver().switchTo().window(tabs.get(tabs.size()-1));

		switch(browser) {
			case "Edge":
				u.get("edge://downloads/all");
				break;

			case "Chrome":
				u.get("chrome://downloads/");
				break;
		}

		u.waitForLoadPage();
		testCase.addScreenShotCurrentStep("downloads");

		try {
			u.click(By.xpath("//*[contains(text(),'" + partofname + "')]"));
			correct = true;
		}catch(Exception ex) {
			System.out.println("No se encontro el archivo buscado");
		}

		u.hardWait(10);
		testCase.addScreenShotCurrentStep("captura archivo");
		u.getDriver().close();
		u.getDriver().switchTo().window(tabs.get(tabs.size()-2));
		boolean close = me.clickElementSikuli("Simoti_Chile", "Close_Excel2"); //Da click en la tachita del excel para cerrarlo y continuar con los pasos
		if (close == false){
			close = me.clickElementSikuli("Simoti_Chile", "Close_Excel");
		}
		return correct;
	}


}
	
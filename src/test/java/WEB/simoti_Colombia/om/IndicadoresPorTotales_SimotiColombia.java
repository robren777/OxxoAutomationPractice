package WEB.simoti_Colombia.om;

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
import org.openqa.selenium.WebElement;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class IndicadoresPorTotales_SimotiColombia {
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	
	/*
	 * Localizadores pantalla Indicadores por totales
	 ********************************************************************************************/
	
	By xmlTitulo = By.xpath("//label[contains(text(), 'Indicadores por Totales')]");
	By xmlPlazaTd = By.xpath("//*[@id=\"j_idt839:panelFiltros\"]/tbody/tr/td[2]");
	By xmlPlazaSeleccionada = By.xpath("//*[@id=\"j_idt839:cmbPlaza_label\"]");
	By xmlPlazas = By.xpath("//*[@id=\"j_idt839:cmbPlaza_items\"]/li");
	By xmlMesTd = By.xpath("//*[@id=\"j_idt839:panelFiltros\"]/tbody/tr/td[4]");
	By xmlMesSeleccionado = By.xpath("//*[@id=\"j_idt839:cmbMes_label\"]");
	By xmlMeses = By.xpath("//*[@id=\"j_idt839:cmbMes_items\"]/li");
	By xmlAnioTd = By.xpath("//*[@id=\"j_idt839:panelFiltros\"]/tbody/tr/td[6]");
	By xmlAnioSeleccionado = By.xpath("//*[@id=\"j_idt839:cmbannio_label\"]");
	By xmlAnios = By.xpath("//*[@id=\"j_idt839:cmbannio_items\"]/li");
	
	By xmlConsultarBtn = By.xpath("//a[contains(text(), 'Consultar')]");
	//By xmlExportarBtn = By.xpath("//a[contains(text(), 'Exportar')]");
	By xmlExportarBtn = By.xpath("//fieldset[@id='j_idt839:j_idt846']/div/div/a[1]");
	
	By xmlSeccionResultados = By.xpath("//fieldset[2]");
	By xmlSinResultados = By.xpath("//fieldset[2]/div/span/div/div/div/table/tbody/tr/td");
	String xmlEncabezadosTabla = "//fieldset[2]/div/span/div/div/div/table/thead/tr/th[%s]/span";
	String xmlDatosTabla = "//fieldset[2]/div/span/div/div/div/table/tbody/tr[%s]";
	By xmlSinResultados2 = By.xpath("//tbody[@id='j_idt839:dtColors_data']/tr/td[contains(text(),'Sin resultados')]");
	By xmlNumResultados = By.xpath("//span[@class='ui-paginator-current']");
	
	
	By xmlInicio = By.xpath("//*[@id=\"j_idt839:dtColors_paginator_bottom\"]/span[2]");
	By xmlAdelante = By.xpath("//*[@id=\"j_idt839:dtColors_paginator_bottom\"]/span[3]");
	By xmlAtras = By.xpath("//*[@id=\"j_idt839:dtColors_paginator_bottom\"]/span[5]");
	By xmlFin = By.xpath("//*[@id=\"j_idt839:dtColors_paginator_bottom\"]/span[6]");
	
	By xmlMensaje = By.xpath("//div[@id='j_idt839:msgs_container']/div/div/div[2]/span");
	
	/*
	 * Constructor
	 ********************************************************************************************/
	public IndicadoresPorTotales_SimotiColombia(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}
	
	
	/*
	 * Metodos pantalla Indicadores por totales
	 ********************************************************************************************/
	
	/**
	 * Metodo para validar que se visualicen los elementos de la pantalla
	 * @author Maria Due�as
	 * @return Retorna true si se visualizan los elementos 
	 */
	public Boolean validarPantalla() {
		Boolean exist = false;
		
		try {
			if(me.existElement(xmlTitulo) && me.existElement(xmlAnioTd) && me.existElement(xmlConsultarBtn) &&
					me.existElement(xmlExportarBtn) && me.existElement(xmlMesTd) && me.existElement(xmlPlazaTd) &&
					me.existElement(xmlSeccionResultados) && u.getText(xmlSinResultados).trim().equals("Sin resultados")) {
			
				exist = true;
				
				u.highLight(xmlTitulo);
				u.highLight(xmlAnioTd);
				u.highLight(xmlConsultarBtn);
				u.highLight(xmlExportarBtn);
				u.highLight(xmlMesTd);
				u.highLight(xmlPlazaTd);
				
				testCase.addScreenShotCurrentStep(u, "validarPantalla1");
				
				u.scrollToElement(xmlSeccionResultados);
				
				u.highLight(xmlSeccionResultados);
				u.highLight(xmlSinResultados);
				
				testCase.addScreenShotCurrentStep(u, "validarPantalla2");
				
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
	 * @author Maria Due�as
	 */
	public Boolean validarEncabezados() {
		Boolean correct = false;
		
		u.scrollToElement(xmlSeccionResultados);
		
		String no = u.getText(By.xpath(String.format(xmlEncabezadosTabla, "1"))).trim();
		String color = u.getText(By.xpath(String.format(xmlEncabezadosTabla, "2"))).trim();
		
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
	 * @author Maria Due�as
	 */
	public Boolean validarBotonespaginado() {
		Boolean exist = false;
		
		u.scrollToElement(xmlSeccionResultados);
		
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
	 * Metodo para validar que se muestre el mes actual y se enlisten todos los meses del a�o
	 * @return Retorna true si se muestran correctamente
	 * @author Maria Due�as
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
	 * Metodo para validar que se muestre el a�o actual y se enlisten todos los a�os de 2010 al actual
	 * @return Retorna true si se muestran correctamente
	 * @author Maria Due�as
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
	 * @author Maria Due�as
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
	 * Metodo para seleccionar un a�o
	 * @param opcion
	 * @return Retorna true si se selecciono correctamente el a�o proporcionado
	 * @author Maria Due�as
	 */
	public Boolean selectAnioActual() {
		Boolean correct = false;
		Calendar c = Calendar.getInstance();
		String anio = String.valueOf(c.get(Calendar.YEAR));

		u.click(xmlAnioSeleccionado);
		List<WebElement> allOptions = u.getDriver().findElements(xmlAnios);
		
		for(WebElement option:allOptions){
			if(option.getText().trim().equals(anio)) {
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
	 * Metodo para seleccionar un a�o
	 * @param opcion
	 * @return Retorna true si se selecciono correctamente el a�o proporcionado
	 * @author Maria Due�as
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
	 * Metodo para seleccionar un mes
	 * @param opcion
	 * @return Retorna true si se selecciono correctamente el mes o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean selectMes(String opcion) {
		Boolean correct = false;
		
		u.click(xmlMesSeleccionado);
		
		List<WebElement> allOptions = u.getDriver().findElements(xmlMeses); 
		
		for(WebElement option:allOptions){
			if(option.getText().trim().equals(opcion)) {
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
	 * @author Maria Due�as
	 */
	public void clickConsultarBtn() {
		u.highLight(xmlConsultarBtn);
		testCase.addScreenShotCurrentStep(u, "Consultar");
		u.unhighLight(xmlConsultarBtn);
		u.click(xmlConsultarBtn);
		u.scrollToElement(xmlSeccionResultados);
		testCase.addScreenShotCurrentStep(u, "clickConsultar");
		u.hardWait(1);
	}
	
	/**
	 * Metodo para dar click en el boton Exportar
	 * @author Maria Due�as
	 */
	public void clickExportarBtn() {
		me.explicitWaitVisibilityOfElement(xmlExportarBtn, Duration.ofSeconds(10));
		//u.highLight(xmlExportarBtn);
		testCase.addScreenShotCurrentStep(u, "Exportar");
		//u.unhighLight(xmlExportarBtn);
		u.click(xmlExportarBtn);
		testCase.addScreenShotCurrentStep(u, "clickExportar");
		u.getDriver().navigate().refresh();
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
	 * Metodo para validar que aparezca el mensaje "No hay informaci�n para exportar."
	 * @return Retorna true si se muestra el mensaje o false en caso contrario
	 * @author Maria Due�as
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
	 * Metodo para validar que aparezca el mensaje <msg> 
	 * @param string
	 * @return retorna true si se muestra el mensaje o false en caso contrario
	 * @author Jorge Ruiz
	 */
	 public Boolean validarMensaje(String msg) {
		Boolean exist = false;
		String mensaje = "";
		
		if(u.isDisplayed(xmlMensaje)) {
			me.scrollToElement(xmlMensaje);
			mensaje = u.getText(xmlMensaje).trim();
			u.highLight(xmlMensaje);
			testCase.addScreenShotCurrentStep(u, "validarMensaje");
			u.unhighLight(xmlMensaje);
			if (mensaje.equals(msg)) {
				exist = true;
			}
			System.out.print(exist);
			
		} else {
			testCase.addScreenShotCurrentStep(u, "validarMensaje");
		}
		
		return exist;
	}
	 /**
		 * Metodo para obtener el numero de mes ingresado
		 * @param string 
		 * @return entero que corresponde al numero de dias en el mes
		 * @author Jorge Ruiz
		 */
		public int numeroDeMes(String mes) {
			int nomes = 1;
			String[] meses = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
			List<String> nameMes = new ArrayList<String>(Arrays.asList(meses));
			Integer[] noMes = {1,2,3,4,5,6,7,8,9,10,11,12};
			List<Integer> noMesa = new ArrayList<Integer>(Arrays.asList(noMes));
			
			int cont = 0;
			for(String soymes: nameMes)
			{
				if(soymes.equals(mes))
					return noMesa.get(cont);
				cont++;
			}
			
			return nomes;
		}
	/**
	 * Metodo auxiliar para el metodo de mostrar los datos de la tabla de indicadores por totales, el cual 
	 * sirve para obtener los dias en cada mes
	 * @param string 
	 * @return entero que corresponde al numero de dias en el mes
	 * @author Jorge Ruiz
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
	 * Metodo para obtener el valor de una celda en un archivo XLSX
	 * @param string
	 * @return string correspondiente a al valor en la celda seleccionada
	 * @author Jorge Ruiz
	 */
	
	public String datoCelda(Sheet sheet, int r, int c) {
		DataFormatter formatter = new DataFormatter();
        
        Cell celda = sheet.getRow(r).getCell(c);
        String contenidoCelda = formatter.formatCellValue(celda);
        
        return contenidoCelda;
	}
	
	
	
	/**
	 * Metodo para mostrar la informacion en la tabla de indicadores por totales
	 * @param string
	 * @return true si la informacion en el xlsx corresponde a la consulta realizada, false en caso contrario
	 * @author Jorge Ruiz
	 */
	public Boolean validarDatosTablaExcel(String filename,String number) throws IOException {
		u.scrollToElement(xmlSeccionResultados);

		Boolean correct = false;
		String formato = "ddMMyyyy";
		SimpleDateFormat formatFecha = new SimpleDateFormat(formato);
        LocalDateTime datetime = LocalDateTime.now();

        String nombreArchivo = filename;
        String fecha = DateTimeFormatter.ofPattern(formato).format(datetime); 

	    String pathYourSystem = System.getProperty("user.home") + "\\Downloads\\"+ nombreArchivo + fecha + number+".xlsx";
	    File fichero = new File(pathYourSystem);
	    System.out.println(pathYourSystem);

        FileInputStream inputStream = new FileInputStream(new File(pathYourSystem));
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        
        String aplicacion = "SIMOTI";
        String apartado = "Integridad de Datos";
        String reporte = "Reporte Indicadores Totales";
        
        String plaza = u.getText(xmlPlazaSeleccionada).trim();
        String mes = u.getText(xmlMesSeleccionado).trim();
        String anio = u.getText(xmlAnioSeleccionado).trim();
        
		Integer days = diasenMes(mes)+2;
		
		String no = u.getText(By.xpath(String.format(xmlEncabezadosTabla, "1"))).trim();
		String color = u.getText(By.xpath(String.format(xmlEncabezadosTabla, "2"))).trim();
		String row = "";
		String registers = u.getText(xmlNumResultados).trim();
		Integer numreg = Integer.parseInt(registers.substring(32,registers.length()));
        
        String cellapp = datoCelda(firstSheet, 1, 6);
        String cellapartado = datoCelda(firstSheet, 2, 6);
        String cellreporte = datoCelda(firstSheet, 3, 6);
        String cellplaza = datoCelda(firstSheet, 6, 2);
        String cellmes = datoCelda(firstSheet, 6, 11);
        String cellanio = datoCelda(firstSheet, 6, 16);        
        							  //1,3,5,7,10,12,13,15,18,21
        							  //B,D,F,H,K, M, N, P, S, U
        Integer[] posicionesheaders = {5,7,10,12,13,15,18,21};
        Integer[] values = new Integer[days];
       
        if(!cellapp.equals(aplicacion) || !cellapartado.equals(apartado) || !cellreporte.equals(reporte) 
        		|| !cellplaza.equals(plaza) || !cellmes.equals(mes) || !cellanio.equals(anio))
        {
        	System.out.println("No coinciden los encabezados o datos de consulta.");
        	return false;
        }
        	
		for(int j = 1 ; j <= numreg; j++)
		{
			row = String.format(xmlDatosTabla, Integer.toString(j));
			Integer rownum = j + 8;
			System.out.println("Row: " + j);
			String str_color = u.getDriver().findElement(By.xpath(row.concat(String.format("/td[%s]/img", Integer.toString(2))))).getAttribute("src");
			str_color = str_color.substring(str_color.indexOf("images/")+7,str_color.indexOf("_plano_png.png.do"));
			String str_colorE = datoCelda(firstSheet,rownum,3);
			
			if((str_color.equals("rojo") && str_colorE.equals("red")))
				str_color = str_colorE;
			else if(str_color.equals("negro")&& str_colorE.equals("black"))
				str_color = str_colorE;
			else if(str_color.equals("blanco")&& str_colorE.equals("white"))
				str_color = str_colorE;
			else if(str_color.equals("amarillo")&& str_colorE.equals("yellow"))
				str_color = str_colorE;
			else
				return false;
			
			for(int i = 1; i <= 8;i++)
			{
				Integer d = Integer.valueOf(u.getText(By.xpath(row.concat(String.format("/td[%s]", Integer.toString(i+2))))).trim());
				values[i-1] = Integer.parseInt(datoCelda(firstSheet,rownum,posicionesheaders[i-1]));
				//System.out.println(d+ " - " + values[i-1]);
				if(d != values[i-1])
					return false;
			}
			for (int i = 9; i < days-10; i++) 
			{
				Integer cellnum = i + 12;
				Integer d = Integer.valueOf(u.getText(By.xpath(row.concat(String.format("/td[%s]", Integer.toString(i))))).trim());
				values[i] = Integer.parseInt(datoCelda(firstSheet,rownum,cellnum));
				//System.out.println(d+ " - " + values[i]);
				if(d != values[i])
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Metodo para validar la informacion de la consulta de indicadores totales contra query
	 * @param string
	 * @return true si la informacion en el xlsx corresponde a la consulta realizada, false en caso contrario
	 * @author Jorge Ruiz
	 */
	public Boolean validarDatosQuery(SQLResult queryPopResult) throws IOException {
		u.scrollToElement(xmlSeccionResultados);
		
		Boolean correct = false;
		String formato = "ddMMyyyy";
		SimpleDateFormat formatFecha = new SimpleDateFormat(formato);
        LocalDateTime datetime = LocalDateTime.now();

       
        String fecha = DateTimeFormatter.ofPattern(formato).format(datetime); 
        
        String plaza = u.getText(xmlPlazaSeleccionada).trim();
        String mes = u.getText(xmlMesSeleccionado).trim();
        String anio = u.getText(xmlAnioSeleccionado).trim();
        
		Integer days = diasenMes(mes)+2;
		
		String no = u.getText(By.xpath(String.format(xmlEncabezadosTabla, "1"))).trim();
		String color = u.getText(By.xpath(String.format(xmlEncabezadosTabla, "2"))).trim();
		String row = "";
		String registers = u.getText(xmlNumResultados).trim();
		Integer numreg = Integer.parseInt(registers.substring(32,registers.length()));

        Integer[] values = new Integer[days];
   
		ArrayList<String> sqlsearch = new ArrayList<String>();
		sqlsearch.add("color");
		for(int i = 0; i < days-2; i++)
			if(i < 10)
				sqlsearch.add("0"+String.valueOf(i));
			else
				sqlsearch.add(String.valueOf(i));
		for(String a: sqlsearch)
			System.out.println(a);
		
		for(int j = 1 ; j <= numreg; j++)
		{
			row = String.format(xmlDatosTabla, Integer.toString(j));
			Integer rownum = j + 8;
			System.out.println("Row: " + j);
			for(int i = 1; i <= 8;i++)
			{
				Integer d = Integer.valueOf(u.getText(By.xpath(row.concat(String.format("/td[%s]", Integer.toString(i+2))))).trim());
				values[i-1] = Integer.parseInt(queryPopResult.getData(i, sqlsearch.get(i-1)));
				//System.out.println(d+ " - " + values[i-1]);
				if(d != values[i-1])
					return false;
			}
			for (int i = 9; i < days-10; i++) 
			{
				Integer cellnum = i + 12;
				Integer d = Integer.valueOf(u.getText(By.xpath(row.concat(String.format("/td[%s]", Integer.toString(i))))).trim());
				values[i] = Integer.parseInt(queryPopResult.getData(i, sqlsearch.get(i)));
				//System.out.println(d+ " - " + values[i]);
				if(d != values[i])
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Metodo para mostrar la informacion en la tabla de indicadores por totales
	 * @param string
	 * @return true si la informacion en el xlsx corresponde a la consulta realizada, false en caso contrario
	 * @author Jorge Ruiz
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
	 * Metodo para validar que aparezca el mensaje "Ha ocurrido un error al consultar la informaci�n del reporte, favor de reportar al �rea de soporte."
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
			
			if (mensaje.equals("Ha ocurrido un error al consultar la informaci�n del reporte, favor de reportar al �rea de soporte.")) {
				exist = true;
			}
			
		} else {
			testCase.addScreenShotCurrentStep(u, "validarMensaje");
		}
		
		return exist;
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
	 * Metodo para validar que se muestren resultados en la tabla
	 * @return Retorna true si se muestra el mensaje o false en caso contrario
	 * @author Andrea Contreras
	 */
	public Boolean seMuestra() {
		
		Boolean exist = false;
		
		me.scrollToElement(xmlSeccionResultados);
		u.highLight(xmlSeccionResultados);
		
		if(me.existElement(xmlSinResultados2)==false) {
						
			exist = true;
			
		}
		
		testCase.addScreenShotCurrentStep(u, "validarResultados");
		
		return exist;
	}
	
}

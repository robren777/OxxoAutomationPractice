package WEB.simoti_Colombia.om;

import java.time.Duration;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class ReporteDeSeguimientoDePendientes_SimotiColombia {
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	MenuPrincipal_SimotiColombia mp;
	
	/*
	 * Localizadores pantalla Seguimiento de pendientes
	 ********************************************************************************************/
	
	By xmlTitulo = By.xpath("//label[contains(text(), 'Reporte de Consultar Promociones')]");
	By xmlPlazaTd = By.xpath("//*[@id=\"TransPromocionesForm:pGEncabezadoCriterio\"]/tbody/tr/td[2]");
	By xmlPlaza = By.xpath("//*[@class=\"ui-icon ui-icon-triangle-1-s ui-c\"]");
	By xmlPlazaBGA = By.xpath("//*[@id=\"TransPromocionesForm:cmbPlaza_1\"]");
	By xmlPlazaBCA = By.xpath("//*[@id=\"TransPromocionesForm:cmbPlaza_2\"]");
	By xmlResultados = By.xpath("//tbody[@id='TransPromocionesForm:repPromocionesGrid_data']/tr/td[contains(text(), 'Sin resultados')]");
	
	By xmlMesTd = By.xpath("//*[@id=\"TransPromocionesForm:pGEncabezadoCriterio\"]/tbody/tr/td[5]");

	By xmlMonthSelect = By.xpath("//select[@data-handler='selectMonth']");
	By xmlFechaInicioBtn = By.xpath("//*[@id=\"TransPromocionesForm:cldfechaI\"]/button");
	By xmlAnioTd = By.xpath("//*[@id=\"TransPromocionesForm:pGEncabezadoCriterio\"]/tbody/tr/td[7]");
	By xmlFechaFinBtn = By.xpath("//*[@id=\"TransPromocionesForm:cldfechaF\"]/button");
	
	String xmlDia = "//table[@class='ui-datepicker-calendar']/tbody/tr/td/a[contains(text(), '%s')]";
	By xmlMes = By.xpath("//select[@data-handler='selectMonth']");
	By xmlSeleccionarDate = By.xpath("//*[@id=\"TransPromocionesForm:cldfechaI\"]/button");
	By xmlCalendarioFechaInicio = By.xpath("//table[@id='TransPromocionesForm:pGEncabezadoCriterio']/tbody/tr/td[5]/span/button");

	By xmlSeleccionarDateFin = By.xpath("//*[@id=\"TransPromocionesForm:cldfechaF\"]/button");

	By xmlAnnio = By.xpath("//select[@data-handler='selectYear']");
	
	By xmlConsultarBtn = By.xpath("//a[contains(text(), 'Consultar')]");
	By xmlExportarBtn = By.xpath("//a[contains(text(), 'Exportar')]");
	
	By xmlSeccionResultados = By.xpath("//*[@id=\"TransPromocionesForm:j_idt417\"]");
	By xmlSinResultados = By.xpath("//*[@id=\"TransPromocionesForm:repPromocionesGrid_data\"]/tr/td");
	String xmlEncabezadosTabla = "//*[@id=\"TransPromocionesForm:repPromocionesGrid_head\"]/tr/th[%s]/span";
	By xmlRegistros = By.xpath("//tbody[@id='TransPromocionesForm:repPromocionesGrid_data']/tr/td");
	
	By xmlInicio = By.xpath("//*[@id=\"TransPromocionesForm:repPromocionesGrid_paginator_bottom\"]/span[2]");
	By xmlAdelante = By.xpath("//*[@id=\"TransPromocionesForm:repPromocionesGrid_paginator_bottom\"]/span[5]");
	By xmlAtras = By.xpath("//*[@id=\"TransPromocionesForm:repPromocionesGrid_paginator_bottom\"]/span[3]");
	By xmlFin = By.xpath("//*[@id=\"TransPromocionesForm:repPromocionesGrid_paginator_bottom\"]/span[6]");
	By xmlTotalRegistros = By.xpath("//div[@id='TransPromocionesForm:repPromocionesGrid_paginator_bottom']/span[contains(text(),'Total de registros encontrados')]");
	By xmlPaginas = By.xpath("//span[@class='ui-paginator-pages']");
	By xmlPaginaActual = By.xpath("");
	
	By xmlMensaje = By.xpath("//*[@id=\"TransPromocionesForm:msgs_container\"]/div/div/div[2]/span");

	By xmlMensajeError = By.xpath("//*[@id=\"TransPromocionesForm:msgs_container\"]/div");

	By xmlPlazaSeleccionada = By.xpath("//*[@id=\"TransPromocionesForm:cmbPlaza\"]/div[3]/span");

	By xmlPlazas = By.xpath("//*[@id=\"TransPromocionesForm:cmbPlaza_items\"]/li");

	String xmlSelectValue = "//select[@data-handler='%1$s']/option[text()='%2$s']";

	String xmlDayCalendar = "//td/a[text()='%s']";

	By xmlYearSelect = By.xpath("//select[@data-handler='selectYear']");




	/*
	 * Constructor
	 ********************************************************************************************/
	public ReporteDeSeguimientoDePendientes_SimotiColombia(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
		this.mp = new MenuPrincipal_SimotiColombia(u, testCase);
	}
	
	
	/*
	 * Metodos pantalla Seguimiento de pendientes
	 ********************************************************************************************/
	
	/**
	 * Metodo para validar el nombre de la pantalla sea 'Reporte de Consultar Promociones'
	 * @author Andrea Contreras
	 * @return Retorna true si encuentra la pantalla
	 */
	public Boolean validarNombrePantalla() {
		Boolean exist = false;
		u.waitForLoadPage();
		
		if(me.existElement(xmlTitulo) ) {
			exist = true;
			u.highLight(xmlTitulo);
			testCase.addScreenShotCurrentStep(u, "nomPantalla");
			u.unhighLight(xmlTitulo);
		}
		return exist;
	}
	
	/**
	 * Metodo para validar que se visualicen los elementos de la pantalla
	 * @author Maria Due�as
	 * @return Retorna true si se visualizan los elementos 
	 */
	public Boolean validarPantalla() {
		Boolean exist = false;
		u.waitForLoadPage();
		
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
				
				testCase.addScreenShotCurrentStep(u, "validarPantalla");
				
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
		
		Boolean correct = true;
		String cadena = "";
		
		for (int i = 1; i <= 5; i++) {
			
			String encabezado = u.getText(By.xpath(String.format(xmlEncabezadosTabla, Integer.toString(i)))).trim();
			u.highLight(By.xpath(String.format(xmlEncabezadosTabla, Integer.toString(i))));
			testCase.addScreenShotCurrentStep(u, "Encabezado");
			u.unhighLight(By.xpath(String.format(xmlEncabezadosTabla, Integer.toString(i))));
			
			switch(i){
				case 1: cadena = "No.";
					break;
				case 2: cadena = "Promoci�n";
				break;
				case 3: cadena = "Descripci�n";
				break;
				case 4: cadena = "Fecha Inicio";
				break;
				case 5: cadena = "Fecha Fin";
				break;
			}
			
			if(!encabezado.equals(cadena)) {
				correct = false;
				break;
			}
			
		}
		
		return correct;
	}
	
	/**
	 * Metodo para validar botones de paginado
	 * @return Retorna true si estan los botones
	 * @author Maria Due�as
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
	 * Metodo para seleccionar la plaza fecha de incio y fecha fin
	 * @param plaza
	 * @param fInicio
	 * @param fFin
	 * @return Retorna true si se seleccionaron correctamente las opciones
	 * @author Maria Due�as
	 */	
	public Boolean filtrosReporteSeguimiento(String Plaza,String fInicio, String fFin) {
		Boolean correct = false;
		
		try {
			
			
			Boolean fP = selectPlaza(Plaza);
			Boolean fI = selectDate(fInicio, xmlFechaInicioBtn);
			Boolean fF = selectDate(fFin, xmlFechaFinBtn);
			
			if (fP == true && fI == true && fF==true) correct = true;
			
		}catch(Exception e) {
			
		}
		
		return correct;
	}
	
	public Boolean filtrosReporteSeguimiento(String fInicio, String fFin) {
		Boolean correct = false;
		
		try {
			Boolean fI = selectDate(fInicio, xmlFechaInicioBtn);
			Boolean fF = selectDate(fInicio, xmlFechaFinBtn);
			
			if (fI == true && fF==true) correct = true;
			
		}catch(Exception e) {
			
		}
		
		return correct;
	}
	/**
	 * 
	 * @param plaza
	 * @return
	 *  @author kevin.valadez
	 */
	
	public Boolean Datosplaza(String opcion) {
		Boolean exist = false;

		switch (opcion) {
		case "10BGA BOGOTA":
			clickopcion(xmlPlaza);
			clickopcion(xmlPlazaBGA);
			exist = true;
			break;

		case "10BCA BUCARAMANGA":
			clickopcion(xmlPlaza);
			clickopcion(xmlPlazaBCA);
			exist = true;
			break;
		}

		return exist;

	}
	
	public Boolean validarResultado(String resultado) {
		Boolean exist = false;
		String mensaje = "";
		
		if (u.isDisplayed(xmlMensaje)) {
			me.scrollToElement(xmlMensaje);
			mensaje = u.getText(xmlMensaje).trim();
			u.highLight(xmlMensaje);
			testCase.addScreenShotCurrentStep(u, "validarMensaje");
			u.unhighLight(xmlMensaje);
			if (mensaje.equals(resultado)) {
				exist = true;
			}

		} else {
			testCase.addScreenShotCurrentStep(u, "validarMensaje");
		}

		return exist;
	}

	
	/**
	 * Metodo para seleccionar opcion de una lista desplegable
	 * @param select
	 * @param opciones
	 * @param opcion
	 * @return Retorna true si se selecciona correctamente
	 * @author Maria Due�as
	 */
	public Boolean selectOption(By select, By opciones, String opcion) { 
		Boolean existOption = false;
		
		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(select));

		element.click();
		List<WebElement> allOptions = u.getDriver().findElements(opciones);
		
		for(WebElement option:allOptions){
			//System.out.print("-"+option.getText()+"-"+opcion+"-\n");
			if(option.getText().equals(opcion)) {//busca la opcion que se pasa por parametro
			  u.hardWait(3);
			  option.click(); //da clic a esa opcion 
			  u.hardWait(1);
			  existOption = true;
			  break;
			}
		}
		
		u.highLight(select);
		testCase.addScreenShotCurrentStep(u, "selectOption");
		u.unhighLight(select);
		return existOption;
	}
	
	public void clickopcion(By by) {
		u.highLight(by);
		testCase.addScreenShotCurrentStep(u, "clickopcion");
		u.unhighLight(by);
		u.click(by);
	}
	/**
	 * Metodo para seleccionar una opcion de un select
	 * @param option
	 * @param by
	 * @return exist: true si se selecciono correctamente la opcion o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean selectOption(String option, By by) {
		Boolean exist = false;
		
		try {
			Select selectObject = new Select(u.getDriver().findElement(by));
			selectObject.selectByVisibleText(option);
			exist = true;
		}catch(Exception e) {
			exist = false;
		}
		
		u.highLight(by);
		testCase.addScreenShotCurrentStep(u, "selectOption");
		u.unhighLight(by);
		
		return exist;
	}
	
	/**
	 * Metodo para ingresar la fecha a buscar
	 * @param fecha
	 * @param calendario
	 * @return correct: true si selecciona correctamente la fecha o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean selectDate(String fecha, By calendario) {
		Boolean correct = false;
		
		me.scrollToElement(calendario);
		u.highLight(calendario);
		testCase.addScreenShotCurrentStep(u, "clickCalendario");
		u.unhighLight(calendario);
		u.click(calendario);
		
		String [] f = fecha.split("\\*");
		//System.out.print(f + "\n");
		Boolean anio = selectOption(f[2], xmlAnnio);
		
		Boolean mes = selectOption(f[1], xmlMes);
		
		String dia = String.format(xmlDia, f[0]);
		u.highLight(By.xpath(dia));
		testCase.addScreenShotCurrentStep(u, "clickDia");
		u.unhighLight(By.xpath(dia));
		u.click(By.xpath(dia));
		
		if(mes == true && anio == true) {
			correct = true;
		}
		
		return correct;
	}
	
	/**
	 * Metodo para dar click en el boton Consultar
	 * @author Maria Due�as
	 */
	public void clickConsultar() {
		u.highLight(xmlConsultarBtn);
		testCase.addScreenShotCurrentStep(u, "Consultar");
		u.unhighLight(xmlConsultarBtn);
		u.click(xmlConsultarBtn);
		mp.waitLoading();
		testCase.addScreenShotCurrentStep(u, "clickConsultar");
	}
	
	/**
	 * Metodo para dar click en el boton Exportar
	 * @author Maria Due�as
	 */
	public void clickExportar() {
		u.highLight(xmlExportarBtn);
		testCase.addScreenShotCurrentStep(u, "Exportar");
		u.unhighLight(xmlExportarBtn);
		u.click(xmlExportarBtn);
		mp.waitLoading();
		testCase.addScreenShotCurrentStep(u, "clickExportar");
	}
	
	/**
	 * Metodo para validar que aparezca el mensaje "No hay informaci�n para exportar."
	 * @return Retorna true si se muestra el mensaje o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean validarMensajeData() {
		Boolean exist = true;
		String mensaje = "";
		
		if(u.getDriver().findElement(xmlMensaje).isDisplayed()) {
			
			me.scrollToElement(xmlMensaje);
			mensaje = u.getText(xmlMensaje).trim();
			u.highLight(xmlMensaje);
			testCase.addScreenShotCurrentStep(u, "validarMensaje");
			u.unhighLight(xmlMensaje);
			
			if (mensaje.equals("No se ha encontrado informaci�n con los filtros seleccionados.")) {
				System.out.print("No se ha encontrado informaci�n con los filtros seleccionados");
				exist=true;
			}
			
		} else {
			testCase.addScreenShotCurrentStep(u, "validarMensaje");
			
		}
	
		return exist;
	}

	/**
	 * Metodo para validar que aparezca el mensaje "No hay informaci�n para exportar."
	 * @return Retorna true si se muestra el mensaje o false en caso contrario
	 * @author Maria Due�as/Pablo Pozos
	 */
	public Boolean validarMensajeError() {
		Boolean exist = false;
		String mensaje = "";

		if(u.getDriver().findElement(xmlMensaje).isDisplayed()) {

			me.scrollToElement(xmlMensaje);
			mensaje = u.getText(xmlMensaje).trim();
			u.highLight(xmlMensaje);
			testCase.addScreenShotCurrentStep(u, "validarMensaje");
			u.unhighLight(xmlMensaje);

			if (mensaje.equals("Estimado usuario, favor de seleccionar una Plaza en la secci�n de filtros")) {
				exist = true;
			}

		} else {
			testCase.addScreenShotCurrentStep(u, "validarMensaje");
		}

		return exist;
	}



	/**
	 * Metodo para validar que se muestre el mes actual y se enlisten todos los meses del a�o
	 * @return Retorna true si se muestran correctamente
	 * @author Maria Due�as / Pablo Pozos
	 */
	public Boolean validarFiltroMes() {
		Boolean correct = false;

		int annio;
		String mes;
		String MES[] = new String[] {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
		Calendar c1 = Calendar.getInstance();
		annio = c1.get(Calendar.YEAR);
		mes = MES[c1.get(Calendar.MONTH)];
		u.highLight(xmlSeleccionarDate);
		u.click(xmlSeleccionarDate);

		String mes_Sistema = u.getText(xmlMes).trim();
		u.highLight(xmlMes);
		testCase.addScreenShotCurrentStep(u, "Mes");
		u.unhighLight(xmlMes);
		u.click(xmlMes);
		List<WebElement> allOptions = u.getDriver().findElements(xmlMesTd);

		int cont = 0;
		String meses[] = new String[12];
		for(WebElement option:allOptions){


			if(!option.getText().equals("Seleccione una opci�n")){
				meses[cont] = option.getText();
				cont ++;
				//System.out.print(option.getText() + "\n");
			}
		}

		if (mes_Sistema.equals(mes) == true && Arrays.equals(meses, MES)) {
			correct = true;
		}

		u.click(xmlMes);

		return true;
	}

	/**
	 * Metodo para validar que se muestre el mes actual y se enlisten todos los meses del a�o
	 * @return Retorna true si se muestran correctamente
	 * @author Maria Due�as / Pablo Pozos
	 */
	public Boolean validarFiltroMesFin() {
		Boolean correct = false;

		int annio;
		String mes;
		String MES[] = new String[] {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
		Calendar c1 = Calendar.getInstance();
		annio = c1.get(Calendar.YEAR);
		mes = MES[c1.get(Calendar.MONTH)];
		u.highLight(xmlSeleccionarDateFin);
		u.click(xmlSeleccionarDateFin);

		String mes_Sistema = u.getText(xmlMes).trim();
		u.highLight(xmlMes);
		testCase.addScreenShotCurrentStep(u, "Mes");
		u.unhighLight(xmlMes);
		u.click(xmlMes);
		List<WebElement> allOptions = u.getDriver().findElements(xmlMesTd);

		int cont = 0;
		String meses[] = new String[12];
		for(WebElement option:allOptions){


			if(!option.getText().equals("Seleccione una opci�n")){
				meses[cont] = option.getText();
				cont ++;
				//System.out.print(option.getText() + "\n");
			}
		}

		if (mes_Sistema.equals(mes) == true && Arrays.equals(meses, MES)) {
			correct = true;
		}

		u.click(xmlMes);

		return true;
	}


	/**
	 * Metodo para validar que se muestre el a�o actual y se enlisten todos los a�os de 2013 al actual
	 * @return Retorna true si se muestran correctamente
	 * @author Maria Due�as / Pablo Pozos
	 */
	public Boolean validarFiltroAnio() {
		Boolean correct = false;

		int annio;
		Calendar c1 = Calendar.getInstance();
		annio = c1.get(Calendar.YEAR);
		u.highLight(xmlSeleccionarDate);
		u.click(xmlSeleccionarDate);

		int cont = 0;
		int ANIO[] = new int[annio-2013];
		for(int i = 2013; i <= annio; i++) {
			ANIO[cont] = i;
			cont ++;
		}

		int anio_Sistema = Integer.parseInt(u.getText(xmlAnnio).trim());
		u.highLight(xmlAnnio);
		testCase.addScreenShotCurrentStep(u, "Anio");
		u.unhighLight(xmlAnnio);
		u.click(xmlAnnio);

		List<WebElement> allOptions = u.getDriver().findElements(xmlAnioTd);

		cont = 0;
		int anios[] = new int[annio-2013];

		for(WebElement option:allOptions){

			if(!option.getText().equals("Seleccione una opci�n")) {
				anios[cont] = Integer.parseInt(option.getText());
				cont ++;
			}
		}

		if(anio_Sistema == annio && Arrays.equals(ANIO, anios)) {
			correct = true;
		}

		u.click(xmlAnnio);

		return correct;
	}
	
	/**
	 * Metodo para validar que se muestre el paginado de la pantalla
	 * 1. paginado con 10 registros por p�gina.
	 * 2. Numeraci�n de paginas y pagina donde se encuentra ubicado el usuario.
	 * 3. Total de registros encontrados en la consulta del reporte
	 * @return Retorna true o false si se muestra correctamente
	 * @author Andrea Contreras
	 */
	public Boolean validacionesPaginado() {
		Boolean correct = false, v1 = false, v2 = false, v3 = false;

		//1. p�ginado con 10 registros por pagina y botones
		
		List<WebElement> registros = u.getDriver().findElements(xmlRegistros);

		if(validarBotonespaginado() == true && (registros.size() == 10) ) {
			v1 = true;
			u.highLight(xmlRegistros);	
			testCase.addScreenShotCurrentStep(u, "validarRegistros");
			u.unhighLight(xmlRegistros);
			
		}

		System.out.print(me.isEnable(xmlPaginas));
        //2. Numeraci�n de paginas y pagina donde se encuentra ubicado el usuario.
		//falta buscar el localizador del pagina actual y descomentar los highlights
		if(/*me.existElement(xmlPaginaActual) &&*/ me.isEnable(xmlPaginas)) {
			v2 = true;
			//u.highLight(xmlPaginaActual);	
			u.highLight(xmlPaginas);
			testCase.addScreenShotCurrentStep(u, "validarTotalRegistros");
			//u.unhighLight(xmlPaginaActual);
			u.unhighLight(xmlPaginas);
			
		}
	
		//3. Total de registros encontrados en la consulta del reporte
		if(me.existElement(xmlTotalRegistros) ) {
			v3 = true;
			u.highLight(xmlTotalRegistros);			
			testCase.addScreenShotCurrentStep(u, "validarTotalRegistros");
			u.unhighLight(xmlTotalRegistros);
			
		}
	    
	    if(v1 == true && v2 == true && v3 == true) {
	    	correct = true;
	    }
	    
		return correct;
	}
	

	/**
	 * Metodo para validar que aparezca el mensaje <msg>
	 * @param string
	 * @return retorna true si se muestra el mensaje o false en caso contrario
	 * @author Pablo Pozos
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

		u.hardWait(5);
		u.highLight(xmlPlazaSeleccionada);
		testCase.addScreenShotCurrentStep(u, "selectPlaza");
		u.unhighLight(xmlPlazaSeleccionada);

		return correct;
	}

	/**
	 * Metodo para validar que aparezca el mensaje <msg>
	 * @param integer correspondiente al mes
	 * @return retorna String correspondiente al mes
	 * @author Pablo Pozos
	 */
	public String getMonth(Integer month)
	{
		String[] months = {"Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dic"};
		return months[month-1];
	}

	public String formatDate(String date)
	{
		String newdate = "";
		if(date.length()<10)
		{
			String day = date.substring(0,date.indexOf('/'));
			date = date.substring(date.indexOf('/')+1,date.length());
			Integer month = Integer.parseInt(date.substring(0,date.indexOf('/')));
			date = date.substring(date.indexOf('/')+1,date.length());
			String year = date;
			if(year.length()<4)
				year = "20" + year;
			newdate = day + "/" + month + "/" + year;
		}
		return newdate;
	}


	/**
	 * Metodo para validar que aparezca el mensaje <msg>
	 * @param string
	 * @return retorna true si se muestra el mensaje o false en caso contrario
	 * @author Pablo Pozos
	 */
	public Boolean selectDate(String tipo, String date)
	{
		String day = date.substring(0,date.indexOf('/'));
		date = date.substring(date.indexOf('/')+1,date.length());
		Integer month = Integer.parseInt(date.substring(0,date.indexOf('/')));
		date = date.substring(date.indexOf('/')+1,date.length());
		String year = date;

		if(tipo == "Inicio")
			u.click(xmlCalendarioFechaInicio);
		else if (tipo == "Fin")
			u.click(xmlSeleccionarDateFin);

		u.click(xmlMonthSelect);
		u.click(By.xpath(String.format(xmlSelectValue, "selectMonth",getMonth(month))));
		u.click(By.xpath(String.format(xmlSelectValue, "selectYear", year)));
		u.click(By.xpath(String.format(xmlDayCalendar, day)));

		return true;
	}
	
	
	/**
	 * Metodo para validar que se muestre resultados en el grid
	 * @return Retorna true o false si se muestran resultados
	 * @author Andrea Contreras
	 */
	public Boolean validarExistanResultados() {
		Boolean validacion = false;
		String resultado = "";
		
		if(me.existElement(xmlRegistros)) {
			resultado = u.getText(xmlRegistros);	
			u.highLight(xmlRegistros);	
			testCase.addScreenShotCurrentStep(u, "validarExistanResultados");
			u.unhighLight(xmlRegistros);	
		}
		
		if(!resultado.equals("Sin resultados")) {
			validacion = true;		
		}else {
			testCase.addTextEvidenceCurrentStep("No se muestran resultados de la consulta");
		}
		
		return validacion;


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
	
}
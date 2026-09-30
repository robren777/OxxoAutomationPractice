package WEB.simoti_Colombia.om;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class TransaccionesTDC_SimotiColombia {
	
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	String Replace = "";
	
	/**Localizadores*/
	By xmlConsultarBtn = By.xpath("//a[contains(text(), 'Consultar')]");
	By xmlExportarBtn = By.xpath("//a[contains(text(), 'Exportar')]");

	By busquedaDatosGenerales = By.xpath("//*[@id='formEnvDineroTdc:tipoBusqueda']/tbody/tr/td[3]/div/div[2]/span");
	By busqueda = By.xpath("//*[@id=\"formEnvDineroTdc:tipoBusqueda\"]/tbody/tr/td[1]/div/div[2]/span");

	By xmlTitulo = By.xpath("(//label[contains(text(), 'Tarjeta de Cr�dito - Transacciones TDC')])");
	By xmlbusquedacontainer = By.xpath("//fieldset[@id=\"formEnvDineroTdc:j_idt550\"]//div[@class=\"ui-fieldset-content\"]");
	By Folio = By.xpath("//label[@for=\"formEnvDineroTdc:tipoBusqueda:0\"]");
	By DatosGenerales = By.xpath("//label[@for=\"formEnvDineroTdc:tipoBusqueda:1\"]");
	By xmlTipotransaccion = By.xpath("//label[@id=\"formEnvDineroTdc:j_idt558\"]");
	By xmlTipotransaccionOP = By.xpath("//label[contains(text(), 'Seleccione una opci�n')]");
	By xmlFolioWebMethods = By.xpath("//label[contains(text(), 'Folio WebMethods')]");
	By FolioWebMethods = By.xpath("//input[@name=\"formEnvDineroTdc:txtfoliowm\"]");

/**************************************/
	By xmlPlaza = By.xpath("//label[contains(text(), 'Plaza')]");
	By xmlTipoTransaccion = By.xpath("//label[contains(text(), 'Tipo de Transacci�n')]");
	By xmlFechaInicio= By.xpath("//label[contains(text(), 'Fecha Inicio')]");
	By xmlNo_tarjeta = By.xpath("//label[contains(text(), 'N�mero de Tarjeta')]");
	By xmlDistrito = By.xpath("//label[contains(text(), 'Distrito')]");
	By xmlTipoTrajeta = By.xpath("//label[contains(text(), 'Tipo de Tarjeta')]");
	By xmlFechaFin = By.xpath("//label[contains(text(), 'Fecha Fin')]");
	By xmlNo_Autorizacion = By.xpath("//label[contains(text(), 'N�mero Autorizaci�n')]");
	By xmlTienda = By.xpath("//label[contains(text(), 'Tienda')]");
	
	By Plaza = By.xpath("//*[@id=\"formEnvDineroTdc:cmbPlaza_label\"]");
	By Plazabogota = By.xpath("//li[contains(text(), '10BGA BOGOTA')]");
	By Plazab = By.xpath("//li[contains(text(), '10BCA BUCARAMANGA')]");
	
	By Transaccion = By.xpath("//label[@id=\"formEnvDineroTdc:cmbTipoTransaccDatos_label\"]");
	By TransaccionC = By.xpath("//li[contains(text(), 'C Corresponsal')]");
	By TransaccionD = By.xpath("//li[contains(text(), 'D Devoluci�n')]");
	By TransaccionV = By.xpath("//li[contains(text(), 'V Venta')]");
	
	By Tipotarjeta = By.xpath("(//span[@class=\"ui-icon ui-icon-triangle-1-s ui-c\"])[5]");
	By TipotarjetaT = By.xpath("//li[contains(text(), 'T Todos')]");
	By TipotarjetaC = By.xpath("//li[contains(text(), 'C Cr�dito')]");
	By TipotarjetaD = By.xpath("//li[contains(text(), 'D D�bito')]");
	By TipotarjetaV = By.xpath("//li[contains(text(), 'V Vale Electr�nico')]");
	

	By xmlSelectPlaza = By.xpath("//*[@id='formEnvDineroTdc:cmbPlaza']");
	String xmlPlaza1 = "//*[@id='formEnvDineroTdc:cmbPlaza_panel']/div/ul/li";
	By xmlSelectDistrito = By.xpath("//*[@id='formEnvDineroTdc:tiendaDis']");
	String xmlDistrito1 = "//*[@id='formEnvDineroTdc:tiendaDis_panel']/div/ul/li";
	By xmlSelectTienda = By.xpath("//*[@id='formEnvDineroTdc:cmbTienda']");
	String xmlTienda1 = "//*[@id='formEnvDineroTdc:cmbTienda_items']/li";
	By xmlSelectTipoTransaccion = By.xpath("//*[@id='formEnvDineroTdc:cmbTipoTransaccDatos']");
	String xmlTipoTransaccion1 = "//*[@id='formEnvDineroTdc:cmbTipoTransaccDatos_items']/li";
	By xmlSelectTipoTarjeta = By.xpath("//*[@id='formEnvDineroTdc:cmbTipoTarjeta']");
	String xmlTipoTarjeta =  "//*[@id='formEnvDineroTdc:cmbTipoTarjeta_items']/li";
	
	By xmlNoTransaccion = By.xpath("//*[@id='formEnvDineroTdc:txtNumTarjeta']");
	By xmlNoTarjeta = By.xpath("//*[@id='formEnvDineroTdc:txtNumTarjeta']");
	By xmlNoAutorizacion = By.xpath("//*[@id='formEnvDineroTdc:txtNumAutorizacion']");
	
	By xmlCalendarInicio = By.xpath("//span[@id='formEnvDineroTdc:cldfechade']/button");
	By xmlCalendarFin = By.xpath("//span[@id='formEnvDineroTdc:cldfechaA']/button");
	String xmlDay = "//td[@data-handler='selectDay']/a[text()='%s']";
	By xmlSelectMonth = By.xpath("//*[@class='ui-datepicker-month']");
	String xmlMonth = "//*[@class='ui-datepicker-month']/option[text()='%s']";
	By xmlSelectYear = By.xpath("//*[@class='ui-datepicker-year']");
	String xmlYear = "//*[@class='ui-datepicker-year']/option[text()='%s']";
	By xmlSelectHour = By.xpath("//dd[@class='ui_tpicker_hour']/div/span");
	By xmlSelectMinute = By.xpath("//dd[@class='ui_tpicker_minute']/div/span");
	
	By xmlConsultar = By.xpath("//*[@id='formEnvDineroTdc:btnBuscar']");
	By xmlExportar = By.xpath("//*[@id='formEnvDineroTdc:btnExportar']");
	
	By xmlDivTable = By.xpath("//*[@id='formEnvDineroTdc:gridRepTransaccTdc']");
	String xmltableHead = "//*[@id='formEnvDineroTdc:gridRepTransaccTdc']/div/table/thead/tr/th[%s]"; //th [15]
	String xmltableBody = "//*[@id='formEnvDineroTdc:gridRepTransaccTdc']/div/table/tbody/tr[%1$s]/td[%2$s]"; //tr[15]
	By xmlRegistros = By.xpath("//*[@id='formEnvDineroTdc:gridRepTransaccTdc_paginator_bottom']/span[@class='ui-paginator-current']");
	
	By xmlMensaje = By.xpath("//*[@id='formEnvDineroTdc:msg_container']/div/div/div[2]/span");
	/**
	 * Constructor
	 ******************************************************************************************/
	
	/** Constructor de la clase
	 * @author David Valdez
	 * @param u, TestCase
	 * @return N/A
	 */
	public TransaccionesTDC_SimotiColombia(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}
	
	/**
	 * Metodo para validar que se visualicen los elementos de la pantalla
	 * 
	 * @author Kevin Valadez
	 * @return Retorna true si se visualizan los elementos
	 */
	public Boolean validarPantallaTDC_Transacccion() {
		Boolean exist = false;

		try {
			if (me.existElement(xmlPlaza) && me.existElement(xmlTipoTransaccion)
					&& me.existElement(xmlFechaInicio)&& me.existElement(xmlNo_tarjeta)
					&& me.existElement(xmlDistrito)&& me.existElement(xmlTipoTrajeta)
					&& me.existElement(xmlFechaFin)&& me.existElement(xmlNo_Autorizacion)
					&& me.existElement(xmlTienda)) {

				exist = true;
				u.highLight(xmlPlaza);
				u.highLight(xmlTipoTransaccion);
				u.highLight(xmlFechaInicio);
				u.highLight(xmlNo_tarjeta);
				u.highLight(xmlDistrito);
				u.highLight(xmlTipoTrajeta);
				u.highLight(xmlFechaFin);
				u.highLight(xmlNo_tarjeta);
				u.highLight(xmlNo_Autorizacion);
				u.highLight(xmlTienda);
				testCase.addScreenShotCurrentStep(u, "validarPantalla");
				u.unhighLight(xmlPlaza);
				u.unhighLight(xmlTipoTransaccion);
				u.unhighLight(xmlFechaInicio);
				u.unhighLight(xmlNo_tarjeta);
				u.unhighLight(xmlDistrito);
				u.unhighLight(xmlTipoTrajeta);
				u.unhighLight(xmlFechaFin);
				u.unhighLight(xmlNo_tarjeta);
				u.unhighLight(xmlNo_Autorizacion);
				u.unhighLight(xmlTienda);
		

			}
		} catch (Exception e) {
			testCase.addScreenShotCurrentStep(u, "Error");
		}

		return exist;
	}
	/**
	 * Metodo para validar que se visualicen los elementos de la pantalla
	 * 
	 * @author Kevin Valadez
	 * @return Retorna true si se visualizan los elementos
	 */
	public Boolean validarPantallaTDC() {
		Boolean exist = false;

		try {
			if (me.existElement(xmlTitulo)&& me.existElement(xmlbusquedacontainer)
					&& me.existElement(Folio)&& me.existElement(DatosGenerales)) {

				exist = true;
				u.highLight(xmlTitulo);
				u.highLight(xmlbusquedacontainer);
				u.highLight(Folio);
				u.highLight(DatosGenerales);
				testCase.addScreenShotCurrentStep(u, "validarPantalla");
				u.unhighLight(xmlTitulo);
				u.unhighLight(xmlbusquedacontainer);
				u.unhighLight(Folio);
				u.unhighLight(DatosGenerales);
		

			}
		} catch (Exception e) {
			testCase.addScreenShotCurrentStep(u, "Error");
		}

		return exist;
	}
	
	/**Metodo para selecionar la transaccion
	 * @autor Kevin Valadez
	 * @param opcion
	 * @return Retorna exist si la opcion es seleccionada y se le da click
	 */
	public Boolean Busquedapor(String opcion) {
		Boolean exist = false;

	switch (opcion) {
	case "Folio":
		clickBoton(Folio);
		exist = true;
		break;

	case "Datos Generales":
		clickBoton(DatosGenerales);
		exist = true;
		break;
	default:
		break;
	}
	return exist;
	}
	
	/**Metodo para selecionar la transaccion
	 * @autor Kevin Valadez
	 * @param opcion
	 * @return Retorna exist si la opcion es seleccionada y se le da click
	 */
	public Boolean DatosPlaza(String opcion) {
		Boolean exist = false;

		switch (opcion) {
		case "10BGA BOGOTA":
			clickopcion(Plaza);
			clickopcion(Plazabogota);
			exist = true;
			break;

		case "10BCA BUCARAMANGA":
			clickopcion(Plaza);
			clickopcion(Plazab);
			exist = true;
			break;
		}

		return exist;

	}
	
	/**Metodo para selecionar la transaccion
	 * @autor Kevin Valadez
	 * @param opcion
	 * @return Retorna exist si la opcion es seleccionada y se le da click
	 */
	public Boolean TipoTarjeta(String opcion) {
		Boolean exist = false;

		switch (opcion) {
		case "C Cr�dito":
			clickopcion(Tipotarjeta);
			clickopcion(TipotarjetaC);
			exist = true;
			break;

		case "D D�bito":
			clickopcion(Tipotarjeta);
			clickopcion(TipotarjetaD);
			exist = true;
			break;
			
		case "V Vale Electr�nico":
			clickopcion(Tipotarjeta);
			clickopcion(TipotarjetaV);
			exist = true;
			break;
	   case "T Todos":
			clickopcion(Tipotarjeta);
			clickopcion(Tipotarjeta);
			exist = true;
			break;
		
		}

		return exist;

	}
	
	/**Metodo para selecionar la transaccion
	 * @autor Kevin Valadez
	 * @param opcion
	 * @return Retorna exist si la opcion es seleccionada y se le da click
	 */
	public Boolean Transaccion(String opcion) {
		Boolean exist = false;

		switch (opcion) {
		case "T Todos":
			clickopcion(Transaccion);
			clickopcion(TransaccionV);
			exist = true;
			break;
		case "C Corresponsal":
			clickopcion(Transaccion);
			clickopcion(TransaccionC);
			exist = true;
			break;

		case "D Devoluci�n":
			clickopcion(Transaccion);
			clickopcion(TransaccionD);
			exist = true;
			break;
			
		case "V Venta":
			clickopcion(Transaccion);
			clickopcion(TransaccionV);
			exist = true;
			break;
		}

		return exist;

	}
	
	
	
	public void clickopcion(By by) {
		u.highLight(by);
		testCase.addScreenShotCurrentStep(u, "clickopcion");
		u.unhighLight(by);
		u.click(by);
	}
	

	public void clickBoton(By by) {
		u.highLight(by);
		testCase.addScreenShotCurrentStep(u, "clickBoton");
		u.unhighLight(by);
		u.click(by);
	}

	public void Numerotajeta_NumeroAutorizacion(String No_tarjeta, String No_autorizacion) {
	System.out.println(No_tarjeta);
	if(No_tarjeta!="")
		typingInput(xmlNoTarjeta,No_tarjeta,"Numero de Tarjeta");
	System.out.println(No_tarjeta);
	if(No_autorizacion!="")
		typingInput(xmlNoAutorizacion,No_autorizacion,"Numero de Autorizaci�n");
	}

	/** Constructor de la clase
	 * @author David Valdez
	 * @param u, TestCase
	 * @return N/A
	 */
	public void click_on_busqueda() {
		u.highLight(busqueda);
		u.click(busqueda);
	}
	

	public void clickConsultaBTN() {
		u.highLight(xmlConsultarBtn);
		testCase.addScreenShotCurrentStep(u, "Consultar");
		u.unhighLight(xmlConsultarBtn);
		u.mouseOverAndClick(xmlConsultarBtn);
		u.waitForLoadPage();
		testCase.addScreenShotCurrentStep(u, "clickTransaccionesTDC");
	}
	public void clickExportar() {
		u.highLight(xmlExportarBtn);
		u.click(xmlExportarBtn);
	}

	/** Metodo para seleccionar busqueda por Datos Generales
	 * @author Jorge Ruiz
	 * @param 
	 * @return 
	 */
	public void busquedaDatosGenerales(String plaza, String distrito, String tienda, String t_transaccion, String t_tarjeta, String no_transaccion, String no_tarjeta) {
		u.highLight(busquedaDatosGenerales);
		u.click(busquedaDatosGenerales);
		testCase.addScreenShotCurrentStep("Busqueda por Datos Generales.");
		u.unhighLight(busquedaDatosGenerales);
		u.hardWait(2);
		System.out.println(plaza);
		if(plaza!="")
			selectOption(xmlSelectPlaza,xmlPlaza1,plaza,"Plaza");
		u.hardWait(5);
		System.out.println(distrito);
		if(distrito!="")
			selectOption(xmlSelectDistrito,xmlDistrito1,distrito,"Distrito");
		System.out.println(tienda);
		if(tienda!="")
			selectOption(xmlSelectTienda,xmlTienda1,tienda,"Tienda");
		System.out.println(t_transaccion);
		if(t_transaccion!="")
			selectOption(xmlSelectTipoTransaccion,xmlTipoTransaccion1,t_transaccion,"Tipo de Transaccion");
		System.out.println(t_tarjeta);
		if(t_tarjeta!="")
			selectOption(xmlSelectTipoTarjeta,xmlTipoTarjeta,t_tarjeta,"Tipo de Tarjeta");
		System.out.println(no_transaccion);
		if(no_transaccion!="")
			typingInput(xmlNoTransaccion,no_transaccion,"Numero de Transaccion");
		System.out.println(no_tarjeta);
		if(no_tarjeta!="")
			typingInput(xmlNoTarjeta,no_tarjeta,"Numero de Tarjeta");
	}

	/**
	 * Metodo para seleccionar la una opcion si el modo de visualizacion es un SELECT
	 * @param opcion
	 * @return Retorna true si se selecciono correctamente la opcion seleccionada proporcionado
	 * @author Jorge Ruiz
	 */
	public Boolean selectOption(By type, String xmlsearch, String opcion, String tipo) {
		Boolean correct = false;
		
		u.click(type);
		List<WebElement> allOptions = u.getDriver().findElements(By.xpath(xmlsearch));
		
		for(WebElement option:allOptions){
			if(option.getText().trim().equals(opcion)) {
				option.click();
				correct = true;
				break;
			}
		}
		
		u.highLight(type);
		testCase.addScreenShotCurrentStep(u, tipo+" Seleccionado");
		u.unhighLight(type);
		
		return correct;
	}
	/**
	 * Metodo para ingresar texto en un input
	 * @param opcion
	 * @return N/A
	 * @author Jorge Ruiz
	 */
	public void typingInput(By by, String dato, String tipo) {
		u.sendKeys(by, dato);
		u.highLight(by);
		testCase.addScreenShotCurrentStep(u, tipo + " Input");
		u.unhighLight(by);
	}
	/**
	 * Metodo para Seleccionar la fechas de consulta
	 * @param opcion
	 * @return N/A
	 * @author Jorge Ruiz
	 */
	public void seleccionarFechas(String f_ini, String f_fin)
	{
		seleccionarFecha(f_ini,xmlCalendarInicio);
		seleccionarFecha(f_fin,xmlCalendarFin);
	}

	public Boolean checarMayusculas(String text)
	{
		char[] chars = text.toCharArray();

		if(chars[0] != Character.toUpperCase(chars[0]))
			return false;
		int i = 0;
		for(char characters : chars )
		{
			if(characters == ' ' && (chars[i+1] != Character.toUpperCase(chars[i+1])))
				return false;
			i++;
		}
		return true;
	}
	/**
	 * Metodo para Seleccionar la fechas y horas de consulta
	 * @param opcion
	 * @return N/A
	 * @author Jorge Ruiz
	 */
	public void seleccionarFecha(String fecha,By type)
	{
		String [] meses = {"Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dic"};
		String f = fecha.substring(0,fecha.indexOf(' '));
		String day = String.valueOf(Integer.parseInt(f.substring(0,f.indexOf('/'))));
		f = f.substring(f.indexOf('/')+1,f.length()); 
		String month = meses[Integer.parseInt(f.substring(0,f.indexOf('/')))-1];
		String year = f.substring(f.indexOf('/')+1,f.length());
		String h = fecha.substring(fecha.indexOf(' ')+1,fecha.length());
		String hour = String.valueOf(Integer.parseInt(h.substring(0,h.indexOf(':')))/* * 4.34783*/);
		String minute = String.valueOf(Integer.parseInt(h.substring(h.indexOf(':')+1,h.length()))/* * 1.69492*/);

		u.click(type);
		u.hardWait(1);
		u.highLight(xmlSelectYear);
		u.click(xmlSelectYear);

		if(Integer.parseInt(year) < 2013)
			u.getDriver().findElement(By.xpath(String.format(xmlYear,"2013"))).click();
		u.click(xmlSelectYear);
		u.getDriver().findElement(By.xpath(String.format(xmlYear,year))).click();
		u.click(xmlSelectMonth);
		u.getDriver().findElement(By.xpath(String.format(xmlMonth,month))).click();
		u.getDriver().findElement(By.xpath(String.format(xmlDay,day))).click();
		moveSlider(xmlSelectHour,hour);
		moveSlider(xmlSelectMinute,minute);
		
	}
	/**
	 * Metodo para mover slider de horas y minutos
	 * @param type y porcentaje de movimiento 
	 * @return N/A
	 * @author Jorge Ruiz
	 */
	public void moveSlider(By type,String porcentage) 
	{
		WebElement element = u.getDriver().findElement(type);
		element.click();
		u.hardWait(1);
		for(int i = 0; i < 61; i++)
			element.sendKeys(Keys.ARROW_LEFT);
		for(int i = 0; i < Integer.parseInt(porcentage); i++)
			element.sendKeys(Keys.ARROW_RIGHT);
	}
	
	/**
	 * Metodo para dar click a boton de consulta
	 * @param opcion
	 * @return N/A
	 * @author Jorge Ruiz
	 */
	public void Consultar()
	{
		u.highLight(xmlConsultar);
		testCase.addScreenShotCurrentStep("Consultar");
		u.click(xmlConsultar);
		u.unhighLight(xmlConsultar);
		u.hardWait(5);
	}
	
	/**
	 * Metodo para dar click a boton de exportar
	 * @param opcion
	 * @return N/A
	 * @author Jorge Ruiz
	 */
	public void Exportar()
	{
		u.highLight(xmlExportar);
		testCase.addScreenShotCurrentStep("Exportar");
		u.click(xmlExportar);
		u.unhighLight(xmlExportar);
		u.hardWait(5);
	}
	/**
	 * Metodo para validar los headers de la tabla de transacciones 
	 * @param string
	 * @return true si la informacion en el xlsx corresponde a la consulta realizada, false en caso contrario
	 * @author Jorge Ruiz
	 */
	public Boolean validarHeadersTabla()
	{ 
		u.scrollToElement(xmlDivTable);
		int cont = 0;
		String[] val = {"No.", "Folio","Fecha","Plaza","Tienda","Caja","Afiliaci�n","Tarjeta","Emisor","Monto","Autorizaci�n","Reversado","Estatus","Error","Casa Valera"};
		ArrayList<String> values = new ArrayList<String>(Arrays.asList(val));
		int i = 1;
		for(String value : values)
		{
			String text = u.getDriver().findElement(By.xpath(String.format(xmltableHead, String.valueOf(i)))).getText().trim();
			if(!(text.equals(values.get(i-1))))
				return false;
			i++;
		}
		return true;
	}
	
	/**
	 * Metodo para validar que aparezca el mensaje <msg>
	 * 
	 * @param string
	 * @return retorna true si se muestra el mensaje o false en caso contrario
	 * @author Jorge Ruiz
	 */
	public Boolean validarConsulta(SQLResult queryPopResult)
	{
		String[] val = {"FOLIO","TO_CHAR(T.CREATION_DATE,'DD-MM-YYYYHH24:MI:SS')--FECHA","PLAZA","TIENDA","CAJA","AFFILIATION"
				,"SUBSTR(T.TRACK1,0,6)||LPAD('X',LENGTH(T.TRACK1)-10,'X')||SUBSTR(T.TRACK1,-4)--TARJETA"
				,"ISSUER","AMOUNT","SW_AUTH","DECODE(T.REVERSED,1,'SI','NO')--REVERSADO","WM_CODE",/*"TO_CHAR(T.REVERSE_DT,'DD-MM-YYYYHH24:MI:SS')--FECHADEREVERSO"*/
				/*,"SW_REVERSE_CODE","SW_DEV_CODE",*/"DESCRIPTION","DECODE(T.BANK,'OTHER',NULL,'BBVA',NULL,T.ISSUER)--CASAVALERA"};
		ArrayList<String> sqlsearch = new ArrayList<String>(Arrays.asList(val));
		//val = {"No.", "Folio","Fecha","Plaza","Tienda","Caja","Afiliación","Tarjeta","Emisor","Monto","Autorización","Reversado","Estatus","Error","Casa Valera"};
		
		String registers = u.getText(xmlRegistros).trim();
		Integer registros = Integer.parseInt(registers.substring(32,registers.length()));
		
		for(int i = 1; i < registros; i++)
		{
			int j = 2;
			for(String search : sqlsearch)
			{
				String result = queryPopResult.getData(i, search);	
				String text = u.getDriver().findElement(By.xpath(String.format(xmltableBody, String.valueOf(i),String.valueOf(j)))).getText().trim();
				System.out.println(result + " _ " + text);
				if(result.equals(text))
					return false;
				j++;
			}
		}
		
		return true;
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
	 * Metodo para Seleccionar la fechas y horas de consulta
	 * @param opcion
	 * @return N/A
	 * @author Jorge Ruiz
	 */
	public void seleccionarHora(String Horas,By type)
	{
		String h = Horas.substring(Horas.indexOf(' ')+1,Horas.length());
		String hour = String.valueOf(Integer.parseInt(h.substring(0,h.indexOf(':')))/* * 4.34783*/);
		String minute = String.valueOf(Integer.parseInt(h.substring(h.indexOf(':')+1,h.length()))/* * 1.69492*/);

		u.hardWait(1);

		moveSlider(xmlSelectHour,hour);
		moveSlider(xmlSelectMinute,minute);
		
	}
	
	public void seleccionarHoras1(String H_ini)
	{
		seleccionarHora(H_ini,xmlCalendarInicio);
	}
	
	
	public void seleccionarHoras2(String H_Fin)
	{
		seleccionarHora(H_Fin,xmlCalendarFin);
	}
	
	
	/**
	 * Metodo para validar que aparezca el mensaje <msg>
	 * 
	 * @param string
	 * @return retorna true si se muestra el mensaje o false en caso contrario
	 * @author Jorge Ruiz
	 */
	public Boolean validarMensaje(String msg) {
		Boolean exist = false;
		String mensaje = "";
		try
		{
			if (u.isDisplayed(xmlMensaje)) {
				me.scrollToElement(xmlMensaje);
				mensaje = u.getText(xmlMensaje).trim();
				u.highLight(xmlMensaje);
				testCase.addScreenShotCurrentStep(u, "validarMensaje");
				u.unhighLight(xmlMensaje);
				if (mensaje.equals(msg)) {
					exist = true;
				}

			} else {
				testCase.addScreenShotCurrentStep(u, "validarMensaje");
			}

			return exist;
		}catch(Exception e)
		{
			return false;
		}
		
	}
	
	
	
}

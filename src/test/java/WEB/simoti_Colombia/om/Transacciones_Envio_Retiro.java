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
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;;

public class Transacciones_Envio_Retiro {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;

	String Replace = "";
	/*
	 * Localizadores pantalla Indicadores por totales
	 ********************************************************************************************/

	By xmlTitulo = By.xpath("//label[contains(text(), 'Env�os de Dinero - Transacciones')]");
	By xmlTipoTd = By.xpath("//*[@for=\"formRepEnvDineroTransacciones:cmbTipo_focus\"]");
	By xmlTipoSeleccionado = By.xpath("//*[@id=\"formRepEnvDineroTransacciones:cmbTipo\"]");
	By xmlNumeroTransferenciaTd = By.xpath("//*[@id=\"formRepEnvDineroTransacciones:j_idt416\"]");
	By xmlNumeroTransferencia = By.xpath("//*[@id=\"formRepEnvDineroTransacciones:txtNumTransaction\"]");
	By xmlFechaInicioTd = By.xpath("//*[@id=\"formRepEnvDineroTransacciones:j_idt424\"]");
	By xmlFechaInicio = By.xpath("//*[@id=\"formRepEnvDineroTransacciones:cldfechade\"]/button");
	By xmlCalendarioFechaInicio = By
			.xpath("//table[@id='formRepEnvDineroTransacciones:panelFiltros']/tbody/tr[3]/td[2]/span/button");
	By xmlClienteTd = By.xpath("//*[@id=\"formRepEnvDineroTransacciones:j_idt408\"]");
	By xmlTelefonoTd = By.xpath("//*[@id=\"formRepEnvDineroTransacciones:j_idt419\"]");
	By xmlFechaFinTd = By.xpath("//*[@id=\"formRepEnvDineroTransacciones:j_idt427\"]");
	By xmlFechaFin = By.xpath("//*[@id=\"formRepEnvDineroTransacciones:cldfechaA\"]/button");
	By xmlCalendarioFechaFin = By
			.xpath("//table[@id='formRepEnvDineroTransacciones:panelFiltros']/tbody/tr[3]/td[4]/span/button");
	String xmlDayCalendar = "//td/a[text()='%s']";
	By xmlMonthSelect = By.xpath("//select[@data-handler='selectMonth']");
	By xmlYearSelect = By.xpath("//select[@data-handler='selectYear']");
	String xmlSelectValue = "//select[@data-handler='%1$s']/option[text()='%2$s']";
	By xmlIFETd = By.xpath("//*[@id=\"formRepEnvDineroTransacciones:j_idt413\"]");
	By xmlresgistrosencontrados = By.xpath("//td[contains (text(),'Sin resultados')]");
	String xmlDia = "//table[@class='ui-datepicker-calendar']/tbody/tr/td/a[contains(text(), '%s')]";
	By xmlMes = By.xpath("//select[@data-handler='selectMonth']");
	By xmlAnnio = By.xpath("//select[@data-handler='selectYear']");
	By xmlConsultarBtn = By.xpath("//a[contains(text(), 'Consultar')]");
	By xmlExportarBtn = By.xpath("//a[text() ='Exportar']");

	/**
	 * Localizadores Datos receptor
	 ******************************************************************************************/

	By xmlTipo = By.xpath("//*[@id=\"formRepEnvDineroTransacciones:cmbTipo\"]/div[3]/span");
	By xmlTipoE = By.xpath("//*[@id=\"formRepEnvDineroTransacciones:cmbTipo_1\"]");
	By xmlTipoD = By.xpath("//*[@id=\"formRepEnvDineroTransacciones:cmbTipo_0\"]");

	By xmlCliente = By.xpath("//*[@id=\"formRepEnvDineroTransacciones:cmbCliente\"]/div[3]");
	By xmlClienteB = By.xpath("//*[@id=\"formRepEnvDineroTransacciones:cmbCliente_0\"]");
	By xmlClienteR = By.xpath("//*[@id=\"formRepEnvDineroTransacciones:cmbCliente_1\"]");

	By xmlIFE = By.xpath("//*[@id=\"formRepEnvDineroTransacciones:txtIfe\"]");
	By xmlTransaccion = By.xpath("//*[@name ='formRepEnvDineroTransacciones:txtNumTransaction']");
	By xmlTelefono = By.xpath("//*[@id=\"formRepEnvDineroTransacciones:txtCelTel\"]");

	By xmlMensaje = By.xpath("//*[@id=\"formRepEnvDineroTransacciones:msg_container\"]/div/div/div[2]/span");

	String xmlEncabezadosTabla = "//fieldset[2]/div/span/div/div/div/table/thead/tr/th[%s]/span";
	String xmlDatosTabla = "//fieldset[2]/div/span/div/div/div/table/tbody/tr[%s]";
	By xmlNumResultados = By.xpath("//span[@class='ui-paginator-current']");
	By xmlSeleccionResultados = By.xpath("//div[@id='formRepEnvDineroTransacciones:gridEDRepTransacc']");

	String xmltableBody = "//*[@id='formRepEnvDineroTransacciones:gridEDRepTransacc_data']/tr[%1$s]/td[%2$s]"; // tr[15]
	By xmlRegistros = By.xpath(
			"//*[@id='formRepEnvDineroTransacciones:gridEDRepTransacc_paginator_bottom']/span[@class='ui-paginator-current']");

	By xmlResultados = By.xpath("//tbody[@id='formRepEnvDineroTransacciones:gridEDRepTransacc_data']/tr/td");
	
	/*
	 * Constructor
	 ********************************************************************************************/
	public Transacciones_Envio_Retiro(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		u.getDriver().manage().window().maximize();
		this.me = new MetodosExtra(u, testCase);
	}

	/**
	 * Metodo para validar que se visualicen los elementos de la pantalla
	 * 
	 * @author Maria Due�as
	 * @return Retorna true si se visualizan los elementos
	 */
	public Boolean validarPantallaE() {
		Boolean exist = false;

		try {
			if (me.existElement(xmlTitulo) && me.existElement(xmlTipoTd) && me.existElement(xmlConsultarBtn)
					&& me.existElement(xmlExportarBtn) && me.existElement(xmlNumeroTransferenciaTd)
					&& me.existElement(xmlFechaInicioTd) && me.existElement(xmlClienteTd)
					&& me.existElement(xmlTelefonoTd) && me.existElement(xmlFechaFinTd) && me.existElement(xmlIFETd)) {

				exist = true;

				u.highLight(xmlTitulo);
				u.highLight(xmlTipoTd);
				u.highLight(xmlNumeroTransferenciaTd);
				u.highLight(xmlFechaInicioTd);
				u.highLight(xmlClienteTd);
				u.highLight(xmlTelefonoTd);
				u.highLight(xmlFechaFinTd);
				u.highLight(xmlIFETd);
				u.highLight(xmlConsultarBtn);
				u.highLight(xmlExportarBtn);

				testCase.addScreenShotCurrentStep(u, "validarPantalla1");
				// testCase.addScreenShotCurrentStep(u, "validarPantalla2");

				u.unhighLight(xmlTitulo);
				u.unhighLight(xmlTipoTd);
				u.unhighLight(xmlNumeroTransferenciaTd);
				u.unhighLight(xmlFechaInicioTd);
				u.unhighLight(xmlClienteTd);
				u.unhighLight(xmlTelefonoTd);
				u.unhighLight(xmlFechaFinTd);
				u.unhighLight(xmlIFETd);
				u.unhighLight(xmlConsultarBtn);
				u.unhighLight(xmlExportarBtn);

			}
		} catch (Exception e) {
			testCase.addScreenShotCurrentStep(u, "Error");
		}

		return exist;
	}

	public By getIFE() {
		return xmlIFE;
	}

	public By getTelefono() {
		return xmlTelefono;
	}

	public By getConsultarBtn() {
		return xmlConsultarBtn;
	}

	public By getNoTransferencia() {
		return xmlNumeroTransferencia;
	}

	public By getExportarBtn() {
		return xmlExportarBtn;
	}

	public By getxmlresgistrosencontrados() {
		return xmlresgistrosencontrados;
	}

	/**
	 * Metodo para dar click en el boton Exportar
	 * 
	 * @author Maria Due�as
	 */
	public void clickExportarBtn() {
		u.highLight(xmlExportarBtn);
		testCase.addScreenShotCurrentStep(u, "Exportar");
		u.unhighLight(xmlExportarBtn);
		u.click(xmlExportarBtn);
		testCase.addScreenShotCurrentStep(u, "clickExportar");
		u.getDriver().navigate().refresh();
	}

	/*
	 * Metodos extra
	 *****************************************************************************************/
	public Boolean selectOption(By select, By opciones, String opcion) {
		Boolean existOption = false;
		// WebElement element = me.explicitWaitToBeClickable(select, 30);
		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(select));
		u.hardWait(2);
		element.click();
		List<WebElement> allOptions = u.getDriver().findElements(opciones);

		for (WebElement option : allOptions) {
			// System.out.print("-"+option.getText()+"-"+opcion+"-\n");
			if (option.getText().equals(opcion)) {// busca la opcion que se pasa por parametro
				u.hardWait(3);
				option.click(); // da clic a esa opcion
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

	public void typingInput(By by, String dato) {
		u.sendKeys(by, dato);
		u.highLight(by);
		testCase.addScreenShotCurrentStep(u, "typingInput");
		u.unhighLight(by);
	}

	public void clickBoton(By by) {
		u.highLight(by);
		testCase.addScreenShotCurrentStep(u, "clickBoton");
		u.unhighLight(by);
		u.click(by);
	}

	public void clickopcion(By by) {
		u.highLight(by);
		testCase.addScreenShotCurrentStep(u, "clickopcion");
		u.unhighLight(by);
		u.click(by);
	}

	/**
	 * Metodo para validar que se seleccionan las opciones tipo y cliente
	 * 
	 * @param string
	 * @return retorna true si se seleccionan las opciones o false en caso contrario
	 * @author Kevin Valadez
	 */
	public Boolean DatosTipo(String opcion) {
		Boolean exist = false;

		switch (opcion) {
		case "ENV Env�o":
			clickopcion(xmlTipo);
			clickopcion(xmlTipoE);
			exist = true;
			break;

		case "DISP Retiro/Disposici�n":
			clickopcion(xmlTipo);
			clickopcion(xmlTipoD);
			exist = true;
			break;
		}

		return exist;

	}

	public Boolean DatosCliente(String opcion2) {
		Boolean exist = false;

		switch (opcion2) {
		case "R Remitente":
			clickopcion(xmlCliente);
			clickopcion(xmlClienteR);
			exist = true;
			break;

		case "B Beneficiario":
			clickopcion(xmlCliente);
			clickopcion(xmlClienteB);
			exist = true;
			break;
		}
		return exist;
	}
	
	/**
	 * Metodo para validar que se muestre resultados en el grid
	 * @return Retorna true o false si se muestran resultados
	 * @author Andrea Contreras
	 */
	public Boolean validarExistanResultados() {
		Boolean validacion = false;
		String resultado = "";
		
		u.scrollToElement(By.id("formRepEnvDineroTransacciones:gridEDRepTransacc"));
		
		if(me.existElement(xmlResultados)) {
			resultado = u.getText(xmlResultados);	
			u.highLight(xmlResultados);	
			testCase.addScreenShotCurrentStep(u, "validarExistanResultados");
			u.unhighLight(xmlResultados);	
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
	 * 
	 * @param string
	 * @return retorna true si se muestra el mensaje o false en caso contrario
	 * @author Jorge Ruiz
	 */
	public Boolean validarMensaje(String msg) {
		Boolean exist = false;
		String mensaje = "";

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
	}

	/**
	 * Metodo para obtener el valor de una celda en un archivo XLSX
	 * 
	 * @param sheet hoja de trabajo, int r fila que se busca, int c columna que se
	 *              busca
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
	 * Metodo para validar los headers de la tabla de transacciones
	 * 
	 * @param string
	 * @return true si la informacion en el xlsx corresponde a la consulta
	 *         realizada, false en caso contrario
	 * @author Jorge Ruiz
	 */
	public Boolean validarHeadersTabla(ArrayList<String> values, ArrayList<String> cellvalues) {
		int cont = 0;
		for (String value : values) {
			if (!value.equals(cellvalues.get(cont))) {
				System.out.println(value + ":" + cellvalues.get(cont));
				System.out.println("No coinciden los encabezados o datos de consulta.");
				return false;
			}
			cont++;

		}
		return true;
	}

	
	/**
	 * Metodo auxiliar para el metodo de mostrar los datos de la tabla de
	 * indicadores por totales, el cual sirve para obtener los dias en cada mes
	 * 
	 * @param string
	 * @return entero que corresponde al numero de dias en el mes
	 * @author Jorge Ruiz
	 */
	public Integer diasenMes(String mes) {
		Integer dias = 0;
		Month month = null;
		switch (mes) {
		case "Enero": {
			month = month.JANUARY;
			dias = month.length(false);
		}
		case "Febrero": {
			month = month.FEBRUARY;
			dias = month.length(false);
		}
		case "Marzo": {
			month = month.MARCH;
			dias = month.length(false);
		}
		case "Abril": {
			month = month.APRIL;
			dias = month.length(false);
		}
		case "Mayo": {
			month = month.MAY;
			dias = month.length(false);
		}
		case "Junio": {
			month = month.JUNE;
			dias = month.length(false);
		}
		case "Julio": {
			month = month.JULY;
			dias = month.length(false);
		}
		case "Agosto": {
			month = month.AUGUST;
			dias = month.length(false);
		}
		case "Septiembre": {
			month = month.SEPTEMBER;
			dias = month.length(false);
		}
		case "Octubre": {
			month = month.OCTOBER;
			dias = month.length(false);
		}
		case "Noviembre": {
			month = month.NOVEMBER;
			dias = month.length(false);
		}
		case "Diciembre": {
			month = month.DECEMBER;
			dias = month.length(false);
		}
		default: {
			dias = 30;
		}

		}
		return dias;
	}

	/**
	 * Metodo para mostrar la informacion en la tabla de transacciones
	 * 
	 * @param string
	 * @return true si la informacion en el xlsx corresponde a la consulta
	 *         realizada, false en caso contrario
	 * @author Jorge Ruiz
	 */
	public Boolean validarDatosTabla(String filename, String number) throws IOException {
		u.scrollToElement(xmlSeleccionResultados);

		Boolean correct = false;
		String formato = "ddMMyyyy";
		SimpleDateFormat formatFecha = new SimpleDateFormat(formato);
		LocalDateTime datetime = LocalDateTime.now();

		String nombreArchivo = filename;
		String fecha = DateTimeFormatter.ofPattern(formato).format(datetime);

		String pathYourSystem = System.getProperty("user.home") + "\\Downloads\\" + nombreArchivo + fecha + number
				+ ".xlsx";
		File fichero = new File(pathYourSystem);
		System.out.println(pathYourSystem);

		FileInputStream inputStream = new FileInputStream(new File(pathYourSystem));
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);

		/*
		 * String aplicacion = "SIMOTI"; String apartado = "Envío de Dinero"; String
		 * reporte = "Reporte de Transferencias";
		 * 
		 * String tipo = "DISP"; String fechainicio = "05/01/2023"; String ife = "";
		 * String cliente = "Beneficiario"; String fechafin = "08/01/2023"; String
		 * telefono = "8100000001"; String transaccion = "OXXO76";
		 */

		/// Integer days = diasenMes(mes)+2;

		String no = u.getText(By.xpath(String.format(xmlEncabezadosTabla, "1"))).trim();
		String color = u.getText(By.xpath(String.format(xmlEncabezadosTabla, "2"))).trim();
		String row = "";
		String registers = u.getText(xmlNumResultados).trim();
		Integer numreg = Integer.parseInt(registers.substring(32, registers.length()));
		System.out.println("Registos: " + numreg);
		ArrayList<String> datavalues = new ArrayList<String>();
		datavalues.add("SIMOTI");
		datavalues.add("Env�o de Dinero");
		datavalues.add("Reporte de Transferencias");

		/*
		 * System.out.println(u.getText(xmlTipo));
		 * System.out.println(u.getText(xmlCliente));
		 * System.out.println(u.getText(xmlIFE));
		 * System.out.println(u.getText(xmlTelefono));
		 * System.out.println(u.getText(xmlFechaFin));
		 * System.out.println(u.getText(xmlFechaInicio));
		 */
		datavalues.add("DISP"); // //label[@id='formRepEnvDineroTransacciones:cmbTipo_label']
		datavalues.add("05/01/2023");// //*[@name ='formRepEnvDineroTransacciones:cldfechade_input']
		datavalues.add("0747116375842");// //*[@name ='formRepEnvDineroTransacciones:txtIfe']
		datavalues.add("Beneficiario"); // //label[@id='formRepEnvDineroTransacciones:cmbCliente_label']
		datavalues.add("08/01/2023"); // //*[@name ='formRepEnvDineroTransacciones:cldfechaA_input']
		datavalues.add("8100000001");// //*[@name ='formRepEnvDineroTransacciones:txtCelTel']
		// datavalues.add("OXXO76");// //*[@name
		// ='formRepEnvDineroTransacciones:txtNumTransaction']

		ArrayList<String> cellvalues = new ArrayList<String>();
		cellvalues.add(datoCelda(firstSheet, 1, 6));
		cellvalues.add(datoCelda(firstSheet, 2, 6));
		cellvalues.add(datoCelda(firstSheet, 3, 6));
		cellvalues.add(datoCelda(firstSheet, 5, 3));
		cellvalues.add(datoCelda(firstSheet, 6, 3));
		cellvalues.add(datoCelda(firstSheet, 7, 3));
		cellvalues.add(datoCelda(firstSheet, 5, 10));
		cellvalues.add(datoCelda(firstSheet, 6, 10));
		cellvalues.add(datoCelda(firstSheet, 7, 10));
		// cellvalues.add(datoCelda(firstSheet, 7, 18));

		// 2,5,7,9,11,12,13,17,19,20, 22
		// C,F,H,J,L, M, N, R, T, U, W
		Integer[] posicionesheaders = { 2, 5, 7, 9, 11, 12, 13, 17, 19, 20, 22 };
		String[] values = new String[30];

		if (!validarHeadersTabla(datavalues, cellvalues)) {
			System.out.println("No coinciden los encabezados o datos de consulta.");
			return false;
		}
		/*
		 * if(!validarHeadersTabla(aplicacion, apartado, reporte, tipo, fechainicio,
		 * ife, cliente, fechafin, telefono, transaccion, cellapp, cellapartado,
		 * cellreporte, celltipo, cellfechainicio, cellife, cellcliente, cellfechafin,
		 * celltelefono, celltransaccion)) {
		 * System.out.println("No coinciden los encabezados o datos de consulta.");
		 * return false; }
		 */

		for (int j = 1; j <= numreg; j++) {
			row = String.format(xmlDatosTabla, Integer.toString(j));
			Integer rownum = j + 10;
			System.out.println("Row: " + j);
			for (int i = 1; i <= 10; i++) {
				String d = u.getText(By.xpath(row.concat(String.format("/td[%s]", Integer.toString(i + 1))))).trim();
				values[i - 1] = datoCelda(firstSheet, rownum, posicionesheaders[i - 1]);
				// System.out.println(d+ " - " + values[i-1]);

				if (!(values[i - 1] == null && d.equals("")))
					if (!d.contains(values[i - 1]))
						return false;

			}
			for (int i = 11; i <= 18; i++) {
				if (i != 15) {
					Integer cellnum = i + 11;
					String d = u.getText(By.xpath(row.concat(String.format("/td[%s]", Integer.toString(i + 1)))))
							.trim();
					values[i] = datoCelda(firstSheet, rownum, cellnum);
					// System.out.println(d+ " - " + values[i]);
					if (values[i] != null) {
						values[i] = values[i].substring(8, values[i].length());
						if (!d.contains(values[i]))
							return false;
					} else {

						if (d.equals(""))
							continue;
					}

				}
			}
		}
		return true;
	}

	public Boolean selectDate(String fecha, By calendario) {
		Boolean correct = false;

		me.scrollToElement(calendario);
		u.highLight(calendario);
		testCase.addScreenShotCurrentStep(u, "clickCalendario");
		u.unhighLight(calendario);
		u.click(calendario);

		String[] f = fecha.split("\\*");
		// System.out.print(f + "\n");
		Boolean anio = selectOption1(f[2], xmlAnnio);

		Boolean mes = selectOption1(f[1], xmlMes);

		String dia = String.format(xmlDia, f[0]);
		u.highLight(By.xpath(dia));
		testCase.addScreenShotCurrentStep(u, "clickDia");
		u.unhighLight(By.xpath(dia));
		u.click(By.xpath(dia));

		if (mes == true && anio == true) {
			correct = true;
		}

		return correct;
	}

	public Boolean selectOption1(String option, By by) {
		Boolean exist = false;

		try {
			Select selectObject = new Select(u.getDriver().findElement(by));
			selectObject.selectByVisibleText(option);
			exist = true;
		} catch (Exception e) {
			exist = false;
		}

		u.highLight(by);
		testCase.addScreenShotCurrentStep(u, "selectOption");
		u.unhighLight(by);

		return exist;
	}

	public Boolean fechas(String fInicio, String fFin) {
		Boolean correct = false;

		try {

			Boolean fI = selectDate(fInicio, xmlFechaInicio);
			Boolean fF = selectDate(fInicio, xmlFechaFin);

			if (fI && fF)
				correct = true;

		} catch (Exception e) {

		}

		return correct;
	}

	/**
	 * Metodo para validar que aparezca el mensaje <msg>
	 * 
	 * @param integer correspondiente al mes
	 * @return retorna String correspondiente al mes
	 * @author Jorge Ruiz
	 */
	public String getMonth(Integer month) {
		String[] months = { "Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic" };
		return months[month - 1];
	}

	/**
	 * Metodo para validar que aparezca el mensaje <msg>
	 * 
	 * @param string
	 * @return retorna true si se muestra el mensaje o false en caso contrario
	 * @author Jorge Ruiz
	 */
	public Boolean selectDate(String tipo, String date) {
		System.out.print(date);
		String day = String.valueOf(Integer.parseInt(date.substring(0,date.indexOf('/'))));
		date = date.substring(date.indexOf('/') + 1, date.length());
		Integer month = Integer.parseInt(date.substring(0, date.indexOf('/')));
		date = date.substring(date.indexOf('/') + 1, date.length());
		String year = date;

		if (tipo == "Inicio")
			u.click(xmlCalendarioFechaInicio);
		else if (tipo == "Fin")
			u.click(xmlCalendarioFechaFin);

		
		u.hardWait(3);
		u.click(xmlYearSelect);
		u.click(By.xpath(String.format(xmlSelectValue, "selectYear", year)));
		u.hardWait(1);
		u.click(xmlMonthSelect);
		u.click(By.xpath(String.format(xmlSelectValue, "selectMonth", getMonth(month))));
		u.hardWait(1);
		u.click(By.xpath(String.format(xmlDayCalendar, day)));

		return true;
	}

	public Boolean validarConsulta(SQLResult queryPopResult) {
		String[] val = { "Folio", "Fecha", "Plaza", "Tienda", "Caja", "No_Transferencia", "NO_IFE", "Telefono",
				"Beneficiario", "Monto" };
		ArrayList<String> sqlsearch = new ArrayList<String>(Arrays.asList(val));
		// val = {"No.",
		// "Folio","Fecha","Plaza","Tienda","Caja","Afiliación","Tarjeta","Emisor","Monto","Autorización","Reversado","Estatus","Error","Casa
		// Valera"};
		int a = val.length;
		String registers = u.getText(xmlRegistros).trim();
		Integer registros = Integer.parseInt(registers.substring(32, registers.length()));
		String text = "";
		for (int i = 1; i < registros; i++) {
			int j = 2;
			for (String search : sqlsearch) {
				String result = queryPopResult.getData(i, search);
				if (j == val.length)
					text = u.getDriver()
							.findElement(By.xpath(String.format(xmltableBody, String.valueOf(i), String.valueOf(12))))
							.getText().trim();
				text = u.getDriver()
						.findElement(By.xpath(String.format(xmltableBody, String.valueOf(i), String.valueOf(j))))
						.getText().trim();
				System.out.println(result + " _ " + text);
				if (!(text == null && result.equals("")))
					if (!result.contains(text))
						return false;
				j++;
			}
		}

		return true;
	}

	public Boolean imprimeConsulta(SQLResult queryPopResult) {
		String[] val = { "Folio", "Fecha", "Plaza", "Tienda", "Caja", "No_Transferencia", "NO_IFE", "Telefono",
				"Beneficiario", "Monto", "TIPO_IDENTIFICACION" };
		ArrayList<String> sqlsearch = new ArrayList<String>(Arrays.asList(val));

		for (int i = 0; i < 1; i++) {
			int j = 2;
			for (String search : sqlsearch) {
				String result = queryPopResult.getData(i, search);
				System.out.println(result);
			}
		}

		return true;
	}

	public String formatDate(String date) {
		String newdate = "";
		if (date.length() <= 10) {
			String day = date.substring(0, date.indexOf('/'));
			date = date.substring(date.indexOf('/') + 1, date.length());
			Integer month = Integer.parseInt(date.substring(0, date.indexOf('/')));
			date = date.substring(date.indexOf('/') + 1, date.length());
			String year = date;
			if (year.length() < 4)
				year = "20" + year;
			newdate = day + "/" + month + "/" + year;
		}
		return newdate;
	}
	/*
	 * metodo para seleccionar opciones de Tipo y cliente /*public Boolean
	 * selectOption(By select, String tipo, String opcion) { Boolean existOption =
	 * false; //WebElement element = me.explicitWaitToBeClickable(select, 30);
	 * WebDriverWait wait = new WebDriverWait(u.getDriver(), 30); WebElement element
	 * = wait.until(ExpectedConditions.elementToBeClickable(select)); u.hardWait(2);
	 * element.click(); String xpath = ""; if(tipo == "Cliente") xpath = xmlClientOp
	 * + "/li[text() = '" + opcion + "']"; else xpath = xmlTypeOp + "/li[text() = '"
	 * + opcion + "']"; //System.out.println(xpath); By op = By.xpath(xpath);
	 * u.hardWait(3); u.click(op);
	 * 
	 * u.highLight(select); testCase.addScreenShotCurrentStep(u, "selectOption");
	 * u.unhighLight(select); return existOption; } public void typingInput(By by,
	 * String dato) { u.sendKeys(by, dato); u.highLight(by);
	 * testCase.addScreenShotCurrentStep(u, "typingInput"); u.unhighLight(by); }
	 * 
	 * public void clickBoton(By by) { u.highLight(by);
	 * testCase.addScreenShotCurrentStep(u, "clickBoton"); u.unhighLight(by);
	 * u.click(by); }
	 */
}

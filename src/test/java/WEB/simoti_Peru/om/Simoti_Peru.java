package WEB.simoti_Peru.om;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
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
import org.sikuli.script.FindFailed;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class Simoti_Peru {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;

	/**
	 * Localizadores y metodos de portal SIMOTI
	 * 
	 ******************************************************************************************/
	/*
	 * Localizadores del menu principal de SIMOTI
	 */

	
	By IntegridadDatos = By.xpath("//div[@id='ddtopmenubar']//a[text()='Integridad de Datos']");
	
	/**   integridad datos submenu*/
	By IndicadoresPorTotales= By.xpath("//div[@class='ddsubmenustyle'][2]//a[contains(text(), 'Indicadores por Totales')]");//By.xpath("//*[@id=\"ddsubmenu20179\"]/li[4]/a");
	
	By CampoPlaza = By.id("j_idt151:cmbPlaza_label");
	By CampoMes = By.id("j_idt151:cmbMes_label");
	By CampoAnio = By.id("j_idt151:cmbannio");
	By BtnConsultar = By.id("j_idt151:btnBuscar");
	By BtnExportar = By.xpath("//a[contains(text(), 'Exportar')]");
	By BtnPgnSigte = By.xpath("/html/body/div[3]/div/div[1]/div/div[2]/form/fieldset[2]/div/span/div/div/div[2]/span[5]/span");
	By BtnPgnAnterior = By.xpath("/html/body/div[3]/div/div[1]/div/div[2]/form/fieldset[2]/div/span/div/div/div[2]/span[3]/span");
	By Dwnld = By.xpath("//*[@id=\"home\"]/div[6]/a[1]");
	By close = By.xpath("//a[contains(text(), 'Close')]");
	
	//*[@id="home"]/div[6]/a[1]
	
	By xmlTitulo = By.xpath("//label[contains(text(), 'Indicadores por Totales')]");
	By xmlPlazaTd = By.xpath("//*[@id=\"j_idt151:panelFiltros\"]/tbody/tr/td[2]");
	By xmlPlazaSeleccionada = By.xpath("//*[@id=\"j_idt151:cmbPlaza_label\"]");
	By xmlMesTd = By.xpath("//*[@id=\"j_idt151:panelFiltros\"]/tbody/tr/td[4]");
	
	By xmlNumResultados = By.xpath("//span[@class='ui-paginator-current']");
	String xmlDatosTabla = "//fieldset[2]/div/span/div/div/div/table/tbody/tr[%s]";
	
	//j_idt151:cmbMes_items
	
	By xmlMesSeleccionado = By.xpath("//*[@id=\"j_idt151:cmbMes_label\"]");
	By xmlMeses = By.xpath("//*[@id=\"j_idt151:cmbMes_items\"]/li");
	By xmlAnioTd = By.xpath("//*[@id=\"j_idt151:panelFiltros\"]/tbody/tr/td[6]");
	By xmlAnioSeleccionado = By.xpath("//*[@id=\"j_idt151:cmbannio_label\"]");
	By xmlAnios = By.xpath("//*[@id=\"j_idt151:cmbannio_items\"]/li");
	
	By xmlConsultarBtn = By.xpath("//a[contains(text(), 'Consultar')]");
	By xmlExportarBtn = By.xpath("//a[@id='j_idt151:btnExprotar']");
	
	By xmlSeccionResultados = By.xpath("//fieldset[2]");
	By xmlSinResultados = By.xpath("//fieldset[2]/div/span/div/div/div/table/tbody/tr/td");
	String xmlEncabezadosTabla = "//fieldset[2]/div/span/div/div/div/table/thead/tr/th[%s]/span";
	
	By xmlInicio = By.xpath("//*[@id=\"j_idt151:dtColors_paginator_bottom\"]/span[2]");
	By xmlAdelante = By.xpath("//*[@id=\"j_idt151:dtColors_paginator_bottom\"]/span[5]");
	By xmlAtras = By.xpath("//*[@id=\"j_idt151:dtColors_paginator_bottom\"]/span[3]");

	By xmlFin = By.xpath("//*[@id=\"j_idt151:dtColors_paginator_bottom\"]/span[6]");
	
	By xmlLoadingMsj= By.xpath("//*[@id=\"j_idt12\"]");
	By xmlMsgSinInformacionExportar = By.xpath("//*[@id=\"j_idt151:msgs_container\"]");
	//*[@id="j_idt151:msgs_container"]
	By SinResultados = By.xpath("//td[text()='Sin resultados']");
	//*[@id="j_idt12"]/div
	//*[@id="j_idt13"]
	//*[@id="j_idt12"]
	
	public void MAX() {
		}
	/**
	 * Metodo para dar click en integridad de datos
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return N/A
	 */
	public void clickIntegridadDatos() {
		u.hardWait(6);
		u.highLight(IntegridadDatos);
		u.mouseOver(IntegridadDatos);
	}

	/**
	 * Metodo para dar click en integridad de datos
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return N/A
	 */
	public void clickIndicadoresPorTotales() {
		u.hardWait(2);
		u.highLight(IndicadoresPorTotales);
		u.click(IndicadoresPorTotales);
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
			month = Month.JANUARY;
			dias = month.length(false);
		}
		case "Febrero":
		{
			month = Month.FEBRUARY;
			dias = month.length(false);
		}
		case "Marzo":
		{
			month = Month.MARCH;
			dias = month.length(false);
		}
		case "Abril":
		{
			month = Month.APRIL;
			dias = month.length(false);
		}
		case "Mayo":
		{
			month = Month.MAY;
			dias = month.length(false);
		}
		case "Junio":
		{
			month = Month.JUNE;
			dias = month.length(false);
		}
		case "Julio":
		{
			month = Month.JULY;
			dias = month.length(false);
		}
		case "Agosto":
		{
			month = Month.AUGUST;
			dias = month.length(false);
		}
		case "Septiembre":
		{
			month = Month.SEPTEMBER;
			dias = month.length(false);
		}
		case "Octubre":
		{
			month = Month.OCTOBER;
			dias = month.length(false);
		}
		case "Noviembre":
		{
			month = Month.NOVEMBER;
			dias = month.length(false);
		}
		case "Diciembre":
		{
			month = Month.DECEMBER;
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
	 * Metodo para dar click en el boton consultar
	 * @author Herbe Cauich
	 * @param N/A
	 * @return N/A
	 */
	public void clickBtnConsultar() {
		u.hardWait(2);
		u.highLight(BtnConsultar);
		u.click(BtnConsultar);
		u.hardWait(15);
	}
	
	public void clickBtnDescarga() {
		u.hardWait(2);
		u.highLight(Dwnld);
		u.click(Dwnld);
	}
	
	public void clickBtnClose() {
		me.validarVisualizacion(close);
		u.hardWait(2);
		u.highLight(close);
		u.click(close);
	}
	/**
	 * Metodo para dar click en el boton Exportar
	 * @author N/A
	 * @param N/A
	 * @return N/A
	 */
	public void clickBtnExportar() {
		u.highLight(xmlExportarBtn);
		testCase.addScreenShotCurrentStep(u, "Exportar");
		u.unhighLight(xmlExportarBtn);
		u.click(xmlExportarBtn);
		testCase.addScreenShotCurrentStep(u, "clickExportar");
		u.getDriver().navigate().refresh();
	}
	/**
	 * Metodo para validar la visibilidad del mensaje que indica que no se tiene informacion para exportar
	 * @author Herbe Cauich
	 * @param N/A
	 * @return N/A
	 */
	public void MensajeSinInformacionExportar() {
		me.validarVisualizacion(xmlMsgSinInformacionExportar);
		u.highLight(xmlMsgSinInformacionExportar);
				
	}
	/**
	 * Metodo para para validar la visibilidad del componente de carga del sistema
	 * @author Herbe Cauich
	 * @param N/A
	 * @return N/A
	 */
	public void Loading() {
		me.validarVisualizacion(xmlLoadingMsj);
		u.highLight(xmlLoadingMsj);
		//testCase.addScreenShotCurrentStep(u, "Loading");
		u.unhighLight(xmlLoadingMsj);
				
	}
	/**
	 * Metodo para dar click en el boton siguiente pagina
	 * @author Herbe Cauich
	 * @param N/A
	 * @return N/A
	 */
	public void clickIBtnPgnSiguiente() {
		u.hardWait(2);
		u.highLight(BtnPgnSigte);
		u.click(BtnPgnSigte);
	}
	
	/**
	 * Metodo para dar click en el boton pagina anterior
	 * @author Herbe Cauich
	 * @param N/A
	 * @return N/A
	 */
	public void clickIBtnPgnAnterior() {
		u.hardWait(2);
		u.highLight(BtnPgnAnterior);
		u.click(BtnPgnAnterior);
	}
	
	/**
	 * Metodo para Validar los encabezados contenidos en la pantalla
	 * @author Herbe Cauich
	 * @param N/A
	 * @return N/A
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
				System.out.println(dia);
			}
			
			
		}
		
		if(no.equals("No.") && color.equals("Color") && dias) {
			correct = true;
		}
		
		return correct;
	}	
	//======================================================
	/**
	 * Metodo para validar ela visibilidad de los elementos de la pantalla
	 * @author Herbe Cauich
	 * @param N/A
	 * @return N/A
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
	 * Metodo para validar la funcionalidad de los botones del paginador
	 * @author Herbe Cauich
	 * @param N/A
	 * @return N/A
	 */
	public Boolean validarBotonespaginado() {
		Boolean exist = false;
		if(me.isEnable(xmlInicio) && me.isEnable(xmlAdelante) && me.isEnable(xmlAtras) && me.isEnable(xmlFin)) {
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
		else {
			exist = true;
			me.existElement(xmlSinResultados);
			u.highLight(xmlSinResultados);
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
	 * Metodo para seleccionar un mes del elemento
	 * @author Herbe Cauich
	 * @param mes
	 * @return N/A
	 */
	public boolean selectMes(String mes) {

		boolean existOption = false;
		WebElement element = me.explicitWaitToBeClickable(xmlMesTd, Duration.ofSeconds(30));
		u.hardWait(2);
		element.click();
		List<WebElement> allOptions = u.getDriver().findElements(xmlMeses);
		for (WebElement option : allOptions) {
			if (option.getText().equals(mes)) {
				u.hardWait(1);
				option.click();
				u.hardWait(1);
				existOption = true;
				break;
			}
		}

		//me.explicitWaitToBeInvisible(15);
		u.highLight(xmlMesTd);
		testCase.addScreenShotCurrentStep(u, "selectMes");
		u.unhighLight(xmlMesTd);
		u.mouseOver(BtnConsultar);
		return existOption;

	}	
	/**
	 * Metodo para seleccionar un anio del elemento
	 * @author Herbe Cauich
	 * @param anio
	 * @return N/A
	 */
	public boolean selectAnio(String anio) {

		boolean existOption = false;
		WebElement element = me.explicitWaitToBeClickable(xmlAnioTd, Duration.ofSeconds(30));
		u.hardWait(2);
		element.click();
		List<WebElement> allOptions = u.getDriver().findElements(xmlAnios);
		for (WebElement option : allOptions) {
			if (option.getText().equals(anio)) {
				u.hardWait(1);
				option.click();
				u.hardWait(1);
				existOption = true;
				break;
			}
		}

		//me.explicitWaitToBeInvisible(15);
		u.highLight(xmlAnioTd);
		testCase.addScreenShotCurrentStep(u, "selectAnio");
		u.unhighLight(xmlAnioTd);
		return existOption;

	}	
	
	public boolean selectPlaza(String plaza) {

		boolean existOption = false;
		WebElement element = me.explicitWaitToBeClickable(xmlPlazaTd, Duration.ofSeconds(30));
		u.hardWait(2);
		element.click();
		List<WebElement> allOptions = u.getDriver().findElements(xmlPlazaSeleccionada);
		for (WebElement option : allOptions) {
			if (option.getText().equals(plaza)) {
				u.hardWait(1);
				option.click();
				u.hardWait(1);
				existOption = true;
				break;
			}
		}

		//me.explicitWaitToBeInvisible(15);
		u.highLight(xmlAnioTd);
		testCase.addScreenShotCurrentStep(u, "selectAnio");
		u.unhighLight(xmlAnioTd);
		return existOption;

	}
	

//		
	/**
	 * Metodo para validar que se desplejaron datos
	 * 
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return
	 */
	public boolean DatosTabla() {

		u.hardWait(7);
//		u.highLight(SinResultados);
		MetodosExtra mo = new MetodosExtra(u, testCase);
		Boolean existe = mo.existElement(SinResultados);
		return existe;
	}
	/**
	 * Metodo para validar que se descargo el excel de transacciones
	 * @author Maria Dueñas
	 * @param N/A
	 * @return
	 */
	public Boolean validarDescarga() {
		Boolean exist = false;
		 DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
	     String date = dateFormat.format(Calendar.getInstance().getTime());
	     String nameFile = "Reporte_Indicadores_Totales_";
	     System.out.println(date);
		String pathYourSystem = System.getProperty("user.home") + "\\Downloads\\" + nameFile + date +".xlsx";
		File f = new File(pathYourSystem);
		
		if (f.exists()) {
			exist = true;
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
	 * Metodo para validar el contenido del filtro mes
	 * @author Herbe Cauich
	 * @param mes
	 * @return N/A
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
        u.hardWait(10);
        testCase.addScreenShotCurrentStep(u, "Mes");
        u.unhighLight(xmlMesSeleccionado);
        u.click(xmlMesSeleccionado);
        List<WebElement> allOptions = u.getDriver().findElements(xmlMeses);
        
        int cont = 0;
        String meses[] = new String[12];
        for(WebElement option:allOptions){
        	
            if(!option.getText().equals("Seleccione una opci�n")) {
              meses[cont] = option.getText();
              cont ++;    
            }
        }
        if (mes_Sistema.equals(mes) == true && Arrays.equals(meses, MES)) {
            correct = true;
        }
        u.click(xmlMesSeleccionado);
        return correct;
    }
	/**
	 * Metodo para validar el contenido del filtro anio
	 * @author Herbe Cauich
	 * @param anio
	 * @return N/A
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
            if(!option.getText().equals("Seleccione una opci�n")) {
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
    
    public Boolean abrirExcel() throws FindFailed {
    	
		u.waitForLoadPage();
		
		testCase.addScreenShotCurrentStep("exceldescarga");
		Boolean correct = me.clickElementSikuli("Simoti_Peru", "exceldescarga"); //Da click en boton Open de la ventana de descarga
		u.waitForLoadPage();
		
		//testCase.addScreenShotCurrentStep("capturaExcel");
		
		me.clickElementSikuli("Simoti_Peru", "cerrarexcel"); //Da click en la tachita del excel para cerrarlo y continuar con los pasos
		
		return correct;
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
				Integer d = Integer.valueOf(u.getText(By.xpath(row.concat(String.format("/td[%s]", Integer.toString(i+2))))).trim());
				values[i] = Integer.parseInt(queryPopResult.getData(i, sqlsearch.get(i)));
				//System.out.println(d+ " - " + values[i]);
				if(d != values[i])
					return false;
			}
		}
		return true;
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
        
        /*String plaza = u.getText(xmlPlazaSeleccionada).trim();
        System.out.println(plaza);*/
        u.hardWait(3);
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
        String cellplaza = datoCelda(firstSheet, 6, 3);
        String cellmes = datoCelda(firstSheet, 6, 11);
        String cellanio = datoCelda(firstSheet, 6, 16);     
        							  //1,3,5,7,10,12,13,15,18,21
        							  //B,D,F,H,K, M, N, P, S, U
        Integer[] posicionesheaders = {5,7,10,12,13,15,18,21};
        Integer[] values = new Integer[days];
       
        
        if(!cellapp.equals(aplicacion) || !cellapartado.equals(apartado) || !cellreporte.equals(reporte) 
        		/*|| !cellplaza.equals(plaza)*/ || !cellmes.equals(mes) || !cellanio.equals(anio))
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
			
			System.out.println(str_color + " _ " + str_colorE);
			
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
				//System.out.println(row.concat(String.format("/td[%s]", Integer.toString(i+2))));
				values[i-1] = Integer.parseInt(datoCelda(firstSheet,rownum,posicionesheaders[i-1]));
				//System.out.println(i + ": " + d + " - " + values[i-1]);
				if((d - values[i-1]) > 0)
					return false;
			}
			for (int i = 9; i < days-3; i++) 
			{
				Integer cellnum = i + 12;
				Integer d = Integer.valueOf(u.getText(By.xpath(row.concat(String.format("/td[%s]", Integer.toString(i+2))))).trim());
				//System.out.println(row.concat(String.format("/td[%s]", Integer.toString(i+2))));
				values[i] = Integer.parseInt(datoCelda(firstSheet,rownum,cellnum));
				//System.out.println(i + ": " + d + " - " + values[i] + " " + cellnum + " _ " + (d-values[i]));
				if((d - values[i]) > 0)
					return false;
			}
		}
		return true;
	}
    
    
	/**
	 * Constructor
	 ******************************************************************************************/

	public Simoti_Peru(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;

		this.u.getDriver().manage().window().maximize();
		this.me = new MetodosExtra(u, testCase);

	}

	/*
	 * METODOS - Acceso correcto a Inventario de espacios Publicitarios V2
	 ******************************************************************************************/

}
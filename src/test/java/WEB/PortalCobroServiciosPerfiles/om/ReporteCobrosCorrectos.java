package WEB.PortalCobroServiciosPerfiles.om;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
//import au.com.bytecode.opencsv.CSVReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class ReporteCobrosCorrectos{

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	
	
	/*
	 * Localizadores Menu portal cobro servicios
	 ********************************************************************************************/
	By selectStatus = By.name("selectEstatus");
	By selectFormato = By.name("selectFormato");
	By selectTipoFecha = By.name("selectTipoFecha");
//	By estadoSeleccionado = By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table/tbody/tr[9]/td[1]/select/option[3]");
	By PortalCobroServicios = By.xpath("//a[contains(text(), 'PORTAL COBRO SERVICIOS')]");
	//String selecDia = "//table/tbody/tr[1]/td[4]/a[contains(text(), '%s')";
	By dias = By.xpath("/html/body/div/table/tbody/tr[2]/td/div/table/tbody/tr[4]/td[3]/font");
	By btnRegresar = By.xpath("//a[contains(text(), 'Regresar')]");
	By calendarInicio = By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table/tbody/tr[10]/td/table/tbody/tr/td[1]/span/img");
	By calendarFin = By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table/tbody/tr[10]/td/table/tbody/tr/td[3]/span/img");
	//Opciones menu
	
	By ReporteCobrosCorrectos = By.xpath("//a[contains(text(), 'REPORTE COBROS CORRECTOS')]");
	
	By btnGenerarReporte = By.xpath("//input[@value='GENERAR REPORTE']");
	//By btnGenerarReporte = By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr/td/div/form/table/tbody/tr/td[2]/table/tbody/tr[11]/td/b/input");
	String encabezadoTabla = "//tbody/tr/th[contains(text(), '%s')]";
	By selectMes1 = By.id("tbSelMonth");
	By selectAnio1 = By.id("tbSelYear");
	By selectMes2 = By.id("tbSelMonth");
	By selectAnio2 = By.id("tbSelYear");
	String seldia= "//tr/td/div/table/tbody/tr/td/font[contains(text(), '%s')]";
	//String encabezadoTabla  = "//*[@id=\"content\"]/table[3]/tbody/tr[2]/th[%s]";
	
	
	
	
	//By selectAnio1 = By.xpath("By btnGenerarReporte = By.xpath(\"//a[contains(text(), 'GENERAR REPORTE')]\");");
	
	//*[@id="tbSelYear"]/option[1]
	
	/*
	 * Constructor
	 ********************************************************************************************/
	public ReporteCobrosCorrectos(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}
	
	/*
	 * Metodos Menu Integridad de datos
	 ********************************************************************************************/
	
	/**
	 * Metodo para dar click en Integridad de Datos
	 * @author Herbe Cauich
	 */
	public void clickPortalCobroServicios() {
		u.highLight(PortalCobroServicios);
		testCase.addScreenShotCurrentStep(u, "PortalCobroServicios");
		u.unhighLight(PortalCobroServicios);
		u.mouseOver(PortalCobroServicios);
		testCase.addScreenShotCurrentStep(u, "clickPortalCobroServicios");
	}
	
	/**
	 * Metodo para dar click en Integridad de Datos
	 * @author Herbe Cauich
	 */
	public void clickReporteCobrosCorrectos() {
		u.highLight(ReporteCobrosCorrectos);
		testCase.addScreenShotCurrentStep(u, "ReporteCobrosCorrectos");
		u.unhighLight(ReporteCobrosCorrectos);
		u.click(ReporteCobrosCorrectos);
		testCase.addScreenShotCurrentStep(u, "clickReporteCobrosCorrectos");
		
	}
	
/*
	public boolean selectStatus(String estatus) {

		boolean existOption = false;
		WebElement element = me.explicitWaitToBeClickable(selectStatus, 30);
		u.hardWait(2);
		element.click();
		List<WebElement> allOptions = u.getDriver().findElements(estadoSeleccionado);
		for (WebElement option : allOptions) {
			if (option.getText().equals(estatus)) {
				u.hardWait(1);
				option.click();
				u.hardWait(1);
				existOption = true;
				break;
			}
		}

		//me.explicitWaitToBeInvisible(15);
		u.highLight(selectStatus);
		testCase.addScreenShotCurrentStep(u, "selectMes");
		u.unhighLight(selectStatus);
		u.highLight(estadoSeleccionado);
		return existOption;

	}
	*/
	
	public void selectStatus2(String estatus) {

		

	}
	
	public void highligthElements() {
		u.highLight(btnRegresar);
	
	}
	
	public void clickGenerarReporte() {
		u.highLight(btnGenerarReporte);
		testCase.addScreenShotCurrentStep(u, "ReporteCobrosCorrectos");
		u.unhighLight(btnGenerarReporte);
		u.click(btnGenerarReporte);
		testCase.addScreenShotCurrentStep(u, "clickReporteCobrosCorrectos");
		
	}
	
	public void clickBtnRegresar() {
		u.highLight(btnRegresar);
		testCase.addScreenShotCurrentStep(u, "ReporteCobrosCorrectos");
		u.unhighLight(btnRegresar);
		u.click(btnRegresar);
		testCase.addScreenShotCurrentStep(u, "clickReporteCobrosCorrectos");
	}
	
	 	public Boolean SelecFiltros(String mes, String anio, String mes2, String anio2,String diaSel1, String diaSel2) {
        Boolean exist = false;

     
        u.highLight(calendarInicio);
        u.click(calendarInicio);
        u.hardWait(2);
        u.highLight(selectMes1);
        exist = selectOption (mes, selectMes1);
        u.highLight(selectAnio1);
        exist = selectOptionValue (anio, selectAnio1);
       // u.click(dias);
        String DiaSelect1 = String.format(seldia, diaSel1);
        u.highLight(By.xpath(DiaSelect1));
		testCase.addScreenShotCurrentStep(u, "clickDia");
		u.unhighLight(By.xpath(DiaSelect1));
		u.click(By.xpath(DiaSelect1));
        u.highLight(calendarFin);
        u.click(calendarFin);
        u.hardWait(2);
        u.highLight(selectMes2);
        exist = selectOption (mes2, selectMes2);
        u.highLight(selectAnio2);
        exist = selectOptionValue (anio2, selectAnio2);
       // u.click(dias);
        String DiaSelect2 = String.format(seldia, diaSel2);
        u.highLight(By.xpath(DiaSelect2));
		testCase.addScreenShotCurrentStep(u, "clickDia");
		u.unhighLight(By.xpath(DiaSelect2));
		u.click(By.xpath(DiaSelect2));

        return exist;
    }
	 	
	 	/**
		 * Metodo para selecionar filtros
		 * @author Herbe Cauich
		 * @param estatus, tipoFecha
		 * @return N/A
		 */	
	 	
	 	public Boolean SelecFiltro(String estatus, String tipoFecha) {
	        Boolean exist = false;

	        exist = selectOption(estatus, selectStatus);
	        exist = selectOption(tipoFecha, selectTipoFecha); 
	        
	        
	        return exist;
	        
	        
	 	}
	 	/**
		 * Metodo para seleccioar filtro formato
		 * @author Herbe Cauich
		 * @param formato
		 * @return N/A
		 */
	 	public Boolean SelecFormat(String formato) {
	        Boolean exist = false;
   
	        exist = selectOption(formato, selectFormato);         
	        return exist;
	    }
	 	
	 	/**
		 * Metodo para seleccionar fecha
		 * @author Herbe Cauich
		 * @param mes, mes2, anio,anio2, diaSel1, diaSel2
		 * @return N/A
		 */
	 	
	 	public Boolean SelecDate(String mes, String anio, String mes2, String anio2,String diaSel1, String diaSel2) {
	        Boolean exist = false;
 	
	        u.highLight(calendarInicio);
	        u.click(calendarInicio);
	        u.hardWait(2);
	        u.highLight(selectMes1);
	        exist = selectOption (mes, selectMes1);
	        u.highLight(selectAnio1);
	        exist = selectOptionValue (anio, selectAnio1);
	       // u.click(dias);
	        String DiaSelect1 = String.format(seldia, diaSel1);
	        u.highLight(By.xpath(DiaSelect1));
			testCase.addScreenShotCurrentStep(u, "clickDia");
			u.unhighLight(By.xpath(DiaSelect1));
			u.click(By.xpath(DiaSelect1));
	        u.highLight(calendarFin);
	        u.click(calendarFin);
	        u.hardWait(2);
	        u.highLight(selectMes2);
	        exist = selectOption (mes2, selectMes2);
	        u.highLight(selectAnio2);
	        exist = selectOptionValue (anio2, selectAnio2);
	       // u.click(dias);
	        String DiaSelect2 = String.format(seldia, diaSel2);
	        u.highLight(By.xpath(DiaSelect2));
			testCase.addScreenShotCurrentStep(u, "clickDia");
			u.unhighLight(By.xpath(DiaSelect2));
			u.click(By.xpath(DiaSelect2));
			
			return exist;
	 	}
	 	
	 	
	 	/**
		 * Metodo para realizar la seleccion de un elemento de un componente select mediante texto visible
		 * @author Herbe Cauich
		 * @param optionn, by
		 * @return N/A
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
	 * Metodo para realizar la seleccion de un elemento de un componente select mediante su valor
	 * @author Herbe Cauich
	 * @param optionn, by
	 * @return N/A
	 */	
    
    public Boolean selectOptionValue(String option, By by) {
		Boolean exist = false;
		
		try {
			Select selectObject = new Select(u.getDriver().findElement(by));
			selectObject.selectByValue(option);
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
	 * Metodo para validar los encabezados
	 * @author Herbe Cauich
	 * @param 
	 * @return N/A
	 */	
    
    public Boolean validarEncabezados() {
		Boolean correct = false;
		
		
		Boolean dias = false;
		Integer dia = 1;
		/*for (int i = 3; i <= 33; i++) {
			Integer d =Integer.valueOf(u.getText(By.xpath(String.format(xmlEncabezadosTabla, Integer.toString(i)))).trim());
			
			if(dia != d) {
				dias = false;
				break;
			}else {
				dias = true;
				dia ++;
				System.out.println(dia);
			}
			
			
		}*/
		
		
		
		return correct;
		
    }
    
  /*  public Boolean selectDate(String fecha, By calendario, By container) {
		Boolean correct = false;
		
		me.scrollToElement(calendario);
		u.highLight(calendario);
		testCase.addScreenShotCurrentStep(u, "clickCalendario");
		u.unhighLight(calendario);
		u.click(calendario);
		
		String [] f = fecha.split("\\*");
		//System.out.print(f + "\n");
		Boolean anio = selectOption(f[2], selecAnio);
		
		Boolean mes = selectOption(f[1], selecMes);
		
		String dia = String.format(selecDia, f[0]);
		u.highLight(By.xpath(dia));
		testCase.addScreenShotCurrentStep(u, "clickDia");
		u.unhighLight(By.xpath(dia));
		u.click(By.xpath(dia));
		
		if(mes == true && anio == true) {
			correct = true;
		}
		
		u.mouseOverAndClick(container);
		
		return correct;
	}
*/	
    
	/**
	 * Metodo para la lectura de datos en excel, indicado por nombre de hoja y coordenadas de celda
	 * @author Herbe Cauich
	 * @param optionn, by
	 * @return N/A
	 */	
    
	public String datoCelda(Sheet sheet, int r, int c) {
		DataFormatter formatter = new DataFormatter();
        
        Cell celda = sheet.getRow(r).getCell(c);
        String contenidoCelda = formatter.formatCellValue(celda);
        //System.out.println("celda: -" + contenidoCelda + "-");
        
        return contenidoCelda;
	}
	
	
	
    
    public String validarDatos(String formato) throws IOException {
		Boolean correct = false;
		//String formato = "dd-MM-yyyy";
		SimpleDateFormat formatFecha = new SimpleDateFormat(formato);
        LocalDateTime datetime = LocalDateTime.now();

        String nombreArchivo = "CobroServicios_";
        String fecha = DateTimeFormatter.ofPattern(formato).format(datetime); 
        System.out.println(fecha);

        
		//me.explicitWaitToBeInvisible(10);
	    String pathYourSystem = System.getProperty("user.home") + "\\Downloads\\caffeine\\"+ nombreArchivo + fecha +".xlsx";
	    File fichero = new File(pathYourSystem);
	    System.out.println(pathYourSystem);

        
        FileInputStream inputStream = new FileInputStream(new File(pathYourSystem));
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        
        String folio = u.getText(By.xpath(String.format(encabezadoTabla, "Folio"))).trim();
    	String lote = u.getText(By.xpath(String.format(encabezadoTabla, "Lote"))).trim();
    	String fechaMovimiento = u.getText(By.xpath(String.format(encabezadoTabla, "Fecha Movimiento"))).trim();
    	String horaTransaccion = u.getText(By.xpath(String.format(encabezadoTabla, "Hora Transacci�n"))).trim();
    	String FechaAdministrativa = u.getText(By.xpath(String.format(encabezadoTabla, "Fecha Administrativa"))).trim();
    	String FechaRecibe = u.getText(By.xpath(String.format(encabezadoTabla, "Fecha Recibe"))).trim();
    	String FechaEnvia = u.getText(By.xpath(String.format(encabezadoTabla, "Fecha Env�a"))).trim();
    	String CrSuperior = u.getText(By.xpath(String.format(encabezadoTabla, "Cr Superior"))).trim();
    	String Tienda = u.getText(By.xpath(String.format(encabezadoTabla, "Tienda"))).trim();
    	String Servicio = u.getText(By.xpath(String.format(encabezadoTabla, "Servicio"))).trim();
    	String TipoServicio = u.getText(By.xpath(String.format(encabezadoTabla, "Tipo Servicio"))).trim();
    	String Ref1 = u.getText(By.xpath(String.format(encabezadoTabla, "Ref1"))).trim();
    	String Ref2 = u.getText(By.xpath(String.format(encabezadoTabla, "Ref2"))).trim();
    	String Corte = u.getText(By.xpath(String.format(encabezadoTabla, "Corte"))).trim();
    	String Caja = u.getText(By.xpath(String.format(encabezadoTabla, "Caja"))).trim();
    	String Ticket = u.getText(By.xpath(String.format(encabezadoTabla, "Ticket"))).trim();
    	String Valor = u.getText(By.xpath(String.format(encabezadoTabla, "Valor"))).trim();
    	String Comision = u.getText(By.xpath(String.format(encabezadoTabla, "Comisi�n"))).trim();
    	String Estatus = u.getText(By.xpath(String.format(encabezadoTabla, "Estatus"))).trim();
    	//String FacturaAP = u.getText(By.xpath(String.format(encabezadoTabla, "Factura AP"))).trim();
    	//String Contabilizado = u.getText(By.xpath(String.format(encabezadoTabla, "Contabilizado"))).trim();
    	String EstatusTransaccionWM = u.getText(By.xpath(String.format(encabezadoTabla, "Estatus Transacci�n WM"))).trim();
    	String NumeroAutorizacion = u.getText(By.xpath(String.format(encabezadoTabla, "Numero Autorizaci�n"))).trim();
    	String FolioWM = u.getText(By.xpath(String.format(encabezadoTabla, "Folio WM"))).trim();
        
        String cellFolio = datoCelda(firstSheet, 1, 0);
        String cellLote = datoCelda(firstSheet, 1, 1);
        String cellFechaMovimiento = datoCelda(firstSheet, 1, 2);
        String cellHoraTransaccion = datoCelda(firstSheet, 1, 3);
        String cellFechaAdministrativa = datoCelda(firstSheet, 1, 4);
        String cellFechaRecibe = datoCelda(firstSheet, 1, 5);
        String cellFechaEnvia = datoCelda(firstSheet, 1, 6);
        String cellCrSuperior = datoCelda(firstSheet, 1, 7);
        String cellTienda = datoCelda(firstSheet, 1, 8);
        String cellServicio = datoCelda(firstSheet, 1, 9);
        String cellTipoServicio = datoCelda(firstSheet, 1, 10);
        String cellRef1 = datoCelda(firstSheet, 1, 11);
        String cellRef2 = datoCelda(firstSheet, 1, 12);
        String cellCorte = datoCelda(firstSheet, 1, 13);
        String cellCaja = datoCelda(firstSheet, 1, 14);
        String cellTicket = datoCelda(firstSheet, 1,15);
        String cellValor = datoCelda(firstSheet, 1, 16);
        String cellComision = datoCelda(firstSheet, 1,17);
        String cellEstatus = datoCelda(firstSheet, 1, 18);
       // String cellFacturaAP = datoCelda(firstSheet, 1, 4);
       // String cellContabilizado = datoCelda(firstSheet, 1, 0);
        String cellEstatusTransaccionWM = datoCelda(firstSheet, 1, 1);
        String cellNumeroAutorizacion = datoCelda(firstSheet, 1, 38);
        String cellFolioWM = datoCelda(firstSheet, 1, 39);

        
        
        
        if(cellFolio == folio &&  
        		cellLote == lote && 
        		cellFechaMovimiento == fechaMovimiento && 
        		cellHoraTransaccion == horaTransaccion && 
        		cellFechaAdministrativa == FechaAdministrativa && 
        		cellFechaRecibe == FechaRecibe &&
        		cellFechaEnvia == FechaEnvia &&
        		cellCrSuperior == CrSuperior &&
        		cellTienda == Tienda &&
        		cellServicio == Servicio &&
        		cellTipoServicio == TipoServicio &&
        		cellRef1 == Ref1 &&
        		cellRef2 == Ref2 &&
        		cellCorte == Corte &&
        		cellCaja == Caja &&
        		cellTicket == Ticket &&
        		cellValor == Valor &&
        		cellComision == Comision &&
        		cellEstatus == Estatus &&
        		//cellFacturaAP == FacturaAP &&
        		//cellContabilizado == Contabilizado &&
        		cellEstatusTransaccionWM == EstatusTransaccionWM &&
        		cellNumeroAutorizacion == NumeroAutorizacion &&
        		cellFolioWM == FolioWM) {
        	System.out.println("todas las columnas estan bien");
        	correct = true;
        }else {
        	System.out.println("no todas las columas coninciden");
        	
        }
                
                
        	
         

        
        
        //
        return fecha;
    
    }  
    
    
    
    
    
    
    
    
    
    
    /*
    public static void main(String[] args) throws IOException {
        Workbook wb = new HSSFWorkbook();
        CreationHelper helper = wb.getCreationHelper();
        Sheet sheet = wb.createSheet("new sheet");

        CSVReader reader = new CSVReader(new FileReader("data.csv"));
        String[] line;
        int r = 0;
        while ((line = reader.readNext()) != null) {
            Row row = sheet.createRow((short) r++);

            for (int i = 0; i < line.length; i++)
                row.createCell(i)
                   .setCellValue(helper.createRichTextString(line[i]));
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("workbook.xls");
        wb.write(fileOut);
        fileOut.close();
    }
    
    
	*/
    
    
    public Boolean validarEncabezado() {
		Boolean correct = false;
		Boolean dias = false;
		Integer dia = 1;
		for (int i = 2; i <= 25; i++) {
			Integer d =Integer.valueOf(u.getText(By.xpath(String.format(encabezadoTabla, Integer.toString(i)))).trim());
			
			
			
			if(dia != d) {
				dias = false;
				break;
			}else {
				dias = true;
				dia ++;
				System.out.println(dia);
			}	
		}
		
		if(dias) {
			correct = true;
		}
		return correct;
	}	
    
    
    
    
    
    
    
    
    public Boolean validarDatoss() throws IOException {
		Boolean correct = false;
		String formato = "dd-MM-yyyy";
		SimpleDateFormat formatFecha = new SimpleDateFormat(formato);
        LocalDateTime datetime = LocalDateTime.now();

        String nombreArchivo = "CobroServicios_";
        String fecha = DateTimeFormatter.ofPattern(formato).format(datetime); 
        System.out.println(fecha);

        
		//me.explicitWaitToBeInvisible(10);
	    String pathYourSystem = System.getProperty("user.home") + "\\Downloads\\caffeine\\"+ nombreArchivo + fecha +".xlsx";
	    File fichero = new File(pathYourSystem);
	    System.out.println(pathYourSystem);

        
        FileInputStream inputStream = new FileInputStream(new File(pathYourSystem));
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        
        String folio = u.getText(By.xpath(String.format(encabezadoTabla, "Folio"))).trim();
    	String lote = u.getText(By.xpath(String.format(encabezadoTabla, "Lote"))).trim();
    	String fechaMovimiento = u.getText(By.xpath(String.format(encabezadoTabla, "Fecha Movimiento"))).trim();
    	String horaTransaccion = u.getText(By.xpath(String.format(encabezadoTabla, "Hora Transacci�n"))).trim();
    	String FechaAdministrativa = u.getText(By.xpath(String.format(encabezadoTabla, "Fecha Administrativa"))).trim();
    	String FechaRecibe = u.getText(By.xpath(String.format(encabezadoTabla, "Fecha Recibe"))).trim();
    	String FechaEnvia = u.getText(By.xpath(String.format(encabezadoTabla, "Fecha Env�a"))).trim();
    	String CrSuperior = u.getText(By.xpath(String.format(encabezadoTabla, "Cr Superior"))).trim();
    	String Tienda = u.getText(By.xpath(String.format(encabezadoTabla, "Tienda"))).trim();
    	String Servicio = u.getText(By.xpath(String.format(encabezadoTabla, "Servicio"))).trim();
    	String TipoServicio = u.getText(By.xpath(String.format(encabezadoTabla, "Tipo Servicio"))).trim();
    	String Ref1 = u.getText(By.xpath(String.format(encabezadoTabla, "Ref1"))).trim();
    	String Ref2 = u.getText(By.xpath(String.format(encabezadoTabla, "Ref2"))).trim();
    	String Corte = u.getText(By.xpath(String.format(encabezadoTabla, "Corte"))).trim();
    	String Caja = u.getText(By.xpath(String.format(encabezadoTabla, "Caja"))).trim();
    	String Ticket = u.getText(By.xpath(String.format(encabezadoTabla, "Ticket"))).trim();
    	String Valor = u.getText(By.xpath(String.format(encabezadoTabla, "Valor"))).trim();
    	String Comision = u.getText(By.xpath(String.format(encabezadoTabla, "Comisi�n"))).trim();
    	String Estatus = u.getText(By.xpath(String.format(encabezadoTabla, "Estatus"))).trim();
    	//String FacturaAP = u.getText(By.xpath(String.format(encabezadoTabla, "Factura AP"))).trim();
    	//String Contabilizado = u.getText(By.xpath(String.format(encabezadoTabla, "Contabilizado"))).trim();
    	//String EstatusTransaccionWM = u.getText(By.xpath(String.format(encabezadoTabla, "Estatus Transacci�n WM"))).trim();
    	String NumeroAutorizacion = u.getText(By.xpath(String.format(encabezadoTabla, "Numero Autorizaci�n"))).trim();
    	String FolioWM = u.getText(By.xpath(String.format(encabezadoTabla, "Folio WM"))).trim();
        
        String cellFolio = datoCelda(firstSheet, 1, 0);
        String cellLote = datoCelda(firstSheet, 1, 1);
        String cellFechaMovimiento = datoCelda(firstSheet, 1, 2);
        String cellHoraTransaccion = datoCelda(firstSheet, 1, 3);
        String cellFechaAdministrativa = datoCelda(firstSheet, 1, 4);
        String cellFechaRecibe = datoCelda(firstSheet, 1, 5);
        String cellFechaEnvia = datoCelda(firstSheet, 1, 6);
        String cellCrSuperior = datoCelda(firstSheet, 1, 7);
        String cellTienda = datoCelda(firstSheet, 1, 8);
        String cellServicio = datoCelda(firstSheet, 1, 9);
        String cellTipoServicio = datoCelda(firstSheet, 1, 10);
        String cellRef1 = datoCelda(firstSheet, 1, 11);
        String cellRef2 = datoCelda(firstSheet, 1, 12);
        String cellCorte = datoCelda(firstSheet, 1, 13);
        String cellCaja = datoCelda(firstSheet, 1, 14);
        String cellTicket = datoCelda(firstSheet, 1,15);
        String cellValor = datoCelda(firstSheet, 1, 16);
        String cellComision = datoCelda(firstSheet, 1,17);
        String cellEstatus = datoCelda(firstSheet, 1, 18);
       // String cellFacturaAP = datoCelda(firstSheet, 1, 4);
       // String cellContabilizado = datoCelda(firstSheet, 1, 0);
        String cellEstatusTransaccionWM = datoCelda(firstSheet, 1, 1);
        String cellNumeroAutorizacion = datoCelda(firstSheet, 1, 38);
        String cellFolioWM = datoCelda(firstSheet, 1, 39);

        
        
        
        if(cellFolio == folio &&  
        		cellLote == lote && 
        		cellFechaMovimiento == fechaMovimiento && 
        		cellHoraTransaccion == horaTransaccion && 
        		cellFechaAdministrativa == FechaAdministrativa && 
        		cellFechaRecibe == FechaRecibe &&
        		cellFechaEnvia == FechaEnvia &&
        		cellCrSuperior == CrSuperior &&
        		cellTienda == Tienda &&
        		cellServicio == Servicio &&
        		cellTipoServicio == TipoServicio &&
        		cellRef1 == Ref1 &&
        		cellRef2 == Ref2 &&
        		cellCorte == Corte &&
        		cellCaja == Caja &&
        		cellTicket == Ticket &&
        		cellValor == Valor &&
        		cellComision == Comision &&
        		cellEstatus == Estatus &&
        		//cellFacturaAP == FacturaAP &&
        		//cellContabilizado == Contabilizado &&
        		//cellEstatusTransaccionWM == EstatusTransaccionWM &&
        		cellNumeroAutorizacion == NumeroAutorizacion &&
        		cellFolioWM == FolioWM) {
        	System.out.println("todas las columnas estan bien");
        	testCase.addTextEvidenceCurrentStep("Todas las columnas coinciden");
        	correct = true;
        }else {  	
        	System.out.println("no todas las columas coninciden");
        	testCase.addTextEvidenceCurrentStep("No todas las columas coinciden");
        	correct = false;
        }
                              
        testCase.addScreenShotCurrentStep("validacionColumnas");	
         
        return correct;
    
    }  
    
    
    
    
    
      
   
}

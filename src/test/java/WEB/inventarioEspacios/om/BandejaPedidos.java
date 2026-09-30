package WEB.inventarioEspacios.om;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class BandejaPedidos {
	
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	PedidosAdministracionIEP pedAdmin;
	
	/**
	 * Localizadores Bandeja Pedidos
	 ******************************************************************************************/
	
	By xmlFiltrosAccordionBP = By.xpath("//*[@id=\"mat-expansion-panel-header-0\"]");
	By xmlFiltrosBP = By.xpath("//*[@id=\"content-nav\"]/app-order-list/div/mat-accordion/mat-expansion-panel");
	
	By xmlTipoPedidoBP = By.xpath("//div[1]/div[1]/mat-form-field/div/div[1]/div/child::mat-select");
	By xmlEstatusBP =By.xpath("//div[1]/div[2]/mat-form-field/div/div[1]/div/child::mat-select");
	By xmlPlazaBP = By.xpath("//div[2]/div[1]/mat-form-field/div/div[1]/div/child::mat-select");
	
	//By xmlMatSelectOp = By.xpath("//div[contains(@id,'mat-select')]/mat-option/span");
	By xmlMatSelectOp = By.xpath("//div[contains(@id,'cdk-overlay')]/div/div/mat-option/span");
	
	By xmlBuscarBPBtn = By.xpath("//*[@id=\"formFiltroInventario\"]/div[2]/div/div[3]/button");
	By xmlLimpiarBPBtn = By.xpath("//*[@id=\"formFiltroInventario\"]/div[2]/div/div[2]/button");
	By xmlNuevoBP = By.xpath("//*[@id=\"formFiltroInventario\"]/div[2]/div/div[1]/button");
	
	By xmlTPText = By.xpath("//*[@id=\"formFiltroInventario\"]/div[1]/div[1]/div[1]/mat-form-field");
	By xmlEstatusText = By.xpath("//*[@id=\"formFiltroInventario\"]/div[1]/div[1]/div[2]/mat-form-field");
	By xmlPlazaText = By.xpath("//*[@id=\"formFiltroInventario\"]/div[1]/div[2]/div[1]/mat-form-field");
	
	//Localizadores para datos de la tabla
	By xmlTableBP = By.xpath("//*[@id=\"content-nav\"]/app-order-list/div/div");
	By xmlTableThBP = By.xpath("//*[@id=\"content-nav\"]/app-order-list/div/div/table/thead/tr/th");
	By xmlTableTrBP = By.xpath("//*[@id=\"content-nav\"]/app-order-list/div/div/table/tbody/tr");
	By xmlAutorizar = By.xpath("//*[@id=\"content-nav\"]/app-order-list/div/div/table/tbody/tr[1]/td[9]/mat-icon[1]");
	By xmlRechazar = By.xpath("//*[@id=\"content-nav\"]/app-order-list/div/div/table/tbody/tr[1]/td[9]/mat-icon[2]");
	By xmlEmpaque = By.xpath("//*[@id=\"mat-checkbox-1\"]/label/span[1]");
	String xmlFolioPedido = "//*[@id=\"content-nav\"]/app-order-list/div/div/table/tbody/tr[%s]/td[1]/a";
	String folio = "//*[@id=\"content-nav\"]/app-order-list/div/div/table/tbody/tr[%s]/td[1]/a";
	String xmlTipoPedido = "//*[@id=\"content-nav\"]/app-order-list/div/div/table/tbody/tr[%s]/td[5]";
	String xmlPlaza = "//*[@id=\"content-nav\"]/app-order-list/div/div/table/tbody/tr[%s]/td[3]";
	String xmlEstatus = "//*[@id=\"content-nav\"]/app-order-list/div/div/table/tbody/tr[%s]/td[7]";
	
	String xmlExportarPdf = "//*[@id=\"content-nav\"]/app-order-list/div/div/table/tbody/tr[%s]/td[10]/img";
	String xmlExportarExcel = "//*[@id=\"content-nav\"]/app-order-list/div/div/table/tbody/tr[%s]/td[10]/a";
	
	//Localizadores de ventana de confirmacion
	
	By xmlMensajeConfirmacion= By.id("mat-dialog-title-0"); 
	By xmlConfirmar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button");
	
	By xmlMensaje = By.xpath("/html/body/div[3]/div/div/snack-bar-container/div/div/simple-snack-bar/span");
	
	/*
	 * Localizadores Detalle de Pedidos
	 ******************************************************************************************/
	
	By xmlDPFolio = By.xpath("//*[@id=\"dataHead\"]/div[1]/div[1]/div/mat-label");
	By xmlTipoPedidoDP = By.xpath("//*[@id=\"dataHead\"]/div[1]/div[3]/div/mat-label");
	//By xmlEstatusDP = By.xpath("//div[1]/div[contains(@id,'mat-select')]/span/child::span");
	By xmlEstatusDP = By.xpath("//*[@id='dataHead']/div[2]/div[1]/div/mat-select/div/div/span/span");
	By xmlEditarPedido = By.xpath("//*[@id=\"content-nav\"]/app-order-detail/div/div[2]/div/a");
	By xmlRechazarPedido = By.xpath("//*[@id=\"content-nav\"]/app-order-detail/div/div[2]/div/div[1]/button");
	By xmlAutorizarPedido = By.xpath("//*[@id=\"content-nav\"]/app-order-detail/div/div[2]/div/div[2]/button");
	By xmlRegresar = By.xpath("/html/body/app-root/mat-sidenav-container/mat-sidenav-content/app-order-detail/div/div[1]/a");
	
	By xmlComentarios = By.xpath("//mat-form-field/div/div[1]/div/textarea");
	
	By xmlTotalPedido = By.xpath("//div[1]/div/mat-label/h6");
	By xmlTabla = By.xpath("//div[2]/table");
	String xmlCelda = "//div[2]/table/tbody/tr/td[%s]/span";
	By xmlTablaTrDP = By.xpath("//div[2]/table/tbody/tr");
	By xmlTableThDP = By.xpath("//div[2]/table/thead/tr/th");
	
	By xmlGuia = By.xpath("//app-order-detail/div/mat-label[contains(text(), 'Gu�as')]");
	By xmlNumeroGuia = By.name("numeroGuia");
	By xmlProveedor = By.xpath("//div[2]/div/mat-form-field/div/div[1]/div/mat-select[1]");
	By xmlProveedor2 = By.xpath("//mat-select[@aria-label='Proveedor'][@aria-required='true'][@aria-disabled='false']");
	//By xmlFlechita = By.xpath("//div[2]/div/mat-form-field/div/div[1]/div/mat-select/div/div");
	By xmlEstatusGuia = By.xpath("//div[3]/div/mat-form-field/div/div[1]/div/mat-select[1]");
	By xmlAgregarGuiaBtn = By.xpath("//button/span[contains(text(),'Agregar Gu�a')]");
	
	By xmlTablaGuiaTr = By.xpath("//div[1]/table/tbody/tr");
	String xmlGuiaInput = "//div[1]/table/tbody/tr[%s]/td[1]/input";
	String xmlCeldaEstatus = "//div[1]/table/tbody/tr[%s]/td[3]/span/mat-select";
	String xmlRecibidoPor = "//div[1]/table/tbody/tr[%s]/td[4]/input";
	String xmlCalendarioBtn = "//div[1]/table/tbody/tr[%s]/td[5]/mat-form-field/div/div[1]/div[2]/mat-datepicker-toggle/button";
	String xmlExportar = "//div[1]/table/tbody/tr[%s]/td[6]/a";
	By xmlAnioBtn = By.xpath("//mat-calendar-header/div/div/button[1]");
	String xmlCalendarioOp = "//table[@class='mat-calendar-table']/tbody/tr/td/div[contains(text(), '%s')]";
	
	/*
	 * Localizador Pedidos
	 ******************************************************************************************/
	
	By xmlTituloPedidos = By.id("titulo_gral");
	
	
	/**
	 * Constructor
	 ******************************************************************************************/
	
	public BandejaPedidos(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
		this.pedAdmin = new PedidosAdministracionIEP(u, testCase);
	}
	
	/**
	 * Metodos Bandeja Pedidos
	 ******************************************************************************************/
	/**
	 * Metodo para validar que se muestren los filtros en la ventana Bandeja de Pedidos
	 * @return Retorna true si estan visibles en la ventana o false si no estan.
	 * @author Maria Ornelas
	 */
	public Boolean filtrosBP() {
		 Boolean exist = false;
		 exist = me.validarVisualizacion(xmlFiltrosBP);
		 return exist;
	 }
	
	/**
	 * Metodo para dar click en el boton Buscar
	 * @author Maria Ornelas
	 */
	public void clickBuscarBPBtn() {
		me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		u.highLight(xmlBuscarBPBtn);
		testCase.addScreenShotCurrentStep(u, "buscarBPBtn");
		u.click(xmlBuscarBPBtn);
		u.unhighLight(xmlBuscarBPBtn);

	}
	
	/**
	 * Metodo para dar click en el boton limpiar
	 * @author Maria Ornelas
	 */
	public void clickLimpiarBPBtn() {
		u.highLight(xmlLimpiarBPBtn);
		testCase.addScreenShotCurrentStep(u, "clickLimpiarBPBtn");
		u.click(xmlLimpiarBPBtn);
		u.unhighLight(xmlLimpiarBPBtn);
	}
	
	/**
	 * Metodo para dar click en el boton Nuevo
	 * @author Maria Ornelas
	 */
	public void clickNuevoBtn() {
		u.highLight(xmlNuevoBP);
		testCase.addScreenShotCurrentStep(u, "clickNuevoBtn");
		u.unhighLight(xmlNuevoBP);
		u.click(xmlNuevoBP);
	}
	
	/**
	 * Metodo para realizar busqueda de pedidos con los filtros Tipo de Pedido, Estatus y Plaza.
	 * @param option: Tipo de pedido
	 * @param option2: Estatus del pedido
	 * @param option3: Plaza del pedido
	 * @return Regresa true si se seleccionaron correctamente los filtros o false si hubo un error.
	 * @author Maria Ornelas
	 */
	public Boolean busquedaTipoPedido(String option, String option2, String option3) {
		Boolean existOptions = false;
		
		me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		u.waitForLoadPage();
		Boolean tipoPedido = pedAdmin.selectOption(xmlTipoPedidoBP, xmlMatSelectOp, option);
		u.mouseOverAndClick(By.xpath("//*[@id=\"titulo_gral\"]"));
		Boolean estatus = pedAdmin.selectOption(xmlEstatusBP, xmlMatSelectOp, option2);
		u.mouseOverAndClick(By.xpath("//*[@id=\"titulo_gral\"]"));
		Boolean plaza = pedAdmin.selectOption(xmlPlazaBP, xmlMatSelectOp, option3);
		u.mouseOverAndClick(By.xpath("//*[@id=\"titulo_gral\"]"));
		if (tipoPedido == true && estatus == true && plaza == true) {
			existOptions = true;
		}
		return existOptions;
	}
	
	/**
	 * Metodo para realizar busqueda de pedidos con el filtro Estatus.
	 * @param option: Estatus del pedido
	 * @return Regresa true si se selecciono correctamente el filtro o false si hubo un error.
	 * @author Maria Ornelas
	 */
	public Boolean busquedaEstatus(String option) {
		Boolean existOptions = false;
		
		me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		Boolean estatus = pedAdmin.selectOption(xmlEstatusBP, xmlMatSelectOp, option);
		u.mouseOverAndClick(By.xpath("//*[@id=\"titulo_gral\"]"));
		if (estatus == true) {
			existOptions = true;
		}
		return existOptions;
	}
	
	/**
	 * Metodo para validar que la tabla de pedidos muestra resultados despu�s de realizar una busqueda
	 * @param col: Recibe el numero de columnas que debe haber en dicha tabla
	 * @return Retorna true si muestra resultados o false si no muestra correctamente los resultados 
	 * @author Maria Ornelas
	 */
	public Boolean resultadosBusquedaBP(int col) {
		 Boolean resultado = false;
		 resultado = pedAdmin.resultadosBusqueda(col, xmlTableThBP, xmlTableTrBP, xmlTableBP);
		 return resultado;
	 }
	
	/**
	 * Metodo para obtener el folio del Pedido desde la tabla de resultados en
	 * Bandeja de pedidos
	 * @param r: Recibe el numero de fila en el que esta el folio 
	 * @return Retorna el folio 
	 * @author Maria Ornelas
	 */
	public String getFolio(String r) {
		
		By xpath = By.xpath(String.format(xmlFolioPedido, r));
		String folio = u.getText(xpath);
		return folio;
		
	}
	
	/**
	 * Metodo para obtener el id del pedido
	 * @param folio
	 * @return Retorna el pedido
	 * @author Maria Due�as
	 */
	public String getPedido(String folio) {
		
		String cadena[] = folio.split("_");
		Integer l = cadena.length;
		String pedido = cadena[l-1];
		return pedido;
		
	}
	
	public void clickFolio(String r) {
		
		By xpath = By.xpath(String.format(xmlFolioPedido, r));
		u.highLight(xpath);
		testCase.addScreenShotCurrentStep(u, "clickFolio");
		u.click(xpath);
		
	}
	
	/**
	 * Metodo para obtener el Tipo de pedido desde la tabla de resultados en ventana bandeja de pedidos
	 * @param r: Recibe el numero de la fila en la quie se encuentra el tipo de pedido
	 * @return
	 */
	public String getTipoPedido(String r) {
		
		By xpath = By.xpath(String.format(xmlTipoPedido, r));
		String pedido = u.getText(xpath);
		return pedido;
		
	}
	
	public String getPlaza(String r) {
		
		By xpath = By.xpath(String.format(xmlPlaza, r));
		String plaza = u.getText(xpath);
		return plaza;
		
	}

	public String getEstatus(String r) {
		
		By xpath = By.xpath(String.format(xmlEstatus, r));
		String estatus = u.getText(xpath);
		return estatus;
		
	}
	
	public By getMensaje() {
		return xmlMensaje;
	}
	
	public String getTotalPedido() {
		String precio = u.getText(xmlTotalPedido);
		String [] p = precio.split(": ");
		precio = p[1];
		return precio; 
	}
	
	public String getDatoCelda(String c) {
		
		String dato = "";
		
		String xpath = String.format(xmlCelda, c); 
		dato = u.getText(By.xpath(xpath)).trim();
		return dato;
		
	}
	
	public void clickAutorizar() {
		u.highLight(xmlAutorizar);
		testCase.addScreenShotCurrentStep(u, "clickAutorizar");
		u.unhighLight(xmlAutorizar);
		u.click(xmlAutorizar);
		testCase.addScreenShotCurrentStep(u, "clickAutorizar");
	}
	
	public String getMessageConfirm() {
		
		String mensaje = u.getText(xmlMensajeConfirmacion);
		u.highLight(xmlMensajeConfirmacion);
		testCase.addScreenShotCurrentStep(u, "getMessageConfirm");
		u.unhighLight(xmlMensajeConfirmacion);
		return mensaje;
		
	}
	
	public Boolean validarMensajeConfirmacion(String pedido, String tipo) {
		
		Boolean equal = false;
		String mensaje = "";
		String mensajeConfirmacion = "";
		
		if (tipo.equals("Autorizar")) {
			
			mensaje = "�Est� seguro que quiere autorizar el pedido %s?";
			mensajeConfirmacion = String.format(mensaje, pedido);
			
		} else if (tipo.equals("Rechazar")) {
			
			mensaje = "�Est� seguro que quiere rechazar el pedido %s?";
			mensajeConfirmacion = String.format(mensaje, pedido);
			
		}
		
		if(mensajeConfirmacion.equals(getMessageConfirm())) {
			equal = true;
		}
		
		return equal;
		
	}
	
	public void clickConfirmar() {
		u.highLight(xmlConfirmar);
		testCase.addScreenShotCurrentStep(u, "clickConfirmar");
		u.unhighLight(xmlConfirmar);
		u.click(xmlConfirmar);
	}
	
	public void clickRechazar() {
		u.highLight(xmlRechazar);
		testCase.addScreenShotCurrentStep(u, "clickRechazar");
		u.unhighLight(xmlRechazar);
		u.click(xmlRechazar);
		testCase.addScreenShotCurrentStep(u, "clickRechazar");
	}
	
	public void checkBoxEmpaque() {
		u.highLight(xmlEmpaque);
		testCase.addScreenShotCurrentStep(u, "checkBoxEmpaque");
		u.unhighLight(xmlEmpaque);
		u.click(xmlEmpaque);
		testCase.addScreenShotCurrentStep(u, "checkBoxEmpaque");
	}
	
	public void exportar(String r) {
		
		By pdf = By.xpath(String.format(xmlExportarPdf, r));
		By excel = By.xpath(String.format(xmlExportarExcel, r));
		
		u.highLight(pdf);
		u.highLight(excel);
		testCase.addScreenShotCurrentStep(u, "exportar");
		u.unhighLight(pdf);
		u.unhighLight(excel);
		//u.click(pdf);
		u.click(excel);
	}
	
	public Boolean validarDescarga() {
		Boolean exist = false;
		
		String pathYourSystem = System.getProperty("user.home") + "\\Downloads\\reportPedido.xlsx";
		File f = new File(pathYourSystem);
		
		if (f.exists()) {
			exist = true;
		}
		
		return exist;
	}
	
	public Boolean validarDatos(String r, String tipoPedido) throws IOException {
		Boolean correct = false;
	    
		me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
	    String pathYourSystem = System.getProperty("user.home") + "\\Downloads\\reportPedido.xlsx";
	    File fichero = new File(pathYourSystem);
        FileInputStream inputStream = new FileInputStream(new File(pathYourSystem));
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        
        String cellFolio = datoCelda(firstSheet, 1, 1);
        String cellTipoPedido = datoCelda(firstSheet, 2, 1);
        String cellPlaza = datoCelda(firstSheet, 3, 1);
        String cellEstatus = datoCelda(firstSheet, 4, 1);
        String cellTotalP = datoCelda(firstSheet, 5, 1);
        
        
	    
        if (cellFolio.equals(getFolio(r)) && cellTipoPedido.equals(getTipoPedido(r)) 
        		&& cellPlaza.equals(getPlaza(r)) && cellEstatus.equals(getEstatus(r)) ) {
        	
        	String f = String.format(xmlFolioPedido, r);
        	u.click(By.xpath(f));
        	me.scrollToElement(xmlTabla);
        	
        	Integer filas = pedAdmin.filasTabla(10, xmlTableThDP, xmlTablaTrDP, xmlTabla);
        	Integer limite = 1;
        	
        	if ( filas != 0 ) {
        		limite = 8 + filas;
        	} else {
        		limite = 9;
        	}
        	
        	Integer col = 0;
        	Integer col2 = 2;
        	switch(tipoPedido) {
        	case "Ambientaci�n de Tiendas": 
        			col = 1;
        			col2 = 3;
        		break;
        	}
        	
        	for (int i = 8; i < limite; i++) {
        		String cellPopId = datoCelda(firstSheet, i, col);
        		String PopId = getDatoCelda(Integer.toString(col2));
        		
        		col++;
        		col2++;
        		
                String cellPop = datoCelda(firstSheet, i, col);
                String Pop = getDatoCelda(Integer.toString(col2));
                
        		col++;
        		col2++;
                
                String cellContenido = datoCelda(firstSheet, i, col);
                String Contenido = getDatoCelda(Integer.toString(col2));
                
        		col++;
        		col2++;
                
                String cellDescripcion = datoCelda(firstSheet, i, col);
                String Descripcion = getDatoCelda(Integer.toString(col2));
                
        		col++;
        		col2++;
                
                String cellPrecio = datoCelda(firstSheet, i, col);
                String Precio = getDatoCelda(Integer.toString(col2));
               
        		col++;
        		col2++;
                
                String cellCantidad = datoCelda(firstSheet, i, col);
                String Cantidad = getDatoCelda(Integer.toString(col2));
               
        		col++;
        		col2++;
                
                String cellTotal = datoCelda(firstSheet, i, col);
                String Total = getDatoCelda(Integer.toString(col2));
               
                if (tipoPedido.equals("Ambientaci�n de Tiendas")) {
                	col += 3;
                	col2 += 3;
                } else {
                	col += 2;
                	col2 += 2;
                }
                String cellComentarios = datoCelda(firstSheet, i, col);
                String Comentarios = getDatoCelda(Integer.toString(col2));
                
            	if (cellPopId.equals(PopId) && cellPop.equals(Pop)  && cellContenido.equals(Contenido)
            			&& cellDescripcion.equals(Descripcion) && cellPrecio.equals(Precio) && cellCantidad.equals(Cantidad)
            			&& cellTotal.equals(Total) && cellComentarios.equals(Comentarios) && cellTotalP.equals(getTotalPedido())) {
            		
            		u.highLight(xmlTabla);
            		correct = true;
            		
            	} else {
            		correct = false;
            		break;
            	}
        	}
        	
        	
        }
        
        testCase.addScreenShotCurrentStep(u, "validarDatos");
        
        inputStream.close();
        
        if (correct == true) {
        	if (fichero.delete())
         	   System.out.println("El archivo ha sido borrado satisfactoriamente");
        	else
         	   System.out.println("El archivo no puede ser borrado");
        }
        
		return correct;
		
	}
	
	public String datoCelda(Sheet sheet, int r, int c) {
		DataFormatter formatter = new DataFormatter();
        
        Cell celda = sheet.getRow(r).getCell(c);
        String contenidoCelda = formatter.formatCellValue(celda);
        //System.out.println("celda: -" + contenidoCelda + "-");
        
        return contenidoCelda;
	}
	
	public Boolean buscarPedido(String folioPedido) {
		Boolean exist = false;
		
		String pedido = "";
		
		me.scrollToElement(xmlTableBP);
		List <WebElement> tablaTr = u.getDriver().findElements(xmlTableTrBP);
		Integer row_count = tablaTr.size();
		
		//System.out.print(row_count + "\n");
		
		if (row_count >= 1) {
			for (int i=1; i <= row_count; i++) {
				pedido = String.format(folio, Integer.toString(i)); 
				String f = u.getText(By.xpath(pedido));
				//System.out.print("-" + f + "-\n");
				if (folioPedido.equals(f)) {
					exist = true;
					u.highLight(By.xpath(pedido));
					testCase.addScreenShotCurrentStep(u, "buscarPedido");
					u.click(By.xpath(pedido));
					break;
				}
			}
		}
		testCase.addScreenShotCurrentStep(u, "buscarPedido");
		return exist;
	}
	
	public Boolean validarDatosDetallePedidos(String folio, String tipoPedido, String estatus) {
		
		me.explicitWaitToBeInvisible(Duration.ofSeconds(5));
		me.scrollToElement(xmlRegresar);
		
		Boolean exist = false;
		
		u.mouseOverAndClick(xmlDPFolio);
		u.highLight(xmlDPFolio);
		String fDP = u.getText(xmlDPFolio);
		
		String tipoPedidoDP = u.getText(xmlTipoPedidoDP);
		u.highLight(xmlTipoPedidoDP);
		String estatusDP = u.getText(xmlEstatusDP);
		u.highLight(xmlEstatusDP);
		
		if(folio.equals(fDP) && tipoPedido.equals(tipoPedidoDP) && estatus.equals(estatusDP)) {
			exist = true;
		}
		
		testCase.addScreenShotCurrentStep(u, "validarDatosDetallePedidos");
		u.unhighLight(xmlDPFolio);
		u.unhighLight(xmlTipoPedidoDP);
		u.unhighLight(xmlEstatusDP);
		
		return exist;
		
	}
	
	public Boolean validarDatosPreguardados(String folio, String tipoPedido, String estatus) {
		Boolean correct = false;
		
		Boolean datos = validarDatosDetallePedidos(folio, tipoPedido, estatus);
		Boolean datos2 = false;
		
		String comentarios = u.getText(xmlComentarios);
		u.highLight(xmlComentarios);
		testCase.addScreenShotCurrentStep(u, "Comentarios");
		u.unhighLight(xmlComentarios);
		
		me.scrollToElement(xmlTabla);
		
		if (getDatoCelda("2") != "" && getDatoCelda("3")  != ""  && getDatoCelda("4")  != "" &&
    			getDatoCelda("5")  != ""  && getDatoCelda("6")  != ""  && getDatoCelda("7")  != "" 
    			&& getDatoCelda("8")  != ""  && getDatoCelda("10")  != "" ) {
    		
    		u.highLight(xmlTabla);
    		datos2 = true;
    		
    	}
		
		if (datos == true && datos2 == true) correct = true;
		
		return correct;
		
	}
	
	public void clickEditarPedido() {
		u.highLight(xmlEditarPedido);
		testCase.addScreenShotCurrentStep(u, "clickEditarPedido");
		u.click(xmlEditarPedido);
	}
	
	public void clickAutorizarPedido() {
		me.scrollToElement(xmlAutorizarPedido);
		u.highLight(xmlAutorizarPedido);
		testCase.addScreenShotCurrentStep(u, "clickAutorizarPedido");
		u.unhighLight(xmlAutorizarPedido);
		u.click(xmlAutorizarPedido);
	}
	
	public void clickRechazarPedido() {
		me.scrollToElement(xmlRechazarPedido);
		u.highLight(xmlRechazarPedido);
		testCase.addScreenShotCurrentStep(u, "clickRechazarPedido");
		u.unhighLight(xmlRechazarPedido);
		u.click(xmlRechazarPedido);
	}
	
	
	public void regresar() {
		me.scrollToElement(xmlRegresar);
		u.highLight(xmlRegresar);
		testCase.addScreenShotCurrentStep(u, "regresar");
		u.click(xmlRegresar);
	}
	
	/**
	 * Metodo para validar que se limpien los filtros y la tabla de resultados
	 * @return Boolean clean: Retorna true si se limpiaron correctamente o false de lo conterario
	 * @author Mar�a Due�as
	 */
	public Boolean validarBtnLimpiar() {
		
		Boolean clean = false;
		
		if (u.getText(xmlTPText).contentEquals("Tipo de pedido") && u.getText(xmlEstatusText).contentEquals("Estatus") 
				&& u.getText(xmlPlazaText).contentEquals("Plaza") && resultadosBusquedaBP(10) == false) {
			
			clean = true;
			
		}
		
		return clean;
		
	}
	
	public Boolean validarCantPedidos() {
		Boolean exist = false;
		
		Integer pedidos = pedAdmin.filasTabla(10, xmlTableThBP, xmlTableTrBP, xmlTableBP);
		
		if (pedidos >= 1) {
			exist = true;
		}
		
		return exist;
	}
	
	/**
	 * Metodo para validar el estatus del pedido
	 * @param estatus
	 * @return Retorna true si el estatus corresponde o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean validarEstatus(String estatus) {
		Boolean correct = false;
		
		me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		u.hardWait(20);
		me.scrollToElement(xmlEstatusDP);
		String estatusDP = u.getText(xmlEstatusDP);
		u.highLight(xmlEstatusDP);
		
		if (estatusDP.equals(estatus)) {
			correct = true;
		}
		
		testCase.addScreenShotCurrentStep(u, "validarEstatus");
		return correct;
	}
	
	/**
	 * Metodo para seleccionar la fecha en calendario
	 * @param fecha (String day*mon*year ejemplo: 1*JAN*2022)
	 * @param calendario
	 * @return Retorna true si se selecciono correctamente la fecha o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean selectDate(String fecha, By calendario) {
		Boolean correct = false;
		try {
			u.highLight(calendario);
			testCase.addScreenShotCurrentStep(u, "clickCalendario");
			u.unhighLight(calendario);
			u.hardWait(3);
			u.click(calendario);
			
			String [] f = fecha.split("\\*");
			//System.out.print(f + "\n");
			u.click(xmlAnioBtn);
			
			String anio = String.format(xmlCalendarioOp, f[2]);
			u.highLight(By.xpath(anio));
			testCase.addScreenShotCurrentStep(u, "clickAnio");
			u.unhighLight(By.xpath(anio));
			u.click(By.xpath(anio));
			
			String mes = String.format(xmlCalendarioOp, f[1]);
			u.highLight(By.xpath(mes));
			testCase.addScreenShotCurrentStep(u, "clickMes");
			u.unhighLight(By.xpath(mes));
			u.click(By.xpath(mes));
			
			String dia = String.format(xmlCalendarioOp, f[0]);
			u.highLight(By.xpath(dia));
			testCase.addScreenShotCurrentStep(u, "clickDia");
			u.unhighLight(By.xpath(dia));
			u.click(By.xpath(dia));
			
			correct = true;
		}catch(Exception e) {
			testCase.addScreenShotCurrentStep(u, "capturaerror");
		}
		
		
		return correct;
	}
	
	public Boolean selectOption(By select, By opciones, String opcion) { 
		Boolean existOption = false;
		//WebElement element =  me.explicitWaitToBeClickable(select, 30);
		//By mat_select = By.xpath("//label/span[contains(text(), '"+select+"')]");
		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(select));
		((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click()", element);
		//u.getDriver().findElement(select).click();
		//u.hardWait(2);
		//element.click();
		List<WebElement> allOptions = u.getDriver().findElements(opciones);
		
		for(WebElement option:allOptions){
			//System.out.print("-"+option.getText()+"-"+opcion+"-\n");
			if(option.getText().equals(opcion)) {//busca la opcion que se pasa por parametro
			  u.hardWait(1);
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
	
	/**
	 * Metodo para ingresar la informacion de los campos Guia
	 * @param guia
	 * @param proveedor
	 * @param estatus
	 * @return Retorna true si se ingresaron correctamente los datos o false en caso contrario.
	 * @author Maria Due�as
	 */
	public Boolean camposGuia(String guia) {
		
		Boolean correct = false;
		
		try {
			
			me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
			u.waitForLoadPage();
			
			me.scrollToElement(xmlGuia);
			
			List <WebElement> tablaTr = u.getDriver().findElements(xmlTablaGuiaTr);
			Integer row_count = tablaTr.size();
			
			if (row_count >= 1 ) {
				for (int i = 1; i <= row_count; i++) {
					By by = By.xpath(String.format(xmlGuiaInput, Integer.toString(i)));
					u.clear(by);
					u.sendKeys(by, guia);
				}
			}
		
			cambiarEstatus("Nuevo");
			correct = true; 
			
		}catch(Exception e) {
			correct = false;
			System.out.print(e);
			testCase.addScreenShotCurrentStep(u, "captura");
		}
		
		return correct;
	}
	
	
	/**
	 * Metodo para dasr click en el boton Agregar Gu�a
	 * @author Maria Due�as
	 */
	public void clickAgregarGuia() {
		u.highLight(xmlAgregarGuiaBtn);
		testCase.addScreenShotCurrentStep(u, "AgregarGuia");
		u.unhighLight(xmlAgregarGuiaBtn);
		u.click(xmlAgregarGuiaBtn);
		testCase.addScreenShotCurrentStep(u, "clickAgregarGuia");
	}
	
	/**
	 * Metodo para cambiar el estado de la guia de los pedidos
	 * @param estatus
	 * @return Retorna true si se cambio el estatus de la guia o false en caso contrario
	 */
	public Boolean cambiarEstatus(String estatus) {
		Boolean correct = false;
		me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		u.waitForLoadPage();
		List <WebElement> tablaTr = u.getDriver().findElements(xmlTablaGuiaTr);
		Integer row_count = tablaTr.size();
		Boolean newEstatus = false;
		
		if (row_count >= 1 ) {
			for (int i = 1; i <= row_count; i++) {
				By by = By.xpath(String.format(xmlCeldaEstatus, Integer.toString(i)));
				newEstatus = pedAdmin.selectOption(by, xmlMatSelectOp, estatus);
				if (!newEstatus) {
					correct = false;
					testCase.addScreenShotCurrentStep(u, "Cambioestatus");
					break;
				}else {
					correct = true;
				}
				me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
			}
		}
		
		return correct;
	}
	
	/**
	 * Metodo para agregar los datos de entrega
	 * @param recibidoPor
	 * @param fecha
	 * @return Retorna true si se registraron correctamente los datos o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean agregarDatosEntrega(String recibidoPor, String fecha) {
		Boolean correct = false;
		
		try {
			me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
			u.waitForLoadPage();
			List <WebElement> tablaTr = u.getDriver().findElements(xmlTablaGuiaTr);
			Integer row_count = tablaTr.size();
			Boolean f = false;
			
			if (row_count >= 1 ) {
				for (int i = 1; i <= row_count; i++) {
					
					By recibido = By.xpath(String.format(xmlRecibidoPor, Integer.toString(i)));
					u.highLight(recibido);
					u.sendKeys(recibido, recibidoPor);
					testCase.addScreenShotCurrentStep(u, "sendRecibidoPor");
					u.unhighLight(recibido);
					u.mouseOverAndClick(xmlGuia);
					me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
					
					By calendario = By.xpath(String.format(xmlCalendarioBtn, Integer.toString(i)));
					f = selectDate(fecha, calendario);
					
					if (!f) {
						correct = false;
						testCase.addScreenShotCurrentStep(u, "Cambioestatus");
						break;
					}else {
						correct = true;
					}
				}
			}
			
			
		}catch(Exception e){
			
		}
		
		return correct;
	}
	
	/**
	 * Metodo para dar click en el boton exportar por proveedor 
	 * @author Maria Due�as
	 */
	public void clickExportarPorProveedor() {
		Boolean correct = false;
		
		me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		u.waitForLoadPage();
		me.scrollToElement(xmlGuia);
		
		List <WebElement> tablaTr = u.getDriver().findElements(xmlTablaGuiaTr);
		Integer row_count = tablaTr.size();
		
		if (row_count >= 1 ) {
			for (int i = 1; i <= 1; i++) {
				By by = By.xpath(String.format(xmlExportar, Integer.toString(i)));
				u.highLight(by);
				testCase.addScreenShotCurrentStep(u, "ExportarPorProveedor");
				u.unhighLight(by);
				u.click(by);
				u.hardWait(10);
				testCase.addScreenShotCurrentStep("clickExportarPorProveedor");
			}
		}
		
	}
	
	/**
	 * Metodo para abrir archivo excel de proveedores
	 * @return Retorna true si se dio click en la descarga o false en caso contrario
	 * @throws FindFailed
	 * @author Maria Due�as
	 */
	public Boolean abrirExcel() throws FindFailed {
		
		u.waitForLoadPage();
		
		testCase.addScreenShotCurrentStep("Open");
		Boolean correct = me.clickElementSikuli("IEP", "OpenWhite"); 
		u.hardWait(3);
		
		testCase.addScreenShotCurrentStep("capturaExcel");
		
		me.clickElementSikuli("SARv3", "CloseExcel"); //Da click en la tachita del excel para cerrarlo y continuar con los pasos
		
		return correct;
	}
	
	/*
	 * Metodos ventana Pedidos
	 *************************************************************************************/
	
	public Boolean validarPantalla() {
		Boolean exist = false;
		
		if (me.validarVisualizacion(xmlTituloPedidos)) {
			me.scrollToElement(xmlTituloPedidos);
			if(u.getText(xmlTituloPedidos).trim().equals("Crear Pedidos > Nuevo")) {
				exist = true;
			}
		}
		return exist;
	}
	
}
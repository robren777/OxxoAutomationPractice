package WEB.inventarioEspacios.om;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class TiposDePopsIEP {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	PedidosAdministracionIEP pedAdmin;
	
	/**
	 * Localizadores Tipos de Pops
	 ******************************************************************************************/
	
	By xmlTipoPop = By.name("nombreTipoPop");
	By xmlNuevoBtn = By.xpath("//*[@id=\"formFiltroPop\"]/div[2]/div/div[1]/button");
	By xmlLimpiarBtn = By.xpath("//*[@id=\"formFiltroPop\"]/div[2]/div/div[3]/button");
	By xmlBuscarBtn = By.xpath("//*[@id=\"formFiltroPop\"]/div[2]/div/div[4]/button");
	By xmlEliminarBtn = By.xpath("//button/span[contains(text(), 'Eliminar')]");
	By xmlMensaje = By.xpath("//h5[contains(@id, 'mat-dialog-title')]");
	
	By xmlTable = By.xpath("//table");
	By xmlTableTr = By.xpath("//table/tbody/tr");
	By xmlTableTh = By.xpath("//table/thead/tr/th");
	String nombrePop = "//table/tbody/tr[%s]/td[3]";
	String tableTr = "//table/tbody/tr[%s]";
	String editarTr = "//table/tbody/tr[%s]/td[5]/mat-icon";
	String seleccionarTr = "//table/tbody/tr[%s]/td[1]/mat-checkbox";
	
	/**
	 * Localizadores Tipos de Pops - Nuevo
	 ******************************************************************************************/
	
	By xmlPromociones = By.xpath("//*[@id=\"content-nav\"]/app-pop-type-detail/div/div/div[3]/div[1]/div[1]/mat-label");
	By xmlKit = By.xpath("//*[@id=\"content-nav\"]/app-pop-type-detail/div/div/div[3]/div[2]/div/div[1]/mat-label");
	By xmlSegmento = By.xpath("//*[@id=\"content-nav\"]/app-pop-type-detail/div/div/div[3]/div[1]/div[3]/div[1]/mat-label");
	By xmlPrecio = By.xpath("//*[@id=\"content-nav\"]/app-pop-type-detail/div/div/div[3]/div[2]/div/div[3]/mat-label");
	By xmlNegocio = By.xpath("//*[@id=\"content-nav\"]/app-pop-type-detail/div/div/div[3]/div[1]/div[3]/div[3]/mat-label");
	By xmlContenido = By.xpath("//*[@id=\"content-nav\"]/app-pop-type-detail/div/div/div[3]/div[2]/div/div[5]/mat-label");
	
	By xmlPromRadio = By.xpath("//*[@id=\"content-nav\"]/app-pop-type-detail/div/div/div[3]/div[1]/div[2]/mat-radio-group");
	By xmlKitRadio = By.xpath("//*[@id=\"content-nav\"]/app-pop-type-detail/div/div/div[3]/div[2]/div/div[2]/mat-radio-group");
	By xmlSegmRadio = By.xpath("//*[@id=\"content-nav\"]/app-pop-type-detail/div/div/div[3]/div[1]/div[3]/div[2]/mat-radio-group");
	By xmlPrecRadio = By.xpath("//*[@id=\"content-nav\"]/app-pop-type-detail/div/div/div[3]/div[2]/div/div[4]/mat-radio-group");
	By xmlNegRadio = By.xpath("//*[@id=\"content-nav\"]/app-pop-type-detail/div/div/div[3]/div[1]/div[3]/div[4]/mat-radio-group");
	By xmlContRadio = By.xpath("//*[@id=\"content-nav\"]/app-pop-type-detail/div/div/div[3]/div[2]/div/div[6]/mat-radio-group");
	
	By xmlCancelarBtn = By.xpath("//*[@id=\"content-nav\"]/app-pop-type-detail/div/div/div[4]/div/div[1]/button");
	By xmlLimpiarPopBtn = By.xpath("//*[@id=\"content-nav\"]/app-pop-type-detail/div/div/div[4]/div/div[2]/button");
	By xmlGuardarBtn = By.xpath("//*[@id=\"content-nav\"]/app-pop-type-detail/div/div/div[4]/div/div[3]/button");
	By xmlConfirmar = By.xpath("//app-dialog-confirm/div[2]/div/div/div/button");
	By xmlCancelar = By.xpath("//app-dialog-confirm/div[2]/div/div/button");
	/**
	 * Constructor
	 ******************************************************************************************/
	
	public TiposDePopsIEP(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
		this.pedAdmin = new PedidosAdministracionIEP(u, testCase);
	}
	
	/**
	 * Metodos Tipos de Pops
	 ******************************************************************************************/
	
	/**
	 * Metodo para validar que se muetren los componentes
	 * Filtros. Nombre de tipo de POP
	 * Bot�n. Buscar
	 * Bot�n. Limpiar
	 * Bot�n. Nuevo
	 * Grid principal. ID Tipo de POP/Nombre/Para promociones.
	 * @return Retorna true si se muestran correctamente los componentes o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean pantallaTipoPop() {
		Boolean exist = false;
		
		try {
			u.highLight(xmlTipoPop);
			u.highLight(xmlNuevoBtn);
			u.highLight(xmlLimpiarBtn);
			u.highLight(xmlBuscarBtn);
			u.highLight(xmlTable);
			
			testCase.addScreenShotCurrentStep(u, "TipoPop");
			
			u.unhighLight(xmlTipoPop);
			u.unhighLight(xmlNuevoBtn);
			u.unhighLight(xmlLimpiarBtn);
			u.unhighLight(xmlBuscarBtn);
			u.unhighLight(xmlTable);
			
			testCase.addTextEvidenceCurrentStep("Se muestran correctamente los componentes");
			
			exist = true;
			
		}catch(Exception e) {
			testCase.addScreenShotCurrentStep(u, "Error");
		}
		
		return exist;
	}
	
	/**
	 * Metodo para capturar el nombre del tipo de pedido 
	 * @param nombre
	 * @return Retorna true si se ingreso correctamente o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean buscarTipoPop(String nombre) {
		Boolean correct = false;
		
		try {
			u.highLight(xmlTipoPop);
			u.sendKeys(xmlTipoPop, nombre);
			testCase.addScreenShotCurrentStep(u, "NombreATipoPop");
			u.unhighLight(xmlTipoPop);
			
			testCase.addTextEvidenceCurrentStep("Se ingresa correctamente el tipo de pedido a buscar");
			
			correct = true;
			
		}catch(Exception e){
			
		}
		
		return correct;
	}
	
	/**
	 * Metodo para dar click en el boton buscar de la ventana Tipos de POPs
	 * @author Maria Due�as
	 */
	public void clickBuscarBtn() {
		u.highLight(xmlBuscarBtn);
		testCase.addScreenShotCurrentStep(u, "BuscarBtn");
		u.unhighLight(xmlBuscarBtn);
		u.click(xmlBuscarBtn);
		testCase.addScreenShotCurrentStep(u, "clickBuscarBtn");
	}
	
	/**
	 * Metodo para dar click en el boton Eliminar de la ventana Tipos de POPs
	 * @author Maria Due�as
	 */
	public void clickEliminarBtn() {
		u.highLight(xmlEliminarBtn);
		testCase.addScreenShotCurrentStep(u, "EliminarBtn");
		u.unhighLight(xmlEliminarBtn);
		u.click(xmlEliminarBtn);
		testCase.addScreenShotCurrentStep(u, "clickEliminarBtn");
	}
	
	/**
	 * Metodo para validar que se muestren resultados despues de una busqueda
	 * @param col
	 * @return Retorna true si se muestran resultados o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean resultadosBusqueda(Integer col) {
		Boolean resultado = false;
		
		List <WebElement> tablaTh = u.getDriver().findElements(xmlTableTh);
		Integer col_count = tablaTh.size();
		List <WebElement> tablaTr = u.getDriver().findElements(xmlTableTr);
		Integer row_count = tablaTr.size();
		
		if (row_count >= 1 && col_count == col) {
			resultado = true;
			testCase.addTextEvidenceCurrentStep("Se muestran correctamente los resultados");
		}
		
		me.scrollToElement(xmlTable);
		u.highLight(xmlTable);
		testCase.addScreenShotCurrentStep(u, "resultadosBusqueda");
		u.unhighLight(xmlTable);
		
		return resultado;
	}
	
	/**
	 * Metodo para dar click en el boton limpiar de la ventana Tipos de POPs
	 * @author Maria Due�as
	 */
	public void clickLimpiarBtn() {
		u.highLight(xmlLimpiarBtn);
		testCase.addScreenShotCurrentStep(u, "LimpiarBtn");
		u.unhighLight(xmlLimpiarBtn);
		u.click(xmlLimpiarBtn);
		testCase.addScreenShotCurrentStep(u, "clickLimpiarBtn");
	}
	
	public Boolean limpiarPantalla() {
		Boolean correct = false;
		
		try {
			me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
			me.scrollToElement(xmlTipoPop);
			String pop = u.getText(xmlTipoPop);
			if (pop.length() == 0) {
				correct = true;
				testCase.addScreenShotCurrentStep(u, "limpiarPantalla");
				testCase.addTextEvidenceCurrentStep("Se limpio correctamente la pantalla");
			}
		}catch(Exception e) {
			testCase.addScreenShotCurrentStep(u, "Error");
		}
		
		return correct;
	}
	
	/**
	 * Metodo para dar click en el boton Nuevo de la ventana Tipos de POPs
	 * @author Maria Due�as
	 */
	public void clickNuevoBtn() {
		u.highLight(xmlNuevoBtn);
		testCase.addScreenShotCurrentStep(u, "NuevoBtn");
		u.unhighLight(xmlNuevoBtn);
		u.click(xmlNuevoBtn);
		testCase.addScreenShotCurrentStep(u, "clickNuevoBtn");
	}
	
	/**
	 * Metodo para verificar que el boton Eliminar se active
	 * @return Retorna true si se activa el boton o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean isVisibleEliminarBtn() {
		Boolean isVisible = false;
		
		me.scrollToElement(xmlTipoPop);
		if(u.getDriver().findElement(xmlEliminarBtn).isDisplayed()) {
			isVisible = true;
			u.highLight(xmlEliminarBtn);
		}
		
		testCase.addScreenShotCurrentStep(u, "isVisibleEliminarBtn");
		return isVisible;
	}
	
	/**
	 * Metodo para validar que se muestre mensaje de confirmaci�n
	 * @return Retyorna true si muestra correctamente el mensaje o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean confirmarMensaje() {
		Boolean exist = false;
		
		u.hardWait(3);
		String mensaje = u.getText(xmlMensaje);
		//System.out.print("-"+mensaje+"-\n");
		u.highLight(xmlMensaje);
		testCase.addScreenShotCurrentStep(u, "Mensaje");
		u.unhighLight(xmlMensaje);
		
		if(mensaje.equals("Est� seguro que desea eliminar los Tipo Pops seleccionados. �Desea continuar?")) {
			exist = true;
		}
		
		return exist;
	}
	/**
	 * Metodos Tipos de Pops - Nuevo
	 ******************************************************************************************/
	
	/**
	 * Metodo para validar que se muestren los componentes d la pantalla Nuevo Tipo de Pop
	 * @return Retorna true si encuentra todos los componentes o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean pantallaTipoPopNuevo() {
		Boolean exist = false;
		
		try {
			u.highLight(xmlTipoPop);
			u.highLight(xmlPromociones);
			u.highLight(xmlKit);
			u.highLight(xmlSegmento);
			u.highLight(xmlPrecio);
			u.highLight(xmlNegocio);
			u.highLight(xmlContenido);
			u.highLight(xmlPromRadio);
			u.highLight(xmlKitRadio);
			u.highLight(xmlSegmRadio);
			u.highLight(xmlPrecRadio);
			u.highLight(xmlNegRadio);
			u.highLight(xmlContRadio);
			testCase.addScreenShotCurrentStep(u, "TipoPop");
			
			me.scrollToElement(xmlCancelarBtn);
			u.highLight(xmlCancelarBtn);
			u.highLight(xmlLimpiarPopBtn);
			u.highLight(xmlGuardarBtn);
			
			testCase.addScreenShotCurrentStep(u, "TipoPop2");
			
			u.unhighLight(xmlTipoPop);
			u.unhighLight(xmlPromociones);
			u.unhighLight(xmlKit);
			u.unhighLight(xmlSegmento);
			u.unhighLight(xmlPrecio);
			u.unhighLight(xmlNegocio);
			u.unhighLight(xmlContenido);
			u.unhighLight(xmlPromRadio);
			u.unhighLight(xmlKitRadio);
			u.unhighLight(xmlSegmRadio);
			u.unhighLight(xmlPrecRadio);
			u.unhighLight(xmlNegRadio);
			u.unhighLight(xmlContRadio);
			u.unhighLight(xmlCancelarBtn);
			u.unhighLight(xmlLimpiarPopBtn);
			u.unhighLight(xmlGuardarBtn);
			
			testCase.addTextEvidenceCurrentStep("Se muestran correctamente los componentes");
			
			exist = true;
			
		}catch(Exception e) {
			testCase.addScreenShotCurrentStep(u, "Error");
		}
		
		return exist;
	}
	
	/**
	 * Metodo para dar click en el boton limpiar de la ventana Tipos de POPs - Nuevo
	 * @author Maria Due�as
	 */
	public void clickLimpiarPopBtn() {
		me.scrollToElement(xmlLimpiarPopBtn);
		u.highLight(xmlLimpiarPopBtn);
		testCase.addScreenShotCurrentStep(u, "LimpiarBtn");
		u.unhighLight(xmlLimpiarPopBtn);
		u.click(xmlLimpiarPopBtn);
		testCase.addScreenShotCurrentStep(u, "clickLimpiarBtn");
	}
	
	/**
	 * Metodo para dar click en el boton Cancelar de la ventana Tipos de POPs - Nuevo
	 * @author Maria Due�as
	 */
	public void clickCancelarBtn() {
		me.scrollToElement(xmlCancelarBtn);
		u.highLight(xmlCancelarBtn);
		testCase.addScreenShotCurrentStep(u, "CancelarBtn");
		u.unhighLight(xmlCancelarBtn);
		u.click(xmlCancelarBtn);
		testCase.addScreenShotCurrentStep(u, "clickCancelarBtn");
	}
	
	/**
	 * Metodo para dar click en el boton Guardar de la ventana Tipos de POPs - Nuevo
	 * @author Maria Due�as
	 */
	public void clickGuardarBtn() {
		me.scrollToElement(xmlGuardarBtn);
		u.highLight(xmlGuardarBtn);
		testCase.addScreenShotCurrentStep(u, "GuardarBtn");
		u.unhighLight(xmlGuardarBtn);
		u.click(xmlGuardarBtn);
		testCase.addScreenShotCurrentStep(u, "clickGuardarBtn");
	}
	
	/**
	 * Metodo para dar click en el boton Confirmar de la ventana Tipos de POPs - Nuevo
	 * @author Maria Due�as
	 */
	public void clickConfirmar() {
		u.hardWait(3);
		me.scrollToElement(xmlConfirmar);
		u.highLight(xmlConfirmar);
		testCase.addScreenShotCurrentStep(u, "Confirmar");
		u.unhighLight(xmlConfirmar);
		u.click(xmlConfirmar);
		testCase.addScreenShotCurrentStep(u, "clickConfirmar");
	}
	
	/**
	 * Metodo para dar click en el boton Cancelar de la ventana Tipos de POPs - Nuevo
	 * @author Maria Due�as
	 */
	public void clickCancelar() {
		me.scrollToElement(xmlCancelar);
		u.highLight(xmlCancelar);
		testCase.addScreenShotCurrentStep(u, "Cancelar");
		u.unhighLight(xmlCancelar);
		u.click(xmlCancelar);
		testCase.addScreenShotCurrentStep(u, "clickCancelar");
	}
	
	/**
	 * Metodo para buscar Pop registrado en ventana Tipo de Pops
	 * @param nombre del pop
	 * @return Retorna true si encuentra el pop o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean buscarPop(String nombre) {
		Boolean exist = false;
		
		String pop = "";
		
		u.hardWait(5);
		me.scrollToElement(xmlTable);
		List <WebElement> tablaTr = u.getDriver().findElements(xmlTableTr);
		Integer row_count = tablaTr.size();
		
		//System.out.print(row_count + "\n");
		
		if (row_count >= 1) {
			for (int i=1; i <= row_count; i++) {
				pop = String.format(nombrePop, Integer.toString(i)); 
				String f = u.getText(By.xpath(pop)).trim();
				//System.out.print("-" + f + "-\n");
				if (nombre.equals(f)) {
					exist = true;
					pop = String.format(tableTr, Integer.toString(i)); 
					u.highLight(By.xpath(pop));
					testCase.addScreenShotCurrentStep(u, "buscarPop");
					break;
				}
			}
		}
		testCase.addScreenShotCurrentStep(u, "buscarPop");
		return exist;
	}
	
	/**
	 * Metodo para buscar Pop registrado en ventana Tipo de Pops y dar click en Editar
	 * @param nombre del pop
	 * @return Retorna true si encuentra el pop o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean clickEditarPop(String nombre) {
		Boolean exist = false;
		
		String pop = "";
		
		u.hardWait(5);
		me.scrollToElement(xmlTable);
		List <WebElement> tablaTr = u.getDriver().findElements(xmlTableTr);
		Integer row_count = tablaTr.size();
		
		//System.out.print(row_count + "\n");
		
		if (row_count >= 1) {
			for (int i=1; i <= row_count; i++) {
				pop = String.format(nombrePop, Integer.toString(i)); 
				String f = u.getText(By.xpath(pop)).trim();
				//System.out.print("-" + f + "-\n");
				if (nombre.equals(f)) {
					exist = true;
					pop = String.format(tableTr, Integer.toString(i)); 
					u.highLight(By.xpath(pop));
					testCase.addScreenShotCurrentStep(u, "buscarPop");
					u.unhighLight(By.xpath(pop));
					
					u.highLight(By.xpath(String.format(editarTr, Integer.toString(i))));
					testCase.addScreenShotCurrentStep(u, "editarPop");
					u.unhighLight(By.xpath(String.format(editarTr, Integer.toString(i))));
					u.click(By.xpath(String.format(editarTr, Integer.toString(i))));
					testCase.addScreenShotCurrentStep(u, "clickEditarPop");
					break;
				}
			}
		}
		testCase.addScreenShotCurrentStep(u, "buscarPop");
		return exist;
	}
	
	/**
	 * Metodo para editar el nombre del pop
	 * @param nombre
	 * @return Retorna true si se edita correctamente o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean editarTipoPop(String nombre) {
		Boolean correct = false;
		
		try {
			me.scrollToElement(xmlTipoPop);
			u.highLight(xmlTipoPop);
			testCase.addScreenShotCurrentStep(u, "NombreTipoPop");
			u.clear(xmlTipoPop);
			testCase.addScreenShotCurrentStep(u, "LimpiarTipoPop");
			u.sendKeys(xmlTipoPop, nombre);
			testCase.addScreenShotCurrentStep(u, "EditarTipoPop");
			u.unhighLight(xmlTipoPop);
			
			testCase.addTextEvidenceCurrentStep("Se edita correctamente el nombre del pop");
			
			correct = true;
			
		}catch(Exception e){
			
		}
		
		return correct;
	}
	
	/**
	 * Metodo para validar que se muestren los componentes d la pantalla Editar Tipo de Pop
	 * @return Retorna true si encuentra todos los componentes o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean pantallaTipoPopEditar() {
		Boolean exist = false;
		
		try {
			u.highLight(xmlTipoPop);
			u.highLight(xmlPromociones);
			u.highLight(xmlKit);
			u.highLight(xmlSegmento);
			u.highLight(xmlPrecio);
			u.highLight(xmlNegocio);
			u.highLight(xmlContenido);
			u.highLight(xmlPromRadio);
			u.highLight(xmlKitRadio);
			u.highLight(xmlSegmRadio);
			u.highLight(xmlPrecRadio);
			u.highLight(xmlNegRadio);
			u.highLight(xmlContRadio);
			testCase.addScreenShotCurrentStep(u, "TipoPop");
			
			me.scrollToElement(xmlCancelarBtn);
			u.highLight(xmlCancelarBtn);
			u.highLight(xmlGuardarBtn);
			
			testCase.addScreenShotCurrentStep(u, "TipoPop2");
			
			u.unhighLight(xmlTipoPop);
			u.unhighLight(xmlPromociones);
			u.unhighLight(xmlKit);
			u.unhighLight(xmlSegmento);
			u.unhighLight(xmlPrecio);
			u.unhighLight(xmlNegocio);
			u.unhighLight(xmlContenido);
			u.unhighLight(xmlPromRadio);
			u.unhighLight(xmlKitRadio);
			u.unhighLight(xmlSegmRadio);
			u.unhighLight(xmlPrecRadio);
			u.unhighLight(xmlNegRadio);
			u.unhighLight(xmlContRadio);
			u.unhighLight(xmlCancelarBtn);
			u.unhighLight(xmlGuardarBtn);
			
			testCase.addTextEvidenceCurrentStep("Se muestran correctamente los componentes");
			
			exist = true;
			
		}catch(Exception e) {
			testCase.addScreenShotCurrentStep(u, "Error");
		}
		
		return exist;
	}
	
	/**
	 * Metodo para buscar Pop registrado en ventana Tipo de Pops y seleccionarlo
	 * @param nombre del pop
	 * @return Retorna true si encuentra el pop o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean clickSeleccionarPop(String nombre) {
		Boolean exist = false;
		
		String pop = "";
		
		u.hardWait(5);
		me.scrollToElement(xmlTable);
		List <WebElement> tablaTr = u.getDriver().findElements(xmlTableTr);
		Integer row_count = tablaTr.size();
		
		//System.out.print(row_count + "\n");
		
		if (row_count >= 1) {
			for (int i=1; i <= row_count; i++) {
				pop = String.format(nombrePop, Integer.toString(i)); 
				String f = u.getText(By.xpath(pop)).trim();
				//System.out.print("-" + f + "-\n");
				if (nombre.equals(f)) {
					exist = true;
					pop = String.format(tableTr, Integer.toString(i)); 
					u.highLight(By.xpath(pop));
					testCase.addScreenShotCurrentStep(u, "buscarPop");
					u.unhighLight(By.xpath(pop));
					
					u.highLight(By.xpath(String.format(seleccionarTr, Integer.toString(i))));
					testCase.addScreenShotCurrentStep(u, "seleccionarPop");
					u.unhighLight(By.xpath(String.format(seleccionarTr, Integer.toString(i))));
					u.click(By.xpath(String.format(seleccionarTr, Integer.toString(i))));
					testCase.addScreenShotCurrentStep(u, "clickSeleccionarPop");
					break;
				}
			}
		}
		testCase.addScreenShotCurrentStep(u, "buscarPop");
		return exist;
	}
}

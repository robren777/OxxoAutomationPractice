package WEB.simoti_Colombia.om;

import org.openqa.selenium.By;
import org.testng.Assert;
import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class ConsultarEditarProveedoresEDI_EditarPlazasBuzones_SimotiColombia {
	
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	MenuPrincipal_SimotiColombia mp;
	String Replace = "";
	
	/*Localizadores Header*/
	By proveedor = By.xpath("");
	
	/*Localizadores Agregar Plaza*/	
	By plazaSelect = By.xpath("");
	By plazaOptions = By.xpath("");
	By agregarPlazaBtn = By.xpath("");
	
	/*Localizadores Agregar Cedis*/	
	By cedisSelect = By.xpath("");
	By cedisOptions = By.xpath("");
	By agregarCedisBtn = By.xpath("");	
	
	/*Localizadores Agregar Buzon*/	
	By agregarBuzonTabla = By.xpath("");
	By agregarBuzonBtn  = By.xpath("");
	String abHeaders = "";
	String checkBuzon = "";
	
	/*Localizadores Lista Buzones*/		
	By listaBuzonesTabla = By.xpath("");
	String lbHeaders = "";
	String editarBuzonStr = "";
	String eliminarBuzonStr = "";
	
	/*Localizadores Lista Cedis/Plazas*/	
	By listaPlazasCedisTabla = By.xpath("");
	String lpcHeaders = "";
	
	
	/*Localizadores Mensajes*/
	By mensajeActualizacion = By.xpath("");
	
	/*
	 * Constructor
	 ******************************************************************************************/
	
	/** Constructor de la clase
	 * @author David Valdez
	 * @param u, TestCase
	 * @return N/A
	 */
	public ConsultarEditarProveedoresEDI_EditarPlazasBuzones_SimotiColombia(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
		this.mp = new MenuPrincipal_SimotiColombia(u, testCase);	
	}

	
	/**
	 * Metodo para validar los encabezados de la tabla Agregar Buzon
	 * @return Retorna true o false si se encuentran los encabezados
	 * @author Andrea Contreras
	 */
	public Boolean validarEncabezadosTablaAgregarBuzon() {
		Boolean correct = false;
		
		u.scrollToElement(agregarBuzonTabla);
			
		String buzon = u.getText(By.xpath(String.format(abHeaders, "1"))).trim();
		String tipo = u.getText(By.xpath(String.format(abHeaders, "2"))).trim();
		String calificador = u.getText(By.xpath(String.format(abHeaders, "3"))).trim();
		
		if(buzon.equals("Buz�n") && tipo.equals("Tipo") && calificador.equals("Calificador")) 
		{
			correct = true;
			for(int i = 1;i<=9;i++) {
				u.scrollToElement(By.xpath(String.format(abHeaders,i)));
				u.highLight(By.xpath(String.format(abHeaders,i)));
			}
			testCase.addScreenShotCurrentStep(u, "encabezados1");
			for(int i = 1;i<=9;i++) {
				u.unhighLight(By.xpath(String.format(abHeaders,i)));
			}
		}
		
		return correct;

	}
	
	/**
	 * Metodo para validar los encabezados de la tabla Lista Buzones
	 * @return Retorna true o false si se encuentran los encabezados
	 * @author Andrea Contreras
	 */
	public Boolean validarEncabezadosTablaListaBuzones() {
		
		Boolean correct = false;
		
		u.scrollToElement(listaBuzonesTabla);

		String buzon = u.getText(By.xpath(String.format(lbHeaders, "1"))).trim();
		String calificador = u.getText(By.xpath(String.format(lbHeaders, "2"))).trim();
		String tipo = u.getText(By.xpath(String.format(lbHeaders, "3"))).trim();
		String editar = u.getText(By.xpath(String.format(lbHeaders, "4"))).trim();
		String eliminar = u.getText(By.xpath(String.format(lbHeaders, "5"))).trim();
		
		
		if(buzon.equals("Buz�n") && tipo.equals("Tipo") && calificador.equals("Calificador")
				&& calificador.equals("Editar") && calificador.equals("Eliminar")) 
		{
			correct = true;
			for(int i = 1;i<=9;i++) {
				u.scrollToElement(By.xpath(String.format(lbHeaders,i)));
				u.highLight(By.xpath(String.format(lbHeaders,i)));
			}
			testCase.addScreenShotCurrentStep(u, "encabezados2");
			for(int i = 1;i<=9;i++) {
				u.unhighLight(By.xpath(String.format(lbHeaders,i)));
			}
		}
		
		return correct;

	}

	/**
	 * Metodo para validar los encabezados de la tabla Plazas o Cedis Activos
	 * @return Retorna true o false si se encuentran los encabezados
	 * @author Andrea Contreras
	 */
	public Boolean validarEncabezadosTablaPlazasCedisActivos() {
		
		Boolean correct = false;
		
		u.scrollToElement(listaPlazasCedisTabla);

		String pc = u.getText(By.xpath(String.format(lpcHeaders, "1"))).trim();
		String desc = u.getText(By.xpath(String.format(lpcHeaders, "2"))).trim();
		String eliminar = u.getText(By.xpath(String.format(lpcHeaders, "3"))).trim();


		if(pc.equals("Plaza/Cedis") && desc.equals("Descripci�n") && eliminar.equals("Eliminar")) 
		{
			correct = true;
			for(int i = 1;i<=9;i++) {
				u.scrollToElement(By.xpath(String.format(lpcHeaders,i)));
				u.highLight(By.xpath(String.format(lpcHeaders,i)));
			}
			testCase.addScreenShotCurrentStep(u, "encabezados3");
			for(int i = 1;i<=9;i++) {
				u.unhighLight(By.xpath(String.format(lpcHeaders,i)));
			}
		}
		
		return correct;

	}
	
	/**
	 * Metodo para validar que se muestre los datos de la pantalla 'Editar Plaza y Buzones'
	 * @return Retorna true o false si se muestran los datos
	 * @author Andrea Contreras
	 */
	public Boolean validarPantalla() {
		Boolean validacion = false, v1, v2, v3, v4;
		
		v1 = me.existElement(proveedor);
		v2 = validarEncabezadosTablaAgregarBuzon();
		v3 = validarEncabezadosTablaListaBuzones();
		v4 = validarEncabezadosTablaPlazasCedisActivos();
		
		if(v1 == true && v2 == true && v3 == true && v4 == true) {
			validacion = true;	
			u.scrollToElement(proveedor);
			u.highLight(proveedor);
			testCase.addScreenShotCurrentStep(u, "validarDatos");
			u.unhighLight(proveedor);
		}
				
		return validacion;

	}
	
	/**
	 * Metodo para dar click en el boton Agregar
	 * @author Andrea Contreras
	 */
	public void clickAgregarBtn() {
		me.clickBoton(agregarBuzonBtn);
		mp.waitLoading();
		testCase.addScreenShotCurrentStep(u, "clickAgregar");
	}
	
	/**
	 * Metodo para seleccionar una opcion (Editar/Eliminar) de un buzon de la tabla 'Lista Buzones'
	 * @author Andrea Contreras
	 * @param
	 * @return Retorna true o false si se selecciono la opcion del buzon
	 */
	public boolean seleccionarOpcionDeUnBuzon(String buzon, String opcion) {
		Boolean click = false;
		By xpath = null;
					
		switch(opcion.toUpperCase()) {
	       case "EDITAR":	
	    	   xpath = By.xpath(String.format(editarBuzonStr, buzon));    	   
		   break;
		   
	       case "ELIMINAR":
	    	   xpath = By.xpath(String.format(eliminarBuzonStr, buzon));
			   break;
		}
		
	    u.hardWait(2);
	    if(me.existElement(xpath)) {
			 u.highLight(xpath);
			 testCase.addScreenShotCurrentStep(u, "checkBuzon");
			 u.click(xpath);
			 mp.waitLoading();
			 testCase.addScreenShotCurrentStep(u, "checkBuzon");
			 click = true;
		}
	    	
		return click;
	}
	
	/**
	 * Metodo para seleccionar el buzon de la tabla 'Agregar Buzon'
	 * @author Andrea Contreras
	 * @return Retorna true o false si se selecciona el buzon
	 */
	public boolean seleccionarBuzon(String buzon) {
		Boolean click = false;
		By xpath = By.xpath(String.format(checkBuzon, buzon));
				
	    u.hardWait(2);
	    if(me.existElement(xpath)) {
			 u.highLight(xpath);
			 u.click(xpath);
			 testCase.addScreenShotCurrentStep(u, "checkBuzon");			 
			 u.unhighLight(xpath);
			 click = true;
		}
	    	
		return click;
	}
	
	
	/**
	 * Metodo para validar que se muestre el mensaje de actualizacion
	 * @author Andrea Contreras
	 * @return Retorna true o false si se muestra el mensaje
	 */
	public boolean validarMensaje() {
		Boolean validacion = false;
		
		if(me.existElement(mensajeActualizacion)) {
			u.highLight(mensajeActualizacion);	
			testCase.addScreenShotCurrentStep(u, "validarMsj");
			u.unhighLight(mensajeActualizacion);	
			validacion = true;
		}
				
		return validacion;
	}
 
	
	/** Metodo para agregar una plaza o un cedis
	 * @author Andrea Contreras
	 * @param 
	 */
	public void agregarPlazaCedis(String valor, String opcion) {
 
		boolean click = true;
				
		switch(opcion.toUpperCase()) {
	       case "PLAZA":	
	    	   click = me.selectOption(plazaSelect,plazaOptions,valor);
	    	   mp.waitLoading();
	            if(click==false) {
					Assert.fail("No se pudo seleccionar la Plaza: "+valor);
				}
	            me.clickBoton(agregarPlazaBtn);
	            mp.waitLoading();
		   break;
		   
	       case "CEDIS":
	    	   click = me.selectOption(cedisSelect,cedisOptions,valor);
	    	   mp.waitLoading();
	            if(click==false) {
					Assert.fail("No se pudo seleccionar el Cedis: "+valor);
				}	 
	           me.clickBoton(agregarCedisBtn);
	           mp.waitLoading();
			   break;
		}
		
		u.hardWait(2);
	
	}
 
	
	
}

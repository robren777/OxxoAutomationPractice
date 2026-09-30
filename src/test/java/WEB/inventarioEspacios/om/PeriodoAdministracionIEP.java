package WEB.inventarioEspacios.om;

import org.openqa.selenium.By;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class PeriodoAdministracionIEP {
	SeleniumUtil u;
	TestCase testCase;
	
	/**
	 * Localizadores pagina principal periodos
	 ******************************************************************************************/
	
	By xmlPeriodoActual = By.xpath("//*[@id=\"content-nav\"]/app-period/div/div/div[1]/div/b/mat-label");
	By xmlEstatus = By.xpath("//*[@id=\"content-nav\"]/app-period/div/div/div[2]/div/b/mat-label");
	By xmlNumPeriodo = By.xpath("/html/body/app-root/mat-sidenav-container/mat-sidenav-content/app-period/div/form/div[1]/div/div/mat-form-field/div/div[1]/div/input");
	By xmlEditarBtn = By.xpath("//span[contains(text(),'Editar')]");
	By xmlCancelarBtn = By.xpath("//span[contains(text(),'Cancelar')]");
	By xmlCerrarBtn = By.xpath("//span[contains(text(),'Cerrar')]");
	By xmlAbrirBtn = By.xpath("//span[contains(text(),'Abrir')]");
	
	//Ventanas emergentes

	By xmlConfirmarBtn = By.xpath("//span[contains(text(),'Confirmar')]");
	By xmlInforme = By.xpath("//*[@id=\"mat-dialog-1\"]/app-dialog-confirm/div[1]/div");
	By xmlCerrarBtn2 = By.xpath("//*[@id=\"mat-dialog-1\"]/app-dialog-confirm/div[2]/div/button");
	
	/**
	 * Constructor
	 ******************************************************************************************/
	
	public PeriodoAdministracionIEP(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}
	
	/*
	 * METODOS - Apartado Pedidos
	 ******************************************************************************************/
	
	/**
	 * M�todo para obtener el periodo actual
	 * @author Mar�a Due�as
	 * @return Regresa el periodo actual
	 */
	public String getTextPeriodoActual() {
	
		u.hardWait(3);
		String pa = u.getText(xmlPeriodoActual);
		u.highLight(xmlPeriodoActual);
		return pa;
		
	}
	
	/**
	 * M�todo para obtener el estatus del periodo actual
	 * @author Mar�a Due�as
	 * @return Regresa el estatus del periodo actual
	 */
	public String getTextEstatus() {
		
		String pa = u.getText(xmlEstatus);
		return pa;
		
	}
	
	/**
	 * M�todo para ingresar periodo en input
	 * @param dato Recibe como parametro el periodo
	 * @author Mar�a Due�as
	 */
	public void typingNumPeriodo(String dato) {
	       
    	u.sendKeys(xmlNumPeriodo, dato);
    	u.highLight(xmlNumPeriodo);
    	testCase.addScreenShotCurrentStep(u, "typingNumPeriodo"); 	
    	u.unhighLight(xmlNumPeriodo);
    }
    
	/**
	 * M�todo para dar click en boton editar (Guarda los cambios realizados al nombre del periodo)
	 * @author Mar�a Due�as
	 */
    public void clickEditarBtn() {
       
    	u.highLight(xmlEditarBtn);
    	u.click(xmlEditarBtn);
    	testCase.addScreenShotCurrentStep(u, "clickEditarBtn"); 	
    	u.unhighLight(xmlEditarBtn);	
    }
    
    /**
	 * M�todo para dar click en boton cancelar
	 * @author Mar�a Due�as
	 */
    public void clickCancelarBtn() {
        
    	u.highLight(xmlCancelarBtn);
    	u.click(xmlCancelarBtn);
    	testCase.addScreenShotCurrentStep(u, "clickCancelarBtn"); 	
    	u.unhighLight(xmlCancelarBtn);	
    }
    
    /**
	 * M�todo para dar click en boton cerrar (Cierra Perido abierto)
	 * @author Mar�a Due�as
	 */
    public void clickCerrarBtn() {
        
    	u.highLight(xmlCerrarBtn);
    	u.click(xmlCerrarBtn);
    	testCase.addScreenShotCurrentStep(u, "clickCerrarBtn"); 	
    	u.unhighLight(xmlCerrarBtn);
    	
    }
    
    /**
	 * M�todo para dar click en boton Confirmar 
	 * @author Mar�a Due�as
	 */
    public void clickConfirmarBtn() {
        
    	u.highLight(xmlConfirmarBtn);
    	testCase.addScreenShotCurrentStep(u, "clickConfirmarBtn"); 	
    	u.click(xmlConfirmarBtn);
    	
    }
    
    /**
	 * M�todo para dar click en boton abrir (Nuevo periodo)
	 * @author Mar�a Due�as
	 */
    public void clickAbrirBtn() {
    	
    	u.highLight(xmlAbrirBtn);
    	u.click(xmlAbrirBtn);
    	testCase.addScreenShotCurrentStep(u, "clickAbrirBtn"); 	
    	u.unhighLight(xmlAbrirBtn);
    	
    }
    
    /**
	 * M�todo para remarcar el estatus del periodo actual
	 * @author Mar�a Due�as
	 */
    public void periodoEstatus() {
    	
    	u.highLight(xmlPeriodoActual);
    	u.highLight(xmlEstatus);
    	testCase.addScreenShotCurrentStep(u, "periodoEstatus");
    	u.unhighLight(xmlPeriodoActual);
    	u.unhighLight(xmlEstatus);
    	
    }
    
    /**
     * M�todo para verificar el mensaje de confirmacion para editar, abrir  y cerrar periodos
     * @param tipo
     * @return Retorna true o false si el mensaje es correcto
     * @author Mar�a Due�as
     */
    public Boolean mensajeConfirmacion(String tipo) {
    	
    	Boolean mensaje = false;
    	String informe = u.getText(xmlInforme);
    	u.highLight(xmlInforme);
    	
    	switch(tipo) {
	    	case "Abrir": if(informe.contains("El periodo ha sido abierto exitosamente")) {
	    						mensaje = true;
	    				  }
    					  break;
	    	case "Cerrar": if(informe.contains("El periodo ha sido cerrado exitosamente")) {
								mensaje = true;
						   }
						   break;
	    	case "Editar": if(informe.contains("El periodo ha sido editado exitosamente")) {
								mensaje = true;
	    				   }
	    				   break;
    	}
    	
    	
    	testCase.addScreenShotCurrentStep(u, "mensajeConfirmacion");
    	u.unhighLight(xmlInforme);
    	
    	return mensaje;
    }
    
    /**
	 * M�todo para dar click en boton cerrar en pesta�a de mensaje de confirmacion
	 * @author Mar�a Due�as
	 */
    public void clickCerrarBtn2() {
    	u.highLight(xmlCerrarBtn2);
    	testCase.addScreenShotCurrentStep(u, "clickCerrarBtn2");
    	u.unhighLight(xmlCerrarBtn2);
    	u.click(xmlCerrarBtn2);
    	
    }
    
    /**
     * M�todo para crear el nombre de un nuevo periodo
     * @param periodo Recibe como parametro el periodo anterior
     * @return Retorna el nuevo periodo
     * @author Mar�a Due�as
     */
    public String nuevoPeriodo(String periodo) {
		
		String nvoPeriodo = "";
		
		if(periodo.contains("B ")) {
			String [] cadena = periodo.split(" ");
	    	String[] antiguoPeriodo = cadena[1].split("P");
	    	Integer nv = Integer.parseInt(antiguoPeriodo[1]);
	    	nv += 1;
	    	nvoPeriodo = "B P" + Integer.toString(nv) + " " + cadena[2];
		} else {
			String [] cadena = periodo.split(" ");
	    	String[] antiguoPeriodo = cadena[0].split("P");
	    	Integer nv = Integer.parseInt(antiguoPeriodo[1]);
	    	nv += 1;
	    	nvoPeriodo = "P" + Integer.toString(nv) + " " + cadena[1];
		}
    	
    	return nvoPeriodo;
    	
    }
    

}

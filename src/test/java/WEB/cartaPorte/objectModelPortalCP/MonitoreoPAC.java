package WEB.cartaPorte.objectModelPortalCP;

import org.openqa.selenium.By;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class MonitoreoPAC {
	SeleniumUtil u;
	TestCase testCase;
	
	//Boton 
	By checkKonesh=By.xpath("//div[@class='col8 form-group']/mat-radio-group/mat-radio-button[@id='mat-radio-10']");
	By checkDiverza=By.xpath("//div[@class='col8 form-group']/mat-radio-group/mat-radio-button[@id='mat-radio-11']");
	By botonGuardar=By.xpath("//div[@class='col12 right']/button");	
	public MonitoreoPAC(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}
	
	/**
	 * Metodo para seleccionar elementos de la pagina de monitoreo -- PAC
	
	 * @param nombreCampo-----------nombre del campo a seleccionardos
	 * @param captura---------------true para sacar una captura de pantalla en la evidencia, false cuando no es necesario 
	 *  @param adicional---------------Información adicional en caso de ser requerida
	 * 
	 * @return regresa true si la seleccion fue exitosa
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean seleccionarElemento(String nombreCampo, boolean captura, String adicional) {
		By elementoSeleccion=null;
		boolean seleccion=false;
		try {
			switch(nombreCampo) {
				case "botonPAC":
					if(adicional.compareToIgnoreCase("Konesh")==0) {
						elementoSeleccion=checkKonesh;
					}else {
						elementoSeleccion=checkDiverza;
					}
					seleccion=true;
				break;	
				case "botonGuardar":
					elementoSeleccion=botonGuardar;
					seleccion=true;
				break;	
			}
			u.highLight(elementoSeleccion);
			if(captura) {
				testCase.addScreenShotCurrentStep("Seleccion"+nombreCampo);
			}
			u.hardWait(2);
			u.unhighLight(elementoSeleccion);
			u.getDriver().findElement(elementoSeleccion).click();		
			if(elementoSeleccion!=null) {
				seleccion=true;
			}
			return seleccion;
		} catch (Exception e) {
			System.out.println("Error" + e);
			return false;
		}
	}
	
}

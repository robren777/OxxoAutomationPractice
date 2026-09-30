package Mobile.Mep.om;

import org.openqa.selenium.JavascriptExecutor;

import com.google.common.collect.ImmutableMap;

import utils.AppiumUtil;

public class utils {
	
	/**
	* Realiza un gesto de scroll hacia arriba en una vista móvil utilizando Appium.	*
	* @author Ivan
	* @param au Objeto de utilidad `AppiumUtil` que contiene el driver de Appium necesario para ejecutar comandos sobre la aplicacion móvil.
	* @date 07/Agosto/2024
	*/
    public void scrollUp(AppiumUtil au)
    {
    	boolean canScrollMore = (Boolean) ((JavascriptExecutor) au.driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
    		    "left", 100, "top", 500, "width", 200, "height", 200, // Coordenadas
    		    "direction", "up", // Hacia donde ira 
    		    "percent", 4.0 // Veces que dará hacia la direction que ingresamos
    	));
    }
}

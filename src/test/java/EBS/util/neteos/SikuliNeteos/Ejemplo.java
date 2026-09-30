package EBS.util.neteos.SikuliNeteos;

import org.sikuli.script.Region;
import org.sikuli.script.Screen;

import utils.SikuliUtil;

public class Ejemplo {
	
	public static void main(String[] args) throws Exception {
		
		SikuliUtil su = new SikuliUtil();

		///////////////////////////////////
		Screen s = new Screen(0);//Líneas añadidas por segundo monitor
		su.setScreen(s); //Líneas añadidas por segundo monitor
		///////////////////////////////////

		Region botonBuscar = su.getRegion("NETEOS/er.png");

		botonBuscar.click();

	}

}

package IMA.tgoRpd;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
public class ATC_FT_30_AccesoAlResumenOperativo extends BaseExecution {

	private SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_30_AccesoAlResumenOperativo_test(HashMap<String, String> data) throws Exception {

		u = new SeleniumUtil(new ChromeTest(), true);

		IMATools ima = new IMATools(u, testCase);

		u.hardWait(2);

		addStep("Abrir navegador y loguearse en la pagina de MicroStrategy");
		ima.Login();
		u.hardWait(1);
		
		addStep("Ingresar a la ruta informacion comercial");
		u.highLight(By.xpath("/html/body/div[4]/table/tbody/tr[2]/td[2]/div[2]/div[3]/table/tbody/tr[4]/td[1]/div"));
		testCase.addScreenShotCurrentStep(u, "Ingresar a la ruta informacion comercial");
		u.click(By.xpath("/html/body/div[4]/table/tbody/tr[2]/td[2]/div[2]/div[3]/table/tbody/tr[4]/td[1]/div/table/tbody/tr/td[2]/a"));
		
		addStep("Informes Compartidos");
		u.highLight(By.xpath("/html/body/div[4]/table/tbody/tr[2]/td[2]/div[2]/table/tbody/tr[2]/td[1]/table/tbody/tr[2]/td/table[1]/tbody/tr[2]/td[1]/a"));
		testCase.addScreenShotCurrentStep(u, "Informes Compartidos");
		u.click(By.xpath("/html/body/div[4]/table/tbody/tr[2]/td[2]/div[2]/table/tbody/tr[2]/td[1]/table/tbody/tr[2]/td/table[1]/tbody/tr[2]/td[1]/a"));
		
		addStep("Operaciones");
		u.highLight(By.xpath("/html/body/div[4]/table/tbody/tr[2]/td[2]/div[2]/div[1]/div/table/tbody/tr[8]/td[1]/div"));
		testCase.addScreenShotCurrentStep(u, "Operaciones");
		u.click(By.xpath("/html/body/div[4]/table/tbody/tr[2]/td[2]/div[2]/div[1]/div/table/tbody/tr[8]/td[1]/div"));
		
		addStep("Resumen Operativo");
		u.highLight(By.xpath("/html/body/div[4]/table/tbody/tr[2]/td[2]/div[2]/div[1]/div/table/tbody/tr/td[2]/div"));
		testCase.addScreenShotCurrentStep(u, "Resumen Operativo");
		u.click(By.xpath("/html/body/div[4]/table/tbody/tr[2]/td[2]/div[2]/div[1]/div/table/tbody/tr/td[2]/div"));
		
		addStep("Reporte Mensual");
		u.highLight(By.xpath("/html/body/div[4]/table/tbody/tr[2]/td[2]/div[2]/div[1]/div/table/tbody/tr[1]/td[1]/div"));
		testCase.addScreenShotCurrentStep(u, "Reporte Mensual");
		u.click(By.xpath("/html/body/div[4]/table/tbody/tr[2]/td[2]/div[2]/div[1]/div/table/tbody/tr[1]/td[1]/div"));
		
		addStep("Resumen Operativo Nacional Mes");
		u.highLight(By.xpath("/html/body/div[4]/table/tbody/tr[2]/td[2]/div[2]/div[1]/div/table/tbody/tr[1]/td[1]/div"));
		testCase.addScreenShotCurrentStep(u, "Resumen Operativo Nacional Mes");
		u.click(By.xpath("/html/body/div[4]/table/tbody/tr[2]/td[2]/div[2]/div[1]/div/table/tbody/tr[1]/td[1]/div"));
		u.hardWait(1);
		
		addStep("Se seleccionan todas");
		u.highLight(By.xpath("//*[@id=\"id_mstr105ListContainer\"]/div[10]"));
		testCase.addScreenShotCurrentStep(u, "Se seleccionan todas");
		u.click(By.xpath("//*[@id=\"id_mstr105ListContainer\"]/div[10]"));
		u.hardWait(1);
		u.highLight(By.xpath("//*[@id=\"id_mstr107\"]"));
		u.click(By.xpath("//*[@id=\"id_mstr107\"]"));
		
		addStep("Se baja a las fechas");
		u.highLight(By.xpath("//*[@id=\"id_mstr75\"]/table/tbody/tr[9]/td[2]"));
		testCase.addScreenShotCurrentStep(u, "Se baja a las fechas");
		u.click(By.xpath("	//*[@id=\"id_mstr75\"]/table/tbody/tr[9]/td[2]"));
		u.hardWait(1);
	
		addStep("Se selecciona un año");
		u.hardWait(2);
		u.click(By.cssSelector("[title='2023:2023']"));
		testCase.addScreenShotCurrentStep(u, "Se seleccionan todas");
		//u.highLight(By.xpath("//*[@id=\"id_mstr305\"]/div[2]/div/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div/div[2]/div[3]/div[2]/div[1]/img"));
		u.hardWait(1);
		
		addStep("Se agrega");
		u.click(By.cssSelector("[title='Nueva condición']"));
		u.hardWait(1);
		
		addStep("Se ejecuta informe");
		u.highLight(By.xpath("//*[@id=\"id_mstr237\"]"));
		u.click(By.xpath("//*[@id=\"id_mstr237\"]"));
		u.hardWait(25);
		addStep("se visualiza información del % Apego RPD y % Acumulado del Mes ");
		u.highLight(By.cssSelector("[title='Indicador: Control Porcentaje Apego RPD']"));
		u.highLight(By.cssSelector("[title='Indicador: Control Porcentaje Apego RPD Acumulado']"));
		testCase.addScreenShotCurrentStep(u, "Se seleccionan todas");
		u.close();
	}
 
	@Override

	public void beforeTest() {

		// TODO Auto-generated method stub

	}

	@AfterTest

	public void afterTest() {

		u.getDriver().quit();

	}
 
	@Override

	public String setPrerequisites() {

		// TODO Auto-generated method stub

		return "Prerequisitos";

	}
 
	@Override

	public String setTestDescription() {

		// TODO Auto-generated method stub

		return "Ingresar a la plataforma MicroStrategy para visualizar dentro del Resumen Operativo Nacional el nuevo indicador % Apego RPD y %Acumulado del mes";

	}
 
	@Override

	public String setTestDesigner() {

		// TODO Auto-generated method stub

		return "Ricardo Gil Espinosa Reyes";

	}
 
	@Override

	public String setTestFullName() {

		// TODO Auto-generated method stub

		return "ATC_FT_30_AccesoAlResumenOperativo";

	}
 
	@Override

	public String setTestInstanceID() {

		// TODO Auto-generated method stub

		return null;

	}
 
}

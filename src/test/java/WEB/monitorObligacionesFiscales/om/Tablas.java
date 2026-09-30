package WEB.monitorObligacionesFiscales.om;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Tablas{

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	
	
	/*
	 * Localizadores Menu Monitor de Obligaciones Fiscales
	 ********************************************************************************************/
	
	By OpcionTablas = By.xpath("//*[text()='Tablas']");
	
	//Opciones menu
	By xmlTablasTarifas = By.xpath("//*[text()='Tarifas']");
	//Fin opciones menu
	
	//Dentro opcion Tablas Tarifas
	By xmlTarifasTipoTabla = By.xpath("//*[@id='form1:tipoTarifaId']");
	By xmlTarifasTipoTablaOption = By.xpath("//*[@id='form1:tipoTarifaId_panel']/ul/li");
	By xmlTarifasAnio = By.xpath("//*[@id='form1:anioId']");
	By xmlTarifasAnioOption = By.xpath("//*[@id='form1:anioId_panel']/ul/li");
	By xmlTarifasMes = By.xpath("//*[@id='form1:mesId']");
	By xmlTarifasMesOption = By.xpath("//*[@id='form1:mesId_panel']/ul/li");
	By xmlTarifasBuscar = By.xpath("//*[text()='Buscar']");
	By xmlDivTable = By.xpath("//*[@id='form1:tablaTarifas']");
	String xmltableHeadTarifas = "//*[@id='form1:tablaTarifas']/table/thead/tr/th[%s]";
	//Fin dentro opcion tablas tarifas
	
	/*
	 * Constructor
	 ********************************************************************************************/
	public Tablas(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}
	
	/*
	 * Metodos Menu Integridad de datos
	 ********************************************************************************************/
	
	/**
	 * Metodo para dar click en opcion tablas
	 * @author Jorge Ruiz
	 */
	public void clickTablas(String opc) 
	{
		u.click(OpcionTablas);
		u.hardWait(1);
		List<WebElement> options = u.getDriver().findElements(xmlTarifasTipoTabla);
		for(WebElement opt:options)
		{
			String text = opt.getText();
			if(text.equals(xmlTarifasTipoTabla))
				opt.click();
		}
	}
	
	/**
	 * Metodo para dar click en opcion Tarifas de Tablas
	 * @author Jorge Ruiz
	 */
	public void clickOpcionTarifas() 
	{	
		u.highLight(xmlTablasTarifas);
		testCase.addScreenShotCurrentStep(u, "Tarifas");
		u.click(xmlTablasTarifas);
	}
	
	/**
	 * Metodo para seleccionar el tipo de tabla de Tablas-Tarifas
	 * @param tipo ISR/Subsidio/ISR RESICO
	 * @author Jorge Ruiz
	 */
	/*public void selectTipoTabla(String tipo) 
	{
		u.click(xmlTarifasTipoTabla);
		u.sendKeys(xmlTarifasTipoTabla, Keys.DOWN);
		u.hardWait(2);
		By xmls = By.xpath(String.format(xmlTarifasSelectOption,tipo));
		System.out.println(String.format(xmlTarifasSelectOption,tipo));
		u.click(By.xpath("//select[@id='form1:tipoTarifaId_input']/option[2]"));
		u.hardWait(3);
	}*/
	
	/**
	 * Metodo para seleccionar el A�o de Tablas-Tarifas
	 * @param tipo A�o o Mes
	 * @author Jorge Ruiz
	 */
	public void selectAnio(String tipo) 
	{
		u.click(xmlTarifasAnio);
		u.hardWait(1);
		List<WebElement> options = u.getDriver().findElements(xmlTarifasAnioOption);
		for(WebElement opt:options)
		{
			String text = opt.getText();
			if(text.equals(xmlTarifasAnioOption))
				opt.click();
		}
	}
	
	/**
	 * Metodo para seleccionar el Mes de Tablas-Tarifas
	 * @param tipo A�o o Mes
	 * @author Jorge Ruiz
	 */
	public void selectMes(String tipo) 
	{
		u.click(xmlTarifasMes);
		u.hardWait(1);
		List<WebElement> options = u.getDriver().findElements(xmlTarifasMesOption);
		for(WebElement opt:options)
		{
			String text = opt.getText();
			if(text.equals(xmlTarifasMesOption))
				opt.click();
		}
		
	}
	
	/**
	 * Metodo para hacer click en el boton buscar
	 * @param tipo A�o o Mes
	 * @author Jorge Ruiz
	 */
	public void buscar() 
	{
		u.click(xmlTarifasBuscar);
		u.hardWait(5);
	}
	
	/**
	 * Metodo para validar los headers de la tabla de transacciones 
	 * @param string
	 * @return true si la informacion en el xlsx corresponde a la consulta realizada, false en caso contrario
	 * @author Jorge Ruiz
	 */
	public Boolean validarHeadersTabla()
	{
		u.scrollToElement(xmlDivTable );
		int cont = 0;
		String[] val = {"L�mite Inferior", "L�mite Superior","Cuota Fija","Excedente"};
		ArrayList<String> values = new ArrayList<String>(Arrays.asList(val));
		int i = 1;
		for(String value : values)
		{
			String text = u.getDriver().findElement(By.xpath(String.format(xmltableHeadTarifas , String.valueOf(i)))).getText().trim();
			if(!(text.equals(values.get(i-1))))
				return false;
			i++;
		}
		return true;
	}
	
	
}

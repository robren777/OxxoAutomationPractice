package WEB.inventarioEspacios.om;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class MenuPrincipalOpcionesIEP {
	
	SeleniumUtil u;
	TestCase testCase;
	MenuPrincipalIEP mp;
	MetodosExtra me;
	
	/**
	 * Localizadores Men� Administrador
	 ******************************************************************************************/
	
	By xmlPeriodo = By.xpath("//label[contains(text(),'Periodo')]");
	By xmlProveedores = By.xpath("//label[contains(text(),'Proveedores')]");
	By xmlConsultaPlazas = By.xpath("//label[contains(text(),'Consulta de plazas')]");
	By xmlConsultaPlazasCCM = By.xpath("//label[contains(text(),'Consulta de plazas CCM')]");
	By xmlUsuarios = By.xpath("//label[contains(text(),'Usuarios')]");
	By xmlAsociarPopPlaza = By.xpath("//label[contains(text(),'Asociar POP a Plazas')]");
	By xmlAsociarPopProveedor = By.xpath("//label[contains(text(),'Asociar POP a Proveedor')]");
	
	
	/**
	 * Localizadores Men� Espacios
	 ******************************************************************************************/
	
	By xmlConsultaInventarios = By.xpath("//label[contains(text(),'Consulta de inventarios')]");
	By xmlConsultaEspEq = By.xpath("//label[contains(text(),'Consulta de Espacios/Equipos')]");
	By xmlCargaMasivaInv = By.xpath("//label[contains(text(),'Carga masiva de inventarios')]");
	By xmlConsultaGrupEsp = By.xpath("//label[contains(text(),'Consulta de grupo de espacios')]");
	
	/**
	 * Localizadores Men� Pedidos
	 ******************************************************************************************/
	
	By xmlBandejaPedidos = By.xpath("//label[contains(text(),'Bandeja de Pedidos')]");
	By xmlCrearPedidos = By.xpath("//label[contains(text(),'Crear Pedidos')]");
	By xmlConsultaDirEnv = By.xpath("//label[contains(text(),'Consulta de direcci�n de env�o')]");
	By xmlTiposPedido = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[4]/a/div/label");
	
	/**
	 * Localizadores Men� POP's
	 ******************************************************************************************/
	
	By xmlPops = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[1]/a/div");
	By xmlCargaMasivaPops = By.xpath("//label[contains(text(),'Carga masiva pops')]");
	By xmlTipoPops = By.xpath("//label[contains(text(),'Tipos de POP')]");
	
	
	/**
	 * Localizadores Men� Reporte
	 ******************************************************************************************/
	
	By xmlRCEspacios = By.xpath("//label[contains(text(),'Reporte de cobertura de grupo de espacios')]");
	By xmlRCEspaciosHistorico = By.xpath("//label[contains(text(),'Reporte de cobertura de espacio hist�rico')]");
	By xmlRDenvio = By.xpath("//label[contains(text(),'Reporte de detalle de envio')]");
	By xmlRDPedido = By.xpath("//label[contains(text(),'Reporte de detalle de pedidos')]");
	
	/**
	 * Constructor
	 ******************************************************************************************/
	
	public MenuPrincipalOpcionesIEP(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.mp = new MenuPrincipalIEP(u, testCase);
		this.me = new MetodosExtra(u, testCase);
	}
	
	/*
	 * Metodos Administrador
	 ******************************************************************************************/
	
	public Boolean menuAdministradorAdmin() {
		
		Boolean correct = false;
		
		mp.clickAdministracion();
		
		if(me.validarVisualizacion(xmlPeriodo) == true &&  me.validarVisualizacion(xmlProveedores) == true && 
				me.validarVisualizacion(xmlConsultaPlazas) == true && me.validarVisualizacion(xmlConsultaPlazasCCM) == true && 
				me.validarVisualizacion(xmlUsuarios) == true && me.validarVisualizacion(xmlAsociarPopPlaza) == true && 
				me.validarVisualizacion(xmlAsociarPopProveedor) == true) {
			
			correct = true;
			
		}
		
		mp.clickAdministracion();
		
		return correct;
		
	}
	
	public Boolean menuAdministradorAdminM() {
		
		Boolean correct = false;
		
		mp.clickAdministracionM();
		
		if( me.validarVisualizacion(xmlConsultaPlazas) == true && me.validarVisualizacion(xmlConsultaPlazasCCM) == true && 
				me.validarVisualizacion(xmlProveedores) == true && me.validarVisualizacion(xmlAsociarPopPlaza) == true && 
				me.validarVisualizacion(xmlAsociarPopProveedor) == true) {
			
			correct = true;
			
		}
		
		mp.clickAdministracionM();
		
		return correct;
		
	}
	
	/*
	 * Metodos Espacios
	 ******************************************************************************************/
	
	public Boolean menuEspaciosAdmin() {
		
		Boolean correct = false;
		
		mp.clickEspacios();
		
		if( me.validarVisualizacion(xmlConsultaInventarios) == true &&  me.validarVisualizacion(xmlConsultaEspEq) == true && 
				me.validarVisualizacion(xmlCargaMasivaInv) && me.validarVisualizacion(xmlConsultaGrupEsp) == true ) {
			
			correct = true;
			
		}
		
		mp.clickEspacios();
		
		return correct;
		
	}
	
	public Boolean menuEspaciosPlaza() {
		
		Boolean correct = false;
		
		mp.clickEspaciosPO();
		
		if( me.validarVisualizacion(xmlConsultaInventarios) == true && me.validarVisualizacion(xmlCargaMasivaInv)) {
			
			correct = true;
			
		}
		
		mp.clickEspaciosPO();
		
		return correct;
		
	}
	
	/*
	 * Metodos Pedidos
	 ******************************************************************************************/
	
	public Boolean menuPedidosAdmin() {
		
		Boolean correct = false;
		
		mp.clickPedidos();
		
		if( me.validarVisualizacion(xmlBandejaPedidos) == true &&  me.validarVisualizacion(xmlCrearPedidos) == true && 
				me.validarVisualizacion(xmlConsultaDirEnv) && me.validarVisualizacion(xmlTiposPedido) == true ) {
			
			correct = true;
			
		}
		
		mp.clickPedidos();
		
		return correct;
		
	}
	
	public Boolean menuPedidosPlaza() {
		
		Boolean correct = false;
		
		//mp.clickPedidosOP();
		mp.clickPedidos();
		
		if( me.validarVisualizacion(xmlBandejaPedidos) == true &&  me.validarVisualizacion(xmlCrearPedidos) == true && 
				me.validarVisualizacion(xmlConsultaDirEnv) ) {
			
			correct = true;
			
		}
		
		//mp.clickPedidosOP();
		mp.clickPedidos();
		
		return correct;
		
	}
	
	public Boolean menuPedidosEstafeta() {
		
		Boolean correct = false;
		
		mp.clickPedidosE();
		
		if( me.validarVisualizacion(xmlBandejaPedidos) == true ) {
			
			correct = true;
			
		}
		
		mp.clickPedidosE();
		
		return correct;
		
	}
	
	public Boolean menuPedidosAdminM() {
		
		Boolean correct = false;
		
		mp.clickPedidosM();
		
		if( me.validarVisualizacion(xmlBandejaPedidos) == true &&  me.validarVisualizacion(xmlCrearPedidos) == true && 
				me.validarVisualizacion(xmlConsultaDirEnv) ) {
			
			correct = true;
			
		}
		
		mp.clickPedidosM();
		
		return correct;
		
	}

	/*
	 * Metodos POPs
	 ******************************************************************************************/
	
	public Boolean menuPopsAdmin() {
		
		Boolean correct = false;
		
		mp.clickPops();
		
		if( me.validarVisualizacion(xmlPops) == true &&  me.validarVisualizacion(xmlCargaMasivaPops) == true && 
				me.validarVisualizacion(xmlTipoPops) ) {
			
			correct = true;
			
		}
		
		mp.clickPops();
		
		return correct;
		
	}
	
	/*
	 * Metodos Reportes
	 ******************************************************************************************/
	
	public Boolean menuReporteAdmin() {
		
		Boolean correct = false;
		
		mp.clickReportes();
		
		if( me.validarVisualizacion(xmlRCEspacios) == true &&  me.validarVisualizacion(xmlRCEspaciosHistorico) == true && 
				me.validarVisualizacion(xmlRDenvio) && me.validarVisualizacion(xmlRDPedido) == true ) {
			
			correct = true;
			
		}
		
		mp.clickReportes();
		
		return correct;
		
	}
	
	public Boolean menuReportePlaza() {
		
		Boolean correct = false;
		
		mp.clickReportesPO();
		
		if( me.validarVisualizacion(xmlRCEspacios) == true &&  me.validarVisualizacion(xmlRCEspaciosHistorico) == true && 
				me.validarVisualizacion(xmlRDenvio) && me.validarVisualizacion(xmlRDPedido) == true ) {
			
			correct = true;
			
		}
		
		mp.clickReportesPO();
		
		return correct;
		
	}
	
	public Boolean menuReporteMarca() {
		
		Boolean correct = false;
		
		mp.clickReportesM();
		System.out.println("se hizo click en el menu reportes");
		
		if( me.validarVisualizacion(xmlRCEspacios) == true &&  me.validarVisualizacion(xmlRCEspaciosHistorico) == true && 
				me.validarVisualizacion(xmlRDenvio) && me.validarVisualizacion(xmlRDPedido) == true ) {
			
			correct = true;
			
		}
		
		mp.clickReportesM();
		
		return correct;
		
	}

	/*
	 * Metodos Administrador 
	 ******************************************************************************************/
	
	public void menuAdministrador() {
		
		Boolean validacion = false;
		
		validacion = menuAdministradorAdmin();
		assertTrue(validacion, "El menu Administrador no se muestra correctamente");
		
		validacion = menuEspaciosAdmin();
		assertTrue(validacion, "El menu Espacios no se muestra correctamente");
		
		validacion = menuPedidosAdmin();
		assertTrue(validacion, "El menu Pedidos no se muestra correctamente");
		
		validacion = menuPopsAdmin();
		assertTrue(validacion, "El menu POPs no se muestra correctamente");
		
		validacion = menuReporteAdmin();
		assertTrue(validacion, "El menu Reportes no se muestra correctamente");
		
	}
	
	/*
	 * Metodos Plaza 
	 ******************************************************************************************/
	
	public void menuPlaza() {
		
		Boolean validacion = false;
		
		validacion = menuEspaciosPlaza();
		assertTrue(validacion, "El menu Espacios no se muestra correctamente");
		
		validacion = menuPedidosPlaza();
		assertTrue(validacion, "El menu Pedidos no se muestra correctamente");
		
		validacion = menuReportePlaza();
		assertTrue(validacion, "El menu Reportes no se muestra correctamente");
		
	}
	
	/*
	 * Metodos Estafeta
	 ******************************************************************************************/
	
	public void menuEstafeta() {
		
		Boolean validacion = false;
	
		validacion = menuPedidosEstafeta();
		assertTrue(validacion, "El menu Pedidos no se muestra correctamente");
		
	}
	
	/*
	 * Metodos Administrador Marca
	 ******************************************************************************************/
	
	public void menuAdministradorM() {
		
		Boolean validacion = false;
		
		validacion = menuAdministradorAdminM();
		assertTrue(validacion, "El menu Administrador no se muestra correctamente");
		
		validacion = menuPedidosAdminM();
		assertTrue(validacion, "El menu Pedidos no se muestra correctamente");
		System.out.println("estas por pasar al menu de reportes");
		validacion = menuReporteMarca();
		assertTrue(validacion, "El menu Reportes no se muestra correctamente");
		
	}

}

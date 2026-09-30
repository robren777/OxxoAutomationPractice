package IMA.MVP2_RST.om;

import org.openqa.selenium.By;

public class RstElements {
	
	By infoComercialQa = By.cssSelector("#projects_ProjectsStyle > table > tbody > tr:nth-child(4) > td:nth-child(1) > div > table > tbody > tr > td.mstrLargeIconViewItemText > a");
	By informesCompartidos = By.cssSelector("#menuTabs > div.mstrMenuSection > div.mstrMenuContent > span.mstrMenuItempublic.mstrMenuItem > a");	
	By inforComercial = By.cssSelector("#FolderIcons > tbody > tr:nth-child(6) > td:nth-child(1) > div > table > tbody > tr > td.mstrLargeIconViewItemText > a");
	By telefoniaServicios = By.cssSelector("#FolderIcons > tbody > tr:nth-child(18) > td:nth-child(1) > div > table > tbody > tr > td.mstrLargeIconViewItemText > a");
	By resultadosServicios = By.cssSelector("#FolderIcons > tbody > tr > td:nth-child(1) > div > table > tbody > tr > td.mstrLargeIconViewItemText > a");
	By nacional = By.cssSelector("#FolderIcons > tbody > tr:nth-child(1) > td:nth-child(1) > div > table > tbody > tr > td.mstrLargeIconViewItemText > a");
	
	By analisisSemana = By.cssSelector("#FolderIcons > tbody > tr:nth-child(5) > td > div > table > tbody > tr > td.mstrLargeIconViewItemText > a");
	
	//Seleccionar semana 10
	
	
	By inputBuscarSemana = By.cssSelector("#id_mstr56_txt");
	By btnBuscarSemana = By.cssSelector("#id_mstr56 > table > tbody > tr > td.mstrTextBoxWithIconCellIcon > div > input");
	
	
	By seleccionarSemana = By.cssSelector("#id_mstr58ListContainer > div > div");
	By btnAgregarSemana = By.cssSelector("#id_mstr60 > img");
	
	//Seleccionar Todas Sucursales
	By seleccionarTienda = By.cssSelector("#id_mstr87ListContainer > div:nth-child(10) > div");
	By btnAgregarTienda = By.cssSelector("#id_mstr89 > img");
	
	
	//Generar reporte
	By btnEjecutarInforme = By.cssSelector("#id_mstr99");
	By lblTituloReporte = By.cssSelector("#reportDetails_ReportDetailsPanelStyle > div.mstrPanelPortrait > table > tbody > tr:nth-child(1) > td > div > span.mstrPanelTitle");
	By tblReport = By.cssSelector("#table_UniqueReportID");
	
	By retiros = By.cssSelector("#FolderIcons > tbody > tr:nth-child(4) > td:nth-child(2) > div > table > tbody > tr > td.mstrLargeIconViewItemText > a");
	By resultadosComerciales = By.cssSelector("#FolderIcons > tbody > tr > td > div > table > tbody > tr > td.mstrLargeIconViewItemText > a");
	By porTema = By.cssSelector("#FolderIcons > tbody > tr:nth-child(2) > td:nth-child(1) > div > table > tbody > tr > td.mstrLargeIconViewItemText > a");
	By analisisServicios = By.cssSelector("#FolderIcons > tbody > tr:nth-child(1) > td:nth-child(1) > div > table > tbody > tr > td.mstrLargeIconViewItemText > a");
	
	
	
	By btnContinuar = By.id("3054");
	By reportesLiberar = By.cssSelector("#FolderIcons > tbody > tr:nth-child(1) > td:nth-child(1) > div > table > tbody > tr > td.mstrLargeIconViewItemText > a");
	
	
	
	By reportSemana = By.cssSelector("#FolderIcons > tbody > tr:nth-child(5) > td > div > table > tbody > tr > td.mstrLargeIconViewItemText > a");
	By btnEjecutaInforme = By.id("id_mstr99");
	By semana45 = By.cssSelector("#id_mstr58ListContainer > div:nth-child(9) > div");
	By agregaSemana = By.cssSelector("#id_mstr60 > img");	
	By disponible = By.cssSelector("#id_mstr87 > tbody > tr > td > span > div.mstrListBlockCaption");
	By tiendasTodas = By.xpath("//*[@id=\"id_mstr87ListContainer\"]/div[10]/div");
	By agregaTienda = By.cssSelector("#id_mstr89 > img");
	
	
	

	
	

}
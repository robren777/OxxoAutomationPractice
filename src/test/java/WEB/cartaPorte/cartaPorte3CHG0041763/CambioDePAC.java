package WEB.cartaPorte.cartaPorte3CHG0041763;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.testng.Assert.assertEquals;

import java.sql.SQLException;
import java.util.HashMap;

import org.sikuli.script.FindFailed;
import org.testng.annotations.Test;

import WEB.cartaPorte.objectModelPortalCP.AdministracionParametros;
import WEB.cartaPorte.objectModelPortalCP.EditarConstante;
import WEB.cartaPorte.objectModelPortalCP.LoginCP;
import WEB.cartaPorte.objectModelPortalCP.MenuPortal;
import WEB.cartaPorte.objectModelPortalCP.MonitoreoPAC;
import WEB.cartaPorte.objectModelPortalCP.PortalCartaPorte;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class CambioDePAC extends BaseExecution{
	/**
	 * PORTAL CARTA PORTE/ CARTA PORTE 
	 * DESCRIPCION: 
	 * Proceso automatizado para cambiar PAC de timbrado de Konesh a Diverza
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	@Test(dataProvider = "data-provider")
	
	public void CambioDePAC_test(HashMap<String, String> data) throws FindFailed, SQLException {
		String portalurl = data.get("server");
		String userName=data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String opcionAdmon=data.get("opcionAdmon");
		String opcionMonitoreo=data.get("opcionMonitoreo"); 
		SeleniumUtil u=new SeleniumUtil(new ChromeTest(), true);
		LoginCP login = new LoginCP(u, testCase, portalurl);
		MenuPortal portalmenu= new MenuPortal(u, testCase);
		PortalCartaPorte portalCP= new PortalCartaPorte(u, testCase);	
		MonitoreoPAC monitoreoPAC= new MonitoreoPAC(u,testCase);
		AdministracionParametros admonParametro=new AdministracionParametros(u,testCase);
		String mensajeEncontrar= "No fue posible encontrar elemento: ";
		String mensajeSeleccion= "No fue posible seleccionar elemento: ";
		EditarConstante editarVariable=new EditarConstante(u,testCase);
		
		String variables []= {"CPDefault","Destino.NombreDestinatario","Destino.RfcDestinatario","Emisor.Nombre",
				"Emisor.RFC","Origen.NombreRemitente","Origen.RfcRemitente","Receptor.DomicilioFiscal","Receptor.Nombre",
				"Receptor.RFC"};
		
		String renglonV[]= {"9","10","1","5","6","3","5","10","5","7"};
		String pag[]= {"1","1","2","2","2","4","4","4","5","5"};
		
		String pac="K"; //K=KONESH  /  D= DIVERZA
		
		
		String pacCONF="";
		if(pac.compareTo("K")==0) {
			pacCONF="Konesh";
		}else {
			pacCONF="Diverza";
		}
	
		
		
		//PASO 1:
		addStep("Ingresar a la liga de acceso: https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf");
		u.get(login.getUrl());
		login.proceedToPortal();
		boolean cargarPagina=login.encontrarMensajeInicio();
		testCase.addScreenShotCurrentStep("CargarURL");
		assertTrue( "El sitio no fue cargado satisfactoriamente",cargarPagina);
		
		//PASO 2: 
		addStep("Capturar credenciales de acceso para iniciar sesión");
		login.cicloLogin2(userName, password);
		String mensajePortal=portalmenu.accesoMenuPortal(userName, password);
		assertTrue("No se encontró mensaje: Portal de Aplicaciones", mensajePortal.compareTo("Portal de Aplicaciones")==0);
		
		/*
		 * PASO 3:
		 * Descripción del paso: Seleccionar "Portal Carta Porte"
		 * 
		 * Resultado Esperado: Encontrar menú "Administracion"
		 */
		addStep("Seleccionar: Portal Carta Porte ");
		portalmenu.seleccionarOpcionCartaPorte();
		boolean encontrarMenuCP=portalCP.encontrarMenuOpcionesCartaPorte();
		assertTrue("No se encontro Menu de Carta Porte", encontrarMenuCP);
		//PASO 8
		addStep("Seleccionar Parametros Generales en Menu Administracion");
		String campoActual="parametros";
		String encontrarOpcionMenu=portalCP.encontrarOpcionesAdministracione(opcionAdmon,false);
		assertEquals(encontrarOpcionMenu, "Parametros Generales", "No se encontro la opcion: Parametros Generales");
		String seleccionarElemento=portalCP.seleccionarOpcionAdministracion(campoActual);
		assertEquals(seleccionarElemento, "Listado de Constantes", "No se encontro el texto: Listado de Equipos");
		//PASO 9y 10
		int contador=0;
		String renglonPagina="1-1";
		String valorActual="";
		int j=0;
		do {
			String nombreVariable=variables[contador];
			valorActual=admonParametro.obtenerValorVariable(variables[contador],pac);
			assertNotEquals("ERROR", valorActual,"No se recibio un PAC valido: "+pac);
			addStep("Buscar variable: "+nombreVariable);
	//		String renglon[]=renglonPagina.split("-");
			renglonPagina=admonParametro.encontrarRenglonYEditarVariableRenglon(nombreVariable, this, true,renglonV[j],pag[j]);
			
			//assertTrue(mensajeEncontrar+nombreVariable, seleccionarCasilla);
			//
			addStep("Limpiar y capturar nuevo valor");
			valorActual=editarVariable.limpiaryCapturarValorRenglon(valorActual);
			if(valorActual.compareTo("ERROR")!=0) {
				testCase.addScreenShotCurrentStep("ModificarCasilla");
				campoActual="botonGuardar";
				boolean seleccionarCasilla=editarVariable.encontrarElemento(campoActual);
				
				addStep("Seleccionar botón Guardar");
				seleccionarCasilla=editarVariable.seleccionarElemento(campoActual, false);
				if(seleccionarCasilla) {
					//assertTrue(mensajeSeleccion+nombreVariable, seleccionarCasilla);
					campoActual="AlertaGuardado";
					campoActual=admonParametro.obtenerTexto(campoActual,true);
					assertEquals(campoActual, "¡Constante actualizada exitosamente!", "No se actualizo exitosamente la variable: "+nombreVariable);
					contador++;
					j++;
				}
			}
		//}while(variables.length>contador);
		}while(pag.length>j);
		addStep("Seleccionar Menu Monitoreo");
		encontrarOpcionMenu=portalCP.encontrarOpcionesMonitoreo(opcionMonitoreo);
		assertEquals(encontrarOpcionMenu, "Configuración PAC", "No se encontro la opcion: Configuración PAC");
		//PASO 5
		addStep("Seleccionar opcion Configuración PAC");
		seleccionarElemento=portalCP.seleccionarOpcionMonitoreo(opcionMonitoreo);
		assertEquals(seleccionarElemento, "PAC Configuración", "No se encontro el texto:  PAC Configuración");
		//PASO 6
		addStep("Seleccionar Konesh en Campo Pac Activo");
		campoActual="botonPAC";
		boolean seleccionarCasilla=monitoreoPAC.seleccionarElemento(campoActual,true,pacCONF);
		assertTrue(mensajeSeleccion+campoActual, seleccionarCasilla);
		//PASO 7
		addStep("Seleccionar Guardar");
		campoActual="botonGuardar";
		seleccionarCasilla=monitoreoPAC.seleccionarElemento(campoActual,true,null);
		assertTrue(mensajeSeleccion+campoActual, seleccionarCasilla);
		
		u.close();
		
	}
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

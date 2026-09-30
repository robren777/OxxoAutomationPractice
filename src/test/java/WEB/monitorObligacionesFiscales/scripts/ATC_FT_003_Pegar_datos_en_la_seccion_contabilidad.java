package WEB.monitorObligacionesFiscales.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.monitorObligacionesFiscales.om.Contabilidad;
import WEB.om.PortalApps;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_003_Pegar_datos_en_la_seccion_contabilidad extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_Pegar_datos_en_la_seccion_contabilidad_test(HashMap<String, String> data) {
		/*
		 * Variables
		 ******************************************************************************************/
		String User = data.get("User");
		String Password = PasswordUtil.decryptPassword(data.get("Password"));//String password = data.get("Password");//
		String Url = data.get("Url");
		String Lider = data.get("Lider");
		String RazonSocial = data.get("Razon_Social");
		String RFC = data.get("RFC");
		String Entidad_Federativa = data.get("Entidad_Federativa");
		String Delegacion = data.get("Delegacion");
		String Colonia = data.get("Colonia");
		String Codigo_Postal = data.get("Codigo_Postal");
		String Otra_Colonia = data.get("Otra_Colonia");
		String Calle = data.get("Calle");
		String No_Exterior = data.get("No_Exterior");
		String No_Interior = data.get("No_Interior");
		String Entre_Calle = data.get("Entre_Calle");
		String Y_Calle = data.get("Y");
		
		String msg = "Estimado usuario, si realizas movimientos en ingresos, deducciones, n�mina o finiquito, es necesario volver a realizar el c�lculo de impuestos.";
		
		String Ingresos = data.get("Ingresos");
		String Iva = data.get("Iva");
		String Iva_Retenido = data.get("Iva_Retenido");
		
		String Concepto = data.get("Concepto");
		String Cuota = data.get("Cuota");
		String Amortizacion = data.get("Amortizacion");
		
		String ConceptoDO = data.get("Concepto");
		String ProveedorDO = data.get("Proveedor");
		String SubtotalDO = data.get("SubtotalDO");
		String IvaDO = data.get("IvaDO");
		
		String ConceptoPE = data.get("ConceptoPE");
		String ProveedorPE = data.get("ProveedorPE");
		String SubtotalPE = data.get("SubtotalPE");
		String IvaPE = data.get("IvaPE");
		
		String ConceptoSI = data.get("ConceptoSI");
		String CuotaSI = data.get("CuotaSI");
		String AmortizacionSI = data.get("AmortizacionSI");
		
		u = new SeleniumUtil(new IETest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		Contabilidad co = new Contabilidad (u,testCase);


		/*
		 *                                             PASO 1
		 ******************************************************************************************/
			addStep("Ingresar al portal de aplicaciones");

			u.get(Url);
			u.hardWait(3);
			pa.proceedToPortalIEsecureSite();


		/*
		 *                                             PASO 2
		 ******************************************************************************************/
			
			addStep(" Ingresar a la liga determinada con credenciales v�lidas ");

			pa.cicloLogin(User, Password);
			
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
			addStep("Seleccionar la aplicaci�n Monitor de obligaciones fiscales");
			pa.clickPortalMonitorObligacionesFiscales(1);
			
		/*
		 *                                             PASO 4
		 ******************************************************************************************/
			addStep("Seleccionar la opci�n Contabilidad --> Principal");
			co.clicContabilidadPrincipal();
			
		/*
		 *                                             PASO 5
		 ******************************************************************************************/
			addStep("Seleccionar un l�der Activo");
			co.seleccionarContabilidadLiderActivo(Lider);
			//Metodo en caso de que arroje el mensaje de advertencia de contabilidad
			assertTrue(co.MensajeAdvertenciaContabilidad(msg),"El mensaje de advertencia no coincide");
		
		/*
		 *                                             PASO 6
		 ******************************************************************************************/
			addStep("Seleccionar la opci�n Proveedores");
			co.clickContabilidadPproveedor();
			
		/*
		 *                                             PASO 7
		 ******************************************************************************************/
			addStep("Copiar y pegar los datos del proveedor ");
			u.hardWait(7);
			co.modificarDatosProveedor(RazonSocial, RFC, Entidad_Federativa, Delegacion, Colonia, Codigo_Postal, Otra_Colonia, No_Exterior, No_Interior, Calle, Entre_Calle, Y_Calle);
			co.clicCPPBtnAgregar();
		
		/*
		 *                                             PASO 8
		 ******************************************************************************************/
			addStep("Seleccionar la opci�n Ingresos");
			u.hardWait(5);
			co.clicContabilidadPIngresos();
			
		/*
		 *                                             PASO 9
		 ******************************************************************************************/
			addStep("Copiar y pegar los datos de Ingresos");
			u.hardWait(5);
			co.modificarDatosIngresos(Ingresos, Iva, Iva_Retenido);
			
			
		/*
		 *                                             PASO 10
		 ******************************************************************************************/
			addStep("Dar clic en el bot�n Guardar");
			co.clicContabilidadPIBtnGuardar();

			
		/*
		 *                                             PASO 11
		******************************************************************************************/
			addStep("Seleccionar la opci�n Deducciones --> Estado ");
			co.clicCPDeduccionesEstado();
			
		/*
		 *                                             PASO 12
		******************************************************************************************/
			addStep("Copiar y pegar los datos para Cuota y Amortizaci�n");
			u.hardWait(5);
			co.modificarDatosDeduccionEstado(Concepto, Cuota, Amortizacion);
			
			
		/*
		 *                                             PASO 13
		 ******************************************************************************************/
			addStep("Dar clic en el bot�n Agregar Deducci�n ");
			co.clicContabilidadPDEBtnAgregar();
			
		/*
		 *                                            PASO 14
		******************************************************************************************/
			addStep("Seleccionar la opci�n Deducciones --> Operativas");
			co.clicCPDeduccionesOperativas();
			
		/*
		 *                                            PASO 15
		******************************************************************************************/
			addStep("Ingresar los datos Concepto y proveedor, Copiar y pegar los datos para Factura, subtotal e IVA");
			co.modificarDatosDeduccionOperativo(ConceptoDO, ProveedorDO, SubtotalDO, IvaDO);

		/*
		 *                                             PASO 16
		 ******************************************************************************************/
			addStep("Dar clic en el bot�n Agregar Deducci�n ");
			co.clicContabilidadPDOBtnActualizar();
	
		/*
		 *                                             PASO 17
		 ******************************************************************************************/
			addStep("Seleccionar la opci�n Deducciones --> Personales");
			co.clicCPDeduccionesPersonales();
			
		/*
		 *                                             PASO 18
		 ******************************************************************************************/
			addStep("Ingresar los datos Concepto y proveedor, Copiar y pegar los datos para Factura, subtotal e IVA");
			co.modificarDatosDeduccionPersonal(ConceptoPE, ProveedorPE, SubtotalPE, IvaPE);
				
		/*
		 *                                             PASO 19
		******************************************************************************************/
			addStep("Dar clic en el bot�n Agregar Deducci�n ");
			co.clicContabilidadPDEBtnAgregar();
					
		/*
		*                                             PASO 20
		******************************************************************************************/
			addStep("Seleccionar la opci�n Solicitud de impuestos");
			co.clicContabilidadPSolicituddeImpuestos();
		/*
		*                                             PASO 21
		******************************************************************************************/
			addStep(" Seleccionar el concepto, copiar y pegar el campo para  cuota y amortizaci�n");
			co.modificarDatosSolicitudeImpuestos(ConceptoSI, CuotaSI, AmortizacionSI);
			
		/*
		*                                             PASO 22
		******************************************************************************************/
			addStep("Dar clic en el bot�n Agregar Solicitud ");
			co.clicContabilidadPSIBtnAgregar();
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
		return "";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "AutomationQA";
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
	@AfterMethod
	public void afterMethod() {
		//u.getDriver().quit();
	}

}
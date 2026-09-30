package Mobile.cedis.mvp3.handheld.sprint2;

import java.util.HashMap;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Mobile.cedis.om.D01_Conexiones_BD;
import modelo.BaseExecution;

public class ATC_FT_006_RecepcionDeProductoCervezaConEnvasesRetornablesCedisTradicional extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_006_RecepcionDeProductoCervezaConEnvasesRetornablesCedisTradicional_test(HashMap<String, String> data) throws Exception {
		
		/******************************* Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script  ********************/

		D01_Conexiones_BD rdmmty = new D01_Conexiones_BD(testCase, null, this);
		
		//=================================== Inicio de Caso =====================================//
//
//		String consultaPAVZONEG = "select * from rdm100.xxfc_pav where ROWNUM <= 10 order by INSERT_DATE desc";
//		//Para el script anterior(select * from rdm100.xxfc_pav) se modifica ya que se tarda en procesar y si se quita el _zone_v regresa una cantidad de datos grande
//		//por lo que se modifica a ese script para que muestre datos correctamente
//		String consultaPAVZONEe = "select * from rdm100.xxfc_pav_zone_v where DEST_ID = '21222' and rownum <= 1";
//		
//		
//		addStep("Se realiza la consulta a la BD de RDM MTY");
//		SQLResult resultQueryIS = dbLog.executeQuery(consultaPAVZONEe);
//		boolean validacosnulta= resultQueryIS.isEmpty();
//		System.out.print(validacosnulta);
//		testCase.addQueryEvidenceCurrentStep(resultQueryIS);
//		assertFalse(validacosnulta, "No existen documentos de tipo DOC_TYPE='INV' pendientes de procesar");

		//=================================== Inicio de Caso =====================================//
		/*
		 *  Paso 1 - Ingresar a la BD de RDMQA-MTY
			Nota: Previo hay que realizar la verificación del pedido 
		 */
		addStep("Ingresar a la BD de RDMQA-MTY");
		rdmmty.conexionrdmmtyv11();
//		/*
//		 *  Paso 2 - ?Se tiene acceso a la BD tradicional con las crendeciales.
//		 */
		addStep("?Se tiene acceso a la BD tradicional con las crendeciales.");
//		/*
//		 *  Paso 3 - Ejecutar la consulta: select * from rdm100.xxfc_pav_zone_v 
//		 */
		addStep("Ejecutar la consulta: select * from rdm100.xxfc_pav_zone_v ");
		rdmmty.consultaPAVZONEG();
//		/*
//		 *  Paso 4 - ? La consulta arroja información en la tabla seleccionada.
//		 */
		addStep("?La consulta arroja información en la tabla seleccionada.");
//		/*
//		 *  Paso 5 -  Ejecutar la consulta: Select * from wmuser.xxfc_pav_zone_v WHERE DISTRO_NBR = '#transferencia'; 
//		 */
		addStep("Ejecutar la consulta: Select * from wmuser.xxfc_pav_zone_v WHERE DISTRO_NBR = '#transferencia';");
		rdmmty.consultaPAVZONEe();
//		/*
//		 *  Paso 6 - ?La consulta arroja información del numero de la transferencia colocada. Con los Campos nuevos:
//
//		 */
		addStep("? ? La consulta arroja información del numero de la transferencia colocada. Con los Campos nuevos:\r\n");
	}
	
		/*
		 * Cedis MVP3 Sprint 2: ATC-FT-006 Recepción de producto Cerveza con envases retornables Cedis Tradicional
		 * Descripción:Validar que al realizar la consulta de una transferencia que contenga  artículos de cerveza con envase retornable se registre la entrada tanto para el producto como para el envase.
		 *
		 * @autor Vanessa Torres
		 * @date 11/09/2024
		 */ 
		 	
		@Override
		public String setTestFullName() {
			return "ATC_FT_006_RecepcionDeProductoCervezaConEnvasesRetornablesCedisTradicional";
		}
		
		@Override
		public String setTestDescription() {
			return "Validar que al realizar la consulta de una transferencia que contenga  artículos de cerveza con envase retornable se registre la entrada tanto para el producto como para el envase.";
		}
		
		@Override
		public String setTestDesigner() {
			return "Vanessa Torres";
		}
		
		@Override
		public String setTestInstanceID() {
			return null;
		}
		
		@Override 
		public String setPrerequisites() {
			return "1.Usuario y Contraseña de Aplicación conexión a BD rdm. \r\n"
					+ "Nombre	RDMQA-MTY\r\n"
					+ "Usuario	RDMVIEW\r\n"
					+ "Contraseña	rdmview01*\r\n"
					+ "Host	fcrdmq01.femcom.net\r\n"
					+ "Puerto	1535\r\n"
					+ "SID	FCRD01CU.\r\n"
					+ "2.  Numero de la transferencia a utilizar\r\n"
					+ "3. Pedido contenga bultos tipo canastilla\"\r\n"
					+ "4. Contar con un ASN con artículo cerveza que contenga envase retornable\r\n"
					+ "";
		}
		
		@BeforeTest
		public void beforeTest() {
		}
		
	}
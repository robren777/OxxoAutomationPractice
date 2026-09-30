package WorkFlows.AtributosLogisticos.ActualizacionBPM;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.AtributosLogisticos.PortalApps;
import WorkFlows.util.AtributosLogisticos.utilAtributos_Metodos;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class MTC_FT_002_FlujoFA_CedisProveedorExistente_ActualizacionBPM extends BaseExecution {
		private SeleniumUtil u;

		@Test(dataProvider = "data-provider")

		public void ATC_FT_002_FlujoFACedisInicioSesion_ActualizacionBPM_Test(HashMap<String, String> data) throws Exception {
			testCase.setProject_Name("ATC_FT_Framework WorkFlows");

			/*
			 * Variables
			 */
			String user = data.get("usuario");
			String password = data.get("clave");
			//String password = PasswordUtil.decryptPassword(data.get("clave"));
			String url = data.get("url");
			//String tiempoVida = data.get("TiempoVida");
			
			/*
			 * Utilerias
			 */
			u = new SeleniumUtil(new FirefoxTest(), true);
			u.get(url);
			PortalApps pomPortalApps = new PortalApps(u, testCase, user, password);
			
			utilAtributos_Metodos atributosMetodos = new utilAtributos_Metodos(u, testCase);
			
			
			addStep("Iniciar Sesion en el Portal de Aplicaciones");
			pomPortalApps.logginAtributosLogisticos();
			
			addStep("Captura de datos basicos");
			atributosMetodos.InformacionArticulo(data.get("CodigoDeBarras"),data.get("DescripcionCorta"), data.get("Descripcion"), data.get("SuperGrupo"), data.get("Categoria"), data.get("articuloEspejo"), data.get("TipoDeNegocio"), data.get("AlmacenadoDeProducto"), data.get("TipoDeProducto"), data.get("TiempoVida"), data.get("CostoPorPieza"), data.get("VentaEsperada"), data.get("CostoPorCaja")
				, data.get("FrecuenciaDeVisitaSemanal"), data.get("Presentacion"), data.get("Contenido"), data.get("UnidadMedidaContenido"), data.get("PlazoPago"), data.get("ClausulasRevisar"), data.get("Comentarios"));
			
		}

		@Override
		public void beforeTest() {
			
		}

		@Override
		public String setPrerequisites() {
			return null;
		}

		@Override
		public String setTestDescription() {
			return null;
		}

		@Override
		public String setTestDesigner() {
			return null;
		}

		@Override
		public String setTestFullName() {
			return null;
		}

		@Override
		public String setTestInstanceID() {
			return null;
		}
}

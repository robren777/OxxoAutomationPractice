package EBS.cajasInteligentes.cho52071602;

import static org.junit.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class ATC_FT_001_ValidacionMigracionTabla extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_001_ValidacionMigracionTabla_test(HashMap<String, String> data) throws Exception {

		/*
		 * UTILERIAS
		 */

		utils.sql.SQLUtil EBSAVANTE = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
				GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);

		/*
		 * VARIABLES
		 */

		String validatabla = "select c.column_name,  case when data_type = 'CHAR'     then      data_type||'('||c.char_length||decode(char_used,'B',' BYTE','C',' CHAR',null)||')'    \r\n"
				+ "    							when data_type = 'VARCHAR'  then      data_type||'('||c.char_length||decode(char_used,'B',' BYTE','C',' CHAR',null)||')'    \r\n"
				+ "    							when data_type = 'VARCHAR2' then      data_type||'('||c.char_length||decode(char_used,'B',' BYTE','C',' CHAR',null)||')'    \r\n"
				+ "    							when data_type = 'NCHAR'    then      data_type||'('||c.char_length||decode(char_used,'B',' BYTE','C',' CHAR',null)||')'    \r\n"
				+ "    							when data_type = 'NUMBER' then      \r\n"
				+ "								    	case when c.data_precision is null and c.data_scale is null then          'NUMBER' \r\n"
				+ "								    	when c.data_precision is null and c.data_scale is not null then          'NUMBER(38,'||c.data_scale||')' \r\n"
				+ "									    else           data_type||'('||c.data_precision||','||c.data_SCALE||')'      end    \r\n"
				+ "    							when data_type = 'NVARCHAR' then      data_type||'('||c.char_length||decode(char_used,'B',' BYTE','C',' CHAR',null)||')'    \r\n"
				+ "    							when data_type = 'NVARCHAR2' then     data_type||'('||c.char_length||decode(char_used,'B',' BYTE','C',' CHAR',null)||')'    \r\n"
				+ "    							else      data_type    end data_type,\r\n"
				+ "      decode(nullable,'Y','Yes','No') nullable,  \r\n"
				+ "    c.DATA_DEFAULT,column_id,   com.comments	     \r\n" + "      from sys.all_tab_Columns c, \r\n"
				+ "           sys.all_col_comments com\r\n" + "      where c.owner      = 'XXFC' \r\n"
				+ "      and  c.table_name =  'XXFC_PA_PROVEEDORES'  \r\n"
				+ "      and c.table_name = com.table_name\r\n" + "      and c.owner = com.owner\r\n"
				+ "      and c.column_name = com.column_name\r\n"
				+ "      and com.ORIGIN_CON_ID = sys_context('USERENV', 'CON_ID')	              \r\n"
				+ "      order by column_id";

		/*
		 * PASOS DEL CASO DE PRUEBA, Matriz: cho52071602
		 */

		// PASO 1

		addStep("Ingresar en la Base de datos");

		// PASO 2

		addStep("Conectarse a la BD de AVEBQA");
		testCase.addTextEvidenceCurrentStep("Conexion a bd correcta: " + GlobalVariables.DB_HOST_AVEBQA + " usuario: "
				+ GlobalVariables.DB_USER_AVEBQA);
		testCase.addTextEvidenceCurrentStep("Conexion exitosa: " + EBSAVANTE.getConn());

		// PASO 3

		addStep("Validar la creacion de la tabla");
		System.out.println(validatabla);

		SQLResult validat = EBSAVANTE.executeQuery(validatabla);

		boolean validaB = validat.isEmpty();

		if (!validaB) {

			testCase.addQueryEvidenceCurrentStep(validat);
		}
		assertFalse("No se encuentra la tabla ", validaB);

		System.out.println(validaB);
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ValidacionMigracionTabla_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar la correcta migracion de la tabla 'XXFC_PA_PROVEEDORES'. Pre-requisitos: Acceso a la BD de AVEBQA";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Acceso a la BD de AVEBQA";
	}

	@Override
	public void beforeTest() {

	}

}

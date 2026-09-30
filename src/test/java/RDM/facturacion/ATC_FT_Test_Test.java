package RDM.facturacion;

import java.util.HashMap;

import org.testng.annotations.Test;

import modelo.BaseExecution;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_Test_Test extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATCFTTestTest(HashMap<String, String> data) throws Exception {

		/*
		 * UTILERIAS
		

		/*
		 * VARIABLES
		 */
		String rdmUrlString = data.get("rdm");
		System.out.println(rdmUrlString);

	

		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		addStep("navegar");
		u.get("https://www.msn.com/en-us/channel/topic/News/tp-Y_46b78bbb-31c4-4fc5-8a4a-858072348d06?ocid=msedgntphdr&cvid=7134cf51baf1469985e4183b9e543503");
		
		u.close();

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "ATCFTTestTest";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "ATCFTTestTest";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "ATCFTTestTest";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATCFTTestTest";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return "ATCFTTestTest";
	}

}

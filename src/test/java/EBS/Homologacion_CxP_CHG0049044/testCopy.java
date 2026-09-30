package EBS.Homologacion_CxP_CHG0049044;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.util.appletsMethods.MetodosSikuli;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class testCopy extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void testCopy_test(HashMap<String, String> data)
			throws Exception {
		SeleniumUtil u = new SeleniumUtil(new IETest());
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		
		
		String log = "FEMCO_MEXICO                                      Journal Import Execution Report                              Date: 12-DEC-24 17:10\r\n"
				+ "Concurrent Request ID: 105048810                                                                               Page:               1\r\n"
				+ "\r\n"
				+ "                                                   Total   Total   Total Total Unbalanced Total Unbalanced Total Flex Total Non-Flex\r\n"
				+ "Journal Entry Source Name       Group Id Status    Lines Batches Headers          Batches          Headers     Errors         Errors\r\n"
				+ "---------------------------- ----------- ------- ------- ------- ------- ---------------- ---------------- ---------- --------------\r\n"
				+ "ERP - Generacion Pasivos       105048809 Success       2       1       1                0                0          0              0\r\n"
				+ "                                                 ------- ------- ------- ---------------- ---------------- ---------- --------------\r\n"
				+ "*** TOTALS ***                                         2       1       1                0                0          0              0\r\n"
				+ " \r\n"
				+ "\r\n"
				+ "=======================================================   Batches Created   ========================================================\r\n"
				+ " \r\n"
				+ " \r\n"
				+ "                                                                               Total   Total               Total               Total\r\n"
				+ "Warning Batch Name                                               Period Name   Lines Headers    Accounted Debits   Accounted Credits\r\n"
				+ "------- -------------------------------------------------------- ----------- ------- ------- ------------------- -------------------\r\n"
				+ "        PF047-0850 ERP - Generacion Pasivos A 105048809 10504881 DIC-2024          2       1                 600                 600\r\n"
				+ " \r\n"
				+ "\r\n"
				+ "=================================================   Unbalanced Journal Entries**   =================================================\r\n"
				+ " \r\n"
				+ " \r\n"
				+ "Error                                                                            Total\r\n"
				+ "Code  Journal Entry Name                    Batch Name                           Lines Period Name     Total Debits    Total Credits\r\n"
				+ "----- ------------------------------------- ------------------------------------ ----- ----------- ---------------- ----------------\r\n"
				+ " \r\n"
				+ "\r\n"
				+ "=========================================================   Error Lines   ==========================================================\r\n"
				+ " \r\n"
				+ " \r\n"
				+ "                                             Accounting\r\n"
				+ "Error Code                     Source        Date        Currency      Entered Debit     Entered Credit Accounting Flexfield/CCID\r\n"
				+ "------------------------------ ------------- ----------- -------- ------------------ ------------------ ----------------------------\r\n"
				+ " \r\n"
				+ "\r\n"
				+ "==========================================================   Error Key   ===========================================================\r\n"
				+ " \r\n"
				+ "Period Error Codes\r\n"
				+ "------------------\r\n"
				+ "EP01    This date is not in any open or future enterable period.\r\n"
				+ "EP03    This date is not within any period in an open encumbrance year.\r\n"
				+ "EP04    This date is not a business day.\r\n"
				+ "EP05    There are no business days in this period.\r\n"
				+ "\r\n"
				+ "Unbalanced Journal Error Codes\r\n"
				+ "------------------------------\r\n"
				+ "WU01    Warning: This journal entry is unbalanced.  It is accepted because suspense posting is allowed in this ledger.\r\n"
				+ "EU02    This journal entry is unbalanced and suspense posting is not allowed in this ledger.\r\n"
				+ "EU03    This encumbrance journal entry is unbalanced and the Reserve for Encumbrance account is not defined.\r\n"
				+ "\r\n"
				+ "Flexfield Error Codes\r\n"
				+ "---------------------\r\n"
				+ "EF01    This Accounting Flexfield is inactive for this accounting date.\r\n"
				+ "EF02    Detail posting not allowed for this Accounting Flexfield.\r\n"
				+ "EF03    Disabled Accounting Flexfield.\r\n"
				+ "EF04    This is an invalid Accounting Flexfield.  Check your cross-validation rules and segment values.\r\n"
				+ "\f\r\n"
				+ "FEMCO_MEXICO                                      Journal Import Execution Report                              Date: 12-DEC-24 17:10\r\n"
				+ "Concurrent Request ID: 105048810                                                                               Page:               2\r\n"
				+ "                                                                                                                                    \r\n"
				+ "==========================================================   Error Key   ===========================================================\r\n"
				+ "\r\n"
				+ "Flexfield Error Codes\r\n"
				+ "---------------------\r\n"
				+ "EF05    There is no Accounting Flexfield with this Code Combination ID.\r\n"
				+ "EF06    The alternate account is invalid.\r\n"
				+ "WF01    An alternate account was used instead of the original account.\r\n"
				+ "WF02    A suspense account was used instead of the original account.\r\n"
				+ "\r\n"
				+ "Foreign Currency Error Codes\r\n"
				+ "----------------------------\r\n"
				+ "EC01    A conversion rate must be entered when using the User conversion rate type.\r\n"
				+ "EC02    There is no conversion date supplied.\r\n"
				+ "EC03    A conversion rate type or an accounted amount must be supplied when entering foreign currency journal lines.\r\n"
				+ "EC06    There is no conversion rate for this currency, conversion type, and conversion date.\r\n"
				+ "EC08    Invalid currency code.\r\n"
				+ "EC09    No currencies are enabled.\r\n"
				+ "EC10    Encumbrance journals cannot be created in a foreign currency.\r\n"
				+ "EC11    Invalid conversion rate type.\r\n"
				+ "EC12    The entered amount must equal the accounted amount in a ledger or STAT currency journal line.\r\n"
				+ "EC13    Amount is too large.\r\n"
				+ "ECW1    Warning: Converted amounts could not be validated because the conversion rate type is not specified.\r\n"
				+ "\r\n"
				+ "Budget Error Codes\r\n"
				+ "------------------\r\n"
				+ "EB01    A budget version is required for budget lines.\r\n"
				+ "EB02    Journals cannot be created for a frozen budget.\r\n"
				+ "EB03    The budget year is not open.\r\n"
				+ "EB04    This budget does not exist for this ledger.\r\n"
				+ "EB05    The encumbrance_type_id column must be null for budget journals.\r\n"
				+ "EB06    A period name is required for budget journals.\r\n"
				+ "EB07    This period name is not valid.  Check calendar for valid periods.\r\n"
				+ "EB08    Average journals cannot be created for budgets.\r\n"
				+ "EB09    Originating company information cannot be specified for budgets.\r\n"
				+ "\r\n"
				+ "Encumbrance Error Codes\r\n"
				+ "-----------------------\r\n"
				+ "EE01    An encumbrance type is required for encumbrance lines.\r\n"
				+ "EE02    Invalid or disabled encumbrance type.\r\n"
				+ "EE03    Encumbrance journals cannot be created in the STAT currency.\r\n"
				+ "EE04    The BUDGET_VERSION_ID column must be null for encumbrance lines.\r\n"
				+ "EE05    Average journals cannot be created for encumbrances.\r\n"
				+ "EE06    Originating company information cannot be specified for encumbrances.\r\n"
				+ "\r\n"
				+ "Reversal Error Codes\r\n"
				+ "--------------------\r\n"
				+ "ER01    A reversal period name must be provided.\r\n"
				+ "ER02    This reversal period name is invalid.  Check your calendar for valid periods.\r\n"
				+ "ER03    A reversal date must be provided\r\n"
				+ "ER04    This reversal date is not in a valid period.\r\n"
				+ "ER05    This reversal date is not in your database date format.\r\n"
				+ "ER06    Your reversal date must be the same as or after your effective date.\r\n"
				+ "ER07    This reversal date is not a business day.\r\n"
				+ "ER08    There are no business days in your reversal period.\r\n"
				+ "\f\r\n"
				+ "FEMCO_MEXICO                                      Journal Import Execution Report                              Date: 12-DEC-24 17:10\r\n"
				+ "Concurrent Request ID: 105048810                                                                               Page:               3\r\n"
				+ "                                                                                                                                    \r\n"
				+ "==========================================================   Error Key   ===========================================================\r\n"
				+ "\r\n"
				+ "Reversal Error Codes\r\n"
				+ "--------------------\r\n"
				+ "ER09    Default reversal information could not be determined.\r\n"
				+ "\r\n"
				+ "Descriptive Flexfield Error Codes\r\n"
				+ "---------------------------------\r\n"
				+ "ED01    The context and attribute values do not form a valid descriptive flexfield for Journals - Journal Entry Lines.\r\n"
				+ "ED02    The context and attribute values do not form a valid descriptive flexfield for Journals - Captured Information.\r\n"
				+ "ED03    The context and attribute values do not form a valid descriptive flexfield for Value Added Tax.\r\n"
				+ "\r\n"
				+ "Miscellaneous Error Codes\r\n"
				+ "-------------------------\r\n"
				+ "EM01    Invalid journal entry category.\r\n"
				+ "EM02    There are no journal entry categories defined.\r\n"
				+ "EM04    The value in the ACTUAL_FLAG must be \"A\" (actuals), \"B\" (budgets), or \"E\" (encumbrances).\r\n"
				+ "EM05    The encumbrance_type_id column must be null for actual journals.\r\n"
				+ "EM06    The budget_version_id column must be null for actual journals.\r\n"
				+ "EM07    A statistical amount belongs in the entered_dr(cr) column when entering a STAT currency journal line.\r\n"
				+ "EM09    There is no Transaction Code defined.\r\n"
				+ "EM10    Invalid Transaction Code.\r\n"
				+ "EM12    An Oracle error occurred when generating sequential numbering.\r\n"
				+ "EM13    The assigned sequence is inactive.\r\n"
				+ "EM14    There is a sequential numbering setup error resulting from a missing grant or synonym.\r\n"
				+ "EM17    Sequential numbering is always used and there is no assignment for this ledger and journal entry category.\r\n"
				+ "EM18    Manual document sequences cannot be used with Journal Import.\r\n"
				+ "EM19    Value Added Tax data is only valid in conjunction with actual journals.\r\n"
				+ "EM24    Average journals can only be imported into consolidation ledgers.\r\n"
				+ "EM25    Invalid average journal flag.  Valid values are \"Y\", \"N\", and null.\r\n"
				+ "EM26    Invalid originating company.\r\n"
				+ "EM27    Originating company information can only be specified when intercompany balancing is enabled.\r\n"
				+ "EM29    You do not have access to this ledger and account combination.\r\n"
				+ "EM30    This balancing segment value is not valid for this ledger.\r\n"
				+ "EM31    This management segment value is not valid for this ledger.\r\n"
				+ " \r\n"
				+ "** Batches listed under \"Unbalanced Batches**\" have not been imported.\r\n"
				+ " \r\n"
				+ " \r\n"
				+ " \r\n"
				+ " \r\n"
				+ "                                                    ***** End of Report *****\r\n"
				+ "\r\n"
				+ "-----------------Award Number Validation Process for DATA ACT ----------------------------------\r\n"
				+ "";
		
		addStep("paso prueba");
		String batchName;
		String pie;

		// Extraer Concurrent Request ID
		String currentRequestId = p.extractConcurrentRequestId(log);
		// Extraer Batch Name
		batchName = p.extractBatchName(log, "DIC");
		// Extraer PIE
		System.out.println("batchName: "+batchName);
		System.out.println("currentRequestId: "+currentRequestId);
		pie = p.extractPIE(batchName);
		//System.out.println("log: "+log);
		System.out.println("batchName: "+batchName);
		System.out.println("currentRequestId: "+currentRequestId);
		System.out.println("pie: "+pie);
		
		//id_documento_pago
		String log2 = "ID_DOCUMENTO_PAGO|DOCUMENTO_PAGO|ORG_ID|VENDOR_ID|VENDOR_SITE_ID|FECHA_REAL_PAGO|MONTO_TOTAL_PAGO|METODO_PAGO|ESTADO|TIPO_PAGO|NOMBRE_LOTE_PAGO|CREATION_DATE|CREATED_BY|LAST_UPDATE_DATE|LAST_UPDATED_BY|WM_STATUS|WM_MENSAJE|WM_FECHA_ENVIO|WM_RUN_ID|SYS_ORIGEN|WM_MANDANTE|\r\n"
				+ "17698626|1000141189|83|22537|10953|19-DEC-24|116|03|NEGOTIABLE|EFT|Quick Payment: ID=17698626|19-DEC-24|EBS|19-DEC-24|EBS|L||||R1224|800\r\n"
				+ "ID_DOCUMENTO_PAGO|ORG_ID|INVOICE_ID|INVOICE_NUM|AMOUNT|AMOUNT_REMANENTE|CREATION_DATE|CREATED_BY|LAST_UPDATE_DATE|LAST_UPDATED_BY|WM_STATUS|WM_MENSAJE|WM_FECHA_ENVIO|WM_RUN_ID|SYS_ORIGEN|WM_MANDANTE\r\n"
				+ "17698626|83|21773298|JE39|116|0|19-DEC-24|EBS|19-DEC-24|EBS|L||||R1224|800";
		String id_documento_pago = p.extractIdDocumentoPago(log2);
		System.out.println("id_documento_pago: "+id_documento_pago);
		
		u.getDriver().quit();
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

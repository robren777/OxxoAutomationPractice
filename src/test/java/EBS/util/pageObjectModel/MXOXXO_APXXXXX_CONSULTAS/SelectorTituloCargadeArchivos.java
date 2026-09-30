package EBS.util.pageObjectModel.MXOXXO_APXXXXX_CONSULTAS;

import org.openqa.selenium.By;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class SelectorTituloCargadeArchivos {

	SeleniumUtil u;
	TestCase testCase;

	public SelectorTituloCargadeArchivos(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}

	By valor = By.xpath("//h1[@type='OraHeader']");

	public String getText() {
		u.highLight(valor);
		return u.getText(valor);
	}

}

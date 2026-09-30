package WEB.updateNomina.om;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modelo.BaseExecution;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class EBSMex {
    SikuliUtil sk;
    SeleniumUtil u;
    Pattern pathimg;
    ArrayList<String> ListImage;
    HashMap<String, Pattern> ImgPattern;
    ArrayList<Pattern> patterns;
    File Fol;
    TestCase testCase;

    //private final TestCase testCase;

    protected static final Logger logger = LoggerFactory.getLogger(BaseExecution.class);

    public EBSMex(SikuliUtil sk, SeleniumUtil u, TestCase testCase, Pattern pathimg, ArrayList<String> ListImage,
                        HashMap<String, Pattern> ImgPattern, ArrayList<Pattern> patterns, File Fol) {

        this.u = u;
        this.sk = sk;
        this.pathimg = pathimg;
        this.ListImage = ListImage;
        this.ImgPattern = ImgPattern;
        this.patterns = patterns;
        this.Fol = Fol;
        this.testCase = testCase;
    }

    public HashMap<String, Pattern> getPatternsImg(File Fol) {
        File[] files = Fol.listFiles();
        for (File s : files) {
            patterns.add(new Pattern(s.getAbsolutePath()).similar((float) .95));
        }
        for (Pattern p : patterns) {
            String fname = p.getFilename().toString();
            fname = fname.replace("\\", "-");
            fname = fname.replace(".png", "");
            fname = fname.replace(".PNG", "");
            String[] afname = fname.split("-");
            ImgPattern.put(afname[afname.length - 1], p);
        }

        return ImgPattern;
    }

	/**
	 * Valida si existe el patron a buscar
	 *
	 * @param target Imagen a buscar
	 * @return True o False utilizado para hacer un assert
	 * @throws FindFailed
	 * @author JesusH@hexaware.com
	 */
	public Boolean validatePattern(String target) throws FindFailed {
		if (sk.screen.exists(ImgPattern.get(target)) != null) {
			return true;
		} else {
			return false;
		}

	}


    // se ejecuta archivo
    public void ejecutararchivofrmservlet(File Folder, String compania, String estadoFinanciero, String crSuperior, String  fecha_VI, String prefijo, String descripcion)throws FindFailed {
        ImgPattern = this.getPatternsImg(Folder);
        if(validatePattern("navBtnConservarEdge")){
        	sk.screen.wait(ImgPattern.get("navBtnConservarEdge"), 15).click();
		}else if(validatePattern("navBtnKeepEdge")){
			    sk.screen.wait(ImgPattern.get("navBtnKeepEdge"), 15).click();
		  }
        
        if(validatePattern("navAbrirArchivoJnlpEdge")){
        	sk.screen.wait(ImgPattern.get("navAbrirArchivoJnlpEdge"), 15).click();
		}else if(validatePattern("navOpnFileJnlpEdge")){
			    sk.screen.wait(ImgPattern.get("navOpnFileJnlpEdge"), 15).click();
		  }
        
        if(validatePattern("aceptoLosRiesgosCheckbox")){
        	sk.screen.wait(ImgPattern.get("aceptoLosRiesgosCheckbox"), 15).click();
		}else if(validatePattern("checkBoxSecurityWarning2")){
			sk.screen.wait(ImgPattern.get("checkBoxSecurityWarning2"), 15).click();
		  }
        
        if(validatePattern("ejecutarAplicacion")){
        	sk.screen.wait(ImgPattern.get("ejecutarAplicacion"), 15).click();
		}else if(validatePattern("Run")){
			sk.screen.wait(ImgPattern.get("Run"), 15).click();
		  }
        
        sk.screen.wait(ImgPattern.get("lblOrigenEbsMex"), 35).highlight();
        
        sk.screen.type(compania);
        sk.screen.type(Key.TAB);
        sk.screen.type(estadoFinanciero);
        sk.screen.type(Key.TAB);
        sk.screen.type(crSuperior);
        sk.screen.type(Key.TAB);
        sk.screen.type(Key.TAB);
        sk.screen.type(fecha_VI);
        sk.screen.type(Key.TAB);
        sk.screen.type(Key.TAB);
        sk.screen.type(prefijo);
        sk.screen.type(Key.TAB);
        sk.screen.type(Key.TAB);
        sk.screen.type(descripcion);	
        sk.screen.type(Key.TAB);
        sk.screen.type(Key.ENTER);

        sk.screen.wait(ImgPattern.get("btngenerarCr"), 15).click();
        testCase.addScreenShotCurrentStep("datos de la tienda");
        u.hardWait(5);;
        sk.screen.wait(ImgPattern.get("btnGenerarCrsAceptarMex"), 15).click();
        sk.screen.wait(ImgPattern.get("btnAceptarSeCreoCr"), 15).click();
        sk.screen.wait(ImgPattern.get("cerrarventana"), 15).click();
        sk.screen.wait(ImgPattern.get("aceptarcerrar"), 15).click();
    }
}

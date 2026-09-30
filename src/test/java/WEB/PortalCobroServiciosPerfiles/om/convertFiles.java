package WEB.PortalCobroServiciosPerfiles.om;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class convertFiles{

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	
	/*
	 * Localizadores Menu portal cobro servicios
	 ********************************************************************************************/
	
	
	/*
	 * Constructor
	 ********************************************************************************************/
	public convertFiles(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}
	
	/*
	 * Metodos Menu Integridad de datos
	 ********************************************************************************************/
	
	/**
	 * Metodo para obtener la fecha actual en un formato predeterminado
	 * @author Herbe Cauich
	 * @param formato
	 * @return N/A
	 */
	
	  public String obtenerFechaActual(String formato) {

	        SimpleDateFormat formatFecha = new SimpleDateFormat(formato);
	        //Date date = Calendar.getInstance().getTime();
	        //String fecha = formatFecha.format(date);

	        LocalDateTime datetime = LocalDateTime.now();

	        //se resta un mes
	        //datetime = datetime.minusMonths(1);

	        String fecha = DateTimeFormatter.ofPattern(formato).format(datetime); 
	        System.out.println(fecha);
	        

	        return fecha;
	    }
	
	  /**
		 * Metodo para dar convertir un archivo CSV a Excel
		 * @author Herbe Cauich
		 * @param formato
		 * @return N/A
		 */
	
    public String csvToXLSX(String formato) {
    
		SimpleDateFormat formatFecha = new SimpleDateFormat(formato);
        LocalDateTime datetime = LocalDateTime.now();

        String nombreArchivo = "CobroServicios_";
        String fecha = DateTimeFormatter.ofPattern(formato).format(datetime); 
        System.out.println(fecha);

        
		//me.explicitWaitToBeInvisible(10);
	    //String pathYourSystem = System.getProperty("user.home") + "\\Downloads\\"+ nombreArchivo + fecha +".csv";
	   // File fichero = new File(pathYourSystem);
	   // System.out.println(pathYourSystem);
    

    	String pathYourSystemxx = System.getProperty("user.home") + "\\Downloads\\"+ nombreArchivo + fecha +".csv";
    	String pathYourSystemxx2 = System.getProperty("user.home") + "\\Downloads\\caffeine\\"+ nombreArchivo + fecha +".xlsx";
        try {
            String csvFileAddress = pathYourSystemxx; //csv file address
            String xlsxFileAddress = pathYourSystemxx2; //xlsx file address
            XSSFWorkbook workBook = new XSSFWorkbook();
            XSSFSheet sheet = workBook.createSheet("sheet1");
            String currentLine=null;
            int RowNum=0;
            BufferedReader br = new BufferedReader(new FileReader(csvFileAddress));
            while ((currentLine = br.readLine()) != null) {
                String str[] = currentLine.split(",");
                RowNum++;
                XSSFRow currentRow=sheet.createRow(RowNum);
                for(int i=0;i<str.length;i++){
                    currentRow.createCell(i).setCellValue(str[i]);
                }
            }

            FileOutputStream fileOutputStream =  new FileOutputStream(xlsxFileAddress);
            workBook.write(fileOutputStream);
            fileOutputStream.close();
            System.out.println("Done");
        } catch (Exception ex) {
            System.out.println(ex.getMessage()+"Exception in try");
        }
        
        return fecha;
        
    }
    /**
	 * Metodo para leer un archivo CSV
	 * @author Herbe Cauich
	 * @param 
	 * @return 
	 */
    
    public static void  leerCSV() throws IOException {
		Boolean correct = false;
	    String folio = "Folio";
		//me.explicitWaitToBeInvisible(10);
	    String pathYourSystem = System.getProperty("user.home") + "\\Downloads\\CobroServicios_03-01-2023.csv";
	    String line = "";
	    try{
	    BufferedReader br = new BufferedReader(new FileReader(pathYourSystem));
	    while((line = br.readLine()) != null){
	    	System.out.println(line);
	    	
	    	break;
	   
	    }
	    }catch (FileNotFoundException e) {
	    	e.printStackTrace(); 
	    }catch (IOException e ) {
	    	e.printStackTrace(); 
	    }	    
	    
    }
	
	
    
}
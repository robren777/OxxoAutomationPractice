package WorkFlows.util.PlanogramasNexxo;

import utils.FTPUtil;

public class FtpTest {

	   public static void main(String[] args) {
	        // TODO Auto-generated method stub
	        FTPUtil ftp = new FTPUtil("5.57.226.207",21, "dummysite","karlita37");
	    //    FTPUtil ftp = new FTPUtil("10.182.92.13",22, "posuser","posuser");
	        System.out.println(" Conexion exitosa ? :" +ftp.isConnected());
	        try {
	          ftp.fileUpload("/web/", "C:\\Users\\alicia.ceja\\Downloads\\test.java", "test.txt");
	         
	        }
	        catch(Exception ioexception) {
	            System.err.println("No se pudo subir el achivo");
	            System.out.println(ioexception);
	            
	        }
	    }
	   
	   
	   
	
}

package WM.EvolucionRetiroSinTarjeta.HU07;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import azure.annotation.AzureTestInfo;

@AzureTestInfo(

		//areaPath = "Test-POCFrameworkOxxo\\I22032-Evolución Retiros sin Tarjeta",

		areaPath = "asd",
		iterationPath = "Test-POCFrameworkOxxo\\I22032-Evolución Retiros sin Tarjeta\\Sprint 6",
		tags = "TestTag1;TestTag2",
		userStoryId = 17086

)
 
public class PruebaAUT {
 
    @BeforeClass

    public void beforeClass() throws Exception {

    	String className = "EvolucionRetiroSinTarjeta.HU08.ATC_FT_004_Validar_en_la_base_de_datos_el_registro_de_TPC";

    	System.out.println("Loaded class: " + className);

    	Class<?> clazz = Class.forName(className);

    	System.out.println("Class loaded from: " + clazz.getProtectionDomain().getCodeSource().getLocation());

        // Check if the AzureTestInfo annotation is present

        if (clazz.isAnnotationPresent(AzureTestInfo.class)) {

            // Get the annotation

            AzureTestInfo info = clazz.getAnnotation(AzureTestInfo.class);

            System.out.println("Area Path: " + info.areaPath());

            System.out.println("Iteration Path: " + info.iterationPath());

            System.out.println("Tags: " + info.tags());

            System.out.println("User Story ID: " + info.userStoryId());

        }

    }
 
    @Test

    public void testMethod() {

        System.out.println("Running testMethod...");

    }

}

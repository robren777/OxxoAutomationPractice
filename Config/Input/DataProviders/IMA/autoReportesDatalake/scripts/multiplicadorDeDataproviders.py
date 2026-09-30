import pandas as pd

# Cargar el archivo Excel con pandas
df = pd.read_excel('ATC_FT_001_PruebaColumnas.xlsx')  # Cambia 'tu_archivo.xlsx' por la ruta de tu archivo

# Iterar sobre cada fila del DataFrame
for index, row in df.iterrows():
    # Crear un DataFrame solo con el header y la fila actual
    fila_df = pd.DataFrame([row], columns=df.columns)
    
    # Obtener el valor de la primera columna (asumiendo que la primera columna es el nombre del archivo)
    nombre_archivo = str(row[df.columns[0]])  # Utilizamos el valor de la primera columna como nombre
    
    # Asegurarnos de que el nombre del archivo sea seguro (evitar caracteres no permitidos)
    nombre_archivo = nombre_archivo.replace("/", "_").replace("\\", "_").replace(" ", "_")  # Reemplazamos espacios y caracteres especiales
    
    # Crear el nombre del archivo
    filename = f'{nombre_archivo}.xlsx'
    
    # Guardar el archivo con el header y la fila
    fila_df.to_excel(filename, index=False)

print("Archivos creados exitosamente.")
# UserWsBCIIntegration
Proyecto Creación de Usuarios

Changelog: 
 - 07/12/2023: Primera revisión del proyecto Gestión y Creación de Usuarios

GUÍA DE USUARIO:

Para ejecutar el API se generó un archivo .jar en el que está embebida la publicación en la ruta http://localhost:8080 

En primer lugar se debe descargar el archivo UserWsBCCIntegration.zip del Tag TEST en Release: https://github.com/danielbby/UserWsBCIIntegration/releases/tag/Test
El archivo comprimido contiene los archivos:
  - application.properties
  - UserWsBCIIntegration-1.0.0-100000.jar

El archivo _application.properties_ es opcional si se quiere establecer propiedades ya definidas en el aplicativo.

Para ejecutar *UserWsBCIIntegration-1.0.0-100000.jar* se debe ubicar en un cmd dentro de la ruta donde se descomprimió y ejecutar el siguiente comando: _java -jar UserWsBCIIntegration-1.0.0-100000.jar --spring.config.location=file:./application.properties_ Con este comando se toma el archivo de propiedades ubicado junto al archivo .jar

Si se ejecuta el comando: _java -jar UserWsBCIIntegration-1.0.0-100000.jar_ tomará el archivo de propiedades embebido en el paquete jar.

Una vez ejecutado el comando el aplicativo iniciará y se registrará información en la consola y en el archivo .log 

EJECUCIÓN DE MÉTODOS

**Método POST**

El método post está destinado para registrar un usuario en base de datos, recibiendo un request en tipo json.

Endpoint: localhost:8080/BCI/User/

Request:
{
 "name": "Daniel Villoria",
 "email": "danielo@villoria.com",
 "password": "Seguridad28!",
    "phones": [    
        {
            "number": "2664747",
            "citycode": "414",
            "contrycode": "58"
        },
        {
            "number": "6700304",
            "citycode": "304",
            "contrycode": "57"
        }
    ]
}

**Método GET**

Consta de dos endpoint para listar los usuarios registrados en la base de datos. Enviando como parámetro el UserID generado por la aplicación al momento de crear un usuario.

Endpoint GetById: localhost:8080/BCI/User/GetById

Request: {"userId":"29b0fd2a-5add-4a41-b4f5-9323ed42cba2"} 

Endpoint GetAll: localhost:8080/BCI/User/GetAll

No se envían parámetros, ya que este método lista todos los usuarios registrados en la base de datos.

**Método DELETE**

Contiene un endpoint para eliminar un usuario registrado en base de datos. Se envía como parámetro el userId generado por la aplicación 

Endpoint: localhost:8080/BCI/User/DeleteById

Request: {"userId":"7c92031b-9d93-4a51-b571-58d4f732b943"}

**Método PUT**

Contiene un endpoint para modificar un usuario, se debe enviar como parámetro el userId generado por la aplicación, además de los datos a modificar.

Endpoint: localhost:8080/BCI/User/UpdateUser

Request: {
 "userId":"8e708fed-591d-4523-b6c3-324e244c4e51",
 "name": "Daniel O Villoria",
 "isactive":"false",
 "email": "danielovi@villoria.com",
    "phones": [    
        {
            "number": "2664747",
            "citycode": "414",
            "contrycode": "58"
        }
    ]
}





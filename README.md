ferreteria-jsp
==============

Once again a ferreteria, but this time made with JSP

Requisitos

    JDK 1.7 or higher
    Apache Tomcat 7
    Hibernate 4.x
    jBCrypt 0.3
    MySQL 5.x*

* Puedes reemplazar MySQL por cualquier otro motor compatible con Hibernate.
Instalación

Aún no completada, pero los pasos por el momento son los siguientes.

    Ejecuta el archivo ./Ferreteria/db/install.sql sobre MySQL
    Añade las dependencias al proyecto:
        MySQL driver
        Hibernate
        Persistence JPA 2.0
        jBCrypt
    En el directorio ./Ferreteria/src/java/, copia el archivo hibernate.cfg.xml.template y pegalo en la misma carpeta con el nombre hibernate.cfg.xml
    Luego modifica el nuevo archivo hibernate.cfg.xml con tus datos
    El servlet index cuenta con el método install() para generar un usuario
    Otros datos, como productos, deberán ser precargados en la base de datos ya que exceden el propósito de este sistema

Autores

Luca Giordano y Lucio Martínez.
Licencia

Copyright (C) 2014 Luca Giordano, Lucio Martínez.

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program. If not, see http://www.gnu.org/licenses/.
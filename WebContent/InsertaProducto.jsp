<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1 style="text-align: center;">Insertar Registro</h1>
	<form name="form1" action="ControladorProductos" method="GET" >
	
	<input type="hidden" name="instruccion" value="insertarBBDD"/>
	<table>
		<tr>
			<td>
				<label for="CArt">Código Artículo</label>
			</td>
			<td>
				<input type="text" name="CArt" id="CArt"/>
			</td>
		</tr>
		<tr>
			<td>
				<label for="seccion">Sección</label>
			</td>
			<td>
				<input type="text" name="seccion" id="seccion"/>
			</td>
		</tr>
		<tr>
			<td>
				<label for="NArt">Nombre Artículo</label>
			</td>
			<td>
				<input type="text" name="NArt" id="NArt"/>
			</td>
		</tr>
		<tr>
			<td>
				<label for="fecha">Fecha</label>
			</td>
			<td>
				<input type="text" name="fecha" id="fecha"/>
			</td>
		</tr>
		<tr>
			<td>
				<label for="precio">Precio</label>
			</td>
			<td>
				<input type="text" name="precio" id="precio"/>
			</td>
		</tr>
		<tr>
			<td>
				<label for="importado">Importado</label>
			</td>
			<td>
				<input type="text" name="importado" id="importado"/>
			</td>
		</tr>
		<tr>
			<td>
				<label for="POrig">País de Origen</label>
			</td>
			<td>
				<input type="text" name="POrig" id="POrig"/>
			</td>
		</tr>
		<tr>
			<td><input type="submit" name="envio" id="envio" value="Enviar"/>
			<td><input type="reset" name="borrar" id="borrar" value="Restablecer"/>
		</tr>	
	</table>

</body>
</html>
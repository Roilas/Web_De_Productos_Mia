<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

HAS LLEGADO A ""LISTAR.JSP"
<BR/>
<table>
		<tr>
			<th>Código Artículo</th>
			<th>Sección</th>
			<th>Nombre Artículo</th>
			<th>Fecha</th>
			<th>Precio</th>
			<th>Importado</th>
			<th>País de Orígen</th>
			<th>Acción</th>
		</tr>
		
		<c:forEach var="prod" items="${LISTAPRODUCTOS}">
		
			<c:url var="linkTemp" value="ControladorProductos">
				<c:param name="instruccion" value="cargar"></c:param>
				<c:param name="CArticulo" value="${prod.cArt}"></c:param>
			</c:url>

			<c:url var="linkTempEliminar" value="ControladorProductos">
				<c:param name="instruccion" value="eliminar"></c:param>
				<c:param name="CArticulo" value="${prod.cArt}"></c:param>
			</c:url>
					
			<tr>
			
				<td>${prod.cArt}</td>
				<td>${prod.seccion}</td>
				<td>${prod.nArt}</td>
				<td>${prod.fecha}</td>
				<td>${prod.precio}</td>
				<td>${prod.importado}</td>
				<td>${prod.pOrig}</td>
				
<%-- 				<td><a href="${linkTemp}">Actualizar</a>&nbsp;<a href="${linkTempEliminar}">Eliminar</a></td>	--%>		
							
			</tr>
			 
		</c:forEach>
	</table>
</body>
</html>
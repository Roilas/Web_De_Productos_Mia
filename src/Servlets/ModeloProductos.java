package Servlets;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ModeloProductos {

	
	// Creamos una variable DataSource, la estableceremos en el constructor para que al crear un modelo estemos
	//obligados a construirla
	private DataSource origenDatos;
	

	public ModeloProductos(DataSource origenDatos) {
		this.origenDatos = origenDatos;
	}
	
	// Este es el metodo que se usara para conectarse a la base, coger los datos y entregarlos
	public List<Producto> ObtenProductosDeLaBase()throws Exception{
//		System.out.println("Excepcion: " + e.getMessage());
		List<Producto> ProductosDeLaBase = new ArrayList<Producto>();
		
		//Intentamos hacer la conexion
		try {
			
			// Aqui cogemos los datos pasados desde el Servlet general que es el context.xml y introduce los
			//datos de la conexion
			Connection miConexion =  origenDatos.getConnection();
			String ComandoIntroducido = "SELECT * FROM PRODUCTOS";
			Statement MiDeclaracion = miConexion.createStatement();
			ResultSet miResultSet = MiDeclaracion.executeQuery(ComandoIntroducido);
			
			// Aqui creamos variables de texto que recogen la info de la tabla
			while (miResultSet.next()) {
				String cArt = miResultSet.getString("CODIGOARTICULO");
				String seccion = miResultSet.getString("SECCION");
				String nArt = miResultSet.getString("NOMBREARTICULO");
				double precio = miResultSet.getDouble("PRECIO");
				
				//System.out.println("precio: " + precio);
				
//				String fecha = miResultSet.getString("FE");
//				String fecha = miResultSet.getString("FECHA");
				String fecha = miResultSet.getString(5);
				
				//System.out.println("fecha: " + fecha);
				
				String importado = miResultSet.getString("IMPORTADO");
				String p_orig = miResultSet.getString("PAISDEORIGEN");
				
				// Esas variables pasan a meterse en un "Producto" "Temporal" y luego este se añade a la lista
				Producto TemporalDeEsteProceso = new Producto(cArt, seccion, nArt, precio, fecha, importado, p_orig);
				ProductosDeLaBase.add(TemporalDeEsteProceso);
			}
			
		}catch (Exception e){
			e.printStackTrace();
			throw new Exception("Error en la BD al LISTAR: " + e.getMessage());
		}
		return ProductosDeLaBase;
		
		
		
		
		
		
	}
	
	
	
	
	
}

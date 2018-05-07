package Servlets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.naming.java.javaURLContextFactory;

public class ModeloProductos {

	
	// Creamos una variable DataSource, la estableceremos en el constructor para que al crear un modelo estemos
	//obligados a construirla
	private DataSource origenDatos;
	

	public ModeloProductos(DataSource origenDatos) {
		this.origenDatos = origenDatos;
	}
	
	// Este es el metodo que se usara para conectarse a la base, coger los datos y entregarlos
	
	
	public List<Producto> ObtenProductosDeLaBase()throws Exception{
		
		List<Producto> ProductosDeLaBase = new ArrayList<Producto>();
		
		//Intentamos hacer la conexion
		try {
			
			// Aqui cogemos los datos pasados desde el Servlet general que es el context.xml y introduce los
			//datos de la conexion
			
			Connection miConexion =  origenDatos.getConnection();
			String ComandoIntroducido = "SELECT * FROM PRODUCTOS";
			Statement MiDeclaracion = miConexion.createStatement();
			ResultSet miResultSet = MiDeclaracion.executeQuery(ComandoIntroducido);
			
		
			
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
	
	public void agregarElNuevoProducto(Producto nuevoProducto) throws Exception {

		try{
			Connection miConexion = origenDatos.getConnection();
			String sql = "INSERT INTO PRODUCTOS (CODIGOARTICULO, SECCION, NOMBREARTICULO, PRECIO, FECHA, IMPORTADO, PAISDEORIGEN) " + 
					 "VALUES(?,?,?,?,?,?,?)";
			PreparedStatement miStatement = miConexion.prepareStatement(sql);
			
			miStatement.setString(1, nuevoProducto.getcArt());
			miStatement.setString(2, nuevoProducto.getSeccion());
			miStatement.setString(3, nuevoProducto.getnArt());
			miStatement.setDouble(4, nuevoProducto.getPrecio());
			
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
			Date dateUtil = formato.parse(nuevoProducto.getFecha()); 
			java.sql.Date dateSql = new java.sql.Date(dateUtil.getTime());
			
			System.out.println("dateSql:" + dateSql);
			System.out.println("dateUtil:" + dateUtil);
			
			miStatement.setDate(5, dateSql);
			miStatement.setString(6, nuevoProducto.getImportado());
			miStatement.setString(7, nuevoProducto.getpOrig());
			
			System.out.println("execute: " + miStatement.execute());
//			System.out.println("executeUpdate: " + miStatement.executeUpdate());
			
		} catch (SQLException e) {
//			e.printStackTrace();
			throw new Exception("Error en la BD al INSERTAR: " + e.getMessage());
		}

	}
	
	
	
	
	
	
}

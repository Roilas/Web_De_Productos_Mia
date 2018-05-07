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

	// Creamos una variable DataSource, la estableceremos en el constructor para que
	// al crear un modelo estemos
	// obligados a construirla
	private DataSource origenDatos;

	public ModeloProductos(DataSource origenDatos) {
		this.origenDatos = origenDatos;
	}

	// Este es el metodo que se usara para conectarse a la base, coger los datos y
	// entregarlos

	/*****************************************************************************************/

	public List<Producto> ObtenProductosDeLaBase() throws Exception {

		List<Producto> ProductosDeLaBase = new ArrayList<Producto>();

		// Intentamos hacer la conexion
		try {

			// Aqui cogemos los datos pasados desde el Servlet general que es el context.xml
			// y introduce los
			// datos de la conexion

			Connection miConexion = origenDatos.getConnection();
			String ComandoIntroducido = "SELECT * FROM PRODUCTOS";
			Statement MiDeclaracion = miConexion.createStatement();
			ResultSet miResultSet = MiDeclaracion.executeQuery(ComandoIntroducido);

			while (miResultSet.next()) {
				String cArt = miResultSet.getString("CODIGOARTICULO");
				String seccion = miResultSet.getString("SECCION");
				String nArt = miResultSet.getString("NOMBREARTICULO");
				double precio = miResultSet.getDouble("PRECIO");

				// System.out.println("precio: " + precio);

				// String fecha = miResultSet.getString("FE");
				// String fecha = miResultSet.getString("FECHA");
				String fecha = miResultSet.getString(5);

				// System.out.println("fecha: " + fecha);

				String importado = miResultSet.getString("IMPORTADO");
				String p_orig = miResultSet.getString("PAISDEORIGEN");

				// Esas variables pasan a meterse en un "Producto" "Temporal" y luego este se
				// añade a la lista
				Producto TemporalDeEsteProceso = new Producto(cArt, seccion, nArt, precio, fecha, importado, p_orig);
				ProductosDeLaBase.add(TemporalDeEsteProceso);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error en la BD al LISTAR: " + e.getMessage());
		}
		return ProductosDeLaBase;

	}

	/*******************************************************/
	public Producto ObtenUnProducto(String CodigoArticuloIntroducido) throws Exception {

		// Creamos un producto vacio que rellenaremos
		Producto ElProductoDevuelto = null;
		try {
			Connection miConexion = origenDatos.getConnection();

			// Preparamos la intruccion a la BBDD con un hueco libre
			String sql = "SELECT * FROM PRODUCTOS WHERE CODIGOARTICULO = ?";
			PreparedStatement miStatement = miConexion.prepareStatement(sql);

			// y Luego ese hueco lo concretamos con el codigo
			miStatement.setString(1, CodigoArticuloIntroducido);
			ResultSet miResultSet = miStatement.executeQuery();
			if (miResultSet.next()) {
				String c_art = miResultSet.getString("CODIGOARTICULO");
				String seccion = miResultSet.getString("SECCION");
				String n_art = miResultSet.getString("NOMBREARTICULO");
				double precio = miResultSet.getDouble("PRECIO");
				String fecha = miResultSet.getString("FECHA");
				String importado = miResultSet.getString("IMPORTADO");
				String p_orig = miResultSet.getString("PAISDEORIGEN");

				ElProductoDevuelto = new Producto(c_art, seccion, n_art, precio, fecha, importado, p_orig);
				return ElProductoDevuelto;
			} else {
				throw new Exception(
						"No hemos encontrado el producto con codigo de articulo: " + CodigoArticuloIntroducido);
			}

		} catch (Exception e) {
			// e.printStackTrace();
			throw new Exception(
					"Error en la BD al BUSCAR el PRODUCTO: " + CodigoArticuloIntroducido + " - " + e.getMessage());
		}
	}

	/*********************************************************/

	public void actualizarProducto(Producto productoActualizado) throws Exception {

		// try(Connection miConexion = origenDatos.getConnection()) {
		try {
			Connection miConexion = origenDatos.getConnection();
			String sql = "UPDATE PRODUCTOS SET SECCION = ?, NOMBREARTICULO = ?,"
					+ " PRECIO = ?, FECHA = ?, IMPORTADO = ?, PAISDEORIGEN = ? " + "WHERE CODIGOARTICULO = ?";

			PreparedStatement miStatement = miConexion.prepareStatement(sql);
			miStatement.setString(1, productoActualizado.getSeccion());
			miStatement.setString(2, productoActualizado.getnArt());
			miStatement.setDouble(3, productoActualizado.getPrecio());
			miStatement.setString(4, productoActualizado.getFecha());
			miStatement.setString(5, productoActualizado.getImportado());
			miStatement.setString(6, productoActualizado.getpOrig());
			miStatement.setString(7, productoActualizado.getcArt());

			miStatement.executeUpdate();

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new Exception("Error en la BD al MODIFICAR: " + e.getMessage());
		}

	}

/*************************************************************************************/

	public void agregarElNuevoProducto(Producto nuevoProducto) throws Exception {

		try {
			Connection miConexion = origenDatos.getConnection();
			String sql = "INSERT INTO PRODUCTOS (CODIGOARTICULO, SECCION, NOMBREARTICULO, PRECIO, FECHA, IMPORTADO, PAISDEORIGEN) "
					+ "VALUES(?,?,?,?,?,?,?)";
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
			// System.out.println("executeUpdate: " + miStatement.executeUpdate());

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new Exception("Error en la BD al INSERTAR: " + e.getMessage());
		}

	}


	
	
	/****************************************************************************/
	public void EliminarProducto(String codigoArticulo) throws Exception{
		try{
			Connection miConexion = origenDatos.getConnection();
			String sql = "DELETE FROM PRODUCTOS WHERE CODIGOARTICULO = ?";

			PreparedStatement miStatement = miConexion.prepareStatement(sql);
			miStatement.setString(1, codigoArticulo);

			miStatement.executeUpdate();
			
		} catch (Exception e) {
//			e.printStackTrace();
			throw new Exception("Error en la BD al ELIMINAR: " + e.getMessage());
		}
		
	}


}

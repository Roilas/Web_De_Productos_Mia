package Servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/ControladorProductos")
public class ControladorProductos extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// En el archivo "context.xml" se establecen todos los datos para acceder a la
	// base de datos y aqui
	// se carga en un objeto llamado "miPool"
	@Resource(name = "jdbc/Productos")
	private DataSource miPool;

	// "ModeloProductos" Sera la clase encargada de gestionar la base de datos.
	// Creada a parte.
	private ModeloProductos MiModelo;

	// MANERA 1 DE PASAR MIPOOL
	// Esto hace solo que cuando se cree el "ControladorProductos" que es este mismo
	// Servlet y inicia al principio
	// estableciendo en "ModeloProductos" el DataSource que llamado "miPool" y tiene
	// la info de mi base de datos
	// public ControladorProductos() {
	// modeloProductos = new ModeloProductos(miPool);
	// }

	// En este metodo inicia el Servlet
	// Click derecho->Source->Implements override methods->Generic server-> init();
	@Override
	public void init() throws ServletException {
		// super.init();
		System.out.println("Comenzando");
		// MANERA 2 DE PASAR MI POOL
		// Aqui lo hacemos mediante try catch para recoger el error si no lo logra
		try {
			MiModelo = new ModeloProductos(miPool);
		} catch (Exception e) {
			System.out.println("Excepcion: " + e.getMessage());
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String elComando = request.getParameter("instruccion");

		System.out.println("elComando: " + elComando);

		if (elComando == null)
			elComando = "listar";

		/*******************************************************************/

		// Eleccion segun comando

		/*****************************************************************/
		try {
			switch (elComando) {

			case "listar":
				obtenerProductos(request, response);
				break;

			case "insertarBBDD":
				agregarProductos(request, response);
				break;

			default:
				obtenerProductos(request, response);
				break;
			}

		}

		// Gestion de errores en las ordenes

		catch (Exception e) {

			// Pasamos el mensaje a String

			System.out.println("EXCEPCIÓN: " + e.getMessage());
			String MensajeError = e.getMessage();

			// Si el error es campo vacio (Al añadir un producto) envia otra vez a añadir
			// producto.

			if (MensajeError == "emptyString") {
				try {
					agregarProductos(request, response);
				} catch (Exception e2) {
					System.out.println("EXCEPCIÓN: " + e2.getMessage());
				}
			}
		}
		// obtenerProductosError(request, response, e.getMessage());
	}

	/************************************************************************/

	private void obtenerProductos(HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<Producto> ProductosDisponibles = MiModelo.ObtenProductosDeLaBase();
		request.setAttribute("LISTAPRODUCTOS", ProductosDisponibles);
		request.setAttribute("excepcion", null);
		RequestDispatcher miDispatcher = request.getRequestDispatcher("/Listar.jsp");
		// RequestDispatcher miDispatcher =
		// request.getRequestDispatcher("/ListaProductos2.jsp");
		miDispatcher.forward(request, response);

	}

	private void agregarProductos(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String CodArticulo = request.getParameter("CArt");
		String Seccion = request.getParameter("seccion");
		String NombreArticulo = request.getParameter("NArt");
		String Fecha = request.getParameter("fecha");
		double Precio = Double.parseDouble(request.getParameter("precio"));
		String Importado = request.getParameter("importado");
		String PaisOrigen = request.getParameter("POrig");

		Producto NuevoProducto = new Producto(CodArticulo, Seccion, NombreArticulo, Precio, Fecha, Importado,
				PaisOrigen);

		MiModelo.agregarElNuevoProducto(NuevoProducto);

		obtenerProductos(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

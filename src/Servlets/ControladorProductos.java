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
		
		
		try {
		System.out.println("Importando datos de la base");
		
		// Aqui creamos una lista llamada "ProductosDisponibles" en la que cogemos todos los de la base
		
		List<Producto> ProductosDisponibles = MiModelo.ObtenProductosDeLaBase();
		
		// Estas tres lineas pasan dicha lista a la clase "ListarProductos.jsp"
		
		request.setAttribute("LISTAPRODUCTOS", ProductosDisponibles);
	RequestDispatcher miDispatcher = request.getRequestDispatcher("/Listar.jsp");
	miDispatcher.forward(request, response);
		
		}
		catch (Exception e) {
			System.out.println("Excepcion: " + e.getMessage());
		}
	}
		
		
		
		

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

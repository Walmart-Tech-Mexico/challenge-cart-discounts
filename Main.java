package com.walmarttech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// public class Main {
//     public static void main(String[] args) {
//         // Tu código comienza aquí
//         System.out.println("Solución del reto: Carrito sin control");
//     }
// }
public class Main {
    public static void main(String[] args) {
        // Reto para el desarrollador:
        // 1. Implementar la clase Producto y Carrito.
        // 2. Agregar la funcionalidad para agregar productos al carrito.
        // 3. Implementar la lógica para aplicar promociones 2x1 en una categoría específica.
        // 4. Implementar la lógica para aplicar descuentos por categoría.
        // 5. Calcular el total del carrito después de aplicar las promociones.
        // 6. Crear pruebas unitarias para verificar la correcta aplicación de las promociones y el cálculo del total.

        // Ejemplo de uso (debe funcionar una vez implementadas las clases y métodos):
        Producto producto1 = new Producto("Camiseta", 20.0, "Ropa");
        Producto producto2 = new Producto("Pantalón", 50.0, "Ropa");
        Producto producto3 = new Producto("Zapatos", 80.0, "Calzado");
        Producto producto4 = new Producto("Calcetines", 10.0, "Ropa");
        Producto producto5 = new Producto("Camiseta", 20.0, "Ropa"); // Repetido para el 2x1

        Carrito carrito = new Carrito();
        carrito.agregarProducto(producto1);
        carrito.agregarProducto(producto2);
        carrito.agregarProducto(producto3);
        carrito.agregarProducto(producto4);
        carrito.agregarProducto(producto5);

        carrito.aplicarPromocion2x1("Ropa");
        carrito.aplicarDescuentoCategoria("Calzado", 0.10);

        System.out.println("Total del carrito: " + carrito.calcularTotal());
    }
}

// Implementar las clases Producto y Carrito aquí
class Producto{
	private String nombre;
	private double precio;
	private String categoria;
	
	public Producto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Producto(String nombre, double precio, String categoria) {
		super();
		this.nombre = nombre;
		this.precio = precio;
		this.categoria = categoria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
}

// class Carrito { ... }
class Carrito {
    private List<Producto> productos;
    private List<Producto> productosConPromociones;

    public Carrito() {
        productos = new ArrayList<Producto>();
        productosConPromociones = new ArrayList<Producto>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public void aplicarPromocion2x1(String categoria) {
        Map<String, Integer> contador = new HashMap<>();

        for (Producto producto : productos) {
            if (producto.getCategoria().equalsIgnoreCase(categoria)) {
                contador.put(producto.getNombre(), contador.getOrDefault(producto.getNombre(), 0) + 1);
            } else {
                productosConPromociones.add(producto);
            }
        }

        for (Map.Entry<String, Integer> entry : contador.entrySet()) {
            String nombre = entry.getKey();
            int cantidad = entry.getValue();
            int aCobrar = (cantidad / 2) + (cantidad % 2);

            int añadidos = 0;
            for (Producto producto : productos) {
                if (añadidos >= aCobrar) break;
                if (producto.getNombre().equals(nombre) && producto.getCategoria().equalsIgnoreCase(categoria)) {
                    productosConPromociones.add(producto);
                    añadidos++;
                }
            }
        }
    }

    public void aplicarDescuentoCategoria(String categoria, double porcentaje) {
        for (int i = 0; i < productosConPromociones.size(); i++) {
            Producto aux = productosConPromociones.get(i);
            if (aux.getCategoria().equalsIgnoreCase(categoria)) {
                double descuento = aux.getPrecio() * porcentaje;
                Producto conDescuento = new Producto(aux.getNombre(), aux.getPrecio() - descuento, aux.getCategoria());
                productosConPromociones.set(i, conDescuento);
            }
        }
    }

    public double calcularTotal() {
        double total = 0.0;
        List<Producto> lista = productosConPromociones.isEmpty() ? productos : productosConPromociones;
        for (Producto aux : lista) {
            total += aux.getPrecio();
        }
        return total;
    }
}

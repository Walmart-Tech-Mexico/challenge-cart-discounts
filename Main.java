package com.walmarttech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
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

class Producto {

    public Producto(String nombre, double precio, String categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

    private String nombre;
    private double precio;
    private String categoria;

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}

class Carrito {

    public Carrito() {
        this.productos = new ArrayList<>();
    }

    private List<Producto> productos;

    public List<Producto> getProductos() {
        return productos;
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public void agregarProducto(Producto... producto) {
        productos.addAll(productos);
    }

    public void aplicarPromocion2x1(String categoria) {
        Map<String, Integer> promocion2x1 = new HashMap<>();

        for (Producto producto : productos) {
            if (producto.getCategoria().equalsIgnoreCase(categoria)) {
                promocion2x1.put(producto.getNombre(), promocion2x1.getOrDefault(producto.getNombre(), 0) + 1);
            }
        }

        promocion2x1.replaceAll((k, v) -> v / 2);

        for (Producto producto : productos) {
            if (producto.getCategoria().equalsIgnoreCase(categoria) && promocion2x1.containsKey(producto.getNombre())) {
                if(promocion2x1.get(producto.getNombre()) > 0) {
                    producto.setPrecio(0.0);
                    promocion2x1.put(producto.getNombre(), promocion2x1.get(producto.getNombre()) - 1);
                }
            }
        }
    }

    public void aplicarDescuentoCategoria(String categoria, double descuento) {
        for (Producto producto : productos) {
            if (producto.getCategoria().equalsIgnoreCase(categoria)) {
                producto.setPrecio(producto.getPrecio() - (producto.getPrecio() * descuento));
            }
        }
    }

    public double calcularTotal() {
        return productos.stream().map(Producto::getPrecio).mapToDouble(Double::doubleValue).sum();
    }
}
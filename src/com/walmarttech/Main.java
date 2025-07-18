package com.walmarttech;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
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
class Producto {
    private String nombre;
    private double precio;
    private String categoria;

    public Producto(String nombre, double precio, String categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

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

    public boolean compararCategoria(String categoria) {
        return this.categoria.toLowerCase().equals(categoria.toLowerCase());
    }
}


class Carrito {
    private List<Producto> productos;

    public Carrito() {
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        this.productos.add(producto);
    }

    public void aplicarPromocion2x1(String categoria) {
        if (categoria == null) return;
        
        List<Producto> productosPromocion = productos.stream().filter(producto -> producto.compararCategoria(categoria)).collect(Collectors.toList());
        Set<String> productosPromocionMap = new HashSet<>();

        if (productosPromocion.isEmpty()) return;

        for (Producto producto : productosPromocion) {
            String key = producto.getNombre();

            if (productosPromocionMap.contains(key)) {
                producto.setPrecio(0.0);
                productosPromocionMap.remove(key);
                continue;
            }
            
            productosPromocionMap.add(key);
        }
    }

    public void aplicarDescuentoCategoria(String categoria, double descuento) {
        if (categoria == null) return;
        
        for (Producto producto : productos) {
            if (!producto.compararCategoria(categoria)) {
                continue;
            }

            double descuentoValidado = descuento > 1.0 ? 1.0 : descuento;
            descuentoValidado = descuentoValidado < 0.0 ? 0.0 : descuentoValidado;

            double precioConDescuento = producto.getPrecio() * (1 - descuentoValidado);
            producto.setPrecio(precioConDescuento);
        }
    }

    public double calcularTotal() {
        return productos.stream().mapToDouble(producto -> producto.getPrecio()).sum();
    }
}
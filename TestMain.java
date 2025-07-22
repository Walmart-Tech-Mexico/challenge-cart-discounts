package com.walmarttech;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CarritoTest {
    private Carrito carrito;
    private Producto p1, p2, p3, p4, p5;

    @BeforeEach
    void setUp() {
        carrito = new Carrito();
        p1 = new Producto("Camiseta", 20.0, "Ropa");
        p2 = new Producto("Pantalon", 50.0, "Ropa");
        p3 = new Producto("Zapatos", 80.0, "Calzado");
        p4 = new Producto("Calcetines", 10.0, "Ropa");
        p5 = new Producto("Camiseta", 20.0, "Ropa"); // para promocion 2x1
        carrito.agregarProducto(p1);
        carrito.agregarProducto(p2);
        carrito.agregarProducto(p3);
        carrito.agregarProducto(p4);
        carrito.agregarProducto(p5);
    }

    @Test
    void testCalcularTotalSinPromociones() {
        double expected = p1.getPrecio() + p2.getPrecio() + p3.getPrecio() + p4.getPrecio() + p5.getPrecio();
        assertEquals(expected, carrito.calcularTotal());
    }

    @Test
    void testPromocion2x1Ropa() {
        carrito.aplicarPromocion2x1("Ropa");
        // Debe haber un producto gratuito de los de categoria Ropa, especificamente una Camiseta
        long gratuitos = carrito.getProductos().stream()
                .filter(p -> p.getCategoria().equalsIgnoreCase("Ropa") && p.getPrecio() == 0.0)
                .count();
        assertEquals(1, gratuitos, "Debe aplicarse un 2x1 en Ropa");
    }

    @Test
    void testDescuentoCategoriaCalzado() {
        carrito.aplicarDescuentoCategoria("Calzado", 0.1);
        // 80 * 0.9 = 72
        assertEquals(72.0, p3.getPrecio());
    }

    @Test
    void testFlujoCompleto() {
        carrito.aplicarPromocion2x1("Ropa");
        carrito.aplicarDescuentoCategoria("Calzado", 0.10);
        double total = carrito.calcularTotal();
        // Calcular manualmente: Productos Ropa: Camiseta(20), Camiseta gratis, Pantalon(50), Calcetines(10) = 80
        // Calzado: Zapatos(80 * 0.9) = 72
        assertEquals(152.0, total, 0.001);
    }
}

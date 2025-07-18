package com.walmarttech;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CartTest {
    @Test
    public void testProductsWithoutDiscounts() {
        Producto producto = new Producto("Camiseta", 10.0, "Ropa");
        Producto producto2 = new Producto("Pantalón", 20.0, "Ropa");

        Carrito carrito = new Carrito();
        carrito.agregarProducto(producto);
        carrito.agregarProducto(producto2);

        assertEquals(30.0, carrito.calcularTotal());
    }

    @Test
    public void testProductsWithDiscountsHigherThan100() {
        Producto producto = new Producto("Camiseta", 10.0, "Ropa");

        Carrito carrito = new Carrito();
        carrito.agregarProducto(producto);

        carrito.aplicarDescuentoCategoria("Ropa", 1500.0);
        assertEquals(0.0, carrito.calcularTotal());
    }

    @Test
    public void testProductsWithNegativeDiscount() {
        Producto producto = new Producto("Camiseta", 10.0, "Ropa");

        Carrito carrito = new Carrito();
        carrito.agregarProducto(producto);

        carrito.aplicarDescuentoCategoria("Ropa", -0.10);
        assertEquals(10.0, carrito.calcularTotal());
    }

    @Test
    public void testProductsWithDiscounts() {
        // Productos con descuento
        Producto producto = new Producto("Camiseta", 10.0, "Ropa");
        Producto producto2 = new Producto("Pantalón", 10.0, "Ropa");

        Carrito carrito = new Carrito();

        carrito.agregarProducto(producto);
        carrito.agregarProducto(producto2);

        carrito.aplicarDescuentoCategoria("Ropa", 0.10);

        assertEquals(18.0, carrito.calcularTotal());
    }

    @Test
    public void testProductsWithDiscountsAndNormalPrice() {
        // Productos con descuento
        Producto producto = new Producto("Camiseta", 10.0, "Ropa");
        Producto producto2 = new Producto("Pantalón", 10.0, "Ropa");

        // Productos sin descuento
        Producto producto3 = new Producto("Zapatos", 20.0, "Calzado");

        Carrito carrito = new Carrito();

        carrito.agregarProducto(producto);
        carrito.agregarProducto(producto2);
        carrito.agregarProducto(producto3);

        carrito.aplicarDescuentoCategoria("Ropa", 0.10);

        assertEquals(38.0, carrito.calcularTotal());
    }

    @Test
    public void testProductsWithNullCategory() {
        Producto producto = new Producto("Camiseta", 10.0, "Ropa");
        Producto producto2 = new Producto("Pantalón", 10.0, "Ropa");

        Carrito carrito = new Carrito();

        carrito.agregarProducto(producto);
        carrito.agregarProducto(producto2);

        carrito.aplicarDescuentoCategoria(null, 0.10);

        assertEquals(20.0, carrito.calcularTotal());
    }

    @Test
    public void testSimplePromotion2x1() {
        Producto producto = new Producto("Camiseta", 10.0, "Ropa");
        Producto producto2 = new Producto("Camiseta", 10.0, "Ropa");

        Carrito carrito = new Carrito();

        carrito.agregarProducto(producto);
        carrito.agregarProducto(producto2);

        carrito.aplicarPromocion2x1("Ropa");

        assertEquals(10.0, carrito.calcularTotal());
    }

    @Test
    public void testPromotionWithNoProductsInCategory() {
        Producto producto = new Producto("Camiseta", 10.0, "Ropa");
        Producto producto2 = new Producto("Camiseta", 10.0, "Ropa");

        Carrito carrito = new Carrito();

        carrito.agregarProducto(producto);
        carrito.agregarProducto(producto2);

        carrito.aplicarPromocion2x1("Calzado");

        assertEquals(20.0, carrito.calcularTotal());
    }

    /**
     * ! IMPORTANTE !
     * En la declaracion original del problema, el 2x1 solo se indica para
     * productos repetidos (Camiseta en el codigo original). Este test asume que
     * el 2x1 solo se aplica a productos repetidos.
     */
    @Test
    public void testPromotion2x1WithNoRepeatedProduct() {
        Producto producto = new Producto("Camiseta", 10.0, "Ropa");
        Producto producto2 = new Producto("Camiseta2", 10.0, "Ropa");
        
        Carrito carrito = new Carrito();

        carrito.agregarProducto(producto);
        carrito.agregarProducto(producto2);

        carrito.aplicarPromocion2x1("Ropa");

        assertEquals(20.0, carrito.calcularTotal());
    }

    @Test
    public void testPromotion2x1WithNullCategory() {
        Producto producto = new Producto("Camiseta", 10.0, "Ropa");
        Producto producto2 = new Producto("Camiseta", 10.0, "Ropa");

        Carrito carrito = new Carrito();

        carrito.agregarProducto(producto);
        carrito.agregarProducto(producto2);

        carrito.aplicarPromocion2x1(null);

        assertEquals(20.0, carrito.calcularTotal());
    }
}


package com.walmarttech;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class CarritoTest {
        private Carrito carrito;

        @BeforeEach
        void setUp() {
            carrito = new Carrito(new ArrayList<>());
        }

        @Test
        @DisplayName("Test agregar producto al carrito")
        void testAgregarProducto() {
            // Arrange
            Producto producto = new Producto("Camiseta", 20.0, "Ropa");

            // Act
            carrito.agregarProducto(producto);

            // Assert
            assertEquals(1, carrito.getProductos().size());
            assertEquals("Camiseta", carrito.getProductos().get(0).getName());
        }

        @Test
        @DisplayName("Test calcular total sin promociones")
        void testCalcularTotalSinPromociones() {
            // Arrange
            carrito.agregarProducto(new Producto("Camiseta", 20.0, "Ropa"));
            carrito.agregarProducto(new Producto("Pantalón", 50.0, "Ropa"));
            carrito.agregarProducto(new Producto("Zapatos", 80.0, "Calzado"));

            // Act
            double total = carrito.calcularTotal();
            // Assert
            assertEquals(150.0, total, 0.01);
        }

        @Test
        @DisplayName("Test promoción 2x1 - exactamente 2 productos iguales")
        void testPromocion2x1DosProductosIguales() {
            // Arrange
            carrito.agregarProducto(new Producto("Camiseta", 20.0, "Ropa"));
            carrito.agregarProducto(new Producto("Camiseta", 20.0, "Ropa"));

            // Act
            carrito.aplicarPromocion2x1("Ropa");
            double total = carrito.calcularTotal();

            // Assert
            assertEquals(20.0, total, 0.01, "Con 2x1, solo debe pagar un producto");
        }

        @Test
        @DisplayName("Test promoción 2x1 - 3 productos iguales (paga 2)")
        void testPromocion2x1TresProductosIguales() {
            // Arrange
            carrito.agregarProducto(new Producto("Camiseta", 20.0, "Ropa"));
            carrito.agregarProducto(new Producto("Camiseta", 20.0, "Ropa"));
            carrito.agregarProducto(new Producto("Camiseta", 20.0, "Ropa"));

            // Act
            carrito.aplicarPromocion2x1("Ropa");
            double total = carrito.calcularTotal();

            // Assert
            assertEquals(40.0, total, 0.01, "Con 3 productos iguales en 2x1, debe pagar 2");
        }

        @Test
        @DisplayName("Test promoción 2x1 - 4 productos iguales (paga 2)")
        void testPromocion2x1CuatroProductosIguales() {
            // Arrange
            carrito.agregarProducto(new Producto("Camiseta", 20.0, "Ropa"));
            carrito.agregarProducto(new Producto("Camiseta", 20.0, "Ropa"));
            carrito.agregarProducto(new Producto("Camiseta", 20.0, "Ropa"));
            carrito.agregarProducto(new Producto("Camiseta", 20.0, "Ropa"));

            // Act
            carrito.aplicarPromocion2x1("Ropa");
            double total = carrito.calcularTotal();

            // Assert
            assertEquals(40.0, total, 0.01, "Con 4 productos iguales en 2x1, debe pagar 2");
        }

        @Test
        @DisplayName("Test promoción 2x1 - productos diferentes de la misma categoría")
        void testPromocion2x1ProductosDiferentes() {
            // Arrange
            carrito.agregarProducto(new Producto("Camiseta", 20.0, "Ropa"));
            carrito.agregarProducto(new Producto("Pantalón", 50.0, "Ropa"));
            carrito.agregarProducto(new Producto("Camiseta", 20.0, "Ropa"));
            carrito.agregarProducto(new Producto("Calcetines", 10.0, "Ropa"));

            // Act
            carrito.aplicarPromocion2x1("Ropa");
            double total = carrito.calcularTotal();

            // Assert
            assertEquals(80.0, total, 0.01, "2x1 solo se aplica a productos idénticos, no a categorías");
        }

        @Test
        @DisplayName("Test promoción 2x1 - no afecta otras categorías")
        void testPromocion2x1NoAfectaOtrasCategorias() {
            // Arrange
            carrito.agregarProducto(new Producto("Camiseta", 20.0, "Ropa"));
            carrito.agregarProducto(new Producto("Camiseta", 20.0, "Ropa"));
            carrito.agregarProducto(new Producto("Zapatos", 80.0, "Calzado"));
            carrito.agregarProducto(new Producto("Zapatos", 80.0, "Calzado"));

            // Act
            carrito.aplicarPromocion2x1("Ropa");
            double total = carrito.calcularTotal();

            // Assert
            assertEquals(180.0, total, 0.01, "2x1 en Ropa no debe afectar Calzado");
        }

        @Test
        @DisplayName("Test descuento por categoría - 10%")
        void testDescuentoCategoria() {
            // Arrange
            carrito.agregarProducto(new Producto("Zapatos", 100.0, "Calzado"));
            carrito.agregarProducto(new Producto("Botas", 150.0, "Calzado"));
            carrito.agregarProducto(new Producto("Camiseta", 20.0, "Ropa"));

            // Act
            carrito.aplicarDescuentoCategoria("Calzado", 0.10);
            double total = carrito.calcularTotal();

            // Assert
            assertEquals(245.0, total, 0.01, "Descuento 10% en Calzado: (100*0.9 + 150*0.9 + 20) = 245");
        }

        @Test
        @DisplayName("Test descuento por categoría - no afecta otras categorías")
        void testDescuentoCategoriaNoAfectaOtras() {
            // Arrange
            carrito.agregarProducto(new Producto("Zapatos", 100.0, "Calzado"));
            carrito.agregarProducto(new Producto("Camiseta", 20.0, "Ropa"));

            // Act
            carrito.aplicarDescuentoCategoria("Calzado", 0.15);

            // Assert
            assertEquals(85.0, carrito.getProductos().get(0).getPrice(), 0.01, "Calzado con 15% descuento");
            assertEquals(20.0, carrito.getProductos().get(1).getPrice(), 0.01, "Ropa sin descuento");
        }

        @Test
        @DisplayName("Test combinación de promoción 2x1 y descuento por categoría")
        void testCombinacionPromociones() {
            // Arrange
            carrito.agregarProducto(new Producto("Camiseta", 20.0, "Ropa"));
            carrito.agregarProducto(new Producto("Camiseta", 20.0, "Ropa"));
            carrito.agregarProducto(new Producto("Zapatos", 100.0, "Calzado"));

            // Act
            carrito.aplicarPromocion2x1("Ropa");
            carrito.aplicarDescuentoCategoria("Calzado", 0.10);
            double total = carrito.calcularTotal();

            // Assert
            assertEquals(110.0, total, 0.01, "2x1 en Ropa (paga 1 camiseta=20) + Calzado con 10% desc (90) = 110");
        }

        @Test
        @DisplayName("Test caso del ejemplo del Main")
        void testCasoEjemploMain() {
            // Arrange - Recrear exactamente el ejemplo del Main
            Producto producto1 = new Producto("Camiseta", 20.0, "Ropa");
            Producto producto2 = new Producto("Pantalón", 50.0, "Ropa");
            Producto producto3 = new Producto("Zapatos", 80.0, "Calzado");
            Producto producto4 = new Producto("Calcetines", 10.0, "Ropa");
            Producto producto5 = new Producto("Camiseta", 20.0, "Ropa");
            Producto producto6 = new Producto("Camiseta", 20.0, "Ropa");
            Producto producto7 = new Producto("Camiseta", 20.0, "Ropa");

            carrito.agregarProducto(producto1);
            carrito.agregarProducto(producto2);
            carrito.agregarProducto(producto3);
            carrito.agregarProducto(producto4);
            carrito.agregarProducto(producto5);
            carrito.agregarProducto(producto6);
            carrito.agregarProducto(producto7);

            // Act
            carrito.aplicarPromocion2x1("Ropa");
            carrito.aplicarDescuentoCategoria("Calzado", 0.10);
            double total = carrito.calcularTotal();

            // Assert
            // 4 Camisetas (20 cada una) con 2x1 = paga 2 = 40
            // 1 Pantalón = 50
            // 1 Calcetines = 10
            // 1 Zapatos con 10% descuento = 72
            // Total esperado = 172
            assertEquals(172.0, total, 0.01, "Total del ejemplo del Main debe ser 172");
        }

        @Test
        @DisplayName("Test carrito vacío")
        void testCarritoVacio() {
            // Act
            double total = carrito.calcularTotal();

            // Assert
            assertEquals(0.0, total, 0.01);
        }

        @Test
        @DisplayName("Test promoción 2x1 en categoría inexistente")
        void testPromocion2x1CategoriaInexistente() {
            // Arrange
            carrito.agregarProducto(new Producto("Camiseta", 20.0, "Ropa"));
            double precioOriginal = carrito.calcularTotal();

            // Act
            carrito.aplicarPromocion2x1("Electrónicos");
            double total = carrito.calcularTotal();

            // Assert
            assertEquals(precioOriginal, total, 0.01, "No debe cambiar el precio si la categoría no existe");
        }

        @Test
        @DisplayName("Test descuento en categoría inexistente")
        void testDescuentoCategoriaInexistente() {
            // Arrange
            carrito.agregarProducto(new Producto("Camiseta", 20.0, "Ropa"));
            double precioOriginal = carrito.calcularTotal();

            // Act
            carrito.aplicarDescuentoCategoria("Electrónicos", 0.20);
            double total = carrito.calcularTotal();

            // Assert
            assertEquals(precioOriginal, total, 0.01, "No debe cambiar el precio si la categoría no existe");
        }

        @Test
        @DisplayName("Test múltiples productos diferentes con 2x1")
        void testMultiplesProductosDiferentes2x1() {
            // Arrange
            carrito.agregarProducto(new Producto("Camiseta", 20.0, "Ropa"));
            carrito.agregarProducto(new Producto("Camiseta", 20.0, "Ropa"));
            carrito.agregarProducto(new Producto("Pantalón", 50.0, "Ropa"));
            carrito.agregarProducto(new Producto("Pantalón", 50.0, "Ropa"));
            carrito.agregarProducto(new Producto("Calcetines", 10.0, "Ropa"));

            // Act
            carrito.aplicarPromocion2x1("Ropa");
            double total = carrito.calcularTotal();

            // Assert
            // 2 Camisetas 2x1 = paga 1 = 20
            // 2 Pantalones 2x1 = paga 1 = 50
            // 1 Calcetines = 10
            // Total = 80
            assertEquals(80.0, total, 0.01, "2x1 debe aplicarse por producto específico");
        }

        @Test
        @DisplayName("Test descuento con valor límite (100%)")
        void testDescuentoCompleto() {
            // Arrange
            carrito.agregarProducto(new Producto("Zapatos", 100.0, "Calzado"));

            // Act
            carrito.aplicarDescuentoCategoria("Calzado", 1.0); // 100% descuento
            double total = carrito.calcularTotal();

            // Assert
            assertEquals(0.0, total, 0.01, "Descuento del 100% debe resultar en precio 0");
        }

        @Test
        @DisplayName("Test orden de aplicación: primero 2x1, después descuento")
        void testOrdenAplicacionPromociones() {
            // Arrange
            carrito.agregarProducto(new Producto("Camiseta", 20.0, "Ropa"));
            carrito.agregarProducto(new Producto("Camiseta", 20.0, "Ropa"));

            // Act
            carrito.aplicarPromocion2x1("Ropa");
            carrito.aplicarDescuentoCategoria("Ropa", 0.10);
            double total = carrito.calcularTotal();

            // Assert
            // Primero 2x1: una camiseta queda en 0, otra en 20
            // Después descuento 10%: la que vale 20 queda en 18, la que vale 0 queda en 0
            assertEquals(18.0, total, 0.01, "Descuento debe aplicarse después del 2x1");
        }

        @Test
        @DisplayName("Test orden inverso: primero descuento, después 2x1")
        void testOrdenInversoPromociones() {
            // Arrange
            carrito.agregarProducto(new Producto("Camiseta", 20.0, "Ropa"));
            carrito.agregarProducto(new Producto("Camiseta", 20.0, "Ropa"));

            // Act
            carrito.aplicarDescuentoCategoria("Ropa", 0.10);
            carrito.aplicarPromocion2x1("Ropa");
            double total = carrito.calcularTotal();

            // Assert
            // Primero descuento 10%: ambas camisetas quedan en 18
            // Después 2x1: una queda en 0, otra en 18
            assertEquals(18.0, total, 0.01, "2x1 debe aplicarse después del descuento");
        }

        @Test
        @DisplayName("Test escenario complejo con múltiples categorías y promociones")
        void testEscenarioComplejo() {
            // Arrange
            carrito.agregarProducto(new Producto("Camiseta", 25.0, "Ropa"));
            carrito.agregarProducto(new Producto("Camiseta", 25.0, "Ropa"));
            carrito.agregarProducto(new Producto("Pantalón", 60.0, "Ropa"));
            carrito.agregarProducto(new Producto("Zapatos", 120.0, "Calzado"));
            carrito.agregarProducto(new Producto("Botas", 150.0, "Calzado"));
            carrito.agregarProducto(new Producto("Laptop", 800.0, "Electrónicos"));

            // Act
            carrito.aplicarPromocion2x1("Ropa");
            carrito.aplicarDescuentoCategoria("Calzado", 0.15);
            carrito.aplicarDescuentoCategoria("Electrónicos", 0.05);
            double total = carrito.calcularTotal();

            // Assert
            // Ropa: 2 Camisetas con 2x1 = 25, Pantalón = 60 → Total Ropa = 85
            // Calzado: Zapatos = 120*0.85 = 102, Botas = 150*0.85 = 127.5 → Total Calzado = 229.5
            // Electrónicos: Laptop = 800*0.95 = 760 → Total Electrónicos = 760
            // Total general = 1074.5
            assertEquals(1074.5, total, 0.01, "Escenario complejo con múltiples promociones");
        }
    
}

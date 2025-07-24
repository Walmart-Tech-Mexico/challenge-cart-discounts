package com.walmarttech;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
class Producto {

    private final String nombre;
    private final double precio;
    private final String categoria;

    public Producto(String nombre, double precio, String categoria) {

        if( precio <= 0)
            throw new IllegalArgumentException("El precio debe ser mayor que 0");
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

    @Override
    public int hashCode() {
        return Objects.hash(nombre, precio, categoria);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Producto producto = (Producto) obj;
        return Double.compare(producto.precio, precio) == 0 && nombre.equals(producto.nombre) && categoria.equals(producto.categoria);
    }



}
class Carrito {

    private final Map<String, ArrayList<Producto>> productos ;
    private final Set<String> categoriasConDescuento;
    private final Map<String,Double> descuento;


    public  Carrito(){
        productos = new HashMap<>();
        categoriasConDescuento = new HashSet<>();
        descuento = new HashMap<>();
    }

    public void agregarProducto(Producto producto) {
        if(!productos.containsKey(producto.getCategoria())){
            productos.put(producto.getCategoria(), new ArrayList<>());
        }
        productos.get(producto.getCategoria()).add(producto);

    }


    public void aplicarPromocion2x1(String categoria) {
        this.categoriasConDescuento.add(categoria);
    }

    public void aplicarDescuentoCategoria(String categoria, double discount) {

        if(discount < 0 || discount > 1) throw new IllegalArgumentException("El descuento debe estar entre 0 y 1");
        this.descuento.put(categoria,discount);
    }

    public double calcularTotal() {
        double total = 0;
        for (Map.Entry<String, ArrayList<Producto>> entry : productos.entrySet()) {
            String categoria = entry.getKey();
            ArrayList<Producto> productosCategoria = entry.getValue();
            double subtotal = 0;
            if(categoriasConDescuento.contains(entry.getKey())){
                subtotal = this.calcularSubtotal(productosCategoria);
            }else{
                subtotal = productosCategoria.stream().mapToDouble(Producto::getPrecio).sum();
            }

            if(descuento.containsKey(categoria)){
                subtotal = subtotal * (1-descuento.get(categoria));
            }

            total += subtotal;

        }
        return total;
    }

    public double calcularSubtotal(ArrayList<Producto> productosCategoria){
        double subtotal = 0;
        Map<Producto,Integer> conteoProductos = new HashMap<>();

        for (Producto producto : productosCategoria) {
            conteoProductos.put(producto,conteoProductos.getOrDefault(producto,0)+1);
        }
        for(Map.Entry<Producto,Integer> productoConteo : conteoProductos.entrySet()){

            Producto producto = productoConteo.getKey();
            int cantidadPagar = productoConteo.getValue()/2 +
                    productoConteo.getValue()%2;
            subtotal += producto.getPrecio()*cantidadPagar;

        }



        return subtotal;
    }
}



class CarritoTest {

    private Carrito carrito;
    private Producto camiseta1;
    private Producto camiseta2;
    private Producto pantalon;
    private Producto zapatos;



    @BeforeEach
    void setUp() {
        carrito = new Carrito();
        camiseta1 = new Producto("Camiseta", 20.0, "Ropa");
        camiseta2 = new Producto("Camiseta", 20.0, "Ropa");
        pantalon = new Producto("Pantalón", 50.0, "Ropa");
        zapatos = new Producto("Zapatos", 80.0, "Calzado");

    }


    @Test
    @DisplayName("Prueba agregar productos al carrito")
    void pruebaAgregarProducto() {
        carrito.agregarProducto(camiseta1);
        carrito.agregarProducto(zapatos);

        double totalEsperado = 20.0 + 80.0;
        assertEquals(totalEsperado, carrito.calcularTotal(), 0.01,
                "El total debe ser la suma de los precios de los productos agregados");
    }


    @Test
    @DisplayName("Prueba calcular total sin promociones aplicadas")
    void pruebaCalcularTotalSinPromociones() {
        carrito.agregarProducto(camiseta1);
        carrito.agregarProducto(pantalon);
        carrito.agregarProducto(zapatos);

        double totalEsperado = 20.0 + 50.0 + 80.0; // 150.0
        assertEquals(totalEsperado, carrito.calcularTotal(), 0.01,
                "Sin promociones, el total debe ser la suma directa de todos los precios");
    }

    @Test
    @DisplayName("Prueba promoción 2x1 con número par de productos idénticos")
    void pruebaPromocion2x1ParProductosIdenticos() {
        carrito.agregarProducto(camiseta1);
        carrito.agregarProducto(camiseta2);
        carrito.aplicarPromocion2x1("Ropa");


        double totalEsperado = 20.0;
        assertEquals(totalEsperado, carrito.calcularTotal(), 0.01,
                "Con promoción 2x1, dos productos idénticos deben costar como uno solo");
    }



    @Test
    @DisplayName("Prueba promoción 2x1 con productos mixtos en la misma categoría")
    void pruebaPromocion2x1ProductosMixtos() {
        carrito.agregarProducto(camiseta1);
        carrito.agregarProducto(camiseta2);
        carrito.agregarProducto(pantalon);
        carrito.aplicarPromocion2x1("Ropa");

        double totalEsperado = 20.0 + 50.0;
        assertEquals(totalEsperado, carrito.calcularTotal(), 0.01,
                "La promoción 2x1 debe aplicarse solo a productos idénticos dentro de la categoría");
    }

    @Test
    @DisplayName("Prueba descuento por categoría con porcentaje válido")
    void pruebaAplicarDescuentoCategoriaValido() {
        carrito.agregarProducto(zapatos);
        carrito.aplicarDescuentoCategoria("Calzado", 0.10);

        double totalEsperado = 80.0 * 0.90;
        assertEquals(totalEsperado, carrito.calcularTotal(), 0.01,
                "Un descuento del 10% debe reducir el precio en la cantidad correcta");
    }


    @Test
    @DisplayName("Prueba promociones combinadas: 2x1 y descuento por categoría")
    void pruebaPromocionesCombinadas() {
        carrito.agregarProducto(camiseta1);
        carrito.agregarProducto(camiseta2);
        carrito.agregarProducto(pantalon);
        carrito.agregarProducto(zapatos);

        carrito.aplicarPromocion2x1("Ropa");
        carrito.aplicarDescuentoCategoria("Ropa", 0.20); // 20% descuento en Ropa
        carrito.aplicarDescuentoCategoria("Calzado", 0.10); // 10% descuento en Calzado


        double totalEsperado = 56.0 + 72.0; // 128.0
        assertEquals(totalEsperado, carrito.calcularTotal(), 0.01,
                "Las promociones combinadas deben aplicarse correctamente en secuencia");
    }

    @Test
    @DisplayName("Prueba carrito vacío")
    void pruebaCarritoVacio() {
        assertEquals(0.0, carrito.calcularTotal(), 0.01,
                "Un carrito vacío debe tener total cero");
    }




}
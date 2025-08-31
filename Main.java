package com.walmarttech;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


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
        Producto producto6 = new Producto("Camiseta", 20.0, "Ropa"); // Repetido para el 2x1
        Producto producto7 = new Producto("Camiseta", 20.0, "Ropa"); // Repetido para el 2x1

        Carrito carrito = new Carrito(new ArrayList<>());
        carrito.agregarProducto(producto1);
        carrito.agregarProducto(producto2);
        carrito.agregarProducto(producto3);
        carrito.agregarProducto(producto4);
        carrito.agregarProducto(producto5);
        carrito.agregarProducto(producto6);
        carrito.agregarProducto(producto7);

        carrito.aplicarPromocion2x1("Ropa");
        carrito.aplicarDescuentoCategoria("Calzado", 0.10);

        System.out.println("Total del carrito: " + carrito.calcularTotal());
    }
}

// Implementar las clases Producto y Carrito aquí
class Producto {
    private String name;
    private double price;
    private String category;

    public Producto(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                '}';
    }
}
class Carrito {
    private List<Producto> productos;

    public Carrito(List<Producto> productos) {
        this.productos = productos;
    }

    public void agregarProducto(Producto p){
        productos.add(p);
    }

    //Crea un HashMap para asociar el nombre del producto y un contador para saber cuando compre un par, cada que se encuentra que ha llegado a un par
    //se modifica el precio del segundo producto a 0, y se reinicia el contador de productos a 0
    public void aplicarPromocion2x1(String category){
        HashMap<String,Integer> counts = new HashMap<>();
        productos.stream().filter(p->p.getCategory().equals(category)).forEach(p->{
            if(!counts.containsKey(p.getName()))
                counts.put(p.getName(),0);
            counts.replace(p.getName(),counts.get(p.getName())+1);
            if(counts.get(p.getName())==2){
                p.setPrice(0);
                counts.replace(p.getName(),0);
            }
        });
    }

    public void aplicarDescuentoCategoria(String category,double descuento){
        productos.forEach(p->{
            if(p.getCategory().equals(category))
                p.setPrice(p.getPrice()*(1-descuento));
        });
    }

    public Double calcularTotal(){
        return productos.stream().mapToDouble(Producto::getPrice).sum();
    }
    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    @Override
    public String toString() {
        return "Carrito{" +
                "productos=" + productos.stream().map(Producto::toString) +
                '}';
    }
}
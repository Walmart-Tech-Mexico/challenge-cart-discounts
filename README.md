# Carrito sin control

**Nivel:** Fácil

## Descripción
Validar un carrito de compras, agrupando productos y aplicando promociones simples (2x1, 10% off).

## Objetivo
Implementa una solución en Java que cumpla con la lógica descrita. Usa la plantilla en `src/com/walmarttech/Main.java` para comenzar.

## Cómo empezar
1. Clona este repositorio.
2. Dirígete a la carpeta `challenges/week-01-cart-discounts`.
3. Abre `Main.java` y escribe tu solución.
4. ¡Comparte tu solución con la comunidad!




# challenge-cart-discount

## Descripción
Sistema de carrito de compras con funcionalidad de promociones y descuentos.

## Funcionalidades Implementadas

### 1. Clases Base
- Implementación de la clase `Producto`
- Implementación de la clase `Carrito`

### 2. Gestión de Productos
- Funcionalidad para agregar productos al carrito
- Agrupación automática de productos por categoría

### 3. Sistema de Promociones 2x1
- Implementación de promoción 2x1 por categoría
- La promoción aplica solo a productos idénticos (mismo nombre y precio)
- Flexibilidad para modificar la lógica y aplicar solo por precio si se requiere

### 4. Sistema de Descuentos
-  Implementación de descuentos por categoría
-  Los descuentos se aplican sin modificar el precio original del producto
-  Soporte para múltiples categorías con diferentes porcentajes de descuento

### 5. Cálculo de Totales
-  Cálculo del total considerando todas las promociones aplicadas
-  Soporte para aplicar múltiples tipos de promociones simultáneamente

### 6. Pruebas Unitarias
- Suite completa de pruebas unitarias
- Verificación de promociones 2x1
- Verificación de descuentos por categoría
- Verificación de casos combinados

package com.tierraconnect.api.service;

import com.tierraconnect.api.model.Producto;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductoService {

    private final Map<String, Producto> productos = new HashMap<>();

    // Constructor con datos de prueba
    public ProductoService() {
        // Agregar productos de ejemplo
        Producto p1 = new Producto(
                "1",
                "Papa Blanca",
                "Tubérculo",
                500.0,
                "Huancayo, Junín",
                "Juan Pérez",
                "DISPONIBLE"
        );

        Producto p2 = new Producto(
                "2",
                "Quinua Roja",
                "Grano",
                200.0,
                "Puno",
                "María García",
                "DISPONIBLE"
        );

        productos.put(p1.getId(), p1);
        productos.put(p2.getId(), p2);
    }

    public List<Producto> listarProductos() {
        return new ArrayList<>(productos.values());
    }

    public Producto obtenerProducto(String id) {
        return productos.get(id);
    }

    public Producto registrarProducto(Producto producto) {
        String id = UUID.randomUUID().toString();
        producto.setId(id);
        producto.setEstado("DISPONIBLE");
        productos.put(id, producto);
        return producto;
    }

    public Producto actualizarProducto(String id, Producto producto) {
        if (productos.containsKey(id)) {
            producto.setId(id);
            productos.put(id, producto);
            return producto;
        }
        return null;
    }

    public boolean eliminarProducto(String id) {
        return productos.remove(id) != null;
    }

    public List<Producto> buscarPorUbicacion(String ubicacion) {
        return productos.values().stream()
                .filter(p -> p.getUbicacion().toLowerCase().contains(ubicacion.toLowerCase()))
                .toList();
    }

    public List<Producto> buscarPorTipo(String tipo) {
        return productos.values().stream()
                .filter(p -> p.getTipo().toLowerCase().contains(tipo.toLowerCase()))
                .toList();
    }
}
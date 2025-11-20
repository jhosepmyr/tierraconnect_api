package com.tierraconnect.api.controller;

import com.tierraconnect.api.model.Producto;
import com.tierraconnect.api.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*") // Para permitir acceso desde cualquier origen
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // GET /api/productos - Listar todos los productos
    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        return ResponseEntity.ok(productoService.listarProductos());
    }

    // GET /api/productos/{id} - Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable String id) {
        Producto producto = productoService.obtenerProducto(id);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        }
        return ResponseEntity.notFound().build();
    }

    // POST /api/productos - Registrar nuevo producto
    @PostMapping
    public ResponseEntity<Producto> registrarProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.registrarProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    // PUT /api/productos/{id} - Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable String id,
            @RequestBody Producto producto) {
        Producto actualizado = productoService.actualizarProducto(id, producto);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE /api/productos/{id} - Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable String id) {
        boolean eliminado = productoService.eliminarProducto(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // GET /api/productos/buscar/ubicacion?q=Huancayo
    @GetMapping("/buscar/ubicacion")
    public ResponseEntity<List<Producto>> buscarPorUbicacion(@RequestParam String q) {
        return ResponseEntity.ok(productoService.buscarPorUbicacion(q));
    }

    // GET /api/productos/buscar/tipo?q=Tubérculo
    @GetMapping("/buscar/tipo")
    public ResponseEntity<List<Producto>> buscarPorTipo(@RequestParam String q) {
        return ResponseEntity.ok(productoService.buscarPorTipo(q));
    }

    // GET /api/productos/health - Health check
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("TierraConnect API está funcionando correctamente");
    }
}
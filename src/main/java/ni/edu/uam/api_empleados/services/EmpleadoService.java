package ni.edu.uam.api_empleados.services;

import ni.edu.uam.api_empleados.dto.EmpleadoDTO;
import ni.edu.uam.api_empleados.exceptions.EmpleadoDuplicadoException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EmpleadoService {
    private final List<EmpleadoDTO> empleados = new ArrayList<>();
    private final AtomicLong secuencia = new AtomicLong(0);

    public List<EmpleadoDTO> listar() {
        return empleados;
    }

    public Optional<EmpleadoDTO> buscarPorId(Long id) {
        return empleados.stream().filter(e -> e.getId().equals(id)).findFirst();
    }

    public EmpleadoDTO guardar(EmpleadoDTO empleado) {
        boolean existe = empleados.stream().anyMatch(e ->
                e.getNombres().equalsIgnoreCase(empleado.getNombres())
                        && e.getApellidos().equalsIgnoreCase(empleado.getApellidos()));

        if (existe) {
            throw new EmpleadoDuplicadoException(
                    "Ya existe un empleado registrado con ese nombre completo");
        }

        empleado.setId(secuencia.incrementAndGet());
        empleados.add(empleado);
        return empleado;
    }

    public Optional<EmpleadoDTO> actualizar(Long id, EmpleadoDTO datos) {
        return buscarPorId(id).map(empleado -> {
            empleado.setNombres(datos.getNombres());
            empleado.setApellidos(datos.getApellidos());
            empleado.setCargo(datos.getCargo());
            empleado.setSalario(datos.getSalario());
            return empleado;
        });
    }

    public boolean eliminar(Long id) {
        return empleados.removeIf(e -> e.getId().equals(id));
    }
}
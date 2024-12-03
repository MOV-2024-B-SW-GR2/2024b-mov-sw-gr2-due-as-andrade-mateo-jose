class CRUDService {
    private val archivoRepository = ArchivoRepository()
    private var empresas = mutableListOf<Empresa>()
    private var empleados = mutableListOf<Empleado>()

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        empresas = archivoRepository.cargarEmpresas().toMutableList()
        empleados = archivoRepository.cargarEmpleados().toMutableList()

        // Asociar empleados a empresas
        empresas.forEach { empresa ->
            empresa.empleados.clear()
            empresa.empleados.addAll(
                empleados.filter { it.empresaId == empresa.id }
            )
        }
    }

    private fun guardarDatos() {
        archivoRepository.guardarEmpresas(empresas)
        archivoRepository.guardarEmpleados(empleados)
    }

    // CRUD de Empresas
    fun crearEmpresa(empresa: Empresa) {
        empresas.add(empresa)
        guardarDatos()
    }

    fun obtenerEmpresa(id: Int): Empresa? {
        return empresas.find { it.id == id }
    }

    fun actualizarEmpresa(empresa: Empresa) {
        val indice = empresas.indexOfFirst { it.id == empresa.id }
        if (indice != -1) {
            empresas[indice] = empresa
            guardarDatos()
        }
    }

    fun eliminarEmpresa(id: Int) {
        empresas.removeIf { it.id == id }
        empleados.removeIf { it.empresaId == id }
        guardarDatos()
    }

    // CRUD de Empleados
    fun crearEmpleado(empleado: Empleado) {
        empleados.add(empleado)
        val empresa = empresas.find { it.id == empleado.empresaId }
        empresa?.empleados?.add(empleado)
        guardarDatos()
    }

    fun obtenerEmpleado(id: Int): Empleado? {
        return empleados.find { it.id == id }
    }

    fun actualizarEmpleado(empleado: Empleado) {
        val indice = empleados.indexOfFirst { it.id == empleado.id }
        if (indice != -1) {
            empleados[indice] = empleado

            // Actualizar en la empresa
            val empresa = empresas.find { it.id == empleado.empresaId }
            empresa?.let {
                val empleadoEnEmpresaIndex = it.empleados.indexOfFirst { e -> e.id == empleado.id }
                if (empleadoEnEmpresaIndex != -1) {
                    it.empleados[empleadoEnEmpresaIndex] = empleado
                }
            }

            guardarDatos()
        }
    }

    fun eliminarEmpleado(id: Int) {
        val empleado = empleados.find { it.id == id }
        empleados.removeIf { it.id == id }

        // Eliminar de la empresa
        empleado?.let {
            val empresa = empresas.find { e -> e.id == it.empresaId }
            empresa?.empleados?.removeIf { e -> e.id == id }
        }

        guardarDatos()
    }

    // Métodos adicionales
    fun obtenerTodasLasEmpresas(): List<Empresa> = empresas
    fun obtenerTodosLosEmpleados(): List<Empleado> = empleados
}
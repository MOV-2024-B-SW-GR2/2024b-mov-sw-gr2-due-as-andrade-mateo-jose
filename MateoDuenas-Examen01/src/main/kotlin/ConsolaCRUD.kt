import java.util.Date
import java.text.SimpleDateFormat
import java.util.Scanner

class ConsolaCRUD {
    private val crudService = CRUDService()
    private val scanner = Scanner(System.`in`)
    private val formatoFecha = SimpleDateFormat("yyyy-MM-dd")

    fun iniciar() {
        while (true) {
            mostrarMenuPrincipal()
            when (scanner.nextLine()) {
                "1" -> menuEmpresas()
                "2" -> menuEmpleados()
                "0" -> break
                else -> println("Opción inválida. Intente nuevamente.")
            }
        }
    }

    private fun mostrarMenuPrincipal() {
        println("\n--- SISTEMA DE GESTIÓN ---")
        println("1. Gestionar Empresas")
        println("2. Gestionar Empleados")
        println("0. Salir")
        print("Seleccione una opción: ")
    }

    private fun menuEmpresas() {
        while (true) {
            println("\n--- MENU EMPRESAS ---")
            println("1. Crear Empresa")
            println("2. Listar Empresas")
            println("3. Buscar Empresa")
            println("4. Actualizar Empresa")
            println("5. Eliminar Empresa")
            println("0. Volver al menú principal")
            print("Seleccione una opción: ")

            when (scanner.nextLine()) {
                "1" -> crearEmpresa()
                "2" -> listarEmpresas()
                "3" -> buscarEmpresa()
                "4" -> actualizarEmpresa()
                "5" -> eliminarEmpresa()
                "0" -> break
                else -> println("Opción inválida. Intente nuevamente.")
            }
        }
    }

    private fun menuEmpleados() {
        while (true) {
            println("\n--- MENU EMPLEADOS ---")
            println("1. Crear Empleado")
            println("2. Listar Empleados")
            println("3. Buscar Empleado")
            println("4. Actualizar Empleado")
            println("5. Eliminar Empleado")
            println("0. Volver al menú principal")
            print("Seleccione una opción: ")

            when (scanner.nextLine()) {
                "1" -> crearEmpleado()
                "2" -> listarEmpleados()
                "3" -> buscarEmpleado()
                "4" -> actualizarEmpleado()
                "5" -> eliminarEmpleado()
                "0" -> break
                else -> println("Opción inválida. Intente nuevamente.")
            }
        }
    }

    // Métodos para Empresas
    private fun crearEmpresa() {
        println("Crear Nueva Empresa:")
        print("ID: ")
        val id = scanner.nextLine().toInt()
        print("Nombre: ")
        val nombre = scanner.nextLine()
        print("Fecha de Fundación (yyyy-MM-dd): ")
        val fechaFundacion = formatoFecha.parse(scanner.nextLine())
        print("¿Es Multinacional? (true/false): ")
        val esMultinacional = scanner.nextLine().toBoolean()
        print("Ingreso Anual: ")
        val ingresoAnual = scanner.nextLine().toDouble()

        val empresa = Empresa(
            id = id,
            nombre = nombre,
            fechaFundacion = fechaFundacion,
            esMultinacional = esMultinacional,
            ingresoAnual = ingresoAnual
        )

        crudService.crearEmpresa(empresa)
        println("Empresa creada exitosamente.")
    }

    private fun listarEmpresas() {
        println("Lista de Empresas:")
        crudService.obtenerTodasLasEmpresas().forEach { empresa ->
            println("ID: ${empresa.id}, Nombre: ${empresa.nombre}, " +
                    "Fundación: ${formatoFecha.format(empresa.fechaFundacion)}, " +
                    "Multinacional: ${empresa.esMultinacional}, " +
                    "Ingreso Anual: ${empresa.ingresoAnual}")
            println("Empleados:")
            empresa.empleados.forEach { empleado ->
                println("  - ${empleado.nombre}")
            }
            println("---")
        }
    }

    private fun buscarEmpresa() {
        print("Ingrese el ID de la empresa: ")
        val id = scanner.nextLine().toInt()
        val empresa = crudService.obtenerEmpresa(id)
        if (empresa != null) {
            println("Detalles de la Empresa:")
            println("Nombre: ${empresa.nombre}")
            println("Fecha de Fundación: ${formatoFecha.format(empresa.fechaFundacion)}")
            println("Multinacional: ${empresa.esMultinacional}")
            println("Ingreso Anual: ${empresa.ingresoAnual}")
        } else {
            println("Empresa no encontrada.")
        }
    }

    private fun actualizarEmpresa() {
        print("Ingrese el ID de la empresa a actualizar: ")
        val id = scanner.nextLine().toInt()
        val empresaExistente = crudService.obtenerEmpresa(id)

        if (empresaExistente != null) {
            println("Ingrese los nuevos datos (deje en blanco para mantener el valor actual):")

            print("Nombre (actual: ${empresaExistente.nombre}): ")
            val nombre = scanner.nextLine().takeIf { it.isNotBlank() } ?: empresaExistente.nombre

            print("Fecha de Fundación (actual: ${formatoFecha.format(empresaExistente.fechaFundacion)}) (yyyy-MM-dd): ")
            val fechaInput = scanner.nextLine()
            val fechaFundacion = if (fechaInput.isNotBlank())
                formatoFecha.parse(fechaInput)
            else empresaExistente.fechaFundacion

            print("¿Es Multinacional? (actual: ${empresaExistente.esMultinacional}) (true/false): ")
            val esMultinacionalInput = scanner.nextLine()
            val esMultinacional = if (esMultinacionalInput.isNotBlank())
                esMultinacionalInput.toBoolean()
            else empresaExistente.esMultinacional

            print("Ingreso Anual (actual: ${empresaExistente.ingresoAnual}): ")
            val ingresoInput = scanner.nextLine()
            val ingresoAnual = if (ingresoInput.isNotBlank())
                ingresoInput.toDouble()
            else empresaExistente.ingresoAnual

            val empresaActualizada = Empresa(
                id = id,
                nombre = nombre,
                fechaFundacion = fechaFundacion,
                esMultinacional = esMultinacional,
                ingresoAnual = ingresoAnual
            )

            crudService.actualizarEmpresa(empresaActualizada)
            println("Empresa actualizada exitosamente.")
        } else {
            println("Empresa no encontrada.")
        }
    }

    private fun eliminarEmpresa() {
        print("Ingrese el ID de la empresa a eliminar: ")
        val id = scanner.nextLine().toInt()
        crudService.eliminarEmpresa(id)
        println("Empresa eliminada exitosamente.")
    }

    // Métodos para Empleados
    private fun crearEmpleado() {
        println("Crear Nuevo Empleado:")
        print("ID: ")
        val id = scanner.nextLine().toInt()
        print("Nombre: ")
        val nombre = scanner.nextLine()
        print("Fecha de Contratación (yyyy-MM-dd): ")
        val fechaContratacion = formatoFecha.parse(scanner.nextLine())
        print("¿Es Tiempo Completo? (true/false): ")
        val esTiempoCompleto = scanner.nextLine().toBoolean()
        print("Salario: ")
        val salario = scanner.nextLine().toDouble()
        print("ID de Empresa: ")
        val empresaId = scanner.nextLine().toInt()

        val empleado = Empleado(
            id = id,
            nombre = nombre,
            fechaContratacion = fechaContratacion,
            esTiempoCompleto = esTiempoCompleto,
            salario = salario,
            empresaId = empresaId
        )

        crudService.crearEmpleado(empleado)
        println("Empleado creado exitosamente.")
    }

    private fun listarEmpleados() {
        println("Lista de Empleados:")
        crudService.obtenerTodosLosEmpleados().forEach { empleado ->
            println("ID: ${empleado.id}, Nombre: ${empleado.nombre}, " +
                    "Contratación: ${formatoFecha.format(empleado.fechaContratacion)}, " +
                    "Tiempo Completo: ${empleado.esTiempoCompleto}, " +
                    "Salario: ${empleado.salario}, " +
                    "Empresa ID: ${empleado.empresaId}")
        }
    }

    private fun buscarEmpleado() {
        print("Ingrese el ID del empleado: ")
        val id = scanner.nextLine().toInt()
        val empleado = crudService.obtenerEmpleado(id)
        if (empleado != null) {
            println("Detalles del Empleado:")
            println("Nombre: ${empleado.nombre}")
            println("Fecha de Contratación: ${formatoFecha.format(empleado.fechaContratacion)}")
            println("Tiempo Completo: ${empleado.esTiempoCompleto}")
            println("Salario: ${empleado.salario}")
            println("Empresa ID: ${empleado.empresaId}")
        } else {
            println("Empleado no encontrado.")
        }
    }

    private fun actualizarEmpleado() {
        print("Ingrese el ID del empleado a actualizar: ")
        val id = scanner.nextLine().toInt()
        val empleadoExistente = crudService.obtenerEmpleado(id)

        if (empleadoExistente != null) {
            println("Ingrese los nuevos datos (deje en blanco para mantener el valor actual):")

            print("Nombre (actual: ${empleadoExistente.nombre}): ")
            val nombre = scanner.nextLine().takeIf { it.isNotBlank() } ?: empleadoExistente.nombre

            print("Fecha de Contratación (actual: ${formatoFecha.format(empleadoExistente.fechaContratacion)}) (yyyy-MM-dd): ")
            val fechaInput = scanner.nextLine()
            val fechaContratacion = if (fechaInput.isNotBlank())
                formatoFecha.parse(fechaInput)
            else empleadoExistente.fechaContratacion

            print("¿Es Tiempo Completo? (actual: ${empleadoExistente.esTiempoCompleto}) (true/false): ")
            val tiempoCompletoInput = scanner.nextLine()
            val esTiempoCompleto = if (tiempoCompletoInput.isNotBlank())
                tiempoCompletoInput.toBoolean()
            else empleadoExistente.esTiempoCompleto

            print("Salario (actual: ${empleadoExistente.salario}): ")
            val salarioInput = scanner.nextLine()
            val salario = if (salarioInput.isNotBlank())
                salarioInput.toDouble()
            else empleadoExistente.salario

            print("ID de Empresa (actual: ${empleadoExistente.empresaId}): ")
            val empresaIdInput = scanner.nextLine()
            val empresaId = if (empresaIdInput.isNotBlank())
                empresaIdInput.toInt()
            else empleadoExistente.empresaId

            val empleadoActualizado = Empleado(
                id = id,
                nombre = nombre,
                fechaContratacion = fechaContratacion,
                esTiempoCompleto = esTiempoCompleto,
                salario = salario,
                empresaId = empresaId
            )

            crudService.actualizarEmpleado(empleadoActualizado)
            println("Empleado actualizado exitosamente.")
        } else {
            println("Empleado no encontrado.")
        }
    }

    private fun eliminarEmpleado() {
        print("Ingrese el ID del empleado a eliminar: ")
        val id = scanner.nextLine().toInt()
        crudService.eliminarEmpleado(id)
        println("Empleado eliminado exitosamente.")
    }
}

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

class ArchivoRepository {
    private val formatoFecha = SimpleDateFormat("yyyy-MM-dd")

    fun guardarEmpresas(empresas: List<Empresa>) {
        val archivo = File("empresas.txt")
        archivo.writeText(
            empresas.joinToString("\n") { empresa ->
                "${empresa.id}|${empresa.nombre}|${formatoFecha.format(empresa.fechaFundacion)}|${empresa.esMultinacional}|${empresa.ingresoAnual}"
            }
        )
    }

    fun cargarEmpresas(): List<Empresa> {
        val archivo = File("empresas.txt")
        if (!archivo.exists()) return emptyList()

        return archivo.readLines().map { linea ->
            val datos = linea.split("|")
            Empresa(
                id = datos[0].toInt(),
                nombre = datos[1],
                fechaFundacion = formatoFecha.parse(datos[2]),
                esMultinacional = datos[3].toBoolean(),
                ingresoAnual = datos[4].toDouble()
            )
        }
    }

    fun guardarEmpleados(empleados: List<Empleado>) {
        val archivo = File("empleados.txt")
        archivo.writeText(
            empleados.joinToString("\n") { empleado ->
                "${empleado.id}|${empleado.nombre}|${formatoFecha.format(empleado.fechaContratacion)}|${empleado.esTiempoCompleto}|${empleado.salario}|${empleado.empresaId}"
            }
        )
    }

    fun cargarEmpleados(): List<Empleado> {
        val archivo = File("empleados.txt")
        if (!archivo.exists()) return emptyList()

        return archivo.readLines().map { linea ->
            val datos = linea.split("|")
            Empleado(
                id = datos[0].toInt(),
                nombre = datos[1],
                fechaContratacion = formatoFecha.parse(datos[2]),
                esTiempoCompleto = datos[3].toBoolean(),
                salario = datos[4].toDouble(),
                empresaId = datos[5].toInt()
            )
        }
    }
}
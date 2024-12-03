import java.util.Date
import java.text.SimpleDateFormat

data class Empresa(
    val id: Int,
    val nombre: String,
    val fechaFundacion: Date,
    val esMultinacional: Boolean,
    val ingresoAnual: Double,
    val empleados: MutableList<Empleado> = mutableListOf()
)
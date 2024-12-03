import java.util.Date
import java.text.SimpleDateFormat

data class Empleado(
    val id: Int,
    val nombre: String,
    val fechaContratacion: Date,
    val esTiempoCompleto: Boolean,
    val salario: Double,
    val empresaId: Int
)
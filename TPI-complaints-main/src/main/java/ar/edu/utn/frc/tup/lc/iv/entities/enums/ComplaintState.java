package ar.edu.utn.frc.tup.lc.iv.entities.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum que representa los posibles estados de una denuncia.
 * Los estados incluyen:
 * - Nueva: cuando la denuncia es recién creada.
 * - Pendiente: cuando la denuncia está en proceso de revisión.
 * - Rechazada: cuando la denuncia ha sido rechazada.
 * - Anexada: cuando la denuncia ha sido anexada a otro proceso o informe.
 */
public enum ComplaintState {
    NEW("Nueva"),
    PENDING("Pendiente"),
    SOLVED("Resuelta"),
    REJECTED("Rechazada"),
    ATTACHED("Anexada");

    /**
     * Valor que representa el estado de la denuncia en formato de cadena.
     */
    private final String value;

    /**
     * Constructor del enum que asigna el valor textual a cada estado.
     * @param value El valor textual asociado con el estado de la denuncia.
     */
    ComplaintState(String value) {
        this.value = value;
    }

    /**
     * Metodo sobrescrito para obtener la representación
     * textual del estado de la denuncia.
     * @return El valor textual del estado.
     */
    @Override
    public String toString() {
        return this.value;
    }

    /**
     * Metodo que devuelve el nombre del estado en formato de clave (nombre del enum).
     * @return El nombre del estado (clave).
     */
    public String getKey() {
        return this.name();
    }

    /**
     * Metodo que devuelve el valor asociado al estado,
     * que es su representación en formato de cadena.
     * @return El valor textual del estado.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Convierte todos los valores del enum en un mapa
     * con las claves como el nombre del estado
     * y los valores como la representación textual de cada estado.
     * Este mapa puede ser utilizado para enviar los valores al front-end.
     * @return Un mapa de claves y valores donde la clave es el nombre del estado
     * y el valor es la representación textual.
     */
    public static Map<String, String> toMap(){
        Map<String, String> map = new HashMap<>();

        for(ComplaintState state : ComplaintState.values()) {
            map.put(state.getKey(), state.getValue());
        }

        return map;
    }

}

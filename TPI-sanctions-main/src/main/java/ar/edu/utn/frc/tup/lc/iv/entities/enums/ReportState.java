package ar.edu.utn.frc.tup.lc.iv.entities.enums;

import java.util.HashMap;
import java.util.Map;
/**
 * Enum representing the different states a report can have.
 */
public enum ReportState {
    /**
     * The report is pending.
     */
    PENDING("Pendiente"),
    /**
     * The report is open.
     */
    OPEN("Abierto"),
    /**
     * The report is closed.
     */
    CLOSED("Cerrado"),
    /**
     * The report is rejected.
     */
    REJECTED("Rechazado"),
    /**
     * The report is ended.
     */
    ENDED("Finalizado");

    private final String value;

    ReportState(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
    /**
     * Gets the key of the report state.
     *
     * @return the name of the report state
     */
    public String getKey() {
        return this.name();
    }
    /**
     * Gets the value of the report state.
     * @return the string representation of the report state
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Converts the enum to a map to send it to the front end.
     * @return a map with the report state names
     * as keys and their string representations as values
     */
    public static Map<String, String> toMap(){
        Map<String, String> map = new HashMap<>();

        for(ReportState state : ReportState.values()) {
            map.put(state.getKey(), state.getValue());
        }

        return map;
    }

}

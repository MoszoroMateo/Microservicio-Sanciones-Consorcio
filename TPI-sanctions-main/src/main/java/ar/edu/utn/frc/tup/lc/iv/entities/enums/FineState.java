package ar.edu.utn.frc.tup.lc.iv.entities.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum representing the different states a fine can have.
 */
public enum FineState {
    /**
     * The fine is pending.
     */
    PENDING("Pendiente"),

    /**
     * The fine is pending payment.
     */
    PAYMENT_PAYMENT("Pendiente de pago"),

    /**
     * The fine has been paid.
     */
    PAYED("Pagada"),

    /**
     * The fine has been appealed.
     */
    APPEALED("Apelada"),

    /**
     * The fine has been acquitted.
     */
    ACQUITTED("Absuelta");

    /**
     * The string representation of the fine state.
     */
    private final String value;

    /**
     * Constructor for FineState.
     *
     * @param value the string representation of the fine state
     */
    FineState(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    /**
     * Gets the key of the fine state.
     *
     * @return the name of the fine state
     */
    public String getKey() {
        return this.name();
    }

    /**
     * Gets the value of the fine state.
     *
     * @return the string representation of the fine state
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Converts the enum to a map to send it to the front end.
     * @return a map with the fine state names
     * as keys and their string representations as values
     */
    public static Map<String, String> toMap(){
        Map<String, String> map = new HashMap<>();

        for(FineState state : FineState.values()) {
            map.put(state.getKey(), state.getValue());
        }

        return map;
    }

}

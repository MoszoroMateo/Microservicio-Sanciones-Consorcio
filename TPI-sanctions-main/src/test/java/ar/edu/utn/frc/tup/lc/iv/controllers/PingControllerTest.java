package ar.edu.utn.frc.tup.lc.iv.controllers;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PingControllerTest {
    @Test
    void pong_shouldReturnPong() {
        PingController pingController = new PingController();
        String response = pingController.pong();
        assertEquals("pong", response, "The pong method should return 'pong'");
    }

}
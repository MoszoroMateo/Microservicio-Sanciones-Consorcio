package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetDisclaimerDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostDisclaimerDto;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.DisclaimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * REST controller for managing disclaimers.
 */
@RestController
@RequestMapping("api/disclaimer")
public class DisclaimerController {
    /**
     * Service for handling disclaimer-related operations.
     */
    @Autowired
    private DisclaimerService disclaimerService;

    /**
     * Endpoint to create a new disclaimer.
     * @param disclaimer The disclaimer data transfer object.
     * @return The created disclaimer.
     */
    @PostMapping("/")
    public ResponseEntity<GetDisclaimerDto> postFine(@RequestBody PostDisclaimerDto disclaimer){
        return ResponseEntity.ok(disclaimerService.postDisclimer(disclaimer));
    }

}

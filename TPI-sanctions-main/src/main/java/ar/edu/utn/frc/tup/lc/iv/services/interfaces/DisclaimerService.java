package ar.edu.utn.frc.tup.lc.iv.services.interfaces;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetDisclaimerDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostDisclaimerDto;
import org.springframework.stereotype.Service;
/**
 * Service interface for managing disclaimers.
 */
@Service
public interface DisclaimerService {

    /**
     * Creates a new disclaimer.
     *
     * @param disclaimerDto the data transfer object
     * containing the disclaimer details
     * @return the created disclaimer
     */
    GetDisclaimerDto postDisclimer(PostDisclaimerDto disclaimerDto);

}

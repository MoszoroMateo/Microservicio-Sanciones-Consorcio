package ar.edu.utn.frc.tup.lc.iv.dtos.post;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Data Transfer Object for Post Disclaimer.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDisclaimerDto {

    /**
     * The ID of the user.
     * Cannot be null.
     */
    @NotNull(message = "El usuario no puede ser nulo")
    private Integer userId;

    /**
     * The ID of the fine.
     * Cannot be null.
     */
    @NotNull(message = "El ..... no puede ser nulo")
    private Integer fineId;

    /**
     * The disclaimer text.
     * Cannot be empty.
     */
    @NotEmpty(message = "El descargo no puede estar vacia")
    private String disclaimer;
}

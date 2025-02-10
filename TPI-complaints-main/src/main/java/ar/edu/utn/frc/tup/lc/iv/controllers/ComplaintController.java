package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetComplaintDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetReducedComplaintDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostComplaintDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutComplaintReportDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutComplaintStateDto;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.ComplaintService;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.PictureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/complaint")
@Tag(name = "Complaints", description = "Gestion de denuncias")
/**
 * Controller to manage complaints.
 * Allows operations like create, update, get and delete complaints.
 */
public class ComplaintController {

        @Autowired
        private ComplaintService complaintService;
        /**
         * Inyección de dependencia de la interfaz ComplaintService.
         */

        @Autowired
        private PictureService pictureService;
        /**
         * Inyección de dependencia de la interfaz PictureService.
         */

        // Respuestas de HTTP para disminuir strings
        public static final String OK = "200";
        /**
         * Código de respuesta: Solicitud exitosa.
         */
        public static final String BAD_REQUEST = "400";
        /**
         * Código de respuesta: Solicitud incorrecta.
         */
        public static final String INTERNAL_SERVER_ERROR = "500";
        /**
         * Código de respuesta: Error interno del servidor.
         */
        public static final String NOT_FOUND = "404";
        /**
         * Código de respuesta: No encontrado.
         */
        public static final String UNAUTHORIZED = "401";
        /**
         * Código de respuesta: No autorizado.
         */
        public static final String FORBIDDEN = "403";
        /**
         * Código de respuesta: Prohibido.
         */
        private static final String CONTENT_TYPE_JSON = "application/json";
        /**
         * Tipo de contenido JSON.
         */
        private static final String SERVER_ERROR_MESSAGE = "Error interno del servidor";
        /**
         * Mensaje de error interno del servidor.
         */



    // Post para una denuncia, recibe un "PostComplaintDto"
    // que incluye una lista (no obligatoria)
    // de MultipartFiles que se espera sean imagenes
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Registrar una nueva denuncia",
            description = "Este endpoint permite registrar una nueva denuncia cargando datos y archivos (pruebas) "
                  +  "en un formato multipart/form-data.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la denuncia a registrar: Se deben enviar en formato multipart/form-data desde el cliente."
            ),
            responses = {
                    @ApiResponse(
                            responseCode = OK,
                            description = "Denuncia registrada con éxito",
                            content = @Content(
                                    mediaType = CONTENT_TYPE_JSON,
                                    schema = @Schema(implementation = GetComplaintDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = BAD_REQUEST, description = "Datos inválidos o solicitud incorrecta"),
                    @ApiResponse(responseCode = INTERNAL_SERVER_ERROR, description = SERVER_ERROR_MESSAGE)
            }
    )
    public ResponseEntity<GetComplaintDto> postComplaint(@ModelAttribute PostComplaintDto postComplaintDto) {
        GetComplaintDto complaint = complaintService.createComplaint(postComplaintDto);

        return ResponseEntity.ok(complaint);
    }



    //Get para traer el listado (como un mapa) de todos los estados posibles de una denuncia
    @GetMapping("/states")
    @Operation(
            summary = "Obtener estados de las denuncias",
            description = "Este endpoint devuelve un mapa con los estados disponibles para "
                    +"las denuncias y sus descripciones en formato ENUM.",
            responses = {
                    @ApiResponse(
                            responseCode = OK,
                            description = "Estados de las denuncias obtenidos con éxito",
                            content = @Content(
                                    mediaType = CONTENT_TYPE_JSON,
                                    schema = @Schema(implementation = Map.class)
                            )
                    ),
                    @ApiResponse(responseCode = INTERNAL_SERVER_ERROR, description = SERVER_ERROR_MESSAGE)
            }
    )
    /**
        * Obtiene los estados de las denuncias.
        *
        * @return ResponseEntity con un mapa de los estados de las denuncias.
        */
    public ResponseEntity<Map<String, String>> getComplaintStates() {
        Map<String, String> complaintStates = complaintService.getAllComplaintStates();

        return ResponseEntity.ok(complaintStates);
    }


    @Operation(
            summary = "Obtener todas las denuncias",
            description = "Este endpoint devuelve una lista de todas las denuncias registradas.",
            responses = {
                    @ApiResponse(
                            responseCode = OK,
                            description = "Lista de denuncias obtenida con éxito",
                            content = @Content(
                                    mediaType = CONTENT_TYPE_JSON,
                                    schema = @Schema(implementation = GetReducedComplaintDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = INTERNAL_SERVER_ERROR, description = SERVER_ERROR_MESSAGE)
            }
    )
    @GetMapping("/all")
    /**
        * Obtiene todas las denuncias en lista.
        *
        * @return ResponseEntity con una lista de denuncias.
        */
    public ResponseEntity<List<GetReducedComplaintDto>> getAllReducedComplaints() {
        List<GetReducedComplaintDto> complaints = complaintService.getAllReducedComplaints();

        return ResponseEntity.ok(complaints);
    }




    //Get para una denuncia especifica por su id
    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener denuncia por ID",
            description = "Este endpoint devuelve una denuncia específica a partir de su ID. "
                    +"El ID debe ser un valor válido que corresponda a una denuncia existente.",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "El ID de la denuncia a obtener",
                            required = true,
                            example = "1"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = OK,
                            description = "Denuncia obtenida con éxito",
                            content = @Content(
                                    mediaType = CONTENT_TYPE_JSON,
                                    schema = @Schema(implementation = GetComplaintDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = NOT_FOUND, description = "Denuncia no encontrada con el ID proporcionado"),
                    @ApiResponse(responseCode = INTERNAL_SERVER_ERROR, description = SERVER_ERROR_MESSAGE)
            }
    )
    /**
        * Obtiene una denuncia por su ID.
        *
        * @param id El ID de la denuncia a obtener.
        * @return ResponseEntity con el objeto correspondiente a la denuncia.
        */
    public ResponseEntity<GetComplaintDto> getComplaintById(@PathVariable("id") int id) {
        GetComplaintDto complaint = complaintService.getComplaintById(id);

        return ResponseEntity.ok(complaint);
    }


    //Put para actualizar el estado de una denuncia
    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar estado de la denuncia",
            description = "Este endpoint permite actualizar el estado de una denuncia específica utilizando su ID"
                    + " y un objeto que contiene el nuevo estado.",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "El ID de la denuncia cuyo estado se desea actualizar",
                            required = true,
                            example = "1"
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Objeto con los datos del nuevo estado de la denuncia",
                    required = true,
                    content = @Content(
                            mediaType = CONTENT_TYPE_JSON,
                            schema = @Schema(implementation = PutComplaintStateDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = OK,
                            description = "Estado de la denuncia actualizado correctamente",
                            content = @Content(
                                    mediaType = CONTENT_TYPE_JSON,
                                    schema = @Schema(implementation = GetComplaintDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = NOT_FOUND, description = "Denuncia no encontrada con el ID proporcionado"),
                    @ApiResponse(responseCode = BAD_REQUEST, description = "Datos inválidos proporcionados"),
                    @ApiResponse(responseCode = INTERNAL_SERVER_ERROR, description = SERVER_ERROR_MESSAGE)
            }
    )
    /**
     * Actualiza el estado de una denuncia.
     *
     * @param id El ID de la denuncia a actualizar.
     * @param putComplaintStateDto El objeto que contiene
     * el nuevo estado de la denuncia.
     * @return ResponseEntity con la denuncia actualizada.
     */
    public ResponseEntity<GetComplaintDto> putComplaintState(@PathVariable("id") int id, @RequestBody
    PutComplaintStateDto putComplaintStateDto) {
        GetComplaintDto complaint = complaintService.updateComplaintState(id, putComplaintStateDto);

        return ResponseEntity.ok(complaint);
    }


//    @GetMapping("/getFile/{uuid}")
//    @Operation(
//            summary = "Obtener archivo por UUID",
//            description = "Este endpoint permite recuperar un archivo utilizando su UUID. Si el archivo es "+
//            "encontrado, se devuelve su contenido.",
//            parameters = {
//                    @Parameter(
//                            name = "uuid",
//                            description = "UUID único del archivo que se desea recuperar",
//                            required = true
//                    )
//            },
//            responses = {
//                    @ApiResponse(
//                            responseCode = "200",
//                            description = "Archivo encontrado y recuperado con éxito",
//                            content = @Content(
//                                    mediaType = "application/json",
//                                    schema = @Schema(type = "string")
//                            )
//                    ),
//                    @ApiResponse(
//                            responseCode = "400",
//                            description = "Solicitud incorrecta, por ejemplo, UUID no válido o archivo no encontrado"
//                    ),
//                    @ApiResponse(
//                            responseCode = "500",
//                            description = "Error interno del servidor"
//                    )
//            }
//    )
//    public ResponseEntity<String> getFile(@PathVariable("uuid") String uuid) {
//        String file = pictureService.getFileByUUID(uuid);
//        if (file != null) {
//            return ResponseEntity.ok(file);
//        }
//        return ResponseEntity.badRequest().build();
//    }

    @GetMapping("/getFiles/{complaintId}")
    @Operation(
            summary = "Obtener archivos por ID de denuncia",
            description = "Este endpoint permite recuperar todos los archivos asociados a una denuncia específica, dado su ID.",
            parameters = {
                    @Parameter(
                            name = "complaintId",
                            description = "ID único de la denuncia para la cual se desean recuperar los archivos asociados",
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = OK,
                            description = "Archivos recuperados exitosamente",
                            content = @Content(
                                    mediaType = CONTENT_TYPE_JSON,
                                    schema = @Schema(implementation = Map.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = BAD_REQUEST,
                            description = "Solicitud incorrecta, por ejemplo, si no se encuentra ningún archivo para esa denuncia"
                    ),
                    @ApiResponse(
                            responseCode = INTERNAL_SERVER_ERROR,
                            description = SERVER_ERROR_MESSAGE
                    )
            }
    )
    /**
        * Obtiene los archivos asociados a una denuncia por su ID.
        *
        * @param complaintId El ID de la denuncia.
        * @return ResponseEntity con los archivos o error.
        */
    public ResponseEntity<Map<String, String>> getFiles(@PathVariable("complaintId") int complaintId) {
        Map<String, String> files = pictureService.getFilesWithNameByComplaintId(complaintId);
        if (files != null && !files.isEmpty()) {
            return ResponseEntity.ok(files);
        }
        return ResponseEntity.badRequest().build();
    }


    //Get para todas las denuncias pertenecientes a un informe
    @GetMapping("/report/{id}")
    @Operation(
            summary = "Obtener denuncias por ID de informe",
            description = "Este endpoint permite obtener todas las denuncias asociadas a un informe específico, dado su ID.",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID único del informe para el cual se desean recuperar las denuncias asociadas",
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = OK,
                            description = "Denuncias asociadas al informe recuperadas exitosamente",
                            content = @Content(
                                    mediaType = CONTENT_TYPE_JSON,
                                    schema = @Schema(implementation = List.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = BAD_REQUEST,
                            description = "Solicitud incorrecta, por ejemplo, si no se encuentran denuncias para ese informe"
                    ),
                    @ApiResponse(
                            responseCode = INTERNAL_SERVER_ERROR,
                            description = SERVER_ERROR_MESSAGE
                    )
            }
    )
    /**
        * Obtiene las denuncias asociadas a un informe por su ID.
        *
        * @param reportId El ID del informe.
        * @return ResponseEntity con las denuncias asociadas.
        */
    public ResponseEntity<List<GetComplaintDto>> getComplaintsByReport(@PathVariable("id") Integer reportId) {
        List<GetComplaintDto> complaints = complaintService.getComplaintsByReport(reportId);

        return ResponseEntity.ok(complaints);
    }


    //Put para anexar y desanexar las denuncias relacionadas a un informe
    @PutMapping("/append")
    @Operation(
            summary = "Agregar denuncias a un informe",
            description = "Este endpoint permite agregar denuncias a un informe específico utilizando los datos "
                    + "proporcionados en el cuerpo de la solicitud.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Objeto que contiene las denuncias que se desean agregar al informe",
                    content = @Content(
                            mediaType = CONTENT_TYPE_JSON,
                            schema = @Schema(implementation = PutComplaintReportDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = OK,
                            description = "Denuncias agregadas al informe exitosamente",
                            content = @Content(
                                    mediaType = CONTENT_TYPE_JSON,
                                    schema = @Schema(implementation = List.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = BAD_REQUEST,
                            description = "Solicitud incorrecta, por ejemplo, datos inválidos en el cuerpo de la solicitud"
                    ),
                    @ApiResponse(
                            responseCode = INTERNAL_SERVER_ERROR,
                            description = SERVER_ERROR_MESSAGE
                    )
            }
    )
    /**
        * Añade denuncias a un informe.
        *
        * @param putComplaintReportDto Datos del informe con las denuncias a añadir.
        * @return ResponseEntity con las denuncias actualizadas.
        */
    public ResponseEntity<List<GetComplaintDto>> appendComplaints(@RequestBody PutComplaintReportDto putComplaintReportDto) {
        List<GetComplaintDto> complaints = complaintService.updateComplaintReport(putComplaintReportDto);

        return ResponseEntity.ok(complaints);
    }


}

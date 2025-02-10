package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.clients.FileManagerClient;
import ar.edu.utn.frc.tup.lc.iv.entities.PictureEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.PictureRepository;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.PictureService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar operaciones relacionadas con imágenes.
 */
@Service
public class PictureServiceImpl implements PictureService {

    /**
     * Cliente para gestionar archivos.
     */
    @Autowired
    private FileManagerClient fileManagerClient;

    /**
     * Repositorio de imágenes.
     */
    @Autowired
    private PictureRepository pictureRepository;

    private static final int MIN_FILE_DATA_LENGTH = 4;


    /**
     * Detecta el tipo MIME de un archivo.
     *
     * @param fileData Datos del archivo.
     * @return Tipo MIME detectado o "unknown" si no se reconoce.
     */
    public String detectMimeType(byte[] fileData) {


        if (fileData.length < MIN_FILE_DATA_LENGTH) {
            return "unknown";
        }

        // JPEG
        if (fileData[0] == (byte) 0xFF && fileData[1] == (byte) 0xD8) {
            return "image/jpeg";
        }

        // PNG
        if (fileData[0] == (byte) 0x89
               && fileData[1] == (byte) 0x50
               && fileData[2] == (byte) 0x4E
               && fileData[3] == (byte) 0x47) {
            return "image/png";
        }

        // GIF
        if (fileData[0] == (byte) 0x47
                && fileData[1] == (byte) 0x49
                && fileData[2] == (byte) 0x46
                && (fileData[3] == (byte) 0x38)) {
            return "image/gif";
        }

        // BMP
        if (fileData[0] == (byte) 0x42
                && fileData[1] == (byte) 0x4D) {
            return "image/bmp";
        }

        // TIFF
        if (fileData.length >= 4
           && ((fileData[0] == (byte) 0x49 && fileData[1] == (byte) 0x49 && fileData[2] == (byte) 0x2A && fileData[3] == 0)
           || (fileData[0] == (byte) 0x4D && fileData[1] == (byte) 0x4D && fileData[2] == (byte) 0x00 && fileData[3] == (byte) 0x2A))) {
            return "image/tiff";
        }

        // WEBP
        if (fileData.length >= 12
                && fileData[0] == (byte) 0x52
                && fileData[1] == (byte) 0x49
                && fileData[2] == (byte) 0x46
                && fileData[3] == (byte) 0x46
                && fileData[8] == (byte) 0x57
                && fileData[9] == (byte) 0x45
                && fileData[10] == (byte) 0x42
                && fileData[11] == (byte) 0x50) {
            return "image/webp";
        }

        // SVG
        if (fileData.length >= 4
                &&fileData[0] == '<'
                &&fileData[1] == 's'
                &&fileData[2] == 'v'
                &&fileData[3] == 'g') {
            return "image/svg+xml";
        }

        return "unknown"; // No es ninguno de esos tipos
    }



    /**
     * Obtiene los UUIDs de imágenes asociadas a una denuncia.
     *
     * @param complaintId ID de la denuncia.
     * @return Lista de UUIDs de imágenes.
     */
    @Override
    public List<String> getUUIDsByComplaintId(int complaintId) {
        List<PictureEntity> pictures = pictureRepository.findAll();
        return pictures.stream()
                .filter(picture -> picture.getComplaint().getId() == complaintId)
                .map(PictureEntity::getPictureUrl).collect(Collectors.toList());
    }

/**
//     * Extrae un UUID de una cadena JSON.
//     *
//     * @param jsonString Cadena JSON que contiene el UUID.
//     * @return UUID extraído o {@code null} si ocurre un error.
//     */
    @Override
    public String extractUuid(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            return jsonNode.get("uuid").asText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Obtiene un archivo con su nombre original a partir de su UUID.
     *
     * @param uuid UUID del archivo.
     * @return Mapa con el archivo en base64 como clave y el nombre como valor.
     */
    @Override
    public Map<String, String> getFileWithNameByUUID(String uuid) {
        ResponseEntity<byte[]> responseEntity = fileManagerClient.getFileWithNameByUUID(uuid);

        if (responseEntity == null) {
            return null;
        }

        byte[] file = responseEntity.getBody();
        String base64String = Base64.getEncoder().encodeToString(file);
        String originalFilename = responseEntity.getHeaders().getFirst("fileName");
        if (originalFilename != null && originalFilename.startsWith("temp-")) {

            int fixedPrefixLength = 25;
            if (originalFilename.length() > fixedPrefixLength) {
                originalFilename = originalFilename.substring(fixedPrefixLength);
            }
        }
        String mimeType = detectMimeType(file);
        String formatted = "data:" + mimeType + ";base64," + base64String;
        if (originalFilename == null) {
            originalFilename = "unknown";
        }
        //Devuelve la base64 y el nombre del archivo.
        return Map.of(formatted, originalFilename);
    }

    /**
     * Obtiene archivos con sus nombres asociados a una denuncia.
     *
     * @param complaintId ID de la denuncia.
     * @return Mapa de archivos en base64 con sus nombres.
     */
    @Override
    public Map<String, String> getFilesWithNameByComplaintId(int complaintId) {
        List<String> uuids = getUUIDsByComplaintId(complaintId);
        Map<String, String> files = new HashMap<>();
        for (String uuid : uuids) {
            Map<String, String> file = getFileWithNameByUUID(extractUuid(uuid));
            if (file != null) {
                files.putAll(file);
            }
        }
        return files;
    }
}

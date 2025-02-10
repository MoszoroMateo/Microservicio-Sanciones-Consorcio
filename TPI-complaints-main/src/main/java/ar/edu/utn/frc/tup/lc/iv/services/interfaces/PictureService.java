package ar.edu.utn.frc.tup.lc.iv.services.interfaces;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Interfaz del servicio para gestionar el FileManager.
 */
@Service
public interface PictureService {

    /**
     * Obtiene un archivo con su nombre original usando su UUID.
     *
     * @param uuid UUID del archivo.
     * @return Mapa con el archivo en Base64 como clave y el nombre del archivo como valor.
     */
    Map<String, String> getFileWithNameByUUID(String uuid);

    /**
     * Obtiene una lista de UUIDs de imágenes asociadas a una denuncia.
     *
     * @param complaintId ID de la denuncia.
     * @return Lista de UUIDs de imágenes.
     */
    List<String> getUUIDsByComplaintId(int complaintId);
    /**
     * Obtiene archivos en formato Base64 con sus nombres asociados por ID de denuncia.
     *
     * @param complaintId ID de la denuncia.
     * @return Mapa con archivos en Base64 como clave y nombres como valor.
     */
    Map<String, String> getFilesWithNameByComplaintId(int complaintId);

    /**
     * Extrae el UUID de un archivo desde una cadena JSON.
     *
     * @param jsonString Cadena JSON con el UUID.
     * @return UUID extraído o {@code null} si ocurre un error.
     */
    String extractUuid(String jsonString);
}

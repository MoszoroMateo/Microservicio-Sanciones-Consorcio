package ar.edu.utn.frc.tup.lc.iv.clients;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Service for managing file operations such as uploading and retrieving files.
 */
@Service
@NoArgsConstructor
public class FileManagerClient {

    /** Logger para el cliente FileManagerClient. */
    private static final Logger LOGGER = Logger.getLogger(FileManagerClient.class.getName());



    @Value("${API-FILEMANAGER}")
    private String fileManagerUrl;


    /** RestTemplate para consumir el FileManager. */
    @Autowired
    private RestTemplate restTemplate;


    /**
     * Sube un archivo al servidor.
     *
     * @param file Archivo a subir.
     * @return Respuesta del servidor.
     */



    public String uploadFile(MultipartFile file) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        String tempDir = System.getProperty("java.io.tmpdir");

        File tempFile = null;
        try {
            // Acá lo que hago es el proceso para poder enviarle un archivo, ya que por http
            // no se puede mandar uno, creo un archivo temporal y lo mando en el body.
            tempFile = new File(tempDir, file.getOriginalFilename());
            System.out.println("Temp file created at: " + tempFile.getAbsolutePath());
            file.transferTo(tempFile);

            body.add("file", new FileSystemResource(tempFile));

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);


            String uploadUrl = fileManagerUrl + "/fileManager/savefile";
            ResponseEntity<String> response = restTemplate.postForEntity(uploadUrl, requestEntity, String.class);
            //ResponseEntity<String> response = restTemplate.postForEntity(urlexter+"fileManager/savefile", requestEntity, String.class);


            if (!response.getStatusCode().is2xxSuccessful()) {
                return null;
            }

            return response.getBody();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error al subir el archivo", e);
            return null;
        } finally {
            // Acá elimino el archivo temporal para que no ocupe espacio porque sí.
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    /**
     * Obtiene un archivo por UUID.
     *
     * @param uuid UUID del archivo.
     * @return Archivo en bytes.
     */
    public byte[] getFileByUUID(String uuid) {

        //String url = urlexter +"fileManager/getFile/" + uuid;
        String getFileUrl = fileManagerUrl + "/fileManager/getFile/";
        String url = getFileUrl + uuid; //Docker


        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_OCTET_STREAM));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            return new byte[0];
        }
    }

    /**
     * Obtiene un archivo con nombre por UUID.
     *
     * @param uuid UUID del archivo.
     * @return Respuesta con el archivo en bytes.
     */
    public ResponseEntity<byte[]> getFileWithNameByUUID(String uuid) {

        //String url = urlexter+"fileManager/getFile/" + uuid;
        String getFile = fileManagerUrl + "/fileManager/getFile/";
        String url = getFile + uuid; //Docker


        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_OCTET_STREAM));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new byte[0]);
        }
    }
}

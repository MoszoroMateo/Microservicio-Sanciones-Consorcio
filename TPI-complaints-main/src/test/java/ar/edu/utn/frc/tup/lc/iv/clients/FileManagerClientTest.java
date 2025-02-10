package ar.edu.utn.frc.tup.lc.iv.clients;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;
@TestPropertySource(locations = "classpath:application.properties")
class FileManagerClientTest {

    @SpyBean
    FileManagerClient fileManagerClient;

    @MockBean
    RestTemplate restTemplate;

    @Test
    void uploadFile_Success() {
        //ToDo
    }

    @Test
    void getFiles_Success() {
        //ToDo
    }
}
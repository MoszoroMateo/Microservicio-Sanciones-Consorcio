package ar.edu.utn.frc.tup.lc.iv.clients;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetComplaintDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutComplaintReportDto;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


/**
 * Service client for handling complaint-related operations.
 * Provides methods to fetch, append, and manage complaints
 * via external API calls.
 */
@Service
@NoArgsConstructor
public class ComplaintClient {

    /**
     * Base URL for the complaint service, injected from application properties.
     */
    @Value("${complaint.url}")
    private String url;

    /**
     * RestTemplate used for making HTTP requests.
     * Handles the communication with external services.
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Fetches a complaint by its ID.
     *
     * @param complaintId The ID of the complaint to be retrieved.
     * @return GetComplaintDto containing the details of the complaint.
     * @throws ResponseStatusException if the response status code
     * is not successful.
     */
    public GetComplaintDto getComplaintById(Integer complaintId) {
        String complaintUrl = url + "/api/complaint/";
        ResponseEntity<GetComplaintDto> response = restTemplate.getForEntity(complaintUrl + complaintId, GetComplaintDto.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ResponseStatusException(response.getStatusCode(), "Error al obtener la denuncia");
        }

        return response.getBody();
    }

    /**
     * Fetches a list of complaints associated with a specific report ID.
     *
     * @param reportId The ID of the report whose complaints are to
     *                 be retrieved.
     * @return List<GetComplaintDto> containing the complaints associated
     * with the report.
     */
    public List<GetComplaintDto> getComplaintsByReportId(Integer reportId) {
        String complaintUrl = url + "/api/complaint/report/";
        ResponseEntity<GetComplaintDto[]> response = restTemplate.getForEntity(complaintUrl + reportId, GetComplaintDto[].class);
       return Arrays.stream(Objects.requireNonNull(response.getBody())).toList();
    }

    /**
     * Appends a complaint to a report.
     *
     * @param putComplaintReportDto The DTO containing the complaint and
     *                              report details to be appended.
     * @return List<GetComplaintDto> containing the updated list of
     * complaints for the report.
     */
    public List<GetComplaintDto> appendComplaint(PutComplaintReportDto putComplaintReportDto) {
        String complaintUrl = url + "/api/complaint/append";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PutComplaintReportDto> requestEntity = new HttpEntity<>(putComplaintReportDto, headers);

        ResponseEntity<GetComplaintDto[]> response = restTemplate.exchange(
                complaintUrl,
                HttpMethod.PUT,
                requestEntity,
                GetComplaintDto[].class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            return Collections.emptyList();
        }

        return Arrays.stream(response.getBody()).toList();
    }

}

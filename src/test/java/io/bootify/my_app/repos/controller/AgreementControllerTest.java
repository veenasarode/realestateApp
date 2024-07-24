package io.bootify.my_app.repos.controller;

import io.bootify.my_app.controller.AgreementController;
import io.bootify.my_app.domain.Agreement;
import io.bootify.my_app.dto.AgreementDto;
import io.bootify.my_app.exception.AgreementNotFoundException;
import io.bootify.my_app.exception.ResourceNotFoundException;
import io.bootify.my_app.repos.AgreementRepository;
import io.bootify.my_app.service.AgreementService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AgreementControllerTest {


    @InjectMocks
    private AgreementController agreementController;

    @Mock
    private AgreementService agreementService;

    @Mock
    private AgreementRepository agreementRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(agreementController).build();
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Test Success scenario for saving new agreement")
    public void testSaveAgreement()
    {
        AgreementDto agreementDto = new AgreementDto();
        agreementDto.setType("Rent Property");

        AgreementDto saveAgreement = new AgreementDto();
        saveAgreement.setAgreementId(1);
        saveAgreement.setType(agreementDto.getType());

        when(agreementService.addAgreement(agreementDto)).thenReturn(saveAgreement);

        ResponseEntity<AgreementDto> responseEntity = agreementController.createAgreement(agreementDto);

        Assertions.assertNotNull(responseEntity.getBody().getAgreementId());
        assertEquals(HttpStatus.CREATED.value() ,  responseEntity.getStatusCodeValue());
    }


    @Test
    @DisplayName("Test Success scenario for Fetching all agreements")
    public void testGetAllAgreement() {

        //Given
        int offset = 0;
        int pageSize = 10;
        String field = "id";

        Agreement agreement1 = new Agreement();
        agreement1.setAgreementId(1);
        agreement1.setType("Rent Property");

        Agreement agreement2 = new Agreement();
        agreement2.setAgreementId(2);
        agreement2.setType("Sell Property");

        List<Agreement> agreements = Arrays.asList(agreement1, agreement2);
        Page<Agreement> agreementPage = new PageImpl<>(agreements);

        when(agreementService.findAgreementWithPaginationAndSorting(offset, pageSize, field)).thenReturn(agreementPage);

        //when
        ResponseEntity<Object> responseEntity = agreementController.showAllAgreement(offset, pageSize, field);

        //Then
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        Page<Agreement> responseBody = (Page<Agreement>) responseEntity.getBody();
        assertEquals(2, responseBody.getTotalElements());
        assertEquals(agreements, responseBody.getContent());
    }



    @Test
    @DisplayName("Test Success scenario for updating  agreement")
    public void testUpdateAgreement()
    {
        AgreementDto agreementDto = new AgreementDto();
        agreementDto.setDuration(6.5);

        when(agreementService.updateAgreement(Mockito.any() , Mockito.anyInt())).thenReturn(agreementDto);

        ResponseEntity<AgreementDto> responseEntity = agreementController.updateAgreement(agreementDto , 1);

        assertEquals(6.5 , responseEntity.getBody().getDuration());

        Assertions.assertEquals(HttpStatus.OK.value() , responseEntity.getStatusCodeValue());
    }

   /* @Test
    public void testDeleteAgreementById() throws Exception {

        //Given
        Integer agreementId = 1;

        Agreement agreement = new Agreement();
        agreement.setAgreementId(agreementId);
        System.out.println(agreement.toString());

        Mockito.when(agreementRepository.findById(agreementId)).thenReturn(Optional.of(agreement));

        //When
        ResponseEntity<?> responseEntity = agreementController.deleteAgreementById(agreementId);
        System.out.println(responseEntity.getBody());
        System.out.println(responseEntity.getStatusCodeValue());

        //Then
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());

        Assertions.assertEquals("Agreement deleted Succesfully" , responseEntity.getBody());
    }*/

    @Test
    @DisplayName("Test Success scenario for get agreement by Id")
    public void testGetAgreementById() throws Exception {

        // Given
        Integer agreementId = 1;

        AgreementDto agreementDto = new AgreementDto(1,2.3,"rent property", 22.56);

        when(agreementService.getAgreementById(agreementDto.getAgreementId())).thenReturn(agreementDto);

        // When
        ResponseEntity<AgreementDto> response = agreementController.showAgreementById(agreementId);

        // Then
        verify(agreementService, times(1)).getAgreementById(agreementId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(agreementDto, response.getBody());
    }

    @Test
    @DisplayName("Test Success scenario for delete agreement")
    public void testDeleteAgreementById_Success() throws AgreementNotFoundException {
        // Given
        int agreementId = 1;
        try {
            doNothing().when(agreementService).deleteAgreementById(agreementId);

            // When
            ResponseEntity<?> responseEntity = agreementController.deleteAgreementById(agreementId);
            System.out.println(responseEntity);
            // Then

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            System.out.println(responseEntity.getStatusCode());
            assertEquals("Agreement deleted successfully.", responseEntity.getBody());
            System.out.println(responseEntity.getBody());
            verify(agreementService).deleteAgreementById(agreementId);
        }
        catch (AgreementNotFoundException e) {
            fail("Exception occurred: " + e.getMessage());
        }

    }

   /* @Test
    public void testGetAgreementById_NotFound() throws Exception {
        // Given
        //Integer agreementId = 1;
        AgreementDto agreementDto = new AgreementDto();
        when(agreementService.getAgreementById(agreementDto.getAgreementId())).thenReturn(null);

        // When
        ResponseEntity<AgreementDto> response = agreementController.showAgreementById(agreementDto.getAgreementId());

        // Then
        verify(agreementService, times(1)).getAgreementById(agreementDto.getAgreementId());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }*/

    @Test
    @DisplayName("Test Success scenario for fetching agreement by userId")
    public void testGetByUserId() throws Exception, ResourceNotFoundException {
        // Given
        Integer userId = 1;
        Integer agreementId = 20;

        AgreementDto agreementDto = new AgreementDto(agreementId , 2.5 , "Rent Property", 22555.33);

        when(agreementService.getAgreementByUserId(userId)).thenReturn(Collections.singletonList(agreementDto));

        // When
        ResponseEntity<List<AgreementDto>> response = agreementController.getAgreementByUser(userId);

        // Then
        verify(agreementService, times(1)).getAgreementByUserId(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(agreementDto, response.getBody().get(0));
    }

    @Test
    @DisplayName("Test Success scenario for fetching agreement by Broker")
    public void testGetByBrokerId() throws Exception, ResourceNotFoundException {
        // Given
        Integer brokerProfileId = 1;
        Integer agreementId = 20;

        AgreementDto agreementDto = new AgreementDto(agreementId , 2.5 , "Sell Property", 22555.33);

        when(agreementService.getAgreementByBrokerId(brokerProfileId)).thenReturn(Collections.singletonList(agreementDto));

        // When
        ResponseEntity<List<AgreementDto>> response = agreementController.getAgreementByBroker(brokerProfileId);

        // Then
        verify(agreementService, times(1)).getAgreementByBrokerId(brokerProfileId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(agreementDto, response.getBody().get(0));
    }

    @Test
    @DisplayName("Test Success scenario for fetching agreement by Property")
    public void testGetByPropertyId() throws Exception, ResourceNotFoundException {
        // Given
        Integer propertyId = 1;
        Integer agreementId = 20;

        AgreementDto agreementDto = new AgreementDto(agreementId , 2.5 , "Rent Property", 22555.33);

        when(agreementService.getAgreementByPropertyId(propertyId)).thenReturn(Collections.singletonList(agreementDto));

        // When
        ResponseEntity<List<AgreementDto>> response = agreementController.getAgreementByProperty(propertyId);

        // Then
        verify(agreementService, times(1)).getAgreementByPropertyId(propertyId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(agreementDto, response.getBody().get(0));
    }
}



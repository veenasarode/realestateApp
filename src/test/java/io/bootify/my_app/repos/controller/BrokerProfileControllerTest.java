package io.bootify.my_app.repos.controller;

import io.bootify.my_app.controller.BrokerProfileController;
import io.bootify.my_app.domain.Agreement;
import io.bootify.my_app.domain.BrokerProfile;
import io.bootify.my_app.dto.AgreementDto;
import io.bootify.my_app.dto.BrokerProfileDto;
import io.bootify.my_app.exception.BrokerProfileNotFoundException;
import io.bootify.my_app.exception.ResourceNotFoundException;
import io.bootify.my_app.service.BrokerProfileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
public class BrokerProfileControllerTest {
    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(brokerProfileController).build();
    }

    @Mock
    private BrokerProfileService brokerProfileService; // Mocked service

    @InjectMocks
    private BrokerProfileController brokerProfileController; // Controller under test

    private MockMvc mockMvc;


    @Test
    @DisplayName("Test Success scenario for saving new broker")
    public void testSaveBroker()
    {
        BrokerProfileDto brokerProfileDto = new BrokerProfileDto();
        brokerProfileDto.setName("abc");

        BrokerProfileDto saveBroker = new BrokerProfileDto();
        saveBroker.setBrokerProfileId(1);
        saveBroker.setName(brokerProfileDto.getName());

        when(brokerProfileService.addBrokerProfile(brokerProfileDto)).thenReturn(saveBroker);

        ResponseEntity<BrokerProfileDto> responseEntity = brokerProfileController.createBrokerProfile(brokerProfileDto);

        Assertions.assertNotNull(responseEntity.getBody().getBrokerProfileId());
        assertEquals(HttpStatus.CREATED.value() ,  responseEntity.getStatusCodeValue());
    }

    @Test
    @DisplayName("Test Success scenario for Fetching all brokers")
    public void testGetAllBroker() {

        //Given
        int offset = 0;
        int pageSize = 10;
        String field = "id";

        BrokerProfile broker1 = new BrokerProfile();
        broker1.setBrokerProfileId(1);
        broker1.setName("ABC");

        BrokerProfile broker2 = new BrokerProfile();
        broker2.setBrokerProfileId(2);
        broker2.setName("XYZ");

        List<BrokerProfile> brokerProfiles = Arrays.asList(broker1, broker2);
        Page<BrokerProfile>  brokerPage = new PageImpl<>(brokerProfiles);

        when(brokerProfileService.findBrokerProfileWithPaginationAndSorting(offset, pageSize, field)).thenReturn(brokerPage);

        //when
        ResponseEntity<Object> responseEntity = brokerProfileController.showAllBrokerProfile(offset, pageSize, field);

        //Then
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        Page<Agreement> responseBody = (Page<Agreement>) responseEntity.getBody();
        assertEquals(2, responseBody.getTotalElements());
        assertEquals(brokerProfiles, responseBody.getContent());
    }

    @Test
    @DisplayName("Test Success scenario for updating  broker")
    public void testUpdateBroker()
    {
        Integer brokerProfileId = 10;
        BrokerProfileDto brokerProfileDto = new BrokerProfileDto();
        brokerProfileDto.setCity("Mumbai");

        when(brokerProfileService.updateBrokerProfile(brokerProfileDto , brokerProfileId)).thenReturn(brokerProfileDto);

        ResponseEntity<BrokerProfileDto> responseEntity = brokerProfileController.editBrokerProfile(brokerProfileDto , brokerProfileId);

        assertEquals("Mumbai" , responseEntity.getBody().getCity());

        Assertions.assertEquals(HttpStatus.OK.value() , responseEntity.getStatusCodeValue());
    }

    @Test
    @DisplayName("Test Success scenario for get brokers by Id")
    public void testGetBrokerById() throws Exception {

        // Given
        Integer brokerProfileId = 1;

        BrokerProfileDto brokerProfileDto = new BrokerProfileDto(1,"ABCD","A235", "Mumbai","Thane");

        when(brokerProfileService.getBrokerProfileById(brokerProfileDto.getBrokerProfileId())).thenReturn(brokerProfileDto);

        // When
        ResponseEntity<?> response = brokerProfileController.showBrokerProfileById(brokerProfileId);

        // Then
        verify(brokerProfileService, times(1)).getBrokerProfileById(brokerProfileId);
        assertEquals(HttpStatus.OK, response.getStatusCodeValue());
        assertEquals(brokerProfileDto, response.getBody());
    }


    @Test
    @DisplayName("Test Success scenario for delete brokers by Id")
    public void testDeleteBrokerProfileById_Success() throws BrokerProfileNotFoundException {
        Integer brokerProfileId = 1;
        // Mocking behavior of service method
        doNothing().when(brokerProfileService).deleteBrokerProfileById(brokerProfileId);
        try {
        // Call the controller method
        ResponseEntity<?> responseEntity = brokerProfileController.deleteBrokerProfileById(brokerProfileId);

        // Verify the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Broker profile deleted successfully.", responseEntity.getBody());

        // Verify that the service method was called once with the correct argument
        verify(brokerProfileService, times(1)).deleteBrokerProfileById(brokerProfileId);
    } catch (BrokerProfileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    @DisplayName("Test Success scenario for fetching broker by userId")
    void testGetBrokerProfileByUserId() throws Exception {
        // Given
        Integer userId = 1;

        BrokerProfileDto broker1 = new BrokerProfileDto();
        broker1.setBrokerProfileId(1);
        broker1.setName("ABC");

        BrokerProfileDto broker2 = new BrokerProfileDto();
        broker2.setBrokerProfileId(2);
        broker2.setName("XYZ");

        List<BrokerProfileDto> brokers = Arrays.asList(broker1, broker2);

        when(brokerProfileService.getBrokerByUserId(userId)).thenReturn(brokers);

        // When
        ResponseEntity<List<BrokerProfileDto>> response = brokerProfileController.getBrokerProfileByUserId(userId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(brokers, response.getBody());
    }
}



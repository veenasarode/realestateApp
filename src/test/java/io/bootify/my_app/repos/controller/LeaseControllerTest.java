package io.bootify.my_app.repos.controller;

import io.bootify.my_app.controller.LeaseController;
import io.bootify.my_app.domain.Agreement;
import io.bootify.my_app.domain.Lease;
import io.bootify.my_app.dto.AgreementDto;
import io.bootify.my_app.dto.LeaseDto;
import io.bootify.my_app.dto.ResponseLeaseDto;
import io.bootify.my_app.exception.LeaseNotFoundException;
import io.bootify.my_app.exception.ResourceNotFoundException;
import io.bootify.my_app.exception.UserNotFound;
import io.bootify.my_app.service.LeaseService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class) // Enable Mockito support for JUnit 5
public class LeaseControllerTest {

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(leaseController).build();
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private LeaseService leaseService; // Mocked service

    @InjectMocks
    private LeaseController leaseController; // Controller under test

    private MockMvc mockMvc;

    @Test
    @DisplayName("Test Success scenario for saving new Lease")
    public void testSaveLease()
    {
        LeaseDto leaseDto = new LeaseDto();
        leaseDto.setStatus("Active");

        LeaseDto saveLease = new LeaseDto();
        saveLease.setLeaseId(1);
        saveLease.setStatus(leaseDto.getStatus());

        when(leaseService.addLease(leaseDto)).thenReturn(saveLease);

        ResponseEntity<LeaseDto> responseEntity = leaseController.createLease(leaseDto);

        Assertions.assertNotNull(responseEntity.getBody().getLeaseId());

        assertEquals(HttpStatus.CREATED.value() ,  responseEntity.getStatusCodeValue());
    }

    /*@Test
    @DisplayName("Test Success scenario for Fetching all lease")
    public void testGetAllLease() throws UserNotFound {

        //Given
        int pageNo = 0;
        int pageSize = 10;
        int totalPages = 100;


        Lease lease1 = new Lease();
        lease1.setLeaseId(1);
        lease1.setStatus("Active");

        Lease lease2 = new Lease();
        lease2.setLeaseId(2);
        lease2.setStatus("InActive");

        List<Lease> leases = Arrays.asList(lease1, lease2);
        LeaseDto leaseDto = new LeaseDto((Lease) leases);
            //    Page<Lease> leasePage = new PageImpl<>(leases);

        when(leaseService.getAllLeases( pageNo , pageSize)).thenReturn((List<LeaseDto>) leaseDto);

        //when
        ResponseLeaseDto responseLeaseDto = new ResponseLeaseDto("lease with pagination" , (List<LeaseDto>) leaseDto, totalPages);

        ResponseEntity<ResponseLeaseDto> responseEntity = leaseController.getAllLeases( pageNo , pageSize);

        //Then
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        Page<Agreement> responseBody = (Page<Agreement>) responseEntity.getBody();
        assertEquals(2, responseBody.getTotalElements());
        assertEquals(leases, responseBody.getContent());
    }*/

   /* @Test
    @DisplayName("Test Success scenario for Fetching all lease")
    void testGetAllLeases() throws Exception, UserNotFound {
        // Given
        int pageNo = 1;
        int pageSize = 10;

        LeaseDto lease1 = new LeaseDto();
        lease1.setLeaseId(1);
        lease1.setStatus("Active");

        LeaseDto lease2 = new LeaseDto();
        lease2.setLeaseId(2);
        lease2.setStatus("InActive");


        List<LeaseDto> leases = Arrays.asList(lease1 , lease2);

        when(leaseService.getAllLeases(pageNo, pageSize)).thenReturn(leases);

        when(leaseController.getTotalPagesForLeases(pageSize)).thenReturn(2);

        // When
        ResponseEntity<ResponseLeaseDto> response = leaseController.getAllLeases(pageNo, pageSize);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ResponseLeaseDto responseLeaseDto = response.getBody();
        assertEquals("success", responseLeaseDto.getMessage());
        assertEquals(leases, responseLeaseDto.getList());
        assertEquals(2, responseLeaseDto.getTotalPages());
    }*/

    @Test
    @DisplayName("Test Success scenario for updating  lease")
    public void testUpdateLease()
    {
        Integer leaseId = 1;

        LeaseDto leaseDto = new LeaseDto();

        leaseDto.setStatus("Active");

        when(leaseService.updateLease(Mockito.any() , Mockito.anyInt())).thenReturn(leaseDto);

        ResponseEntity<LeaseDto> responseEntity = leaseController.editLease(leaseDto , leaseId);

        assertEquals("Active" , responseEntity.getBody().getStatus());

        Assertions.assertEquals(HttpStatus.OK.value() , responseEntity.getStatusCodeValue());
    }

    @Test
    @DisplayName("Test Success scenario for get lease by Id")
    public void testGetLeaseById() throws Exception {

        // Given
        Integer leaseId = 1;

        LeaseDto leaseDto = new LeaseDto(leaseId ,"Active" );

        when(leaseService.getLeaseById(leaseDto.getLeaseId())).thenReturn(leaseDto);

        // When
        ResponseEntity<?> response = leaseController.getLeaseById(leaseId);

        // Then
        verify(leaseService, times(1)).getLeaseById(leaseId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(leaseDto, response.getBody());
    }



    @Test
    @DisplayName("Test Success scenario for fetching leases by user ID")
    public void testGetLeasesByUserId_Success() throws Exception {
        // Given
        Integer userId = 1;
        LeaseDto lease1 = new LeaseDto(1, "Active");
        LeaseDto lease2 = new LeaseDto(2, "Inactive");

        List<LeaseDto> leases = Arrays.asList(lease1, lease2);

        when(leaseService.getLeasesByUserId(userId)).thenReturn(leases);

        // When
        ResponseEntity<?> responseEntity = leaseController.getLeasesByUserId(userId);

        // Then
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(leases, responseEntity.getBody());
    }

    @Test
    @DisplayName("Test LeaseNotFoundException scenario for fetching leases by user ID")
    public void testGetLeasesByUserId_LeaseNotFoundException() throws Exception {
        // Given
        Integer userId = 1;

        when(leaseService.getLeasesByUserId(userId)).thenThrow(new LeaseNotFoundException("No leases found for User ID: " + userId));

        // When
        ResponseEntity<?> responseEntity = leaseController.getLeasesByUserId(userId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getStatusCodeValue());
        assertEquals("No leases found for User ID: " + userId, responseEntity.getBody());
    }

    @Test
    @DisplayName("Test Exception scenario for fetching leases by user ID")
    public void testGetLeasesByUserId_Exception() throws Exception {
        // Given
        Integer userId = 1;

        when(leaseService.getLeasesByUserId(userId)).thenThrow(new RuntimeException("Unexpected error"));

        // When
        ResponseEntity<?> responseEntity = leaseController.getLeasesByUserId(userId);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
        assertEquals("Failed to get leases by user ID: Unexpected error", responseEntity.getBody());
    }

    @Test
    @DisplayName("Test Success scenario for fetching leases by propertyOwner ID")
    public void testGetLeasesByPropertyOwnerId_Success() throws Exception {
        // Given
        Integer propertyOwnerId = 1;

        LeaseDto lease1 = new LeaseDto(1, "Active");
        LeaseDto lease2 = new LeaseDto(2, "Inactive");

        List<LeaseDto> leases = Arrays.asList(lease1, lease2);

        when(leaseService.getLeasesByPropertyOwnerId(propertyOwnerId)).thenReturn(leases);

        // When
        ResponseEntity<?> responseEntity = leaseController.getLeasesByPropertyOwnerId(propertyOwnerId);

        // Then
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(leases, responseEntity.getBody());
    }

   /* @Test
    @DisplayName("Test LeaseNotFoundException scenario for fetching leases by propertyOwner ID")
    public void testGetLeasesByPropertyOwnerId_LeaseNotFoundException() throws Exception {
        // Given
        Integer propertyOwnerId = 1;

        when(leaseService.getLeasesByPropertyOwnerId(propertyOwnerId)).thenThrow(new LeaseNotFoundException("No leases found for User ID: " + propertyOwnerId));

        // When
        ResponseEntity<?> responseEntity = leaseController.getLeasesByPropertyOwnerId(propertyOwnerId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getStatusCodeValue());
        assertEquals("No leases found for PropertyOwner ID: " + propertyOwnerId, responseEntity.getBody());
    }*/

   /* @Test
    @DisplayName("Test Exception scenario for fetching leases by propertyOwner ID")
    public void testGetLeasesByPropertyOwnerId_Exception() throws Exception {
        // Given
        Integer propertyOwnerId = 1;

        when(leaseService.getLeasesByPropertyOwnerId(propertyOwnerId)).thenThrow(new RuntimeException("Unexpected error"));

        // When
        ResponseEntity<?> responseEntity = leaseController.getLeasesByPropertyOwnerId(propertyOwnerId);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
        assertEquals("Failed to get leases by PropertyOwner ID: Unexpected error", responseEntity.getBody());
    }*/

    @Test
    @DisplayName("Test Success scenario for fetching leases by brokerProfile ID")
    public void testGetLeasesByBrokerProfileId_Success() throws Exception {

        // Given
        Integer brokerId = 1;

        LeaseDto lease1 = new LeaseDto(1, "Active");
        LeaseDto lease2 = new LeaseDto(2, "Inactive");

        List<LeaseDto> leases = Arrays.asList(lease1, lease2);

        when(leaseService.getLeasesByBrokerProfileId(brokerId)).thenReturn(leases);

        // When
        ResponseEntity<?> responseEntity = leaseController.getLeasesByBrokerProfileId(brokerId);

        // Then
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(leases, responseEntity.getBody());
    }


    @Test
    public void testDeleteLeaseById_Success() throws LeaseNotFoundException {
        // Given
        int leaseId = 1;
        try {
            doNothing().when(leaseService).deleteLeaseById(leaseId);

            // When
            ResponseEntity<?> responseEntity = leaseController.deleteLeaseById(leaseId);
            System.out.println(responseEntity);
            // Then

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            System.out.println(responseEntity.getStatusCode());
            assertEquals("Lease deleted successfully.", responseEntity.getBody());
            System.out.println(responseEntity.getBody());
            verify(leaseService).deleteLeaseById(leaseId);
        }
        catch (LeaseNotFoundException e) {
            fail("Exception occurred: " + e.getMessage());
        }

    }
}

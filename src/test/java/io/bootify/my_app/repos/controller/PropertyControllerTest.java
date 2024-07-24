package io.bootify.my_app.repos.controller;

import io.bootify.my_app.controller.PropertyController;
import io.bootify.my_app.dto.AgreementDto;
import io.bootify.my_app.dto.LeaseDto;
import io.bootify.my_app.dto.PropertyDto;
import io.bootify.my_app.exception.LeaseNotFoundException;
import io.bootify.my_app.exception.PropertyNotFoundException;
import io.bootify.my_app.service.PropertyService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
public class PropertyControllerTest {

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(propertyController).build();
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private PropertyService propertyService; // Mocked service

    @InjectMocks
    private PropertyController propertyController; // Controller under test

    private MockMvc mockMvc;

    @Test
    @DisplayName("Test Success scenario for saving new property")
    public void testSaveProperty()
    {
        PropertyDto propertyDto = new PropertyDto();
        propertyDto.setAddress("India");

        PropertyDto saveProperty = new PropertyDto();
        saveProperty.setPropertyId(1);
        saveProperty.setAddress(saveProperty.getAddress());

        when(propertyService.addProperty(propertyDto)).thenReturn(saveProperty);

        ResponseEntity<PropertyDto> responseEntity = propertyController.createProperty(propertyDto);

        Assertions.assertNotNull(responseEntity.getBody().getPropertyId());
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
    @DisplayName("Test Success scenario for updating  property")
    public void testUpdateProperty()
    {
        Integer propertyId = 1;

        PropertyDto propertyDto = new PropertyDto();

        propertyDto.setArea("Commercial");

        when(propertyService.updateProperty(Mockito.any() , Mockito.anyInt())).thenReturn(propertyDto);

        ResponseEntity<PropertyDto> responseEntity = propertyController.editProperty(propertyDto, propertyId);

        assertEquals("Commercial" , responseEntity.getBody().getArea());

        Assertions.assertEquals(HttpStatus.OK.value() , responseEntity.getStatusCodeValue());
    }

    @Test
    @DisplayName("Test Success scenario for get property by Id")
    public void testGetPropertyById() throws  PropertyNotFoundException {

        // Given
        Integer propertyId = 1;

        PropertyDto propertyDto = new PropertyDto(propertyId ,"David" );

        when(propertyService.getPropertyById(propertyDto.getPropertyId())).thenReturn(propertyDto);

        // When
        ResponseEntity<?> response = propertyController.getpropertyById(propertyId);

        // Then
        verify(propertyService, times(1)).getPropertyById(propertyId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(propertyDto, response.getBody());
    }



    @Test
    @DisplayName("Test Success scenario for fetching property by user ID")
    public void testGetPropertyByUserId_Success() throws Exception {
        // Given
        Integer userId = 1;
        PropertyDto property1 = new PropertyDto(1, "ABC");
        PropertyDto property2 = new PropertyDto(2, "XYZ");

        List<PropertyDto> propertyDtos = Arrays.asList(property1, property2);

        when(propertyService.getPropertiesByUserId(userId)).thenReturn(propertyDtos);

        // When
        ResponseEntity<?> responseEntity = propertyController.getPropertiesByUserId(userId);

        // Then
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(propertyDtos, responseEntity.getBody());
    }

    /*@Test
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
    }*/

    /*@Test
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
    }*/

    @Test
    @DisplayName("Test Success scenario for fetching leases by propertyOwner ID")
    public void testGetPropertyByPropertyOwnerId_Success() throws Exception {
        // Given
        Integer propertyOwnerId = 1;

        PropertyDto property1 = new PropertyDto(1, "ABC");
        PropertyDto property2 = new PropertyDto(2, "XYZ");

        List<PropertyDto> propertyDtos = Arrays.asList(property1, property2);

        when(propertyService.getPropertiesByPropertyOwnerId(propertyOwnerId)).thenReturn(propertyDtos);

        // When
        ResponseEntity<?> responseEntity = propertyController.getPropertiesByPropertyOwnerId(propertyOwnerId);

        // Then
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(propertyDtos, responseEntity.getBody());
    }




    @Test
    public void testDeletePropertyById_Success() throws PropertyNotFoundException {
        // Mocking behavior
        Integer propertyId = 1;
        try{
        doNothing().when(propertyService).deletePropertyById(propertyId);

        // Calling the controller method
        ResponseEntity<?> responseEntity = propertyController.deletePropertyById(propertyId);

        // Assertions
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(" Property deleted successfully.", responseEntity.getBody());
    } catch (PropertyNotFoundException e) {
            throw new RuntimeException(e);
        }
    }}


package io.bootify.my_app.repos.controller;

import io.bootify.my_app.controller.RentPersonController;
import io.bootify.my_app.domain.RentPerson;
import io.bootify.my_app.dto.LeaseDto;
import io.bootify.my_app.dto.RentPersonDto;
import io.bootify.my_app.exception.LeaseNotFoundException;
import io.bootify.my_app.exception.RentPersonNotFoundException;
import io.bootify.my_app.repos.RentPersonRepository;
import io.bootify.my_app.service.RentPersonService;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class) // Enable Mockito support for JUnit 5
public class RentPersonControllerTest {

    @Mock
    private RentPersonService rentPersonService; // Mocked service

    @Mock
    private RentPersonRepository rentPersonRepository;

    @InjectMocks
    private RentPersonController rentPersonController; // Controller under test

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(rentPersonController).build();
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Test Success scenario for saving new RentPerson")
    public void testSaveRentPerson()
    {
        RentPersonDto rentPersonDto = new RentPersonDto();
        rentPersonDto.setName("ABC");

        RentPersonDto saveRentPerson = new RentPersonDto();
        saveRentPerson.setRentPersonId(1);
        saveRentPerson.setName(rentPersonDto.getName());

        when(rentPersonService.createRentPerson(rentPersonDto)).thenReturn(saveRentPerson);

        ResponseEntity<RentPersonDto> responseEntity = rentPersonController.createRentPerson(rentPersonDto);

        Assertions.assertNotNull(responseEntity.getBody().getRentPersonId());

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
    @DisplayName("Test Success scenario for updating  RentPerson")
    public void testUpdateRentPerson()
    {
        Integer rentPersonId = 1;

        RentPersonDto rentPersonDto = new RentPersonDto();

        rentPersonDto.setMoNumber("123456789");

        when(rentPersonService.updateRentPerson(rentPersonId , rentPersonDto)).thenReturn(rentPersonDto);

        ResponseEntity<?> responseEntity = rentPersonController.updateRentPerson(rentPersonId , rentPersonDto);

        assertEquals("123456789" , rentPersonDto.getMoNumber());

        Assertions.assertEquals(HttpStatus.OK.value() , responseEntity.getStatusCodeValue());
    }


    @Test
    @DisplayName("Test Success scenario for get rentPerson by Id")
    public void testGetRentPersonById() throws Exception {

        // Given
        Integer rentPersonId = 1;

        RentPersonDto rentPersonDto = new RentPersonDto(rentPersonId ,"ABC" );

        when(rentPersonService.getRentPersonById(rentPersonDto.getRentPersonId())).thenReturn(rentPersonDto);

        // When
        ResponseEntity<RentPersonDto> response = rentPersonController.getRentPersonById(rentPersonId);

        // Then
        verify(rentPersonService, times(1)).getRentPersonById(rentPersonId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(rentPersonDto, response.getBody());
    }



    /*@Test
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
    }*/

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


    @DisplayName("Test Success scenario for deleting the rent person")
    @Test
    void deleteRentPerson_Success() throws  RentPersonNotFoundException{
        // Arrange
        Integer id = 1;
        try {
            doNothing().when(rentPersonService).deleteRentPerson(id);

            // Act
            ResponseEntity<?> responseEntity = rentPersonController.deleteRentPerson(id);

            // Assert
            assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
            verify(rentPersonService, times(1)).deleteRentPerson(id);
        } catch (RentPersonNotFoundException e) {
            throw new RuntimeException(e);
        }
    }}



package io.bootify.my_app.repos.controller;

import com.jayway.jsonpath.internal.path.PathCompiler;
import io.bootify.my_app.controller.AgreementController;
import io.bootify.my_app.controller.PropertyOwnerController;
import io.bootify.my_app.domain.Agreement;
import io.bootify.my_app.domain.PropertyOwner;
import io.bootify.my_app.dto.AgreementDto;
import io.bootify.my_app.dto.PropertyOwnerDto;
import io.bootify.my_app.exception.AgreementNotFoundException;
import io.bootify.my_app.exception.PropertyOwnerNotFoundException;
import io.bootify.my_app.repos.AgreementRepository;
import io.bootify.my_app.repos.PropertyRepository;
import io.bootify.my_app.service.AgreementService;
import io.bootify.my_app.service.PropertyOwnerService;
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
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PropertyOwnerControllerTest {
    @InjectMocks
    private PropertyOwnerController propertyOwnerController;

    @Mock
    private PropertyOwnerService propertyOwnerService;

    @Mock
    private PropertyRepository propertyRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(propertyOwnerController).build();
        MockitoAnnotations.openMocks(this);
    }

    /*@Test
    public void testCreatePropertyOwner() throws Exception {
        // Given
        PropertyOwnerDto propertyOwnerDto = new PropertyOwnerDto(1,"123456789", "john.doe@example.com");

        when(propertyOwnerService.createPropertyOwner(propertyOwnerDto)).thenReturn(propertyOwnerDto);

        // When
        ResponseEntity<User> response = userController.createUser(user);

        // Then
        verify(userService, times(1)).createUser(user);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }*/

    @Test
    @DisplayName("Test Success scenario for saving new propertyOwner")
    public void testSavePropertyOwner()
    {
        PropertyOwnerDto propertyOwnerDto = new PropertyOwnerDto();
        propertyOwnerDto.setAddress("Mumbai");

        PropertyOwnerDto savePropertyOwner = new PropertyOwnerDto();
        savePropertyOwner.setPropertyOwnerId(1);
        savePropertyOwner.setAddress(propertyOwnerDto.getAddress());

        when(propertyOwnerService.createPropertyOwner(propertyOwnerDto)).thenReturn(savePropertyOwner);

        ResponseEntity<?> responseEntity;
        responseEntity = propertyOwnerController.createPropertyOwner(propertyOwnerDto);

        Assertions.assertNotNull(responseEntity.getBody().toString());
        assertEquals(HttpStatus.OK.value() , responseEntity.getStatusCodeValue());

        /*verify(propertyOwnerService, times(1)).createPropertyOwner(propertyOwnerDto);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(propertyOwnerDto, responseEntity.getBody());*/
    }

    /*@Test
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
    }*/


   /* @Test
    @DisplayName("Test Success scenario for updating only price of agreement")
    public void testUpdatePropertyPrice() {
        AgreementDto agreementDto = new AgreementDto();
        agreementDto.setDuration(6.5);

        when(agreementService.updateAgreement(Mockito.any(), Mockito.anyInt())).thenReturn(agreementDto);

        ResponseEntity<AgreementDto> responseEntity = agreementController.updateAgreement(agreementDto, 1);

        assertEquals(6.5, responseEntity.getBody().getDuration());

        // Assertions.assertEquals(HttpStatus.OK.value() , responseEntity.getStatusCodeValue());
    }*/


    /*@Test
    public void testGetAgreementById() throws Exception {

        // Given
        Integer agreementId = 1;

        AgreementDto agreementDto = new AgreementDto(1, 2.3, "rent property", 22.56);

        when(agreementService.getAgreementById(agreementDto.getAgreementId())).thenReturn(agreementDto);

        // When
        ResponseEntity<AgreementDto> response = agreementController.showAgreementById(agreementId);

        // Then
        verify(agreementService, times(1)).getAgreementById(agreementId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(agreementDto, response.getBody());
    }*/

    /*@Test
    @DisplayName("Test Success scenario for deleting the property owner")
    public void testDeletePropertyOwner_Success() throws PropertyOwnerNotFoundException {
        int propertyOwnerId = 2;
        try {

            doNothing().when(propertyOwnerService).deletePropertyOwner(propertyOwnerId);

            ResponseEntity<?> response = propertyOwnerController.deletePropertyOwner(propertyOwnerId);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("Property Owner deleted successfully", response.getBody());
            verify(propertyOwnerService, times(1)).deletePropertyOwner(propertyOwnerId);
        } catch (Exception e) {
            PathCompiler.fail("Exception occurred: " + e.getMessage());
        }
*/

    }







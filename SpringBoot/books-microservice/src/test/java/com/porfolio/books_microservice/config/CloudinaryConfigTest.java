package com.porfolio.books_microservice.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cloudinary.Cloudinary;

@ExtendWith(MockitoExtension.class)
class CloudinaryConfigTest {

    @Mock
    private CloudinaryProperties cloudinaryProperties;

    @InjectMocks
    private CloudinaryConfig cloudinaryConfig;

    @Test
    void testCloudinaryBeanCreation() {
        // Arrange
        when(cloudinaryProperties.getCloudName()).thenReturn("test-cloud");
        when(cloudinaryProperties.getApiKey()).thenReturn("12345");
        when(cloudinaryProperties.getApiSecret()).thenReturn("secret");

        // Act
        Cloudinary cloudinary = cloudinaryConfig.cloudinary();

        // Assert
        assertNotNull(cloudinary);

        assertEquals("test-cloud", cloudinary.config.cloudName);
        assertEquals("12345", cloudinary.config.apiKey);
        assertEquals("secret", cloudinary.config.apiSecret);

        assertTrue(cloudinary.getClass().getName().contains("Cloudinary")); // Validación indirecta
    }

    @Test
    void testCloudinaryBeanCreationWithEmptyValues() {
        // Arrange
        when(cloudinaryProperties.getCloudName()).thenReturn("");
        when(cloudinaryProperties.getApiKey()).thenReturn("");
        when(cloudinaryProperties.getApiSecret()).thenReturn("");

        // Act
        Cloudinary cloudinary = cloudinaryConfig.cloudinary();

        // Assert
        assertNotNull(cloudinary);

        assertEquals("", cloudinary.config.cloudName);
        assertEquals("", cloudinary.config.apiKey);
        assertEquals("", cloudinary.config.apiSecret);

        assertTrue(cloudinary.getClass().getName().contains("Cloudinary")); // Validación indirecta
    }

    @Test
    void testCloudinaryBeanCreationWithNullValues() {
        // Arrange
        when(cloudinaryProperties.getCloudName()).thenReturn(null);
        when(cloudinaryProperties.getApiKey()).thenReturn(null);
        when(cloudinaryProperties.getApiSecret()).thenReturn(null);

        // Act
        Cloudinary cloudinary = cloudinaryConfig.cloudinary();

        // Assert
        assertNotNull(cloudinary);

        // Verificar los valores de configuración directamente desde cloudinary.config
        assertNull(cloudinary.config.cloudName);
        assertNull(cloudinary.config.apiKey);
        assertNull(cloudinary.config.apiSecret);

        assertTrue(cloudinary.getClass().getName().contains("Cloudinary")); // Validación indirecta
    }
}
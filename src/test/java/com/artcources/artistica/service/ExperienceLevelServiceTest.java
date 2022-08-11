package com.artcources.artistica.service;

import com.artcources.artistica.model.entity.ExperienceLevelEntity;
import com.artcources.artistica.model.enums.ExperienceLevelEnum;
import com.artcources.artistica.repository.ExperienceLevelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExperienceLevelServiceTest {

    @Mock
    private ExperienceLevelRepository experienceLevelRepository;
    private ExperienceLevelService toTest;

    @BeforeEach
    void setUp() {
        toTest = new ExperienceLevelService(experienceLevelRepository);
    }

    @Test
    void testInit() {
        when(experienceLevelRepository.count()).thenReturn(0l);
        toTest.init();
        verify(experienceLevelRepository, times(ExperienceLevelEnum.values().length)).save(any(ExperienceLevelEntity.class));
    }

}

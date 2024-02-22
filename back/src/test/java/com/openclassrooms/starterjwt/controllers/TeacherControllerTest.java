package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TeacherControllerTest {

    @Mock
    private TeacherMapper teacherMapper;

    @Mock
    private TeacherService teacherService;

    private TeacherController teacherController;

    private Teacher mockTeacher;

    private TeacherDto mockTeacherDto;

    @BeforeEach
    void init(){
        teacherController = new TeacherController(teacherService, teacherMapper);
        mockTeacher = new Teacher(22L,"Blanc","Michel",null,null);
        mockTeacherDto = new TeacherDto(22L,"Blanc","Michel",null,null);
    }

    @Test
    void findByIdCatchErrorTest(){
        assertThrows(NumberFormatException.class,() -> teacherService.findById(Long.valueOf("test")));
    }

    @Test
    void findByIdTeacherNullTest(){
        when(teacherService.findById(Long.valueOf("22"))).thenReturn(null);

        ResponseEntity response = teacherController.findById("22");

        verify(teacherService,times(1)).findById(Long.valueOf("22"));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void findByIdTest(){
        when(teacherService.findById(Long.valueOf("22"))).thenReturn(mockTeacher);
        when(teacherMapper.toDto(mockTeacher)).thenReturn(mockTeacherDto);

        ResponseEntity response = teacherController.findById("22");
        TeacherDto teacherDto = (TeacherDto) response.getBody();

        verify(teacherMapper,times(1)).toDto(mockTeacher);
        verify(teacherService,times(1)).findById(Long.valueOf("22"));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(teacherDto.getId(),mockTeacher.getId());
    }
}

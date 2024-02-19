package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;
    private TeacherService teacherService;

    private List<Teacher> teacherList = new ArrayList<>();

    Teacher mockTeacher = new Teacher();
    Long teacherId;

    @BeforeEach
    public void init(){
        teacherId = 999L;
        mockTeacher.setId(teacherId);
        teacherService = new TeacherService(teacherRepository);

        Teacher teacher1 = new Teacher();
        teacher1.setId(1L);
        Teacher teacher2 = new Teacher();
        teacher2.setId(2L);
        Teacher teacher3 = new Teacher();
        teacher3.setId(3L);

        teacherList.add(teacher1);
        teacherList.add(teacher2);
        teacherList.add(teacher3);
    }

    @Test
    public void isFindAllCalledAndReturnAllTeachers(){
        when(teacherRepository.findAll()).thenReturn(teacherList);

        List<Teacher> teacherListResult = teacherRepository.findAll();

        assertEquals(teacherListResult.size(),teacherList.size());
        assertEquals(teacherListResult.get(0).getId(),teacherList.get(0).getId());

        verify(teacherRepository, times(1)).findAll();
    }

    @Test
    public void isFindTeacherCalledAndFindTeacher(){
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.ofNullable(mockTeacher));
        Teacher teacher = teacherService.findById(teacherId);

        assertNotNull(teacher);
        assertEquals(teacherId, teacher.getId());
        verify(teacherRepository, times(1)).findById(teacherId);
    }

    @Test
    public void isFindTeacherCalledAndReturnNull(){
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.empty());

        assertNull(teacherService.findById(teacherId));
        verify(teacherRepository, times(1)).findById(teacherId);
    }
}

package com.alexandr1017.edtechschool.controller;


import com.alexandr1017.edtechschool.dto.CourseDto;
import com.alexandr1017.edtechschool.service.CourseService;
import com.alexandr1017.edtechschool.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServletTest {

    private CourseService courseService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter stringWriter;
    private PrintWriter writer;

    @BeforeEach
    void setUp() {
        courseService = mock(CourseService.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);
    }

    @Test
    void doGet() throws IOException {

        when(response.getWriter()).thenReturn(writer);

        CourseDto courseDto = new CourseDto();
        courseDto.setId(1);
        courseDto.setName("Test Course");
        List<CourseDto> courseDtos = Arrays.asList(courseDto);

        when(courseService.findAll()).thenReturn(courseDtos);

        CourseServlet courseServlet = new CourseServlet();
        courseServlet.setCourseService(courseService);
        courseServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding(StandardCharsets.UTF_8.name());
        String result = stringWriter.toString();
        assertTrue(result.contains("\"id\":1"));
        assertTrue(result.contains("\"name\":\"Test Course\""));
    }

    @Test
    void doGet_withCourseId() throws IOException {
        when(response.getWriter()).thenReturn(writer);

        String courseId = "1";
        when(request.getPathInfo()).thenReturn("/" + courseId);

        CourseDto courseDto = new CourseDto();
        courseDto.setId(1);
        courseDto.setName("Test Course");

        when(courseService.getCourseById(Integer.parseInt(courseId))).thenReturn(courseDto);

        CourseServlet courseServlet = new CourseServlet();
        courseServlet.setCourseService(courseService);
        courseServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding(StandardCharsets.UTF_8.name());
        String result = stringWriter.toString();
        assertTrue(result.contains("\"id\":1"));
        assertTrue(result.contains("\"name\":\"Test Course\""));
    }


    @Test
    void doPost() throws IOException {


        when(response.getWriter()).thenReturn(writer);

        String jsonRequest = "{\"id\":1,\"name\":\"Test Course\"}";

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonRequest)));

        CourseDto courseDto = new CourseDto();

        courseDto.setId(1);

        courseDto.setName("Test Course");

        CourseServlet courseServlet = new CourseServlet();
        courseServlet.setCourseService(courseService);
        courseServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void doPut() throws IOException {


        when(response.getWriter()).thenReturn(writer);

        String jsonRequest = "{\"id\":1,\"name\":\"Test Course\"}";

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonRequest)));

        CourseDto courseDto = new CourseDto();

        courseDto.setId(1);

        courseDto.setName("Test Course");

        CourseServlet courseServlet = new CourseServlet();
        courseServlet.setCourseService(courseService);
        courseServlet.doPut(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void doDelete() throws IOException {


        when(request.getPathInfo()).thenReturn("/1");

        CourseServlet courseServlet = new CourseServlet();
        courseServlet.setCourseService(courseService);
        courseServlet.doDelete(request, response);

        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
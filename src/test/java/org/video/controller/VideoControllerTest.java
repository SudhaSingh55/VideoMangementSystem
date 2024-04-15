package org.video.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.video.repository.VideoRepository;
import org.video.request.VideoRequest;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class VideoControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

   /* @Autowired
    private VideoRepository videoRepository;*/

    @WithMockUser(value = "admin")
    @Test
    public void basicVideoCrudTest() throws Exception {
        VideoRequest request = new VideoRequest()
                .setVideoId("1")
                .setTitle("TESTVIDEO")
                .setDescription("metadata description for video1")
                .setFormat("mpv4")
                .setResolution("1920x1080")
                .setDuration(1);

        //Create video
        mockMvc.perform(post("/videos", 42L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        //Get video
        mockMvc.perform(get("/videos/{id}", "1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.title").value("TESTVIDEO"));

        //Update video
        request.setDuration(2);
        mockMvc.perform(put("/videos/{id}", "1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        //Delete video
        request.setDuration(2);
        mockMvc.perform(delete("/videos/{id}", "1")
                        .contentType("application/json")
                        .accept("application/json"))
                        .andExpect(status().isOk());

    }


    //@Test
    public void videoProcessingTest() throws Exception {
        VideoRequest request = new VideoRequest()
                .setVideoId("1")
                .setTitle("TESTVIDEO")
                .setDescription("metadata description for video1")
                .setFormat("mpv4")
                .setResolution("1920x1080")
                .setDuration(1);

        //Create video
        mockMvc.perform(post("/videos", 42L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/video-process/{id}", "1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value("Video Processing Started"));

    }



}


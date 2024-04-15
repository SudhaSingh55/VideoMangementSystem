package org.video.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.video.entity.Video;
import org.video.exception.ApiException;
import org.video.exception.DataNotFound;
import org.video.repository.VideoRepository;
import org.video.request.VideoRequest;
import org.video.response.ApiResponse;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
public class VideoServiceTest {
    @Mock
    VideoRepository videoRepository;
    @InjectMocks
    VideoService videoService;

    VideoRequest request;

    public VideoServiceTest(){
        this.request = new VideoRequest()
                .setVideoId("1")
                .setTitle("TESTVIDEO")
                .setDescription("metadata description for video1")
                .setFormat("mpv4")
                .setResolution("1920x1080")
                .setDuration(1);
    }

    @Test
    public void createVideo(){

        Video video = new Video();
        Mockito.when(videoRepository.save(video)).thenReturn(null);

        ApiResponse apiresponse = videoService.createVideo(request);
        Assertions.assertEquals("Video Created Successfully", apiresponse.getMessage());
    }

    @Test
    public void createVideoExceptionCases(){
        Video video = new Video();
        Mockito.when(videoRepository.save(video)).thenReturn(null);

        //Validation error for Missing parameters
        VideoRequest requestCase1 = new VideoRequest()
                .setVideoId("1")
                .setFormat("mpv4")
                .setDuration(1);
        ApiException apiException = assertThrows(ApiException.class, ()-> videoService.createVideo(requestCase1));
        String message = apiException.getMessage();
        assertEquals("(Title,Resolution) Mandatory parameters are missing!!!",message);


        //resolution format validation
        request.setResolution("19201080");
        apiException = assertThrows(ApiException.class, ()-> videoService.createVideo(request));
        message = apiException.getMessage();
        assertEquals("Resolution should be in (<<length>>X<<width>>) format",message);

        //Validate exception for video already exist
        Optional<Video> videoResult = Optional.of(video);
        Mockito.when(videoRepository.findById("1")).thenReturn(videoResult);
        VideoRequest requestCase2 = new VideoRequest()
                .setVideoId("1")
                .setTitle("TESTVIDEO")
                .setDescription("metadata description for video1")
                .setFormat("mpv4")
                .setResolution("1920x1080")
                .setDuration(1);
        apiException = assertThrows(ApiException.class, ()-> videoService.createVideo(requestCase2));
        message = apiException.getMessage();
        assertEquals("Video already exist. Please try update instead of insert!!",message);

    }


    @Test
    public void getVideo(){
        Video video = new Video();
        video.setId("1");
        Optional<Video> videoResult = Optional.of(video);
        Mockito.when(videoRepository.findById("1")).thenReturn(videoResult);

        Video result = videoService.getVideo("1");
        Assertions.assertEquals("1", result.getId());
    }

    @Test
    public void getVideoException(){
        Video video = new Video();
        video.setId("1");
        Optional<Video> videoResult = Optional.of(video);
        Mockito.when(videoRepository.findById("1")).thenReturn(videoResult);

        DataNotFound ex = assertThrows(DataNotFound.class, ()-> videoService.getVideo("2"));
        String message = ex.getMessage();
        assertEquals("Video does not exist. Please try inserting first!!",message);
    }

    @Test
    public void updateVideo(){
        request.setDuration(2);
        request.setFormat("mp3");

        Video video = new Video();
        video.setId("1");
        Optional<Video> videoResult = Optional.of(video);
        Mockito.when(videoRepository.findById("1")).thenReturn(videoResult);
        Mockito.when(videoRepository.save(video)).thenReturn(null);

        ApiResponse apiresponse = videoService.updateVideo("1",request);
        Assertions.assertEquals("Video Updated Successfully", apiresponse.getMessage());

    }

    @Test
    public void updateVideoException(){
        Video video = new Video();
        video.setId("1");
        Optional<Video> videoResult = Optional.of(video);
        Mockito.when(videoRepository.findById("1")).thenReturn(videoResult);

        DataNotFound ex = assertThrows(DataNotFound.class, ()-> videoService.updateVideo("2",request));
        String message = ex.getMessage();
        assertEquals("Video does not exist. Please try inserting first!!",message);

    }

    @Test
    public void deleteVideo(){

        Video video = new Video();
        video.setId("1");
        Optional<Video> videoResult = Optional.of(video);
        Mockito.when(videoRepository.findById("1")).thenReturn(videoResult);

        ApiResponse apiresponse = videoService.deleteVideo("1");
        Assertions.assertEquals("Video Deleted Successfully", apiresponse.getMessage());
    }

    @Test
    public void deleteVideoException(){

        Video video = new Video();
        video.setId("1");
        Optional<Video> videoResult = Optional.of(video);
        Mockito.when(videoRepository.findById("1")).thenReturn(videoResult);

        DataNotFound ex = assertThrows(DataNotFound.class, ()-> videoService.deleteVideo("2"));
        String message = ex.getMessage();
        assertEquals("Video does not exist. Please try inserting first!!",message);
    }



}

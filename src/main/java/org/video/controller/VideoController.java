package org.video.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.video.entity.Video;
import org.video.request.VideoRequest;
import org.video.response.ApiResponse;
import org.video.service.VideoProcessService;
import org.video.service.VideoService;

@Api(value = "Video Metadata Management System Rest APIs for video metadata management")
@RestController()
@RequestMapping("/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;
    private final VideoProcessService videoProcessService;

    @ApiOperation(value = "Create new video REST API")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "successful operation") })
    @PostMapping()
    public ResponseEntity<?> createVideo(@RequestBody VideoRequest request) {
        ApiResponse apiAddResponse = videoService.createVideo(request);
        return new ResponseEntity<>(apiAddResponse, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get video REST API")
    @GetMapping("/{id}")
    public ResponseEntity<?> getVideo(@PathVariable String id) {
        Video video = videoService.getVideo(id);
        return ResponseEntity.ok(video);
    }

    @ApiOperation(value = "Update video REST API")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateVideo(@PathVariable String id, @RequestBody VideoRequest request) {
        ApiResponse apiAddResponse = videoService.updateVideo(id, request);
        return  ResponseEntity.ok(apiAddResponse);
    }

    @ApiOperation(value = "Delete video REST API")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVideo(@PathVariable String id) {
        ApiResponse apiAddResponse = videoService.deleteVideo(id);
        return  ResponseEntity.ok(apiAddResponse);
    }

    @ApiOperation(value = "Start video processing REST API")
    @GetMapping("/video-process/{id}")
    public ResponseEntity<?> processVideo(@PathVariable String id) throws InterruptedException {
        ApiResponse apiAddResponse = videoProcessService.startVideoProcessing(id);
        return ResponseEntity.ok(apiAddResponse);
    }




}

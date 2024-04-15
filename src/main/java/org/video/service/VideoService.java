package org.video.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.video.entity.Video;
import org.video.exception.ApiException;
import org.video.exception.DataNotFound;
import org.video.exception.DatabasePersistenceException;
import org.video.repository.VideoRepository;
import org.video.request.VideoRequest;
import org.video.response.ApiResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class VideoService {

    private final VideoRepository videoRepository;

    public ApiResponse createVideo(VideoRequest request){
        String mandatoryFields = validateMandatoryFields(request);
        if(mandatoryFields!=null){
            throw new ApiException(mandatoryFields);
        }

        if(videoRepository.findById(request.getVideoId()).isPresent()){
            throw new ApiException("Video already exist. Please try update instead of insert!!");
        }

        Video video = new Video(request);
        setResolution(request.getResolution(), video);
        try {
            videoRepository.save(video);
            return new ApiResponse().setMessage("Video Created Successfully")
                    .setResult(true);
        }catch(Exception e){
            log.info("error:"+e.getMessage());
            throw new DatabasePersistenceException("Exception while persisting into database: "+e.getMessage());
        }
    }

    public Video getVideo(String id){
        if(videoRepository.findById(id).isPresent()){
            return videoRepository.findById(id).get();

        }else{
            throw new DataNotFound("Video does not exist. Please try inserting first!!");
        }
    }

    public ApiResponse updateVideo(String id, VideoRequest request){
        request.setVideoId(id);
        if(videoRepository.findById(id).isPresent()){
            Video video = videoRepository.findById(id).get();
            updateVideoFields(request, video);

            try {
                videoRepository.save(video);
                return new ApiResponse().setMessage("Video Updated Successfully")
                        .setResult(true);
            }catch(Exception e){
                log.info("error:"+e.getMessage());
                throw new DatabasePersistenceException("Exception while Update into database: "+e.getMessage());
            }

        }else{
            throw new DataNotFound("Video does not exist. Please try inserting first!!");
        }
    }

    public ApiResponse deleteVideo(String id){
        if(!videoRepository.findById(id).isPresent()){
            throw new DataNotFound("Video does not exist. Please try inserting first!!");
        }

        try {
            videoRepository.deleteById(id);
            return new ApiResponse().setMessage("Video Deleted Successfully")
                    .setResult(true);
        }catch(Exception e){
            log.info("error:"+e.getMessage());
            throw new DatabasePersistenceException("Exception while Deleting into database: "+e.getMessage());
        }

    }

    private void updateVideoFields(VideoRequest request, Video video){
        if(request.getTitle()!=null){
            video.setTitle(request.getTitle());
        }
        if(request.getDescription()!=null){
            video.setDescription(request.getDescription());
        }
        if(request.getDuration()!=0){
            video.setDuration(request.getDuration());
        }
        if(request.getResolution()!=null){
            setResolution(request.getResolution(),video);
        }
        if(request.getFormat()!=null){
            video.setFormat(request.getFormat());
        }
        if(request.getStatus()!=null){
            video.setStatus(request.getStatus());
        }
    }

    private String validateMandatoryFields(VideoRequest request){
        List<String> missingList = new ArrayList<>();
        if(request.getVideoId()==null){
            missingList.add("Video Id");
        }
        if(request.getTitle()==null){
            missingList.add("Title");
        }
        if(request.getDuration()==0){
            missingList.add("Duration");
        }
        if(request.getResolution()==null){
            missingList.add("Resolution");
        }
        if(request.getFormat()==null){
            missingList.add("Format");
        }

        if(CollectionUtils.isEmpty(missingList))
            return null;
        else{
            return "("+String.join(",", missingList) + ") Mandatory parameters are missing!!!";
        }
    }

    private void setResolution(String resolution, Video video){
        if(!StringUtils.isEmpty(resolution)) {
            if (resolution.contains("x")) {
                final String[] resolutionArray = resolution.split("x");
                if (resolutionArray.length < 1) {
                    throw new ApiException("Resolution length and width pixel values should be provided");
                }
                video.setLengthPixel(Long.parseLong(resolutionArray[0]));
                video.setWidthPixel(Long.parseLong(resolutionArray[1]));

            } else {
                throw new ApiException("Resolution should be in (<<length>>X<<width>>) format");
            }
        }
    }
}

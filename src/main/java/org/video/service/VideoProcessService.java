package org.video.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.stereotype.Service;
import org.video.entity.Video;
import org.video.exception.ApiException;
import org.video.exception.DataNotFound;
import org.video.repository.VideoRepository;
import org.video.response.ApiResponse;

import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class VideoProcessService {
    private final VideoRepository videoRepository;

    private enum Status {
        STARTED,
        PROCESSING,
        COMPLETED;
    }

    public ApiResponse startVideoProcessing(String id) throws InterruptedException {
        val startTime = System.currentTimeMillis();
        try {
            if (!videoRepository.findById(id).isPresent()) {
                throw new DataNotFound("Video does not exist. Please try inserting first!!");
            }
            Video video = videoRepository.findById(id).get();
            video.setStatus(Status.STARTED.toString());
            videoRepository.save(video);
            //Thread.sleep(60000);

            ExecutorService executor = Executors.newFixedThreadPool(10);
            log.info("Video started for videoID={} and time={}ms:",id,System.currentTimeMillis());
            Runnable runnableTaskProcessing = () -> {
                try {
                    log.info("Video delay for processing started, videoID={} and time={}ms:",id,System.currentTimeMillis() - startTime);
                    video.setStatus(Status.PROCESSING.toString());
                    videoRepository.save(video);
                    Thread.sleep(30000);
                    video.setStatus(Status.COMPLETED.toString());
                    videoRepository.save(video);
                    log.info("Video processing completed, videoID={} and time={}ms:",id,System.currentTimeMillis() - startTime);
                } catch (InterruptedException e) {
                    log.info("Error start runnable task:"+e.getMessage());
                }
            };

            Future<?> future = executor.submit(runnableTaskProcessing);
            try {
               future.get();
                executor.shutdown();
            } catch (InterruptedException | ExecutionException e) {
               throw new ApiException("Thread has been interrupted "+ Thread.currentThread().getName());
            }
            return new ApiResponse().setMessage("Video Processing Completed")
                    .setResult(true);

        }catch(Exception e ){
            log.info("Error in processing video:"+e.getMessage());
            throw e;
        }

    }

}

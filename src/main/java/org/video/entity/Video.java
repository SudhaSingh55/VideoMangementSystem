package org.video.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.video.request.VideoRequest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "VIDEO")
@Data
@RequiredArgsConstructor
public class Video implements Serializable {
    @Id
    @Column(name = "ID")
    private String id;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "UPLOAD_TIMESTAMP")
    private LocalDateTime uploadTimestamp;
    @Column(name = "UPDATED_TIMESTAMP")
    private LocalDateTime updatedTimestamp;
    @Column(name = "DURATION")
    private long duration;
    @Column(name = "LENGTH_PIXEL")
    private long lengthPixel;
    @Column(name = "WIDTH_PIXEL")
    private long widthPixel;
    @Column(name = "FORMAT")
    private String format;
    @Column(name = "STATUS")
    private String status;

    public Video(VideoRequest request){
        this.id = request.getVideoId();
        this.title = request.getTitle();
        this.description = request.getDescription();
        this.updatedTimestamp = LocalDateTime.now();
        this.uploadTimestamp = LocalDateTime.now();
        this.duration = request.getDuration();
        this.format = request.getFormat();

    }



}

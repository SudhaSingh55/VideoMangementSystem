package org.video.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class VideoRequest implements Serializable {
    private String videoId;
    private String title;
    private String description;
    private long duration;
    private String resolution;
    private String format;
    private String status;
}

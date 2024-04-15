package org.video.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.video.entity.Video;
@Repository
public interface VideoRepository extends JpaRepository<Video, String> {
}

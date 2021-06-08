package com.main.app.repository.album;

import com.main.app.domain.model.album.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}

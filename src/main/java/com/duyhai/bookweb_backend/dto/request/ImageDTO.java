package com.duyhai.bookweb_backend.dto.request;

import lombok.Data;

@Data
public class ImageDTO {
    private String name;
    private boolean isIcon;
    private String link;
    private String dataImage;
}

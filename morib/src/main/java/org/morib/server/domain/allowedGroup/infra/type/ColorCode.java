package org.morib.server.domain.allowedGroup.infra.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ColorCode {
    ALLOWED_SERVICE_COLOR_CODE_RED("RED", "#FF8080"),
    ALLOWED_SERVICE_COLOR_CODE_ORANGE("ORANGE", "#FFB62F"),
    ALLOWED_SERVICE_COLOR_CODE_YELLOW("YELLOW", "#FFF787"),
    ALLOWED_SERVICE_COLOR_CODE_LIGHT_GREEN("LIGHT_GREEN", "#B6FFA5"),
    ALLOWED_SERVICE_COLOR_CODE_MEDIUM_GREEN("MEDIUM_GREEN", "#5CE082"),
    ALLOWED_SERVICE_COLOR_CODE_HEAVY_GREEN("HEAVY_GREEN", "#179F62"),
    ALLOWED_SERVICE_COLOR_CODE_MINT("MINT", "#06FFD2"),
    ALLOWED_SERVICE_COLOR_CODE_LIGHT_BLUE("LIGHT_BLUE", "#27C5FF"),
    ALLOWED_SERVICE_COLOR_CODE_HEAVY_BLUE("HEAVY_BLUE", "#3D6DFF"),
    ALLOWED_SERVICE_COLOR_CODE_PURPLE("PURPLE", "#7742FF"),
    ALLOWED_SERVICE_COLOR_CODE_PINK("PINK", "#FF74F8"),
    ALLOWED_SERVICE_COLOR_CODE_GREY("GREY", "#868C93");

    private final String colorName;
    private final String hexCode;
}

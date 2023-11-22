package forwarding.agent.api.exceptions;

import java.time.ZonedDateTime;

public record ErrorResponse(
        int statusCode,
        String message,
        ZonedDateTime timeStamp
) {
}
package com.netcracker.edu.varabey.exception.handler;

import com.netcracker.edu.varabey.exception.remote.RemoteAPIException;
import org.apache.commons.io.IOUtils;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        String theString = IOUtils.toString(response.getBody(), StandardCharsets.UTF_8.name());
        throw new RemoteAPIException(theString, response.getStatusCode());
    }

    @Override
    public boolean hasError(ClientHttpResponse clienthttpresponse) throws IOException {
        return !clienthttpresponse.getStatusCode().is2xxSuccessful();
    }
}
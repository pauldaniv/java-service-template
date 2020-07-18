package com.pauldaniv.template.client;

import com.pauldaniv.template.model.TestOne;
import feign.Param;
import feign.RequestLine;

public interface TestClient {

    @RequestLine("GET /{isbn}")
    TestOne findByIsbn(@Param("isbn") String isbn);

//    @RequestLine("POST")
//    @Headers("Content-Type: application/json")
//    void create(TestOne book);
}

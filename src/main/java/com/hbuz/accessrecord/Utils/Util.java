package com.hbuz.accessrecord.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {

    static public String objectToString(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}

package com.example.minibankc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/27/22
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T>  {

    @NotNull(message = "#Status code cant be null")
    @JsonProperty("status_code")
    private int statusCode;
    private String message;
    private  String path;//transient
    private String informationLink;
    @JsonIgnore
    private int httpStatus;
    @JsonProperty("elapsed_time")
    private Long elapsedTime;
    @JsonProperty("count")
    private Long count;// use when we need paging
    @JsonProperty("pages_count")
    private Long pagesCount;// use when we need paging
    private List<T> payload=new ArrayList<>();

}

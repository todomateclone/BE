package com.team6.todomateclone.todo.dto.createdto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.*;

@ApiModel(value = "투두 Requset Dto")
@Getter
public class RequestCreateTodoDto { //여기는 받기만

    @ApiModelProperty(value = "투두 내용", dataType = "String", example = "프로틴 구입")
    @NotEmpty
    @Size(min = 1, max = 255)
    private String content;

    @ApiModelProperty(value = "투두 등록연도", dataType = "Long", example = "2022")
    @NotNull
    @Positive //양수만 허용
    @Min(1980) //최소값 1980, 추억을 기록할 수도 있음
    @Max(3000) //최대값 3000년
    private Long todoYear;

    @ApiModelProperty(value = "투두 등록월", dataType = "Long", example = "12")
    @NotNull
    @Positive //양수만 허용
    @Min(1) //최소값 1월
    @Max(12) //최대값 12월
    private Long todoMonth;

    @ApiModelProperty(value = "투두 등록일", dataType = "Long", example = "27")
    @NotNull
    @Positive //양수만 허용
    @Min(1) //최소값 1일
    @Max(31) //최대값 31일
    private Long todoDay;

    //Builder를 통해 RequestDto -> ServiceDto로 변경
    public CreateTodoDto toCreateTodoDto(){
        return CreateTodoDto.builder()
                .content(content)
                .todoYear(todoYear)
                .todoMonth(todoMonth)
                .todoDay(todoDay)
                .build();
    }

}

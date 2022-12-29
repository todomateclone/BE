package com.team6.todomateclone.todo.dto.updatedto;

import com.team6.todomateclone.common.exception.ValidationGroups;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.*;

@ApiModel(value = "투두 수정 Request Dto")
@Getter
public class RequestUpdateTodoDto {

    @ApiModelProperty(value = "투두 내용", dataType = "String", example = "프로틴 구입")
    @NotEmpty(message = "Todo를 입력해주세요.", groups = ValidationGroups.NotEmptyGroup.class)
    @Size(min = 1, max = 255, message = "Todo는 1글자 이상 255글자 이하입니다.", groups = ValidationGroups.SizeCheckGroup.class)
    private String content;

    @ApiModelProperty(value = "투두 등록연도", dataType = "Long", example = "2022")
    @NotNull(message = "연도를 입력해주세요.")
    @Positive(message = "연도는 양수로만 입력해주세요")
    @Min(value = 1980, message = "연도의 최소값은 1980입니다.")
    @Max(value = 3000, message = "연도의 최대값은 1980입니다.")
    private Long todoYear;

    @ApiModelProperty(value = "투두 등록월", dataType = "Long", example = "12")
    @NotNull(message = "월을 입력해주세요.")
    @Positive(message = "월은 양수로만 입력해주세요.")
    @Min(value = 1, message = "월의 최소값은 1월 입니다.")
    @Max(value = 12, message = "월의 최대값은 12월 입니다.")
    private Long todoMonth;

    @ApiModelProperty(value = "투두 등록일", dataType = "Long", example = "27")
    @NotNull(message = "날짜를 입력해주세요.")
    @Positive(message = "날짜는 양수로만 입력해주세요.")
    @Min(value = 1, message = "날짜의 최소값은 1일 입니다.")
    @Max(value = 31, message = "날짜의 최대값은 31일 입니다.")
    private Long todoDay;


    //Builder를 통해 RequestDto -> ServiceDto로 변경
    public UpdateTodoDto toUpdateTodoDto(){
        return UpdateTodoDto.builder()
                .content(content)
                .todoYear(todoYear)
                .todoMonth(todoMonth)
                .todoDay(todoDay)
                .build();
    }
}

package com.team6.todomateclone.todo.dto.updatedto;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.*;

@Getter
@ToString
public class RequestUpdateTodoDto {

    @NotEmpty
    @Size(min = 1, max = 255)
    private String content;

    @NotNull
    @Positive //양수만 허용
    @Min(1980) //최소값 1980, 추억을 기록할 수도 있음
    @Max(3000) //최대값 3000년
    private Long todoYear;

    @NotNull
    @Positive //양수만 허용
    @Min(1) //최소값 1월
    @Max(12) //최대값 12월
    private Long todoMonth;

    @NotNull
    @Positive //양수만 허용
    @Min(1) //최소값 1일
    @Max(31) //최대값 31일
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

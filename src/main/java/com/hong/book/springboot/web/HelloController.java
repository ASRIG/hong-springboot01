package com.hong.book.springboot.web;

import com.hong.book.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


// 컨트롤러를 JSON을 반환하는 컨트롤러로 만들어준다.
// 예전에는 @ResponseBody를 각 메소드마다 선언했던 것을 한번에 사용할 수 있게 해준다.
@RestController
public class HelloController {
    // HTTP Method인 Get의 요청을 받을 수 있는 API를 만들어준다.
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name,
                                     // 외부 API로 넘긴 파라미터를 가져오는 어노테이션
                                     //     외부 amount란 이름으로 넘긴 파라미터 메소드를 메소드 파라미터 int amount에 저장하게 한다.
                                     @RequestParam("amount") int amount){
        return new HelloResponseDto(name, amount);
    }
}

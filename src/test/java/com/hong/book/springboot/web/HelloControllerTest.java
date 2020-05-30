package com.hong.book.springboot.web;


import com.hong.book.springboot.web.HelloController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 테스트를 진행할 때, JUnit에 내장된 실행자 외에 다른 실행자를 실행시킨다.
//      여기서는 SpringRunner라는 스프링 실행자를 사용한다.
//      즉, 스프링부트 테스트와 JUnit 사이의 연결자 역할을 한다.
@RunWith(SpringRunner.class)
// 여러 스프링 어노테이션 중, Web(Spring MVC)에 집중할 수 있는 어노테이션 Controller관련만 사용가능.
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {
    // 스프링이 관리하는 빈(Bean)을 주입받는다.
    @Autowired
    // 웹 API를 테스트할 때 사용한다. 스프링MVC 테스트의 시작점.
    //      이 클래스를 통해 HTTP GET, POST 등에 대한 API를 테스트할 수 있다.
    private MockMvc mvc;

    @Test
    public void hello() throws Exception{
        String hello = "hello";

        // MockMvc를 통해 /hello주소로 HTTP GET 요청을 한다. 체이닝이 지원되어 여러 검증을 이어서 선언할 수 있다.
        mvc.perform(get("/hello"))
                // mvc.perform의 결과를 검증한다. HTTP Header의 Status를 검증한다.
                .andExpect(status().isOk())
                // mvc.perform의 결과를 검증한다. 응답 본문의 내용을 검증한다. Controller에서 "hello"를 리턴학디 때문에 이 값이 맞는지 검증.
                .andExpect(content().string(hello));
    }
}

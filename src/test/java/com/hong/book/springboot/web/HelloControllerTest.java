package com.hong.book.springboot.web;


import com.hong.book.springboot.web.HelloController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    public void hello가_리턴된다() throws Exception{
        String hello = "hello";

        // MockMvc를 통해 /hello주소로 HTTP GET 요청을 한다. 체이닝이 지원되어 여러 검증을 이어서 선언할 수 있다.
        mvc.perform(get("/hello"))
                // mvc.perform의 결과를 검증한다. HTTP Header의 Status를 검증한다.
                .andExpect(status().isOk())
                // mvc.perform의 결과를 검증한다. 응답 본문의 내용을 검증한다. Controller에서 "hello"를 리턴학디 때문에 이 값이 맞는지 검증.
                .andExpect(content().string(hello));
    }

    @Test
    public void helloDto가_리턴된다() throws Exception{
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                        // API테스트를 할 때 사용될 요청 파라미터를 설정. 단 값은 String만 허용된다.
                        // 그래서 숫자/날짜 등의 데이터를 등록할 때에도 문자열로 변경해야한다.
                        .param("name", name)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                // JSON 응닶값을 필드별로 검증할 수 있는 메소, $를 기준으로 필드명을 명시.
                // 여기서는 name과 amount를 ㄱ검증하니, $.name과 $.amount로 검증한다.
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}

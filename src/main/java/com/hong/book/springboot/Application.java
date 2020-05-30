package com.hong.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 메인 클래스로 SpringBootApplication이 있는 위치부터 설정을 읽기 때문에
    // 항상 최상단에 위치해야한다.
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        // 내장 WAS (Web Application Server, 웹 애플리케이션 서버)
        // : 별도로 외부에 WAS를 두지않고 애플리케이션을 실행할 때 내부에서 WAS를 실행하는 것을 이야기한다. 이렇게 되면 항상 Tomcat을 설치 할
        //      필요가 없어지며, 스프링 부트로 만들어진 Jar파일(실행가능한 Java 패키징 파일)로 실행하면 된다.
        SpringApplication.run(Application.class, args);
    }
}

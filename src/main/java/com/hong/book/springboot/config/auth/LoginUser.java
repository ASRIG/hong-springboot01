package com.hong.book.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 이 어노테이션이 생성될 수 있는 위치를 지정한다. PARAMETER로 선언 했으므로 메소드의 파라미터로 선언된 객체에서만 사용할 수 있다.
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
// 이 파일을 어노테이션 클래스로 지정한다. LoginUser라는 이름을 가진 어노테이션 생성.
public @interface LoginUser {
}
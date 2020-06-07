package com.hong.book.springboot.config.auth;

import com.hong.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
// 스프링 시큐리티 설정들을 활성화시켜준다.
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        // 해당하는 줄은 h2-console화면을 사용하기 위해 해당옵션들을 disable한다.
        http.csrf().disable().headers().frameOptions().disable()
                .and()
                    // URL별 권한 관리를 설정하는 옵션의 시작점.
                    .authorizeRequests()
                    // 권한 관리 대상을 지정하는 옵션으로 URL, HTTP 메소드별로 관리가 가능하다. /등 지정된 URL들은 permitAll()옵션을 통해 전체 권한을 주었다.
                    .antMatchers("/", "/css/**", "images/**", "/js/**", "/h2-console/**").permitAll()
                    // /api/v1/** 주소를 가진 API는 USER 권한을 가진 사람만 가능하도록 설정한다.
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    // anyRequest :: 설정된 값들 이외 나머지 URL들을 나타낸다. 여기서 추가된 authenticated는 나머지 URL들은 모두 인증된 사용자들에게만 허용하게 한다.
                    // 인증된 사용자 == 로그인한 사람들을 이야기 한다.
                    .anyRequest().authenticated()
                .and()
                    // 로그아웃 기능에 대한 여러 설정의 진입점으로 로그아웃 성공시 / 주소로 이동한다.
                    .logout().logoutSuccessUrl("/")
                .and()
                    // OAuth2 로그인 기능에 대한 여러 설정의 진입점
                    .oauth2Login()
                        // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당.
                        .userInfoEndpoint()
                        // 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록한다. 리소스 서버(즉, 소셜 서비스)들에서
                        // 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시한다.
                        .userService(customOAuth2UserService);
    }
}

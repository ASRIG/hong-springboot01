package com.hong.book.springboot.config.auth;

import com.hong.book.springboot.config.auth.dto.OAuthAttributes;
import com.hong.book.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 현재 로그인 진행중인 서비스를 구분하는 코드.
        // (구글만 사용하는 불 필요한 값이나, 네이버인지 구글잊ㄴ지 구분하기위해 사용)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // userNameAttributeName :: OAuth2 로그인 진행 시 키가 되는 필드값을 이야기한다. Primary Key와 같은 의미.
        // 구글의 경우 기본적으로 코드를 지원하지만, 네이버,카카오는 지원하지 않는다. (이후 네이버와 구글 로그인을 동시에 지원할 시 사용된다.)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스 (이후 다른 소셜 로그인에서도 사용된다)
        OAuthAttributes attributes = OAuthAttributes.of

        return null;
    }
}

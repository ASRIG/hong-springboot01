package com.hong.book.springboot.config.auth;

import com.hong.book.springboot.config.auth.dto.OAuthAttributes;
import com.hong.book.springboot.config.auth.dto.SessionUser;
import com.hong.book.springboot.domain.user.User;
import com.hong.book.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

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
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        // 세션에 사용자 정보를 저장하기 위한 Dto클래스. (User와 별개)
        // [ 만약 User 클래스를 그대로 사용했을 경우에는 오류가 발생한다. ]
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())), attributes.getAttributes(), attributes.getNameAttributeKey());
    }

    // 구글 사용자 정보가 업데이트 되었을 경우 update기능을 구현.이름이나 프로필사진이 변경되면 User 엔티티에도 반영된다.
    private User saveOrUpdate(OAuthAttributes attributes){
        User user = userRepository.findByEmail(attributes.getEmail()).map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());
        return userRepository.save(user);
    }
}

package com.hong.book.springboot.web;

import com.hong.book.springboot.config.auth.dto.SessionUser;
import com.hong.book.springboot.service.posts.PostsService;
import com.hong.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {
    // Spring에서 Bean객체를 주입받는 방식은 @Autowired, Setter, 생성자가 있다. 그 중 생성자가 가장 권장하는 방법이다.
    // Controller와 Service 사이에 Autowired가 없는 것을 확인할 수 있다. 이것은 생성자로 주입을 하기 때문이다.
    // 즉, 생성자로 Bean 객체를 받도록하면, Autowired와 동일한 효과를 볼 수 있다. RequredArgsConstructor가 이것을 해결해준다. (생성자 생성)
    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    // model :: 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있다.
            // 여기서는 postsService.findAllDesc()로 가져온 결과를 posts로 index.mustache에 전달한다.
    public String index(Model model){
        // mustache starter로 자동적으로 앞의 경로는 src/main/resources/templates로, mustache파일로 변환됨.
        model.addAttribute("posts", postsService.findAllDesc());
        // 앞서 작성된 CustomOAuth2UserService에서 로그인 성공시 세션에 유저를 저장하도록 구성.
        // 로그인 성공 시, getAttribute로 값을 가져올 수 있다.
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        // 세션에 저장된 값이 있을 때만 model에 userName으로 등록한다.
        if(user != null){
            model.addAttribute("userName", user.getName());
        }

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }
}

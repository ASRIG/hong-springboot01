package com.hong.book.springboot.web.domain.posts;

import com.hong.book.springboot.domain.posts.Posts;
import com.hong.book.springboot.domain.posts.PostsRepository;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
// SpringBootTest를 실행할 경우 자동적으로 H2가 실행된다 (별다른 설정 없이)
@SpringBootTest
public class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    // Junit에서 단위 테스트가 끝날때마다 수행되는 메소드를 지정.
        // 배포 전 전체 테스트를 수행할 때, 테스트간 데이터 침범을 막기위해 사용한다. (여러 테스트가 동시에 수행되면 DB에 이상한 값이 남을 수 있음)
    @After
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기(){
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        // 테이블 posts에 insert/update 쿼리를 실행한다.   id값이 있다면 update, 없을 경우 insert를 실행한다.
        postsRepository.save(Posts.builder()
                .title(title)
                .author("cmychs@gmail.com")
                .content(content)
                .build());

        // when
        // 테이블 posts에 있는 모든 데이터를 조회해오는 메소드
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        Assertions.assertThat(posts.getTitle()).isEqualTo(title);
        Assertions.assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록(){
        // given
        LocalDateTime now = LocalDateTime.of(2019, 6, 4,0,0,0);
        postsRepository.save(Posts.builder()
            .title("title").author("author").content("content").build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>> createDate=" + posts.getCreateDate()+", modifiedDate="+posts.getModifiedDate());

        Assertions.assertThat(posts.getCreateDate()).isAfter(now);
        Assertions.assertThat(posts.getModifiedDate()).isAfter(now);
    }
}

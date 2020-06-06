package com.hong.book.springboot.service.posts;

import com.hong.book.springboot.domain.posts.Posts;
import com.hong.book.springboot.domain.posts.PostsRepository;
import com.hong.book.springboot.web.dto.PostsListResponseDto;
import com.hong.book.springboot.web.dto.PostsResponseDto;
import com.hong.book.springboot.web.dto.PostsSaveRequestDto;
import com.hong.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        // update 기능에 쿼리를 날리는 부분이 없다.
        // 이것이 가능한 이유는 JPA의 영속성 컨텍스트 때문이다.
        // -- 영속성 컨텍스트
        //      엔티티를 영구 저장하는 환경을 의미. 일종의 논리적인 개념으로, JPA의 핵심 내용은 엔티티가 영속성 컨텍스트에 포함되어 있냐 아니냐로 갈린다.
        //      JPA의 엔티티 매니저(EntityManager)가 활성화된 상태로 (Spring Data Jpa를 쓴다면 기본 옵션)
        //          - 트랜잭션 안에서 데이터베이스에서 데이터를 가져오면 이 데이터는 영속성 컨텍스트가 유지된 상태이다.
        //            이 상태에서 해당 데이터의 값을 변경하면 트랜잭션이 끝나는 시점에 해당 테이블에 변경분을 반영한다. 즉, 별도로 update 쿼리를 날릴 필요가 없다.
        //      [ 이러한 개념을 Dirty Checking (더티 체킹) 이라고 한다. ]


        // Mybatis보다 좀 더 객체지향적으로 프로그래밍이 가능하다.
        Posts posts = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById (Long id){
        Posts entity = postsRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        return new PostsResponseDto(entity);
    }

    // readOnly를 주면 트랜잭션 범위는 유지하되, 조회기능만 남겨두어 조회속도가 개선된다. (등록, 수정, 삭제 기능이 없는 곳에 사용)
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc(){
        // Repository에서 넘어온 Posts의 Stream을 map을 통해 PostsListResponseDto로 변환. 그 후 List를 반환하는 메소드.
        return postsRepository.findAllDesc().stream()
                // (dto::new는 람다식으로 posts -> new Dto(posts)와 같다.)
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id){
        Posts posts = postsRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        // Repository에서 지원하는 delete를 사용. 엔티티를 파라미터로 삭제할 수 있고, deleteById를 이용하면 id로 삭제할 수 있다.
        postsRepository.delete(posts);
    }
}

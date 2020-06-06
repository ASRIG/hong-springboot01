package com.hong.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// JPA에서는 repository (Mybatis의 dao)로 사용한다.
// JpaRepository<Entity 클래스, PK 타입>를 상속하면 자동적으로 기본 CRUD에 대한 메소드가 생성된다.
// 주의! Repository를 설정할 필요도 없다. 하지만 Entity 클래스와 기본 Entity Repository는 함꼐 위치해야한다.
public interface PostsRepository extends JpaRepository<Posts, Long> {
    // SpringDataJpa에서 제공하지 않는 메소드는 Query어노테이션을 이용하여 작성해도 된다.
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}

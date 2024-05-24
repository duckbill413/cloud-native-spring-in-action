package com.polarbookshop.catalogservice.demo;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 프로파일을 기능 플래그처럼 사용하는 대신 좀 더 확장 가능하고 구조화된 접근법은 기능 설정을 위한 사용자 정의 속성을 정의하고
 * @ConditionalOnPropert나 @ConditionalOnCloudPlatform 같은 애너테이션을 사용해 스프링 애플리케이션 콘텍스트로 로드할 빈을 제어하는 것이다.
 * 예를 들어. polar.testdata.enabled 사용자 정의 속성을 정의하고 BookDataLoader 클래스에 @ConditionalOnProperty(name="polar.testdata.enabled", havingValue="true")
 * 애너테이션을 사용하면 된다.
 */
@Component
@Profile("testdata") // 이 클래스를 testdata profile에 할당
public class BookDataLoader {
    private final BookRepository bookRepository;

    public BookDataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * ApplicationReadyEvent가 발생하면
     * 테스트 데이터 생성이 시작된다.
     * 이 이벤트는 애플리케이션 시작 단계가
     * 완료되면 발생한다.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void loadBookTestData() {
        var book1 = new Book("1234567891", "Northern Lights", "Lyra Silverstar", 9.90);
        var book2 = new Book("1234567892", "Polar Journey", "Iorek Polarson", 12.90);
        bookRepository.save(book1);
        bookRepository.save(book2);
    }
}

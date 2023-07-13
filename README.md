# 나만의 블로그 만들기 개인 과제

ERD
-
![blog erd.PNG](src%2Fmain%2Fresources%2Fstatic%2FRM%2Fblog%20erd.PNG)

-------------------------------------
Api 명세
-
1. User
![User api.PNG](src%2Fmain%2Fresources%2Fstatic%2FRM%2FUser%20api.PNG)

   
2. Blog
![Blog api.PNG](src%2Fmain%2Fresources%2Fstatic%2FRM%2FBlog%20api.PNG)


3. Comment
![Comment Api.PNG](src%2Fmain%2Fresources%2Fstatic%2FRM%2FComment%20Api.PNG)

-------------------------------------


질문
-
1. Spring Security를 적용했을 때 어떤 점이 도움이 되셨나요?
    - Spring Security 는 보안과 관련해서 체계적으로 많은 옵션을 제공하기 때문에 개발자 입장에서 일일이 보안관련 로직을 작성하지 않아도 된다는 장점이 있습니다.
   
2. Spring Security를 사용하지 않는다면 어떻게 인증/인가를 효율적으로 처리할 수 있을까요?
    - controller에서 login을 구현하고 service부분에서 토큰을 발급하여 header에 저장한다.


3. AOP에 대해 설명해 주세요!
    - 관점 지향 프로그램 
    - 아직 정확하게 알고있지 않습니다 더 공부해서 수정하겠습니다 !
   

4. JWT를 사용하여 인증/인가를 구현 했을 때의 장/단점에 대해 숙련주차의 답변을 Upgrade 하여 작성해 주세요!
    - 인증을 위한 별도의 저장소가 필요 없다. 
    - 빠른 인증 처리
    - 확장성 우수
    
5. 즉시로딩 / 지연로딩에 대해 설명해 주세요!
    - 즉시로딩은 연관관계에 있는 데이터를 모두 조회한다
    - 지연로딩은 연관관계에 있는 데이터를 모두 조회하지 않는다. (해당 테이블의 데이터만 조회한다.)
    - fetch의 디폴트 값은 @xxToOne에서는 EAGER, @xxToMany에서는 LAZY이다.
    - 더 많은 상황에서 연관관계에 있는 모든 데이터를 조회하는 것이 필요하지 않기 때문에 LAZY를 사용하는 것이 좋다.
    - @Transactional 사용 주의 !

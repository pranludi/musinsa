# 구현 범위
- 카테고리 별 최저가격 브랜드와 상품 가격, 총액 조회
  - `GET /api/category/lowest-price`
- 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액 조회
  - `GET /api/brand/lowest-price`
- 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회
  - `POST /api/search/lowest-price`
- 상품 추가, 수정, 삭제
  - `POST /product/add`
  - `POST /product/update`
  - `POST /product/delete`
- API DOC - Swagger
  - 서버 실행 후 접속
  - http://localhost:8080/musinsa/swagger-ui/index.html

# 실행 방법
- 빌드
```bash
./gradlew build
```
- 테스트 방법
```bash
./gradlew test
```
- 실행
```bash
./gradlew bootRun 
```

# 기타
- 서버가 실행될때 상품 데이터를 메모리에 저장함( Metadata )
- API 처리할때, Metadata 에서 읽어서 처리하도록 구현
- 상품, 추가, 삭제시 DB 와 Metadata 동기화
  - 이후, 상황과 상태에 따라서 한가지 방향 또는 개선이 필요함

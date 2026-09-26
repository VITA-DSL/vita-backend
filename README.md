# VITA: 가상발전소(VPP) 중개사업자 플랫폼 백엔드

동아대학교 프로젝트 수업 과제(2025.05 ~ 06)로 만든 가상발전소 플랫폼의 백엔드입니다. 분산 에너지 자원(DER)의 발전량 예측을 받아 실측과 비교해 자원별 신뢰도를 계산하고, 신뢰도로 예측을 보정하며, 예측 정확도에 따라 정산금을 산출합니다.

## 도메인
| 패키지 | 역할 |
|---|---|
| `der` | 분산 에너지 자원 등록·조회 |
| `vpp` | 가상발전소와 DER 배분(allocation) |
| `prediction` | 발전량 예측 제출·조회 |
| `generation` | 실측 발전량 기록 |
| `weight` | 예측과 실측의 오차로 자원 신뢰도 계산 |
| `adjustment`, `adjustedPrediction` | 신뢰도로 보정한 예측 |
| `settlement`, `settlementRecord` | 예측 오차율에 따른 정산 |

REST API 34개. 도메인마다 Controller, Service, Repository, Mapper를 나눈 계층형 구조입니다.

## 신뢰도와 예측 보정
- 상대오차 e = |예측 − 실측| / 실측
- 신뢰도: 실측 ≥ 예측이면 1 / (1 − min(e, 1)), 실측 < 예측이면 1 − min(e, 1)
- 보정 예측: 최근 신뢰도 96개를 감쇠 계수 0.9로 가중 평균해 예측을 보정합니다. 신뢰도 상한은 1.02입니다
- 윈도우 크기와 상한은 검증하며 조정했습니다 (윈도우 72 → 96, 상한 1.5 → 1.25 → 1.1 → 1.05 → 1.02)

## 정산
예측 오차율에 따라 단가를 달리합니다: 6% 이하 4, 8% 이하 3, 8% 초과 0.

## 기술
Java 21 · Spring Boot 3.4 · Spring Data JPA · MySQL · Lombok · Docker · GitHub Actions

## 실행
```bash
# DB 접속 정보는 환경변수로: DB_IP, DB_PORT, DB_USERNAME, DB_PASSWORD
./gradlew bootRun
```

## 역할
백엔드 팀장, 백엔드 구현 (나지성)

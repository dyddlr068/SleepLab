<div align="center">

# 💤 SleepLab
### Java Swing 기반 수면 루틴 처방 시스템

수면 기록을 입력하면 수면 유형을 분석하고,<br/>
맞춤 루틴 처방부터 통계 리포트까지 한번에 제공합니다.

<br/>

![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)
![Swing](https://img.shields.io/badge/Java_Swing-FF6F00?style=for-the-badge&logo=java&logoColor=white)
![Eclipse](https://img.shields.io/badge/Eclipse-2C2255?style=for-the-badge&logo=eclipse&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)

</div>

---

## 📌 1. 기획 의도

바쁜 현대사회로 인해 잠이 부족한 분들을 위해서 수면관리가 필요할 것 같다는 경험에서 출발했습니다.

SleepLab은 이러한 단절을 해결하기 위해,
**수면 기록 → 유형 분석 → 루틴 처방 → 통계 리포트**의 흐름을
하나의 프로그램 안에서 유기적으로 연결하도록 설계된
Java 기반 수면 루틴 처방 시스템입니다.

객체지향 구조(OOP)를 중심으로 설계하였으며,
`SleepRoutine` 추상 클래스와 `Analyzable` · `Prescribable` · `Recordable`
인터페이스를 통해 공통 속성과 행위를 구조화했습니다.
또한 `ArrayList`와 파일 입출력, 예외 처리를 통해
데이터의 지속성과 안정성을 확보했습니다.

---

## 🎯 2. 기획 목표

- ✅ 상속 · 다형성 기반 수면 유형 분류 구현
- ✅ 인터페이스 기반 역할 분리 설계
- ✅ Custom Exception 으로 안전한 예외 처리 구현
- ✅ Swing GUI 기반 직관적인 사용자 인터페이스 구현
- ✅ 파일 입출력 기반 데이터 영속성 확보
- ✅ 목표 수면 시간 기반 금·은·동 메달 시스템 구현

---

## 👨‍💻 3. 개발자 정보

| 이름 | GitHub | 주요 작업 |
|------|--------|-----------|
| 권용익 | [@dyddlr068](https://github.com/dyddlr068) | 🔹 전체 설계 및 구현<br/>🔹 OOP 구조 설계 (상속 · 인터페이스 · 다형성)<br/>🔹 Swing GUI 전체 화면 구현<br/>🔹 수면 분석 · 루틴 처방 · 통계 기능 구현<br/>🔹 로그인 · 회원가입 · 캘린더 기능 구현 |

- **개발 기간**: 2026.05.11 ~ 2026.05.14

---

## 🛠 4. 기술 스택

| 카테고리 | 기술 |
|----------|------|
| Language | Java (JDK 17) |
| GUI | Java Swing |
| Paradigm | OOP (상속 · 인터페이스 · 다형성) |
| Data Structure | ArrayList |
| Persistence | File I/O (BufferedReader, BufferedWriter) |
| Error Handling | Custom Exception, try-catch |
| IDE | Eclipse |
| Version Control | Git, GitHub |

---

## 💻 5. 개발 환경

- **에디터**: Eclipse
- **버전 관리**: GitHub
- **OS**: Windows
- **JDK**: 17

---

## 🗂️ 6. 프로젝트 구조

```
SleepLab/
├── data/
│   ├── sleep_records.txt
│   ├── goal.txt
│   └── users.txt
└── src/
    ├── com.sleeplab/
    │   └── Main.java
    ├── com.sleeplab.model/
    │   └── SleepRecord.java
    ├── com.sleeplab.model.routine/
    │   ├── SleepRoutine.java
    │   ├── MorningType.java
    │   ├── EveningType.java
    │   └── IrregularType.java
    ├── com.sleeplab.interfaces/
    │   ├── Analyzable.java
    │   ├── Prescribable.java
    │   └── Recordable.java
    ├── com.sleeplab.service/
    │   ├── SleepAnalyzer.java
    │   ├── SleepRecordManager.java
    │   ├── GoalManager.java
    │   └── UserManager.java
    ├── com.sleeplab.exception/
    │   ├── InvalidSleepTimeException.java
    │   ├── InsufficientDataException.java
    │   └── OutOfRangeQualityException.java
    └── com.sleeplab.ui/
        ├── MainFrame.java
        ├── LoginPanel.java
        ├── RegisterPanel.java
        ├── MainPanel.java
        ├── InputPanel.java
        ├── AnalyzePanel.java
        ├── PrescribePanel.java
        ├── RecordPanel.java
        ├── StatPanel.java
        ├── GoalPanel.java
        ├── CalendarPanel.java
        ├── GraphPanel.java
        └── Refreshable.java
```

---

## 🧩 7. 패키지 구조

| 패키지 | 주요 역할 | 핵심 클래스 |
|--------|----------|------------|
| `com.sleeplab.model` | 데이터 클래스 정의 | `SleepRecord` |
| `com.sleeplab.model.routine` | 수면 유형별 루틴 처방 | `SleepRoutine`, `MorningType`, `EveningType`, `IrregularType` |
| `com.sleeplab.interfaces` | 역할별 규칙 정의 | `Analyzable`, `Prescribable`, `Recordable` |
| `com.sleeplab.service` | 비즈니스 로직 처리 | `SleepAnalyzer`, `SleepRecordManager`, `GoalManager`, `UserManager` |
| `com.sleeplab.exception` | 커스텀 예외 처리 | `InvalidSleepTimeException`, `InsufficientDataException`, `OutOfRangeQualityException` |
| `com.sleeplab.ui` | Swing GUI 화면 구성 | `MainFrame`, `LoginPanel`, `StatPanel`, `CalendarPanel` 등 |

---

## 🗺️ 8. 화면 구조

| 화면 | 설명 | 접근 |
|------|------|------|
| 로그인 | 아이디 · 비밀번호 입력 | 🌐 공개 |
| 회원가입 | 계정 생성 | 🌐 공개 |
| 메인 메뉴 | 전체 기능 메뉴 | 🔒 로그인 필요 |
| 수면 기록 입력 | 날짜 · 취침 · 기상 · 품질 · 컨디션 입력 | 🔒 로그인 필요 |
| 수면 유형 분석 | 아침형 · 저녁형 · 불규칙형 판별 | 🔒 로그인 필요 |
| 루틴 처방 | 유형별 오전 · 오후 · 취침 루틴 처방 | 🔒 로그인 필요 |
| 기록 전체 보기 | 날짜 내림차순 정렬, 다중 삭제 · 수정 | 🔒 로그인 필요 |
| 수면 통계 요약 | 그래프 · 메달 · 수면빚 표시 | 🔒 로그인 필요 |
| 목표 수면 설정 | 목표 시간 설정, 메달 기준 안내 | 🔒 로그인 필요 |
| 월별 캘린더 | 날짜별 금 · 은 · 동 메달 표시 | 🔒 로그인 필요 |

---

## 🔀 9. OOP 설계 구조

### 1. 상속 — 공통 속성의 구조화

`SleepRoutine` 추상 클래스는 수면 유형이라는 상위 개념을 추상화한 부모 클래스입니다.
`MorningType`, `EveningType`, `IrregularType` 3개가 이를 상속받아
유형별로 루틴 처방 메서드를 다르게 구현했습니다.

```java
// 부모 타입 하나로 자식 3개를 모두 처리 — 다형성
SleepRoutine routine = analyzer.analyze(records);
routine.prescribeNightRoutine(); // 유형마다 다른 루틴 출력
```

### 2. 인터페이스 — 역할별 규칙 강제

| 인터페이스 | 구현체 | 담당 역할 |
|-----------|--------|----------|
| `Analyzable` | `SleepAnalyzer` | 수면 분석 |
| `Prescribable` | `MorningType`, `EveningType`, `IrregularType` | 루틴 처방 |
| `Recordable` | `SleepRecordManager` | 저장 · 불러오기 |
| `Refreshable` | 모든 UI 패널 | 화면 갱신 |

### 3. 자료구조 선택 이유

수면 기록은 순서대로 누적하고 Stream API로 평균·편차를 집계하는 구조라서
`ArrayList`를 선택했습니다. 단순 배열은 크기가 고정되어 동적 추가가 불가능하고,
`HashMap`은 순서 보장이 안 되어 날짜 정렬에 불리하기 때문입니다.

---

## 🔧 10. 핵심 기능 구현

| 기능 | 구현 내용 | 적용 기술 |
|------|-----------|----------|
| **로그인 · 회원가입** | 아이디 · 비밀번호 인증, users.txt 파일 기반 저장 | `File I/O`, `UserManager` |
| **수면 기록 입력** | 날짜 · 취침 · 기상 · 품질 · 컨디션 입력, 중복 날짜 방지 | `File I/O`, `Custom Exception` |
| **수면 유형 분석** | 편차 60분 이상→불규칙형, 평균 취침 23시 이후→저녁형, 나머지→아침형 | `Stream API`, `다형성` |
| **루틴 처방** | 유형별 오전 · 오후 · 취침 루틴 처방, txt 파일 저장 | `상속`, `인터페이스` |
| **기록 전체 보기** | 날짜 내림차순 정렬, 체크박스 다중 삭제 · 수정 | `Collections`, `정렬` |
| **수면 통계 요약** | 꺾은선 그래프, 슬라이더 기간 조절, 메달 시스템 | `Graphics2D`, `Stream API` |
| **목표 수면 설정** | 목표 시간 설정, 금 · 은 · 동 메달 기준 적용 | `File I/O`, `GoalManager` |
| **월별 캘린더** | 날짜별 금 · 은 · 동 메달 표시, 년도 · 월 선택 | `YearMonth`, `LocalDate` |

---

## ⚠️ 11. 예외 처리 설계

| 상황 | 예외 클래스 | 처리 방식 |
|------|------------|----------|
| 날짜 형식 오류 / 취침·기상 시각 역전 | `InvalidSleepTimeException` | 입력 화면에서 오류 메시지 출력 후 재입력 유도 |
| 분석에 필요한 기록 부족 (3일 미만) | `InsufficientDataException` | 분석·처방 화면에서 안내 메시지 출력 |
| 수면 품질 1~5 범위 초과 | `OutOfRangeQualityException` | while + try-catch로 올바른 값 받을 때까지 반복 |
| 숫자 외 문자 입력 | `NumberFormatException` | catch 후 안내 메시지 출력 |
| 파일 접근 오류 | `IOException` | try-with-resources로 안전 처리 |

**책임 분리 원칙 (SRP)**
- 서비스 클래스: 오류 감지 후 `throw` 만 담당
- UI 패널: `catch` 후 사용자 안내만 담당

---

## 🛠️ 12. SleepLab 트러블슈팅 전체 기록

---
<details>
<summary>## 1. 파일명 불일치로 데이터 복구 불가 문제</summary>

| 항목 | 내용 |
|------|------|
| **증상** | 앱 종료 후 재실행하면 수면 기록이 항상 빈 목록으로 표시됨 |
| **원인** | 저장 시 `sleep_record.txt`, 불러올 때 `sleep_records.txt` 로 파일명 불일치 |
| **해결** | 파일명을 상수로 선언하여 저장·로딩에서 동일한 상수 참조 |

```java
// BEFORE
new FileWriter("data/sleep_record.txt")   // 저장
new FileReader("data/sleep_records.txt")  // 로딩 (오타)

// AFTER
private static final String FILE_PATH = "data/sleep_records.txt";
```
</details>

## 2. GoalPanel 패키지 오류

| 항목 | 내용 |
|------|------|
| **증상** | `GoalPanel cannot be resolved to a type` 오류 발생 |
| **원인** | `GoalPanel.java` 파일이 `com.sleeplab.service` 패키지에 잘못 생성됨 |
| **해결** | `com.sleeplab.ui` 패키지로 이동 후 정상 동작 확인 |

```
// BEFORE
com.sleeplab.service
└── GoalPanel.java  ← 잘못된 위치

// AFTER
com.sleeplab.ui
└── GoalPanel.java  ← 올바른 위치
```

---

## 3. GraphPanel 내부 클래스 충돌

| 항목 | 내용 |
|------|------|
| **증상** | `StatPanel` 내부에 선언한 `GraphPanel` 클래스로 인해 컴파일 오류 발생 |
| **원인** | 내부 클래스로 선언하면 외부에서 타입을 인식하지 못하는 문제 발생 |
| **해결** | `GraphPanel` 을 별도 파일로 분리하여 독립 클래스로 구성 |

```java
// BEFORE — StatPanel 내부에 선언
public class StatPanel extends JPanel {
    class GraphPanel extends JPanel { // 내부 클래스 → 충돌 발생
        ...
    }
}

// AFTER — 별도 파일로 분리
// GraphPanel.java
public class GraphPanel extends JPanel {
    ...
}
```

---

## 4. 이모지 폰트 깨짐 문제

| 항목 | 내용 |
|------|------|
| **증상** | `Segoe UI Emoji` 폰트 적용 시 한글이 깨져서 표시됨 |
| **원인** | `Segoe UI Emoji` 폰트가 한글을 지원하지 않음 |
| **해결** | 한글은 `맑은 고딕` 폰트로 통일하고 이모지는 텍스트로 대체 |

```java
// BEFORE
btn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 15));

// AFTER
btn.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
```

---

## 5. SwingUtilities cannot be resolved 오류

| 항목 | 내용 |
|------|------|
| **증상** | `SwingUtilities cannot be resolved` 오류 발생 |
| **원인** | `module-info.java` 에 `java.desktop` 모듈이 선언되지 않음 |
| **해결** | `module-info.java` 에 `requires java.desktop` 추가 |

```java
// BEFORE
module SleepLab {
}

// AFTER
module SleepLab {
    requires java.desktop;
}
```

---

## 6. StatPanel 에서 getSleeQuality() 오타 오류

| 항목 | 내용 |
|------|------|
| **증상** | `The method getSleeQuality() is undefined for the type SleepRecord` 오류 발생 |
| **원인** | `getSleepQuality()` 를 `getSleeQuality()` 로 오타 입력 |
| **해결** | `getSleepQuality()` 로 수정 |

```java
// BEFORE
r.getSleeQuality()  // 오타

// AFTER
r.getSleepQuality() // 정상
```

---

## 7. 날짜 오류 메시지 불명확 문제

| 항목 | 내용 |
|------|------|
| **증상** | 잘못된 날짜 입력 시 오류 메시지가 불명확하게 표시됨 |
| **원인** | 단순히 "올바른 날짜 형식이 아닙니다" 만 표시하여 사용자가 어떤 날짜를 입력해야 하는지 모름 |
| **해결** | 입력 가능한 날짜 범위를 명확하게 안내하도록 수정 |

```java
// BEFORE
System.out.println("올바른 날짜 형식이 아닙니다.");

// AFTER
showError("존재하지 않는 날짜입니다.\n2000-01-01 ~ "
    + LocalDate.now() + " 사이로 입력해주세요.");
```

---

## 8. Refreshable 인터페이스 파일명 대소문자 오류

| 항목 | 내용 |
|------|------|
| **증상** | `Refreshable` 을 못 찾는 오류 발생 |
| **원인** | 파일명이 `ReFreshable.java` 로 대문자 F가 들어가 있어서 인식 불가 |
| **해결** | 파일명을 `Refreshable.java` 로 변경 |

```
// BEFORE
ReFreshable.java  ← 대문자 F

// AFTER
Refreshable.java  ← 정상
```

---

## 9. 중복 날짜 입력 방지 미구현 문제

| 항목 | 내용 |
|------|------|
| **증상** | 같은 날짜로 수면 기록을 두 번 입력하면 중복 저장됨 |
| **원인** | 저장 전 중복 날짜 확인 로직이 없었음 |
| **해결** | `isDuplicate()` 메서드 추가 후 저장 전 중복 체크 |

```java
// AFTER — SleepRecordManager.java
public boolean isDuplicate(String date) {
    return records.stream()
        .anyMatch(r -> r.getDate().equals(date));
}

// InputPanel.java
if (manager.isDuplicate(date)) {
    showError(date + " 날짜의 기록이 이미 존재합니다.");
    return;
}
```

---

## 10. if-else → Custom Exception 개선

| 항목 | 내용 |
|------|------|
| **증상** | 기록 부족 시 `null` 반환으로 호출부에서 원인 파악 불가 |
| **원인** | 예외 상황을 `null` 반환 또는 단순 출력으로 처리 |
| **해결** | Custom Exception 으로 원인 명확히 전달 |

```java
// BEFORE
if (records.size() < 3) {
    System.out.println("기록이 부족합니다.");
    return null;
}

// AFTER
if (records.size() < MIN_RECORDS) {
    throw new InsufficientDataException(records.size(), MIN_RECORDS);
}
```

---

## 📋 트러블슈팅 요약

| 번호 | 문제 | 원인 | 해결 |
|------|------|------|------|
| 1 | 파일명 불일치 | 오타 | 상수로 통일 |
| 2 | GoalPanel 패키지 오류 | 잘못된 패키지 | ui 패키지로 이동 |
| 3 | GraphPanel 내부 클래스 충돌 | 내부 클래스 선언 | 별도 파일로 분리 |
| 4 | 이모지 폰트 깨짐 | 폰트 미지원 | 맑은 고딕으로 통일 |
| 5 | SwingUtilities 오류 | module-info 누락 | requires java.desktop 추가 |
| 6 | getSleeQuality 오타 | 오타 | getSleepQuality 로 수정 |
| 7 | 날짜 오류 메시지 불명확 | 안내 부족 | 범위 명시 |
| 8 | Refreshable 파일명 오류 | 대소문자 오류 | 파일명 수정 |
| 9 | 중복 날짜 미체크 | 로직 누락 | isDuplicate 추가 |
| 10 | null 반환 → Custom Exception | 설계 미흡 | Custom Exception 도입 |

## 📊 14. UML 클래스 다이어그램

![클래스다이어그램](https://github.com/dyddlr068/SleepLab/blob/main/SleepLab%20Diagram.png?raw=true)

---

## 👀 15. 주요 기능 실행 화면

### 🔐 로그인 · 회원가입

<details>
<summary>📸 스크린샷</summary>

**로그인 화면**

<img width="483" height="560" alt="로그인메인" src="https://github.com/user-attachments/assets/654028f7-a314-4046-9459-7b7b85cdf2db" />

**회원가입 화면**

<img width="483" height="560" alt="회원가입" src="https://github.com/user-attachments/assets/a6648aff-311d-45a8-8925-15ba2f3fe5d9" />

</details>

<details>
<summary>🎬 GIF</summary>

**로그인 화면 (달 · 별 애니메이션)**
![로그인화면](https://github.com/user-attachments/assets/245758a1-6bbf-4e41-92a8-380bf23ea6ef)

**회원가입**
![회원가입](https://github.com/user-attachments/assets/6314142c-b370-4ef2-88a4-400111170732)

</details>

---

### 🏠 메인 메뉴

<details>
<summary>📸 스크린샷</summary>

<img width="484" height="561" alt="메인메뉴" src="https://github.com/user-attachments/assets/aea762cf-581e-4dbd-9c6c-13ef41b3513c" />

</details>

<details>
<summary>🎬 GIF</summary>

![메인메뉴화면](https://github.com/user-attachments/assets/62f87045-1ae2-40e4-853f-5a7c197e03d2)

</details>

---

### 🌙 수면 기록 입력

<details>
<summary>📸 스크린샷</summary>

**입력 화면**

<img width="484" height="561" alt="수면기록입력" src="https://github.com/user-attachments/assets/b013eaf8-e246-48ea-a686-f966621f97d2" />

**저장 완료**

<img width="484" height="561" alt="수면기록입력완료" src="https://github.com/user-attachments/assets/3986c134-8621-48ce-9a41-57b21cd043b1" />

**예외 처리 — 잘못된 시간 입력**

<img width="484" height="561" alt="수면기록입력예외처리" src="https://github.com/user-attachments/assets/17bfe50c-bf91-42ef-9888-a74a7d9f0637" />

</details>

<details>
<summary>🎬 GIF</summary>

![수면기록입력](https://github.com/user-attachments/assets/b34dc555-d22a-4d5c-910e-8fde27ad24ce)

</details>

---

### 📊 수면 유형 분석

<details>
<summary>📸 스크린샷</summary>

**분석 결과**

<img width="484" height="561" alt="수면유형완료" src="https://github.com/user-attachments/assets/0dda2820-d42e-4453-9928-7504c95d6aed" />

**예외 처리 — 기록 부족**

<img width="484" height="561" alt="수면유형예외처리" src="https://github.com/user-attachments/assets/afc51240-3df3-4717-8d7d-5fe6e9bbc1b7" />

</details>

<details>
<summary>🎬 GIF</summary>

![수면유형분석](https://github.com/user-attachments/assets/80769a09-9f3d-45fd-ab74-6f61bd4161b4)

</details>

---

### 💊 루틴 처방

<details>
<summary>📸 스크린샷</summary>

<img width="483" height="560" alt="루틴처방화면" src="https://github.com/user-attachments/assets/cb7d548d-4e52-47f3-9950-11e7f2a67fec" />

</details>

<details>
<summary>🎬 GIF</summary>

![루틴처방화면](https://github.com/user-attachments/assets/b736074e-39e2-4c35-9207-0a39ee9a9125)

</details>

---

### 📋 기록 전체 보기

<details>
<summary>📸 스크린샷</summary>

**기록 목록**

<img width="483" height="560" alt="기록전체보기" src="https://github.com/user-attachments/assets/d38911e1-8e30-47c8-8510-6bd32145a495" />

**체크박스 선택**

<img width="483" height="560" alt="기록전체보기체크박스" src="https://github.com/user-attachments/assets/17505be2-51d4-4611-a015-9a51a52d549e" />

**예외 처리 — 선택 없이 삭제**

<img width="483" height="560" alt="기록전체보기예외처리" src="https://github.com/user-attachments/assets/f4e506c7-7cb8-4208-b81e-0f96bed4c95d" />

</details>

<details>
<summary>🎬 GIF</summary>

![기록전체보기수정삭제](https://github.com/user-attachments/assets/4dd03360-149a-4aba-b35c-cdd499780ca1)

</details>

---

### 📈 수면 통계 요약

<details>
<summary>📸 스크린샷</summary>

**통계 화면**

<img width="483" height="560" alt="수면통계요약" src="https://github.com/user-attachments/assets/9ac4a50c-0bca-4aac-9abf-cf5d6b685133" />

**슬라이더 조절**

<img width="484" height="561" alt="수면통계요약슬라이더" src="https://github.com/user-attachments/assets/b2e9c150-8880-43bf-b99e-792d82b0fec1" />

**예외 처리 — 품질 경고**

<img width="485" height="559" alt="수면통계요약예외처리" src="https://github.com/user-attachments/assets/e3389e0c-0a68-4d57-a013-d8b701752f59" />

</details>

<details>
<summary>🎬 GIF</summary>

![수면통계요약](https://github.com/user-attachments/assets/05ed3fd9-ef4b-47dc-8305-d7c72ba75524)

</details>

---

### 📅 월별 캘린더

<details>
<summary>📸 스크린샷</summary>

<img width="484" height="561" alt="월별캘린더" src="https://github.com/user-attachments/assets/18fc489b-b494-456a-89bb-fbf7af75f535" />

</details>

<details>
<summary>🎬 GIF</summary>

![월별캘린더화면](https://github.com/user-attachments/assets/a1878cc4-5263-4ae9-ab32-1f0aef94cfa4)

</details>

---

### ⚙️ 목표 수면 설정

<details>
<summary>📸 스크린샷</summary>

**목표 설정 화면**

<img width="483" height="560" alt="목표수면화면" src="https://github.com/user-attachments/assets/2aa3bffd-28c0-42cc-ab96-268bca7c47f1" />

**저장 성공**

<img width="483" height="560" alt="목표수면화면성공" src="https://github.com/user-attachments/assets/a4f499bb-55c8-4cd5-ad24-9b52d1b5fc49" />

</details>

<details>
<summary>🎬 GIF</summary>

![목표수면시간](https://github.com/user-attachments/assets/68b8f11a-dbbf-4ec5-bdd9-5f5ad3cc7c39)

</details>

---

## 💡 16. 향후 발전 방향

- 🔐 **보안 강화** — 비밀번호 SHA-256 해시 암호화 적용
- 📊 **통계 고도화** — 태그 시스템 추가 (카페인·음주·운동 등 영향 요소 기록)
- 🌐 **DB 연동** — 파일 I/O → MySQL 전환, 기기 간 데이터 동기화
- 📱 **모바일 연동** — 알람 연동, 수면 트래커 디바이스 연결

---

## 🏁 17. 프로젝트 마무리

이번 프로젝트 **「SleepLab」** 은 수면 기록을 분석하여 맞춤 루틴을 처방해주는
Java 기반 수면 관리 시스템을 목표로 개발하였습니다.

개발 기간 동안 OOP 구조 설계, Swing GUI 구현, 파일 I/O 기반 데이터 관리,
Custom Exception 설계, 꺾은선 그래프 직접 구현 등 다양한 기술을 직접
설계·구현했습니다.

이를 통해 객체지향 프로그래밍 설계 능력, 예외 처리 설계 능력,
그리고 사용자 경험(UX)을 고려한 GUI 구현 역량을 성장시킬 수 있었습니다.

---

<div align="center">

**SleepLab** · 2026 · Java + Swing GUI

</div>

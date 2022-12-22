면접 질문 중에, 막상 알고 있긴 한데, 포장을 해서 설명을 할 수 있어야 하는데, 막상 떠올리면 뭔지는 아는데 설명이 말이 막히는 경우가 많아서 연습삼아서 정리를 시작하게 되었다.<br>

그런데, 막상 시작하려니, 뭐부터 시작할줄 몰라서 꽤 고통스러웠는데 다행히도 여기 저기 자료를 찾다보니 https://dev-coco.tistory.com/20 의 자료를 찾게 되어서 여기의 목차대로 정리를 했는데, 더 필요한 내용들이 있으면 다른 블로그들을 더 참고해서 Java 관련 자료들에 대해서만 또 여기에 추가해둘 예정.<br>

이번 문서에서는 깊은 내용보다는, 빠르게 전반적인 내용들을 반복해서 훑어볼 수 있게 요약했다. 블로그의 내용들을 요약한 내용이나, 자료를 검색한 내용들, 또는 내가 일하면서 또는 내가 공부하면서 알게된 내용들을 주관적으로 추가해서 재편집했다.<br>

<br>

이 글은 비공개 리포지터리에 정리하던 내용인데, 여기에도 정리하는게 맞을지 나도 잘 아직은 판단이 안선다. <br>

그냥 일단 여기에 정리해두고, 나중에 삭제할지 결정해둬야 겠다. 고민하는 시간이 더 아까워!!!<br>

<Br>



# try with resource 에 대해 설명해주세요

참고 : https://dev-coco.tistory.com/20<br>

<br>

try catch finally 의 경우 자원을 열고나서 닫는 구문을 직접 모두 정의해야 했었다. 

java 7 부터는 try with resources 라는 문법이 도입되었다. 

try with resources 는 Auto Closeable 인터페이스를 구현한 객체를 사용하는 경우에 정확하게 동작한다. try(...) 에 자원과 관련된 객체를 전달하면 try 구문이 끝난 후에 close() 메서드를 호출해서 자원을 해제해준다.

가독성 면에서 try with resources 를 사용하면 좋다. 하지만, Auto Closeable 을 구현하고 있는 클래스인지 살펴보고 try with resources 를 사용해야 한다.

<br>

e.g. try with resource

```java
try(FileOutputStream out = new FileOutputStream("hello.txt")){
    // ...
} catch(IOException e){
    e.printStackTrace();
}
```

<br>

e.g. try catch finally

```java
FileOutputStream out = null;
try{
    out = new FileOutputStream("hello.txt");
    // ...
} catch (FileNotFoundException e){
    e.printStackTrace();
} finally {
    if(out != null){
        try {
            out.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
```

<br>



# final, finally, finalize  차이점

final, finally, finalize의 차이를 설명해주세





# 불변객체, final

## 불변객체 또는 final 을 사용해서 얻는 장점은 어떤 것이 있나요?

불변 객체는 아래의 장점을 갖는다.

Thread Safe

- 병렬 프로그래밍에 유용하고, 동기화를 고려하지 않아도 된다.

<br>

실패 원자적인 메서드를 만들수 있다.

- 실행 중에 예외가 발생해도 메서드 호출 전의 상태를 유지할 수 있기 때문에 예외가 발생하기 전의 상태로 다음 로직을 처리가능하다.

<br>

부수효과를 피해서 오류를 최소화할 수 있다.

- 부수효과: 
  - 변수의 값이 변하거나, 객체의 필드 값이 변경되는 것으로 인해 예외 또는 오류가 발생해서 실행이 중단되는 현상

<br>

메서드 호출 시 파라미터 값이 변하지 않는다는 것을 보장 가능

<br>

가비지 컬렉션 성능을 높일 수 있다.

- 가비지 컬렉터가 스캔하는 객체의 수가 줄어들기 때문에 GC 수행시 지연시간이 줄어든다.
- (아래 내용 참고)

<Br>

## 불변 객체가 가비지 컬렉션의 성능을 높일 수 있는 이유는?

참고자료 : [Java 불변 객체](https://velog.io/@bey1548/JAVA-%EB%B6%88%EB%B3%80%EA%B0%9D%EC%B2%B4)



불변의 객체가 먼저 생성되어야 컨테이너 객체가 이를 참조할 수 있다.

- 1 ) Object 타입의 value 객체 생성
- 2 ) ImmutableHolder 타입의 컨테이너 객체 생성
- 3 ) ImmutableHolder 가 Value 객체를 참조

<br>

이 때 GC 가 수행될때 가비지 컬렉터는 컨테이너 하위의 불변객체들은 skip 할 수 있도록 도와준다. 해당 불변 컨테이너 객체 (ImmutableHolder) 가 살아있다는 것은 하위의 객체들도 모두 처음에 할당된 그 상태로 참조되고 있다는 것을 의미하기에, 가비지 컬렉터는 이 컨테이너 하의의 불변객체들을 스캐닝을 덜 하고, skip 하게 된다.<br>

따라서, 불변객체를 사용하면 가비지컬렉터가 스캔해야 하는 객체의 수가 줄어서 스캔해야 하는 메모리의 영역과 빈도수도 줄어든다. 따라서 GC가 수행되어도 지연시간을 줄일 수 있다.<br>

<br>



## 불변 클래스란

참고자료 : [Java 불변 객체](https://velog.io/@bey1548/JAVA-%EB%B6%88%EB%B3%80%EA%B0%9D%EC%B2%B4)<br>

<br>

아래의 규칙에 따라 정의한 클래스를 **불변 클래스**라 한다.

- 클래스를 final 로 선언하라
- 클래스 내의 모든 변수를 private과 final 로 선언하라
- 객체를 생성하기 위한 생성자 또는 정적 팩토리 메서드를 추가하라
- 참조에 의한 변경가능성이 있는 경우 방어적 복사를 이용해 전달하라.

<br>

e.g. ImmutableClass.java

```java
public final class ImmutableClass{
    private final int age;
    private final String name;
    private final List<String> list;
    
    private ImmutableClass(int age, String name){
        this.age = age;
        this.name = name;
        this.list = new ArrayList<>();
    }
    
    public static ImmutableClass of(int age, String name){
        return new ImmutableClass(age, name);
    }
    
    public int getAge(){return age;}
    public String getName(){return name;}
    public List<String> getList() {
        return Collections.unmodifieableList(list);
    }
}
```

<br>

정적 팩토리 메서드

- 내부 생성자를 만드는 대신 객체의 생성을 위해 정적 팩토리 메서드를 정의했다.
- 또한 List와 같은 컬렉션 타입의 경우 list를 방어적 복사를 통해 제공했다.

<br>

기본생성자의 위험함 -> 기본생성자를 private으로 지정하고 정적 팩토리 메서드로 제공

- Java 는 생성자를 선언하지 않으면 기본 생성자가 자동으로 생성된다.
- 이 경우 다른 클래스에서 기본생성자를 자유롭게 호출가능하게 된다.
- 이런 경우에 대해 정적 팩토리 메서드를 통해 객체를 생성하도록 강제하면 좋다.

<br>

참조를 통해 변경이 가능한 경우 방어적 복사

- List와 같은 타입의 경우 객체 생성시 list 를 방어적 복사를 통해 생성

<br>



## 불변객체가 무엇인가요

참고자료 : [신입개발자 기술면접 질문 정리 - Java](https://dev-coco.tistory.com/153#%F-%-F%--%A-%--%EA%B-%--%--%EB%A-%--%EB%AA%A-%EB%A-%AC%--%EC%--%--%EC%--%AD%EC%-D%B-%--%ED%--%A-%EB%-B%B-%EB%--%--%EB%-A%--%--%EC%-B%-C%EC%A-%--%EC%-D%--%--%EC%--%B-%EC%A-%-C%EC%-D%B-%EA%B-%--%EC%-A%--%-F)

<br>

불변객체는 객체가 생성된 이후로 내부의 상태가 변하지 않는 객체를 의미

- 원시타입의 멤버필드의 경우 단순하게 final 키워드와 함께 선언하면, 불변타입으로 선언가능하다
- 다만, 참조 타입의 멤버필드가 선언되어 있을 경우 final 로 선언했다라도 추가적으로 작업을 해줘야 한다.

<br>



## 참조 타입일 경우 해주는 추가적인 작업

일반 객체 일 경우, 배열/List와 같은 타입일 경우 이렇게 두가지 경우로 나누어서 불변객체 처리를 해줄수 있다.

- 일반객체
- 배열/리스트 등의 자료구조
  - 조회시에는 쉽게 설명하면 값의 복사본을 생성해서 반환해야 한다.
  - 수정 시에는 파라미터로 전달된 전달된 배열/리스트의 각 요소 값을 복사한 새로운 값으로 멤버필드에 저장해준다.
  - 배열, 리스트의 경우 내부를 복사해서 전달하는 이런 방식을 **방어적 복사(defensive-copy)**라고 한다.

<br>



참조변수가 일반객체일 경우

- 객체를 사용하는 필드의 참조 변수도 불변 객체로 변경해야 한다.



참조 변수가 배열/리스트 등의 자료구조일 경우

- 배열 일 경우 배열을 받아서 copy 해서 저장하고, getter에서는 clone 메서드를 통해 반환하도록 해준다.
- 배열을 그대로 참조하거나 반환할 경우, 외부에서 내부 값을 변경할수 있다. 따라서 clone 을 통해 복사한 값을 반환해서 원본 값을 변경하지 못하게 해야 한다.

- 리스트일 경우에도 배열과 마찬가지로 생성시에 새로운 List 를 만들어 값을 복사하게끔 해줘야 한다.

<br>



# 추상클래스, 인터페이스

참고자료 : [신입개발자 기술면접 질문 정리 - Java](https://dev-coco.tistory.com/153#%F-%-F%--%A-%--%EA%B-%--%--%EB%A-%--%EB%AA%A-%EB%A-%AC%--%EC%--%--%EC%--%AD%EC%-D%B-%--%ED%--%A-%EB%-B%B-%EB%--%--%EB%-A%--%--%EC%-B%-C%EC%A-%--%EC%-D%--%--%EC%--%B-%EC%A-%-C%EC%-D%B-%EA%B-%--%EC%-A%--%-F)

<br>

추상클래스

- 클래스 내에 추상 메서드가 하나 이상 포함되어 있는 클래스
- 이 경우 클래스 앞에 `abstract` 키워드를 붙이지 않으면 컴파일타임에 에러가 난다.
- `extends` 키워드를 사용해서 상속을 받는다.
- 다중상속 불가
- 일부 메서드 정의부를 둘 수 있다.
- 추상클래스 자체로만 인스턴스를 생성(new)하는 것은 불가능하다.

<br>

인터페이스

- 모든 메서드가 추상메서드로 이루어진 타입을 의미한다.
- `interface` 라는 키워드를 사용한다.
- `implements` 키워드를 사용해서 메서드의 내용을 구현한다.
- implements 시 모든 추상 메서드를 오버라이딩 해야 한다.
- 다중상속이 가능하다는 장점이 있다.
- 인터페이스 자체로만 인스턴스를 생성(new)하는 것은 불가능하다.

<br>



차이점

- 추상클래스는 `extends` 키워드를 사용하고 다중 상속이 불가능하다.
- 인터페이스는 `implements` 키워드를 사용하고 다중 상속이 가능하다/

<br>



# 싱글턴 패턴

프로그램 전역에 인스턴스가 하나만 존재하도록 하고 싶은 경우에 싱글턴 패턴을 사용<br>

생성자가 private 으로 되어있고, 프로그램 로딩시에 하나의 인스턴스만 생성한다.<br>

DCL 관련 이슈가 있는데, 이에 관해서는 Bill Pugh 가 언급한 Inner Class 를 사용한 싱글턴을 사용하는 방식으로 해결이 가능하다.<br>

<br>

# 싱글턴 패턴의 대표적인 예 - Spring Bean

스프링의 빈은 스코프에 대해 아무런 설정을 하지 않으면 기본설정으로 싱글톤 스코프가 적용된다.<br>

스프링 컨테이너는 빈으로 정의한 모든 객체들을 싱글톤으로 관리한다.<br>

<br>



# 참고 - Prototype 스코프 Bean

빈을 prototype 스코프를 가지도록 정의하면, 스프링 컨테이너에 빈 객체를 요청할 때마다 새로운 객체를 생성해서 반환하게 된다.<br>

e.g. `@Scope("prototype")`<br>

<br>



# 클래스와 객체

## 클래스와 객체에 대해 설명해주세요

멤버필드 들과 멤버 메스드 들을 하나의 단위로 묶어서 클래스라는 단위로 표현가능하다. 이 클래스를 기준으로 객체를 생성할 수 있다.<br>

보통 필드(field)를 상태 라고 표현하고, 메서드(method)를 행동이라고 표현한다.<br>

객체에 메모리가 할당되어 실제로 생성된 실체를 '인스턴스'라고 부른다.<br>

<br>



## 클래스 멤버 변수 초기화 순서

클래스 멤버 변수 초기화 순서에 대해 설명해주세요

1 ) static 변수 선언부

- 클래스 로드 시 static 필드 선언부가 가장 먼저 초기화 된다.

2 ) 필드 변수 선언부

- 객체가 생성시 생성자 영역 보다 먼저 초기화 된다.

3 ) 생성자 영역

- 객체 생성시 JVM이 내부적으로 생성자 영역을 locking 한다. (thread safe 영역)

<br>



# Inner Class (내부 클래스)

## Inner Class (내부 클래스) 란

클래스 내부에 선언된 또 하나의 클래스를 의미<br>

우리가 자주 사용하는 Builder 패턴이나, 주요 라이브러리들에서 제공하는 Builder 패턴들이 주로 Inner Class 를 통해 라이브러리를 제공한다.<br>

우리가 자주 사용하는 스프링 프레임워크에서 제공하는 `ResponseEntity` 이라는 클래스 역시 내부 구현을 살펴보면 `DefaultBuilder` 라는 inner class 를 사용하고 있다.<br>

<br>

**ResponseEntity**<br>

```java
public class ResponseEntity<T> extends HttpEntity<T> {
    
    // ...
    
    public static BodyBuilder status(HttpStatus status) {
		Assert.notNull(status, "HttpStatus must not be null");
		return new DefaultBuilder(status);
	}
    
    // ...
    
    private static class DefaultBuilder implements BodyBuilder {
        
        // ...
        
        public DefaultBuilder(Object statusCode){
            this.statusCode = statusCode;
        }
        
        // ... 
        
        @Override
		public BodyBuilder header(String headerName, String... headerValues) {
			for (String headerValue : headerValues) {
				this.headers.add(headerName, headerValue);
			}
			return this;
		}
        
        // ...
        
    }
}
```

<br>



## Inner Class (내부 클래스) 의 장점에 대해 설명해주세요

참고자료: 

- [Inner Class (내부 클래스)의 장점, 종류](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EB%82%B4%EB%B6%80-%ED%81%B4%EB%9E%98%EC%8A%A4Inner-Class-%EC%9E%A5%EC%A0%90-%EC%A2%85%EB%A5%98)
- [코딩의 시작, TCP School](http://www.tcpschool.com/java/java_usingClass_innerClass)

<br>

1 ) 클래스를 논리적으로 그루핑

- Builder 패턴을 예로 들어보면, 클래스 내부에 Inner Class(내부클래스)를 두는 것으로 클래스를 논리적으로 그룹화할 수 있다.
- 객체의 필드들을 세팅해주는 역할을 Builder 라는 이름의 내부 클래스에서 전담하도록 해주는 것이다.<br>

2 ) 캡슐화

- Inner Class (내부 클래스)에 private 접근 제한자를 적용해주면, 캡슐화를 통해 Inner Class(내부클래스를)를 내부에서만 접근하도록 숨길 수 있다.
- 캡슐화를 통해 주요 로직을 외부에서의 접근을 차단하면서, 내부 클래스에서 외부 클래스의 멤버들을 제약없이 쉽게 접근할 수 있기에 구조적인 프로그래밍이 가능해진다.
- 클래스 구조를 숨기기에 코드의 복잡성도 줄일 수 있다.<br>

<br>

3 ) 가독성이 좋고 유지 보수가 쉬워진다.

- Inner Class (내부 클래스) 를 사용하면 클래스를 외부에 따로 작성하는 것 보다 물리적으로, 논리적으로 외부 클래스에 더 가깝고, 의미적으로도 연관관계를 유추할 수 있기에 유지보수에 이점을 제공해준다.
- Inner Class 에서 외부 클래스의 멤버에 자유롭게 접근 가능하다.

- 하나의 클래스를 다른 클래스의 Inner Class(내부클래스)로 선언하면, 두 클래스 멤버들 간에 서로 자유롭게 접근할 수 있고 외부에는 불필요한 클래스를 감춰서 코드의 복잡성을 줄일 수 있다는 장점이 있다.
- 동시성/병렬 프로그래밍을 구현 중이라면 이런 점들은 조금 도움이 될 수 있다. 객체 외부에서 객체 내부의 상태에 접근하는 것 보다 객체 안에서 객체의 상태에 접근하는 것은 조금은 더 동기화에 유리하기 때문

<br>



# 생성자

객체를 생성할 때 사용하는 함수같은 역할을 하는 연산자다.<br>

클래스 정의시 생성자를 정의하지 않으면 기본 생성자가 기본으로 생성된다.<br>

생성자는 클래스와 같은 이름을 가지고 있다.<br>

<br>

생성자는 오버로딩이 가능하다.<br>

<br>



# 제너릭 (Generic)

## 제너릭에 대해 설명해주세요

제너릭은 자바 5 에서부터 도입되었다. C++ 의 Template 과 같은 역할을 수행한다.<br>

제너릭은 `<>` 을 사용해 타입을 지정한다.<br>

보통 `<T>` 와 같이 표현하는데 `T` 는 보통 `Type` 의 약자다.<br>

<br>



## 제너릭은 주로 언제 사용할까요

제너릭은 어떤 타입에 대해서든 공통적으로 동작하도록 할 때 제너릭 타입을 사용해 데이터의 타입을 정의한다. 예를 들면 공통으로 사용할 Response 객체의 경우 `ServerResponse<T> response` 와 같은 형태로 정의하기도 한다.<br>

<br>



## 제너릭의 장점은?

참고 : [Effective Java 5장 제네릭](https://umbum.dev/1020)  

<br>

타입 안정성 보장

- 이펙티브 자바에서 언급하는 내용이다.
- 컴파일 타임에 타입 체크를 하므로 런타임에 ClassCastException 이 발생하는 것을 막아준다.
- 따라서 타입 안정성을 보장한다. 

타입 체크와 형변환 생략

- 코드가 간결해진다는 장점이 있다.
- 관리하기가 편해진다.

코드의 재사용성이 높아진다.

- 비슷한 기능을 지원하는 경우 코드의 재사용성이 높아진다.

<br>



## 제너릭의 타입

| 타입  | 설명    |
| ----- | ------- |
| `<T>` | Type    |
| `<E>` | Element |
| `<K>` | Key     |
| `<V>` | Value   |
| `<N>` | Number  |

<br>



# 리플렉션

## 리플렉션(Reflection) 설명 해보세요

클래스의 구체적인 타입을 알지 못해도 그 클래스의 특정 메서드, 특정 타입, 특정 변수들에 접근할 수 있도록 자바에서 제공해주는 API<br>

<br>



## 리플렉션은 어떤 경우에 사용되는지 설명해주세요

코드 작성 시점에는 어떤 타입의 클래스를 사용할지 알 수 없고, 런타임 시점에 특정 클래스를 지정해서 실행시켜야 하는 경우에 사용.<br>

스프링에서 주로 사용하는 애노테이션들이 리플렉션을 적극적으로 활용하는 예.<br>

스프링은 프레임워크 레벨에서 리플렉션을 활용해서 애노테이션들에 대한 기능들을 구현해두고 여러가지 기능들을 제공해주고 있다<br>

<br>

인텔리제이 와 같은 IDE에서 제공하는 자동완성 기능도 리플렉션을 활용한 기능 중의 하나로 볼 수 있다.<br>

<br>



# Wrapper Class, Boxing, UnBoxing

기본 자료형을 객체 단위로 표현하는 클래스를 Wrapper Class 라고 한다.

기본 자료형을 표현하는 데에 있어서 필요한 여러가지 연산들을 포함하고 있고, 자료형에 대한 표준 연산들이 포함되어 있다.<br>

<br>

Boxing 

- 기본 자료형 -> Wrapper Class 타입으로 변환

<br>

Un Boxing

- Wrapper Class 타입 -> 기본 자료형 으로 변환

<br>



# String

## new String() 과 리터럴("") 의 차이점은?

`new String() `

- new 키워드로 String 타입의 새로운 객체를 생성한다.
- Heap 메모리 영역에 저장된다.

<br>

`""` (리터럴)

- Heap 안에 있는 String Constant Pool 영역에 저장된다.

<br>

![1](C:\Users\soong\workspace\gosgjung\job-interview\img\JAVA\1.png)

출처 : [What is String Pool? - journaldev.com](https://www.digitalocean.com/community/tutorials/what-is-java-string-pool)

예전에는 https://www.journaldev.com/797/what-is-java-string-pool 라는 곳에 게제 되었던 글인데, 현재는 https://www.digitalocean.com/community/tutorials/what-is-java-string-pool 에서 게제되는 중인 글.

<br>



## String, StringBuffer, StringBuilder 의 차이점

String

- 불변 성격을 가진다.

StringBuffer

- 가변 성격을 가진다.
- 동기화를 지원한다. 멀티스레드 환경에서 주로 사용된다.

StringBuilder

- 가변 성격을 가진다.
- 동기화를 지원하지 않기에 싱글스레드 환경에서 주로 사용된다.

<br>

**String, StringBuilder, StringBuffer의 차이점 문서** 는 따로 정리 중이다.<br>

문서 정리가 완료되면 정리해둘 예정

<br>



## String 객체가 불변으로 제공되는 것의 장점

뭐랄까... 불변으로 제공된다는 것은 String 객체를 접근할 때는 복사해서 제공한다는 이야기.<br>

<br>

- Thread Safe

- 보안

<br>

**Thread Safe**<br>

String 객체를 불변으로 제공하면, 여러 쓰레드에서 어떤 특정 String 객체를 동시에 접근해도 안전하다.<br>

<br>

**보안**<br>

중요한 데이터를 문자열로 다루는 경우 현재 참조하고 있는 문자열 값을 변경해서 다른 스레드에서의 관련된 로직에 사이드이펙트를 발생시키지 않는 다는 점은 장점이다.<br>

<br>



# String 의 불변으로 인한 단점 보완 - 캐싱처리

메모리 절약/성능 최적화를 위한 캐싱처리

- Java 의 String 객체들은 Heap 메모리영역의 String Constant Pool 이라는 영역에 저장된다.
- 이 때, 참조하려는 문자열이 String Constant Pool 에 존재할 경우, 새로 생성하지 않고 String Constant Pool 에 있는 객체를 사용한다.
- 따라서 특정 문자열 값을 재사용하는 빈도가 높을 수록 성능 향상을 기대할 수 있다.

<br>



# 접근제한자 (Access Modifier)

멤버필드, 멤버 메서드의 접근 범위를 설정해주기 위해 Java 에서 제공하는 예약어<br>

public

- 모든 곳에서의 접근이 가능하다

protected

- 상속을 받은 자식클래스라면, 다른 패키지에서든, 같은 패키지에서든 멤버필드, 멤버 메서드에 접근 가능하다.

default

- 해당 패키지에서만 접근 가능하다

private

- private 멤버 필드/메서드가 선언된 클래스 내에서만 사용가능하다.

<br>



# static 

## static 이란? 

참고자료 : [Java static 의 의미와 사용법](https://coding-factory.tistory.com/524)<br>

<br>



static 은 정적인 변수이고, 컴파일 타임에 값이 결정되는 변수.<br>

주로 공통으로 사용되는 값이나 한번 정의 된 후 변하지 않는 값들에 대해 적용을 검토하는 편.<br>

static 은 객체에 소속된 멤버가 아니라 클래스에 고정된 멤버다.<br>

클래스 로더가 클래스를 로딩해서 메소드 메모리 영역에 적재시, 클래스 별로 관리된다. 즉, 클래스 로딩이 끝나는 즉시 바로 사용할 수 있다.<br>

<br>



## static 을 사용하는 이유

런타임에 결정되는 값이 아니라 컴파일 타임에 값이 결정되어 정적인 값을 제공해야 하는 경우이거나,<br>

프로그램 로딩 후 프로그램 종료 시 까지 계속해서 메모리에 상주해야 하는 경우 static으로 선언한다.<br>

매번 메모리에 값을 로딩하기 보다는, 정적으로 값을 제공해서 일종의 전역변수 처럼 사용해야 할 경우에 `static` 키워드를 사용한다.<br>

<br>

인스턴스로 생성하지 않고도 바로 사용가능하기 때문에 프로그램 전역적으로 공통으로 사용해야 하는 데이터들을 관리할 때 자주 사용되는 편이다.<br>

<br>

우리가 자주 사용하는 스프링 프레임워크의 `MediaType` 역시 내부 구현을 보면 `static final` 로 선언된 필드들이 많다. 직관적으로 생각했을 때 보통은 Enum 으로 구현되었을 것이라는 생각도 들때가 있지만, 막상 내부 구현을 보면 static 블록과 static 멤버필드들로 구현되어 있다.<br>

<br>



## static 멤버필드, static 멤버메서드

참고자료 : [Java static 의 의미와 사용법](https://coding-factory.tistory.com/524)<br>

<br>

static 멤버 필드는 객체를 생성하지 않고도 클래스 내의 변수에 접근이 가능한 변수다. 객체 레벨이 아닌 클래스 레벨에서 멤버 필드에 접근할 수 있다.<br>

static 멤버 메서드는 객체를 생성하지 않고도 클래스 내부의 메서드에 접근이 가능한 메서드다. 객체 레벨이 아닌 클래스 레벨에서 멤버 필드에 접근할 수 있다.<br>

<br>



### static 멤버 필드

엄청 어려운 예제는 아니다. 엄청 중요한 예제도 아니다. 그런데, 그냥 한번씩 훑을때, 뭔가 느낌이 강하게 남는게 좋을 것 같아서 정리했다.<br>



e.g.

```java
class Number{
    static int num = 0;
    int num2 = 0;
}

public class StaticEx{
    public static void main(String [] args){
        Number n1 = new Number();
        Number n2 = new Number();
        
        n1.num++;		// static 변수 ++
        n1.num2++;		// 객체 변수 ++
        
        System.out.println(n2.num); 
        System.out.println(n2.num2);
    }
}
```



출력결과는 아래와 같다.

```plain
1
0
```



static 변수는 `n1.num++` 에서 증가시켜놓은 값 그대로 가지고 있고, 일반 객체 변수는 객체에서 자기만의 스코프를 가지기 때문에 `n1.num2++` 에서 증가시킨 값은 `n2.num2` 로 못불러온다.<br>

<br>



### static 메서드

```java
class Hello {
    static void engHello(){
        System.out.println("hello~");
    }
    
    void korHello(){
        System.out.println("헬로");
    }
}

public class StaticTest{
    public static void main (String [] args){
        Hello.engHello();			// 1) static 메서드 호출
        new Hello().korHello();		// 2) 객체의 메서드 호출
    }
}
```

<br>

`1 )`  

- static 메서드를 호출하고 있다.
- Hello 클래스의 static 메서드인 `engHello()` 메서드를 호출하고 있다.

<br>

`2 )` 

- Hello 클래스의 객체를 생성해서 `korHello()` 메서드를 호출하고 있다.

<br>



# Error, Exception

## Error 와 Exception 의 차이를 설명해주세요



## CheckedException 과 UncheckedException 의 차이를 설명해주세요









# 컬렉션 프레임워크



## 컬렉션 프레임워크에 대해 설명햇주세요

## List, Map, Stack, Queue 의 특징에 대해 설명해주세요

## Set과 Map 의 타입이 Wrapper Class 가 아닌 Object를 받을 때 중복 검사는 어떻게 할 것인지 설명해주세요

## Vector와 List의 차이를 설명해주세요







# 직렬화 (Serialize) 

## 직렬화에 대해 설명해주세요

## SerialVersionUID를 선언해야하는 이유에 대해 설명해주세요





우리가 자주 사용하는 스프링 프레임워크의 `MediaType` 이라는 클래스 역시 클래스 내에 멤버 필드로 아래와 같이 serialVersionUID 가 선언되어 있다.

```java
public class MediaType extends MimeType implements Serializable {

    // ...
    
	private static final long serialVersionUID = 2069937152339670231L;
    
    // ...
    
}
```


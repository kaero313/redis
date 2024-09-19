<h1>JAVA환경에서의 Redis 연동 및 다양한 활용 방법</h1>

<h3>1. Redis란?</h3>

- Key, Value 구조로 이루어진 비정형 데이터를 관리하기 위한 NoSQL이며 In-Memory 데이터베이스이다.

<br/>

<h3>2. Redis의 특징</h3>

<br/>

- 기본적으로 데이터를 메모리에 저장하여 처리하기 때문에 읽기 및 쓰기 작업 속도가 디스크 기반 데이터베이스보다 빠르다
- 별도의 쿼리문이 필요하지 않기 때문에 간편하게 사용이 가능하다
- String,JSON,Set,List 등 다양한 데이터 구조를 지원한다
- 싱글 스레드로 동작하기 때문에 한 번에 여러 요청을 수행할 수 없고, 시간이 오래 걸리는 요청을 수행하면 장애가 발생할 수 있다
- Master-Slave(Replica), Sentinel, Cluster을 활용한 HA구성을 하여 서버 이중화 가능

<br/>

<h3>3. Memory VS Disk</h3>

<br/>

![image](https://github.com/user-attachments/assets/92bafc28-4065-476e-bf48-00f66d0d468e)

> 하드웨어의 측면에서 보면 구조적으로 접근 속도에서 퍼포먼스 차이가 날 수 밖에 없음

<br/>

<h3>4. Redis 시스템 구성 방법</h3>

<h4>4-1. Master-Slave(Replica) 구성</h4>

![image](https://github.com/user-attachments/assets/443a61a9-dbfe-4f22-89c1-2a4a6960d144)
> Master는 1개 이상의 Slave를 가질 수 있다

- Slave는 Master의 데이터를 실시간으로 전달받아 저장한다.
- Master가 다운 시 Slave가 이를 대신할 수 있다.(수동으로 변경해야 함)

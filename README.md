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

- Slave는 Master의 데이터를 실시간으로 전달받아 저장한다
- Master가 다운 시 Slave가 이를 대신할 수 있다(수동으로 변경해야 함)

<br/>

<h4>4-2. Master-Slave(Replica) + Sentinel 구성</h4>

![image](https://github.com/user-attachments/assets/11a4f295-ef2f-46e3-a818-4a7e41f7a2fc)
> Sentinel을 각 서버에 한 대씩 설치하여 3대 이상의 홀수를 유지한다

- 센티널은 각 서버를 감시하고 있다가 마스터가 다운되면 과반수가 투표된 슬레이브를 마스터로 승격시킨다(홀수를 맞춰야 하는 이유)
- 다운되었던 마스터가 다시 시작되면 센티널이 슬레이브로 전환시킨다
- 레디스는 마스터가 모든 데이터를 가지고있기 때문에 슬레이브는 마스터의 복제본을 유지하고 있을 뿐이며, 데이터를 분산하지 않는다
- 따라서 장애가 발생하는 동안의 내용들을 완벽히 보장할 수는 없다
- 센티널은 데이터를 처리하지 않고 감시 역할만 한다
- 고가용성과 자동 Failover를 지원한다

<br/>

<h4>4-3. Master-Slave(Replica) + Cluster 구성</h4>

![image](https://github.com/user-attachments/assets/d5c8bb1a-e240-40f2-a177-5c98f4e0b63e)
> 서버1의 마스터가 다운되어도 다른 서버의 슬레이브가 서버1의 마스터를 대체한다

- 여러 노드에 데이터를 분산하여 저장한다(Sharding 방식)
- 고성능, 고가용성, Failover, 확장성이 좋다(스케일 아웃)
- 큰 트래픽이 없다면 오버스펙이 될 수도 있기 때문에, 비용 체크가 필요하다

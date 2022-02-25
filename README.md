# Beez_BackEnd
과학기술정보통신부가 주최한 혁신성장 블록체인 시스템 엔지니어 양성과정 프로젝트로
이더리움 기반 지역상권 선순환 지역화폐 시스템 입니다.

## 프로젝트 구조
│  .classpath                    
│  .factorypath                  
│  .gitignore                    
│  .project                      
│  keystore.p12
│  mvnw
│  mvnw.cmd
│  pom.xml
│
├─.mvn
│  └─wrapper
│          maven-wrapper.jar
│          maven-wrapper.properties
│          MavenWrapperDownloader.java
│
├─.settings
│      org.eclipse.core.resources.prefs
│      org.eclipse.jdt.apt.core.prefs
│      org.eclipse.jdt.core.prefs
│      org.eclipse.m2e.core.prefs
│
├─.vscode
│      launch.json
│
├─src
│  ├─main
│  │  ├─java
│  │  │  └─com
│  │  │      └─blockb
│  │  │          └─beez
│  │  │              │  BeezApplication.java
│  │  │              │
│  │  │              ├─controller
│  │  │              │      AddressController.java
│  │  │              │      ChargeController.java
│  │  │              │      ExchangeController.java
│  │  │              │      MapController.java
│  │  │              │      PassCheckController.java
│  │  │              │      UserController.java
│  │  │              │      WithdrawalController.java
│  │  │              │
│  │  │              ├─dao
│  │  │              │      AddressDao.java
│  │  │              │      ChargeDao.java
│  │  │              │      MapDao.java
│  │  │              │      nonceCheckDao.java
│  │  │              │      PassCheckDao.java
│  │  │              │      TransactionDao.java
│  │  │              │      UserDao.java
│  │  │              │      WithdrawalDao.java
│  │  │              │
│  │  │              ├─dto
│  │  │              │  │  AddressDto.java
│  │  │              │  │  AddressListDto.java
│  │  │              │  │  ChargeDto.java
│  │  │              │  │  ContractCADto.java
│  │  │              │  │  ExchangeDto.java
│  │  │              │  │  HistoryDto.java
│  │  │              │  │  KakaoLoginDto.java
│  │  │              │  │  LoginDto.java
│  │  │              │  │  MapDto.java
│  │  │              │  │  PassCheckDto.java
│  │  │              │  │  PaymentDto.java
│  │  │              │  │  UserDto.java
│  │  │              │  │  WithdrawalDto.java
│  │  │              │  │  WithdrawalHistoryDto.java
│  │  │              │  │
│  │  │              │  └─response
│  │  │              │          BaseResponse.java
│  │  │              │          ListDataResponse.java
│  │  │              │          MapDataResponse.java
│  │  │              │          SingleDataResponse.java
│  │  │              │
│  │  │              ├─exception
│  │  │              │      DuplicatedUsernameException.java
│  │  │              │      LoginFailedException.java
│  │  │              │      UserNotFoundException.java
│  │  │              │
│  │  │              ├─security
│  │  │              │      CustomAccessDeniedHandler.java
│  │  │              │      CustomAuthenticationEntryPoint.java
│  │  │              │      JwtFilter.java
│  │  │              │      SecurityConfig.java
│  │  │              │
│  │  │              ├─service
│  │  │              │      AddressService.java
│  │  │              │      ChargeService.java
│  │  │              │      Coolsms.java
│  │  │              │      CustomUserDetailsService.java
│  │  │              │      ExchangeService.java
│  │  │              │      MapService.java
│  │  │              │      PassCheckService.java
│  │  │              │      ResponseService.java
│  │  │              │      UserService.java
│  │  │              │      WithdrawalService.java
│  │  │              │
│  │  │              └─utils
│  │  │                      JwtTokenProvider.java
│  │  │
│  │  └─resources
│  │      │  application.properties
│  │      │
│  │      └─mapper
│  │              addressMapper.xml
│  │              chargeMapper.xml
│  │              mapMapper.xml
│  │              nonceCheckMapper.xml
│  │              passCheckMapper.xml
│  │              userMapper.xml
│  │              withdrawalMapper.xml
│  │
│  └─test
│      └─java
│          └─com
│              └─blockb
│                  └─beez
│                          BeezApplicationTests.java
├─wallets
│      UTC--2021-09-30T04-17-22.503Z--e96864b245de769fcc64c1e9f446*********
│
└─walletsdb
        cwallet.sso
        ewallet.p12
        keystore.jks
        ojdbc.properties
        README
        sqlnet.ora
        tnsnames.ora
        truststore.jks

# 프로그램 실행방법
 ./mvnw spring-boot:run


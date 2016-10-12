# EarthquakeAlarmForKakaotalk
- Spring Boot 으로 간단하게 만든 REST 서버
- 카카오톡 옐로우 아이디를 이용하여 지진 정보 제공

#기능
- 최근 지진 정보 (최근 5건)
- 대피 방법 
- 대피소 정보 제공(지역 선택)

# 이슈
- 일단 더이상 업데이트는 하지 않겠습니다.(마음에 안드는 부분이 있긴 하나 ..다른 개인 프로젝트 시작으로..) 
- 오류 없이 PR 하시면 확인후 바로 소스 반영 해드리겠습니다.

# Maven
- scp를 이용해서 패키징 후 리포트에 업로드를 사용 합니다.
- 아래 부분을 채워 사용 하시면 됩니다.
```xml
		<remote.server>{server ip}</remote.server>
		<remote.userid>{id}</remote.userid>
		<remote.password>{password}</remote.password>
		<remote.path>{path}</remote.path>
		<remote.port>{port}</remote.port>
```

#참고
- https://github.com/plusfriend/auto_reply
- https://itunes.apple.com/app/id990571676?mt=8
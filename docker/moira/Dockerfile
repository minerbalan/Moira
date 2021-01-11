FROM adoptopenjdk/openjdk8:alpine as cache

RUN mkdir -p /home/gradle/cache_home
ENV GRADLE_USER_HOME /home/gradle/cache_home
COPY build.gradle.kts /home/gradle/java-code/
COPY settings.gradle.kts /home/gradle/java-code/
COPY gradle/ /home/gradle/java-code/
COPY gradlew /home/gradle/java-code/
COPY gradlew.bat /home/gradle/java-code/
WORKDIR /home/gradle/java-code
# 依存関係をダウンロードしキャッシュする ソースコードがないのでビルドは失敗するが無視
RUN ./gradlew clean build --no-daemon > /dev/null 2>&1 || true

FROM adoptopenjdk/openjdk8:alpine

COPY --from=cache /home/gradle/cache_home /app/moira/.gradle
WORKDIR /app/moira

CMD ./gradlew bootRun

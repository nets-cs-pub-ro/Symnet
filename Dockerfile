FROM openjdk:8-jdk-alpine
MAINTAINER Johannes M. Scheuermann <ugene@student.kit.edu>

RUN apk --no-cache add ca-certificates wget openssl bash && \
    update-ca-certificates

RUN cd / && \
    wget https://dl.bintray.com/sbt/native-packages/sbt/0.13.9/sbt-0.13.9.tgz && \
    tar xf sbt-0.13.9.tgz && \
    rm *.tgz && \
    ln -s /sbt/bin/sbt /usr/local/bin/sbt

COPY . /Symnet

WORKDIR /Symnet

RUN sbt compile && \
    sbt sample

CMD bash

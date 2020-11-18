FROM dkron/dkron
WORKDIR /root/scheduler
COPY Scheduler.jar /root/scheduler

RUN apk add openjdk8

ENV JAVA_HOME /usr/lib/jvm/java-1.8-openjdk
ENV PATH $JAVA_HOME/bin:$PATH

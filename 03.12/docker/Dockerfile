FROM ubuntu:latest
RUN apt-get update && \
    apt-get install python3-pip -y && \
    pip3 install --upgrade pip && \
    pip3 install --upgrade virtualenv && \
    pip3 install ansible && \
    apt install openssh-server openssh-client -y && \
    apt install sshpass -y && \
    apt install net-tools

RUN mkdir -p /root/.ssh
RUN echo "HOST *" > /root/.ssh/config && echo " StrictHostKeyChecking no" >> /root/.ssh/config

# RUN useradd -rm -d /home/ubuntu -s /bin/bash -g root -G sudo -u 1000 test 
# EXPOSE 22

WORKDIR /

CMD tail -f /dev/null
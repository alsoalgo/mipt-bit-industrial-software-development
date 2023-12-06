FROM python:3.12.0-alpine3.18

WORKDIR /app

RUN ["mkdir", "src"]
COPY src src
COPY requirements.txt requirements.txt
RUN ["pip3", "install", "-r", "requirements.txt"]

CMD ["python3", "./src/app.py"]
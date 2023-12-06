FROM python:3.12.0-alpine3.18

WORKDIR /app

RUN ["mkdir", "src"]
COPY src src

CMD ["python3", "-m", "http.server", "8000"]
FROM python:3.11

RUN apt-get update
RUN apt-get install gcc

RUN pip install -U pip
RUN pip install poetry

COPY poetry.lock pyproject.toml /

RUN poetry config virtualenvs.create false \
  && poetry install --no-interaction --no-ansi

COPY toonstats /toonstats

EXPOSE 8080

CMD uvicorn toonstats.app.main:app --port 8080 --host '0.0.0.0' --workers 1

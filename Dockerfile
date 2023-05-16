FROM python:3.11

RUN apt-get update
RUN apt-get install gcc
RUN pip install poetry

COPY poetry.lock pyproject.toml /

RUN poetry config virtualenvs.create false \
  && poetry install --no-interaction --no-ansi

COPY toonstats /toonstats

CMD ["python", "-m", "toonstats"]
